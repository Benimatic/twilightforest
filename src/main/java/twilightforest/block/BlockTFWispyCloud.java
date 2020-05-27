package twilightforest.block;

import net.minecraft.block.BreakableBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockTFWispyCloud extends BreakableBlock {

	protected BlockTFWispyCloud(Properties props) {
		super(props);
	}

	//TODO: Move to client
//	@Override
//	public BlockRenderLayer getRenderLayer() {
//		return BlockRenderLayer.TRANSLUCENT;
//	}

//	@Override
//	public boolean canSilkHarvest(World world, BlockPos pos, BlockState state, PlayerEntity player) {
//		return true;
//	}
//
//	@Override
//	public int quantityDropped(Random random) {
//		return 0;
//	}
}
