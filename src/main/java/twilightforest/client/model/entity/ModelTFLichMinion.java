package twilightforest.client.model.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import twilightforest.entity.boss.EntityTFLichMinion;

public class ModelTFLichMinion<T extends EntityTFLichMinion> extends ZombieModel<T> {

	public ModelTFLichMinion() {
		super(0.0F, true);
	}

	/**
	 * Used for easily adding entity-dependent animations. The second and third float params here are the same second
	 * and third as in the setRotationAngles method.
	 */
	@Override
	public void setLivingAnimations(T entity, float limbSwing, float limbSwingAmount, float partialTicks) {
		EntityTFLichMinion minion = entity;
		// make strong minions greener
		if (minion.isStrong()) {
			RenderSystem.color3f(0.25F, 2.0F, 0.25F);
		} else {
			RenderSystem.color3f(0.5F, 1.0F, 0.5F);
		}
	}

	@Override
	public void render(MatrixStack stack, IVertexBuilder builder, int light, int overlay, float red, float green, float blue, float scale) {
		// make strong minions bigger FIXME: actually do this?
		if (entity.isStrong()) {
			super.render(stack, builder, light, overlay, red, green, blue, scale);
		} else {
			super.render(stack, builder, light, overlay, red, green, blue, scale);
		}
	}
}
