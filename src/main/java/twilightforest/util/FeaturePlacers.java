package twilightforest.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.Material;
import twilightforest.block.TFBlocks;

import java.util.Random;
import java.util.function.BiConsumer;

/**
 * Feature Utility methods that invoke placement. For non-placement see FeatureLogic
 */
public final class FeaturePlacers {
    /**
     * Draws a line from {x1, y1, z1} to {x2, y2, z2}
     * This takes all variables for setting Branch
     */
    public static void drawBresenhamBranch(LevelAccessor world, BiConsumer<BlockPos, BlockState> trunkPlacer, Random random, BlockPos from, BlockPos to, BlockStateProvider config) {
        for (BlockPos pixel : FeatureLogic.getBresenhamArrays(from, to)) {
            placeIfValidTreePos(world, trunkPlacer, random, pixel, config);
        }
    }

    /**
     * Draws a line from {x1, y1, z1} to {x2, y2, z2}
     * This just takes a BlockState, used to set Trunk
     */
    public static void drawBresenhamTree(BiConsumer<BlockPos, BlockState> placer, BlockPos from, BlockPos to, BlockStateProvider config, Random random) {
        for (BlockPos pixel : FeatureLogic.getBresenhamArrays(from, to)) {
            placeProvidedBlock(placer, pixel, config, random);
        }
    }

    public static void placeProvidedBlock(BiConsumer<BlockPos, BlockState> worldPlacer, BlockPos pos, BlockStateProvider config, Random random) {
        worldPlacer.accept(pos, config.getState(random, pos));
    }

    // Use for trunks with Odd-count widths
    public static void placeCircleOdd(BiConsumer<BlockPos, BlockState> worldPlacer, Random random, BlockPos centerPos, float radius, BlockStateProvider config) {
        // Normally, I'd use mutable pos here but there are multiple bits of logic down the line that force
        // the pos to be immutable causing multiple same BlockPos instances to exist.
        float radiusSquared = radius * radius;
        FeaturePlacers.placeProvidedBlock(worldPlacer, centerPos, config, random);

        // trace out a quadrant
        for (int x = 0; x <= radius; x++) {
            for (int z = 1; z <= radius; z++) {
                // if we're inside the blob, fill it
                if (x * x + z * z <= radiusSquared) {
                    // do four at a time for easiness!
                    FeaturePlacers.placeProvidedBlock(worldPlacer, centerPos.offset(  x, 0,  z), config, random);
                    FeaturePlacers.placeProvidedBlock(worldPlacer, centerPos.offset( -x, 0, -z), config, random);
                    FeaturePlacers.placeProvidedBlock(worldPlacer, centerPos.offset( -z, 0,  x), config, random);
                    FeaturePlacers.placeProvidedBlock(worldPlacer, centerPos.offset(  z, 0, -x), config, random);
                    // Confused how this circle pixel-filling algorithm works exactly? https://www.desmos.com/calculator/psqynhk21k
                }
            }
        }
    }

    // Use for trunks with Even-count widths
    // TODO Verify that it works correctly, haven't gotten to a compiling state yet to test
    public static void placeCircleEven(BiConsumer<BlockPos, BlockState> worldPlacer, Random random, BlockPos centerPos, float radius, BlockStateProvider state) {
        // Normally, I'd use mutable pos here but there are multiple bits of logic down the line that force
        // the pos to be immutable causing multiple same BlockPos instances to exist.
        float radiusSquared = radius * radius;
        FeaturePlacers.placeProvidedBlock(worldPlacer, centerPos, state, random);

        // trace out a quadrant
        for (int x = 0; x <= radius; x++) {
            for (int z = 0; z <= radius; z++) {
                // if we're inside the blob, fill it
                if (x * x + z * z <= radiusSquared) {
                    // do four at a time for easiness!
                    FeaturePlacers.placeProvidedBlock(worldPlacer, centerPos.offset( 1+x, 0, 1+z), state, random);
                    FeaturePlacers.placeProvidedBlock(worldPlacer, centerPos.offset( -x, 0, -z), state, random);
                    FeaturePlacers.placeProvidedBlock(worldPlacer, centerPos.offset( -x, 0, 1+z), state, random);
                    FeaturePlacers.placeProvidedBlock(worldPlacer, centerPos.offset( 1+x, 0, -z), state, random);
                    // Confused how this circle pixel-filling algorithm works exactly? https://www.desmos.com/calculator/psqynhk21k
                }
            }
        }
    }

