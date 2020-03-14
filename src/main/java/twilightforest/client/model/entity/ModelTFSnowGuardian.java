package twilightforest.client.model.entity;

import net.minecraft.client.renderer.entity.model.BipedModel;
import twilightforest.entity.EntityTFSnowGuardian;

public class ModelTFSnowGuardian<T extends EntityTFSnowGuardian> extends BipedModel<T> {

	public ModelTFSnowGuardian() {
		super(0.0F);
	}

//	/**
//	 * Sets the models various rotation angles then renders the model.
//	 */
//	@Override
//	public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
//		this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//	}

	/**
	 * Used for easily adding entity-dependent animations. The second and third float params here are the same second
	 * and third as in the setRotationAngles method.
	 */
	@Override
	public void setLivingAnimations(T entity, float limbSwing, float limbSwingAmount, float partialTicks) {
		//float bounce = entity.ticksExisted + partialTicks;
		//entity.yOffset = 0.5F + MathHelper.sin((bounce) * 0.3F) * 0.5F;
	}
}
