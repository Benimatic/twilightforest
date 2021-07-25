package twilightforest.client.model.entity.legacy;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.util.Mth;
import twilightforest.entity.boss.MinoshroomEntity;

import net.minecraft.client.model.HumanoidModel.ArmPose;

public class MinoshroomLegacyModel extends HumanoidModel<MinoshroomEntity> {

	ModelPart body;
	ModelPart leg1;
	ModelPart leg2;
	ModelPart leg3;
	ModelPart leg4;
	ModelPart udders;

	ModelPart snout;

	public ModelPart righthorn1;
	public ModelPart righthorn2;
	public ModelPart lefthorn1;
	public ModelPart lefthorn2;

	public MinoshroomLegacyModel() {
		super(0.0F, 0.0F, 128, 32);
//		textureWidth = 128;
//		textureHeight = 32;

		head = new ModelPart(this, 96, 16);
		head.addBox(-4F, -8F, -4F, 8, 8, 8);
		head.setPos(0F, -6F, -9F);

		body = new ModelPart(this, 18, 4);
		body.addBox(-6F, -10F, -7F, 12, 18, 10);
		body.setPos(0F, 5F, 2F);
		setRotation(body, 1.570796F, 0F, 0F);

		leg1 = new ModelPart(this, 0, 16);
		leg1.addBox(-3F, 0F, -2F, 4, 12, 4);
		leg1.setPos(-3F, 12F, 7F);

		leg2 = new ModelPart(this, 0, 16);
		leg2.addBox(-1F, 0F, -2F, 4, 12, 4);
		leg2.setPos(3F, 12F, 7F);

		leg3 = new ModelPart(this, 0, 16);
		leg3.addBox(-3F, 0F, -3F, 4, 12, 4);
		leg3.setPos(-3F, 12F, -5F);

		leg4 = new ModelPart(this, 0, 16);
		leg4.addBox(-1F, 0F, -3F, 4, 12, 4);
		leg4.setPos(3F, 12F, -5F);

		udders = new ModelPart(this, 52, 0);
		udders.addBox(-2F, -3F, 0F, 4, 6, 2);
		udders.setPos(0F, 14F, 6F);
		setRotation(udders, 1.570796F, 0F, 0F);

		body = new ModelPart(this, 64, 0);
		body.addBox(-4F, 0F, -2.5F, 8, 12, 5);
		body.setPos(0F, -6F, -9F);

		this.leftArm = new ModelPart(this, 90, 0);
		this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4);
		this.leftArm.setPos(5F, -4F, -9F);
		this.leftArm.mirror = true;

		this.rightArm = new ModelPart(this, 90, 0);
		this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4);
		this.rightArm.setPos(-5F, -4F, -9F);

		// horns
		this.righthorn1 = new ModelPart(this, 0, 0);
		this.righthorn1.addBox(-5.5F, -1.5F, -1.5F, 5, 3, 3);
		this.righthorn1.setPos(-2.5F, -6.5F, 0.0F);
		this.righthorn1.yRot = -25F / (180F / (float) Math.PI);
		this.righthorn1.zRot = 10F / (180F / (float) Math.PI);

		this.righthorn2 = new ModelPart(this, 16, 0);
		this.righthorn2.addBox(-3.5F, -1.0F, -1.0F, 3, 2, 2);
		this.righthorn2.setPos(-4.5F, 0.0F, 0.0F);
		this.righthorn2.yRot = -15F / (180F / (float) Math.PI);
		this.righthorn2.zRot = 45F / (180F / (float) Math.PI);

		this.righthorn1.addChild(righthorn2);

		this.lefthorn1 = new ModelPart(this, 0, 0);
		this.lefthorn1.mirror = true;
		this.lefthorn1.addBox(0.5F, -1.5F, -1.5F, 5, 3, 3);
		this.lefthorn1.setPos(2.5F, -6.5F, 0.0F);
		this.lefthorn1.yRot = 25F / (180F / (float) Math.PI);
		this.lefthorn1.zRot = -10F / (180F / (float) Math.PI);

		this.lefthorn2 = new ModelPart(this, 16, 0);
		this.lefthorn2.addBox(0.5F, -1.0F, -1.0F, 3, 2, 2);
		this.lefthorn2.setPos(4.5F, 0.0F, 0.0F);
		this.lefthorn2.yRot = 15F / (180F / (float) Math.PI);
		this.lefthorn2.zRot = -45F / (180F / (float) Math.PI);

		this.lefthorn1.addChild(lefthorn2);

		this.head.addChild(righthorn1);
		this.head.addChild(lefthorn1);

		snout = new ModelPart(this, 105, 28);
		snout.addBox(-2, -1, -1, 4, 3, 1);
		snout.setPos(0F, -2.0F, -4F);

		this.head.addChild(snout);

		// kill off headwear box
		this.hat = new ModelPart(this, 0, 0);
	}

