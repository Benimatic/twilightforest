package twilightforest.client.model.entity;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import twilightforest.entity.EntityTFMinotaur;

public class ModelTFMinotaur<T extends EntityTFMinotaur> extends BipedModel<T> {

	public RendererModel righthorn1;
	public RendererModel righthorn2;
	public RendererModel lefthorn1;
	public RendererModel lefthorn2;

	RendererModel snout;

	public ModelTFMinotaur() {

		this.righthorn1 = new RendererModel(this, 24, 0);
		this.righthorn1.addBox(-5.5F, -1.5F, -1.5F, 5, 3, 3);
		this.righthorn1.setRotationPoint(-2.5F, -6.5F, 0.0F);
		this.righthorn1.rotateAngleY = -25F / (180F / (float) Math.PI);
		this.righthorn1.rotateAngleZ = 10F / (180F / (float) Math.PI);

		this.righthorn2 = new RendererModel(this, 40, 0);
		this.righthorn2.addBox(-3.5F, -1.0F, -1.0F, 3, 2, 2);
		this.righthorn2.setRotationPoint(-4.5F, 0.0F, 0.0F);
		this.righthorn2.rotateAngleY = -15F / (180F / (float) Math.PI);
		this.righthorn2.rotateAngleZ = 45F / (180F / (float) Math.PI);

		this.righthorn1.addChild(righthorn2);

		this.lefthorn1 = new RendererModel(this, 24, 0);
		this.lefthorn1.mirror = true;
		this.lefthorn1.addBox(0.5F, -1.5F, -1.5F, 5, 3, 3);
		this.lefthorn1.setRotationPoint(2.5F, -6.5F, 0.0F);
		this.lefthorn1.rotateAngleY = 25F / (180F / (float) Math.PI);
		this.lefthorn1.rotateAngleZ = -10F / (180F / (float) Math.PI);

		this.lefthorn2 = new RendererModel(this, 40, 0);
		this.lefthorn2.addBox(0.5F, -1.0F, -1.0F, 3, 2, 2);
		this.lefthorn2.setRotationPoint(4.5F, 0.0F, 0.0F);
		this.lefthorn2.rotateAngleY = 15F / (180F / (float) Math.PI);
		this.lefthorn2.rotateAngleZ = -45F / (180F / (float) Math.PI);

		this.lefthorn1.addChild(lefthorn2);

		this.bipedHead.addChild(righthorn1);
		this.bipedHead.addChild(lefthorn1);

		snout = new RendererModel(this, 9, 12);
		snout.addBox(-2, -1, -1, 4, 3, 1);
		snout.setRotationPoint(0F, -2.0F, -4F);

		this.bipedHead.addChild(snout);

		// kill off headwear box
		this.bipedHeadwear = new RendererModel(this, 0, 0);

	}

}
