package twilightforest.block.enums;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum PlantVariant implements IStringSerializable {
    MOSSPATCH,
    MAYAPPLE,
    CLOVERPATCH,
    FIDDLEHEAD,
    MUSHGLOOM,
    FORESTGRASS,
    DEADBUSH,
    TORCHBERRY,
    ROOT_STRAND;

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
