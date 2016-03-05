package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.client.model.ModelTFMoonworm;

public class RenderTFMoonwormShot extends Render {

    private ModelTFMoonworm wormModel;
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "moonworm.png");


    public RenderTFMoonwormShot()
    {
        wormModel = new ModelTFMoonworm();
        this.shadowSize = 0.5F;
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
	@Override
	public void doRender(Entity var1, double x, double y, double z, float var8, float partialTick) {

        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(90F, 1F, 0F, 1F);

        
        this.bindTexture(textureLoc);
        
        wormModel.render(0.075F);

        GL11.glPopMatrix();
	}

	/**
	 * Return our specific texture
	 */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return textureLoc;
    }
}
