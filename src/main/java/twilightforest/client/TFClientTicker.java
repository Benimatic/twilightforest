package twilightforest.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
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