package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.passive.SquirrelEntity;

/**
 * ModelForestSquirrel - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class SquirrelModel extends ListModel<SquirrelEntity> {
    public ModelPart body;
    public ModelPart head;
    public ModelPart rightFrontLeg;
    public ModelPart leftFrontLeg;
    public ModelPart rightBackLeg;
    public ModelPart leftBackLeg;
    public ModelPart tail1;
    public ModelPart tail2;

    public SquirrelModel() {
        this.texWidth = 32;
        this.texHeight = 32;
        this.rightFrontLeg = new ModelPart(this, 0, 0);
        this.rightFrontLeg.setPos(-1.5F, 23.0F, -2.5F);
        this.rightFrontLeg.texOffs(0, 16).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelPart(this, 0, 0);
        this.body.setPos(0.0F, 23.0F, 0.0F);
        this.body.texOffs(0, 8).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.leftBackLeg = new ModelPart(this, 0, 0);
        this.leftBackLeg.setPos(1.5F, 23.0F, 1.5F);
        this.leftBackLeg.texOffs(4, 18).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 0, 0);
        this.head.setPos(0.0F, 20.0F, -3.0F);
        this.head.addBox(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.head.addBox(-2.0F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.head.texOffs(0, 2).addBox(1.0F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.tail2 = new ModelPart(this, 0, 0);
        this.tail2.setPos(0.0F, 4.0F, 0.5F);
        this.tail2.texOffs(13, 11).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 3.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.leftFrontLeg = new ModelPart(this, 0, 0);
        this.leftFrontLeg.setPos(1.5F, 23.0F, -2.5F);
        this.leftFrontLeg.texOffs(4, 16).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.tail1 = new ModelPart(this, 0, 0);
        this.tail1.setPos(0.0F, -3.0F, 2.0F);
        this.tail1.texOffs(18, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail1, 2.530727415391778F, 0.0F, 0.0F);
        this.rightBackLeg = new ModelPart(this, 0, 0);
        this.rightBackLeg.setPos(-1.5F, 23.0F, 1.5F);
        this.rightBackLeg.texOffs(0, 18).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.tail1.addChild(this.tail2);
        this.body.addChild(this.tail1);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(
                body,
                leftFrontLeg,
                rightFrontLeg,
                leftBackLeg,
                rightBackLeg,
                head
        );
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
    public void setupAnim(SquirrelEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch / (180F / (float) Math.PI);
        this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leftBackLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.rightBackLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        if (limbSwingAmount > 0.2) {
            float wiggle = Math.min(limbSwingAmount, 0.6F);
            this.tail2.xRot = (Mth.cos(ageInTicks * 0.6662F) - (float) Math.PI / 3) * wiggle;
            this.tail1.xRot = 2.5F + Mth.cos(ageInTicks * 0.7774F) * 1.2F * wiggle;
        } else {
            this.tail2.xRot = 0.2F + Mth.cos(ageInTicks * 0.3335F) * 0.25F;
            this.tail1.xRot = 2.5F + Mth.cos(ageInTicks * 0.4445F) * 0.20F;
        }
    }
}
