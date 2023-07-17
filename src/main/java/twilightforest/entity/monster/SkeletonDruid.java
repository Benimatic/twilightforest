package twilightforest.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import twilightforest.entity.projectile.NatureBolt;
import twilightforest.init.TFSounds;

import java.util.UUID;

public class SkeletonDruid extends AbstractSkeleton {
	private RangedAttackGoal rangedAttackGoal;

	public SkeletonDruid(EntityType<? extends SkeletonDruid> type, Level world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

		this.rangedAttackGoal = new RangedAttackGoal(this, 1.25D, 60, 5.0F);

		this.goalSelector.addGoal(4, this.rangedAttackGoal);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(DATA_BABY_ID, false);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TFSounds.SKELETON_DRUID_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return TFSounds.SKELETON_DRUID_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TFSounds.SKELETON_DRUID_DEATH.get();
	}

	@Override
	protected SoundEvent getStepSound() {
		return TFSounds.SKELETON_DRUID_STEP.get();
	}


	@Override
	public void reassessWeaponGoal() {
		if (!this.getLevel().isClientSide()) {
			this.goalSelector.removeGoal(this.rangedAttackGoal);

			if (this.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof HoeItem) {
				this.goalSelector.addGoal(4, this.rangedAttackGoal);
			} else {
				super.reassessWeaponGoal();
			}
		} else {
			super.reassessWeaponGoal();
		}
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
		if (this.isBaby()) {
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STICK));
		} else {
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_HOE));
		}
	}

	@Override
	public void performRangedAttack(LivingEntity attackTarget, float extraDamage) {
		if (this.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof HoeItem) {
			NatureBolt natureBolt = new NatureBolt(this.getLevel(), this);
			playSound(TFSounds.SKELETON_DRUID_SHOOT.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));

			double tx = attackTarget.getX() - this.getX();
			double ty = attackTarget.getY() + attackTarget.getEyeHeight() - 2.7D - this.getY();
			double tz = attackTarget.getZ() - this.getZ();
			float heightOffset = Mth.sqrt((float) (tx * tx + tz * tz)) * 0.2F;
			natureBolt.shoot(tx, ty + heightOffset, tz, 0.6F, 6.0F);
			this.getLevel().addFreshEntity(natureBolt);
		} else if (!this.getItemInHand(InteractionHand.MAIN_HAND).is(Items.STICK)) {
			super.performRangedAttack(attackTarget, extraDamage);
		}
	}

	public static boolean checkDruidSpawnRules(EntityType<? extends SkeletonDruid> entity, LevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
		return world.getDifficulty() != Difficulty.PEACEFUL && isValidLightLevel(world, pos, random) && checkMobSpawnRules(entity, world, reason, pos, random);
	}

	// [VanillaCopy] of super. Edits noted.
	public static boolean isValidLightLevel(LevelAccessor accessor, BlockPos pos, RandomSource random) {
		if (accessor.getBrightness(LightLayer.SKY, pos) > random.nextInt(32)) {
			return false;
		} else {
			return accessor.getMaxLocalRawBrightness(pos) <= random.nextInt(12); // TF - rand(8) -> rand(12)
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("IsBaby", this.isBaby());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setBaby(compound.getBoolean("IsBaby"));
	}

	// Below: VANILLACOPY Zombie Baby Code

	private static final UUID SPEED_MODIFIER_BABY_UUID = UUID.fromString("3F508BEA-92F5-47B3-BCA2-B0FA84860574");
	private static final AttributeModifier SPEED_MODIFIER_BABY = new AttributeModifier(SPEED_MODIFIER_BABY_UUID, "Baby speed boost", 0.5D, AttributeModifier.Operation.MULTIPLY_BASE);
	private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(SkeletonDruid.class, EntityDataSerializers.BOOLEAN);

	@Override
	public boolean isBaby() {
		return this.getEntityData().get(DATA_BABY_ID);
	}

	@Override
	public int getExperienceReward() {
		if (this.isBaby()) {
			this.xpReward = (int) (this.xpReward * 2.5F);
		}

		return super.getExperienceReward();
	}

	@Override
	public void setBaby(boolean shouldBaby) {
		this.getEntityData().set(DATA_BABY_ID, shouldBaby);
		if (!this.getLevel().isClientSide()) {
			AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
			attributeinstance.removeModifier(SPEED_MODIFIER_BABY);
			if (shouldBaby) {
				attributeinstance.addTransientModifier(SPEED_MODIFIER_BABY);
			}
		}
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> dataAccessor) {
		if (DATA_BABY_ID.equals(dataAccessor)) {
			this.refreshDimensions();
		}

		super.onSyncedDataUpdated(dataAccessor);
	}

	@Override
	public double getMyRidingOffset() {
		return this.isBaby() ? -0.35D : -0.6D;
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
		return this.isBaby() ? 0.93F : super.getStandingEyeHeight(pose, size);
	}
}
