package twilightforest.block.enums;

import com.google.common.collect.ImmutableBiMap;
import net.minecraft.util.IStringSerializable;

import java.util.Locale;

// Meta for legacy
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

    public final int meta;
    PlantVariant(int meta) {
        this.meta = meta;
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    private static final ImmutableBiMap<Integer, PlantVariant> LOOKUP;

    static {
        ImmutableBiMap.Builder<Integer, PlantVariant> builder = ImmutableBiMap.builder();
        for (PlantVariant v : values()) {
            builder.put(v.meta, v);
        }
        LOOKUP = builder.build();
    }
}
