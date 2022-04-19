package twilightforest.world.registration.features;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.sounds.Music;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.TFSounds;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.block.TorchberryPlantBlock;
import twilightforest.world.components.feature.config.HollowLogConfig;
import twilightforest.world.components.feature.config.SpikeConfig;
import twilightforest.world.components.feature.config.TFTreeFeatureConfig;
import twilightforest.world.components.feature.config.ThornsConfig;
import twilightforest.world.registration.TFBiomeFeatures;
import twilightforest.world.registration.TreeConfigurations;

import java.util.List;

public final class TFConfiguredFeatures {

	public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, TwilightForestMod.ID);
	
	//vanilla features with custom placement code
	public static final RegistryObject<ConfiguredFeature<LakeFeature.Configuration, ?>> LAKE_LAVA = CONFIGURED_FEATURES.register("lava_lake", () -> new ConfiguredFeature<>(Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(Blocks.LAVA.defaultBlockState()), BlockStateProvider.simple(Blocks.STONE))));
	public static final RegistryObject<ConfiguredFeature<LakeFeature.Configuration, ?>> LAKE_WATER = CONFIGURED_FEATURES.register("water_lake", () -> new ConfiguredFeature<>(Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(Blocks.WATER), BlockStateProvider.simple(Blocks.STONE))));

	//"structures" that arent actually structures
	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> SIMPLE_WELL = CONFIGURED_FEATURES.register("simple_well", () -> new ConfiguredFeature<>(TFBiomeFeatures.SIMPLE_WELL.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> FANCY_WELL = CONFIGURED_FEATURES.register("fancy_well", () -> new ConfiguredFeature<>(TFBiomeFeatures.FANCY_WELL.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> DRUID_HUT = CONFIGURED_FEATURES.register("druid_hut", () -> new ConfiguredFeature<>(TFBiomeFeatures.DRUID_HUT.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> GRAVEYARD = CONFIGURED_FEATURES.register("graveyard", () -> new ConfiguredFeature<>(TFBiomeFeatures.GRAVEYARD.get(), FeatureConfiguration.NONE));

	//all the fun little things you find around the dimension
	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> BIG_MUSHGLOOM = CONFIGURED_FEATURES.register("big_mushgloom", () -> new ConfiguredFeature<>(TFBiomeFeatures.BIG_MUSHGLOOM.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> FALLEN_LEAVES = CONFIGURED_FEATURES.register("fallen_leaves", () -> new ConfiguredFeature<>(TFBiomeFeatures.FALLEN_LEAVES.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FIDDLEHEAD = CONFIGURED_FEATURES.register("fiddlehead", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(TFBlocks.FIDDLEHEAD.get())))));
	public static final RegistryObject<ConfiguredFeature<BlockStateConfiguration, ?>> FIRE_JET = CONFIGURED_FEATURES.register("fire_jet", () -> new ConfiguredFeature<>(TFBiomeFeatures.FIRE_JET.get(), new BlockStateConfiguration(TFBlocks.FIRE_JET.get().defaultBlockState())));
	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> FOUNDATION = CONFIGURED_FEATURES.register("foundation", () -> new ConfiguredFeature<>(TFBiomeFeatures.FOUNDATION.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> GROVE_RUINS = CONFIGURED_FEATURES.register("grove_ruins", () -> new ConfiguredFeature<>(TFBiomeFeatures.GROVE_RUINS.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> HOLLOW_LOG = CONFIGURED_FEATURES.register("hollow_log", () -> new ConfiguredFeature<>(TFBiomeFeatures.FALLEN_HOLLOW_LOG.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<TFTreeFeatureConfig, ?>> HOLLOW_STUMP = CONFIGURED_FEATURES.register("hollow_stump", () -> new ConfiguredFeature<>(TFBiomeFeatures.HOLLOW_STUMP.get(), TreeConfigurations.HOLLOW_TREE));
	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> HUGE_LILY_PAD = CONFIGURED_FEATURES.register("huge_lily_pad", () -> new ConfiguredFeature<>(TFBiomeFeatures.HUGE_LILY_PAD.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> HUGE_WATER_LILY = CONFIGURED_FEATURES.register("huge_water_lily", () -> new ConfiguredFeature<>(TFBiomeFeatures.HUGE_WATER_LILY.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<BlockStateConfiguration, ?>> CICADA_LAMPPOST = CONFIGURED_FEATURES.register("cicada_lamppost", () -> new ConfiguredFeature<>(TFBiomeFeatures.LAMPPOSTS.get(), new BlockStateConfiguration(TFBlocks.CICADA_JAR.get().defaultBlockState())));
	public static final RegistryObject<ConfiguredFeature<BlockStateConfiguration, ?>> FIREFLY_LAMPPOST = CONFIGURED_FEATURES.register("firefly_lamppost", () -> new ConfiguredFeature<>(TFBiomeFeatures.LAMPPOSTS.get(), new BlockStateConfiguration(TFBlocks.FIREFLY_JAR.get().defaultBlockState())));
	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> MONOLITH = CONFIGURED_FEATURES.register("monolith", () -> new ConfiguredFeature<>(TFBiomeFeatures.MONOLITH.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> MUSHGLOOM_CLUSTER = CONFIGURED_FEATURES.register("mushgloom_cluster", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(TFBlocks.MUSHGLOOM.get())))));
	public static final RegistryObject<ConfiguredFeature<DiskConfiguration, ?>> MYCELIUM_BLOB = CONFIGURED_FEATURES.register("mycelium_blob", () -> new ConfiguredFeature<>(TFBiomeFeatures.MYCELIUM_BLOB.get(), new DiskConfiguration(Blocks.MYCELIUM.defaultBlockState(), UniformInt.of(4, 6), 3, ImmutableList.of(Blocks.GRASS_BLOCK.defaultBlockState()))));
	public static final RegistryObject<ConfiguredFeature<SpikeConfig, ?>> OUTSIDE_STALAGMITE = CONFIGURED_FEATURES.register("outside_stalagmite", () -> new ConfiguredFeature<>(TFBiomeFeatures.CAVE_STALACTITE.get(), new SpikeConfig(BlockStateProvider.simple(Blocks.STONE), UniformInt.of(5, 10), ConstantInt.of(0), false)));
	public static final RegistryObject<ConfiguredFeature<BlockStateConfiguration, ?>> PLANT_ROOTS = CONFIGURED_FEATURES.register("plant_roots", () -> new ConfiguredFeature<>(TFBiomeFeatures.UNDERGROUND_PLANTS.get(), new BlockStateConfiguration(TFBlocks.ROOT_STRAND.get().defaultBlockState())));
	public static final RegistryObject<ConfiguredFeature<BlockStateConfiguration, ?>> PUMPKIN_LAMPPOST = CONFIGURED_FEATURES.register("pumpkin_lamppost", () -> new ConfiguredFeature<>(TFBiomeFeatures.LAMPPOSTS.get(), new BlockStateConfiguration(Blocks.JACK_O_LANTERN.defaultBlockState())));
	public static final RegistryObject<ConfiguredFeature<BlockStateConfiguration, ?>> SMOKER = CONFIGURED_FEATURES.register("smoker", () -> new ConfiguredFeature<>(TFBiomeFeatures.FIRE_JET.get(), new BlockStateConfiguration(TFBlocks.SMOKER.get().defaultBlockState())));
	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> STONE_CIRCLE = CONFIGURED_FEATURES.register("stone_circle", () -> new ConfiguredFeature<>(TFBiomeFeatures.STONE_CIRCLE.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<ThornsConfig, ?>> THORNS = CONFIGURED_FEATURES.register("thorns", () -> new ConfiguredFeature<>(TFBiomeFeatures.THORNS.get(), new ThornsConfig(7, 3, 3, 50)));
	public static final RegistryObject<ConfiguredFeature<BlockStateConfiguration, ?>> TORCH_BERRIES = CONFIGURED_FEATURES.register("torch_berries", () -> new ConfiguredFeature<>(TFBiomeFeatures.UNDERGROUND_PLANTS.get(), new BlockStateConfiguration(TFBlocks.TORCHBERRY_PLANT.get().defaultBlockState().setValue(TorchberryPlantBlock.HAS_BERRIES, true))));
	public static final RegistryObject<ConfiguredFeature<BlockStateConfiguration, ?>> TROLL_ROOTS = CONFIGURED_FEATURES.register("troll_roots", () -> new ConfiguredFeature<>(TFBiomeFeatures.TROLL_VINES.get(), new BlockStateConfiguration(TFBlocks.TROLLVIDR.get().defaultBlockState())));
	public static final RegistryObject<ConfiguredFeature<BlockStateConfiguration, ?>> VANILLA_ROOTS = CONFIGURED_FEATURES.register("vanilla_roots", () -> new ConfiguredFeature<>(TFBiomeFeatures.UNDERGROUND_PLANTS.get(), new BlockStateConfiguration(Blocks.HANGING_ROOTS.defaultBlockState())));
	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> WEBS = CONFIGURED_FEATURES.register("webs", () -> new ConfiguredFeature<>(TFBiomeFeatures.WEBS.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> WOOD_ROOTS_SPREAD = CONFIGURED_FEATURES.register("ore/wood_roots_spread", () -> new ConfiguredFeature<>(TFBiomeFeatures.WOOD_ROOTS.get(), FeatureConfiguration.NONE));
	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> SNOW_UNDER_TREES = CONFIGURED_FEATURES.register("snow_under_trees", () -> new ConfiguredFeature<>(TFBiomeFeatures.SNOW_UNDER_TREES.get(), FeatureConfiguration.NONE));

	//fallen logs!
	public static final RegistryObject<ConfiguredFeature<HollowLogConfig, ?>> TF_OAK_FALLEN_LOG = CONFIGURED_FEATURES.register("tf_oak_fallen_log", () -> new ConfiguredFeature<>(TFBiomeFeatures.FALLEN_SMALL_LOG.get(), new HollowLogConfig(TFBlocks.TWILIGHT_OAK_LOG.get().defaultBlockState(), TFBlocks.HOLLOW_OAK_LOG_HORIZONTAL.get().defaultBlockState())));
	public static final RegistryObject<ConfiguredFeature<HollowLogConfig, ?>> CANOPY_FALLEN_LOG = CONFIGURED_FEATURES.register("canopy_fallen_log", () -> new ConfiguredFeature<>(TFBiomeFeatures.FALLEN_SMALL_LOG.get(), new HollowLogConfig(TFBlocks.CANOPY_LOG.get().defaultBlockState(), TFBlocks.HOLLOW_CANOPY_LOG_HORIZONTAL.get().defaultBlockState())));
	public static final RegistryObject<ConfiguredFeature<HollowLogConfig, ?>> MANGROVE_FALLEN_LOG = CONFIGURED_FEATURES.register("mangrove_fallen_log", () -> new ConfiguredFeature<>(TFBiomeFeatures.FALLEN_SMALL_LOG.get(), new HollowLogConfig(TFBlocks.MANGROVE_LOG.get().defaultBlockState(), TFBlocks.HOLLOW_MANGROVE_LOG_HORIZONTAL.get().defaultBlockState())));
	public static final RegistryObject<ConfiguredFeature<HollowLogConfig, ?>> OAK_FALLEN_LOG = CONFIGURED_FEATURES.register("oak_fallen_log", () -> new ConfiguredFeature<>(TFBiomeFeatures.FALLEN_SMALL_LOG.get(), new HollowLogConfig(Blocks.OAK_LOG.defaultBlockState(), TFBlocks.HOLLOW_OAK_LOG_HORIZONTAL.get().defaultBlockState())));
	public static final RegistryObject<ConfiguredFeature<HollowLogConfig, ?>> SPRUCE_FALLEN_LOG = CONFIGURED_FEATURES.register("spruce_fallen_log", () -> new ConfiguredFeature<>(TFBiomeFeatures.FALLEN_SMALL_LOG.get(), new HollowLogConfig(Blocks.SPRUCE_LOG.defaultBlockState(), TFBlocks.HOLLOW_SPRUCE_LOG_HORIZONTAL.get().defaultBlockState())));
	public static final RegistryObject<ConfiguredFeature<HollowLogConfig, ?>> BIRCH_FALLEN_LOG = CONFIGURED_FEATURES.register("birch_fallen_log", () -> new ConfiguredFeature<>(TFBiomeFeatures.FALLEN_SMALL_LOG.get(), new HollowLogConfig(Blocks.BIRCH_LOG.defaultBlockState(), TFBlocks.HOLLOW_BIRCH_LOG_HORIZONTAL.get().defaultBlockState())));

	//smol stone veins
	public static final RegistryObject<ConfiguredFeature<OreConfiguration,?>> SMALL_GRANITE = CONFIGURED_FEATURES.register("small_granite", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, Blocks.GRANITE.defaultBlockState(), 16)));
	public static final RegistryObject<ConfiguredFeature<OreConfiguration,?>> SMALL_DIORITE = CONFIGURED_FEATURES.register("small_diorite", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, Blocks.DIORITE.defaultBlockState(), 16)));
	public static final RegistryObject<ConfiguredFeature<OreConfiguration,?>> SMALL_ANDESITE = CONFIGURED_FEATURES.register("small_andesite", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, Blocks.ANDESITE.defaultBlockState(), 16)));

	//Dark Forest needs special placements, so here we go
	public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> DARK_MUSHGLOOMS = CONFIGURED_FEATURES.register("dark_mushglooms", () -> new ConfiguredFeature<>(TFBiomeFeatures.DARK_FOREST_PLACER.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(TFBlocks.MUSHGLOOM.get())), List.of(Blocks.GRASS_BLOCK), 50)));
	public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> DARK_PUMPKINS = CONFIGURED_FEATURES.register("dark_pumpkins", () -> new ConfiguredFeature<>(TFBiomeFeatures.DARK_FOREST_PLACER.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PUMPKIN)), List.of(Blocks.GRASS_BLOCK), 50)));
	public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> DARK_GRASS = CONFIGURED_FEATURES.register("dark_grass", () -> new ConfiguredFeature<>(TFBiomeFeatures.DARK_FOREST_PLACER.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.GRASS)), List.of(Blocks.GRASS_BLOCK), 128)));
	public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> DARK_FERNS = CONFIGURED_FEATURES.register("dark_ferns", () -> new ConfiguredFeature<>(TFBiomeFeatures.DARK_FOREST_PLACER.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.FERN)), List.of(Blocks.GRASS_BLOCK), 128)));
	public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> DARK_MUSHROOMS = CONFIGURED_FEATURES.register("dark_mushrooms", () -> new ConfiguredFeature<>(TFBiomeFeatures.DARK_FOREST_PLACER.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.BROWN_MUSHROOM)), List.of(Blocks.GRASS_BLOCK), 50)));
	public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> DARK_DEAD_BUSHES = CONFIGURED_FEATURES.register("dark_dead_bushes", () -> new ConfiguredFeature<>(TFBiomeFeatures.DARK_FOREST_PLACER.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.DEAD_BUSH)), List.of(Blocks.GRASS_BLOCK), 50)));

	//troll caves special stuff
	public static final RegistryObject<ConfiguredFeature<DiskConfiguration, ?>> UBEROUS_SOIL_PATCH_BIG = CONFIGURED_FEATURES.register("uberous_soil_patch_big", () -> new ConfiguredFeature<>(TFBiomeFeatures.MYCELIUM_BLOB.get(), new DiskConfiguration(TFBlocks.UBEROUS_SOIL.get().defaultBlockState(), UniformInt.of(4, 8), 1, ImmutableList.of(Blocks.PODZOL.defaultBlockState(), Blocks.COARSE_DIRT.defaultBlockState(), Blocks.DIRT.defaultBlockState()))));
	public static final RegistryObject<ConfiguredFeature<DiskConfiguration, ?>> TROLL_CAVE_MYCELIUM =  CONFIGURED_FEATURES.register("troll_cave_mycelium", () -> new ConfiguredFeature<>(TFBiomeFeatures.MYCELIUM_BLOB.get(), new DiskConfiguration(Blocks.MYCELIUM.defaultBlockState(), UniformInt.of(3, 5), 0, ImmutableList.of(Blocks.STONE.defaultBlockState(), TFBlocks.DEADROCK.get().defaultBlockState()))));
	public static final RegistryObject<ConfiguredFeature<DiskConfiguration, ?>> TROLL_CAVE_DIRT = CONFIGURED_FEATURES.register("troll_cave_dirt", () -> new ConfiguredFeature<>(TFBiomeFeatures.MYCELIUM_BLOB.get(), new DiskConfiguration(Blocks.DIRT.defaultBlockState(), UniformInt.of(2, 5), 0, ImmutableList.of(Blocks.STONE.defaultBlockState(), TFBlocks.DEADROCK.get().defaultBlockState()))));
	public static final RegistryObject<ConfiguredFeature<DiskConfiguration, ?>> UBEROUS_SOIL_PATCH_SMALL = CONFIGURED_FEATURES.register("uberous_soil_patch_small", () -> new ConfiguredFeature<>(TFBiomeFeatures.MYCELIUM_BLOB.get(), new DiskConfiguration(TFBlocks.UBEROUS_SOIL.get().defaultBlockState(), UniformInt.of(2, 3), 0, ImmutableList.of(Blocks.PODZOL.defaultBlockState(), Blocks.COARSE_DIRT.defaultBlockState(), Blocks.DIRT.defaultBlockState()))));

	//random selector stuff
	//public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> WELL_PLACER = CONFIGURED_FEATURES.register("well_placer", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(Holder.direct(TFPlacedFeatures.PLACED_FANCY_WELL.get()), 0.07F)), Holder.direct(TFPlacedFeatures.PLACED_SIMPLE_WELL.get()))));
	//public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> LAMPPOST_PLACER = CONFIGURED_FEATURES.register("lamppost_placer", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(Holder.direct(TFPlacedFeatures.PLACED_CICADA_LAMPPOST.get()), 0.1F)), Holder.direct(TFPlacedFeatures.PLACED_FIREFLY_LAMPPOST.get()))));
	//public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> DEFAULT_FALLEN_LOGS = CONFIGURED_FEATURES.register("default_fallen_logs", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(Holder.direct(TFPlacedFeatures.PLACED_BIRCH_FALLEN_LOG.get()), 0.1F), new WeightedPlacedFeature(Holder.direct(TFPlacedFeatures.PLACED_OAK_FALLEN_LOG.get()), 0.2F), new WeightedPlacedFeature(Holder.direct(TFPlacedFeatures.PLACED_CANOPY_FALLEN_LOG.get()), 0.4F)), Holder.direct(TFPlacedFeatures.PLACED_TF_OAK_FALLEN_LOG.get()))));

	//Trees!
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> TWILIGHT_OAK_TREE = CONFIGURED_FEATURES.register("tree/twilight_oak_tree", () -> new ConfiguredFeature<>(Feature.TREE, TreeConfigurations.TWILIGHT_OAK));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> SWAMPY_OAK_TREE = CONFIGURED_FEATURES.register("tree/swampy_oak_tree", () -> new ConfiguredFeature<>(Feature.TREE, TreeConfigurations.SWAMPY_OAK));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> CANOPY_TREE = CONFIGURED_FEATURES.register("tree/canopy_tree", () -> new ConfiguredFeature<>(Feature.TREE, TreeConfigurations.CANOPY_TREE));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> FIREFLY_CANOPY_TREE = CONFIGURED_FEATURES.register("tree/firefly_canopy_tree", () -> new ConfiguredFeature<>(Feature.TREE, TreeConfigurations.CANOPY_TREE_FIREFLY));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> DEAD_CANOPY_TREE = CONFIGURED_FEATURES.register("tree/dead_canopy_tree", () -> new ConfiguredFeature<>(Feature.TREE, TreeConfigurations.CANOPY_TREE_DEAD));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> MANGROVE_TREE = CONFIGURED_FEATURES.register("tree/mangrove_tree", () -> new ConfiguredFeature<>(Feature.TREE, TreeConfigurations.MANGROVE_TREE));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> DARKWOOD_TREE = CONFIGURED_FEATURES.register("tree/darkwood_tree", () -> new ConfiguredFeature<>(TFBiomeFeatures.DARK_CANOPY_TREE.get(), TreeConfigurations.DARKWOOD_TREE));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> HOMEGROWN_DARKWOOD_TREE = CONFIGURED_FEATURES.register("tree/homegrown_darkwood_tree", () -> new ConfiguredFeature<>(TFBiomeFeatures.DARK_CANOPY_TREE.get(), TreeConfigurations.HOMEGROWN_DARKWOOD_TREE));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> DARKWOOD_LANTERN_TREE = CONFIGURED_FEATURES.register("tree/darkwood_lantern_tree", () -> new ConfiguredFeature<>(TFBiomeFeatures.DARK_CANOPY_TREE.get(), TreeConfigurations.DARKWOOD_LANTERN_TREE));
	public static final RegistryObject<ConfiguredFeature<TFTreeFeatureConfig, ?>> TIME_TREE = CONFIGURED_FEATURES.register("tree/time_tree", () -> new ConfiguredFeature<>(TFBiomeFeatures.TREE_OF_TIME.get(), TreeConfigurations.TIME_TREE));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> TRANSFORMATION_TREE = CONFIGURED_FEATURES.register("tree/transformation_tree", () -> new ConfiguredFeature<>(Feature.TREE, TreeConfigurations.TRANSFORM_TREE));
	public static final RegistryObject<ConfiguredFeature<TFTreeFeatureConfig, ?>> MINING_TREE = CONFIGURED_FEATURES.register("tree/mining_tree", () -> new ConfiguredFeature<>(TFBiomeFeatures.MINERS_TREE.get(), TreeConfigurations.MINING_TREE));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> SORTING_TREE = CONFIGURED_FEATURES.register("tree/sorting_tree", () -> new ConfiguredFeature<>(Feature.TREE, TreeConfigurations.SORT_TREE));
	//public static final RegistryObject<ConfiguredFeature<TFTreeFeatureConfig, ?>>> DENSE_OAK_TREE = CONFIGURED_FEATURES.register("tree/dense_oak_tree", () -> new ConfiguredFeature<>(TFBiomeFeatures.CANOPY_OAK.get(), TreeConfigurations.DENSE_OAK));
	public static final RegistryObject<ConfiguredFeature<TFTreeFeatureConfig, ?>> HOLLOW_TREE = CONFIGURED_FEATURES.register("tree/hollow_tree", () -> new ConfiguredFeature<>(TFBiomeFeatures.HOLLOW_TREE.get(), TreeConfigurations.HOLLOW_TREE));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> RAINBOW_OAK_TREE = CONFIGURED_FEATURES.register("tree/rainbow_oak", () -> new ConfiguredFeature<>(Feature.TREE, TreeConfigurations.RAINBOAK_TREE));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> LARGE_RAINBOW_OAK_TREE = CONFIGURED_FEATURES.register("tree/large_rainbow_oak", () -> new ConfiguredFeature<>(Feature.TREE, TreeConfigurations.LARGE_RAINBOAK_TREE));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> BROWN_CANOPY_MUSHROOM_TREE = CONFIGURED_FEATURES.register("mushroom/brown_canopy_mushroom", () -> new ConfiguredFeature<>(Feature.TREE, TreeConfigurations.MUSHROOM_BROWN));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> RED_CANOPY_MUSHROOM_TREE = CONFIGURED_FEATURES.register("mushroom/red_canopy_mushroom", () -> new ConfiguredFeature<>(Feature.TREE, TreeConfigurations.MUSHROOM_RED));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> MEGA_SPRUCE_TREE = CONFIGURED_FEATURES.register("tree/mega_spruce_tree", () -> new ConfiguredFeature<>(TFBiomeFeatures.SNOW_TREE.get(), TreeConfigurations.BIG_SPRUCE));
	public static final RegistryObject<ConfiguredFeature<TFTreeFeatureConfig, ?>> LARGE_WINTER_TREE = CONFIGURED_FEATURES.register("tree/large_winter_tree", () -> new ConfiguredFeature<>(TFBiomeFeatures.LARGE_WINTER_TREE.get(), TreeConfigurations.LARGE_WINTER));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> SNOWY_SPRUCE_TREE = CONFIGURED_FEATURES.register("tree/snowy_spruce_tree", () -> new ConfiguredFeature<>(TFBiomeFeatures.SNOW_TREE.get(), TreeFeatures.SPRUCE.value().config()));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> DARK_FOREST_OAK_TREE = CONFIGURED_FEATURES.register("tree/dark_forest_oak_tree", () -> new ConfiguredFeature<>(TFBiomeFeatures.DARK_CANOPY_TREE.get(), TreeFeatures.OAK.value().config()));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> DARK_FOREST_BIRCH_TREE = CONFIGURED_FEATURES.register("tree/dark_forest_birch_tree", () -> new ConfiguredFeature<>(TFBiomeFeatures.DARK_CANOPY_TREE.get(), TreeFeatures.BIRCH.value().config()));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> VANILLA_OAK_TREE = CONFIGURED_FEATURES.register("tree/vanilla_oak_tree", () -> new ConfiguredFeature<>(Feature.TREE, TreeFeatures.OAK.value().config()));
	public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> VANILLA_BIRCH_TREE = CONFIGURED_FEATURES.register("tree/vanilla_birch_tree", () -> new ConfiguredFeature<>(Feature.TREE, TreeFeatures.BIRCH.value().config()));
	public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> DUMMY_TREE = CONFIGURED_FEATURES.register("tree/dummy", () -> new ConfiguredFeature<>(Feature.NO_OP, NoneFeatureConfiguration.INSTANCE));

	//random selectors
	public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> CANOPY_TREES = CONFIGURED_FEATURES.register("tree/selector/canopy_trees", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(Holder.direct(CANOPY_TREE.get())), 0.6F)), PlacementUtils.inlinePlaced(Holder.direct(TWILIGHT_OAK_TREE.get())))));
	public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> DENSE_CANOPY_TREES = CONFIGURED_FEATURES.register("tree/selector/dense_canopy_trees", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(Holder.direct(CANOPY_TREE.get())), 0.7F)), PlacementUtils.inlinePlaced(Holder.direct(TWILIGHT_OAK_TREE.get())))));
	public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> FIREFLY_FOREST_TREES = CONFIGURED_FEATURES.register("tree/selector/firefly_forest_trees", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(Holder.direct(CANOPY_TREE.get())), 0.33F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(Holder.direct(FIREFLY_CANOPY_TREE.get())), 0.45F)), PlacementUtils.inlinePlaced(Holder.direct(TWILIGHT_OAK_TREE.get())))));
	public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> DARK_FOREST_TREES = CONFIGURED_FEATURES.register("tree/selector/dark_forest_trees", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(Holder.direct(DARK_FOREST_BIRCH_TREE.get())), 0.35F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(Holder.direct(DARK_FOREST_OAK_TREE.get())), 0.35F)), PlacementUtils.inlinePlaced(Holder.direct(DARKWOOD_TREE.get())))));
	public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> HIGHLANDS_TREES = CONFIGURED_FEATURES.register("tree/selector/highlands_trees", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.SPRUCE), 0.25F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.PINE), 0.1F)), PlacementUtils.inlinePlaced(Holder.direct(MEGA_SPRUCE_TREE.get())))));
	public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> ENCHANTED_FOREST_TREES = CONFIGURED_FEATURES.register("tree/selector/enchanted_forest_trees", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(Holder.direct(VANILLA_OAK_TREE.get())), 0.15F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(Holder.direct(VANILLA_BIRCH_TREE.get())), 0.15F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(Holder.direct(LARGE_RAINBOW_OAK_TREE.get())), 0.1F)), PlacementUtils.inlinePlaced(Holder.direct(RAINBOW_OAK_TREE.get())))));
	public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> SNOWY_FOREST_TREES = CONFIGURED_FEATURES.register("tree/selector/snowy_forest_trees", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(Holder.direct(MEGA_SPRUCE_TREE.get())), 0.1F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(Holder.direct(LARGE_WINTER_TREE.get())), 0.01F)), PlacementUtils.inlinePlaced(Holder.direct(SNOWY_SPRUCE_TREE.get())))));
	public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> VANILLA_TF_TREES = CONFIGURED_FEATURES.register("tree/selector/vanilla_trees", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(Holder.direct(VANILLA_BIRCH_TREE.get())), 0.25F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(Holder.direct(VANILLA_OAK_TREE.get())), 0.25F)), PlacementUtils.inlinePlaced(Holder.direct(TWILIGHT_OAK_TREE.get())))));
	public static final RegistryObject<ConfiguredFeature<RandomBooleanFeatureConfiguration, ?>> VANILLA_TF_BIG_MUSH = CONFIGURED_FEATURES.register("tree/selector/vanilla/vanilla_mushrooms", () -> new ConfiguredFeature<>(Feature.RANDOM_BOOLEAN_SELECTOR, new RandomBooleanFeatureConfiguration(PlacementUtils.inlinePlaced(TreeFeatures.HUGE_RED_MUSHROOM), PlacementUtils.inlinePlaced(TreeFeatures.HUGE_BROWN_MUSHROOM))));

	//super funky tree placement lists
	public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> CANOPY_MUSHROOMS_SPARSE =
			CONFIGURED_FEATURES.register("mushroom/canopy_mushrooms_sparse", () -> new ConfiguredFeature<>(
					Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(Holder.direct(BROWN_CANOPY_MUSHROOM_TREE.get())), 0.15f),
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(Holder.direct(RED_CANOPY_MUSHROOM_TREE.get())), 0.05f)),
							PlacementUtils.inlinePlaced(Holder.direct(DUMMY_TREE.get())))));

	public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> CANOPY_MUSHROOMS_DENSE =
			CONFIGURED_FEATURES.register("mushroom/canopy_mushrooms_dense", () -> new ConfiguredFeature<>(
					Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(Holder.direct(BROWN_CANOPY_MUSHROOM_TREE.get())), 0.675f),
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(Holder.direct(RED_CANOPY_MUSHROOM_TREE.get())), 0.225f)),
							PlacementUtils.inlinePlaced(Holder.direct(DUMMY_TREE.get())))));

	//ground decoration
	public static final RandomPatchConfiguration SMALL_FLOWER_CONFIG = (new RandomPatchConfiguration(32, 7, 7,
			PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
					new NoiseProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0D), 0.020833334F, List.of(
							Blocks.POPPY.defaultBlockState(),
							Blocks.DANDELION.defaultBlockState(),
							Blocks.RED_TULIP.defaultBlockState(),
							Blocks.ORANGE_TULIP.defaultBlockState(),
							Blocks.PINK_TULIP.defaultBlockState(),
							Blocks.WHITE_TULIP.defaultBlockState(),
							Blocks.CORNFLOWER.defaultBlockState(),
							Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
							Blocks.BLUE_ORCHID.defaultBlockState(),
							Blocks.ALLIUM.defaultBlockState(),
							Blocks.AZURE_BLUET.defaultBlockState(),
							Blocks.OXEYE_DAISY.defaultBlockState())
					)), BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.matchesBlock(Blocks.GRASS_BLOCK, new BlockPos(0, -1, 0))))));

	public static final RandomPatchConfiguration FOREST_GRASS = (new RandomPatchConfiguration(64, 7, 7,
            PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
			new NoiseProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0D), 0.020833334F, List.of(
							//TFBlocks.MAYAPPLE.get().defaultBlockState(),
							Blocks.GRASS.defaultBlockState(),
							Blocks.TALL_GRASS.defaultBlockState(),
							Blocks.FERN.defaultBlockState(),
							Blocks.LARGE_FERN.defaultBlockState())
			)), BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.matchesBlock(Blocks.GRASS_BLOCK, new BlockPos(0, -1, 0))))));

	public static final RandomPatchConfiguration OTHER_GRASS = (new RandomPatchConfiguration(64, 7, 7,
			PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
			new NoiseProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0D), 0.020833334F, List.of(
							Blocks.GRASS.defaultBlockState(),
							Blocks.TALL_GRASS.defaultBlockState(),
							Blocks.FERN.defaultBlockState(),
							Blocks.LARGE_FERN.defaultBlockState())
			)), BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.matchesBlock(Blocks.GRASS_BLOCK, new BlockPos(0, -1, 0))))));

	public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> GRASS_PLACER = CONFIGURED_FEATURES.register("grass_placer", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, OTHER_GRASS));
	public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FOREST_GRASS_PLACER = CONFIGURED_FEATURES.register("forest_grass_placer", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, FOREST_GRASS));
	public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_PLACER = CONFIGURED_FEATURES.register("flower_placer", () -> new ConfiguredFeature<>(Feature.FLOWER, SMALL_FLOWER_CONFIG));

	//music!
	public static final Music TFMUSICTYPE = new Music(TFSounds.MUSIC, 1200, 12000, true);
}