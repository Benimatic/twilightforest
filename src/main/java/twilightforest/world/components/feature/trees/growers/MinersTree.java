package twilightforest.world.components.feature.trees.growers;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import twilightforest.world.components.feature.config.TFTreeFeatureConfig;
import twilightforest.world.registration.features.TFTreeFeatures;

public class MinersTree extends TFTree {

	@Override
	public ConfiguredFeature<TFTreeFeatureConfig, ?> createTreeFeature() {
		return TFTreeFeatures.MINING_TREE_BASE;
	}
}
