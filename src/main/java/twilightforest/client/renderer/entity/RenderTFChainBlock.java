package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFChainBlock;

public class RenderTFChainBlock extends Render<EntityTFChainBlock> {
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "blockgoblin.png");
    private final ModelBase model;

	public RenderTFChainBlock(RenderManager manager, ModelBase modelTFSpikeBlock) {
	    super(manager);
		this.model = modelTFSpikeBlock;
	}

    /**
     * The render method used in RenderBoat that renders the boat model.
     */
    public void renderSpikeBlock(EntityTFChainBlock par1Entity, double par2, double par4, double par6, float par8, float time)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);

        this.bindEntityTexture(par1Entity);

        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        
        
        GL11.glRotatef(MathHelper.wrapDegrees((float)par4), 1, 0, 1);
        GL11.glRotatef(MathHelper.wrapDegrees(((float)par2 + (float)par6) * 11F), 0, 1, 0);
//        GL11.glRotatef(MathHelper.wrapDegrees((float)par8), 0, 0, 1);
        

        
        this.model.render(par1Entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(EntityTFChainBlock chainBlock, double par2, double par4, double par6, float par8, float par9)
    {
	    super.doRender(chainBlock, par2, par4, par6, par8, par9);

	    this.renderSpikeBlock(chainBlock, par2, par4, par6, par8, par9);

	    renderManager.renderEntityStatic(chainBlock.chain1, par9, false);
	    renderManager.renderEntityStatic(chainBlock.chain2, par9, false);
	    renderManager.renderEntityStatic(chainBlock.chain3, par9, false);
	    renderManager.renderEntityStatic(chainBlock.chain4, par9, false);
	    renderManager.renderEntityStatic(chainBlock.chain5, par9, false);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTFChainBlock par1Entity)
    {
        return textureLoc;
    }
}
