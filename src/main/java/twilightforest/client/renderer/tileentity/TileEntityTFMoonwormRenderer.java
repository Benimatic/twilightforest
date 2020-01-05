package twilightforest.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.BugModelAnimationHelper;
import twilightforest.client.model.entity.ModelTFMoonworm;
import twilightforest.tileentity.critters.TileEntityTFMoonwormTicking;

public class TileEntityTFMoonwormRenderer extends TileEntityRenderer<TileEntityTFMoonwormTicking> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("moonworm.png");
	private final ModelTFMoonworm moonwormModel = new ModelTFMoonworm();

	@Override
	public void render(TileEntityTFMoonwormTicking te, double x, double y, double z, float partialTicks, int destroyStage) {
		int yaw = te != null ? te.currentYaw : BugModelAnimationHelper.currentRotation;
		if (te == null) partialTicks = Minecraft.getInstance().getRenderPartialTicks();

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
		GlStateManager.translated(x + 0.5F, y + 0.5F, z + 0.5F);
		GlStateManager.rotatef(rotX, 1F, 0F, 0F);
		GlStateManager.rotatef(rotZ, 0F, 0F, 1F);
		GlStateManager.rotatef(yaw, 0F, 1F, 0F);

		this.bindTexture(textureLoc);

		GlStateManager.scalef(1f, -1f, -1f);

		moonwormModel.setLivingAnimations(te, partialTicks);

		// render the firefly body
		moonwormModel.render(0.0625f);

		GlStateManager.popMatrix();
	}
}
