package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.math.MathHelper;
import twilightforest.client.model.entity.ModelTFIceShooter;
import twilightforest.entity.EntityTFIceShooter;

public class RenderTFIceShooter<T extends EntityTFIceShooter, M extends ModelTFIceShooter<T>> extends RenderTFBiped<T, M> {

	public RenderTFIceShooter(EntityRendererManager manager, M model) {
		super(manager, model, 0.4F, "iceshooter.png");
	}

	@Override
	protected void scale(T entity, MatrixStack stack, float partialTicks) {
		float bounce = entity.ticksExisted + partialTicks;
		stack.translate(0F, MathHelper.sin((bounce) * 0.2F) * 0.15F, 0F);
	}
}