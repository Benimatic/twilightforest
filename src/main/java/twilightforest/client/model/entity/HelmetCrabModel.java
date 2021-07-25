package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.HelmetCrabEntity;

/**
 * ModelHelmetCrab - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class HelmetCrabModel extends ListModel<HelmetCrabEntity> {
    public ModelPart body;
    public ModelPart rightLeg1;
    public ModelPart rightLeg2;
    public ModelPart leftLeg1;
    public ModelPart leftLeg2;
    public ModelPart leftClaw;
    public ModelPart rightClaw;
    public ModelPart helmet;
    public ModelPart horns;

    public HelmetCrabModel() {
        this.texWidth = 64;
        this.texHeight = 32;
        this.leftLeg1 = new ModelPart(this, 0, 0);
        this.leftLeg1.setPos(2.0F, 21.0F, 0.0F);
        this.leftLeg1.texOffs(48, 19).addBox(0.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg1, 0.2181661564992912F, -0.4363323129985824F, 0.4363323129985824F);
        this.helmet = new ModelPart(this, 0, 0);
        this.helmet.setPos(0.0F, -1.0F, 0.5F);
        this.helmet.texOffs(40, 0).addBox(-4.0F, -8.0F, -4.0F, 6.0F, 8.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.helmet.texOffs(16, 0).addBox(-4.0F, -8.0F, -4.0F, 6.0F, 8.0F, 6.0F, -0.25F, -0.25F, -0.25F);
        this.setRotateAngle(helmet, -1.3089969389957472F, -0.2617993877991494F, 0.7463027588580033F);
        this.leftLeg2 = new ModelPart(this, 0, 0);
        this.leftLeg2.setPos(2.0F, 21.0F, -1.5F);
        this.leftLeg2.texOffs(48, 15).addBox(0.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg2, 0.2181661564992912F, 0.0F, 0.4363323129985824F);
        this.leftClaw = new ModelPart(this, 0, 0);
        this.leftClaw.setPos(3.0F, 0.0F, -3.0F);
        this.leftClaw.texOffs(0, 23).addBox(-1.0F, -3.0F, -5.0F, 2.0F, 4.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftClaw, 0.0F, -0.39269908169872414F, 0.0F);
        this.horns = new ModelPart(this, 0, 0);
        this.horns.setPos(0.0F, 0.0F, 0.0F);
        this.horns.texOffs(18, 23).addBox(-11.5F, -12.0F, -0.67F, 23.0F, 9.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(horns, 0.0F, 0.7853981633974483F, 0.0F);
        this.rightLeg1 = new ModelPart(this, 0, 0);
        this.rightLeg1.setPos(-2.0F, 21.0F, 0.0F);
        this.rightLeg1.texOffs(32, 15).addBox(-6.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg1, 0.2181661564992912F, 0.4363323129985824F, -0.4363323129985824F);
        this.rightClaw = new ModelPart(this, 0, 0);
        this.rightClaw.setPos(-3.0F, 0.0F, -3.0F);
        this.rightClaw.addBox(-1.0F, -3.0F, -5.0F, 2.0F, 4.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightClaw, 0.0F, 0.39269908169872414F, 0.0F);
        this.rightLeg2 = new ModelPart(this, 0, 0);
        this.rightLeg2.setPos(-2.0F, 21.0F, -1.5F);
        this.rightLeg2.texOffs(32, 19).addBox(-6.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg2, 0.2181661564992912F, 0.0F, -0.4363323129985824F);
        this.body = new ModelPart(this, 0, 0);
        this.body.setPos(0.0F, 21.0F, 0.0F);
        this.body.texOffs(0, 9).addBox(-2.5F, -4.0F, -2.5F, 5.0F, 4.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.body.texOffs(58, 0).addBox(-1.5F, -5.0F, -3.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.body.texOffs(58, 3).addBox(0.5F, -5.0F, -3.5F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.helmet);
        this.body.addChild(this.leftClaw);
        this.body.addChild(this.rightClaw);
        this.helmet.addChild(this.horns);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if(this.riding) matrixStackIn.translate(0, -0.25F, 0);
        super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(
                body,
                rightLeg1,
                rightLeg2,
                leftLeg1,
                leftLeg2

        );
    }

    @Override
    public void setupAnim(HelmetCrabEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        this.body.yRot = netHeadYaw / (180F / (float) Math.PI);
        this.body.xRot = headPitch / (180F / (float) Math.PI);
        float f6 = ((float) Math.PI / 4F);
        this.rightLeg1.zRot = -f6 * 0.74F;
        this.leftLeg1.zRot = f6 * 0.74F;
        this.rightLeg2.zRot = -f6 * 0.74F;
        this.leftLeg2.zRot = f6 * 0.74F;
        float f7 = -0.0F;
        float f8 = 0.3926991F;
        this.rightLeg1.yRot = f8 + f7;
        this.leftLeg1.yRot = -f8 - f7;
        this.rightLeg2.yRot = -f8 + f7;
        this.leftLeg2.yRot = f8 - f7;
        float f10 = -(Mth.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * limbSwingAmount;
        float f11 = -(Mth.cos(limbSwing * 0.6662F * 2.0F + ((float) Math.PI / 2F)) * 0.4F) * limbSwingAmount;
        float f14 = Math.abs(Mth.sin(limbSwing * 0.6662F + (float) Math.PI) * 0.4F) * limbSwingAmount;
        float f15 = Math.abs(Mth.sin(limbSwing * 0.6662F + ((float) Math.PI / 2F)) * 0.4F) * limbSwingAmount;
        this.rightLeg1.yRot += f10;
        this.leftLeg1.yRot += -f10;
        this.rightLeg2.yRot += f11;
        this.leftLeg2.yRot += -f11;
        this.rightLeg1.zRot += f14;
        this.leftLeg1.zRot += -f14;
        this.rightLeg2.zRot += f15;
        this.leftLeg2.zRot += -f15;

        // swing right arm as if it were an arm, not a leg
        this.leftClaw.yRot = -0.319531F;
        this.leftClaw.yRot += -(Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F) / 2;
        this.rightClaw.yRot = 0.319531F;
        this.rightClaw.yRot += (Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F) / 2;

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
