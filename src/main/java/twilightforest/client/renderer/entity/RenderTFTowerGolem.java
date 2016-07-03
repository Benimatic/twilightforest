package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFTowerGolem;

public class RenderTFTowerGolem extends RenderLiving 
{

    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "carminitegolem.png");

	public RenderTFTowerGolem(ModelBase par1ModelBase, float par2) 
	{
		super(par1ModelBase, par2);
	}

	
    @Override
    protected void rotateCorpse(EntityLivingBase par1EntityLiving, float par2, float par3, float par4)
    {
        this.rotateTowerGolem((EntityTFTowerGolem)par1EntityLiving, par2, par3, par4);
    }


	private void rotateTowerGolem(EntityTFTowerGolem par1EntityLiving, float par2, float par3, float par4) 
	{
        super.rotateCorpse(par1EntityLiving, par2, par3, par4);

        if ((double)par1EntityLiving.limbSwingAmount >= 0.01D)
        {
            float var5 = 13.0F;
            float var6 = par1EntityLiving.limbSwing - par1EntityLiving.limbSwingAmount * (1.0F - par4) + 6.0F;
            float var7 = (Math.abs(var6 % var5 - var5 * 0.5F) - var5 * 0.25F) / (var5 * 0.25F);
            GL11.glRotatef(6.5F * var7, 0.0F, 0.0F, 1.0F);
        }
	}
	
	/**
	 * Return our specific texture
	 */
    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return textureLoc;
    }

}
