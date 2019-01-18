package twilightforest.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import twilightforest.TFTreasure;
import twilightforest.block.BlockTFRoots;
import twilightforest.block.TFBlocks;
import twilightforest.entity.TFCreatures;



public class TFGenHollowTree extends TFGenerator 
{

	private static final int LEAF_DUNGEON_CHANCE = 8;
	
	protected Block treeBlock = TFBlocks.log;
	protected int treeMeta = 0;
	protected int branchMeta = 12;
	protected Block leafBlock = TFBlocks.leaves;
	protected int leafMeta = 0;
	protected Block rootBlock = TFBlocks.root;
	protected int rootMeta = BlockTFRoots.ROOT_META;

    public TFGenHollowTree()
    {
        this(false);
    }    
    
    public TFGenHollowTree(boolean par1)
    {
        super(par1);
    }
    
	public boolean generate(World world, Random random, int x, int y, int z) {
		
		int height = random.nextInt(64) + 32;
		int diameter =  random.nextInt(4) + 1;


		// do we have enough height?
		if(y < 1 || y + height + diameter > TFWorld.MAXHEIGHT)
		{
//			System.out.println("Failed with hollow tree of height " + height);
			return false;
		}
//		System.out.println("Succeeded with hollow tree of height " + height);

//		// are we going to hit something?
//		for (int dx = -diameter; dx <= diameter; dx++)
//		{
//			for (int dz = -diameter; dz <= diameter; dz++)
//			{
//				for (int dy = 1; dy <= height; dy++)
//				{
//					int whatsThere = world.getBlock(dx + x, dy + y, dz + z);
//					if(whatsThere != 0 && whatsThere != Blocks.leaves)
//					{
//						System.out.println("Failed tree due to things at the base at height " + dy);
//						return false;
//					}
//				}
//			}
//		}
		// check the top too
		int crownRadius = diameter * 4 + 8;
		for (int dx = -crownRadius; dx <= crownRadius; dx++)
		{
			for (int dz = -crownRadius; dz <= crownRadius; dz++)
			{
				for (int dy = height - crownRadius; dy <= height + crownRadius; dy++)
				{
					Block whatsThere = world.getBlock(dx + x, dy + y, dz + z);
					if(whatsThere != Blocks.air && whatsThere != Blocks.leaves)
					{
//						System.out.println("Failed tree due to things at the top");
						return false;
					}
				}
			}
		}


		// make a tree!

		// check if we're on dirt or grass
		Block j1 = world.getBlock(x, y - 1, z);
		if(j1 != Blocks.grass && j1 != Blocks.dirt)
		{
			return false;
		}

		// build the trunk
		buildTrunk(world, random, x, y, z, diameter, height);

		// fireflies
		int numFireflies = random.nextInt(3 * diameter) + 5;
		for (int i = 0; i <= numFireflies; i++) {
			int fHeight = (int)(height * random.nextFloat() * 0.9) + (height / 10);
			double fAngle = random.nextFloat();
			addFirefly(world, x, y, z, diameter, fHeight, fAngle);
		}
		
		// cicadas
		numFireflies = random.nextInt(3 * diameter) + 5;
		for (int i = 0; i <= numFireflies; i++) {
			int fHeight = (int)(height * random.nextFloat() * 0.9) + (height / 10);
			double fAngle = random.nextFloat();
			addCicada(world, x, y, z, diameter, fHeight, fAngle);
		}
		
		// build the crown
		buildFullCrown(world, random, x, y, z, diameter, height);

		
		// 3-5 couple branches on the way up...
		int numBranches = random.nextInt(3) + 3;
		for (int i = 0; i <= numBranches; i++) {
			int branchHeight = (int)(height * random.nextFloat() * 0.9) + (height / 10);
			double branchRotation = random.nextFloat();
			makeSmallBranch(world, random, x, y, z, diameter, branchHeight, 4, branchRotation, 0.35D, true);
		}

		// 3-5 roots at the bottom
		buildBranchRing(world, random, x, y, z, diameter, 3, 2, 6, 0, 0.75D, 0, 3, 5, 3, false);


		// several more taproots
		buildBranchRing(world, random, x, y, z, diameter, 1, 2, 8, 0, 0.9D, 0, 3, 5, 3, false);
		
		return true;
	}
	
