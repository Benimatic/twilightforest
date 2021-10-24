package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fmllegacy.RegistryObject;
import twilightforest.enums.HollowLogVariants;
import twilightforest.item.HollowLogItem;
import twilightforest.util.DirectionUtil;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class HollowLogVertical extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape HOLLOW_SHAPE = Shapes.or(
            Block.box(0, 0, 0, 2, 16, 16),
            Block.box(14, 0, 0, 16, 16, 16),
            Block.box(2, 0, 0, 14, 16, 2),
            Block.box(2, 0, 14, 14, 16, 16)
    );

    private final RegistryObject<HollowLogClimbable> climbable;
    private final Supplier<HollowLogItem> asItem;

    public HollowLogVertical(Properties props, RegistryObject<HollowLogClimbable> climbable, Supplier<HollowLogItem> itemSupplier) {
        super(props);
        this.climbable = climbable;

        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
        this.asItem = itemSupplier;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return HOLLOW_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(WATERLOGGED));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(Blocks.VINE.asItem())) {
            level.setBlock(pos, this.climbable.get().defaultBlockState().setValue(HollowLogClimbable.VARIANT, HollowLogVariants.Climbable.VINE).setValue(HollowLogClimbable.FACING, DirectionUtil.horizontalOrElse(hit.getDirection(), player.getDirection().getOpposite())), 3);
            if (!player.isCreative()) stack.shrink(1);

            player.swing(hand);

            return InteractionResult.CONSUME;
        } else if (stack.is(Blocks.LADDER.asItem())) {
            level.setBlock(pos, this.climbable.get().defaultBlockState().setValue(HollowLogClimbable.VARIANT, state.getValue(WATERLOGGED) ? HollowLogVariants.Climbable.LADDER_WATERLOGGED : HollowLogVariants.Climbable.LADDER).setValue(HollowLogClimbable.FACING, DirectionUtil.horizontalOrElse(hit.getDirection(), player.getDirection().getOpposite())), 3);
            if (!player.isCreative()) stack.shrink(1);

            player.swing(hand);

            return InteractionResult.CONSUME;
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(WATERLOGGED, context.getLevel().getBlockState(context.getClickedPos()).getFluidState().getType() == Fluids.WATER);
    }

    @Override
    public Item asItem() {
        return this.asItem.get();
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, facing, neighborState, level, pos, neighborPos);
    }
}
