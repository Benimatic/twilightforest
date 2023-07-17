package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.enums.HollowLogVariants;

public class HollowLogClimbable extends HorizontalDirectionalBlock implements WaterloggedBlock {
	public static final EnumProperty<HollowLogVariants.Climbable> VARIANT = EnumProperty.create("variant", HollowLogVariants.Climbable.class);

	private static final VoxelShape LADDER_EAST = Block.box(0, 0, 0, 3, 16, 16);
	private static final VoxelShape LADDER_WEST = Block.box(13, 0, 0, 16, 16, 16);
	private static final VoxelShape LADDER_SOUTH = Block.box(0, 0, 0, 16, 16, 3);
	private static final VoxelShape LADDER_NORTH = Block.box(0, 0, 13, 16, 16, 16);

	private static final VoxelShape HOLLOW_SHAPE = Shapes.join(Shapes.block(), Block.box(2, 0, 2, 14, 16, 14), BooleanOp.ONLY_FIRST);
	private static final VoxelShape HOLLOW_SHAPE_SOUTH = Shapes.or(HOLLOW_SHAPE, LADDER_SOUTH);
	private static final VoxelShape HOLLOW_SHAPE_NORTH = Shapes.or(HOLLOW_SHAPE, LADDER_NORTH);
	private static final VoxelShape HOLLOW_SHAPE_EAST = Shapes.or(HOLLOW_SHAPE, LADDER_EAST);
	private static final VoxelShape HOLLOW_SHAPE_WEST = Shapes.or(HOLLOW_SHAPE, LADDER_WEST);

	private static final VoxelShape COLLISION_SHAPE = Shapes.join(Shapes.block(), Block.box(1, 0, 1, 15, 16, 15), BooleanOp.ONLY_FIRST);
	private static final VoxelShape COLLISION_SHAPE_SOUTH = Shapes.or(COLLISION_SHAPE, LADDER_SOUTH);
	private static final VoxelShape COLLISION_SHAPE_NORTH = Shapes.or(COLLISION_SHAPE, LADDER_NORTH);
	private static final VoxelShape COLLISION_SHAPE_EAST = Shapes.or(COLLISION_SHAPE, LADDER_EAST);
	private static final VoxelShape COLLISION_SHAPE_WEST = Shapes.or(COLLISION_SHAPE, LADDER_WEST);

	private final RegistryObject<HollowLogVertical> vertical;

	public HollowLogClimbable(Properties properties, RegistryObject<HollowLogVertical> vertical) {
		super(properties);
		this.vertical = vertical;

		this.registerDefaultState(this.getStateDefinition().any().setValue(VARIANT, HollowLogVariants.Climbable.VINE).setValue(FACING, Direction.NORTH));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return state.getValue(VARIANT) == HollowLogVariants.Climbable.VINE ? HOLLOW_SHAPE : switch (state.getValue(FACING)) {
			case SOUTH -> HOLLOW_SHAPE_SOUTH;
			case WEST -> HOLLOW_SHAPE_WEST;
			case EAST -> HOLLOW_SHAPE_EAST;
			default -> HOLLOW_SHAPE_NORTH;
		};
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return state.getValue(VARIANT) == HollowLogVariants.Climbable.VINE ? COLLISION_SHAPE : switch (state.getValue(FACING)) {
			case SOUTH -> COLLISION_SHAPE_SOUTH;
			case WEST -> COLLISION_SHAPE_WEST;
			case EAST -> COLLISION_SHAPE_EAST;
			default -> COLLISION_SHAPE_NORTH;
		};
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(VARIANT, FACING));
	}

	@Override
	public boolean isStateWaterlogged(BlockState state) {
		return state.getValue(VARIANT) == HollowLogVariants.Climbable.LADDER_WATERLOGGED;
	}

	@Override
	public BlockState setWaterlog(BlockState prior, boolean doWater) {
		return switch (prior.getValue(VARIANT)) {
			case VINE -> doWater ? this.vertical.get().defaultBlockState().setValue(HollowLogVertical.WATERLOGGED, true) : prior;
			case LADDER -> prior.setValue(VARIANT, HollowLogVariants.Climbable.LADDER_WATERLOGGED);
			case LADDER_WATERLOGGED -> prior.setValue(VARIANT, HollowLogVariants.Climbable.LADDER);
		};
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(VARIANT) == HollowLogVariants.Climbable.LADDER_WATERLOGGED ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor accessor, BlockPos pos, BlockPos neighborPos) {
		if (this.isStateWaterlogged(state)) {
			accessor.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
		}

		return super.updateShape(state, facing, neighborState, accessor, pos, neighborPos);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (!isInside(hit, pos)) return super.use(state, level, pos, player, hand, hit);

		ItemStack stack = player.getItemInHand(hand);

		if (stack.canPerformAction(ToolActions.SHEARS_HARVEST)) {
			HollowLogVariants.Climbable variant = state.getValue(VARIANT);
			level.setBlock(pos, this.vertical.get().defaultBlockState().setValue(HollowLogVertical.WATERLOGGED, variant == HollowLogVariants.Climbable.LADDER_WATERLOGGED), 3);
			level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
			if (!player.isCreative()) {
				stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
				level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(variant == HollowLogVariants.Climbable.VINE ? Blocks.VINE : Blocks.LADDER)));
			}

			return InteractionResult.sidedSuccess(level.isClientSide());
		}

		return super.use(state, level, pos, player, hand, hit);
	}

	private static boolean isInside(HitResult result, BlockPos pos) {
		Vec3 vec = result.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());

		return (0.124 <= vec.x() && vec.x() <= 0.876) && (0.124 <= vec.z() && vec.z() <= 0.876);
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter getter, BlockPos pos, Direction face) {
		return 5;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter getter, BlockPos pos, Direction face) {
		return 5;
	}
}
