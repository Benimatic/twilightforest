package twilightforest.client.model.armor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;

public class YetiArmorModel extends TFArmorModel {

	private final EquipmentSlot slot;

	private ModelPart bipedLegBody;
	private ModelPart rightRuff;
	private ModelPart leftRuff;
	private ModelPart rightToe;
	private ModelPart leftToe;

	public YetiArmorModel(EquipmentSlot slot, float expand) {
		super(expand);
		this.slot = slot;

		//bigger head
		this.head = new ModelPart(this, 0, 0);
		this.head.addBox(-4.5F, -7.5F, -4.0F, 9, 8, 8, expand);
		this.head.setPos(0.0F, 0.0F + expand, 0.0F);

		// add horns
		addPairHorns(-8.0F, 35F);
		addPairHorns(-6.0F, 15F);
		addPairHorns(-4.0F, -5F);


		// change leg texture
		this.rightLeg = new ModelPart(this, 40, 0);
		this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, expand);
		this.rightLeg.setPos(-1.9F, 12.0F + 0.0F, 0.0F);

		this.leftLeg = new ModelPart(this, 40, 0);
		this.leftLeg.mirror = true;
		this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, expand);
		this.leftLeg.setPos(1.9F, 12.0F + 0.0F, 0.0F);

		this.rightRuff = new ModelPart(this, 40, 22);
		this.rightRuff.addBox(-2.5F, 0.0F, -2.5F, 5, 2, 5, expand);
		this.rightRuff.setPos(0.0F, 6.0F, 0.0F);
		this.rightLeg.addChild(rightRuff);

		this.leftRuff = new ModelPart(this, 40, 22);
		this.leftRuff.addBox(-2.5F, 0.0F, -2.5F, 5, 2, 5, expand);
		this.leftRuff.setPos(0.0F, 6.0F, 0.0F);
		this.leftLeg.addChild(leftRuff);

		this.rightToe = new ModelPart(this, 40, 17);
		this.rightToe.addBox(-2.0F, 0.0F, -1.0F, 4, 2, 1, expand);
		this.rightToe.setPos(0.0F, 10.0F, -2.0F);
		this.rightLeg.addChild(rightToe);

		this.leftToe = new ModelPart(this, 40, 17);
		this.leftToe.addBox(-2.0F, 0.0F, -1.0F, 4, 2, 1, expand);
		this.leftToe.setPos(0.0F, 10.0F, -2.0F);
		this.leftLeg.addChild(leftToe);

		// stuff for chest and legs
		this.body = new ModelPart(this, 0, 0);
		this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 11, 4, expand);
		this.body.setPos(0.0F, 0.0F + 0.0f, 0.0F);

		this.bipedLegBody = new ModelPart(this, 40, 16);
		this.bipedLegBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, expand);
		this.bipedLegBody.setPos(0.0F, 0.0F + 0.0f, 0.0F);

		this.rightArm = new ModelPart(this, 0, 16);
		this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 10, 4, expand);
		this.rightArm.setPos(-5.0F, 2.0F + 0.0f, 0.0F);

		this.leftArm = new ModelPart(this, 0, 16);
		this.leftArm.mirror = true;
		this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 10, 4, expand);
		this.leftArm.setPos(5.0F, 2.0F + 0.0f, 0.0F);

	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return Iterables.concat(super.bodyParts(), ImmutableList.of(bipedLegBody));
	}

	private void addPairHorns(float height, float zangle) {
		ModelPart horn1a;
		ModelPart horn1b;

		horn1a = new ModelPart(this, 0, 19);
		horn1a.addBox(-3.0F, -1.5F, -1.5F, 3, 3, 3);
		horn1a.setPos(-4.5F, height, -1.0F);
		horn1a.yRot = -30F / (180F / (float) Math.PI);
		horn1a.zRot = zangle / (180F / (float) Math.PI);
		this.head.addChild(horn1a);

		horn1b = new ModelPart(this, 0, 26);
		horn1b.addBox(-4.0F, -1.0F, -1.0F, 5, 2, 2);
		horn1b.setPos(-3.0F, 0.0F, 0.0F);
		horn1b.yRot = -20F / (180F / (float) Math.PI);
		horn1b.zRot = zangle / (180F / (float) Math.PI);
		horn1a.addChild(horn1b);

		ModelPart horn2a;
		ModelPart horn2b;

		horn2a = new ModelPart(this, 0, 19);
		horn2a.addBox(0.0F, -1.5F, -1.5F, 3, 3, 3);
		horn2a.setPos(4.5F, height, -1.0F);
		horn2a.yRot = 30F / (180F / (float) Math.PI);
		horn2a.zRot = -zangle / (180F / (float) Math.PI);
		this.head.addChild(horn2a);

		horn2b = new ModelPart(this, 0, 26);
		horn2b.addBox(-1.0F, -1.0F, -1.0F, 5, 2, 2);
		horn2b.setPos(3.0F, 0.0F, 0.0F);
		horn2b.yRot = 20F / (180F / (float) Math.PI);
		horn2b.zRot = -zangle / (180F / (float) Math.PI);
		horn2a.addChild(horn2b);
	}

	@Override
	public void setAllVisible(boolean visible) {
		super.setAllVisible(visible);
		bipedLegBody.visible = visible;
	}

	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float scale) {
		switch (slot) {
			case HEAD:
				this.head.visible = true;
				this.hat.visible = false;
				this.body.visible = false;
				this.rightArm.visible = false;
				this.leftArm.visible = false;
				this.bipedLegBody.visible = false;
				this.rightLeg.visible = false;
				this.leftLeg.visible = false;
				break;
			case CHEST:
				this.head.visible = false;
				this.hat.visible = false;
				this.body.visible = true;
				this.rightArm.visible = true;
				this.leftArm.visible = true;
				this.bipedLegBody.visible = false;
				this.rightLeg.visible = false;
				this.leftLeg.visible = false;
				break;
			case LEGS:
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
				break;
			case FEET:
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
				break;
			default:
				break;
		}
		super.renderToBuffer(stack, builder, light, overlay, red, green, blue, scale);
		//this.bipedLegBody.render(scale);
	}
}
