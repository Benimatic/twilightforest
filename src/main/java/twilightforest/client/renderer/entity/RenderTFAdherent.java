package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import twilightforest.client.model.entity.ModelTFAdherent;
import twilightforest.entity.EntityTFAdherent;

public class RenderTFAdherent extends RenderTFBiped<EntityTFAdherent, ModelTFAdherent> {

	public RenderTFAdherent(EntityRendererManager manager, ModelTFAdherent modelBiped, float shadowSize, String textureName) {
		super(manager, modelBiped, shadowSize, textureName);
	}

	//TODO: Do we need this?
//	@Override
//	public void doRender(T entity, double x, double y, double z, float yaw, float partialTicks) {
//		//RenderSystem.translatef(0.0F, 1.0F, 0.0F);
//
//		super.doRender(entity, x, y, z, yaw, partialTicks);
//	}
}
