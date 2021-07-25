package twilightforest.enums;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum PlantVariant implements StringRepresentable {
	MOSSPATCH(),
	MAYAPPLE(),
	CLOVERPATCH(),
	FIDDLEHEAD(true),
	MUSHGLOOM(),
	TORCHBERRY(),
	ROOT_STRAND(),
	FALLEN_LEAVES(true, true);

	public final boolean isColored;
	public final boolean isLeaves;

	PlantVariant() {
		this(false);
	}

	PlantVariant(boolean isColored) {
		this(isColored, false);
	}

	PlantVariant(boolean isColored, boolean isLeaves) {
		this.isColored = isColored;
		this.isLeaves = isLeaves;
	}

	@Override
	public String getSerializedName() {
		return name().toLowerCase(Locale.ROOT);
	}
}
