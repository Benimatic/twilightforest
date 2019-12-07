package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFLich;
import twilightforest.entity.boss.EntityTFLich;

import javax.annotation.Nonnull;

public class RenderTFLich<T extends EntityTFLich, M extends ModelTFLich<T>> extends BipedRenderer<T, M> {

	private static final ResourceLocation LICH_TEXTURE = TwilightForestMod.getModelTexture("twilightlich64.png");

	public RenderTFLich(EntityRendererManager manager, M modelbiped, float shadowSize) {
		super(manager, modelbiped, shadowSize);
		addLayer(new LayerShields<>());
	}

	@Override
	protected ResourceLocation getEntityTexture(@Nonnull EntityTFLich entity) {
		return LICH_TEXTURE;
	}

}
