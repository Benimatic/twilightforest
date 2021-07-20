package twilightforest.client.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

public class FieryArmorModel extends TFArmorModel {

	public FieryArmorModel(float expand) {
		super(expand);
	}

	@Override
	public void render(MatrixStack stack, IVertexBuilder builder, int light, int overlay, float red, float green, float blue, float scale) {
		super.render(stack, builder, 0xF000F0, overlay, red, green, blue, scale);
	}

}
