package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.client.model.ModelTFSlimeBeetle;
import twilightforest.entity.EntityTFSlimeBeetle;

public class RenderTFSlimeBeetle extends RenderLiving<EntityTFSlimeBeetle> {
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "slimebeetle.png");
    private final ModelBase renderModel = new ModelTFSlimeBeetle(true);

	public RenderTFSlimeBeetle(RenderManager manager, ModelBase par1ModelBase, float shadowSize) {
		super(manager, par1ModelBase, shadowSize);
		this.shadowSize = 1.1f;
	}

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLivingBase par1EntityLiving, int par2, float par3)
    {
        return this.shouldSlimeRenderPass((EntityLivingBase)par1EntityLiving, par2, par3);
    }


    /**
     * Determines whether Slime Render should pass or not.
     */
    protected int shouldSlimeRenderPass(EntityLivingBase par1EntitySlime, int par2, float par3)
    {
        if (par1EntitySlime.isInvisible())
        {
            return 0;
        }
        else if (par2 == 0)
        {
            this.setRenderPassModel(this.renderModel);
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            return 1;
        }
        else
        {
            if (par2 == 1)
            {
                GlStateManager.disableBlend();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            }

            return -1;
        }
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityTFSlimeBeetle par1Entity)
    {
        return textureLoc;
    }
}
