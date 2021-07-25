package twilightforest.client.model.entity.legacy;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import twilightforest.entity.UpperGoblinKnightEntity;

import net.minecraft.client.model.HumanoidModel.ArmPose;

public class UpperGoblinKnightLegacyModel extends HumanoidModel<UpperGoblinKnightEntity> {

	public ModelPart breastplate;
	public ModelPart helmet;
	public ModelPart righthorn1;
	public ModelPart righthorn2;
	public ModelPart lefthorn1;
	public ModelPart lefthorn2;

	public ModelPart shield;
	public ModelPart spear;


	public UpperGoblinKnightLegacyModel() {
		super(0.0F, 0.0F, 128, 64);
		this.crouching = false;
		this.texWidth = 128;
		this.texHeight = 64;

//FIXME: AtomicBlom: Replace with something like LayerCape
/*
		this.bipedCloak = new ModelRenderer(this, 0, 0);
        this.bipedCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1);
*/
//FIXME: AtomicBlom replace with some variant of LayerDeadmau5Head
/*
        this.bipedEars = new ModelRenderer(this, 24, 0);
        this.bipedEars.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1);
*/

		this.head = new ModelPart(this, 0, 0);
		this.head.addBox(0, 0, 0, 0, 0, 0);
		this.head.setPos(0.0F, 12.0F, 0.0F);

		this.hat = new ModelPart(this, 0, 0);
		this.hat.addBox(0, 0, 0, 0, 0, 0);
		this.hat.setPos(0.0F, 12.0F, 0.0F);

		this.helmet = new ModelPart(this, 0, 0);
		this.helmet.addBox(-3.5F, -11.0F, -3.5F, 7, 11, 7);
		this.helmet.yRot = 45F / (180F / (float) Math.PI);

		this.righthorn1 = new ModelPart(this, 28, 0);
		this.righthorn1.addBox(-6F, -1.5F, -1.5F, 7, 3, 3);
		this.righthorn1.setPos(-3.5F, -9F, 0.0F);
		this.righthorn1.yRot = 15F / (180F / (float) Math.PI);
		this.righthorn1.zRot = 10F / (180F / (float) Math.PI);

		this.righthorn2 = new ModelPart(this, 28, 6);
		this.righthorn2.addBox(-3.0F, -1.0F, -1.0F, 3, 2, 2);
		this.righthorn2.setPos(-5.5F, 0.0F, 0.0F);
		this.righthorn2.zRot = 10F / (180F / (float) Math.PI);

		this.righthorn1.addChild(righthorn2);

		this.lefthorn1 = new ModelPart(this, 28, 0);
		this.lefthorn1.mirror = true;
		this.lefthorn1.addBox(-1F, -1.5F, -1.5F, 7, 3, 3);
		this.lefthorn1.setPos(3.5F, -9F, 0.0F);
		this.lefthorn1.yRot = -15F / (180F / (float) Math.PI);
		this.lefthorn1.zRot = -10F / (180F / (float) Math.PI);

		this.lefthorn2 = new ModelPart(this, 28, 6);
		this.lefthorn2.addBox(0.0F, -1.0F, -1.0F, 3, 2, 2);
		this.lefthorn2.setPos(5.5F, 0.0F, 0.0F);
		this.lefthorn2.zRot = -10F / (180F / (float) Math.PI);

		this.lefthorn1.addChild(lefthorn2);

		this.hat.addChild(helmet);
		this.hat.addChild(righthorn1);
		this.hat.addChild(lefthorn1);

		this.body = new ModelPart(this, 0, 18);
		this.body.setPos(0.0F, 12.0F, 0.0F);
		this.body.addBox(-5.5F, 0.0F, -2.0F, 11, 8, 4);
		this.body.texOffs(30, 24).addBox(-6.5F, 0F, -2F, 1, 4, 4); // right shoulder
		this.body.texOffs(30, 24).addBox(5.5F, 0F, -2F, 1, 4, 4); // left shoulder

		this.rightArm = new ModelPart(this, 44, 16);
		this.rightArm.addBox(-4.0F, -2.0F, -2.0F, 4, 12, 4);
		this.rightArm.setPos(-6.5F, 14.0F, 0.0F);

		this.leftArm = new ModelPart(this, 44, 16);
		this.leftArm.mirror = true;
		this.leftArm.addBox(0.0F, -2.0F, -2.0F, 4, 12, 4);
		this.leftArm.setPos(6.5F, 14.0F, 0.0F);

		this.rightLeg = new ModelPart(this, 30, 16);
		this.rightLeg.addBox(-1.5F, 0.0F, -2.0F, 3, 4, 4);
		this.rightLeg.setPos(-4F, 20.0F, 0.0F);

		this.leftLeg = new ModelPart(this, 30, 16);
		this.leftLeg.mirror = true;
		this.leftLeg.addBox(-1.5F, 0.0F, -2.0F, 3, 4, 4);
		this.leftLeg.setPos(4F, 20.0F, 0.0F);

		this.shield = new ModelPart(this, 63, 36);
		this.shield.addBox(-6.0F, -6.0F, -2.0F, 12, 20, 2);
		this.shield.setPos(0F, 12F, 0.0F);
		this.shield.xRot = 90F / (180F / (float) Math.PI);

		this.leftArm.addChild(shield);

		this.spear = new ModelPart(this, 108, 0);
		this.spear.addBox(-1.0F, -19.0F, -1.0F, 2, 40, 2);
		this.spear.setPos(-2F, 8.5F, 0.0F);
		this.spear.xRot = 90F / (180F / (float) Math.PI);

		this.rightArm.addChild(spear);

		this.breastplate = new ModelPart(this, 64, 0);
		this.breastplate.addBox(-6.5F, 0.0F, -3.0F, 13, 12, 6);
		this.breastplate.setPos(0F, 11.5F, 0.0F);
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float scale) {
		super.renderToBuffer(stack, builder, light, overlay, red, green, blue, scale);

		this.breastplate.render(stack, builder, light, overlay, red, green, blue, scale);
	}

