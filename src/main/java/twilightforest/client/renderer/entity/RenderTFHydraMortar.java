package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFHydraMortar;

public class RenderTFHydraMortar<T extends RenderTFHydraMortar<T>> extends EntityRenderer<T> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("hydramortar.png");
	private final ModelTFHydraMortar mortarModel = new ModelTFHydraMortar();

	public RenderTFHydraMortar(EntityRendererManager manager) {
		super(manager);
		this.shadowSize = 0.5F;
	}

	@Override
	public void doRender(T mortar, double x, double y, double z, float yaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) x, (float) y, (float) z);
		float var10;

		if ((float) mortar.fuse - partialTicks + 1.0F < 10.0F) {
			var10 = 1.0F - ((float) mortar.fuse - partialTicks + 1.0F) / 10.0F;

			if (var10 < 0.0F) {
				var10 = 0.0F;
			}

			if (var10 > 1.0F) {
				var10 = 1.0F;
			}

			var10 *= var10;
			var10 *= var10;
			float var11 = 1.0F + var10 * 0.3F;
			GlStateManager.scalef(var11, var11, var11);
		}

		var10 = (1.0F - ((float) mortar.fuse - partialTicks + 1.0F) / 100.0F) * 0.8F;
		this.bindTexture(textureLoc);

		mortarModel.render(0.075F);

		if (mortar.fuse / 5 % 2 == 0) {
			GlStateManager.disableTexture2D();
			GlStateManager.disableLighting();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, var10);

			mortarModel.render(0.075F);

			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
			GlStateManager.enableTexture2D();
		}

		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}
}
