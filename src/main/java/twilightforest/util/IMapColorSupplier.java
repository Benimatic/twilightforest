package twilightforest.util;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.MaterialColor;

//TODO: Probably not needed?
public interface IMapColorSupplier {
    default MaterialColor supplyMapColor() {
        return supplyPlankColor().getMaterialColor();
    }

    BlockPlanks.EnumType supplyPlankColor();
}
