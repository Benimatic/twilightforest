package twilightforest.client.model.entity;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.KoboldEntity;

/**
 * ModelKobold - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class KoboldModel extends HumanoidModel<KoboldEntity> {

    public ModelPart mouth;
    public ModelPart rightEar;
    public ModelPart leftEar;

    boolean isJumping;

    public KoboldModel() {
        super(0, 0, 64, 32);
        this.rightArm = new ModelPart(this, 0, 0);
        this.rightArm.setPos(-4.5F, 13.0F, 0.0F);
        this.rightArm.texOffs(34, 12).addBox(-2.0F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 0, 0);
        this.head.setPos(0.0F, 12.0F, 0.0F);
        this.head.addBox(-3.5F, -6.0F, -3.0F, 7.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.head.texOffs(20, 0).addBox(-1.5F, -3.0F, -6.0F, 3.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelPart(this, 0, 0);
        this.body.setPos(0.0F, 12.0F, 0.0F);
        this.body.texOffs(12, 12).addBox(-3.5F, 0.0F, -2.0F, 7.0F, 7.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg = new ModelPart(this, 0, 0);
        this.leftLeg.setPos(1.9F, 19.0F, 0.0F);
        this.leftLeg.texOffs(0, 20).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.mouth = new ModelPart(this, 0, 0);
        this.mouth.setPos(0.0F, -1.0F, -3.0F);
        this.mouth.texOffs(26, 5).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(mouth, 0.2181661564992912F, 0.0F, 0.0F);
        this.rightEar = new ModelPart(this, 0, 0);
        this.rightEar.setPos(-3.0F, -4.0F, 0.0F);
        this.rightEar.texOffs(32, 0).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightEar, 0.0F, 0.0F, -1.3089969389957472F);
        this.rightLeg = new ModelPart(this, 0, 0);
        this.rightLeg.setPos(-1.9F, 19.0F, 0.0F);
        this.rightLeg.texOffs(0, 12).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.leftArm = new ModelPart(this, 0, 0);
        this.leftArm.setPos(4.5F, 13.0F, 0.0F);
        this.leftArm.texOffs(34, 22).addBox(-1.0F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.leftEar = new ModelPart(this, 0, 0);
        this.leftEar.setPos(3.0F, -4.0F, 0.0F);
        this.leftEar.texOffs(42, 0).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftEar, 0.0F, 0.0F, 1.3089969389957472F);
        this.hat = new ModelPart(this, 0, 0);
        this.hat.addBox(0, 0, 0, 0, 0, 0);
        this.head.addChild(this.mouth);
        this.head.addChild(this.rightEar);
        this.head.addChild(this.leftEar);
    }

    @Override
    public void prepareMobModel(KoboldEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {
        // check if entity is jumping
        this.isJumping = entity.getDeltaMovement().y() > 0;
    }

    @Override
    public void setupAnim(KoboldEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
        this.head.xRot = headPitch / (180F / (float) Math.PI);

        this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
        this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.rightArm.zRot = 0.0F;
        this.leftArm.zRot = 0.0F;

        this.rightArm.xRot = -((float) Math.PI * .15F);
        this.leftArm.xRot = -((float) Math.PI * .15F);

        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.rightLeg.yRot = 0.0F;
        this.leftLeg.yRot = 0.0F;

        this.rightArm.zRot += Mth.cos(ageInTicks * 0.19F) * 0.15F + 0.05F;
        this.leftArm.zRot -= Mth.cos(ageInTicks * 0.19F) * 0.15F + 0.05F;
        this.rightArm.xRot += Mth.sin(ageInTicks * 0.267F) * 0.25F;
        this.leftArm.xRot -= Mth.sin(ageInTicks * 0.267F) * 0.25F;

        if (this.isJumping) {
            // open jaw
            this.mouth.xRot = 0.6F;
        } else {
            this.mouth.xRot = 0.20944F;
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
