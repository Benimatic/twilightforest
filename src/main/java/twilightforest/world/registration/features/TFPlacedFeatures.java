package twilightforest.world.registration.features;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.world.components.placements.ChunkBlanketingModifier;
import twilightforest.world.components.placements.OutOfStructureFilter;

import java.util.List;

public class TFPlacedFeatures {

	public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, TwilightForestMod.ID);

	public static final RegistryObject<PlacedFeature> PLACED_LAKE_LAVA = PLACED_FEATURES.register("lava_lake", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.LAKE_LAVA.get()), tfFeatureCheckArea(OutOfStructureFilter.checkBoth(), 10).build()));
	public static final RegistryObject<PlacedFeature> PLACED_LAKE_WATER = PLACED_FEATURES.register("water_lake", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.LAKE_WATER.get()), tfFeatureCheckArea(OutOfStructureFilter.checkBoth(), 4).build()));
	public static final RegistryObject<PlacedFeature> PLACED_SIMPLE_WELL = PLACED_FEATURES.register("simple_well", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.SIMPLE_WELL.get()), ImmutableList.<PlacementModifier>builder().build()));
	public static final RegistryObject<PlacedFeature> PLACED_FANCY_WELL = PLACED_FEATURES.register("fancy_well", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.FANCY_WELL.get()), ImmutableList.<PlacementModifier>builder().build()));
	public static final RegistryObject<PlacedFeature> PLACED_DRUID_HUT = PLACED_FEATURES.register("druid_hut", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.DRUID_HUT.get()), tfFeatureCheckArea(OutOfStructureFilter.checkBoth(), 105).build()));
	public static final RegistryObject<PlacedFeature> PLACED_GRAVEYARD = PLACED_FEATURES.register("graveyard", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.GRAVEYARD.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 75).build()));
	public static final RegistryObject<PlacedFeature> PLACED_BIG_MUSHGLOOM = PLACED_FEATURES.register("big_mushgloom", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.BIG_MUSHGLOOM.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 1).build()));
	public static final RegistryObject<PlacedFeature> PLACED_FALLEN_LEAVES = PLACED_FEATURES.register("fallen_leaves", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.FALLEN_LEAVES.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 1).build()));
	public static final RegistryObject<PlacedFeature> PLACED_FIDDLEHEAD = PLACED_FEATURES.register("fiddlehead", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.FIDDLEHEAD.get()), ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()).build()));
	public static final RegistryObject<PlacedFeature> PLACED_FIRE_JET = PLACED_FEATURES.register("fire_jet", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.FIRE_JET.get()), ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), BiomeFilter.biome()).build()));
	public static final RegistryObject<PlacedFeature> PLACED_FOUNDATION = PLACED_FEATURES.register("foundation", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.FOUNDATION.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 100).build()));
	public static final RegistryObject<PlacedFeature> PLACED_GROVE_RUINS = PLACED_FEATURES.register("grove_ruins", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.GROVE_RUINS.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 105).build()));
	public static final RegistryObject<PlacedFeature> PLACED_HOLLOW_LOG = PLACED_FEATURES.register("hollow_log", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.HOLLOW_LOG.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 100).build()));
	public static final RegistryObject<PlacedFeature> PLACED_HOLLOW_STUMP = PLACED_FEATURES.register("hollow_stump", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.HOLLOW_STUMP.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 90).build()));
	public static final RegistryObject<PlacedFeature> PLACED_HUGE_LILY_PAD = PLACED_FEATURES.register("huge_lily_pad", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.HUGE_LILY_PAD.get()), ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, RarityFilter.onAverageOnceEvery(20), InSquarePlacement.spread(), CountPlacement.of(10), BiomeFilter.biome()).build()));
	public static final RegistryObject<PlacedFeature> PLACED_HUGE_WATER_LILY = PLACED_FEATURES.register("huge_water_lily", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.HUGE_WATER_LILY.get()), ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, RarityFilter.onAverageOnceEvery(25), InSquarePlacement.spread(), CountPlacement.of(5), BiomeFilter.biome()).build()));
	public static final RegistryObject<PlacedFeature> PLACED_CICADA_LAMPPOST = PLACED_FEATURES.register("cicada_lamppost", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.CICADA_LAMPPOST.get()), ImmutableList.<PlacementModifier>builder().build()));
	public static final RegistryObject<PlacedFeature> PLACED_FIREFLY_LAMPPOST = PLACED_FEATURES.register("firefly_lamppost", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.FIREFLY_LAMPPOST.get()), ImmutableList.<PlacementModifier>builder().build()));
	public static final RegistryObject<PlacedFeature> PLACED_MONOLITH = PLACED_FEATURES.register("monolith", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.MONOLITH.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 90).build()));
	public static final RegistryObject<PlacedFeature> PLACED_MUSHGLOOM_CLUSTER = PLACED_FEATURES.register("mushgloom_cluster", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.MUSHGLOOM_CLUSTER.get()), ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, CountPlacement.of(5), InSquarePlacement.spread(), BiomeFilter.biome()).build()));
	public static final RegistryObject<PlacedFeature> PLACED_MYCELIUM_BLOB = PLACED_FEATURES.register("mycelium_blob", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.MYCELIUM_BLOB.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 3).build()));
	public static final RegistryObject<PlacedFeature> PLACED_OUTSIDE_STALAGMITE = PLACED_FEATURES.register("outside_stalagmite", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.OUTSIDE_STALAGMITE.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 88).build()));
	public static final RegistryObject<PlacedFeature> PLACED_PLANT_ROOTS = PLACED_FEATURES.register("plant_roots", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.PLANT_ROOTS.get()), tfFeatureCheckArea(OutOfStructureFilter.checkUnderground(), 1, CountPlacement.of(4), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(10))).build()));
	public static final RegistryObject<PlacedFeature> PLACED_PUMPKIN_LAMPPOST = PLACED_FEATURES.register("pumpkin_lamppost", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.PUMPKIN_LAMPPOST.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 1).build()));
	public static final RegistryObject<PlacedFeature> PLACED_SMOKER = PLACED_FEATURES.register("smoker", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.SMOKER.get()), ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), BiomeFilter.biome()).build()));
	public static final RegistryObject<PlacedFeature> PLACED_STONE_CIRCLE = PLACED_FEATURES.register("stone_circle", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.STONE_CIRCLE.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 105).build()));
	public static final RegistryObject<PlacedFeature> PLACED_THORNS = PLACED_FEATURES.register("thorns", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.THORNS.get()), ImmutableList.<PlacementModifier>builder().add(ChunkBlanketingModifier.addThorns(), BiomeFilter.biome()).build()));
	public static final RegistryObject<PlacedFeature> PLACED_TORCH_BERRIES = PLACED_FEATURES.register("torch_berries", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.TORCH_BERRIES.get()), ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(60)), CountPlacement.of(8), OutOfStructureFilter.checkUnderground(), BiomeFilter.biome()).build()));
	public static final RegistryObject<PlacedFeature> PLACED_TROLL_ROOTS = PLACED_FEATURES.register("troll_roots", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.TROLL_ROOTS.get()), ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(60)), CountPlacement.of(8), BiomeFilter.biome()).build()));
	public static final RegistryObject<PlacedFeature> PLACED_VANILLA_ROOTS = PLACED_FEATURES.register("vanilla_roots", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.VANILLA_ROOTS.get()), tfFeatureCheckArea(OutOfStructureFilter.checkUnderground(), 1, CountPlacement.of(16), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(10))).build()));
	public static final RegistryObject<PlacedFeature> PLACED_WEBS = PLACED_FEATURES.register("webs", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.WEBS.get()), ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, InSquarePlacement.spread(), CountPlacement.of(60), BiomeFilter.biome()).build()));
	public static final RegistryObject<PlacedFeature> PLACED_WOOD_ROOTS_SPREAD = PLACED_FEATURES.register("wood_roots", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.WOOD_ROOTS_SPREAD.get()), tfFeatureCheckArea(OutOfStructureFilter.checkUnderground(), 40, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0))).build()));
	public static final RegistryObject<PlacedFeature> PLACED_SNOW_UNDER_TREES = PLACED_FEATURES.register("snow_under_trees", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.SNOW_UNDER_TREES.get()), ImmutableList.<PlacementModifier>builder().add(BiomeFilter.biome()).build()));
	public static final RegistryObject<PlacedFeature> PLACED_TF_OAK_FALLEN_LOG = PLACED_FEATURES.register("tf_oak_fallen_log", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.TF_OAK_FALLEN_LOG.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build()));
	public static final RegistryObject<PlacedFeature> PLACED_CANOPY_FALLEN_LOG = PLACED_FEATURES.register("canopy_fallen_log", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.CANOPY_FALLEN_LOG.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build()));
	public static final RegistryObject<PlacedFeature> PLACED_MANGROVE_FALLEN_LOG = PLACED_FEATURES.register("mangrove_fallen_log", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.MANGROVE_FALLEN_LOG.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build()));
	public static final RegistryObject<PlacedFeature> PLACED_OAK_FALLEN_LOG = PLACED_FEATURES.register("oak_fallen_log", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.OAK_FALLEN_LOG.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build()));
	public static final RegistryObject<PlacedFeature> PLACED_SPRUCE_FALLEN_LOG = PLACED_FEATURES.register("spruce_fallen_log", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.SPRUCE_FALLEN_LOG.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build()));
	public static final RegistryObject<PlacedFeature> PLACED_BIRCH_FALLEN_LOG = PLACED_FEATURES.register("birch_fallen_log", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.BIRCH_FALLEN_LOG.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build()));
	public static final RegistryObject<PlacedFeature> PLACED_SMALL_GRANITE = PLACED_FEATURES.register("small_granite", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.SMALL_GRANITE.get()), ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(60), InSquarePlacement.spread(), CountPlacement.of(5)).build()));
	public static final RegistryObject<PlacedFeature> PLACED_SMALL_DIORITE = PLACED_FEATURES.register("small_diorite", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.SMALL_DIORITE.get()), ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(60), InSquarePlacement.spread(), CountPlacement.of(5)).build()));
	public static final RegistryObject<PlacedFeature> PLACED_SMALL_ANDESITE = PLACED_FEATURES.register("small_andesite", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.SMALL_ANDESITE.get()), ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(60), InSquarePlacement.spread(), CountPlacement.of(5)).build()));
	public static final RegistryObject<PlacedFeature> PLACED_DARK_MUSHGLOOMS = PLACED_FEATURES.register("dark_mushglooms", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.DARK_MUSHGLOOMS.get()), ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(30), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()).build()));
	public static final RegistryObject<PlacedFeature> PLACED_DARK_PUMPKINS = PLACED_FEATURES.register("dark_pumpkins", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.DARK_PUMPKINS.get()), ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(30), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()).build()));
	public static final RegistryObject<PlacedFeature> PLACED_DARK_GRASS = PLACED_FEATURES.register("dark_grass", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.DARK_GRASS.get()), ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(4), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()).build()));
	public static final RegistryObject<PlacedFeature> PLACED_DARK_FERNS = PLACED_FEATURES.register("dark_ferns", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.DARK_FERNS.get()), ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(4), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()).build()));
	public static final RegistryObject<PlacedFeature> PLACED_DARK_MUSHROOMS = PLACED_FEATURES.register("dark_mushrooms", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.DARK_MUSHROOMS.get()), ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()).build()));
	public static final RegistryObject<PlacedFeature> PLACED_DARK_DEAD_BUSHES = PLACED_FEATURES.register("dark_dead_bushes", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.DARK_DEAD_BUSHES.get()), ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(15), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()).build()));

	//public static final RegistryObject<PlacedFeature> PLACED_WELL_PLACER = PLACED_FEATURES.register("well_placer", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.WELL_PLACER.get()), tfFeatureCheckArea(OutOfStructureFilter.checkBoth(), 80).build()));
	//public static final RegistryObject<PlacedFeature> PLACED_LAMPPOST_PLACER = PLACED_FEATURES.register("lamppost_placer", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.LAMPPOST_PLACER.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 2).build()));
	//public static final RegistryObject<PlacedFeature> PLACED_DEFAULT_FALLEN_LOGS = PLACED_FEATURES.register("default_fallen_logs", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.DEFAULT_FALLEN_LOGS.get()), tfFeatureCheckArea(OutOfStructureFilter.checkSurface(), 40).build()));

	public static final RegistryObject<PlacedFeature> PLACED_GRASS_PLACER = PLACED_FEATURES.register("grass_placer", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.GRASS_PLACER.get()), ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, CountPlacement.of(30), InSquarePlacement.spread(), RarityFilter.onAverageOnceEvery(3)).build()));
	public static final RegistryObject<PlacedFeature> PLACED_FOREST_GRASS_PLACER = PLACED_FEATURES.register("forest_grass_placer", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.FOREST_GRASS_PLACER.get()), ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, CountPlacement.of(30), InSquarePlacement.spread(), RarityFilter.onAverageOnceEvery(3)).build()));
	public static final RegistryObject<PlacedFeature> PLACED_FLOWER_PLACER = PLACED_FEATURES.register("flower_placer", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.FLOWER_PLACER.get()), ImmutableList.<PlacementModifier>builder().add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, CountPlacement.of(25), InSquarePlacement.spread(), RarityFilter.onAverageOnceEvery(15)).build()));

	//Trees!
	public static final RegistryObject<PlacedFeature> PLACED_DEAD_CANOPY_TREE = PLACED_FEATURES.register("tree/dead_canopy_tree", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.DEAD_CANOPY_TREE.get()), tfTreeCheckArea(PlacementUtils.countExtra(2, 0.1F, 1), TFBlocks.CANOPY_SAPLING.get().defaultBlockState())));
	public static final RegistryObject<PlacedFeature> PLACED_MANGROVE_TREE = PLACED_FEATURES.register("tree/mangrove_tree", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.MANGROVE_TREE.get()), List.of(PlacementUtils.countExtra(3, 0.1F, 1), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(6), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, OutOfStructureFilter.checkSurface(), PlacementUtils.filteredByBlockSurvival(TFBlocks.DARKWOOD_SAPLING.get()), BiomeFilter.biome())));
	public static final RegistryObject<PlacedFeature> PLACED_TWILIGHT_OAK_TREE = PLACED_FEATURES.register("tree/twilight_oak_tree", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.TWILIGHT_OAK_TREE.get()), tfTreeCheckArea(PlacementUtils.countExtra(2, 0.1F, 1), TFBlocks.TWILIGHT_OAK_SAPLING.get().defaultBlockState())));
	public static final RegistryObject<PlacedFeature> PLACED_DENSE_TREE = PLACED_FEATURES.register("tree/dense_tree", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.TWILIGHT_OAK_TREE.get()), tfTreeCheckArea(PlacementUtils.countExtra(7, 0.1F, 1), TFBlocks.TWILIGHT_OAK_SAPLING.get().defaultBlockState())));
	public static final RegistryObject<PlacedFeature> PLACED_SAVANNAH_OAK_TREE = PLACED_FEATURES.register("tree/savannah_oak_tree", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.TWILIGHT_OAK_TREE.get()), tfTreeCheckArea(PlacementUtils.countExtra(0, 0.1F, 1), TFBlocks.TWILIGHT_OAK_SAPLING.get().defaultBlockState())));
	public static final RegistryObject<PlacedFeature> PLACED_SWAMPY_OAK_TREE = PLACED_FEATURES.register("tree/swampy_oak_tree", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.SWAMPY_OAK_TREE.get()), tfTreeCheckArea(PlacementUtils.countExtra(4, 0.1F, 1), TFBlocks.TWILIGHT_OAK_SAPLING.get().defaultBlockState())));
	public static final RegistryObject<PlacedFeature> PLACED_DARKWOOD_TREE = PLACED_FEATURES.register("tree/darkwood_tree", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.DARKWOOD_TREE.get()), List.of(PlacementUtils.countExtra(5, 0.1F, 1), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(0), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, new OutOfStructureFilter(true, false, 16), PlacementUtils.filteredByBlockSurvival(TFBlocks.DARKWOOD_SAPLING.get()), BiomeFilter.biome())));
	public static final RegistryObject<PlacedFeature> PLACED_SNOWY_SPRUCE_TREE = PLACED_FEATURES.register("tree/snowy_spruce", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.SNOWY_SPRUCE_TREE.get()), tfTreeCheckArea(Blocks.SPRUCE_SAPLING.defaultBlockState())));

	public static final RegistryObject<PlacedFeature> PLACED_CANOPY_TREES = PLACED_FEATURES.register("tree/selector/canopy_trees", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.CANOPY_TREES.get()), tfTreeCheckArea(PlacementUtils.countExtra(2, 0.1F, 1), TFBlocks.CANOPY_SAPLING.get().defaultBlockState())));
	public static final RegistryObject<PlacedFeature> PLACED_DENSE_CANOPY_TREES = PLACED_FEATURES.register("tree/selector/dense_canopy_trees", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.DENSE_CANOPY_TREES.get()), tfTreeCheckArea(PlacementUtils.countExtra(5, 0.1F, 1), TFBlocks.CANOPY_SAPLING.get().defaultBlockState())));
	public static final RegistryObject<PlacedFeature> PLACED_FIREFLY_FOREST_TREES = PLACED_FEATURES.register("tree/selector/firefly_forest_trees", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.FIREFLY_FOREST_TREES.get()), tfTreeCheckArea(PlacementUtils.countExtra(5, 0.1F, 1), TFBlocks.CANOPY_SAPLING.get().defaultBlockState())));
	public static final RegistryObject<PlacedFeature> PLACED_DARK_FOREST_TREES = PLACED_FEATURES.register("tree/selector/dark_forest_trees", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.DARK_FOREST_TREES.get()), tfTreeCheckArea(PlacementUtils.countExtra(5, 0.1F, 1), TFBlocks.DARKWOOD_SAPLING.get().defaultBlockState(), false)));
	public static final RegistryObject<PlacedFeature> PLACED_HIGHLANDS_TREES = PLACED_FEATURES.register("tree/selector/highlands_trees", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.HIGHLANDS_TREES.get()), tfTreeCheckArea(PlacementUtils.countExtra(5, 0.1F, 1), Blocks.SPRUCE_SAPLING.defaultBlockState())));
	public static final RegistryObject<PlacedFeature> PLACED_ENCHANTED_FOREST_TREES = PLACED_FEATURES.register("tree/selector/enchanted_forest_trees", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.ENCHANTED_FOREST_TREES.get()), tfTreeCheckArea(PlacementUtils.countExtra(10, 0.1F, 1), TFBlocks.RAINBOW_OAK_SAPLING.get().defaultBlockState())));
	public static final RegistryObject<PlacedFeature> PLACED_SNOWY_FOREST_TREES = PLACED_FEATURES.register("tree/selector/snowy_forest_trees", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.SNOWY_FOREST_TREES.get()), tfTreeCheckArea(PlacementUtils.countExtra(10, 0.1F, 1), TFBlocks.CANOPY_SAPLING.get().defaultBlockState())));
	public static final RegistryObject<PlacedFeature> PLACED_VANILLA_TF_TREES = PLACED_FEATURES.register("tree/selector/vanilla_trees", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.VANILLA_TF_TREES.get()), tfTreeCheckArea(PlacementUtils.countExtra(10, 0.1F, 1), TFBlocks.TWILIGHT_OAK_SAPLING.get().defaultBlockState())));
	public static final RegistryObject<PlacedFeature> PLACED_VANILLA_TF_BIG_MUSH = PLACED_FEATURES.register("tree/selector/vanilla_mushrooms", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.VANILLA_TF_BIG_MUSH.get()), tfTreeCheckArea(TFBlocks.CANOPY_SAPLING.get().defaultBlockState())));

	public static final RegistryObject<PlacedFeature> PLACED_CANOPY_MUSHROOMS_SPARSE = PLACED_FEATURES.register("mushroom/canopy_mushrooms_sparse", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.CANOPY_MUSHROOMS_SPARSE.get()), tfTreeCheckArea(PlacementUtils.countExtra(8, 0.1F, 1), TFBlocks.CANOPY_SAPLING.get().defaultBlockState())));
	
	public static final RegistryObject<PlacedFeature> PLACED_CANOPY_MUSHROOMS_DENSE = PLACED_FEATURES.register("mushroom/canopy_mushrooms_dense", () -> new PlacedFeature(Holder.direct(TFConfiguredFeatures.CANOPY_MUSHROOMS_DENSE.get()), tfTreeCheckArea(PlacementUtils.countExtra(8, 0.1F, 1), TFBlocks.CANOPY_SAPLING.get().defaultBlockState())));

	private static List<PlacementModifier> tfTreeCheckArea(BlockState sapling) {
		return List.of(InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(0), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, OutOfStructureFilter.checkSurface(), PlacementUtils.filteredByBlockSurvival(sapling.getBlock()), BiomeFilter.biome());
	}

	private static List<PlacementModifier> tfTreeCheckArea(PlacementModifier count, BlockState sapling) {
		return tfTreeCheckArea(count, sapling, true);
	}

	private static List<PlacementModifier> tfTreeCheckArea(PlacementModifier count, BlockState sapling, boolean checkSurvival) {
		ImmutableList.Builder<PlacementModifier> list = ImmutableList.builder();
		list.add(count, InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(0), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, OutOfStructureFilter.checkSurface());
		if (checkSurvival) {
			list.add(PlacementUtils.filteredByBlockSurvival(sapling.getBlock()));
		}
		list.add(BiomeFilter.biome());
		return list.build();
	}

	private static ImmutableList.Builder<PlacementModifier> tfFeatureCheckArea(OutOfStructureFilter filter, int rarity) {
		return ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, filter, BiomeFilter.biome());
	}

	private static ImmutableList.Builder<PlacementModifier> tfFeatureCheckArea(OutOfStructureFilter filter, int rarity, PlacementModifier... extra) {
		return ImmutableList.<PlacementModifier>builder().add(extra).add(filter, RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
	}
}
