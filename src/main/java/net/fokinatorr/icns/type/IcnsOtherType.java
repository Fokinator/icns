package net.fokinatorr.icns.type;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;
import static net.fokinatorr.icns.impl.IcnsTypeUtils.*;

/**
 * ICNS other types.
 */
@FieldDefaults(makeFinal = true, level = PRIVATE)
@Getter
public enum IcnsOtherType implements IcnsType {
    /**
     * Bundle version number of {@code Icon Composer.app} that created the icon.
     * <p>Format: single 4-byte big-endian floating-point number (Java {@code float})
     */
    ICNS_ICON_COMPOSER_VERSION("icnV"),
    /**
     * <p>Format: bytes representing a UTF-8 string
     */
    ICNS_NAME("name"),
    /**
     * <p>Format: a binary {@code .plist}
     */
    ICNS_INFO("info");

    String osType;
    int ioType;

    IcnsOtherType(String osType) {
        this.osType = osType;
        this.ioType = compressOSType(osType);
    }

    /**
     * Returns a type constant corresponding to the given {@code OSType} value, or {@code null}
     * if no match found.
     * @param osType the {@code OSType} value
     * @return the corresponding nested ICNS object or {@code null}
     */
    public static IcnsOtherType of(String osType) {
        for (var i : values()) {
            if (i.osType.equals(osType)) {
                return i;
            }
        }
        return null;
    }

    /**
     * Returns a type constant corresponding to the given compressed {@code OSType} value,
     * or {@code null}  if no match found.
     * @param ioType the compressed {@code OSType} value
     * @return the corresponding nested ICNS object type or {@code null}
     * @see IcnsType#getIoType()
     */
    public static IcnsOtherType of(int ioType) {
        for (var i : values()) {
            if (i.ioType == ioType) {
                return i;
            }
        }
        return null;
    }
}
