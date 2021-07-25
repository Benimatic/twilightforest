package twilightforest.client.model.entity;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.boss.SnowQueenEntity;

/**
 * ModelSnowQueen - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class SnowQueenModel extends HumanoidModel<SnowQueenEntity> {

    public ModelPart crownFront;
    public ModelPart crownBack;
    public ModelPart crownRight;
    public ModelPart crownLeft;

    public SnowQueenModel() {
        super(0, 0, 64, 64);
        this.crownRight = new ModelPart(this, 0, 0);
        this.crownRight.setPos(-4.0F, -6.0F, 0.0F);
        this.crownRight.texOffs(24, 4).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(crownRight, 0.39269908169872414F, 1.5707963267948966F, 0.0F);
        this.crownBack = new ModelPart(this, 0, 0);
        this.crownBack.setPos(0.0F, -6.0F, 4.0F);
        this.crownBack.texOffs(44, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(crownBack, -0.39269908169872414F, 0.0F, 0.0F);
        this.body = new ModelPart(this, 0, 0);
        this.body.setPos(0.0F, 0.0F, 0.0F);
        this.body.texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.body.texOffs(32, 45).addBox(-4.5F, 10.0F, -2.5F, 9.0F, 14.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 0, 0);
        this.head.setPos(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.leftArm = new ModelPart(this, 0, 0);
        this.leftArm.setPos(5.0F, 2.0F, 0.0F);
        this.leftArm.texOffs(14, 32).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg = new ModelPart(this, 0, 0);
        this.leftLeg.setPos(1.9F, 12.0F, 0.0F);
        this.leftLeg.texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.crownLeft = new ModelPart(this, 0, 0);
        this.crownLeft.setPos(4.0F, -6.0F, 0.0F);
        this.crownLeft.texOffs(44, 4).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(crownLeft, -0.39269908169872414F, 1.5707963267948966F, 0.0F);
        this.rightLeg = new ModelPart(this, 0, 0);
        this.rightLeg.setPos(-1.9F, 12.0F, 0.0F);
        this.rightLeg.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.rightArm = new ModelPart(this, 0, 0);
        this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
        this.rightArm.texOffs(0, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.crownFront = new ModelPart(this, 0, 0);
        this.crownFront.setPos(0.0F, -6.0F, -4.0F);
        this.crownFront.texOffs(24, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(crownFront, 0.39269908169872414F, 0.0F, 0.0F);
        this.hat = new ModelPart(this, 0, 0);
        this.hat.addBox(0, 0, 0, 0, 0, 0);

        this.hat.addChild(this.crownRight);
        this.hat.addChild(this.crownBack);
        this.hat.addChild(this.crownLeft);
        this.hat.addChild(this.crownFront);
    }

    @Override
    public void setupAnim(SnowQueenEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        // in beam phase, arms forwards
        if (entity.getCurrentPhase() == SnowQueenEntity.Phase.BEAM) {
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
    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
