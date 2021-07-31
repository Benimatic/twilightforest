package twilightforest.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.LevelAccessor;
import twilightforest.entity.TFEntities;
import twilightforest.loot.TFTreasure;
import twilightforest.block.TFBlocks;
import twilightforest.util.FeatureUtil;
import twilightforest.world.TFGenerationSettings;
import twilightforest.world.feature.config.TFTreeFeatureConfig;

import java.util.Random;
import java.util.Set;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TFGenHollowTree extends TFTreeGenerator<TFTreeFeatureConfig> {

	private static final int LEAF_DUNGEON_CHANCE = 8;

	public TFGenHollowTree(Codec<TFTreeFeatureConfig> config) {
		super(config);
	}

//	public static boolean canGrowInto(Block blockType) {
//		Material material = blockType.getDefaultState().getMaterial();
//		return material == Material.AIR || material == Material.LEAVES || material == Material.WATER || material == Material.LAVA || blockType instanceof IGrowable || blockType instanceof BlockDirt || blockType instanceof BlockLog || blockType instanceof BlockBush || blockType instanceof BlockVine;
//	}

//	@Override
//	@Deprecated
//	protected void setBlockAndNotifyAdequately(World worldIn, BlockPos pos, BlockState state) {
//		if (canGrowInto(worldIn.getBlockState(pos).getBlock()))
//			super.setBlockAndNotifyAdequately(worldIn, pos, state);
//	}

	@Override
	public boolean generate(LevelAccessor world, Random random, BlockPos pos, Set<BlockPos> trunk, Set<BlockPos> leaves, Set<BlockPos> branch, Set<BlockPos> root, BoundingBox mbb, TFTreeFeatureConfig config) {
		int diameter = random.nextInt(3) + 2;
		int height = random.nextInt(64) + (diameter * 4);

		// do we have enough height?
		if (pos.getY() < 1 || pos.getY() + height + diameter > TFGenerationSettings.MAXHEIGHT) {
			return false;
		}

//		// are we going to hit something?
//		for (int dx = -diameter; dx <= diameter; dx++)
//		{
//			for (int dz = -diameter; dz <= diameter; dz++)
//			{
//				for (int dy = 1; dy <= height; dy++)
//				{
//					int whatsThere = world.getBlock(dx + x, dy + y, dz + z);
//					if(whatsThere != 0 && whatsThere != Blocks.LEAVES)
//					{
//						return false;
//					}
//				}
//			}
//		}
		// check the top too
		int crownRadius = diameter * 4 + 8;
		for (int dx = -crownRadius; dx <= crownRadius; dx++) {
			for (int dz = -crownRadius; dz <= crownRadius; dz++) {
				for (int dy = height - crownRadius; dy <= height + crownRadius; dy++) {
					Block whatsThere = world.getBlockState(pos.offset(dx, dy, dz)).getBlock();
					if (whatsThere != Blocks.AIR && !(whatsThere instanceof LeavesBlock)) {
						return false;
					}
				}
			}
		}

		// check if we're on dirt or grass
		BlockState state = world.getBlockState(pos.below());
		if (!state.getBlock().canSustainPlant(state, world, pos.below(), Direction.UP, config.getSapling(random, pos))) {
			return false;
		}

		// make a tree!

		// build the trunk
		buildTrunk(world, random, pos, trunk, branch, root, diameter, height, mbb, config);

		// fireflies
		int numFireflies = random.nextInt(6 * diameter) + 5;
		for (int i = 0; i <= numFireflies; i++) {
			int fHeight = (int) (height * random.nextDouble() * 0.9) + (height / 10);
			double fAngle = random.nextDouble();
			addFirefly(world, pos, diameter, fHeight, fAngle);
		}

		// cicadas
		int numCicadas = random.nextInt(3 * diameter) + 5;
		for (int i = 0; i <= numCicadas; i++) {
			int fHeight = (int) (height * random.nextDouble() * 0.9) + (height / 10);
			double fAngle = random.nextDouble();
			addCicada(world, pos, diameter, fHeight, fAngle);
		}

		// build the crown
		buildFullCrown(world, random, pos, leaves, branch, diameter, height, mbb, config);

		// 3-5 couple branches on the way up...
		int numBranches = random.nextInt(3) + 3;
		for (int i = 0; i <= numBranches; i++) {
			int branchHeight = (int) (height * random.nextDouble() * 0.9) + (height / 10);
			double branchRotation = random.nextDouble();
			makeSmallBranch(world, random, pos, leaves, branch, diameter, branchHeight, 4, branchRotation, 0.35D, true, mbb, config);
		}

		// 3-5 roots at the bottom
		buildBranchRing(world, random, pos, leaves, branch, diameter, 3, 2, 6, 0.75D, 3, 5, 3, false, mbb, config);

		// several more taproots
		buildBranchRing(world, random, pos, leaves, branch, diameter, 1, 2, 8, 0.9D, 3, 5, 3, false, mbb, config);

		return true;
	}

	/**
	 * Build the crown of the tree
	 *
	 * @param diameter
	 * @param height
	 */
	protected void buildFullCrown(LevelAccessor world, Random random, BlockPos pos, Set<BlockPos> leaves, Set<BlockPos> branch, int diameter, int height, BoundingBox mbb, TFTreeFeatureConfig config) {
		int crownRadius = diameter * 4 + 4;
		int bvar = diameter + 2;

		// okay, let's do 3-5 main branches starting at the bottom of the crown
		buildBranchRing(world, random, pos, leaves, branch, diameter, height - crownRadius, 0, crownRadius, 0.35D, bvar, bvar + 2, 2, true, mbb, config);

		// then, let's do 3-5 medium branches at the crown middle
		buildBranchRing(world, random, pos, leaves, branch, diameter, height - (crownRadius / 2), 0, crownRadius, 0.28D, bvar, bvar + 2, 1, true, mbb, config);

		// finally, let's do 2-4 main branches at the crown top
		buildBranchRing(world, random, pos, leaves, branch, diameter, height, 0, crownRadius, 0.15D, 2, 4, 2, true, mbb, config);

		// and extra finally, let's do 3-6 medium branches going straight up
		buildBranchRing(world, random, pos, leaves, branch, diameter, height, 0, (crownRadius / 2), 0.05D, bvar, bvar + 2, 1, true, mbb, config);

		// this glass sphere approximates where we want our crown		
		//drawBlob(x, y + height, z, (byte)crownRadius, (byte)Blocks.GLASS, false);

	}

	/**
	 * Build a ring of branches around the tree
	 * size 0 = small, 1 = med, 2 = large, 3 = root
	 */
	protected void buildBranchRing(LevelAccessor world, Random random, BlockPos pos, Set<BlockPos> leaves, Set<BlockPos> branch, int diameter, int branchHeight, int heightVar, int length, double tilt, int minBranches, int maxBranches, int size, boolean leafy, BoundingBox mbb, TFTreeFeatureConfig config) {
		//let's do this!
		int numBranches = random.nextInt(maxBranches - minBranches) + minBranches;
		;
		double branchRotation = 1.0 / (numBranches + 1);
		double branchOffset = random.nextDouble();

		for (int i = 0; i <= numBranches; i++) {
			int dHeight;
			if (heightVar > 0) {
				dHeight = branchHeight - heightVar + random.nextInt(2 * heightVar);
			} else {
				dHeight = branchHeight;
			}

			if (size == 2) {
				makeLargeBranch(world, random, pos, leaves, branch, diameter, dHeight, length, i * branchRotation + branchOffset, tilt, leafy, mbb, config);
			} else if (size == 1) {
				makeMedBranch(world, random, pos, leaves, branch, diameter, dHeight, length, i * branchRotation + branchOffset, tilt, leafy, mbb, config);
			} else if (size == 3) {
				makeRoot(world, random, pos, diameter, dHeight, length, i * branchRotation + branchOffset, tilt, config);
			} else {
				makeSmallBranch(world, random, pos, leaves, branch, diameter, dHeight, length, i * branchRotation + branchOffset, tilt, leafy, mbb, config);
			}
		}
	}

	/**
	 * This function builds the hollow trunk of the tree
	 */
	protected void buildTrunk(LevelAccessor world, Random random, BlockPos pos, Set<BlockPos> trunk, Set<BlockPos> branch, Set<BlockPos> root, int diameter, int height, BoundingBox mbb, TFTreeFeatureConfig config) {

		int hollow = diameter / 2;

		// go down 4 squares and fill in extra trunk as needed, in case we're on uneven terrain
		for (int dx = -diameter; dx <= diameter; dx++) {
			for (int dz = -diameter; dz <= diameter; dz++) {
				for (int dy = -4; dy < 0; dy++) {
					// determine how far we are from the center.
					int ax = Math.abs(dx);
					int az = Math.abs(dz);
					int dist = (int) (Math.max(ax, az) + (Math.min(ax, az) * 0.5));

					if (dist <= diameter) {
						BlockPos dPos = pos.offset(dx, dy, dz);
						if (FeatureUtil.hasAirAround(world, dPos)) {
							if (dist > hollow) {
								this.setLogBlockState(world, random, dPos, trunk, mbb, config);
							} else {
								this.setBranchBlockState(world, random, dPos, branch, mbb, config);
							}
						} else {
							this.setRootsBlockState(world, random, dPos, root, mbb, config);
						}
					}
				}
			}
		}

		// build the trunk upwards
		for (int dx = -diameter; dx <= diameter; dx++) {
			for (int dz = -diameter; dz <= diameter; dz++) {
				for (int dy = 0; dy <= height; dy++) {
					BlockPos dPos = pos.offset(dx, dy, dz);
					// determine how far we are from the center.
					int ax = Math.abs(dx);
					int az = Math.abs(dz);
					int dist = (int) (Math.max(ax, az) + (Math.min(ax, az) * 0.5));

					// make a trunk!
					if (dist <= diameter && dist > hollow) {
						setLogBlockState(world, random, dPos, trunk, mbb, config);
					}

					// fill it with lava!
					if (dist <= hollow) {
						// just kidding!
						//world.setBlock(dx + x, dy + y, dz + z, Blocks.LAVA);
					}

					// how about a ladder?  is that okay?
					if (dist == hollow && dx == hollow) {
//						putBlockAndMetadata(dx + x, dy + y, dz + z, Blocks.LADDER,  4, true);
						world.setBlock(dPos, Blocks.VINE.defaultBlockState().setValue(VineBlock.EAST, true),3);
					}
				}
			}
		}
	}

	/**
	 * Make a branch!
	 */
	protected void makeMedBranch(LevelAccessor world, Random random, BlockPos pos, Set<BlockPos> leaves, Set<BlockPos> branch, int diameter, int branchHeight, double length, double angle, double tilt, boolean leafy, BoundingBox mbb, TFTreeFeatureConfig config) {
		BlockPos src = FeatureUtil.translate(pos.above(branchHeight), diameter, angle, 0.5);
		makeMedBranch(world, random, src, leaves, branch, length, angle, tilt, leafy, mbb, config);
	}

	/**
	 * Make a branch!
	 */
	protected void makeMedBranch(LevelAccessor world, Random random, BlockPos src, Set<BlockPos> leaves, Set<BlockPos> branch, double length, double angle, double tilt, boolean leafy, BoundingBox mbb, TFTreeFeatureConfig config) {
		BlockPos dest = FeatureUtil.translate(src, length, angle, tilt);

		FeatureUtil.drawBresenhamBranch(this, world, random, src, dest, branch, mbb, config);

		// with leaves!

		if (leafy) {
			/*
			int numLeafBalls = random.nextInt(2) + 1;
			for(int i = 0; i <= numLeafBalls; i++) {

				double slength = random.nextDouble() * 0.6 + 0.2;
				int[] bdst = translate(src[0], src[1], src[2], slength, angle, tilt);

				drawBlob(bdst[0], bdst[1], bdst[2], 2, leafBlock, false);		
			}
			*/

			// and a blob at the end
			FeatureUtil.drawLeafBlob(world, dest, 2, config.leavesProvider.getState(random, dest), leaves);
		}

		// and several small branches

		int numShoots = random.nextInt(2) + 1;
		double angleInc, angleVar, outVar, tiltVar;

		angleInc = 0.8 / numShoots;

		for (int i = 0; i <= numShoots; i++) {

			angleVar = (angleInc * i) - 0.4;
			outVar = (random.nextDouble() * 0.8) + 0.2;
			tiltVar = (random.nextDouble() * 0.75) + 0.15;

			BlockPos bsrc = FeatureUtil.translate(src, length * outVar, angle, tilt);
			double slength = length * 0.4;

			makeSmallBranch(world, random, bsrc, leaves, branch, slength, angle + angleVar, tilt * tiltVar, leafy, mbb, config);
		}
	}

	/**
	 * Make a small branch with a leaf blob at the end
	 */
	protected void makeSmallBranch(LevelAccessor world, Random random, BlockPos src, Set<BlockPos> leaves, Set<BlockPos> branch, double length, double angle, double tilt, boolean leafy, BoundingBox mbb, TFTreeFeatureConfig config) {
		BlockPos dest = FeatureUtil.translate(src, length, angle, tilt);

		FeatureUtil.drawBresenhamBranch(this, world, random, src, dest, branch, mbb, config);

		if (leafy) {
			byte leafRad = (byte) (random.nextInt(2) + 1);
			FeatureUtil.drawLeafBlob(world, dest, leafRad, config.leavesProvider.getState(random, dest), leaves);
		}
	}

	/**
	 * Make a small branch at a certain height
	 */
	protected void makeSmallBranch(LevelAccessor world, Random random, BlockPos pos, Set<BlockPos> leaves, Set<BlockPos> branch, int diameter, int branchHeight, double length, double angle, double tilt, boolean leafy, BoundingBox mbb, TFTreeFeatureConfig config) {
		BlockPos src = FeatureUtil.translate(pos.above(branchHeight), diameter, angle, 0.5);
		makeSmallBranch(world, random, src, leaves, branch, length, angle, tilt, leafy, mbb, config);
	}

	/**
	 * Make a root
	 */
	protected void makeRoot(LevelAccessor world, Random random, BlockPos pos, int diameter, int branchHeight, double length, double angle, double tilt, TFTreeFeatureConfig config) {
		BlockPos src = FeatureUtil.translate(pos.above(branchHeight), diameter, angle, 0.5);
		BlockPos dest = FeatureUtil.translate(src, length, angle, tilt);

		BlockPos[] lineArray = FeatureUtil.getBresenhamArrays(src, dest);
		boolean stillAboveGround = true;
		for (BlockPos coord : lineArray) {
			if (stillAboveGround && FeatureUtil.hasAirAround(world, coord)) {
				world.setBlock(coord, config.branchProvider.getState(random, coord), 3);
				world.setBlock(coord.below(), config.branchProvider.getState(random, coord.below()), 3);
			} else {
				world.setBlock(coord, config.rootsProvider.getState(random, coord), 3);
				world.setBlock(coord.below(), config.rootsProvider.getState(random, coord.below()), 3);
				stillAboveGround = false;
			}
		}
	}

	/**
	 * Make a large, branching "base" branch in a specific location.
	 * <p>
	 * The large branch will have 1-4 medium branches and several small branches too
	 */
	protected void makeLargeBranch(LevelAccessor world, Random random, BlockPos src, Set<BlockPos> leaves, Set<BlockPos> branch, double length, double angle, double tilt, boolean leafy, BoundingBox mbb, TFTreeFeatureConfig config) {
		BlockPos dest = FeatureUtil.translate(src, length, angle, tilt);

		// draw the main branch
		FeatureUtil.drawBresenhamBranch(this, world, random, src, dest, branch, mbb, config);

		// reinforce it
		//drawBresehnam(src[0], src[1] + 1, src[2], dest[0], dest[1], dest[2], treeBlock, true);
		int reinforcements = random.nextInt(3);
		for (int i = 0; i <= reinforcements; i++) {
			int vx = (i & 2) == 0 ? 1 : 0;
			int vy = (i & 1) == 0 ? 1 : -1;
			int vz = (i & 2) == 0 ? 0 : 1;
			FeatureUtil.drawBresenhamBranch(this, world, random, src.offset(vx, vy, vz), dest, branch, mbb, config);
		}

		if (leafy) {
			// add a leaf blob at the end
			FeatureUtil.drawLeafBlob(world, dest.above(), 3, config.leavesProvider.getState(random, dest.above()), leaves);
		}

		// go about halfway out and make a few medium branches.
		// the number of medium branches we can support depends on the length of the big branch
		// every other branch switches sides
		int numMedBranches = random.nextInt((int) (length / 6)) + random.nextInt(2) + 1;

		for (int i = 0; i <= numMedBranches; i++) {

			double outVar = (random.nextDouble() * 0.3) + 0.3;
			double angleVar = random.nextDouble() * 0.225 * ((i & 1) == 0 ? 1.0 : -1.0);
			BlockPos bsrc = FeatureUtil.translate(src, length * outVar, angle, tilt);

			makeMedBranch(world, random, bsrc, leaves, branch, length * 0.6, angle + angleVar, tilt, leafy, mbb, config);
		}

		// make 1-2 small ones near the base
		int numSmallBranches = random.nextInt(2) + 1;
		for (int i = 0; i <= numSmallBranches; i++) {

			double outVar = (random.nextDouble() * 0.25) + 0.25;
			double angleVar = random.nextDouble() * 0.25 * ((i & 1) == 0 ? 1.0 : -1.0);
			BlockPos bsrc = FeatureUtil.translate(src, length * outVar, angle, tilt);

			makeSmallBranch(world, random, bsrc, leaves, branch, Math.max(length * 0.3, 2), angle + angleVar, tilt, leafy, mbb, config);
		}

		if (random.nextInt(LEAF_DUNGEON_CHANCE) == 0) {
			makeLeafDungeon(world, random, dest.above(), leaves, config);
		}
	}

	private void makeLeafDungeon(LevelAccessor world, Random random, BlockPos pos, Set<BlockPos> leaves, TFTreeFeatureConfig config) {
		// make leaves
		FeatureUtil.drawLeafBlob(world, pos, 4, config.leavesProvider.getState(random, pos), leaves);
		// wood support
		FeatureUtil.drawBlob(world, pos, 3, config.branchProvider.getState(random, pos));
		// air
		FeatureUtil.drawBlob(world, pos, 2, Blocks.AIR.defaultBlockState());

		// spawner
		world.setBlock(pos.above(), Blocks.SPAWNER.defaultBlockState(), 16 | 2);
		SpawnerBlockEntity ms = (SpawnerBlockEntity) world.getBlockEntity(pos.above());
		if (ms != null) {
			ms.getSpawner().setEntityId(TFEntities.swarm_spider);
		}

		// treasure chests?
		makeLeafDungeonChest(world, random, pos);
	}

	private void makeLeafDungeonChest(LevelAccessor world, Random random, BlockPos pos) {
		pos = pos.relative(Direction.Plane.HORIZONTAL.getRandomDirection(random));
		TFTreasure.tree_cache.generateChest(world, pos.below(), Direction.NORTH, false);
	}

	/**
	 * Make a large, branching "base" branch off of the tree
	 */
	protected void makeLargeBranch(LevelAccessor world, Random random, BlockPos pos, Set<BlockPos> leaves, Set<BlockPos> branch, int diameter, int branchHeight, double length, double angle, double tilt, boolean leafy, BoundingBox mbb, TFTreeFeatureConfig config) {
		BlockPos src = FeatureUtil.translate(pos.above(branchHeight), diameter, angle, 0.5);
		makeLargeBranch(world, random, src, leaves, branch, length, angle, tilt, leafy, mbb, config);
	}

	/**
	 * Add a firefly at the specified height and angle.
	 */
	protected void addFirefly(LevelAccessor world, BlockPos pos, int diameter, int fHeight, double fAngle) {
		BlockPos src = FeatureUtil.translate(pos.above(fHeight), diameter + 1, fAngle, 0.5);

		fAngle = fAngle % 1.0;
		Direction facing = Direction.EAST;

		if (fAngle > 0.875 || fAngle <= 0.125) {
			facing = Direction.SOUTH;
		} else if (fAngle > 0.125 && fAngle <= 0.375) {
			facing = Direction.EAST;
		} else if (fAngle > 0.375 && fAngle <= 0.625) {
			facing = Direction.NORTH;
		} else if (fAngle > 0.625 && fAngle <= 0.875) {
			facing = Direction.WEST;
		}

		if (TFBlocks.firefly.get().defaultBlockState().setValue(DirectionalBlock.FACING, facing).canSurvive(world, src)) {
			world.setBlock(src, TFBlocks.firefly.get().defaultBlockState().setValue(DirectionalBlock.FACING, facing), 3);
		}
	}

	protected void addCicada(LevelAccessor world, BlockPos pos, int diameter, int fHeight, double fAngle) {
		BlockPos src = FeatureUtil.translate(pos.above(fHeight), diameter + 1, fAngle, 0.5);

		fAngle = fAngle % 1.0;
		Direction facing = Direction.EAST;

		if (fAngle > 0.875 || fAngle <= 0.125) {
			facing = Direction.SOUTH;
		} else if (fAngle > 0.125 && fAngle <= 0.375) {
			facing = Direction.EAST;
		} else if (fAngle > 0.375 && fAngle <= 0.625) {
			facing = Direction.NORTH;
		} else if (fAngle > 0.625 && fAngle <= 0.875) {
			facing = Direction.WEST;
		}

		if (TFBlocks.cicada.get().defaultBlockState().setValue(DirectionalBlock.FACING, facing).canSurvive(world, src)) {
			world.setBlock(src, TFBlocks.cicada.get().defaultBlockState().setValue(DirectionalBlock.FACING, facing), 3);
		}
	}
}
