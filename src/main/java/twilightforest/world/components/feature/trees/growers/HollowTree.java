package twilightforest.world.components.feature.trees.growers;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import twilightforest.world.registration.ConfiguredFeatures;
import twilightforest.world.components.feature.config.TFTreeFeatureConfig;

public class HollowTree extends TFTree {

	@Override
	public ConfiguredFeature<TFTreeFeatureConfig, ?> createTreeFeature() {
		return ConfiguredFeatures.HOLLOW_TREE_BASE;
	}
}
