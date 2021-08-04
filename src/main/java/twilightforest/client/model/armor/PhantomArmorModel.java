package twilightforest.client.model.armor;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class PhantomArmorModel extends KnightmetalArmorModel {

	public PhantomArmorModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition addPieces() {
		MeshDefinition mesh = KnightmetalArmorModel.setup();
		PartDefinition part = mesh.getRoot();

		var head = part.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
						.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		var rightHorn = head.addOrReplaceChild("right_horn_1",
				CubeListBuilder.create()
						.texOffs(24, 0)
						.addBox(-5.5F, -1.5F, -1.5F, 5, 3, 3),
				PartPose.offsetAndRotation(-4.0F, -6.5F, 0.0F,
						0.0F, -25F / (180F / Mth.PI), 45F / (180F / Mth.PI)));

		rightHorn.addOrReplaceChild("right_horn_2",
				CubeListBuilder.create()
						.texOffs(54, 16)
						.addBox(-3.5F, -1.0F, -1.0F, 3, 2, 2),
				PartPose.offsetAndRotation(-4.5F, 0.0F, 0.0F,
						0.0F, -15F / (180F / Mth.PI), 45F / (180F / Mth.PI)));

		var leftHorn = head.addOrReplaceChild("left_horn_1",
				CubeListBuilder.create()
						.texOffs(24, 0).mirror()
						.addBox(0.5F, -1.5F, -1.5F, 5, 3, 3),
				PartPose.offsetAndRotation(4.0F, -6.5F, 0.0F,
						0.0F, 25F / (180F / Mth.PI), -45F / (180F / Mth.PI)));

		leftHorn.addOrReplaceChild("left_horn_2",
				CubeListBuilder.create()
						.texOffs(54, 16)
						.addBox(0.5F, -1.0F, -1.0F, 3, 2, 2),
				PartPose.offsetAndRotation(4.5F, 0.0F, 0.0F,
						0.0F, 15F / (180F / Mth.PI), -45F / (180F / Mth.PI)));

		return LayerDefinition.create(mesh, 64, 32);
	}
}
