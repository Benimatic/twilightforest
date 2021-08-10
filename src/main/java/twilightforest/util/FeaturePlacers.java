package twilightforest.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import twilightforest.world.feature.TFTreeGenerator;
import twilightforest.world.feature.config.TFTreeFeatureConfig;

import java.util.Random;
import java.util.function.BiConsumer;

public class FeaturePlacers {
    /**
     * Draws a line from {x1, y1, z1} to {x2, y2, z2}
     * This takes all variables for setting Branch
     */
    public static void drawBresenhamBranch(TFTreeGenerator<? extends TFTreeFeatureConfig> generator, LevelAccessor world, BiConsumer<BlockPos, BlockState> trunkPlacer, Random random, BlockPos from, BlockPos to, TFTreeFeatureConfig config) {
        for (BlockPos pixel : FeatureLogic.getBresenhamArrays(from, to)) {
            generator.setBranchBlockState(world, trunkPlacer, random, pixel, config);
        }
    }

    /**
     * Draws a line from {x1, y1, z1} to {x2, y2, z2}
     * This just takes a BlockState, used to set Trunk
     */
    public static void drawBresenhamTree(BiConsumer<BlockPos, BlockState> trunkPlacer, BlockPos from, BlockPos to, BlockState state) {
        for (BlockPos pixel : FeatureLogic.getBresenhamArrays(from, to)) {
            trunkPlacer.accept(pixel, state);
        }
    }

    public static void putLeafBlock(BiConsumer<BlockPos, BlockState> worldPlacer, BlockPos pos, BlockStateProvider state, Random random) {
        worldPlacer.accept(pos, state.getState(random, pos));
    }

