package twilightforest.client.model.entity;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import twilightforest.entity.YetiEntity;

import net.minecraft.client.model.HumanoidModel.ArmPose;

public class YetiModel<T extends YetiEntity> extends HumanoidModel<T> {

	public ModelPart mouth;
	public ModelPart leftEye;
	public ModelPart rightEye;
	public ModelPart angryLeftEye;
	public ModelPart angryRightEye;

	public YetiModel() {
		super(0.0F, 0.0F, 128, 64);

        this.head = new ModelPart(this, 0, 0);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 0, 0, 0);
		this.hat = new ModelPart(this, 32, 0);
		this.hat.addBox(-4.0F, -8.0F, -4.0F, 0, 0, 0);

		this.body = new ModelPart(this, 32, 0);
		this.body.addBox(-10.0F, 0.0F, -6.0F, 20, 26, 12);
		this.body.setPos(0.0F, -14.0F, 0.0F);

		this.mouth = new ModelPart(this, 96, 6);
		this.mouth.addBox(-7.0F, -5.0F, -0.5F, 14, 10, 1);
		this.mouth.setPos(0.0F, 12.0F, -6.0F);
		this.body.addChild(mouth);

		this.rightEye = new ModelPart(this, 96, 0);
		this.rightEye.addBox(-2.5F, -2.5F, -0.5F, 5, 5, 1);
		this.rightEye.setPos(-5.5F, 4.5F, -6.0F);
		this.body.addChild(rightEye);

		this.leftEye = new ModelPart(this, 96, 0);
		this.leftEye.addBox(-2.5F, -2.5F, -0.5F, 5, 5, 1);
		this.leftEye.setPos(5.5F, 4.5F, -6.0F);
		this.body.addChild(leftEye);

		this.angryRightEye = new ModelPart(this, 109, 0);
		this.angryRightEye.addBox(-2.5F, -2.5F, -0.5F, 5, 5, 1);
		this.angryRightEye.setPos(5.5F, 4.5F, -6.0F);
		this.body.addChild(angryRightEye);

		this.angryLeftEye = new ModelPart(this, 109, 0);
		this.angryLeftEye.addBox(-2.5F, -2.5F, -0.5F, 5, 5, 1);
		this.angryLeftEye.setPos(-5.5F, 4.5F, -6.0F);
		this.body.addChild(angryLeftEye);

		this.rightArm = new ModelPart(this, 0, 0);
		this.rightArm.addBox(-5.0F, -2.0F, -3.0F, 6, 16, 6);
		this.rightArm.setPos(-11.0F, -4.0F, 0.0F);
		this.leftArm = new ModelPart(this, 0, 0);
		this.leftArm.mirror = true;
		this.leftArm.addBox(-1.0F, -2.0F, -3.0F, 6, 16, 6);
		this.leftArm.setPos(11.0F, -4.0F, 0.0F);
		this.rightLeg = new ModelPart(this, 0, 22);
		this.rightLeg.addBox(-4.0F, 0.0F, -4.0F, 8, 12, 8);
		this.rightLeg.setPos(-6.0F, 12.0F, 0.0F);
		this.leftLeg = new ModelPart(this, 0, 22);
		this.leftLeg.mirror = true;
		this.leftLeg.addBox(-4.0F, 0.0F, -4.0F, 8, 12, 8);
		this.leftLeg.setPos(6.0F, 12.0F, 0.0F);
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, limbSwing and limbSwingAmount are used for animating the movement of arms
	 * and legs, where limbSwing represents the time(so that arms and legs swing back and forth) and limbSwingAmount represents how
	 * "far" arms and legs can swing at most.
	 */
	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
			this.leftArm.xRot = this.leftArm.xRot * 0.5F - ((float) Math.PI / 10F);
		}

		if (this.rightArmPose != ArmPose.EMPTY) {
			this.rightArm.xRot = this.rightArm.xRot * 0.5F - ((float) Math.PI / 10F);
		}

		this.rightArm.yRot = 0.0F;
		this.leftArm.yRot = 0.0F;

		this.rightArm.zRot += Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.leftArm.zRot -= Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.rightArm.xRot += Mth.sin(ageInTicks * 0.067F) * 0.05F;
		this.leftArm.xRot -= Mth.sin(ageInTicks * 0.067F) * 0.05F;

		// if yeti is angry, hold arms forwards like a zombie
		if (entity.isAngry()) {
			float f6 = Mth.sin(this.attackTime * (float) Math.PI);
			float f7 = Mth.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * (float) Math.PI);
			this.rightArm.zRot = 0.0F;
			this.leftArm.zRot = 0.0F;
			this.rightArm.yRot = -(0.1F - f6 * 0.6F);
			this.leftArm.yRot = 0.1F - f6 * 0.6F;
			this.rightArm.xRot = -((float) Math.PI / 2F);
			this.leftArm.xRot = -((float) Math.PI / 2F);
			this.rightArm.xRot -= f6 * 1.2F - f7 * 0.4F;
			this.leftArm.xRot -= f6 * 1.2F - f7 * 0.4F;
			this.rightArm.zRot += Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
			this.leftArm.zRot -= Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
			this.rightArm.xRot += Mth.sin(ageInTicks * 0.067F) * 0.05F;
			this.leftArm.xRot -= Mth.sin(ageInTicks * 0.067F) * 0.05F;
		}
	}

	/**
	 * Change eye color if yeti is angry
	 */
	@Override
	public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTicks) {
		if (entity.isAngry()) {
			this.rightEye.visible = false;
			this.leftEye.visible = false;
			this.angryRightEye.visible = true;
			this.angryLeftEye.visible = true;
		} else {
			this.rightEye.visible = true;
			this.leftEye.visible = true;
			this.angryRightEye.visible = false;
			this.angryLeftEye.visible = false;
		}
	}
}
