package twilightforest.worldgen.treeplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import twilightforest.util.FeatureUtil;
import twilightforest.world.feature.TFTreeGenerator;
import twilightforest.worldgen.TwilightFeatures;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class TreeRootsDecorator extends TreeDecorator {
    public static final Codec<TreeRootsDecorator> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.intRange(0, 16).fieldOf("base_strand_count").forGetter(o -> o.strands),
                    Codec.intRange(0, 16).fieldOf("additional_random_strands").forGetter(o -> o.addExtraStrands),
                    Codec.intRange(0, 32).fieldOf("root_length").forGetter(o -> o.length),
                    BlockStateProvider.CODEC.optionalFieldOf("air_roots_provider").forGetter(o -> Optional.ofNullable(o.surfaceBlock)),
                    BlockStateProvider.CODEC.fieldOf("ground_roots_provider").forGetter(o -> o.rootBlock)
            ).apply(instance, TreeRootsDecorator::new)
    );

    private final int strands;
    private final int addExtraStrands;
    private final int length;
    private final BlockStateProvider surfaceBlock;
    private final BlockStateProvider rootBlock;

    private final boolean hasSurfaceRoots;

    private TreeRootsDecorator(int count, int addExtraStrands, int length, Optional<BlockStateProvider> surfaceBlock, BlockStateProvider rootBlock) {
        this.strands = count;
        this.addExtraStrands = addExtraStrands;
        this.length = length;
        this.rootBlock = rootBlock;

        if (hasSurfaceRoots = surfaceBlock.isPresent()) {
            this.surfaceBlock = surfaceBlock.get();
        } else {
            this.surfaceBlock = null;
        }
    }

    public TreeRootsDecorator(int count, int addExtraStrands, int length, BlockStateProvider rootBlock) {
        this.strands = count;
        this.addExtraStrands = addExtraStrands;
        this.length = length;
        this.rootBlock = rootBlock;
        this.hasSurfaceRoots = false;
        this.surfaceBlock = null;
    }

    public TreeRootsDecorator(int count, int addExtraStrands, int length, BlockStateProvider surfaceBlock, BlockStateProvider rootBlock) {
        this.strands = count;
        this.addExtraStrands = addExtraStrands;
        this.length = length;
        this.rootBlock = rootBlock;
        this.hasSurfaceRoots = true;
        this.surfaceBlock = surfaceBlock;
    }

    @Override
    protected TreeDecoratorType<TreeRootsDecorator> type() {
        return TwilightFeatures.TREE_ROOTS;
    }

    @Override
    public void place(WorldGenLevel world, Random random, List<BlockPos> trunkBlocks, List<BlockPos> leafBlocks, Set<BlockPos> decorations, BoundingBox mutableBoundingBox) {
        if (trunkBlocks.isEmpty())
            return;

        int numBranches = strands + random.nextInt(addExtraStrands + 1);
        float offset = random.nextFloat();
        BlockPos startPos = trunkBlocks.get(0);

        if (hasSurfaceRoots) {
            for (int i = 0; i < numBranches; i++) {
                buildRootWithAir(world, random, startPos, decorations, offset, i, length, mutableBoundingBox, surfaceBlock, rootBlock);
            }
        } else {
            for (int i = 0; i < numBranches; i++) {
                buildRoot(world, random, startPos, decorations, offset, i, length, mutableBoundingBox, rootBlock);
            }
        }
    }

    protected void buildRootWithAir(WorldGenLevel world, Random random, BlockPos pos, Set<BlockPos> decorations, double offset, int iteration, int length, BoundingBox mutableBoundingBox, BlockStateProvider airRoot, BlockStateProvider dirtRoot) {
        BlockPos dest = FeatureUtil.translate(pos.below(iteration + 2), length, 0.3 * iteration + offset, 0.8);

        // go through block by block and stop drawing when we head too far into open air
        BlockPos[] lineArray = FeatureUtil.getBresenhamArrays(pos.below(), dest);
        boolean stillAboveGround = true;
        for (BlockPos coord : lineArray) {
            if (stillAboveGround && FeatureUtil.hasAirAround(world, coord)) {
                setBlock(world, coord, airRoot.getState(random, coord), decorations, mutableBoundingBox);
            } else {
                stillAboveGround = false;
                if (TFTreeGenerator.canRootGrowIn(world, coord)) {
                    setBlock(world, coord, dirtRoot.getState(random, coord), decorations, mutableBoundingBox);
                }
            }
        }
    }

    // Shortcircuited version of above function
    protected void buildRoot(WorldGenLevel world, Random random, BlockPos pos, Set<BlockPos> decorations, double offset, int iteration, int length, BoundingBox mutableBoundingBox, BlockStateProvider dirtRoot) {
        BlockPos dest = FeatureUtil.translate(pos.below(iteration + 2), length, 0.3 * iteration + offset, 0.8);

        // go through block by block and stop drawing when we head too far into open air
        BlockPos[] lineArray = FeatureUtil.getBresenhamArrays(pos.below(), dest);
        for (BlockPos coord : lineArray) {
            if (TFTreeGenerator.canRootGrowIn(world, coord)) {
                setBlock(world, coord, dirtRoot.getState(random, coord), decorations, mutableBoundingBox);
            }
        }
    }
}
