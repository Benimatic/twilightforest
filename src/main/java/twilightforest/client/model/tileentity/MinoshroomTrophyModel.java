package twilightforest.client.model.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;

public class MinoshroomTrophyModel extends GenericTrophyModel {

	public ModelPart head;

	public MinoshroomTrophyModel() {

		this.texWidth = 64;
		this.texHeight = 64;

		this.head = new ModelPart(this, 0, 0);
		this.head.setPos(0.0F, -4.0F, 0.0F);
		this.head.addBox(-4.0F, -9.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
		this.head.texOffs(0, 16).addBox(-3.0F, -4.0F, -5.0F, 6.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.0F);
		this.head.texOffs(32, 0).addBox(-8.0F, -8.0F, -1.0F, 4.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.head.texOffs(32, 5).addBox(-8.0F, -11.0F, -1.0F, 2.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.head.texOffs(46, 0).addBox(4.0F, -8.0F, -1.0F, 4.0F, 2.0F, 3.0F, 0.0F, 0.0F, 0.0F);
		this.head.texOffs(46, 5).addBox(6.0F, -11.0F, -1.0F, 2.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);

	}
	
	@Override
	public void setRotations(float x, float y, float z) {
		super.setRotations(x, y, z);
		this.head.yRot = y * ((float) Math.PI / 180F);
		this.head.xRot = x * ((float) Math.PI / 180F);
	}
	
	@Override
	public void renderToBuffer(PoseStack matrix, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		matrix.translate(0.0F, .25F, 0.0F);
		this.head.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
