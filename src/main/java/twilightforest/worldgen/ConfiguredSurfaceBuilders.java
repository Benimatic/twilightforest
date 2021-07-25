package twilightforest.worldgen;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;
import twilightforest.TwilightForestMod;
import twilightforest.world.surfacebuilders.TFSurfaceBuilders;

public class ConfiguredSurfaceBuilders {
	public static final SurfaceBuilderBaseConfiguration DEADROCK_CONFIG = new SurfaceBuilderBaseConfiguration(BlockConstants.WEATHERED_DEADROCK, BlockConstants.CRACKED_DEADROCK, BlockConstants.DEADROCK);
	public static final SurfaceBuilderBaseConfiguration HIGHLANDS_CONFIG = new SurfaceBuilderBaseConfiguration(BlockConstants.PODZOL, BlockConstants.COARSE_DIRT, BlockConstants.GRASS_BLOCK);
	public static final SurfaceBuilderBaseConfiguration SNOW_CONFIG = new SurfaceBuilderBaseConfiguration(BlockConstants.SNOW, BlockConstants.SNOW, BlockConstants.PACKED_ICE);

	public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> CONFIGURED_TF_DEFAULT = TFSurfaceBuilders.DEFAULT_TF.configured(SurfaceBuilder.CONFIG_OCEAN_SAND);
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> CONFIGURED_HIGHLANDS = TFSurfaceBuilders.HIGHLANDS.configured(HIGHLANDS_CONFIG);
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> CONFIGURED_PLATEAU = TFSurfaceBuilders.PLATEAU.configured(DEADROCK_CONFIG);
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> CONFIGURED_SNOW = SurfaceBuilder.DEFAULT.configured(SNOW_CONFIG);

	public static void registerConfigurations(Registry<ConfiguredSurfaceBuilder<?>> registry) {
		Registry.register(registry, TwilightForestMod.prefix("coarse_podzol_highlands"), ConfiguredSurfaceBuilders.CONFIGURED_HIGHLANDS);
		Registry.register(registry, TwilightForestMod.prefix("deadrock_plateau"), ConfiguredSurfaceBuilders.CONFIGURED_PLATEAU);
	}
}