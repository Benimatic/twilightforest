package twilightforest.client.renderer.tileentity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.BugModelAnimationHelper;
import twilightforest.client.model.entity.ModelTFCicada;
import twilightforest.tileentity.critters.TileEntityTFCicadaTicking;

import javax.annotation.Nullable;

public class TileEntityTFCicadaRenderer extends TileEntitySpecialRenderer<TileEntityTFCicadaTicking> {

	private final ModelTFCicada cicadaModel = new ModelTFCicada();
	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("cicada-model.png");

	@Override
	public void render(@Nullable TileEntityTFCicadaTicking te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		int yaw = te != null ? te.currentYaw : BugModelAnimationHelper.currentYaw;

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
		cicadaModel.render(0.0625f);
		GlStateManager.popMatrix();
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.popMatrix();
	}
}
