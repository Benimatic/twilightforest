package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFNaga;
import twilightforest.entity.boss.EntityTFNaga;

@OnlyIn(Dist.CLIENT)
public class NagaEyelidsLayer<T extends EntityTFNaga, M extends ModelTFNaga<T>> extends LayerRenderer<T, M> {
	private static final ResourceLocation textureLocDazed = TwilightForestMod.getModelTexture("nagahead_dazed.png");

	public NagaEyelidsLayer(IEntityRenderer<T, M> renderer) {
		super(renderer);
	}

//	public NagaEyelidsLayer(RenderTFNaga nagaRenderer) {
//		this.nagaRenderer = nagaRenderer;
//	}

	@Override
	public void render(MatrixStack stack, IRenderTypeBuffer buffer, int i, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if(entitylivingbaseIn.isDazed()) {
			this.bindTexture(textureLocDazed);
			this.getEntityModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}
//
//	@Override
//	public boolean shouldCombineTextures() {
//		return true;
//	}
}
