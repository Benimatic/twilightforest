package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.client.model.ModelTFSnowQueen;
import twilightforest.entity.boss.EntityTFSnowQueen;

public class RenderTFSnowQueen extends RenderBiped {

    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "snowqueen.png");

	public RenderTFSnowQueen() {
		super(new ModelTFSnowQueen(), 0.625F);
	}

	/**
	 * Return our specific texture
	 */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
    	return textureLoc;
    }
    
    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	float scale = 1.2F;
        GL11.glScalef(scale, scale, scale);
    }
    
	/**
	 * Render the queen and shield
	 */
	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {

		EntityTFSnowQueen queen = (EntityTFSnowQueen)entity;
		
        BossStatus.setBossStatus(queen, false);

		super.doRender(entity, d, d1, d2, f, f1);
		
		for (int i = 0; i < queen.iceArray.length; i++) {
			RenderManager.instance.renderEntitySimple(queen.iceArray[i], f1);
		}
	}
}
