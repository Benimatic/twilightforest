package twilightforest.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import twilightforest.TFConfig;
import twilightforest.TwilightForestMod;
import twilightforest.compat.curios.CuriosCompat;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFBlockEntities;
import twilightforest.client.renderer.entity.IceLayer;
import twilightforest.client.renderer.entity.ShieldLayer;
import twilightforest.init.TFMenuTypes;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = TwilightForestMod.ID)
public class TFClientSetup {

	public static boolean optifinePresent = false;

	public static void init() {
		IEventBus busMod = FMLJavaModLoadingContext.get().getModEventBus();
		TFShaders.init(busMod);
	}

	@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE, modid = TwilightForestMod.ID)
	public static class ForgeEvents {

		private static boolean optifineWarningShown = false;

		@SubscribeEvent
		public static void showOptifineWarning(ScreenEvent.Init.Post event) {
			if (optifinePresent && !optifineWarningShown && !TFConfig.CLIENT_CONFIG.disableOptifineNagScreen.get() && event.getScreen() instanceof TitleScreen) {
				optifineWarningShown = true;
				Minecraft.getInstance().setScreen(new OptifineWarningScreen(event.getScreen()));
			}
		}

	}

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent evt) {
		try {
			Class.forName("net.optifine.Config");
			optifinePresent = true;
		} catch (ClassNotFoundException e) {
			optifinePresent = false;
		}

        TFBlockEntities.registerTileEntityRenders();
        TFMenuTypes.renderScreens();

        evt.enqueueWork(() -> {
            Sheets.addWoodType(TFBlocks.TWILIGHT_OAK);
            Sheets.addWoodType(TFBlocks.CANOPY);
            Sheets.addWoodType(TFBlocks.MANGROVE);
            Sheets.addWoodType(TFBlocks.DARKWOOD);
            Sheets.addWoodType(TFBlocks.TIMEWOOD);
            Sheets.addWoodType(TFBlocks.TRANSFORMATION);
            Sheets.addWoodType(TFBlocks.MINING);
            Sheets.addWoodType(TFBlocks.SORTING);

			if(ModList.get().isLoaded("curios")) {
				CuriosCompat.registerCurioRenderers();
			}
        });
       
    }

	private static Field field_EntityRenderersEvent$AddLayers_renderers;

	@SubscribeEvent
	@SuppressWarnings("unchecked")
	public static void attachRenderLayers(EntityRenderersEvent.AddLayers event) {
		if (field_EntityRenderersEvent$AddLayers_renderers == null) {
			try {
				field_EntityRenderersEvent$AddLayers_renderers = EntityRenderersEvent.AddLayers.class.getDeclaredField("renderers");
				field_EntityRenderersEvent$AddLayers_renderers.setAccessible(true);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		if (field_EntityRenderersEvent$AddLayers_renderers != null) {
			event.getSkins().forEach(renderer -> {
				LivingEntityRenderer<Player, EntityModel<Player>> skin = event.getSkin(renderer);
				attachRenderLayers(Objects.requireNonNull(skin));
			});
			try {
				((Map<EntityType<?>, EntityRenderer<?>>) field_EntityRenderersEvent$AddLayers_renderers.get(event)).values().stream().
						filter(LivingEntityRenderer.class::isInstance).map(LivingEntityRenderer.class::cast).forEach(TFClientSetup::attachRenderLayers);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	private static <T extends LivingEntity, M extends EntityModel<T>> void attachRenderLayers(LivingEntityRenderer<T, M> renderer) {
		renderer.addLayer(new ShieldLayer<>(renderer));
		renderer.addLayer(new IceLayer<>(renderer));
	}
}
