package twilightforest.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.DownloadTerrainScreen;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import twilightforest.TFConfig;
import twilightforest.block.TFBlocks;

@OnlyIn(Dist.CLIENT)
public class LoadingScreenListener {

	private final Minecraft client = Minecraft.getInstance();

	@SubscribeEvent
	public void onOpenGui(GuiOpenEvent event) {
		if (event.getGui() instanceof DownloadTerrainScreen && client.player != null) {
			RegistryKey<World> tfDimension = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(TFConfig.COMMON_CONFIG.DIMENSION.twilightForestID.get()));
			if (client.player.getEntityWorld().getBlockState(client.player.getPosition().down()) == TFBlocks.twilight_portal.get().getDefaultState() || client.player.getEntityWorld().getDimensionKey() == tfDimension) {
				LoadingScreenGui guiLoading = new LoadingScreenGui();
				guiLoading.setEntering(client.player.getEntityWorld().getDimensionKey() == World.OVERWORLD);
				event.setGui(guiLoading);
			}
		}
	}
}
