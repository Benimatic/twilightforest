package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import twilightforest.TFSounds;
import twilightforest.entity.ai.FlockToSameKindGoal;
import twilightforest.entity.ai.PanicOnFlockDeathGoal;
import twilightforest.util.TFDamageSources;

import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class KoboldEntity extends Monster {

	private static final EntityDataAccessor<Boolean> PANICKED = SynchedEntityData.defineId(KoboldEntity.class, EntityDataSerializers.BOOLEAN);

	public KoboldEntity(EntityType<? extends KoboldEntity> type, Level world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicOnFlockDeathGoal(this, 2.0F));
		this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.3F));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(4, new FlockToSameKindGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(PANICKED, false);
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 13.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.28D)
				.add(Attributes.ATTACK_DAMAGE, 4.0D);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TFSounds.KOBOLD_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return TFSounds.KOBOLD_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TFSounds.KOBOLD_DEATH;
	}

	public boolean isPanicked() {
		return entityData.get(PANICKED);
	}

	public void setPanicked(boolean flag) {
		entityData.set(PANICKED, flag);
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (level.isClientSide && isPanicked()) {
			for (int i = 0; i < 2; i++) {
				this.level.addParticle(ParticleTypes.SPLASH, this.getX() + (this.random.nextDouble() - 0.5D) * this.getBbWidth() * 0.5, this.getY() + this.getEyeHeight(), this.getZ() + (this.random.nextDouble() - 0.5D) * this.getBbWidth() * 0.5, 0, 0, 0);
			}
		}
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 4;
	}
}
