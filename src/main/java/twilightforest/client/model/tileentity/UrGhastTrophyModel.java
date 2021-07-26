package twilightforest.client.model.tileentity;

import java.util.Random;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class UrGhastTrophyModel extends GenericTrophyModel {

	public ModelPart body;
	protected ModelPart[] tentacles = new ModelPart[9];
	protected ModelPart[][] subTentacles;
	protected ModelPart[][] smallTentacles;
	
	public UrGhastTrophyModel(ModelPart part) {
		super(part);
	}

	public static LayerDefinition createHead() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		byte yOffset = -16;
		partdefinition.addOrReplaceChild("body",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F),
				PartPose.offset(0.0F, 24 + yOffset, 0.0F));

		Random rand = new Random(1660L);
		for (int i = 0; i < 9; ++i) {
			makeTentacle(yOffset, rand, i);
		}

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	//FIXME porting madness
	protected static void makeTentacle(byte yOffset, Random random, int num) {
		this.tentacles[num] = new ModelPart(this, 0, 0);

		float length = 5.333F;

		this.tentacles[num].addBox(-1.5F, 0.0F, -1.5F, 3.333F, length, 3.333F);

		if (num == 0) {
			this.tentacles[num].x = 4.5F;
			this.tentacles[num].z = 4.5F;
			this.tentacles[num].y = 23 + yOffset;
		}
		if (num == 1) {
			this.tentacles[num].x = -4.5F;
			this.tentacles[num].z = 4.5F;
			this.tentacles[num].y = 23 + yOffset;
		}
		if (num == 2) {
			this.tentacles[num].x = 0F;
			this.tentacles[num].z = 0F;
			this.tentacles[num].y = 23 + yOffset;
		}
		if (num == 3) {
			this.tentacles[num].x = 5.5F;
			this.tentacles[num].z = -4.5F;
			this.tentacles[num].y = 23 + yOffset;
		}
		if (num == 4) {
			this.tentacles[num].x = -5.5F;
			this.tentacles[num].z = -4.5F;
			this.tentacles[num].y = 23 + yOffset;
		} else if (num == 5) {
			this.tentacles[num].x = -7.5F;
			this.tentacles[num].y = 3.5F;
			this.tentacles[num].z = -1F;

			this.tentacles[num].zRot = (float) Math.PI / 4.0F;
		} else if (num == 6) {
			this.tentacles[num].x = -7.5F;
			this.tentacles[num].y = -1.5F;
			this.tentacles[num].z = 3.5F;

			this.tentacles[num].zRot = (float) Math.PI / 3.0F;
		} else if (num == 7) {
			this.tentacles[num].x = 7.5F;
			this.tentacles[num].y = 3.5F;
			this.tentacles[num].z = -1F;

			this.tentacles[num].zRot = -(float) Math.PI / 4.0F;
		} else if (num == 8) {
			this.tentacles[num].x = 7.5F;
			this.tentacles[num].y = -1.5F;
			this.tentacles[num].z = 3.5F;

			this.tentacles[num].zRot = -(float) Math.PI / 3.0F;
		}

		// goofy mid-method initializer
		if (this.subTentacles == null) {
			this.subTentacles = new ModelPart[tentacles.length][2];
		}

		length = 6.66F;

		this.subTentacles[num][0] = new ModelPart(this, 0, 3);

		this.subTentacles[num][0].addBox(-1.5F, -1.35F, -1.5F, 3.333F, length, 3.333F);
		this.subTentacles[num][0].x = 0;
		this.subTentacles[num][0].z = 0;
		this.subTentacles[num][0].y = length;

		this.tentacles[num].addChild(this.subTentacles[num][0]);

		length = 4;

		this.subTentacles[num][1] = new ModelPart(this, 0, 9);

		this.subTentacles[num][1].addBox(-1.5F, 1.3F, -1.5F, 3.333F, length, 3.333F);
		this.subTentacles[num][1].x = 0;
		this.subTentacles[num][1].z = 0;
		this.subTentacles[num][1].y = length;

		this.subTentacles[num][0].addChild(this.subTentacles[num][1]);


		this.body.addChild(this.tentacles[num]);
	}
	
	@Override
	public void setRotations(float x, float y, float z) {
		super.setRotations(x, y, z);
		this.body.yRot = y * ((float) Math.PI / 180F);
		this.body.xRot = z * ((float) Math.PI / 180F);
		for (int i = 0; i < this.subTentacles.length; ++i) {

			float wiggle = Math.min(x, 0.6F);

			float time = ((x * .35F) + (i * 9)) / 2.0F;

			this.tentacles[i].xRot = 0.2F * Mth.sin(time * 0.3F + i) + 0.4F;

			this.subTentacles[i][0].xRot = (Mth.cos(time * 0.6662F) - (float) Math.PI / 3.0F) * wiggle;
			this.subTentacles[i][1].xRot = Mth.cos(time * 0.7774F) * 1.2F * wiggle;

			this.subTentacles[i][0].xRot = 0.2F + Mth.cos(time * 0.3335F) * 0.15F;
			this.subTentacles[i][1].xRot = 0.1F + Mth.cos(time * 0.4445F) * 0.20F;

			float yTwist = 0.4F;

			this.tentacles[i].yRot = yTwist * Mth.sin(time * 0.3F);
		}
	}

	public void setTranslate(PoseStack matrix, float x, float y, float z) {
		matrix.translate(x, y, z);
	}

	@Override
	public void renderToBuffer(PoseStack matrix, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.body.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

}
