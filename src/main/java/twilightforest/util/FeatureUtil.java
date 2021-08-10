package twilightforest.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.material.Material;
import twilightforest.block.TFBlocks;
import twilightforest.world.feature.TFTreeGenerator;
import twilightforest.world.feature.config.TFTreeFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

// TODO Split into FeatureLogic and FeaturePlacers
//  jesus christ
public class FeatureUtil {
	/**
	 * Draws a line from {x1, y1, z1} to {x2, y2, z2}
	 * This takes all variables for setting Branch
	 */
	public static void drawBresenhamBranch(TFTreeGenerator<? extends TFTreeFeatureConfig> generator, LevelAccessor world, BiConsumer<BlockPos, BlockState> trunkPlacer, Random random, BlockPos from, BlockPos to, TFTreeFeatureConfig config) {
		for (BlockPos pixel : getBresenhamArrays(from, to)) {
			generator.setBranchBlockState(world, trunkPlacer, random, pixel, config);
		}
	}

	/**
	 * Draws a line from {x1, y1, z1} to {x2, y2, z2}
	 * This just takes a BlockState, used to set Trunk
	 */
	public static void drawBresenhamTree(BiConsumer<BlockPos, BlockState> trunkPlacer, BlockPos from, BlockPos to, BlockState state) {
		for (BlockPos pixel : getBresenhamArrays(from, to)) {
			trunkPlacer.accept(pixel, state);
		}
	}

	private static final Predicate<BlockState> IS_AIR = BlockBehaviour.BlockStateBase::isAir;
	public static boolean hasEmptyNeighbor(LevelSimulatedReader worldReader, BlockPos pos) {
		return worldReader.isStateAtPosition(pos.above(), IS_AIR)
				|| worldReader.isStateAtPosition(pos.north(), IS_AIR)
				|| worldReader.isStateAtPosition(pos.south(), IS_AIR)
				|| worldReader.isStateAtPosition(pos.west(), IS_AIR)
				|| worldReader.isStateAtPosition(pos.east(), IS_AIR)
				|| worldReader.isStateAtPosition(pos.below(), IS_AIR);
	}

	// Slight stretch of logic: We check if the block is completely surrounded by air.
	// If it's not completely surrounded by air, then there's a solid
	public static boolean hasSolidNeighbor(LevelSimulatedReader worldReader, BlockPos pos) {
		return !(worldReader.isStateAtPosition(pos.below(), IS_AIR)
				&& worldReader.isStateAtPosition(pos.north(), IS_AIR)
				&& worldReader.isStateAtPosition(pos.south(), IS_AIR)
				&& worldReader.isStateAtPosition(pos.west(), IS_AIR)
				&& worldReader.isStateAtPosition(pos.east(), IS_AIR)
				&& worldReader.isStateAtPosition(pos.above(), IS_AIR));
	}

	public static boolean canRootGrowIn(LevelSimulatedReader worldReader, BlockPos pos) {
		if (worldReader.isStateAtPosition(pos, IS_AIR)) {
			// roots can grow through air if they are near a solid block
			return hasSolidNeighbor(worldReader, pos);
		} else {
			return worldReader.isStateAtPosition(pos, FeatureUtil::canRootReplace);
		}
	}

	public static boolean canRootReplace(BlockState state) {
		Block block = state.getBlock();

		return /*(state.getDestroySpeed() >= 0) //
				&&*/ block != TFBlocks.stronghold_shield.get()
				&& block != TFBlocks.trophy_pedestal.get()
				&& block != TFBlocks.boss_spawner_naga.get()
				&& block != TFBlocks.boss_spawner_lich.get()
				&& block != TFBlocks.boss_spawner_hydra.get()
				&& block != TFBlocks.boss_spawner_ur_ghast.get()
				&& block != TFBlocks.boss_spawner_knight_phantom.get()
				&& block != TFBlocks.boss_spawner_snow_queen.get()
				&& block != TFBlocks.boss_spawner_minoshroom.get()
				&& block != TFBlocks.boss_spawner_alpha_yeti.get()
				&& (state.getMaterial() == Material.GRASS || state.getMaterial() == Material.DIRT || state.getMaterial() == Material.STONE || state.getMaterial() == Material.WATER);
	}

	public static void putLeafBlock(BiConsumer<BlockPos, BlockState> worldPlacer, BlockPos pos, BlockStateProvider state, Random random) {
		worldPlacer.accept(pos, state.getState(random, pos));
	}

