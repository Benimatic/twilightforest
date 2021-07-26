package twilightforest.client.model.armor;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ArcticArmorModel extends TFArmorModel {

	public ArcticArmorModel(ModelPart expand) {
		super(expand);

		this.head.getChild("right_hood");
		this.head.getChild("left_hood");
		this.head.getChild("top_hood");
		this.head.getChild("bottom_hood");
	}

	public static LayerDefinition addPieces(CubeDeformation deformation) {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("right_hood",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-1.0F, -2.0F, -1.0F, 1, 4, 1, deformation),
				PartPose.offset(-2.5F, -3.0F, -5.0F));

		partdefinition.addOrReplaceChild("left_hood",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(0.0F, -2.0F, -1.0F, 1, 4, 1, deformation),
				PartPose.offset(2.5F, -3.0F, -5.0F));

		partdefinition.addOrReplaceChild("top_hood",
				CubeListBuilder.create()
						.texOffs(24, 0)
						.addBox(-2.0F, -1.0F, -1.0F, 4, 1, 1, deformation),
				PartPose.offset(0.0F, -5.5F, -5.0F));

		partdefinition.addOrReplaceChild("bottom_hood",
				CubeListBuilder.create()
						.texOffs(24, 0)
						.addBox(-2.0F, -1.0F, -1.0F, 4, 1, 1, deformation),
				PartPose.offset(0.0F, 0.5F, -5.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}