	@Override
	public void setupAnim(UpperGoblinKnightEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		boolean hasShield = entity.hasShield();

		this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.head.xRot = headPitch / (180F / (float) Math.PI);
		this.head.zRot = 0;
		this.hat.yRot = this.head.yRot;
		this.hat.xRot = this.head.xRot;
		this.hat.zRot = this.head.zRot;

		this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;

		float leftConstraint = hasShield ? 0.2F : limbSwingAmount;

		this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * leftConstraint * 0.5F;
		this.rightArm.zRot = 0.0F;
		this.leftArm.zRot = 0.0F;

		this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.rightLeg.yRot = 0.0F;
		this.leftLeg.yRot = 0.0F;

		if (this.riding) {
			this.rightArm.xRot += -((float) Math.PI / 5F);
			this.leftArm.xRot += -((float) Math.PI / 5F);
			this.rightLeg.xRot = 0;
			this.leftLeg.xRot = 0;
//            this.bipedRightLeg.rotateAngleY = ((float)Math.PI / 10F);
//            this.bipedLeftLeg.rotateAngleY = -((float)Math.PI / 10F);
		}

		if (this.leftArmPose != ArmPose.EMPTY) {
			this.leftArm.xRot = this.leftArm.xRot * 0.5F - ((float) Math.PI / 10F);
		}

		this.rightArmPose = ArmPose.ITEM;

		if (this.rightArmPose != ArmPose.EMPTY) {
			this.rightArm.xRot = this.rightArm.xRot * 0.5F - ((float) Math.PI / 10F);
		}

		rightArm.xRot -= (Math.PI * 0.66);

		// during swing move arm forward
		if (entity.heavySpearTimer > 0) {
			rightArm.xRot -= this.getArmRotationDuringSwing(60 - entity.heavySpearTimer) / (180F / (float) Math.PI);
		}

		this.rightArm.yRot = 0.0F;
		this.leftArm.yRot = 0.0F;

		this.rightArm.zRot += Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.leftArm.zRot -= Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.rightArm.xRot += Mth.sin(ageInTicks * 0.067F) * 0.05F;
		this.leftArm.xRot -= Mth.sin(ageInTicks * 0.067F) * 0.05F;

		// shield arm points somewhat inward
		this.leftArm.zRot = -this.leftArm.zRot;

		// fix shield so that it's always perpendicular to the floor
		this.shield.xRot = (float) (Math.PI * 2 - this.leftArm.xRot);

		this.breastplate.visible = entity.hasArmor();
		this.shield.visible = entity.hasShield();
	}

	/**
	 *
	 */
	private float getArmRotationDuringSwing(float attackTime) {
		if (attackTime <= 10) {
			// rock back
			return attackTime;
		}
		if (attackTime > 10 && attackTime <= 30) {
			// hang back
			return 10F;
		}
		if (attackTime > 30 && attackTime <= 33) {
			// slam forward
			return (attackTime - 30) * -8F + 10F;
		}
		if (attackTime > 33 && attackTime <= 50) {
			// stay forward
			return -15F;
		}
		if (attackTime > 50 && attackTime <= 60) {
			// back to normal
			return (10 - (attackTime - 50)) * -1.5F;
		}

		return 0;
	}
}
