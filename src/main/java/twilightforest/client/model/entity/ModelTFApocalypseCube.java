package twilightforest.client.model.entity;

import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import twilightforest.entity.EntityTFHarbingerCube;

public class ModelTFApocalypseCube<T extends EntityTFHarbingerCube> extends QuadrupedModel<T> {

	public ModelTFApocalypseCube() {
		this(0.0F);
	}

	public ModelTFApocalypseCube(float fNumber) {
		super(6, fNumber, false, 0.0F, 0.0F, 0.0F, 0.0F, 4); //All this is from AgeableModel. Do we scale?

		this.textureWidth = 128;
		this.textureHeight = 64;

		this.headModel = new ModelRenderer(this, 0, 0);

		body = new ModelRenderer(this, 0, 0);
		body.addCuboid(-16F, -16F, -16F, 32, 32, 32);
		body.setRotationPoint(0F, 0F, -2F);

        legBackRight = new ModelRenderer(this, 0, 0);
        legBackRight.addCuboid(-4F, 0F, -4F, 8, 8, 8);
        legBackRight.setRotationPoint(-6F, 16F, 9F);

        legBackLeft = new ModelRenderer(this, 0, 0);
        legBackLeft.addCuboid(-4F, 0F, -4F, 8, 8, 8);
        legBackLeft.setRotationPoint(6F, 16F, 9F);

        legFrontRight = new ModelRenderer(this, 0, 0);
        legFrontRight.addCuboid(-4F, 0F, -4F, 8, 8, 8);
        legFrontRight.setRotationPoint(-9F, 16F, -14F);

        legFrontLeft = new ModelRenderer(this, 0, 0);
        legFrontLeft.addCuboid(-4F, 0F, -4F, 8, 8, 8);
        legFrontLeft.setRotationPoint(9F, 16F, -14F);
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
	 * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
	 * "far" arms and legs can swing at most.
	 */
	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		this.body.rotateAngleX = 0F;
	}
}
