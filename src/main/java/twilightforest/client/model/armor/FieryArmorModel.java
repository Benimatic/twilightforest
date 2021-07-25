package twilightforest.client.model.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

public class FieryArmorModel extends TFArmorModel {

	public FieryArmorModel(float expand) {
		super(expand);
	}

	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float scale) {
		super.renderToBuffer(stack, builder, 0xF000F0, overlay, red, green, blue, scale);
	}

}
