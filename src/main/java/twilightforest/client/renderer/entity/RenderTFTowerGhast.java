package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import twilightforest.client.model.entity.ModelTFGhast;
import twilightforest.entity.EntityTFTowerGhast;

/**
 * This is a copy of the RenderGhast class that changes the model
 */
public class RenderTFTowerGhast<T extends EntityTFTowerGhast, M extends ModelTFGhast<T>> extends RenderTFGhast<T, M> {

	private float ghastScale = 8.0F;

	public RenderTFTowerGhast(EntityRendererManager renderManager, M modelTFGhast, float f) {
		super(renderManager, modelTFGhast, f);
	}

	public RenderTFTowerGhast(EntityRendererManager renderManager, M modelTFGhast, float f, float scale) {
		super(renderManager, modelTFGhast, f);
		this.ghastScale = scale;
	}

	@Override
	protected void preRenderCallback(T ghast, float partialTicks) {
		int attackTimer = ghast.getAttackTimer();
		int prevAttackTimer = ghast.getPrevAttackTimer();
		float scaleVariable = (prevAttackTimer + (attackTimer - prevAttackTimer) * partialTicks) / 20.0F;
		if (scaleVariable < 0.0F) {
			scaleVariable = 0.0F;
		}

		scaleVariable = 1.0F / (scaleVariable * scaleVariable * scaleVariable * scaleVariable * scaleVariable * 2.0F + 1.0F);
		float yScale = (ghastScale + scaleVariable) / 2.0F;
		float xzScale = (ghastScale + 1.0F / scaleVariable) / 2.0F;
		GlStateManager.scalef(xzScale, yScale, xzScale);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}