package twilightforest.world.newfeature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import twilightforest.util.FeatureUtil;

import java.util.Random;
import java.util.Set;

public class LeafSpheroidFoliagePlacer extends FoliagePlacer {
    public static final Codec<LeafSpheroidFoliagePlacer> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.FLOAT.fieldOf("horizontal_radius").forGetter(o -> o.horizontalRadius),
                    FeatureSpread.func_242254_a(0, 8, 8).fieldOf("offset").forGetter(obj -> obj.field_236750_g_),
                    Codec.FLOAT.fieldOf("vertical_radius").forGetter(o -> o.verticalRadius)
            ).apply(instance, LeafSpheroidFoliagePlacer::new)
    );
    private float horizontalRadius;
    private float verticalRadius;

    public LeafSpheroidFoliagePlacer(float horizontalRadius, FeatureSpread yOffset, float verticalRadius) {
        super(FeatureSpread.func_242252_a((int) horizontalRadius), yOffset);

        this.horizontalRadius = horizontalRadius;
        this.verticalRadius = verticalRadius;
    }

    @Override
    protected FoliagePlacerType<?> func_230371_a_() {
        return TFFeatures.FOLIAGE_SPHEROID;
    }

    @Override // place foliage
    protected void func_230372_a_(IWorldGenerationReader world, Random random, BaseTreeFeatureConfig baseTreeFeatureConfig, int trunkHeight, Foliage foliage, int foliageHeight, int radius, Set<BlockPos> set, int offset, MutableBoundingBox mutableBoundingBox) {
        BlockPos center = foliage.func_236763_a_().up(offset); // foliage.getCenter

        //FeatureUtil.makeLeafCircle(world, random, center, radius, baseTreeFeatureConfig.leavesProvider, set);
        FeatureUtil.makeLeafSpheroid(world, random, center, horizontalRadius, verticalRadius, baseTreeFeatureConfig.leavesProvider, set);
    }

    @Override // foliage Height
    public int func_230374_a_(Random random, int i, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        return 0;
    }

    @Override
    protected boolean func_230373_a_(Random random, int i, int i1, int i2, int i3, boolean b) {
        return false;
    }
}
