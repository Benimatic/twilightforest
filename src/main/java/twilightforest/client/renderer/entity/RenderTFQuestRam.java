package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFQuestRam;
import twilightforest.entity.passive.EntityTFQuestRam;

public class RenderTFQuestRam<T extends EntityTFQuestRam, M extends ModelTFQuestRam<T>> extends LivingRenderer<T, M> {

	private static final ResourceLocation textureLoc      = TwilightForestMod.getModelTexture("questram.png");
	private static final ResourceLocation textureLocLines = TwilightForestMod.getModelTexture("questram_lines.png");

	public RenderTFQuestRam(EntityRendererManager manager) {
		super(manager, new ModelTFQuestRam(), 1.0F);
		addLayer(new LayerGlowingLines(this));
	}

	@Override
	public ResourceLocation getEntityTexture(EntityTFQuestRam entity) {
		return textureLoc;
	}

	// todo verify / cleanup gl state?
	class LayerGlowingLines extends LayerRenderer<T, M> {

		public LayerGlowingLines(IEntityRenderer<T, M> renderer) {
			super(renderer);
		}

		@Override
		public void render(MatrixStack stack, IRenderTypeBuffer buffer, int i, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
			RenderTFQuestRam.this.bindTexture(textureLocLines);
			float var4 = 1.0F;
			GlStateManager.enableBlend();
			RenderSystem.disableAlphaTest();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			stack.scale(1.025f, 1.025f, 1.025f);
			char var5 = 61680;
			int var6 = var5 % 65536;
			int var7 = var5 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) var6 / 1.0F, (float) var7 / 1.0F);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, var4);
			RenderTFQuestRam.this.getEntityModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
//
//		@Override
//		public boolean shouldCombineTextures() {
//			return false;
//		}
	}
}