	// TODO phase out other leaf circle algorithms
	public static void makeLeafCircle(BiConsumer<BlockPos, BlockState> worldPlacer, Random random, BlockPos centerPos, float radius, BlockStateProvider state) {
		// Normally, I'd use mutable pos here but there are multiple bits of logic down the line that force
		// the pos to be immutable causing multiple same BlockPos instances to exist.
		float radiusSquared = radius * radius;
		putLeafBlock(worldPlacer, centerPos, state, random);

		// trace out a quadrant
		for (int x = 0; x <= radius; x++) {
			for (int z = 1; z <= radius; z++) {
				// if we're inside the blob, fill it
				if (x * x + z * z <= radiusSquared) {
					// do four at a time for easiness!
					putLeafBlock(worldPlacer, centerPos.offset(  x, 0,  z), state, random);
					putLeafBlock(worldPlacer, centerPos.offset( -x, 0, -z), state, random);
					putLeafBlock(worldPlacer, centerPos.offset( -z, 0,  x), state, random);
					putLeafBlock(worldPlacer, centerPos.offset(  z, 0, -x), state, random);
					// Confused how this circle pixel-filling algorithm works exactly? https://www.desmos.com/calculator/psqynhk21k
				}
			}
		}
	}

	// Same as above except for even-sized trunks
	public static void makeLeafCircle2(BiConsumer<BlockPos, BlockState> worldPlacer, Random random, BlockPos centerPos, float radius, BlockStateProvider state) {
		// Normally, I'd use mutable pos here but there are multiple bits of logic down the line that force
		// the pos to be immutable causing multiple same BlockPos instances to exist.
		float radiusSquared = radius * radius;
		putLeafBlock(worldPlacer, centerPos, state, random);

		// trace out a quadrant
		for (int x = 0; x <= radius; x++) {
			for (int z = 1; z <= radius; z++) {
				// if we're inside the blob, fill it
				if (x * x + z * z <= radiusSquared) {
					// do four at a time for easiness!
					putLeafBlock(worldPlacer, centerPos.offset( 1+x, 0, 1+z), state, random);
					putLeafBlock(worldPlacer, centerPos.offset( -x, 0, -z), state, random);
					putLeafBlock(worldPlacer, centerPos.offset( -x, 0, 1+z), state, random);
					putLeafBlock(worldPlacer, centerPos.offset( 1+x, 0, -z), state, random);
					// Confused how this circle pixel-filling algorithm works exactly? https://www.desmos.com/calculator/psqynhk21k
				}
			}
		}
	}

	public static void makeLeafSpheroid(BiConsumer<BlockPos, BlockState> worldPlacer, Random random, BlockPos centerPos, float xzRadius, float yRadius, float verticalBias, BlockStateProvider state) {
		float xzRadiusSquared = xzRadius * xzRadius;
		float yRadiusSquared = yRadius * yRadius;
		float superRadiusSquared = xzRadiusSquared * yRadiusSquared;
		putLeafBlock(worldPlacer, centerPos, state, random);

		for (int y = 0; y <= yRadius; y++) {
			if (y > yRadius) continue;

			putLeafBlock(worldPlacer, centerPos.offset( 0,  y, 0), state, random);
			putLeafBlock(worldPlacer, centerPos.offset( 0,  y, 0), state, random);
			putLeafBlock(worldPlacer, centerPos.offset( 0,  y, 0), state, random);
			putLeafBlock(worldPlacer, centerPos.offset( 0,  y, 0), state, random);

			putLeafBlock(worldPlacer, centerPos.offset( 0, -y, 0), state, random);
			putLeafBlock(worldPlacer, centerPos.offset( 0, -y, 0), state, random);
			putLeafBlock(worldPlacer, centerPos.offset( 0, -y, 0), state, random);
			putLeafBlock(worldPlacer, centerPos.offset( 0, -y, 0), state, random);
		}

		for (int x = 0; x <= xzRadius; x++) {
			for (int z = 1; z <= xzRadius; z++) {
				if (x * x + z * z > xzRadiusSquared) continue;

				putLeafBlock(worldPlacer, centerPos.offset(  x, 0,  z), state, random);
				putLeafBlock(worldPlacer, centerPos.offset( -x, 0, -z), state, random);
				putLeafBlock(worldPlacer, centerPos.offset( -z, 0,  x), state, random);
				putLeafBlock(worldPlacer, centerPos.offset(  z, 0, -x), state, random);

				for (int y = 1; y <= yRadius; y++) {
					float xzSquare = ((x * x + z * z) * yRadiusSquared);

					if (xzSquare + (((y - verticalBias) * (y - verticalBias)) * xzRadiusSquared) <= superRadiusSquared) {
						putLeafBlock(worldPlacer, centerPos.offset(  x,  y,  z), state, random);
						putLeafBlock(worldPlacer, centerPos.offset( -x,  y, -z), state, random);
						putLeafBlock(worldPlacer, centerPos.offset( -z,  y,  x), state, random);
						putLeafBlock(worldPlacer, centerPos.offset(  z,  y, -x), state, random);
					}

					if (xzSquare + (((y + verticalBias) * (y + verticalBias)) * xzRadiusSquared) <= superRadiusSquared) {
						putLeafBlock(worldPlacer, centerPos.offset(  x, -y,  z), state, random);
						putLeafBlock(worldPlacer, centerPos.offset( -x, -y, -z), state, random);
						putLeafBlock(worldPlacer, centerPos.offset( -z, -y,  x), state, random);
						putLeafBlock(worldPlacer, centerPos.offset(  z, -y, -x), state, random);
					}
				}
			}
		}
	}

