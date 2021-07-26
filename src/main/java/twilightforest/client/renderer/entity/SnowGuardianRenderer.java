package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import twilightforest.client.model.entity.NoopModel;
import twilightforest.entity.SnowGuardianEntity;

public class SnowGuardianRenderer extends TFBipedRenderer<SnowGuardianEntity, NoopModel<SnowGuardianEntity>> {

	public SnowGuardianRenderer(EntityRendererProvider.Context manager, NoopModel<SnowGuardianEntity> model) {
		super(manager, model, new NoopModel<>(), new NoopModel<>(), 0.25F, "textures/entity/zombie/zombie.png");
		this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(0.5F), new HumanoidModel<>(1.0F)));
	}

	@Override
	protected void scale(SnowGuardianEntity entity, PoseStack stack, float partialTicks) {
		float bounce = entity.tickCount + partialTicks;
		stack.translate(0F, Mth.sin((bounce) * 0.2F) * 0.15F, 0F);
	}
}
