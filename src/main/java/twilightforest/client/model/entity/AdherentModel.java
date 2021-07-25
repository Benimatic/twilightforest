package twilightforest.client.model.entity;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import twilightforest.entity.AdherentEntity;

public class AdherentModel extends HumanoidModel<AdherentEntity> {

	ModelPart leftSleeve;
	ModelPart rightSleeve;

	public AdherentModel() {
		super(0.0F);

		this.hat = new ModelPart(this, 0, 0);
		this.leftLeg = new ModelPart(this, 0, 0);
		this.rightLeg = new ModelPart(this, 0, 0);

		this.head = new ModelPart(this, 0, 0);
		this.head.addBox(-4F, -8F, -4F, 8, 8, 8);
		this.head.setPos(0F, 0F, 0F);

		this.body = new ModelPart(this, 32, 0);
		this.body.addBox(-4F, 0F, -2F, 8, 24, 4);
		this.body.setPos(0F, 0F, 0F);

		this.rightArm = new ModelPart(this, 0, 16);
		this.rightArm.addBox(-3F, -2F, -2F, 4, 12, 4);
		this.rightArm.setPos(-5F, 2F, 0F);

		this.leftArm = new ModelPart(this, 0, 16);
		this.leftArm.addBox(-1F, -2F, -2F, 4, 12, 4);
		this.leftArm.setPos(5F, 2F, 0F);

		this.leftSleeve = new ModelPart(this, 16, 16);
		this.leftSleeve.addBox(-1F, -2F, 2F, 4, 12, 4);
		this.leftSleeve.setPos(0F, 0F, 0F);

		this.leftArm.addChild(this.leftSleeve);

		this.rightSleeve = new ModelPart(this, 16, 16);
		this.rightSleeve.addBox(-3F, -2F, 2F, 4, 12, 4);
		this.rightSleeve.setPos(0F, 0F, 0F);

		this.rightArm.addChild(this.rightSleeve);

	}

	@Override
	public void setupAnim(AdherentEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// rotate head normally
		this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.head.xRot = headPitch / (180F / (float) Math.PI);

		this.rightArm.xRot = 0.0F;
		this.leftArm.xRot = 0.0F;
		this.rightArm.zRot = 0.0F;
		this.leftArm.zRot = 0.0F;

		this.rightArm.zRot += Mth.cos((ageInTicks + 10F) * 0.133F) * 0.3F + 0.3F;
		this.leftArm.zRot -= Mth.cos((ageInTicks + 10F) * 0.133F) * 0.3F + 0.3F;
		this.rightArm.xRot += Mth.sin(ageInTicks * 0.067F) * 0.05F;
		this.leftArm.xRot -= Mth.sin(ageInTicks * 0.067F) * 0.05F;
	}
}
