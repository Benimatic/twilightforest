package twilightforest.client.model.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;

public class LichTrophyModel extends GenericTrophyModel {

	public final ModelPart head;
	public final ModelPart crown;

	public LichTrophyModel() {
		this(0, 0, 64, 64);
	}

	public LichTrophyModel(int offsetX, int offsetY, int width, int height) {
		this.texWidth = width;
		this.texHeight = height;
		this.head = new ModelPart(this, 0, 0);
		this.head.addBox(-4F, -8F, -4F, 8, 8, 8);
		this.head.setPos(0F, -4F, 0F);
		this.crown = new ModelPart(this, 32, 0);
		this.crown.addBox(-4F, -8F, -4F, 8, 8, 8, 0.5F);
		this.crown.setPos(0.0F, -4.0F, 0.0F);
		this.head.addChild(crown);
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
