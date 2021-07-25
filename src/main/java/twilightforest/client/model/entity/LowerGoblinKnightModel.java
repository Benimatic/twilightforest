package twilightforest.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.LowerGoblinKnightEntity;

import net.minecraft.client.model.HumanoidModel.ArmPose;

/**
 * ModelTFGoblinKnightLower - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class LowerGoblinKnightModel extends HumanoidModel<LowerGoblinKnightEntity> {
    public ModelPart tunic;

    public LowerGoblinKnightModel() {
        super(0, 0, 128, 64);
        this.rightArm = new ModelPart(this, 48, 48);
        this.rightArm.setPos(-3.5F, 10.0F, 0.0F);
        this.rightArm.addBox(-2.0F, -2.0F, -1.5F, 2.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm, 0.0F, 0.0F, 0.10000000116728046F);
        this.tunic = new ModelPart(this, 64, 19);
        this.tunic.setPos(0.0F, 7.5F, 0.0F);
        this.tunic.addBox(-6.0F, 0.0F, -3.0F, 12.0F, 9.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 0, 30);
        this.head.setPos(0.0F, 10.0F, 1.0F);
        this.head.addBox(-2.5F, -5.0F, -3.5F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.leftArm = new ModelPart(this, 38, 48);
        this.leftArm.setPos(3.5F, 10.0F, 0.0F);
        this.leftArm.addBox(0.0F, -2.0F, -1.5F, 2.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm, 0.0F, 0.0F, -0.10000736647217022F);
        this.leftLeg = new ModelPart(this, 0, 52);
        this.leftLeg.setPos(2.5F, 16.0F, 0.0F);
        this.leftLeg.addBox(-1.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.rightLeg = new ModelPart(this, 0, 40);
        this.rightLeg.setPos(-2.5F, 16.0F, 0.0F);
        this.rightLeg.addBox(-3.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.hat = new ModelPart(this, 0, 0);
        this.hat.setPos(0.0F, 0.0F, 0.0F);
        this.hat.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelPart(this, 16, 48);
        this.body.setPos(0.0F, 8.0F, 0.0F);
        this.body.addBox(-3.5F, 0.0F, -2.0F, 7.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
    }

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
        this.rightLeg.zRot = 0.0F;
        this.leftLeg.zRot = 0.0F;

        if (this.riding) {
            this.rightArm.xRot += (-(float)Math.PI / 5F);
            this.leftArm.xRot += (-(float)Math.PI / 5F);
            this.rightLeg.xRot = -1.4137167F;
            this.rightLeg.yRot = ((float)Math.PI / 10F);
            this.rightLeg.zRot = 0.07853982F;
            this.leftLeg.xRot = -1.4137167F;
            this.leftLeg.yRot = (-(float)Math.PI / 10F);
            this.leftLeg.zRot = -0.07853982F;
        }

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
    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
