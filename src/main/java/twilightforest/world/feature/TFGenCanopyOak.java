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

public class TFGenCanopyOak<T extends TFTreeFeatureConfig> extends TFGenCanopyTree<T> {

	private List<BlockPos> leaves = Lists.newArrayList();

	public TFGenCanopyOak(Function<Dynamic<?>, T> config) {
		super(config);
	}

	@Override
	protected boolean generate(IWorldGenerationReader world, Random random, BlockPos pos, Set<BlockPos> trunk, Set<BlockPos> leaves, MutableBoundingBox mbb, T config) {
		// determine a height
		int treeHeight = minHeight;
		if (random.nextInt(chanceAddFirstFive) == 0) {
			treeHeight += random.nextInt(5);

			if (random.nextInt(chanceAddSecondFive) == 0) {
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

		this.leaves.clear();

		//okay build a tree!  Go up to the height
		buildTrunk(world, random, pos, trunk, treeHeight, mbb, config);

		// make 12 - 20 branches
		int numBranches = 12 + random.nextInt(9);
		float bangle = random.nextFloat();
		for (int b = 0; b < numBranches; b++) {
			float btilt = 0.15F + (random.nextFloat() * 0.35F);
			buildBranch(world, pos, treeHeight - 10 + (b / 2), 5, bangle, btilt, false, random);

			bangle += (random.nextFloat() * 0.4F);
			if (bangle > 1.0F) {
				bangle -= 1.0F;
			}
		}

		// add the actual leaves
		for (BlockPos leafPos : leaves) {
			makeLeafBlob(world, leafPos);
		}

		makeRoots(world, random, pos);
		makeRoots(world, random, pos.east());
		makeRoots(world, random, pos.south());
		makeRoots(world, random, pos.east().south());

		return true;
	}

	private void makeLeafBlob(World world, BlockPos leafPos) {
		FeatureUtil.drawLeafBlob(this, world, leafPos, 2, leafState);
	}

	private void makeRoots(World world, Random random, BlockPos pos) {
		// root bulb
		if (FeatureUtil.hasAirAround(world, pos.down())) {
			this.setBlockAndNotifyAdequately(world, pos.down(), treeState);
		} else {
			this.setBlockAndNotifyAdequately(world, pos.down(), rootState);
		}

		// roots!
		int numRoots = 1 + random.nextInt(2);
		float offset = random.nextFloat();
		for (int b = 0; b < numRoots; b++) {
			buildRoot(world, pos, offset, b);
		}
	}

	private void buildTrunk(IWorldGenerationReader world, Random rand, BlockPos pos, Set<BlockPos> trunk, int treeHeight, MutableBoundingBox mbb, T config) {
		for (int dy = 0; dy < treeHeight; dy++) {
			this.setLogBlockState(world, rand, pos.add(0, dy, 0), trunk, mbb, config);
			this.setLogBlockState(world, rand, pos.add(1, dy, 0), trunk, mbb, config);
			this.setLogBlockState(world, rand, pos.add(0, dy, 1), trunk, mbb, config);
			this.setLogBlockState(world, rand, pos.add(1, dy, 1), trunk, mbb, config);
		}

		this.leaves.add(pos.add(0, treeHeight, 0));
	}

	/**
	 * Build a branch with a flat blob of leaves at the end.
	 */
	@Override
	void buildBranch(World world, BlockPos pos, int height, double length, double angle, double tilt, boolean trunk, Random treeRNG) {
		BlockPos src = pos.up(height);
		BlockPos dest = FeatureUtil.translate(src, length, angle, tilt);

		// constrain branch spread
		int limit = 5;
		if ((dest.getX() - pos.getX()) < -limit) {
			dest = new BlockPos(pos.getX() - limit, dest.getY(), dest.getZ());
		}
		if ((dest.getX() - pos.getX()) > limit) {
			dest = new BlockPos(pos.getX() + limit, dest.getY(), dest.getZ());
		}
		if ((dest.getZ() - pos.getZ()) < -limit) {
			dest = new BlockPos(dest.getX(), dest.getY(), pos.getZ() - limit);
		}
		if ((dest.getZ() - pos.getZ()) > limit) {
			dest = new BlockPos(dest.getX(), dest.getY(), pos.getZ() + limit);
		}

		FeatureUtil.drawBresehnam(world, src, dest, trunk ? treeState : branchState);

		setBlockAndNotifyAdequately(world, dest.east(), branchState);
		setBlockAndNotifyAdequately(world, dest.west(), branchState);
		setBlockAndNotifyAdequately(world, dest.north(), branchState);
		setBlockAndNotifyAdequately(world, dest.south(), branchState);

		this.leaves.add(dest);
	}
}