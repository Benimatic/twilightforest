package twilightforest.world.feature.tree;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import twilightforest.world.feature.config.TFTreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * It's just a Tree, but for TFTreeFeatureConfig
 */
public abstract class TFTree extends Tree {

	@Nullable
	@Override
	protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean b) {
		return null;
	}

	public abstract ConfiguredFeature<TFTreeFeatureConfig, ?> createTreeFeature(Random rand);

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, BlockPos pos, BlockState state, Random rand) {
		ConfiguredFeature<TFTreeFeatureConfig, ?> feature = this.createTreeFeature(rand);
		if (feature == null) {
			return false;
		} else {
			world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
			feature.config.func_227373_a_();
			if (feature.place(world, generator, rand, pos)) {
				return true;
			} else {
				world.setBlockState(pos, state, 4);
				return false;
			}
		}
	}
}
