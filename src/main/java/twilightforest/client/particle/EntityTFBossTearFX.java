package twilightforest.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityTFBossTearFX extends EntityFX
{
    public EntityTFBossTearFX(World par1World, double par2, double par4, double par6, Item par8Item)
    {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
        this.setParticleIcon(par8Item.getIconFromDamage(0));
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.particleGravity = Blocks.snow.blockParticleGravity * 2F;
        this.particleScale = 16.0F;
        
        this.particleMaxAge = 20 + rand.nextInt(40);
    }

    public EntityTFBossTearFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, Item par14Item)
    {
        this(par1World, par2, par4, par6, par14Item);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;
        this.motionX += par8;
        this.motionY += par10;
        this.motionZ += par12;
    }

    public int getFXLayer()
    {
        return 2;
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
	    super.onUpdate();    
    	
        if (this.onGround && rand.nextBoolean())
        {
        	//worldObj.playSoundEffect(this.posX, this.posY + 1D, this.posZ, "random.fizz", 2.0F, 2.0F);
            //worldObj.spawnParticle("lava", this.posX, this.posY, this.posZ, 0, 0, 0);
			//worldObj.playAuxSFXAtEntity(null, 2001, (int)this.posX, (int)this.posY + 1, (int)this.posZ, 0);
    		//this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 2002, (int)this.posX, (int)this.posY, (int)this.posZ, 4037);
        	
        	worldObj.playSound(this.posX, this.posY + 1D, this.posZ, "random.glass", 0.5F, 1.0F, false);

    		for (int var1 = 0; var1 < 50; ++var1)
    		{
    		    double gaussX = rand.nextGaussian() * 0.1D;
    		    double gaussY = rand.nextGaussian() * 0.2D;
    		    double gaussZ = rand.nextGaussian() * 0.1D;
    		    Item popItem = Items.ghast_tear;
    		    
    		    
    		    worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(popItem), this.posX + rand.nextFloat() - rand.nextFloat(), this.posY + 0.5F, this.posZ + rand.nextFloat(), gaussX, gaussY, gaussZ);
    		}
            this.setDead();
        }
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        float f6 = ((float)this.particleTextureIndexX) / 16.0F;
        float f7 = f6 + 0.015609375F * 4F;
        float f8 = ((float)this.particleTextureIndexY) / 16.0F;
        float f9 = f8 + 0.015609375F * 4F;
        float f10 = 0.1F * this.particleScale;

        if (this.particleIcon != null)
        {
            f6 = this.particleIcon.getInterpolatedU(0);
            f7 = this.particleIcon.getInterpolatedU(16.0F);
            f8 = this.particleIcon.getInterpolatedV(0);
            f9 = this.particleIcon.getInterpolatedV(16);
        }

        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
        float f14 = 1.0F;
        par1Tessellator.setColorOpaque_F(f14 * this.particleRed, f14 * this.particleGreen, f14 * this.particleBlue);
        par1Tessellator.addVertexWithUV((double)(f11 - par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 - par5 * f10 - par7 * f10), (double)f6, (double)f9);
        par1Tessellator.addVertexWithUV((double)(f11 - par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 - par5 * f10 + par7 * f10), (double)f6, (double)f8);
        par1Tessellator.addVertexWithUV((double)(f11 + par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 + par5 * f10 + par7 * f10), (double)f7, (double)f8);
        par1Tessellator.addVertexWithUV((double)(f11 + par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 + par5 * f10 - par7 * f10), (double)f7, (double)f9);
    }

}
