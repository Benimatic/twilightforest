package twilightforest.world.components.feature.trees.treeplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import twilightforest.TwilightForestMod;
import twilightforest.world.registration.TwilightFeatures;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

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
        return TwilightFeatures.TRUNKSIDE_DECORATOR.get();
    }

    @Override
    public void place(LevelSimulatedReader worldReader, BiConsumer<BlockPos, BlockState> worldPlacer, Random random, List<BlockPos> trunkBlocks, List<BlockPos> leafBlocks) {
        int blockCount = trunkBlocks.size();

        if (blockCount <= 0) {
            TwilightForestMod.LOGGER.error("[TrunkSideDecorator] Trunk Blocks were empty! Why?");
            return;
        }

        for (int attempt = 0; attempt < this.count; attempt++) {
            if (random.nextFloat() >= this.probability) continue;

            Rotation rot = Rotation.getRandom(random);
            BlockPos pos = trunkBlocks.get(random.nextInt(blockCount)).relative(rot.rotate(Direction.NORTH));

            if (Feature.isAir(worldReader, pos)) // Checks if block is air
                worldPlacer.accept(pos, this.decoration.getState(random, pos));
        }
    }
}
