package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.shield.IShieldCapability;
import twilightforest.entity.boss.EntityTFLich;
import twilightforest.item.TFItems;

import javax.annotation.Nonnull;

public class LayerShields<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
    private int getShieldCount(T entity) {
        EntityTFLich lich = null;
        if (entity instanceof EntityTFLich)
            lich = (EntityTFLich) entity;

        IShieldCapability cap = null;
        if (entity.hasCapability(CapabilityList.SHIELDS, null))
            cap = entity.getCapability(CapabilityList.SHIELDS, null);

        return lich != null ? lich.getShieldStrength() : cap != null ? cap.shieldsLeft() : 0;
    }

    private void renderShields(float scale, T entity, float partialTicks) {
        float rotateAngleY = (entity.ticksExisted + partialTicks) / 5.0F;
        float rotateAngleX = MathHelper.sin((entity.ticksExisted + partialTicks) / 5.0F) / 4.0F;
        float rotateAngleZ = MathHelper.cos((entity.ticksExisted + partialTicks) / 5.0F) / 4.0F;

        ItemStack shieldStack = new ItemStack(TFItems.experiment_115, 1, 3);
        IBakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelWithOverrides(shieldStack, entity.world, entity);

        float prevX = OpenGlHelper.lastBrightnessX, prevY = OpenGlHelper.lastBrightnessY;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
        GL11.glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_EMISSION, RenderHelper.setColorBuffer(1f, 1f, 1f, 1f));

        Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        int count = getShieldCount(entity);
        for (int c = 0; c < count; c++) {
            GlStateManager.pushMatrix();

            GlStateManager.rotatef(rotateAngleZ * (180F / (float) Math.PI)                       , 0.0F, 0.0F, 1.0F);
            GlStateManager.rotatef(rotateAngleY * (180F / (float) Math.PI) + (c * (360F / count)), 0.0F, 1.0F, 0.0F);
            GlStateManager.rotatef(rotateAngleX * (180F / (float) Math.PI)                       , 1.0F, 0.0F, 0.0F);

            // It's upside-down, gotta make it upside-up
            GlStateManager.scalef(scale, -scale, scale);

            // Move the draw away from the entity being drawn around
            GlStateManager.translatef(0F, 0F, 1F);

            Minecraft.getInstance().getItemRenderer().renderItem(shieldStack, ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.NONE, false));

            GlStateManager.popMatrix();
        }

        GL11.glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_EMISSION, RenderHelper.setColorBuffer(0f, 0f, 0f, 1f));
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, prevX, prevY);
    }

    @Override
    public void render(@Nonnull T living, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (getShieldCount(living) > 0) {
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0f);
            renderShields(scale * 13, living, partialTicks);
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
