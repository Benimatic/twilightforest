package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.finalcastle.CastleGuardianModel;
import twilightforest.entity.finalcastle.CastleGuardianEntity;

public class CastleGuardianRenderer extends LivingEntityRenderer<CastleGuardianEntity, CastleGuardianModel> {

	private final ResourceLocation textureLoc;

	public CastleGuardianRenderer(EntityRendererProvider.Context manager, CastleGuardianModel model, float shadowSize, String textureName) {
		super(manager, model, shadowSize);

		if (textureName.startsWith("textures"))
			textureLoc = new ResourceLocation(textureName);
		else
			textureLoc = TwilightForestMod.getModelTexture(textureName);
	}

	@Override
	public ResourceLocation getTextureLocation(CastleGuardianEntity entity) {
		return textureLoc;
	}
}
