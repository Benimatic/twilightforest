package twilightforest.worldgen;

import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import twilightforest.block.FireflyBlock;
import twilightforest.block.TFBlocks;
import twilightforest.worldgen.treeplacers.TreeRootsDecorator;
import twilightforest.worldgen.treeplacers.TrunkSideDecorator;

public final class TreeDecorators {
    public static final TreeRootsDecorator LIVING_ROOTS = new TreeRootsDecorator(3, 1, 5, (new WeightedStateProvider())
            .add(BlockConstants.ROOTS, 6)
            .add(TFBlocks.liveroot_block.get().defaultBlockState(), 1));

    public static final TrunkSideDecorator FIREFLY = new TrunkSideDecorator(1, 1.0f, new SimpleStateProvider(TFBlocks.firefly.get().defaultBlockState().setValue(FireflyBlock.FACING, Direction.NORTH)));
}
