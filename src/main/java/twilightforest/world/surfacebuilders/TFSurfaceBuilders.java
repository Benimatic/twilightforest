package twilightforest.world.surfacebuilders;

import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import twilightforest.TwilightForestMod;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TFSurfaceBuilders {
	// Biomes are registered before surface builders and need the raw objects. So don't use DeferredRegister here.
	public static final DeadrockFillingSurfaceBuilder DEADROCK_FILLING = new DeadrockFillingSurfaceBuilder(SurfaceBuilderBaseConfiguration.CODEC);
	// TODO Can we move off of this and use surface features instead?
	public static final TFHighlandsSurfaceBuilder HIGHLANDS = new TFHighlandsSurfaceBuilder(SurfaceBuilderBaseConfiguration.CODEC);

	static {
		// setRegistryName for some dumb reason erases the type for SurfaceBuilder<> and we have to keep the type for later in the line, so the reg names are kept here instead
		HIGHLANDS.setRegistryName(TwilightForestMod.ID, "highlands");
		DEADROCK_FILLING.setRegistryName(TwilightForestMod.ID, "plateau");
	}

	@SubscribeEvent
	public static void register(RegistryEvent.Register<SurfaceBuilder<?>> evt) {
		evt.getRegistry().register(HIGHLANDS);
		evt.getRegistry().register(DEADROCK_FILLING);
	}
}
