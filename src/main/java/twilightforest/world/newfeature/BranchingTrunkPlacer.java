package twilightforest.world.newfeature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import twilightforest.util.FeatureUtil;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class BranchingTrunkPlacer extends AbstractTrunkPlacer {
    public static final Codec<BranchingTrunkPlacer> CODEC = RecordCodecBuilder.create(instance ->
            func_236915_a_(instance).and(instance.group(
                    Codec.intRange(0, 16).fieldOf("base_branch_count").forGetter(o -> o.branches),
                    Codec.intRange(0, 16).fieldOf("additional_random_branches").forGetter(o -> o.addExtraBranches),
                    Codec.intRange(0, 24).fieldOf("branch_start_offset_down").forGetter(o -> o.branchStartDownwardsOffset),
                    Codec.intRange(0, 16).fieldOf("branch_length").forGetter(o -> o.branchLength)
            )).apply(instance, BranchingTrunkPlacer::new)
    );

    private final int branches;
    private final int addExtraBranches;
    private final int branchStartDownwardsOffset;
    private final int branchLength;

    public BranchingTrunkPlacer(int baseHeight, int randomHeightA, int randomHeightB, int branches, int addExtraBranches, int branchDownwardsOffset, int branchLength) {
        super(baseHeight, randomHeightA, randomHeightB);
        this.branches = branches;
        this.addExtraBranches = addExtraBranches;
        this.branchStartDownwardsOffset = branchDownwardsOffset;
        this.branchLength = branchLength;
    }

    @Override
    protected TrunkPlacerType<BranchingTrunkPlacer> func_230381_a_() {
        return TwilightFeatures.TRUNK_BRANCHING;
    }

    @Override
    public List<FoliagePlacer.Foliage> func_230382_a_(IWorldGenerationReader world, Random random, int height, BlockPos startPos, Set<BlockPos> trunkBlocks, MutableBoundingBox mutableBoundingBox, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        List<FoliagePlacer.Foliage> leafBlocks = Lists.newArrayList();

        for (int y = 0; y < height; y++) { // Keep building upwards until we cannot, and then adjust height if we run into something
            if (!func_236911_a_(world, random, startPos.up(y), trunkBlocks, mutableBoundingBox, baseTreeFeatureConfig)) {
                height = y;
                break;
            }
        }

        leafBlocks.add(new FoliagePlacer.Foliage(startPos.up(height), 1, false));

        int numBranches = branches + random.nextInt(addExtraBranches + 1);
        float offset = random.nextFloat();
        for (int b = 0; b < numBranches; b++) {
            buildBranch(world, startPos, trunkBlocks, leafBlocks, height - branchStartDownwardsOffset + b, branchLength, 0.3 * b + offset, 0.2, random, mutableBoundingBox, baseTreeFeatureConfig);
        }

        return leafBlocks;
    }


    private static void buildBranch(IWorldGenerationReader world, BlockPos pos, Set<BlockPos> trunkBlocks, List<FoliagePlacer.Foliage> leafBlocks, int height, double length, double angle, double tilt, Random treeRNG, MutableBoundingBox mbb, BaseTreeFeatureConfig config) {
        BlockPos src = pos.up(height);
        BlockPos dest = FeatureUtil.translate(src, length, angle, tilt);

        drawBresenhamBranch(world, treeRNG, src, dest, trunkBlocks, mbb, config);

        func_236911_a_(world, treeRNG, dest.east(), trunkBlocks, mbb, config);
        func_236911_a_(world, treeRNG, dest.west(), trunkBlocks, mbb, config);
        func_236911_a_(world, treeRNG, dest.south(), trunkBlocks, mbb, config);
        func_236911_a_(world, treeRNG, dest.north(), trunkBlocks, mbb, config);

        leafBlocks.add(new FoliagePlacer.Foliage(dest, 0, false));
    }

    private static void drawBresenhamBranch(IWorldGenerationReader world, Random random, BlockPos from, BlockPos to, Set<BlockPos> state, MutableBoundingBox mbb, BaseTreeFeatureConfig config) {
        for (BlockPos pixel : FeatureUtil.getBresenhamArrays(from, to)) {
            func_236911_a_(world, random, pixel, state, mbb, config);
        }
    }
}
