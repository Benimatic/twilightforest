package twilightforest.entity.boss;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityTFHydraPart extends LivingEntity {

	private static final DataParameter<String> PART_NAME = EntityDataManager.createKey(EntityTFHydraPart.class, DataSerializers.STRING);

	public EntityTFHydra hydra;

	public EntityTFHydraPart(EntityType<? extends EntityTFHydraPart> type, World world) {
		super(type, world);
		isImmuneToFire();
	}

	public EntityTFHydraPart(EntityType<? extends EntityTFHydraPart> type, EntityTFHydra hydra, String name) {
		super(type, hydra.world);
		this.hydra = hydra;
		setPartName(name);
		//texture = TwilightForestMod.MODEL_DIR + "hydra4.png";
		isImmuneToFire();
	}

	@Override
	protected void registerData() {
		super.registerData();
		dataManager.register(PART_NAME, "");
	}

	public String getPartName() {
		return dataManager.get(PART_NAME);
	}

	public void setPartName(String name) {
		dataManager.set(PART_NAME, name);
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putString("PartName", getPartName());
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		setPartName(compound.getString("PartName"));
	}

	@Override
	public void tick() {
		if (this.hydra != null && this.hydra.deathTime > 190) {
			setDead();
		}

		//  just die if we've been alive 60 seconds and there's still no body
		if (this.hydra == null && this.ticksExisted > 1200) {
			setDead();
		}

		super.baseTick();

		lastTickPosX = posX;
		lastTickPosY = posY;
		lastTickPosZ = posZ;

		if (this.newPosRotationIncrements > 0) {
			double x = this.posX + (this.interpTargetX - this.posX) / this.newPosRotationIncrements;
			double y = this.posY + (this.interpTargetY - this.posY) / this.newPosRotationIncrements;
			double z = this.posZ + (this.interpTargetZ - this.posZ) / this.newPosRotationIncrements;
			double yawDelta = MathHelper.wrapDegrees(this.interpTargetYaw - this.rotationYaw);
			this.rotationYaw = (float) (this.rotationYaw + yawDelta / this.newPosRotationIncrements);
			this.rotationPitch = (float) (this.rotationPitch + (this.interpTargetPitch - this.rotationPitch) / this.newPosRotationIncrements);
			--this.newPosRotationIncrements;
			this.setPosition(x, y, z);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		}

		this.rotationYawHead = this.rotationYaw;
		this.prevRotationYawHead = this.prevRotationYaw;

		while (rotationYaw - prevRotationYaw < -180F) prevRotationYaw -= 360F;
		while (rotationYaw - prevRotationYaw >= 180F) prevRotationYaw += 360F;

		while (renderYawOffset - prevRenderYawOffset < -180F) prevRenderYawOffset -= 360F;
		while (renderYawOffset - prevRenderYawOffset >= 180F) prevRenderYawOffset += 360F;

		while (rotationPitch - prevRotationPitch < -180F) prevRotationPitch -= 360F;
		while (rotationPitch - prevRotationPitch >= 180F) prevRotationPitch += 360F;

		while (rotationYawHead - prevRotationYawHead < -180F) prevRotationYawHead -= 360F;
		while (rotationYawHead - prevRotationYawHead >= 180F) prevRotationYawHead += 360F;
	}

	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1000D);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return hydra != null && hydra.attackEntityFromPart(this, source, amount);
	}

	@Override
	public boolean isEntityEqual(Entity entity) {
		return this == entity || hydra == entity;
	}

	@Override
	protected void setRotation(float yaw, float pitch) {
		this.rotationYaw = yaw % 360.0F;
		this.rotationPitch = pitch % 360.0F;
	}

	@Override
	public boolean isNonBoss() {
		return false;
	}

	@Override
	protected boolean canDespawn() {
		return hydra == null;
	}
}
