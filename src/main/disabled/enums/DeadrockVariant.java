package twilightforest.enums;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum DeadrockVariant implements IStringSerializable {
	SURFACE,
	CRACKED,
	SOLID;

	@Override
	public String func_176610_l() {
		return name().toLowerCase(Locale.ROOT);
	}
}
