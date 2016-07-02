package twilightforest.block.state.enums;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum SpawnerVariant implements IStringSerializable {
    NAGA,
    LICH,
    HYDRA,
    TOWER,
    KNIGHT_PHANTOM,
    SNOW_QUEEN;

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
