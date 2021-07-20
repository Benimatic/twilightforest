package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.TFGhastModel;
import twilightforest.entity.CarminiteGhastguardEntity;

public class TFGhastRenderer<T extends CarminiteGhastguardEntity, M extends TFGhastModel<T>> extends MobRenderer<T, M> {

	private static final ResourceLocation textureLocClosed = TwilightForestMod.getModelTexture("towerghast.png");
	private static final ResourceLocation textureLocOpen = TwilightForestMod.getModelTexture("towerghast_openeyes.png");
	private static final ResourceLocation textureLocAttack = TwilightForestMod.getModelTexture("towerghast_fire.png");

	public TFGhastRenderer(EntityRendererManager manager, M model, float shadowSize) {
		super(manager, model, shadowSize);
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		switch (entity.isAttacking() ? 2 : entity.getAttackStatus()) {
			default:
			case 0:
				return textureLocClosed;
			case 1:
				return textureLocOpen;
			case 2:
				return textureLocAttack;
		}
	}
}
