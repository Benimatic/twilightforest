package twilightforest.block;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import twilightforest.client.ModelRegisterCallback;

public class BlockTFPressurePlate extends BlockPressurePlate implements ModelRegisterCallback {
    public BlockTFPressurePlate(Material wood, BlockPressurePlate.Sensitivity sensitivity) {
        super(wood, sensitivity);
    }
}
