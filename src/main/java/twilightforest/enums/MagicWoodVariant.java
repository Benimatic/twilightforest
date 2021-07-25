package twilightforest.enums;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum MagicWoodVariant implements StringRepresentable {

	TIME,
	TRANS,
	MINE,
	SORT;

	@Override
	public String getSerializedName() {
		return name().toLowerCase(Locale.ROOT);
	}
}
