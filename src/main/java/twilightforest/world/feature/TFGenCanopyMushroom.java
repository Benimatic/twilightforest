package twilightforest.world.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
 * Makes large mushrooms with flat mushroom tops that provide a canopy for the forest
 *
 * @author Ben
 */
public class TFGenCanopyMushroom<T extends TFTreeFeatureConfig> extends TFTreeGenerator<T> {

//	public TFGenCanopyMushroom() {
//		this(false);
//	}
//
//	public TFGenCanopyMushroom(boolean notify) {
//		super(notify);
//		treeState   = Blocks.RED_MUSHROOM_BLOCK.getDefaultState().with(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.STEM);
//		branchState = Blocks.RED_MUSHROOM_BLOCK.getDefaultState().with(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.ALL_STEM);
//		leafState   = Blocks.RED_MUSHROOM_BLOCK.getDefaultState().with(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.CENTER);
//		source = Blocks.RED_MUSHROOM;
//	}

	public TFGenCanopyMushroom(Function<Dynamic<?>, T> config) {
		super(config);
	}

	@Override
	protected boolean generate(IWorldGenerationReader world, Random random, BlockPos pos, Set<BlockPos> trunk, Set<BlockPos> leaves, MutableBoundingBox mbb, T config) {
		// determine a height
		int treeHeight = 12;
		if (random.nextInt(3) == 0) {
			treeHeight += random.nextInt(5);

			if (random.nextInt(8) == 0) {
				treeHeight += random.nextInt(5);
			}
		}

		if (pos.getY() >= TFWorld.MAXHEIGHT - treeHeight - 1) {
			return false;
		}

		// check if we're on dirt or grass
		Block blockUnder = world.getBlockState(pos.down()).getBlock();
		if (blockUnder != Blocks.GRASS && blockUnder != Blocks.DIRT && blockUnder != Blocks.MYCELIUM) {
			return false;
		}

		BlockState baseState = (random.nextInt(3) == 0 ? Blocks.RED_MUSHROOM_BLOCK : Blocks.BROWN_MUSHROOM_BLOCK).getDefaultState();

		this.treeState   = baseState.with(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.STEM);
		this.branchState = baseState.with(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.ALL_STEM);
		this.leafState   = baseState.with(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.CENTER);

		//okay build a tree!  Go up to the height
		buildBranch(world, pos, 0, treeHeight, 0, 0, true, random);

		// make 3-4 branches
		int numBranches = 3 + random.nextInt(2);
		double offset = random.nextDouble();
		for (int b = 0; b < numBranches; b++) {
			buildBranch(world, pos, treeHeight - 5 + b, 9, 0.3 * b + offset, 0.2, false, random);
		}

		return true;
	}

	/**
	 * Build a branch with a flat blob of leaves at the end.
	 *
	 * @param height
	 * @param length
	 * @param angle
	 * @param tilt
	 */
	private void buildBranch(World world, BlockPos pos, int height, double length, double angle, double tilt, boolean trunk, Random treeRNG) {
		BlockPos src = pos.up(height);
		BlockPos dest = FeatureUtil.translate(src, length, angle, tilt);

		// only actually draw the branch if it's not going to load new chunks
		if (world.isAreaLoaded(dest, 5)) {
			if (src.getX() != dest.getX() || src.getZ() != dest.getZ()) {
				// branch
				FeatureUtil.drawBresehnam(this, world, src, new BlockPos(dest.getX(), src.getY(), dest.getZ()), branchState);
				FeatureUtil.drawBresehnam(this, world, new BlockPos(dest.getX(), src.getY() + 1, dest.getZ()), dest.down(), treeState);
			} else {
				// trunk
				FeatureUtil.drawBresehnam(this, world, src, dest.down(), treeState);
			}

			if (trunk) {
				// add a firefly (torch) to the trunk
				addFirefly(world, pos, 3 + treeRNG.nextInt(7), treeRNG.nextDouble());
			}

			drawMushroomCircle(world, dest, 4, leafState);
		}
	}

	/**
	 * Draw a flat blob (a circle?) of mushroom pieces
	 * <p>
	 * This assumes that the baseState you've passed in is the center variant
	 */
	private void drawMushroomCircle(World world, BlockPos pos, int rad, BlockState baseState) {
		// trace out a quadrant
		for (byte dx = 0; dx <= rad; dx++) {
			for (byte dz = 0; dz <= rad; dz++) {
				int dist = (int) (Math.max(dx, dz) + (Math.min(dx, dz) * 0.5));

				//hack!  I keep getting failing leaves at a certain position.
				if (dx == 3 && dz == 3) {
					dist = 6;
				}

				// if we're inside the blob, fill it
				if (dx == 0) {
					// two!
					if (dz < rad) {
						setBlockAndNotifyAdequately(world, pos.add(0, 0, dz), baseState);
						setBlockAndNotifyAdequately(world, pos.add(0, 0, -dz), baseState);
					} else {
						setBlockAndNotifyAdequately(world, pos.add(0, 0, dz), baseState.with(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.SOUTH));
						setBlockAndNotifyAdequately(world, pos.add(0, 0, -dz), baseState.with(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.NORTH));
					}
				} else if (dz == 0) {
					// two!
					if (dx < rad) {
						setBlockAndNotifyAdequately(world, pos.add(dx, 0, 0), baseState);
						setBlockAndNotifyAdequately(world, pos.add(-dx, 0, 0), baseState);
					} else {
						setBlockAndNotifyAdequately(world, pos.add(dx, 0, 0), baseState.with(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.EAST));
						setBlockAndNotifyAdequately(world, pos.add(-dx, 0, 0), baseState.with(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.WEST));
					}
				} else if (dist < rad) {
					// do four at a time for easiness!
					setBlockAndNotifyAdequately(world, pos.add(dx, 0, dz), baseState);
					setBlockAndNotifyAdequately(world, pos.add(dx, 0, -dz), baseState);
					setBlockAndNotifyAdequately(world, pos.add(-dx, 0, dz), baseState);
					setBlockAndNotifyAdequately(world, pos.add(-dx, 0, -dz), baseState);
				} else if (dist == rad) {
					// do four at a time for easiness!
					setBlockAndNotifyAdequately(world, pos.add(dx, 0, dz), baseState.with(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.SOUTH_EAST));
					setBlockAndNotifyAdequately(world, pos.add(dx, 0, -dz), baseState.with(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.NORTH_EAST));
					setBlockAndNotifyAdequately(world, pos.add(-dx, 0, dz), baseState.with(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.SOUTH_WEST));
					setBlockAndNotifyAdequately(world, pos.add(-dx, 0, -dz), baseState.with(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.NORTH_WEST));
				}
			}
		}
	}
}
