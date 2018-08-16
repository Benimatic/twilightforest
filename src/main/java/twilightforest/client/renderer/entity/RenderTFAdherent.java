package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import twilightforest.entity.EntityTFAdherent;

public class RenderTFAdherent extends RenderTFBiped<EntityTFAdherent> {

	public RenderTFAdherent(RenderManager manager, ModelBiped modelBiped, float scale, String textureName) {
		super(manager, modelBiped, scale, textureName);
	}

	@Override
	public void doRender(EntityTFAdherent entity, double x, double y, double z, float yaw, float partialTicks) {
		//GlStateManager.translate(0.0F, 1.0F, 0.0F);

		super.doRender(entity, x, y, z, yaw, partialTicks);
	}

}
