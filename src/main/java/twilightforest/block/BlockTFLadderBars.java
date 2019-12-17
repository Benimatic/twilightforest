package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;

import static net.minecraft.util.Direction.*;

public class BlockTFLadderBars extends LadderBlock {
    public static final BooleanProperty LEFT = BooleanProperty.create("left");
	public static final BooleanProperty RIGHT = BooleanProperty.create("right");

    BlockTFLadderBars() {
        this.setDefaultState(this.getDefaultState().with(LEFT, false).with(RIGHT, false));
    }

    @Override
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockLadder.FACING, LEFT, RIGHT);
    }

    @Override
    public BlockState getActualState(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        Direction facing = state.getValue(BlockLadder.FACING);

        BlockState leftState  = worldIn.getBlockState(pos.offset(rotateCW (facing)));
        BlockState rightState = worldIn.getBlockState(pos.offset(rotateCCW(facing)));

        return super.getActualState(state, worldIn, pos)
                .with(LEFT , leftState .getBlock() instanceof BlockTFLadderBars && leftState .getValue(BlockLadder.FACING) == facing)
                .with(RIGHT, rightState.getBlock() instanceof BlockTFLadderBars && rightState.getValue(BlockLadder.FACING) == facing);
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
