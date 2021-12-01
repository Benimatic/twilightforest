package twilightforest.world.components.feature.trees.growers;

import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import twilightforest.world.registration.features.TFTreeFeatures;

import java.util.Random;

public class TransformationTree extends AbstractTreeGrower {

	@Override
	public ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random rand, boolean largeHive) {
		return TFTreeFeatures.TRANSFORM_TREE_BASE;
	}
}
