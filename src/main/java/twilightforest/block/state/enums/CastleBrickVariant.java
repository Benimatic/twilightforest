package twilightforest.block.state.enums;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum CastleBrickVariant implements IStringSerializable {
    NORMAL,
    WORN,
    CRACKED,
    ROOF;

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
