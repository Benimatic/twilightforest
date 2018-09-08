package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import twilightforest.client.ModelRegisterCallback;

public class BlockTF extends Block implements ModelRegisterCallback {
    public BlockTF(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    public BlockTF(Material materialIn) {
        super(materialIn);
    }
}
