package twilightforest.client.model.tileentity.legacy;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import twilightforest.client.model.tileentity.GenericTrophyModel;

public class QuestRamTrophyLegacyModel extends GenericTrophyModel {

	public ModelPart neck;
	public ModelPart nose;
	public ModelPart head;

	public QuestRamTrophyLegacyModel() {
		texWidth = 128;
		texHeight = 128;
		
		neck = new ModelPart(this, 66, 37);
		neck.addBox(-5.5F, -8F, 0F, 11, 14, 12);
		neck.setPos(0F, -8F, -7F);

		setRotation(neck, 0.2617994F, 0F, 0F);

		head = new ModelPart(this/*, "head"*/);
		head.setPos(0F, -4F, -0F);

		head.texOffs(0, 70).addBox(-6F, -4.5F, -7F, 12, 9, 15);
		head.texOffs(0, 94).addBox(5F, -9F, 1F, 4, 4, 6);
		head.texOffs(20, 96).addBox(7F, -8F, 6F, 3, 4, 4);
		head.texOffs(34, 95).addBox(8F, -6F, 8F, 3, 6, 3);
		head.texOffs(46, 98).addBox(9.5F, -2F, 6F, 3, 3, 3);
		head.texOffs(58, 95).addBox(11F, 0F, 1F, 3, 3, 6);
		head.texOffs(76, 95).addBox(12F, -4F, -1F, 3, 6, 3);
		head.texOffs(88, 97).addBox(13F, -6F, 1F, 3, 3, 4);
		head.texOffs(0, 94).addBox(-9F, -9F, 1F, 4, 4, 6);
		head.texOffs(20, 96).addBox(-10F, -8F, 6F, 3, 4, 4);
		head.texOffs(34, 95).addBox(-11F, -6F, 8F, 3, 6, 3);
		head.texOffs(46, 98).addBox(-12.5F, -2F, 6F, 3, 3, 3);
		head.texOffs(58, 95).addBox(-14F, 0F, 1F, 3, 3, 6);
		head.texOffs(76, 95).addBox(-15F, -4F, -1F, 3, 6, 3);
		head.texOffs(88, 97).addBox(-16F, -6F, 1F, 3, 3, 4);

		nose = new ModelPart(this, 54, 73);
		nose.addBox(-5.5F, -1F, -6F, 11, 9, 12);
		nose.setPos(0F, -7F, -1F);
		nose.setTexSize(128, 128);
		setRotation(nose, 0.5235988F, 0F, 0F);
		head.addChild(nose);
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
	
	@Override
	public void renderToBuffer(PoseStack matrix, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.head.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
