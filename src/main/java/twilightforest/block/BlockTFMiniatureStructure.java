package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockTFMiniatureStructure extends Block {

    public BlockTFMiniatureStructure() {
        super(Properties.create(Material.BARRIER).hardnessAndResistance(0.75F));
        //TODO Set the Item Group (item)
    }

    //TODO: Check this
//	@Override
//	public boolean isSolid(BlockState state) {
//		return false;
//	}

//  @Override
//  public BlockFaceShape getBlockFaceShape(IBlockAccess world, BlockState state, BlockPos pos, Direction face) {
//      return BlockFaceShape.UNDEFINED;
//  }

	//TODO: Move to client
//  @OnlyIn(Dist.CLIENT)
//  @Override
//  public BlockRenderLayer getRenderLayer() {
//      return BlockRenderLayer.CUTOUT;
//  }
}
