package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.WraithModel;
import twilightforest.entity.WraithEntity;

import javax.annotation.Nullable;

public class WraithRenderer extends BipedRenderer<WraithEntity, WraithModel> {

	private static final ResourceLocation textureWraith = TwilightForestMod.getModelTexture("ghost.png");

	public WraithRenderer(EntityRendererManager manager, WraithModel modelbiped, float shadowSize) {
		super(manager, modelbiped, shadowSize);
	}

	@Nullable
	@Override
	protected RenderType func_230496_a_(WraithEntity entity, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
		return RenderType.getEntityTranslucent(getEntityTexture(entity));
	}

	@Override
	public ResourceLocation getEntityTexture(WraithEntity wraith) {
		return textureWraith;
	}
}
