package twilightforest.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import twilightforest.entity.ai.EntityAITFRiderSpearAttack;

import javax.annotation.Nullable;

public class EntityTFGoblinKnightLower extends MonsterEntity {

	private static final DataParameter<Boolean> ARMOR = EntityDataManager.createKey(EntityTFGoblinKnightLower.class, DataSerializers.BOOLEAN);
	private static final AttributeModifier ARMOR_MODIFIER = new AttributeModifier("Armor boost", 17, AttributeModifier.Operation.ADDITION).setSaved(false);

	public EntityTFGoblinKnightLower(EntityType<? extends EntityTFGoblinKnightLower> type, World world) {
		super(type, world);
		this.setHasArmor(true);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new EntityAITFRiderSpearAttack(this));
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
	}

	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
	}

	@Override
	protected void registerData() {
		super.registerData();
		dataManager.register(ARMOR, false);
	}

	public boolean hasArmor() {
		return dataManager.get(ARMOR);
	}

	private void setHasArmor(boolean flag) {
		dataManager.set(ARMOR, flag);

		if (!world.isRemote) {
			if (flag) {
				if (!getAttribute(SharedMonsterAttributes.ARMOR).hasModifier(ARMOR_MODIFIER)) {
					getAttribute(SharedMonsterAttributes.ARMOR).applyModifier(ARMOR_MODIFIER);
				}
			} else {
				getAttribute(SharedMonsterAttributes.ARMOR).removeModifier(ARMOR_MODIFIER);
			}
		}
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putBoolean("hasArmor", this.hasArmor());
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.setHasArmor(compound.getBoolean("hasArmor"));
	}

	@Nullable
	@Override
	public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData livingData, @Nullable CompoundNBT dataTag) {
		livingData = super.onInitialSpawn(worldIn, difficulty, reason, livingData, dataTag);

		EntityTFGoblinKnightUpper upper = new EntityTFGoblinKnightUpper(TFEntities.goblin_knight_upper, this.world);
		upper.setLocationAndAngles(this.getX(), this.getY(), this.getZ(), this.rotationYaw, 0.0F);
		upper.onInitialSpawn(worldIn, difficulty, SpawnReason.NATURAL, livingData, dataTag); //TODO: verify
		this.world.addEntity(upper);
		upper.startRiding(this);

		return livingData;
	}

	@Override
	public double getMountedYOffset() {
		return 1.0D;
	}

	@Override
	public void updateAITasks() {
		super.updateAITasks();

		if (isBeingRidden() && getPassengers().get(0) instanceof LivingEntity && this.getAttackTarget() == null) {
			this.setAttackTarget(((MobEntity) this.getPassengers().get(0)).getAttackTarget());
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {

		if (isBeingRidden() && getPassengers().get(0) instanceof LivingEntity) {
			return ((LivingEntity) this.getPassengers().get(0)).attackEntityAsMob(entity);
		} else {
			return super.attackEntityAsMob(entity);
		}

	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		// check the angle of attack, if applicable
		Entity attacker = null;
		if (source.getTrueSource() != null) {
			attacker = source.getTrueSource();
		}

		if (source.getTrueSource() != null) {
			attacker = source.getTrueSource();
		}

		if (attacker != null) {
			// determine angle

			double dx = this.getX() - attacker.getX();
			double dz = this.getZ() - attacker.getZ();
			float angle = (float) ((Math.atan2(dz, dx) * 180D) / Math.PI) - 90F;

			float difference = MathHelper.abs((this.renderYawOffset - angle) % 360);

			// shield?
			EntityTFGoblinKnightUpper upper = null;

			if (isBeingRidden() && getPassengers().get(0) instanceof EntityTFGoblinKnightUpper) {
				upper = (EntityTFGoblinKnightUpper) this.getPassengers().get(0);
			}

			if (upper != null && upper.hasShield() && difference > 150 && difference < 230) {
				if (upper.takeHitOnShield(source, amount)) {
					return false;
				}
			}

			// break armor?
			if (this.hasArmor() && (difference > 300 || difference < 60)) {
				breakArmor();
			}
		}

		return super.attackEntityFrom(source, amount);
	}

	private void breakArmor() {
		this.renderBrokenItemStack(new ItemStack(Items.IRON_CHESTPLATE));
		this.renderBrokenItemStack(new ItemStack(Items.IRON_CHESTPLATE));
		this.renderBrokenItemStack(new ItemStack(Items.IRON_CHESTPLATE));

		this.setHasArmor(false);
	}
}
