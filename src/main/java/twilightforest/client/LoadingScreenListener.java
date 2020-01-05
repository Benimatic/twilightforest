package twilightforest.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.DownloadTerrainScreen;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import twilightforest.TFConfig;

@OnlyIn(Dist.CLIENT)
public class LoadingScreenListener {

	private final Minecraft client = Minecraft.getInstance();
	private DimensionType lastDimension = DimensionType.OVERWORLD;

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END && event.player == client.player) {
			lastDimension = event.player.dimension;
		}
	}

	@SubscribeEvent
	public void onOpenGui(GuiOpenEvent event) {
		if (event.getGui() instanceof DownloadTerrainScreen && client.player != null) {
			int tfDimension = TFConfig.dimension.dimensionID; //TODO: We're not ints, anymore
			if (client.player.dimension == tfDimension || lastDimension == tfDimension) {
				GuiTwilightForestLoading guiLoading = new GuiTwilightForestLoading();
				guiLoading.setEntering(client.player.dimension == tfDimension);
				event.setGui(guiLoading);
			}
		}
	}
}
