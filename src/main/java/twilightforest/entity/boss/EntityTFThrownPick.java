package twilightforest.entity.boss;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityTFThrownPick extends EntityThrowable  {

	private static final float PROJECTILE_DAMAGE = 3*1.5F;

	public EntityTFThrownPick(World par1World, EntityLivingBase par2EntityLivingBase) {
		super(par1World, par2EntityLivingBase);
        this.setSize(0.5F, 0.5F);
	}

	public EntityTFThrownPick(World par1World) {
		super(par1World);
        this.setSize(0.5F, 0.5F);
	}



	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
		boolean passThru = false;

		if (par1MovingObjectPosition.entityHit != null)
		{
			if (par1MovingObjectPosition.entityHit instanceof EntityTFKnightPhantom)
			{
				passThru = true;
			}

	    	// if we're not set to pass, damage what we hit
			if (!passThru)
			{
				par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), PROJECTILE_DAMAGE);
			}
		}

        for (int i = 0; i < 8; ++i)
        {
            this.worldObj.spawnParticle("largesmoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!passThru && !this.worldObj.isRemote)
        {
            this.setDead();
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
     * Projectile speed
     */
    protected float func_70182_d()
    {
        return 1.0F;
    }
    
    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float getGravityVelocity()
    {
        return 0.015F;
    }
}
