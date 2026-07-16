package net.fokinatorr.icns.records;

import net.fokinatorr.icns.type.IcnsOtherType;

/**
 * ICNS record representing the bundle version number of {@code Icon Composer.app} that created the icon.
 * @param floatData the version number
 */
@SuppressWarnings("UnnecessaryBoxing")
public record IcnsIconComposerVersion(float floatData) implements IcnsRecord<IcnsOtherType, Float> {
    @Override
    public IcnsOtherType type() {
        return IcnsOtherType.ICNS_ICON_COMPOSER_VERSION;
    }

    /**
     * {@inheritDoc}
     * It is recommended to use {@link #floatData()} instead to avoid boxing/unboxing overhead.
     * @return {@inheritDoc}
     */
    @Override
    public Float data() {
        return Float.valueOf(floatData);
    }

    @Override
    public int length() {
        return Float.BYTES;
    }
}
