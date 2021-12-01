package twilightforest.world.registration.features;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import twilightforest.TwilightForestMod;
import twilightforest.world.components.feature.config.TFTreeFeatureConfig;
import twilightforest.world.components.placements.ChunkCenterModifier;
import twilightforest.world.components.placements.OutOfStructureFilter;
import twilightforest.world.registration.TFBiomeFeatures;
import twilightforest.world.registration.TreeConfigurations;
import twilightforest.world.registration.TwilightFeatures;

import java.util.List;

public class TFTreeFeatures {

	//base configs
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> TWILIGHT_OAK_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/twilight_oak"), Feature.TREE.configured(TreeConfigurations.TWILIGHT_OAK));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> SWAMPY_OAK_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/swampy_oak"), Feature.TREE.configured(TreeConfigurations.SWAMPY_OAK));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> CANOPY_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/canopy_tree"), Feature.TREE.configured(TreeConfigurations.CANOPY_TREE));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> FIREFLY_CANOPY_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/firefly_canopy_tree"), Feature.TREE.configured(TreeConfigurations.CANOPY_TREE_FIREFLY));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> DEAD_CANOPY_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/dead_canopy_tree"), Feature.TREE.configured(TreeConfigurations.CANOPY_TREE_DEAD));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> MANGROVE_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/mangrove_tree"), Feature.TREE.configured(TreeConfigurations.MANGROVE_TREE));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> DARKWOOD_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/darkwood_tree"), TFBiomeFeatures.DARK_CANOPY_TREE.get().configured(TreeConfigurations.DARKWOOD_TREE));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> HOMEGROWN_DARKWOOD_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/homegrown_darkwood_tree"), TFBiomeFeatures.DARK_CANOPY_TREE.get().configured(TreeConfigurations.HOMEGROWN_DARKWOOD_TREE));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> DARKWOOD_LANTERN_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/darkwood_lantern_tree"), TFBiomeFeatures.DARK_CANOPY_TREE.get().configured(TreeConfigurations.DARKWOOD_LANTERN_TREE));
	public static final ConfiguredFeature<TFTreeFeatureConfig, ? extends Feature<?>> TIME_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/time_tree"), TFBiomeFeatures.TREE_OF_TIME.get().configured(TreeConfigurations.TIME_TREE));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> TRANSFORM_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/transform_tree"), Feature.TREE.configured(TreeConfigurations.TRANSFORM_TREE));
	public static final ConfiguredFeature<TFTreeFeatureConfig, ? extends Feature<?>> MINING_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/mining_tree"), TFBiomeFeatures.MINERS_TREE.get().configured(TreeConfigurations.MINING_TREE));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> SORT_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/sort_tree"), Feature.TREE.configured(TreeConfigurations.SORT_TREE));
	//public static final ConfiguredFeature<TFTreeFeatureConfig, ? extends Feature<?>> DENSE_OAK_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/dense_oak_tree"), TFBiomeFeatures.CANOPY_OAK.get().configured(TreeConfigurations.DENSE_OAK));
	public static final ConfiguredFeature<TFTreeFeatureConfig, ? extends Feature<?>> HOLLOW_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/hollow_tree"), TFBiomeFeatures.HOLLOW_TREE.get().configured(TreeConfigurations.HOLLOW_TREE));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> RAINBOW_OAK_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/rainbow_oak"), Feature.TREE.configured(TreeConfigurations.RAINBOAK_TREE));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> LARGE_RAINBOW_OAK_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/large_rainbow_oak"), Feature.TREE.configured(TreeConfigurations.LARGE_RAINBOAK_TREE));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> BROWN_CANOPY_MUSHROOM_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mushroom/base/brown_canopy_mushroom"), Feature.TREE.configured(TreeConfigurations.MUSHROOM_BROWN));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> RED_CANOPY_MUSHROOM_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mushroom/base/red_canopy_mushroom"), Feature.TREE.configured(TreeConfigurations.MUSHROOM_RED));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> MEGA_SPRUCE_NO_PODZOL_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/mega_spruce_no_podzol"), TFBiomeFeatures.SNOW_TREE.get().configured(TreeConfigurations.BIG_SPRUCE));
	public static final ConfiguredFeature<TFTreeFeatureConfig, ? extends Feature<?>> LARGE_WINTER_TREE_BASE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/large_winter_tree_base"), TFBiomeFeatures.LARGE_WINTER_TREE.get().configured(TreeConfigurations.LARGE_WINTER));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> SNOW_SPRUCE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("snow_spruce"), TFBiomeFeatures.SNOW_TREE.get().configured(TreeFeatures.SPRUCE.config()));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> OAK_DARK_FOREST = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/oak_dark_forest"), TFBiomeFeatures.DARK_CANOPY_TREE.get().configured(TreeFeatures.OAK.config()));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> BIRCH_DARK_FOREST = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/birch_dark_forest"), TFBiomeFeatures.DARK_CANOPY_TREE.get().configured(TreeFeatures.BIRCH.config()));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> VANILLA_TF_OAK = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/vanilla_tf_oak"), Feature.TREE.configured(TreeFeatures.OAK.config()));
	public static final ConfiguredFeature<TreeConfiguration, ? extends Feature<?>> VANILLA_TF_BIRCH = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/base/vanilla_tf_birch"), Feature.TREE.configured(TreeFeatures.BIRCH.config()));

	//Placements
	public static final PlacedFeature CANOPY_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/canopy_trees"), Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(CANOPY_TREE_BASE.placed(), 0.6F)), TWILIGHT_OAK_BASE.placed())).placed(tfTreeCheckArea(PlacementUtils.countExtra(2, 0.1F, 1)).build()));
	public static final PlacedFeature DENSE_CANOPY_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/dense_canopy_trees"), Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(CANOPY_TREE_BASE.placed(), 0.7F)), TWILIGHT_OAK_BASE.placed())).placed(tfTreeCheckArea(PlacementUtils.countExtra(5, 0.1F, 1)).build()));
	public static final PlacedFeature DEAD_CANOPY_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/dead_canopy_trees"), DEAD_CANOPY_TREE_BASE.placed(tfTreeCheckArea(PlacementUtils.countExtra(2, 0.1F, 1)).build()));
	public static final PlacedFeature MANGROVE_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/mangrove_trees"), MANGROVE_TREE_BASE.placed(PlacementUtils.countExtra(3, 0.1F, 1), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(6), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, OutOfStructureFilter.checkSurface(), BiomeFilter.biome()));
	public static final PlacedFeature TWILIGHT_OAK_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/twilight_oak_trees"), TWILIGHT_OAK_BASE.placed(tfTreeCheckArea(PlacementUtils.countExtra(2, 0.1F, 1)).build()));
	public static final PlacedFeature DENSE_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/dense_trees"), TWILIGHT_OAK_BASE.placed(tfTreeCheckArea(PlacementUtils.countExtra(7, 0.1F, 1)).build()));
	public static final PlacedFeature SAVANNAH_OAK_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/savannah_oak_trees"), TWILIGHT_OAK_BASE.placed(tfTreeCheckArea(PlacementUtils.countExtra(0, 0.1F, 1)).build()));
	public static final PlacedFeature SWAMPY_OAK_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/swampy_oak_trees"), SWAMPY_OAK_BASE.placed(tfTreeCheckArea(PlacementUtils.countExtra(4, 0.1F, 1)).build()));
	public static final PlacedFeature FIREFLY_FOREST_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/firefly_forest_trees"), Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(CANOPY_TREE_BASE.placed(), 0.33F), new WeightedPlacedFeature(FIREFLY_CANOPY_TREE_BASE.placed(), 0.45F)), TWILIGHT_OAK_BASE.placed())).placed(tfTreeCheckArea(PlacementUtils.countExtra(5, 0.1F, 1)).build()));
	public static final PlacedFeature DARK_FOREST_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/dark_forest_trees"), Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(BIRCH_DARK_FOREST.placed(), 0.35F), new WeightedPlacedFeature(OAK_DARK_FOREST.placed(), 0.35F)), DARKWOOD_TREE_BASE.placed())).placed(tfTreeCheckArea(PlacementUtils.countExtra(5, 0.1F, 1)).build()));
	public static final PlacedFeature DARKWOOD_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/darkwood_trees"), DARKWOOD_TREE_BASE.placed(PlacementUtils.countExtra(5, 0.1F, 1), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(0), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, new OutOfStructureFilter(true, false, 16), BiomeFilter.biome()));
	public static final PlacedFeature HIGHLANDS_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/highlands_trees"), Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(TreeFeatures.SPRUCE.placed(), 0.25F), new WeightedPlacedFeature(TreeFeatures.PINE.placed(), 0.1F)), MEGA_SPRUCE_NO_PODZOL_BASE.placed())).placed(tfTreeCheckArea(PlacementUtils.countExtra(5, 0.1F, 1)).build()));
	public static final PlacedFeature ENCHANTED_FOREST_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/enchanted_forest_trees"), Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(VANILLA_TF_OAK.placed(), 0.15F), new WeightedPlacedFeature(VANILLA_TF_BIRCH.placed(), 0.15F), new WeightedPlacedFeature(LARGE_RAINBOW_OAK_BASE.placed(), 0.1F)), RAINBOW_OAK_TREE_BASE.placed())).placed(tfTreeCheckArea(PlacementUtils.countExtra(10, 0.1F, 1)).build()));
	public static final PlacedFeature SNOWY_FOREST_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/snowy_forest_trees"), Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(MEGA_SPRUCE_NO_PODZOL_BASE.placed(), 0.1F), new WeightedPlacedFeature(LARGE_WINTER_TREE_BASE.placed(), 0.01F)), SNOW_SPRUCE.placed())).placed(tfTreeCheckArea(PlacementUtils.countExtra(10, 0.1F, 1)).build()));
	public static final PlacedFeature VANILLA_TF_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/vanilla_tf_trees"), Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(VANILLA_TF_BIRCH.placed(), 0.25F), new WeightedPlacedFeature(VANILLA_TF_OAK.placed(), 0.25F)), TWILIGHT_OAK_BASE.placed())).placed(tfTreeCheckArea(PlacementUtils.countExtra(10, 0.1F, 1)).build()));
	public static final PlacedFeature SNOW_SPRUCE_SNOWY = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("snow_spruce_snowy"), SNOW_SPRUCE.placed(tfTreeCheckArea().build()));
	public static final PlacedFeature VANILLA_TF_BIG_MUSH = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/vanilla/vanilla_tf_big_mush"), Feature.RANDOM_BOOLEAN_SELECTOR.configured(new RandomBooleanFeatureConfiguration(
			TreeFeatures.HUGE_RED_MUSHROOM::placed,
			TreeFeatures.HUGE_BROWN_MUSHROOM::placed
	)).placed(tfTreeCheckArea().build()));

	//super funky tree placement lists
	public static final PlacedFeature CANOPY_MUSHROOMS_SPARSE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mushroom/canopy_mushrooms_sparse"),
			Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(List.of(
							new WeightedPlacedFeature(TFTreeFeatures.BROWN_CANOPY_MUSHROOM_TREE_BASE.placed(), 0.15f),
							new WeightedPlacedFeature(TFTreeFeatures.BROWN_CANOPY_MUSHROOM_TREE_BASE.placed(), 0.05f)),
							Feature.NO_OP.configured(NoneFeatureConfiguration.INSTANCE).placed()))
					.placed(tfTreeCheckArea(PlacementUtils.countExtra(8, 0.1F, 1)).build()));

	public static final PlacedFeature CANOPY_MUSHROOMS_DENSE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mushroom/canopy_mushrooms_dense"),
			Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(List.of(
					new WeightedPlacedFeature(TFTreeFeatures.BROWN_CANOPY_MUSHROOM_TREE_BASE.placed(), 0.675f),
					new WeightedPlacedFeature(TFTreeFeatures.RED_CANOPY_MUSHROOM_TREE_BASE.placed(), 0.225f)),
					Feature.NO_OP.configured(NoneFeatureConfiguration.INSTANCE).placed()))
					.placed(tfTreeCheckArea(PlacementUtils.countExtra(8, 0.1F, 1)).build()));

	//why does it only place hollow trees this way?
	public static final PlacedFeature HOLLOW_TREE_PLACER = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/hollow_placer"),
			Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(
					new WeightedPlacedFeature(TFTreeFeatures.HOLLOW_TREE_BASE.placed(), 0.04f)),
					Feature.NO_OP.configured(NoneFeatureConfiguration.INSTANCE).placed()))
					.placed(
							SurfaceWaterDepthFilter.forMaxDepth(0),
							PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
							new OutOfStructureFilter(true, false, 16),
							ChunkCenterModifier.center(),
							BiomeFilter.biome()
					));

	public static final PlacementModifier TREE_THRESHOLD = SurfaceWaterDepthFilter.forMaxDepth(0);

	private static ImmutableList.Builder<PlacementModifier> tfTreeCheckArea() {
		return ImmutableList.<PlacementModifier>builder().add(InSquarePlacement.spread(), TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, OutOfStructureFilter.checkSurface(), BiomeFilter.biome());
	}

	private static ImmutableList.Builder<PlacementModifier> tfTreeCheckArea(PlacementModifier count) {
		return ImmutableList.<PlacementModifier>builder().add(count, InSquarePlacement.spread(), TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, OutOfStructureFilter.checkSurface(), BiomeFilter.biome());
	}

}
