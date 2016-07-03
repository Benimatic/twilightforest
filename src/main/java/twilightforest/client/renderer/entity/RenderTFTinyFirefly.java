package twilightforest.client.renderer.entity;

import java.nio.FloatBuffer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.client.model.ModelTFTinyFirefly;
import twilightforest.entity.passive.EntityTFMobileFirefly;
import twilightforest.entity.passive.EntityTFTinyFirefly;




public class RenderTFTinyFirefly extends Render {

	ModelTFTinyFirefly fireflyModel;
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "firefly-tiny.png");
	
    public RenderTFTinyFirefly()
    {
    	fireflyModel = new ModelTFTinyFirefly();
    }


	/**
	 * Draw a cute firefly!
	 * 
	 * @param firefly
	 * @param x
	 * @param y
	 * @param z
	 * @param f
	 * @param f1
	 */
	public void doRenderTinyFirefly(EntityTFTinyFirefly firefly, double x, double y, double z, float brightness, float size) {

		GL11.glPushMatrix();

		GL11.glTranslatef((float)x, (float)y + 0.5F, (float)z);
		
		// undo rotations so we can draw a billboarded firefly
		FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
		
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);

		for(int i=0; i<3; i++ ) {
			for(int j=0; j<3; j++ ) {
				int index = i * 4 + j;
				if ( i==j ) {
					modelview.put(index, 1.0f);
				} else {
					modelview.put(index, 0.0f);
				}
			}
		}
		
		GL11.glLoadMatrix(modelview);
		
		//loadTexture(TwilightForestMod.MODEL_DIR + "firefly-tiny.png");
        this.renderManager.renderEngine.bindTexture(textureLoc);

		GL11.glColorMask(true, true, true, true);

		// render the firefly glow
		GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

		GL11.glDisable(GL11.GL_LIGHTING);

        
//		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, brightness);
		fireflyModel.glow1.render(0.0625f * size);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();		
	}
	


	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		
		if (entity instanceof EntityTFTinyFirefly)
		{
			EntityTFTinyFirefly firefly = (EntityTFTinyFirefly)entity;

			doRenderTinyFirefly(firefly, d, d1, d2, firefly.getGlowBrightness(), firefly.glowSize);

		}
		else if (entity instanceof EntityTFMobileFirefly)
		{
			EntityTFMobileFirefly firefly = (EntityTFMobileFirefly)entity;

			
			doRenderTinyFirefly(null, d, d1, d2, firefly.getGlowBrightness(), 1.0F);
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
