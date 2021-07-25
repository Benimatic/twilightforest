package twilightforest.worldgen.treeplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import twilightforest.worldgen.TwilightFeatures;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class DangleFromTreeDecorator extends TreeDecorator {
    public static final Codec<DangleFromTreeDecorator> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.intRange(0, 32).fieldOf("attempts_minimum").forGetter(o -> o.count),
                    Codec.intRange(0, 32).fieldOf("random_add_attempts").orElse(0).forGetter(o -> o.randomAddCount),
                    Codec.intRange(1, 24).fieldOf("minimum_required_length").forGetter(o -> o.minimumRequiredLength),
                    Codec.intRange(1, 24).fieldOf("base_length").forGetter(o -> o.baseLength),
                    Codec.intRange(0, 16).fieldOf("random_add_length").orElse(0).forGetter(o -> o.randomAddLength),
                    BlockStateProvider.CODEC.fieldOf("rope_provider").forGetter(o -> o.rope),
                    BlockStateProvider.CODEC.fieldOf("baggage_provider").forGetter(o -> o.baggage)
            ).apply(instance, DangleFromTreeDecorator::new)
    );
    private final int count;
    private final int randomAddCount;
    private final int minimumRequiredLength;
    private final int baseLength;
    private final int randomAddLength;
    private final BlockStateProvider rope;
    private final BlockStateProvider baggage;

    public DangleFromTreeDecorator(int count, int randomAddCount, int minimumRequiredLength, int baseLength, int randomAddLength, BlockStateProvider rope, BlockStateProvider baggage) {
        this.count = count;
        this.randomAddCount = randomAddCount;
        this.minimumRequiredLength = minimumRequiredLength;
        this.baseLength = baseLength;
        this.randomAddLength = randomAddLength;
        this.rope = rope;
        this.baggage = baggage;
    }

    @Override
    protected TreeDecoratorType<DangleFromTreeDecorator> type() {
        return TwilightFeatures.DANGLING_DECORATOR;
    }

    @Override
    public void place(WorldGenLevel world, Random random, List<BlockPos> trunkBlocks, List<BlockPos> leafBlocks, Set<BlockPos> decorations, BoundingBox mutableBoundingBox) {
        if (leafBlocks.isEmpty())
            return;

        int totalTries = count + random.nextInt(randomAddCount + 1);
        int leafTotal = leafBlocks.size();
        boolean clearedOfPossibleLeaves;
        boolean isAir;
        int cordLength;
        BlockPos pos;

        totalTries = Math.min(totalTries, leafTotal);

        for (int attempt = 0; attempt < totalTries; attempt++) {
            clearedOfPossibleLeaves = false;
            pos = leafBlocks.get(random.nextInt(leafTotal));

            cordLength = baseLength + random.nextInt(randomAddLength + 1);

            // Scan to make sure we have
            for (int ropeUnrolling = 1; ropeUnrolling <= cordLength; ropeUnrolling++) {
                isAir = world.isEmptyBlock(pos.below(ropeUnrolling));

                if (!clearedOfPossibleLeaves && isAir)
                    clearedOfPossibleLeaves = true;

                if (clearedOfPossibleLeaves && !isAir) {
                    cordLength = ropeUnrolling;

                    break;
                }
            }

            if (cordLength > minimumRequiredLength) { // We don't want no pathetic unroped baggage
                for (int ropeUnrolling = 1; ropeUnrolling < cordLength; ropeUnrolling++) {
                    pos = pos.below(1);

                    setBlock(world, pos, rope.getState(random, pos), decorations, mutableBoundingBox);
                }

                pos = pos.below(1);

                setBlock(world, pos, baggage.getState(random, pos), decorations, mutableBoundingBox);
            }
        }
    }
}