    // TODO phase out other leaf circle algorithms
    public static void makeLeafCircle(BiConsumer<BlockPos, BlockState> worldPlacer, Random random, BlockPos centerPos, float radius, BlockStateProvider state) {
        // Normally, I'd use mutable pos here but there are multiple bits of logic down the line that force
        // the pos to be immutable causing multiple same BlockPos instances to exist.
        float radiusSquared = radius * radius;
        FeaturePlacers.putLeafBlock(worldPlacer, centerPos, state, random);

        // trace out a quadrant
        for (int x = 0; x <= radius; x++) {
            for (int z = 1; z <= radius; z++) {
                // if we're inside the blob, fill it
                if (x * x + z * z <= radiusSquared) {
                    // do four at a time for easiness!
                    FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset(  x, 0,  z), state, random);
                    FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( -x, 0, -z), state, random);
                    FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( -z, 0,  x), state, random);
                    FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset(  z, 0, -x), state, random);
                    // Confused how this circle pixel-filling algorithm works exactly? https://www.desmos.com/calculator/psqynhk21k
                }
            }
        }
    }

    // Same as above except for even-sized trunks
    public static void makeLeafCircle2(BiConsumer<BlockPos, BlockState> worldPlacer, Random random, BlockPos centerPos, float radius, BlockStateProvider state) {
        // Normally, I'd use mutable pos here but there are multiple bits of logic down the line that force
        // the pos to be immutable causing multiple same BlockPos instances to exist.
        float radiusSquared = radius * radius;
        FeaturePlacers.putLeafBlock(worldPlacer, centerPos, state, random);

        // trace out a quadrant
        for (int x = 0; x <= radius; x++) {
            for (int z = 0; z <= radius; z++) {
                // if we're inside the blob, fill it
                if (x * x + z * z <= radiusSquared) {
                    // do four at a time for easiness!
                    FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( 1+x, 0, 1+z), state, random);
                    FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( -x, 0, -z), state, random);
                    FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( -x, 0, 1+z), state, random);
                    FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( 1+x, 0, -z), state, random);
                    // Confused how this circle pixel-filling algorithm works exactly? https://www.desmos.com/calculator/psqynhk21k
                }
            }
        }
    }

    public static void makeLeafSpheroid(BiConsumer<BlockPos, BlockState> worldPlacer, Random random, BlockPos centerPos, float xzRadius, float yRadius, float verticalBias, BlockStateProvider state) {
        float xzRadiusSquared = xzRadius * xzRadius;
        float yRadiusSquared = yRadius * yRadius;
        float superRadiusSquared = xzRadiusSquared * yRadiusSquared;
        FeaturePlacers.putLeafBlock(worldPlacer, centerPos, state, random);

        for (int y = 0; y <= yRadius; y++) {
            if (y > yRadius) continue;

            FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( 0,  y, 0), state, random);
            FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( 0,  y, 0), state, random);
            FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( 0,  y, 0), state, random);
            FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( 0,  y, 0), state, random);

            FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( 0, -y, 0), state, random);
            FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( 0, -y, 0), state, random);
            FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( 0, -y, 0), state, random);
            FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( 0, -y, 0), state, random);
        }

        for (int x = 0; x <= xzRadius; x++) {
            for (int z = 1; z <= xzRadius; z++) {
                if (x * x + z * z > xzRadiusSquared) continue;

                FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset(  x, 0,  z), state, random);
                FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( -x, 0, -z), state, random);
                FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( -z, 0,  x), state, random);
                FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset(  z, 0, -x), state, random);

                for (int y = 1; y <= yRadius; y++) {
                    float xzSquare = ((x * x + z * z) * yRadiusSquared);

                    if (xzSquare + (((y - verticalBias) * (y - verticalBias)) * xzRadiusSquared) <= superRadiusSquared) {
                        FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset(  x,  y,  z), state, random);
                        FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( -x,  y, -z), state, random);
                        FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( -z,  y,  x), state, random);
                        FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset(  z,  y, -x), state, random);
                    }

                    if (xzSquare + (((y + verticalBias) * (y + verticalBias)) * xzRadiusSquared) <= superRadiusSquared) {
                        FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset(  x, -y,  z), state, random);
                        FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( -x, -y, -z), state, random);
                        FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( -z, -y,  x), state, random);
                        FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset(  z, -y, -x), state, random);
                    }
                }
            }
        }
    }

    public static void makeLeafSpheroid(BiConsumer<BlockPos, BlockState> worldPlacer, Random random, BlockPos centerPos, float xzRadius, float yRadius, BlockStateProvider state) {
        float xzRadiusSquared = xzRadius * xzRadius;
        float yRadiusSquared = yRadius * yRadius;
        float superRadiusSquared = xzRadiusSquared * yRadiusSquared;
        FeaturePlacers.putLeafBlock(worldPlacer, centerPos, state, random);

        for (int y = 0; y <= yRadius; y++) {
            if (y > yRadius) continue;

            FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( 0,  y, 0), state, random);
            FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( 0,  y, 0), state, random);
            FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( 0,  y, 0), state, random);
            FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( 0,  y, 0), state, random);

            FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( 0, -y, 0), state, random);
            FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( 0, -y, 0), state, random);
            FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( 0, -y, 0), state, random);
            FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( 0, -y, 0), state, random);
        }

        for (int x = 0; x <= xzRadius; x++) {
            for (int z = 1; z <= xzRadius; z++) {
                if (x * x + z * z > xzRadiusSquared) continue;

                FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset(  x, 0,  z), state, random);
                FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( -x, 0, -z), state, random);
                FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( -z, 0,  x), state, random);
                FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset(  z, 0, -x), state, random);

                for (int y = 1; y <= yRadius; y++) {
                    float xzSquare = ((x * x + z * z) * yRadiusSquared);

                    if (xzSquare + (y * y) * xzRadiusSquared <= superRadiusSquared) {
                        FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset(  x,  y,  z), state, random);
                        FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( -x,  y, -z), state, random);
                        FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( -z,  y,  x), state, random);
                        FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset(  z,  y, -x), state, random);

                        FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset(  x, -y,  z), state, random);
                        FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( -x, -y, -z), state, random);
                        FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset( -z, -y,  x), state, random);
                        FeaturePlacers.putLeafBlock(worldPlacer, centerPos.offset(  z, -y, -x), state, random);
                    }
                }
            }
        }
    }
}
