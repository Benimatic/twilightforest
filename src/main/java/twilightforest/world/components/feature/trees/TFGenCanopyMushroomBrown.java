package twilightforest.world.components.feature.trees;

import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
public class TFGenCanopyMushroomBrown extends TFGenCanopyMushroom {
    public TFGenCanopyMushroomBrown(Codec<HugeMushroomFeatureConfiguration> featureConfigurationCodec) {
        super(featureConfigurationCodec);
    }

    @Override
    protected int getBranches(RandomSource random) {
        return Math.max(random.nextInt(5), 3);
    }

    @Override
    protected double getLength(RandomSource random) {
        return 9 - random.nextInt(2);
    }
}
