package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFTowerGolem;
import twilightforest.entity.EntityTFTowerGolem;

public class RenderTFTowerGolem<T extends EntityTFTowerGolem, M extends ModelTFTowerGolem<T>> extends MobRenderer<T, M> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("carminitegolem.png");

	public RenderTFTowerGolem(EntityRendererManager manager, M model, float shadowSize) {
		super(manager, model, shadowSize);
	}

	/**
	 * [VanillaCopy] {@link net.minecraft.client.renderer.entity.IronGolemRenderer}
	 */
	@Override
	protected void setupTransforms(T entity, MatrixStack ms, float ageInTicks, float rotationYaw, float partialTicks) {
		super.setupTransforms(entity, ms, ageInTicks, rotationYaw, partialTicks);

		if (!((double)entity.limbSwingAmount < 0.01D)) {
			float f = 13.0F;
			float f1 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks) + 6.0F;
			float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
			ms.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(6.5F * f2));
		}
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}
}
