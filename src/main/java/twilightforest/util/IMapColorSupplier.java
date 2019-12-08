package twilightforest.util;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.MaterialColor;

public interface IMapColorSupplier {
    default MaterialColor supplyMapColor() {
        return supplyPlankColor().getMaterialColor();
    }

    BlockPlanks.EnumType supplyPlankColor();
}
