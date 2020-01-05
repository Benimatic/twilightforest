package twilightforest.client.renderer.tileentity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import twilightforest.TwilightForestMod;
import twilightforest.client.BugModelAnimationHelper;
import twilightforest.client.model.entity.ModelTFFirefly;
import twilightforest.tileentity.critters.TileEntityTFFireflyTicking;

public class TileEntityTFFireflyRenderer extends TileEntityRenderer<TileEntityTFFireflyTicking> {

	private final ModelTFFirefly fireflyModel = new ModelTFFirefly();
	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("firefly-tiny.png");

	@Override
	public void render(TileEntityTFFireflyTicking te, double x, double y, double z, float partialTicks, int destroyStage) {
		int yaw = te != null ? te.currentYaw : BugModelAnimationHelper.currentYaw;
		float glow = te != null ? te.glowIntensity : BugModelAnimationHelper.glowIntensity;

		GlStateManager.pushMatrix();
		Direction facing = Direction.byIndex(te != null ? te.getBlockMetadata() : 0);

		float rotX = 90.0F;
		float rotZ = 0.0F;
		if (facing == Direction.SOUTH) {
			rotZ = 0F;
		} else if (facing == Direction.NORTH) {
			rotZ = 180F;
		} else if (facing == Direction.EAST) {
			rotZ = -90F;
		} else if (facing == Direction.WEST) {
			rotZ = 90F;
		} else if (facing == Direction.UP) {
			rotX = 0F;
		} else if (facing == Direction.DOWN) {
			rotX = 180F;
		}
		GlStateManager.translatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		GlStateManager.rotatef(rotX, 1F, 0F, 0F);
		GlStateManager.rotatef(rotZ, 0F, 0F, 1F);
		GlStateManager.rotatef(yaw, 0F, 1F, 0F);

		this.bindTexture(textureLoc);
		GlStateManager.pushMatrix();
		GlStateManager.scalef(1f, -1f, -1f);

		GlStateManager.colorMask(true, true, true, true);

		// render the firefly body
		GlStateManager.disableBlend();
		fireflyModel.render(0.0625f);

//
//        GL11.glEnable(3042 /*GL_BLEND*/);
//        GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
//        GL11.glBlendFunc(770, 771);
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);

		// render the firefly glow
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.disableLighting();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, glow);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		fireflyModel.glow.render(0.0625f);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableLighting();

		GlStateManager.popMatrix();
		GlStateManager.color4f(1, 1, 1, 1);
		GlStateManager.popMatrix();
	}
}
