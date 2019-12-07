package twilightforest.client.model.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import twilightforest.entity.EntityTFRisingZombie;

public class ModelTFRisingZombie<T extends EntityTFRisingZombie> extends ZombieModel<T> {
	@Override
	public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		GlStateManager.pushMatrix();

		if (this.isChild) {
			GlStateManager.pushMatrix();
			{
				GlStateManager.scalef(0.75F, 0.75F, 0.75F);
				GlStateManager.translatef(0.0F, 16.0F * scale, 0.0F);
				this.bipedHead.render(scale);
				GlStateManager.popMatrix();
				GlStateManager.pushMatrix();
				GlStateManager.scalef(0.5F, 0.5F, 0.5F);
				GlStateManager.translatef(0.0F, 24.0F * scale, 0.0F);
				this.bipedBody.render(scale);
				this.bipedRightArm.render(scale);
				this.bipedLeftArm.render(scale);
				this.bipedHeadwear.render(scale);
			}
			GlStateManager.popMatrix();
			this.bipedRightLeg.render(scale);
			this.bipedLeftLeg.render(scale);
		} else {
			if (entityIn.isSneaking()) {
				GlStateManager.translatef(0.0F, 0.2F, 0.0F);
			}

			GlStateManager.translatef(0F, (80F - Math.min(80F, ageInTicks)) / 80F, 0F);
			GlStateManager.translatef(0F, (40F - Math.min(40F, Math.max(0F, ageInTicks - 80F))) / 40F, 0F);
			GlStateManager.pushMatrix();
			{
				final float yOff = 1F;
				GlStateManager.translatef(0, yOff, 0);
				GlStateManager.rotatef(-120F * (80F - Math.min(80F, ageInTicks)) / 80F, 1F, 0F, 0F);
				GlStateManager.rotatef(30F * (40F - Math.min(40F, Math.max(0F, ageInTicks - 80F))) / 40F, 1F, 0F, 0F);
				GlStateManager.translatef(0, -yOff, 0);
				this.bipedHead.render(scale);
				this.bipedBody.render(scale);
				this.bipedRightArm.render(scale);
				this.bipedLeftArm.render(scale);
				this.bipedHeadwear.render(scale);
			}
			GlStateManager.popMatrix();
			this.bipedRightLeg.render(scale);
			this.bipedLeftLeg.render(scale);
		}

		GlStateManager.popMatrix();
	}
}
