package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;

public class ChainModel extends ListModel<Entity> {
	ModelPart chain;

	public ChainModel() {
		this.texWidth = 64;
		this.texHeight = 64;
		chain = new ModelPart(this, 56, 36);
		chain.addBox(-1F, -1F, -1F, 2, 2, 2, 0F);
		chain.setPos(0F, 0F, 0F);

	}

	@Override
	public Iterable<ModelPart> parts() {
		return ImmutableList.of(chain);
	}

	@Override
	public void setupAnim(Entity entity, float v, float v1, float v2, float v3, float v4) { }
}
