package twilightforest.client.model.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.ZombieModel;

public class ModelTFLoyalZombie extends ZombieModel {

	public ModelTFLoyalZombie() {
		super(0.0F, true);
	}

	@Override
	public void render(MatrixStack stack, IVertexBuilder builder, int light, int overlay, float red, float green, float blue, float scale) {
		// GREEEEN
		RenderSystem.color3f(0.25F, 2.0F, 0.25F);
		super.render(stack, builder, light, overlay, red, green, blue, scale);
	}
}
