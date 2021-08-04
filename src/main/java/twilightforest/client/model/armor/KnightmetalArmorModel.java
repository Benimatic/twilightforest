package twilightforest.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class KnightmetalArmorModel extends TFArmorModel {

	public KnightmetalArmorModel(ModelPart root) {
		super(root);
	}

	public static MeshDefinition setup() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0);
		PartDefinition partdefinition = meshdefinition.getRoot();
		CubeDeformation deformation = new CubeDeformation(0.5F);

		var head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
				.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		var rightHorn = head.addOrReplaceChild("right_horn_1",
				CubeListBuilder.create()
						.texOffs(24, 0)
						.addBox(-5.5F, -1.5F, -1.5F, 5, 3, 3),
				PartPose.offsetAndRotation(-4.0F, -6.5F, 0.0F,
						0.0F, -15F / (180F / (float) Math.PI), 10F / (180F / (float) Math.PI)));

		rightHorn.addOrReplaceChild("right_horn_2",
				CubeListBuilder.create()
						.texOffs(54, 16)
						.addBox(-3.5F, -1.0F, -1.0F, 3, 2, 2),
				PartPose.offsetAndRotation(-4.5F, 0.0F, 0.0F,
						0.0F, 0.0F, 10F / (180F / (float) Math.PI)));

		var leftHorn = head.addOrReplaceChild("left_horn_1",
				CubeListBuilder.create()
						.texOffs(24, 0).mirror()
						.addBox(0.5F, -1.5F, -1.5F, 5, 3, 3),
				PartPose.offsetAndRotation(4.0F, -6.5F, 0.0F,
						0.0F, 15F / (180F / (float) Math.PI), -10F / (180F / (float) Math.PI)));

		leftHorn.addOrReplaceChild("left_horn_2",
				CubeListBuilder.create()
						.texOffs(54, 16)
						.addBox(0.5F, -1.0F, -1.0F, 3, 2, 2),
				PartPose.offsetAndRotation(4.5F, 0.0F, 0.0F,
						0.0F, 0.0F, -10F / (180F / (float) Math.PI)));

		var rightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create()
						.texOffs(40, 16)
						.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
				PartPose.offset(-5.0F, 2.0F, 0.0F));

		var leftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create()
						.texOffs(40, 16).mirror()
						.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
				PartPose.offset(5.0F, 2.0F, 0.0F));

		rightArm.addOrReplaceChild("shoulder_spike_1",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, deformation),
				PartPose.offsetAndRotation(-3.75F, -2.5F, 0.0F,
						45F / (180F / (float) Math.PI), 10F / (180F / (float) Math.PI), 35F / (180F / (float) Math.PI)));

		leftArm.addOrReplaceChild("shoulder_spike_2",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, deformation),
				PartPose.offsetAndRotation(3.75F, -2.5F, 0.0F,
						-45F / (180F / (float) Math.PI), -10F / (180F / (float) Math.PI), 55F / (180F / (float) Math.PI)));

		var rightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create()
						.texOffs(0, 16)
						.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
				PartPose.offset(-1.9F, 12.0F, 0.0F));

		var leftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create()
						.texOffs(0, 16).mirror()
						.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
				PartPose.offset(1.9F, 12.0F, 0.0F));


		rightLeg.addOrReplaceChild("shoe_spike_1",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, deformation),
				PartPose.offsetAndRotation(-2.5F, 11F, 2.0F,
						0.0F, -45F / (180F / (float) Math.PI), 0.0F));

		leftLeg.addOrReplaceChild("shoe_spike_2",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, deformation),
				PartPose.offsetAndRotation(2.5F, 11F, 2.0F, 0.0F, 45F / (180F / (float) Math.PI), 0.0F));

		return meshdefinition;
	}

	public static LayerDefinition addPieces() {
		return LayerDefinition.create(setup(), 64, 32);
	}
}
