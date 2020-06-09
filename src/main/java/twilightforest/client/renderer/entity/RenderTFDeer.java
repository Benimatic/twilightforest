package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFDeer;
import twilightforest.entity.passive.EntityTFDeer;

public class RenderTFDeer extends MobRenderer<EntityTFDeer, ModelTFDeer> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("wilddeer.png");

	public RenderTFDeer(EntityRendererManager manager, ModelTFDeer model, float shadowSize) {
		super(manager, model, shadowSize);
	}

	@Override
	public ResourceLocation getEntityTexture(EntityTFDeer entity) {
		return textureLoc;
	}
}
