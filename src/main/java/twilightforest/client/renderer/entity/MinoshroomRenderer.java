package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.MinoshroomModel;
import twilightforest.entity.boss.MinoshroomEntity;

//old renderer had the head mushroom in a different spot - line is commented out
public class MinoshroomRenderer extends BipedRenderer<MinoshroomEntity, MinoshroomModel> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("minoshroomtaur.png");

	public MinoshroomRenderer(EntityRendererManager manager, MinoshroomModel model, float shadowSize) {
		super(manager, model, shadowSize);
		this.addLayer(new LayerMinoshroomMushroom(this));
	}

	/**
	 * [VanillaCopy] {@link net.minecraft.client.renderer.entity.layers.MooshroomMushroomLayer}
	 */
	static class LayerMinoshroomMushroom extends LayerRenderer<MinoshroomEntity, MinoshroomModel> {

		public LayerMinoshroomMushroom(IEntityRenderer<MinoshroomEntity, MinoshroomModel> renderer) {
			super(renderer);
		}

		@Override
		public void render(MatrixStack ms, IRenderTypeBuffer buffers, int light, MinoshroomEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
			if (!entity.isChild() && !entity.isInvisible()) {
				BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
				BlockState blockstate = Blocks.RED_MUSHROOM.getDefaultState(); // TF: hardcode mushroom state
				int i = LivingRenderer.getPackedOverlay(entity, 0.0F);
				ms.push();
				ms.translate(0.2F, -0.35F, 0.5D);
				ms.rotate(Vector3f.YP.rotationDegrees(-48.0F));
				ms.scale(-1.0F, -1.0F, 1.0F);
				ms.translate(-0.5D, -0.5D, -0.5D);
				blockrendererdispatcher.renderBlock(blockstate, ms, buffers, light, i);
				ms.pop();
				ms.push();
				ms.translate(0.2F, -0.35F, 0.5D);
				ms.rotate(Vector3f.YP.rotationDegrees(42.0F));
				ms.translate(0.1F, 0.0D, -0.6F);
				ms.rotate(Vector3f.YP.rotationDegrees(-48.0F));
				ms.scale(-1.0F, -1.0F, 1.0F);
				ms.translate(-0.5D, -0.5D, -0.5D);
				blockrendererdispatcher.renderBlock(blockstate, ms, buffers, light, i);
				ms.pop();
				ms.push();
				this.getEntityModel().bipedHead.translateRotate(ms);
				// TF - adjust head shroom
				//old render
				//ms.translate(0.0D, -0.9, 0.05);
				ms.translate(0.0D, -1.1, 0.05);
				ms.rotate(Vector3f.YP.rotationDegrees(-78.0F));
				ms.scale(-1.0F, -1.0F, 1.0F);
				ms.translate(-0.5D, -0.5D, -0.5D);
				blockrendererdispatcher.renderBlock(blockstate, ms, buffers, light, i);
				ms.pop();
			}
		}
	}

	@Override
	public ResourceLocation getEntityTexture(MinoshroomEntity entity) {
		return textureLoc;
	}
}
