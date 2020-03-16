package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;
import twilightforest.entity.boss.EntityTFNaga;

@OnlyIn(Dist.CLIENT)
public class NagaEyelidsLayer<T extends EntityTFNaga> extends LayerRenderer<T> {
	private static final ResourceLocation textureLocDazed = TwilightForestMod.getModelTexture("nagahead_dazed.png");
	private final RenderTFNaga nagaRenderer;

	public NagaEyelidsLayer(RenderTFNaga nagaRenderer) {
		this.nagaRenderer = nagaRenderer;
	}

	@Override
	public void render(MatrixStack stack, IRenderTypeBuffer buffer, int i, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if(entitylivingbaseIn.isDazed()) {
			this.nagaRenderer.bindTexture(textureLocDazed);
			this.nagaRenderer.getEntityModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}
//
//	@Override
//	public boolean shouldCombineTextures() {
//		return true;
//	}
}
