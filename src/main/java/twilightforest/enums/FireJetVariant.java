package twilightforest.enums;

import net.minecraft.util.IStringSerializable;

public enum FireJetVariant implements IStringSerializable {
	IDLE("idle"),
	POPPING("popping"),
	FLAME("flame");

	final String name;

	FireJetVariant(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String getName() {
		return name;
	}
}
