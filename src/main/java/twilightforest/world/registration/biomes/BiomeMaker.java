package twilightforest.world.registration.biomes;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectSortedMap;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import twilightforest.init.BiomeKeys;
import twilightforest.world.components.BiomeGrassColors;
import twilightforest.world.components.chunkgenerators.warp.TerrainColumn;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class BiomeMaker extends BiomeHelper {

	public static List<TerrainColumn> makeBiomeList(HolderLookup.RegistryLookup<Biome> biomeRegistry, Holder<Biome> undergroundBiome) {
		return List.of(
				biomeColumnWithUnderground(0.025F, 0.05F, biomeRegistry, BiomeKeys.FOREST, undergroundBiome),
				biomeColumnWithUnderground(0.1F, 0.2F, biomeRegistry, BiomeKeys.DENSE_FOREST, undergroundBiome),
				biomeColumnWithUnderground(0.0625F, 0.05F, biomeRegistry, BiomeKeys.FIREFLY_FOREST, undergroundBiome),
				biomeColumnWithUnderground(0.005F, 0.005F, biomeRegistry, BiomeKeys.CLEARING, undergroundBiome),
				biomeColumnWithUnderground(0.05F, 0.1F, biomeRegistry, BiomeKeys.OAK_SAVANNAH, undergroundBiome),
				biomeColumnWithUnderground(-1.65F, 0.25F, biomeRegistry, BiomeKeys.STREAM, undergroundBiome),
				biomeColumnWithUnderground(-1.97F, 0.0F, biomeRegistry, BiomeKeys.LAKE, undergroundBiome),

				biomeColumnWithUnderground(0.025F, 0.05F, biomeRegistry, BiomeKeys.MUSHROOM_FOREST, undergroundBiome),
				biomeColumnWithUnderground(0.05F, 0.05F, biomeRegistry, BiomeKeys.DENSE_MUSHROOM_FOREST, undergroundBiome),

				biomeColumnWithUnderground(0.025F, 0.05F, biomeRegistry, BiomeKeys.ENCHANTED_FOREST, undergroundBiome),
				biomeColumnWithUnderground(0.025F, 0.05F, biomeRegistry, BiomeKeys.SPOOKY_FOREST, undergroundBiome),

				biomeColumnWithUnderground(-0.9F, 0.15F, biomeRegistry, BiomeKeys.SWAMP, undergroundBiome),
				biomeColumnWithUnderground(-0.2F, 0.05F, biomeRegistry, BiomeKeys.FIRE_SWAMP, undergroundBiome),

				biomeColumnWithUnderground(0.025F, 0.005F, biomeRegistry, BiomeKeys.DARK_FOREST, undergroundBiome),
				biomeColumnWithUnderground(0.025F, 0.005F, biomeRegistry, BiomeKeys.DARK_FOREST_CENTER, undergroundBiome),

				biomeColumnWithUnderground(0.05F, 0.15F, biomeRegistry, BiomeKeys.SNOWY_FOREST, undergroundBiome),
				biomeColumnWithUnderground(0.025F, 0.05F, biomeRegistry, BiomeKeys.GLACIER, undergroundBiome),

				biomeColumnWithUnderground(3.0F, 0.25F, biomeRegistry, BiomeKeys.HIGHLANDS, undergroundBiome),
				biomeColumnToBedrock(7.0F, 0.1F, biomeRegistry, BiomeKeys.THORNLANDS),
				biomeColumnToBedrock(13.75F, 0.025F, biomeRegistry, BiomeKeys.FINAL_PLATEAU)
		);
	}

	private static TerrainColumn biomeColumnWithUnderground(float noiseDepth, float noiseScale, HolderLookup.RegistryLookup<Biome> biomeRegistry, ResourceKey<Biome> key, Holder<Biome> undergroundBiome) {
		Holder.Reference<Biome> biomeHolder = Holder.Reference.createStandAlone(biomeRegistry, key);

		biomeHolder.bindKey(key);

		return makeColumn(noiseDepth, noiseScale, biomeHolder, treeMap -> {
			// This will put the transition boundary around Y-8
			treeMap.put(Math.min(noiseDepth - 1, -1), biomeHolder);
			treeMap.put(Math.min(noiseDepth - 3, -3), undergroundBiome);
		});
	}

	private static TerrainColumn biomeColumnToBedrock(float noiseDepth, float noiseScale, HolderLookup.RegistryLookup<Biome> biomeRegistry, ResourceKey<Biome> key) {
		Holder.Reference<Biome> biomeHolder = Holder.Reference.createStandAlone(biomeRegistry, key);

		biomeHolder.bindKey(key);

		return makeColumn(noiseDepth, noiseScale, biomeHolder, treeMap -> treeMap.put(0, biomeHolder));
	}

	private static TerrainColumn makeColumn(float noiseDepth, float noiseScale, Holder<Biome> biomeHolder, Consumer<Float2ObjectSortedMap<Holder<Biome>>> layerBuilder) {
		return new TerrainColumn(biomeHolder, Util.make(new Float2ObjectAVLTreeMap<>(), layerBuilder), noiseDepth, noiseScale);
	}

	public static Holder<Biome> registerUnderground(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter, Registry<Biome> registry, boolean fullRegister) {
		Holder.Reference<Biome> holder = Holder.Reference.createStandAlone(registry.asLookup(), BiomeKeys.UNDERGROUND);
		Biome underground = biomeWithDefaults(
				defaultAmbientBuilder(),
				undergroundMobSpawning(),
				undergroundGen(featureGetter, carverGetter)
		)
				.temperature(0.7F)
				.downfall(0.0F)
				.build();

		if (fullRegister)
			Registry.register(registry, BiomeKeys.UNDERGROUND, underground);

		holder.bindKey(BiomeKeys.UNDERGROUND);

		return holder;
	}

	public static Map<ResourceKey<Biome>, Biome> generateBiomes(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
		final ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes = ImmutableMap.builder();

		commonBiomes(featureGetter, carverGetter, biomes);
		mushroomBiomes(featureGetter, carverGetter, biomes);
		rareBiomes(featureGetter, carverGetter, biomes);
		swampBiomes(featureGetter, carverGetter, biomes);
		darkForestBiomes(featureGetter, carverGetter, biomes);
		snowRegionBiomes(featureGetter, carverGetter, biomes);
		highlandsBiomes(featureGetter, carverGetter, biomes);

		return biomes.build();
	}

	private static void commonBiomes(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter, ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes) {
		biomes.put(BiomeKeys.FOREST,
				biomeWithDefaults(
						fireflyParticles(defaultAmbientBuilder()),
						defaultMobSpawning(),
						twilightForestGen(featureGetter, carverGetter)
				)
						.build()
		);

		biomes.put(BiomeKeys.DENSE_FOREST,
				biomeWithDefaults(
						fireflyParticles(defaultAmbientBuilder())
								.waterColor(0x005522),
						defaultMobSpawning(),
						denseForestGen(featureGetter, carverGetter)
				)
						.temperature(0.7F)
						.downfall(0.8F)
						.build()
		);

		biomes.put(BiomeKeys.FIREFLY_FOREST,
				biomeWithDefaults(
						fireflyForestParticles(defaultAmbientBuilder()),
						defaultMobSpawning(),
						fireflyForestGen(featureGetter, carverGetter)
				)
						.temperature(0.5F)
						.downfall(1)
						.build()
		);

		biomes.put(BiomeKeys.CLEARING,
				biomeWithDefaults(
						defaultAmbientBuilder(),
						defaultMobSpawning(),
						clearingGen(featureGetter, carverGetter)
				)
						.temperature(0.8F)
						.downfall(0.4F)
						.build()
		);

		biomes.put(BiomeKeys.OAK_SAVANNAH,
				biomeWithDefaults(
						defaultAmbientBuilder(),
						defaultMobSpawning(),
						oakSavannaGen(featureGetter, carverGetter)
				)
						.temperature(0.9F)
						.downfall(0)
						.build()
		);
	}

	private static void mushroomBiomes(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter, ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes) {
		biomes.put(BiomeKeys.MUSHROOM_FOREST,
				biomeWithDefaults(
						fireflyParticles(defaultAmbientBuilder()),
						defaultMobSpawning(),
						mushroomForestGen(featureGetter, carverGetter)
				)
						.temperature(0.8F)
						.downfall(0.8F)
						.build()
		);

		biomes.put(BiomeKeys.DENSE_MUSHROOM_FOREST,
				biomeWithDefaults(
						fireflyParticles(defaultAmbientBuilder()),
						defaultMobSpawning(),
						denseMushroomForestGen(featureGetter, carverGetter)
				)
						.temperature(0.8F)
						.downfall(1)
						.build()
		);
	}

	private static void rareBiomes(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter, ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes) {
		biomes.put(BiomeKeys.SPOOKY_FOREST,
				biomeWithDefaults(
						defaultAmbientBuilder()
								.grassColorOverride(0xC45123)
								.foliageColorOverride(0xFF8501)
								.waterColor(0xBC8857)
								.grassColorModifier(BiomeGrassColors.SPOOKY_FOREST),
						spookSpawning(),
						spookyForestGen(featureGetter, carverGetter)
				)
						.temperature(0.5F)
						.downfall(1)
						.build()
		);

		biomes.put(BiomeKeys.ENCHANTED_FOREST,
				biomeWithDefaults(fireflyParticles(
								defaultAmbientBuilder())
								.foliageColorOverride(0x00FFFF)
								.grassColorOverride(0x00FFFF)
								.grassColorModifier(BiomeGrassColors.ENCHANTED_FOREST),
						defaultMobSpawning(),
						enchantedForestGen(featureGetter, carverGetter)
				)
						.precipitation(Biome.Precipitation.NONE)
						.build()
		);

		biomes.put(BiomeKeys.STREAM,
				biomeWithDefaults(
						defaultAmbientBuilder(),
						defaultMobSpawning(),
						streamsAndLakes(featureGetter, carverGetter, false))
						.temperature(0.5F)
						.downfall(0.1F)
						.build()
		);

		biomes.put(BiomeKeys.LAKE,
				biomeWithDefaults(
						defaultAmbientBuilder(),
						defaultMobSpawning(),
						streamsAndLakes(featureGetter, carverGetter, true)
				)
						.temperature(0.66F)
						.downfall(1)
						.build()
		);
	}

	private static void swampBiomes(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter, ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes) {
		biomes.put(BiomeKeys.SWAMP,
				biomeWithDefaults(
						defaultAmbientBuilder()
								.skyColor(0x002112)
								.fogColor(0x003F21)
								.grassColorOverride(0x5C694E)
								.foliageColorOverride(0x496137)
								.waterColor(0x95B55F)
								.grassColorModifier(BiomeGrassColors.SWAMP),
						swampSpawning(),
						swampGen(featureGetter, carverGetter)
				)
						.temperature(0.8F)
						.downfall(0.9F)
						.build()
		);

		biomes.put(BiomeKeys.FIRE_SWAMP,
				biomeWithDefaults(
						whiteAshParticles(defaultAmbientBuilder())
								.waterColor(0x2D0700)
								.fogColor(0x380A00)
								.grassColorOverride(0x572E23)
								.foliageColorOverride(0x64260F)
								.waterColor(0x6C2C2C),
						new MobSpawnSettings.Builder(),
						fireSwampGen(featureGetter, carverGetter)
				)
						.precipitation(Biome.Precipitation.NONE)
						.temperature(1)
						.downfall(0.4F)
						.build()
		);
	}

	private static void darkForestBiomes(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter, ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes) {
		biomes.put(BiomeKeys.DARK_FOREST,
				biomeWithDefaults(defaultAmbientBuilder()
								.skyColor(0x000000)
								.fogColor(0x000000)
								.grassColorOverride(0x4B6754).
								foliageColorOverride(0x3B5E3F)
								.grassColorModifier(BiomeGrassColors.DARK_FOREST),
						darkForestSpawning(),
						darkForestGen(featureGetter, carverGetter)
				)
						.temperature(0.7F)
						.downfall(0.8F)
						.build()
		);

		biomes.put(BiomeKeys.DARK_FOREST_CENTER,
				biomeWithDefaults(
						defaultAmbientBuilder()
								.skyColor(0x000000)
								.fogColor(0x493000)
								.grassColorOverride(0x667540)
								.foliageColorOverride(0xF9821E)
								.grassColorModifier(BiomeGrassColors.DARK_FOREST_CENTER),
						new MobSpawnSettings.Builder(),
						darkForestCenterGen(featureGetter, carverGetter)
				)
						.build()
		);
	}

	private static void snowRegionBiomes(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter, ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes) {
		biomes.put(BiomeKeys.SNOWY_FOREST,
				biomeWithDefaults(
						defaultAmbientBuilder()
								.skyColor(0x808080)
								.fogColor(0xFFFFFF)
								.foliageColorOverride(0xFFFFFF)
								.grassColorOverride(0xFFFFFF),
						snowForestSpawning(),
						snowyForestGen(featureGetter, carverGetter)
				)
						.precipitation(Biome.Precipitation.SNOW)
						.temperature(0.09F)
						.downfall(0.9F)
						.build()
		);

		biomes.put(BiomeKeys.GLACIER,
				biomeWithDefaults(
						defaultAmbientBuilder()
								.skyColor(0x130D28)
								.fogColor(0x361F88),
						penguinSpawning(),
						glacierGen(featureGetter, carverGetter)
				)
						.temperature(0.8F)
						.downfall(0.1F)
						.precipitation(Biome.Precipitation.SNOW)
						.build()
		);
	}

	private static void highlandsBiomes(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter, ImmutableMap.Builder<ResourceKey<Biome>, Biome> biomes) {
		biomes.put(BiomeKeys.HIGHLANDS,
				biomeWithDefaults(
						defaultAmbientBuilder(),
						defaultMobSpawning(),
						highlandsGen(featureGetter, carverGetter)
				)
						.temperature(0.4F)
						.downfall(0.7F)
						.build()
		);

		biomes.put(BiomeKeys.THORNLANDS,
				biomeWithDefaults(
						defaultAmbientBuilder(),
						new MobSpawnSettings.Builder(),
						thornlandsGen(featureGetter, carverGetter)
				)
						.temperature(0.3F)
						.downfall(0.2F)
						.build()
		);

		biomes.put(BiomeKeys.FINAL_PLATEAU,
				biomeWithDefaults(
						defaultAmbientBuilder(),
						ravenSpawning(),
						new BiomeGenerationSettings.Builder(featureGetter, carverGetter)
				)
						.temperature(1.0F)
						.downfall(0.2F)
						.build()
		);
	}
}
