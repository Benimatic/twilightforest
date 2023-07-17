package twilightforest.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import twilightforest.entity.IHostileMount;
import twilightforest.entity.ai.goal.ThrowRiderGoal;
import twilightforest.init.TFBiomes;
import twilightforest.init.TFSounds;

import org.jetbrains.annotations.Nullable;
import java.util.Objects;
import java.util.Optional;

public class Yeti extends Monster implements IHostileMount {

	private static final EntityDataAccessor<Boolean> ANGER_FLAG = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.BOOLEAN);
	private static final AttributeModifier ANGRY_MODIFIER = new AttributeModifier("Angry follow range boost", 24, AttributeModifier.Operation.ADDITION);

	public Yeti(EntityType<? extends Yeti> type, Level world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new ThrowRiderGoal(this, 1.0D, false) {
			@Override
			protected void checkAndPerformAttack(LivingEntity victim, double p_190102_2_) {
				super.checkAndPerformAttack(victim, p_190102_2_);
				if (!getPassengers().isEmpty())
					playSound(TFSounds.YETI_GRAB.get(), 1F, 1.25F + getRandom().nextFloat() * 0.5F);
			}

			@Override
			public void stop() {
				if (!getPassengers().isEmpty())
					playSound(TFSounds.YETI_THROW.get(), 1F, 1.25F + getRandom().nextFloat() * 0.5F);
				super.stop();
			}
		});
		this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 20.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.38D)
				.add(Attributes.ATTACK_DAMAGE, 0.0D)
				.add(Attributes.FOLLOW_RANGE, 4.0D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ANGER_FLAG, false);
	}

	@Override
	public void aiStep() {

		super.aiStep();

		// if we no longer have a target, lets calm down
		if (this.getTarget() == null && this.isAngry()) {
			this.setAngry(false);
		}

		// look at things in our jaws
		if (!this.getPassengers().isEmpty()) {
			this.getLookControl().setLookAt(this.getPassengers().get(0), 100F, 100F);
		}
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.getEntity() != null && !source.isCreativePlayer()) {
			// become angry
			this.setAngry(true);
		}

		return super.hurt(source, amount);
	}

	public boolean isAngry() {
		return this.entityData.get(ANGER_FLAG);
	}

	public void setAngry(boolean anger) {
		this.entityData.set(ANGER_FLAG, anger);

		if (!this.getLevel().isClientSide()) {
			if (anger) {
				if (!Objects.requireNonNull(getAttribute(Attributes.FOLLOW_RANGE)).hasModifier(ANGRY_MODIFIER)) {
					Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).addTransientModifier(ANGRY_MODIFIER);
				}
			} else {
				Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).removeModifier(ANGRY_MODIFIER);
			}
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("Angry", this.isAngry());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setAngry(compound.getBoolean("Angry"));
	}

	/**
	 * Put the player out in front of where we are
	 */
	@Override
	public void positionRider(Entity passenger) {
		Vec3 riderPos = this.getRiderPosition(passenger);
		passenger.setPos(riderPos.x(), riderPos.y(), riderPos.z());
	}

	/**
	 * Returns the Y offset from the entity's position for any entity riding this one.
	 */
	@Override
	public double getPassengersRidingOffset() {
		return 2.25D;
	}

	/**
	 * Used to both get a rider position and to push out of blocks
	 */
	private Vec3 getRiderPosition(@Nullable Entity passenger) {
		if (passenger != null) {
			float distance = 0.4F;

			double dx = Math.cos((this.getYRot() + 90) * Math.PI / 180.0D) * distance;
			double dz = Math.sin((this.getYRot() + 90) * Math.PI / 180.0D) * distance;

			return new Vec3(this.getX() + dx, this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset(), this.getZ() + dz);
		} else {
			return new Vec3(this.getX(), this.getY(), this.getZ());
		}
	}

	@Override
	public boolean canRiderInteract() {
		return true;
	}

	public static boolean yetiSnowyForestSpawnHandler(EntityType<? extends Yeti> entityType, ServerLevelAccessor accessor, MobSpawnType reason, BlockPos pos, RandomSource random) {
		Optional<ResourceKey<Biome>> key = accessor.getBiome(pos).unwrapKey();
		if (Objects.equals(key, Optional.of(TFBiomes.SNOWY_FOREST))) {
			return checkMobSpawnRules(entityType, accessor, reason, pos, random);
		} else {
			// normal EntityMob spawn check, checks light level
			return normalYetiSpawnHandler(entityType, accessor, reason, pos, random);
		}
	}

	public static boolean normalYetiSpawnHandler(EntityType<? extends Monster> entity, ServerLevelAccessor accessor, MobSpawnType reason, BlockPos pos, RandomSource random) {
		return accessor.getDifficulty() != Difficulty.PEACEFUL && isValidLightLevel(accessor, pos, random) && checkMobSpawnRules(entity, accessor, reason, pos, random);
	}

	public static boolean isValidLightLevel(ServerLevelAccessor accessor, BlockPos blockPos, RandomSource random) {
		Optional<ResourceKey<Biome>> key = accessor.getBiome(blockPos).unwrapKey();
		if (accessor.getBrightness(LightLayer.SKY, blockPos) > random.nextInt(32)) {
			return Objects.equals(key, Optional.of(TFBiomes.SNOWY_FOREST));
		} else {
			int i = accessor.getLevel().isThundering() ? accessor.getMaxLocalRawBrightness(blockPos, 10) : accessor.getMaxLocalRawBrightness(blockPos);
			return i <= random.nextInt(8) || Objects.equals(key, Optional.of(TFBiomes.SNOWY_FOREST));
		}
	}

	@Override
	public float getVoicePitch() {
		return super.getVoicePitch() + 0.55F;
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return TFSounds.YETI_GROWL.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return TFSounds.YETI_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TFSounds.YETI_DEATH.get();
	}
}
