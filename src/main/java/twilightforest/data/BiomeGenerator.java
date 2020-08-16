package twilightforest.data;

import com.google.common.collect.ImmutableMap;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.*;
import twilightforest.TwilightForestMod;

import java.util.Map;

public class BiomeGenerator extends BiomeDataHelper {
    public BiomeGenerator(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected Map<ResourceLocation, Biome> generateBiomes() {
        final ImmutableMap.Builder<ResourceLocation, Biome> biomes = ImmutableMap.builder();

        // defaultBiomeBuilder() returns a Biome.Builder and Biome.Builder#func_242455_a() builds it

        biomes.put(TwilightForestMod.prefix("forest"), defaultBiomeBuilder().func_242455_a());
        // Default values
        // depth(0.1F)
        // scale(0.2F)
        // temperature(0.5F)
        // downfall(0.5F)

        biomes.put(TwilightForestMod.prefix("dense_forest"),
                defaultBiomeBuilder(defaultAmbientBuilder().setWaterColor(0x005522), new MobSpawnInfo.Builder(), defaultGenSettingBuilder())
                        .temperature(0.7F)
                        .downfall(0.8F)
                        .depth(0.2F)
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("firefly_forest"), defaultBiomeBuilder()
                .downfall(1)
                .depth(0.125F)
                .scale(0.05F)
                .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("clearing"), defaultBiomeBuilder()
                .category(Biome.Category.PLAINS)
                .temperature(0.8F)
                .downfall(0.4F)
                .depth(0.125F)
                .scale(0.05F)
                .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("oak_savannah"), defaultBiomeBuilder()
                .category(Biome.Category.SAVANNA)
                .temperature(0.9F)
                .downfall(0)
                .depth(0.2F)
                .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("mushroom_forest"), defaultBiomeBuilder()
                .temperature(0.8F)
                .downfall(0.8F)
                .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("deep_mushroom_forest"), defaultBiomeBuilder()
                .temperature(0.8F)
                .downfall(1)
                .depth(0.125F)
                .scale(0.05F)
                .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("enchanted_forest"), defaultBiomeBuilder().func_242455_a());

        biomes.put(TwilightForestMod.prefix("stream"), defaultBiomeBuilder()
                .category(Biome.Category.RIVER)
                .depth(-0.7F)
                .scale(0)
                .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("lake"),
                defaultBiomeBuilder(defaultAmbientBuilder().setWaterColor(0xC0FFD8).setWaterFogColor(0x3F76E4), new MobSpawnInfo.Builder(), defaultGenSettingBuilder())
                        .category(Biome.Category.OCEAN)
                        .temperature(0.66F)
                        .downfall(1)
                        .depth(-1.8F)
                        .scale(0.1F)
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("swamp"),
                defaultBiomeBuilder(defaultAmbientBuilder().setWaterColor(0xE0FFAE), new MobSpawnInfo.Builder(), defaultGenSettingBuilder())
                        .category(Biome.Category.SWAMP)
                        .temperature(0.8F)
                        .downfall(0.9F)
                        .depth(-0.125F)
                        .scale(0.125F)
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("fire_swamp"),
                defaultBiomeBuilder(defaultAmbientBuilder().setWaterColor(0x6C2C2C), new MobSpawnInfo.Builder(), defaultGenSettingBuilder())
                        .category(Biome.Category.SWAMP)
                        .temperature(1)
                        .downfall(0.4F)
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("dark_forest"), defaultBiomeBuilder()
                .temperature(0.7F)
                .downfall(0.8F)
                .depth(0.125F)
                .scale(0.05F)
                .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("dark_forest_center"), defaultBiomeBuilder()
                .depth(0.125F)
                .scale(0.05F)
                .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("snowy_forest"), defaultBiomeBuilder()
                .temperature(0.09F)
                .downfall(0.9F)
                .depth(0.2F)
                .precipitation(Biome.RainType.SNOW)
                .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("glacier"), defaultBiomeBuilder()
                .category(Biome.Category.ICY)
                .temperature(0)
                .downfall(0.1F)
                .precipitation(Biome.RainType.SNOW)
                .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("highlands"), defaultBiomeBuilder()
                .category(Biome.Category.MESA)
                .temperature(0.4F)
                .downfall(0.7F)
                .depth(3.5F)
                .scale(0.05F)
                .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("thornlands"), defaultBiomeBuilder()
                .category(Biome.Category.NONE)
                .temperature(0.3F)
                .downfall(0.2F)
                .depth(6)
                .scale(0.1F)
                .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("final_plateau"), defaultBiomeBuilder()
                .category(Biome.Category.MESA)
                .temperature(0.3F)
                .downfall(0.2F)
                .depth(10.5F)
                .scale(0.025F)
                .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("spooky_forest"),
                defaultBiomeBuilder(defaultAmbientBuilder().setWaterColor(0XFA9111), new MobSpawnInfo.Builder(), defaultGenSettingBuilder())
                        .downfall(1)
                        .depth(0.125F)
                        .scale(0.05F)
                        .func_242455_a()
        );

        return biomes.build();
    }
}
