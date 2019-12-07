package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFTowerBroodling;

public class RenderTFTowerBroodling<T extends EntityTFTowerBroodling> extends SpiderRenderer<T> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("towerbroodling.png");

	public RenderTFTowerBroodling(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}

	@Override
	protected void preRenderCallback(T entity, float partialTicks) {
		float scale = 0.7F;
		GlStateManager.scalef(scale, scale, scale);
	}
}
