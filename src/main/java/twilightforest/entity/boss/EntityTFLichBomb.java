package twilightforest.entity.boss;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;


public class EntityTFLichBomb extends EntityThrowable {
	
	public EntityTFLichBomb(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}

	public EntityTFLichBomb(World par1World, EntityLivingBase par2EntityLiving) {
		super(par1World, par2EntityLiving);
	}

	public EntityTFLichBomb(World par1World) {
		super(par1World);
	}


	/**
	 * projectile speed
	 */
    protected float func_40077_c()
    {
        return 0.35F;
    }
	
	
	@Override
	public void onUpdate() {
		super.onUpdate();

        makeTrail();
	}
	

	public void makeTrail() {
		for (int i = 0; i < 1; i++) {
			double sx =  0.5 * (rand.nextDouble() - rand.nextDouble()) + this.motionX;
			double sy =  0.5 * (rand.nextDouble() - rand.nextDouble()) + this.motionY;
			double sz =  0.5 * (rand.nextDouble() - rand.nextDouble()) + this.motionZ;
			
			
			double dx = posX + sx; 
			double dy = posY + sy; 
			double dz = posZ + sz; 

			worldObj.spawnParticle("flame", dx, dy, dz, sx * -0.25, sy * -0.25, sz * -0.25);
		}
	}
	

	/**
	 * Always be on fire!
	 */
	@Override
	public boolean isBurning()
	{
		return true;
	}

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    @Override
	public boolean canBeCollidedWith()
    {
        return true;
    }
    
    /**
     * We need to set this so that the player can attack and reflect the bolt
     */
    @Override
	public float getCollisionBorderSize()
    {
        return 1.0F;
    }

	
	/**
	 * Reflect!
	 */
	@Override
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
//		System.out.println("Lich bolt being attacked!");
		
        setBeenAttacked();
        if (damagesource.getEntity() != null)
        {
        	// explode
        	explode();
        	
            return true;
        }
        else
        {
            return false;
        }
    }

	protected void explode() {
		float explosionPower = 2F;
        this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, explosionPower, false, false);

        this.setDead();
	}

	/**
	 * How much this entity falls each tick
	 */
	@Override
    protected float getGravityVelocity()
    {
        return 0.001F;
    }

	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
		boolean passThrough = false;
		
		// pass through other lich bolts
		if (par1MovingObjectPosition.entityHit != null && (par1MovingObjectPosition.entityHit instanceof EntityTFLichBolt || par1MovingObjectPosition.entityHit instanceof EntityTFLichBomb)) {
			passThrough = true;
		}
		
		// only damage living things
		if (par1MovingObjectPosition.entityHit != null && par1MovingObjectPosition.entityHit instanceof EntityTFLich)
		{
			passThrough = true;
		}
        
    	// if we're not set to pass, damage what we hit
        if (!passThrough)
        {
        	explode();
        }

	}


}
