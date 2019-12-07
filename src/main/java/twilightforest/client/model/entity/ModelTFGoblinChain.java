package twilightforest.client.model.entity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;


public class ModelTFGoblinChain extends EntityModel {
	RendererModel chain;

	public ModelTFGoblinChain() {

		chain = new RendererModel(this, 56, 16);
		chain.addBox(-1F, -1F, -1F, 2, 2, 2, 0F);
		chain.setRotationPoint(0F, 0F, 0F);

	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		chain.render(scale);
	}

}
