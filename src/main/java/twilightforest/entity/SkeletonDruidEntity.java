package twilightforest.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import twilightforest.TFSounds;
import twilightforest.entity.projectile.NatureBoltEntity;

import java.util.Random;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;

public class SkeletonDruidEntity extends Skeleton {

	public SkeletonDruidEntity(EntityType<? extends SkeletonDruidEntity> type, Level world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(4, new RangedAttackGoal(this, 1.25D, 60, 5.0F));
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
	      return TFSounds.SKELETON_DRUID_AMBIENT;
	   }

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
	      return TFSounds.SKELETON_DRUID_HURT;
	   }

	@Override
	protected SoundEvent getDeathSound() {
	      return TFSounds.SKELETON_DRUID_DEATH;
	   }

	@Override
	protected SoundEvent getStepSound() {
	      return TFSounds.SKELETON_DRUID_STEP;
	   }


	@Override
	public void reassessWeaponGoal() {
		if (!(this.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof HoeItem)) {
			super.reassessWeaponGoal();
		}
	}

	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_HOE));
	}

	@Override
	public void performRangedAttack(LivingEntity attackTarget, float extraDamage) {
		if (this.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof HoeItem) {
			NatureBoltEntity natureBolt = new NatureBoltEntity(this.level, this);
			playSound(TFSounds.SKELETON_DRUID_SHOOT, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));

			double tx = attackTarget.getX() - this.getX();
			double ty = attackTarget.getY() + attackTarget.getEyeHeight() - 2.699999988079071D - this.getY();
			double tz = attackTarget.getZ() - this.getZ();
			float heightOffset = Mth.sqrt(tx * tx + tz * tz) * 0.2F;
			natureBolt.shoot(tx, ty + heightOffset, tz, 0.6F, 6.0F);
			this.level.addFreshEntity(natureBolt);
		} else {
			super.performRangedAttack(attackTarget, extraDamage);
		}
	}

	public static boolean skeletonDruidSpawnHandler(EntityType<? extends SkeletonDruidEntity> entity, LevelAccessor world, MobSpawnType reason, BlockPos pos, Random random) {
		return world.getDifficulty() != Difficulty.PEACEFUL && isValidLightLevel(world, pos, random) && checkMobSpawnRules(entity, world, reason, pos, random);
	}

	// [VanillaCopy] of super. Edits noted.
	public static boolean isValidLightLevel(LevelAccessor world, BlockPos pos, Random random) {
		if (world.getBrightness(LightLayer.SKY, pos) > random.nextInt(32)) {
			return false;
		} else {
			int i = world.getMaxLocalRawBrightness(pos);

			// TF - no thunder check
			/*if (this.world.isThundering())
            {
                int j = this.world.getSkylightSubtracted();
                this.world.setSkylightSubtracted(10);
                i = this.world.getLightFromNeighbors(blockpos);
                this.world.setSkylightSubtracted(j);
            }*/

			return i <= random.nextInt(12); // TF - rand(8) -> rand(12)
		}
	}
}
