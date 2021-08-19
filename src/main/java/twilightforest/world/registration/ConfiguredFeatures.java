package twilightforest.world.registration;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.data.worldgen.Features;
import net.minecraft.sounds.Music;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.DoublePlantPlacer;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.ConfiguredDecorator;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.placement.FrequencyWithExtraChanceDecoratorConfiguration;
import twilightforest.TFConfig;
import twilightforest.TFSounds;
import twilightforest.TwilightForestMod;
import twilightforest.world.components.feature.config.CaveStalactiteConfig;
import twilightforest.world.components.feature.config.TFTreeFeatureConfig;

import java.util.function.Supplier;

public final class ConfiguredFeatures {
    // Base configurations
    public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> TWILIGHT_OAK_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/twilight_oak"), Feature.TREE.configured(TreeConfigurations.TWILIGHT_OAK));
    public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> SWAMPY_OAK_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/swampy_oak"), Feature.TREE.configured(TreeConfigurations.SWAMPY_OAK));
    public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> CANOPY_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/canopy_tree"), Feature.TREE.configured(TreeConfigurations.CANOPY_TREE));
    public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> FIREFLY_CANOPY_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/firefly_canopy_tree"), Feature.TREE.configured(TreeConfigurations.CANOPY_TREE_FIREFLY));
    public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> DEAD_CANOPY_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/dead_canopy_tree"), Feature.TREE.configured(TreeConfigurations.CANOPY_TREE_DEAD));
    public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> MANGROVE_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/mangrove_tree"), Feature.TREE.configured(TreeConfigurations.MANGROVE_TREE));
    public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> DARKWOOD_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/darkwood_tree"), TFBiomeFeatures.DARK_CANOPY_TREE.get().configured(TreeConfigurations.DARKWOOD_TREE));
    public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> DARKWOOD_LANTERN_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/darkwood_lantern_tree"), TFBiomeFeatures.DARK_CANOPY_TREE.get().configured(TreeConfigurations.DARKWOOD_LANTERN_TREE));
    public static final ConfiguredFeature<TFTreeFeatureConfig, ? extends Feature<?>> TIME_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/time_tree"), TFBiomeFeatures.TREE_OF_TIME.get().configured(TreeConfigurations.TIME_TREE));
    public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> TRANSFORM_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/transform_tree"), Feature.TREE.configured(TreeConfigurations.TRANSFORM_TREE));
    public static final ConfiguredFeature<TFTreeFeatureConfig, ? extends Feature<?>> MINING_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/mining_tree"), TFBiomeFeatures.MINERS_TREE.get().configured(TreeConfigurations.MINING_TREE));
    public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> SORT_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/sort_tree"), Feature.TREE.configured(TreeConfigurations.SORT_TREE));
    public static final ConfiguredFeature<TFTreeFeatureConfig, ? extends Feature<?>> DENSE_OAK_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/dense_oak_tree"), TFBiomeFeatures.CANOPY_OAK.get().configured(TreeConfigurations.DENSE_OAK));
    public static final ConfiguredFeature<TFTreeFeatureConfig, ? extends Feature<?>> HOLLOW_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/hollow_tree"), TFBiomeFeatures.HOLLOW_TREE.get().configured(TreeConfigurations.HOLLOW_TREE));
    public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> RAINBOW_OAK_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/rainbow_oak"), Feature.TREE.configured(TreeConfigurations.RAINBOAK_TREE));
    public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> LARGE_RAINBOW_OAK_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/large_rainbow_oak"), Feature.TREE.configured(TreeConfigurations.LARGE_RAINBOAK_TREE));
    public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> BROWN_CANOPY_MUSHROOM_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mushroom/base/brown_canopy_mushroom"), Feature.TREE.configured(TreeConfigurations.MUSHROOM_BROWN));
    public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> RED_CANOPY_MUSHROOM_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mushroom/base/red_canopy_mushroom"), Feature.TREE.configured(TreeConfigurations.MUSHROOM_RED));
    public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> MEGA_SPRUCE_NO_PODZOL_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/mega_spruce_no_podzol"), TFBiomeFeatures.SNOW_TREE.get().configured(TreeConfigurations.BIG_SPRUCE));
    private static final ConfiguredDecorator<?> DEFAULT_TREE_PLACEMENT = TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE_WITHHEIGHTMAP.squared();/*.withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(8, 0.4F, 4)));*/ // FIXME: Execution order = Outer -> Inner (Last -> First); COUNT_EXTRA was first meaning it did nothing, so now properly placing it last causes mayhem. Reenable this and fix all the random values to match 1.12 again
    //Spruce trees that can be placed on snow
    public static final ConfiguredFeature<TreeConfiguration, ?> SNOW_SPRUCE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("snow_spruce"), TFBiomeFeatures.SNOW_TREE.get().configured(Features.SPRUCE.config()));
    public static final ConfiguredFeature<?, ?> SNOW_SPRUCE_SNOWY = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("snow_spruce_snowy"), SNOW_SPRUCE.decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.1F, 1))));
    //Dark Forest
    public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> OAK_DARK_FOREST = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/oak_dark_forest"), TFBiomeFeatures.DARK_CANOPY_TREE.get().configured(Features.OAK.config()));
    public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> BIRCH_DARK_FOREST = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/birch_dark_forest"), TFBiomeFeatures.DARK_CANOPY_TREE.get().configured(Features.BIRCH.config()));
    //fun, can't inherit the config so just have to copy-paste it :)
    public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> BUSH_DARK_FOREST = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/bush_dark_forest"), TFBiomeFeatures.DARK_CANOPY_TREE.get().configured((new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(BlockConstants.OAK_LOG), new StraightTrunkPlacer(1, 0, 0), new SimpleStateProvider(BlockConstants.OAK_LEAVES), new SimpleStateProvider(BlockConstants.OAK_SAPLING), new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2), new TwoLayersFeatureSize(0, 0, 0))).build()));

    //Trees!
    public static final ConfiguredFeature<?, ?> CANOPY_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/canopy_trees"), CANOPY_TREE_BASE.decorated(DEFAULT_TREE_PLACEMENT));
    public static final ConfiguredFeature<?, ?> FIREFLY_CANOPY_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/firefly_canopy_trees"), FIREFLY_CANOPY_TREE_BASE.decorated(DEFAULT_TREE_PLACEMENT));
    public static final ConfiguredFeature<?, ?> FIREFLY_CANOPY_TREE_MIX = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/firefly_canopy_tree_mix"), Feature.SIMPLE_RANDOM_SELECTOR.configured(new SimpleRandomFeatureConfiguration(ImmutableList.of(() -> CANOPY_TREE_BASE, () -> FIREFLY_CANOPY_TREE_BASE))).decorated(DEFAULT_TREE_PLACEMENT));
    public static final ConfiguredFeature<?, ?> DEAD_CANOPY_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/dead_canopy_trees"), DEAD_CANOPY_TREE_BASE.decorated(DEFAULT_TREE_PLACEMENT));
    public static final ConfiguredFeature<?, ?> RAINBOW_OAK_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/rainbow_oak_trees"), RAINBOW_OAK_TREE_BASE.decorated(DEFAULT_TREE_PLACEMENT));
    public static final ConfiguredFeature<?, ?> LARGE_RAINBOW_OAK_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/large_rainbow_oak_trees"), LARGE_RAINBOW_OAK_BASE.decorated(DEFAULT_TREE_PLACEMENT));
    public static final ConfiguredFeature<?, ?> MANGROVE_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/mangrove_trees"), MANGROVE_TREE_BASE.decorated(DEFAULT_TREE_PLACEMENT));
    public static final ConfiguredFeature<?, ?> DARKWOOD_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/darkwood_trees"), DARKWOOD_TREE_BASE.decorated(DEFAULT_TREE_PLACEMENT).count(2).countRandom(2));
    public static final ConfiguredFeature<?, ?> DARKWOOD_LANTERN_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/darkwood_lantern_trees"), DARKWOOD_LANTERN_TREE_BASE.decorated(DEFAULT_TREE_PLACEMENT).count(4).countRandom(2));
    public static final ConfiguredFeature<?, ?> TWILIGHT_OAK_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/twilight_oak_trees"), TWILIGHT_OAK_BASE.decorated(DEFAULT_TREE_PLACEMENT).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(3, 0.6F, 1))));
    public static final ConfiguredFeature<?, ?> SAVANNAH_OAK_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/savannah_oak_trees"), TWILIGHT_OAK_BASE.decorated(DEFAULT_TREE_PLACEMENT));
    public static final ConfiguredFeature<?, ?> SWAMPY_OAK_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/swampy_oak_trees"), SWAMPY_OAK_BASE.decorated(DEFAULT_TREE_PLACEMENT).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(3, 0.6F, 1))));
    public static final ConfiguredFeature<?, ?> HOLLOW_OAK_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/hollow_oak_trees"), HOLLOW_TREE_BASE.decorated(DEFAULT_TREE_PLACEMENT).decorated(Features.Decorators.HEIGHTMAP_SQUARE));
    public static final ConfiguredFeature<?, ?> MEGA_SPRUCE_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/mega_spruce_no_podzol"), MEGA_SPRUCE_NO_PODZOL_BASE.decorated(DEFAULT_TREE_PLACEMENT).squared().countRandom(4));
    public static final ConfiguredFeature<?, ?> OAK_DARK_FOREST_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/oak_dark_forest_trees"), OAK_DARK_FOREST.decorated(DEFAULT_TREE_PLACEMENT).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(2, 0.6F, 1))).squared());
    public static final ConfiguredFeature<?, ?> BIRCH_DARK_FOREST_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/birch_dark_forest_trees"), BIRCH_DARK_FOREST.decorated(DEFAULT_TREE_PLACEMENT).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(2, 0.6F, 1))).squared());
    public static final ConfiguredFeature<?, ?> BUSH_DARK_FOREST_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/bush_dark_forest_trees"), BUSH_DARK_FOREST.decorated(DEFAULT_TREE_PLACEMENT).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(2, 0.6F, 1))).squared());

    //"structures" that arent actually structures
    public static final ConfiguredFeature<?, ?> WELL = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("well"), TFBiomeFeatures.WELL.get().configured(FeatureConfiguration.NONE).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).decorated(Features.Decorators.HEIGHTMAP_SQUARE));
    public static final ConfiguredFeature<?, ?> DRUID_HUT = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("druid_hut"), TFBiomeFeatures.DRUID_HUT.get().configured(FeatureConfiguration.NONE).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).decorated(Features.Decorators.HEIGHTMAP_SQUARE));
    public static final ConfiguredFeature<?, ?> GRAVEYARD = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("graveyard"), TFBiomeFeatures.GRAVEYARD.get().configured(FeatureConfiguration.NONE).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).rarity(95));

    //all the fun little things you find around the dimension
    public static final ConfiguredFeature<?, ?> BIG_MUSHGLOOM = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("big_mushgloom"), TFBiomeFeatures.BIG_MUSHGLOOM.get().configured(FeatureConfiguration.NONE).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).squared());
    public static final ConfiguredFeature<?, ?> FALLEN_LEAVES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("fallen_leaves"), TFBiomeFeatures.FALLEN_LEAVES.get().configured(FeatureConfiguration.NONE).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).squared());
    public static final ConfiguredFeature<?, ?> FIDDLEHEAD = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("fiddlehead"), Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlockConstants.FIDDLEHEAD), SimpleBlockPlacer.INSTANCE)).tries(16).build()));
    public static final ConfiguredFeature<?, ?> FIRE_JET = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("fire_jet"), TFBiomeFeatures.FIRE_JET.get().configured(new BlockStateConfiguration(BlockConstants.FIRE_JET)).decorated(Features.Decorators.HEIGHTMAP_SQUARE));
    public static final ConfiguredFeature<?, ?> FOUNDATION = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("foundation"), TFBiomeFeatures.FOUNDATION.get().configured(FeatureConfiguration.NONE).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).squared());
    public static final ConfiguredFeature<?, ?> GROVE_RUINS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("grove_ruins"), TFBiomeFeatures.GROVE_RUINS.get().configured(FeatureConfiguration.NONE).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).squared());
    public static final ConfiguredFeature<?, ?> HOLLOW_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("hollow_log"), TFBiomeFeatures.FALLEN_HOLLOW_LOG.get().configured(FeatureConfiguration.NONE).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).squared());
    public static final ConfiguredFeature<?, ?> HOLLOW_STUMP = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("hollow_stump"), TFBiomeFeatures.HOLLOW_STUMP.get().configured(TreeConfigurations.HOLLOW_TREE).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).squared());
    public static final ConfiguredFeature<?, ?> HUGE_LILY_PAD = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("huge_lily_pad"), TFBiomeFeatures.HUGE_LILY_PAD.get().configured(FeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).squared());
    public static final ConfiguredFeature<?, ?> HUGE_WATER_LILY = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("huge_water_lily"), TFBiomeFeatures.HUGE_WATER_LILY.get().configured(FeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).squared());
    public static final ConfiguredFeature<?, ?> LAMPPOST = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("lamppost"), TFBiomeFeatures.LAMPPOSTS.get().configured(new BlockStateConfiguration(BlockConstants.FIREFLY_JAR)).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).decorated(Features.Decorators.HEIGHTMAP_WORLD_SURFACE).squared());
    public static final ConfiguredFeature<?, ?> MONOLITH = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("monolith"), TFBiomeFeatures.MONOLITH.get().configured(FeatureConfiguration.NONE).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).squared().count(1));
    public static final ConfiguredFeature<?, ?> MUSHGLOOM_CLUSTER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mushgloom_cluster"), Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlockConstants.MUSHGLOOM), SimpleBlockPlacer.INSTANCE)).tries(8).build()));
    public static final ConfiguredFeature<?, ?> MYCELIUM_BLOB = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mycelium_blob"), TFBiomeFeatures.MYCELIUM_BLOB.get().configured(new DiskConfiguration(BlockConstants.MYCELIUM, UniformInt.of(4, 6), 3, ImmutableList.of(BlockConstants.GRASS_BLOCK))).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE).squared());
    public static final ConfiguredFeature<?, ?> OUTSIDE_STALAGMITE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("outside_stalagmite"), TFBiomeFeatures.OUTSIDE_STALAGMITE.get().configured(new CaveStalactiteConfig(BlockConstants.STONE, 3, 5, 10, false)).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).squared());
    public static final ConfiguredFeature<?, ?> PLANT_ROOTS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("plant_roots"), TFBiomeFeatures.PLANT_ROOTS.get().configured(FeatureConfiguration.NONE).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).squared().count(4));
    public static final ConfiguredFeature<?, ?> PUMPKIN_LAMPPOST = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("pumpkin_lamppost"), TFBiomeFeatures.LAMPPOSTS.get().configured(new BlockStateConfiguration(BlockConstants.JACK_O_LANTERN)).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).decorated(Features.Decorators.HEIGHTMAP_WORLD_SURFACE).squared());
    public static final ConfiguredFeature<?, ?> SMALL_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("small_log"), TFBiomeFeatures.FALLEN_SMALL_LOG.get().configured(FeatureConfiguration.NONE).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).squared());
    public static final ConfiguredFeature<?, ?> SMOKER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("smoker"), TFBiomeFeatures.FIRE_JET.get().configured(new BlockStateConfiguration(BlockConstants.SMOKER)).decorated(Features.Decorators.HEIGHTMAP_SQUARE));
    public static final ConfiguredFeature<?, ?> STONE_CIRCLE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("stone_circle"), TFBiomeFeatures.STONE_CIRCLE.get().configured(FeatureConfiguration.NONE).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).squared().count(1));
    public static final ConfiguredFeature<?, ?> THORNS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("thorns"), TFBiomeFeatures.THORNS.get().configured(FeatureConfiguration.NONE).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).rarity(95).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(50).countRandom(85));
    public static final ConfiguredFeature<?, ?> TORCH_BERRIES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("torch_berries"), TFBiomeFeatures.TORCH_BERRIES.get().configured(FeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).squared().count(4));
    public static final ConfiguredFeature<?, ?> TROLL_ROOTS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("troll_roots"), TFBiomeFeatures.TROLL_ROOTS.get().configured(FeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).squared().count(4).countRandom(8));
    public static final ConfiguredFeature<?, ?> WEBS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("webs"), TFBiomeFeatures.WEBS.get().configured(FeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).squared().count(60));
    public static final ConfiguredFeature<?, ?> WOOD_ROOTS_SPREAD = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("ore/wood_roots_spread"), TFBiomeFeatures.WOOD_ROOTS.get().configured(FeatureConfiguration.NONE).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).rarity(30).squared().count(20));
    public static final ConfiguredFeature<?, ?> SNOW_UNDER_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("snow_under_trees"), TFBiomeFeatures.SNOW_UNDER_TREES.get().configured(FeatureConfiguration.NONE));
    //smol stone veins
    public static final ConfiguredFeature<?, ?> SMALL_GRANITE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("small_granite"), Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, Blocks.GRANITE.defaultBlockState(), 16)).rarity(60).squared().count(5));
    public static final ConfiguredFeature<?, ?> SMALL_DIORITE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("small_diorite"), Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, Blocks.DIORITE.defaultBlockState(), 16)).rarity(60).squared().count(5));
    public static final ConfiguredFeature<?, ?> SMALL_ANDESITE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("small_andesite"), Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, Blocks.ANDESITE.defaultBlockState(), 16)).rarity(60).squared().count(5));
    //Dark Forest needs special placements, so here we go
    public static final ConfiguredFeature<?, ?> DARK_MUSHGLOOMS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_mushglooms"), TFBiomeFeatures.DARK_FOREST_PLACER.get().configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlockConstants.MUSHGLOOM), SimpleBlockPlacer.INSTANCE)).whitelist(ImmutableSet.of(BlockConstants.GRASS_BLOCK.getBlock())).tries(64).noProjection().build()).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE).rarity(10));
    public static final ConfiguredFeature<?, ?> DARK_PUMPKINS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_pumpkins"), TFBiomeFeatures.DARK_FOREST_PLACER.get().configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlockConstants.PUMPKIN), SimpleBlockPlacer.INSTANCE)).whitelist(ImmutableSet.of(BlockConstants.GRASS_BLOCK.getBlock())).tries(16).noProjection().build()).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE).rarity(30));
    public static final ConfiguredFeature<?, ?> DARK_GRASS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_grass"), TFBiomeFeatures.DARK_FOREST_PLACER.get().configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlockConstants.GRASS), SimpleBlockPlacer.INSTANCE)).whitelist(ImmutableSet.of(BlockConstants.GRASS_BLOCK.getBlock())).tries(128).noProjection().build()).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE).count(4));
    public static final ConfiguredFeature<?, ?> DARK_FERNS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_ferns"), TFBiomeFeatures.DARK_FOREST_PLACER.get().configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlockConstants.FERN), SimpleBlockPlacer.INSTANCE)).whitelist(ImmutableSet.of(BlockConstants.GRASS_BLOCK.getBlock())).tries(128).noProjection().build()).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE).count(4));
    public static final ConfiguredFeature<?, ?> DARK_MUSHROOMS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_mushrooms"), TFBiomeFeatures.DARK_FOREST_PLACER.get().configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlockConstants.BROWN_MUSHROOM), SimpleBlockPlacer.INSTANCE)).whitelist(ImmutableSet.of(BlockConstants.GRASS_BLOCK.getBlock())).tries(64).noProjection().build()).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE));
    public static final ConfiguredFeature<?, ?> DARK_DEAD_BUSHES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_dead_bushes"), TFBiomeFeatures.DARK_FOREST_PLACER.get().configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlockConstants.DEAD_BUSH), SimpleBlockPlacer.INSTANCE)).whitelist(ImmutableSet.of(BlockConstants.GRASS_BLOCK.getBlock())).tries(32).noProjection().build()).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE).rarity(15));

    //Since we use some vanilla features, we have to register some of them as our own with our own placements
    public static final ConfiguredFeature<?, ?> VANILLA_TF_OAK = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/vanilla/vanilla_tf_oak"), Feature.TREE.configured(Features.OAK.config()).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).decorated(Features.Decorators.HEIGHTMAP_SQUARE));
    public static final ConfiguredFeature<?, ?> VANILLA_TF_BIRCH = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/vanilla/vanilla_tf_birch"), Feature.TREE.configured(Features.BIRCH.config()).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).decorated(Features.Decorators.HEIGHTMAP_SQUARE));
    public static final ConfiguredFeature<?, ?> VANILLA_TF_BIG_MUSH = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/vanilla/vanilla_tf_big_mush"), Feature.RANDOM_BOOLEAN_SELECTOR.configured(new RandomBooleanFeatureConfiguration(
            () -> Features.HUGE_RED_MUSHROOM,
            () -> Features.HUGE_BROWN_MUSHROOM
            )).decorated(TwilightFeatures.CONFIGURED_PLACEMENT_NOTFSTRUCTURE).decorated(Features.Decorators.HEIGHTMAP_SQUARE));

    //tree and mushroom placements
    public static final ConfiguredFeature<?, ?> CANOPY_MUSHROOMS_SPARSE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mushroom/canopy_mushrooms_sparse"),
            Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(
                    ConfiguredFeatures.BROWN_CANOPY_MUSHROOM_TREE_BASE.decorated(DEFAULT_TREE_PLACEMENT).weighted(0.15f),
                    ConfiguredFeatures.RED_CANOPY_MUSHROOM_TREE_BASE.decorated(DEFAULT_TREE_PLACEMENT).weighted(0.05f)
            ), ConfiguredFeatures.CANOPY_TREE_BASE.decorated(DEFAULT_TREE_PLACEMENT)))
                    .decorated(Features.Decorators.HEIGHTMAP_WORLD_SURFACE)
                    .squared()
                    .count(8)
    );

    public static final ConfiguredFeature<?, ?> CANOPY_MUSHROOMS_DENSE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mushroom/canopy_mushrooms_dense"),
            Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(
                    ConfiguredFeatures.BROWN_CANOPY_MUSHROOM_TREE_BASE.decorated(DEFAULT_TREE_PLACEMENT).weighted(0.675f),
                    ConfiguredFeatures.RED_CANOPY_MUSHROOM_TREE_BASE.decorated(DEFAULT_TREE_PLACEMENT).weighted(0.225f)
            ), ConfiguredFeatures.CANOPY_TREE_BASE.decorated(DEFAULT_TREE_PLACEMENT)))
                    .decorated(Features.Decorators.HEIGHTMAP_WORLD_SURFACE)
                    .squared()
                    .count(8)
    );

    public static final ConfiguredFeature<?, ?> OAK_TREES_SPARSE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/oak_trees_sparse"),
            Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(
                    ConfiguredFeatures.HOLLOW_OAK_TREES.weighted(0.05f),
                    ConfiguredFeatures.SAVANNAH_OAK_TREES.weighted(0.35f)
            ), Feature.NO_OP.configured(NoneFeatureConfiguration.INSTANCE)))
                    .decorated(Features.Decorators.HEIGHTMAP_WORLD_SURFACE)
                    .squared()
                    .count(1)
    );
    //why does it only place hollow trees this way?
    public static final ConfiguredFeature<?, ?> HOLLOW_TREE_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/hollow_placer"),
            Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(
                    ConfiguredFeatures.HOLLOW_OAK_TREES.weighted(0.01f),
                    ConfiguredFeatures.HOLLOW_OAK_TREES.weighted(0.01f)
            ), Feature.NO_OP.configured(NoneFeatureConfiguration.INSTANCE)))
                    .decorated(Features.Decorators.HEIGHTMAP_SQUARE)
                    .count(1)
    );

    //selects a random feature to place down. This makes things more random and rare
    //List first
    private static TFConfig.Common.Dimension.WorldGenWeights weights = TFConfig.COMMON_CONFIG.DIMENSION.worldGenWeights;
    private static final ImmutableList<Supplier<ConfiguredFeature<?, ?>>> COMMON_FEATURES = ImmutableList.of(
            () -> DRUID_HUT.rarity(weights.druidHutWeight.get() / 4), //make this a higher rarity because theyre SUPER rare otherwise
            () -> WELL.rarity(weights.wellWeight.get() / 2),
            () -> GROVE_RUINS.rarity(weights.groveRuinsWeight.get() / 2),
            () -> MONOLITH.rarity(weights.monolithWeight.get() / 2),
            () -> OUTSIDE_STALAGMITE.rarity(weights.stalagmiteWeight.get() / 2),
            () -> STONE_CIRCLE.rarity(weights.stoneCircleWeight.get() / 2),
            () -> FOUNDATION.rarity(weights.foundationWeight.get() / 2),
            () -> HOLLOW_LOG.rarity(weights.fallenHollowLogWeight.get() / 2),
            () -> HOLLOW_STUMP.rarity(weights.hollowStumpWeight.get() / 2),
            () -> SMALL_LOG.rarity(weights.fallenSmallLogWeight.get() / 2));

    public static final ConfiguredFeature<?, ?> RANDOM_COMMON_FEATURE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("random_common"),
            Feature.SIMPLE_RANDOM_SELECTOR.configured(new SimpleRandomFeatureConfiguration(COMMON_FEATURES))
                    .decorated(Features.Decorators.HEIGHTMAP_SQUARE)
                    .squared()
    );

    public static final ConfiguredFeature<?, ?> RANDOM_FALLEN_FEATURE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("random_fallen"),
            Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(
                    FOUNDATION.weighted(0.05F),
                    HOLLOW_LOG.weighted(0.05F),
                    HOLLOW_STUMP.weighted(0.05F),
                    SMALL_LOG.weighted(0.1F)
            ), Feature.NO_OP.configured(NoneFeatureConfiguration.INSTANCE)))
                    .decorated(Features.Decorators.HEIGHTMAP_SQUARE)
                    .squared()
                    .count(1)
    );

    public static final ConfiguredFeature<?, ?> RANDOM_WATER_FEATURE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("random_water"),
            Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(
                    HUGE_LILY_PAD.weighted(0.50F),
                    HUGE_WATER_LILY.weighted(0.20F)
            ), Features.PATCH_WATERLILLY))
                    .decorated(Features.Decorators.HEIGHTMAP_SQUARE)
                    .squared()
                    .count(5)
    );

    //ground decoration
    public static final RandomPatchConfiguration SMALL_FLOWER_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder((new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
            .add(BlockConstants.POPPY, 1)
            .add(BlockConstants.DANDELION, 1)
            .add(BlockConstants.RED_TULIP, 1)
            .add(BlockConstants.ORANGE_TULIP, 1)
            .add(BlockConstants.PINK_TULIP, 1)
            .add(BlockConstants.WHITE_TULIP, 1)
            .add(BlockConstants.CORNFLOWER, 1)
            .add(BlockConstants.LILY, 1)
            .add(BlockConstants.ORCHID, 1)
            .add(BlockConstants.ALLIUM, 1)
            .add(BlockConstants.AZURE, 1)
            .add(BlockConstants.OXEYE, 1).build())),
            SimpleBlockPlacer.INSTANCE))
            .tries(128)
            .build();

    public static final ImmutableList<Supplier<ConfiguredFeature<?, ?>>> FOREST_GRASS = ImmutableList.of(
            () -> Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlockConstants.MAYAPPLE), SimpleBlockPlacer.INSTANCE)).tries(4).build()),
            () -> Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlockConstants.GRASS), SimpleBlockPlacer.INSTANCE)).tries(64).build()),
            () -> Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlockConstants.TALL_GRASS), DoublePlantPlacer.INSTANCE)).tries(32).noProjection().build()),
            () -> Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlockConstants.FERN), SimpleBlockPlacer.INSTANCE)).tries(32).build()),
            () -> Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlockConstants.TALL_FERN), DoublePlantPlacer.INSTANCE)).tries(16).noProjection().build()));

    public static final ImmutableList<Supplier<ConfiguredFeature<?, ?>>> OTHER_GRASS = ImmutableList.of(
            () -> Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlockConstants.GRASS), SimpleBlockPlacer.INSTANCE)).tries(64).build()),
            () -> Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlockConstants.TALL_GRASS), DoublePlantPlacer.INSTANCE)).tries(32).noProjection().build()),
            () -> Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlockConstants.FERN), SimpleBlockPlacer.INSTANCE)).tries(32).build()),
            () -> Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlockConstants.TALL_FERN), DoublePlantPlacer.INSTANCE)).tries(16).noProjection().build()));

    public static final ConfiguredFeature<?, ?> GRASS_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("grass_placer"), Feature.SIMPLE_RANDOM_SELECTOR.configured(new SimpleRandomFeatureConfiguration(OTHER_GRASS)).count(UniformInt.of(1, 4)).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).squared().count(30).rarity(5));
    public static final ConfiguredFeature<?, ?> FOREST_GRASS_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("forest_grass_placer"), Feature.SIMPLE_RANDOM_SELECTOR.configured(new SimpleRandomFeatureConfiguration(FOREST_GRASS)).count(UniformInt.of(1, 4)).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).squared().count(30).rarity(5));
    public static final ConfiguredFeature<?, ?> FLOWER_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("flower_placer"), Feature.FLOWER.configured(SMALL_FLOWER_CONFIG).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(25).rarity(15));

    //music!
    public static final Music TFMUSICTYPE = new Music(TFSounds.MUSIC, 1200, 12000, true);
}