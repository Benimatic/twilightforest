package twilightforest.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.BugModelAnimationHelper;
import twilightforest.client.model.entity.ModelTFMoonworm;
import twilightforest.tileentity.critters.TileEntityTFMoonwormTicking;

import javax.annotation.Nullable;

public class TileEntityTFMoonwormRenderer extends TileEntitySpecialRenderer<TileEntityTFMoonwormTicking> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("moonworm.png");
	private final ModelTFMoonworm moonwormModel = new ModelTFMoonworm();

	@Override
	public void render(@Nullable TileEntityTFMoonwormTicking te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		int yaw = te != null ? te.currentYaw : BugModelAnimationHelper.currentRotation;
		if (te == null) partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();

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
		GlStateManager.translate(x + 0.5F, y + 0.5F, z + 0.5F);
		GlStateManager.rotate(rotX, 1F, 0F, 0F);
		GlStateManager.rotate(rotZ, 0F, 0F, 1F);
		GlStateManager.rotate(yaw, 0F, 1F, 0F);

		this.bindTexture(textureLoc);

		GlStateManager.scale(1f, -1f, -1f);

		moonwormModel.setLivingAnimations(te, partialTicks);

		// render the firefly body
		moonwormModel.render(0.0625f);

		GlStateManager.popMatrix();
	}
}
