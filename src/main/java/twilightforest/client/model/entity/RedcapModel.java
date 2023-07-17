package twilightforest.client.model.entity;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import twilightforest.entity.monster.Redcap;

public class RedcapModel<T extends Redcap> extends FixedHumanoidModel<T> {

	public RedcapModel(ModelPart root) {
		super(root, 3.0F);
	}

	public static LayerDefinition create() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition partRoot = mesh.getRoot();

		partRoot.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 1)
						.addBox(-5.5F, -6.0F, -0.5F, 2.0F, 3.0F, 0.0F)
						.texOffs(0, 1).mirror()
						.addBox(3.5F, -6.0F, -0.5F, 2.0F, 3.0F, 0.0F).mirror(false)
						.texOffs(0, 0)
						.addBox(-3.5F, -8.0F, -3.5F, 7.0F, 7.0F, 7.0F),
				PartPose.offset(0.0F, 8.0F, 0.0F));

		partRoot.addOrReplaceChild("hat", CubeListBuilder.create()
						.texOffs(28, 0)
						.addBox(-3.5F, -8.0F, -3.5F, 7.0F, 8.0F, 7.0F, new CubeDeformation(0.25F)),
				PartPose.offset(0.0F, 6.0F, 0.0F));

		partRoot.addOrReplaceChild("body", CubeListBuilder.create()
						.texOffs(15, 19)
						.addBox(-4.0F, 1.0F, -2.0F, 8.0F, 9.0F, 4.0F),
				PartPose.offset(0.0F, 5.0F, 0.0F));

		partRoot.addOrReplaceChild("right_arm", CubeListBuilder.create()
						.texOffs(39, 17).mirror()
						.addBox(-3.0F, -1.0F, -1.5F, 3.0F, 12.0F, 3.0F).mirror(false),
				PartPose.offset(-4.0F, 7.0F, 0.0F));

		partRoot.addOrReplaceChild("left_arm", CubeListBuilder.create()
						.texOffs(39, 17)
						.addBox(0.0F, -1.0F, -1.5F, 3.0F, 12.0F, 3.0F),
				PartPose.offset(4.0F, 7.0F, 0.0F));

		partRoot.addOrReplaceChild("right_leg", CubeListBuilder.create()
						.texOffs(0, 19).mirror()
						.addBox(-1.5F, 0.0F, -2.0F, 3.0F, 9.0F, 4.0F).mirror(false),
				PartPose.offset(-2.5F, 15.0F, 0.0F));

		partRoot.addOrReplaceChild("left_leg", CubeListBuilder.create()
						.texOffs(0, 19)
						.addBox(-1.5F, 0.0F, -2.0F, 3.0F, 9.0F, 4.0F),
				PartPose.offset(2.5F, 15.0F, 0.0F));


		return LayerDefinition.create(mesh, 64, 32);
	}
}