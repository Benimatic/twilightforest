package twilightforest.worldgen.treeplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import twilightforest.worldgen.TwilightFeatures;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class TrunkSideDecorator extends TreeDecorator {
    public static final Codec<TrunkSideDecorator> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.intRange(0, 64).fieldOf("placement_count").forGetter(o -> o.count),
                    Codec.floatRange(0f, 1f).fieldOf("probability_of_placement").forGetter(o -> o.probability),
                    BlockStateProvider.CODEC.fieldOf("deco_provider").forGetter(o -> o.decoration)
            ).apply(instance, TrunkSideDecorator::new)
    );

    private final int count;
    private final float probability;
    private final BlockStateProvider decoration;

    public TrunkSideDecorator(int count, float probability, BlockStateProvider decorator) {
        this.count = count;
        this.probability = probability;
        this.decoration = decorator;
    }

    @Override
    protected TreeDecoratorType<TrunkSideDecorator> type() {
        return TwilightFeatures.TRUNKSIDE_DECORATOR;
    }

    @Override
    public void place(WorldGenLevel world, Random random, List<BlockPos> trunkBlocks, List<BlockPos> leafBlocks, Set<BlockPos> decorations, BoundingBox mutableBoundingBox) {
        int blockCount = trunkBlocks.size();

        for (int attempt = 0; attempt < count; attempt++) {
            if (random.nextFloat() >= probability) continue;

            Rotation rot = Rotation.getRandom(random);
            BlockPos pos = trunkBlocks.get(random.nextInt(blockCount)).relative(rot.rotate(Direction.NORTH));

            if (Feature.isAir(world, pos)) // Checks if block is air
                setBlock(world, pos, decoration.getState(random, pos).rotate(rot), decorations, mutableBoundingBox);
        }
    }
}
