package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;

public class RenderTFBoar extends PigRenderer {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("wildboar.png");

	public RenderTFBoar(EntityRendererManager manager, PigModel<PigEntity> model) {
		super(manager);
		this.entityModel = model;
	}

	@Override
	public ResourceLocation getEntityTexture(PigEntity entity) {
		return textureLoc;
	}
}
