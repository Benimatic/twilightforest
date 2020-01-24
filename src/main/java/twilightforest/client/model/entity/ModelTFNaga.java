package twilightforest.client.model.entity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import twilightforest.entity.boss.EntityTFNaga;
import twilightforest.entity.boss.EntityTFNagaSegment;

public class ModelTFNaga<T extends Entity> extends EntityModel<T> {
	public ModelTFNaga() {
		head = new ModelRenderer(this, 0, 0);
		head.addCuboid(-8F, -12F, -8F, 16, 16, 16, 0F);
		head.setRotationPoint(0F, 0F, 0F);

		body = new ModelRenderer(this, 0, 0);
		body.addCuboid(-8F, -16F, -8F, 16, 16, 16, 0F);
		body.setRotationPoint(0F, 0F, 0F);
	}

	@Override
	public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		if (entity instanceof EntityTFNaga) {
			head.render(scale * 2);
		} else if (entity instanceof EntityTFNagaSegment) {
			body.render(scale * 2);
		} else {
			head.render(scale * 2);
		}

	}

	//fields
	public ModelRenderer head;
	public ModelRenderer body;
}
