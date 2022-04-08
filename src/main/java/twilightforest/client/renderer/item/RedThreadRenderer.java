package twilightforest.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraftforge.client.ForgeHooksClient;
import twilightforest.block.entity.RedThreadBlockEntity;

import java.util.Random;

public class RedThreadRenderer<T extends RedThreadBlockEntity> implements BlockEntityRenderer<T> {

	public RedThreadRenderer(BlockEntityRendererProvider.Context context) {
		//do I need to do anything with this? I needed a ctor to call it in TFBlockEntites
	}

	//grabbed from FallingBlockRenderer.render, ripped out the parts we dont need
	@Override
	public void render(T thread, float ticks, PoseStack ms, MultiBufferSource source, int light, int overlay) {
		ms.pushPose();
		BlockRenderDispatcher blockrenderdispatcher = Minecraft.getInstance().getBlockRenderer();
		for (RenderType type : RenderType.chunkBufferLayers()) {
			if (ItemBlockRenderTypes.canRenderInLayer(thread.getBlockState(), type)) {
				ForgeHooksClient.setRenderType(type);
				blockrenderdispatcher.getModelRenderer().tesselateBlock(thread.getLevel(), blockrenderdispatcher.getBlockModel(thread.getBlockState()), thread.getBlockState(), thread.getBlockPos(), ms, source.getBuffer(type), false, new Random(), thread.getBlockState().getSeed(thread.getBlockPos()), OverlayTexture.NO_OVERLAY);
			}
		}
		ForgeHooksClient.setRenderType(null);
		ms.popPose();
	}
}
