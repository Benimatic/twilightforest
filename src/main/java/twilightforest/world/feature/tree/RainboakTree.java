package twilightforest.world.feature.tree;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import twilightforest.biomes.TFBiomeDecorator;

import javax.annotation.Nullable;
import java.util.Random;

public class RainboakTree extends Tree {

	@Nullable
	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random random, boolean b) {
		return Feature.field_236291_c_.withConfiguration(TFBiomeDecorator.RAINBOAK_TREE);
	}
}
