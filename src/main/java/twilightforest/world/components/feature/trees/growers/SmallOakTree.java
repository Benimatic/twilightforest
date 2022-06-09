package twilightforest.world.components.feature.trees.growers;

import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import twilightforest.init.TFConfiguredFeatures;

import javax.annotation.Nullable;

public class SmallOakTree extends AbstractTreeGrower {

	@Nullable
	@Override
	protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean b) {
		return TFConfiguredFeatures.TWILIGHT_OAK_TREE;
	}
}
