package twilightforest.entity.boss;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;


public class EntityTFHydraPart extends EntityLiving {
	private static final DataParameter<String> PART_NAME = EntityDataManager.createKey(EntityTFHydraPart.class, DataSerializers.STRING);

	public EntityTFHydra hydra;

	public EntityTFHydraPart(World world) {
		super(world);
		isImmuneToFire = true;
	}

	public EntityTFHydraPart(EntityTFHydra hydra, String s, float f, float f1) {
		super(hydra.world);
		setSize(f, f1);
		this.hydra = hydra;
		setPartName(s);

		//texture = TwilightForestMod.MODEL_DIR + "hydra4.png";
		isImmuneToFire = true;

	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(PART_NAME, "");
	}

	public String getPartName() {
		return dataManager.get(PART_NAME);
	}

	public void setPartName(String name) {
		dataManager.set(PART_NAME, name);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setString("PartName", getPartName());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		setPartName(compound.getString("PartName"));
	}

	@Override
	public void onUpdate() {
		if (this.hydra != null && this.hydra.deathTime > 190) {
			setDead();
		}

		//  just die if we've been alive 60 seconds and there's still no body
		if (this.hydra == null && this.ticksExisted > 1200) {
			setDead();
		}

		super.onEntityUpdate();

		lastTickPosX = posX;
		lastTickPosY = posY;
		lastTickPosZ = posZ;

		if (this.newPosRotationIncrements > 0) {
			double var1 = this.posX + (this.interpTargetX - this.posX) / this.newPosRotationIncrements;
			double var3 = this.posY + (this.interpTargetY - this.posY) / this.newPosRotationIncrements;
			double var5 = this.posZ + (this.interpTargetZ - this.posZ) / this.newPosRotationIncrements;
			double var7 = MathHelper.wrapDegrees(this.interpTargetYaw - this.rotationYaw);
			this.rotationYaw = (float) (this.rotationYaw + var7 / this.newPosRotationIncrements);
			this.rotationPitch = (float) (this.rotationPitch + (this.interpTargetPitch - this.rotationPitch) / this.newPosRotationIncrements);
			--this.newPosRotationIncrements;
			this.setPosition(var1, var3, var5);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		}


		this.rotationYawHead = this.rotationYaw;
		this.prevRotationYawHead = this.prevRotationYaw;

		for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) {
		}
		for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) {
		}
		for (; renderYawOffset - prevRenderYawOffset < -180F; prevRenderYawOffset -= 360F) {
		}
		for (; renderYawOffset - prevRenderYawOffset >= 180F; prevRenderYawOffset += 360F) {
		}
		for (; rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) {
		}
		for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) {
		}
		for (; rotationYawHead - prevRotationYawHead < -180F; prevRotationYawHead -= 360F) {
		}
		for (; rotationYawHead - prevRotationYawHead >= 180F; prevRotationYawHead += 360F) {
		}


	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1000D);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (hydra != null) {
			return hydra.attackEntityFromPart(this, source, amount);
		} else {
			return false;
		}
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
}
