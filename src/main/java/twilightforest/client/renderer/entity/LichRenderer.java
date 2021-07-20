package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.LichModel;
import twilightforest.entity.boss.LichEntity;

public class LichRenderer extends BipedRenderer<LichEntity, LichModel> {

	private static final ResourceLocation LICH_TEXTURE = TwilightForestMod.getModelTexture("twilightlich64.png");

	public LichRenderer(EntityRendererManager manager, LichModel modelbiped, float shadowSize) {
		super(manager, modelbiped, shadowSize);
		addLayer(new ShieldLayer<>(this));
	}

	@Override
	public ResourceLocation getEntityTexture(LichEntity entity) {
		return LICH_TEXTURE;
	}
}
