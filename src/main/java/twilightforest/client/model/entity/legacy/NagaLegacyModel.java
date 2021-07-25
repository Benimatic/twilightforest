package twilightforest.client.model.entity.legacy;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import twilightforest.entity.boss.NagaEntity;
import twilightforest.entity.boss.NagaSegmentEntity;

public class NagaLegacyModel<T extends Entity> extends ListModel<T> {

	private T entity;

	public NagaLegacyModel() {
		head = new ModelPart(this, 0, 0);
		head.addBox(-8F, -12F, -8F, 16, 16, 16, 0F);
		head.setPos(0F, 0F, 0F);

		body = new ModelPart(this, 0, 0);
		body.addBox(-8F, -16F, -8F, 16, 16, 16, 0F);
		body.setPos(0F, 0F, 0F);
	}

	@Override
	public Iterable<ModelPart> parts() {
		return ImmutableList.of(head, body);
	}

	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, float red, float green, float blue, float scale) {

		//setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		if (entity instanceof NagaEntity) {
			head.render(stack, builder, light, overlay, red, green, blue, scale * 2);
		} else if (entity instanceof NagaSegmentEntity) {
			body.render(stack, builder, light, overlay, red, green, blue, scale * 2);
		} else {
			head.render(stack, builder, light, overlay, red, green, blue, scale * 2);
		}
	}

	@Override
	public void setupAnim(T entity, float v, float v1, float v2, float v3, float v4) {
		this.entity = entity;
	}

	//fields
	public ModelPart head;
	public ModelPart body;
}
