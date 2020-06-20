package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.ModelTFMinoshroom;
import twilightforest.entity.boss.EntityTFMinoshroom;

public class RenderTFMinoshroom extends BipedRenderer<EntityTFMinoshroom, ModelTFMinoshroom> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("minoshroomtaur.png");

	public RenderTFMinoshroom(EntityRendererManager manager, ModelTFMinoshroom model, float shadowSize) {
		super(manager, model, shadowSize);
		this.addLayer(new LayerMinoshroomMushroom(this));
	}

	/**
	 * [VanillaCopy] {@link net.minecraft.client.renderer.entity.layers.MooshroomMushroomLayer}
	 */
	static class LayerMinoshroomMushroom extends LayerRenderer<EntityTFMinoshroom, ModelTFMinoshroom> {

		public LayerMinoshroomMushroom(IEntityRenderer<EntityTFMinoshroom, ModelTFMinoshroom> renderer) {
			super(renderer);
		}

		@Override
		public void render(MatrixStack ms, IRenderTypeBuffer buffers, int light, EntityTFMinoshroom entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
			if (!entity.isChild() && !entity.isInvisible()) {
				BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
				BlockState blockstate = Blocks.RED_MUSHROOM.getDefaultState(); // TF: hardcode mushroom state
				int i = LivingRenderer.getOverlay(entity, 0.0F);
				ms.push();
				ms.translate((double)0.2F, (double)-0.35F, 0.5D);
				ms.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-48.0F));
				ms.scale(-1.0F, -1.0F, 1.0F);
				ms.translate(-0.5D, -0.5D, -0.5D);
				blockrendererdispatcher.renderBlockAsEntity(blockstate, ms, buffers, light, i);
				ms.pop();
				ms.push();
				ms.translate((double)0.2F, (double)-0.35F, 0.5D);
				ms.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(42.0F));
				ms.translate((double)0.1F, 0.0D, (double)-0.6F);
				ms.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-48.0F));
				ms.scale(-1.0F, -1.0F, 1.0F);
				ms.translate(-0.5D, -0.5D, -0.5D);
				blockrendererdispatcher.renderBlockAsEntity(blockstate, ms, buffers, light, i);
				ms.pop();
				ms.push();
				this.getEntityModel().bipedHead.rotate(ms);
				// TF - adjust head shroom
				ms.translate(0.0D, -0.9, 0.05);
				ms.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-78.0F));
				ms.scale(-1.0F, -1.0F, 1.0F);
				ms.translate(-0.5D, -0.5D, -0.5D);
				blockrendererdispatcher.renderBlockAsEntity(blockstate, ms, buffers, light, i);
				ms.pop();
			}
		}
	}

	@Override
	public ResourceLocation getEntityTexture(EntityTFMinoshroom entity) {
		return textureLoc;
	}
}
