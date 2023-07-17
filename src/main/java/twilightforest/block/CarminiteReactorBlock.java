package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import twilightforest.block.entity.CarminiteReactorBlockEntity;
import twilightforest.init.TFBlockEntities;

import org.jetbrains.annotations.Nullable;
import java.util.Arrays;

public class CarminiteReactorBlock extends BaseEntityBlock {

	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

	public CarminiteReactorBlock(Properties props) {
		super(props);
		this.registerDefaultState(this.getStateDefinition().any().setValue(ACTIVE, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ACTIVE);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	@Deprecated
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if (level.isClientSide()) return;

		if (!state.getValue(ACTIVE) && this.isReactorReady(level, pos)) {
			// check if we should fire up the reactor
			level.setBlockAndUpdate(pos, state.setValue(ACTIVE, true));
		}
	}

	/**
	 * Check if the reactor has all the specified things around it
	 */
	private boolean isReactorReady(Level level, BlockPos pos) {
		return Arrays.stream(Direction.values())
				.allMatch(e -> level.getBlockState(pos.relative(e)).getBlock() == Blocks.REDSTONE_BLOCK);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CarminiteReactorBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, TFBlockEntities.CARMINITE_REACTOR.get(), CarminiteReactorBlockEntity::tick);
	}
}
