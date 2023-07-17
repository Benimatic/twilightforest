package twilightforest.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import twilightforest.entity.EnforcedHomePoint;
import twilightforest.entity.ai.control.NoClipMoveControl;
import twilightforest.entity.ai.goal.SimplifiedAttackGoal;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFSounds;

import java.util.EnumSet;

public class Wraith extends FlyingMob implements Enemy, EnforcedHomePoint {

	public Wraith(EntityType<? extends Wraith> type, Level level) {
		super(type, level);
		this.moveControl = new NoClipMoveControl(this);
		this.noPhysics = true;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(2, new MoveTowardsHomeGoal(this, 0.85D));
		this.goalSelector.addGoal(4, new SimplifiedAttackGoal(this));
		this.goalSelector.addGoal(5, new FlyTowardsTargetGoal(this));
		this.goalSelector.addGoal(6, new RandomFloatAroundGoal(this));
		this.goalSelector.addGoal(7, new LookAroundGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 20.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.5D)
				.add(Attributes.ATTACK_DAMAGE, 5.0D);
	}

	@Override
	public boolean isSteppingCarefully() {
		return true;
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (super.hurt(source, amount)) {
			Entity entity = source.getEntity();
			if (this.getVehicle() == entity || this.getPassengers().contains(entity)) {
				return true;
			}
			if (entity != this && entity instanceof LivingEntity && !source.isCreativePlayer()) {
				this.setTarget((LivingEntity) entity);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		entity.hurt(TFDamageTypes.getEntityDamageSource(this.getLevel(), TFDamageTypes.HAUNT, this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
		return super.doHurtTarget(entity);
	}

	@Override
	protected boolean canRide(Entity entity) {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TFSounds.WRAITH_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return TFSounds.WRAITH_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TFSounds.WRAITH_DEATH.get();
	}

	public static boolean checkMonsterSpawnRules(EntityType<? extends Wraith> entity, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
		return world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && checkMobSpawnRules(entity, world, reason, pos, random);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType type, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
		if (type == MobSpawnType.STRUCTURE || type == MobSpawnType.SPAWNER) this.restrictTo(this.blockPosition(), 20);
		return super.finalizeSpawn(accessor, difficulty, type, data, tag);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		this.saveHomePointToNbt(tag);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.loadHomePointFromNbt(tag, 20);
	}

	@Override
	public BlockPos getRestrictionCenter() {
		return this.getRestrictCenter();
	}

	@Override
	public void setRestriction(BlockPos pos, int dist) {
		this.restrictTo(pos, dist);
	}

	static class FlyTowardsTargetGoal extends Goal {
		private final Wraith wraith;

		FlyTowardsTargetGoal(Wraith wraith) {
			this.wraith = wraith;
			this.setFlags(EnumSet.of(Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			return wraith.getTarget() != null;
		}

		@Override
		public boolean canContinueToUse() {
			return false;
		}

		@Override
		public void start() {
			LivingEntity target = wraith.getTarget();
			if (target != null)
				wraith.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 0.5F);
		}
	}

	// [VanillaCopy] Ghast.RandomFloatAroundGoal
	static class RandomFloatAroundGoal extends Goal {
		private final Wraith wraith;

		public RandomFloatAroundGoal(Wraith wraith) {
			this.wraith = wraith;
			this.setFlags(EnumSet.of(Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			if (this.wraith.getTarget() != null || !this.wraith.isWithinRestriction())
				return false;
			MoveControl control = this.wraith.getMoveControl();
			double d0 = control.getWantedX() - this.wraith.getX();
			double d1 = control.getWantedY() - this.wraith.getY();
			double d2 = control.getWantedZ() - this.wraith.getZ();
			double d3 = d0 * d0 + d1 * d1 + d2 * d2;
			return d3 < 1.0D || d3 > 3600.0D;
		}

		@Override
		public boolean canContinueToUse() {
			return false;
		}

		@Override
		public void start() {
			RandomSource random = this.wraith.getRandom();
			double d0 = this.wraith.getX() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
			double d1 = this.wraith.getY() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
			double d2 = this.wraith.getZ() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
			this.wraith.getMoveControl().setWantedPosition(d0, d1, d2, 0.5D);
		}
	}

	// [VanillaCopy] Ghast.GhastLookGoal
	public static class LookAroundGoal extends Goal {
		private final Wraith wraith;

		public LookAroundGoal(Wraith wraith) {
			this.wraith = wraith;
			this.setFlags(EnumSet.of(Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			return true;
		}

		@Override
		public void tick() {
			if (this.wraith.getTarget() == null) {
				this.wraith.setYRot(-((float) Mth.atan2(this.wraith.getDeltaMovement().x(), this.wraith.getDeltaMovement().z())) * (180F / (float) Math.PI));
				this.wraith.setYBodyRot(this.wraith.getYRot());
			} else {
				LivingEntity entitylivingbase = this.wraith.getTarget();

				if (entitylivingbase.distanceToSqr(this.wraith) < 4096.0D) {
					double d1 = entitylivingbase.getX() - this.wraith.getX();
					double d2 = entitylivingbase.getZ() - this.wraith.getZ();
					this.wraith.setYRot(-((float) Mth.atan2(d1, d2)) * (180F / (float) Math.PI));
					this.wraith.setYBodyRot(this.wraith.getYRot());
				}
			}
		}
	}

	//modified version of MoveTowardsRestrictionGoal. We're limited with what we can use since Wraiths arent PathfinderMobs
	public static class MoveTowardsHomeGoal extends Goal {
		private final FlyingMob mob;
		private double wantedX;
		private double wantedY;
		private double wantedZ;
		private final double speedModifier;

		public MoveTowardsHomeGoal(FlyingMob mob, double speedModifier) {
			this.mob = mob;
			this.speedModifier = speedModifier;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			if (this.mob.isWithinRestriction() || this.mob.getTarget() != null) {
				return false;
			} else {
				BlockPos pos = this.mob.getRestrictCenter()
						.relative(Direction.getRandom(this.mob.getRandom()))
						.offset(this.mob.getRandom().nextInt(5), this.mob.getRandom().nextInt(5), this.mob.getRandom().nextInt(5));
				if (pos == null || !this.mob.getLevel().isLoaded(pos)) {
					return false;
				} else {
					this.wantedX = pos.getX();
					this.wantedY = pos.getY();
					this.wantedZ = pos.getZ();
					return true;
				}
			}
		}

		public boolean canContinueToUse() {
			return false;
		}

		public void start() {
			this.mob.getMoveControl().setWantedPosition(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
		}
	}
}
