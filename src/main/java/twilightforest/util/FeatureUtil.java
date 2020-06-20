package twilightforest.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import twilightforest.world.feature.TFTreeGenerator;
import twilightforest.world.feature.config.TFTreeFeatureConfig;

import java.util.Random;
import java.util.Set;

public class FeatureUtil {

	/**
	 * Moves distance along the vector.
	 * <p>
	 * This goofy function takes a float between 0 and 1 for the angle, where 0 is 0 degrees, .5 is 180 degrees and 1 and 360 degrees.
	 * For the tilt, it takes a float between 0 and 1 where 0 is straight up, 0.5 is straight out and 1 is straight down.
	 */
	public static BlockPos translate(BlockPos pos, double distance, double angle, double tilt) {
		double rangle = angle * 2.0D * Math.PI;
		double rtilt = tilt * Math.PI;

		return pos.add(
				Math.round(Math.sin(rangle) * Math.sin(rtilt) * distance),
				Math.round(Math.cos(rtilt) * distance),
				Math.round(Math.cos(rangle) * Math.sin(rtilt) * distance)
		);
	}

	/**
	 * Draws a line from {x1, y1, z1} to {x2, y2, z2}
	 * This takes all variables for setting Branch
	 */
	public static void drawBresehnamBranch(TFTreeGenerator generator, World world, Random random, BlockPos from, BlockPos to, Set<BlockPos> state, MutableBoundingBox mbb, TFTreeFeatureConfig config) {
		for (BlockPos pixel : getBresehnamArrays(from, to)) {
			generator.setBranchBlockState(world, random, pixel, state, mbb, config);
			//world.setBlockState(pixel, state);
		}
	}

	/**
	 * Draws a line from {x1, y1, z1} to {x2, y2, z2}
	 * This just takes a BlockState, used to set Trunk
	 */
	public static void drawBresehnamTree(World world, BlockPos from, BlockPos to, BlockState state, Set<BlockPos> treepos) {
		for (BlockPos pixel : getBresehnamArrays(from, to)) {
			world.setBlockState(pixel, state);
			treepos.add(pixel.toImmutable());
		}
	}

	/**
	 * Get an array of values that represent a line from point A to point B
	 */
	public static BlockPos[] getBresehnamArrays(BlockPos src, BlockPos dest) {
		return getBresehnamArrays(src.getX(), src.getY(), src.getZ(), dest.getX(), dest.getY(), dest.getZ());
	}

	/**
	 * Get an array of values that represent a line from point A to point B
	 * todo 1.9 lazify this into an iterable?
	 */
	public static BlockPos[] getBresehnamArrays(int x1, int y1, int z1, int x2, int y2, int z2) {
		int i, dx, dy, dz, absDx, absDy, absDz, x_inc, y_inc, z_inc, err_1, err_2, doubleAbsDx, doubleAbsDy, doubleAbsDz;

		BlockPos pixel = new BlockPos(x1, y1, z1);
		BlockPos lineArray[];

		dx = x2 - x1;
		dy = y2 - y1;
		dz = z2 - z1;
		x_inc = (dx < 0) ? -1 : 1;
		absDx = Math.abs(dx);
		y_inc = (dy < 0) ? -1 : 1;
		absDy = Math.abs(dy);
		z_inc = (dz < 0) ? -1 : 1;
		absDz = Math.abs(dz);
		doubleAbsDx = absDx << 1;
		doubleAbsDy = absDy << 1;
		doubleAbsDz = absDz << 1;

		if ((absDx >= absDy) && (absDx >= absDz)) {
			err_1 = doubleAbsDy - absDx;
			err_2 = doubleAbsDz - absDx;
			lineArray = new BlockPos[absDx + 1];
			for (i = 0; i < absDx; i++) {
				lineArray[i] = pixel;
				if (err_1 > 0) {
					pixel = pixel.up(y_inc);
					err_1 -= doubleAbsDx;
				}
				if (err_2 > 0) {
					pixel = pixel.south(z_inc);
					err_2 -= doubleAbsDx;
				}
				err_1 += doubleAbsDy;
				err_2 += doubleAbsDz;
				pixel = pixel.east(x_inc);
			}
		} else if ((absDy >= absDx) && (absDy >= absDz)) {
			err_1 = doubleAbsDx - absDy;
			err_2 = doubleAbsDz - absDy;
			lineArray = new BlockPos[absDy + 1];
			for (i = 0; i < absDy; i++) {
				lineArray[i] = pixel;
				if (err_1 > 0) {
					pixel = pixel.east(x_inc);
					err_1 -= doubleAbsDy;
				}
				if (err_2 > 0) {
					pixel = pixel.south(z_inc);
					err_2 -= doubleAbsDy;
				}
				err_1 += doubleAbsDx;
				err_2 += doubleAbsDz;
				pixel = pixel.up(y_inc);
			}
		} else {
			err_1 = doubleAbsDy - absDz;
			err_2 = doubleAbsDx - absDz;
			lineArray = new BlockPos[absDz + 1];
			for (i = 0; i < absDz; i++) {
				lineArray[i] = pixel;
				if (err_1 > 0) {
					pixel = pixel.up(y_inc);
					err_1 -= doubleAbsDz;
				}
				if (err_2 > 0) {
					pixel = pixel.east(x_inc);
					err_2 -= doubleAbsDz;
				}
				err_1 += doubleAbsDy;
				err_2 += doubleAbsDx;
				pixel = pixel.south(z_inc);
			}
		}
		lineArray[lineArray.length - 1] = pixel;

		return lineArray;
	}

