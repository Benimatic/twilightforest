package twilightforest.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import twilightforest.block.TFBlocks;

public class RenderLayerRegistration {
	public static void init() {
		RenderType cutout = RenderType.getCutout();
		RenderTypeLookup.setRenderLayer(TFBlocks.oak_sapling.get(), cutout);
		RenderTypeLookup.setRenderLayer(TFBlocks.canopy_sapling.get(), cutout);
		RenderTypeLookup.setRenderLayer(TFBlocks.mangrove_sapling.get(), cutout);
		RenderTypeLookup.setRenderLayer(TFBlocks.darkwood_sapling.get(), cutout);
		RenderTypeLookup.setRenderLayer(TFBlocks.time_sapling.get(), cutout);
		RenderTypeLookup.setRenderLayer(TFBlocks.transformation_sapling.get(), cutout);
		RenderTypeLookup.setRenderLayer(TFBlocks.mining_sapling.get(), cutout);
		RenderTypeLookup.setRenderLayer(TFBlocks.sorting_sapling.get(), cutout);
	}
}