	/**
	 * Build the crown of the tree
	 * @param diameter 
	 * @param height 
	 */
	protected void buildFullCrown(World world, Random random, int x, int y, int z, int diameter, int height) {
		int crownRadius = diameter * 4 + 4;
		int bvar = diameter + 2;
		
		// okay, let's do 3-5 main branches starting at the bottom of the crown
		buildBranchRing(world, random, x, y, z, diameter, height - crownRadius, 0, crownRadius, 0, 0.35D, 0, bvar, bvar + 2, 2, true);

		// then, let's do 3-5 medium branches at the crown middle
		buildBranchRing(world, random, x, y, z, diameter, height - (crownRadius / 2), 0, crownRadius, 0, 0.28D, 0, bvar, bvar + 2, 1, true);
		
		// finally, let's do 2-4 main branches at the crown top
		buildBranchRing(world, random, x, y, z, diameter, height, 0, crownRadius, 0, 0.15D, 0, 2, 4, 2, true);
		
		// and extra finally, let's do 3-6 medium branches going straight up
		buildBranchRing(world, random, x, y, z, diameter, height, 0, (crownRadius / 2), 0, 0.05D, 0, bvar, bvar + 2, 1, true);
		
		// this glass sphere approximates where we want our crown		
		//drawBlob(x, y + height, z, (byte)crownRadius, (byte)Blocks.glass, false);
		
	}
	
	/**
	 * Build the crown of the tree.  This builds a smaller crown, since the large ones were causing some performance issues
	 * @param height 
	 */
	protected void buildWeakCrown(World world, Random random, int x, int y, int z, int diameter, int height) {
		int crownRadius = 8;
		int bvar = 2;
		
		// 3-5 medium branches starting at the bottom of the crown
		buildBranchRing(world, random, x, y, z, diameter, height - crownRadius, 0, crownRadius, 0, 0.35D, 0, bvar, bvar + 2, 1, true);

		// 3-5 medium branches at the crown middle
		buildBranchRing(world, random, x, y, z, diameter, height - (crownRadius / 2), 0, crownRadius, 0, 0.28D, 0, bvar, bvar + 2, 1, true);
		
		// 2-4 medium branches at the crown top
		buildBranchRing(world, random, x, y, z, diameter, height, 0, crownRadius, 0, 0.15D, 0, 2, 4, 1, true);
		
		// 3-6 medium branches going straight up
		buildBranchRing(world, random, x, y, z, diameter, height, 0, (crownRadius / 2), 0, 0.05D, 0, bvar, bvar + 2, 1, true);
	}
	
	/**
	 * Build a ring of branches around the tree
	 * size 0 = small, 1 = med, 2 = large, 3 = root
	 */
	protected void buildBranchRing(World world, Random random, int x, int y, int z, int diameter, int branchHeight, int heightVar, int length, int lengthVar, double tilt, double tiltVar, int minBranches, int maxBranches, int size, boolean leafy) {
		//let's do this!
		int numBranches = random.nextInt(maxBranches - minBranches) + minBranches;
		;
		double branchRotation = 1.0 / (numBranches + 1);
		double branchOffset = random.nextFloat();
		
		for (int i = 0; i <= numBranches; i++) {
			int dHeight;
			if (heightVar > 0) {
				dHeight = branchHeight - heightVar + random.nextInt(2 * heightVar);
			} else {
				dHeight = branchHeight;
			}
			
			if (size == 2) {
				makeLargeBranch(world, random, x, y, z, diameter, dHeight, length, i * branchRotation + branchOffset, tilt, leafy);
			} else if (size == 1) {
				makeMedBranch(world, random, x, y, z, diameter, dHeight, length, i * branchRotation + branchOffset, tilt, leafy);
			} else if (size == 3) {
				makeRoot(world, random, x, y, z, diameter, dHeight, length, i * branchRotation + branchOffset, tilt);
			} else {
				makeSmallBranch(world, random, x, y, z, diameter, dHeight, length, i * branchRotation + branchOffset, tilt, leafy);
			}
		}
	}
	
	
	/**
	 *  This function builds the hollow trunk of the tree
	 */
	protected void buildTrunk(World world, Random random, int x, int y, int z, int diameter, int height) {

		int hollow = diameter / 2;

		
		// go down 4 squares and fill in extra trunk as needed, in case we're on uneven terrain
		for (int dx = -diameter; dx <= diameter; dx++)
		{
			for (int dz = -diameter; dz <= diameter; dz++)
			{
				for (int dy = -4; dy < 0; dy++)
				{
					// determine how far we are from the center.
					int ax = Math.abs(dx);
					int az = Math.abs(dz);
					int dist = (int)(Math.max(ax, az) + (Math.min(ax, az) * 0.5));

					if (dist <= diameter) {
						if (hasAirAround(world, dx + x, dy + y, dz + z)) {
							this.setBlockAndMetadata(world, dx + x, dy + y, dz + z, treeBlock, dist > hollow ? treeMeta : branchMeta);
						}
						else {
							this.setBlockAndMetadata(world, dx + x, dy + y, dz + z, rootBlock, rootMeta);
						}
					}

				}
			}
		}

		// build the trunk upwards
		for (int dx = -diameter; dx <= diameter; dx++)
		{
			for (int dz = -diameter; dz <= diameter; dz++)
			{
				for (int dy = 0; dy <= height; dy++)
				{
					// determine how far we are from the center.
					int ax = Math.abs(dx);
					int az = Math.abs(dz);
					int dist = (int)(Math.max(ax, az) + (Math.min(ax, az) * 0.5));

					// make a trunk!
					if (dist <= diameter && dist > hollow) {
						setBlockAndMetadata(world, dx + x, dy + y, dz + z, treeBlock, treeMeta);
					}


					// fill it with lava!
					/*if (dist <= hollow) {
						// just kidding!
						//world.setBlock(dx + x, dy + y, dz + z, Blocks.lava);
					}*/
					
					// how about a ladder?  is that okay?
					if (dist == hollow && dx == hollow) {
//						putBlockAndMetadata(dx + x, dy + y, dz + z, Blocks.ladder,  4, true);
						setBlockAndMetadata(world, dx + x, dy + y, dz + z, Blocks.vine,  8);
					}
				}
			}
		}
		
	}
	
