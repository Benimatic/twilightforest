package twilightforest.enums;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum TowerDeviceVariant implements IStringSerializable {
	BUILDER_INACTIVE,
	BUILDER_ACTIVE,
	BUILDER_TIMEOUT;

	@Override
	public String getString() {
		return name().toLowerCase(Locale.ROOT);
	}
}
