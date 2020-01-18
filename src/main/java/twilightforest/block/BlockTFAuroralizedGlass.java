package twilightforest.block;

import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.material.Material;

public class BlockTFAuroralizedGlass extends AbstractGlassBlock {

    public BlockTFAuroralizedGlass() {
        super(Properties.create(Material.ICE));
        //this.setCreativeTab(TFItems.creativeTab); TODO 1.14
    }

    //TODO: Move to client
//    @Override
//    public BlockRenderLayer getRenderLayer() {
//        return BlockRenderLayer.TRANSLUCENT;
//    }
}
