package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;

public class RenderTFBiped extends RenderBiped {

	private final ResourceLocation textureLoc;

	public RenderTFBiped(ModelBiped modelBiped, float scale, String textureName) {
		super(modelBiped, scale);
		
		if (textureName.startsWith("textures"))
		{
			textureLoc = new ResourceLocation(textureName);
		}
		else
		{
			textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + textureName);
		}
	}

	/**
	 * Return our specific texture
	 */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return textureLoc;
    }
}
