package twilightforest.client.model.entity;

import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import twilightforest.entity.passive.EntityTFDeer;

public class ModelTFDeer extends QuadrupedModel<EntityTFDeer> {
	public ModelTFDeer() {
		super(12, 0.0F, false, 4.0F, 4.0F, 2.0F, 2.0F, 10);

		headModel = new ModelRenderer(this, 0, 5);
		headModel.addCuboid(-2F, -8F, -6F, 4, 6, 6, 0F);
		headModel.setRotationPoint(0F, 4F, -7F);

		body = new ModelRenderer(this, 36, 6);
		body.addCuboid(-4F, -10F, -7F, 6, 18, 8, 0F);
		body.setRotationPoint(1F, 5F, 2F);

		body.rotateAngleX = 1.570796F;
		legBackRight = new ModelRenderer(this, 0, 17);
		legBackRight.addCuboid(-3F, 0F, -2F, 2, 12, 3, 0F);
		legBackRight.setRotationPoint(0F, 12F, 9F);

		legBackLeft = new ModelRenderer(this, 0, 17);
		legBackLeft.addCuboid(-1F, 0F, -2F, 2, 12, 3, 0F);
		legBackLeft.setRotationPoint(2F, 12F, 9F);

		legFrontRight = new ModelRenderer(this, 0, 17);
		legFrontRight.addCuboid(-3F, 0F, -3F, 2, 12, 3, 0F);
		legFrontRight.setRotationPoint(0F, 12F, -5F);

		legFrontLeft = new ModelRenderer(this, 0, 17);
		legFrontLeft.addCuboid(-1F, 0F, -3F, 2, 12, 3, 0F);
		legFrontLeft.setRotationPoint(2F, 12F, -5F);

		// neck
		neck = new ModelRenderer(this, 10, 19);
		neck.addCuboid(-2.5F, -8, -11F, 3, 9, 4, 0F);
//		neck.setRotationPoint(1F, 5F, 2F);

		neck.rotateAngleX = 4.974188f;

		body.addChild(neck);

		// nose
		headModel.setTextureOffset(52, 0).addCuboid(-1.5F, -5F, -9F, 3, 3, 3, 0F);

		// antler 1
		headModel.setTextureOffset(20, 0);
		headModel.addCuboid(-3F, -10F, -2F, 2, 2, 2, 0F);
		headModel.addCuboid(-3F, -10F, -2F, 2, 2, 2, 0F);
		headModel.addCuboid(-4F, -10F, -1F, 1, 1, 3, 0F);
		headModel.addCuboid(-5F, -11F, 1F, 1, 1, 5, 0F);
		headModel.addCuboid(-5F, -14F, 2F, 1, 4, 1, 0F);
		headModel.addCuboid(-6F, -17F, 3F, 1, 4, 1, 0F);
		headModel.addCuboid(-6F, -13F, 0F, 1, 1, 3, 0F);
		headModel.addCuboid(-6F, -14F, -3F, 1, 1, 4, 0F);
		headModel.addCuboid(-7F, -15F, -6F, 1, 1, 4, 0F);
		headModel.addCuboid(-6F, -16F, -9F, 1, 1, 4, 0F);
		headModel.addCuboid(-7F, -18F, -1F, 1, 5, 1, 0F);
		headModel.addCuboid(-6F, -19F, -6F, 1, 5, 1, 0F);

		// antler 2
		headModel.addCuboid(1F, -10F, -2F, 2, 2, 2, 0F);
		headModel.addCuboid(3F, -10F, -1F, 1, 1, 3, 0F);
		headModel.addCuboid(4F, -11F, 1F, 1, 1, 5, 0F);
		headModel.addCuboid(4F, -14F, 2F, 1, 4, 1, 0F);
		headModel.addCuboid(5F, -17F, 3F, 1, 4, 1, 0F);
		headModel.addCuboid(5F, -13F, 0F, 1, 1, 3, 0F);
		headModel.addCuboid(5F, -14F, -3F, 1, 1, 4, 0F);
		headModel.addCuboid(6F, -15F, -6F, 1, 1, 4, 0F);
		headModel.addCuboid(5F, -16F, -9F, 1, 1, 4, 0F);
		headModel.addCuboid(6F, -18F, -1F, 1, 5, 1, 0F);
		headModel.addCuboid(5F, -19F, -6F, 1, 5, 1, 0F);
	}

	//fields
	public ModelRenderer neck;
}
