package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.client.model.ModelTFCubeOfAnnihilation;
import twilightforest.entity.EntityTFSpikeBlock;

public class RenderTFCubeOfAnnihilation extends Render<EntityTFSpikeBlock> {
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "cubeofannihilation.png");
    private final ModelBase model = new ModelTFCubeOfAnnihilation();

	public RenderTFCubeOfAnnihilation(RenderManager manager) {
        super(manager);
	}

    /**
     * The render method used in RenderBoat that renders the boat model.
     */
    public void renderSpikeBlock(EntityTFSpikeBlock entity, double x, double y, double z, float par8, float time)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);

        this.bindEntityTexture(entity);

        GL11.glScalef(-1.0F, -1.0F, 1.0F);

        // rotate
        GL11.glRotatef(MathHelper.wrapDegrees(((float)x + (float)z + entity.ticksExisted + time) * 11F), 0, 1, 0);


        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glTranslatef(0F, -0.5F, 0F);
        this.model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, time, 0.0625F / 2F);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);


        GL11.glPopMatrix();
    }

    @Override
    public void doRender(EntityTFSpikeBlock par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        super.doRender(par1Entity, par2, par4, par6, par8, par9);
        this.renderSpikeBlock(par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTFSpikeBlock par1Entity)
    {
        return textureLoc;
    }
}
