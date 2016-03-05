package twilightforest.client.renderer.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import twilightforest.client.model.ModelTFIceExploder;

public class RenderTFIceExploder extends RenderTFBiped {

	public RenderTFIceExploder() {
		super(new ModelTFIceExploder(), 1.0F, "iceexploder.png");
	}

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float partialTick)
    {
		float bounce = par1EntityLivingBase.ticksExisted + partialTick;
		
		GL11.glTranslatef(0F, MathHelper.sin((bounce) * 0.2F) * 0.15F, 0F);

		// flash

		float f1 = par1EntityLivingBase.deathTime;
		if (f1 > 0) {
			float f2 = 1.0F + MathHelper.sin(f1 * 100.0F) * f1 * 0.01F;

			if (f1 < 0.0F)
			{
				f1 = 0.0F;
			}

			if (f1 > 1.0F)
			{
				f1 = 1.0F;
			}

			f1 *= f1;
			f1 *= f1;
			float f3 = (1.0F + f1 * 0.4F) * f2;
			float f4 = (1.0F + f1 * 0.1F) / f2;
			GL11.glScalef(f3, f4, f3);
		}
    }
    
    protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
    {
        GL11.glRotatef(180.0F - par3, 0.0F, 1.0F, 0.0F);
    }


    /**
     * Returns an ARGB int color back. Args: entityLiving, lightBrightness, partialTickTime
     */
    protected int getColorMultiplier(EntityLivingBase par1EntityLivingBase, float par2, float par3)
    {
    	if (par1EntityLivingBase.deathTime > 0) {
    		float f2 = par1EntityLivingBase.deathTime + par3;

    		if ((int)(f2 / 2) % 2 == 0)
    		{
    			return 0;
    		}
    		else
    		{
    			int i = (int)(f2 * 0.2F * 255.0F);

    			if (i < 0)
    			{
    				i = 0;
    			}

    			if (i > 255)
    			{
    				i = 255;
    			}

    			short short1 = 255;
    			short short2 = 255;
    			short short3 = 255;
    			return i << 24 | short1 << 16 | short2 << 8 | short3;
    		}
    	} else {
    		return 0;
    	}
    }

}


