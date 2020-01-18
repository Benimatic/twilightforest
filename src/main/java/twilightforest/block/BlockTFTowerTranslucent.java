package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.world.IWorldReader;

public class BlockTFTowerTranslucent extends Block {

	public BlockTFTowerTranslucent(Properties props) {
		super(props.noDrops());
	}

	//TODO: Check this
//	@Override
//	@Deprecated
//	public boolean isSolid(BlockState state) {
//		return false;
//	}

	@Override
	public int tickRate(IWorldReader world) {
		return 15;
	}

//	@Override
//	public Item getItemDropped(BlockState state, Random random, int fortune) {
//		return Items.AIR;
//	}
//
//	@Override
//	public boolean canSilkHarvest(World world, BlockPos pos, BlockState state, PlayerEntity player) {
//		return false;
//	}

//	@Override
//	@Deprecated
//	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockState state, BlockPos pos, Direction face) {
//		switch (state.getValue(VARIANT)) {
//			case REAPPEARING_INACTIVE:
//			case REAPPEARING_ACTIVE:
//				return BlockFaceShape.UNDEFINED;
//			default:
//				return super.getBlockFaceShape(worldIn, state, pos, face);
//		}
//	}

	// todo 1.10 smart model for REACTOR_DEBRIS that randomly chooses sides from portal/netherrack/bedrock/obsidian

	//TODO: Move to client
//	@OnlyIn(Dist.CLIENT)
//	@Override
//	public BlockRenderLayer getRenderLayer() {
//		return BlockRenderLayer.CUTOUT;
//	}
}
