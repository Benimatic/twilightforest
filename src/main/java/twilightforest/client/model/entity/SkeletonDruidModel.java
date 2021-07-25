package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelPart;
import twilightforest.entity.SkeletonDruidEntity;

public class SkeletonDruidModel extends SkeletonModel<SkeletonDruidEntity> {
	private ModelPart dress;

	public SkeletonDruidModel() {
		body = new ModelPart(this, 8, 16);
		body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
		body.setPos(0.0F, 0.0F, 0.0F);
		rightArm = new ModelPart(this, 0, 16);
		rightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, 0.0F);
		rightArm.setPos(-5.0F, 2.0F, 0.0F);
		leftArm = new ModelPart(this, 0, 16);
		leftArm.mirror = true;
		leftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, 0.0F);
		leftArm.setPos(5.0F, 2.0F, 0.0F);
		
		dress = new ModelPart(this, 32, 16);
		dress.addBox(-4F, 12.0F, -2F, 8, 12, 4, 0);
		dress.setPos(0.0F, 0.0F, 0.0F);
	}

	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		if(this.riding) matrixStackIn.translate(0, -0.25F, 0);
		super.renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return Iterables.concat(super.bodyParts(), ImmutableList.of(this.dress));
	}
}
