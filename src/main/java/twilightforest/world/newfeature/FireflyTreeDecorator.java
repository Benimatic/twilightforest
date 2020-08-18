package twilightforest.world.newfeature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import twilightforest.block.BlockTFFirefly;
import twilightforest.block.TFBlocks;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class FireflyTreeDecorator extends TreeDecorator {
    public static final Codec<FireflyTreeDecorator> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.intRange(0, 64).fieldOf("placement_count").forGetter(o -> o.count),
                    Codec.floatRange(0f, 1f).fieldOf("probability_of_placement").forGetter(o -> o.probability)
            ).apply(instance, FireflyTreeDecorator::new)
    );

    private int count;
    private float probability;

    public FireflyTreeDecorator(int count, float probability) {
        this.count = count;
        this.probability = probability;
    }

    @Override
    protected TreeDecoratorType<?> func_230380_a_() {
        return TFFeatures.FIREFLY;
    }

    @Override
    public void func_225576_a_(ISeedReader world, Random random, List<BlockPos> trunkBlocks, List<BlockPos> leafBlocks, Set<BlockPos> decorations, MutableBoundingBox mutableBoundingBox) {
        int blockCount = trunkBlocks.size();

        for (int attempt = 0; attempt < count; attempt++) {
            if (random.nextFloat() >= probability) continue;

            Direction dir = Direction.getRandomDirection(random);
            BlockPos pos = trunkBlocks.get(random.nextInt(blockCount)).offset(dir);

            if (Feature.func_236297_b_(world, pos))
                func_227423_a_(world, pos, TFBlocks.firefly.get().getDefaultState().with(BlockTFFirefly.FACING, dir), decorations, mutableBoundingBox);
        }
    }
}
