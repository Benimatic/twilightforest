package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.finalcastle.CastleGuardianModel;
import twilightforest.entity.finalcastle.CastleGuardianEntity;

public class CastleGuardianRenderer extends LivingRenderer<CastleGuardianEntity, CastleGuardianModel> {

	private final ResourceLocation textureLoc;

	public CastleGuardianRenderer(EntityRendererManager manager, CastleGuardianModel model, float shadowSize, String textureName) {
		super(manager, model, shadowSize);

		if (textureName.startsWith("textures"))
			textureLoc = new ResourceLocation(textureName);
		else
			textureLoc = TwilightForestMod.getModelTexture(textureName);
	}

	@Override
	public ResourceLocation getEntityTexture(CastleGuardianEntity entity) {
		return textureLoc;
	}
}
