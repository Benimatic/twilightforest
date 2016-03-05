package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityTFGoblinChain extends Entity {
	
	Entity goblin;

	public EntityTFGoblinChain(World par1World) {
		super(par1World);
        setSize(0.1F, 0.1F);
	}

	public EntityTFGoblinChain(Entity goblin) {
		this(goblin.worldObj);
		this.goblin = goblin;
	}

	/**
	 * Don't take damage from attacks
	 */
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		return false;
	}

	
	/**
	 * Keep position updated
	 */
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
    
    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return false;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed()
    {
        return false;
    }
    
    public boolean isEntityEqual(Entity entity)
    {
        return this == entity || this.goblin == entity;
    }

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) { }

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) { }

}
