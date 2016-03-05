package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.client.model.ModelTFCubeOfAnnihilation;
import twilightforest.client.model.ModelTFHydraMortar;
import twilightforest.entity.EntityTFBlockGoblin;
import twilightforest.entity.EntityTFChainBlock;

public class RenderTFCubeOfAnnihilation extends Render {

	private ModelBase model;
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "cubeofannihilation.png");

	public RenderTFCubeOfAnnihilation() {
        this.model = new ModelTFCubeOfAnnihilation();
	}

    /**
     * The render method used in RenderBoat that renders the boat model.
     */
    public void renderSpikeBlock(Entity entity, double x, double y, double z, float par8, float time)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);

        this.bindEntityTexture(entity);

        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        
        // rotate
        GL11.glRotatef(MathHelper.wrapAngleTo180_float(((float)x + (float)z + entity.ticksExisted + time) * 11F), 0, 1, 0);
        

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        GL11.glTranslatef(0F, -0.5F, 0F);
        this.model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, time, 0.0625F / 2F);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);

        
        GL11.glPopMatrix();
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
         this.renderSpikeBlock(par1Entity, par2, par4, par6, par8, par9);

    }

    
	/**
	 * Return our specific texture
	 */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return textureLoc;
    }
}
