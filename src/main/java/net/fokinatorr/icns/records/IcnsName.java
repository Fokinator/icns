package net.fokinatorr.icns.records;

import net.fokinatorr.icns.type.IcnsOtherType;

import java.nio.charset.StandardCharsets;

/**
 * ICNS name record. Usage unknown.
 * @param data the name
 */
public record IcnsName(String data) implements IcnsRecord<IcnsOtherType, String> {
    @Override
    public IcnsOtherType type() {
        return IcnsOtherType.ICNS_NAME;
    }

    @Override
    public int length() {
        return data.getBytes(StandardCharsets.UTF_8).length;
    }
}
