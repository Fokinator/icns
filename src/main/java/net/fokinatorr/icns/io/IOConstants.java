package net.fokinatorr.icns.io;

import lombok.experimental.UtilityClass;

import static net.fokinatorr.icns.impl.IcnsTypeUtils.*;

/**
 * I/O constants for the ICNS format.
 */
@UtilityClass
class IOConstants {
    /**
     * "Table of Contents" compressed {@code OSType} value.
     * <p>This is an optional ICNS record that enables effective querying of icon metadata.
     */
    public final int TOC = compressOSType("TOC "); // intentional space in the end
    /**
     * The magic number identifying ICNS format.
     */
    public final int MAGIC = compressOSType("icns");
    /**
     * Size of the ICNS header and record header.
     */
    public final int HEADER_SIZE = 8;
}
