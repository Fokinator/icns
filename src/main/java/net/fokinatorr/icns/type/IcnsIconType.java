package net.fokinatorr.icns.type;

import lombok.Getter;

import static net.fokinatorr.icns.impl.IcnsTypeUtils.*;

/**
 * ICNS icon types.
 * <p>
 * The code is based on {@code org.apache.tika.parser.image.ICNSType}.
 */
public enum IcnsIconType implements IcnsType {
    ICNS_32x32_1BIT_IMAGE("ICON", 32, 32, 1, false, false),
    ICNS_16x12_1BIT_IMAGE_AND_MASK("icm#", 16, 12, 1, true, false),
    ICNS_16x12_4BIT_IMAGE("icm4", 16, 12, 4, false, false),
    ICNS_16x12_8BIT_IMAGE("icm8", 16, 12, 8, false, false),

    ICNS_16x16_8BIT_MASK("s8mk", 16, 16, 8, true, false),
    ICNS_16x16_1BIT_IMAGE_AND_MASK("ics#", 16, 16, 1, true, false),
    ICNS_16x16_4BIT_IMAGE("ics4", 16, 16, 4, false, false),
    ICNS_16x16_8BIT_IMAGE("ics8", 16, 16, 8, false, false),
    ICNS_16x16_24BIT_IMAGE("is32", 16, 16, 24, false, false),

    ICNS_32x32_8BIT_MASK("l8mk", 32, 32, 8, true, false),
    ICNS_32x32_1BIT_IMAGE_AND_MASK("ICN#", 32, 32, 1, true, false),
    ICNS_32x32_4BIT_IMAGE("icl4", 32, 32, 4, false, false),
    ICNS_32x32_8BIT_IMAGE("icl8", 32, 32, 8, false, false),
    ICNS_32x32_24BIT_IMAGE("il32", 32, 32, 24, false, false),

    ICNS_48x48_8BIT_MASK("h8mk", 48, 48, 8, true, false),
    ICNS_48x48_1BIT_IMAGE_AND_MASK("ich#", 48, 48, 1, true, false),
    ICNS_48x48_4BIT_IMAGE("ich4", 48, 48, 4, false, false),
    ICNS_48x48_8BIT_IMAGE("ich8", 48, 48, 8, false, false),
    ICNS_48x48_24BIT_IMAGE("ih32", 48, 48, 24, false, false),
    ICNS_128x128_8BIT_MASK("t8mk", 128, 128, 8, true, false),
    ICNS_128x128_24BIT_IMAGE("it32", 128, 128, 24, false, false),

    ICNS_16x16_JPEG_PNG_IMAGE("icp4", 16, 16, 0, false, false),
    ICNS_32x32_JPEG_PNG_IMAGE("icp5", 32, 32, 0, false, false),
    ICNS_64x64_JPEG_PNG_IMAGE("icp6", 64, 64, 0, false, false),
    ICNS_128x128_JPEG_PNG_IMAGE("ic07", 128, 128, 0, false, false),
    ICNS_256x256_JPEG_PNG_IMAGE("ic08", 256, 256, 0, false, false),
    ICNS_512x512_JPEG_PNG_IMAGE("ic09", 512, 512, 0, false, false),
    ICNS_16x16_2X_JPEG_PNG_IMAGE("ic11", 32, 32, 0, false, true),
    ICNS_32x32_2X_JPEG_PNG_IMAGE("ic12", 64, 64, 0, false, true),
    ICNS_128x128_2X_JPEG_PNG_IMAGE("ic13", 256, 256, 0, false, true),
    ICNS_256x256_2X_JPEG_PNG_IMAGE("ic14", 512, 512, 0, false, true),
    ICNS_512x512_2X_JPEG_PNG_IMAGE("ic10", 1024, 1024, 0, false, true);

    /**
     * The {@code OSType} value for this format.
     */
    @Getter
    private final String osType;
    /**
     * The compressed {@code OSType} value for encoding.
     */
    @Getter
    private final int ioType;
    /**
     * The width of this icon format.
     */
    @Getter
    private final int width;
    /**
     * The height of this icon format.
     */
    @Getter
    private final int height;
    /**
     * The count of bits per pixel of this icon format.
     */
    @Getter
    private final int bitsPerPixel;
    private final boolean hasMask;
    /**
     * Whether this icon format is for Retina displays (2x pixel density).
     */
    @Getter
    private final boolean isRetina;

    IcnsIconType(String osType, int width, int height, int bitsPerPixel, boolean hasMask, boolean isRetina) {
        this.osType = osType;
        this.ioType = compressOSType(osType);
        this.width = width;
        this.height = height;
        this.bitsPerPixel = bitsPerPixel;
        this.hasMask = hasMask;
        this.isRetina = isRetina;
    }

    /**
     * Returns whether this icon format includes a mask.
     * @return whether this format has a mask
     */
    public boolean hasMask() {
        return hasMask;
    }

    /**
     * Returns an icon type constant corresponding to the given {@code OSType} value, or {@code null}
     * if no match found.
     * @param osType the {@code OSType} value
     * @return the corresponding icon type or {@code null}
     */
    public static IcnsIconType of(String osType) {
        for (var i : values()) {
            if (i.osType.equals(osType)) {
                return i;
            }
        }
        return null;
    }

    /**
     * Returns an icon type constant corresponding to the given compressed {@code OSType} value, or {@code null}
     * if no match found.
     * @param ioType the compressed {@code OSType} value
     * @return the corresponding icon type or {@code null}
     * @see IcnsType#getIoType()
     */
    public static IcnsIconType of(int ioType) {
        for (var i : values()) {
            if (i.ioType == ioType) {
                return i;
            }
        }
        return null;
    }
}
