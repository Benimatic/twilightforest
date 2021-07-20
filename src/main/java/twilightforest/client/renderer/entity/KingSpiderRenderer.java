package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.entity.KingSpiderEntity;

public class KingSpiderRenderer extends SpiderRenderer<KingSpiderEntity> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("kingspider.png");

	public KingSpiderRenderer(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	public ResourceLocation getEntityTexture(KingSpiderEntity entity) {
		return textureLoc;
	}

	@Override
	protected void preRenderCallback(KingSpiderEntity entity, MatrixStack stack, float partialTicks) {
		float scale = 1.9F;
		stack.scale(scale, scale, scale);
	}
}
