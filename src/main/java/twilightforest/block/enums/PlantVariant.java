package twilightforest.block.enums;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum PlantVariant implements IStringSerializable {
    MOSSPATCH(3, false),
    MAYAPPLE(4, true),
    CLOVERPATCH(5, true),
    FIDDLEHEAD(8, true),
    MUSHGLOOM(9, false),
    FORESTGRASS(10, true),
    DEADBUSH(11, false),
    TORCHBERRY(13, false),
    ROOT_STRAND(14, false);

    public final int itemMetadata;
    public final boolean isGrassColored;

    PlantVariant(int itemMetadata, boolean isGrassColored) {
        this.itemMetadata = itemMetadata;
        this.isGrassColored = isGrassColored;
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
