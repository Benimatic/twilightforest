package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import twilightforest.entity.boss.HydraMortarHead;

public class HydraMortarModel extends SegmentedModel<HydraMortarHead> {

	public ModelRenderer box;

	public HydraMortarModel() {
		textureWidth = 32;
		textureHeight = 32;
		box = new ModelRenderer(this, 0, 0);
		box.addBox(-4F, 0F, -4F, 8, 8, 8, 0F);
		box.setRotationPoint(0F, 0F, 0F);
	}

	@Override
	public void setRotationAngles(HydraMortarHead entity, float v, float v1, float v2, float v3, float v4) {

	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.box);
	}
}
