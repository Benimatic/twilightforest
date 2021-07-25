package twilightforest.client.model.tileentity.legacy;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import twilightforest.client.model.tileentity.GenericTrophyModel;

public class HydraTrophyLegacyModel extends GenericTrophyModel {

	public ModelPart head;
	public ModelPart jaw;
	public ModelPart frill;

	public HydraTrophyLegacyModel() {
		texWidth = 512;
		texHeight = 256;

		head = new ModelPart(this);
		head.texOffs(272, 0).addBox(-16F, -14F, -16F, 32, 24, 32);
		head.texOffs(272, 56).addBox(-15F, -2F, -40F, 30, 12, 24);
		head.texOffs(272, 132).addBox(-15F, 10F, -4F, 30, 8, 16);
		head.texOffs(128, 200).addBox(-2F, -30F, 4F, 4, 24, 24);
		head.texOffs(272, 156).addBox(-12F, 10, -33F, 2, 5, 2);
		head.texOffs(272, 156).addBox(10F, 10, -33F, 2, 5, 2);
		head.texOffs(280, 156).addBox(-8F, 9, -33F, 16, 2, 2);
		head.texOffs(280, 160).addBox(-10F, 9, -29F, 2, 2, 16);
		head.texOffs(280, 160).addBox(8F, 9, -29F, 2, 2, 16);
		head.setPos(0F, 0F, 0F);

		jaw = new ModelPart(this);
		jaw.setPos(0F, 10F, -20F);
		jaw.texOffs(272, 92).addBox(-15F, 0F, -16F, 30, 8, 32);
		jaw.texOffs(272, 156).addBox(-10F, -5, -13F, 2, 5, 2);
		jaw.texOffs(272, 156).addBox(8F, -5, -13F, 2, 5, 2);
		jaw.texOffs(280, 156).addBox(-8F, -1, -13F, 16, 2, 2);
		jaw.texOffs(280, 160).addBox(-10F, -1, -9F, 2, 2, 16);
		jaw.texOffs(280, 160).addBox(8F, -1, -9F, 2, 2, 16);
		setRotation(jaw, 0F, 0F, 0F);
		head.addChild(jaw);

		frill = new ModelPart(this);
		frill.setPos(0F, 0F, -14F);
		frill.texOffs(272, 200).addBox(-24F, -48.0F, 12F, 48, 48, 4);
		setRotation(frill, -0.5235988F, 0F, 0F);
		head.addChild(frill);
	}
	
	private void setRotation(ModelPart model, float x, float y, float z) {
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
		jaw.xRot = (float) (mouthOpen * (Math.PI / 3.0));
	}
	
	@Override
	public void renderToBuffer(PoseStack matrix, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.head.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
