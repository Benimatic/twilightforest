package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;

public class RenderTFGenericLiving extends RenderLiving {
	
	final ResourceLocation textureLoc;

	public RenderTFGenericLiving(ModelBase par1ModelBase, float par2, String textureName) {
		super(par1ModelBase, par2);
		
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
    @Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return textureLoc;
    }

}
