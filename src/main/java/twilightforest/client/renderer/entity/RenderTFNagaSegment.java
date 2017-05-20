package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.entity.boss.EntityTFNagaSegment;

public class RenderTFNagaSegment extends Render<EntityTFNagaSegment> {
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "nagasegment.png");
    private final ModelBase model;

	public RenderTFNagaSegment(RenderManager manager, ModelBase model) {
	    super(manager);
	    this.model = model;
	}

    /**
     * The render method used in RenderBoat that renders the boat model.
     */
    public void renderMe(Entity par1Entity, double par2, double par4, double par6, float par8, float time)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glRotatef(180 - MathHelper.wrapDegrees(par8), 0.0F, 1.0F, 0.0F);
        
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

    @Override
    public void doRender(EntityTFNagaSegment par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderMe(par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTFNagaSegment par1Entity)
    {
        return textureLoc;
    }
}
