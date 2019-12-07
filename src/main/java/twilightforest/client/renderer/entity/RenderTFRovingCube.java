package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFCubeOfAnnihilation;
import twilightforest.entity.EntityTFRovingCube;

public class RenderTFRovingCube<T extends EntityTFRovingCube> extends EntityRenderer<T> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("cubeofannihilation.png");
	private final ModelBase model = new ModelTFCubeOfAnnihilation();

	public RenderTFRovingCube(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float yaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translated(x, y, z);

		this.bindEntityTexture(entity);

		GlStateManager.scalef(2.0F, 2.0F, 2.0F);

		GlStateManager.rotatef(MathHelper.wrapDegrees(((float) x + (float) z + entity.ticksExisted + partialTicks) * 11F), 0, 1, 0);

		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GlStateManager.translatef(0F, 0.75F, 0F);
		this.model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, partialTicks, 0.0625F / 2F);
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();

		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}
}