	/**
	 * Make a branch!
	 */
	protected void makeMedBranch(World world, Random random, int x, int y, int z, int diameter, int branchHeight, double length, double angle, double tilt, boolean leafy) {
		int sx = x, sy = y + branchHeight, sz = z;
		int[] src = translate(sx, sy, sz, diameter, angle, 0.5);
		
		makeMedBranch(world, random, src[0], src[1], src[2], length, angle, tilt, leafy);
		
	}
	
	/**
	 * Make a branch!
	 */
	protected void makeMedBranch(World world, Random random, int sx, int sy, int sz, double length, double angle, double tilt, boolean leafy) {
		int[] src = {sx, sy, sz};
		int[] dest = translate(src[0], src[1], src[2], length, angle, tilt);
		
		//System.out.println("making a branch at angle " + angle);
		
		drawBresehnam(world, src[0], src[1], src[2], dest[0], dest[1], dest[2], treeBlock, branchMeta);
		
		// with leaves!

		if (leafy) {
			/*
			int numLeafBalls = random.nextInt(2) + 1;
			for(int i = 0; i <= numLeafBalls; i++) {

				double slength = random.nextFloat() * 0.6 + 0.2;
				int[] bdst = translate(src[0], src[1], src[2], slength, angle, tilt);


				drawBlob(bdst[0], bdst[1], bdst[2], 2, leafBlock, false);		
			}
			*/

			// and a blob at the end
			drawLeafBlob(world, dest[0], dest[1], dest[2], (byte)2, leafBlock, leafMeta);	
		}


		// and several small branches

		int numShoots = random.nextInt(2) + 1;
		double angleInc, angleVar, outVar, tiltVar;

		angleInc = 0.8 / numShoots;

		for(int i = 0; i <= numShoots; i++) {

			angleVar = (angleInc * i) - 0.4;
			outVar = (random.nextFloat() * 0.8) + 0.2;
			tiltVar = (random.nextFloat() * 0.75) + 0.15;

			int[] bsrc = translate(src[0], src[1], src[2], length * outVar, angle, tilt);
			double slength = length * 0.4;

			makeSmallBranch(world, random, bsrc[0], bsrc[1], bsrc[2], slength, angle + angleVar, tilt * tiltVar, leafy);
		}
	}
	
	/**
	 * Make a small branch with a leaf blob at the end	
	 */
	protected void makeSmallBranch(World world, Random random, int sx, int sy, int sz, double length, double angle, double tilt, boolean leafy) {
		int[] src = {sx, sy, sz};
		int[] dest = translate(src[0], src[1], src[2], length, angle, tilt);
		
		drawBresehnam(world, src[0], src[1], src[2], dest[0], dest[1], dest[2], treeBlock, branchMeta);
		
		//System.out.println("making a branch at angle " + angle);
		if (leafy) {
			byte leafRad = (byte) (random.nextInt(2) + 1);
			drawLeafBlob(world, dest[0], dest[1] , dest[2], leafRad, leafBlock, leafMeta);
		}
	}
	
