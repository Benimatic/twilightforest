package twilightforest.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityTFGhastTrapFX extends EntityFX
{
    float reddustParticleScale;
	private double originX;
	private double originY;
	private double originZ;

    public EntityTFGhastTrapFX(World par1World, double par2, double par4, double par6, double par8, double par9, double par10)
    {
        this(par1World, par2, par4, par6, 3.0F, par8, par9, par10);
    }

    public EntityTFGhastTrapFX(World par1World, double x, double y, double z, float scale, double mx, double my, double mz)
    {
        super(par1World, x + mx, y + my, z + mz, mx, my, mz);
        this.motionX = mx;
        this.motionY = my;
        this.motionZ = mz;
        
        this.originX = x;
        this.originY = y;
        this.originZ = z;

        float f4 = (float)(new org.bogdang.modifications.random.XSTR()).nextFloat() * 0.4F;// + 0.6F;
        //this.particleRed = ((float)((new org.bogdang.modifications.random.XSTR()).nextFloat() * 0.20000000298023224D) + 0.8F) * f4;
        this.particleGreen = ((float)((new org.bogdang.modifications.random.XSTR()).nextFloat() * 0.20000000298023224D) + 0.8F) * f4;
        this.particleBlue = ((float)((new org.bogdang.modifications.random.XSTR()).nextFloat() * 0.20000000298023224D) + 0.8F) * f4;
        
        this.particleRed  = 1.0F;
        
        this.particleScale *= 0.75F;
        this.particleScale *= scale;
        this.reddustParticleScale = this.particleScale;
        this.particleMaxAge = (int)(10.0D / ((new org.bogdang.modifications.random.XSTR()).nextFloat() * 0.8D + 0.2D));
        //this.particleMaxAge = (int)((float)this.particleMaxAge * scale);
        
        this.noClip = false;
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        float f6 = ((float)this.particleAge + par2) / (float)this.particleMaxAge * 32.0F;

        if (f6 < 0.0F)
        {
            f6 = 0.0F;
        }

        if (f6 > 1.0F)
        {
            f6 = 1.0F;
        }

        this.particleScale = this.reddustParticleScale * f6;
        super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
    }

//    /**
//     * Called to update the entity's position/logic.
//     */
//    public void onUpdate()
//    {
//        this.prevPosX = this.posX;
//        this.prevPosY = this.posY;
//        this.prevPosZ = this.posZ;
//
//        if (this.particleAge++ >= this.particleMaxAge)
//        {
//            this.setDead();
//        }
//
//        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
//        this.moveEntity(this.motionX, this.motionY, this.motionZ);
//
//        if (this.posY == this.prevPosY)
//        {
//            this.motionX *= 1.1D;
//            this.motionZ *= 1.1D;
//        }
//
//        this.motionX *= 0.9599999785423279D;
//        this.motionY *= 0.9599999785423279D;
//        this.motionZ *= 0.9599999785423279D;
//
//        if (this.onGround)
//        {
//            this.motionX *= 0.699999988079071D;
//            this.motionZ *= 0.699999988079071D;
//        }
//    }
    
    
    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
    	
      this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        float proportion = (float)this.particleAge / (float)this.particleMaxAge;
        proportion = 1.0F - proportion;
//        float antiProportion = 1.0F - proportion;
//        antiProportion *= antiProportion;
//        antiProportion *= antiProportion;
        this.posX = this.originX + this.motionX * (double)proportion;
        this.posY = this.originY + this.motionY * (double)proportion;// - (double)(antiProportion * 1.2F);
        this.posZ = this.originZ + this.motionZ * (double)proportion;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }
    }
}
