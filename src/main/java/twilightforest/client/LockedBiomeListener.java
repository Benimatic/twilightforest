package twilightforest.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import twilightforest.TFConfig;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFBiomes;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import twilightforest.util.LandmarkUtil;
import twilightforest.world.registration.TFGenerationSettings;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE, modid = TwilightForestMod.ID)
public class LockedBiomeListener {

	private static boolean shownToast = false;
	private static int timeUntilToast = 60;

	@SubscribeEvent
	public static void clientTick(TickEvent.ClientTickEvent event) {
		Player player = Minecraft.getInstance().player;
		if (player == null || !(player.level() instanceof ClientLevel level))
			return;

		//attempt to send a biome locked toast if our player is in a locked biome, only every 5 ticks
		if(level.isClientSide() && event.phase == TickEvent.Phase.END && player.tickCount % 5 == 0
				&& LandmarkUtil.isProgressionEnforced(level)
				&& !player.isCreative() && !player.isSpectator() && !TFConfig.CLIENT_CONFIG.disableLockedBiomeToasts.get()) {
			if (!TFGenerationSettings.isBiomeSafeFor(level.getBiome(player.blockPosition()).value(), player)) {
				timeUntilToast--;
				ItemStack item;
				if (level.getBiome(player.blockPosition()).is(TFBiomes.DARK_FOREST)) {
					item = new ItemStack(TFBlocks.LICH_TOWER_MINIATURE_STRUCTURE.get());
				} else if (level.getBiome(player.blockPosition()).is(TFBiomes.DARK_FOREST_CENTER)) {
					item = new ItemStack(TFBlocks.KNIGHT_PHANTOM_TROPHY.get().asItem());
				} else if (level.getBiome(player.blockPosition()).is(TFBiomes.SNOWY_FOREST)) {
					item = new ItemStack(TFBlocks.LICH_TOWER_MINIATURE_STRUCTURE.get());
				} else if (level.getBiome(player.blockPosition()).is(TFBiomes.GLACIER)) {
					item = new ItemStack(TFItems.ALPHA_YETI_FUR.get());
				} else if (level.getBiome(player.blockPosition()).is(TFBiomes.SWAMP)) {
					item = new ItemStack(TFBlocks.LICH_TOWER_MINIATURE_STRUCTURE.get());
				} else if (level.getBiome(player.blockPosition()).is(TFBiomes.FIRE_SWAMP)) {
					item = new ItemStack(TFItems.MEEF_STROGANOFF.get());
				} else if (level.getBiome(player.blockPosition()).is(TFBiomes.HIGHLANDS)) {
					item = new ItemStack(TFBlocks.UBEROUS_SOIL.get());
				} else if (level.getBiome(player.blockPosition()).is(TFBiomes.THORNLANDS)) {
					item = new ItemStack(TFItems.LAMP_OF_CINDERS.get());
				} else if (level.getBiome(player.blockPosition()).is(TFBiomes.FINAL_PLATEAU)) {
					item = new ItemStack(TFItems.LAMP_OF_CINDERS.get());
				} else {
					item = new ItemStack(TFBlocks.TWILIGHT_PORTAL_MINIATURE_STRUCTURE.get());
				}
				if(!shownToast && timeUntilToast <= 0) {
					Minecraft.getInstance().getToasts().addToast(new LockedBiomeToast(item));
					shownToast = true;
				}
			} else {
				if(shownToast) {
					timeUntilToast = 60;
					shownToast = false;
				}
			}
		}
	}
}
