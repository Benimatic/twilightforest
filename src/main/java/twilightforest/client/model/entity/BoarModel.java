package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.passive.BoarEntity;

/**
 * ModelWildBoar - Undefined
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class BoarModel<T extends BoarEntity> extends AgeableListModel<T> {
    public ModelPart part1;
    public ModelPart torso;
    public ModelPart rightFrontLeg;
    public ModelPart leftFrontLeg;
    public ModelPart rightBackLeg;
    public ModelPart leftBackLeg;

    public BoarModel() {
        this.texWidth = 64;
        this.texHeight = 64;
        this.rightFrontLeg = new ModelPart(this, 0, 0);
        this.rightFrontLeg.setPos(-2.9F, 18.0F, -2.0F);
        this.rightFrontLeg.texOffs(0, 13).addBox(-2.0F, 0.0F, -1.9F, 4.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.leftFrontLeg = new ModelPart(this, 0, 0);
        this.leftFrontLeg.setPos(2.9F, 18.0F, -2.0F);
        this.leftFrontLeg.texOffs(0, 23).addBox(-2.0F, 0.0F, -1.9F, 4.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.rightBackLeg = new ModelPart(this, 0, 0);
        this.rightBackLeg.setPos(-3.1F, 18.0F, 9.0F);
        this.rightBackLeg.texOffs(0, 33).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.leftBackLeg = new ModelPart(this, 0, 0);
        this.leftBackLeg.setPos(3.1F, 18.0F, 9.0F);
        this.leftBackLeg.texOffs(0, 43).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.part1 = new ModelPart(this, 0, 0);
        this.part1.setPos(0.0F, 15.5F, -5.0F);
        this.part1.addBox(-4.0F, -4.0F, -5.0F, 8.0F, 7.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.part1.texOffs(46, 22).addBox(-3.0F, -1.0F, -8.0F, 6.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.part1.texOffs(28, 0).addBox(-4.0F, 0.0F, -8.0F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.part1.texOffs(28, 3).addBox(3.0F, 0.0F, -8.0F, 1.0F, 2.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.torso = new ModelPart(this, 0, 0);
        this.torso.setPos(0.0F, 19.0F, 2.0F);
        this.torso.texOffs(28, 0).addBox(-5.0F, -6.0F, 0.0F, 10.0F, 14.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(torso, 1.6580627893946132F, 0.0F, 0.0F);
    }

    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.part1.xRot = headPitch * ((float)Math.PI / 180F);
        this.part1.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.torso.xRot = ((float)Math.PI / 2F);
        this.rightBackLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftBackLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.part1);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.torso, this.leftBackLeg, this.rightBackLeg, this.leftFrontLeg, this.rightFrontLeg);
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
