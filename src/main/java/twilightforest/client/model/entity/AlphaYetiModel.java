package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import twilightforest.entity.boss.AlphaYetiEntity;

public class AlphaYetiModel extends HumanoidModel<AlphaYetiEntity> {

	public ModelPart mouth;
	public ModelPart leftEye;
	public ModelPart rightEye;

	public AlphaYetiModel() {
		super(0.0F, 0.0F, 256, 128);

		this.head = new ModelPart(this, 0, 0);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 0, 0, 0);

		this.hat = new ModelPart(this, 32, 0);
		this.hat.addBox(-4.0F, -8.0F, -4.0F, 0, 0, 0);

		this.body = new ModelPart(this, 80, 0);
		this.body.addBox(-24.0F, -60.0F, -18.0F, 48, 72, 36);
		this.body.setPos(0.0F, -6.0F, 0.0F);

		this.mouth = new ModelPart(this, 121, 50);
		this.mouth.addBox(-17.0F, -7.0F, -1.5F, 34, 29, 2);
		this.mouth.setPos(0.0F, -37.0F, -18.0F);
		this.body.addChild(mouth);

		this.rightEye = new ModelPart(this, 64, 0);
		this.rightEye.addBox(-6.0F, -6.0F, -1.5F, 12, 12, 2);
		this.rightEye.setPos(-14.0F, -50.0F, -18.0F);
		this.body.addChild(rightEye);

		this.leftEye = new ModelPart(this, 64, 0);
		this.leftEye.addBox(-6.0F, -6.0F, -1.5F, 12, 12, 2);
		this.leftEye.setPos(14.0F, -50.0F, -18.0F);
		this.body.addChild(leftEye);

		this.rightArm = new ModelPart(this, 0, 0);
		this.rightArm.addBox(-15.0F, -6.0F, -8.0F, 16, 48, 16);
		this.rightArm.setPos(-25.0F, -26.0F, 0.0F);

		this.body.addChild(this.rightArm);

		this.leftArm = new ModelPart(this, 0, 0);
		this.leftArm.mirror = true;
		this.leftArm.addBox(-1.0F, -6.0F, -8.0F, 16, 48, 16);
		this.leftArm.setPos(25.0F, -26.0F, 0.0F);

		this.body.addChild(this.leftArm);

		this.rightLeg = new ModelPart(this, 0, 66);
		this.rightLeg.addBox(-10.0F, 0.0F, -10.0F, 20, 20, 20);
		this.rightLeg.setPos(-13.5F, 4.0F, 0.0F);

		this.leftLeg = new ModelPart(this, 0, 66);
		this.leftLeg.mirror = true;
		this.leftLeg.addBox(-10.0F, 0.0F, -10.0F, 20, 20, 20);
		this.leftLeg.setPos(13.5F, 4.0F, 0.0F);

		addPairHorns(-58.0F, 35F);
		addPairHorns(-46.0F, 15F);
		addPairHorns(-36.0F, -5F);
	}

	/**
	 * Add a pair of horns
	 */
	private void addPairHorns(float height, float zangle) {
		ModelPart horn1a;
		ModelPart horn1b;

		horn1a = new ModelPart(this, 0, 108);
		horn1a.addBox(-9.0F, -5.0F, -5.0F, 10, 10, 10);
		horn1a.setPos(-24.0F, height, -8.0F);
		horn1a.yRot = -30F / (180F / (float) Math.PI);
		horn1a.zRot = zangle / (180F / (float) Math.PI);
		this.body.addChild(horn1a);

		horn1b = new ModelPart(this, 40, 108);
		horn1b.addBox(-14.0F, -4.0F, -4.0F, 18, 8, 8);
		horn1b.setPos(-8.0F, 0.0F, 0.0F);
		horn1b.yRot = -20F / (180F / (float) Math.PI);
		horn1b.zRot = zangle / (180F / (float) Math.PI);
		horn1a.addChild(horn1b);

		ModelPart horn2a;
		ModelPart horn2b;

		horn2a = new ModelPart(this, 0, 108);
		horn2a.addBox(-1.0F, -5.0F, -5.0F, 10, 10, 10);
		horn2a.setPos(24.0F, height, 0.0F);
		horn2a.yRot = 30F / (180F / (float) Math.PI);
		horn2a.zRot = -zangle / (180F / (float) Math.PI);
		this.body.addChild(horn2a);

		horn2b = new ModelPart(this, 40, 108);
		horn2b.addBox(-2.0F, -4.0F, -4.0F, 18, 8, 8);
		horn2b.setPos(8.0F, 0.0F, 0.0F);
		horn2b.yRot = 20F / (180F / (float) Math.PI);
		horn2b.zRot = -zangle / (180F / (float) Math.PI);
		horn2a.addChild(horn2b);
	}


	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body, this.rightLeg, this.leftLeg);
	}

	@Override
	public void setupAnim(AlphaYetiEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.head.xRot = headPitch / (180F / (float) Math.PI);

		this.body.xRot = headPitch / (180F / (float) Math.PI);

		this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.rightLeg.yRot = 0.0F;
		this.leftLeg.yRot = 0.0F;

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

		this.body.y = -6F;
		this.rightLeg.y = 4F;
		this.leftLeg.y = 4F;

		if (entity.isTired()) {
			// arms down
			this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
			this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
			this.rightArm.zRot = 0.0F;
			this.leftArm.zRot = 0.0F;

			// legs out
			this.rightArm.xRot += -((float) Math.PI / 5F);
			this.leftArm.xRot += -((float) Math.PI / 5F);
			this.rightLeg.xRot = -((float) Math.PI * 2F / 5F);
			this.leftLeg.xRot = -((float) Math.PI * 2F / 5F);
			this.rightLeg.yRot = ((float) Math.PI / 10F);
			this.leftLeg.yRot = -((float) Math.PI / 10F);

			//body down
			this.body.y = 6F;
			this.rightLeg.y = 12F;
			this.leftLeg.y = 12F;
		}

		if (entity.isRampaging()) {
			// arms up
			this.rightArm.xRot = Mth.cos(limbSwing * 0.66F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
			this.leftArm.xRot = Mth.cos(limbSwing * 0.66F) * 2.0F * limbSwingAmount * 0.5F;

            this.rightArm.yRot += Mth.cos(limbSwing * 0.25F) * 0.5F + 0.5F;
			this.leftArm.yRot -= Mth.cos(limbSwing * 0.25F) * 0.5F + 0.5F;

			this.rightArm.xRot += Math.PI * 1.25;
			this.leftArm.xRot += Math.PI * 1.25;
			this.rightArm.zRot = 0.0F;
			this.leftArm.zRot = 0.0F;
		}

		if (entity.isVehicle()) {
			// arms up!
			this.rightArm.xRot += Math.PI;
			this.leftArm.xRot += Math.PI;
		}
	}
}
