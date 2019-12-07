package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFSpikeBlock;

public class RenderTFSpikeBlock<T extends EntityTFSpikeBlock> extends EntityRenderer<T> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("blockgoblin.png");
	private final ModelBase model;

	public RenderTFSpikeBlock(EntityRendererManager manager, ModelBase modelTFSpikeBlock) {
		super(manager);
		this.model = modelTFSpikeBlock;
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float yaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translated(x, y, z);
		GlStateManager.rotatef(180 - MathHelper.wrapDegrees(yaw), 0.0F, 1.0F, 0.0F);

		float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;

		GlStateManager.rotatef(pitch, 1.0F, 0.0F, 0.0F);
		this.bindEntityTexture(entity);

		GlStateManager.scalef(-1.0F, -1.0F, 1.0F);
		this.model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}
}