//	@Override
//	public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
//		//super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//		setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(head);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(
				body,
				leg1,
				leg2,
				leg3,
				leg4,
				udders,
				body,
				leftArm,
				rightArm
		);
	}

	private void setRotation(ModelPart model, float x, float y, float z) {
		model.xRot = x;
		model.yRot = y;
		model.zRot = z;
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, limbSwing and limbSwingAmount are used for animating the movement of arms
	 * and legs, where limbSwing represents the time(so that arms and legs swing back and forth) and limbSwingAmount represents how
	 * "far" arms and legs can swing at most.
	 */
	@Override
	public void setupAnim(MinoshroomEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// copied from ModelBiped

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

		if (this.leftArmPose != ArmPose.EMPTY) {
			this.leftArm.xRot = this.leftArm.xRot * 0.5F - ((float) Math.PI / 10F);
		}

		if (this.rightArmPose != ArmPose.EMPTY) {
			this.rightArm.xRot = this.rightArm.xRot * 0.5F - ((float) Math.PI / 10F);
		}


		this.rightArm.zRot += Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.leftArm.zRot -= Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.rightArm.xRot += Mth.sin(ageInTicks * 0.067F) * 0.05F;
		this.leftArm.xRot -= Mth.sin(ageInTicks * 0.067F) * 0.05F;

		float var7 = 0.0F;
		float var8 = 0.0F;

		if (this.leftArmPose == ArmPose.BOW_AND_ARROW) {
			this.leftArm.zRot = 0.0F;
			this.leftArm.yRot = 0.1F - var7 * 0.6F + this.head.yRot + 0.4F;
			this.leftArm.xRot = -((float) Math.PI / 2F) + this.head.xRot;
			this.leftArm.xRot -= var7 * 1.2F - var8 * 0.4F;
			this.leftArm.zRot -= Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
			this.leftArm.xRot -= Mth.sin(ageInTicks * 0.067F) * 0.05F;
		}

		if (this.rightArmPose == ArmPose.BOW_AND_ARROW) {
			this.rightArm.zRot = 0.0F;
			this.rightArm.yRot = -(0.1F - var7 * 0.6F) + this.head.yRot;
			this.rightArm.xRot = -((float) Math.PI / 2F) + this.head.xRot;
			this.rightArm.xRot -= var7 * 1.2F - var8 * 0.4F;
			this.rightArm.zRot += Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
			this.rightArm.xRot += Mth.sin(ageInTicks * 0.067F) * 0.05F;
		}

		// copied from ModelQuadruped
		this.body.xRot = ((float) Math.PI / 2F);
		this.leg1.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leg2.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leg3.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leg4.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

		float f = ageInTicks - entity.tickCount;
		float f1 = entity.getChargeAnimationScale(f);
		f1 = f1 * f1;
		float f2 = 1.0F - f1;
		if (f1 > 0) {

			if (entity.getMainArm() == HumanoidArm.RIGHT) {
				this.rightArm.xRot = f1 * -1.8F;
				this.leftArm.xRot = 0.0F;
				this.rightArm.zRot = -0.2F;
			} else {
				this.rightArm.xRot = 0.0F;
				this.leftArm.xRot = f1 * -1.8F;
				this.leftArm.zRot = 0.2F;
			}
			this.body.xRot = ((float) Math.PI / 2F) - f1 * (float) Math.PI * 0.2F;
			this.leg3.y = 12.0F + (-5.8F * f1);
			this.leg3.z = -4.0F + (-5.8F * f1);
			this.leg3.xRot -= f1 * (float) Math.PI * 0.3F;

			this.leg4.y = this.leg3.y;
			this.leg4.z = this.leg3.z;
			this.leg4.xRot -= f1 * (float) Math.PI * 0.3F;
			this.body.y = -6F + -3.0F * f1;
		}
	}
}
