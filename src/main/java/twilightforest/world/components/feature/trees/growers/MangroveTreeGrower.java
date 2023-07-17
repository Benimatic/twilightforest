package twilightforest.world.components.feature.trees.growers;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFConfiguredFeatures;

public class MangroveTreeGrower extends AbstractTreeGrower {

	@Nullable
	@Override
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean makeBees) {
		return TFConfiguredFeatures.MANGROVE_TREE;
	}
}
