package twilightforest.world.registration.features;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.sounds.Music;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
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
import twilightforest.world.components.feature.config.TFTreeFeatureConfig;
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
	public static final Holder<ConfiguredFeature<LakeFeature.Configuration, Feature<LakeFeature.Configuration>>> LAKE_LAVA = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("lava_lake"), Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(BlockConstants.LAVA), BlockStateProvider.simple(Blocks.STONE)));
	public static final Holder<ConfiguredFeature<LakeFeature.Configuration, Feature<LakeFeature.Configuration>>> LAKE_WATER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("water_lake"), Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(BlockConstants.WATER), BlockStateProvider.simple(Blocks.STONE)));

	//"structures" that arent actually structures
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, Feature<NoneFeatureConfiguration>>> SIMPLE_WELL = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("simple_well"), TFBiomeFeatures.SIMPLE_WELL.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, Feature<NoneFeatureConfiguration>>> FANCY_WELL = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("fancy_well"), TFBiomeFeatures.FANCY_WELL.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, Feature<NoneFeatureConfiguration>>> DRUID_HUT = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("druid_hut"), TFBiomeFeatures.DRUID_HUT.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, Feature<NoneFeatureConfiguration>>> GRAVEYARD = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("graveyard"), TFBiomeFeatures.GRAVEYARD.get(), FeatureConfiguration.NONE);

	//all the fun little things you find around the dimension
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, Feature<NoneFeatureConfiguration>>> BIG_MUSHGLOOM = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("big_mushgloom"), TFBiomeFeatures.BIG_MUSHGLOOM.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, Feature<NoneFeatureConfiguration>>> FALLEN_LEAVES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("fallen_leaves"), TFBiomeFeatures.FALLEN_LEAVES.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> FIDDLEHEAD = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("fiddlehead"), Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BlockConstants.FIDDLEHEAD))));
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, Feature<BlockStateConfiguration>>> FIRE_JET = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("fire_jet"), TFBiomeFeatures.FIRE_JET.get(), new BlockStateConfiguration(BlockConstants.FIRE_JET));
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, Feature<NoneFeatureConfiguration>>> FOUNDATION = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("foundation"), TFBiomeFeatures.FOUNDATION.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, Feature<NoneFeatureConfiguration>>> GROVE_RUINS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("grove_ruins"), TFBiomeFeatures.GROVE_RUINS.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, Feature<NoneFeatureConfiguration>>> HOLLOW_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("hollow_log"), TFBiomeFeatures.FALLEN_HOLLOW_LOG.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<TFTreeFeatureConfig, Feature<TFTreeFeatureConfig>>> HOLLOW_STUMP = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("hollow_stump"), TFBiomeFeatures.HOLLOW_STUMP.get(), TreeConfigurations.HOLLOW_TREE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, Feature<NoneFeatureConfiguration>>> HUGE_LILY_PAD = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("huge_lily_pad"), TFBiomeFeatures.HUGE_LILY_PAD.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, Feature<NoneFeatureConfiguration>>> HUGE_WATER_LILY = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("huge_water_lily"), TFBiomeFeatures.HUGE_WATER_LILY.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, Feature<BlockStateConfiguration>>> CICADA_LAMPPOST = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("cicada_lamppost"), TFBiomeFeatures.LAMPPOSTS.get(), new BlockStateConfiguration(BlockConstants.CICADA_JAR));
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, Feature<BlockStateConfiguration>>> FIREFLY_LAMPPOST = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("firefly_lamppost"), TFBiomeFeatures.LAMPPOSTS.get(), new BlockStateConfiguration(BlockConstants.FIREFLY_JAR));
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, Feature<NoneFeatureConfiguration>>> MONOLITH = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("monolith"), TFBiomeFeatures.MONOLITH.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> MUSHGLOOM_CLUSTER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mushgloom_cluster"), Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BlockConstants.MUSHGLOOM))));
	public static final Holder<ConfiguredFeature<DiskConfiguration, Feature<DiskConfiguration>>> MYCELIUM_BLOB = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mycelium_blob"), TFBiomeFeatures.MYCELIUM_BLOB.get(), new DiskConfiguration(BlockConstants.MYCELIUM, UniformInt.of(4, 6), 3, ImmutableList.of(BlockConstants.GRASS_BLOCK)));
	public static final Holder<ConfiguredFeature<SpikeConfig, Feature<SpikeConfig>>> OUTSIDE_STALAGMITE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("outside_stalagmite"), TFBiomeFeatures.CAVE_STALACTITE.get(), new SpikeConfig(BlockStateProvider.simple(Blocks.STONE), UniformInt.of(5, 10), ConstantInt.of(0), false));
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, Feature<BlockStateConfiguration>>> PLANT_ROOTS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("plant_roots"), TFBiomeFeatures.UNDERGROUND_PLANTS.get(), new BlockStateConfiguration(BlockConstants.ROOT_STRAND));
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, Feature<BlockStateConfiguration>>> PUMPKIN_LAMPPOST = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("pumpkin_lamppost"), TFBiomeFeatures.LAMPPOSTS.get(), new BlockStateConfiguration(BlockConstants.JACK_O_LANTERN));
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, Feature<BlockStateConfiguration>>> SMOKER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("smoker"), TFBiomeFeatures.FIRE_JET.get(), new BlockStateConfiguration(BlockConstants.SMOKER));
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, Feature<NoneFeatureConfiguration>>> STONE_CIRCLE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("stone_circle"), TFBiomeFeatures.STONE_CIRCLE.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<ThornsConfig, Feature<ThornsConfig>>> THORNS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("thorns"), TFBiomeFeatures.THORNS.get(), new ThornsConfig(7, 3, 3, 50));
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, Feature<BlockStateConfiguration>>> TORCH_BERRIES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("torch_berries"), TFBiomeFeatures.UNDERGROUND_PLANTS.get(), new BlockStateConfiguration(BlockConstants.TORCHBERRIES));
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, Feature<BlockStateConfiguration>>> TROLL_ROOTS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("troll_roots"), TFBiomeFeatures.TROLL_VINES.get(), new BlockStateConfiguration(BlockConstants.TROLLVIDR));
	public static final Holder<ConfiguredFeature<BlockStateConfiguration, Feature<BlockStateConfiguration>>> VANILLA_ROOTS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("vanilla_roots"), TFBiomeFeatures.UNDERGROUND_PLANTS.get(), new BlockStateConfiguration(BlockConstants.HANGING_ROOTS));
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, Feature<NoneFeatureConfiguration>>> WEBS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("webs"), TFBiomeFeatures.WEBS.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, Feature<NoneFeatureConfiguration>>> WOOD_ROOTS_SPREAD = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("ore/wood_roots_spread"), TFBiomeFeatures.WOOD_ROOTS.get(), FeatureConfiguration.NONE);
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, Feature<NoneFeatureConfiguration>>> SNOW_UNDER_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("snow_under_trees"), TFBiomeFeatures.SNOW_UNDER_TREES.get(), FeatureConfiguration.NONE);

	//fallen logs!
	public static final Holder<ConfiguredFeature<HollowLogConfig, Feature<HollowLogConfig>>> TF_OAK_FALLEN_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tf_oak_fallen_log"), TFBiomeFeatures.FALLEN_SMALL_LOG.get(), new HollowLogConfig(BlockConstants.TF_OAK_LOG, BlockConstants.HOLLOW_TF_OAK_LOG));
	public static final Holder<ConfiguredFeature<HollowLogConfig, Feature<HollowLogConfig>>> CANOPY_FALLEN_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("canopy_fallen_log"), TFBiomeFeatures.FALLEN_SMALL_LOG.get(), new HollowLogConfig(BlockConstants.CANOPY_LOG, BlockConstants.HOLLOW_CANOPY_LOG));
	public static final Holder<ConfiguredFeature<HollowLogConfig, Feature<HollowLogConfig>>> MANGROVE_FALLEN_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mangrove_fallen_log"), TFBiomeFeatures.FALLEN_SMALL_LOG.get(), new HollowLogConfig(BlockConstants.MANGROVE_LOG, BlockConstants.HOLLOW_MANGROVE_LOG));
	public static final Holder<ConfiguredFeature<HollowLogConfig, Feature<HollowLogConfig>>> OAK_FALLEN_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("oak_fallen_log"), TFBiomeFeatures.FALLEN_SMALL_LOG.get(), new HollowLogConfig(BlockConstants.OAK_LOG, BlockConstants.HOLLOW_OAK_LOG));
	public static final Holder<ConfiguredFeature<HollowLogConfig, Feature<HollowLogConfig>>> SPRUCE_FALLEN_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("spruce_fallen_log"), TFBiomeFeatures.FALLEN_SMALL_LOG.get(), new HollowLogConfig(BlockConstants.SPRUCE_LOG, BlockConstants.HOLLOW_SPRUCE_LOG));
	public static final Holder<ConfiguredFeature<HollowLogConfig, Feature<HollowLogConfig>>> BIRCH_FALLEN_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("birch_fallen_log"), TFBiomeFeatures.FALLEN_SMALL_LOG.get(), new HollowLogConfig(BlockConstants.BIRCH_LOG, BlockConstants.HOLLOW_BIRCH_LOG));

	//smol stone veins
	public static final Holder<ConfiguredFeature<OreConfiguration, Feature<OreConfiguration>>> SMALL_GRANITE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("small_granite"), Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, Blocks.GRANITE.defaultBlockState(), 16));
	public static final Holder<ConfiguredFeature<OreConfiguration, Feature<OreConfiguration>>> SMALL_DIORITE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("small_diorite"), Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, Blocks.DIORITE.defaultBlockState(), 16));
	public static final Holder<ConfiguredFeature<OreConfiguration, Feature<OreConfiguration>>> SMALL_ANDESITE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("small_andesite"), Feature.ORE, new OreConfiguration(OreFeatures.NATURAL_STONE, Blocks.ANDESITE.defaultBlockState(), 16));

	//Dark Forest needs special placements, so here we go
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> DARK_MUSHGLOOMS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_mushglooms"), TFBiomeFeatures.DARK_FOREST_PLACER.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BlockConstants.MUSHGLOOM)), List.of(BlockConstants.GRASS_BLOCK.getBlock()), 50));
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> DARK_PUMPKINS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_pumpkins"), TFBiomeFeatures.DARK_FOREST_PLACER.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BlockConstants.PUMPKIN)), List.of(BlockConstants.GRASS_BLOCK.getBlock()), 50));
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> DARK_GRASS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_grass"), TFBiomeFeatures.DARK_FOREST_PLACER.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BlockConstants.GRASS)), List.of(BlockConstants.GRASS_BLOCK.getBlock()), 128));
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> DARK_FERNS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_ferns"), TFBiomeFeatures.DARK_FOREST_PLACER.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BlockConstants.FERN)), List.of(BlockConstants.GRASS_BLOCK.getBlock()), 128));
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> DARK_MUSHROOMS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_mushrooms"), TFBiomeFeatures.DARK_FOREST_PLACER.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BlockConstants.BROWN_MUSHROOM)), List.of(BlockConstants.GRASS_BLOCK.getBlock()), 50));
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> DARK_DEAD_BUSHES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_dead_bushes"), TFBiomeFeatures.DARK_FOREST_PLACER.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BlockConstants.DEAD_BUSH)), List.of(BlockConstants.GRASS_BLOCK.getBlock()), 50));

	//placements! Use these in the biome maker
	public static final Holder<PlacedFeature> PLACED_LAKE_LAVA = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("lava_lake"), LAKE_LAVA, tfFeatureCheckArea(OutOfStructureFilter.checkBoth(), 10).build());
	public static final Holder<PlacedFeature> PLACED_LAKE_WATER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("water_lake"), LAKE_WATER, tfFeatureCheckArea(OutOfStructureFilter.checkBoth(), 4).build());
	public static final Holder<PlacedFeature> PLACED_SIMPLE_WELL = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("simple_well"), SIMPLE_WELL, ImmutableList.<PlacementModifier>builder().build());
	public static final Holder<PlacedFeature> PLACED_FANCY_WELL = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("fancy_well"), FANCY_WELL, ImmutableList.<PlacementModifier>builder().build());
	public static final Holder<PlacedFeature> PLACED_DRUID_HUT = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("druid_hut"), DRUID_HUT, tfFeatureCheckArea(OutOfStructureFilter.checkBoth(), 105).build());
	public static final Holder<PlacedFeature> PLACED_GRAVEYARD = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("graveyard"), GRAVEYARD, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 75).build());
	public static final Holder<PlacedFeature> PLACED_BIG_MUSHGLOOM = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("big_mushgloom"), BIG_MUSHGLOOM, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 1).build());
	public static final Holder<PlacedFeature> PLACED_FALLEN_LEAVES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("fallen_leaves"), FALLEN_LEAVES, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 1).build());
	public static final Holder<PlacedFeature> PLACED_FIDDLEHEAD = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("fiddlehead"), FIDDLEHEAD, ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()).build());
	public static final Holder<PlacedFeature> PLACED_FIRE_JET = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("fire_jet"), FIRE_JET, ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), BiomeFilter.biome()).build());
	public static final Holder<PlacedFeature> PLACED_FOUNDATION = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("foundation"), FOUNDATION, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 100).build());
	public static final Holder<PlacedFeature> PLACED_GROVE_RUINS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("grove_ruins"), GROVE_RUINS, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 105).build());
	public static final Holder<PlacedFeature> PLACED_HOLLOW_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("hollow_log"), HOLLOW_LOG, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 100).build());
	public static final Holder<PlacedFeature> PLACED_HOLLOW_STUMP = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("hollow_stump"), HOLLOW_STUMP, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 90).build());
	public static final Holder<PlacedFeature> PLACED_HUGE_LILY_PAD = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("huge_lily_pad"), HUGE_LILY_PAD, ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, RarityFilter.onAverageOnceEvery(20), InSquarePlacement.spread(), CountPlacement.of(10), BiomeFilter.biome()).build());
	public static final Holder<PlacedFeature> PLACED_HUGE_WATER_LILY = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("huge_water_lily"), HUGE_WATER_LILY, ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, RarityFilter.onAverageOnceEvery(25), InSquarePlacement.spread(), CountPlacement.of(5), BiomeFilter.biome()).build());
	public static final Holder<PlacedFeature> PLACED_CICADA_LAMPPOST = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("cicada_lamppost"), CICADA_LAMPPOST, ImmutableList.<PlacementModifier>builder().build());
	public static final Holder<PlacedFeature> PLACED_FIREFLY_LAMPPOST = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("firefly_lamppost"), FIREFLY_LAMPPOST, ImmutableList.<PlacementModifier>builder().build());
	public static final Holder<PlacedFeature> PLACED_MONOLITH = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("monolith"), MONOLITH, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 90).build());
	public static final Holder<PlacedFeature> PLACED_MUSHGLOOM_CLUSTER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mushgloom_cluster"), MUSHGLOOM_CLUSTER, ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, CountPlacement.of(5), InSquarePlacement.spread(), BiomeFilter.biome()).build());
	public static final Holder<PlacedFeature> PLACED_MYCELIUM_BLOB = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mycelium_blob"), MYCELIUM_BLOB, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 3).build());
	public static final Holder<PlacedFeature> PLACED_OUTSIDE_STALAGMITE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("outside_stalagmite"), OUTSIDE_STALAGMITE, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 88).build());
	public static final Holder<PlacedFeature> PLACED_PLANT_ROOTS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("plant_roots"), PLANT_ROOTS, tfFeatureCheckArea(OutOfStructureFilter.checkUnderground(), 1, CountPlacement.of(4), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(10))).build());
	public static final Holder<PlacedFeature> PLACED_PUMPKIN_LAMPPOST = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("pumpkin_lamppost"), PUMPKIN_LAMPPOST, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 1).build());
	public static final Holder<PlacedFeature> PLACED_SMOKER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("smoker"), SMOKER, ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), BiomeFilter.biome()).build());
	public static final Holder<PlacedFeature> PLACED_STONE_CIRCLE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("stone_circle"), STONE_CIRCLE, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 105).build());
	public static final Holder<PlacedFeature> PLACED_THORNS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("thorns"), THORNS, ImmutableList.<PlacementModifier>builder().add(ChunkBlanketingModifier.addThorns(), BiomeFilter.biome()).build());
	public static final Holder<PlacedFeature> PLACED_TORCH_BERRIES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("torch_berries"), TORCH_BERRIES, ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(60)), CountPlacement.of(8), OutOfStructureFilter.checkUnderground(), BiomeFilter.biome()).build());
	public static final Holder<PlacedFeature> PLACED_TROLL_ROOTS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("troll_roots"), TROLL_ROOTS, ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(60)), CountPlacement.of(8), BiomeFilter.biome()).build());
	public static final Holder<PlacedFeature> PLACED_VANILLA_ROOTS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("vanilla_roots"), VANILLA_ROOTS, tfFeatureCheckArea(OutOfStructureFilter.checkUnderground(), 1, CountPlacement.of(16), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(10))).build());
	public static final Holder<PlacedFeature> PLACED_WEBS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("webs"), WEBS, ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, InSquarePlacement.spread(), CountPlacement.of(60), BiomeFilter.biome()).build());
	public static final Holder<PlacedFeature> PLACED_WOOD_ROOTS_SPREAD = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("wood_roots"), WOOD_ROOTS_SPREAD, tfFeatureCheckArea(OutOfStructureFilter.checkUnderground(), 40, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0))).build());
	public static final Holder<PlacedFeature> PLACED_SNOW_UNDER_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("snow_under_trees"), SNOW_UNDER_TREES, ImmutableList.<PlacementModifier>builder().add(BiomeFilter.biome()).build());
	public static final Holder<PlacedFeature> PLACED_TF_OAK_FALLEN_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tf_oak_fallen_log"), TF_OAK_FALLEN_LOG, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build());
	public static final Holder<PlacedFeature> PLACED_CANOPY_FALLEN_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("canopy_fallen_log"), CANOPY_FALLEN_LOG, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build());
	public static final Holder<PlacedFeature> PLACED_MANGROVE_FALLEN_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mangrove_fallen_log"), MANGROVE_FALLEN_LOG, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build());
	public static final Holder<PlacedFeature> PLACED_OAK_FALLEN_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("oak_fallen_log"), OAK_FALLEN_LOG, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build());
	public static final Holder<PlacedFeature> PLACED_SPRUCE_FALLEN_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("spruce_fallen_log"), SPRUCE_FALLEN_LOG, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build());
	public static final Holder<PlacedFeature> PLACED_BIRCH_FALLEN_LOG = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("birch_fallen_log"), BIRCH_FALLEN_LOG, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build());
	public static final Holder<PlacedFeature> PLACED_SMALL_GRANITE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("small_granite"), SMALL_GRANITE, ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(60), InSquarePlacement.spread(), CountPlacement.of(5)).build());
	public static final Holder<PlacedFeature> PLACED_SMALL_DIORITE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("small_diorite"), SMALL_DIORITE, ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(60), InSquarePlacement.spread(), CountPlacement.of(5)).build());
	public static final Holder<PlacedFeature> PLACED_SMALL_ANDESITE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("small_andesite"), SMALL_ANDESITE, ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(60), InSquarePlacement.spread(), CountPlacement.of(5)).build());
	public static final Holder<PlacedFeature> PLACED_DARK_MUSHGLOOMS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_mushglooms"), DARK_MUSHGLOOMS, ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(30), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()).build());
	public static final Holder<PlacedFeature> PLACED_DARK_PUMPKINS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_pumpkins"), DARK_PUMPKINS, ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(30), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()).build());
	public static final Holder<PlacedFeature> PLACED_DARK_GRASS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_grass"), DARK_GRASS, ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(4), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()).build());
	public static final Holder<PlacedFeature> PLACED_DARK_FERNS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_ferns"), DARK_FERNS, ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(4), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()).build());
	public static final Holder<PlacedFeature> PLACED_DARK_MUSHROOMS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_mushrooms"), DARK_MUSHROOMS, ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()).build());
	public static final Holder<PlacedFeature> PLACED_DARK_DEAD_BUSHES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("dark_dead_bushes"), DARK_DEAD_BUSHES, ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(15), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()).build());

	//random selector stuff
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, Feature<RandomFeatureConfiguration>>> WELL_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("well_placer"), Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PLACED_FANCY_WELL, 0.07F)), PLACED_SIMPLE_WELL));
	public static final Holder<PlacedFeature> PLACED_WELL_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("well_placer"), WELL_PLACER, tfFeatureCheckArea(OutOfStructureFilter.checkBoth(), 80).build());
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, Feature<RandomFeatureConfiguration>>> LAMPPOST_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("lamppost_placer"), Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PLACED_CICADA_LAMPPOST, 0.1F)), PLACED_FIREFLY_LAMPPOST));
	public static final Holder<PlacedFeature> PLACED_LAMPPOST_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("lamppost_placer"), LAMPPOST_PLACER, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 2).build());
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, Feature<RandomFeatureConfiguration>>> DEFAULT_FALLEN_LOGS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("default_fallen_logs"), Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PLACED_BIRCH_FALLEN_LOG, 0.1F), new WeightedPlacedFeature(PLACED_OAK_FALLEN_LOG, 0.2F), new WeightedPlacedFeature(PLACED_CANOPY_FALLEN_LOG, 0.4F)), PLACED_TF_OAK_FALLEN_LOG));
	public static final Holder<PlacedFeature> PLACED_DEFAULT_FALLEN_LOGS = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("default_fallen_logs"), DEFAULT_FALLEN_LOGS, tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build());


	private static ImmutableList.Builder<PlacementModifier> tfFeatureCheckArea(OutOfStructureFilter filter, int rarity) {
		return ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, filter, BiomeFilter.biome());
	}

	private static ImmutableList.Builder<PlacementModifier> tfFeatureCheckArea(OutOfStructureFilter filter, int rarity, PlacementModifier... extra) {
		return ImmutableList.<PlacementModifier>builder().add(extra).add(filter, RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
	}

	//ground decoration
	public static final RandomPatchConfiguration SMALL_FLOWER_CONFIG = (new RandomPatchConfiguration(32, 7, 7,
			PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
					new NoiseProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0D), 0.020833334F, List.of(
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
					)), BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.matchesBlock(Blocks.GRASS_BLOCK, new BlockPos(0, -1, 0))))));

	public static final RandomPatchConfiguration FOREST_GRASS = (new RandomPatchConfiguration(64, 7, 7,
            PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
			new NoiseProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0D), 0.020833334F, List.of(
							BlockConstants.MAYAPPLE,
							BlockConstants.GRASS,
							BlockConstants.TALL_GRASS,
							BlockConstants.FERN,
							BlockConstants.TALL_FERN)
			)), BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.matchesBlock(Blocks.GRASS_BLOCK, new BlockPos(0, -1, 0))))));

	public static final RandomPatchConfiguration OTHER_GRASS = (new RandomPatchConfiguration(64, 7, 7,
			PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
			new NoiseProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0D), 0.020833334F, List.of(
							BlockConstants.GRASS,
							BlockConstants.TALL_GRASS,
							BlockConstants.FERN,
							BlockConstants.TALL_FERN)
			)), BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.matchesBlock(Blocks.GRASS_BLOCK, new BlockPos(0, -1, 0))))));

	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> GRASS_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("grass_placer"), Feature.RANDOM_PATCH, OTHER_GRASS);
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> FOREST_GRASS_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("forest_grass_placer"), Feature.RANDOM_PATCH, FOREST_GRASS);
	public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> FLOWER_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("flower_placer"), Feature.FLOWER, SMALL_FLOWER_CONFIG);

	public static final Holder<PlacedFeature> PLACED_GRASS_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("grass_placer"), GRASS_PLACER, ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, CountPlacement.of(30), InSquarePlacement.spread(), RarityFilter.onAverageOnceEvery(3)).build());
	public static final Holder<PlacedFeature> PLACED_FOREST_GRASS_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("forest_grass_placer"), FOREST_GRASS_PLACER, ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, CountPlacement.of(30), InSquarePlacement.spread(), RarityFilter.onAverageOnceEvery(3)).build());
	public static final Holder<PlacedFeature> PLACED_FLOWER_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("flower_placer"), FLOWER_PLACER, ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, CountPlacement.of(25), InSquarePlacement.spread(), RarityFilter.onAverageOnceEvery(15)).build());

	//music!
	public static final Music TFMUSICTYPE = new Music(TFSounds.MUSIC, 1200, 12000, true);
}