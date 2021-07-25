package twilightforest.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.LevelAccessor;
import twilightforest.block.TFBlocks;
import twilightforest.util.FeatureUtil;
import twilightforest.world.TFGenerationSettings;
import twilightforest.world.feature.config.TFTreeFeatureConfig;

import java.util.Random;
import java.util.Set;

public class TFGenMinersTree extends TFTreeGenerator<TFTreeFeatureConfig> {

	public TFGenMinersTree(Codec<TFTreeFeatureConfig> config) {
		super(config);
	}

	@Override
	protected boolean generate(LevelAccessor world, Random rand, BlockPos pos, Set<BlockPos> trunk, Set<BlockPos> leaves, Set<BlockPos> branch, Set<BlockPos> root, BoundingBox mbb, TFTreeFeatureConfig config) {
		if (pos.getY() >= TFGenerationSettings.MAXHEIGHT - 12) {
			return false;
		}

		// check soil
		BlockState state = world.getBlockState(pos.below());
		if (!state.getBlock().canSustainPlant(state, world, pos.below(), Direction.UP, config.getSapling(rand, pos))) {
			return false;
		}

		// 9 block high trunk
		for (int dy = 0; dy <= 9; dy++) {
			setLogBlockState(world, rand, pos.above(dy), trunk, mbb, config);
		}

		// branches with leaf blocks
		putBranchWithLeaves(world, rand, pos.offset(0, 9, 1), leaves, branch, true, mbb, config);
		putBranchWithLeaves(world, rand, pos.offset(0, 9, 2), leaves, branch, false, mbb, config);
		putBranchWithLeaves(world, rand, pos.offset(0, 8, 3), leaves, branch, false, mbb, config);
		putBranchWithLeaves(world, rand, pos.offset(0, 7, 4), leaves, branch, false, mbb, config);
		putBranchWithLeaves(world, rand, pos.offset(0, 6, 5), leaves, branch, false, mbb, config);

		putBranchWithLeaves(world, rand, pos.offset(0, 9, -1), leaves, branch, true, mbb, config);
		putBranchWithLeaves(world, rand, pos.offset(0, 9, -2), leaves, branch, false, mbb, config);
		putBranchWithLeaves(world, rand, pos.offset(0, 8, -3), leaves, branch, false, mbb, config);
		putBranchWithLeaves(world, rand, pos.offset(0, 7, -4), leaves, branch, false, mbb, config);
		putBranchWithLeaves(world, rand, pos.offset(0, 6, -5), leaves, branch, false, mbb, config);

		// place minewood core
		world.setBlock(pos.above(), TFBlocks.mining_log_core.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y), 3);
		world.getBlockTicks().scheduleTick(pos.above(), TFBlocks.mining_log_core.get(), 20);

		// root bulb
		if (FeatureUtil.hasAirAround(world, pos.below())) {
			this.setLogBlockState(world, rand, pos.below(), trunk, mbb, config);
		} else {
			this.setRootsBlockState(world, rand, pos.below(), root, mbb, config);
		}

		// roots!
		int numRoots = 3 + rand.nextInt(2);
		double offset = rand.nextDouble();
		for (int b = 0; b < numRoots; b++) {
			buildRoot(world, rand, pos, root, offset, b, mbb, config);
		}

		return true;
	}

	protected void putBranchWithLeaves(LevelAccessor world, Random rand, BlockPos pos, Set<BlockPos> leaves, Set<BlockPos> branch, boolean bushy, BoundingBox mbb, TFTreeFeatureConfig config) {
		setBranchBlockState(world, rand, pos, branch, mbb, config);

		for (int lx = -1; lx <= 1; lx++) {
			for (int ly = -1; ly <= 1; ly++) {
				for (int lz = -1; lz <= 1; lz++) {
					if (!bushy && Math.abs(ly) > 0 && Math.abs(lx) > 0) {
						continue;
					}
					FeatureUtil.putLeafBlock(world, pos.offset(lx, ly, lz), config.leavesProvider.getState(rand, pos.offset(lx, ly, lz)), leaves);
				}
			}
		}
	}
}
