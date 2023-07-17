package twilightforest.compat.curios.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class CharmOfLife2NecklaceModel extends HumanoidModel<LivingEntity> {

	public CharmOfLife2NecklaceModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition create() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-6.5F, -0.1F, -4.0F, 13.0F, 21.0F, 8.0F),
				PartPose.ZERO);

		body.addOrReplaceChild("heart", CubeListBuilder.create()
						.texOffs(46, 0).addBox(-4.0F, 4.9F, -4.5F, 3.0F, 2.0F, 1.0F)
						.texOffs(56, 0).addBox(1.0F, 4.9F, -4.5F, 3.0F, 2.0F, 1.0F)
						.texOffs(42, 5).addBox(-5.0F, 5.9F, -4.5F, 10.0F, 4.0F, 1.0F)
						.texOffs(44, 12).addBox(-4.0F, 9.9F, -4.5F, 8.0F, 1.0F, 1.0F)
						.texOffs(46, 16).addBox(-3.0F, 10.9F, -4.5F, 6.0F, 1.0F, 1.0F)
						.texOffs(48, 20).addBox(-2.0F, 11.9F, -4.5F, 4.0F, 1.0F, 1.0F)
						.texOffs(50, 24).addBox(-1.0F, 12.9F, -4.5F, 2.0F, 1.0F, 1.0F),
				PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 64, 48);
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(body);
	}

	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer consumer, int light, int overlay, float red, float green, float blue, float alpha) {
		stack.pushPose();
		stack.scale(0.55F, 0.55F, 0.55F);
		this.body.render(stack, consumer, light, overlay, red, green, blue, alpha);
		stack.popPose();
	}
}
