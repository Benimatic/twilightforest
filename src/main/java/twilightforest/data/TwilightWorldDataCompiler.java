package twilightforest.data;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.MappedRegistry;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FuzzyOffsetBiomeZoomer;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.NoiseSamplingSettings;
import net.minecraft.world.level.levelgen.NoiseSlideSettings;
import twilightforest.TwilightForestMod;
import twilightforest.world.*;
import twilightforest.worldgen.ConfiguredSurfaceBuilders;
import twilightforest.worldgen.biomes.BiomeMaker;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;

public class TwilightWorldDataCompiler extends WorldDataCompilerAndOps<JsonElement> {
	public TwilightWorldDataCompiler(DataGenerator generator) {
		super(generator, JsonOps.INSTANCE, GSON::toJson, new RegistryAccess.RegistryHolder());
	}

	@Override
	public void generate(HashCache directoryCache) {
		ConfiguredSurfaceBuilders.registerConfigurations(dynamicRegistries.registryOrThrow(Registry.CONFIGURED_SURFACE_BUILDER_REGISTRY));

		getBiomes().forEach((rl, biome) -> serialize(Registry.BIOME_REGISTRY, rl, biome, Biome.DIRECT_CODEC));
		getDimensions().forEach((rl, dimension) -> serialize(Registry.LEVEL_STEM_REGISTRY, rl, dimension, LevelStem.CODEC));
	}

	private Map<ResourceLocation, LevelStem> getDimensions() {
		Optional<NoiseGeneratorSettings> forestDimensionSettings = makeDimensionSettings(
				new StructureSettings(Optional.empty(), ImmutableMap.of()),
				NoiseSettings.create(
						0,
						128, // Noise Height - This allows us to shorten the world so we can cram more stuff upwards
						new NoiseSamplingSettings(0.9999999814507745D, 0.9999999814507745D, 80.0D, 160.0D),
						new NoiseSlideSettings(-10, 3, 0),
						new NoiseSlideSettings(-30, 0, 0),
						1,
						2,
						1.0D,
						-0.46875D,
						false,
						true,
						false,
						false
				),
				Blocks.STONE.defaultBlockState(),
				Blocks.WATER.defaultBlockState(),
				-20,
				0,
				31,
				0,
				false,
				true,
				true,
				true,
				true,
				true
		);

		// Problem island at /tp 9389.60 90.00 11041.66
		Optional<NoiseGeneratorSettings> skyDimensionSettings = makeDimensionSettings(
				new StructureSettings(Optional.empty(), ImmutableMap.of()),
				// https://misode.github.io/worldgen/noise-settings/
				// So far this looks great! We just need to raise the island levels to sea level. Otherwise is generates flat-flakey islands that really show the roots on dirt bottoms from trees
				NoiseSettings.create(
						0, //min height
						128, // height
						new NoiseSamplingSettings(3.0D, 1.0D, 256.0D, 16.0D), // sampling
						new NoiseSlideSettings(-3000, 92, -66), // top_slide
						new NoiseSlideSettings(-30, 7, 1), // bottom_slide
						2, // size_horizontal
						1, // size_vertical
						0.3D, // density_factor
						-0.2D, // density_offset
						true, // simplex_surface_noise
						false, // random_density_offset
						false, // island_noise_override
						false  // amplified
				),
				Blocks.STONE.defaultBlockState(),
				Blocks.WATER.defaultBlockState(),
				-20,
				-20,
				0,
				0,
				false,
				true,
				true,
				true,
				true,
				true
		);

		// Register the dimension noise settings in the local datagen registry.
		getOrCreateInRegistry(dynamicRegistries.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY), ResourceKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, TwilightForestMod.prefix("forest_noise_config")), forestDimensionSettings::get);
		getOrCreateInRegistry(dynamicRegistries.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY), ResourceKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, TwilightForestMod.prefix("sky_noise_config")), skyDimensionSettings::get);

		TFDimensions.init();
		//FIXME: The issue with generated files using 0 as the seed is here. We need to somehow just...not have it here?
		ChunkGeneratorTwilightBase forestChunkGen = new ChunkGeneratorTwilightForest(new TFBiomeProvider(0L, new MappedRegistry<>(Registry.BIOME_REGISTRY, Lifecycle.experimental())), 0L, forestDimensionSettings::get);
		NoiseBasedChunkGenerator skyChunkGen = new NoiseBasedChunkGenerator(new TFBiomeProvider(0L, new MappedRegistry<>(Registry.BIOME_REGISTRY, Lifecycle.experimental())), 0L, skyDimensionSettings::get);
		//NoiseChunkGenerator skyChunkGen = new NoiseChunkGenerator(new TFBiomeProvider(0L, new SimpleRegistry<>(Registry.BIOME_KEY, Lifecycle.experimental())), 4L, () -> WorldGenRegistries.NOISE_SETTINGS.getValueForKey(RegistryKey.getOrCreateKey(Registry.NOISE_SETTINGS_KEY, new ResourceLocation("floating_islands"))));
		//NoiseChunkGenerator skyChunkGen = new NoiseChunkGenerator(new CheckerboardBiomeProvider(BiomeMaker.BIOMES.values().stream().sorted((o1, o2) -> Float.compare(o1.getDepth(), o2.getDepth())).map(b -> (Supplier<Biome>) () -> b).collect(Collectors.toList()), 2), 4L, skyDimensionSettings::get);

		final DimensionType twilightType = DimensionType.create(
				OptionalLong.of(13000L), // TODO Kill the celestial bodies
				true,
				false,
				false,
				true,
				0.125D,
				false,
				false,
				true,
				true,
				false,
				0, // Minimum Height
				256, // Max Height
				256, // Logical Height
				FuzzyOffsetBiomeZoomer.INSTANCE,
				new ResourceLocation("infiniburn_overworld"),
				TwilightForestMod.prefix("renderer"), // DimensionRenderInfo
				0f // Wish this could be set to -0.05 since it'll make the world truly blacked out if an area is not sky-lit (see: Dark Forests) Sadly this also messes up night vision so it gets 0
		);

		// Register the type in the local datagen registry. Hacky.
		getOrCreateInRegistry(dynamicRegistries.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY), ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation(TwilightForestMod.ID, "forest_type")), () -> twilightType);

		return ImmutableMap.of(
				TwilightForestMod.prefix("twilight_forest"), new LevelStem(() -> twilightType, forestChunkGen),
				TwilightForestMod.prefix("skylight_forest"), new LevelStem(() -> twilightType, skyChunkGen)
				// TODO add *actual* twilightforest:void world without islands
		);
	}

	private Map<ResourceLocation, Biome> getBiomes() {
		return BiomeMaker
				.BIOMES
				.entrySet()
				.stream()
				.peek(registryKeyBiomeEntry -> registryKeyBiomeEntry.getValue().setRegistryName(registryKeyBiomeEntry.getKey().location()))
				.collect(Collectors.toMap(entry -> entry.getKey().location(), Map.Entry::getValue));
	}
}
