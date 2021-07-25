package twilightforest.client.model.tileentity.legacy;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import twilightforest.client.model.tileentity.GenericTrophyModel;

import java.util.Random;

public class UrGhastTrophyLegacyModel extends GenericTrophyModel {

	public ModelPart body;
	protected ModelPart[] tentacles = new ModelPart[9];
	protected ModelPart[][] subTentacles;
	protected ModelPart[][] smallTentacles;

	public UrGhastTrophyLegacyModel() {
		texWidth = 64;
		texHeight = 32;
		
		byte yOffset = -16;
		this.body = new ModelPart(this, 0, 0);
		this.body.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
		this.body.y += 24 + yOffset;
		Random rand = new Random(1660L);

		for (int i = 0; i < this.tentacles.length; ++i) {
			makeTentacle(yOffset, rand, i);
		}
	}
	
	protected void makeTentacle(byte yOffset, Random random, int num) {
		this.tentacles[num] = new ModelPart(this, num % 3, 0);

		int length = 5;

		this.tentacles[num].addBox(-1.5F, 0.0F, -1.5F, 3, length, 3);

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
			this.subTentacles = new ModelPart[tentacles.length][3];
		}

		for (int i = 0; i < 3; i++) {
			length = 4;

			this.subTentacles[num][i] = new ModelPart(this, num % 4, (i * 5) - 1);

			this.subTentacles[num][i].addBox(-1.5F, -0.5F, -1.5F, 3, length, 3);
			this.subTentacles[num][i].x = 0;
			this.subTentacles[num][i].z = 0;
			this.subTentacles[num][i].y = length;

			if (i == 0) {
				this.tentacles[num].addChild(this.subTentacles[num][i]);
			} else {
				this.subTentacles[num][i - 1].addChild(this.subTentacles[num][i]);
			}
		}

		this.body.addChild(this.tentacles[num]);
	}
	
	@Override
	public void setRotations(float x, float y, float z) {
		super.setRotations(x, y, z);
		this.body.yRot = y * ((float) Math.PI / 180F);
		this.body.xRot = z * ((float) Math.PI / 180F);
		for (int i = 0; i < this.subTentacles.length; ++i) {

			float wiggle = Math.min(x, 0.6F);

			float time = ((x * .5F) + (i * 9)) / 2.0F;

			this.subTentacles[i][0].xRot = (Mth.cos(time * 0.6662F) - (float) Math.PI / 3.0F) * wiggle;
			this.subTentacles[i][1].xRot = Mth.cos(time * 0.7774F) * 1.2F * wiggle;
			this.subTentacles[i][2].xRot = Mth.cos(time * 0.8886F + (float) Math.PI / 2.0F) * 1.4F * wiggle;

			this.subTentacles[i][0].xRot = 0.2F + Mth.cos(time * 0.3335F) * 0.15F;
			this.subTentacles[i][1].xRot = 0.1F + Mth.cos(time * 0.4445F) * 0.20F;
			this.subTentacles[i][2].xRot = 0.1F + Mth.cos(time * 0.5555F) * 0.25F;

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
