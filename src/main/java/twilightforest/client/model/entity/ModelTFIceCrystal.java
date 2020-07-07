package twilightforest.client.model.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import twilightforest.entity.boss.EntityTFIceCrystal;

public class ModelTFIceCrystal extends EntityModel<EntityTFIceCrystal> {

	private final ModelRenderer[] spikes = new ModelRenderer[16];

	private boolean alive;

	public ModelTFIceCrystal() {
		super(RenderType::getEntityTranslucent);
		this.textureWidth = 32;
		this.textureHeight = 32;

		float par1 = 0F;
		float par2 = 0F;

		// spikes
		for (int i = 0; i < spikes.length; i++) {
			this.spikes[i] = new ModelRenderer(this, 0, 16);

			int spikeLength = i % 2 == 0 ? 6 : 8;

			this.spikes[i].addBox(-1.0F, -1.0F, -1.0F, 2, spikeLength, 2, par1);
			this.spikes[i].setRotationPoint(0.0F, 0.0F + par2, 0.0F);

			ModelRenderer cube = new ModelRenderer(this, 8, 16);
			cube.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3);
			cube.setRotationPoint(0.0F, spikeLength, 0.0F);

			cube.rotateAngleZ = (float) (Math.PI / 4F);

			this.spikes[i].addChild(cube);
		}
	}

	@Override
	public void render(MatrixStack stack, IVertexBuilder builder, int light, int overlay, float red, float green, float blue, float alpha) {
		for (ModelRenderer spike : spikes) {
			spike.render(stack, builder, light, overlay, red, green, blue, alive ? 0.6F : alpha);
		}
	}

	@Override
	public void setRotationAngles(EntityTFIceCrystal entity, float v, float v1, float v2, float v3, float v4) {
	}

	@Override
	public void setLivingAnimations(EntityTFIceCrystal entity, float limbSwing, float limbSwingAmount, float partialTicks) {
		this.alive = entity.isAlive();
		for (int i = 0; i < spikes.length; i++) {
			// rotate the spikes
			this.spikes[i].rotateAngleX = MathHelper.sin((entity.ticksExisted + partialTicks) / 5.0F) / 4.0F;
			this.spikes[i].rotateAngleY = (entity.ticksExisted + partialTicks) / 5.0F;
			this.spikes[i].rotateAngleZ = MathHelper.cos((entity.ticksExisted + partialTicks) / 5.0F) / 4.0F;

			this.spikes[i].rotateAngleX += i * (Math.PI / 8F);

			if (i % 4 == 0) {
				this.spikes[i].rotateAngleY += 1;
			} else if (i % 4 == 2) {
				this.spikes[i].rotateAngleY -= 1;
			}
		}
	}
}
