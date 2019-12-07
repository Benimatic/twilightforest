package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.client.ModelUtils;

import static net.minecraft.util.Direction.*;

public class BlockTFLadderBars extends BlockLadder implements ModelRegisterCallback {
    public static final PropertyBool LEFT  = PropertyBool.create("left");
    public static final PropertyBool RIGHT = PropertyBool.create("right");

    BlockTFLadderBars() {
        this.setDefaultState(this.getDefaultState().withProperty(LEFT, false).withProperty(RIGHT, false));
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
                .withProperty(LEFT , leftState .getBlock() instanceof BlockTFLadderBars && leftState .getValue(BlockLadder.FACING) == facing)
                .withProperty(RIGHT, rightState.getBlock() instanceof BlockTFLadderBars && rightState.getValue(BlockLadder.FACING) == facing);
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

    @Override
    public void registerModel() {
        ModelUtils.registerIncludingItemModels(this, "inventory", new IProperty[0]);
    }
}
