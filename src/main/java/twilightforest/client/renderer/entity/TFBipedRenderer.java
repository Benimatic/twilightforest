package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;

public class TFBipedRenderer<T extends MobEntity, M extends BipedModel<T>> extends BipedRenderer<T, M> {

	private final ResourceLocation textureLoc;

	public TFBipedRenderer(EntityRendererManager manager, M modelBiped, float shadowSize, String textureName) {
		super(manager, modelBiped, shadowSize);

		if (textureName.startsWith("textures")) {
			textureLoc = new ResourceLocation(textureName);
		} else {
			textureLoc = TwilightForestMod.getModelTexture(textureName);
		}
	}

	public TFBipedRenderer(EntityRendererManager manager, M modelBiped, M armorModel1, M armorModel2, float shadowSize, String textureName) {
		this(manager, modelBiped, shadowSize, textureName);
		this.addLayer(new BipedArmorLayer<>(this, armorModel1, armorModel2));
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}
}
