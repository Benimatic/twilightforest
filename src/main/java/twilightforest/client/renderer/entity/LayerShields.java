package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.shield.IShieldCapability;
import twilightforest.entity.boss.EntityTFLich;
import twilightforest.item.TFItems;

public class LayerShields<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {

	public LayerShields(IEntityRenderer<T, M> renderer) {
		super(renderer);
	}

	private int getShieldCount(T entity) {
        EntityTFLich lich = null;
        if (entity instanceof EntityTFLich)
            lich = (EntityTFLich) entity;

        int capShields = entity.getCapability(CapabilityList.SHIELDS).map(IShieldCapability::shieldsLeft).orElse(0);

        return lich != null ? lich.getShieldStrength() : capShields;
    }

    private void renderShields(MatrixStack stack, float scale, T entity, float partialTicks) {
        float rotateAngleY = (entity.ticksExisted + partialTicks) / 5.0F;
        float rotateAngleX = MathHelper.sin((entity.ticksExisted + partialTicks) / 5.0F) / 4.0F;
        float rotateAngleZ = MathHelper.cos((entity.ticksExisted + partialTicks) / 5.0F) / 4.0F;

        ItemStack shieldStack = new ItemStack(TFItems.experiment_115.get(), 1, 3); //TODO: Evaluate this
        IBakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelWithOverrides(shieldStack, entity.world, entity);

        float prevX = OpenGlHelper.lastBrightnessX, prevY = OpenGlHelper.lastBrightnessY;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
        GL11.glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_EMISSION, RenderHelper.setColorBuffer(1f, 1f, 1f, 1f));

        Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        int count = getShieldCount(entity);
        for (int c = 0; c < count; c++) {
            stack.push();

            RenderSystem.rotatef(rotateAngleZ * (180F / (float) Math.PI)                       , 0.0F, 0.0F, 1.0F);
            RenderSystem.rotatef(rotateAngleY * (180F / (float) Math.PI) + (c * (360F / count)), 0.0F, 1.0F, 0.0F);
            RenderSystem.rotatef(rotateAngleX * (180F / (float) Math.PI)                       , 1.0F, 0.0F, 0.0F);

            // It's upside-down, gotta make it upside-up
            stack.scale(scale, -scale, scale);

            // Move the draw away from the entity being drawn around
            stack.translate(0F, 0F, 1F);

            Minecraft.getInstance().getItemRenderer().renderItem(shieldStack, ForgeHooksClient.handleCameraTransforms(stack, model, ItemCameraTransforms.TransformType.NONE, false));

            stack.pop();
        }

        GL11.glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_EMISSION, RenderHelper.setColorBuffer(0f, 0f, 0f, 1f));
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, prevX, prevY);
    }

    @Override
    public void render(MatrixStack stack, IRenderTypeBuffer buffer, int i, T living, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (getShieldCount(living) > 0) {
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0f);
			renderShields(stack, scale * 13, living, partialTicks);
		}
    }
//
//    @Override
//    public boolean shouldCombineTextures() {
//        return false;
//    }
}
