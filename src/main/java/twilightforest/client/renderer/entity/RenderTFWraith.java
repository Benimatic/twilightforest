package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFWraith;
import twilightforest.entity.EntityTFWraith;

public class RenderTFWraith extends BipedRenderer<EntityTFWraith, ModelTFWraith> {

	private static final ResourceLocation textureWraith = TwilightForestMod.getModelTexture("ghost.png");

	public RenderTFWraith(EntityRendererManager manager, ModelTFWraith modelbiped, float shadowSize) {
		super(manager, modelbiped, shadowSize);
	}

	@Override
	public ResourceLocation getEntityTexture(EntityTFWraith wraith) {
		return textureWraith;
	}
}