	public static void makeLeafSpheroid(BiConsumer<BlockPos, BlockState> worldPlacer, Random random, BlockPos centerPos, float xzRadius, float yRadius, BlockStateProvider state) {
		float xzRadiusSquared = xzRadius * xzRadius;
		float yRadiusSquared = yRadius * yRadius;
		float superRadiusSquared = xzRadiusSquared * yRadiusSquared;
		putLeafBlock(worldPlacer, centerPos, state, random);

		for (int y = 0; y <= yRadius; y++) {
			if (y > yRadius) continue;

			putLeafBlock(worldPlacer, centerPos.offset( 0,  y, 0), state, random);
			putLeafBlock(worldPlacer, centerPos.offset( 0,  y, 0), state, random);
			putLeafBlock(worldPlacer, centerPos.offset( 0,  y, 0), state, random);
			putLeafBlock(worldPlacer, centerPos.offset( 0,  y, 0), state, random);

			putLeafBlock(worldPlacer, centerPos.offset( 0, -y, 0), state, random);
			putLeafBlock(worldPlacer, centerPos.offset( 0, -y, 0), state, random);
			putLeafBlock(worldPlacer, centerPos.offset( 0, -y, 0), state, random);
			putLeafBlock(worldPlacer, centerPos.offset( 0, -y, 0), state, random);
		}

		for (int x = 0; x <= xzRadius; x++) {
			for (int z = 1; z <= xzRadius; z++) {
				if (x * x + z * z > xzRadiusSquared) continue;

				putLeafBlock(worldPlacer, centerPos.offset(  x, 0,  z), state, random);
				putLeafBlock(worldPlacer, centerPos.offset( -x, 0, -z), state, random);
				putLeafBlock(worldPlacer, centerPos.offset( -z, 0,  x), state, random);
				putLeafBlock(worldPlacer, centerPos.offset(  z, 0, -x), state, random);

				for (int y = 1; y <= yRadius; y++) {
					float xzSquare = ((x * x + z * z) * yRadiusSquared);

					if (xzSquare + (y * y) * xzRadiusSquared <= superRadiusSquared) {
						putLeafBlock(worldPlacer, centerPos.offset(  x,  y,  z), state, random);
						putLeafBlock(worldPlacer, centerPos.offset( -x,  y, -z), state, random);
						putLeafBlock(worldPlacer, centerPos.offset( -z,  y,  x), state, random);
						putLeafBlock(worldPlacer, centerPos.offset(  z,  y, -x), state, random);

						putLeafBlock(worldPlacer, centerPos.offset(  x, -y,  z), state, random);
						putLeafBlock(worldPlacer, centerPos.offset( -x, -y, -z), state, random);
						putLeafBlock(worldPlacer, centerPos.offset( -z, -y,  x), state, random);
						putLeafBlock(worldPlacer, centerPos.offset(  z, -y, -x), state, random);
					}
				}
			}
		}
	}

	// ===== Pre-1.16.2 below =========================================================================================

	/**
	 * Moves distance along the vector.
	 * <p>
	 * This goofy function takes a float between 0 and 1 for the angle, where 0 is 0 degrees, .5 is 180 degrees and 1 and 360 degrees.
	 * For the tilt, it takes a float between 0 and 1 where 0 is straight up, 0.5 is straight out and 1 is straight down.
	 */
	public static BlockPos translate(BlockPos pos, double distance, double angle, double tilt) {
		double rangle = angle * 2.0D * Math.PI;
		double rtilt = tilt * Math.PI;

		return pos.offset(
				Math.round(Math.sin(rangle) * Math.sin(rtilt) * distance),
				Math.round(Math.cos(rtilt) * distance),
				Math.round(Math.cos(rangle) * Math.sin(rtilt) * distance)
		);
	}