	/**
	 * Make a small branch at a certain height
	 */
	protected void makeSmallBranch(World world, Random random, int x, int y, int z, int diameter, int branchHeight, double length, double angle, double tilt, boolean leafy) {
		int sx = x, sy = y + branchHeight, sz = z;
		int[] src = translate(sx, sy, sz, diameter, angle, 0.5);
		
		makeSmallBranch(world, random, src[0], src[1], src[2], length, angle, tilt, leafy);
	}
	
	
	/**
	 * Make a root
	 */
	protected void makeRoot(World world, Random random, int x, int y, int z, int diameter, int branchHeight, double length, double angle, double tilt) {
		ChunkCoordinates src = translateCoords(x, y + branchHeight, z, diameter, angle, 0.5);
		ChunkCoordinates dest = translateCoords(src.posX, src.posY, src.posZ, length, angle, tilt);		
		
		ChunkCoordinates[] lineArray = getBresehnamArrayCoords(src, dest);
		boolean stillAboveGround = true; 
		for (ChunkCoordinates coord : lineArray) 
		{
			if (stillAboveGround && hasAirAround(world, coord.posX, coord.posY, coord.posZ)) {
				this.setBlockAndMetadata(world, coord.posX, coord.posY, coord.posZ, treeBlock, branchMeta);
				this.setBlockAndMetadata(world, coord.posX, coord.posY - 1, coord.posZ, treeBlock, branchMeta);
			}
			else {
				this.setBlockAndMetadata(world, coord.posX, coord.posY, coord.posZ, rootBlock, rootMeta);
				this.setBlockAndMetadata(world, coord.posX, coord.posY - 1, coord.posZ, rootBlock, rootMeta);
				stillAboveGround = false;
			}
		}
	}
	
	/**
	 * Make a large, branching "base" branch in a specific location.
	 * 
	 * The large branch will have 1-4 medium branches and several small branches too
	 */
	protected void makeLargeBranch(World world, Random random, int sx, int sy, int sz, double length, double angle, double tilt, boolean leafy) {
		int[] src = {sx, sy, sz};
		int[] dest = translate(src[0], src[1], src[2], length, angle, tilt);
		
		// draw the main branch
		drawBresehnam(world, src[0], src[1], src[2], dest[0], dest[1], dest[2], treeBlock, branchMeta);
		
		// reinforce it
		//drawBresehnam(src[0], src[1] + 1, src[2], dest[0], dest[1], dest[2], treeBlock, true);
		int reinforcements = random.nextInt(3);
		for(int i = 0; i <= reinforcements; i++) {
			int vx = (i & 2) == 0 ? 1 : 0;
			int vy = (i & 1) == 0 ? 1 : -1;
			int vz = (i & 2) == 0 ? 0 : 1;
			drawBresehnam(world, src[0] + vx, src[1] + vy, src[2] + vz, dest[0], dest[1], dest[2], treeBlock, branchMeta);
		}

		if (leafy) {
			// add a leaf blob at the end
			drawLeafBlob(world, dest[0], dest[1] + 1, dest[2], (byte)3, leafBlock, leafMeta);
		}
		
		// go about halfway out and make a few medium branches.
		// the number of medium branches we can support depends on the length of the big branch
		// every other branch switches sides
		int numMedBranches = random.nextInt((int)(length / 6)) + random.nextInt(2) + 1;
		
		for (int i = 0; i <= numMedBranches; i++) {
			
			double outVar = (random.nextFloat() * 0.3) + 0.3;
			double angleVar = random.nextFloat() * 0.225 * ((i & 1) == 0 ? 1.0 : -1.0);
			int[] bsrc = translate(src[0], src[1], src[2], length * outVar, angle, tilt);
			
			makeMedBranch(world, random, bsrc[0], bsrc[1], bsrc[2], length * 0.6, angle + angleVar, tilt, leafy);
		}
		
		// make 1-2 small ones near the base
		int numSmallBranches = random.nextInt(2) + 1;
		for(int i = 0; i <= numSmallBranches; i++) {
			
			double outVar = (random.nextFloat() * 0.25) + 0.25;
			double angleVar = random.nextFloat() * 0.25 * ((i & 1) == 0 ? 1.0 : -1.0);
			int[] bsrc = translate(src[0], src[1], src[2], length * outVar, angle, tilt);
			
			makeSmallBranch(world, random, bsrc[0], bsrc[1], bsrc[2], Math.max(length * 0.3, 2), angle + angleVar, tilt, leafy);
		}
		
		if (random.nextInt(LEAF_DUNGEON_CHANCE) == 0)
		{
			makeLeafDungeon(world, random , dest[0], dest[1] + 1, dest[2]);
		}
		
	}
		
	
	private void makeLeafDungeon(World world, Random random, int x, int y, int z) {
		// make leaves
		drawLeafBlob(world, x, y, z, 4, leafBlock, leafMeta);
		// wood support
		drawBlob(world, x, y, z, 3, treeBlock, branchMeta);
		// air
		drawBlob(world, x, y, z, 2, Blocks.air, 0);
		
		
		// spawner
        world.setBlock(x + 0, y + 1, z + 0, Blocks.mob_spawner, 0, 2);
        TileEntityMobSpawner ms = (TileEntityMobSpawner)world.getTileEntity(x + 0, y + 1, z + 0);
        if (ms != null) {
        	ms.func_145881_a().setEntityName(TFCreatures.getSpawnerNameFor("Swarm Spider"));
        }
		
		// treasure chests?
        makeLeafDungeonChest(world, random, x, y, z);
	}

