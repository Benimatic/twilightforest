package twilightforest.world.newfeature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class HollowTrunkPlacer extends AbstractTrunkPlacer {
    public static final Codec<HollowTrunkPlacer> CODEC = RecordCodecBuilder.create(instance ->
            func_236915_a_(instance).and(instance.group(
                    Codec.floatRange(1, 16).fieldOf("outside_radius").forGetter(o -> o.outerRadius),
                    //Codec.floatRange(0, 14).fieldOf("hollow_radius").forGetter(o -> o.hollowRadius),
                    Codec.intRange(0, 8).fieldOf("random_add_radius").forGetter(o -> o.randomAddRadius),
                    BranchesConfiguration.CODEC.fieldOf("branch_config").forGetter(o -> o.branchesConfiguration),
                    BlockStateProvider.field_236796_a_.fieldOf("eastside_ladder").forGetter(o -> o.ladder)
            )).apply(instance, HollowTrunkPlacer::new)
    );

    private final float outerRadius;
    //private final float hollowRadius;
    private final int randomAddRadius;
    private final BranchesConfiguration branchesConfiguration;
    private final BlockStateProvider ladder;

    public HollowTrunkPlacer(int baseHeight, int randomHeightA, int randomHeightB, float outerRadius, /*float hollowRadius,*/ int randomAddRadius, BranchesConfiguration branchesConfiguration, BlockStateProvider ladder) {
        super(baseHeight, randomHeightA, randomHeightB);
        this.outerRadius = outerRadius;
        //this.hollowRadius = hollowRadius;
        this.randomAddRadius = randomAddRadius;
        this.branchesConfiguration = branchesConfiguration;
        this.ladder = ladder;
    }

    @Override
    protected TrunkPlacerType<HollowTrunkPlacer> func_230381_a_() {
        return TwilightFeatures.HOLLOW_TRUNK;
    }

    @Override
    public List<FoliagePlacer.Foliage> func_230382_a_(IWorldGenerationReader world, Random random, int height, BlockPos startPos, Set<BlockPos> trunkBlocks, MutableBoundingBox mutableBoundingBox, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        final float additionalRadius = random.nextInt(randomAddRadius + 1);
        final float outerRadius = this.outerRadius + additionalRadius;
        final float hollowRadius = outerRadius / 2f;

        final float outerRadiusSquared = outerRadius * outerRadius;
        final float hollowRadiusSquared = hollowRadius * hollowRadius;

        int dist;
        boolean trunkDelta;
        boolean notHollow = false;

        // build the trunk upwards
        for (int z = 0; z <= outerRadius; z++) {
            for (int x = (int) outerRadius; x >= 0; x--) { // We go in reverse just to simplify logic for vines
                dist = x * x + z * z;

                if (dist < outerRadiusSquared) { // Can confirm inside the circle
                    trunkDelta = notHollow;
                    notHollow = dist >= hollowRadiusSquared;

                    for (int y = 0; y <= height; y++) {
                        BlockPos dPos = startPos.add(x, y, z);

                        if (trunkDelta && !notHollow) {
                            world.setBlockState(dPos, ladder.getBlockState(random, dPos), 3);

                            BlockPos opposite = startPos.add(x, y, -z);

                            if (!dPos.equals(opposite)) {
                                world.setBlockState(opposite, ladder.getBlockState(random, opposite), 3);
                            }
                        }

                        if (notHollow) {
                            func_236911_a_(world, random, dPos, trunkBlocks, mutableBoundingBox, baseTreeFeatureConfig);
                            func_236911_a_(world, random, startPos.add( -x, y, -z), trunkBlocks, mutableBoundingBox, baseTreeFeatureConfig);
                            func_236911_a_(world, random, startPos.add( -z, y,  x), trunkBlocks, mutableBoundingBox, baseTreeFeatureConfig);
                            func_236911_a_(world, random, startPos.add(  z, y, -x), trunkBlocks, mutableBoundingBox, baseTreeFeatureConfig);
                        }

                        /*BlockPos dPos = startPos.add(x, y, z);
                        // determine how far we are from the center.
                        int ax = Math.abs(x);
                        int az = Math.abs(z);
                        int dist = (int) (Math.max(ax, az) + (Math.min(ax, az) * 0.5));

                        // make a trunk!
                        if (dist <= outerRadius && dist > hollowRadius) {
                            func_236911_a_(world, random, dPos, trunkBlocks, mutableBoundingBox, baseTreeFeatureConfig);
                        }

                        // fill it with lava!
                        if (dist <= hollowRadius) {
                            // just kidding!
                            //world.setBlock(xDiameter + x, yDiameter + y, zDiameter + z, Blocks.LAVA);
                        }

                        // how about a ladder?  is that okay?
                        if (dist == hollowRadius && x == hollowRadius) {
						//putBlockAndMetadata(xDiameter + x, yDiameter + y, zDiameter + z, Blocks.LADDER,  4, true);
                            world.setBlockState(dPos, Blocks.VINE.getDefaultState().with(VineBlock.EAST, true),3);
                        }*/
                    }
                }
            }
        }

        return ImmutableList.of();
    }
}
