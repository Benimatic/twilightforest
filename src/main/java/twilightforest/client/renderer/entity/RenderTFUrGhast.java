package twilightforest.client.renderer.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.ModelTFGhast;
import twilightforest.entity.EntityTFTowerGhast;


public class RenderTFUrGhast extends RenderTFTowerGhast {

	
	final ResourceLocation textureLocClosed;
	final ResourceLocation textureLocOpen;
	final ResourceLocation textureLocAttack;
	
	
	public RenderTFUrGhast(ModelTFGhast modelTFGhast, float f, float scale) {
		super(modelTFGhast, f, scale);
		textureLocClosed = new ResourceLocation(TwilightForestMod.MODEL_DIR + "towerboss.png");
		textureLocOpen = new ResourceLocation(TwilightForestMod.MODEL_DIR + "towerboss_openeyes.png");
		textureLocAttack = new ResourceLocation(TwilightForestMod.MODEL_DIR + "towerboss_fire.png");
	}

	
	/**
	 * Return our specific texture
	 */
	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		if (par1Entity instanceof EntityTFTowerGhast)
		{
			switch (((EntityTFTowerGhast)par1Entity).getAttackStatus())
			{
			default: 
			case 0:
				return textureLocClosed;

			case 1:
				return textureLocOpen;

			case 2:
				return textureLocAttack;
			}
		}

		// fallback
		return textureLocClosed;
    }
}
