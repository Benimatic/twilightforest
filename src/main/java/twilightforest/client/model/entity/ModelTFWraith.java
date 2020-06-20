package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import twilightforest.entity.EntityTFWraith;

public class ModelTFWraith extends BipedModel<EntityTFWraith> {

	public ModelRenderer dress;

	public ModelTFWraith() {
		super(RenderType::getEntityTranslucent, 0.0F, 0.0F, 64, 32);

		float f = 0.0F;
		dress = new ModelRenderer(this, 40, 16);
		dress.addCuboid(-4F, 12.0F, -2F, 8, 12, 4, f);
		dress.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of(bipedHead, bipedHeadwear);
	}

	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(
				bipedBody,
				bipedRightArm,
				bipedLeftArm,
				dress
		);
	}

	@Override
	public void setAngles(EntityTFWraith entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		float var8 = MathHelper.sin(this.swingProgress * (float) Math.PI);
		float var9 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
		this.bipedRightArm.rotateAngleZ = 0.0F;
		this.bipedLeftArm.rotateAngleZ = 0.0F;
		this.bipedRightArm.rotateAngleY = -(0.1F - var8 * 0.6F);
		this.bipedLeftArm.rotateAngleY = 0.1F - var8 * 0.6F;
		this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2F);
		this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F);
		this.bipedRightArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
		this.bipedLeftArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
		this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
	}
}
