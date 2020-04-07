package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import twilightforest.entity.boss.EntityTFHydraMortar;

public class ModelTFHydraMortar<T extends EntityTFHydraMortar> extends SegmentedModel<T> {

	public ModelRenderer box;

	public ModelTFHydraMortar() {
		textureWidth = 32;
		textureHeight = 32;
		box = new ModelRenderer(this, 0, 0);
		box.addCuboid(-4F, 0F, -4F, 8, 8, 8, 0F);
		box.setRotationPoint(0F, 0F, 0F);
	}

	@Override
	public void setAngles(T entity, float v, float v1, float v2, float v3, float v4) {

	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.box);
	}
}