    public static void placeSpheroid(BiConsumer<BlockPos, BlockState> placer, Random random, BlockPos centerPos, float xzRadius, float yRadius, float verticalBias, BlockStateProvider state) {
        float xzRadiusSquared = xzRadius * xzRadius;
        float yRadiusSquared = yRadius * yRadius;
        float superRadiusSquared = xzRadiusSquared * yRadiusSquared;
        FeaturePlacers.placeProvidedBlock(placer, centerPos, state, random);

        for (int y = 0; y <= yRadius; y++) {
            if (y > yRadius) continue;

            FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( 0,  y, 0), state, random);
            FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( 0,  y, 0), state, random);
            FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( 0,  y, 0), state, random);
            FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( 0,  y, 0), state, random);

            FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( 0, -y, 0), state, random);
            FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( 0, -y, 0), state, random);
            FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( 0, -y, 0), state, random);
            FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( 0, -y, 0), state, random);
        }

        for (int x = 0; x <= xzRadius; x++) {
            for (int z = 1; z <= xzRadius; z++) {
                if (x * x + z * z > xzRadiusSquared) continue;

                FeaturePlacers.placeProvidedBlock(placer, centerPos.offset(  x, 0,  z), state, random);
                FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( -x, 0, -z), state, random);
                FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( -z, 0,  x), state, random);
                FeaturePlacers.placeProvidedBlock(placer, centerPos.offset(  z, 0, -x), state, random);

                for (int y = 1; y <= yRadius; y++) {
                    float xzSquare = ((x * x + z * z) * yRadiusSquared);

                    if (xzSquare + (((y - verticalBias) * (y - verticalBias)) * xzRadiusSquared) <= superRadiusSquared) {
                        FeaturePlacers.placeProvidedBlock(placer, centerPos.offset(  x,  y,  z), state, random);
                        FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( -x,  y, -z), state, random);
                        FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( -z,  y,  x), state, random);
                        FeaturePlacers.placeProvidedBlock(placer, centerPos.offset(  z,  y, -x), state, random);
                    }

                    if (xzSquare + (((y + verticalBias) * (y + verticalBias)) * xzRadiusSquared) <= superRadiusSquared) {
                        FeaturePlacers.placeProvidedBlock(placer, centerPos.offset(  x, -y,  z), state, random);
                        FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( -x, -y, -z), state, random);
                        FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( -z, -y,  x), state, random);
                        FeaturePlacers.placeProvidedBlock(placer, centerPos.offset(  z, -y, -x), state, random);
                    }
                }
            }
        }
    }

    // Version without the `verticalBias` unlike above
    public static void placeSpheroid(BiConsumer<BlockPos, BlockState> placer, Random random, BlockPos centerPos, float xzRadius, float yRadius, BlockStateProvider state) {
        float xzRadiusSquared = xzRadius * xzRadius;
        float yRadiusSquared = yRadius * yRadius;
        float superRadiusSquared = xzRadiusSquared * yRadiusSquared;
        FeaturePlacers.placeProvidedBlock(placer, centerPos, state, random);

        for (int y = 0; y <= yRadius; y++) {
            if (y > yRadius) continue;

            FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( 0,  y, 0), state, random);
            FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( 0,  y, 0), state, random);
            FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( 0,  y, 0), state, random);
            FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( 0,  y, 0), state, random);

            FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( 0, -y, 0), state, random);
            FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( 0, -y, 0), state, random);
            FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( 0, -y, 0), state, random);
            FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( 0, -y, 0), state, random);
        }

        for (int x = 0; x <= xzRadius; x++) {
            for (int z = 1; z <= xzRadius; z++) {
                if (x * x + z * z > xzRadiusSquared) continue;

                FeaturePlacers.placeProvidedBlock(placer, centerPos.offset(  x, 0,  z), state, random);
                FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( -x, 0, -z), state, random);
                FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( -z, 0,  x), state, random);
                FeaturePlacers.placeProvidedBlock(placer, centerPos.offset(  z, 0, -x), state, random);

                for (int y = 1; y <= yRadius; y++) {
                    float xzSquare = ((x * x + z * z) * yRadiusSquared);

                    if (xzSquare + (y * y) * xzRadiusSquared <= superRadiusSquared) {
                        FeaturePlacers.placeProvidedBlock(placer, centerPos.offset(  x,  y,  z), state, random);
                        FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( -x,  y, -z), state, random);
                        FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( -z,  y,  x), state, random);
                        FeaturePlacers.placeProvidedBlock(placer, centerPos.offset(  z,  y, -x), state, random);

                        FeaturePlacers.placeProvidedBlock(placer, centerPos.offset(  x, -y,  z), state, random);
                        FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( -x, -y, -z), state, random);
                        FeaturePlacers.placeProvidedBlock(placer, centerPos.offset( -z, -y,  x), state, random);
                        FeaturePlacers.placeProvidedBlock(placer, centerPos.offset(  z, -y, -x), state, random);
                    }
                }
            }
        }
    }

    public static boolean canRootGrowIn(LevelReader world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        Block blockID = blockState.getBlock();

        if (blockState.isAir()) {
            // roots can grow through air if they are near a solid block
            return FeatureUtil.isNearSolid(world, pos);
        } else {
            return (blockState.getDestroySpeed(world, pos) >= 0) // TODO We should probably find a more universal solution?
                    && blockID != TFBlocks.stronghold_shield.get()
                    && blockID != TFBlocks.trophy_pedestal.get()
                    && blockID != TFBlocks.boss_spawner_naga.get()
                    && blockID != TFBlocks.boss_spawner_lich.get()
                    && blockID != TFBlocks.boss_spawner_hydra.get()
                    && blockID != TFBlocks.boss_spawner_ur_ghast.get()
                    && blockID != TFBlocks.boss_spawner_knight_phantom.get()
                    && blockID != TFBlocks.boss_spawner_snow_queen.get()
                    && blockID != TFBlocks.boss_spawner_minoshroom.get()
                    && blockID != TFBlocks.boss_spawner_alpha_yeti.get()
                    && (blockState.getMaterial() == Material.GRASS || blockState.getMaterial() == Material.DIRT || blockState.getMaterial() == Material.STONE || blockState.getMaterial() == Material.WATER);
        }
    }

    // TrunkPlacer.placeLog copy - Use that one instead when extending that Abstract
    // Swapped TreeConfiguration for BlockStateProvider
    public static boolean placeIfValidTreePos(LevelAccessor world, BiConsumer<BlockPos, BlockState> placer, Random random, BlockPos pos, BlockStateProvider config) {
        if (TreeFeature.validTreePos(world, pos)) {
            placer.accept(pos, config.getState(random, pos));
            return true;
        } else {
            return false;
        }
    }

    // TODO Root-placing should hook into this too
    public static boolean placeIfValidRootPos(LevelAccessor world, BiConsumer<BlockPos, BlockState> placer, Random random, BlockPos pos, BlockStateProvider config) {
        if (FeatureLogic.canRootGrowIn(world, pos)) {
            placer.accept(pos, config.getState(random, pos));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add a firefly at the specified height and angle.
     *
     * @param height how far up the tree
     * @param angle  from 0 - 1 rotation around the tree
     */
    public static void addFirefly(LevelAccessor world, BlockPos pos, int height, double angle) {
        int iAngle = (int) (angle * 4.0);
        if (iAngle == 0) {
            setIfEmpty(world, pos.offset( 1, height,  0), TFBlocks.firefly.get().defaultBlockState().setValue(DirectionalBlock.FACING, Direction.EAST));
        } else if (iAngle == 1) {
            setIfEmpty(world, pos.offset(-1, height,  0), TFBlocks.firefly.get().defaultBlockState().setValue(DirectionalBlock.FACING, Direction.WEST));
        } else if (iAngle == 2) {
            setIfEmpty(world, pos.offset( 0, height,  1), TFBlocks.firefly.get().defaultBlockState().setValue(DirectionalBlock.FACING, Direction.SOUTH));
        } else if (iAngle == 3) {
            setIfEmpty(world, pos.offset( 0, height, -1), TFBlocks.firefly.get().defaultBlockState().setValue(DirectionalBlock.FACING, Direction.NORTH));
        }
    }

    private static void setIfEmpty(LevelAccessor world, BlockPos pos, BlockState state) {
        if (world.isEmptyBlock(pos)) {
            world.setBlock(pos, state,3);
        }
    }
}
