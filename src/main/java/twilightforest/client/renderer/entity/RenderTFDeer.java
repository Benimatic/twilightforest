package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;

public class RenderTFDeer extends RenderCow {
    private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "wilddeer.png");

	public RenderTFDeer(RenderManager manager, ModelBase model, float shadowSize) {
		super(manager, model, shadowSize);
	}

	@Override
    protected ResourceLocation getEntityTexture(EntityCow par1Entity)
    {
        return textureLoc;
    }
}
