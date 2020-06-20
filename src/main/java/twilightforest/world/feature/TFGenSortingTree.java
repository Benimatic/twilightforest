package twilightforest.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import twilightforest.block.TFBlocks;
import twilightforest.util.FeatureUtil;
import twilightforest.world.TFGenerationSettings;
import twilightforest.world.feature.config.TFTreeFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class TFGenSortingTree extends Feature<TFTreeFeatureConfig> {

	public TFGenSortingTree(Function<Dynamic<?>, TFTreeFeatureConfig> config) {
		super(config);
	}

	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, TFTreeFeatureConfig config) {
		// check soil
		Material materialUnder = world.getBlockState(pos.down()).getMaterial();
		if ((materialUnder != Material.ORGANIC && materialUnder != Material.EARTH) || pos.getY() >= TFGenerationSettings.MAXHEIGHT - 12) {
			return false;
		}

		// 3 block high trunk
		for (int dy = 0; dy < 4; dy++) {
			FeatureUtil.setBlockStateProvider(world, config.trunkProvider, rand, pos.up(dy));
		}

		// leaves
		putLeaves(world.getWorld(), rand, pos.up(2), false, config);
		putLeaves(world.getWorld(), rand, pos.up(3), false, config);

		// sorting engine
		world.setBlockState(pos.up(), TFBlocks.sorting_log_core.get().getDefaultState(), 3);

		return true;
	}

	private void putLeaves(World world, Random rand, BlockPos pos, boolean bushy, TFTreeFeatureConfig config) {
		for (int lx = -1; lx <= 1; lx++) {
			for (int ly = -1; ly <= 1; ly++) {
				for (int lz = -1; lz <= 1; lz++) {
					if (!bushy && Math.abs(ly) > 0 && (Math.abs(lx) + Math.abs(lz)) > 1) {
						continue;
					}
					putLeafBlock(world, pos.add(lx, ly, lz), config.leavesProvider.getBlockState(rand, pos.add(lx, ly, lz)));
				}
			}
		}
	}

	public void putLeafBlock(World world, BlockPos pos, BlockState state) {
		BlockState whatsThere = world.getBlockState(pos);

		if (whatsThere.getBlock().canBeReplacedByLeaves(whatsThere, world, pos) && whatsThere.getBlock() != state.getBlock()) {
			world.setBlockState(pos, state);
		}
	}
}
