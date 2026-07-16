package net.fokinatorr.icns;

import lombok.NonNull;
import net.fokinatorr.icns.impl.IcnsIconsImpl;
import net.fokinatorr.icns.records.IcnsRecord;

import java.util.List;

/**
 * Represents an ICNS object.
 * @see IcnsRecord
 * @see #of(IcnsRecord...)
 * @see #of(List)
 */
public interface IcnsIcons {

    /**
     * Returns a list of ICNS records.
     *
     * @return unmodifiable list of records
     */
    List<IcnsRecord<?, ?>> records();

    /**
     * Creates an ICNS object with the given records.
     * @param entries the records
     * @return a new ICNS object
     */
    static IcnsIcons of(@NonNull List<? extends IcnsRecord<?, ?>> entries) {
        return new IcnsIconsImpl(List.copyOf(entries));
    }

    /**
     * Array version of {@link #of(List)}.
     * @param entries the records
     * @return a new ICNS object
     */
    static IcnsIcons of(@NonNull IcnsRecord<?, ?>... entries) {
        return new IcnsIconsImpl(List.of(entries));
    }
}
