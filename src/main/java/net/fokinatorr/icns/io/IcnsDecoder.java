package net.fokinatorr.icns.io;

import lombok.experimental.UtilityClass;
import net.fokinatorr.icns.IcnsIcons;
import net.fokinatorr.icns.records.*;
import net.fokinatorr.icns.type.IcnsIconType;
import net.fokinatorr.icns.type.IcnsNestedIcnsType;
import net.fokinatorr.icns.type.IcnsOtherType;
import net.fokinatorr.icns.type.IcnsType;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static net.fokinatorr.icns.io.IOConstants.*;

/**
 * Decodes ICNS objects from byte streams.
 */
@UtilityClass
public class IcnsDecoder {

    /**
     * Reads an ICNS object from the given input stream.
     * @param in the input stream
     * @return a parsed ICNS object
     * @throws StreamCorruptedException if unable to parse an ICNS object
     * @throws EOFException if an unexpected end-of-file occurs
     * @throws IOException if an error occurs reading from the input stream
     */
    public IcnsIcons read(InputStream in) throws IOException {
        DataInputStream dis = new DataInputStream(in);

        // header
        if (dis.readInt() != MAGIC) {
            throw new StreamCorruptedException("Invalid ICNS header: missing magic sequence");
        }
        int remaining = dis.readInt() - HEADER_SIZE;
        if (remaining < 0) {
            throw new StreamCorruptedException("Invalid ICNS header: ICNS size less than header size");
        }

        return readNoHeader(remaining, dis);
    }

    private IcnsIcons readNoHeader(int remaining, DataInputStream dis) throws IOException {
        List<IcnsRecord<?, ?>> records = new ArrayList<>();

        while (remaining > 0) {
            int recIoType = dis.readInt();
            int recSize = dis.readInt();
            int recRemaining = recSize - HEADER_SIZE;
            if (recRemaining < 0) {
                throw new StreamCorruptedException("Invalid ICNS record header: record size less than record header size");
            }
            if (recIoType != TOC) {
                IcnsType recType = IcnsType.of(recIoType);
                if (recType instanceof IcnsIconType iconType) {
                    ByteBuffer recData = ByteBuffer.allocate(recRemaining);
                    dis.readFully(recData.array());
                    records.add(new IcnsIcon(iconType, recData));
                } else if (recType instanceof IcnsNestedIcnsType nestedIcnsType) {
                    IcnsIcons nestedIcns = readNoHeader(recRemaining, dis);
                    records.add(new IcnsNestedIcns(nestedIcnsType, nestedIcns));
                } else switch ((IcnsOtherType) recType) {
                    case ICNS_ICON_COMPOSER_VERSION -> records.add(new IcnsIconComposerVersion(dis.readFloat()));
                    case ICNS_NAME -> {
                        byte[] utf = new byte[recRemaining];
                        dis.readFully(utf);
                        records.add(new IcnsName(new String(utf, StandardCharsets.UTF_8)));
                    }
                    case ICNS_INFO -> {
                        // not parsing binary plist, see IcnsInfo for the reason
                        ByteBuffer recData = ByteBuffer.allocate(recRemaining);
                        dis.readFully(recData.array());
                        records.add(new IcnsInfo(recData));
                    }
                }
            } else {
                // table of contents can be recreated so we discard it
                dis.skipNBytes(recRemaining);
            }
            remaining -= recSize;
        }

        return IcnsIcons.of(records);
    }
}
