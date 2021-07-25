package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.passive.BighornEntity;

/**
 * ModelBighornSheep - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class BighornModel<T extends BighornEntity> extends SheepModel<T> {

    public ModelPart rightHorn;
    public ModelPart leftHorn;

    public BighornModel() {
        this.texWidth = 64;
        this.texHeight = 64;
        this.leg2 = new ModelPart(this, 0, 0);
        this.leg2.setPos(3.0F, 12.0F, -3.0F);
        this.leg2.texOffs(16, 32).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.leg0 = new ModelPart(this, 0, 0);
        this.leg0.setPos(-3.5F, 12.0F, 9.0F);
        this.leg0.texOffs(0, 48).addBox(-1.5F, 0.0F, -4.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelPart(this, 0, 0);
        this.body.setPos(0.0F, 10.0F, 6.0F);
        this.body.texOffs(34, 13).addBox(-4.5F, -14.0F, -3.0F, 9.0F, 16.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, 1.5707963267948966F, 0.0F, 0.0F);
        this.leg1 = new ModelPart(this, 0, 0);
        this.leg1.setPos(3.5F, 12.0F, 9.0F);
        this.leg1.texOffs(16, 48).addBox(-2.5F, 0.0F, -4.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.leg2 = new ModelPart(this, 0, 0);
        this.leg2.setPos(-3.0F, 12.0F, -3.0F);
        this.leg2.texOffs(0, 32).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.leftHorn = new ModelPart(this, 0, 0);
        this.leftHorn.setPos(2.0F, -3.0F, -1.0F);
        this.leftHorn.texOffs(16, 0).addBox(0.0F, -1.0F, -7.0F, 3.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.leftHorn.texOffs(16, 8).addBox(1.0F, 2.0F, -9.0F, 3.0F, 2.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.leftHorn.texOffs(20, 15).addBox(2.0F, 0.0F, -11.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftHorn, 0.0F, -0.39269908169872414F, -0.2181661564992912F);
        this.head = new ModelPart(this, 0, 0);
        this.head.setPos(0.0F, 5.0F, -8.0F);
        this.head.texOffs(38, 0).addBox(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.rightHorn = new ModelPart(this, 0, 0);
        this.rightHorn.setPos(-2.0F, -3.0F, -1.0F);
        this.rightHorn.addBox(-3.0F, -1.0F, -7.0F, 3.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.rightHorn.texOffs(0, 8).addBox(-4.0F, 2.0F, -9.0F, 3.0F, 2.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.rightHorn.texOffs(4, 15).addBox(-4.0F, 0.0F, -11.0F, 2.0F, 3.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightHorn, 0.0F, 0.39269908169872414F, 0.2181661564992912F);
        this.head.addChild(this.leftHorn);
        this.head.addChild(this.rightHorn);
    }



    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.young) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.0, 0.5, 0.25);
            ImmutableList.of(this.head).forEach((modelRenderer) -> {
                modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
            matrixStackIn.popPose();

            matrixStackIn.pushPose();
            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            matrixStackIn.translate(0.0, 1.5, 0.0);
            ImmutableList.of(this.leg1, this.leg0, this.body, this.leg3, this.leg2).forEach((modelRenderer) -> {
                modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
            matrixStackIn.popPose();
        } else {
            ImmutableList.of(this.leg1, this.leg0, this.body, this.leg3, this.leg2, this.head).forEach((modelRenderer) -> {
                modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
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
