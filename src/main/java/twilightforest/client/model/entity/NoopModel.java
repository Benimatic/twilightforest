package twilightforest.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;

public class NoopModel<T extends LivingEntity> extends HumanoidModel<T> {

	public NoopModel() {
		super(0.0F);
	}

	@Override
	public void renderToBuffer(PoseStack ms, VertexConsumer buffers, int light, int overlay, float r, float g, float b, float a) {
	}
}
