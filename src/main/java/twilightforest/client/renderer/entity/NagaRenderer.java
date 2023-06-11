package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.NagaModel;
import twilightforest.entity.boss.Naga;

public class NagaRenderer<M extends NagaModel<Naga>> extends MobRenderer<Naga, M> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("nagahead.png");

	public NagaRenderer(EntityRendererProvider.Context manager, M modelbase, float shadowSize) {
		super(manager, modelbase, shadowSize);
		this.addLayer(new NagaEyelidsLayer<>(this));
	}

	@Override
	protected void scale(Naga entity, PoseStack stack, float p_225620_3_) {
		super.scale(entity, stack, p_225620_3_);
		//make size adjustment
		stack.translate(0.0F, 1.75F, 0.0F);
		stack.scale(2.0F, 2.0F, 2.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(Naga entity) {
		return textureLoc;
	}

	public static class NagaEyelidsLayer<T extends Naga, M extends NagaModel<T>> extends RenderLayer<T, M> {
		private static final ResourceLocation textureLocDazed = TwilightForestMod.getModelTexture("nagahead_dazed.png");

		public NagaEyelidsLayer(RenderLayerParent<T, M> renderer) {
			super(renderer);
		}

		@Override
		public void render(PoseStack stack, MultiBufferSource buffer, int i, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
			if(entitylivingbaseIn.isDazed()) {
				VertexConsumer vertex = buffer.getBuffer(RenderType.entityCutoutNoCull(textureLocDazed));
				this.getParentModel().renderToBuffer(stack, vertex, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			}
		}
	}
}
