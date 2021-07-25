package twilightforest.client.model.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;

//This model doesnt require a legacy as the tongue will only show up in newer versions
public class NagaTrophyModel extends GenericTrophyModel {

	public final ModelPart head;
	public final ModelPart tongue;

	public NagaTrophyModel() {
		this.texWidth = 64;
		this.texHeight = 32;
		this.head = new ModelPart(this, 0, 0);
		this.head.addBox(-8F, -16F, -8F, 16, 16, 16, 0.0F);
		this.head.setPos(0F, -4F, 0F);
		this.tongue = new ModelPart(this, 0, 0);
		this.tongue.setPos(0.0F, 0.0F, 0.0F);
		this.tongue.texOffs(42, 0).addBox(-3.0F, -3.0F, -14.0F, 6.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.head.addChild(this.tongue);
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
