package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.passive.TinyBirdEntity;

/**
 * ModelTinyBird - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class TinyBirdModel extends AgeableListModel<TinyBirdEntity> {
    public ModelPart head;
    public ModelPart body;
    public ModelPart rightFoot;
    public ModelPart leftFoot;
    public ModelPart rightWing;
    public ModelPart leftWing;
    public ModelPart tail;

    public TinyBirdModel() {
        this.texWidth = 32;
        this.texHeight = 32;
        this.body = new ModelPart(this, 0, 0);
        this.body.setPos(0.0F, 20.0F, 0.0F);
        this.body.texOffs(12, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.leftFoot = new ModelPart(this, 0, 0);
        this.leftFoot.setPos(1.0F, 23.0F, 2.0F);
        this.leftFoot.texOffs(0, 11).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.tail = new ModelPart(this, 0, 0);
        this.tail.setPos(0.0F, 1.0F, 3.0F);
        this.tail.texOffs(1, 6).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, 0.4363323129985824F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 0, 0);
        this.head.setPos(0.0F, 21.0F, 0.0F);
        this.head.addBox(-1.5F, -2.0F, -2.0F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.head.texOffs(9, 0).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.head.texOffs(0, 6).addBox(-1.5F, -5.0F, 1.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.rightWing = new ModelPart(this, 0, 0);
        this.rightWing.setPos(-2.0F, 0.0F, 1.0F);
        this.rightWing.texOffs(24, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.leftWing = new ModelPart(this, 0, 0);
        this.leftWing.setPos(2.0F, 0.0F, 1.0F);
        this.leftWing.texOffs(24, 5).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.rightFoot = new ModelPart(this, 0, 0);
        this.rightFoot.setPos(-1.0F, 23.0F, 2.0F);
        this.rightFoot.texOffs(0, 9).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.tail);
        this.body.addChild(this.rightWing);
        this.body.addChild(this.leftWing);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(
                head,
                body,
                rightFoot,
                leftFoot
        );
    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float scale) {
        if (young) {
            float f = 2.0F;
            stack.pushPose();
            stack.translate(0.0F, 5F * scale, 0.75F * scale);
            this.headParts().forEach((renderer) -> renderer.render(stack, builder, light, overlay, red, green, blue, scale));
            stack.popPose();
            stack.pushPose();
            stack.scale(1.0F / f, 1.0F / f, 1.0F / f);
            stack.translate(0.0F, 24F * scale, 0.0F);
            this.bodyParts().forEach((renderer) -> renderer.render(stack, builder, light, overlay, red, green, blue, scale));
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

    @Override
    public void setupAnim(TinyBirdEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.xRot = headPitch / (180F / (float) Math.PI);
        head.yRot = netHeadYaw / (180F / (float) Math.PI);

        rightFoot.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        leftFoot.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

        rightWing.zRot = ageInTicks;
        leftWing.zRot = -ageInTicks;

        if (entity.isBirdLanded()) {
            rightFoot.y = 23;
            leftFoot.y = 23;
        } else {
            rightFoot.y = 22.5F;
            leftFoot.y = 22.5F;
        }
    }
}
