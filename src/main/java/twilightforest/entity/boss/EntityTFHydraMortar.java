package twilightforest.entity.boss;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityTFHydraMortar extends EntityThrowable {

    private static final int BURN_FACTOR = 5;
	private static final int DIRECT_DAMAGE = 18;
	
	public EntityLivingBase playerReflects = null;
	
	public int fuse = 80;
	
	public boolean megaBlast = false;

	public EntityTFHydraMortar(World par1World)
    {
        super(par1World);
        this.setSize(0.75F, 0.75F);
        
    }
	
	public EntityTFHydraMortar(World par1World, EntityLivingBase par2EntityLiving) {
		super(par1World, par2EntityLiving);
        this.setSize(0.75F, 0.75F);
	}

    /**
     * Called to update the entity's position/logic.
     */
    @Override
	public void onUpdate()
    {
    	super.onUpdate();
    	
        this.func_145771_j(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0D, this.posZ); // push out of block

    	if (this.onGround)
    	{
    		if (!worldObj.isRemote)
    		{
	    		// slow down
	    		this.motionX *= 0.9D;
	    		this.motionY *= 0.9D;
	    		this.motionZ *= 0.9D;
    		}

    		// eventually explode
    		if (this.fuse-- <= 0)
    		{
    			detonate();
    		}

    	}
    }
    

    /**
     * Converts this mortar into a blasting one.
     */
	public void setToBlasting() {
		this.megaBlast = true;
	}

	
    /**
     * Called when this EntityThrowable hits a block or entity.
     */
	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if (mop.entityHit == null && !megaBlast)
		{
			// we hit the ground
			this.motionY = 0;
			this.onGround = true;
		}
		else 
		{
			detonate();

		}
	}
	
	@Override
    public float func_145772_a(Explosion par1Explosion, World par2World, int par3, int par4, int par5, Block par6Block)
    {
        float var6 = super.func_145772_a(par1Explosion, par2World, par3, par4, par5, par6Block);

        if (this.megaBlast && par6Block != Blocks.bedrock && par6Block != Blocks.end_portal && par6Block != Blocks.end_portal_frame)
        {
            var6 = Math.min(0.8F, var6);
        }

        return var6;
    }
	
	@SuppressWarnings("unchecked")
	protected void detonate()
	{
		//this.worldObj.playAuxSFX(2004, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), 32764);
		
        //this.worldObj.createExplosion((Entity)null, this.posX, this.posY, this.posZ, explosionPower, true);
        
		float explosionPower = megaBlast ? 4.0F : 0.1F;
        this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, explosionPower, true, true);


		if (!worldObj.isRemote)
		{
			// damage nearby things
			List<Entity> nearbyList = new ArrayList<Entity>(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(1.0D, 1.0D, 1.0D)));

			for (Entity nearby : nearbyList)
			{
				if (nearby.attackEntityFrom(DamageSource.causeFireballDamage(null, this.getThrower()), DIRECT_DAMAGE) && !nearby.isImmuneToFire())
				{
					nearby.setFire(BURN_FACTOR);
				}
			}
		}

        this.setDead();
    }
	
	/**
	 * Reflect!
	 */
	@Override
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
//		System.out.println("Hydra mortar being attacked!");
		
        setBeenAttacked();
        if (damagesource.getEntity() != null && !this.worldObj.isRemote)
        {
            Vec3 vec3d = damagesource.getEntity().getLookVec();
            if (vec3d != null)
            {
            	this.setThrowableHeading(vec3d.xCoord, vec3d.yCoord + 1, vec3d.zCoord, 1.5F, 0.1F);  // reflect faster and more accurately
    			this.onGround = false;
    			this.fuse += 20;

            }
            if (damagesource.getEntity() instanceof EntityLivingBase)
            {
            	this.playerReflects = (EntityLivingBase)damagesource.getEntity();
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
        return 1.5F;
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    @Override
	protected float getGravityVelocity()
    {
        return 0.05F;
    }

    @Override
	protected float func_70182_d()
    {
        return 0.75F;
    }

    @Override
	protected float func_70183_g()
    {
        return -20.0F;
    }
}
