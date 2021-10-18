package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Random;

//all the common lighting/extinguishing methods for the candelabra and skull candles are here to reduce clutter
//it may also be handy if we decide to add more candle-based blocks in the future
public abstract class AbstractLightableBlock extends BaseEntityBlock {

	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public AbstractLightableBlock(Properties properties) {
		super(properties);
		registerDefaultState(getStateDefinition().any().setValue(LIT, false));
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if (player.getAbilities().mayBuild && player.getItemInHand(hand).isEmpty() && state.getValue(LIT)) {
			extinguish(player, state, level, pos);
			return InteractionResult.sidedSuccess(level.isClientSide);

		} else if (!state.getValue(LIT)){
			if(player.getItemInHand(hand).is(Items.FLINT_AND_STEEL)) {
				setLit(level, state, pos, true);
				level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
				if(!player.getAbilities().instabuild) player.getItemInHand(hand).hurtAndBreak(1, player, (res) -> {
					res.broadcastBreakEvent(hand);
				});
				return InteractionResult.sidedSuccess(level.isClientSide);
			} else if (player.getItemInHand(hand).is(Items.FIRE_CHARGE)) {
				setLit(level, state, pos, true);
				level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
				if(!player.getAbilities().instabuild) player.getItemInHand(hand).shrink(1);
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	public void onProjectileHit(Level level, BlockState state, BlockHitResult result, Projectile projectile) {
		if (!level.isClientSide && projectile.isOnFire() && this.canBeLit(state)) {
			setLit(level, state, result.getBlockPos(), true);
		}
	}

	protected boolean canBeLit(BlockState state) {
		return !state.getValue(LIT);
	}

	// Original methods used Vec3 but here we can avoid creation of extraneous vectors
	protected static void addParticlesAndSound(Level level, BlockPos pos, double xFraction, double yFraction, double zFraction, Random rand) {
		addParticlesAndSound(level, pos.getX() + xFraction, pos.getY() + yFraction, pos.getZ() + zFraction, rand);
	}

	protected static void addParticlesAndSound(Level level, double x, double y, double z, Random rand) {
		float var3 = rand.nextFloat();
		if (var3 < 0.3F) {
			level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
			if (var3 < 0.17F) {
				level.playLocalSound(x + 0.5D, y + 0.5D, z + 0.5D, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
			}
		}

		level.addParticle(ParticleTypes.SMALL_FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
	}

	//still, we should include the vector method because im too lazy to convert :P
	protected static void addParticlesAndSound(Level level, Vec3 vec, Random rand) {
		float var3 = rand.nextFloat();
		if (var3 < 0.3F) {
			level.addParticle(ParticleTypes.SMOKE, vec.x, vec.y, vec.z, 0.0D, 0.0D, 0.0D);
			if (var3 < 0.17F) {
				level.playLocalSound(vec.x + 0.5D, vec.y + 0.5D, vec.z + 0.5D, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
			}
		}

		level.addParticle(ParticleTypes.SMALL_FLAME, vec.x, vec.y, vec.z, 0.0D, 0.0D, 0.0D);
	}

	public static void extinguish(@Nullable Player player, BlockState state, LevelAccessor accessor, BlockPos pos) {
		setLit(accessor, state, pos, false);
		//TODO add an extinguish effect for the candelabra. The system is too wack for me
		if(state.getBlock() instanceof AbstractSkullCandleBlock skull) {
			skull.getParticleOffsets(state).forEach((p_151926_) ->
					accessor.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + p_151926_.x(), (double) pos.getY() + p_151926_.y(), (double) pos.getZ() + p_151926_.z(), 0.0D, 0.025D, 0.0D));
		}

		accessor.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
		accessor.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
	}

	private static void setLit(LevelAccessor accessor, BlockState state, BlockPos pos, boolean lit) {
		accessor.setBlock(pos, state.setValue(LIT, lit), 11);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(LIT);
	}
}
