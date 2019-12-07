package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFApocalypseCube;
import twilightforest.entity.EntityTFHarbingerCube;

public class RenderTFHarbingerCube<T extends EntityTFHarbingerCube, M extends ModelTFApocalypseCube<T>> extends LivingRenderer<T, M> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("apocalypse2.png");

	public RenderTFHarbingerCube(EntityRendererManager manager) {
		super(manager, new ModelTFApocalypseCube(), 1.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTFHarbingerCube entity) {
		return textureLoc;
	}

	@Override
	protected void preRenderCallback(EntityTFHarbingerCube entity, float partialTicks) {
		float scale = 1.0F;
		GlStateManager.scalef(scale, scale, scale);
	}
}
