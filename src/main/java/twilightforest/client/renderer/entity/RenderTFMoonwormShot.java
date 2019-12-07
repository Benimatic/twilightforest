package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFMoonworm;
import twilightforest.entity.EntityTFMoonwormShot;

public class RenderTFMoonwormShot<T extends EntityTFMoonwormShot> extends EntityRenderer<T> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("moonworm.png");
	private final ModelTFMoonworm wormModel = new ModelTFMoonworm();

	public RenderTFMoonwormShot(EntityRendererManager manager) {
		super(manager);
		this.shadowSize = 0.5F;
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float yaw, float partialTicks) {

		GlStateManager.pushMatrix();
		GlStateManager.translated(x, y, z);
		GlStateManager.rotatef(90F, 1F, 0F, 1F);

		this.bindTexture(getEntityTexture(entity));
		wormModel.render(0.075F);

		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}
}
