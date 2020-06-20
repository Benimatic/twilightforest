package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFHydra;
import twilightforest.entity.boss.EntityTFHydra;

public class RenderTFHydra extends MobRenderer<EntityTFHydra, ModelTFHydra> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("hydra4.png");

	public RenderTFHydra(EntityRendererManager manager, ModelTFHydra modelbase, float shadowSize) {
		super(manager, modelbase, shadowSize);
	}

	@Override
	protected float getDeathMaxRotation(EntityTFHydra entity) {
		return 0F;
	}

	@Override
	public ResourceLocation getEntityTexture(EntityTFHydra entity) {
		return textureLoc;
	}
}
