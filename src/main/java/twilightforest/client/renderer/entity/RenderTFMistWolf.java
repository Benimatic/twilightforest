package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;

public class RenderTFMistWolf extends RenderWolf {

    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "mistwolf.png");

	public RenderTFMistWolf(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3) {
		super(par1ModelBase, par2ModelBase, par3);
	}

	
    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2)
    {
    	float wolfScale = 1.9F;
        GL11.glScalef(wolfScale, wolfScale, wolfScale);
        
        GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
        //GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        //GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_ALPHA, GL11.GL_DST_ALPHA);
        
        float misty = par1EntityLiving.getBrightness(0F) * 3F + 0.25F;
        misty = Math.min(1F, misty);

        float smoky = par1EntityLiving.getBrightness(0F) * 2F + 0.6F;

        //System.out.println("Misty value is " + misty);
        
        GL11.glColor4f(misty, misty, misty, smoky);

    }
    
    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLivingBase par1EntityLiving, int par2, float par3)
    {
//        GL11.glFogf(GL11.GL_FOG_START, 1.0f);	
//        GL11.glFogf(GL11.GL_FOG_END, 5.0f);
        

    	
        return -1;
    }
    
	/**
	 * Return our specific texture
	 */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return textureLoc;
    }
    
}
