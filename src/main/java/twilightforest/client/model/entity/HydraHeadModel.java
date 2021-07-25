package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import twilightforest.entity.boss.HydraHeadEntity;
import twilightforest.entity.boss.HydraPartEntity;

public class HydraHeadModel extends ListModel<HydraHeadEntity> {

	ModelPart head;
	ModelPart mouth;
	ModelPart plate;

	public HydraHeadModel() {
		texWidth = 512;
		texHeight = 256;

		this.head = new ModelPart(this, 0, 0);
		this.head.setPos(0F, 0F, 0F);
		this.head.texOffs(260, 64).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
		this.head.texOffs(236, 128).addBox(-16.0F, -2.0F, -40.0F, 32.0F, 10.0F, 24.0F, 0.0F, 0.0F, 0.0F);
		this.head.texOffs(356, 70).addBox(-12.0F, 8.0F, -36.0F, 24.0F, 6.0F, 20.0F, 0.0F, 0.0F, 0.0F);


		this.plate = new ModelPart(this, 0, 0);
		this.plate.setPos(0.0F, 0.0F, 0.0F);
		this.plate.texOffs(388, 0).addBox(-24.0F, -48.0F, 0.0F, 48.0F, 48.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.plate.texOffs(220, 0).addBox(-4.0F, -32.0F, -8.0F, 8.0F, 32.0F, 8.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(plate, -0.7853981633974483F, 0.0F, 0.0F);

		head.addChild(plate);

		this.mouth = new ModelPart(this, 0, 0);
		this.mouth.setPos(0.0F, 10.0F, -14.0F);
		this.mouth.texOffs(240, 162).addBox(-15.0F, 0.0F, -24.0F, 30.0F, 8.0F, 24.0F, 0.0F, 0.0F, 0.0F);

		head.addChild(mouth);
	}

	@Override
	public Iterable<ModelPart> parts() {
		return ImmutableList.of(head);
	}

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
		mouth.xRot = (float) (mouthOpen * (Math.PI / 3.0));
	}

	public float getRotationY(HydraPartEntity whichHead, float time) {
		float yaw = whichHead.yRotO + (whichHead.yRot - whichHead.yRotO) * time;

		return yaw / 57.29578F;
	}

	public float getRotationX(HydraPartEntity whichHead, float time) {
		return (whichHead.xRotO + (whichHead.xRot - whichHead.xRotO) * time) / 57.29578F;
	}

	public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
