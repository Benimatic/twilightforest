package twilightforest.world.registration.features;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.world.components.feature.config.TFTreeFeatureConfig;
import twilightforest.world.components.placements.OutOfStructureFilter;
import twilightforest.world.registration.BlockConstants;
import twilightforest.world.registration.TFBiomeFeatures;
import twilightforest.world.registration.TreeConfigurations;
import twilightforest.world.registration.TwilightFeatures;

import java.util.List;

public class TFTreeFeatures {

	//base configs
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> TWILIGHT_OAK_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/twilight_oak_tree"), Feature.TREE, TreeConfigurations.TWILIGHT_OAK);
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> SWAMPY_OAK_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/swampy_oak_tree"), Feature.TREE, TreeConfigurations.SWAMPY_OAK);
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> CANOPY_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/canopy_tree"), Feature.TREE, TreeConfigurations.CANOPY_TREE);
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> FIREFLY_CANOPY_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/firefly_canopy_tree"), Feature.TREE, TreeConfigurations.CANOPY_TREE_FIREFLY);
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> DEAD_CANOPY_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/dead_canopy_tree"), Feature.TREE, TreeConfigurations.CANOPY_TREE_DEAD);
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> MANGROVE_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/mangrove_tree"), Feature.TREE, TreeConfigurations.MANGROVE_TREE);
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> DARKWOOD_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/darkwood_tree"), TFBiomeFeatures.DARK_CANOPY_TREE.get(), TreeConfigurations.DARKWOOD_TREE);
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> HOMEGROWN_DARKWOOD_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/homegrown_darkwood_tree"), TFBiomeFeatures.DARK_CANOPY_TREE.get(), TreeConfigurations.HOMEGROWN_DARKWOOD_TREE);
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> DARKWOOD_LANTERN_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/darkwood_lantern_tree"), TFBiomeFeatures.DARK_CANOPY_TREE.get(), TreeConfigurations.DARKWOOD_LANTERN_TREE);
	public static final Holder<ConfiguredFeature<TFTreeFeatureConfig, Feature<TFTreeFeatureConfig>>> TIME_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/time_tree"), TFBiomeFeatures.TREE_OF_TIME.get(), TreeConfigurations.TIME_TREE);
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> TRANSFORMATION_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/transformation_tree"), Feature.TREE, TreeConfigurations.TRANSFORM_TREE);
	public static final Holder<ConfiguredFeature<TFTreeFeatureConfig, Feature<TFTreeFeatureConfig>>> MINING_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/mining_tree"), TFBiomeFeatures.MINERS_TREE.get(), TreeConfigurations.MINING_TREE);
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> SORTING_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/sorting_tree"), Feature.TREE, TreeConfigurations.SORT_TREE);
	//public static final Holder<ConfiguredFeature<TFTreeFeatureConfig, Feature<TFTreeFeatureConfig>>>> DENSE_OAK_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/dense_oak_tree"), TFBiomeFeatures.CANOPY_OAK.get(), TreeConfigurations.DENSE_OAK);
	public static final Holder<ConfiguredFeature<TFTreeFeatureConfig, Feature<TFTreeFeatureConfig>>> HOLLOW_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/hollow_tree"), TFBiomeFeatures.HOLLOW_TREE.get(), TreeConfigurations.HOLLOW_TREE);
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> RAINBOW_OAK_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/rainbow_oak"), Feature.TREE, TreeConfigurations.RAINBOAK_TREE);
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> LARGE_RAINBOW_OAK_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/large_rainbow_oak"), Feature.TREE, TreeConfigurations.LARGE_RAINBOAK_TREE);
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> BROWN_CANOPY_MUSHROOM_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mushroom/brown_canopy_mushroom"), Feature.TREE, TreeConfigurations.MUSHROOM_BROWN);
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> RED_CANOPY_MUSHROOM_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mushroom/red_canopy_mushroom"), Feature.TREE, TreeConfigurations.MUSHROOM_RED);
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> MEGA_SPRUCE_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/mega_spruce_tree"), TFBiomeFeatures.SNOW_TREE.get(), TreeConfigurations.BIG_SPRUCE);
	public static final Holder<ConfiguredFeature<TFTreeFeatureConfig, Feature<TFTreeFeatureConfig>>> LARGE_WINTER_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/large_winter_tree"), TFBiomeFeatures.LARGE_WINTER_TREE.get(), TreeConfigurations.LARGE_WINTER);
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> SNOWY_SPRUCE_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/snowy_spruce_tree"), TFBiomeFeatures.SNOW_TREE.get(), TreeFeatures.SPRUCE.value().config());
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> DARK_FOREST_OAK_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/dark_forest_oak_tree"), TFBiomeFeatures.DARK_CANOPY_TREE.get(), TreeFeatures.OAK.value().config());
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> DARK_FOREST_BIRCH_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/dark_forest_birch_tree"), TFBiomeFeatures.DARK_CANOPY_TREE.get(), TreeFeatures.BIRCH.value().config());
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> VANILLA_OAK_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/vanilla_oak_tree"), Feature.TREE, TreeFeatures.OAK.value().config());
	public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> VANILLA_BIRCH_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/vanilla_birch_tree"), Feature.TREE, TreeFeatures.BIRCH.value().config());

