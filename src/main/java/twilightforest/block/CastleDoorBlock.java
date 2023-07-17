package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;
import twilightforest.util.WorldUtil;
import twilightforest.world.components.chunkgenerators.ChunkGeneratorTwilight;

public class CastleDoorBlock extends Block {

	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
	public static final BooleanProperty VANISHED = BooleanProperty.create("vanish");

	private static final VoxelShape REAPPEARING_BB = Shapes.create(new AABB(0.375F, 0.375F, 0.375F, 0.625F, 0.625F, 0.625F));

	public CastleDoorBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.getStateDefinition().any().setValue(ACTIVE, false).setValue(VANISHED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ACTIVE, VANISHED);
	}

	@Override
	public boolean skipRendering(BlockState state, BlockState otherState, Direction direction) {
		return otherState.getBlock() instanceof CastleDoorBlock && otherState.getValue(VANISHED) == state.getValue(VANISHED);
	}

	@Override
	public VoxelShape getOcclusionShape(BlockState state, BlockGetter getter, BlockPos pos) {
		return state.getValue(VANISHED) || !state.getValue(ACTIVE) ? Shapes.empty() : super.getOcclusionShape(state, getter, pos);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return state.getValue(VANISHED) ? Shapes.empty() : super.getCollisionShape(state, getter, pos, context);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
		return state.getValue(VANISHED) ? REAPPEARING_BB : super.getShape(state, getter, pos, context);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		return this.onActivation(level, pos, state);
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		if (!(block instanceof CastleDoorBlock) && level.hasNeighborSignal(pos)) {
			this.onActivation(level, pos, state);
		}
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	}

	private InteractionResult onActivation(Level level, BlockPos pos, BlockState state) {

		if (state.getValue(VANISHED) || state.getValue(ACTIVE)) return InteractionResult.FAIL;

		if (isBlockLocked(level, pos)) {
			level.playSound(null, pos, TFSounds.DOOR_ACTIVATED.get(), SoundSource.BLOCKS, 1.0F, 0.3F);
			return InteractionResult.PASS;
		} else {
			changeToActiveBlock(level, pos, state);
			return InteractionResult.SUCCESS;
		}
	}

	private void changeToActiveBlock(Level level, BlockPos pos, BlockState originState) {
		if (originState.getBlock() instanceof CastleDoorBlock) {
			level.setBlockAndUpdate(pos, originState.setValue(ACTIVE, true));
		}
		level.scheduleTick(pos, originState.getBlock(), 2 + level.getRandom().nextInt(5));
	}

	private static boolean isBlockLocked(Level level, BlockPos pos) {
		// check if we are in a structure, and if that structure says that we are locked
		if (!level.isClientSide()) {
			ChunkGeneratorTwilight generator = WorldUtil.getChunkGenerator(level);
			//return generator != null && generator.isStructureLocked(pos, lockIndex);
		}
		return false;
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (state.getValue(VANISHED)) {
			if (state.getValue(ACTIVE)) {
				level.setBlockAndUpdate(pos, state.setValue(VANISHED, false).setValue(ACTIVE, false));
			} else {
				this.changeToActiveBlock(level, pos, state);
			}
			playReappearSound(level, pos);
		} else {
			if (state.getValue(ACTIVE)) {
				level.setBlockAndUpdate(pos, state.setValue(VANISHED, true).setValue(ACTIVE, false));
				level.scheduleTick(pos, this, 80);

				this.playVanishSound(level, pos);

				this.vanishParticles(level, pos);

				// activate all adjacent inactive doors
				for (Direction e : Direction.values()) {
					this.checkAndActivateCastleDoor(level, pos.relative(e));
				}
			}
		}
	}

	private void playVanishSound(Level level, BlockPos pos) {
		level.playSound(null, pos, TFSounds.DOOR_VANISH.get(), SoundSource.BLOCKS, 0.125f, level.getRandom().nextFloat() * 0.25F + 1.75F);
	}

	private void playReappearSound(Level level, BlockPos pos) {
		level.playSound(null, pos, TFSounds.DOOR_REAPPEAR.get(), SoundSource.BLOCKS, 0.125f, level.getRandom().nextFloat() * 0.25F + 1.25F);
	}

	/**
	 * If the targeted block is a vanishing block, activate it
	 */
	public void checkAndActivateCastleDoor(Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);

		if (state.getBlock() instanceof CastleDoorBlock && !state.getValue(VANISHED) && !state.getValue(ACTIVE) && !isBlockLocked(level, pos)) {
			this.changeToActiveBlock(level, pos, state);
		}
	}

	private void vanishParticles(Level level, BlockPos pos) {
		RandomSource rand = level.getRandom();
		if (level instanceof ServerLevel) {
			for (int dx = 0; dx < 4; ++dx) {
				for (int dy = 0; dy < 4; ++dy) {
					for (int dz = 0; dz < 4; ++dz) {

						double x = pos.getX() + (dx + 0.5D) / 4;
						double y = pos.getY() + (dy + 0.5D) / 4;
						double z = pos.getZ() + (dz + 0.5D) / 4;

						double speed = rand.nextGaussian() * 0.2D;

						((ServerLevel) level).sendParticles(TFParticleType.ANNIHILATE.get(), x, y, z, 1, 0, 0, 0, speed);
					}
				}
			}
		}
	}
}
