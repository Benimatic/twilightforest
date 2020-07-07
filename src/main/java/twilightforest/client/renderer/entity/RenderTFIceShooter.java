package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.math.MathHelper;
import twilightforest.client.model.entity.ModelTFIceShooter;
import twilightforest.entity.EntityStableIceCore;

public class RenderTFIceShooter extends RenderTFBiped<EntityStableIceCore, ModelTFIceShooter> {

	public RenderTFIceShooter(EntityRendererManager manager, ModelTFIceShooter model) {
		super(manager, model, 0.4F, "iceshooter.png");
	}

	@Override
	protected void preRenderCallback(EntityStableIceCore entity, MatrixStack stack, float partialTicks) {
		float bounce = entity.ticksExisted + partialTicks;
		stack.translate(0F, MathHelper.sin((bounce) * 0.2F) * 0.15F, 0F);
	}
}
