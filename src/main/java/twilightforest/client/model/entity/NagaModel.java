package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.boss.NagaEntity;
import twilightforest.entity.boss.NagaSegmentEntity;

/**
 * ModelNagaHead - Undefined
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class NagaModel<T extends Entity> extends ListModel<T> {
    public ModelPart head;
    public ModelPart tongue;
    public ModelPart body;
    private T entity;

    public NagaModel() {
        this.texWidth = 128;
        this.texHeight = 64;
        this.tongue = new ModelPart(this, 0, 0);
        this.tongue.setPos(0.0F, 10.0F, -16.0F);
        this.tongue.texOffs(84, 0).addBox(-6.0F, 0.0F, -12.0F, 12.0F, 0.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tongue, 0.4363323129985824F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 0, 0);
        this.head.setPos(0.0F, 8.0F, 0.0F);
        this.head.addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.head.addChild(this.tongue);

        this.body = new ModelPart(this, 0, 0);
        this.body.setPos(0.0F, 8.0F, 0.0F);
        this.body.addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);

    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float alpha) {
        if (entity instanceof NagaEntity) {
            head.render(stack, builder, light, overlay, red, green, blue, alpha * 2);
        } else if (entity instanceof NagaSegmentEntity) {
            body.render(stack, builder, light, overlay, red, green, blue, alpha * 2);
        } else {
            head.render(stack, builder, light, overlay, red, green, blue, alpha * 2);
        }
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(head, body);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) { }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
