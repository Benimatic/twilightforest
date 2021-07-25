package twilightforest.client.model.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;

public class ModelTFYetiAlphaTrophy extends GenericTrophyModel {
	public final ModelPart main;

	public ModelTFYetiAlphaTrophy() {
		texWidth = 256;
		texHeight = 128;

		main = new ModelPart(this);
		main.setPos(0.0F, -6.0F, 0.0F);


		ModelPart head = new ModelPart(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		main.addChild(head);
		head.texOffs(80, 0).addBox(-24.0F, -24.0F, -18.0F, 48.0F, 54.0F, 36.0F, 0.0F, false);
		head.texOffs(64, 0).addBox(8.0F, -20.0F, -19.5F, 12.0F, 12.0F, 2.0F, 0.0F, false);
		head.texOffs(121, 50).addBox(-17.0F, -8.0F, -19.5F, 34.0F, 29.0F, 2.0F, 0.0F, false);
		head.texOffs(64, 0).addBox(-20.0F, -20.0F, -19.5F, 12.0F, 12.0F, 2.0F, 0.0F, false);

		ModelPart lefthorn1 = new ModelPart(this);
		lefthorn1.setPos(22.0F, 6.0F, -1.0F);
		main.addChild(lefthorn1);
		setRotationAngle(lefthorn1, 0.0F, 0.5236F, 0.0873F);
		lefthorn1.texOffs(0, 108).addBox(1.6981F, -10.9848F, -5.1743F, 10.0F, 10.0F, 10.0F, 0.0F, false);

		ModelPart top_r1 = new ModelPart(this);
		top_r1.setPos(11.0F, -2.0F, 0.0F);
		lefthorn1.addChild(top_r1);
		setRotationAngle(top_r1, 0.0F, 0.3491F, 0.0873F);
		top_r1.texOffs(40, 108).addBox(0.4505F, -7.9433F, -4.3855F, 18.0F, 8.0F, 8.0F, 0.0F, false);

		ModelPart lefthorn2 = new ModelPart(this);
		lefthorn2.setPos(24.0F, -4.0F, -1.0F);
		main.addChild(lefthorn2);
		setRotationAngle(lefthorn2, 0.0F, 0.5236F, -0.2618F);
		lefthorn2.texOffs(0, 108).addBox(0.8966F, -10.8637F, -4.4824F, 10.0F, 10.0F, 10.0F, 0.0F, false);

		ModelPart top_r2 = new ModelPart(this);
		top_r2.setPos(9.0F, -1.0F, 0.0F);
		lefthorn2.addChild(top_r2);
		setRotationAngle(top_r2, 0.0F, 0.3491F, -0.2618F);
		top_r2.texOffs(40, 108).addBox(2.5764F, -8.5F, -2.8753F, 18.0F, 8.0F, 8.0F, 0.0F, false);

		ModelPart lefthorn3 = new ModelPart(this);
		lefthorn3.setPos(24.0F, -16.0F, -1.0F);
		main.addChild(lefthorn3);
		setRotationAngle(lefthorn3, 0.0F, 0.5236F, -0.6109F);
		lefthorn3.texOffs(0, 108).addBox(1.9869F, -10.2766F, -3.8528F, 10.0F, 10.0F, 10.0F, 0.0F, false);

		ModelPart top_r3 = new ModelPart(this);
		top_r3.setPos(8.0F, -2.0F, 0.0F);
		lefthorn3.addChild(top_r3);
		setRotationAngle(top_r3, 0.0F, 0.3491F, -0.6109F);
		top_r3.texOffs(40, 108).addBox(4.9031F, -5.5443F, -1.7225F, 18.0F, 8.0F, 8.0F, 0.0F, false);

		ModelPart righthorn1 = new ModelPart(this);
		righthorn1.setPos(-22.0F, 6.0F, -1.0F);
		main.addChild(righthorn1);
		setRotationAngle(righthorn1, 0.0F, -0.5236F, -0.0873F);
		righthorn1.texOffs(0, 108).addBox(-11.6981F, -10.9848F, -5.1743F, 10.0F, 10.0F, 10.0F, 0.0F, true);

		ModelPart top_r4 = new ModelPart(this);
		top_r4.setPos(-11.0F, -2.0F, 0.0F);
		righthorn1.addChild(top_r4);
		setRotationAngle(top_r4, 0.0F, -0.3491F, -0.0873F);
		top_r4.texOffs(40, 108).addBox(-18.4505F, -7.9433F, -4.3855F, 18.0F, 8.0F, 8.0F, 0.0F, true);

		ModelPart righthorn2 = new ModelPart(this);
		righthorn2.setPos(-24.0F, -4.0F, -1.0F);
		main.addChild(righthorn2);
		setRotationAngle(righthorn2, 0.0F, -0.5236F, 0.2618F);
		righthorn2.texOffs(0, 108).addBox(-10.8966F, -10.8637F, -4.4824F, 10.0F, 10.0F, 10.0F, 0.0F, true);

		ModelPart top_r5 = new ModelPart(this);
		top_r5.setPos(-9.0F, -1.0F, 0.0F);
		righthorn2.addChild(top_r5);
		setRotationAngle(top_r5, 0.0F, -0.3491F, 0.2618F);
		top_r5.texOffs(40, 108).addBox(-20.5764F, -8.5F, -2.8753F, 18.0F, 8.0F, 8.0F, 0.0F, true);

		ModelPart righthorn3 = new ModelPart(this);
		righthorn3.setPos(-24.0F, -16.0F, -1.0F);
		main.addChild(righthorn3);
		setRotationAngle(righthorn3, 0.0F, -0.5236F, 0.6109F);
		righthorn3.texOffs(0, 108).addBox(-11.9869F, -10.2766F, -3.8528F, 10.0F, 10.0F, 10.0F, 0.0F, true);

		ModelPart top_r6 = new ModelPart(this);
		top_r6.setPos(-8.0F, -2.0F, 0.0F);
		righthorn3.addChild(top_r6);
		setRotationAngle(top_r6, 0.0F, -0.3491F, 0.6109F);
		top_r6.texOffs(40, 108).addBox(-22.9031F, -5.5443F, -1.7225F, 18.0F, 8.0F, 8.0F, 0.0F, true);
	}

	@Override
	public void setRotations(float x, float y, float z) {
		super.setRotations(x, y, z);
		main.yRot = y * ((float) Math.PI / 180F);
		main.xRot = x * ((float) Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}