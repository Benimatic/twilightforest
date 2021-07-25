package twilightforest.client.model.entity;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import twilightforest.entity.TrollEntity;

import net.minecraft.client.model.HumanoidModel.ArmPose;

public class TrollModel extends HumanoidModel<TrollEntity> {

	public ModelPart nose;

	public TrollModel() {
		super(0.0F, 0.0F, 128, 64);

        this.head = new ModelPart(this, 0, 0);
		this.head.addBox(-5.0F, -8.0F, -3.0F, 10, 10, 10);
		this.head.setPos(0.0F, -9.0F, -6.0F);

		this.hat = new ModelPart(this, 32, 0);
		this.hat.addBox(-4.0F, -8.0F, -4.0F, 0, 0, 0);

		this.body = new ModelPart(this, 40, 0);
		this.body.addBox(-8.0F, 0.0F, -5.0F, 16, 26, 10);
		this.body.setPos(0.0F, -14.0F, 0.0F);


		this.nose = new ModelPart(this, 0, 21);
		this.nose.addBox(-2.0F, -2.0F, -2.0F, 4, 8, 4);
		this.nose.setPos(0.0F, -2.0F, -4.0F);
		this.head.addChild(nose);

		this.rightArm = new ModelPart(this, 32, 36);
		this.rightArm.addBox(-5.0F, -2.0F, -3.0F, 6, 22, 6);
		this.rightArm.setPos(-9.0F, -9.0F, 0.0F);

		this.leftArm = new ModelPart(this, 32, 36);
		this.leftArm.mirror = true;
		this.leftArm.addBox(-1.0F, -2.0F, -3.0F, 6, 22, 6);
		this.leftArm.setPos(9.0F, -9.0F, 0.0F);


		this.rightLeg = new ModelPart(this, 0, 44);
		this.rightLeg.addBox(-3.0F, 0.0F, -4.0F, 6, 12, 8);
		this.rightLeg.setPos(-5.0F, 12.0F, 0.0F);

		this.leftLeg = new ModelPart(this, 0, 44);
		this.leftLeg.mirror = true;
		this.leftLeg.addBox(-3.0F, 0.0F, -4.0F, 6, 12, 8);
		this.leftLeg.setPos(5.0F, 12.0F, 0.0F);
	}

	@Override
	public void setupAnim(TrollEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.head.xRot = headPitch / (180F / (float) Math.PI);
		this.hat.yRot = this.head.yRot;
		this.hat.xRot = this.head.xRot;
		this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.rightArm.zRot = 0.0F;
		this.leftArm.zRot = 0.0F;
		this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.rightLeg.yRot = 0.0F;
		this.leftLeg.yRot = 0.0F;

		if (entity.isVehicle()) {
			// arms up!
			this.rightArm.xRot += Math.PI;
			this.leftArm.xRot += Math.PI;

		}

		if (this.leftArmPose != ArmPose.EMPTY) {
			this.rightArm.xRot += Math.PI;
		}
		if (this.rightArmPose != ArmPose.EMPTY) {
			this.leftArm.xRot += Math.PI;
		}

		if (this.attackTime > 0F) {
			float swing = 1.0F - this.attackTime;

			this.rightArm.xRot -= (Math.PI * swing);
			this.leftArm.xRot -= (Math.PI * swing);
		}

		this.rightArm.yRot = 0.0F;
		this.leftArm.yRot = 0.0F;

		this.rightArm.zRot += Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.leftArm.zRot -= Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.rightArm.xRot += Mth.sin(ageInTicks * 0.067F) * 0.05F;
		this.leftArm.xRot -= Mth.sin(ageInTicks * 0.067F) * 0.05F;
	}

	@Override
	public void prepareMobModel(TrollEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {
		if (entity.getTarget() != null) {
			this.rightArm.xRot += Math.PI;
			this.leftArm.xRot += Math.PI;
		}
	}
}
