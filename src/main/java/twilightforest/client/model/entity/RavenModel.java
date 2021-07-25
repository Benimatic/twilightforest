package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.passive.RavenEntity;

/**
 * ModelForestRaven - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class RavenModel extends ListModel<RavenEntity> {
    public ModelPart head;
    public ModelPart torso;
    public ModelPart rightWing;
    public ModelPart leftWing;
    public ModelPart rightLeg;
    public ModelPart leftLeg;
    public ModelPart tail;

    public RavenModel() {
        this.texWidth = 32;
        this.texHeight = 32;
        this.torso = new ModelPart(this, 0, 0);
        this.torso.setPos(0.0F, 18.5F, -2.0F);
        this.torso.texOffs(0, 6).addBox(-2.0F, -1.5F, 0.0F, 4.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(torso, -0.4363323129985824F, 0.0F, 0.0F);
        this.leftWing = new ModelPart(this, 0, 0);
        this.leftWing.setPos(2.0F, -1.0F, 2.0F);
        this.leftWing.texOffs(14, 15).addBox(0.0F, 0.0F, -1.0F, 1.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftWing, 0.2617993877991494F, 0.0F, 0.0F);
        this.leftLeg = new ModelPart(this, 0, 0);
        this.leftLeg.setPos(1.0F, 0.0F, 0.0F);
        this.leftLeg.texOffs(14, 15).addBox(0.0F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftLeg, 0.7853981633974483F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 0, 0);
        this.head.setPos(0.0F, 18.5F, -2.0F);
        this.head.addBox(-1.5F, -1.0F, -2.0F, 3.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.head.texOffs(9, 0).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.rightWing = new ModelPart(this, 0, 0);
        this.rightWing.setPos(-2.0F, -1.0F, 2.0F);
        this.rightWing.texOffs(0, 15).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightWing, 0.2617993877991494F, 0.0F, 0.0F);
        this.rightLeg = new ModelPart(this, 0, 0);
        this.rightLeg.setPos(-1.0F, 0.0F, 0.0F);
        this.rightLeg.texOffs(8, 15).addBox(0.0F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightLeg, 0.7853981633974483F, 0.0F, 0.0F);
        this.tail = new ModelPart(this, 0, 0);
        this.tail.setPos(0.0F, -1.5F, 6.0F);
        this.tail.texOffs(8, 0).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(tail, -0.4363323129985824F, 0.0F, 0.0F);
        this.torso.addChild(this.leftWing);
        this.torso.addChild(this.rightWing);
        this.torso.addChild(this.tail);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(
                head,
                torso,
                leftLeg,
                rightLeg
        );
    }

    @Override
    public void setupAnim(RavenEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.xRot = headPitch / (180F / (float) Math.PI);
        head.yRot = netHeadYaw / (180F / (float) Math.PI);
        head.zRot = netHeadYaw > 5 ? -0.2617994F : 0;

        leftLeg.xRot = Mth.cos (limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        rightLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

        rightWing.zRot = ageInTicks;
        leftWing.zRot = -ageInTicks;

        if (entity.isBirdLanded()) {
            rightLeg.y = 21;
            leftLeg.y = 21;
        } else {
            rightLeg.y = 20F;
            leftLeg.y = 20F;
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
