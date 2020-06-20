package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFTinyBird;
import twilightforest.entity.passive.EntityTFTinyBird;

public class RenderTFTinyBird extends RenderTFBird<EntityTFTinyBird, ModelTFTinyBird> {

	private static final ResourceLocation textureLocSparrow  = TwilightForestMod.getModelTexture("tinybirdbrown.png");
	private static final ResourceLocation textureLocFinch    = TwilightForestMod.getModelTexture("tinybirdgold.png");
	private static final ResourceLocation textureLocCardinal = TwilightForestMod.getModelTexture("tinybirdred.png");
	private static final ResourceLocation textureLocBluebird = TwilightForestMod.getModelTexture("tinybirdblue.png");

	public RenderTFTinyBird(EntityRendererManager manager, ModelTFTinyBird model, float shadowSize) {
		super(manager, model, shadowSize, "");
	}

	@Override
	public ResourceLocation getEntityTexture(EntityTFTinyBird entity) {
		switch (entity.getBirdType()) {
			default:
			case 0:
				return textureLocSparrow;
			case 1:
				return textureLocBluebird;
			case 2:
				return textureLocCardinal;
			case 3:
				return textureLocFinch;
		}
	}
}
