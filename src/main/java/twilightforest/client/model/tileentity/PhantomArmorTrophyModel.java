package twilightforest.client.model.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;

public class PhantomArmorTrophyModel extends GenericTrophyModel {

	public ModelPart head;
	public ModelPart righthorn1;
	public ModelPart righthorn2;
	public ModelPart lefthorn1;
	public ModelPart lefthorn2;

	public PhantomArmorTrophyModel() {
		texWidth = 64;
		texHeight = 32;
		
		this.head = new ModelPart(this, 0, 0);
	    this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F);
	    this.head.setPos(0.0F, -4.0F, 0.0F);
	      
		this.righthorn1 = new ModelPart(this, 24, 0);
		this.righthorn1.addBox(-5.5F, -1.5F, -1.5F, 5, 3, 3);
		this.righthorn1.setPos(-4.0F, -6.5F, 0.0F);
		this.righthorn1.yRot = -25F / (180F / (float) Math.PI);
		this.righthorn1.zRot = 45F / (180F / (float) Math.PI);

		this.righthorn2 = new ModelPart(this, 54, 16);
		this.righthorn2.addBox(-3.5F, -1.0F, -1.0F, 3, 2, 2);
		this.righthorn2.setPos(-4.5F, 0.0F, 0.0F);
		this.righthorn2.yRot = -15F / (180F / (float) Math.PI);
		this.righthorn2.zRot = 45F / (180F / (float) Math.PI);

		this.head.addChild(righthorn1);
		this.righthorn1.addChild(righthorn2);

		this.lefthorn1 = new ModelPart(this, 24, 0);
		this.lefthorn1.mirror = true;
		this.lefthorn1.addBox(0.5F, -1.5F, -1.5F, 5, 3, 3);
		this.lefthorn1.setPos(4.0F, -6.5F, 0.0F);
		this.lefthorn1.yRot = 25F / (180F / (float) Math.PI);
		this.lefthorn1.zRot = -45F / (180F / (float) Math.PI);

		this.lefthorn2 = new ModelPart(this, 54, 16);
		this.lefthorn2.addBox(0.5F, -1.0F, -1.0F, 3, 2, 2);
		this.lefthorn2.setPos(4.5F, 0.0F, 0.0F);
		this.lefthorn2.yRot = 15F / (180F / (float) Math.PI);
		this.lefthorn2.zRot = -45F / (180F / (float) Math.PI);

		this.head.addChild(lefthorn1);
		this.lefthorn1.addChild(lefthorn2);
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
