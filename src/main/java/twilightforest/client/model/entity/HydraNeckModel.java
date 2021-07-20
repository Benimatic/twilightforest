package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import twilightforest.entity.boss.HydraNeckEntity;

public class HydraNeckModel extends SegmentedModel<HydraNeckEntity> {

	ModelRenderer neck;

	public HydraNeckModel() {
		textureWidth = 512;
		textureHeight = 256;

		this.neck = new ModelRenderer(this, 0, 0);
		this.neck.setRotationPoint(0F, 0F, 0F);
		this.neck.setTextureOffset(260, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
		this.neck.setTextureOffset(0, 0).addBox(-2.0F, -24.0F, 0.0F, 4.0F, 8.0F, 16.0F, 0.0F, 0.0F, 0.0F);

	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(neck);
	}

	@Override
	public void setRotationAngles(HydraNeckEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		neck.rotateAngleY = netHeadYaw / 57.29578F;
		neck.rotateAngleX = headPitch / 57.29578F;
	}
}
