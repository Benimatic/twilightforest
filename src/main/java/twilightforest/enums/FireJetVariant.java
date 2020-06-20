package twilightforest.enums;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum FireJetVariant implements IStringSerializable {
	IDLE,
	POPPING,
	FLAME;

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public String getName() {
		return name().toLowerCase(Locale.ROOT);
	}
}
