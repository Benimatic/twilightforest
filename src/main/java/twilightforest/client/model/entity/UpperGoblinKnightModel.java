package twilightforest.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.UpperGoblinKnightEntity;

import net.minecraft.client.model.HumanoidModel.ArmPose;

/**
 * ModelTFGoblinKnightUpper - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class UpperGoblinKnightModel extends HumanoidModel<UpperGoblinKnightEntity> {
    public ModelPart breastplate;
    public ModelPart helmet;
    public ModelPart head;
    public ModelPart spear;
    public ModelPart shield;

    public UpperGoblinKnightModel() {
        super(0, 0, 128, 64);
        this.rightLeg = new ModelPart(this, 30, 24);
        this.rightLeg.setPos(-4.0F, 20.0F, 0.0F);
        this.rightLeg.addBox(-1.5F, 0.0F, -2.0F, 3.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 0, 0);
        this.head.setPos(0.0F, 0.0F, 0.0F);
        this.head.texOffs(28, 0).addBox(-8.0F, -14.0F, -1.9F, 16.0F, 14.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.head.texOffs(116, 0).addBox(-6.0F, -12.0F, -0.9F, 4.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.head.texOffs(116, 4).addBox(2.0F, -12.0F, -1.0F, 4.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(head, 0.0F, -0.7853981633974483F, 0.0F);
        this.rightArm = new ModelPart(this, 44, 16);
        this.rightArm.setPos(-5.5F, 14.0F, 0.0F);
        this.rightArm.addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm, -2.3876104699914644F, 0.0F, 0.10000736647217022F);
        this.spear = new ModelPart(this, 108, 0);
        this.spear.setPos(-2.0F, 8.5F, 0.0F);
        this.spear.addBox(-1.0F, -19.0F, -1.0F, 2.0F, 40.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(spear, 1.5707963267948966F, 0.0F, 0.0F);
        this.shield = new ModelPart(this, 63, 36);
        this.shield.setPos(0.0F, 12.0F, 0.0F);
        this.shield.addBox(-6.0F, -6.0F, -2.0F, 12.0F, 20.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(shield, 6.083185105107944F, 0.0F, 0.0F);
        this.body = new ModelPart(this, 0, 18);
        this.body.setPos(0.0F, 12.0F, 0.0F);
        this.body.addBox(-5.5F, 0.0F, -2.0F, 11.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 0, 0);
        this.head.setPos(0.0F, 12.0F, 0.0F);
        this.head.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.leftArm = new ModelPart(this, 44, 32);
        this.leftArm.setPos(5.5F, 14.0F, 0.0F);
        this.leftArm.addBox(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm, 0.20001473294434044F, 0.0F, 0.10000736647217022F);
        this.leftLeg = new ModelPart(this, 30, 16);
        this.leftLeg.setPos(4.0F, 20.0F, 0.0F);
        this.leftLeg.addBox(-1.5F, 0.0F, -2.0F, 3.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.helmet = new ModelPart(this, 0, 0);
        this.helmet.setPos(0.0F, 0.0F, 0.0F);
        this.helmet.addBox(-3.5F, -11.0F, -3.5F, 7.0F, 11.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(helmet, 0.0F, 0.7853981633974483F, 0.0F);
        this.hat = new ModelPart(this, 0, 0);
        this.hat.setPos(0.0F, 12.0F, 0.0F);
        this.hat.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.breastplate = new ModelPart(this, 64, 0);
        this.breastplate.setPos(0.0F, 11.5F, 0.0F);
        this.breastplate.addBox(-6.5F, 0.0F, -3.0F, 13.0F, 12.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.helmet.addChild(this.head);
        this.rightArm.addChild(this.spear);
        this.leftArm.addChild(this.shield);
        this.hat.addChild(this.helmet);
    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float scale) {
        super.renderToBuffer(stack, builder, light, overlay, red, green, blue, scale);

        this.breastplate.render(stack, builder, light, overlay, red, green, blue, scale);
    }

    @Override
    public void setupAnim(UpperGoblinKnightEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean hasShield = entity.hasShield();
        boolean boat = entity.getVehicle() instanceof Boat;

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

        if (this.riding && boat) {
            this.rightArm.xRot += (-(float)Math.PI / 5F);
            this.leftArm.xRot += (-(float)Math.PI / 5F);
            this.rightLeg.xRot = -1.4137167F;
            this.rightLeg.yRot = ((float)Math.PI / 10F);
            this.rightLeg.zRot = 0.07853982F;
            this.leftLeg.xRot = -1.4137167F;
            this.leftLeg.yRot = (-(float)Math.PI / 10F);
            this.leftLeg.zRot = -0.07853982F;
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

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
