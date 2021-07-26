package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.entity.HedgeSpiderEntity;

public class HedgeSpiderRenderer<T extends HedgeSpiderEntity> extends SpiderRenderer<T> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("hedgespider.png");

	public HedgeSpiderRenderer(EntityRendererProvider.Context manager) {
		super(manager);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return textureLoc;
	}
}
