package twilightforest.client.model.entity;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import twilightforest.entity.boss.EntityTFSnowQueen;
import twilightforest.entity.boss.EntityTFSnowQueen.Phase;

public class ModelTFSnowQueen<T extends EntityTFSnowQueen> extends BipedModel<T> {

	public ModelTFSnowQueen() {

		float par1 = 0;
		float par2 = 0;

		// crown
		this.bipedHeadwear = new ModelRenderer(this, 0, 0);

		this.bipedHeadwear.addChild(makeFrontCrown(-1, -4, 10F));
		this.bipedHeadwear.addChild(makeFrontCrown(0, 4, -10F));
		this.bipedHeadwear.addChild(makeSideCrown(-1, -4, 10F));
		this.bipedHeadwear.addChild(makeSideCrown(0, 4, -10F));

		// copy to back

		// dress
		this.bipedBody = new ModelRenderer(this, 32, 0);
		this.bipedBody.addCuboid(-4.0F, 0.0F, -2.0F, 8, 23, 4, par1);
		this.bipedBody.setRotationPoint(0.0F, 0.0F + par2, 0.0F);

		// shrink
		this.bipedRightArm = new ModelRenderer(this, 16, 16);
		this.bipedRightArm.addCuboid(-2.0F, -2.0F, -1.5F, 3, 12, 3, par1);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + par2, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 16, 16);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addCuboid(-1.0F, -2.0F, -1.3F, 3, 12, 3, par1);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + par2, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.addCuboid(-1.5F, 0.0F, -1.5F, 3, 12, 3, par1);
		this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + par2, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.addCuboid(-1.5F, 0.0F, -1.5F, 3, 12, 3, par1);
		this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + par2, 0.0F);
	}

	private ModelRenderer makeSideCrown(float spikeDepth, float crownX, float angle) {
		ModelRenderer crownSide = new ModelRenderer(this, 28, 28);
		crownSide.addCuboid(-3.5F, -0.5F, -0.5F, 7, 1, 1);
		crownSide.setRotationPoint(crownX, -6.0F, 0.0F);
		crownSide.rotateAngleY = 3.14159F / 2.0F;

		ModelRenderer spike4 = new ModelRenderer(this, 48, 27);
		spike4.addCuboid(-0.5F, -3.5F, spikeDepth, 1, 4, 1);
		spike4.rotateAngleX = angle * 1.5F / 180F * 3.14159F;

		ModelRenderer spike3l = new ModelRenderer(this, 52, 28);
		spike3l.addCuboid(-0.5F, -2.5F, spikeDepth, 1, 3, 1);
		spike3l.setRotationPoint(-2.5F, 0.0F, 0.0F);
		spike3l.rotateAngleX = angle / 180F * 3.14159F;
		spike3l.rotateAngleZ = -10F / 180F * 3.14159F;

		ModelRenderer spike3r = new ModelRenderer(this, 52, 28);
		spike3r.addCuboid(-0.5F, -2.5F, spikeDepth, 1, 3, 1);
		spike3r.setRotationPoint(2.5F, 0.0F, 0.0F);
		spike3r.rotateAngleX = angle / 180F * 3.14159F;
		spike3r.rotateAngleZ = 10F / 180F * 3.14159F;

		crownSide.addChild(spike4);
		crownSide.addChild(spike3l);
		crownSide.addChild(spike3r);
		return crownSide;
	}

	private ModelRenderer makeFrontCrown(float spikeDepth, float crownZ, float angle) {
		ModelRenderer crownFront = new ModelRenderer(this, 28, 30);
		crownFront.addCuboid(-4.5F, -0.5F, -0.5F, 9, 1, 1);
		crownFront.setRotationPoint(0.0F, -6.0F, crownZ);

		ModelRenderer spike4 = new ModelRenderer(this, 48, 27);
		spike4.addCuboid(-0.5F, -3.5F, spikeDepth, 1, 4, 1);
		spike4.rotateAngleX = angle * 1.5F / 180F * 3.14159F;

		ModelRenderer spike3l = new ModelRenderer(this, 52, 28);
		spike3l.addCuboid(-0.5F, -2.5F, spikeDepth, 1, 3, 1);
		spike3l.setRotationPoint(-2.5F, 0.0F, 0.0F);
		spike3l.rotateAngleX = angle / 180F * 3.14159F;
		spike3l.rotateAngleZ = -10F / 180F * 3.14159F;

		ModelRenderer spike3r = new ModelRenderer(this, 52, 28);
		spike3r.addCuboid(-0.5F, -2.5F, spikeDepth, 1, 3, 1);
		spike3r.setRotationPoint(2.5F, 0.0F, 0.0F);
		spike3r.rotateAngleX = angle / 180F * 3.14159F;
		spike3r.rotateAngleZ = 10F / 180F * 3.14159F;

		crownFront.addChild(spike4);
		crownFront.addChild(spike3l);
		crownFront.addChild(spike3r);
		return crownFront;
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, limbSwing and limbSwingAmount are used for animating the movement of arms
	 * and legs, where limbSwing represents the time(so that arms and legs swing back and forth) and limbSwingAmount represents how
	 * "far" arms and legs can swing at most.
	 */
	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		// in beam phase, arms forwards
		if (entity.getCurrentPhase() == Phase.BEAM) {
			if (entity.isBreathing()) {
				float f6 = MathHelper.sin(this.swingProgress * (float) Math.PI);
				float f7 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
				this.bipedRightArm.rotateAngleZ = 0.0F;
				this.bipedLeftArm.rotateAngleZ = 0.0F;
				this.bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F);
				this.bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F;
				this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2F);
				this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F);
				this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
				this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
				this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
				this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
				this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
				this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
			} else {
				// arms up
				this.bipedRightArm.rotateAngleX += Math.PI;
				this.bipedLeftArm.rotateAngleX += Math.PI;
			}
		}
	}
}
