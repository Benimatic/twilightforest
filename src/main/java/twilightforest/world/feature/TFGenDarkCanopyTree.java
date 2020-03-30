package twilightforest.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.material.Material;
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

/**
 * Makes large trees with flat leaf ovals that provide a canopy for the forest
 *
 * @author Ben
 */
public class TFGenDarkCanopyTree<T extends TFTreeFeatureConfig> extends TFTreeGenerator<T> {

	public TFGenDarkCanopyTree(Function<Dynamic<?>, T> config) {
		super(config);
	}

	@Override
	protected boolean generate(IWorldGenerationReader worldIn, Random random, BlockPos pos, Set<BlockPos> trunk, Set<BlockPos> leaves, Set<BlockPos> branch, Set<BlockPos> root, MutableBoundingBox mbb, T config) {
		World world = (World)worldIn;

		// if we are given leaves as a starting position, seek dirt or grass underneath
		boolean foundDirt = false;
		Material materialUnder;
		for (int dy = pos.getY(); dy >= TFWorld.SEALEVEL; dy--) {
			materialUnder = world.getBlockState(new BlockPos(pos.getX(), dy - 1, pos.getZ())).getMaterial();
			if (materialUnder == Material.ORGANIC || materialUnder == Material.EARTH) {
				// yes!
				foundDirt = true;
				pos = new BlockPos(pos.getX(), dy, pos.getZ());
				break;
			} else if (materialUnder == Material.ROCK || materialUnder == Material.SAND) {
				// nope
				break;
			}
		}

		if (!foundDirt) {
			return false;
		}

		// do not grow next to another tree
		for (Direction e : Direction.Plane.HORIZONTAL) {
			if (world.getBlockState(pos.offset(e)).getMaterial() == Material.WOOD)
				return false;
		}

		// determine a height
		int treeHeight = 6 + random.nextInt(5);

		//okay build a tree!  trunk here
		FeatureUtil.drawBresehnamTree(world, pos, pos.up(treeHeight), config.trunkProvider.getBlockState(random, pos), trunk);
		leafAround(world, random, pos.up(treeHeight), leaves, config);

		// make 4 branches
		int numBranches = 4;
		double offset = random.nextFloat();
		for (int b = 0; b < numBranches; b++) {
			buildBranch(world, pos, leaves, branch, treeHeight - 3 - numBranches + (b / 2), 10 + random.nextInt(4), 0.23 * b + offset, 0.23, random, mbb, config);
		}

		// root bulb
		if (FeatureUtil.hasAirAround(world, pos.down())) {
			this.setLogBlockState(world, random, pos.down(), trunk, mbb, config);
		} else {
			this.setRootsBlockState(world, random, pos.down(), root, mbb, config);
		}

		// roots!
		int numRoots = 3 + random.nextInt(2);
		offset = random.nextDouble();
		for (int b = 0; b < numRoots; b++) {
			buildRoot(world, random, pos, root, offset, b, mbb, config);
		}

		return true;
	}

	/**
	 * Build a branch with a flat blob of leaves at the end.
	 */
	private void buildBranch(World world, BlockPos pos, Set<BlockPos> leaves, Set<BlockPos> branch, int height, double length, double angle, double tilt, Random random, MutableBoundingBox mbb, T config) {
		BlockPos src = pos.up(height);
		BlockPos dest = FeatureUtil.translate(src, length, angle, tilt);

		// only actually draw the branch if it's not going to load new chunks
		if (world.isAreaLoaded(dest, 6)) {
			FeatureUtil.drawBresehnamBranch(this, world, random, src, dest, branch, mbb, config);
			leafAround(world, random, dest, leaves, config);
		}
	}

	/**
	 * Make our leaf pattern
	 */
	private void leafAround(World world, Random random, BlockPos pos, Set<BlockPos> leaves, T config) {
		int leafSize = 4;

		// only leaf if there are no leaves by where we are thinking of leafing
		if (FeatureUtil.hasAirAround(world, pos)) {
			FeatureUtil.makeLeafCircle(world, pos.down(), leafSize, config.leavesProvider.getBlockState(random, pos), leaves, false);
			FeatureUtil.makeLeafCircle(world, pos, leafSize + 1, config.leavesProvider.getBlockState(random, pos), leaves, false);
			FeatureUtil.makeLeafCircle(world, pos.up(), leafSize, config.leavesProvider.getBlockState(random, pos), leaves, false);
			FeatureUtil.makeLeafCircle(world, pos.up(2), leafSize - 2, config.leavesProvider.getBlockState(random, pos), leaves, false);
		}
	}
}
