package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.PinchBeetleEntity;

/**
 * ModelPinchBeetle - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class PinchBeetleModel extends ListModel<PinchBeetleEntity> {
    public ModelPart Head;
    public ModelPart body;
    public ModelPart rightLeg1;
    public ModelPart rightLeg2;
    public ModelPart rightLeg3;
    public ModelPart leftLeg1;
    public ModelPart leftLeg2;
    public ModelPart leftLeg3;
    public ModelPart rightPincer;
    public ModelPart leftPincer;
    public ModelPart rightAntenna;
    public ModelPart leftAntenna;

    public PinchBeetleModel() {
        this.texWidth = 64;
        this.texHeight = 64;
        this.leftLeg2 = new ModelPart(this, 0, 0);
        this.leftLeg2.setPos(2.0F, 21.0F, 4.0F);
        this.leftLeg2.texOffs(40, 46).addBox(0.0F, 0.0F, -1.0F, 10.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg2, 0.0F, -0.20943951023931953F, 0.17453292519943295F);
        this.leftAntenna = new ModelPart(this, 0, 0);
        this.leftAntenna.setPos(1.0F, -3.0F, -6.0F);
        this.leftAntenna.texOffs(52, 0).addBox(0.0F, 0.0F, -10.0F, 1.0F, 0.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftAntenna, -0.4363323129985824F, -0.4363323129985824F, 0.0F);
        this.rightLeg3 = new ModelPart(this, 0, 0);
        this.rightLeg3.setPos(-2.0F, 21.0F, 2.0F);
        this.rightLeg3.texOffs(40, 36).addBox(-10.0F, 0.0F, -1.0F, 10.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg3, 0.0F, -0.20943951023931953F, -0.17453292519943295F);
        this.rightLeg2 = new ModelPart(this, 0, 0);
        this.rightLeg2.setPos(-2.0F, 21.0F, 4.0F);
        this.rightLeg2.texOffs(40, 32).addBox(-10.0F, 0.0F, -1.0F, 10.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg2, 0.0F, 0.20943951023931953F, -0.17453292519943295F);
        this.rightLeg1 = new ModelPart(this, 0, 0);
        this.rightLeg1.setPos(-2.0F, 21.0F, 6.0F);
        this.rightLeg1.texOffs(40, 28).addBox(-10.0F, 0.0F, -1.0F, 10.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg1, 0.0F, 0.6108652381980153F, -0.17453292519943295F);
        this.leftPincer = new ModelPart(this, 0, 0);
        this.leftPincer.setPos(4.0F, 2.0F, -4.0F);
        this.leftPincer.texOffs(16, 14).addBox(0.0F, 0.0F, -12.0F, 12.0F, 2.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftPincer, 0.08726646259971647F, 0.6108652381980153F, 0.0F);
        this.leftLeg1 = new ModelPart(this, 0, 0);
        this.leftLeg1.setPos(2.0F, 21.0F, 6.0F);
        this.leftLeg1.texOffs(40, 42).addBox(0.0F, 0.0F, -1.0F, 10.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg1, 0.0F, -0.6108652381980153F, 0.17453292519943295F);
        this.Head = new ModelPart(this, 0, 0);
        this.Head.setPos(0.0F, 19.0F, 0.0F);
        this.Head.addBox(-4.0F, -3.0F, -6.0F, 8.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelPart(this, 0, 0);
        this.body.setPos(0.0F, 19.0F, 8.0F);
        this.body.texOffs(0, 28).addBox(-5.0F, -8.0F, -3.0F, 10.0F, 10.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, 1.5707963267948966F, 0.0F, 0.0F);
        this.leftLeg3 = new ModelPart(this, 0, 0);
        this.leftLeg3.setPos(2.0F, 21.0F, 2.0F);
        this.leftLeg3.texOffs(40, 50).addBox(0.0F, 0.0F, -1.0F, 10.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg3, 0.0F, 0.20943951023931953F, 0.17453292519943295F);
        this.rightPincer = new ModelPart(this, 0, 0);
        this.rightPincer.setPos(-4.0F, 2.0F, -4.0F);
        this.rightPincer.texOffs(16, 0).addBox(-12.0F, 0.0F, -12.0F, 12.0F, 2.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightPincer, 0.08726646259971647F, -0.6108652381980153F, 0.0F);
        this.rightAntenna = new ModelPart(this, 0, 0);
        this.rightAntenna.setPos(-1.0F, -3.0F, -6.0F);
        this.rightAntenna.texOffs(48, 0).addBox(-1.0F, 0.0F, -10.0F, 1.0F, 0.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightAntenna, -0.4363323129985824F, 0.4363323129985824F, 0.0F);
        this.Head.addChild(this.leftAntenna);
        this.Head.addChild(this.leftPincer);
        this.Head.addChild(this.rightPincer);
        this.Head.addChild(this.rightAntenna);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(
                Head,
                body,
                leftLeg1,
                leftLeg2,
                leftLeg3,
                rightLeg1,
                rightLeg2,
                rightLeg3
        );
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if(this.riding) matrixStackIn.translate(0, -0.15F, 0);
        super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(PinchBeetleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.Head.yRot = netHeadYaw / (180F / (float) Math.PI);
        this.Head.xRot = headPitch / (180F / (float) Math.PI);

        float legZ = ((float) Math.PI / 11F);
        this.leftLeg1.zRot = legZ;
        this.rightLeg1.zRot = -legZ;
        this.leftLeg2.zRot = legZ * 0.74F;
        this.rightLeg2.zRot = -legZ * 0.74F;
        this.leftLeg3.zRot = legZ;
        this.rightLeg3.zRot = -legZ;

        float var9 = -0.0F;
        float var10 = 0.3926991F;
        this.leftLeg1.yRot = -var10 * 2.0F + var9;
        this.rightLeg1.yRot = var10 * 2.0F - var9;
        this.leftLeg2.yRot = var10 + var9;
        this.rightLeg2.yRot = -var10 - var9;
        this.leftLeg3.yRot = var10 * 2.0F + var9;
        this.rightLeg3.yRot = -var10 * 2.0F - var9;

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
    }

    @Override
    public void prepareMobModel(PinchBeetleEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {
        if (entity.isVehicle()) {
            // open jaws
            this.rightPincer.yRot = -0.3490658503988659F;
            this.leftPincer.yRot = 0.3490658503988659F;
        } else {
            // close jaws
            this.rightPincer.yRot = -0.7853981633974483F;
            this.leftPincer.yRot = 0.7853981633974483F;
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
