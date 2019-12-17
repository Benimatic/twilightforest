package twilightforest.block;

import net.minecraft.block.FenceBlock;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.BooleanProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import twilightforest.TwilightForestMod;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BlockTFConnectableRotatedPillar extends RotatedPillarBlock {

    final double boundingBoxWidthLower;
    final double boundingBoxWidthUpper;

    private final double boundingBoxHeightLower;
    private final double boundingBoxHeightUpper;

    BlockTFConnectableRotatedPillar(Material material, double size) {
        this(material, material.getColor(), size, size);
    }

    BlockTFConnectableRotatedPillar(Material material, double width, double height) {
        this(material, material.getColor(), width, height);
    }

    BlockTFConnectableRotatedPillar(Material material, MaterialColor mapColor, double size) {
        this(material, mapColor, size, size);
    }

    BlockTFConnectableRotatedPillar(Material material, MaterialColor mapColor, double width, double height) {
        super(Properties.create(material, mapColor));

        if (width >= 16d) {
            this.boundingBoxWidthLower = 0d;
            this.boundingBoxWidthUpper = 16d;
        } else {
            this.boundingBoxWidthLower = 8d-(width/2d);
            this.boundingBoxWidthUpper = 16d-this.boundingBoxWidthLower;
        }

        if (height >= 16d) {
            this.boundingBoxHeightLower = 0d;
            this.boundingBoxHeightUpper = 16d;
        } else {
            this.boundingBoxHeightLower = 8d-(height/2d);
            this.boundingBoxHeightUpper = 16d-this.boundingBoxHeightLower;
        }

        this.setDefaultState(stateContainer.getBaseState().with(AXIS, Direction.Axis.Y)
                .with(FenceBlock.NORTH, false).with(FenceBlock.WEST, false)
                .with(FenceBlock.SOUTH, false).with(FenceBlock.EAST, false));
    }

    protected abstract IProperty[] getAdditionalProperties();

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer.Builder(this)
                .add(AXIS, FenceBlock.NORTH, FenceBlock.EAST, FenceBlock.SOUTH, FenceBlock.WEST)
                .add(getAdditionalProperties())
                .build();
    }

    @Override
    public BlockState getActualState(BlockState state, IBlockAccess world, BlockPos pos) {
        // If our axis is rotated (i.e. not upright), then adjust the actual sides tested
        // This allows the entire model to be rotated in the assets in a cleaner way

        Direction.Axis axis = state.getValue(AXIS);

        for (PairHelper pair : PairHelper.values()) {
            Direction connectTo = PairHelper.getFacingFromPropertyWithAxis(pair.property, axis);
            state = state.with(pair.property, canConnectTo(state, world.getBlockState(pos.offset(connectTo)), world, pos, connectTo));
        }

        return state;
    }

    protected boolean canConnectTo(BlockState state, BlockState otherState, IBlockAccess world, BlockPos pos, Direction connectTo) {
        return state.getBlock() == otherState.getBlock() && state.getValue(AXIS) != connectTo.getAxis();
    }

    @Override
    public void addCollisionBoxToList(BlockState state, World world, BlockPos pos, AxisAlignedBB aabb, List<AxisAlignedBB> list, @Nullable Entity entity, boolean useActualState) {
        if (!useActualState) state = this.getActualState(state, world, pos);

        Direction.Axis axis = state.getValue(AXIS);

        // Add main pillar
        addCollisionBoxToList(pos, aabb, list, makeQuickAABB(
                axis == Direction.Axis.X ? 16d : this.boundingBoxWidthLower,
                axis == Direction.Axis.Y ? 16d : this.boundingBoxWidthLower,
                axis == Direction.Axis.Z ? 16d : this.boundingBoxWidthLower,
                axis == Direction.Axis.X ?  0d : this.boundingBoxWidthUpper,
                axis == Direction.Axis.Y ?  0d : this.boundingBoxWidthUpper,
                axis == Direction.Axis.Z ?  0d : this.boundingBoxWidthUpper));

        // Add axillary pillar connections
        // Cycle through all possible faces
        for (Direction facing : Direction.values()) {
            // If the facing in loop doesn't cover the axis of the block and if that side's state is true, then add to list
            if (facing.getAxis() != axis && state.getValue(PairHelper.getPropertyFromFacingWithAxis(facing, axis))) {
                // Create AABB piece and add to list
                addCollisionBoxToList(pos, aabb, list, getSidedAABBStraight(facing, axis));
            }
        }
    }

    // Utility to make a bounding-box piece
    protected AxisAlignedBB getSidedAABBStraight(Direction facing, Direction.Axis axis) {
        return makeQuickAABB(
                facing == Direction.EAST  ? 16d : axis == Direction.Axis.X ? this.boundingBoxHeightLower : this.boundingBoxWidthLower,
                facing == Direction.UP    ? 16d : axis == Direction.Axis.Y ? this.boundingBoxHeightLower : this.boundingBoxWidthLower,
                facing == Direction.SOUTH ? 16d : axis == Direction.Axis.Z ? this.boundingBoxHeightLower : this.boundingBoxWidthLower,
                facing == Direction.WEST  ?  0d : axis == Direction.Axis.X ? this.boundingBoxHeightUpper : this.boundingBoxWidthUpper,
                facing == Direction.DOWN  ?  0d : axis == Direction.Axis.Y ? this.boundingBoxHeightUpper : this.boundingBoxWidthUpper,
                facing == Direction.NORTH ?  0d : axis == Direction.Axis.Z ? this.boundingBoxHeightUpper : this.boundingBoxWidthUpper);
    }

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
        state = this.getActualState(state, world, pos);

        switch (state.getValue(AXIS)) {
            case X:
                return makeQuickAABB(
                        0d,
                        state.getValue(FenceBlock.NORTH) ?  0d : this.boundingBoxWidthLower,
                        state.getValue(FenceBlock.WEST ) ?  0d : this.boundingBoxWidthLower,
                        16d,
                        state.getValue(FenceBlock.SOUTH) ? 16d : this.boundingBoxWidthUpper,
                        state.getValue(FenceBlock.EAST ) ? 16d : this.boundingBoxWidthUpper
                );
            case Z:
                return makeQuickAABB(
                        state.getValue(FenceBlock.EAST ) ?  0d : this.boundingBoxWidthLower,
                        state.getValue(FenceBlock.SOUTH) ?  0d : this.boundingBoxWidthLower,
                        0d,
                        state.getValue(FenceBlock.WEST ) ? 16d : this.boundingBoxWidthUpper,
                        state.getValue(FenceBlock.NORTH) ? 16d : this.boundingBoxWidthUpper,
                        16d
                );
            default:
                return makeQuickAABB(
                        state.getValue(FenceBlock.WEST)  ?  0d : this.boundingBoxWidthLower,
                        0d,
                        state.getValue(FenceBlock.NORTH) ?  0d : this.boundingBoxWidthLower,
                        state.getValue(FenceBlock.EAST)  ? 16d : this.boundingBoxWidthUpper,
                        16d,
                        state.getValue(FenceBlock.SOUTH) ? 16d : this.boundingBoxWidthUpper
                );
        }
    }

    protected AxisAlignedBB makeQuickAABB(double x1, double y1, double z1, double x2, double y2, double z2) {
        return new AxisAlignedBB(
                x1/16.0d, y1/16.0d,
                z1/16.0d, x2/16.0d,
                y2/16.0d, z2/16.0d);
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullCube(BlockState state) {
        return false;
    }

    enum PairHelper {
        NORTH(Direction.NORTH, FenceBlock.NORTH),
        EAST (Direction.EAST , FenceBlock.EAST ),
        SOUTH(Direction.SOUTH, FenceBlock.SOUTH),
        WEST (Direction.WEST , FenceBlock.WEST );

        final Direction facing;
        final BooleanProperty property;

        PairHelper(Direction facing, BooleanProperty property) {
            this.facing = facing;
            this.property = property;
        }

        static Direction getFacingFromPropertyWithAxis(BooleanProperty property, Direction.Axis axis) {
            switch (axis) {
                case X:
                    if (property == FenceBlock.NORTH) return Direction.DOWN;
                    if (property == FenceBlock.SOUTH) return Direction.UP;
                    if (property == FenceBlock.WEST ) return Direction.NORTH;
                    if (property == FenceBlock.EAST ) return Direction.SOUTH;
                    break;
                case Y:
                    if (property == FenceBlock.NORTH) return Direction.NORTH;
                    if (property == FenceBlock.SOUTH) return Direction.SOUTH;
                    if (property == FenceBlock.WEST ) return Direction.WEST;
                    if (property == FenceBlock.EAST ) return Direction.EAST;
                    break;
                case Z:
                    if (property == FenceBlock.NORTH) return Direction.UP;
                    if (property == FenceBlock.SOUTH) return Direction.DOWN;
                    if (property == FenceBlock.WEST ) return Direction.EAST;
                    if (property == FenceBlock.EAST ) return Direction.WEST;
                    break;
            }

            TwilightForestMod.LOGGER.warn("ConnectableRotatedPillar helper (getFacingFromPropertyWithAxis) had a problem? (property '{}' with axis '{}')", property.getName(), axis.getName());
            return Direction.UP;
        }

        static BooleanProperty getPropertyFromFacingWithAxis(Direction facing, Direction.Axis axis) {
            switch (axis) {
                case X:
                    switch (facing) {
                        case DOWN:
                            return FenceBlock.NORTH;
                        case UP:
                            return FenceBlock.SOUTH;
                        case NORTH:
                            return FenceBlock.WEST;
                        case SOUTH:
                            return FenceBlock.EAST;
                    }
                    break;
                case Y:
                    switch (facing) {
                        case NORTH:
                            return FenceBlock.NORTH;
                        case SOUTH:
                            return FenceBlock.SOUTH;
                        case WEST:
                            return FenceBlock.WEST;
                        case EAST:
                            return FenceBlock.EAST;
                    }
                    break;
                case Z:
                    switch (facing) {
                        case DOWN:
                            return FenceBlock.SOUTH;
                        case UP:
                            return FenceBlock.NORTH;
                        case WEST:
                            return FenceBlock.EAST;
                        case EAST:
                            return FenceBlock.WEST;
                    }
                    break;
            }

            TwilightForestMod.LOGGER.warn("ConnectableRotatedPillar helper (getPropertyFromFacingWithAxis) had a problem? (facing '{}' with axis '{}')", facing.getName(), axis.getName());
            return FenceBlock.NORTH;
        }
    }
}
