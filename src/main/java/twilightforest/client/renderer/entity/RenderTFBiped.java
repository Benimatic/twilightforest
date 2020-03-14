package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;

public class RenderTFBiped<T extends MobEntity, M extends BipedModel<T>> extends BipedRenderer<T, M> {

	private final ResourceLocation textureLoc;

	public RenderTFBiped(EntityRendererManager manager, M modelBiped, float shadowSize, String textureName) {
		super(manager, modelBiped, shadowSize);
		this.addLayer(new BipedArmorLayer<>(this, modelBiped, modelBiped)); //TODO: probably janky

		if (textureName.startsWith("textures")) {
			textureLoc = new ResourceLocation(textureName);
		} else {
			textureLoc = TwilightForestMod.getModelTexture(textureName);
		}
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}
}
