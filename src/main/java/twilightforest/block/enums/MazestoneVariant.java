package twilightforest.block.enums;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum MazestoneVariant implements IStringSerializable {
    PLAIN,
    BRICK,
    PILLAR,
    DECORATIVE,
    CRACKED,
    MOSSY,
    MOSAIC,
    BORDER;

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
