package twilightforest.data;

import com.google.common.collect.ImmutableMap;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import twilightforest.TwilightForestMod;
import twilightforest.world.newfeature.TwilightFeatures;

import java.util.Map;

public final class BiomeGenerator extends BiomeDataHelper {
    public BiomeGenerator(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected Map<ResourceLocation, Biome> generateBiomes() {
        final ImmutableMap.Builder<ResourceLocation, Biome> biomes = ImmutableMap.builder();
        // defaultBiomeBuilder() returns a Biome.Builder and Biome.Builder#func_242455_a() builds it

        biomes.put(TwilightForestMod.prefix("forest"),
                biomeWithDefaults(defaultAmbientBuilder(), new MobSpawnInfo.Builder(), modify(defaultGenSettingBuilder(), b -> b.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, TwilightFeatures.ConfiguredTrees.CANOPY_TREE)))
                        .func_242455_a()
        );
        // Default values
        // depth(0.1F)
        // scale(0.2F)
        // temperature(0.5F)
        // downfall(0.5F)

        /*int iter = -1;

        biomes.put(TwilightForestMod.prefix("" + ++iter),
                biomeWithDefaults(defaultAmbientBuilder(), new MobSpawnInfo.Builder(), modify((new BiomeGenerationSettings.Builder()).func_242517_a(ConfiguredSurfaceBuilders.field_244178_j), b -> b.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION,
                        Feature.field_236291_c_.withConfiguration(new BaseTreeFeatureConfig.Builder(
                                new SimpleBlockStateProvider(Blocks.DARK_OAK_LOG.getDefaultState()),
                                new SimpleBlockStateProvider(Blocks.DARK_OAK_LEAVES.getDefaultState()),
                                new LeafSpheroidFoliagePlacer(4.5f, FeatureSpread.func_242252_a(0), 1.5f),
                                new BranchingTrunkPlacer(20, 5, 5, 3, 1),
                                new TwoLayerFeature(1, 0, 1)
                        )
                                .func_236703_a_(ImmutableList.of(
                                        new FireflyTreeDecorator(1, 1.0f),
                                        new TreeRootsDecorator(3, 1, (new WeightedBlockStateProvider())
                                                .addWeightedBlockstate(TFBlocks.root.get().getDefaultState(), 4)
                                                .addWeightedBlockstate(TFBlocks.liveroot_block.get().getDefaultState(), 1))
                                        )
                                )
                                .func_236701_a_(Integer.MAX_VALUE)
                                .func_236702_a_(Heightmap.Type.MOTION_BLOCKING)
                                .setIgnoreVines()
                                .build()))
                        )
                ).func_242455_a()
        );//*/

        biomes.put(TwilightForestMod.prefix("dense_forest"),
                biomeWithDefaults(defaultAmbientBuilder().setWaterColor(0x005522), new MobSpawnInfo.Builder(), defaultGenSettingBuilder())
                        .temperature(0.7F)
                        .downfall(0.8F)
                        .depth(0.2F)
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("firefly_forest"),
                biomeWithDefaults(defaultAmbientBuilder(), new MobSpawnInfo.Builder(), defaultGenSettingBuilder())
                        .downfall(1)
                        .depth(0.125F)
                        .scale(0.05F)
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("clearing"),
                biomeWithDefaults(defaultAmbientBuilder(), new MobSpawnInfo.Builder(), defaultGenSettingBuilder())
                        .category(Biome.Category.PLAINS)
                        .temperature(0.8F)
                        .downfall(0.4F)
                        .depth(0.125F)
                        .scale(0.05F)
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("oak_savannah"),
                biomeWithDefaults(defaultAmbientBuilder(), new MobSpawnInfo.Builder(), defaultGenSettingBuilder())
                        .category(Biome.Category.SAVANNA)
                        .temperature(0.9F)
                        .downfall(0)
                        .depth(0.2F)
                        .func_242455_a()
        );

        BiomeGenerationSettings.Builder mushroomBiome = defaultGenSettingBuilder();

        DefaultBiomeFeatures.func_243712_Z(mushroomBiome); // Add small mushrooms
        DefaultBiomeFeatures.func_243703_Q(mushroomBiome); // Add large mushrooms

        biomes.put(TwilightForestMod.prefix("mushroom_forest"),
                biomeWithDefaults(defaultAmbientBuilder().setWaterColor(0xC0FFD8).setWaterFogColor(0x3F76E4), new MobSpawnInfo.Builder(), mushroomBiome)
                        .temperature(0.8F)
                        .downfall(0.8F)
                        .func_242455_a()
        );

        // TODO add towering mushrooms

        modify(mushroomBiome, builder -> builder.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, TwilightFeatures.ConfiguredTrees.TWILIGHT_OAK));

