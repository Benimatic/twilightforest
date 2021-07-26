package twilightforest.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TrollRootBlock extends Block {

	protected static final VoxelShape AABB = Shapes.create(new AABB(0.1, 0.0, 0.1, 0.9, 1.0, 0.9));

	protected TrollRootBlock(Properties props) {
		super(props);
	}

	public static boolean canPlaceRootBelow(LevelReader world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		return state.is(BlockTags.BASE_STONE_OVERWORLD) || block == TFBlocks.trollvidr.get() || block == TFBlocks.trollber.get() || block == TFBlocks.unripe_trollber.get();
	}

	@Override
	@Deprecated
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return canPlaceRootBelow(world, pos.above());
	}

	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return AABB;
	}

	@Override
	@Deprecated
	public BlockState updateShape(BlockState state, Direction dirToNeighbor, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (dirToNeighbor == Direction.UP) {
			return canSurvive(state, world, pos) ? state : Blocks.AIR.defaultBlockState();
		}
		return state;
	}
}
