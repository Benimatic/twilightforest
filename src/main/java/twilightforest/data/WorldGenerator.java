package twilightforest.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import org.slf4j.Logger;
import twilightforest.TwilightForestMod;
import twilightforest.world.components.biomesources.TFBiomeProvider;
import twilightforest.world.components.chunkgenerators.ChunkGeneratorTwilight;
import twilightforest.world.registration.TFNoiseGenerationSettings;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;

public record WorldGenerator(DataGenerator generator) implements DataProvider {

	private static final Logger LOGGER = LogUtils.getLogger();
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public void run(HashCache cache) {
		Path path = this.generator.getOutputFolder();
		RegistryAccess registryaccess = RegistryAccess.BUILTIN.get();
		DynamicOps<JsonElement> dynamicops = RegistryOps.create(JsonOps.INSTANCE, registryaccess);

		Registry<LevelStem> twilight = this.registerTFSettings(registryaccess);
		//Registry<LevelStem> skylight = this.registerSkylightSettings(registryaccess);
		//TODO void world

		RegistryAccess.knownRegistries().forEach((data) ->
				dumpRegistryCap(cache, path, registryaccess, dynamicops, data));

		dumpRegistry(path, cache, dynamicops, Registry.LEVEL_STEM_REGISTRY, twilight, LevelStem.CODEC);
	}

	private static <T> void dumpRegistryCap(HashCache cache, Path path, RegistryAccess access, DynamicOps<JsonElement> ops, RegistryAccess.RegistryData<T> data) {
		dumpRegistry(path, cache, ops, data.key(), access.ownedRegistryOrThrow(data.key()), data.codec());
	}

	private Registry<LevelStem> registerTFSettings(RegistryAccess access) {
		WritableRegistry<LevelStem> writableregistry = new MappedRegistry<>(Registry.LEVEL_STEM_REGISTRY, Lifecycle.experimental(), null);

		NoiseBasedChunkGenerator forestChunkGen =
				new NoiseBasedChunkGenerator(
						access.registryOrThrow(Registry.STRUCTURE_SET_REGISTRY),
						access.registryOrThrow(Registry.NOISE_REGISTRY),
						new TFBiomeProvider(0L, new MappedRegistry<>(Registry.BIOME_REGISTRY, Lifecycle.experimental(), null)),
						0L,
						access.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY).getOrCreateHolder(TFNoiseGenerationSettings.TWILIGHT_NOISE_GEN.getKey())
				);

		writableregistry.register(ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, TwilightForestMod.prefix("twilightforest")), new LevelStem(Holder.direct(this.twilightDimType()), new ChunkGeneratorTwilight(forestChunkGen, access.registryOrThrow(Registry.STRUCTURE_SET_REGISTRY), true, true, Optional.of(12), true)), Lifecycle.experimental());
		return writableregistry;
	}

	private Registry<LevelStem> registerSkylightSettings(RegistryAccess access) {
		WritableRegistry<LevelStem> writableregistry = new MappedRegistry<>(Registry.LEVEL_STEM_REGISTRY, Lifecycle.experimental(), null);

		NoiseBasedChunkGenerator forestChunkGen =
				new NoiseBasedChunkGenerator(
						access.registryOrThrow(Registry.STRUCTURE_SET_REGISTRY),
						access.registryOrThrow(Registry.NOISE_REGISTRY),
						new TFBiomeProvider(0L, new MappedRegistry<>(Registry.BIOME_REGISTRY, Lifecycle.experimental(), null)),
						4L, //drull had it set like this so its staying until he changes it
						access.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY).getOrCreateHolder(TFNoiseGenerationSettings.SKYLIGHT_NOISE_GEN.getKey())
				);

		writableregistry.register(ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, TwilightForestMod.prefix("skylight_forest")), new LevelStem(Holder.direct(this.twilightDimType()), new ChunkGeneratorTwilight(forestChunkGen, access.registryOrThrow(Registry.STRUCTURE_SET_REGISTRY), true, true, Optional.of(12), true)), Lifecycle.stable());
		return writableregistry;
	}

	private static <E, T extends Registry<E>> void dumpRegistry(Path path, HashCache cache, DynamicOps<JsonElement> ops, ResourceKey<? extends T> key, T registry, Encoder<E> encoder) {
		for (Map.Entry<ResourceKey<E>, E> entry : registry.entrySet()) {
			if (entry.getKey().location().getNamespace().equals(TwilightForestMod.ID)) {
				Path otherPath = createPath(path, key.location(), entry.getKey().location());
				dumpValue(otherPath, cache, ops, encoder, entry.getValue());
			}
		}

	}

	private static <E> void dumpValue(Path path, HashCache cache, DynamicOps<JsonElement> ops, Encoder<E> encoder, E entry) {
		try {
			Optional<JsonElement> optional = encoder.encodeStart(ops, entry).resultOrPartial((p_206405_) -> {
				LOGGER.error("Couldn't serialize element {}: {}", path, p_206405_);
			});
			if (optional.isPresent()) {
				DataProvider.save(GSON, cache, optional.get(), path);
			}
		} catch (IOException ioexception) {
			LOGGER.error("Couldn't save element {}", path, ioexception);
		}

	}

	private static Path createPath(Path path, ResourceLocation registry, ResourceLocation entry) {
		return path.resolve("data").resolve(entry.getNamespace()).resolve(registry.getPath()).resolve(entry.getPath() + ".json");
	}

	public String getName() {
		return "Worldgen";
	}

	private DimensionType twilightDimType() {
		return DimensionType.create(
				OptionalLong.of(13000L), //fixed time TODO Kill the celestial bodies
				true, //skylight
				false, //ceiling
				false, //ultrawarm
				true, //natural
				0.125D, //coordinate scale
				false, //dragon fight
				false, //piglin safe
				true, //bed works
				true, //respawn anchor works
				false, //raids
				-32, // Minimum Y Level
				32 + 256, // Height + Min Y = Max Y
				32 + 256, // Logical Height
				BlockTags.INFINIBURN_OVERWORLD, //infiburn
				TwilightForestMod.prefix("renderer"), // DimensionRenderInfo
				0f // Wish this could be set to -0.05 since it'll make the world truly blacked out if an area is not sky-lit (see: Dark Forests) Sadly this also messes up night vision so it gets 0
		);
	}
}