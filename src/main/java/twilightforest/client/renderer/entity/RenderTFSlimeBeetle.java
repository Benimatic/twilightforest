package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFSlimeBeetle;
import twilightforest.entity.EntityTFSlimeBeetle;

public class RenderTFSlimeBeetle<T extends EntityTFSlimeBeetle, M extends ModelTFSlimeBeetle<T>> extends LivingRenderer<T, M> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("slimebeetle.png");

	public RenderTFSlimeBeetle(EntityRendererManager manager, M par1ModelBase, float shadowSize) {
		super(manager, par1ModelBase, shadowSize);
		addLayer(new LayerInner());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTFSlimeBeetle entity) {
		return textureLoc;
	}

	class LayerInner extends LayerRenderer<T, M> {
		private final ModelBase innerModel = new ModelTFSlimeBeetle(true);

		@Override
		public void render(T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
			if (!entity.isInvisible()) {
				GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.enableNormalize();
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
				this.innerModel.setModelAttributes(RenderTFSlimeBeetle.this.getMainModel());
				this.innerModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
				GlStateManager.disableBlend();
				GlStateManager.disableNormalize();
			}
		}

		@Override
		public boolean shouldCombineTextures() {
			return true;
		}
	}
}
