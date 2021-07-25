package twilightforest.client.model.entity;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.BlockChainGoblinEntity;

/**
 * ModelMaceGoblin - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class BlockChainGoblinModel<T extends BlockChainGoblinEntity> extends HumanoidModel<T> {
    public ModelPart helmet;
    public ModelPart horns;

    ModelPart block;
    ModelPart[] spikes = new ModelPart[27];

    public BlockChainGoblinModel() {
        super(0, 0, 64, 48);
        this.rightArm = new ModelPart(this, 0, 0);
        this.rightArm.setPos(-5.0F, 12.0F, 0.0F);
        this.rightArm.texOffs(52, 2).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 12.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(rightArm, 0.0F, 0.0F, 3.0543261909900767F);
        this.leftArm = new ModelPart(this, 0, 0);
        this.leftArm.setPos(5.0F, 12.0F, 0.0F);
        this.leftArm.texOffs(52, 17).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 12.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(leftArm, 0.0F, 0.0F, -3.0543261909900767F);
        this.horns = new ModelPart(this, 0, 0);
        this.horns.setPos(0.0F, 0.0F, 0.0F);
        this.horns.texOffs(0, 18).addBox(-7.5F, -9.0F, -2.03F, 15.0F, 10.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(horns, 0.0F, -0.7853981633974483F, 0.0F);
        this.rightLeg = new ModelPart(this, 0, 0);
        this.rightLeg.setPos(-2.0F, 18.0F, 0.0F);
        this.rightLeg.texOffs(0, 33).addBox(-1.4F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.leftLeg = new ModelPart(this, 0, 0);
        this.leftLeg.setPos(2.0F, 18.0F, 0.0F);
        this.leftLeg.texOffs(12, 33).addBox(-1.6F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelPart(this, 0, 0);
        this.body.setPos(0.0F, 12.0F, 0.0F);
        this.body.texOffs(28, 6).addBox(-3.5F, 1.0F, -2.0F, 7.0F, 6.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.helmet = new ModelPart(this, 0, 0);
        this.helmet.setPos(0.0F, 0.0F, 0.0F);
        this.helmet.texOffs(0, 5).addBox(-2.5F, -7.0F, -2.5F, 5.0F, 8.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(helmet, 0.0F, 0.7853981633974483F, 0.0F);
        //kill the head and headwear to prevent issues
        head = new ModelPart(this, 0, 0);
        head.addBox(0F, 0F, 0F, 0, 0, 0, 0F);
        this.hat = new ModelPart(this, 0, 0);
        this.hat.addBox(0, 0, 0, 0, 0, 0);

        this.helmet.addChild(this.horns);
        this.hat.addChild(helmet);

        block = new ModelPart(this, 32, 32);
        block.addBox(-4F, -8F, -4F, 8, 8, 8, 0F);
        block.setPos(0F, 0F, 0F);

        for (int i = 0; i < spikes.length; i++) {
            spikes[i] = new ModelPart(this, 56, 36);
            spikes[i].addBox(-1F, -1F, -1F, 2, 2, 2, 0F);
            block.addChild(spikes[i]);
        }

        // X
        spikes[2].x = 4;
        spikes[3].x = 4;
        spikes[4].x = 4;
        spikes[11].x = 4;
        spikes[12].x = 5;
        spikes[13].x = 4;
        spikes[20].x = 4;
        spikes[21].x = 4;
        spikes[22].x = 4;

        spikes[6].x = -4;
        spikes[7].x = -4;
        spikes[8].x = -4;
        spikes[15].x = -4;
        spikes[16].x = -5;
        spikes[17].x = -4;
        spikes[24].x = -4;
        spikes[25].x = -4;
        spikes[26].x = -4;

        // Y
        spikes[0].y = -9;
        spikes[1].y = -8;
        spikes[2].y = -8;
        spikes[3].y = -8;
        spikes[4].y = -8;
        spikes[5].y = -8;
        spikes[6].y = -8;
        spikes[7].y = -8;
        spikes[8].y = -8;

        spikes[9].y = -4; // this spike is not really there
        spikes[10].y = -4;
        spikes[11].y = -4;
        spikes[12].y = -4;
        spikes[13].y = -4;
        spikes[14].y = -4;
        spikes[15].y = -4;
        spikes[16].y = -4;
        spikes[17].y = -4;

        spikes[18].y = 1;

        // Z
        spikes[1].z = 4;
        spikes[2].z = 4;
        spikes[8].z = 4;
        spikes[10].z = 4;
        spikes[11].z = 5;
        spikes[17].z = 4;
        spikes[19].z = 4;
        spikes[20].z = 4;
        spikes[26].z = 4;

        spikes[4].z = -4;
        spikes[5].z = -4;
        spikes[6].z = -4;
        spikes[13].z = -4;
        spikes[14].z = -5;
        spikes[15].z = -4;
        spikes[22].z = -4;
        spikes[23].z = -4;
        spikes[24].z = -4;

        // rotation
        float fourtyFive = (float) (Math.PI / 4F);

        spikes[1].xRot = fourtyFive;
        spikes[5].xRot = fourtyFive;
        spikes[19].xRot = fourtyFive;
        spikes[23].xRot = fourtyFive;

        spikes[11].yRot = fourtyFive;
        spikes[13].yRot = fourtyFive;
        spikes[15].yRot = fourtyFive;
        spikes[17].yRot = fourtyFive;

        spikes[3].zRot = fourtyFive;
        spikes[7].zRot = fourtyFive;
        spikes[21].zRot = fourtyFive;
        spikes[25].zRot = fourtyFive;

        spikes[2].xRot = -55F / (180F / (float) Math.PI);
        spikes[2].yRot = fourtyFive;
        spikes[24].xRot = -55F / (180F / (float) Math.PI);
        spikes[24].yRot = fourtyFive;

        spikes[4].xRot = -35F / (180F / (float) Math.PI);
        spikes[4].yRot = -fourtyFive;
        spikes[26].xRot = -35F / (180F / (float) Math.PI);
        spikes[26].yRot = -fourtyFive;

        spikes[6].yRot = fourtyFive;
        spikes[6].xRot = -35F / (180F / (float) Math.PI);
        spikes[20].yRot = fourtyFive;
        spikes[20].xRot = -35F / (180F / (float) Math.PI);

        spikes[8].xRot = -55F / (180F / (float) Math.PI);
        spikes[8].yRot = -fourtyFive;
        spikes[22].xRot = -55F / (180F / (float) Math.PI);
        spikes[22].yRot = -fourtyFive;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        head.y = 11.0F;
        hat.y = 11.0F;
        body.y = 11F;

        rightLeg.y = 18F;
        leftLeg.y = 18F;

        rightArm.setPos(-3.5F, 12F, 0F);
        rightArm.xRot += Math.PI;

        leftArm.setPos(3.5F, 12F, 0F);
        leftArm.xRot += Math.PI;

        float angle = ageInTicks / 4F;
        float length = 0;//16F;

        block.x = (float) Math.sin(angle) * length;
        block.z = (float) -Math.cos(angle) * length;

        block.yRot = -angle;
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
