package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.passive.QuestRamEntity;

/**
 * ModelQuestingRam - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class QuestRamModel extends ListModel<QuestRamEntity> {
    public ModelPart rightFrontLeg;
    public ModelPart leftFrontLeg;
    public ModelPart rightBackLeg;
    public ModelPart leftBackLeg;
    public ModelPart frontTorso;
    public ModelPart horns;
    public ModelPart neck;
    public ModelPart backtorso;
    public ModelPart head;

    ModelPart[] segments;
    int[] colorOrder = new int[]{0, 8, 7, 15, 14, 1, 4, 5, 13, 3, 9, 11, 10, 2, 6, 12};

    public QuestRamModel() {
        this.texWidth = 128;
        this.texHeight = 128;
        this.head = new ModelPart(this, 0, 0);
        this.head.setPos(0.0F, -4.0F, 3.0F);
        this.head.texOffs(74, 70).addBox(-6.0F, -2.0F, -13.0F, 12.0F, 8.0F, 15.0F, 0.0F, 0.0F, 0.0F);
        this.head.texOffs(42, 71).addBox(-6.0F, -5.0F, -9.0F, 12.0F, 3.0F, 11.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(head, 0.4363323129985824F, 0.0F, 0.0F);
        this.frontTorso = new ModelPart(this, 0, 0);
        this.frontTorso.setPos(0.0F, 0.0F, 0.0F);
        this.frontTorso.addBox(-8.0F, -7.0F, -6.0F, 16.0F, 14.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.rightFrontLeg = new ModelPart(this, 0, 0);
        this.rightFrontLeg.setPos(-5.0F, 6.0F, 0.0F);
        this.rightFrontLeg.texOffs(0, 60).addBox(-3.0F, 2.0F, -3.0F, 6.0F, 16.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.rightFrontLeg.texOffs(54, 20).addBox(-4.0F, -4.0F, -5.0F, 8.0F, 10.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.neck = new ModelPart(this, 0, 0);
        this.neck.setPos(0.0F, 2.0F, -3.0F);
        this.neck.texOffs(84, 93).addBox(-5.0F, -11.0F, -2.0F, 10.0F, 12.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(neck, 0.6108652381980153F, 0.0F, 0.0F);
        this.leftBackLeg = new ModelPart(this, 0, 0);
        this.leftBackLeg.setPos(16.0F, 6.0F, 0.0F);
        this.leftBackLeg.texOffs(24, 82).addBox(-13.0F, 2.0F, -5.0F, 6.0F, 16.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.leftBackLeg.texOffs(90, 50).addBox(-14.0F, -4.0F, -7.0F, 8.0F, 10.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.leftFrontLeg = new ModelPart(this, 0, 0);
        this.leftFrontLeg.setPos(5.0F, 6.0F, 0.0F);
        this.leftFrontLeg.texOffs(24, 60).addBox(-3.0F, 2.0F, -3.0F, 6.0F, 16.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.leftFrontLeg.texOffs(90, 20).addBox(-4.0F, -4.0F, -5.0F, 8.0F, 10.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.backtorso = new ModelPart(this, 0, 0);
        this.backtorso.setPos(0.0F, 0.0F, 6.0F);
        this.backtorso.texOffs(0, 30).addBox(-8.0F, -7.0F, 8.0F, 16.0F, 14.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.horns = new ModelPart(this, 0, 0);
        this.horns.setPos(0.0F, -10.0F, -8.0F);
        this.horns.texOffs(64, 0).addBox(-9.0F, -11.0F, -1.0F, 4.0F, 10.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.horns.texOffs(48, 0).addBox(-13.0F, -11.0F, 5.0F, 4.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.horns.texOffs(92, 0).addBox(5.0F, -11.0F, -1.0F, 4.0F, 10.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.horns.texOffs(110, 0).addBox(9.0F, -11.0F, 5.0F, 4.0F, 4.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.rightBackLeg = new ModelPart(this, 0, 0);
        this.rightBackLeg.setPos(-16.0F, 6.0F, 0.0F);
        this.rightBackLeg.texOffs(0, 82).addBox(7.0F, 2.0F, -5.0F, 6.0F, 16.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.rightBackLeg.texOffs(54, 50).addBox(6.0F, -4.0F, -7.0F, 8.0F, 10.0F, 10.0F, 0.0F, 0.0F, 0.0F);

        segments = new ModelPart[16];
        for (int i = 0; i < 16; i++) {
            segments[i] = new ModelPart(this, 0, 112);
            segments[i].addBox(-8.0F, -7.0F, 8.0F, 16.0F, 14.0F, 2.0F, 0.0F, 0.0F, 0.0F);
            segments[i].setPos(0F, 0F, 10F);
            segments[i].visible = false;
            this.frontTorso.addChild(this.segments[i]);
        }

        this.horns.addChild(this.head);
        this.frontTorso.addChild(this.neck);
    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float alpha) {
        super.renderToBuffer(stack, builder, light, overlay, red, green, blue, alpha);

        for (int i = 0; i < 16; i++) {
            final float[] dyeRgb = Sheep.getColorArray(DyeColor.byId(i));
            segments[i].render(stack, builder, light, overlay, dyeRgb[0], dyeRgb[1], dyeRgb[2], alpha);
        }
    }

    @Override
    public void setupAnim(QuestRamEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.horns.xRot = headPitch / (180F / (float) Math.PI);
        this.horns.yRot = netHeadYaw / (180F / (float) Math.PI);

        //this.neck.rotateAngleY = this.head.rotateAngleY;

        this.leftBackLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.rightBackLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
    }

    @Override
    public void prepareMobModel(QuestRamEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {

        // how many colors should we display?
        int count = entity.countColorsSet();

        this.backtorso.z = 2 + 2 * count;
        this.leftBackLeg.z = 25 + 2 * count;
        this.rightBackLeg.z = 25 + 2 * count;

        // set up the colors displayed in color order
        int segmentOffset = 2;
        for (int color : colorOrder) {
            if (entity.isColorPresent(DyeColor.byId(color))) {
                segments[color].visible = true;
                segments[color].z = segmentOffset;

                segmentOffset += 2;
            } else {
                segments[color].visible = false;
            }
        }
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(
                horns,
                frontTorso,
                backtorso,
                leftFrontLeg,
                rightFrontLeg,
                leftBackLeg,
                rightBackLeg
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
}
