package twilightforest.client.model.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import twilightforest.entity.boss.EntityTFLichMinion;

public class ModelTFLichMinion<T extends EntityTFLichMinion> extends ZombieModel<T> {

	/**
	 * Used for easily adding entity-dependent animations. The second and third float params here are the same second
	 * and third as in the setRotationAngles method.
	 */
	@Override
	public void setLivingAnimations(T entity, float limbSwing, float limbSwingAmount, float partialTicks) {
		EntityTFLichMinion minion = entity;
		// make strong minions greener
		if (minion.isStrong()) {
			GlStateManager.color3f(0.25F, 2.0F, 0.25F);
		} else {
			GlStateManager.color3f(0.5F, 1.0F, 0.5F);
		}
	}

	@Override
	public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		// make strong minions bigger FIXME: actually do this?
		if (entity.isStrong()) {
			super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		} else {
			super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}
}
