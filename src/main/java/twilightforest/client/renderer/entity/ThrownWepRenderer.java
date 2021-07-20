package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import twilightforest.entity.boss.ThrownWepEntity;

public class ThrownWepRenderer extends EntityRenderer<ThrownWepEntity> {

	public ThrownWepRenderer(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	public void render(ThrownWepEntity entity, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
		stack.push();

		float spin = (entity.ticksExisted + partialTicks) * -10F + 90F;

		// size up
		stack.scale(1.25F, 1.25F, 1.25F);

		this.renderDroppedItem(stack, buffer, light, entity.getItem(), yaw, spin);

		stack.pop();
	}

	// todo recheck transformations
	private void renderDroppedItem(MatrixStack matrix, IRenderTypeBuffer buffer, int light, ItemStack stack, float rotation, float spin) {
		matrix.push();

		float f9 = 0.5F;
		float f10 = 0.25F;

		matrix.rotate(Vector3f.YP.rotationDegrees(rotation + 270));
		matrix.rotate(Vector3f.ZP.rotationDegrees(spin));

		float f12 = 0.0625F;
		float f11 = 0.021875F;

		matrix.translate(-f9, -f10, -(f12 + f11));
		matrix.translate(0f, 0f, f12 + f11);

		Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND, light, OverlayTexture.NO_OVERLAY, matrix, buffer);

		matrix.pop();
	}

	@Override
	public ResourceLocation getEntityTexture(ThrownWepEntity entity) {
		return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
	}
}
