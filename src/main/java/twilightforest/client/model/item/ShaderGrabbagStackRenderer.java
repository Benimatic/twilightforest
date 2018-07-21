package twilightforest.client.model.item;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import org.apache.commons.lang3.tuple.Pair;
import twilightforest.TFConfig;
import twilightforest.TwilightForestMod;
import twilightforest.client.TFClientEvents;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.vecmath.Matrix4f;
import java.util.Collections;
import java.util.List;

public class ShaderGrabbagStackRenderer extends TileEntitySpecialRenderer<ShaderGrabbagStackRenderer.DummyTile> {
    public static class DummyTile extends TileEntity {}

    @MethodsReturnNonnullByDefault
    @ParametersAreNonnullByDefault
    public class BakedModel implements IBakedModel {
        private class Overrides extends ItemOverrideList {
            public Overrides() {
                super(Collections.EMPTY_LIST);
            }

            @Override
            public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
                ShaderGrabbagStackRenderer.this.stack = stack;
                return BakedModel.this;
            }
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            return Collections.EMPTY_LIST;
        }

        @Override
        public boolean isAmbientOcclusion() {
            return true;
        }

        @Override
        public boolean isGui3d() {
            return true;
        }

        @Override
        public boolean isBuiltInRenderer() {
            return true;
        }

        @Override
        public TextureAtlasSprite getParticleTexture() {
            return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/iron_block");
        }

        @Override
        public ItemOverrideList getOverrides() {
            return new Overrides();
        }

        @Override
        public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
            ShaderGrabbagStackRenderer.this.transform = cameraTransformType;
            return Pair.of(this, null);
        }
    }

    public final BakedModel baked = new BakedModel();

    private ItemStack stack;
    private ItemCameraTransforms.TransformType transform;
    private IBakedModel modelBack;
    private IBakedModel modelCase;

    @Override
    public void render(DummyTile te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();

        if (transform == ItemCameraTransforms.TransformType.GUI) {
            if (modelBack == null) modelBack = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(new ModelResourceLocation(TwilightForestMod.ID + ":trophy_minor", "inventory"));
            if (modelCase == null) modelCase = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(new ModelResourceLocation(TwilightForestMod.ID + ":shader", "inventory"));

            GlStateManager.disableLighting();
            GlStateManager.translate(0.5F, 0.5F, -1.5F);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ForgeHooksClient.handleCameraTransforms(modelBack, transform, transform == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND));
            GlStateManager.enableLighting();
            GlStateManager.translate(-0.5F, 0.0F, 1.5F);

            GlStateManager.translate(0.5F, 0F, 0F);
            GlStateManager.rotate(TFConfig.rotateTrophyHeadsGui ? TFClientEvents.rotationTicker : 0F, 0.0625F, 1F, 0.0625F);
        }

        // FIXME transformations; Why are they all over the place??
        GlStateManager.disableLighting();
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, ForgeHooksClient.handleCameraTransforms(modelCase, transform, false));
        GlStateManager.enableLighting();

        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }
}
