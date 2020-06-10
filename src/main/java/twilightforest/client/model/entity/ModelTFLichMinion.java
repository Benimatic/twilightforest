package twilightforest.client.model.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.potion.Effects;
import twilightforest.entity.boss.EntityTFLichMinion;

public class ModelTFLichMinion extends ZombieModel<EntityTFLichMinion> {

	private boolean hasStrength;

	public ModelTFLichMinion(boolean isArmor) {
		super(0.0F, isArmor);
	}

	@Override
	public void setLivingAnimations(EntityTFLichMinion entity, float limbSwing, float limbSwingAmount, float partialTicks) {
		this.hasStrength = entity.getActivePotionEffect(Effects.STRENGTH) != null;
	}

	@Override
	public void render(MatrixStack stack, IVertexBuilder builder, int light, int overlay, float red, float green, float blue, float scale) {
		if (hasStrength) {
			super.render(stack, builder, light, overlay, red * 0.25F, green, blue * 0.25F, scale);
		} else {
			super.render(stack, builder, light, overlay, red * 0.5F, green, blue * 0.5F, scale);
		}
	}
}
