package net.fokinatorr.icns.records;

import lombok.NonNull;
import net.fokinatorr.icns.type.IcnsIconType;

import java.nio.ByteBuffer;

/**
 * ICNS icon record.
 * @param type the icon type
 * @param data the icon data
 */
public record IcnsIcon(@NonNull IcnsIconType type, @NonNull ByteBuffer data) implements IcnsRecord<IcnsIconType, ByteBuffer> {

    /**
     * {@inheritDoc}
     * The returned buffer cannot be modified.
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
