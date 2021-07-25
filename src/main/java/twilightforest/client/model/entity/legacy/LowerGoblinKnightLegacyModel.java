package twilightforest.client.model.entity.legacy;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import twilightforest.entity.LowerGoblinKnightEntity;

import net.minecraft.client.model.HumanoidModel.ArmPose;

public class LowerGoblinKnightLegacyModel extends HumanoidModel<LowerGoblinKnightEntity> {

	public ModelPart tunic;

	public LowerGoblinKnightLegacyModel() {
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

		this.head = new ModelPart(this, 0, 32);
		this.head.addBox(-2.5F, -5.0F, -3.5F, 5, 5, 5);
		this.head.setPos(0.0F, 10.0F, 1.0F);

		this.hat = new ModelPart(this, 0, 0);
		this.hat.addBox(0, 0, 0, 0, 0, 0);

		this.body = new ModelPart(this, 16, 48);
		this.body.addBox(-3.5F, 0.0F, -2.0F, 7, 8, 4);
		this.body.setPos(0.0F, 8.0F, 0.0F);

		this.rightArm = new ModelPart(this, 40, 48);
		this.rightArm.addBox(-2.0F, -2.0F, -1.5F, 2, 8, 3);
		this.rightArm.setPos(-3.5F, 10.0F, 0.0F);

		this.leftArm = new ModelPart(this, 40, 48);
		this.leftArm.mirror = true;
		this.leftArm.addBox(0.0F, -2.0F, -1.5F, 2, 8, 3);
		this.leftArm.setPos(3.5F, 10.0F, 0.0F);

		this.rightLeg = new ModelPart(this, 0, 48);
		this.rightLeg.addBox(-3.0F, 0.0F, -2.0F, 4, 8, 4);
		this.rightLeg.setPos(-2.5F, 16.0F, 0.0F);

		this.leftLeg = new ModelPart(this, 0, 48);
		this.leftLeg.mirror = true;
		this.leftLeg.addBox(-1.0F, 0.0F, -2.0F, 4, 8, 4);
		this.leftLeg.setPos(2.5F, 16.0F, 0.0F);

		this.tunic = new ModelPart(this, 64, 19);
		this.tunic.addBox(-6.0F, 0.0F, -3.0F, 12, 9, 6);
		this.tunic.setPos(0F, 7.5F, 0.0F);
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float scale) {
		super.renderToBuffer(stack, builder, light, overlay, red, green, blue, scale);
		this.tunic.render(stack, builder, light, overlay, red, green, blue, scale);
	}

	@Override
	public void setupAnim(LowerGoblinKnightEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
			this.head.yRot = 0;
			this.head.xRot = 0;
			this.hat.yRot = this.head.yRot;
			this.hat.xRot = this.head.xRot;
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

		this.tunic.visible = entity.hasArmor();
	}
}
