package twilightforest.client.model.tileentity.legacy;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import twilightforest.client.model.tileentity.GenericTrophyModel;

public class SnowQueenTrophyLegacyModel extends GenericTrophyModel {

	public ModelPart head;
	public ModelPart crown;

	public SnowQueenTrophyLegacyModel() {
		texWidth = 64;
		texHeight = 32;

		this.head = new ModelPart(this, 0, 0);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F);
		this.head.setPos(0.0F, -4.0F, 0.0F);

		this.crown = new ModelPart(this, 0, 0);
		this.crown.addChild(makeFrontCrown(-1, -4, 10F));
		this.crown.addChild(makeFrontCrown(0, 4, -10F));
		this.crown.addChild(makeSideCrown(-1, -4, 10F));
		this.crown.addChild(makeSideCrown(0, 4, -10F));
		this.head.addChild(crown);
	}

	private ModelPart makeSideCrown(float spikeDepth, float crownX, float angle) {
		ModelPart crownSide = new ModelPart(this, 28, 28);
		crownSide.addBox(-3.5F, -0.5F, -0.5F, 7, 1, 1);
		crownSide.setPos(crownX, -6.0F, 0.0F);
		crownSide.yRot = 3.14159F / 2.0F;

		ModelPart spike4 = new ModelPart(this, 48, 27);
		spike4.addBox(-0.5F, -3.5F, spikeDepth, 1, 4, 1);
		spike4.xRot = angle * 1.5F / 180F * 3.14159F;

		ModelPart spike3l = new ModelPart(this, 52, 28);
		spike3l.addBox(-0.5F, -2.5F, spikeDepth, 1, 3, 1);
		spike3l.setPos(-2.5F, 0.0F, 0.0F);
		spike3l.xRot = angle / 180F * 3.14159F;
		spike3l.zRot = -10F / 180F * 3.14159F;

		ModelPart spike3r = new ModelPart(this, 52, 28);
		spike3r.addBox(-0.5F, -2.5F, spikeDepth, 1, 3, 1);
		spike3r.setPos(2.5F, 0.0F, 0.0F);
		spike3r.xRot = angle / 180F * 3.14159F;
		spike3r.zRot = 10F / 180F * 3.14159F;

		crownSide.addChild(spike4);
		crownSide.addChild(spike3l);
		crownSide.addChild(spike3r);
		return crownSide;
	}

	private ModelPart makeFrontCrown(float spikeDepth, float crownZ, float angle) {
		ModelPart crownFront = new ModelPart(this, 28, 30);
		crownFront.addBox(-4.5F, -0.5F, -0.5F, 9, 1, 1);
		crownFront.setPos(0.0F, -6.0F, crownZ);

		ModelPart spike4 = new ModelPart(this, 48, 27);
		spike4.addBox(-0.5F, -3.5F, spikeDepth, 1, 4, 1);
		spike4.xRot = angle * 1.5F / 180F * 3.14159F;

		ModelPart spike3l = new ModelPart(this, 52, 28);
		spike3l.addBox(-0.5F, -2.5F, spikeDepth, 1, 3, 1);
		spike3l.setPos(-2.5F, 0.0F, 0.0F);
		spike3l.xRot = angle / 180F * 3.14159F;
		spike3l.zRot = -10F / 180F * 3.14159F;

		ModelPart spike3r = new ModelPart(this, 52, 28);
		spike3r.addBox(-0.5F, -2.5F, spikeDepth, 1, 3, 1);
		spike3r.setPos(2.5F, 0.0F, 0.0F);
		spike3r.xRot = angle / 180F * 3.14159F;
		spike3r.zRot = 10F / 180F * 3.14159F;

		crownFront.addChild(spike4);
		crownFront.addChild(spike3l);
		crownFront.addChild(spike3r);
		return crownFront;
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
