package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class RootStrandBlock extends TFPlantBlock {

	private static final VoxelShape ROOT_SHAPE = box(2, 0, 2, 14, 16, 14);

	public RootStrandBlock(Properties props) {
		super(props);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return TFPlantBlock.canPlaceRootAt(world, pos);
	}

	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, BlockGetter access, BlockPos pos, CollisionContext context) {
		return ROOT_SHAPE;
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient) {
		return isBottomOpen(level, pos);
	}

	@Override
	public boolean isBonemealSuccess(Level level, Random random, BlockPos pos, BlockState state) {
		return isBottomOpen(level, pos);
	}

	private boolean isBottomOpen(BlockGetter level, BlockPos pos) {
		BlockPos.MutableBlockPos mutable = pos.mutable();
		do {
			mutable.move(Direction.DOWN);
		} while(level.getBlockState(mutable).is(this));

		return level.getBlockState(mutable).isAir() || level.getBlockState(mutable).getMaterial().isReplaceable();
	}

	@Override
	public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state) {
		BlockPos.MutableBlockPos mutable = pos.mutable();

		do {
			mutable.move(Direction.DOWN);
		} while (level.getBlockState(mutable).is(this));

		if (level.getBlockState(mutable).isAir() || level.getBlockState(mutable).getMaterial().isReplaceable()) {
			level.setBlockAndUpdate(mutable, this.defaultBlockState());
		}
	}
}
