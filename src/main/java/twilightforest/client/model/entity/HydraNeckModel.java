package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import twilightforest.entity.boss.HydraNeckEntity;

public class HydraNeckModel extends ListModel<HydraNeckEntity> {

	ModelPart neck;

	public HydraNeckModel() {
		texWidth = 512;
		texHeight = 256;

		this.neck = new ModelPart(this, 0, 0);
		this.neck.setPos(0F, 0F, 0F);
		this.neck.texOffs(260, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F, 0.0F, 0.0F, 0.0F);
		this.neck.texOffs(0, 0).addBox(-2.0F, -24.0F, 0.0F, 4.0F, 8.0F, 16.0F, 0.0F, 0.0F, 0.0F);

	}

	@Override
	public Iterable<ModelPart> parts() {
		return ImmutableList.of(neck);
	}

	@Override
	public void setupAnim(HydraNeckEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		neck.yRot = netHeadYaw / 57.29578F;
		neck.xRot = headPitch / 57.29578F;
	}
}