	/**
	 * Draws a line from {x1, y1, z1} to {x2, y2, z2}
	 * This takes all variables for setting Branch
	 */
	public static void drawBresenhamBranch(TFTreeGenerator<? extends TFTreeFeatureConfig> generator, LevelAccessor world, Random random, BlockPos from, BlockPos to, Set<BlockPos> state, BoundingBox mbb, TFTreeFeatureConfig config) {
		for (BlockPos pixel : getBresenhamArrays(from, to)) {
			generator.setBranchBlockState(world, random, pixel, state, mbb, config);
			//world.setBlockState(pixel, state);
		}
	}

	/**
	 * Draws a line from {x1, y1, z1} to {x2, y2, z2}
	 * This just takes a BlockState, used to set Trunk
	 */
	@Deprecated
	public static void drawBresenhamTree(LevelAccessor world, BlockPos from, BlockPos to, BlockState state, Set<BlockPos> treepos) {
		for (BlockPos pixel : getBresenhamArrays(from, to)) {
			world.setBlock(pixel, state, 3);
			treepos.add(pixel.immutable());
		}
	}

	/**
	 * Get an array of values that represent a line from point A to point B
	 */
	public static BlockPos[] getBresenhamArrays(BlockPos src, BlockPos dest) {
		return getBresenhamArrays(src.getX(), src.getY(), src.getZ(), dest.getX(), dest.getY(), dest.getZ());
	}

