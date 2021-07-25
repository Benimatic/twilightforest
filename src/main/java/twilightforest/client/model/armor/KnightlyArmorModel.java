package twilightforest.client.model.armor;

import net.minecraft.client.model.geom.ModelPart;

public class KnightlyArmorModel extends TFArmorModel {

	public ModelPart righthorn1;
	public ModelPart righthorn2;
	public ModelPart lefthorn1;
	public ModelPart lefthorn2;

	public ModelPart shoulderSpike1;
	public ModelPart shoulderSpike2;

	public ModelPart shoeSpike1;
	public ModelPart shoeSpike2;


	public KnightlyArmorModel(float expand) {
		super(expand);

		this.righthorn1 = new ModelPart(this, 24, 0);
		this.righthorn1.addBox(-5.5F, -1.5F, -1.5F, 5, 3, 3);
		this.righthorn1.setPos(-4.0F, -6.5F, 0.0F);
		this.righthorn1.yRot = -15F / (180F / (float) Math.PI);
		this.righthorn1.zRot = 10F / (180F / (float) Math.PI);

		this.righthorn2 = new ModelPart(this, 54, 16);
		this.righthorn2.addBox(-3.5F, -1.0F, -1.0F, 3, 2, 2);
		this.righthorn2.setPos(-4.5F, 0.0F, 0.0F);
		this.righthorn2.zRot = 10F / (180F / (float) Math.PI);

		this.head.addChild(righthorn1);
		this.righthorn1.addChild(righthorn2);

		this.lefthorn1 = new ModelPart(this, 24, 0);
		this.lefthorn1.mirror = true;
		this.lefthorn1.addBox(0.5F, -1.5F, -1.5F, 5, 3, 3);
		this.lefthorn1.setPos(4.0F, -6.5F, 0.0F);
		this.lefthorn1.yRot = 15F / (180F / (float) Math.PI);
		this.lefthorn1.zRot = -10F / (180F / (float) Math.PI);

		this.lefthorn2 = new ModelPart(this, 54, 16);
		this.lefthorn2.addBox(0.5F, -1.0F, -1.0F, 3, 2, 2);
		this.lefthorn2.setPos(4.5F, 0.0F, 0.0F);
		this.lefthorn2.zRot = -10F / (180F / (float) Math.PI);

		this.head.addChild(lefthorn1);
		this.lefthorn1.addChild(lefthorn2);

		this.shoulderSpike1 = new ModelPart(this, 0, 0);
		this.shoulderSpike1.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.5F);
		this.shoulderSpike1.setPos(-3.75F, -2.5F, 0.0F);
		this.shoulderSpike1.xRot = 45F / (180F / (float) Math.PI);
		this.shoulderSpike1.yRot = 10F / (180F / (float) Math.PI);
		this.shoulderSpike1.zRot = 35F / (180F / (float) Math.PI);

		this.rightArm.addChild(shoulderSpike1);

		this.shoulderSpike2 = new ModelPart(this, 0, 0);
		this.shoulderSpike2.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.5F);
		this.shoulderSpike2.setPos(3.75F, -2.5F, 0.0F);
		this.shoulderSpike2.xRot = -45F / (180F / (float) Math.PI);
		this.shoulderSpike2.yRot = -10F / (180F / (float) Math.PI);
		this.shoulderSpike2.zRot = 55F / (180F / (float) Math.PI);

		this.leftArm.addChild(shoulderSpike2);

		this.shoeSpike1 = new ModelPart(this, 0, 0);
		this.shoeSpike1.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.5F);
		this.shoeSpike1.setPos(-2.5F, 11F, 2.0F);
		this.shoeSpike1.yRot = -45F / (180F / (float) Math.PI);

		this.rightLeg.addChild(shoeSpike1);

		this.shoeSpike2 = new ModelPart(this, 0, 0);
		this.shoeSpike2.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.5F);
		this.shoeSpike2.setPos(2.5F, 11F, 2.0F);
		this.shoeSpike2.yRot = 45F / (180F / (float) Math.PI);

		this.leftLeg.addChild(shoeSpike2);
	}
}
