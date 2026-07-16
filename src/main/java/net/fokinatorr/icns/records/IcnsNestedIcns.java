package net.fokinatorr.icns.records;

import lombok.NonNull;
import net.fokinatorr.icns.IcnsIcons;
import net.fokinatorr.icns.type.IcnsNestedIcnsType;

/**
 * ICNS nested ICNS record.
 * @param type the type of the ICNS object
 * @param data the ICNS object
 */
public record IcnsNestedIcns(@NonNull IcnsNestedIcnsType type, @NonNull IcnsIcons data) implements IcnsRecord<IcnsNestedIcnsType, IcnsIcons> {
    @Override
    public int length() {
        return data.records().stream()
                .mapToInt(IcnsRecord::length)
                .sum();
    }
}
