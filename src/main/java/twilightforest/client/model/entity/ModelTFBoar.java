package twilightforest.client.model.entity;

import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import twilightforest.entity.passive.EntityTFBoar;

public class ModelTFBoar<T extends EntityTFBoar> extends PigModel<T> {
	public ModelTFBoar() {
		super();

		headModel = new ModelRenderer(this, 0, 0);
		headModel.addCuboid(-4F, -2F, -6F, 8, 7, 6, 0F);
		headModel.setRotationPoint(0F, 12F, -6F);

		body = new ModelRenderer(this, 28, 10);
		body.addCuboid(-5F, -8F, -7F, 10, 14, 8, 0F);
		body.setRotationPoint(0F, 11F, 2F);

		body.rotateAngleX = 1.570796F;

		legBackRight = new ModelRenderer(this, 0, 16);
		legBackRight.addCuboid(-2F, 0F, -2F, 4, 6, 4, 0F);
		legBackRight.setRotationPoint(-3F, 18F, 7F);

		legBackLeft = new ModelRenderer(this, 0, 16);
		legBackLeft.addCuboid(-2F, 0F, -2F, 4, 6, 4, 0F);
		legBackLeft.setRotationPoint(3F, 18F, 7F);

		legFrontRight = new ModelRenderer(this, 0, 16);
		legFrontRight.addCuboid(-2F, 0F, -2F, 4, 6, 4, 0F);
		legFrontRight.setRotationPoint(-3F, 18F, -5F);

		legFrontLeft = new ModelRenderer(this, 0, 16);
		legFrontLeft.addCuboid(-2F, 0F, -2F, 4, 6, 4, 0F);
		legFrontLeft.setRotationPoint(3F, 18F, -5F);

		headModel.setTextureOffset(28, 0).addCuboid(-3F, 1F, -9F, 6, 4, 3, 0F); // snout

		headModel.setTextureOffset(17, 17).addCuboid(3F, 2F, -9F, 1, 2, 1, 0F); // tusk1
		headModel.setTextureOffset(17, 17).addCuboid(-4F, 2F, -9F, 1, 2, 1, 0F); // tusk2
	}
}
