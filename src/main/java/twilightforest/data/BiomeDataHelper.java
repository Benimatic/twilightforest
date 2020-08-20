package twilightforest.data;

import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.BiomeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import twilightforest.entity.TFEntities;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
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

                    System.out.print("\n\"" + biomeKeyPair.getKey() + "\", ");
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

    protected static BiomeGenerationSettings.Builder modify(BiomeGenerationSettings.Builder builder, Consumer<BiomeGenerationSettings.Builder> consumer) {
        consumer.accept(builder);
        return builder;
    }

    protected static BiomeGenerationSettings.Builder defaultGenSettingBuilder() {
        BiomeGenerationSettings.Builder biomeGenerationSettings = (new BiomeGenerationSettings.Builder())
                .func_242517_a(ConfiguredSurfaceBuilders.field_244178_j); // GRASS_DIRT_GRAVEL_CONFIG

        // TODO Re-enable, currently disabled so it's just easier to read the jsons
        DefaultBiomeFeatures.func_243748_i(biomeGenerationSettings); // Dirt / AltStones
        DefaultBiomeFeatures.func_243750_j(biomeGenerationSettings); // Ores
        DefaultBiomeFeatures.func_243742_f(biomeGenerationSettings); // Lava and Water lakes
        DefaultBiomeFeatures.func_243755_o(biomeGenerationSettings); // Clay disks
        DefaultBiomeFeatures.func_243704_R(biomeGenerationSettings); // Plain grass
        DefaultBiomeFeatures.func_243711_Y(biomeGenerationSettings); // Tall grass
        return biomeGenerationSettings;
    }

    protected static MobSpawnInfo.Builder defaultMobSpawning() {
        MobSpawnInfo.Builder spawnInfo = new MobSpawnInfo.Builder();

        spawnInfo.func_242572_a(0.75f); // Spawns passive mobs as long as random value is less than this

        // TODO why is mob spawning in overdrive?!
        spawnInfo.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(TFEntities.bighorn_sheep, 2, 4, 4));
        spawnInfo.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(TFEntities.wild_boar, 2, 4, 4));
        spawnInfo.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.CHICKEN, 2, 4, 4));
        spawnInfo.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(TFEntities.deer, 2, 4, 5));
        spawnInfo.func_242575_a(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.WOLF, 2, 4, 4));

        // TODO make Monsters spawn underground only somehow - These are originally underground spawns
        spawnInfo.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SPIDER, 1, 1, 1)); //EntityType.SPIDER, 10, 4, 4));
        spawnInfo.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE, 1, 1, 1)); //EntityType.ZOMBIE, 10, 4, 4));
        spawnInfo.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SKELETON, 1, 1, 1)); //EntityType.SKELETON, 10, 4, 4));
        spawnInfo.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.CREEPER, 1, 1, 1)); //EntityType.CREEPER, 1, 4, 4));
        spawnInfo.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SLIME, 1, 1, 1)); //EntityType.SLIME, 10, 4, 4));
        spawnInfo.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 1, 1, 1)); //EntityType.ENDERMAN, 1, 1, 4));
        spawnInfo.func_242575_a(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(TFEntities.kobold, 10, 1, 8)); //TFEntities.kobold, 10, 4, 8));

        spawnInfo.func_242575_a(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(EntityType.BAT, 1, 1, 1)); //10, 8, 8));

        //spawnInfo.func_242573_a(TFEntities.bighorn_sheep, 1, 2);
        //spawnInfo.func_242573_a(TFEntities.wild_boar, 1, 2);
        //spawnInfo.func_242573_a(EntityType.CHICKEN, 1, 2);
        //spawnInfo.func_242573_a(TFEntities.deer, 1, 2);
        //spawnInfo.func_242573_a(EntityType.WOLF, 1, 2);

        spawnInfo.func_242573_a(EntityType.SPIDER, 1, 2);
        spawnInfo.func_242573_a(EntityType.ZOMBIE, 1, 2);
        spawnInfo.func_242573_a(EntityType.SKELETON, 1, 2);
        spawnInfo.func_242573_a(EntityType.CREEPER, 1, 2);
        spawnInfo.func_242573_a(EntityType.SLIME, 1, 2);
        spawnInfo.func_242573_a(EntityType.ENDERMAN, 1, 2);
        spawnInfo.func_242573_a(TFEntities.kobold, 1, 2);

        //spawnInfo.func_242573_a(EntityType.BAT, 1, 2);

        return spawnInfo;
    }

    protected static Biome.Builder biomeWithDefaults() {
        return biomeWithDefaults(defaultAmbientBuilder(), new MobSpawnInfo.Builder(), defaultGenSettingBuilder());
    }

    protected static Biome.Builder biomeWithDefaults(BiomeAmbience.Builder biomeAmbience, MobSpawnInfo.Builder mobSpawnInfo, BiomeGenerationSettings.Builder biomeGenerationSettings) {
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
