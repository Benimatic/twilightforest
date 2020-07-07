package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;

public class RenderTFWinterWolf extends WolfRenderer {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("winterwolf.png");

	public RenderTFWinterWolf(EntityRendererManager manager) {
		super(manager);
		this.shadowSize = 1.0F;
	}

	@Override
	protected void preRenderCallback(WolfEntity entity, MatrixStack stack, float partialTicks) {
		float wolfScale = 1.9F;
		stack.scale(wolfScale, wolfScale, wolfScale);

		//The rest of this stuff won't belong here
//        GlStateManager.enableBlend();
//        GlStateManager.disableAlpha();
//        //GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);
//        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        //GlStateManager.blendFunc(GL11.GL_ONE_MINUS_DST_ALPHA, GL11.GL_DST_ALPHA);
//
//        float misty = entity.getBrightness(0F) * 3F + 0.25F;
//        misty = Math.min(1F, misty);
//
//        float smoky = entity.getBrightness(0F) * 2F + 0.6F;
//
//        GlStateManager.color(misty, misty, misty, smoky);
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	protected int shouldRenderPass(LivingEntity par1EntityLiving, int par2, float par3) {
//        GL11.glFogf(GL11.GL_FOG_START, 1.0f);	
//        GL11.glFogf(GL11.GL_FOG_END, 5.0f);

		return -1;
	}

	@Override
	public ResourceLocation getEntityTexture(WolfEntity entity) {
		return textureLoc;
	}
}
