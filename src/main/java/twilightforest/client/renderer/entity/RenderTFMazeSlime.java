package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;

public class RenderTFMazeSlime extends SlimeRenderer {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("mazeslime.png");

	public RenderTFMazeSlime(EntityRendererManager manager, float shadowSize) {
		super(manager);
		this.shadowSize = shadowSize;
	}

	@Override
	protected ResourceLocation getEntityTexture(SlimeEntity entity) {
		return textureLoc;
	}
}
