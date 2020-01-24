package twilightforest.client.model.entity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import twilightforest.entity.EntityTFCubeOfAnnihilation;

public class ModelTFCubeOfAnnihilation<T extends EntityTFCubeOfAnnihilation> extends EntityModel<T> {

	public ModelRenderer box;
	public ModelRenderer boxX;
	public ModelRenderer boxY;
	public ModelRenderer boxZ;

	public ModelTFCubeOfAnnihilation() {
		textureWidth = 64;
		textureHeight = 64;
		box = new ModelRenderer(this, 0, 0);
		box.addCuboid(-8F, -8F, -8F, 16, 16, 16, 0F);
		box.setRotationPoint(0F, 0F, 0F);

		boxX = new ModelRenderer(this, 0, 32);
		boxX.addCuboid(-8F, -8F, -8F, 16, 16, 16, 0F);
		boxX.setRotationPoint(0F, 0F, 0F);

		boxY = new ModelRenderer(this, 0, 32);
		boxY.addCuboid(-8F, -8F, -8F, 16, 16, 16, 0F);
		boxY.setRotationPoint(0F, 0F, 0F);

		boxZ = new ModelRenderer(this, 0, 32);
		boxZ.addCuboid(-8F, -8F, -8F, 16, 16, 16, 0F);
		boxZ.setRotationPoint(0F, 0F, 0F);
	}


	@Override
	public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

		this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		box.render(scale);
		boxX.render(scale);
		boxY.render(scale);
		boxZ.render(scale);
	}

	@Override
	public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

		boxX.rotateAngleX = (float) Math.sin((entity.ticksExisted + headPitch)) / 5F;
		boxY.rotateAngleY = (float) Math.sin((entity.ticksExisted + headPitch)) / 5F;
		boxZ.rotateAngleZ = (float) Math.sin((entity.ticksExisted + headPitch)) / 5F;

	}

}
