package twilightforest.client;

import net.minecraft.block.BlockLeaves;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import twilightforest.block.TFBlocks;
import twilightforest.world.WorldProviderTwilightForest;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class TFClientTicker {
    /**
     * On the tick, we kill the vignette
	 */
	@SubscribeEvent
	public void clientTick(ClientTickEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		World world = mc.world;

		((BlockLeaves) TFBlocks.leaves).setGraphicsLevel(mc.gameSettings.fancyGraphics);
		((BlockLeaves) TFBlocks.leaves3).setGraphicsLevel(mc.gameSettings.fancyGraphics);
		((BlockLeaves) TFBlocks.magicLeaves).setGraphicsLevel(mc.gameSettings.fancyGraphics);

		// only fire if we're in the twilight forest
		if (world != null && (world.provider instanceof WorldProviderTwilightForest))
		{
			// vignette
			if (mc.ingameGUI != null)
			{
				mc.ingameGUI.prevVignetteBrightness = 0.0F;
			}

		}
	}
}