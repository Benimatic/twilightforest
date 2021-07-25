package twilightforest.client.model.entity.legacy;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import twilightforest.entity.MinotaurEntity;

public class MinotaurLegacyModel extends HumanoidModel<MinotaurEntity> {

	public ModelPart righthorn1;
	public ModelPart righthorn2;
	public ModelPart lefthorn1;
	public ModelPart lefthorn2;

	ModelPart snout;

	public MinotaurLegacyModel() {
		super(0.0F);

		this.righthorn1 = new ModelPart(this, 24, 0);
		this.righthorn1.addBox(-5.5F, -1.5F, -1.5F, 5, 3, 3);
		this.righthorn1.setPos(-2.5F, -6.5F, 0.0F);
		this.righthorn1.yRot = -25F / (180F / (float) Math.PI);
		this.righthorn1.zRot = 10F / (180F / (float) Math.PI);

		this.righthorn2 = new ModelPart(this, 40, 0);
		this.righthorn2.addBox(-3.5F, -1.0F, -1.0F, 3, 2, 2);
		this.righthorn2.setPos(-4.5F, 0.0F, 0.0F);
		this.righthorn2.yRot = -15F / (180F / (float) Math.PI);
		this.righthorn2.zRot = 45F / (180F / (float) Math.PI);

		this.righthorn1.addChild(righthorn2);

		this.lefthorn1 = new ModelPart(this, 24, 0);
		this.lefthorn1.mirror = true;
		this.lefthorn1.addBox(0.5F, -1.5F, -1.5F, 5, 3, 3);
		this.lefthorn1.setPos(2.5F, -6.5F, 0.0F);
		this.lefthorn1.yRot = 25F / (180F / (float) Math.PI);
		this.lefthorn1.zRot = -10F / (180F / (float) Math.PI);

		this.lefthorn2 = new ModelPart(this, 40, 0);
		this.lefthorn2.addBox(0.5F, -1.0F, -1.0F, 3, 2, 2);
		this.lefthorn2.setPos(4.5F, 0.0F, 0.0F);
		this.lefthorn2.yRot = 15F / (180F / (float) Math.PI);
		this.lefthorn2.zRot = -45F / (180F / (float) Math.PI);

		this.lefthorn1.addChild(lefthorn2);

		this.head.addChild(righthorn1);
		this.head.addChild(lefthorn1);

		snout = new ModelPart(this, 9, 12);
		snout.addBox(-2, -1, -1, 4, 3, 1);
		snout.setPos(0F, -2.0F, -4F);

		this.head.addChild(snout);

		// kill off headwear box
		this.hat = new ModelPart(this, 0, 0);
	}
}
