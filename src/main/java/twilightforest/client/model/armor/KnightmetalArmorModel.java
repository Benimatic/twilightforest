package twilightforest.client.model.armor;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class KnightmetalArmorModel extends TFArmorModel {

	public ModelPart righthorn1;
	public ModelPart righthorn2;
	public ModelPart lefthorn1;
	public ModelPart lefthorn2;

	public ModelPart shoulderSpike1;
	public ModelPart shoulderSpike2;

	public ModelPart shoeSpike1;
	public ModelPart shoeSpike2;


	public KnightmetalArmorModel(ModelPart expand) {
		super(expand);
		this.righthorn1 = this.head.getChild("right_horn_1");
		this.righthorn2 = this.righthorn1.getChild("right_horn_2");
		this.lefthorn1 = this.head.getChild("left_horn_1");
		this.lefthorn2 = this.lefthorn1.getChild("left_horn_2");
		this.shoulderSpike1 = this.rightArm.getChild("shoulder_spike_1");
		this.shoulderSpike2 = this.leftArm.getChild("shoulder_spike_2");
		this.shoeSpike1 = this.rightLeg.getChild("shoe_spike_1");
		this.shoeSpike2 = this.leftLeg.getChild("shoe_spike_2");
	}

	public static LayerDefinition addPieces() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		CubeDeformation deformation = new CubeDeformation(0.5F);

		partdefinition.addOrReplaceChild("right_horn_1",
				CubeListBuilder.create()
						.texOffs(24, 0)
						.addBox(-5.5F, -1.5F, -1.5F, 5, 3, 3),
				PartPose.offsetAndRotation(-4.0F, -6.5F, 0.0F,
						0.0F, -15F / (180F / (float) Math.PI), 10F / (180F / (float) Math.PI)));

		partdefinition.addOrReplaceChild("right_horn_2",
				CubeListBuilder.create()
						.texOffs(54, 16)
						.addBox(-3.5F, -1.0F, -1.0F, 3, 2, 2),
				PartPose.offsetAndRotation(-4.5F, 0.0F, 0.0F,
						0.0F, 0.0F, 10F / (180F / (float) Math.PI)));

		partdefinition.addOrReplaceChild("left_horn_1",
				CubeListBuilder.create()
						.texOffs(24, 0).mirror()
						.addBox(0.5F, -1.5F, -1.5F, 5, 3, 3),
				PartPose.offsetAndRotation(4.0F, -6.5F, 0.0F,
						0.0F, 15F / (180F / (float) Math.PI), -10F / (180F / (float) Math.PI)));

		partdefinition.addOrReplaceChild("left_horn_2",
				CubeListBuilder.create()
						.texOffs(54, 16)
						.addBox(0.5F, -1.0F, -1.0F, 3, 2, 2),
				PartPose.offsetAndRotation(4.5F, 0.0F, 0.0F,
						0.0F, 0.0F, -10F / (180F / (float) Math.PI)));

		partdefinition.addOrReplaceChild("shoulder_spike_1",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, deformation),
				PartPose.offsetAndRotation(-3.75F, -2.5F, 0.0F,
						45F / (180F / (float) Math.PI), 10F / (180F / (float) Math.PI), 35F / (180F / (float) Math.PI)));

		partdefinition.addOrReplaceChild("shoulder_spike_2",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, deformation),
				PartPose.offsetAndRotation(3.75F, -2.5F, 0.0F,
						-45F / (180F / (float) Math.PI), -10F / (180F / (float) Math.PI), 55F / (180F / (float) Math.PI)));

		partdefinition.addOrReplaceChild("shoe_spike_1",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, deformation),
				PartPose.offsetAndRotation(-2.5F, 11F, 2.0F,
						0.0F, -45F / (180F / (float) Math.PI), 0.0F));

		partdefinition.addOrReplaceChild("shoe_spike_2",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, deformation),
				PartPose.offsetAndRotation(2.5F, 11F, 2.0F, 0.0F, 45F / (180F / (float) Math.PI), 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}
