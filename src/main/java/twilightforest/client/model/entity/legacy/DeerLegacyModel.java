package twilightforest.client.model.entity.legacy;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import twilightforest.entity.passive.DeerEntity;

public class DeerLegacyModel extends QuadrupedModel<DeerEntity> {
	public DeerLegacyModel() {
		super(12, 0.0F, false, 4.0F, 4.0F, 2.0F, 2.0F, 10);

		head = new ModelPart(this, 0, 5);
		head.addBox(-2F, -8F, -6F, 4, 6, 6, 0F);
		head.setPos(0F, 4F, -7F);

		body = new ModelPart(this, 36, 6);
		body.addBox(-4F, -10F, -7F, 6, 18, 8, 0F);
		body.setPos(1F, 5F, 2F);

		body.xRot = 1.570796F;
		leg0 = new ModelPart(this, 0, 17);
		leg0.addBox(-3F, 0F, -2F, 2, 12, 3, 0F);
		leg0.setPos(0F, 12F, 9F);

		leg1 = new ModelPart(this, 0, 17);
		leg1.addBox(-1F, 0F, -2F, 2, 12, 3, 0F);
		leg1.setPos(2F, 12F, 9F);

		leg2 = new ModelPart(this, 0, 17);
		leg2.addBox(-3F, 0F, -3F, 2, 12, 3, 0F);
		leg2.setPos(0F, 12F, -5F);

		leg3 = new ModelPart(this, 0, 17);
		leg3.addBox(-1F, 0F, -3F, 2, 12, 3, 0F);
		leg3.setPos(2F, 12F, -5F);

		// neck
		neck = new ModelPart(this, 10, 19);
		neck.addBox(-2.5F, -8, -11F, 3, 9, 4, 0F);
//		neck.setRotationPoint(1F, 5F, 2F);

		neck.xRot = 4.974188f;

		body.addChild(neck);

		// nose
		head.texOffs(52, 0).addBox(-1.5F, -5F, -9F, 3, 3, 3, 0F);

		// antler 1
		head.texOffs(20, 0);
		head.addBox(-3F, -10F, -2F, 2, 2, 2, 0F);
		head.addBox(-3F, -10F, -2F, 2, 2, 2, 0F);
		head.addBox(-4F, -10F, -1F, 1, 1, 3, 0F);
		head.addBox(-5F, -11F, 1F, 1, 1, 5, 0F);
		head.addBox(-5F, -14F, 2F, 1, 4, 1, 0F);
		head.addBox(-6F, -17F, 3F, 1, 4, 1, 0F);
		head.addBox(-6F, -13F, 0F, 1, 1, 3, 0F);
		head.addBox(-6F, -14F, -3F, 1, 1, 4, 0F);
		head.addBox(-7F, -15F, -6F, 1, 1, 4, 0F);
		head.addBox(-6F, -16F, -9F, 1, 1, 4, 0F);
		head.addBox(-7F, -18F, -1F, 1, 5, 1, 0F);
		head.addBox(-6F, -19F, -6F, 1, 5, 1, 0F);

		// antler 2
		head.addBox(1F, -10F, -2F, 2, 2, 2, 0F);
		head.addBox(3F, -10F, -1F, 1, 1, 3, 0F);
		head.addBox(4F, -11F, 1F, 1, 1, 5, 0F);
		head.addBox(4F, -14F, 2F, 1, 4, 1, 0F);
		head.addBox(5F, -17F, 3F, 1, 4, 1, 0F);
		head.addBox(5F, -13F, 0F, 1, 1, 3, 0F);
		head.addBox(5F, -14F, -3F, 1, 1, 4, 0F);
		head.addBox(6F, -15F, -6F, 1, 1, 4, 0F);
		head.addBox(5F, -16F, -9F, 1, 1, 4, 0F);
		head.addBox(6F, -18F, -1F, 1, 5, 1, 0F);
		head.addBox(5F, -19F, -6F, 1, 5, 1, 0F);
	}

	//fields
	public ModelPart neck;

	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float scale) {
		if (young) {
			stack.pushPose();
			stack.scale(0.75F, 0.75F, 0.75F);
			stack.translate(0F, 0.95F, 0.15F);
			this.headParts().forEach((modelRenderer) -> modelRenderer.render(stack, builder, light, overlay, red, green, blue, scale));
			stack.popPose();

			stack.pushPose();
			stack.scale(0.5F, 0.5F, 0.5F);
			stack.translate(0F, 1.5F, 0F);
			this.bodyParts().forEach((modelRenderer) -> modelRenderer.render(stack, builder, light, overlay, red, green, blue, scale));
			stack.popPose();
		} else {
			this.headParts().forEach((renderer) -> renderer.render(stack, builder, light, overlay, red, green, blue, scale));
			this.bodyParts().forEach((renderer) -> renderer.render(stack, builder, light, overlay, red, green, blue, scale));
		}
	}
}
