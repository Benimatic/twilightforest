package twilightforest.world.components.feature.trees.growers;

import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import twilightforest.world.components.feature.config.TFTreeFeatureConfig;
import twilightforest.world.registration.features.TFTreeFeatures;

public class HollowTree extends TFTree {

	@Override
	public Holder<ConfiguredFeature<TFTreeFeatureConfig, Feature<TFTreeFeatureConfig>>> createTreeFeature() {
		return TFTreeFeatures.HOLLOW_TREE;
	}
}
