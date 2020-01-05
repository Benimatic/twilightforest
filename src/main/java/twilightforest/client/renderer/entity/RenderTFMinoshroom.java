package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFMinoshroom;
import twilightforest.entity.boss.EntityTFMinoshroom;

public class RenderTFMinoshroom<T extends EntityTFMinoshroom, M extends ModelTFMinoshroom<T>> extends BipedRenderer<T, M> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("minoshroomtaur.png");

	public RenderTFMinoshroom(EntityRendererManager manager, M model, float shadowSize) {
		super(manager, model, shadowSize);
		this.addLayer(new LayerMinoshroomMushroom());
	}

	// TODO fix offsets (currently copypastedfrom LayerMooshroomMushroom)
	class LayerMinoshroomMushroom extends LayerRenderer<T, M> {
		@Override
		public void render(T minoshroom, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
			if (!minoshroom.isChild() && !minoshroom.isInvisible()) {
				BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
				RenderTFMinoshroom.this.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
				GlStateManager.enableCull();
				GlStateManager.cullFace(GlStateManager.CullFace.FRONT);
				GlStateManager.pushMatrix();
				GlStateManager.scalef(1.0F, -1.0F, 1.0F);
				GlStateManager.translatef(0.2F, 0.35F, 0.5F);
				GlStateManager.rotatef(42.0F, 0.0F, 1.0F, 0.0F);
				GlStateManager.pushMatrix();
				GlStateManager.translatef(-0.5F, -0.5F, 0.5F);
				blockrendererdispatcher.renderBlockBrightness(Blocks.RED_MUSHROOM.getDefaultState(), 1.0F);
				GlStateManager.popMatrix();
				GlStateManager.pushMatrix();
				GlStateManager.translatef(0.1F, 0.0F, -0.6F);
				GlStateManager.rotatef(42.0F, 0.0F, 1.0F, 0.0F);
				GlStateManager.translatef(-0.5F, -0.5F, 0.5F);
				blockrendererdispatcher.renderBlockBrightness(Blocks.RED_MUSHROOM.getDefaultState(), 1.0F);
				GlStateManager.popMatrix();
				GlStateManager.popMatrix();
				GlStateManager.pushMatrix();
				RenderTFMinoshroom.this.getEntityModel().bipedHead.postRender(0.0625F);
				GlStateManager.scalef(1.0F, -1.0F, 1.0F);
				GlStateManager.translatef(0.0F, 1.0F, 0.0F);
				GlStateManager.rotatef(12.0F, 0.0F, 1.0F, 0.0F);
				GlStateManager.translatef(-0.5F, -0.5F, 0.5F);
				blockrendererdispatcher.renderBlockBrightness(Blocks.RED_MUSHROOM.getDefaultState(), 1.0F);
				GlStateManager.popMatrix();
				GlStateManager.cullFace(GlStateManager.CullFace.BACK);
				GlStateManager.disableCull();
			}
		}

		@Override
		public boolean shouldCombineTextures() {
			return false;
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}

}
