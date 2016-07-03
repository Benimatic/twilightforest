package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.client.model.ModelTFKnightPhantom2;
import twilightforest.entity.boss.EntityTFKnightPhantom;

public class RenderTFKnightPhantom extends RenderBiped {

    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "phantomskeleton.png");

	
	public RenderTFKnightPhantom(ModelTFKnightPhantom2 modelTFKnightPhantom2, float f) {
		super(modelTFKnightPhantom2, f);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return textureLoc;
	}
	
    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    @Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        float scale = ((EntityTFKnightPhantom)par1EntityLivingBase).isChargingAtPlayer() ? 1.8F : 1.2F;
		GL11.glScalef(scale, scale, scale);
    }

}
