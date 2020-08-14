package twilightforest.world.feature.tree;

import net.minecraft.world.gen.feature.ConfiguredFeature;
import twilightforest.world.feature.TFBiomeFeatures;
import twilightforest.world.feature.config.TFTreeFeatureConfig;

import java.util.Random;

public class TimeTree extends TFTree {

	@Override
	public ConfiguredFeature<TFTreeFeatureConfig, ?> createTreeFeature(Random rand) {
		return null;// FIXME  TFBiomeFeatures.TREE_OF_TIME.get().withConfiguration(TFBiomeDecorator.TIME_TREE);
	}
}
