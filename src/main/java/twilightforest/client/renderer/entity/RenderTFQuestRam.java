package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFQuestRam;
import twilightforest.entity.passive.EntityTFQuestRam;

public class RenderTFQuestRam extends MobRenderer<EntityTFQuestRam, ModelTFQuestRam> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("questram.png");
	private static final ResourceLocation textureLocLines = TwilightForestMod.getModelTexture("questram_lines.png");

	public RenderTFQuestRam(EntityRendererManager manager, ModelTFQuestRam model) {
		super(manager, model, 1.0F);
		addLayer(new LayerGlowingLines(this));
	}

	@Override
	public ResourceLocation getEntityTexture(EntityTFQuestRam entity) {
		return textureLoc;
	}

	class LayerGlowingLines extends LayerRenderer<EntityTFQuestRam, ModelTFQuestRam> {

		public LayerGlowingLines(IEntityRenderer<EntityTFQuestRam, ModelTFQuestRam> renderer) {
			super(renderer);
		}

		@Override
		public void render(MatrixStack stack, IRenderTypeBuffer buffer, int i, EntityTFQuestRam entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
			IVertexBuilder builder = buffer.getBuffer(RenderType.getEntityTranslucent(textureLocLines));
			stack.scale(1.025f, 1.025f, 1.025f);
			RenderTFQuestRam.this.getEntityModel().render(stack, builder, 0xF000F0, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
