package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.SlimeBeetleEntity;

/**
 * ModelSlimeBeetle - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class SlimeBeetleModel extends ListModel<SlimeBeetleEntity> {
    public ModelPart head;
    public ModelPart body;
    public ModelPart tailBottom;
    public ModelPart rightLeg1;
    public ModelPart rightLeg2;
    public ModelPart rightLeg3;
    public ModelPart leftLeg1;
    public ModelPart leftLeg2;
    public ModelPart leftLeg3;
    public ModelPart rightAntenna;
    public ModelPart leftAntenna;
    public ModelPart rightEye;
    public ModelPart leftEye;
    public ModelPart tailTop;
    public ModelPart slime;
    public ModelPart slimeCenter;

    private final boolean translucent;

    public SlimeBeetleModel() {
        this(false);
    }

    public SlimeBeetleModel(boolean translucent) {
        this.translucent = translucent;
        this.texWidth = 64;
        this.texHeight = 64;
        this.body = new ModelPart(this, 0, 0);
        this.body.setPos(0.0F, 17.0F, -8.0F);
        this.body.texOffs(32, 8).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, 1.5707963267948966F, 0.0F, 0.0F);
        this.rightLeg1 = new ModelPart(this, 0, 0);
        this.rightLeg1.setPos(-2.0F, 20.0F, -6.0F);
        this.rightLeg1.texOffs(40, 0).addBox(-10.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg1, 0.0F, -0.4363323129985824F, -0.4363323129985824F);
        this.tailBottom = new ModelPart(this, 0, 0);
        this.tailBottom.setPos(0.0F, 18.0F, 2.0F);
        this.tailBottom.texOffs(0, 34).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.slime = new ModelPart(this, 16, 40);
        this.slime.setPos(0.0F, -8.0F, 2.0F);
        this.slime.addBox(-6.0F, -12.0F, -7.0F, 12.0F, 12.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.slimeCenter = new ModelPart(this, 0, 18);
        this.slimeCenter.setPos(0.0F, -9.0F, 2.0F);
        this.slimeCenter.addBox(-4.0F, -9.0F, -5.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.rightEye = new ModelPart(this, 0, 0);
        this.rightEye.setPos(-2.5F, -1.0F, -4.5F);
        this.rightEye.texOffs(0, 12).addBox(-2.0F, -1.0F, -2.0F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.leftEye = new ModelPart(this, 0, 0);
        this.leftEye.setPos(2.5F, -1.0F, -4.5F);
        this.leftEye.texOffs(16, 12).addBox(-1.0F, -1.0F, -2.0F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.rightAntenna = new ModelPart(this, 0, 0);
        this.rightAntenna.setPos(-0.5F, -1.5F, -5.0F);
        this.rightAntenna.texOffs(38, 4).addBox(-12.0F, -0.5F, -0.5F, 12.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightAntenna, 0.0F, -0.7853981633974483F, 0.7853981633974483F);
        this.leftLeg2 = new ModelPart(this, 0, 0);
        this.leftLeg2.mirror = true;
        this.leftLeg2.setPos(2.0F, 20.0F, -4.0F);
        this.leftLeg2.texOffs(40, 0).addBox(0.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg2, 0.0F, -0.2181661564992912F, 0.4363323129985824F);
        this.tailTop = new ModelPart(this, 0, 0);
        this.tailTop.setPos(0.0F, 0.0F, 3.0F);
        this.tailTop.texOffs(32, 28).addBox(-3.0F, -9.0F, -1.0F, 6.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.rightLeg3 = new ModelPart(this, 0, 0);
        this.rightLeg3.setPos(-2.0F, 20.0F, -2.0F);
        this.rightLeg3.texOffs(40, 0).addBox(-10.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg3, 0.0F, 0.7853981633974483F, -0.4363323129985824F);
        this.leftAntenna = new ModelPart(this, 0, 0);
        this.leftAntenna.setPos(0.5F, -1.5F, -5.0F);
        this.leftAntenna.texOffs(38, 6).addBox(0.0F, -0.5F, -0.5F, 12.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftAntenna, 0.0F, 0.7853981633974483F, -0.7853981633974483F);
        this.head = new ModelPart(this, 0, 0);
        this.head.setPos(0.0F, 17.0F, -8.0F);
        this.head.addBox(-4.0F, -3.0F, -6.0F, 8.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.rightLeg2 = new ModelPart(this, 0, 0);
        this.rightLeg2.setPos(-2.0F, 20.0F, -4.0F);
        this.rightLeg2.texOffs(40, 0).addBox(-10.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg2, 0.0F, 0.2181661564992912F, -0.4363323129985824F);
        this.leftLeg3 = new ModelPart(this, 0, 0);
        this.leftLeg3.mirror = true;
        this.leftLeg3.setPos(2.0F, 20.0F, -2.0F);
        this.leftLeg3.texOffs(40, 0).addBox(0.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg3, 0.0F, -0.7853981633974483F, 0.4363323129985824F);
        this.leftLeg1 = new ModelPart(this, 0, 0);
        this.leftLeg1.mirror = true;
        this.leftLeg1.setPos(2.0F, 20.0F, -6.0F);
        this.leftLeg1.texOffs(40, 0).addBox(0.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg1, 0.0F, 0.4363323129985824F, 0.4363323129985824F);
        this.tailTop.addChild(translucent ? this.slime : this.slimeCenter);
        this.head.addChild(this.rightEye);
        this.head.addChild(this.leftEye);
        this.head.addChild(this.rightAntenna);
        this.tailBottom.addChild(this.tailTop);
        this.head.addChild(this.leftAntenna);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(
                head,
                body,
                leftLeg1,
                leftLeg2,
                leftLeg3,
                rightLeg1,
                rightLeg2,
                rightLeg3,
                tailBottom
        );
    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float alpha) {
        tailBottom.render(stack, builder, light, overlay, red, green, blue, alpha);

        if (!translucent) {
            parts().forEach((part) -> part.render(stack, builder, light, overlay, red, green, blue, alpha));
        }
    }

    @Override
    public void setupAnim(SlimeBeetleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
        this.head.xRot = headPitch / (180F / (float) Math.PI);

        // legs!
        float legZ = ((float) Math.PI / 11F);
        this.leftLeg1.zRot = legZ;
        this.rightLeg1.zRot = -legZ;
        this.leftLeg2.zRot = legZ * 0.74F;
        this.rightLeg2.zRot = -legZ * 0.74F;
        this.leftLeg3.zRot = legZ;
        this.rightLeg3.zRot = -legZ;

        float var9 = -0.0F;
        float var10 = 0.3926991F;
        this.leftLeg1.yRot = var10 * 2.0F + var9;
        this.rightLeg1.yRot = -var10 * 2.0F - var9;
        this.leftLeg2.yRot = var10 + var9;
        this.rightLeg2.yRot = -var10 - var9;
        this.leftLeg3.yRot = -var10 * 2.0F + var9;
        this.rightLeg3.yRot = var10 * 2.0F - var9;

        float var11 = -(Mth.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
        float var12 = -(Mth.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * limbSwingAmount;
        float var14 = -(Mth.cos(limbSwing * 0.6662F * 2.0F + ((float) Math.PI * 3F / 2F)) * 0.4F) * limbSwingAmount;

        float var15 = Math.abs(Mth.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
        float var16 = Math.abs(Mth.sin(limbSwing * 0.6662F + (float) Math.PI) * 0.4F) * limbSwingAmount;
        float var18 = Math.abs(Mth.sin(limbSwing * 0.6662F + ((float) Math.PI * 3F / 2F)) * 0.4F) * limbSwingAmount;

        this.leftLeg1.yRot += var11;
        this.rightLeg1.yRot += -var11;
        this.leftLeg2.yRot += var12;
        this.rightLeg2.yRot += -var12;
        this.leftLeg3.yRot += var14;
        this.rightLeg3.yRot += -var14;

        this.leftLeg1.zRot += var15;
        this.rightLeg1.zRot += -var15;

        this.leftLeg2.zRot += var16;
        this.rightLeg2.zRot += -var16;

        this.leftLeg3.zRot += var18;
        this.rightLeg3.zRot += -var18;

        // tail wiggle
        this.tailBottom.xRot = Mth.cos(ageInTicks * 0.3335F) * 0.15F;
        this.tailTop.xRot = Mth.cos(ageInTicks * 0.4445F) * 0.20F;
        this.slime.xRot = Mth.cos(ageInTicks * 0.5555F) * 0.25F;
        this.slimeCenter.xRot = Mth.cos(ageInTicks * 0.5555F + 0.25F) * 0.25F;
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
