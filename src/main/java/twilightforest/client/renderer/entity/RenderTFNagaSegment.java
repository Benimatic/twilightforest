package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;

public class RenderTFNagaSegment extends Render {

	private ModelBase model;
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "nagasegment.png");

	public RenderTFNagaSegment(ModelBase model, float f) {
		this.model = model;
	}

    /**
     * The render method used in RenderBoat that renders the boat model.
     */
    public void renderMe(Entity par1Entity, double par2, double par4, double par6, float par8, float time)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glRotatef(180 - MathHelper.wrapAngleTo180_float(par8), 0.0F, 1.0F, 0.0F);
        
        // pitch
        float pitch = par1Entity.prevRotationPitch + (par1Entity.rotationPitch - par1Entity.prevRotationPitch) * time;
        
        GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);


//        float f4 = 0.75F;
//        GL11.glScalef(f4, f4, f4);
//        GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
        //this.loadTexture(par1Entity.getTexture());
        this.bindTexture(textureLoc);

        
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        this.model.render(par1Entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
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
        this.renderMe(par1Entity, par2, par4, par6, par8, par9);
    }

    
	/**
	 * Return our specific texture
	 */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return textureLoc;
    }
}
