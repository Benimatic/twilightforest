package twilightforest.world.feature.tree;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.server.level.ServerLevel;
import twilightforest.world.feature.config.TFTreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * It's just a Tree, but for TFTreeFeatureConfig
 */
public abstract class TFTree extends AbstractTreeGrower {

	@Nullable
	@Override
	protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random random, boolean b) {
		return null;
	}

	public abstract ConfiguredFeature<TFTreeFeatureConfig, ?> createTreeFeature();

	@Override
	public boolean growTree(ServerLevel world, ChunkGenerator generator, BlockPos pos, BlockState state, Random rand) {
		ConfiguredFeature<TFTreeFeatureConfig, ?> feature = this.createTreeFeature();
		if (feature == null) {
			return false;
		} else {
			world.setBlock(pos, Blocks.AIR.defaultBlockState(), 4);
			feature.config.forcePlacement();
			if (feature.place(world, generator, rand, pos)) {
				return true;
			} else {
				world.setBlock(pos, state, 4);
				return false;
			}
		}
	}
}
