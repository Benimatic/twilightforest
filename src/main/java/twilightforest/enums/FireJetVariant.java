package twilightforest.enums;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum FireJetVariant implements IStringSerializable {
	IDLE,
	POPPING,
	FLAME;

	@Override
	public String toString() {
		return func_176610_l();
	}

	@Override
	public String func_176610_l() {
		return name().toLowerCase(Locale.ROOT);
	}
}