	//Placements
	public static final Holder<PlacedFeature> PLACED_DEAD_CANOPY_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/dead_canopy_tree"), DEAD_CANOPY_TREE, tfTreeCheckArea(PlacementUtils.countExtra(2, 0.1F, 1), BlockConstants.CANOPY_SAPLING));
	public static final Holder<PlacedFeature> PLACED_MANGROVE_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/mangrove_tree"), MANGROVE_TREE, List.of(PlacementUtils.countExtra(3, 0.1F, 1), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(6), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, OutOfStructureFilter.checkSurface(), PlacementUtils.filteredByBlockSurvival(TFBlocks.DARKWOOD_SAPLING.get()), BiomeFilter.biome()));
	public static final Holder<PlacedFeature> PLACED_TWILIGHT_OAK_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/twilight_oak_tree"), TWILIGHT_OAK_TREE, tfTreeCheckArea(PlacementUtils.countExtra(2, 0.1F, 1), BlockConstants.TF_OAK_SAPLING));
	public static final Holder<PlacedFeature> PLACED_DENSE_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/dense_tree"), TWILIGHT_OAK_TREE, tfTreeCheckArea(PlacementUtils.countExtra(7, 0.1F, 1), BlockConstants.TF_OAK_SAPLING));
	public static final Holder<PlacedFeature> PLACED_SAVANNAH_OAK_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/savannah_oak_tree"), TWILIGHT_OAK_TREE, tfTreeCheckArea(PlacementUtils.countExtra(0, 0.1F, 1), BlockConstants.TF_OAK_SAPLING));
	public static final Holder<PlacedFeature> PLACED_SWAMPY_OAK_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/swampy_oak_tree"), SWAMPY_OAK_TREE, tfTreeCheckArea(PlacementUtils.countExtra(4, 0.1F, 1), BlockConstants.TF_OAK_SAPLING));
	public static final Holder<PlacedFeature> PLACED_DARKWOOD_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/darkwood_tree"), DARKWOOD_TREE, List.of(PlacementUtils.countExtra(5, 0.1F, 1), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(0), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, new OutOfStructureFilter(true, false, 16), PlacementUtils.filteredByBlockSurvival(TFBlocks.DARKWOOD_SAPLING.get()), BiomeFilter.biome()));
	public static final Holder<PlacedFeature> PLACED_SNOWY_SPRUCE_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/snowy_spruce"), SNOWY_SPRUCE_TREE, tfTreeCheckArea(BlockConstants.SPRUCE_SAPLING));

