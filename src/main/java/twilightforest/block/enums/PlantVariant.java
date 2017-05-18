package twilightforest.block.enums;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum PlantVariant implements IStringSerializable {
    MOSSPATCH(3),
    MAYAPPLE(4),
    CLOVERPATCH(5),
    FIDDLEHEAD(8),
    MUSHGLOOM(9),
    FORESTGRASS(10),
    DEADBUSH(11),
    TORCHBERRY(13),
    ROOT_STRAND(14);

    public final int itemMetadata;

    PlantVariant(int itemMetadata) {
        this.itemMetadata = itemMetadata;
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
