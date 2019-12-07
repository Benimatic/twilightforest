package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFTinyBird;
import twilightforest.entity.passive.EntityTFTinyBird;

public class RenderTFTinyBird<T extends EntityTFTinyBird, M extends ModelTFTinyBird<T>> extends RenderTFBird<T, M> {

	private static final ResourceLocation textureLocSparrow  = TwilightForestMod.getModelTexture("tinybirdbrown.png");
	private static final ResourceLocation textureLocFinch    = TwilightForestMod.getModelTexture("tinybirdgold.png");
	private static final ResourceLocation textureLocCardinal = TwilightForestMod.getModelTexture("tinybirdred.png");
	private static final ResourceLocation textureLocBluebird = TwilightForestMod.getModelTexture("tinybirdblue.png");

	public RenderTFTinyBird(EntityRendererManager manager, M model, float shadowSize) {
		super(manager, model, shadowSize, "");
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		if (entity instanceof EntityTFTinyBird) {
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

		// fallback
		return textureLocSparrow;
	}
}
