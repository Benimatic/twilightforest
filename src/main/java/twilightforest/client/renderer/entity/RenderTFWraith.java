package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFWraith;
import twilightforest.entity.EntityTFWraith;

import javax.annotation.Nonnull;

public class RenderTFWraith<T extends EntityTFWraith, M extends ModelTFWraith<T>> extends BipedRenderer<T, M> {

	private static final ResourceLocation textureWraith = TwilightForestMod.getModelTexture("ghost.png");
	private static final ResourceLocation textureCrown  = TwilightForestMod.getModelTexture("ghost-crown.png");

	public RenderTFWraith(EntityRendererManager manager, M modelbiped, float shadowSize) {
		super(manager, modelbiped, shadowSize);
		addLayer(new LayerWraith());
	}

	@Override
	public ResourceLocation getEntityTexture(@Nonnull T wraith) {
		return textureCrown;
	}

	class LayerWraith extends LayerRenderer<T, M> {

		public LayerWraith(IEntityRenderer<T, M> renderer) {
			super(renderer);
		}

		@Override
		public void render(MatrixStack stack, IRenderTypeBuffer buffer, int i, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
			RenderTFWraith.this.bindTexture(textureWraith);
			GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			RenderTFWraith.this.mainModel.render(wraith, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
		}
//
//		@Override
//		public boolean shouldCombineTextures() {
//			return false;
//		}
	}
}
