package twilightforest.block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.advancements.TFAdvancements;
import twilightforest.data.tags.EntityTagGenerator;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStats;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class CritterBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
	public static final DirectionProperty FACING = DirectionalBlock.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private final VoxelShape DOWN_BB = Shapes.create(new AABB(0.2F, 0.85F, 0.2F, 0.8F, 1.0F, 0.8F));
	private final VoxelShape UP_BB = Shapes.create(new AABB(0.2F, 0.0F, 0.2F, 0.8F, 0.15F, 0.8F));
	private final VoxelShape NORTH_BB = Shapes.create(new AABB(0.2F, 0.2F, 0.85F, 0.8F, 0.8F, 1.0F));
	private final VoxelShape SOUTH_BB = Shapes.create(new AABB(0.2F, 0.2F, 0.0F, 0.8F, 0.8F, 0.15F));
	private final VoxelShape WEST_BB = Shapes.create(new AABB(0.85F, 0.2F, 0.2F, 1.0F, 0.8F, 0.8F));
	private final VoxelShape EAST_BB = Shapes.create(new AABB(0.0F, 0.2F, 0.2F, 0.15F, 0.8F, 0.8F));

	protected CritterBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.UP).setValue(WATERLOGGED, Boolean.FALSE));
	}

	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(FACING)) {
			case DOWN -> DOWN_BB;
			default -> UP_BB;
			case NORTH -> NORTH_BB;
			case SOUTH -> SOUTH_BB;
			case WEST -> WEST_BB;
			case EAST -> EAST_BB;
		};
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction clicked = context.getClickedFace();
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		BlockState state = defaultBlockState().setValue(FACING, clicked).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

		if (canSurvive(state, context.getLevel(), context.getClickedPos())) {
			return state;
		}

		for (Direction dir : context.getNearestLookingDirections()) {
			state = defaultBlockState().setValue(FACING, dir.getOpposite());
			if (canSurvive(state, context.getLevel(), context.getClickedPos())) {
				return state;
			}
		}
		return null;
	}

	@Override
	@Deprecated
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor accessor, BlockPos pos, BlockPos neighborPos) {
		if (state.getValue(WATERLOGGED)) {
			accessor.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
		}
		if (state.getValue(FACING).getOpposite() == direction && !state.canSurvive(accessor, pos)) {
			return Blocks.AIR.defaultBlockState();
		} else {
			return super.updateShape(state, direction, neighborState, accessor, pos, neighborPos);
		}
	}

	@Override
	@Deprecated
	public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		Direction facing = state.getValue(DirectionalBlock.FACING);
		BlockPos restingPos = pos.relative(facing.getOpposite());
		return canSupportCenter(reader, restingPos, facing);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() == Items.GLASS_BOTTLE) {
			if (this == TFBlocks.FIREFLY.get()) {
				if (!player.isCreative()) stack.shrink(1);
				player.getInventory().add(new ItemStack(TFBlocks.FIREFLY_JAR.get()));
				level.setBlockAndUpdate(pos, state.getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState());
				return InteractionResult.sidedSuccess(level.isClientSide());
			} else if (this == TFBlocks.CICADA.get()) {
				if (!player.isCreative()) stack.shrink(1);
				player.getInventory().add(new ItemStack(TFBlocks.CICADA_JAR.get()));
				if (level.isClientSide())
					Minecraft.getInstance().getSoundManager().stop(TFSounds.CICADA.get().getLocation(), SoundSource.NEUTRAL);
				level.setBlockAndUpdate(pos, state.getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState());
				return InteractionResult.sidedSuccess(level.isClientSide());
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if ((entity instanceof Projectile && !entity.getType().is(EntityTagGenerator.DONT_KILL_BUGS)) || entity instanceof FallingBlockEntity) {
			level.setBlockAndUpdate(pos, state.getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState());
			if (level.isClientSide())
				Minecraft.getInstance().getSoundManager().stop(TFSounds.CICADA.get().getLocation(), SoundSource.NEUTRAL);

			level.playSound(null, pos, TFSounds.BUG_SQUISH.get(), SoundSource.BLOCKS, 1.0F, 1.0F);

			if (level instanceof ServerLevel serverLevel && this.getSquishLootTable() != null) {
				LootContext ctx = new LootContext.Builder(serverLevel).withParameter(LootContextParams.BLOCK_STATE, state).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos)).withParameter(LootContextParams.TOOL, ItemStack.EMPTY).create(LootContextParamSets.BLOCK);
				serverLevel.getServer().getLootTables().get(this.getSquishLootTable()).getRandomItems(ctx).forEach((stack) -> popResource(serverLevel, pos, stack));
			}

			for (int i = 0; i < 50; i++) {
				boolean wallBug = state.getValue(FACING) != Direction.UP;
				level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SLIME_BLOCK.defaultBlockState()), true,
						pos.getX() + Mth.nextFloat(level.getRandom(), 0.25F, 0.75F),
						pos.getY() + (wallBug ? 0.5F : 0.0F),
						pos.getZ() + Mth.nextFloat(level.getRandom(), 0.25F, 0.75F),
						0.0D, 0.0D, 0.0D);
			}
			if (entity instanceof Projectile projectile && projectile.getOwner() instanceof ServerPlayer player) {
				player.awardStat(TFStats.BUGS_SQUISHED.get());
				TFAdvancements.KILL_BUG.trigger(player, state);
			}
		}
	}

	public abstract @Nullable ResourceLocation getSquishLootTable(); // Oh, no!

	@Nullable
	@Override
	public abstract BlockEntity newBlockEntity(BlockPos pos, BlockState state);

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, WATERLOGGED);
	}
}
