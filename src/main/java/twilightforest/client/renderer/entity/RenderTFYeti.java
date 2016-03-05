package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;

import org.lwjgl.opengl.GL11;

public class RenderTFYeti extends RenderTFBiped {

	public RenderTFYeti(ModelBiped modelBiped, float scale, String textureName) {
		super(modelBiped, scale, textureName);
	}

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	float scale = 1.0F;
        GL11.glScalef(scale, scale, scale);
    }
}
