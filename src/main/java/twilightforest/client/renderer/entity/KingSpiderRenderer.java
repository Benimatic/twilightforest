package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.entity.KingSpiderEntity;

public class KingSpiderRenderer extends SpiderRenderer<KingSpiderEntity> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("kingspider.png");

	public KingSpiderRenderer(EntityRenderDispatcher manager) {
		super(manager);
	}

	@Override
	public ResourceLocation getTextureLocation(KingSpiderEntity entity) {
		return textureLoc;
	}

	@Override
	protected void scale(KingSpiderEntity entity, PoseStack stack, float partialTicks) {
		float scale = 1.9F;
		stack.scale(scale, scale, scale);
	}
}
