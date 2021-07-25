package twilightforest.client.model.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;

public class HydraTrophyModel extends GenericTrophyModel {

	public ModelPart head;
	public ModelPart plate;
	public ModelPart mouth;

	public HydraTrophyModel() {
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
		this.mouth.texOffs(240, 162).addBox(-15.0F, -2.0F, -24.0F, 30.0F, 8.0F, 24.0F, 0.0F, 0.0F, 0.0F);

		head.addChild(mouth);
	}
	
	private void setRotateAngle(ModelPart model, float x, float y, float z) {
		model.xRot = x;
		model.yRot = y;
		model.zRot = z;
	}
	
	@Override
	public void setRotations(float x, float y, float z) {
		super.setRotations(x, y, z);
		this.head.yRot = y * ((float) Math.PI / 180F);
		this.head.xRot = x * ((float) Math.PI / 180F);
	}
	
	public void openMouthForTrophy(float mouthOpen) {
		head.yRot = 0;
		head.xRot = 0;

		head.xRot -= (float) (mouthOpen * (Math.PI / 12.0));
		mouth.xRot = (float) (mouthOpen * (Math.PI / 3.0));
	}
	
	@Override
	public void renderToBuffer(PoseStack matrix, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.head.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
