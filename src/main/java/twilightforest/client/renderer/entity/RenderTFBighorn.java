package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.entity.passive.EntityTFBighorn;

public class RenderTFBighorn extends RenderLiving<EntityTFBighorn> {
	private static final ResourceLocation textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + "bighorn.png");

	public RenderTFBighorn(RenderManager manager, ModelBase baseModel, ModelBase coatModel, float shadowSize) {
		super(manager, baseModel, shadowSize);
		// TODO layer renderer for coat
	}

	@Override
    protected ResourceLocation getEntityTexture(EntityTFBighorn ent)
    {
        return textureLoc;
    }
}
