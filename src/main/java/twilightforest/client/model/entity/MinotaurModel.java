package twilightforest.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.MinotaurEntity;

/**
 * ModelMinotaur - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class MinotaurModel extends HumanoidModel<MinotaurEntity> {

    public MinotaurModel() {
        super(0, 0, 64, 64);
        this.rightLeg = new ModelPart(this, 0, 0);
        this.rightLeg.setPos(-2.5F, 12.0F, 0.0F);
        this.rightLeg.texOffs(0, 26).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 12.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.rightArm = new ModelPart(this, 0, 0);
        this.rightArm.setPos(-7.5F, -4.0F, 0.0F);
        this.rightArm.texOffs(20, 26).addBox(-3.0F, -4.0F, -2.5F, 4.0F, 14.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelPart(this, 0, 0);
        this.body.setPos(0.0F, -2.0F, 0.0F);
        this.body.texOffs(34, 0).addBox(-5.0F, -2.0F, -2.5F, 10.0F, 14.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 0, 0);
        this.head.setPos(0.0F, -2.0F, 0.0F);
        this.head.addBox(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.head.texOffs(25, 1).addBox(-3.0F, -5.0F, -5.0F, 6.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.head.texOffs(0, 16).addBox(-8.0F, -9.0F, -1.0F, 4.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.head.texOffs(0, 20).addBox(-8.0F, -11.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.head.texOffs(12, 16).addBox(4.0F, -9.0F, -1.0F, 4.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.head.texOffs(12, 20).addBox(6.0F, -11.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg = new ModelPart(this, 0, 0);
        this.leftLeg.setPos(2.5F, 12.0F, 0.0F);
        this.leftLeg.texOffs(0, 43).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 12.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.leftArm = new ModelPart(this, 0, 0);
        this.leftArm.setPos(7.5F, -4.0F, 0.0F);
        this.leftArm.texOffs(20, 45).addBox(0.0F, -4.0F, -2.5F, 4.0F, 14.0F, 5.0F, 0.0F, 0.0F, 0.0F);

        this.hat = new ModelPart(this, 0, 0);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if(this.riding) matrixStackIn.translate(0, 0.5F, 0);
        super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
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
