package twilightforest.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.RegistryWriteOps;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.biome.BiomeZoomer;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraftforge.registries.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import twilightforest.TwilightForestMod;

import javax.annotation.Nullable;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class WorldDataCompilerAndOps<Format> extends RegistryWriteOps<Format> implements DataProvider {
    protected static final Logger LOGGER = LogManager.getLogger();
    protected static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create(); // Todo registerTypeAdapter for custom printing
    protected final DataGenerator generator;
    private final Function<Format, String> fileContentWriter;
    protected final RegistryAccess dynamicRegistries;

    private HashCache directoryCache;

    public WorldDataCompilerAndOps(DataGenerator generator, DynamicOps<Format> ops, Function<Format, String> fileContentWriter, RegistryAccess dynamicRegistries) {
        super(ops, dynamicRegistries);
        this.generator = generator;
        this.fileContentWriter = fileContentWriter;
        this.dynamicRegistries = dynamicRegistries;
    }

    protected <Resource> Resource getOrCreateInRegistry(Registry<Resource> registry, ResourceKey<Resource> registryKey, Supplier<Resource> resourceCreator) {
        Resource resourceSaved = getFromVanillaRegistryIllegally(registry, registryKey);

        if (resourceSaved == null) {
            //SimpleRegistry<Resource> simpleRegistry = new SimpleRegistry<>(registryKey, Lifecycle.experimental());

            resourceSaved = Registry.register(registry, registryKey.location(), resourceCreator.get());
        }

        return resourceSaved;
    }

    @Override
    public final void run(final HashCache directoryCache) {
        this.directoryCache = directoryCache;

        generate(directoryCache);
    }

    public abstract void generate(final HashCache directoryCache);

    private final HashSet<Object> objectsSerializationCache = new HashSet<>();

    public <Resource> void serialize(ResourceKey<? extends Registry<Resource>> resourceType, ResourceLocation resourceLocation, Resource resource, Encoder<Resource> encoder) {
        if (objectsSerializationCache.contains(resource)) {
            LOGGER.debug("Avoiding duplicate serialization with " + resourceLocation);

            return;
        }

        objectsSerializationCache.add(resource);

        Optional<Format> output = this
                .withEncoder(encoder)
                .apply(resource)
                .setLifecycle(Lifecycle.experimental())
                .resultOrPartial(error -> LOGGER.error("Object [" + resourceType.getRegistryName() + "] " + resourceLocation + " not serialized within recursive serialization: " + error));

        if (output.isPresent()) {
            try {
                save(directoryCache, output.get(), makePath(generator.getOutputFolder(), resourceType, resourceLocation));
            } catch (IOException e) {
                LOGGER.error("Could not save resource `" + resourceLocation + "` (Resource Type `" + resourceType.location() + "`)", e);
            }
        }
    }

    private static Path makePath(Path path, ResourceKey<?> key, ResourceLocation resc) {
        return path.resolve("data").resolve(resc.getNamespace()).resolve(key.location().getPath()).resolve(resc.getPath() + ".json");
    }

    /** VanillaCopy: IDataProvider.save */
    @SuppressWarnings("UnstableApiUsage") // Mojang uses HASH_FUNCTION as well, hence the warning suppression
    private void save(HashCache cache, Format dynamic, Path pathIn) throws IOException {
        String s = fileContentWriter.apply(dynamic);
        String s1 = SHA1.hashUnencodedChars(s).toString();
        if (!Objects.equals(cache.getHash(pathIn), s1) || !Files.exists(pathIn)) {
            Files.createDirectories(pathIn.getParent());

            try (BufferedWriter bufferedwriter = Files.newBufferedWriter(pathIn)) {
                bufferedwriter.write(s);
            }
        }

        cache.putNew(pathIn, s1);
    }

    @SuppressWarnings({"unchecked", "rawtypes", "SameParameterValue"})
    @Nullable
    protected static <T> T getFromVanillaRegistryIllegally(Registry registry, ResourceKey<T> key) {
        return (T) registry.get(key);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <Resource> Optional<ResourceLocation> getFromForgeRegistryIllegally(ResourceKey<? extends Registry<Resource>> registryKey, Resource resource) {
        if (resource instanceof IForgeRegistryEntry) {
            IForgeRegistryEntry<Resource> entry = (IForgeRegistryEntry<Resource>) resource;
            ResourceLocation location = entry.getRegistryName();

            if (location != null) {
                return Optional.of(location);
            }

            // This is safe because we've tested IForgeRegistry, but the type-checker is too stupid to recognize it as such
            IForgeRegistry forgeRegistry = RegistryManager.ACTIVE.getRegistry(registryKey.location());
            return Optional.ofNullable(forgeRegistry.getKey((IForgeRegistryEntry) resource));
        }

        return Optional.empty();
    }

    private <Resource> Optional<ResourceLocation> rummageForResourceLocation(Resource resource, ResourceKey<? extends Registry<Resource>> registryKey) {
        Optional<ResourceLocation> instanceKey = Optional.empty();

        // Ask the object itself if it has a key first
        if (resource instanceof IForgeRegistryEntry) {
            instanceKey = Optional.ofNullable(((IForgeRegistryEntry<?>) resource).getRegistryName());
        }

        // Check "Local" Registry
        if (!instanceKey.isPresent()) {
            try {
                Registry<Resource> dynRegistry = dynamicRegistries.registryOrThrow(registryKey);

                //noinspection ConstantConditions
                instanceKey = dynRegistry != null ? dynRegistry.getResourceKey(resource).map(ResourceKey::location) : Optional.empty();
            } catch (Throwable t) {
                // Registry not supported, skip
            }
        }

        // Check Vanilla Worldgen Registries
        if (!instanceKey.isPresent()) {
            Registry<Resource> registry = getFromVanillaRegistryIllegally(BuiltinRegistries.REGISTRY, registryKey);

            if (registry != null) {
                instanceKey = registry.getResourceKey(resource).map(ResourceKey::location);
            }
        }

        // Check Global Vanilla Registries
        if (!instanceKey.isPresent()) {
            Registry<Resource> registry = getFromVanillaRegistryIllegally(Registry.REGISTRY, registryKey);

            if (registry != null) {
                instanceKey = registry.getResourceKey(resource).map(ResourceKey::location);
            }
        }

        // Check Forge Registries
        if (!instanceKey.isPresent()) {
            instanceKey = getFromForgeRegistryIllegally(registryKey, resource);
        }

        return instanceKey;
    }

    @Override
    protected <Resource> DataResult<Format> encode(Resource resource, Format dynamic, ResourceKey<? extends Registry<Resource>> registryKey, Codec<Resource> codec) {
        Optional<ResourceLocation> instanceKey = rummageForResourceLocation(resource, registryKey);

        // five freaking locations to check... Let's see if we won a prize
        if (instanceKey.isPresent()) {
            if (TwilightForestMod.ID.equals(instanceKey.get().getNamespace())) // This avoids generating anything that belongs to Minecraft
                serialize(registryKey, instanceKey.get(), resource, codec);

            return ResourceLocation.CODEC.encode(instanceKey.get(), this.delegate, dynamic);
        }

        // AND we turned out empty-handed. Inline the object begrudgingly instead.
        return codec.encode(resource, this, dynamic);
    }

    @Override
    public String getName() {
        return "Twilight World";
    }

    // Otherwise using an AT increases runtime overhead, so we use reflection here instead since dataGen won't run on regular minecraft runtime, so we instead have faux-constructors here
    @SuppressWarnings("SameParameterValue")
    protected static Optional<NoiseGeneratorSettings> makeDimensionSettings(StructureSettings structures, NoiseSettings noise, BlockState defaultBlock, BlockState defaultFluid, int bedrockRoofPosition, int bedrockFloorPosition, int seaLevel, boolean disableMobGeneration) {
        try {
            Constructor<NoiseGeneratorSettings> ctor = NoiseGeneratorSettings.class.getDeclaredConstructor(
                    StructureSettings.class,
                    NoiseSettings.class,
                    BlockState.class,
                    BlockState.class,
                    int.class,
                    int.class,
                    int.class,
                    boolean.class
            );

            ctor.setAccessible(true);

            return Optional.of(ctor.newInstance(structures, noise, defaultBlock, defaultFluid, bedrockRoofPosition, bedrockFloorPosition, seaLevel, disableMobGeneration));
        } catch (Exception e) {
            LOGGER.error("Error constructing `DimensionSettings`!", e);

            return Optional.empty();
        }
    }

    @SuppressWarnings("SameParameterValue")
    protected static Optional<DimensionType> makeDimensionType(
            OptionalLong fixedTime,
            boolean hasSkyLight,
            boolean hasCeiling,
            boolean ultrawarm,
            boolean natural,
            double coordinateScale,
            boolean hasDragonFight,
            boolean piglinSafe,
            boolean bedWorks,
            boolean respawnAnchorWorks,
            boolean hasRaids,
            int logicalHeight,
            BiomeZoomer magnifier,
            ResourceLocation infiniburn,
            ResourceLocation effects,
            float ambientLight
    ) {
        try {
            Constructor<DimensionType> ctor = DimensionType.class.getDeclaredConstructor(
                    OptionalLong.class,
                    boolean.class,
                    boolean.class,
                    boolean.class,
                    boolean.class,
                    double.class,
                    boolean.class,
                    boolean.class,
                    boolean.class,
                    boolean.class,
                    boolean.class,
                    int.class,
                    BiomeZoomer.class,
                    ResourceLocation.class,
                    ResourceLocation.class,
                    float.class
            );

            ctor.setAccessible(true);

            return Optional.of(ctor.newInstance(fixedTime, hasSkyLight, hasCeiling, ultrawarm, natural, coordinateScale, hasDragonFight, piglinSafe, bedWorks, respawnAnchorWorks, hasRaids, logicalHeight, magnifier, infiniburn, effects, ambientLight));
        } catch (Exception e) {
            LOGGER.error("Error constructing `DimensionType`!", e);

            return Optional.empty();
        }
    }
}
