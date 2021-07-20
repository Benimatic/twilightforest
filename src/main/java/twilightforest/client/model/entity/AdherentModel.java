package twilightforest.client.model.entity;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import twilightforest.entity.AdherentEntity;

public class AdherentModel extends BipedModel<AdherentEntity> {

	ModelRenderer leftSleeve;
	ModelRenderer rightSleeve;

	public AdherentModel() {
		super(0.0F);

		this.bipedHeadwear = new ModelRenderer(this, 0, 0);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 0);
		this.bipedRightLeg = new ModelRenderer(this, 0, 0);

		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-4F, -8F, -4F, 8, 8, 8);
		this.bipedHead.setRotationPoint(0F, 0F, 0F);

		this.bipedBody = new ModelRenderer(this, 32, 0);
		this.bipedBody.addBox(-4F, 0F, -2F, 8, 24, 4);
		this.bipedBody.setRotationPoint(0F, 0F, 0F);

		this.bipedRightArm = new ModelRenderer(this, 0, 16);
		this.bipedRightArm.addBox(-3F, -2F, -2F, 4, 12, 4);
		this.bipedRightArm.setRotationPoint(-5F, 2F, 0F);

		this.bipedLeftArm = new ModelRenderer(this, 0, 16);
		this.bipedLeftArm.addBox(-1F, -2F, -2F, 4, 12, 4);
		this.bipedLeftArm.setRotationPoint(5F, 2F, 0F);

		this.leftSleeve = new ModelRenderer(this, 16, 16);
		this.leftSleeve.addBox(-1F, -2F, 2F, 4, 12, 4);
		this.leftSleeve.setRotationPoint(0F, 0F, 0F);

		this.bipedLeftArm.addChild(this.leftSleeve);

		this.rightSleeve = new ModelRenderer(this, 16, 16);
		this.rightSleeve.addBox(-3F, -2F, 2F, 4, 12, 4);
		this.rightSleeve.setRotationPoint(0F, 0F, 0F);

		this.bipedRightArm.addChild(this.rightSleeve);

	}

	@Override
	public void setRotationAngles(AdherentEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// rotate head normally
		this.bipedHead.rotateAngleY = netHeadYaw / (180F / (float) Math.PI);
		this.bipedHead.rotateAngleX = headPitch / (180F / (float) Math.PI);

		this.bipedRightArm.rotateAngleX = 0.0F;
		this.bipedLeftArm.rotateAngleX = 0.0F;
		this.bipedRightArm.rotateAngleZ = 0.0F;
		this.bipedLeftArm.rotateAngleZ = 0.0F;

		this.bipedRightArm.rotateAngleZ += MathHelper.cos((ageInTicks + 10F) * 0.133F) * 0.3F + 0.3F;
		this.bipedLeftArm.rotateAngleZ -= MathHelper.cos((ageInTicks + 10F) * 0.133F) * 0.3F + 0.3F;
		this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
	}
}
