package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;

public class RenderTFHedgeSpider extends RenderSpider {

    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "hedgespider.png");

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return textureLoc;
	}

}
