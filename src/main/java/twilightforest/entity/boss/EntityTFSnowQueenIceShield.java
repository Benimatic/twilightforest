package twilightforest.entity.boss;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityTFSnowQueenIceShield extends Entity {
	
	EntityTFSnowQueen queen;
	
	public EntityTFSnowQueenIceShield(World par1World) {
		super(par1World);
        setSize(0.75F, 0.75F);
	}

	public EntityTFSnowQueenIceShield(EntityTFSnowQueen goblin) {
		this(goblin.getWorld());
		this.queen = goblin;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		this.worldObj.playSoundAtEntity(this, "random.break", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);

		return false;
	}

    @Override
    public void onUpdate() {
    	super.onUpdate();

    	this.ticksExisted++;
    	
    	lastTickPosX = posX;
    	lastTickPosY = posY;
    	lastTickPosZ = posZ;

    	
    	//System.out.println("Updating " + this + " with angles " + rotationYawHead + ", " + rotationPitch);

    	for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }
    	for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }
    	for (; rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
    	for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }

    }

	@Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

	@Override
    public boolean canBePushed()
    {
        return false;
    }

	@Override
    public boolean isEntityEqual(Entity entity)
    {
        return this == entity || this.queen == entity;
    }

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) { }

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) { }

}
