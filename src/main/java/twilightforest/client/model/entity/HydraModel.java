package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.boss.HydraEntity;

/**
 * ModelHydra - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class HydraModel extends ListModel<HydraEntity> {
    public ModelPart torso;
    public ModelPart rightLeg;
    public ModelPart leftLeg;
    public ModelPart neck;
    public ModelPart neck_1;
    public ModelPart neck_2;
    public ModelPart tail;
    public ModelPart neck_3;
    public ModelPart neck_4;
    public ModelPart head;
    public ModelPart mouth;
    public ModelPart plate;
    public ModelPart neck_5;
    public ModelPart neck_6;
    public ModelPart head_1;
    public ModelPart mouth_1;
    public ModelPart plate_1;
    public ModelPart neck_7;
    public ModelPart neck_8;
    public ModelPart head_2;
    public ModelPart mouth_2;
    public ModelPart plate_2;
    public ModelPart tail_1;
    public ModelPart tail_2;
    public ModelPart tail_3;

    public HydraModel() {
        this.texWidth = 512;
        this.texHeight = 256;
        this.tail = new ModelPart(this, 0, 0);
        this.tail.setPos(0.0F, 8.0F, 80.0F);
        this.tail.texOffs(260, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.tail.texOffs(0, 0).addBox(-2.0F, -24.0F, 0.0F, 4.0F, 8.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.neck_5 = new ModelPart(this, 0, 0);
        this.neck_5.setPos(-8.0F, -24.0F, -16.0F);
        this.neck_5.texOffs(260, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.neck_5.addBox(-2.0F, -24.0F, 0.0F, 4.0F, 8.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.tail_3 = new ModelPart(this, 0, 0);
        this.tail_3.setPos(0.0F, 0.0F, 32.0F);
        this.tail_3.texOffs(260, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.tail_3.texOffs(0, 0).addBox(-2.0F, -24.0F, 0.0F, 4.0F, 8.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.neck_2 = new ModelPart(this, 0, 0);
        this.neck_2.setPos(42.0F, -48.0F, 0.0F);
        this.neck_2.texOffs(260, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.neck_2.addBox(-2.0F, -24.0F, 0.0F, 4.0F, 8.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.neck_4 = new ModelPart(this, 0, 0);
        this.neck_4.setPos(-16.0F, -24.0F, -16.0F);
        this.neck_4.texOffs(260, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.neck_4.addBox(-2.0F, -24.0F, 0.0F, 4.0F, 8.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.neck = new ModelPart(this, 0, 0);
        this.neck.setPos(-42.0F, -48.0F, 0.0F);
        this.neck.texOffs(260, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.neck.addBox(-2.0F, -24.0F, 0.0F, 4.0F, 8.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.neck_7 = new ModelPart(this, 0, 0);
        this.neck_7.setPos(16.0F, -24.0F, -16.0F);
        this.neck_7.texOffs(260, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.neck_7.addBox(-2.0F, -24.0F, 0.0F, 4.0F, 8.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 0, 0);
        this.head.setPos(-8.0F, -24.0F, -16.0F);
        this.head.texOffs(260, 64).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.head.texOffs(236, 128).addBox(-16.0F, -2.0F, -40.0F, 32.0F, 10.0F, 24.0F, 0.0F, 0.0F, 0.0F);
        this.head.texOffs(356, 70).addBox(-12.0F, 8.0F, -36.0F, 24.0F, 6.0F, 20.0F, 0.0F, 0.0F, 0.0F);
        this.neck_1 = new ModelPart(this, 0, 0);
        this.neck_1.setPos(0.0F, -58.0F, -16.0F);
        this.neck_1.texOffs(260, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.neck_1.addBox(-2.0F, -24.0F, 0.0F, 4.0F, 8.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.plate_1 = new ModelPart(this, 0, 0);
        this.plate_1.setPos(0.0F, 0.0F, -1.0F);
        this.plate_1.texOffs(388, 0).addBox(-24.0F, -48.0F, 0.0F, 48.0F, 48.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.plate_1.texOffs(220, 0).addBox(-4.0F, -32.0F, -8.0F, 8.0F, 32.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(plate_1, -0.7853981633974483F, 0.0F, 0.0F);
        this.mouth_1 = new ModelPart(this, 0, 0);
        this.mouth_1.setPos(0.0F, 8.0F, -16.0F);
        this.mouth_1.texOffs(240, 162).addBox(-15.0F, 0.0F, -24.0F, 30.0F, 8.0F, 24.0F, 0.0F, 0.0F, 0.0F);
        this.tail_2 = new ModelPart(this, 0, 0);
        this.tail_2.setPos(0.0F, 0.0F, 32.0F);
        this.tail_2.texOffs(260, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.tail_2.texOffs(0, 0).addBox(-2.0F, -24.0F, 0.0F, 4.0F, 8.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.torso = new ModelPart(this, 0, 0);
        this.torso.setPos(0.0F, -32.0F, 0.0F);
        this.torso.addBox(-45.0F, -12.0F, -20.0F, 90.0F, 96.0F, 40.0F, 0.0F, 0.0F, 0.0F);
        this.torso.texOffs(88, 136).addBox(-2.0F, 20.0F, 20.0F, 4.0F, 16.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.torso.texOffs(120, 136).addBox(-2.0F, 48.0F, 20.0F, 4.0F, 16.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(torso, 1.117010721276371F, 0.0F, 0.0F);
        this.rightLeg = new ModelPart(this, 0, 0);
        this.rightLeg.setPos(-40.0F, -20.0F, -12.0F);
        this.rightLeg.texOffs(0, 136).addBox(-14.0F, -8.0F, -16.0F, 28.0F, 52.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.rightLeg.texOffs(0, 220).addBox(-14.0F, 36.0F, -22.0F, 28.0F, 8.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.tail_1 = new ModelPart(this, 0, 0);
        this.tail_1.setPos(0.0F, 0.0F, 32.0F);
        this.tail_1.texOffs(260, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.tail_1.texOffs(0, 0).addBox(-2.0F, -24.0F, 0.0F, 4.0F, 8.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.head_2 = new ModelPart(this, 0, 0);
        this.head_2.setPos(8.0F, -24.0F, -16.0F);
        this.head_2.texOffs(260, 64).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.head_2.texOffs(236, 128).addBox(-16.0F, -2.0F, -40.0F, 32.0F, 10.0F, 24.0F, 0.0F, 0.0F, 0.0F);
        this.head_2.texOffs(356, 70).addBox(-12.0F, 8.0F, -36.0F, 24.0F, 6.0F, 20.0F, 0.0F, 0.0F, 0.0F);
        this.mouth = new ModelPart(this, 0, 0);
        this.mouth.setPos(0.0F, 8.0F, -16.0F);
        this.mouth.texOffs(240, 162).addBox(-15.0F, 0.0F, -24.0F, 30.0F, 8.0F, 24.0F, 0.0F, 0.0F, 0.0F);
        this.head_1 = new ModelPart(this, 0, 0);
        this.head_1.setPos(0.0F, -32.0F, -16.0F);
        this.head_1.texOffs(260, 64).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.head_1.texOffs(236, 128).addBox(-16.0F, -2.0F, -40.0F, 32.0F, 10.0F, 24.0F, 0.0F, 0.0F, 0.0F);
        this.head_1.texOffs(356, 70).addBox(-12.0F, 8.0F, -36.0F, 24.0F, 6.0F, 20.0F, 0.0F, 0.0F, 0.0F);
        this.plate = new ModelPart(this, 0, 0);
        this.plate.setPos(0.0F, 0.0F, -1.0F);
        this.plate.texOffs(388, 0).addBox(-24.0F, -48.0F, 0.0F, 48.0F, 48.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.plate.texOffs(220, 0).addBox(-4.0F, -32.0F, -8.0F, 8.0F, 32.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(plate, -0.7853981633974483F, 0.0F, 0.0F);
        this.neck_6 = new ModelPart(this, 0, 0);
        this.neck_6.setPos(8.0F, -24.0F, -16.0F);
        this.neck_6.texOffs(260, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.neck_6.addBox(-2.0F, -24.0F, 0.0F, 4.0F, 8.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.mouth_2 = new ModelPart(this, 0, 0);
        this.mouth_2.setPos(0.0F, 8.0F, -16.0F);
        this.mouth_2.texOffs(240, 162).addBox(-15.0F, 0.0F, -24.0F, 30.0F, 8.0F, 24.0F, 0.0F, 0.0F, 0.0F);
        this.neck_8 = new ModelPart(this, 0, 0);
        this.neck_8.setPos(16.0F, -24.0F, -16.0F);
        this.neck_8.texOffs(260, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.neck_8.addBox(-2.0F, -24.0F, 0.0F, 4.0F, 8.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg = new ModelPart(this, 0, 0);
        this.leftLeg.setPos(40.0F, -20.0F, -12.0F);
        this.leftLeg.texOffs(120, 136).addBox(-14.0F, -8.0F, -16.0F, 28.0F, 52.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg.texOffs(68, 220).addBox(-14.0F, 36.0F, -22.0F, 28.0F, 8.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.plate_2 = new ModelPart(this, 0, 0);
        this.plate_2.setPos(0.0F, 0.0F, -1.0F);
        this.plate_2.texOffs(388, 0).addBox(-24.0F, -48.0F, 0.0F, 48.0F, 48.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.plate_2.texOffs(220, 0).addBox(-4.0F, -32.0F, -8.0F, 8.0F, 32.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(plate_2, -0.7853981633974483F, 0.0F, 0.0F);
        this.neck_3 = new ModelPart(this, 0, 0);
        this.neck_3.setPos(-16.0F, -24.0F, -16.0F);
        this.neck_3.texOffs(260, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
        this.neck_3.addBox(-2.0F, -22.0F, 0.0F, 4.0F, 6.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.neck_1.addChild(this.neck_5);
        this.tail_2.addChild(this.tail_3);
        this.neck_3.addChild(this.neck_4);
        this.neck_2.addChild(this.neck_7);
        this.neck_4.addChild(this.head);
        this.head_1.addChild(this.plate_1);
        this.head_1.addChild(this.mouth_1);
        this.tail_1.addChild(this.tail_2);
        this.tail.addChild(this.tail_1);
        this.neck_8.addChild(this.head_2);
        this.head.addChild(this.mouth);
        this.neck_6.addChild(this.head_1);
        this.head.addChild(this.plate);
        this.neck_5.addChild(this.neck_6);
        this.head_2.addChild(this.mouth_2);
        this.neck_7.addChild(this.neck_8);
        this.head_2.addChild(this.plate_2);
        this.neck.addChild(this.neck_3);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(
                torso,
                leftLeg,
                rightLeg,
                tail
        );
    }

    @Override
    public void setupAnim(HydraEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

        leftLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        rightLeg.xRot = Mth.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;

        leftLeg.yRot = 0.0F;
        rightLeg.yRot = 0.0F;
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
