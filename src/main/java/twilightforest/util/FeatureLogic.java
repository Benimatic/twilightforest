package twilightforest.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.data.tags.BlockTagGenerator;

import java.util.Random;
import java.util.function.Predicate;

/**
 * Feature Utility methods that don't invoke placement. For placement see FeaturePlacers
 */
public final class FeatureLogic {
    public static final Predicate<BlockState> IS_REPLACEABLE_AIR = state -> state.getMaterial().isReplaceable() || state.isAir();
    public static final Predicate<BlockState> ROOT_SHOULD_SKIP = state -> state.is(BlockTagGenerator.ROOT_TRACE_SKIP);
    public static boolean hasEmptyHorizontalNeighbor(LevelSimulatedReader worldReader, BlockPos pos) {
        return worldReader.isStateAtPosition(pos.north(), IS_REPLACEABLE_AIR)
                || worldReader.isStateAtPosition(pos.south(), IS_REPLACEABLE_AIR)
                || worldReader.isStateAtPosition(pos.west(), IS_REPLACEABLE_AIR)
                || worldReader.isStateAtPosition(pos.east(), IS_REPLACEABLE_AIR);
    }

    // Slight stretch of logic: We check if the block is completely surrounded by air.
    // If it's not completely surrounded by air, then there's a solid
    public static boolean hasSolidNeighbor(LevelSimulatedReader worldReader, BlockPos pos) {
        return !(worldReader.isStateAtPosition(pos.below(), IS_REPLACEABLE_AIR)
                && worldReader.isStateAtPosition(pos.north(), IS_REPLACEABLE_AIR)
                && worldReader.isStateAtPosition(pos.south(), IS_REPLACEABLE_AIR)
                && worldReader.isStateAtPosition(pos.west(), IS_REPLACEABLE_AIR)
                && worldReader.isStateAtPosition(pos.east(), IS_REPLACEABLE_AIR)
                && worldReader.isStateAtPosition(pos.above(), IS_REPLACEABLE_AIR));
    }

    public static boolean canRootGrowIn(LevelSimulatedReader worldReader, BlockPos pos) {
        if (worldReader.isStateAtPosition(pos, IS_REPLACEABLE_AIR)) {
            // roots can grow through air if they are near a solid block
            return hasSolidNeighbor(worldReader, pos);
        } else {
            return worldReader.isStateAtPosition(pos, FeatureLogic::worldGenReplaceable);
        }
    }

    public static boolean worldGenReplaceable(BlockState state) {
        return (state.getMaterial().isReplaceable() || state.is(BlockTagGenerator.WORLDGEN_REPLACEABLES)) && !state.is(BlockTags.FEATURES_CANNOT_REPLACE);
    }

    /**
     * Moves distance along the vector.
     * <p>
     * This goofy function takes a float between 0 and 1 for the angle, where 0 is 0 degrees, .5 is 180 degrees and 1 and 360 degrees.
     * For the tilt, it takes a float between 0 and 1 where 0 is straight up, 0.5 is straight out and 1 is straight down.
     */
    public static BlockPos translate(BlockPos pos, double distance, double angle, double tilt) {
        double rangle = angle * 2.0D * Math.PI;
        double rtilt = tilt * Math.PI;

        //TODO verify if casting to int is the right play here
        return pos.offset(
                (int) Math.round(Math.sin(rangle) * Math.sin(rtilt) * distance),
                (int) Math.round(Math.cos(rtilt) * distance),
                (int) Math.round(Math.cos(rangle) * Math.sin(rtilt) * distance)
        );
    }

    /**
     * Gets either cobblestone or mossy cobblestone, randomly.  Used for ruins.
     */
    @Deprecated // Determine if we can actually remove this one and delegate to StructureProcessor
    public static BlockState randStone(RandomSource rand, int howMuch) {
        return rand.nextInt(howMuch) >= 1 ? Blocks.COBBLESTONE.defaultBlockState() : Blocks.MOSSY_COBBLESTONE.defaultBlockState();
    }
}
