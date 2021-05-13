package twilightforest.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.DownloadTerrainScreen;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.world.TFDimensions;

@OnlyIn(Dist.CLIENT)
public class LoadingScreenListener {

	private final Minecraft client = Minecraft.getInstance();

	@SubscribeEvent
	public void onOpenGui(GuiOpenEvent event) {
		if (event.getGui() instanceof DownloadTerrainScreen && client.player != null) {
			if (client.player.getEntityWorld().getBlockState(client.player.getPosition().down()) == TFBlocks.twilight_portal.get().getDefaultState() || client.player.getEntityWorld().getDimensionKey() == TFDimensions.twilightForest) {
				GuiTwilightForestLoading guiLoading = new GuiTwilightForestLoading();
				guiLoading.setEntering(client.player.getEntityWorld().getDimensionKey() == World.OVERWORLD);
				event.setGui(guiLoading);
			}
		}
	}
}
