package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;

public class RenderTFGenericLiving<T extends LivingEntity, M extends EntityModel<T>> extends LivingRenderer<T, M> {

	private final ResourceLocation textureLoc;

	public RenderTFGenericLiving(EntityRendererManager manager, M model, float shadowSize, String textureName) {
		super(manager, model, shadowSize);

		if (textureName.startsWith("textures")) {
			textureLoc = new ResourceLocation(textureName);
		} else {
			textureLoc = TwilightForestMod.getModelTexture(textureName);
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}

}
