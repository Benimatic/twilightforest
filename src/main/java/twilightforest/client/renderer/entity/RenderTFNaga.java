package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.entity.boss.EntityTFNaga;

public class RenderTFNaga extends RenderLiving<EntityTFNaga> {
	private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "nagahead.png");

	public RenderTFNaga(RenderManager manager, ModelBase modelbase, float shadowSize) {
		super(manager, modelbase, shadowSize);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTFNaga par1Entity) {
		return textureLoc;
	}
}
