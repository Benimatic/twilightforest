package twilightforest.worldgen;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Lantern;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.util.UniformInt;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import twilightforest.block.FireflyBlock;
import twilightforest.block.TFLogBlock;
import twilightforest.block.TFBlocks;
import twilightforest.world.feature.config.TFTreeFeatureConfig;
import twilightforest.worldgen.treeplacers.*;

import java.util.OptionalInt;

public final class TreeConfigurations {
    private static final int canopyDistancing = 5;

    public static final TreeConfiguration TWILIGHT_OAK = new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(BlockConstants.TOAK_LOG),
            new SimpleStateProvider(BlockConstants.TOAK_LEAVES),
            new BlobFoliagePlacer(UniformInt.fixed(2), UniformInt.fixed(0), 3),
            new StraightTrunkPlacer(4, 2, 0),
            new TwoLayersFeatureSize(1, 0, 1)
    )
            .decorators(ImmutableList.of(TreeDecorators.LIVING_ROOTS))
            .build();

    public static final TreeConfiguration SWAMPY_OAK = new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(BlockConstants.TOAK_LOG),
            new SimpleStateProvider(BlockConstants.TOAK_LEAVES),
            new BlobFoliagePlacer(UniformInt.fixed(2), UniformInt.fixed(0), 3),
            new StraightTrunkPlacer(4, 2, 0),
            new TwoLayersFeatureSize(1, 0, 1)
    )
            .decorators(ImmutableList.of(TreeDecorators.LIVING_ROOTS, LeaveVineDecorator.INSTANCE))
            .build();

    private final static int LEAF_SHAG_FACTOR = 24;
    public static final TreeConfiguration CANOPY_TREE = new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(BlockConstants.CANOPY_LOG),
            new SimpleStateProvider(BlockConstants.CANOPY_LEAVES),
            new LeafSpheroidFoliagePlacer(4.5f, 1.5f, UniformInt.fixed(0), 1, 0, -0.25f, LEAF_SHAG_FACTOR),
            new BranchingTrunkPlacer(20, 5, 5, 7, new BranchesConfig(3, 1, 10, 1, 0.3, 0.2), false),
            new TwoLayersFeatureSize(20, 0, canopyDistancing)
    )
            .decorators(ImmutableList.of(TreeDecorators.FIREFLY, TreeDecorators.LIVING_ROOTS))
            .ignoreVines()
            .build();

    public static final TreeConfiguration CANOPY_TREE_FIREFLY = new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(BlockConstants.CANOPY_LOG),
            new SimpleStateProvider(BlockConstants.CANOPY_LEAVES),
            new LeafSpheroidFoliagePlacer(4.5f, 1.5f, UniformInt.fixed(0), 1, 0, -0.25f, LEAF_SHAG_FACTOR),
            new BranchingTrunkPlacer(20, 5, 5, 7, new BranchesConfig(3, 1, 10, 1, 0.3, 0.2), false),
            new TwoLayersFeatureSize(20, 0, canopyDistancing)
    )
            .decorators(ImmutableList.of(
                    TreeDecorators.LIVING_ROOTS,
                    TreeDecorators.FIREFLY,
                    new TrunkSideDecorator( // A few more Fireflies!
                            4,
                            0.5f,
                            new SimpleStateProvider(TFBlocks.firefly.get().defaultBlockState().setValue(FireflyBlock.FACING, Direction.NORTH))
                    ),
                    new DangleFromTreeDecorator(
                            0,
                            1,
                            2,
                            5,
                            15,
                            new SimpleStateProvider(TFBlocks.canopy_fence.get().defaultBlockState()),
                            new SimpleStateProvider(TFBlocks.firefly_jar.get().defaultBlockState())
                    ),
                    new DangleFromTreeDecorator(
                            0,
                            1,
                            2,
                            5,
                            15,
                            new SimpleStateProvider(Blocks.CHAIN.defaultBlockState()),
                            new SimpleStateProvider(TFBlocks.firefly_jar.get().defaultBlockState())
                    )
            ))
            .ignoreVines()
            .build();

    public static final TreeConfiguration CANOPY_TREE_DEAD = new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(BlockConstants.CANOPY_LOG),
            new SimpleStateProvider(BlockConstants.AIR),
            // TODO Make No-op foilage placer - dead tree
            new LeafSpheroidFoliagePlacer(0, 0, UniformInt.fixed(0), 0, 0, 0, 0),
            new BranchingTrunkPlacer(20, 5, 5, 7, new BranchesConfig(3, 1, 10, 1, 0.3, 0.2), false),
            new TwoLayersFeatureSize(20, 0, canopyDistancing)
    )
            .decorators(ImmutableList.of(TreeDecorators.FIREFLY, TreeDecorators.LIVING_ROOTS))
            .ignoreVines()
            .build();

    public static final TreeConfiguration MANGROVE_TREE = new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(BlockConstants.MANGROVE_LOG),
            new SimpleStateProvider(BlockConstants.MANGROVE_LEAVES),
            new LeafSpheroidFoliagePlacer(2.5f, 1.5f, UniformInt.fixed(0), 2, 0, -0.25f, (int) (LEAF_SHAG_FACTOR * 0.666f)),
            new TrunkRiser(5, new BranchingTrunkPlacer(6, 4, 0, 1, new BranchesConfig(0, 3, 6, 2, 0.3, 0.25), false)),
            new TwoLayersFeatureSize(1, 0, 1)
    )
            .maxWaterDepth(6)
            .decorators(ImmutableList.of(
                    TreeDecorators.FIREFLY,
                    new TreeRootsDecorator(3, 1, 12, new SimpleStateProvider(BlockConstants.MANGROVE_WOOD), (new WeightedStateProvider())
                            .add(BlockConstants.ROOTS, 4)
                            .add(TFBlocks.liveroot_block.get().defaultBlockState(), 1)),
                    LeaveVineDecorator.INSTANCE
                    )
            )
            .build();

    private static final SimpleStateProvider DARKWOOD_LEAVES_PROVIDER = new SimpleStateProvider(BlockConstants.DARKWOOD_LEAVES);

    public static final TreeConfiguration DARKWOOD_TREE = new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(BlockConstants.DARKWOOD_LOG),
            DARKWOOD_LEAVES_PROVIDER,
            new LeafSpheroidFoliagePlacer(4.5f, 2.25f, UniformInt.fixed(0), 1, 0, 0.45f, (int) (LEAF_SHAG_FACTOR * 1.5f)),
            new BranchingTrunkPlacer(6, 2, 2, 4, new BranchesConfig(4, 0, 10, 4, 0.23, 0.23), false),
            new TwoLayersFeatureSize(1, 0, 1)
    )
            //.setDecorators(ImmutableList.of(TreeDecorators.LIVING_ROOTS)) //TODO roots grow EVERYWHERE in strongholds, lets disable for now
            .ignoreVines()
            .build();

    public static final TreeConfiguration DARKWOOD_LANTERN_TREE = new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(BlockConstants.DARKWOOD_LOG),
            DARKWOOD_LEAVES_PROVIDER,
            new LeafSpheroidFoliagePlacer(4.5f, 2.25f, UniformInt.fixed(0), 1, 0, 0.45f,  (int) (LEAF_SHAG_FACTOR * 1.5f)),
            new BranchingTrunkPlacer(6, 2, 2, 4, new BranchesConfig(4, 0, 10, 4, 0.23, 0.23), false),
            new TwoLayersFeatureSize(1, 0, 1)
    )
            .decorators(ImmutableList.of(
                    TreeDecorators.LIVING_ROOTS,
                    new DangleFromTreeDecorator(
                            0,
                            1,
                            2,
                            4,
                            2,
                            new SimpleStateProvider(Blocks.CHAIN.defaultBlockState()),
                            new SimpleStateProvider(Blocks.LANTERN.defaultBlockState().setValue(Lantern.HANGING, true))
                    )
            ))
            .ignoreVines()
            .build();

    //[VanillaCopy] of Features.MEGA_SPRUCE, just without all the nasty podzol so it fits better
    public static final TreeConfiguration BIG_SPRUCE = new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(BlockConstants.SPRUCE_LOG),
            new SimpleStateProvider(BlockConstants.SPRUCE_LEAVES),
            new MegaPineFoliagePlacer(UniformInt.fixed(0),
                    UniformInt.fixed(0),
                    UniformInt.of(13, 4)),
            new GiantTrunkPlacer(13, 2, 14),
            new TwoLayersFeatureSize(1, 1, 2))
        .build();

    // Requires Hollowtree gen
    public static final TFTreeFeatureConfig TIME_TREE = new TFTreeFeatureConfig.Builder(
            new SimpleStateProvider(BlockConstants.TIME_LOG),
            new SimpleStateProvider(BlockConstants.TIME_LEAVES),
            new SimpleStateProvider(BlockConstants.TIME_WOOD),
            new SimpleStateProvider(BlockConstants.ROOTS)
    )
            .setSapling(TFBlocks.time_sapling.get())
            .build();

    public static final TreeConfiguration TRANSFORM_TREE = new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(BlockConstants.TRANSFORM_LOG),
            new SimpleStateProvider(BlockConstants.TRANSFORM_LEAVES),
            new LeafSpheroidFoliagePlacer(4.5f, 1.5f, UniformInt.fixed(0), 1, 0, -0.25f, 0),
            new BranchingTrunkPlacer(6, 5, 5, 7, new BranchesConfig(3, 1, 10, 1, 0.3, 0.2), false),
            new TwoLayersFeatureSize(20, 0, canopyDistancing)
    )
            .ignoreVines()
            .decorators(ImmutableList.of(new TreeCorePlacer(3, new SimpleStateProvider(TFBlocks.transformation_log_core.get().defaultBlockState().setValue(TFLogBlock.AXIS, Direction.Axis.Y)))))
            .build();

    public static final TFTreeFeatureConfig MINING_TREE = new TFTreeFeatureConfig.Builder(
            new SimpleStateProvider(BlockConstants.MINING_LOG),
            new SimpleStateProvider(BlockConstants.MINING_LEAVES),
            new SimpleStateProvider(BlockConstants.MINING_WOOD),
            new SimpleStateProvider(BlockConstants.ROOTS)
    )
            .setSapling(TFBlocks.mining_sapling.get())
            .build();

    public static final TreeConfiguration SORT_TREE = new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(BlockConstants.SORT_LOG),
            new SimpleStateProvider(BlockConstants.SORT_LEAVES),
            new LeafSpheroidFoliagePlacer(1.5f, 2.25f, UniformInt.fixed(0), 1, 0, 0.5f, 0),
            new StraightTrunkPlacer(3, 0, 0),
            new TwoLayersFeatureSize(1, 0, 1)
    )
            .ignoreVines()
            .decorators(ImmutableList.of(new TreeCorePlacer(1, new SimpleStateProvider(TFBlocks.sorting_log_core.get().defaultBlockState().setValue(TFLogBlock.AXIS, Direction.Axis.Y)))))
            .build();

    public static final TFTreeFeatureConfig DENSE_OAK = new TFTreeFeatureConfig.Builder(
            new SimpleStateProvider(BlockConstants.TOAK_LOG),
            new SimpleStateProvider(BlockConstants.TOAK_LEAVES),
            new SimpleStateProvider(BlockConstants.TOAK_WOOD),
            new SimpleStateProvider(BlockConstants.ROOTS)
    )
            .setSapling(TFBlocks.oak_sapling.get())
            .build();

    public static final TFTreeFeatureConfig HOLLOW_TREE = new TFTreeFeatureConfig.Builder(
            new SimpleStateProvider(BlockConstants.TOAK_LOG),
            new SimpleStateProvider(BlockConstants.TOAK_LEAVES),
            new SimpleStateProvider(BlockConstants.TOAK_WOOD),
            new SimpleStateProvider(BlockConstants.ROOTS)
    )
            .setSapling(TFBlocks.hollow_oak_sapling.get())
            .build();

    public static final TreeConfiguration RAINBOAK_TREE = new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(BlockConstants.TOAK_LOG),
            new SimpleStateProvider(BlockConstants.RAINBOW_LEAVES),
            new BlobFoliagePlacer(UniformInt.fixed(2), UniformInt.fixed(0), 3),
            new StraightTrunkPlacer(4, 2, 0),
            new TwoLayersFeatureSize(1, 0, 1)
    )
            .decorators(ImmutableList.of(TreeDecorators.LIVING_ROOTS))
            .build();

    public static final TreeConfiguration LARGE_RAINBOAK_TREE =  new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(BlockConstants.TOAK_LOG),
            new SimpleStateProvider(BlockConstants.RAINBOW_LEAVES),
            new FancyFoliagePlacer(UniformInt.fixed(2), UniformInt.fixed(4), 4),
            new FancyTrunkPlacer(3, 11, 0),
            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
    )
            .decorators(ImmutableList.of(TreeDecorators.LIVING_ROOTS))
            .build();

    // TODO Smarter "foilage placer" for mushrooms that actually set the mushroom block states properly
    public static final TreeConfiguration MUSHROOM_BROWN = new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(BlockConstants.MUSHROOM_STEM),
            new SimpleStateProvider(BlockConstants.MUSHROOM_CAP_BROWN),
            new LeafSpheroidFoliagePlacer(4.25f, 0f, UniformInt.fixed(1), 1, 0, 0f, 0),
            new BranchingTrunkPlacer(12, 5, 5, 6, new BranchesConfig(3, 1, 9, 1, 0.3, 0.2), true),
            new TwoLayersFeatureSize(11, 0, canopyDistancing)
    )
            .decorators(ImmutableList.of(TreeDecorators.FIREFLY))
            .ignoreVines()
            .build();

    public static final TreeConfiguration MUSHROOM_RED = new TreeConfiguration.TreeConfigurationBuilder(
            new SimpleStateProvider(BlockConstants.MUSHROOM_STEM),
            new SimpleStateProvider(BlockConstants.MUSHROOM_CAP_RED),
            new LeafSpheroidFoliagePlacer(4.25f, 1.75f, UniformInt.fixed(1), 0, 0, -0.45f, 0),
            new BranchingTrunkPlacer(12, 5, 5, 6, new BranchesConfig(3, 1, 9, 1, 0.3, 0.2), true),
            new TwoLayersFeatureSize(11, 0, canopyDistancing)
    )
            .decorators(ImmutableList.of(TreeDecorators.FIREFLY))
            .ignoreVines()
            .build();

}