        biomes.put(TwilightForestMod.prefix("dense_mushroom_forest"),
                biomeWithDefaults(defaultAmbientBuilder().setWaterColor(0xC0FFD8).setWaterFogColor(0x3F76E4), new MobSpawnInfo.Builder(), mushroomBiome)
                        .temperature(0.8F)
                        .downfall(1)
                        .depth(0.125F)
                        .scale(0.05F)
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("spooky_forest"),
                biomeWithDefaults(defaultAmbientBuilder().setWaterColor(0xFA9111), new MobSpawnInfo.Builder(), defaultGenSettingBuilder())
                        .downfall(1)
                        .depth(0.125F)
                        .scale(0.05F)
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("enchanted_forest"),
                biomeWithDefaults(defaultAmbientBuilder(), new MobSpawnInfo.Builder(), modify(defaultGenSettingBuilder(), c -> c.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, TwilightFeatures.ConfiguredTrees.RAINBOAK_TREE)))
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("stream"),
                biomeWithDefaults(defaultAmbientBuilder(), new MobSpawnInfo.Builder(), defaultGenSettingBuilder())
                        .category(Biome.Category.RIVER)
                        .depth(-0.7F)
                        .scale(0)
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("lake"),
                biomeWithDefaults(defaultAmbientBuilder().setWaterColor(0xC0FFD8).setWaterFogColor(0x3F76E4), new MobSpawnInfo.Builder(), defaultGenSettingBuilder())
                        .category(Biome.Category.OCEAN)
                        .temperature(0.66F)
                        .downfall(1)
                        .depth(-1.8F)
                        .scale(0.1F)
                        .func_242455_a()
        );

        BiomeGenerationSettings.Builder swampGenerationBuilder = modify(defaultGenSettingBuilder(), b -> b.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, TwilightFeatures.ConfiguredTrees.MANGROVE_TREE));

        biomes.put(TwilightForestMod.prefix("swamp"),
                biomeWithDefaults(defaultAmbientBuilder().setWaterColor(0xE0FFAE), new MobSpawnInfo.Builder(), swampGenerationBuilder)
                        .category(Biome.Category.SWAMP)
                        .temperature(0.8F)
                        .downfall(0.9F)
                        .depth(-0.125F)
                        .scale(0.125F)
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("fire_swamp"),
                biomeWithDefaults(defaultAmbientBuilder().setWaterColor(0x6C2C2C), new MobSpawnInfo.Builder(), swampGenerationBuilder)
                        .category(Biome.Category.SWAMP)
                        .temperature(1)
                        .downfall(0.4F)
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("dark_forest"),
                biomeWithDefaults(defaultAmbientBuilder(), new MobSpawnInfo.Builder(), defaultGenSettingBuilder())
                        .temperature(0.7F)
                        .downfall(0.8F)
                        .depth(0.125F)
                        .scale(0.05F)
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("dark_forest_center"),
                biomeWithDefaults(defaultAmbientBuilder(), new MobSpawnInfo.Builder(), defaultGenSettingBuilder())
                        .depth(0.125F)
                        .scale(0.05F)
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("snowy_forest"),
                biomeWithDefaults(defaultAmbientBuilder(), new MobSpawnInfo.Builder(), modify(defaultGenSettingBuilder(), DefaultBiomeFeatures::func_243690_D))
                        .temperature(0.09F)
                        .downfall(0.9F)
                        .depth(0.2F)
                        .precipitation(Biome.RainType.SNOW)
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("glacier"),
                biomeWithDefaults(defaultAmbientBuilder(), new MobSpawnInfo.Builder(), defaultGenSettingBuilder())
                        .category(Biome.Category.ICY)
                        .temperature(0)
                        .downfall(0.1F)
                        .precipitation(Biome.RainType.SNOW)
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("highlands"),
                biomeWithDefaults(defaultAmbientBuilder(), new MobSpawnInfo.Builder(), defaultGenSettingBuilder())
                        .category(Biome.Category.MESA)
                        .temperature(0.4F)
                        .downfall(0.7F)
                        .depth(3.5F)
                        .scale(0.05F)
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("thornlands"),
                biomeWithDefaults(defaultAmbientBuilder(), new MobSpawnInfo.Builder(), defaultGenSettingBuilder())
                        .category(Biome.Category.NONE)
                        .temperature(0.3F)
                        .downfall(0.2F)
                        .depth(6)
                        .scale(0.1F)
                        .func_242455_a()
        );

        biomes.put(TwilightForestMod.prefix("final_plateau"),
                biomeWithDefaults(defaultAmbientBuilder(), new MobSpawnInfo.Builder(), defaultGenSettingBuilder())
                        .category(Biome.Category.MESA)
                        .temperature(0.3F)
                        .downfall(0.2F)
                        .depth(10.5F)
                        .scale(0.025F)
                        .func_242455_a()
        );

        return biomes.build();
    }
}