	/**
	 * Get an array of values that represent a line from point A to point B
	 * todo 1.9 lazify this into an iterable?
	 */
	public static BlockPos[] getBresenhamArrays(int x1, int y1, int z1, int x2, int y2, int z2) {
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
					pixel = pixel.above(y_inc);
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
				pixel = pixel.above(y_inc);
			}
		} else {
			err_1 = doubleAbsDy - absDz;
			err_2 = doubleAbsDx - absDz;
			lineArray = new BlockPos[absDz + 1];
			for (i = 0; i < absDz; i++) {
				lineArray[i] = pixel;
				if (err_1 > 0) {
					pixel = pixel.above(y_inc);
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
	@Deprecated
	public static void makeLeafCircle(LevelAccessor world, BlockPos pos, int rad, BlockState state, Set<BlockPos> leaves, boolean useHack) {
		// trace out a quadrant
		for (byte dx = 0; dx <= rad; dx++) {
			for (byte dz = 0; dz <= rad; dz++) {
				int dist = Math.max(dx, dz) + (Math.min(dx, dz) >> 1);

				// hack! I keep getting failing leaves at a certain position.
				if (useHack && dx == 3 && dz == 3) {
					dist = 6;
				}

				// if we're inside the blob, fill it
				if (dist <= rad) {
					// do four at a time for easiness!
					putLeafBlock(world, pos.offset(+dx, 0, +dz), state, leaves);
					putLeafBlock(world, pos.offset(+dx, 0, -dz), state, leaves);
					putLeafBlock(world, pos.offset(-dx, 0, +dz), state, leaves);
					putLeafBlock(world, pos.offset(-dx, 0, -dz), state, leaves);
				}
			}
		}
	}

	/**
	 * Put a leaf only in spots where leaves can go!
	 */
	public static void putLeafBlock(LevelAccessor world, BlockPos pos, BlockState state, Set<BlockPos> leavespos) {
		BlockState whatsThere = world.getBlockState(pos);

		if (TreeFeature.isAirOrLeaves(world, pos) && whatsThere.getBlock() != state.getBlock()) {
			world.setBlock(pos, state, 3);
			leavespos.add(pos.immutable());
		}
	}

	/**
	 * Draw a flat blob (circle) of leaves.  This one makes it offset to surround a 2x2 area instead of a 1 block area
	 */
	// FIXME Probably nuke method once 1.16.2 comes
	public static void makeLeafCircle2(LevelAccessor world, BlockPos pos, int rad, BlockState state, Set<BlockPos> leaves) {
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
					putLeafBlock(world, pos.offset(1 + dx, 0, 1 + dz), state, leaves);
					putLeafBlock(world, pos.offset(1 + dx, 0, -dz), state, leaves);
					putLeafBlock(world, pos.offset(-dx, 0, 1 + dz), state, leaves);
					putLeafBlock(world, pos.offset(-dx, 0, -dz), state, leaves);
				}
			}
		}
	}

	/**
	 * Gets either cobblestone or mossy cobblestone, randomly.  Used for ruins.
	 */
	public static BlockState randStone(Random rand, int howMuch) {
		return rand.nextInt(howMuch) >= 1 ? Blocks.COBBLESTONE.defaultBlockState() : Blocks.MOSSY_COBBLESTONE.defaultBlockState();
	}

	/**
	 * Checks an area to see if it consists of flat natural ground below and air above
	 */
	public static boolean isAreaSuitable(LevelAccessor world, BlockPos pos, int width, int height, int depth) {
		boolean flag = true;

		// check if there's anything within the diameter
		for (int cx = 0; cx < width; cx++) {
			for (int cz = 0; cz < depth; cz++) {
				BlockPos pos_ = pos.offset(cx, 0, cz);
				// check if the blocks even exist?
				if (world.hasChunkAt(pos_)) {
					// is there grass, dirt or stone below?
					Material m = world.getBlockState(pos_.below()).getMaterial();
					if (m != Material.DIRT && m != Material.GRASS && m != Material.STONE) {
						flag = false;
					}

					for (int cy = 0; cy < height; cy++) {
						// blank space above?
						if (!world.isEmptyBlock(pos_.above(cy))) {
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
	public static void drawBlob(LevelAccessor world, BlockPos pos, int rad, BlockState state) {
		// then trace out a quadrant
		for (byte dx = 0; dx <= rad; dx++) {
			for (byte dy = 0; dy <= rad; dy++) {
				for (byte dz = 0; dz <= rad; dz++) {
					// determine how far we are from the center.
					int dist;
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
						world.setBlock(pos.offset(+dx, +dy, +dz), state, 3);
						world.setBlock(pos.offset(+dx, +dy, -dz), state, 3);
						world.setBlock(pos.offset(-dx, +dy, +dz), state, 3);
						world.setBlock(pos.offset(-dx, +dy, -dz), state, 3);
						world.setBlock(pos.offset(+dx, -dy, +dz), state, 3);
						world.setBlock(pos.offset(+dx, -dy, -dz), state, 3);
						world.setBlock(pos.offset(-dx, -dy, +dz), state, 3);
						world.setBlock(pos.offset(-dx, -dy, -dz), state, 3);
					}
				}
			}
		}
	}

	/**
	 * Draw a giant blob of leaves.
	 */
	public static void drawLeafBlob(LevelAccessor world, BlockPos pos, int rad, BlockState state, Set<BlockPos> leaves) {
		// then trace out a quadrant
		for (byte dx = 0; dx <= rad; dx++) {
			for (byte dy = 0; dy <= rad; dy++) {
				for (byte dz = 0; dz <= rad; dz++) {
					// determine how far we are from the center.
					int dist;
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
						putLeafBlock(world, pos.offset(+dx, +dy, +dz), state, leaves);
						putLeafBlock(world, pos.offset(+dx, +dy, -dz), state, leaves);
						putLeafBlock(world, pos.offset(-dx, +dy, +dz), state, leaves);
						putLeafBlock(world, pos.offset(-dx, +dy, -dz), state, leaves);
						putLeafBlock(world, pos.offset(+dx, -dy, +dz), state, leaves);
						putLeafBlock(world, pos.offset(+dx, -dy, -dz), state, leaves);
						putLeafBlock(world, pos.offset(-dx, -dy, +dz), state, leaves);
						putLeafBlock(world, pos.offset(-dx, -dy, -dz), state, leaves);
					}
				}
			}
		}
	}

	/**
	 * Does the block have only air blocks adjacent
	 */
	public static boolean surroundedByAir(LevelReader world, BlockPos pos) {
		for (Direction e : Direction.values()) {
			if (!world.isEmptyBlock(pos.relative(e))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Does the block have at least 1 air block adjacent
	 */
	private static final Direction[] directionsExceptDown = new Direction[]{Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

	public static boolean hasAirAround(LevelAccessor world, BlockPos pos) {
		for (Direction e : directionsExceptDown) {
			if (world.isEmptyBlock(pos.relative(e))) {
				return true;
			}
		}

		return false;
	}

	public static boolean isNearSolid(LevelReader world, BlockPos pos) {
		for (Direction e : Direction.values()) {
			if (world.hasChunkAt(pos.relative(e))
					&& world.getBlockState(pos.relative(e)).getMaterial().isSolid()) {
				return true;
			}
		}

		return false;
	}

	public static void setBlockStateProvider(LevelAccessor world, BlockStateProvider provider, Random rand, BlockPos pos) {
		world.setBlock(pos, provider.getState(rand, pos), 3);
	}
}
