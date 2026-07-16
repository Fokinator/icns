package net.fokinatorr.icns.records;

import net.fokinatorr.icns.type.IcnsType;

/**
 * An ICNS record.
 * @param <T> the type group of the record
 * @param <D> the type of the record data
 */
public sealed interface IcnsRecord<T extends IcnsType, D> permits IcnsIcon, IcnsIconComposerVersion, IcnsInfo, IcnsName, IcnsNestedIcns {

    /**
     * {@return the type of the record}
     */
    T type();

    /**
     * {@return the data of this record}
     */
    D data();

    /**
     * {@return the length in bytes of this record (excluding the header)} Used for writing.
     */
    int length();
}
