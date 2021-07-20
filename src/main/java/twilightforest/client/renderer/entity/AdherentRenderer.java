package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.math.MathHelper;
import twilightforest.client.model.entity.AdherentModel;
import twilightforest.entity.AdherentEntity;

public class AdherentRenderer extends TFBipedRenderer<AdherentEntity, AdherentModel> {
	public AdherentRenderer(EntityRendererManager manager) {
		super(manager, new AdherentModel(), new AdherentModel(), new AdherentModel(), 0.625F, "adherent.png");
	}

	@Override
	protected void preRenderCallback(AdherentEntity e, MatrixStack ms, float partialTicks) {
		float bounce = e.ticksExisted + partialTicks;
		ms.translate(0F, -0.125F - MathHelper.sin((bounce) * 0.133F) * 0.1F, 0F);
	}
}
