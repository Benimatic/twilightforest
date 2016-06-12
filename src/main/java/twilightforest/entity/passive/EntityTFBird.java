package twilightforest.entity.passive;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;


public abstract class EntityTFBird extends EntityAnimal {

	public float flapLength = 0.0F;
	public float flapIntensity = 0.0F;
	public float lastFlapIntensity;
	public float lastFlapLength;
	public float flapSpeed = 1.0F;

	public EntityTFBird(World par1World) {
		super(par1World);
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	@Override
	public boolean isAIEnabled() {
	    return true;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
	    super.onLivingUpdate();
	    this.lastFlapLength = this.flapLength;
	    this.lastFlapIntensity = this.flapIntensity;
	    this.flapIntensity = (float)(this.flapIntensity + (this.onGround ? -1 : 4) * 0.3D);
	
	    if (this.flapIntensity < 0.0F)
	    {
	        this.flapIntensity = 0.0F;
	    }
	
	    if (this.flapIntensity > 1.0F)
	    {
	        this.flapIntensity = 1.0F;
	    }
	
	    if (!this.onGround && this.flapSpeed < 1.0F)
	    {
	        this.flapSpeed = 1.0F;
	    }
	
	    this.flapSpeed = (float)(this.flapSpeed * 0.9D);
	
	    // don't fall as fast
	    if (!this.onGround && this.motionY < 0.0D)
	    {
	        this.motionY *= 0.6D;
	    }
	
	    this.flapLength += this.flapSpeed * 2.0F;
	    
//	    // rise up when we go fast?
//        if (this.getMoveHelper().getSpeed() > 0.39F && this.moveForward > 0.1F)
//        {
//        	this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
//        	this.moveForward *= 2F;
//        }
	}

	/**
	 * Called when the mob is falling. Calculates and applies fall damage.
	 */
	@Override
	protected void fall(float par1) {}

    /**
     * returns if this entity triggers Blocks.ONENTITYWALKING on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    @Override
	protected boolean canTriggerWalking()
    {
        return false;
    }
	
	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected Item getDropItem() {
	    return Items.FEATHER;
	}

	/**
	 * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
	 */
	@Override
	public EntityAnimal createChild(EntityAgeable entityanimal)
	{
		return null;
	}

	/**
	 * Overridden by flying birds
	 */
    public boolean isBirdLanded()
    {
    	return true;
    }

}