package twilightforest.world.registration;

import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import twilightforest.TwilightForestMod;
import twilightforest.world.components.surfacebuilders.FillingSurfaceBuilder;
import twilightforest.world.components.surfacebuilders.GlacierSurfaceBuilder;
import twilightforest.world.components.surfacebuilders.HighlandsSurfaceBuilder;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TwilightSurfaceBuilders {
	// Biomes are registered before surface builders and need the raw objects. So don't use DeferredRegister here.
	public static final FillingSurfaceBuilder DEADROCK_FILLING = new FillingSurfaceBuilder(FillingSurfaceBuilder.FillingSurfaceBuilderConfig.CODEC);
	public static final GlacierSurfaceBuilder GLACIER = new GlacierSurfaceBuilder(GlacierSurfaceBuilder.GlacierSurfaceConfig.CODEC);

	// TODO Can we move off of this and use surface features instead?
	public static final HighlandsSurfaceBuilder HIGHLANDS = new HighlandsSurfaceBuilder(SurfaceBuilderBaseConfiguration.CODEC);

	static {
		// setRegistryName for some dumb reason erases the type for SurfaceBuilder<> and we have to keep the type for later in the line, so the reg names are kept here instead
		DEADROCK_FILLING.setRegistryName(TwilightForestMod.ID, "plateau");
		GLACIER.setRegistryName(TwilightForestMod.ID, "glacier");
		HIGHLANDS.setRegistryName(TwilightForestMod.ID, "highlands");
	}

	@SubscribeEvent
	public static void register(RegistryEvent.Register<SurfaceBuilder<?>> evt) {
		evt.getRegistry().register(DEADROCK_FILLING);
		evt.getRegistry().register(GLACIER);
		evt.getRegistry().register(HIGHLANDS);
	}
}
