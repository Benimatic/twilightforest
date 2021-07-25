package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import twilightforest.entity.boss.HydraMortarHead;

public class HydraMortarModel extends ListModel<HydraMortarHead> {

	public ModelPart box;

	public HydraMortarModel() {
		texWidth = 32;
		texHeight = 32;
		box = new ModelPart(this, 0, 0);
		box.addBox(-4F, 0F, -4F, 8, 8, 8, 0F);
		box.setPos(0F, 0F, 0F);
	}

	@Override
	public void setupAnim(HydraMortarHead entity, float v, float v1, float v2, float v3, float v4) {

	}

	@Override
	public Iterable<ModelPart> parts() {
		return ImmutableList.of(this.box);
	}
}
