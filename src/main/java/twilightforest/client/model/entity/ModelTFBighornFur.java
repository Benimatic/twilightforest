package twilightforest.client.model.entity;

import net.minecraft.client.renderer.entity.model.SheepWoolModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import twilightforest.entity.passive.EntityTFBighorn;

public class ModelTFBighornFur extends SheepWoolModel<EntityTFBighorn> {

	public ModelTFBighornFur() {
		super();
		headModel = new ModelRenderer(this, 0, 0);
		headModel.addBox(-3F, -4F, -4F, 6, 6, 6, 0.6F);
		headModel.setRotationPoint(0.0F, 6F, -8F);
		body = new ModelRenderer(this, 28, 8);
		body.addBox(-4F, -9F, -7F, 8, 15, 6, 0.5F);
		body.setRotationPoint(0.0F, 5F, 2.0F);
		float f = 0.4F;
		legBackRight = new ModelRenderer(this, 0, 16);
		legBackRight.addBox(-2F, 0.0F, -2F, 4, 6, 4, f);
		legBackRight.setRotationPoint(-3F, 12F, 7F);
		legBackLeft = new ModelRenderer(this, 0, 16);
		legBackLeft.addBox(-2F, 0.0F, -2F, 4, 6, 4, f);
		legBackLeft.setRotationPoint(3F, 12F, 7F);
		legFrontRight = new ModelRenderer(this, 0, 16);
		legFrontRight.addBox(-2F, 0.0F, -2F, 4, 6, 4, f);
		legFrontRight.setRotationPoint(-3F, 12F, -5F);
		legFrontLeft = new ModelRenderer(this, 0, 16);
		legFrontLeft.addBox(-2F, 0.0F, -2F, 4, 6, 4, f);
		legFrontLeft.setRotationPoint(3F, 12F, -5F);
	}
}
