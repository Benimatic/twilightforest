package twilightforest.entity.monster;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.init.TFSounds;
import twilightforest.init.TFEntities;
import twilightforest.entity.ai.goal.RiderSpearAttackGoal;

import org.jetbrains.annotations.Nullable;
import java.util.Objects;

public class LowerGoblinKnight extends Monster {

	private static final EntityDataAccessor<Boolean> ARMOR = SynchedEntityData.defineId(LowerGoblinKnight.class, EntityDataSerializers.BOOLEAN);
	private static final AttributeModifier ARMOR_MODIFIER = new AttributeModifier("Armor boost", 17, AttributeModifier.Operation.ADDITION);

	public LowerGoblinKnight(EntityType<? extends LowerGoblinKnight> type, Level world) {
		super(type, world);
		this.setHasArmor(true);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new RiderSpearAttackGoal(this));
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, false) {
			@Override
			public boolean canUse() {
				if (mob.isVehicle() && this.mob.getPassengers().get(0) instanceof UpperGoblinKnight knight && knight.heavySpearTimer > 0) {
					return false;
				}
				return super.canUse();
			}
		});
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 20.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.28D)
				.add(Attributes.ATTACK_DAMAGE, 4.0D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ARMOR, false);
	}

	public boolean hasArmor() {
		return this.entityData.get(ARMOR);
	}

	private void setHasArmor(boolean flag) {
		this.entityData.set(ARMOR, flag);

		if (!this.getLevel().isClientSide()) {
			if (flag) {
				if (!Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).hasModifier(ARMOR_MODIFIER)) {
					Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).addTransientModifier(ARMOR_MODIFIER);
				}
			} else {
				Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).removeModifier(ARMOR_MODIFIER);
			}
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("hasArmor", this.hasArmor());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setHasArmor(compound.getBoolean("hasArmor"));
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
		data = super.finalizeSpawn(accessor, difficulty, reason, data, tag);

		UpperGoblinKnight upper = new UpperGoblinKnight(TFEntities.UPPER_GOBLIN_KNIGHT.get(), this.getLevel());
		upper.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
		upper.finalizeSpawn(accessor, difficulty, MobSpawnType.NATURAL, data, tag);
		upper.startRiding(this);

		return data;
	}

	@Override
	public double getPassengersRidingOffset() {
		return 1.0D;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.isVehicle() && this.getPassengers().get(0) instanceof LivingEntity living) {
			return living.doHurtTarget(entity);
		} else {
			return super.doHurtTarget(entity);
		}

	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return this.isVehicle() ? TFSounds.GOBLIN_KNIGHT_MUFFLED_AMBIENT.get() : TFSounds.GOBLIN_KNIGHT_AMBIENT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return this.isVehicle() ? TFSounds.GOBLIN_KNIGHT_MUFFLED_DEATH.get() : TFSounds.GOBLIN_KNIGHT_DEATH.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return this.isVehicle() ? TFSounds.GOBLIN_KNIGHT_MUFFLED_HURT.get() : TFSounds.GOBLIN_KNIGHT_HURT.get();
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		// check the angle of attack, if applicable
		Entity attacker = null;
		if (source.getEntity() != null) {
			attacker = source.getEntity();
		}

		if (source.getEntity() != null) {
			attacker = source.getEntity();
		}

		if (attacker != null) {
			// determine angle

			double dx = this.getX() - attacker.getX();
			double dz = this.getZ() - attacker.getZ();
			float angle = (float) ((Math.atan2(dz, dx) * 180D) / Math.PI) - 90F;

			float difference = Mth.abs((this.yBodyRot - angle) % 360);

			// shield?
			UpperGoblinKnight upper = null;

			if (this.isVehicle() && this.getPassengers().get(0) instanceof UpperGoblinKnight goblin) {
				upper = goblin;
			}

			if (upper != null && upper.hasShield() && difference > 150 && difference < 230) {
				if (upper.takeHitOnShield(source, amount)) {
					return false;
				}
			}

			// break armor?
			if (this.hasArmor() && (difference > 300 || difference < 60)) {
				this.breakArmor();
			}
		}

		return super.hurt(source, amount);
	}

	@Override
	public void positionRider(Entity entity) {
		super.positionRider(entity);
		if(entity instanceof UpperGoblinKnight goblin) {
			goblin.setYBodyRot(this.getYRot());
			goblin.setYHeadRot(this.getYRot());
			goblin.setYRot(this.getYRot());
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		if (id == 5) {
			ItemStack broken = new ItemStack(Items.IRON_CHESTPLATE);
			this.breakItem(broken);
			this.breakItem(broken);
			this.breakItem(broken);
		} else {
			super.handleEntityEvent(id);
		}
	}

	private void breakArmor() {
		this.getLevel().broadcastEntityEvent(this, (byte) 5);
		this.setHasArmor(false);
	}
}
