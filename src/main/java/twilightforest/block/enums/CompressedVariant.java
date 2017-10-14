package twilightforest.block.enums;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum CompressedVariant implements IStringSerializable {
    IRONWOOD,
    FIERY,
    STEELLEAF,
    ARTIC_FUR,
    CARMINITE;

    @Override
    public String getName() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
