package twilightforest.client.model.entity;

import com.google.common.collect.Iterables;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import twilightforest.entity.boss.LichEntity;

import java.util.Arrays;

public class LichModel extends HumanoidModel<LichEntity> {
	private final ModelPart collar;
	private final ModelPart cloak;

	private boolean shadowClone;

	public LichModel() {
		super(0.0F, 0.0F, 64, 64);

		body = new ModelPart(this, 8, 16);
		body.addBox(-4F, 0.0F, -2F, 8, 24, 4);
		body.setPos(0.0F, -4.0F, 0.0F);
		body.setTexSize(64, 64);

		rightArm = new ModelPart(this, 0, 16);
		rightArm.addBox(-2.0F, -2.0F, -2.0F, 2.0F, 12.0F, 2.0F);
		rightArm.setTexSize(64, 64);
		rightArm.setPos(-5.0F, -2.0F, 0.0F);

		leftArm = new ModelPart(this, 0, 16);
		leftArm.mirror = true;
		leftArm.addBox(-1.0F, -2.0F, -2.0F, 2.0F, 12.0F, 2.0F);
		leftArm.setPos(5.0F, 2.0F, 0.0F);
		leftArm.setTexSize(64, 64);

		hat = new ModelPart(this, 32, 0);
		hat.addBox(-4F, -8F, -4F, 8, 8, 8, 0.5F);
		hat.setPos(0.0F, -4.0F, 0.0F);
		hat.setTexSize(64, 64);

		head = new ModelPart(this, 0, 0);
		head.addBox(-4F, -4F, -4F, 8, 8, 8);
		head.setPos(0F, -4F, 0F);
		head.setTexSize(64, 64);

		rightLeg = new ModelPart(this, 0, 16);
		rightLeg.addBox(-1F, 2F, -1F, 2, 12, 2);
		rightLeg.setPos(-2F, 9.5F, 0F);
		rightLeg.setTexSize(64, 64);

		leftLeg = new ModelPart(this, 0, 16);
		leftLeg.addBox(-1F, 2F, -1F, 2, 12, 2);
		leftLeg.setPos(2F, 9.5F, 0F);
		leftLeg.setTexSize(64, 64);
		leftLeg.mirror = true;

		collar = new ModelPart(this, 32, 16);
		collar.addBox(-6F, -2F, -4F, 12, 12, 1);
		collar.setPos(0F, -3F, -1F);
		collar.setTexSize(64, 64);
		setRotation(collar, 2.164208F, 0F, 0F);

		cloak = new ModelPart(this, 0, 44);
		cloak.addBox(-6F, 2F, 0F, 12, 19, 1);
		cloak.setPos(0F, -4F, 2.5F);
		cloak.setTexSize(64, 64);
		setRotation(cloak, 0F, 0F, 0F);
	}

	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float alpha) {
		if (!shadowClone) {
			super.renderToBuffer(stack, builder, light, overlay, red, green, blue, alpha);
		} else {
			float shadow = 0.33f;
			super.renderToBuffer(stack, builder, light, overlay, red * shadow, green * shadow, blue * shadow, 0.8F);
		}
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		if (shadowClone) {
			return super.bodyParts();
		} else {
			return Iterables.concat(Arrays.asList(cloak, collar), super.bodyParts());
		}
	}

	@Override
	public void setupAnim(LichEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.shadowClone = entity.isShadowClone();
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		float ogSin = Mth.sin(attackTime * 3.141593F);
		float otherSin = Mth.sin((1.0F - (1.0F - attackTime) * (1.0F - attackTime)) * 3.141593F);
		rightArm.zRot = 0.0F;
		leftArm.zRot = 0.5F;
		rightArm.yRot = -(0.1F - ogSin * 0.6F);
		leftArm.yRot = 0.1F - ogSin * 0.6F;
		rightArm.xRot = -1.570796F;
		leftArm.xRot = -3.141593F;
		rightArm.xRot -= ogSin * 1.2F - otherSin * 0.4F;
		leftArm.xRot -= ogSin * 1.2F - otherSin * 0.4F;
		rightArm.zRot += Mth.cos(ageInTicks * 0.26F) * 0.15F + 0.05F;
		leftArm.zRot -= Mth.cos(ageInTicks * 0.26F) * 0.15F + 0.05F;
		rightArm.xRot += Mth.sin(ageInTicks * 0.167F) * 0.15F;
		leftArm.xRot -= Mth.sin(ageInTicks * 0.167F) * 0.15F;

		head.y = -4.0F;
		hat.y = -4.0F;
		rightLeg.y = 9.5F;
		leftLeg.y = 9.5F;
	}

	private void setRotation(ModelPart model, float x, float y, float z) {
		model.xRot = x;
		model.yRot = y;
		model.zRot = z;
	}
}
