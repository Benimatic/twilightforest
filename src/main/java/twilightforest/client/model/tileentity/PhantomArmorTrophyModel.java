package twilightforest.client.model.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class PhantomArmorTrophyModel extends GenericTrophyModel {

	public ModelPart head;
	public ModelPart righthorn1;
	public ModelPart righthorn2;
	public ModelPart lefthorn1;
	public ModelPart lefthorn2;

	public PhantomArmorTrophyModel(ModelPart part) {
		this.head = part.getChild("head");
		this.righthorn1 = this.head.getChild("right_horn_1");
		this.righthorn2 = this.righthorn1.getChild("right_horn_2");
		this.lefthorn1 = this.head.getChild("left_horn_1");
		this.lefthorn2 = this.lefthorn1.getChild("left_horn_2");
	}

	public static LayerDefinition createHead() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		var head = partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
				PartPose.offset(0.0F, -4.0F, 0.0F));

		var rightHorn = head.addOrReplaceChild("right_horn_1",
				CubeListBuilder.create()
						.texOffs(24, 0)
						.addBox(-5.5F, -1.5F, -1.5F, 5, 3, 3),
				PartPose.offsetAndRotation(-4.0F, -6.5F, 0.0F,
						0.0F, -25F / (180F / (float) Math.PI), 45F / (180F / (float) Math.PI)));

		rightHorn.addOrReplaceChild("right_horn_2",
				CubeListBuilder.create()
						.texOffs(54, 16)
						.addBox(-3.5F, -1.0F, -1.0F, 3, 2, 2),
				PartPose.offsetAndRotation(-4.5F, 0.0F, 0.0F,
						0.0F, -15F / (180F / (float) Math.PI), 45F / (180F / (float) Math.PI)));

		var leftHorn = head.addOrReplaceChild("left_horn_1",
				CubeListBuilder.create()
						.texOffs(24, 0)
						.addBox(-5.5F, -1.5F, -1.5F, 5, 3, 3),
				PartPose.offsetAndRotation(4.0F, -6.5F, 0.0F,
						0.0F, 25F / (180F / (float) Math.PI), -45F / (180F / (float) Math.PI)));

		leftHorn.addOrReplaceChild("left_horn_2",
				CubeListBuilder.create()
						.texOffs(54, 16)
						.addBox(-3.5F, -1.0F, -1.0F, 3, 2, 2),
				PartPose.offsetAndRotation(4.5F, 0.0F, 0.0F,
						0.0F, 15F / (180F / (float) Math.PI), -45F / (180F / (float) Math.PI)));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
	
	@Override
	public void setRotations(float x, float y, float z) {
		this.head.yRot = y * ((float) Math.PI / 180F);
		this.head.xRot = x * ((float) Math.PI / 180F);
		
	}
	
	@Override
	public void renderToBuffer(PoseStack matrix, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.head.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
