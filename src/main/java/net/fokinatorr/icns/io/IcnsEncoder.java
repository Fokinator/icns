package net.fokinatorr.icns.io;

import lombok.experimental.UtilityClass;
import net.fokinatorr.icns.IcnsIcons;
import net.fokinatorr.icns.records.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static net.fokinatorr.icns.io.IOConstants.*;

/**
 * Encodes ICNS objects into byte streams.
 */
@UtilityClass
public class IcnsEncoder {

    /**
     * Writes the given ICNS object into the given output stream.
     * @param icns the ICNS object
     * @param out the output stream
     * @throws IOException if an error occurs writing to the output stream
     */
    public void write(IcnsIcons icns, OutputStream out) throws IOException {
        List<IcnsRecord<?, ?>> rec = icns.records();
        DataOutputStream dos = new DataOutputStream(out);

        int tocSize = HEADER_SIZE;
        int size = HEADER_SIZE;
        for (var r : rec) {
            if (r instanceof IcnsIcon) {
                tocSize += HEADER_SIZE;
            }
            size += HEADER_SIZE + r.length();
        }
        size += tocSize;

        // Header
        dos.writeInt(MAGIC);
        dos.writeInt(size);

        writeNoHeader(icns, dos, tocSize);
        dos.flush();
    }

    private void writeNoHeader(IcnsIcons icns, DataOutputStream dos, int tocSize) throws IOException {
        List<IcnsRecord<?, ?>> rec = icns.records();
        if (tocSize == -1) {
            tocSize = HEADER_SIZE;
            for (var r : rec) {
                if (r instanceof IcnsIcon) {
                    tocSize += HEADER_SIZE;
                }
            }
        }

        // TOC header
        dos.writeInt(TOC);
        dos.writeInt(tocSize);
        // TOC
        for (var r : rec) {
            if (r instanceof IcnsIcon) {
                dos.writeInt(r.type().getIoType());
                dos.writeInt(HEADER_SIZE + r.length());
            }
        }

        for (var r : rec) {
            // record header
            dos.writeInt(r.type().getIoType());
            dos.writeInt(HEADER_SIZE + r.length());
            // record data
            switch (r) {
                case IcnsIcon icon -> writeByteBuffer(icon.data(), dos);
                case IcnsNestedIcns nestedIcns -> writeNoHeader(nestedIcns.data(), dos, -1);
                case IcnsIconComposerVersion(float floatData) -> dos.writeFloat(floatData);
                case IcnsName(String name) -> dos.write(name.getBytes(StandardCharsets.UTF_8));
                case IcnsInfo(ByteBuffer binPlist) -> writeByteBuffer(binPlist, dos);
            }
        }
    }

    private void writeByteBuffer(ByteBuffer buf, DataOutputStream dos) throws IOException {
        if (buf.hasArray()) {
            dos.write(buf.array());
        } else {
            byte[] a = new byte[buf.remaining()];
            buf.get(a);
            dos.write(a);
        }
    }
}
