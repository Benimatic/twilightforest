package twilightforest.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import twilightforest.block.TFBlocks;
import twilightforest.item.TFItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class EntityTFMoonwormShot extends EntityThrowable {

	public EntityTFMoonwormShot(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
		// TODO Auto-generated constructor stub
	}

	public EntityTFMoonwormShot(World par1World, EntityLivingBase par2EntityLiving) {
		super(par1World, par2EntityLiving);
		// TODO Auto-generated constructor stub
	}

	public EntityTFMoonwormShot(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
	}


	/**
	 * projectile speed
	 */
    protected float func_40077_c()
    {
        return 0.5F;
    }
	
	
	@Override
	public void onUpdate() {
		super.onUpdate();

        makeTrail();
	}
	
    /**
     * Gets how bright this entity is.
     */
	@Override
    public float getBrightness(float par1)
    {
        return 1.0F;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1)
    {
        return 15728880;
    }

	

	public void makeTrail() {
//		for (int i = 0; i < 5; i++) {
//			double dx = posX + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
//			double dy = posY + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
//			double dz = posZ + 0.5 * (rand.nextDouble() - rand.nextDouble()); 
//			
//			double s1 = ((rand.nextFloat() * 0.5F) + 0.5F) * 0.17F;
//			double s2 = ((rand.nextFloat() * 0.5F) + 0.5F) * 0.80F;
//			double s3 = ((rand.nextFloat() * 0.5F) + 0.5F) * 0.69F;
//
//			worldObj.spawnParticle("mobSpell", dx, dy, dz, s1, s2, s3);
//		}
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
	 * How much this entity falls each tick
	 */
	@Override
    protected float getGravityVelocity()
    {
        return 0.03F;
    }

	@Override
	protected void onImpact(MovingObjectPosition mop) {

		// did we hit a block?  Make a worm there!
		
		if (mop.typeOfHit == MovingObjectType.BLOCK)
		{
			if (!worldObj.isRemote)
			{
				TFItems.moonwormQueen.onItemUse(null, (EntityPlayer)this.getThrower(), this.worldObj, mop.blockX, mop.blockY, mop.blockZ, mop.sideHit, 0, 0, 0);
			}
			else
			{
				//particles
				
			}
		}
		
        if (mop.entityHit != null)
        {
            mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0);
        }

        for (int var3 = 0; var3 < 8; ++var3)
        {
            this.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(TFBlocks.moonworm) + "_0", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
	}


}
