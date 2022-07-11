package twilightforest.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import twilightforest.init.TFBlocks;

public class RenderLayerRegistration {
	@SuppressWarnings("removal")
	public static void init() {
		RenderType cutout = RenderType.cutout();
		RenderType translucent = RenderType.translucent();
		//FIXME these have to stay until https://github.com/MinecraftForge/MinecraftForge/pull/8852 is merged
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TIME_DOOR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TRANSFORMATION_DOOR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.SORTING_DOOR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TIME_TRAPDOOR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.SORTING_TRAPDOOR.get(), cutout);

		//FIXME these blocks absolutely refuse to use the render types defined in the json
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.CANDELABRA.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.TWISTED_STONE_PILLAR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.FIERY_BLOCK.get(), translucent);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.YELLOW_CASTLE_RUNE_BRICK.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.VIOLET_CASTLE_RUNE_BRICK.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.PINK_CASTLE_RUNE_BRICK.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.BLUE_CASTLE_RUNE_BRICK.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.YELLOW_CASTLE_DOOR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.VIOLET_CASTLE_DOOR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.PINK_CASTLE_DOOR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.BLUE_CASTLE_DOOR.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.GREEN_THORNS.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.BROWN_THORNS.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.BURNT_THORNS.get(), cutout);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.PINK_FORCE_FIELD.get(), translucent);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.BLUE_FORCE_FIELD.get(), translucent);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.GREEN_FORCE_FIELD.get(), translucent);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.VIOLET_FORCE_FIELD.get(), translucent);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.ORANGE_FORCE_FIELD.get(), translucent);
		ItemBlockRenderTypes.setRenderLayer(TFBlocks.RED_THREAD.get(), cutout);
	}
}