	/**
	 * Draw a flat blob (circle) of leaves
	 */
	public static void makeLeafCircle(World world, BlockPos pos, int rad, BlockState state, Set<BlockPos> leaves, boolean useHack) {
		// trace out a quadrant
		for (byte dx = 0; dx <= rad; dx++) {
			for (byte dz = 0; dz <= rad; dz++) {
				int dist = Math.max(dx, dz) + (Math.min(dx, dz) >> 1);

				//hack!  I keep getting failing leaves at a certain position.
				if (useHack && dx == 3 && dz == 3) {
					dist = 6;
				}

				// if we're inside the blob, fill it
				if (dist <= rad) {
					// do four at a time for easiness!
					putLeafBlock(world, pos.add(+dx, 0, +dz), state, leaves);
					putLeafBlock(world, pos.add(+dx, 0, -dz), state, leaves);
					putLeafBlock(world, pos.add(-dx, 0, +dz), state, leaves);
					putLeafBlock(world, pos.add(-dx, 0, -dz), state, leaves);
				}
			}
		}
	}

	/**
	 * Put a leaf only in spots where leaves can go!
	 */
	public static void putLeafBlock(World world, BlockPos pos, BlockState state, Set<BlockPos> leavespos) {
		BlockState whatsThere = world.getBlockState(pos);

		if (whatsThere.canBeReplacedByLeaves(world, pos) && whatsThere.getBlock() != state.getBlock()) {
			world.setBlockState(pos, state);
			leavespos.add(pos.toImmutable());
		}
	}

	/**
	 * Draw a flat blob (circle) of leaves.  This one makes it offset to surround a 2x2 area instead of a 1 block area
	 */
	// TODO: Parameter "useHack" is unused. Is it worth keeping? -Androsa
	public static void makeLeafCircle2(World world, BlockPos pos, int rad, BlockState state, Set<BlockPos> leaves,  boolean useHack) {
		// trace out a quadrant
		for (byte dx = 0; dx <= rad; dx++) {
			for (byte dz = 0; dz <= rad; dz++) {
//				int dist = Math.max(dx, dz) + (int)(Math.min(dx, dz) * 0.6F);
//
//				//hack!  I keep getting failing leaves at a certain position.
//				if (useHack && dx == 3 && dz == 3) {
//					dist = 6;
//				}

				// if we're inside the blob, fill it
				if (dx * dx + dz * dz <= rad * rad) {
					// do four at a time for easiness!
					putLeafBlock(world, pos.add(1 + dx, 0, 1 + dz), state, leaves);
					putLeafBlock(world, pos.add(1 + dx, 0, -dz), state, leaves);
					putLeafBlock(world, pos.add(-dx, 0, 1 + dz), state, leaves);
					putLeafBlock(world, pos.add(-dx, 0, -dz), state, leaves);
				}
			}
		}
	}

	/**
	 * Gets either cobblestone or mossy cobblestone, randomly.  Used for ruins.
	 */
	public static BlockState randStone(Random rand, int howMuch) {
		return rand.nextInt(howMuch) >= 1 ? Blocks.COBBLESTONE.getDefaultState() : Blocks.MOSSY_COBBLESTONE.getDefaultState();
	}

	/**
	 * Checks an area to see if it consists of flat natural ground below and air above
	 */
	// TODO: Parameter "rand" is unused. Is it worth keeping? -Androsa
	public static boolean isAreaSuitable(IWorld world, Random rand, BlockPos pos, int width, int height, int depth) {
		boolean flag = true;

		// check if there's anything within the diameter
		for (int cx = 0; cx < width; cx++) {
			for (int cz = 0; cz < depth; cz++) {
				BlockPos pos_ = pos.add(cx, 0, cz);
				// check if the blocks even exist?
				if (world.isBlockLoaded(pos_)) {
					// is there grass, dirt or stone below?
					Material m = world.getBlockState(pos_.down()).getMaterial();
					if (m != Material.EARTH && m != Material.ORGANIC && m != Material.ROCK) {
						flag = false;
					}

					for (int cy = 0; cy < height; cy++) {
						// blank space above?
						if (!world.isAirBlock(pos_.up(cy))) {
							flag = false;
						}
					}
				} else {
					flag = false;
				}
			}
		}

		// Okie dokie
		return flag;
	}

