package twilightforest.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import twilightforest.entity.DeathTomeEntity;

public class DeathTomeModel extends EntityModel<DeathTomeEntity> {
    private final ModelPart everything;

    private final ModelPart book;
    private final ModelPart loosePage1;
    private final ModelPart loosePage2;
    private final ModelPart loosePage3;
    private final ModelPart loosePage4;
    // Book render
    private final ModelPart coverRight = (new ModelPart(64, 32, 0, 0)).addBox(-6.0F, -5.0F, -0.005F, 6.0F, 10.0F, 0.005F);
    private final ModelPart coverLeft = (new ModelPart(64, 32, 16, 0)).addBox(0.0F, -5.0F, -0.005F, 6.0F, 10.0F, 0.005F);
    private final ModelPart pagesRight;
    private final ModelPart pagesLeft;
    private final ModelPart flippingPageRight;
    private final ModelPart flippingPageLeft;

    public DeathTomeModel() {
        everything = (new ModelPart(this)).texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 0, 0, 0);

        book = (new ModelPart(this)).texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 0, 0, 0);

        this.pagesRight = (new ModelPart(64, 32, 0, 10)).addBox(0.0F, -4.0F, -0.99F, 5.0F, 8.0F, 1.0F);
        this.pagesLeft = (new ModelPart(64, 32, 12, 10)).addBox(0.0F, -4.0F, -0.01F, 5.0F, 8.0F, 1.0F);
        this.flippingPageRight = (new ModelPart(64, 32, 24, 10)).addBox(0.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.005F);
        this.flippingPageLeft = (new ModelPart(64, 32, 24, 10)).addBox(0.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.005F);
        this.coverRight.setPos(0.0F, 0.0F, -1.0F);
        this.coverLeft.setPos(0.0F, 0.0F, 1.0F);
        ModelPart bookSpine = (new ModelPart(64, 32, 12, 0)).addBox(-1.0F, -5.0F, 0.0F, 2.0F, 10.0F, 0.005F);
        bookSpine.yRot = ((float) Math.PI / 2F);

        book.addChild(coverRight);
        book.addChild(coverLeft);
        book.addChild(bookSpine);
        book.addChild(pagesRight);
        book.addChild(pagesLeft);
        book.addChild(flippingPageRight);
        book.addChild(flippingPageLeft);

        loosePage1 = (new ModelPart(this)).texOffs(24, 10).addBox(0F, -4F, -8F, 5, 8, 0.005F);
        loosePage2 = (new ModelPart(this)).texOffs(24, 10).addBox(0F, -4F, 9F, 5, 8, 0.005F);
        loosePage3 = (new ModelPart(this)).texOffs(24, 10).addBox(0F, -4F, 11F, 5, 8, 0.005F);
        loosePage4 = (new ModelPart(this)).texOffs(24, 10).addBox(0F, -4F, 7F, 5, 8, 0.005F);

        //everything.addChild(book);
        everything.addChild(loosePage1);
        everything.addChild(loosePage2);
        everything.addChild(loosePage3);
        everything.addChild(loosePage4);
    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float scale) {
        this.everything.render(stack, builder, light, overlay);
        this.book.render(stack, builder, light, overlay);
    }

    @Override
    public void setupAnim(DeathTomeEntity entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
        book.zRot = -0.8726646259971647F;

        this.everything.yRot = customAngle / (180F / (float) Math.PI) + (float) Math.PI / 2.0F;
    }

    @Override
    public void prepareMobModel(DeathTomeEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {
        float bounce = entity.tickCount + partialTicks;
        float open = 0.9f;
        float flipRight = 0.4f;
        float flipLeft = 0.6f;

        // hoveriness
        book.setPos(0, 4 + Mth.sin((bounce) * 0.3F) * 2.0F, 0);

        // book openness
        float openAngle = (Mth.sin(bounce * 0.4F) * 0.3F + 1.25F) * open;
        this.coverRight.yRot = (float) Math.PI + openAngle;
        this.coverLeft.yRot = -openAngle;
        this.pagesRight.yRot = openAngle;
        this.pagesLeft.yRot = -openAngle;
        this.flippingPageRight.yRot = openAngle - openAngle * 2.0F * flipRight;
        this.flippingPageLeft.yRot = openAngle - openAngle * 2.0F * flipLeft;
        this.pagesRight.x = Mth.sin(openAngle);
        this.pagesLeft.x = Mth.sin(openAngle);
        this.flippingPageRight.x = Mth.sin(openAngle);
        this.flippingPageLeft.x = Mth.sin(openAngle);

        // page rotations
        loosePage1.yRot = (bounce) / 4.0F;
        loosePage1.xRot = Mth.sin((bounce) / 5.0F) / 3.0F;
        loosePage1.zRot = Mth.cos((bounce) / 5.0F) / 5.0F;

        loosePage2.yRot = (bounce) / 3.0F;
        loosePage2.xRot = Mth.sin((bounce) / 5.0F) / 3.0F;
        loosePage2.zRot = Mth.cos((bounce) / 5.0F) / 4.0F + 2;

        loosePage3.yRot = (bounce) / 4.0F;
        loosePage3.xRot = -Mth.sin((bounce) / 5.0F) / 3.0F;
        loosePage3.zRot = Mth.cos((bounce) / 5.0F) / 5.0F - 1.0F;

        loosePage4.yRot = (bounce) / 4.0F;
        loosePage4.xRot = -Mth.sin((bounce) / 2.0F) / 4.0F;
        loosePage4.zRot = Mth.cos((bounce) / 7.0F) / 5.0F;
    }
}
