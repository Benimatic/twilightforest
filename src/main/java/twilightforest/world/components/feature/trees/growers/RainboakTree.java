package twilightforest.world.components.feature.trees.growers;

import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import twilightforest.world.registration.ConfiguredFeatures;

import javax.annotation.Nullable;
import java.util.Random;

public class RainboakTree extends AbstractTreeGrower {

	@Nullable
	@Override
	protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random random, boolean b) {
		return ConfiguredFeatures.RAINBOW_OAK_TREE_BASE;
	}
}
