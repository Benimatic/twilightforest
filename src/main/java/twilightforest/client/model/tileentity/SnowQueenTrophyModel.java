package twilightforest.client.model.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;

public class SnowQueenTrophyModel extends GenericTrophyModel {

	public ModelPart head;
	public ModelPart crownFront;
	public ModelPart crownBack;
	public ModelPart crownRight;
	public ModelPart crownLeft;

	public SnowQueenTrophyModel() {
		texWidth = 64;
		texHeight = 64;

		this.head = new ModelPart(this, 0, 0);
		this.head.setPos(0.0F, 0.0F, 0.0F);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);

		this.crownRight = new ModelPart(this, 0, 0);
		this.crownRight.setPos(-4.0F, -6.0F, 0.0F);
		this.crownRight.texOffs(24, 4).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(crownRight, 0.39269908169872414F, 1.5707963267948966F, 0.0F);
		this.crownBack = new ModelPart(this, 0, 0);
		this.crownBack.setPos(0.0F, -6.0F, 4.0F);
		this.crownBack.texOffs(44, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(crownBack, -0.39269908169872414F, 0.0F, 0.0F);
		this.crownLeft = new ModelPart(this, 0, 0);
		this.crownLeft.setPos(4.0F, -6.0F, 0.0F);
		this.crownLeft.texOffs(44, 4).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(crownLeft, -0.39269908169872414F, 1.5707963267948966F, 0.0F);
		this.crownFront = new ModelPart(this, 0, 0);
		this.crownFront.setPos(0.0F, -6.0F, -4.0F);
		this.crownFront.texOffs(24, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		this.setRotateAngle(crownFront, 0.39269908169872414F, 0.0F, 0.0F);

		this.head.addChild(this.crownRight);
		this.head.addChild(this.crownBack);
		this.head.addChild(this.crownLeft);
		this.head.addChild(this.crownFront);

	}

	public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
	
	@Override
	public void setRotations(float x, float y, float z) {
		super.setRotations(x, y, z);
		this.head.yRot = y * ((float) Math.PI / 180F);
		this.head.xRot = x * ((float) Math.PI / 180F);
	}
	
	@Override
	public void renderToBuffer(PoseStack matrix, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.head.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
