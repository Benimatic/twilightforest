package twilightforest.block;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.item.TFItems;

public class BlockTFAuroralizedGlass extends BlockGlass implements ModelRegisterCallback {

    public BlockTFAuroralizedGlass() {
        super(Material.ICE, false);
        this.setCreativeTab(TFItems.creativeTab);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
