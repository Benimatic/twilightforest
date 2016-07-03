package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityTFSpikeBlock extends Entity {
	
	EntityTFBlockGoblin goblin;
	
	public EntityTFSpikeBlock(World par1World) {
		super(par1World);
        setSize(0.75F, 0.75F);
	}

	public EntityTFSpikeBlock(EntityTFBlockGoblin goblin) {
		this(goblin.func_82194_d());
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
	 * Skip most of the living update things
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
    @Override
	public boolean canBeCollidedWith()
    {
        return false;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    @Override
	public boolean canBePushed()
    {
        return false;
    }
    
    @Override
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
    
//    /**
//     * Returns the texture's file path as a String.
//     */
//	@Override
//    public String getTexture()
//    {
//        return this.texture;
//    }
}
