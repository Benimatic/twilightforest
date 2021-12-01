package twilightforest.world.components.feature.trees.growers;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import twilightforest.world.components.feature.config.TFTreeFeatureConfig;
import twilightforest.world.registration.features.TFTreeFeatures;

public class TimeTree extends TFTree {

	@Override
	public ConfiguredFeature<TFTreeFeatureConfig, ?> createTreeFeature() {
		return TFTreeFeatures.TIME_TREE_BASE;
	}
}
