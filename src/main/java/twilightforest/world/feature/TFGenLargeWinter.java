package twilightforest.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.World;
import net.minecraft.world.gen.IWorldGenerationReader;
import twilightforest.util.FeatureUtil;
import twilightforest.world.TFWorld;
import twilightforest.world.feature.config.TFTreeFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class TFGenLargeWinter<T extends TFTreeFeatureConfig> extends TFTreeGenerator<T> {

	public TFGenLargeWinter(Function<Dynamic<?>, T> config) {
		super(config);
	}

	@Override
	protected boolean generate(IWorldGenerationReader world, Random random, BlockPos pos, Set<BlockPos> trunk, Set<BlockPos> leaves, MutableBoundingBox mbb, T config) {
		// determine a height
		int treeHeight = 35;
		if (random.nextInt(3) == 0) {
			treeHeight += random.nextInt(10);

			if (random.nextInt(8) == 0) {
				treeHeight += random.nextInt(10);
			}
		}

		if (pos.getY() >= TFWorld.MAXHEIGHT - treeHeight) {
			return false;
		}

		// check if we're on dirt or grass
		BlockState state = world.getBlockState(pos.down());
		if (!state.getBlock().canSustainPlant(state, world, pos.down(), Direction.UP, config.getSapling())) {
			return false;
		}

		//okay build a tree!  Go up to the height
		buildTrunk(world, pos, treeHeight);

		// make leaves
		makeLeaves(world, pos, treeHeight);

		// roots!
		int numRoots = 4 + random.nextInt(3);
		float offset = random.nextFloat();
		for (int b = 0; b < numRoots; b++) {
			buildRoot(world, pos, offset, b);
		}

		return true;
	}

	private void makeLeaves(World world, BlockPos pos, int treeHeight) {
		int offGround = 3;
		int leafType = 1;

		for (int dy = 0; dy < treeHeight; dy++) {

			int radius = leafRadius(treeHeight, dy, leafType);

			FeatureUtil.makeLeafCircle2(this, world, pos.up(offGround + treeHeight - dy), radius, leafState, false);
			this.makePineBranches(world, pos.up(offGround + treeHeight - dy), radius);
		}
	}

	private void makePineBranches(World world, BlockPos pos, int radius) {
		int branchLength = radius > 4 ? radius - 1 : radius - 2;

		switch (pos.getY() % 2) {
			case 0:
				// branches
				for (int i = 1; i <= branchLength; i++) {
					this.setBlockAndNotifyAdequately(world, pos.add(-i, 0, 0), branchState.with(BlockOldLog.LOG_AXIS, BlockLog.EnumAxis.X));
					this.setBlockAndNotifyAdequately(world, pos.add(0, 0, i + 1), branchState.with(BlockOldLog.LOG_AXIS, BlockLog.EnumAxis.Z));
					this.setBlockAndNotifyAdequately(world, pos.add(i + 1, 0, 1), branchState.with(BlockOldLog.LOG_AXIS, BlockLog.EnumAxis.X));
					this.setBlockAndNotifyAdequately(world, pos.add(1, 0, -i), branchState.with(BlockOldLog.LOG_AXIS, BlockLog.EnumAxis.Z));
				}
				break;
			case 1:
				for (int i = 1; i <= branchLength; i++) {
					this.setBlockAndNotifyAdequately(world, pos.add(-1, 0, 1), branchState.with(BlockOldLog.LOG_AXIS, BlockLog.EnumAxis.X));
					this.setBlockAndNotifyAdequately(world, pos.add(1, 0, i + 1), branchState.with(BlockOldLog.LOG_AXIS, BlockLog.EnumAxis.Z));
					this.setBlockAndNotifyAdequately(world, pos.add(i + 1, 0, 0), branchState.with(BlockOldLog.LOG_AXIS, BlockLog.EnumAxis.X));
					this.setBlockAndNotifyAdequately(world, pos.add(0, 0, -i), branchState.with(BlockOldLog.LOG_AXIS, BlockLog.EnumAxis.Z));
				}
				break;
		}
	}

	private int leafRadius(int treeHeight, int dy, int functionType) {
		switch (functionType) {
			case 0:
			default:
				return (dy - 1) % 4;
			case 1:
				return (int) (4F * (float) dy / (float) treeHeight + (0.75F * dy % 3));
			case 99:
				return (treeHeight - (dy / 2) - 1) % 4; // bad
		}
	}

	private void buildTrunk(World world, BlockPos pos, int treeHeight) {
		for (int dy = 0; dy < treeHeight; dy++) {
			this.setBlockAndNotifyAdequately(world, pos.add(0, dy, 0), treeState);
			this.setBlockAndNotifyAdequately(world, pos.add(1, dy, 0), treeState);
			this.setBlockAndNotifyAdequately(world, pos.add(0, dy, 1), treeState);
			this.setBlockAndNotifyAdequately(world, pos.add(1, dy, 1), treeState);
		}
	}
}
