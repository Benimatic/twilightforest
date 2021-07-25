package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import twilightforest.entity.WraithEntity;

public class WraithModel extends HumanoidModel<WraithEntity> {

	public ModelPart dress;

	public WraithModel() {
		super(RenderType::entityTranslucent, 0.0F, 0.0F, 64, 32);

		float f = 0.0F;
		dress = new ModelPart(this, 40, 16);
		dress.addBox(-4F, 12.0F, -2F, 8, 12, 4, f);
		dress.setPos(0.0F, 0.0F, 0.0F);
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(head, hat);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(
				body,
				rightArm,
				leftArm,
				dress
		);
	}

	@Override
	public void setupAnim(WraithEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		float var8 = Mth.sin(this.attackTime * (float) Math.PI);
		float var9 = Mth.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * (float) Math.PI);
		this.rightArm.zRot = 0.0F;
		this.leftArm.zRot = 0.0F;
		this.rightArm.yRot = -(0.1F - var8 * 0.6F);
		this.leftArm.yRot = 0.1F - var8 * 0.6F;
		this.rightArm.xRot = -((float) Math.PI / 2F);
		this.leftArm.xRot = -((float) Math.PI / 2F);
		this.rightArm.xRot -= var8 * 1.2F - var9 * 0.4F;
		this.leftArm.xRot -= var8 * 1.2F - var9 * 0.4F;
		this.rightArm.zRot += Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.leftArm.zRot -= Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.rightArm.xRot += Mth.sin(ageInTicks * 0.067F) * 0.05F;
		this.leftArm.xRot -= Mth.sin(ageInTicks * 0.067F) * 0.05F;
	}
}
