package twilightforest.world.components.feature.trees.growers;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import twilightforest.world.components.feature.config.TFTreeFeatureConfig;
import twilightforest.world.registration.features.TFTreeFeatures;

public class HollowTree extends TFTree {

	@Override
	public ConfiguredFeature<TFTreeFeatureConfig, ?> createTreeFeature() {
		return TFTreeFeatures.HOLLOW_TREE_BASE;
	}
}
