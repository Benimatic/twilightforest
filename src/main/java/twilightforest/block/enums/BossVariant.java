package twilightforest.block.enums;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum BossVariant implements IStringSerializable {
    // Enum for all bosses basically. NONE is for none.
    NONE(false),
    NAGA(true),
    LICH(true),
    HYDRA(true),
    URGHAST(true),
    KNIGHT_PHANTOM(false),
    SNOW_QUEEN(true);

    private final boolean hasTrophy;

    BossVariant(boolean isNotMiniBoss) {
        this.hasTrophy = isNotMiniBoss;
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public boolean hasTrophy() {
        return hasTrophy;
    }
}
