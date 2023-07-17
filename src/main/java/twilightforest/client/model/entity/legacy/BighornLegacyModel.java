package twilightforest.client.model.entity.legacy;

import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import twilightforest.entity.passive.Bighorn;

public class BighornLegacyModel<T extends Bighorn> extends SheepModel<T> {

	public BighornLegacyModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition create() {
		MeshDefinition mesh = SheepModel.createBodyMesh(0, CubeDeformation.NONE);
		PartDefinition partRoot = mesh.getRoot();

		var head = partRoot.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 7.0F),
				PartPose.offset(0.0F, 6.0F, -8.0F));

		//register horns as separate parts, prevents a crash with the legacy pack
		head.addOrReplaceChild("left_horn", CubeListBuilder.create()
						.texOffs(28, 16)
						.addBox(-5.0F, -4.0F, -4.0F, 2.0F, 2.0F, 2.0F)
						.texOffs(16, 13)
						.addBox(-6.0F, -5.0F, -3.0F, 2.0F, 2.0F, 4.0F)
						.texOffs(16, 19)
						.addBox(-7.0F, -4.0F, 0.0F, 2.0F, 5.0F, 2.0F)
						.texOffs(18, 27)
						.addBox(-8.0F, 0.0F, -2.0F, 2.0F, 2.0F, 3.0F)
						.texOffs(28, 27)
						.addBox(-9.0F, -1.0F, -3.0F, 2.0F, 2.0F, 1.0F),
				PartPose.ZERO);

		head.addOrReplaceChild("right_horn", CubeListBuilder.create()
						.texOffs(28, 16)
						.addBox(3.0F, -4.0F, -4.0F, 2.0F, 2.0F, 2.0F)
						.texOffs(16, 13)
						.addBox(4.0F, -5.0F, -3.0F, 2.0F, 2.0F, 4.0F)
						.texOffs(16, 19)
						.addBox(5.0F, -4.0F, 0.0F, 2.0F, 5.0F, 2.0F)
						.texOffs(18, 27)
						.addBox(6.0F, 0.0F, -2.0F, 2.0F, 2.0F, 3.0F)
						.texOffs(28, 27)
						.addBox(7.0F, -1.0F, -3.0F, 2.0F, 2.0F, 1.0F),
				PartPose.ZERO);

		partRoot.addOrReplaceChild("body", CubeListBuilder.create()
						.texOffs(36, 10)
						.addBox(-4.0F, -9.0F, -7.0F, 8.0F, 15.0F, 6.0F),
				PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, 1.5707963267948966F, 0.0F, 0.0F));

		partRoot.addOrReplaceChild("left_front_leg", CubeListBuilder.create()
						.texOffs(0, 16)
						.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
				PartPose.offset(-3.0F, 12.0F, 7.0F));

		partRoot.addOrReplaceChild("right_front_leg", CubeListBuilder.create()
						.texOffs(0, 16)
						.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
				PartPose.offset(3.0F, 12.0F, 7.0F));

		partRoot.addOrReplaceChild("left_hind_leg", CubeListBuilder.create()
						.texOffs(0, 16)
						.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
				PartPose.offset(-3.0F, 12.0F, -5.0F));

		partRoot.addOrReplaceChild("right_hind_leg", CubeListBuilder.create()
						.texOffs(0, 16)
						.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
				PartPose.offset(3.0F, 12.0F, -5.0F));

		return LayerDefinition.create(mesh, 64, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.getChild("left_horn").visible = !entity.isBaby();
		this.head.getChild("right_horn").visible = !entity.isBaby();
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}
}
