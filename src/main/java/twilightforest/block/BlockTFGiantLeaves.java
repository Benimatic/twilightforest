package twilightforest.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import twilightforest.TFConfig;

public class BlockTFGiantLeaves extends BlockTFGiantBlock {

	public BlockTFGiantLeaves() {
		super(Blocks.OAK_LEAVES.getDefaultState(), 0.2F * 64F, 0.0F);
		//this.setCreativeTab(TFItems.creativeTab); TODO 1.14
	}

	@Override
	public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return TFConfig.performance.leavesLightOpacity;
	}

//	@Override
//	@Deprecated
//	public boolean isOpaqueCube(BlockState state) {
//		return false;
//	}


	@Override
	@Deprecated
	public boolean doesSideBlockRendering(BlockState state, IEnviromentBlockReader world, BlockPos pos, Direction side) {
		switch (side) {
			case DOWN:
				return (pos.getY() & 3) == 0;
			case UP:
				return (pos.getY() & 3) == 3;
			case NORTH:
				return (pos.getZ() & 3) == 0;
			case SOUTH:
				return (pos.getZ() & 3) == 3;
			case WEST:
				return (pos.getX() & 3) == 0;
			case EAST:
				return (pos.getX() & 3) == 3;
		}

		return shouldSideBeRendered(state, world, pos, side);
	}
}
