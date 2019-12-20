package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockTFMiniatureStructure extends Block {

    public BlockTFMiniatureStructure() {
        super(Properties.create(Material.BARRIER).hardnessAndResistance(0.75F));
        //TODO Set the Item Group (item)
    }

	@Override
	public boolean isSolid(BlockState state) {
		return false;
	}

//    @Override
//    public BlockFaceShape getBlockFaceShape(IBlockAccess world, BlockState state, BlockPos pos, Direction face) {
//        return BlockFaceShape.UNDEFINED;
//    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
