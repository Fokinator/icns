package net.fokinatorr.icns.impl;

import lombok.experimental.UtilityClass;

import java.util.Arrays;

import static java.nio.charset.StandardCharsets.US_ASCII;

/**
 * {@code OSType}-related utilities.
 */
@UtilityClass
public class IcnsTypeUtils {

    /**
     * Converts the given {@code OSType} value into an {@code int}. The string must
     * consist of exactly 4 characters, which all must be ASCII symbols.
     * @param osType the string
     * @return an integer from the given string
     */
    public int compressOSType(String osType) {
        return compressOSType(osType.getBytes(US_ASCII));
    }

    /**
     * Compresses the given {@code OSType} byte array into an integer. The array must contain exactly 4 elements.
     * @param bytes the byte array
     * @return a compressed integer
     */
    public int compressOSType(byte[] bytes) {
        if (bytes.length != 4) {
            throw new InternalError("BUG: not 4-long byte array: " + Arrays.toString(bytes));
        }
        return bytes[0] << 24 | bytes[1] << 16 | bytes[2] << 8 | bytes[3];
    }

    /**
     * Converts the given ASCII bytes into a string.
     * @param a the byte array
     * @return an ASCII string
     */
    public String decompressOSType(byte[] a) {
        return new String(a, US_ASCII);
    }
}
