package twilightforest.worldgen.treeplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.LevelSimulatedRW;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.util.UniformInt;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import twilightforest.util.FeatureUtil;
import twilightforest.worldgen.TwilightFeatures;

import java.util.Random;
import java.util.Set;

import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment;

public class LeafSpheroidFoliagePlacer extends FoliagePlacer {
    public static final Codec<LeafSpheroidFoliagePlacer> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.floatRange(0, 16f).fieldOf("horizontal_radius").forGetter(o -> o.horizontalRadius),
                    Codec.floatRange(0, 16f).fieldOf("vertical_radius").forGetter(o -> o.verticalRadius),
                    UniformInt.codec(0, 8, 8).fieldOf("offset").forGetter(obj -> obj.offset),
                    Codec.intRange(0, 16).fieldOf("random_add_horizontal").orElse(0).forGetter(o -> o.randomHorizontal),
                    Codec.intRange(0, 16).fieldOf("random_add_vertical").orElse(0).forGetter(o -> o.randomVertical),
                    Codec.floatRange(-0.5f, 0.5f).fieldOf("vertical_filler_bias").orElse(0f).forGetter(o -> o.verticalBias),
                    Codec.intRange(0, 256).fieldOf("shag_factor").orElse(0).forGetter(o -> o.shag_factor) // Shhh
            ).apply(instance, LeafSpheroidFoliagePlacer::new)
    );

    // These two variables are floats to help round out the pixel-snapping of the sphere-filling algorithm
    // n+0.5 numbers seem to work best but messing with it is encouraged to find best results
    private final float horizontalRadius;
    private final float verticalRadius;
    private final float verticalBias;

    private final int randomHorizontal;
    private final int randomVertical;
    private final int shag_factor;

    public LeafSpheroidFoliagePlacer(float horizontalRadius, float verticalRadius, UniformInt yOffset, int randomHorizontal, int randomVertical, float verticalBias, int shag_factor) {
        super(UniformInt.fixed((int) horizontalRadius), yOffset);

        this.horizontalRadius = horizontalRadius;
        this.verticalRadius = verticalRadius;
        this.randomHorizontal = randomHorizontal;
        this.randomVertical = randomVertical;
        this.verticalBias = verticalBias;
        this.shag_factor = shag_factor;
    }

    @Override
    protected FoliagePlacerType<LeafSpheroidFoliagePlacer> type() {
        return TwilightFeatures.FOLIAGE_SPHEROID;
    }

    @Override // place foliage
    protected void createFoliage(LevelSimulatedRW world, Random random, TreeConfiguration baseTreeFeatureConfig, int trunkHeight, FoliageAttachment foliage, int foliageHeight, int radius, Set<BlockPos> leavesSet, int offset, BoundingBox mutableBoundingBox) {
        BlockPos center = foliage.foliagePos().above(offset); // foliage.getCenter

        //FeatureUtil.makeLeafCircle(world, random, center, radius, baseTreeFeatureConfig.leavesProvider, leavesSet);
        FeatureUtil.makeLeafSpheroid(world, random, center, foliage.radiusOffset() + horizontalRadius + random.nextInt(randomHorizontal + 1), foliage.radiusOffset() + verticalRadius + random.nextInt(randomVertical + 1), verticalBias, baseTreeFeatureConfig.leavesProvider, leavesSet);

        if (shag_factor > 0) {
            for (int i = 0; i < shag_factor; i++) {
                float TWO_PI = (float) (2.0 * Math.PI);
                float randomYaw = random.nextFloat() * TWO_PI;
                float randomPitch = random.nextFloat() * 2f - 1f; //random.nextFloat() * 0.75f + 0.25f;
                float yUnit = Mth.sqrt(1 - randomPitch * randomPitch); // Inverse Trigonometry identity (sin(arcos(t)) == sqrt(1 - t*t))
                float xCircleOffset = yUnit * Mth.cos(randomYaw) * (horizontalRadius - 1f); // We do radius minus 1 here so the leaf 2x2 generates overlapping the existing foilage
                float zCircleOffset = yUnit * Mth.sin(randomYaw) * (horizontalRadius - 1f); // instead of making awkward 2x2 pieces of foilage jutting out

                BlockPos placement = center.offset(xCircleOffset + ((int) xCircleOffset >> 31), randomPitch * (verticalRadius + 0.25f) + verticalBias, zCircleOffset + ((int) zCircleOffset >> 31));

                placeLeafCluster(world, random, placement.immutable(), baseTreeFeatureConfig.leavesProvider, leavesSet);
            }
        }
    }

    private void placeLeafCluster(LevelSimulatedRW world, Random random, BlockPos pos, BlockStateProvider state, Set<BlockPos> leavesPos) {
        FeatureUtil.putLeafBlock(world, random, pos, state, leavesPos);
        FeatureUtil.putLeafBlock(world, random, pos.east(), state, leavesPos);
        FeatureUtil.putLeafBlock(world, random, pos.south(), state, leavesPos);
        FeatureUtil.putLeafBlock(world, random, pos.offset(1, 0, 1), state, leavesPos);
    }

    @Override // foliage Height
    public int foliageHeight(Random random, int i, TreeConfiguration baseTreeFeatureConfig) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(Random random, int i, int i1, int i2, int i3, boolean b) {
        return false;
    }
}
