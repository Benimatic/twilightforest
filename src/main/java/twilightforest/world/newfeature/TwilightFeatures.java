package twilightforest.world.newfeature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import twilightforest.TwilightForestMod;
import twilightforest.block.BlockTFFirefly;
import twilightforest.block.TFBlocks;

public final class TwilightFeatures {
    public static final FoliagePlacerType<LeafSpheroidFoliagePlacer> FOLIAGE_SPHEROID = registerFoliage(TwilightForestMod.prefix("spheroid_foliage_placer"), LeafSpheroidFoliagePlacer.CODEC);
    public static final TrunkPlacerType<BranchingTrunkPlacer> TRUNK_BRANCHING = registerTrunk(TwilightForestMod.prefix("branching_trunk_placer"), BranchingTrunkPlacer.CODEC);
    public static final TrunkPlacerType<TrunkRiser> TRUNK_RISER = registerTrunk(TwilightForestMod.prefix("trunk_mover_upper"), TrunkRiser.CODEC);
    public static final TreeDecoratorType<TrunkSideDecorator> TRUNKSIDE_DECORATOR = registerTreeFeature(TwilightForestMod.prefix("trunkside_decorator"), TrunkSideDecorator.CODEC);
    public static final TreeDecoratorType<TreeRootsDecorator> TREE_ROOTS = registerTreeFeature(TwilightForestMod.prefix("tree_roots"), TreeRootsDecorator.CODEC);

    public static final class Decorators {
        public static final TreeRootsDecorator LIVING_ROOTS = new TreeRootsDecorator(3, 1, 5, (new WeightedBlockStateProvider())
                .addWeightedBlockstate(TFBlocks.root.get().getDefaultState(), 4)
                .addWeightedBlockstate(TFBlocks.liveroot_block.get().getDefaultState(), 1));

        public static final TrunkSideDecorator FIREFLY = new TrunkSideDecorator(1, 1.0f, new SimpleBlockStateProvider(TFBlocks.firefly.get().getDefaultState().with(BlockTFFirefly.FACING, Direction.NORTH)));
    }

    public static final class TreeConfigurations {
        public static final BaseTreeFeatureConfig TWILIGHT_OAK = new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(TFBlocks.oak_log.get().getDefaultState()),
                new SimpleBlockStateProvider(TFBlocks.oak_leaves.get().getDefaultState()),
                new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
                new StraightTrunkPlacer(4, 2, 0),
                new TwoLayerFeature(1, 0, 1)
        )
                .func_236703_a_(ImmutableList.of(Decorators.LIVING_ROOTS))
                .setIgnoreVines()
                .build();

