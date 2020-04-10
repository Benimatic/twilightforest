package twilightforest.world.feature.tree;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import twilightforest.biomes.TFBiomeDecorator;

import javax.annotation.Nullable;
import java.util.Random;

public class SmallOakTree extends Tree {

	@Nullable
	@Override
	protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean b) {
		return Feature.NORMAL_TREE.configure(TFBiomeDecorator.OAK_TREE);
	}
}
