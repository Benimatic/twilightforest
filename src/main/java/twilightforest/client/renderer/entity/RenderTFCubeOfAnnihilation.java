package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFCubeOfAnnihilation;
import twilightforest.entity.EntityTFCubeOfAnnihilation;

public class RenderTFCubeOfAnnihilation extends EntityRenderer<EntityTFCubeOfAnnihilation> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("cubeofannihilation.png");
	private final ModelBase model = new ModelTFCubeOfAnnihilation();

	public RenderTFCubeOfAnnihilation(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	public void doRender(EntityTFCubeOfAnnihilation entity, double x, double y, double z, float yaw, float partialTicks) {
		super.doRender(entity, x, y, z, yaw, partialTicks);
		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) x, (float) y, (float) z);

		this.bindEntityTexture(entity);

		GlStateManager.scalef(-1.0F, -1.0F, 1.0F);

		GlStateManager.rotatef(MathHelper.wrapDegrees(((float) x + (float) z + entity.ticksExisted + partialTicks) * 11F), 0, 1, 0);


		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GlStateManager.translatef(0F, -0.5F, 0F);
		this.model.renderf(entity, 0.0F, 0.0F, 0.0F, 0.0F, partialTicks, 0.0625F / 2F);
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();


		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTFCubeOfAnnihilation entity) {
		return textureLoc;
	}
}
