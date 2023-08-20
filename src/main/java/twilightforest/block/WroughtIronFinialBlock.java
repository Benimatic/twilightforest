package twilightforest.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class WroughtIronFinialBlock extends Block implements SimpleWaterloggedBlock {

    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(
            Direction.UP, Block.box(6.5D, 0.0D, 6.5D, 9.5D, 4.0D, 9.5D),
            Direction.DOWN, Block.box(6.5D, 12.0D, 6.5D, 9.5D, 16.0D, 9.5D),
            Direction.NORTH, Block.box(6.5D, 6.5D, 12.0D, 9.5D, 9.5D, 16.0D),
            Direction.SOUTH, Block.box(6.5D, 6.5D, 0.0D, 9.5D, 9.5D, 4.0D),
            Direction.WEST, Block.box(12.0D, 6.5D, 6.5D, 16.0D, 9.5D, 9.5D),
            Direction.EAST, Block.box(0.0D, 6.5D, 6.5D, 4.0D, 9.5D, 9.5D)));

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty ROTATED = BooleanProperty.create("rotated");

    public WroughtIronFinialBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.UP).setValue(ROTATED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(DirectionalBlock.FACING, context.getClickedFace()).setValue(ROTATED, context.isSecondaryUseActive());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ROTATED);
    }
}
