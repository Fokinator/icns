package net.fokinatorr.icns.records;

import net.fokinatorr.icns.type.IcnsOtherType;

import java.nio.ByteBuffer;

/**
 * ICNS binary {@code .plist} info record.
 * @param data the binary {@code .plist} data
 */
public record IcnsInfo(ByteBuffer data) implements IcnsRecord<IcnsOtherType, ByteBuffer> {
    // the macOS binary plist format is opaque, and we aim to be lightweight, so we don't parse it here

    @Override
    public IcnsOtherType type() {
        return IcnsOtherType.ICNS_INFO;
    }

    /**
     * {@inheritDoc} The returned buffer cannot be modified.
     * @return {@inheritDoc}
     */
    @Override
    public ByteBuffer data() {
        return data.asReadOnlyBuffer();
    }

    @Override
    public int length() {
        // respect the position & limit of the supplied buffer
        return data.remaining();
    }
}
