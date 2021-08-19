package twilightforest.world.components.feature.trees.growers;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import twilightforest.world.registration.ConfiguredFeatures;
import twilightforest.world.components.feature.config.TFTreeFeatureConfig;

public class MinersTree extends TFTree {

	@Override
	public ConfiguredFeature<TFTreeFeatureConfig, ?> createTreeFeature() {
		return ConfiguredFeatures.MINING_TREE_BASE;
	}
}
