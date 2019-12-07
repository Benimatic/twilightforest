package twilightforest.client.model.entity;

import net.minecraft.client.renderer.entity.model.SpiderModel;
import twilightforest.entity.EntityTFSwarmSpider;

public class ModelTFSwarmSpider<T extends EntityTFSwarmSpider> extends SpiderModel<T> {

	@Override
	public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale * 0.5f);
	}
}
