package twilightforest.client.model.entity.legacy;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import twilightforest.entity.boss.HydraHeadEntity;
import twilightforest.entity.boss.HydraPartEntity;

public class HydraHeadLegacyModel extends ListModel<HydraHeadEntity> {

    ModelPart head;
    ModelPart jaw;
    ModelPart frill;

    public HydraHeadLegacyModel() {
        texWidth = 512;
        texHeight = 256;

        head = new ModelPart(this/*, "head"*/);
        head.texOffs(272, 0).addBox(-16F, -14F, -32F, 32, 24, 32);
        head.texOffs(272, 56).addBox(-15F, -2F, -56F, 30, 12, 24);
        head.texOffs(272, 132).addBox(-15F, 10F, -20F, 30, 8, 16);
        head.texOffs(128, 200).addBox(-2F, -30F, -12F, 4, 24, 24);
        head.texOffs(272, 156).addBox(-12F, 10, -49F, 2, 5, 2);
        head.texOffs(272, 156).addBox(10F, 10, -49F, 2, 5, 2);
        head.texOffs(280, 156).addBox(-8F, 9, -49F, 16, 2, 2);
        head.texOffs(280, 160).addBox(-10F, 9, -45F, 2, 2, 16);
        head.texOffs(280, 160).addBox(8F, 9, -45F, 2, 2, 16);
        head.setPos(0F, 0F, 0F);

        jaw = new ModelPart(this/*, "jaw"*/);
        jaw.setPos(0F, 10F, -20F);
        jaw.texOffs(272, 92).addBox(-15F, 0F, -32F, 30, 8, 32);
        jaw.texOffs(272, 156).addBox(-10F, -5, -29F, 2, 5, 2);
        jaw.texOffs(272, 156).addBox(8F, -5, -29F, 2, 5, 2);
        jaw.texOffs(280, 156).addBox(-8F, -1, -29F, 16, 2, 2);
        jaw.texOffs(280, 160).addBox(-10F, -1, -25F, 2, 2, 16);
        jaw.texOffs(280, 160).addBox(8F, -1, -25F, 2, 2, 16);
        setRotation(jaw, 0F, 0F, 0F);
        head.addChild(jaw);

        frill = new ModelPart(this/*, "frill"*/);
        frill.setPos(0F, 0F, -14F);
        frill.texOffs(272, 200).addBox(-24F, -40.0F, 0F, 48, 48, 4);
        setRotation(frill, -0.5235988F, 0F, 0F);
        head.addChild(frill);
    }

    private void setRotation(ModelPart model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

//	@Override
//	public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
//		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//		setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//		head.render(scale);
//	}

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(head);
    }

//	@Override
//	public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
////		head.rotateAngleY = netHeadYaw / (180F / (float)Math.PI);
////		head.rotateAngleX = headPitch / (180F / (float)Math.PI);
//	}

    @Override
    public void setupAnim(HydraHeadEntity entity, float v, float v1, float v2, float v3, float v4) { }

    @Override
    public void prepareMobModel(HydraHeadEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {
        head.yRot = getRotationY(entity, partialTicks);
        head.xRot = getRotationX(entity, partialTicks);

        float mouthOpenLast = entity.getMouthOpenLast();
        float mouthOpenReal = entity.getMouthOpen();
        float mouthOpen = Mth.lerp(partialTicks, mouthOpenLast, mouthOpenReal);
        head.xRot -= (float) (mouthOpen * (Math.PI / 12.0));
        jaw.xRot = (float) (mouthOpen * (Math.PI / 3.0));
    }

    public void openMouthForTrophy(float mouthOpen) {
        head.yRot = 0;
        head.xRot = 0;

        head.xRot -= (float) (mouthOpen * (Math.PI / 12.0));
        jaw.xRot = (float) (mouthOpen * (Math.PI / 3.0));
    }

    public float getRotationY(HydraPartEntity whichHead, float time) {
        //float yawOffset = hydra.prevRenderYawOffset + (hydra.renderYawOffset - hydra.prevRenderYawOffset) * time;
        float yaw = whichHead.yRotO + (whichHead.yRot - whichHead.yRotO) * time;

        return yaw / 57.29578F;
    }

    public float getRotationX(HydraPartEntity whichHead, float time) {
        return (whichHead.xRotO + (whichHead.xRot - whichHead.xRotO) * time) / 57.29578F;
    }
}