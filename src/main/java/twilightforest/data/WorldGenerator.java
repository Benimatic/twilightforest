package twilightforest.data;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import twilightforest.TwilightForestMod;
import twilightforest.init.*;
import twilightforest.world.components.biomesources.TFBiomeProvider;
import twilightforest.world.components.chunkgenerators.ChunkGeneratorTwilight;
import twilightforest.world.registration.biomes.BiomeMaker;

import java.util.Map;
import java.util.Optional;

public class WorldGenerator extends DatapackBuiltinEntriesProvider {

	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, TFConfiguredFeatures::bootstrap)
			.add(Registries.PLACED_FEATURE, TFPlacedFeatures::bootstrap)
			.add(Registries.STRUCTURE, TFStructures::bootstrap)
			.add(Registries.STRUCTURE_SET, TFStructureSets::bootstrap)
			.add(Registries.CONFIGURED_CARVER, TFCaveCarvers::bootstrap)
			.add(Registries.NOISE_SETTINGS, TFDimensionSettings::bootstrapNoise)
			.add(Registries.DIMENSION_TYPE, TFDimensionSettings::bootstrapType)
			.add(Registries.BIOME, TFBiomes::bootstrap);

	public WorldGenerator(PackOutput output) {
		super(output, WorldGenerator::createLookup);
	}

	public static HolderLookup.Provider createLookup() {
		return BUILDER.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), VanillaRegistries.createLookup());
	}

	public static DataProvider createLevelStem(PackOutput output, ExistingFileHelper helper) {
		HolderLookup.Provider registry = createLookup();
		RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, registry);
		HolderGetter<Biome> biomeRegistry = registry.lookupOrThrow(Registries.BIOME);
		HolderGetter<DimensionType> dimTypes = registry.lookupOrThrow(Registries.DIMENSION_TYPE);
		HolderGetter<NoiseGeneratorSettings> noiseGenSettings = registry.lookupOrThrow(Registries.NOISE_SETTINGS);

		NoiseBasedChunkGenerator wrappedChunkGenerator = new NoiseBasedChunkGenerator(
				new TFBiomeProvider(
						0L,
						biomeRegistry,
						BiomeMaker.makeBiomeList(biomeRegistry, biomeRegistry.getOrThrow(TFBiomes.UNDERGROUND)),
						-1.25F,
						2.5F),
				noiseGenSettings.getOrThrow(TFDimensionSettings.TWILIGHT_NOISE_GEN));

		LevelStem stem = new LevelStem(
				dimTypes.getOrThrow(TFDimensionSettings.TWILIGHT_DIM_TYPE),
				new ChunkGeneratorTwilight(
						wrappedChunkGenerator,
						noiseGenSettings.getOrThrow(TFDimensionSettings.TWILIGHT_NOISE_GEN),
						true,
						Optional.of(19),
						BiomeMaker.BIOME_FEATURES_SETS));

		return new JsonCodecProvider<>(output, helper, TwilightForestMod.ID, ops, PackType.SERVER_DATA, Registries.LEVEL_STEM.location().getPath(), LevelStem.CODEC, Map.of(TFDimensionSettings.TWILIGHT_LEVEL_STEM.location(), stem));
	}
}
