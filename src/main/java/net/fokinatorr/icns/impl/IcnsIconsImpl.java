package net.fokinatorr.icns.impl;

import net.fokinatorr.icns.IcnsIcons;
import net.fokinatorr.icns.records.IcnsRecord;

import java.util.List;

/**
 * Default implementation of {@link IcnsIcons}.
 */
public record IcnsIconsImpl(List<IcnsRecord<?, ?>> records) implements IcnsIcons {
}
