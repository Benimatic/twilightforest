package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import twilightforest.entity.IceMobEntity;

import java.util.Arrays;

public class UnstableIceCoreModel<T extends IceMobEntity> extends HumanoidModel<T> {

	public ModelPart[] spikes = new ModelPart[16];

	private final ImmutableList<ModelPart> parts;
	protected boolean alive;

	public UnstableIceCoreModel() {
		super(RenderType::entityTranslucent, 0.0F, 0.0F, 32, 32);

		float par1 = 0F;
		float par2 = 0F;
		this.head = new ModelPart(this, 0, 0);
		this.head.addBox(-4.0F, 0.0F, -4.0F, 8, 8, 8, par1);
		this.head.setPos(0.0F, 0.0F + par2, 0.0F);

		// delete all other body parts
		this.hat = new ModelPart(this, 0, 0);
		this.body = new ModelPart(this, 0, 0);
		this.rightArm = new ModelPart(this, 0, 0);
		this.leftArm = new ModelPart(this, 0, 0);
		this.rightLeg = new ModelPart(this, 0, 0);
		this.leftLeg = new ModelPart(this, 0, 0);

		// spikes

		for (int i = 0; i < spikes.length; i++) {
			this.spikes[i] = new ModelPart(this, 0, 16);

			int spikeLength = i % 2 == 0 ? 6 : 8;

			this.spikes[i].addBox(-1.0F, 6.0F, -1.0F, 2, spikeLength, 2, par1);
			this.spikes[i].setPos(0.0F, 4.0F + par2, 0.0F);

			ModelPart cube = new ModelPart(this, 8, 16);
			cube.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3);
			cube.setPos(0.0F, 8F, 0.0F);

			cube.zRot = (float) (Math.PI / 4F);

			this.spikes[i].addChild(cube);
		}

		ImmutableList.Builder<ModelPart> builder = ImmutableList.builder();
		builder.addAll(Arrays.asList(this.spikes));
		parts = builder.build();
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return parts;
	}

	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float alpha) {
		this.headParts().forEach((renderer) -> renderer.render(stack, builder, light, overlay, red, green, blue, alpha));
		this.bodyParts().forEach((renderer) -> renderer.render(stack, builder, light, overlay, red, green, blue, alive ? 0.6F : alpha));
	}

	@Override
	public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTicks) {
		this.alive = entity.isAlive();

		for (int i = 0; i < spikes.length; i++) {
			// rotate the spikes
			this.spikes[i].yRot = (entity.tickCount + partialTicks) / 5.0F;
			this.spikes[i].xRot = Mth.sin((entity.tickCount + partialTicks) / 5.0F) / 4.0F;
			this.spikes[i].zRot = Mth.cos((entity.tickCount + partialTicks) / 5.0F) / 4.0F;

			this.spikes[i].xRot += i * 5;
			this.spikes[i].yRot += i * 2.5f;
			this.spikes[i].zRot += i * 3;

			this.spikes[i].x = Mth.cos((entity.tickCount + partialTicks) / i) * 3F;
			this.spikes[i].y = 5F + Mth.sin((entity.tickCount + partialTicks) / i) * 3F;
			this.spikes[i].z = Mth.sin((entity.tickCount + partialTicks) / i) * 3F;

			this.spikes[i].children.get(0).y = 10 + Mth.sin((i + entity.tickCount + partialTicks) / i) * 3F;
		}
	}
}
