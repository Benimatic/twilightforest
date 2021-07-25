package twilightforest.client.model.entity.legacy;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import twilightforest.entity.boss.SnowQueenEntity;
import twilightforest.entity.boss.SnowQueenEntity.Phase;

public class SnowQueenLegacyModel extends HumanoidModel<SnowQueenEntity> {

	public SnowQueenLegacyModel() {
		super(0.0F);

		float par1 = 0;
		float par2 = 0;

		// crown
		this.hat = new ModelPart(this, 0, 0);

		this.hat.addChild(makeFrontCrown(-1, -4, 10F));
		this.hat.addChild(makeFrontCrown(0, 4, -10F));
		this.hat.addChild(makeSideCrown(-1, -4, 10F));
		this.hat.addChild(makeSideCrown(0, 4, -10F));

		// copy to back

		// dress
		this.body = new ModelPart(this, 32, 0);
		this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 23, 4, par1);
		this.body.setPos(0.0F, 0.0F + par2, 0.0F);

		// shrink
		this.rightArm = new ModelPart(this, 16, 16);
		this.rightArm.addBox(-2.0F, -2.0F, -1.5F, 3, 12, 3, par1);
		this.rightArm.setPos(-5.0F, 2.0F + par2, 0.0F);
		this.leftArm = new ModelPart(this, 16, 16);
		this.leftArm.mirror = true;
		this.leftArm.addBox(-1.0F, -2.0F, -1.3F, 3, 12, 3, par1);
		this.leftArm.setPos(5.0F, 2.0F + par2, 0.0F);
		this.rightLeg = new ModelPart(this, 0, 16);
		this.rightLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3, par1);
		this.rightLeg.setPos(-1.9F, 12.0F + par2, 0.0F);
		this.leftLeg = new ModelPart(this, 0, 16);
		this.leftLeg.mirror = true;
		this.leftLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3, par1);
		this.leftLeg.setPos(1.9F, 12.0F + par2, 0.0F);
	}

	private ModelPart makeSideCrown(float spikeDepth, float crownX, float angle) {
		ModelPart crownSide = new ModelPart(this, 28, 28);
		crownSide.addBox(-3.5F, -0.5F, -0.5F, 7, 1, 1);
		crownSide.setPos(crownX, -6.0F, 0.0F);
		crownSide.yRot = 3.14159F / 2.0F;

		ModelPart spike4 = new ModelPart(this, 48, 27);
		spike4.addBox(-0.5F, -3.5F, spikeDepth, 1, 4, 1);
		spike4.xRot = angle * 1.5F / 180F * 3.14159F;

		ModelPart spike3l = new ModelPart(this, 52, 28);
		spike3l.addBox(-0.5F, -2.5F, spikeDepth, 1, 3, 1);
		spike3l.setPos(-2.5F, 0.0F, 0.0F);
		spike3l.xRot = angle / 180F * 3.14159F;
		spike3l.zRot = -10F / 180F * 3.14159F;

		ModelPart spike3r = new ModelPart(this, 52, 28);
		spike3r.addBox(-0.5F, -2.5F, spikeDepth, 1, 3, 1);
		spike3r.setPos(2.5F, 0.0F, 0.0F);
		spike3r.xRot = angle / 180F * 3.14159F;
		spike3r.zRot = 10F / 180F * 3.14159F;

		crownSide.addChild(spike4);
		crownSide.addChild(spike3l);
		crownSide.addChild(spike3r);
		return crownSide;
	}

	private ModelPart makeFrontCrown(float spikeDepth, float crownZ, float angle) {
		ModelPart crownFront = new ModelPart(this, 28, 30);
		crownFront.addBox(-4.5F, -0.5F, -0.5F, 9, 1, 1);
		crownFront.setPos(0.0F, -6.0F, crownZ);

		ModelPart spike4 = new ModelPart(this, 48, 27);
		spike4.addBox(-0.5F, -3.5F, spikeDepth, 1, 4, 1);
		spike4.xRot = angle * 1.5F / 180F * 3.14159F;

		ModelPart spike3l = new ModelPart(this, 52, 28);
		spike3l.addBox(-0.5F, -2.5F, spikeDepth, 1, 3, 1);
		spike3l.setPos(-2.5F, 0.0F, 0.0F);
		spike3l.xRot = angle / 180F * 3.14159F;
		spike3l.zRot = -10F / 180F * 3.14159F;

		ModelPart spike3r = new ModelPart(this, 52, 28);
		spike3r.addBox(-0.5F, -2.5F, spikeDepth, 1, 3, 1);
		spike3r.setPos(2.5F, 0.0F, 0.0F);
		spike3r.xRot = angle / 180F * 3.14159F;
		spike3r.zRot = 10F / 180F * 3.14159F;

		crownFront.addChild(spike4);
		crownFront.addChild(spike3l);
		crownFront.addChild(spike3r);
		return crownFront;
	}

	@Override
	public void setupAnim(SnowQueenEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		// in beam phase, arms forwards
		if (entity.getCurrentPhase() == Phase.BEAM) {
			if (entity.isBreathing()) {
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
			} else {
				// arms up
				this.rightArm.xRot += Math.PI;
				this.leftArm.xRot += Math.PI;
			}
		}
	}
}
