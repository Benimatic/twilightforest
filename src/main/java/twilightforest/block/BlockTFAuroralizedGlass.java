package twilightforest.block;

import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

public class BlockTFAuroralizedGlass extends AbstractGlassBlock {

    public BlockTFAuroralizedGlass() {
        super(Properties.create(Material.ICE));
        //this.setCreativeTab(TFItems.creativeTab); TODO 1.14
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
