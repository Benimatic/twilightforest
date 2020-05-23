package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import static net.minecraft.util.Direction.*;

public class BlockTFLadderBars extends LadderBlock {
    public static final BooleanProperty LEFT = BooleanProperty.create("left");
	public static final BooleanProperty RIGHT = BooleanProperty.create("right");

    BlockTFLadderBars() {
    	super(Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(5.0F, 10.0F).sound(SoundType.METAL));
        this.setDefaultState(this.getDefaultState().with(LEFT, false).with(RIGHT, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(LEFT, RIGHT);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		Direction facing = state.get(LadderBlock.FACING);

		BlockState leftState  = worldIn.getBlockState(currentPos.offset(rotateCW (facing)));
		BlockState rightState = worldIn.getBlockState(currentPos.offset(rotateCCW(facing)));

		return super.updatePostPlacement(state, direction, facingState, worldIn, currentPos,facingPos)
				.with(LEFT , leftState .getBlock() instanceof BlockTFLadderBars && leftState .get(LadderBlock.FACING) == facing)
				.with(RIGHT, rightState.getBlock() instanceof BlockTFLadderBars && rightState.get(LadderBlock.FACING) == facing);
    }

    private static Direction rotateCW(Direction facing) {
        switch (facing) {
            case NORTH:
                return WEST;
            case WEST:
                return SOUTH;
            case SOUTH:
                return EAST;
            case EAST:
                return NORTH;
            default:
                return facing;
        }
    }

    private static Direction rotateCCW(Direction facing) {
        switch (facing) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            default:
                return facing;
        }
    }
}
