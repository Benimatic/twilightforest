package twilightforest.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.UseAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class ItemTFScepterLifeDrain extends Item {

	protected ItemTFScepterLifeDrain(Properties props) {
		super(props);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		player.setActiveHand(hand);
		return ActionResult.success(player.getHeldItem(hand));
	}

	@Override
	public float getXpRepairRatio(ItemStack stack) {
		return 1f;
	}

	/**
	 * Animates the target falling apart into a rain of shatter particles
	 */
	private static void animateTargetShatter(World world, LivingEntity target) {
		ItemStack itemId = new ItemStack(getTargetDropItem(target));
		for (int i = 0; i < 50; ++i) {
			double gaussX = random.nextGaussian() * 0.02D;
			double gaussY = random.nextGaussian() * 0.02D;
			double gaussZ = random.nextGaussian() * 0.02D;
			double gaussFactor = 10.0D;
			world.addParticle(new ItemParticleData(ParticleTypes.ITEM, itemId), target.getX() + random.nextFloat() * target.getWidth() * 2.0F - target.getWidth() - gaussX * gaussFactor, target.getY() + random.nextFloat() * target.getHeight() - gaussY * gaussFactor, target.getZ() + random.nextFloat() * target.getWidth() * 2.0F - target.getWidth() - gaussZ * gaussFactor, gaussX, gaussY, gaussZ);
		}
	}

	private static Item getTargetDropItem(LivingEntity target) {
		// TODO: make this actually work
		return Items.ROTTEN_FLESH;
	}

	/**
	 * What, if anything, is the player currently looking at?
	 */
	@Nullable
	private Entity getPlayerLookTarget(World world, LivingEntity living) {
		Entity pointedEntity = null;
		double range = 20.0D;
		Vec3d srcVec = new Vec3d(living.getX(), living.getY() + living.getEyeHeight(), living.getZ());
		Vec3d lookVec = living.getLook(1.0F);
		Vec3d destVec = srcVec.add(lookVec.x * range, lookVec.y * range, lookVec.z * range);
		float var9 = 1.0F;
		List<Entity> possibleList = world.getEntitiesWithinAABBExcludingEntity(living, living.getBoundingBox().expand(lookVec.x * range, lookVec.y * range, lookVec.z * range).grow(var9, var9, var9));
		double hitDist = 0;

		for (Entity possibleEntity : possibleList) {

			if (possibleEntity.canBeCollidedWith()) {
				float borderSize = possibleEntity.getCollisionBorderSize();
				AxisAlignedBB collisionBB = possibleEntity.getBoundingBox().grow((double) borderSize, (double) borderSize, (double) borderSize);
				Optional<Vec3d> interceptPos = collisionBB.rayTrace(srcVec, destVec);

				if (collisionBB.contains(srcVec)) {
					if (0.0D < hitDist || hitDist == 0.0D) {
						pointedEntity = possibleEntity;
						hitDist = 0.0D;
					}
				} else if (interceptPos.isPresent()) {
					double possibleDist = srcVec.distanceTo(interceptPos.get());

					if (possibleDist < hitDist || hitDist == 0.0D) {
						pointedEntity = possibleEntity;
						hitDist = possibleDist;
					}
				}
			}
		}
		return pointedEntity;
	}

	@Override
	public void onUsingTick(ItemStack stack, LivingEntity living, int count) {
		World world = living.world;

		if (stack.getDamage() >= this.getMaxDamage(stack)) {
			// do not use
			living.resetActiveHand();
			return;
		}

		if (count % 5 == 0) {

			// is the player looking at an entity
			Entity pointedEntity = getPlayerLookTarget(world, living);

			if (pointedEntity != null && pointedEntity instanceof LivingEntity) {
				LivingEntity target = (LivingEntity) pointedEntity;

				if (target.getActivePotionEffect(Effects.SLOWNESS) != null || target.getHealth() < 1) {

					if (target.getHealth() <= 3) {
						// make it explode

						makeRedMagicTrail(world, living.getX(), living.getY() + living.getEyeHeight(), living.getZ(), target.getX(), target.getY() + target.getEyeHeight(), target.getZ());
						if (target instanceof MobEntity) {
							((MobEntity) target).spawnExplosionParticle();
						}
						target.playSound(SoundEvents.ENTITY_GENERIC_BIG_FALL, 1.0F, ((random.nextFloat() - random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
						animateTargetShatter(world, (LivingEntity) target);
						if (!world.isRemote) {
							target.remove();
							target.onDeath(DamageSource.causeIndirectMagicDamage(living, living));
						}
						living.resetActiveHand();
					} else {
						// we have hit this creature recently
						if (!world.isRemote) {
							target.attackEntityFrom(DamageSource.causeIndirectMagicDamage(living, living), 3);

							// only do lifting effect on creatures weaker than the player
							if (getMaxHealth(target) <= getMaxHealth(living)) {
								target.setMotion(0, 0.2, 0);
							}

							target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20, 2));

							if (count % 10 == 0) {
								// heal the player
								living.heal(1);
								// and give foods
								if (living instanceof PlayerEntity)
									((PlayerEntity) living).getFoodStats().addStats(1, 0.1F);
							}
						}
					}
				} else {
					// this is a new creature to start draining
					makeRedMagicTrail(world, living.getX(), living.getY() + living.getEyeHeight(), living.getZ(), target.getX(), target.getY() + target.getEyeHeight(), target.getZ());

					living.playSound(SoundEvents.ITEM_FLINTANDSTEEL_USE, 1.0F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);

					if (!world.isRemote) {
						target.attackEntityFrom(DamageSource.causeIndirectMagicDamage(living, living), 1);

						// only do lifting effect on creatures weaker than the player
						if (getMaxHealth(target) <= getMaxHealth(living)) {
							target.setMotion(0, 0.2, 0);
						}

						target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20, 2));
					}
				}

				if (!world.isRemote) {
					stack.damageItem(1, living, (user) -> user.sendBreakAnimation(living.getActiveHand()));
				}
			}
		}
	}

	private float getMaxHealth(LivingEntity target) {
		return (float) target.getAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue();
	}

	private void makeRedMagicTrail(World world, double srcX, double srcY, double srcZ, double destX, double destY, double destZ) {
		// make particle trail
		int particles = 32;
		for (int i = 0; i < particles; i++) {
			double trailFactor = i / (particles - 1.0D);
			float f = 1.0F;
			float f1 = 0.5F;
			float f2 = 0.5F;
			double tx = srcX + (destX - srcX) * trailFactor + world.rand.nextGaussian() * 0.005;
			double ty = srcY + (destY - srcY) * trailFactor + world.rand.nextGaussian() * 0.005;
			double tz = srcZ + (destZ - srcZ) * trailFactor + world.rand.nextGaussian() * 0.005;
			world.addParticle(ParticleTypes.ENTITY_EFFECT, tx, ty, tz, f, f1, f2);
		}
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

	@Override
	public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
		return oldStack.getItem() == newStack.getItem();
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged || newStack.getItem() != oldStack.getItem();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
		super.addInformation(stack, world, tooltip, flags);
		tooltip.add(new TranslationTextComponent("twilightforest.scepter_charges", stack.getMaxDamage() - stack.getDamage()));
	}
}
