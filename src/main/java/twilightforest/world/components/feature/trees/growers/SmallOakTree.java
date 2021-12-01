package twilightforest.world.components.feature.trees.growers;

import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import twilightforest.world.registration.features.TFTreeFeatures;

import javax.annotation.Nullable;
import java.util.Random;

public class SmallOakTree extends AbstractTreeGrower {

	@Nullable
	@Override
	protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random random, boolean b) {
		return TFTreeFeatures.TWILIGHT_OAK_BASE;
	}
}
