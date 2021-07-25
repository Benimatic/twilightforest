package twilightforest.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class IronLadderBlock extends LadderBlock {
    public static final BooleanProperty LEFT = BooleanProperty.create("left");
	public static final BooleanProperty RIGHT = BooleanProperty.create("right");

    IronLadderBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.defaultBlockState().setValue(LEFT, false).setValue(RIGHT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LEFT, RIGHT);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		Direction facing = state.getValue(LadderBlock.FACING);
		BlockState superUpdated = super.updateShape(state, direction, facingState, worldIn, currentPos, facingPos);
		if (superUpdated.getBlock() != this) {
			return superUpdated;
		}

		BlockState leftState  = worldIn.getBlockState(currentPos.relative(facing.getCounterClockWise()));
		BlockState rightState = worldIn.getBlockState(currentPos.relative(facing.getClockWise()));

		return superUpdated.setValue(LEFT, leftState.getBlock() instanceof IronLadderBlock && leftState.getValue(LadderBlock.FACING) == facing)
						.setValue(RIGHT, rightState.getBlock() instanceof IronLadderBlock && rightState.getValue(LadderBlock.FACING) == facing);
    }
}
