package twilightforest.client.model.armor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.EquipmentSlot;

public class YetiArmorModel extends TFArmorModel {

	private final EquipmentSlot slot;

	private final ModelPart bipedLegBody;
	private final ModelPart rightRuff;
	private final ModelPart leftRuff;
	private final ModelPart rightToe;
	private final ModelPart leftToe;

	public YetiArmorModel(EquipmentSlot slot, ModelPart part) {
		super(part);
		this.slot = slot;
		this.bipedLegBody = part.getChild("biped_leg_body");
		this.rightRuff = this.rightLeg.getChild("right_ruff");
		this.leftRuff = this.leftLeg.getChild("left_ruff");
		this.rightToe = this.rightLeg.getChild("right_toe");
		this.leftToe = this.leftLeg.getChild("left_toe");
	}

	public static MeshDefinition addPieces(CubeDeformation deformation) {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(deformation, 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();

		//bigger head
		var head= partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-4.5F, -7.5F, -4.0F, 9, 8, 8, deformation),
		PartPose.offset(0.0F, 0.0F, 0.0F));

		// add horns
		addPairHorns(head, 1, -8.0F, 35F);
		addPairHorns(head, 2, -6.0F, 15F);
		addPairHorns(head, 3, -4.0F, -5F);


		// change leg texture
		var rightLeg = partdefinition.addOrReplaceChild("right_leg",
				CubeListBuilder.create()
						.texOffs(40, 0)
						.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, deformation),
				PartPose.offset(-1.9F, 12.0F + 0.0F, 0.0F));

		var leftLeg = partdefinition.addOrReplaceChild("left_leg",
				CubeListBuilder.create()
						.texOffs(40, 0).mirror()
						.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, deformation),
				PartPose.offset(1.9F, 12.0F + 0.0F, 0.0F));

		rightLeg.addOrReplaceChild("right_ruff",
				CubeListBuilder.create()
						.texOffs(40, 22)
						.addBox(-2.5F, 0.0F, -2.5F, 5, 2, 5, deformation),
				PartPose.offset(0.0F, 6.0F, 0.0F));


		leftLeg.addOrReplaceChild("left_ruff",
				CubeListBuilder.create()
						.texOffs(40, 22)
						.addBox(-2.5F, 0.0F, -2.5F, 5, 2, 5, deformation),
				PartPose.offset(0.0F, 6.0F, 0.0F));


		rightLeg.addOrReplaceChild("right_toe",
				CubeListBuilder.create()
						.texOffs(40, 17)
						.addBox(-2.0F, 0.0F, -1.0F, 4, 2, 1, deformation),
				PartPose.offset(0.0F, 10.0F, -2.0F));


		leftLeg.addOrReplaceChild("left_toe",
				CubeListBuilder.create()
						.texOffs(40, 17)
						.addBox(-2.0F, 0.0F, -1.0F, 4, 2, 1, deformation),
				PartPose.offset(0.0F, 10.0F, -2.0F));


		// stuff for chest and legs
		partdefinition.addOrReplaceChild("body",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-4.0F, 0.0F, -2.0F, 8, 11, 4, deformation),
				PartPose.offset(0.0F, 0.0F + 0.0f, 0.0F));

		partdefinition.addOrReplaceChild("biped_leg_body",
				CubeListBuilder.create()
						.texOffs(40, 16)
						.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, deformation),
				PartPose.offset(0.0F, 0.0F + 0.0f, 0.0F));

		partdefinition.addOrReplaceChild("right_arm",
				CubeListBuilder.create()
						.texOffs(0, 16)
						.addBox(-3.0F, -2.0F, -2.0F, 4, 10, 4, deformation),
				PartPose.offset(-5.0F, 2.0F + 0.0f, 0.0F));

		partdefinition.addOrReplaceChild("left_arm",
				CubeListBuilder.create()
						.texOffs(0, 16).mirror()
						.addBox(-1.0F, -2.0F, -2.0F, 4, 10, 4, deformation),
				PartPose.offset(5.0F, 2.0F + 0.0f, 0.0F));

		return meshdefinition;
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return Iterables.concat(super.bodyParts(), ImmutableList.of(bipedLegBody));
	}

	private static void addPairHorns(PartDefinition partdefinition, int iter, float height, float zangle) {

		var leftBottom = partdefinition.addOrReplaceChild("horn_" + iter +"_left_bottom",
				CubeListBuilder.create()
						.texOffs(0, 19)
						.addBox(-3.0F, -1.5F, -1.5F, 3, 3, 3),
				PartPose.offsetAndRotation(-4.5F, height, -1.0F,
						0.0F, -30F / (180F / (float) Math.PI), zangle / (180F / (float) Math.PI)));

		leftBottom.addOrReplaceChild("horn_" + iter + "_left_top",
				CubeListBuilder.create()
						.texOffs(0, 26)
						.addBox(-4.0F, -1.0F, -1.0F, 5, 2, 2),
				PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F,
						0.0F, -20F / (180F / (float) Math.PI), zangle / (180F / (float) Math.PI)));

		var rightBottom = partdefinition.addOrReplaceChild("horn_" + iter + "_right_bottom",
				CubeListBuilder.create()
						.texOffs(0, 19)
						.addBox(0.0F, -1.5F, -1.5F, 3, 3, 3),
				PartPose.offsetAndRotation(4.5F, height, -1.0F,
						0.0F, 30F / (180F / (float) Math.PI), -zangle / (180F / (float) Math.PI)));

		rightBottom.addOrReplaceChild("horn_" + iter + "_right_top",
				CubeListBuilder.create()
						.texOffs(0, 26)
						.addBox(-1.0F, -1.0F, -1.0F, 5, 2, 2),
				PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F,
						0.0F, 20F / (180F / (float) Math.PI), -zangle / (180F / (float) Math.PI)));
	}

	@Override
	public void setAllVisible(boolean visible) {
		super.setAllVisible(visible);
		bipedLegBody.visible = visible;
	}

	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float scale) {
		switch (slot) {
			case HEAD -> {
				this.head.visible = true;
				this.hat.visible = false;
				this.body.visible = false;
				this.rightArm.visible = false;
				this.leftArm.visible = false;
				this.bipedLegBody.visible = false;
				this.rightLeg.visible = false;
				this.leftLeg.visible = false;
			}
			case CHEST -> {
				this.head.visible = false;
				this.hat.visible = false;
				this.body.visible = true;
				this.rightArm.visible = true;
				this.leftArm.visible = true;
				this.bipedLegBody.visible = false;
				this.rightLeg.visible = false;
				this.leftLeg.visible = false;
			}
			case LEGS -> {
				this.head.visible = false;
				this.hat.visible = false;
				this.body.visible = false;
				this.rightArm.visible = false;
				this.leftArm.visible = false;
				this.bipedLegBody.visible = true;
				this.rightLeg.visible = true;
				this.leftLeg.visible = true;
				this.leftRuff.visible = false;
				this.leftToe.visible = false;
				this.rightRuff.visible = false;
				this.rightToe.visible = false;
			}
			case FEET -> {
				this.head.visible = false;
				this.hat.visible = false;
				this.body.visible = false;
				this.rightArm.visible = false;
				this.leftArm.visible = false;
				this.bipedLegBody.visible = false;
				this.rightLeg.visible = true;
				this.leftLeg.visible = true;
				this.leftRuff.visible = true;
				this.leftToe.visible = true;
				this.rightRuff.visible = true;
				this.rightToe.visible = true;
			}
			default -> { }
		}
		super.renderToBuffer(stack, builder, light, overlay, red, green, blue, scale);
		//this.bipedLegBody.render(scale);
	}
}
