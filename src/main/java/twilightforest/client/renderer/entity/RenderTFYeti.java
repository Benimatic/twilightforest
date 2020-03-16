package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import twilightforest.client.model.entity.ModelTFYeti;
import twilightforest.entity.EntityTFYeti;

public class RenderTFYeti<T extends EntityTFYeti, M extends ModelTFYeti<T>> extends RenderTFBiped<T, M> {
	public RenderTFYeti(EntityRendererManager manager, M modelBiped, float shadowSize, String textureName) {
		super(manager, modelBiped, shadowSize, textureName);
	}

	@Override
	protected void scale(T living, MatrixStack stack, float partialTicks) {
		float scale = 1.0F;
		stack.scale(scale, scale, scale);
	}
}
