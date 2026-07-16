package net.fokinatorr.icns.type;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;
import static net.fokinatorr.icns.impl.IcnsTypeUtils.*;

/**
 * ICNS nested ICNS object types.
 */
@FieldDefaults(makeFinal = true, level = PRIVATE)
@Getter
public enum IcnsNestedIcnsType implements IcnsType {
    /**
     * Nested "template" ICNS object. Usage unknown.
     */
    ICNS_TEMPLATE_NESTED_ICNS("sbtp"),
    /**
     * Nested ICNS object used for "selected" state.
     */
    ICNS_SELECTED_NESTED_ICNS("slct"),
    /**
     * Nested ICNS object used for dark mode.
     */
    ICNS_DARK_NESTED_ICNS(new byte[] {(byte) 0xFD, (byte) 0xD9, 0x2F, (byte) 0xA8});

    String osType;
    int ioType;

    IcnsNestedIcnsType(String osType) {
        this.osType = osType;
        this.ioType = compressOSType(osType);
    }

    IcnsNestedIcnsType(byte[] osType) {
        this.osType = decompressOSType(osType);
        this.ioType = compressOSType(osType);
    }

    /**
     * Returns a nested ICNS object type constant corresponding to the given {@code OSType} value, or {@code null}
     * if no match found.
     * @param osType the {@code OSType} value
     * @return the corresponding nested ICNS object or {@code null}
     */
    public static IcnsNestedIcnsType of(String osType) {
        for (var i : values()) {
            if (i.osType.equals(osType)) {
                return i;
            }
        }
        return null;
    }

    /**
     * Returns a nested ICNS object type constant corresponding to the given compressed {@code OSType} value,
     * or {@code null}  if no match found.
     * @param ioType the compressed {@code OSType} value
     * @return the corresponding nested ICNS object type or {@code null}
     * @see IcnsType#getIoType()
     */
    public static IcnsNestedIcnsType of(int ioType) {
        for (var i : values()) {
            if (i.ioType == ioType) {
                return i;
            }
        }
        return null;
    }
}
