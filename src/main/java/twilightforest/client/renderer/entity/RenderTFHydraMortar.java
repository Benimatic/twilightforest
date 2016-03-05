package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.client.model.ModelTFHydraMortar;
import twilightforest.entity.boss.EntityTFHydraMortar;

public class RenderTFHydraMortar extends Render {
	
    private ModelTFHydraMortar mortarModel;
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "hydramortar.png");


    public RenderTFHydraMortar()
    {
        mortarModel = new ModelTFHydraMortar();
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

		EntityTFHydraMortar mortar = (EntityTFHydraMortar)var1;

        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        float var10;

        if ((float)mortar.fuse - partialTick + 1.0F < 10.0F)
        {
            var10 = 1.0F - ((float)mortar.fuse - partialTick + 1.0F) / 10.0F;

            if (var10 < 0.0F)
            {
                var10 = 0.0F;
            }

            if (var10 > 1.0F)
            {
                var10 = 1.0F;
            }

            var10 *= var10;
            var10 *= var10;
            float var11 = 1.0F + var10 * 0.3F;
            GL11.glScalef(var11, var11, var11);
        }

        var10 = (1.0F - ((float)mortar.fuse - partialTick + 1.0F) / 100.0F) * 0.8F;
        //this.loadTexture(TwilightForestMod.MODEL_DIR + "hydramortar.png");
        this.bindTexture(textureLoc);
        
        mortarModel.render(0.075F);

        if (mortar.fuse / 5 % 2 == 0)
        {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, var10);
            
            mortarModel.render(0.075F);
            
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }

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
