package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.math.MathHelper;
import twilightforest.client.model.entity.StableIceCoreModel;
import twilightforest.entity.StableIceCoreEntity;

public class StableIceCoreRenderer extends TFBipedRenderer<StableIceCoreEntity, StableIceCoreModel> {

	public StableIceCoreRenderer(EntityRendererManager manager, StableIceCoreModel model) {
		super(manager, model, 0.4F, "iceshooter.png");
	}

	@Override
	protected void preRenderCallback(StableIceCoreEntity entity, MatrixStack stack, float partialTicks) {
		float bounce = entity.ticksExisted + partialTicks;
		stack.translate(0F, MathHelper.sin((bounce) * 0.2F) * 0.15F, 0F);
	}
}
