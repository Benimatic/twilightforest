package twilightforest.world.registration.features;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.sounds.Music;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import twilightforest.TFSounds;
import twilightforest.TwilightForestMod;
import twilightforest.world.components.feature.config.HollowLogConfig;
import twilightforest.world.components.feature.config.SpikeConfig;
import twilightforest.world.components.feature.config.ThornsConfig;
import twilightforest.world.components.placements.ChunkBlanketingModifier;
import twilightforest.world.components.placements.OutOfStructureFilter;
import twilightforest.world.registration.BlockConstants;
import twilightforest.world.registration.TFBiomeFeatures;
import twilightforest.world.registration.TreeConfigurations;
import twilightforest.world.registration.TwilightFeatures;

import java.util.List;

public final class TFVegetationFeatures {

    //vanilla features with custom placement code
    public static final PlacedFeature LAKE_LAVA = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("lava_lake"), Feature.LAKE.configured(new LakeFeature.Configuration(BlockStateProvider.simple(BlockConstants.LAVA), BlockStateProvider.simple(Blocks.STONE))).placed(tfFeatureCheckArea(OutOfStructureFilter.checkBoth(), 10).build()));
    public static final PlacedFeature LAKE_WATER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("water_lake"), Feature.LAKE.configured(new LakeFeature.Configuration(BlockStateProvider.simple(BlockConstants.WATER), BlockStateProvider.simple(Blocks.STONE))).placed(tfFeatureCheckArea(OutOfStructureFilter.checkBoth(), 4).build()));

    //"structures" that arent actually structures
    public static final ConfiguredFeature<?, ?> SIMPLE_WELL = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("simple_well"), TFBiomeFeatures.SIMPLE_WELL.get().configured(FeatureConfiguration.NONE));
    public static final ConfiguredFeature<?, ?> FANCY_WELL = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("fancy_well"), TFBiomeFeatures.FANCY_WELL.get().configured(FeatureConfiguration.NONE));
    public static final PlacedFeature WELL_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("well_placer"), Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(FANCY_WELL.placed(), 0.07F)), SIMPLE_WELL.placed())).placed(tfFeatureCheckArea(OutOfStructureFilter.checkBoth(), 80).build()));
    public static final PlacedFeature DRUID_HUT = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("druid_hut"), TFBiomeFeatures.DRUID_HUT.get().configured(FeatureConfiguration.NONE).placed(tfFeatureCheckArea(OutOfStructureFilter.checkBoth(), 105).build()));
    public static final PlacedFeature GRAVEYARD = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("graveyard"), TFBiomeFeatures.GRAVEYARD.get().configured(FeatureConfiguration.NONE).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 75).build()));

    //all the fun little things you find around the dimension
    public static final PlacedFeature BIG_MUSHGLOOM = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("big_mushgloom"), TFBiomeFeatures.BIG_MUSHGLOOM.get().configured(FeatureConfiguration.NONE).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 1).build()));
    public static final PlacedFeature FALLEN_LEAVES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("fallen_leaves"), TFBiomeFeatures.FALLEN_LEAVES.get().configured(FeatureConfiguration.NONE).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 1).build()));
    public static final PlacedFeature FIDDLEHEAD = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("fiddlehead"), Feature.RANDOM_PATCH.configured(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlockConstants.FIDDLEHEAD))))).placed(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
    public static final PlacedFeature FIRE_JET = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("fire_jet"), TFBiomeFeatures.FIRE_JET.get().configured(new BlockStateConfiguration(BlockConstants.FIRE_JET)).placed(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), BiomeFilter.biome()));
    public static final PlacedFeature FOUNDATION = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("foundation"), TFBiomeFeatures.FOUNDATION.get().configured(FeatureConfiguration.NONE).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 100).build()));
    public static final PlacedFeature GROVE_RUINS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("grove_ruins"), TFBiomeFeatures.GROVE_RUINS.get().configured(FeatureConfiguration.NONE).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 105).build()));
    public static final PlacedFeature HOLLOW_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("hollow_log"), TFBiomeFeatures.FALLEN_HOLLOW_LOG.get().configured(FeatureConfiguration.NONE).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 100).build()));
    public static final PlacedFeature HOLLOW_STUMP = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("hollow_stump"), TFBiomeFeatures.HOLLOW_STUMP.get().configured(TreeConfigurations.HOLLOW_TREE).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 90).build()));
    public static final PlacedFeature HUGE_LILY_PAD = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("huge_lily_pad"), TFBiomeFeatures.HUGE_LILY_PAD.get().configured(FeatureConfiguration.NONE).placed(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, RarityFilter.onAverageOnceEvery(20), InSquarePlacement.spread(), CountPlacement.of(10), BiomeFilter.biome()));
    public static final PlacedFeature HUGE_WATER_LILY = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("huge_water_lily"), TFBiomeFeatures.HUGE_WATER_LILY.get().configured(FeatureConfiguration.NONE).placed(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, RarityFilter.onAverageOnceEvery(25), InSquarePlacement.spread(), CountPlacement.of(5), BiomeFilter.biome()));
    public static final ConfiguredFeature<?, ?> CICADA_LAMPPOST = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("cicada_lamppost"), TFBiomeFeatures.LAMPPOSTS.get().configured(new BlockStateConfiguration(BlockConstants.CICADA_JAR)));
    public static final ConfiguredFeature<?, ?> FIREFLY_LAMPPOST = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("firefly_lamppost"), TFBiomeFeatures.LAMPPOSTS.get().configured(new BlockStateConfiguration(BlockConstants.FIREFLY_JAR)));
    public static final PlacedFeature LAMPPOST_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("lamppost_placer"), Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(CICADA_LAMPPOST.placed(), 0.1F)), FIREFLY_LAMPPOST.placed())).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 2).build()));
    public static final PlacedFeature MONOLITH = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("monolith"), TFBiomeFeatures.MONOLITH.get().configured(FeatureConfiguration.NONE).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 90).build()));
    public static final PlacedFeature MUSHGLOOM_CLUSTER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mushgloom_cluster"), Feature.RANDOM_PATCH.configured(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlockConstants.MUSHGLOOM))))).placed(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, CountPlacement.of(5), InSquarePlacement.spread(), BiomeFilter.biome()));
    public static final PlacedFeature MYCELIUM_BLOB = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mycelium_blob"), TFBiomeFeatures.MYCELIUM_BLOB.get().configured(new DiskConfiguration(BlockConstants.MYCELIUM, UniformInt.of(4, 6), 3, ImmutableList.of(BlockConstants.GRASS_BLOCK))).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 3).build()));
    public static final PlacedFeature OUTSIDE_STALAGMITE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("outside_stalagmite"), TFBiomeFeatures.CAVE_STALACTITE.get().configured(new SpikeConfig(BlockStateProvider.simple(Blocks.STONE), UniformInt.of(5, 10), ConstantInt.of(0), false)).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 88).build()));
    public static final PlacedFeature PLANT_ROOTS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("plant_roots"), TFBiomeFeatures.UNDERGROUND_PLANTS.get().configured(new BlockStateConfiguration(BlockConstants.ROOT_STRAND)).placed(tfFeatureCheckArea(OutOfStructureFilter.checkUnderground(), 1, CountPlacement.of(4), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(10))).build()));
    public static final PlacedFeature PUMPKIN_LAMPPOST = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("pumpkin_lamppost"), TFBiomeFeatures.LAMPPOSTS.get().configured(new BlockStateConfiguration(BlockConstants.JACK_O_LANTERN)).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 1).build()));
    public static final PlacedFeature SMOKER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("smoker"), TFBiomeFeatures.FIRE_JET.get().configured(new BlockStateConfiguration(BlockConstants.SMOKER)).placed(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), BiomeFilter.biome()));
    public static final PlacedFeature STONE_CIRCLE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("stone_circle"), TFBiomeFeatures.STONE_CIRCLE.get().configured(FeatureConfiguration.NONE).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 105).build()));
    public static final PlacedFeature THORNS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("thorns"), TFBiomeFeatures.THORNS.get().configured(new ThornsConfig(7, 3, 3, 50)).placed(ChunkBlanketingModifier.addThorns(), BiomeFilter.biome()));
    public static final PlacedFeature TORCH_BERRIES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("torch_berries"), TFBiomeFeatures.UNDERGROUND_PLANTS.get().configured(new BlockStateConfiguration(BlockConstants.TORCHBERRIES)).placed(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(60)), CountPlacement.of(8), OutOfStructureFilter.checkUnderground(), BiomeFilter.biome()));
    public static final PlacedFeature TROLL_ROOTS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("troll_roots"), TFBiomeFeatures.TROLL_VINES.get().configured(new BlockStateConfiguration(BlockConstants.TROLLVIDR)).placed(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(60)), CountPlacement.of(8), BiomeFilter.biome()));
    public static final PlacedFeature VANILLA_ROOTS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("vanilla_roots"), TFBiomeFeatures.UNDERGROUND_PLANTS.get().configured(new BlockStateConfiguration(BlockConstants.HANGING_ROOTS)).placed(tfFeatureCheckArea(OutOfStructureFilter.checkUnderground(), 1, CountPlacement.of(16), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(10))).build()));
    public static final PlacedFeature WEBS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("webs"), TFBiomeFeatures.WEBS.get().configured(FeatureConfiguration.NONE).placed(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, InSquarePlacement.spread(), CountPlacement.of(60), BiomeFilter.biome()));
    public static final PlacedFeature WOOD_ROOTS_SPREAD = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("ore/wood_roots_spread"), TFBiomeFeatures.WOOD_ROOTS.get().configured(FeatureConfiguration.NONE).placed(tfFeatureCheckArea(OutOfStructureFilter.checkUnderground(), 40, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0))).build()));
    public static final PlacedFeature SNOW_UNDER_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("snow_under_trees"), TFBiomeFeatures.SNOW_UNDER_TREES.get().configured(FeatureConfiguration.NONE)).placed(BiomeFilter.biome());

    //fallen logs!
    public static final PlacedFeature TF_OAK_FALLEN_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tf_oak_fallen_log"), TFBiomeFeatures.FALLEN_SMALL_LOG.get().configured(new HollowLogConfig(BlockConstants.TF_OAK_LOG, BlockConstants.HOLLOW_TF_OAK_LOG)).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build()));
    public static final PlacedFeature CANOPY_FALLEN_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("canopy_fallen_log"), TFBiomeFeatures.FALLEN_SMALL_LOG.get().configured(new HollowLogConfig(BlockConstants.CANOPY_LOG, BlockConstants.HOLLOW_CANOPY_LOG)).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build()));
    public static final PlacedFeature MANGROVE_FALLEN_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mangrove_fallen_log"), TFBiomeFeatures.FALLEN_SMALL_LOG.get().configured(new HollowLogConfig(BlockConstants.MANGROVE_LOG, BlockConstants.HOLLOW_MANGROVE_LOG)).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build()));
    public static final PlacedFeature OAK_FALLEN_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("oak_fallen_log"), TFBiomeFeatures.FALLEN_SMALL_LOG.get().configured(new HollowLogConfig(BlockConstants.OAK_LOG, BlockConstants.HOLLOW_OAK_LOG)).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build()));
    public static final PlacedFeature SPRUCE_FALLEN_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("spruce_fallen_log"), TFBiomeFeatures.FALLEN_SMALL_LOG.get().configured(new HollowLogConfig(BlockConstants.SPRUCE_LOG, BlockConstants.HOLLOW_SPRUCE_LOG)).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build()));
    public static final PlacedFeature BIRCH_FALLEN_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("birch_fallen_log"), TFBiomeFeatures.FALLEN_SMALL_LOG.get().configured(new HollowLogConfig(BlockConstants.BIRCH_LOG, BlockConstants.HOLLOW_BIRCH_LOG)).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build()));
    public static final PlacedFeature DEFAULT_FALLEN_LOGS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("default_fallen_logs"), Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(BIRCH_FALLEN_LOG, 0.1F), new WeightedPlacedFeature(OAK_FALLEN_LOG, 0.2F), new WeightedPlacedFeature(CANOPY_FALLEN_LOG, 0.4F)), TF_OAK_FALLEN_LOG))).placed(tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build());

    //smol stone veins
    public static final PlacedFeature SMALL_GRANITE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("small_granite"), Feature.ORE.configured(new OreConfiguration(OreFeatures.NATURAL_STONE, Blocks.GRANITE.defaultBlockState(), 16)).placed(RarityFilter.onAverageOnceEvery(60), InSquarePlacement.spread(), CountPlacement.of(5)));
    public static final PlacedFeature SMALL_DIORITE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("small_diorite"), Feature.ORE.configured(new OreConfiguration(OreFeatures.NATURAL_STONE, Blocks.DIORITE.defaultBlockState(), 16)).placed(RarityFilter.onAverageOnceEvery(60), InSquarePlacement.spread(), CountPlacement.of(5)));
    public static final PlacedFeature SMALL_ANDESITE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("small_andesite"), Feature.ORE.configured(new OreConfiguration(OreFeatures.NATURAL_STONE, Blocks.ANDESITE.defaultBlockState(), 16)).placed(RarityFilter.onAverageOnceEvery(60), InSquarePlacement.spread(), CountPlacement.of(5)));

    //Dark Forest needs special placements, so here we go
    public static final PlacedFeature DARK_MUSHGLOOMS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_mushglooms"), TFBiomeFeatures.DARK_FOREST_PLACER.get().configured(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlockConstants.MUSHGLOOM))), List.of(BlockConstants.GRASS_BLOCK.getBlock()), 50)).placed(RarityFilter.onAverageOnceEvery(30), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
    public static final PlacedFeature DARK_PUMPKINS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_pumpkins"), TFBiomeFeatures.DARK_FOREST_PLACER.get().configured(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlockConstants.PUMPKIN))), List.of(BlockConstants.GRASS_BLOCK.getBlock()), 50)).placed(RarityFilter.onAverageOnceEvery(30), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
    public static final PlacedFeature DARK_GRASS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_grass"), TFBiomeFeatures.DARK_FOREST_PLACER.get().configured(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlockConstants.GRASS))), List.of(BlockConstants.GRASS_BLOCK.getBlock()), 128)).placed(RarityFilter.onAverageOnceEvery(4), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
    public static final PlacedFeature DARK_FERNS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_ferns"), TFBiomeFeatures.DARK_FOREST_PLACER.get().configured(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlockConstants.FERN))), List.of(BlockConstants.GRASS_BLOCK.getBlock()), 128)).placed(RarityFilter.onAverageOnceEvery(4), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
    public static final PlacedFeature DARK_MUSHROOMS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_mushrooms"), TFBiomeFeatures.DARK_FOREST_PLACER.get().configured(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlockConstants.BROWN_MUSHROOM))), List.of(BlockConstants.GRASS_BLOCK.getBlock()), 50)).placed(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
    public static final PlacedFeature DARK_DEAD_BUSHES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_dead_bushes"), TFBiomeFeatures.DARK_FOREST_PLACER.get().configured(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(BlockConstants.DEAD_BUSH))), List.of(BlockConstants.GRASS_BLOCK.getBlock()), 50)).placed(RarityFilter.onAverageOnceEvery(15), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));

    private static ImmutableList.Builder<PlacementModifier> tfFeatureCheckArea(OutOfStructureFilter filter, int rarity) {
        return ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, filter, BiomeFilter.biome());
    }

    private static ImmutableList.Builder<PlacementModifier> tfFeatureCheckArea(OutOfStructureFilter filter, int rarity, PlacementModifier... extra) {
        return ImmutableList.<PlacementModifier>builder().add(extra).add(filter, RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    }

    //ground decoration
    public static final RandomPatchConfiguration SMALL_FLOWER_CONFIG = (new RandomPatchConfiguration(32, 7, 7, () ->
            Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(
                    new NoiseProvider(2345L,
                            new NormalNoise.NoiseParameters(0, 1.0D), 0.020833334F,
                            List.of(
                                    BlockConstants.POPPY,
                                    BlockConstants.DANDELION,
                                    BlockConstants.RED_TULIP,
                                    BlockConstants.ORANGE_TULIP,
                                    BlockConstants.PINK_TULIP,
                                    BlockConstants.WHITE_TULIP,
                                    BlockConstants.CORNFLOWER,
                                    BlockConstants.LILY,
                                    BlockConstants.ORCHID,
                                    BlockConstants.ALLIUM,
                                    BlockConstants.AZURE,
                                    BlockConstants.OXEYE)
                    ))).onlyWhenEmpty()));

    public static final RandomPatchConfiguration FOREST_GRASS = (new RandomPatchConfiguration(64, 7, 7, () ->
            Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(
                    new NoiseProvider(2345L,
                            new NormalNoise.NoiseParameters(0, 1.0D), 0.020833334F,
                            List.of(
                                    BlockConstants.MAYAPPLE,
                                    BlockConstants.GRASS,
                                    BlockConstants.TALL_GRASS,
                                    BlockConstants.FERN,
                                    BlockConstants.TALL_FERN)
                    ))).onlyWhenEmpty()));

    public static final RandomPatchConfiguration OTHER_GRASS = (new RandomPatchConfiguration(64, 7, 7, () ->
            Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(
                    new NoiseProvider(2345L,
                            new NormalNoise.NoiseParameters(0, 1.0D), 0.020833334F,
                            List.of(
                                    BlockConstants.GRASS,
                                    BlockConstants.TALL_GRASS,
                                    BlockConstants.FERN,
                                    BlockConstants.TALL_FERN)
                    ))).onlyWhenEmpty()));

    public static final PlacedFeature GRASS_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("grass_placer"), Feature.RANDOM_PATCH.configured(OTHER_GRASS).placed(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, CountPlacement.of(30), InSquarePlacement.spread(), RarityFilter.onAverageOnceEvery(3)));
    public static final PlacedFeature FOREST_GRASS_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("forest_grass_placer"), Feature.RANDOM_PATCH.configured(FOREST_GRASS).placed(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, CountPlacement.of(30), InSquarePlacement.spread(), RarityFilter.onAverageOnceEvery(3)));
    public static final PlacedFeature FLOWER_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("flower_placer"), Feature.FLOWER.configured(SMALL_FLOWER_CONFIG).placed(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, CountPlacement.of(25), InSquarePlacement.spread(), RarityFilter.onAverageOnceEvery(15)));

    //music!
    public static final Music TFMUSICTYPE = new Music(TFSounds.MUSIC, 1200, 12000, true);
}