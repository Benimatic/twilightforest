package twilightforest.client.renderer.entity;

import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import twilightforest.entity.boss.EntityTFThrownWep;

public class RenderTFThrownWep<T extends EntityTFThrownWep> extends EntityRenderer<T> {

	public RenderTFThrownWep(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	public void doRender(EntityTFThrownWep entity, double x, double y, double z, float yaw, float partialTicks) {

		GlStateManager.pushMatrix();

		float spin = (entity.ticksExisted + partialTicks) * -10F + 90F;


		GlStateManager.translatef((float) x, (float) y, (float) z);
		GlStateManager.enableRescaleNormal();

		// size up
		GlStateManager.scalef(1.25F, 1.25F, 1.25F);

		this.renderDroppedItem(entity.getItem(), yaw, spin);

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}

	// todo recheck transformations
	private void renderDroppedItem(ItemStack stack, float rotation, float spin) {
		GlStateManager.pushMatrix();

		float f9 = 0.5F;
		float f10 = 0.25F;

		GlStateManager.rotatef(rotation + 270f, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotatef(spin, 0.0F, 0.0F, 1.0F);

		float f12 = 0.0625F;
		float f11 = 0.021875F;

		GlStateManager.translatef(-f9, -f10, -(f12 + f11));
		GlStateManager.translatef(0f, 0f, f12 + f11);

		Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);

		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
}
