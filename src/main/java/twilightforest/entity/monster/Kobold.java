package twilightforest.entity.monster;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.entity.ai.goal.FlockToSameKindGoal;
import twilightforest.entity.ai.goal.PanicOnFlockDeathGoal;
import twilightforest.init.TFSounds;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class Kobold extends Monster {

	private static final EntityDataAccessor<Boolean> PANICKED = SynchedEntityData.defineId(Kobold.class, EntityDataSerializers.BOOLEAN);
	private int lastEatenBreadTicks;
	private int eatingTime;

	public Kobold(EntityType<? extends Kobold> type, Level world) {
		super(type, world);
		this.setCanPickUpLoot(true);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicOnFlockDeathGoal(this, 2.0F));
		this.goalSelector.addGoal(2, new SeekBreadGoal(this));
		this.goalSelector.addGoal(2, new RunAwayWhileHoldingBreadGoal(this));
		this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.3F));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(5, new FlockToSameKindGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new KoboldAttackPlayerTarget(this));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(PANICKED, false);
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 13.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.28D)
				.add(Attributes.ATTACK_DAMAGE, 4.0D);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TFSounds.KOBOLD_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return TFSounds.KOBOLD_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TFSounds.KOBOLD_DEATH.get();
	}

	public boolean isPanicked() {
		return this.entityData.get(PANICKED);
	}

	public void setPanicked(boolean flag) {
		this.entityData.set(PANICKED, flag);
	}

	@Override
	public SoundEvent getEatingSound(ItemStack stack) {
		return TFSounds.KOBOLD_MUNCH.get();
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (this.getLevel().isClientSide() && this.isPanicked()) {
			for (int i = 0; i < 2; i++) {
				this.getLevel().addParticle(ParticleTypes.SPLASH, this.getX() + (this.getRandom().nextDouble() - 0.5D) * this.getBbWidth() * 0.5, this.getY() + this.getEyeHeight(), this.getZ() + (this.getRandom().nextDouble() - 0.5D) * this.getBbWidth() * 0.5, 0, 0, 0);
			}
		}

		//bread munching
		if (!this.getLevel().isClientSide() && this.isAlive() && this.getItemBySlot(EquipmentSlot.MAINHAND).is(ItemTagGenerator.KOBOLD_PACIFICATION_BREADS)) {
			++this.lastEatenBreadTicks;
			if (this.eatingTime > 0) this.eatingTime--;
			ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
			if (this.canEat(itemstack)) {
				if (this.eatingTime <= 0) {
					ItemStack itemstack1 = itemstack.finishUsingItem(this.getLevel(), this);
					if (!itemstack1.isEmpty()) {
						this.setItemSlot(EquipmentSlot.MAINHAND, itemstack1);
					}
				}
				//every 3 seconds chew some bread
				if (this.lastEatenBreadTicks > 60 && this.getRandom().nextFloat() < 0.1F) {
					this.playSound(this.getEatingSound(itemstack), 0.75F, 0.9F);
					this.gameEvent(GameEvent.EAT);
					this.getLevel().broadcastEntityEvent(this, (byte) 45);
					this.lastEatenBreadTicks = 0;
				}
			}
		}
	}

	@Override
	public void handleEntityEvent(byte pId) {
		if (pId == 45) {
			ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
			if (!itemstack.isEmpty()) {
				this.spawnItemParticles(itemstack, 8);
			}
		} else {
			super.handleEntityEvent(pId);
		}

	}

	private void spawnItemParticles(ItemStack stack, int amount) {
		for (int i = 0; i < amount; ++i) {
			Vec3 vec3 = new Vec3((this.getRandom().nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
			vec3 = vec3.xRot(-this.getXRot() * Mth.DEG_TO_RAD);
			vec3 = vec3.yRot(-this.getYHeadRot() * Mth.DEG_TO_RAD);
			double d0 = -this.getRandom().nextFloat() * 0.6D - 0.3D;
			Vec3 vec31 = new Vec3((this.getRandom().nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
			vec31 = vec31.xRot(-this.getXRot() * Mth.DEG_TO_RAD);
			vec31 = vec31.yRot(-this.getYHeadRot() * Mth.DEG_TO_RAD);
			vec31 = vec31.add(this.getX(), this.getEyeY(), this.getZ());
			if (this.getLevel() instanceof ServerLevel server)
				server.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), vec31.x(), vec31.y(), vec31.z(), 1, vec3.x(), vec3.y() + 0.05D, vec3.z(), 0.0D);
			else
				this.getLevel().addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), vec31.x(), vec31.y(), vec31.z(), vec3.x(), vec3.y() + 0.05D, vec3.z());
		}
	}

	private boolean canEat(ItemStack stack) {
		return stack.getItem().isEdible() && !this.isPanicked();
	}

	@Override
	public boolean canTakeItem(ItemStack stack) {
		EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(stack);
		if (!this.getItemBySlot(equipmentslot).isEmpty()) {
			return false;
		} else {
			return equipmentslot == EquipmentSlot.MAINHAND && super.canTakeItem(stack);
		}
	}

	@Override
	public boolean canHoldItem(ItemStack stack) {
		return this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && stack.is(ItemTagGenerator.KOBOLD_PACIFICATION_BREADS) && !this.isPanicked();
	}

	@Override
	protected void pickUpItem(ItemEntity item) {
		ItemStack itemstack = item.getItem();
		if (this.canHoldItem(itemstack)) {
			int i = itemstack.getCount();
			if (i > 1) {
				this.dropItemStack(itemstack.split(i - 1));
			}

			this.onItemPickup(item);
			this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
			this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0F;
			this.take(item, itemstack.getCount());
			this.gameEvent(GameEvent.EQUIP);
			item.discard();
			this.lastEatenBreadTicks = 1;
			this.eatingTime = this.difficultyTime() + this.getRandom().nextInt(600);
			this.setTarget(null);
		}
	}

	//change the timer based on difficulty
	private int difficultyTime() {
		switch (this.getLevel().getDifficulty()) {
			case EASY -> {
				return 400;
			}
			case NORMAL -> {
				return 200;
			}
			case HARD -> {
				return 100;
			}
		}
		return 200;
	}

	private void dropItemStack(ItemStack stack) {
		ItemEntity itementity = new ItemEntity(this.getLevel(), this.getX(), this.getY(), this.getZ(), stack);
		this.getLevel().addFreshEntity(itementity);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("EatingTimeLeft", this.eatingTime);
		tag.putInt("TimeSinceBreadLastEaten", this.lastEatenBreadTicks);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.eatingTime = tag.getInt("EatingTimeLeft");
		this.lastEatenBreadTicks = tag.getInt("TimeSinceBreadLastEaten");
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	//we dont want kobolds to attack if they're pacified
	private static class KoboldAttackPlayerTarget extends NearestAttackableTargetGoal<Player> {

		public KoboldAttackPlayerTarget(Kobold mob) {
			super(mob, Player.class, true);
		}

		@Override
		public boolean canUse() {
			if (this.mob.getItemBySlot(EquipmentSlot.MAINHAND).is(ItemTagGenerator.KOBOLD_PACIFICATION_BREADS)) {
				return false;
			}
			return super.canUse();
		}
	}

	//look for bread
	//greatly inspired by Fox.FoxSearchForItemsGoal
	private static class SeekBreadGoal extends Goal {

		private static final Predicate<ItemEntity> ALLOWED_ITEMS = (item) ->
				item.getItem().is(ItemTagGenerator.KOBOLD_PACIFICATION_BREADS);

		private final Kobold mob;

		public SeekBreadGoal(Kobold mob) {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
			this.mob = mob;
		}

		@Override
		public boolean canUse() {
			if (!this.mob.getUseItem().isEmpty()) {
				return false;
			} else if (!this.mob.isPanicked()) {
				if (this.mob.getRandom().nextInt(10) != 0) {
					return false;
				} else {
					List<ItemEntity> list = this.mob.getLevel().getEntitiesOfClass(ItemEntity.class, this.mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), ALLOWED_ITEMS);
					return !list.isEmpty() && this.mob.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
				}
			} else {
				return false;
			}
		}

		@Override
		public void tick() {
			List<ItemEntity> list = this.mob.getLevel().getEntitiesOfClass(ItemEntity.class, this.mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), ALLOWED_ITEMS);
			ItemStack itemstack = this.mob.getItemBySlot(EquipmentSlot.MAINHAND);
			if (itemstack.isEmpty() && !list.isEmpty()) {
				this.mob.getNavigation().moveTo(list.get(0), 1.2F);
				this.mob.getLookControl().setLookAt(list.get(0).getX(), list.get(0).getY(), list.get(0).getZ());
			}
		}

		@Override
		public void start() {
			List<ItemEntity> list = this.mob.getLevel().getEntitiesOfClass(ItemEntity.class, this.mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), ALLOWED_ITEMS);
			if (!list.isEmpty()) {
				this.mob.getNavigation().moveTo(list.get(0), 1.2F);
			}
		}
	}

	//avoid players only while holding bread
	private static class RunAwayWhileHoldingBreadGoal extends AvoidEntityGoal<Player> {

		public RunAwayWhileHoldingBreadGoal(Kobold mob) {
			super(mob, Player.class, 8.0F, 1.5F, 1.5F);
		}

		@Override
		public boolean canUse() {
			if (this.mob.getItemBySlot(EquipmentSlot.MAINHAND).is(ItemTagGenerator.KOBOLD_PACIFICATION_BREADS)) {
				return super.canUse();
			}
			return false;
		}
	}
}