	//random selectors
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, Feature<RandomFeatureConfiguration>>> CANOPY_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/selector/canopy_trees"), Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(CANOPY_TREE), 0.6F)), PlacementUtils.inlinePlaced(TWILIGHT_OAK_TREE)));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, Feature<RandomFeatureConfiguration>>> DENSE_CANOPY_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/selector/dense_canopy_trees"), Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(CANOPY_TREE), 0.7F)), PlacementUtils.inlinePlaced(TWILIGHT_OAK_TREE)));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, Feature<RandomFeatureConfiguration>>> FIREFLY_FOREST_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/selector/firefly_forest_trees"), Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(CANOPY_TREE), 0.33F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(FIREFLY_CANOPY_TREE), 0.45F)), PlacementUtils.inlinePlaced(TWILIGHT_OAK_TREE)));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, Feature<RandomFeatureConfiguration>>> DARK_FOREST_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/selector/dark_forest_trees"), Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(DARK_FOREST_BIRCH_TREE), 0.35F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(DARK_FOREST_OAK_TREE), 0.35F)), PlacementUtils.inlinePlaced(DARKWOOD_TREE)));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, Feature<RandomFeatureConfiguration>>> HIGHLANDS_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/selector/highlands_trees"), Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.SPRUCE), 0.25F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.PINE), 0.1F)), PlacementUtils.inlinePlaced(MEGA_SPRUCE_TREE)));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, Feature<RandomFeatureConfiguration>>> ENCHANTED_FOREST_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/selector/enchanted_forest_trees"), Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(VANILLA_OAK_TREE), 0.15F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(VANILLA_BIRCH_TREE), 0.15F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(LARGE_RAINBOW_OAK_TREE), 0.1F)), PlacementUtils.inlinePlaced(RAINBOW_OAK_TREE)));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, Feature<RandomFeatureConfiguration>>> SNOWY_FOREST_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/selector/snowy_forest_trees"), Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(MEGA_SPRUCE_TREE), 0.1F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(LARGE_WINTER_TREE), 0.01F)), PlacementUtils.inlinePlaced(SNOWY_SPRUCE_TREE)));
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, Feature<RandomFeatureConfiguration>>> VANILLA_TF_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/selector/vanilla_trees"), Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(VANILLA_BIRCH_TREE), 0.25F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(VANILLA_OAK_TREE), 0.25F)), PlacementUtils.inlinePlaced(TWILIGHT_OAK_TREE)));
	public static final Holder<ConfiguredFeature<RandomBooleanFeatureConfiguration, Feature<RandomBooleanFeatureConfiguration>>> VANILLA_TF_BIG_MUSH = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/selector/vanilla/vanilla_mushrooms"), Feature.RANDOM_BOOLEAN_SELECTOR, new RandomBooleanFeatureConfiguration(PlacementUtils.inlinePlaced(TreeFeatures.HUGE_RED_MUSHROOM), PlacementUtils.inlinePlaced(TreeFeatures.HUGE_BROWN_MUSHROOM)));


	public static final Holder<PlacedFeature> PLACED_CANOPY_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/selector/canopy_trees"), CANOPY_TREES, tfTreeCheckArea(PlacementUtils.countExtra(2, 0.1F, 1), BlockConstants.CANOPY_SAPLING));
	public static final Holder<PlacedFeature> PLACED_DENSE_CANOPY_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/selector/dense_canopy_trees"), DENSE_CANOPY_TREES, tfTreeCheckArea(PlacementUtils.countExtra(5, 0.1F, 1), BlockConstants.CANOPY_SAPLING));
	public static final Holder<PlacedFeature> PLACED_FIREFLY_FOREST_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/selector/firefly_forest_trees"), FIREFLY_FOREST_TREES, tfTreeCheckArea(PlacementUtils.countExtra(5, 0.1F, 1), BlockConstants.CANOPY_SAPLING));
	public static final Holder<PlacedFeature> PLACED_DARK_FOREST_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/selector/dark_forest_trees"), DARK_FOREST_TREES, tfTreeCheckArea(PlacementUtils.countExtra(5, 0.1F, 1), BlockConstants.DARKWOOD_SAPLING, false));
	public static final Holder<PlacedFeature> PLACED_HIGHLANDS_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/selector/highlands_trees"), HIGHLANDS_TREES, tfTreeCheckArea(PlacementUtils.countExtra(5, 0.1F, 1), BlockConstants.SPRUCE_SAPLING));
	public static final Holder<PlacedFeature> PLACED_ENCHANTED_FOREST_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/selector/enchanted_forest_trees"), ENCHANTED_FOREST_TREES, tfTreeCheckArea(PlacementUtils.countExtra(10, 0.1F, 1), BlockConstants.RAINBOW_SAPLING));
	public static final Holder<PlacedFeature> PLACED_SNOWY_FOREST_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/selector/snowy_forest_trees"), SNOWY_FOREST_TREES, tfTreeCheckArea(PlacementUtils.countExtra(10, 0.1F, 1), BlockConstants.CANOPY_SAPLING));
	public static final Holder<PlacedFeature> PLACED_VANILLA_TF_TREES = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/selector/vanilla_trees"), VANILLA_TF_TREES, tfTreeCheckArea(PlacementUtils.countExtra(10, 0.1F, 1), BlockConstants.TF_OAK_SAPLING));
	public static final Holder<PlacedFeature> PLACED_VANILLA_TF_BIG_MUSH = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/selector/vanilla_mushrooms"), VANILLA_TF_BIG_MUSH, tfTreeCheckArea(BlockConstants.CANOPY_SAPLING));

	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, Feature<NoneFeatureConfiguration>>> DUMMY_TREE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("tree/dummy"), Feature.NO_OP, NoneFeatureConfiguration.INSTANCE);

	//super funky tree placement lists
	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, Feature<RandomFeatureConfiguration>>> CANOPY_MUSHROOMS_SPARSE =
			TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mushroom/canopy_mushrooms_sparse"),
					Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(BROWN_CANOPY_MUSHROOM_TREE), 0.15f),
							new WeightedPlacedFeature(PlacementUtils.inlinePlaced(RED_CANOPY_MUSHROOM_TREE), 0.05f)),
							PlacementUtils.inlinePlaced(DUMMY_TREE)));

	public static final Holder<PlacedFeature> PLACED_CANOPY_MUSHROOMS_SPARSE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mushroom/canopy_mushrooms_sparse"), CANOPY_MUSHROOMS_SPARSE, tfTreeCheckArea(PlacementUtils.countExtra(8, 0.1F, 1), BlockConstants.CANOPY_SAPLING));

	public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, Feature<RandomFeatureConfiguration>>> CANOPY_MUSHROOMS_DENSE =
			TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mushroom/canopy_mushrooms_dense"),
					Feature.RANDOM_SELECTOR,
					new RandomFeatureConfiguration(List.of(
						new WeightedPlacedFeature(PlacementUtils.inlinePlaced(BROWN_CANOPY_MUSHROOM_TREE), 0.675f),
						new WeightedPlacedFeature(PlacementUtils.inlinePlaced(RED_CANOPY_MUSHROOM_TREE), 0.225f)),
						PlacementUtils.inlinePlaced(DUMMY_TREE)));

	public static final Holder<PlacedFeature> PLACED_CANOPY_MUSHROOMS_DENSE = TwilightFeatures.registerWorldFeature(TwilightForestMod.prefix("mushroom/canopy_mushrooms_dense"),
			CANOPY_MUSHROOMS_DENSE, tfTreeCheckArea(PlacementUtils.countExtra(8, 0.1F, 1), BlockConstants.CANOPY_SAPLING));

	//why does it only place hollow trees this way?

	private static List<PlacementModifier> tfTreeCheckArea(BlockState sapling) {
		return List.of(InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(0), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, OutOfStructureFilter.checkSurface(), PlacementUtils.filteredByBlockSurvival(sapling.getBlock()), BiomeFilter.biome());
	}

	private static List<PlacementModifier> tfTreeCheckArea(PlacementModifier count, BlockState sapling) {
		return tfTreeCheckArea(count, sapling, true);
	}

	private static List<PlacementModifier> tfTreeCheckArea(PlacementModifier count, BlockState sapling, boolean checkSurvival) {
		ImmutableList.Builder<PlacementModifier> list = ImmutableList.<PlacementModifier>builder();
		list.add(count, InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(0), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, OutOfStructureFilter.checkSurface());
		if (checkSurvival) {
			list.add(PlacementUtils.filteredByBlockSurvival(sapling.getBlock()));
		}
		list.add(BiomeFilter.biome());
		return list.build();
	}

}
