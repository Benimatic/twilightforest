package twilightforest.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import twilightforest.TwilightForestMod;
import twilightforest.client.BugModelAnimationHelper;
import twilightforest.client.model.entity.MoonwormModel;
import twilightforest.tileentity.MoonwormTileEntity;

import javax.annotation.Nullable;

public class MoonwormTileEntityRenderer extends TileEntityRenderer<MoonwormTileEntity> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("moonworm.png");
	private final MoonwormModel moonwormModel = new MoonwormModel();

	public MoonwormTileEntityRenderer(TileEntityRendererDispatcher dispatch) {
		super(dispatch);
	}

	@Override
	public void render(@Nullable MoonwormTileEntity te, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffer, int light, int overlay) {
		int yaw = te != null ? te.currentYaw : BugModelAnimationHelper.currentRotation;
		if (te == null) partialTicks = Minecraft.getInstance().getRenderPartialTicks();

		ms.push();
		Direction facing = te != null ? te.getBlockState().get(DirectionalBlock.FACING) : Direction.NORTH;

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
		ms.translate(0.5, 0.5, 0.5);
		ms.rotate(Vector3f.XP.rotationDegrees(rotX));
		ms.rotate(Vector3f.ZP.rotationDegrees(rotZ));
		ms.rotate(Vector3f.YP.rotationDegrees(yaw));
		ms.scale(1f, -1f, -1f);

		IVertexBuilder builder = buffer.getBuffer(moonwormModel.getRenderType(textureLoc));
		moonwormModel.setRotationAngles(te, partialTicks);
		moonwormModel.render(ms, builder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

		ms.pop();
	}
}
