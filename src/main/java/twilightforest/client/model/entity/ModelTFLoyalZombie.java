package twilightforest.client.model.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.entity.LivingEntity;

public class ModelTFLoyalZombie extends ZombieModel {
	@Override
	public void render(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		// GREEEEN
		GlStateManager.color3f(0.25F, 2.0F, 0.25F);
		super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}
}
