package net.fokinatorr.icns.type;

/**
 * An ICNS record type.
 */
public sealed interface IcnsType permits IcnsIconType, IcnsNestedIcnsType, IcnsOtherType {

    /**
     * Returns the {@code OSType} value of this record type.
     * @return the corresponding {@code OSType} value
     */
    String getOsType();

    /**
     * Returns the compressed {@code OSType} value of this record type (typically, the ASCII bytes of the
     * {@code OSType} value concatenated into a 32-bit integer value). Used for writing/reading ICNS objects.
     * @return the corresponding compressed {@code OSType} value
     */
    int getIoType();

    /**
     * Looks up the record type constant corresponding to the given {@code OSType} value.
     * @param osType the {@code OSType} value
     * @return the corresponding record type
     * @throws IllegalArgumentException if no match found
     */
    static IcnsType of(String osType) {
        IcnsType type = IcnsIconType.of(osType);
        if (type == null) {
            type = IcnsOtherType.of(osType);
            if (type == null) {
                type = IcnsNestedIcnsType.of(osType);
                if (type == null) {
                    throw new IllegalArgumentException("No matching record type for '" + osType + "'");
                }
            }
        }
        return type;
    }

    /**
     * Looks up the record type constant corresponding to the given compressed {@code OSType} value.
     * @param ioType the compressed {@code OSType} value
     * @return the corresponding record type
     * @throws IllegalArgumentException if no match found
     * @see #getIoType()
     */
    static IcnsType of(int ioType) {
        IcnsType type = IcnsIconType.of(ioType);
        if (type == null) {
            type = IcnsOtherType.of(ioType);
            if (type == null) {
                type = IcnsNestedIcnsType.of(ioType);
                if (type == null) {
                    throw new IllegalArgumentException("No matching record type for '" + Integer.toHexString(ioType) + "'");
                }
            }
        }
        return type;
    }
}
