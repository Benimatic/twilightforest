package twilightforest.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import twilightforest.entity.boss.IceCrystalEntity;

public class IceCrystalModel extends EntityModel<IceCrystalEntity> {

	private final ModelPart[] spikes = new ModelPart[16];

	private boolean alive;

	public IceCrystalModel() {
		super(RenderType::entityTranslucent);
		this.texWidth = 32;
		this.texHeight = 32;

		float par1 = 0F;
		float par2 = 0F;

		// spikes
		for (int i = 0; i < spikes.length; i++) {
			this.spikes[i] = new ModelPart(this, 0, 16);

			int spikeLength = i % 2 == 0 ? 6 : 8;

			this.spikes[i].addBox(-1.0F, -1.0F, -1.0F, 2, spikeLength, 2, par1);
			this.spikes[i].setPos(0.0F, 0.0F + par2, 0.0F);

			ModelPart cube = new ModelPart(this, 8, 16);
			cube.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3);
			cube.setPos(0.0F, spikeLength, 0.0F);

			cube.zRot = (float) (Math.PI / 4F);

			this.spikes[i].addChild(cube);
		}
	}

	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float alpha) {
		for (ModelPart spike : spikes) {
			spike.render(stack, builder, light, overlay, red, green, blue, alive ? 0.6F : alpha);
		}
	}

	@Override
	public void setupAnim(IceCrystalEntity entity, float v, float v1, float v2, float v3, float v4) {
	}

	@Override
	public void prepareMobModel(IceCrystalEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {
		this.alive = entity.isAlive();
		for (int i = 0; i < spikes.length; i++) {
			// rotate the spikes
			this.spikes[i].xRot = Mth.sin((entity.tickCount + partialTicks) / 5.0F) / 4.0F;
			this.spikes[i].yRot = (entity.tickCount + partialTicks) / 5.0F;
			this.spikes[i].zRot = Mth.cos((entity.tickCount + partialTicks) / 5.0F) / 4.0F;

			this.spikes[i].xRot += i * (Math.PI / 8F);

			if (i % 4 == 0) {
				this.spikes[i].yRot += 1;
			} else if (i % 4 == 2) {
				this.spikes[i].yRot -= 1;
			}
		}
	}
}
