package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.math.MathHelper;
import twilightforest.client.model.entity.ModelTFAdherent;
import twilightforest.entity.EntityTFAdherent;

public class RenderAdherent extends RenderTFBiped<EntityTFAdherent, ModelTFAdherent> {
	public RenderAdherent(EntityRendererManager manager) {
		super(manager, new ModelTFAdherent(), new ModelTFAdherent(), new ModelTFAdherent(), 0.625F, "adherent.png");
	}

	@Override
	protected void preRenderCallback(EntityTFAdherent e, MatrixStack ms, float partialTicks) {
		float bounce = e.ticksExisted + partialTicks;
		ms.translate(0F, -0.125F - MathHelper.sin((bounce) * 0.133F) * 0.1F, 0F);
	}
}
