package twilightforest.entity.boss;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;


public class EntityTFLichBolt extends EntityThrowable {
	
	public EntityLivingBase playerReflects = null;

	public EntityTFLichBolt(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}

	public EntityTFLichBolt(World par1World, EntityLivingBase par2EntityLiving) {
		super(par1World, par2EntityLiving);
	}

	public EntityTFLichBolt(World par1World) {
		super(par1World);
	}


	/**
	 * projectile speed
	 */
    protected float func_70182_d()
    {
        return 0.5F;
    }
	
	
	@Override
	public void onUpdate() {
		super.onUpdate();

        makeTrail();
	}
	

	public void makeTrail() {
		for (int i = 0; i < 5; i++) {
			double dx = posX + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
			double dy = posY + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
			double dz = posZ + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
			
			double s1 = ((rand.nextFloat() * 0.5F) + 0.5F) * 0.17F;
			double s2 = ((rand.nextFloat() * 0.5F) + 0.5F) * 0.80F;
			double s3 = ((rand.nextFloat() * 0.5F) + 0.5F) * 0.69F;

			worldObj.spawnParticle("mobSpell", dx, dy, dz, s1, s2, s3);
		}
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
            Vec3 vec3d = damagesource.getEntity().getLookVec();
            if (vec3d != null)
            {
            	this.setThrowableHeading(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord, 1.5F, 0.1F);  // reflect faster and more accurately
            }
            if (damagesource.getEntity() instanceof EntityLivingBase)
            {
            	playerReflects = (EntityLivingBase)damagesource.getEntity();
            }
            return true;
        }
        else
        {
            return false;
        }
    }
	
	/**
	 * Return who threw this projectile
	 */
    @Override
	public EntityLivingBase getThrower()
    {
        if (this.playerReflects != null)
        {
        	return this.playerReflects;
        }
        else
        {
        	return super.getThrower();
        }
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
        if (par1MovingObjectPosition.entityHit != null && par1MovingObjectPosition.entityHit instanceof EntityLivingBase)
        {
        	if (par1MovingObjectPosition.entityHit instanceof EntityTFLich) {
        		EntityTFLich lich = (EntityTFLich)par1MovingObjectPosition.entityHit;
        		if (lich.isShadowClone()) {
        			passThrough = true;
        		}
        	}
        	// if we're not set to pass, damage what we hit
            if (!passThrough && par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.getThrower()), 6))
            {
                ;
            }
        }
        // if we don't pass through, then stop and die
        if (!passThrough) {
	        for (int i = 0; i < 8; ++i)
	        {
	            this.worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(Items.ENDER_PEARL), this.posX, this.posY, this.posZ, rand.nextGaussian() * 0.05D, rand.nextDouble() * 0.2D, rand.nextGaussian() * 0.05D);
	        }
	
	        if (!this.worldObj.isRemote)
	        {
	            this.setDead();
	        }
        }

	}


}
