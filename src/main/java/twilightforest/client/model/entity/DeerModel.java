package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.passive.DeerEntity;

/**
 * ModelWildDeer - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class DeerModel extends QuadrupedModel<DeerEntity> {
    public ModelPart neck;
    public ModelPart rightAntler;
    public ModelPart leftAntler;

    public DeerModel() {
        super(12, 0.0F, false, 4.0F, 4.0F, 2.0F, 2.0F, 10);
        this.texWidth = 64;
        this.texHeight = 48;
        this.body = new ModelPart(this, 0, 0);
        this.body.setPos(0.0F, 10.0F, 7.0F);
        this.body.texOffs(36, 6).addBox(-3.0F, -14.0F, -2.0F, 6.0F, 18.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(body, 1.5707963267948966F, 0.0F, 0.0F);
        this.rightAntler = new ModelPart(this, 0, 0);
        this.rightAntler.setPos(-1.0F, -4.0F, 0.0F);
        this.rightAntler.texOffs(0, 16).addBox(0.0F, -16.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightAntler, 0.0F, -0.39269908169872414F, -0.39269908169872414F);
        this.leftAntler = new ModelPart(this, 0, 0);
        this.leftAntler.setPos(1.0F, -4.0F, 0.0F);
        this.leftAntler.texOffs(32, 16).addBox(0.0F, -16.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftAntler, 0.0F, 0.39269908169872414F, 0.39269908169872414F);
        this.leg3 = new ModelPart(this, 0, 0);
        this.leg3.setPos(2.0F, 12.0F, -4.5F);
        this.leg3.texOffs(10, 0).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 12.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.leg2 = new ModelPart(this, 0, 0);
        this.leg2.setPos(-2.0F, 12.0F, -4.5F);
        this.leg2.addBox(-1.0F, 0.0F, -1.5F, 2.0F, 12.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.leg1 = new ModelPart(this, 0, 0);
        this.leg1.setPos(2.0F, 12.0F, 9.5F);
        this.leg1.texOffs(10, 15).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 12.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 0, 0);
        this.head.setPos(0.0F, -9.0F, 0.0F);
        this.head.texOffs(24, 2).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 6.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.head.texOffs(52, 0).addBox(-1.5F, -1.0F, -7.0F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.neck = new ModelPart(this, 22, 14);
        this.neck.setPos(0.0F, 8.0F, -5.0F);
        this.neck.addBox(-1.5F, -8.0F, -2.0F, 3.0F, 9.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(neck, 0.4363323129985824F, 0.0F, 0.0F);
        this.setRotateAngle(head, -0.4363323129985824F, 0.0F, 0.0F);
        this.leg0 = new ModelPart(this, 0, 0);
        this.leg0.setPos(-2.0F, 12.0F, 9.5F);
        this.leg0.texOffs(0, 15).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 12.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.head.addChild(this.rightAntler);
        this.head.addChild(this.leftAntler);
        this.neck.addChild(this.head);
    }

    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.neck);
    }

    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.leg0, this.leg1, this.leg2, this.leg3);
    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float scale) {
        if (young) {
            stack.pushPose();
            stack.scale(0.75F, 0.75F, 0.75F);
            stack.translate(0F, 0.95F, 0.15F);
            this.headParts().forEach((modelRenderer) -> modelRenderer.render(stack, builder, light, overlay, red, green, blue, scale));
            stack.popPose();

            stack.pushPose();
            stack.scale(0.5F, 0.5F, 0.5F);
            stack.translate(0F, 1.5F, 0F);
            this.bodyParts().forEach((modelRenderer) -> modelRenderer.render(stack, builder, light, overlay, red, green, blue, scale));
            stack.popPose();
        } else {
            this.headParts().forEach((renderer) -> renderer.render(stack, builder, light, overlay, red, green, blue, scale));
            this.bodyParts().forEach((renderer) -> renderer.render(stack, builder, light, overlay, red, green, blue, scale));
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
