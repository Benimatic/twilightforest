package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFKingSpider;

public class RenderTFKingSpider<T extends EntityTFKingSpider> extends SpiderRenderer<T> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("kingspider.png");

	public RenderTFKingSpider(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}

	@Override
	protected void preRenderCallback(T entity, float partialTicks) {
		float scale = 1.9F;
		GlStateManager.scalef(scale, scale, scale);
	}
}
