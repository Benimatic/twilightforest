package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import twilightforest.TwilightForestMod;
import twilightforest.client.model.ModelTFQuestRam;
import twilightforest.entity.passive.EntityTFQuestRam;

public class RenderTFQuestRam extends RenderLiving<EntityTFQuestRam> {
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "questram.png");
    private static final ResourceLocation textureLocLines = new ResourceLocation(TwilightForestMod.MODEL_DIR + "questram_lines.png");

	public RenderTFQuestRam(RenderManager manager) {
		super(manager, new ModelTFQuestRam(), 1.0F);
        this.setRenderPassModel(new ModelTFQuestRam());
	}


    /**
     * Sets the ram's glowing lines
     */
    protected int setQuestRamLineBrightness(EntityTFQuestRam par1EntityQuestRam, int par2, float par3)
    {
        if (par2 != 0)
        {
            return -1;
        }
        else
        {
            this.bindTexture(textureLocLines);
            float var4 = 1.0F;
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GlStateManager.scale(1.025f, 1.025f, 1.025f);
            char var5 = 61680;
            int var6 = var5 % 65536;
            int var7 = var5 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var6 / 1.0F, (float)var7 / 1.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, var4);
            return 1;
        }
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLivingBase par1EntityLiving, int par2, float par3)
    {
        return this.setQuestRamLineBrightness((EntityTFQuestRam)par1EntityLiving, par2, par3);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTFQuestRam par1Entity)
    {
        return textureLoc;
    }
	
}
