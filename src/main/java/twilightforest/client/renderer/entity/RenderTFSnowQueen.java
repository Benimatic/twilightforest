package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFSnowQueen;
import twilightforest.entity.boss.EntityTFSnowQueen;

public class RenderTFSnowQueen<T extends EntityTFSnowQueen, M extends ModelTFSnowQueen<T>> extends BipedRenderer<T, M> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("snowqueen.png");

	public RenderTFSnowQueen(EntityRendererManager manager) {
		super(manager, new ModelTFSnowQueen(), 0.625F);
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}

	@Override
	protected void preRenderCallback(T queen, float partialTicks) {
		float scale = 1.2F;
		GlStateManager.scalef(scale, scale, scale);
	}

	@Override
	public void doRender(T queen, double x, double y, double z, float yaw, float partialTicks) {
		super.doRender(queen, x, y, z, yaw, partialTicks);

		for (int i = 0; i < queen.iceArray.length; i++) {
			renderManager.renderEntityStatic(queen.iceArray[i], partialTicks, false);
		}
	}
}
