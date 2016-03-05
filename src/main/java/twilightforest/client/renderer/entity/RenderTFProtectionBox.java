package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.client.model.ModelTFProtectionBox;
import twilightforest.entity.EntityTFProtectionBox;

public class RenderTFProtectionBox extends Render {



    private ModelTFProtectionBox boxModel;
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "protectionbox.png");

	public RenderTFProtectionBox()
    {
        this.boxModel = new ModelTFProtectionBox();
        this.shadowSize = 0.5F;
    }
	
	@Override
	public void doRender(Entity var1, double x, double y, double z, float var8, float partialTick) {
        GL11.glPushMatrix();
        
        
        GL11.glTranslatef((float)x, (float)y, (float)z);
		
        this.bindTexture(textureLoc);
        
        // move texture
        float f1 = (float)var1.ticksExisted + partialTick;
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glLoadIdentity();
        float f2 = f1 * 0.05F;
        float f3 = f1 * 0.05F;
        GL11.glTranslatef(f2, f3, 0.0F);
        GL11.glScalef(1.0f, 1.0f, 1.0f);
        
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
        // enable transparency, go full brightness
    	GL11.glColorMask(true, true, true, true);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        
        float alpha = 1.0F;
        if (((EntityTFProtectionBox)var1).lifeTime < 20) {
        	alpha = ((EntityTFProtectionBox)var1).lifeTime / 20F;
        }
        
		GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);

        boxModel.render(var1, 0F, 0F, 0F, 0F, 0F, 1F / 16F);
        
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glPopMatrix();
	}
	
    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2)
    {
    	float scale = 1.0F;
        GL11.glScalef(scale, scale, scale);
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
