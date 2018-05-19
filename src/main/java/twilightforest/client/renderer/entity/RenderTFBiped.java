package twilightforest.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;

public class RenderTFBiped<T extends EntityLiving> extends RenderBiped<T> {
	private final ResourceLocation textureLoc;

	public RenderTFBiped(RenderManager manager, ModelBiped modelBiped, float scale, String textureName) {
		super(manager, modelBiped, scale);

		if (textureName.startsWith("textures")) {
			textureLoc = new ResourceLocation(textureName);
		} else {
			textureLoc = new ResourceLocation(TwilightForestMod.MODEL_DIR + textureName);
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(T par1Entity) {
		return textureLoc;
	}
}
