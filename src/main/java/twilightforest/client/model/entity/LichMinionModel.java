package twilightforest.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.world.effect.MobEffects;
import twilightforest.entity.boss.LichMinionEntity;

public class LichMinionModel extends ZombieModel<LichMinionEntity> {

	private boolean hasStrength;

	public LichMinionModel(boolean isArmor) {
		super(0.0F, isArmor);
	}

	@Override
	public void prepareMobModel(LichMinionEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {
		this.hasStrength = entity.getEffect(MobEffects.DAMAGE_BOOST) != null;
	}

	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float scale) {
		if (hasStrength) {
			super.renderToBuffer(stack, builder, light, overlay, red * 0.25F, green, blue * 0.25F, scale);
		} else {
			super.renderToBuffer(stack, builder, light, overlay, red * 0.5F, green, blue * 0.5F, scale);
		}
	}
}
