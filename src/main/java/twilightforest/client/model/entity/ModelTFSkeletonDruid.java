package twilightforest.client.model.entity;

import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import twilightforest.entity.EntityTFSkeletonDruid;

public class ModelTFSkeletonDruid<T extends EntityTFSkeletonDruid> extends SkeletonModel<T> {
	private ModelRenderer dress;

	public ModelTFSkeletonDruid() {
		bipedBody = new ModelRenderer(this, 8, 16);
		bipedBody.addCuboid(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
		bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedRightArm = new ModelRenderer(this, 0, 16);
		bipedRightArm.addCuboid(-1.0F, -2.0F, -1.0F, 2, 12, 2, 0.0F);
		bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		bipedLeftArm = new ModelRenderer(this, 0, 16);
		bipedLeftArm.mirror = true;
		bipedLeftArm.addCuboid(-1.0F, -2.0F, -1.0F, 2, 12, 2, 0.0F);
		bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		
		dress = new ModelRenderer(this, 32, 16);
		dress.addCuboid(-4F, 12.0F, -2F, 8, 12, 4, 0);
		dress.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	@Override
	public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		dress.render(scale);
	}
}