	/**
	 * Draw a giant blob of whatevs.
	 */
	public static void drawBlob(World world, BlockPos pos, int rad, BlockState state) {
		// then trace out a quadrant
		for (byte dx = 0; dx <= rad; dx++) {
			for (byte dy = 0; dy <= rad; dy++) {
				for (byte dz = 0; dz <= rad; dz++) {
					// determine how far we are from the center.
					int dist = 0;
					if (dx >= dy && dx >= dz) {
						dist = dx + (Math.max(dy, dz) >> 1) + (Math.min(dy, dz) >> 2);
					} else if (dy >= dx && dy >= dz) {
						dist = dy + (Math.max(dx, dz) >> 1) + (Math.min(dx, dz) >> 2);
					} else {
						dist = dz + (Math.max(dx, dy) >> 1) + (Math.min(dx, dy) >> 2);
					}


					// if we're inside the blob, fill it
					if (dist <= rad) {
						// do eight at a time for easiness!
						world.setBlockState(pos.add(+dx, +dy, +dz), state);
						world.setBlockState(pos.add(+dx, +dy, -dz), state);
						world.setBlockState(pos.add(-dx, +dy, +dz), state);
						world.setBlockState(pos.add(-dx, +dy, -dz), state);
						world.setBlockState(pos.add(+dx, -dy, +dz), state);
						world.setBlockState(pos.add(+dx, -dy, -dz), state);
						world.setBlockState(pos.add(-dx, -dy, +dz), state);
						world.setBlockState(pos.add(-dx, -dy, -dz), state);
					}
				}
			}
		}
	}

	/**
	 * Draw a giant blob of leaves.
	 */
	public static void drawLeafBlob(World world, BlockPos pos, int rad, BlockState state, Set<BlockPos> leaves) {
		// then trace out a quadrant
		for (byte dx = 0; dx <= rad; dx++) {
			for (byte dy = 0; dy <= rad; dy++) {
				for (byte dz = 0; dz <= rad; dz++) {
					// determine how far we are from the center.
					int dist = 0;
					if (dx >= dy && dx >= dz) {
						dist = dx + (Math.max(dy, dz) >> 1) + (Math.min(dy, dz) >> 2);
					} else if (dy >= dx && dy >= dz) {
						dist = dy + (Math.max(dx, dz) >> 1) + (Math.min(dx, dz) >> 2);
					} else {
						dist = dz + (Math.max(dx, dy) >> 1) + (Math.min(dx, dy) >> 2);
					}

					// if we're inside the blob, fill it
					if (dist <= rad) {
						// do eight at a time for easiness!
						putLeafBlock(world, pos.add(+dx, +dy, +dz), state, leaves);
						putLeafBlock(world, pos.add(+dx, +dy, -dz), state, leaves);
						putLeafBlock(world, pos.add(-dx, +dy, +dz), state, leaves);
						putLeafBlock(world, pos.add(-dx, +dy, -dz), state, leaves);
						putLeafBlock(world, pos.add(+dx, -dy, +dz), state, leaves);
						putLeafBlock(world, pos.add(+dx, -dy, -dz), state, leaves);
						putLeafBlock(world, pos.add(-dx, -dy, +dz), state, leaves);
						putLeafBlock(world, pos.add(-dx, -dy, -dz), state, leaves);
					}
				}
			}
		}
	}

	/**
	 * Does the block have only air blocks adjacent
	 */
	public static boolean surroundedByAir(IWorldReader world, BlockPos pos) {
		for (Direction e : Direction.values()) {
			if (!world.isAirBlock(pos.offset(e))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Does the block have at least 1 air block adjacent
	 */
	public static boolean hasAirAround(World world, BlockPos pos) {
		for (Direction e : Direction.values()) {
			if (e == Direction.DOWN)
				continue; // todo 1.9 was in old logic
			if (world.isBlockLoaded(pos.offset(e))
					&& world.isAirBlock(pos.offset(e))) {
				return true;
			}
		}

		return false;
	}

	public static boolean isNearSolid(IWorldReader world, BlockPos pos) {
		for (Direction e : Direction.values()) {
			if (world.isBlockLoaded(pos.offset(e))
					&& world.getBlockState(pos.offset(e)).getMaterial().isSolid()) {
				return true;
			}
		}

		return false;
	}

	public static void setBlockStateProvider(IWorld world, BlockStateProvider provider, Random rand, BlockPos pos) {
		world.setBlockState(pos, provider.getBlockState(rand, pos), 3);
	}
}
