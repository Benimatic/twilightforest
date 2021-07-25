package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import twilightforest.client.model.entity.TFGhastModel;
import twilightforest.entity.CarminiteGhastguardEntity;

/**
 * This is a copy of the RenderGhast class that changes the model
 */
public class CarminiteGhastRenderer<T extends CarminiteGhastguardEntity, M extends TFGhastModel<T>> extends TFGhastRenderer<T, M> {

	private float ghastScale = 8.0F;

	public CarminiteGhastRenderer(EntityRenderDispatcher renderManager, M modelTFGhast, float f) {
		super(renderManager, modelTFGhast, f);
	}

	public CarminiteGhastRenderer(EntityRenderDispatcher renderManager, M modelTFGhast, float f, float scale) {
		super(renderManager, modelTFGhast, f);
		this.ghastScale = scale;
	}

	@Override
	protected void scale(T ghast, PoseStack stack, float partialTicks) {
		int attackTimer = ghast.getAttackTimer();
		int prevAttackTimer = ghast.getPrevAttackTimer();
		float scaleVariable = (prevAttackTimer + (attackTimer - prevAttackTimer) * partialTicks) / 20.0F;
		if (scaleVariable < 0.0F) {
			scaleVariable = 0.0F;
		}

		scaleVariable = 1.0F / (scaleVariable * scaleVariable * scaleVariable * scaleVariable * scaleVariable * 2.0F + 1.0F);
		float yScale = (ghastScale + scaleVariable) / 2.0F;
		float xzScale = (ghastScale + 1.0F / scaleVariable) / 2.0F;
		stack.scale(xzScale, yScale, xzScale);
	}
}
