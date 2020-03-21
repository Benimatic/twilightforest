package twilightforest.world.feature;

import com.google.common.collect.Lists;
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

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

/**
 * Makes large trees with flat leaf ovals that provide a canopy for the forest
 *
 * @author Ben
 */
public class TFGenCanopyTree<T extends TFTreeFeatureConfig> extends TFTreeGenerator<T> {

	protected int minHeight = 20;

	private List<BlockPos> leaves = Lists.newArrayList();

	public TFGenCanopyTree(Function<Dynamic<?>, T> config) {
		super(config);
	}

	@Override
	protected boolean generate(IWorldGenerationReader world, Random random, BlockPos pos, Set<BlockPos> trunk, Set<BlockPos> leaves, MutableBoundingBox mbb, T config) {
		// determine a height
		int treeHeight = minHeight;
		if (random.nextInt(config.chanceAddFiveFirst) == 0) {
			treeHeight += random.nextInt(5);

			if (random.nextInt(config.chanceAddFiveSecond) == 0) {
				treeHeight += random.nextInt(5);
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

		leaves.clear();

		//okay build a tree!  Go up to the height
		buildBranch(world, pos, 0, treeHeight, 0, 0, true, random);

		// make 3-4 branches
		int numBranches = 3 + random.nextInt(2);
		float offset = random.nextFloat();
		for (int b = 0; b < numBranches; b++) {
			buildBranch(world, pos, treeHeight - 10 + b, 9, 0.3 * b + offset, 0.2, false, random);
		}

		// add the actual leaves
		if (config.hasLeaves)
			for (BlockPos leafPos : leaves) {
				makeLeafBlob(world, leafPos);
			}

		// root bulb
		if (FeatureUtil.hasAirAround(world, pos.down())) {
			this.setLogBlockState(world, random, pos.down(), trunk, mbb, config);
		} else {
			this.setBlockAndNotifyAdequately(world, pos.down(), rootState);
		}

		// roots!
		int numRoots = 3 + random.nextInt(2);
		offset = random.nextFloat();
		for (int b = 0; b < numRoots; b++) {
			buildRoot(world, pos, offset, b);
		}

		return true;
	}

//	@Override
//	public boolean generate(World world, Random random, BlockPos pos) {
//		return generate(world, random, pos, true);
//	}

	private void makeLeafBlob(World world, BlockPos leafPos) {
		FeatureUtil.makeLeafCircle(this, world, leafPos.down(), 3, leafState, true);
		FeatureUtil.makeLeafCircle(this, world, leafPos, 4, leafState, true);
		FeatureUtil.makeLeafCircle(this, world, leafPos.up(), 2, leafState, true);
	}

	/**
	 * Build a branch with a flat blob of leaves at the end.
	 */
	void buildBranch(World world, BlockPos pos, int height, double length, double angle, double tilt, boolean trunk, Random treeRNG) {
		BlockPos src = pos.up(height);
		BlockPos dest = FeatureUtil.translate(src, length, angle, tilt);

		// only actually draw the branch if it's not going to load new chunks
		if (world.isAreaLoaded(dest, 5)) {

			FeatureUtil.drawBresehnam(world, src, dest, trunk ? treeState : branchState);

			// seems to help lighting to place this firefly now
			if (trunk) {
				// add a firefly (torch) to the trunk
				addFirefly(world, pos, 3 + treeRNG.nextInt(7), treeRNG.nextDouble());
			}

			setBlockAndNotifyAdequately(world, dest.east(), branchState);
			setBlockAndNotifyAdequately(world, dest.west(), branchState);
			setBlockAndNotifyAdequately(world, dest.south(), branchState);
			setBlockAndNotifyAdequately(world, dest.north(), branchState);

			// save leaf position for later
			this.leaves.add(dest);
		}
	}
}
