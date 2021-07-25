package twilightforest.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import twilightforest.entity.boss.KnightPhantomEntity;

public class KnightPhantomModel extends HumanoidModel<KnightPhantomEntity> {

	private KnightPhantomEntity knight;

	public KnightPhantomModel() {
		this(0.0F);
	}

	public KnightPhantomModel(float scale) {
		super(scale);

		this.rightArm = new ModelPart(this, 40, 16);
		this.rightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, scale);
		this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
		this.leftArm = new ModelPart(this, 40, 16);
		this.leftArm.mirror = true;
		this.leftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, scale);
		this.leftArm.setPos(5.0F, 2.0F, 0.0F);
		this.rightLeg = new ModelPart(this, 0, 16);
		this.rightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, scale);
		this.rightLeg.setPos(-2.0F, 12.0F, 0.0F);
		this.leftLeg = new ModelPart(this, 0, 16);
		this.leftLeg.mirror = true;
		this.leftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, scale);
		this.leftLeg.setPos(2.0F, 12.0F, 0.0F);
	}

	/**
	 * TODO: Should this be in Renderer instead?
	 */
	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float scale) {
		if (knight != null && knight.isChargingAtPlayer()) {
			// render full skeleton
			super.renderToBuffer(stack, builder, light, overlay, red, green, blue, scale);
		}
	}

	@Override
	public void setupAnim(KnightPhantomEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.knight = entity;

		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		this.leftLeg.xRot = 0;
		this.leftLeg.yRot = 0;
		this.leftLeg.zRot = 0;

		this.rightLeg.xRot = 0;
		this.rightLeg.yRot = 0;
		this.rightLeg.zRot = 0;

		this.rightLeg.xRot = 0.2F * Mth.sin(ageInTicks * 0.3F) + 0.4F;
		this.leftLeg.xRot = 0.2F * Mth.sin(ageInTicks * 0.3F) + 0.4F;
	}
}
