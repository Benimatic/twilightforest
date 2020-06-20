package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFBunny;
import twilightforest.entity.passive.EntityTFBunny;

public class RenderTFBunny extends MobRenderer<EntityTFBunny, ModelTFBunny> {

	private final ResourceLocation textureLocDutch = TwilightForestMod.getModelTexture("bunnydutch.png");
	private final ResourceLocation textureLocWhite = TwilightForestMod.getModelTexture("bunnywhite.png");
	private final ResourceLocation textureLocBrown = TwilightForestMod.getModelTexture("bunnybrown.png");

	public RenderTFBunny(EntityRendererManager manager, ModelTFBunny model, float shadowSize) {
		super(manager, model, shadowSize);
	}

	@Override
	public ResourceLocation getEntityTexture(EntityTFBunny entity) {
		switch (entity.getBunnyType()) {
			default:
			case 0:
			case 1:
				return textureLocDutch;
			case 2:
				return textureLocWhite;
			case 3:
				return textureLocBrown;
		}
	}
}
