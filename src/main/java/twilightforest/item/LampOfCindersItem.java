package twilightforest.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFSounds;

import javax.annotation.Nonnull;

public class LampOfCindersItem extends Item {

	private static final int FIRING_TIME = 12;

	public LampOfCindersItem(Properties props) {
		super(props);
	}

	@Override
	public boolean isEnchantable(ItemStack pStack) {
		return false;
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return false;
	}

	@Nonnull
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
		player.startUsingItem(hand);
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(hand));
	}

	@Nonnull
	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Player player = context.getPlayer();

		if (this.burnBlock(world, pos)) {
			if (player instanceof ServerPlayer)
				CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, pos, player.getItemInHand(context.getHand()));

			if (player != null) player.playSound(TFSounds.LAMP_BURN.get(), 0.5F, 1.5F);

			// spawn flame particles
			for (int i = 0; i < 10; i++) {
				float dx = pos.getX() + 0.5F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.75F;
				float dy = pos.getY() + 0.5F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.75F;
				float dz = pos.getZ() + 0.5F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.75F;
				world.addParticle(ParticleTypes.SMOKE, dx, dy, dz, 0.0D, 0.0D, 0.0D);
				world.addParticle(ParticleTypes.FLAME, dx, dy, dz, 0.0D, 0.0D, 0.0D);
			}

			return InteractionResult.SUCCESS;
		} else return InteractionResult.PASS;
	}

	private boolean burnBlock(Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (state.is(TFBlocks.BROWN_THORNS.get()) || state.is(TFBlocks.GREEN_THORNS.get())) {
			level.setBlockAndUpdate(pos, TFBlocks.BURNT_THORNS.get().withPropertiesOf(state));
			return true;
		} else return false;
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity living, int useRemaining) {
		int useTime = this.getUseDuration(stack) - useRemaining;

		if (useTime > FIRING_TIME && (stack.getDamageValue() + 1) < this.getMaxDamage(stack)) {
			this.doBurnEffect(level, living);
		}
	}

	private void doBurnEffect(Level level, LivingEntity living) {
		BlockPos pos = new BlockPos(
				Mth.floor(living.xOld),
				Mth.floor(living.yOld + living.getEyeHeight()),
				Mth.floor(living.zOld)
		);

		int range = 4;

		if (!level.isClientSide()) {
			level.playSound(null, living.getX(), living.getY(), living.getZ(), TFSounds.LAMP_BURN.get(), living.getSoundSource(), 1.5F, 0.8F);

			// set nearby thorns to burnt
			for (int dx = -range; dx <= range; dx++) {
				for (int dy = -range; dy <= range; dy++) {
					for (int dz = -range; dz <= range; dz++) {
						this.burnBlock(level, pos.offset(dx, dy, dz));
					}
				}
			}
		}

		if (living instanceof Player) {
			for (int i = 0; i < 6; i++) {
				BlockPos rPos = pos.offset(
						level.getRandom().nextInt(range) - level.getRandom().nextInt(range),
						level.getRandom().nextInt(2),
						level.getRandom().nextInt(range) - level.getRandom().nextInt(range)
				);

				level.levelEvent((Player) living, 2004, rPos, 0);
			}

			//burn mobs!
			for (LivingEntity targets : level.getEntitiesOfClass(LivingEntity.class, new AABB(living.blockPosition()).inflate(4.0D))) {
				if (!(targets instanceof Player)) {
					targets.setSecondsOnFire(5);
				}
			}
		}
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}
}