	private void makeLeafDungeonChest(World world, Random random, int x, int y, int z) {
		// which direction is this chest in?
		int dir = random.nextInt(4);
		
		x += Direction.offsetX[dir];
		x += Direction.offsetX[dir];
		z += Direction.offsetZ[dir];
		z += Direction.offsetZ[dir];
		
		TFTreasure.tree_cache.generate(world, random, x, y - 1, z);
	}

	/**
	 * Make a large, branching "base" branch off of the tree
	 */
	protected void makeLargeBranch(World world, Random random, int x, int y, int z, int diameter, int branchHeight, double length, double angle, double tilt, boolean leafy) {
		int sx = x, sy = y + branchHeight, sz = z;
		int[] src = translate(sx, sy, sz, diameter, angle, 0.5);
		
		makeLargeBranch(world, random, src[0], src[1], src[2], length, angle, tilt, leafy);
	}
	
	/**
	 * Add a firefly at the specified height and angle.
	 * 
	 * @param depth how far up the tree
	 * @param angle from 0 - 1 rotation around the tree
	 */
	protected void addFirefly(World world, int x, int y, int z, int diameter, int fHeight, double fAngle)
	{
		int[] src = translate(x, y + fHeight, z, diameter + 1, fAngle, 0.5);
		
		fAngle = fAngle % 1.0;
		int tmeta = 0;
		
		if (fAngle > 0.875 || fAngle <= 0.125)
		{
			tmeta = 3;
		}
		else if (fAngle > 0.125 && fAngle <= 0.375)
		{
			tmeta = 1;
		}
		else if (fAngle > 0.375 && fAngle <= 0.625)
		{
			tmeta = 4;
		}
		else if (fAngle > 0.625 && fAngle <= 0.875)
		{
			tmeta = 2;
		}
		
		if (TFBlocks.firefly.canPlaceBlockAt(world, src[0], src[1], src[2]))
		{
			setBlockAndMetadata(world, src[0], src[1], src[2], TFBlocks.firefly, tmeta);
		}
	}

	protected void addCicada(World world, int x, int y, int z, int diameter, int fHeight, double fAngle)
	{
		int[] src = translate(x, y + fHeight, z, diameter + 1, fAngle, 0.5);
		
		fAngle = fAngle % 1.0;
		int tmeta = 1;
		
		if (fAngle > 0.875 || fAngle <= 0.125)
		{
			tmeta = 3;
		}
		else if (fAngle > 0.125 && fAngle <= 0.375)
		{
			tmeta = 1;
		}
		else if (fAngle > 0.375 && fAngle <= 0.625)
		{
			tmeta = 4;
		}
		else if (fAngle > 0.625 && fAngle <= 0.875)
		{
			tmeta = 2;
		}

		if (TFBlocks.cicada.canPlaceBlockAt(world, src[0], src[1], src[2]))
		{
			setBlockAndMetadata(world, src[0], src[1], src[2], TFBlocks.cicada, tmeta);
		}
	}


}