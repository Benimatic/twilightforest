package twilightforest.worldgen.treeplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.LevelSimulatedRW;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import twilightforest.worldgen.TwilightFeatures;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class TrunkRiser extends TrunkPlacer {
    public static final Codec<TrunkRiser> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.intRange(0, 16).fieldOf("offset_up").forGetter(o -> o.offset),
                    TrunkPlacer.CODEC.fieldOf("trunk_placer").forGetter(o -> o.placer)
            ).apply(instance, TrunkRiser::new)
    );

    private final int offset;
    private final TrunkPlacer placer;

    public TrunkRiser(int baseHeight, TrunkPlacer placer) {
        super(placer.baseHeight, placer.heightRandA, placer.heightRandB);

        this.offset = baseHeight;
        this.placer = placer;
    }

    @Override
    protected TrunkPlacerType<TrunkRiser> type() {
        return TwilightFeatures.TRUNK_RISER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedRW iWorldGenerationReader, Random random, int i, BlockPos blockPos, Set<BlockPos> set, BoundingBox mutableBoundingBox, TreeConfiguration baseTreeFeatureConfig) {
        return placer.placeTrunk(iWorldGenerationReader, random, i, blockPos.above(offset), set, mutableBoundingBox, baseTreeFeatureConfig);
    }
}
