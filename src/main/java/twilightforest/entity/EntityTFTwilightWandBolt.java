package twilightforest.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;


public class EntityTFTwilightWandBolt extends EntityThrowable {

	public EntityTFTwilightWandBolt(World par1World) {
		super(par1World);
	}

	
	public EntityTFTwilightWandBolt(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
		// TODO Auto-generated constructor stub
	}


	public EntityTFTwilightWandBolt(World par1World, EntityLivingBase par2EntityLiving) {
		super(par1World, par2EntityLiving);
		// TODO Auto-generated constructor stub
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
			
			double s1 = ((rand.nextFloat() * 0.5F) + 0.5F) * 0.17F;  // color
			double s2 = ((rand.nextFloat() * 0.5F) + 0.5F) * 0.80F;  // color
			double s3 = ((rand.nextFloat() * 0.5F) + 0.5F) * 0.69F;  // color

			worldObj.spawnParticle("mobSpell", dx, dy, dz, s1, s2, s3);
		}
	}

	/**
	 * How much this entity falls each tick
	 */
	@Override
    protected float getGravityVelocity()
    {
        return 0.003F;
    }



	@Override
	protected void onImpact(RayTraceResult par1MovingObjectPosition) {
		// only hit living things
        if (par1MovingObjectPosition.entityHit != null && par1MovingObjectPosition.entityHit instanceof EntityLivingBase)
        {
            if (par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.getThrower()), 6))
            {
                ;
            }
        }

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
