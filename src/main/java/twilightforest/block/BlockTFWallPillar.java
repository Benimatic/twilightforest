package twilightforest.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import twilightforest.TwilightForestMod;
import twilightforest.client.ModelRegisterCallbackCTM;
import twilightforest.client.ModelUtils;

public class BlockTFWallPillar extends BlockTFConnectableRotatedPillar implements ModelRegisterCallbackCTM {

    protected static final PropertyBool UP = PropertyBool.create("up");
    protected static final PropertyBool DOWN = PropertyBool.create("down");

    BlockTFWallPillar(Material material, double width, double height) {
        super(material, width, height);

        this.setDefaultState(this.getDefaultState().with(UP, false).with(DOWN, false));
    }

    @Override
    protected IProperty[] getAdditionalProperties() {
        return new IProperty[]{ UP, DOWN };
    }

    @Override
    protected boolean canConnectTo(BlockState state, BlockState otherState, IBlockAccess world, BlockPos pos, Direction connectTo) {
        return otherState.getBlock() == this && state.getValue(AXIS) == otherState.getValue(AXIS);
    }

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
        return /*(!state.getValue(UP)) || (!state.getValue(DOWN)) ? FULL_BLOCK_AABB :*/ super.getBoundingBox(state, world, pos);
    }

    @Override
    public BlockState getActualState(BlockState state, IBlockAccess world, BlockPos pos) {
        Direction.Axis axis = state.getValue(AXIS);
        return super.getActualState(state.with(UP, canConnectTo(state, world.getBlockState(pos.offset(getFacingFromPropertyWithAxis(UP, axis))), world, pos, Direction.UP)).with(DOWN, canConnectTo(state, world.getBlockState(pos.offset(getFacingFromPropertyWithAxis(DOWN, axis))), world, pos, Direction.DOWN)), world, pos);
    }

    private static Direction getFacingFromPropertyWithAxis(PropertyBool property, Direction.Axis axis) {
        switch (axis) {
            case X:
                if (property == DOWN) return Direction.WEST;
                if (property == UP  ) return Direction.EAST;
                break;
            case Y:
                if (property == DOWN) return Direction.DOWN;
                if (property == UP  ) return Direction.UP;
                break;
            case Z:
                if (property == DOWN) return Direction.SOUTH;
                if (property == UP  ) return Direction.NORTH;
                break;
        }

        TwilightForestMod.LOGGER.warn("BlockTFWallPillar helper (getFacingFromPropertyWithAxis) had a problem? (property '{}' with axis '{}')", property.getName(), axis.getName());
        return BlockTFConnectableRotatedPillar.PairHelper.getFacingFromPropertyWithAxis(property, axis);
    }

    @Override
    public void registerModel() {
        ModelUtils.registerToState(this, 0, this.getDefaultState());
    }
}
