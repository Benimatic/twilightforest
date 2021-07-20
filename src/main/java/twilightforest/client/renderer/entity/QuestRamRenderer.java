package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.QuestRamModel;
import twilightforest.entity.passive.QuestRamEntity;

public class QuestRamRenderer extends MobRenderer<QuestRamEntity, QuestRamModel> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("questram.png");
	private static final ResourceLocation textureLocLines = TwilightForestMod.getModelTexture("questram_lines.png");

	public QuestRamRenderer(EntityRendererManager manager, QuestRamModel model) {
		super(manager, model, 1.0F);
		addLayer(new LayerGlowingLines(this));
	}

	@Override
	public ResourceLocation getEntityTexture(QuestRamEntity entity) {
		return textureLoc;
	}

	class LayerGlowingLines extends LayerRenderer<QuestRamEntity, QuestRamModel> {

		public LayerGlowingLines(IEntityRenderer<QuestRamEntity, QuestRamModel> renderer) {
			super(renderer);
		}

		@Override
		public void render(MatrixStack stack, IRenderTypeBuffer buffer, int i, QuestRamEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
			IVertexBuilder builder = buffer.getBuffer(RenderType.getEntityTranslucent(textureLocLines));
			stack.scale(1.025f, 1.025f, 1.025f);
			QuestRamRenderer.this.getEntityModel().render(stack, builder, 0xF000F0, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
