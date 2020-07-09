package twilightforest.world.feature.tree;

import net.minecraft.world.gen.feature.ConfiguredFeature;
import twilightforest.biomes.TFBiomeDecorator;
import twilightforest.world.feature.TFBiomeFeatures;
import twilightforest.world.feature.config.TFTreeFeatureConfig;

import java.util.Random;

public class CanopyTree extends TFTree {

	@Override
	public ConfiguredFeature<TFTreeFeatureConfig, ?> createTreeFeature(Random rand) {
		return TFBiomeFeatures.CANOPY_TREE.get().withConfiguration(TFBiomeDecorator.CANOPY_TREE_CONFIG);
	}
}