        public static final BaseTreeFeatureConfig CANOPY_TREE = new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(TFBlocks.canopy_log.get().getDefaultState()),
                new SimpleBlockStateProvider(TFBlocks.canopy_leaves.get().getDefaultState()),
                new LeafSpheroidFoliagePlacer(4.5f, FeatureSpread.func_242252_a(0), 1.5f, 1, 0),
                new BranchingTrunkPlacer(20, 5, 5, 3, 1, 10),
                new TwoLayerFeature(1, 0, 1)
        )
                .func_236703_a_(ImmutableList.of(Decorators.FIREFLY, Decorators.LIVING_ROOTS))
                .setIgnoreVines()
                .build();

        public static final BaseTreeFeatureConfig MANGROVE_TREE = new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(TFBlocks.mangrove_log.get().getDefaultState()),
                new SimpleBlockStateProvider(TFBlocks.mangrove_leaves.get().getDefaultState()),
                new LeafSpheroidFoliagePlacer(2.5f, FeatureSpread.func_242252_a(0), 1.5f, 2, 0),
                new TrunkRiser(5, new BranchingTrunkPlacer(6, 4, 0, 1, 2, 6)),
                new TwoLayerFeature(1, 0, 1)
        )
                .func_236703_a_(ImmutableList.of(
                        Decorators.FIREFLY,
                        new TreeRootsDecorator(3, 1, 12, new SimpleBlockStateProvider(TFBlocks.mangrove_wood.get().getDefaultState()), (new WeightedBlockStateProvider())
                                .addWeightedBlockstate(TFBlocks.root.get().getDefaultState(), 4)
                                .addWeightedBlockstate(TFBlocks.liveroot_block.get().getDefaultState(), 1))
                        )
                )
                .setIgnoreVines()
                .build();

        public static final BaseTreeFeatureConfig RAINBOAK_TREE = new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(TFBlocks.oak_log.get().getDefaultState()),
                new SimpleBlockStateProvider(TFBlocks.rainboak_leaves.get().getDefaultState()),
                new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
                new StraightTrunkPlacer(4, 2, 0),
                new TwoLayerFeature(1, 0, 1)
        )
                .func_236703_a_(ImmutableList.of(Decorators.LIVING_ROOTS))
                .setIgnoreVines()
                .build();
    }

    public static final class ConfiguredFeatures {
        public static final ConfiguredFeature<BaseTreeFeatureConfig, ? extends Feature<?>> RAINBOAK_TREE = registerFeature(TwilightForestMod.prefix("rainbow_oak"), Feature.field_236291_c_.withConfiguration(TreeConfigurations.RAINBOAK_TREE));
        public static final ConfiguredFeature<BaseTreeFeatureConfig, ? extends Feature<?>> MANGROVE_TREE = registerFeature(TwilightForestMod.prefix("mangrove_tree"), Feature.field_236291_c_.withConfiguration(TreeConfigurations.MANGROVE_TREE));
        public static final ConfiguredFeature<BaseTreeFeatureConfig, ? extends Feature<?>> CANOPY_TREE = registerFeature(TwilightForestMod.prefix("canopy_tree"), Feature.field_236291_c_.withConfiguration(TreeConfigurations.CANOPY_TREE));
        public static final ConfiguredFeature<BaseTreeFeatureConfig, ? extends Feature<?>> TWILIGHT_OAK = registerFeature(TwilightForestMod.prefix("twilight_oak"), Feature.field_236291_c_.withConfiguration(TreeConfigurations.TWILIGHT_OAK));

        public static final ConfiguredFeature<?, ?> DEFAULT_TWILIGHT_TREES = registerFeature(TwilightForestMod.prefix("twilight_trees"),
                Feature.RANDOM_SELECTOR
                        .withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(TWILIGHT_OAK.withChance(0.3f), CANOPY_TREE.withChance(0.3f), MANGROVE_TREE.withChance(0.3f), RAINBOAK_TREE.withChance(0.05f)), TWILIGHT_OAK))
                        .withPlacement(Features.Placements.field_244001_l)
                        //.withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(0, 0.1f, 1)))
        );
    }

    private static <P extends FoliagePlacer> FoliagePlacerType<P> registerFoliage(ResourceLocation name, Codec<P> codec) {
        return Registry.register(Registry.FOLIAGE_PLACER_TYPE, name, new FoliagePlacerType<>(codec));
    }

    private static <P extends AbstractTrunkPlacer> TrunkPlacerType<P> registerTrunk(ResourceLocation name, Codec<P> codec) {
        // TRUNK_REPLACER is wrong, it only places, not replacing
        return Registry.register(Registry.TRUNK_REPLACER, name, new TrunkPlacerType<>(codec));
    }

    private static <P extends TreeDecorator> TreeDecoratorType<P> registerTreeFeature(ResourceLocation name, Codec<P> codec) {
        // TRUNK_REPLACER is wrong, it only places, not replacing
        return Registry.register(Registry.TREE_DECORATOR_TYPE, name, new TreeDecoratorType<>(codec));
    }

    protected static <FC extends IFeatureConfig, F extends Feature<FC>> ConfiguredFeature<FC, F> registerFeature(ResourceLocation rl, ConfiguredFeature<FC, F> feature) {
        return Registry.register(WorldGenRegistries.field_243653_e, rl, feature);
    }
}
