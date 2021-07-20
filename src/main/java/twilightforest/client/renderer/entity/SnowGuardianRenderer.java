package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.math.MathHelper;
import twilightforest.client.model.entity.NoopModel;
import twilightforest.entity.SnowGuardianEntity;

public class SnowGuardianRenderer extends TFBipedRenderer<SnowGuardianEntity, NoopModel<SnowGuardianEntity>> {

	public SnowGuardianRenderer(EntityRendererManager manager, NoopModel<SnowGuardianEntity> model) {
		super(manager, model, new NoopModel<>(), new NoopModel<>(), 0.25F, "textures/entity/zombie/zombie.png");
		this.addLayer(new BipedArmorLayer<>(this, new BipedModel<>(0.5F), new BipedModel<>(1.0F)));
	}

	@Override
	protected void preRenderCallback(SnowGuardianEntity entity, MatrixStack stack, float partialTicks) {
		float bounce = entity.ticksExisted + partialTicks;
		stack.translate(0F, MathHelper.sin((bounce) * 0.2F) * 0.15F, 0F);
	}
}
