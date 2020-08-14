package twilightforest.world.feature.tree;

import net.minecraft.world.gen.feature.ConfiguredFeature;
import twilightforest.world.feature.TFBiomeFeatures;
import twilightforest.world.feature.config.TFTreeFeatureConfig;

import java.util.Random;

public class MangroveTree extends TFTree {

	@Override
	public ConfiguredFeature<TFTreeFeatureConfig, ?> createTreeFeature(Random rand) {
		return null;// FIXME  TFBiomeFeatures.MANGROVE_TREE.get().withConfiguration(TFBiomeDecorator.MANGROVE_TREE_NO_WATER);
	}
}
