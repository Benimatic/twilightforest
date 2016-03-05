package twilightforest.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import twilightforest.block.BlockTFRoots;
import twilightforest.block.TFBlocks;


public class TFGenMangroveTree extends TFTreeGenerator {
	
	boolean checkForWater;
	
	
    public TFGenMangroveTree()
    {
        this(false);
    }    
    
    public TFGenMangroveTree(boolean par1)
    {
        super(par1);
        
        this.checkForWater = !par1; 
        
    	treeBlock = TFBlocks.log;
    	treeMeta = 2;
    	branchMeta = 14;
    	leafBlock = TFBlocks.leaves;
    	leafMeta = 2;
    	rootBlock = TFBlocks.root;
    	rootMeta = BlockTFRoots.ROOT_META;
    }
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		// we only start over water
		if ((this.checkForWater && world.getBlock(x, y - 1, z) != Blocks.water) || y >= 128 - 18 - 1)
		{
			return false;
		}
		
		//okay build a trunk!  Start 5 squares off the ground and go up maybe 6-9 squares
		buildBranch(world, random, x, y, z, 5, 6 + random.nextInt(3), 0, 0, true);
		
		// make 0-3 branches
		int numBranches = random.nextInt(3);
		double offset = random.nextDouble();
		for (int b = 0; b < numBranches; b++)
		{
			buildBranch(world, random, x, y, z, 7 + b, 6 + random.nextInt(2), 0.3 * b + offset, 0.25, false);
		}
		
		// make 3-5 roots
		int numRoots = 3 + random.nextInt(2);
		offset = random.nextDouble();
		for (int i = 0; i < numRoots; i++)
		{
			double rTilt = 0.75 + (random.nextDouble() * 0.1);
			buildRoot(world, x, y, z, 5, 12, 0.4 * i + offset, rTilt);
		}

		// add a firefly (torch) to the trunk
		addFirefly(world, x, y, z, 5 + random.nextInt(5), random.nextDouble());
		
		
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
	void buildBranch(World world, Random random, int x, int y, int z, int height, double length, double angle, double tilt, boolean trunk)
	{
		ChunkCoordinates src = new ChunkCoordinates(x, y + height, z);
		ChunkCoordinates dest = translateCoords(src.posX, src.posY, src.posZ, length, angle, tilt);
		
		
		// constrain branch spread
		if ((dest.posX - x) < -4)
		{
			dest.posX = x - 4;
		}
		if ((dest.posX - x) > 4)
		{
			dest.posX = x + 4;
		}
		if ((dest.posZ - z) < -4)
		{
			dest.posZ = z - 4;
		}
		if ((dest.posZ - z) > 4)
		{
			dest.posZ = z + 4;
		}
		
		drawBresehnam(world, src.posX, src.posY, src.posZ, dest.posX, dest.posY, dest.posZ, treeBlock, trunk ? treeMeta : branchMeta);
		
		
		// variable size leaves
		int bSize = 2 + random.nextInt(3);
		
		// we only need these side blocks if the size is > 2
		if (bSize > 2) {
			setBlockAndMetadata(world, dest.posX + 1, dest.posY, dest.posZ, treeBlock, branchMeta);
			setBlockAndMetadata(world, dest.posX - 1, dest.posY, dest.posZ, treeBlock, branchMeta);
			setBlockAndMetadata(world, dest.posX, dest.posY, dest.posZ + 1, treeBlock, branchMeta);
			setBlockAndMetadata(world, dest.posX, dest.posY, dest.posZ - 1, treeBlock, branchMeta);
		}
		// leaves!
		makeLeafCircle(world, dest.posX, dest.posY - 1, dest.posZ, (byte)(bSize - 1), leafBlock, leafMeta);	
		makeLeafCircle(world, dest.posX, dest.posY, dest.posZ, (byte)(bSize), leafBlock, leafMeta);	
		makeLeafCircle(world, dest.posX, dest.posY + 1, dest.posZ, (byte)(bSize - 2), leafBlock, leafMeta);	
	}
	
	/**
	 * Build a root.  (Which is really like a branch without the leaves)
	 * 
	 * @param height
	 * @param length
	 * @param angle
	 * @param tilt
	 */
	void buildRoot(World world, int x, int y, int z, int height, double length, double angle, double tilt)
	{
		ChunkCoordinates src = new ChunkCoordinates(x, y + height, z);
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
				this.placeRootBlock(world, coord.posX, coord.posY, coord.posZ, rootBlock, rootMeta);
				this.placeRootBlock(world, coord.posX, coord.posY - 1, coord.posZ, rootBlock, rootMeta);
				stillAboveGround = false;
			}
		}

	}

	/**
	 * Add a firefly at the specified height and angle.
	 * 
	 * @param height how far up the tree
	 * @param angle from 0 - 1 rotation around the tree
	 */
	private void addFirefly(World world, int x, int y, int z, int height, double angle)
	{
		int iAngle = (int)(angle * 4.0);
		if (iAngle == 0)
		{
			setBlockAndMetadata(world, x + 1, y + height, z, TFBlocks.firefly, 0);
		}
		else if (iAngle == 1)
		{
			setBlockAndMetadata(world, x - 1, y + height, z, TFBlocks.firefly, 0);
		}
		else if (iAngle == 2)
		{
			setBlockAndMetadata(world, x, y + height, z + 1, TFBlocks.firefly, 0);
		}
		else if (iAngle == 3)
		{
			setBlockAndMetadata(world, x, y + height, z - 1, TFBlocks.firefly, 0);
		}
	}
}
