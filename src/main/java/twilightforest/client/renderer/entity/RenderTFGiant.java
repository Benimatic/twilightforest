package twilightforest.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderTFGiant extends RenderBiped {

	private ResourceLocation textureLoc;

	public RenderTFGiant() {
		super(new ModelBiped(), 0.625F);
		
		this.textureLoc = new ResourceLocation("textures/entity/steve.png");
	}

	/**
	 * Return our specific texture
	 */
    @Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
    	if (Minecraft.getMinecraft().thePlayer.getLocationSkin() != null) {
    		return Minecraft.getMinecraft().thePlayer.getLocationSkin();
    	} else {
    		return textureLoc;
    	}
    }
    
    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    @Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	float scale = 4.0F;
        GL11.glScalef(scale, scale, scale);
    }
}
