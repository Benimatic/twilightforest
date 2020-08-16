package twilightforest.data;

import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.BiomeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BiomeDataHelper extends BiomeProvider {
    public BiomeDataHelper(DataGenerator generator) {
        super(generator);
    }

    /** Vanillacopy net.minecraft.data.BiomeProvider */
    @Override
    public void act(final DirectoryCache directoryCache) {
        final Path outputPath = this.field_244197_d.getOutputFolder();

        for (Map.Entry<ResourceLocation, Biome> biomeKeyPair : generateBiomes().entrySet()) {
            Path filePath = makePath(outputPath, biomeKeyPair.getKey());
            Function<Supplier<Biome>, DataResult<JsonElement>> serializer = JsonOps.INSTANCE.withEncoder(Biome.field_235051_b_);

            try {
                Optional<JsonElement> jsonOptional = serializer.apply(biomeKeyPair::getValue).result();

                if (jsonOptional.isPresent()) {
                    IDataProvider.save(field_244196_c, directoryCache, jsonOptional.get(), filePath);

                    System.out.print("\"" + biomeKeyPair.getKey() + "\", ");
                } else {
                    field_244195_b.error("Couldn't serialize biome {}", filePath);
                }
            } catch (IOException e) {
                field_244195_b.error("Couldn't save biome {}", filePath, e);
            }
        }

        System.out.print("\nTF Biome generation finished execution!\n");
    }

    protected static Path makePath(Path path, ResourceLocation resc) {
        return path.resolve("data/" + resc.getNamespace() + "/worldgen/biome/" + resc.getPath() + ".json");
    }

    protected abstract Map<ResourceLocation, Biome> generateBiomes();

    protected static BiomeAmbience.Builder defaultAmbientBuilder() {
        return (new BiomeAmbience.Builder())
                .setFogColor(0xC0FFD8) // TODO Change based on Biome. Not previously done before
                .setWaterColor(0x3F76E4)
                .setWaterFogColor(0x050533)
                .func_242539_d(0x20224A) // Sky Color, TODO Change based on Biome. Not previously done before
                .setMoodSound(MoodSoundAmbience.field_235027_b_); // This sets cave sounds, we should probably change it
    }

    protected static BiomeGenerationSettings.Builder defaultGenSettingBuilder() {
        BiomeGenerationSettings.Builder biomeGenerationSettings = (new BiomeGenerationSettings.Builder())
                .func_242517_a(ConfiguredSurfaceBuilders.field_244178_j); // GRASS_DIRT_GRAVEL_CONFIG

        DefaultBiomeFeatures.func_243748_i(biomeGenerationSettings); // Dirt / AltStones
        DefaultBiomeFeatures.func_243750_j(biomeGenerationSettings); // Ores
        return biomeGenerationSettings;
    }

    protected static Biome.Builder defaultBiomeBuilder() {
        return defaultBiomeBuilder(defaultAmbientBuilder(), new MobSpawnInfo.Builder(), defaultGenSettingBuilder());
    }

    protected static Biome.Builder defaultBiomeBuilder(BiomeAmbience.Builder biomeAmbience, MobSpawnInfo.Builder mobSpawnInfo, BiomeGenerationSettings.Builder biomeGenerationSettings) {
        return (new Biome.Builder())
                .precipitation(Biome.RainType.RAIN)
                .category(Biome.Category.FOREST)
                .depth(0.1F)
                .scale(0.2F)
                .temperature(0.5F)
                .downfall(0.5F)
                .func_235097_a_(biomeAmbience.build())
                .func_242458_a(mobSpawnInfo.func_242577_b())
                .func_242457_a(biomeGenerationSettings.func_242508_a())
                .func_242456_a(Biome.TemperatureModifier.NONE);
    }
}
