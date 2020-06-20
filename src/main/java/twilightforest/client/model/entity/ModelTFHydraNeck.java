package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import twilightforest.entity.boss.EntityTFHydraNeck;

public class ModelTFHydraNeck<T extends EntityTFHydraNeck> extends SegmentedModel<T> {

	ModelRenderer neck;

	public ModelTFHydraNeck() {
		textureWidth = 512;
		textureHeight = 256;

		neck = new ModelRenderer(this/*, "neck"*/);
		neck.setTextureOffset(128, 136).addCuboid(-16F, -16F, -16F, 32, 32, 32);
		neck.setTextureOffset(128, 200).addCuboid(-2F, -23F, 0F, 4, 24, 24);
		neck.setRotationPoint(0F, 0F, 0F);
	}

//	@Override
//	public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
//		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//		setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(neck);
	}

	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		neck.rotateAngleY = netHeadYaw / 57.29578F;
		neck.rotateAngleX = headPitch / 57.29578F;
	}
}
