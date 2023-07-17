package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.PlantType;

public class RootStrandBlock extends TFPlantBlock {

	private static final VoxelShape ROOT_SHAPE = box(2, 0, 2, 14, 16, 14);

	public RootStrandBlock(Properties props) {
		super(props);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		return TFPlantBlock.canPlaceRootAt(reader, pos) || reader.getBlockState(pos.above()).is(this);
	}

	@Override
	public PlantType getPlantType(BlockGetter getter, BlockPos pos) {
		return PlantType.CAVE;
	}

	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return ROOT_SHAPE;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader getter, BlockPos pos, BlockState state, boolean isClient) {
		return this.isBottomOpen(getter, pos);
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return this.isBottomOpen(level, pos);
	}

	private boolean isBottomOpen(BlockGetter getter, BlockPos pos) {
		BlockPos.MutableBlockPos mutable = pos.mutable();
		do {
			mutable.move(Direction.DOWN);
		} while (getter.getBlockState(mutable).is(this));

		return getter.getBlockState(mutable).isAir() || getter.getBlockState(mutable).getMaterial().isReplaceable();
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		BlockPos.MutableBlockPos mutable = pos.mutable();

		do {
			mutable.move(Direction.DOWN);
		} while (level.getBlockState(mutable).is(this));

		if (level.getBlockState(mutable).isAir() || level.getBlockState(mutable).getMaterial().isReplaceable()) {
			level.setBlockAndUpdate(mutable, this.defaultBlockState());
		}
	}
}
