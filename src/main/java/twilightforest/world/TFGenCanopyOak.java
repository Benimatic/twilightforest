package twilightforest.world;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import twilightforest.block.BlockTFRoots;
import twilightforest.block.TFBlocks;

public class TFGenCanopyOak extends TFGenCanopyTree {
	
    public TFGenCanopyOak()
    {
        this(false);
    }    
    
    public TFGenCanopyOak(boolean par1)
    {
    	super(par1);
    	this.treeBlock = TFBlocks.log;
    	this.treeMeta = 0;
    	this.branchMeta = 12;
    	this.leafBlock = TFBlocks.leaves;
    	this.leafMeta = 0;
    	this.rootBlock = TFBlocks.root;
    	this.rootMeta = BlockTFRoots.ROOT_META;
    	
    }
    
	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		int treeHeight;
		
		// check if we're on dirt or grass
		Material materialUnder = world.getBlock(x, y - 1, z).getMaterial();
		if ((materialUnder != Material.grass && materialUnder != Material.ground) || y >= TFWorld.MAXHEIGHT - 12)
		{
			return false;
		}


		// determine a height
		treeHeight = minHeight;
		if (random.nextInt(chanceAddFirstFive) == 0) {
			treeHeight += random.nextInt(5);
			
			if (random.nextInt(chanceAddSecondFive) == 0) {
				treeHeight += random.nextInt(5);
			}
		}
		
		//okay build a tree!  Go up to the height
		buildTrunk(world, x, y, z, treeHeight);

		// make 12 - 20 branches
		int numBranches = 12 + random.nextInt(9);
		float bangle = random.nextFloat();
		for (int b = 0; b < numBranches; b++) {
			float btilt = 0.15F + (random.nextFloat() * 0.35F);
			buildBranch(world, x, y, z, treeHeight - 10 + (b / 2), 5, bangle, btilt, false, random);
			
			bangle += (random.nextFloat() * 0.4F);
			if (bangle > 1.0F) {
				bangle -= 1.0F;
			}
		}
		
		makeRoots(world, random, x + 0, y, z + 0);
		makeRoots(world, random, x + 1, y, z + 0);
		makeRoots(world, random, x + 0, y, z + 1);
		makeRoots(world, random, x + 1, y, z + 1);
		
		
		return true;
	}

	private void makeRoots(World world, Random random, int x, int y, int z) {
		// root bulb
		if (hasAirAround(world, x, y - 1, z)) {
			this.setBlockAndMetadata(world, x, y - 1, z, treeBlock, treeMeta);
		}
		else {
			this.setBlockAndMetadata(world, x, y - 1, z, rootBlock, rootMeta);
		}

		// roots!
		int numRoots = 1 + random.nextInt(2);
		float offset = random.nextFloat();
		for (int b = 0; b < numRoots; b++)
		{
			buildRoot(world, x, y, z, offset, b);
		}
	}
    
	private void buildTrunk(World world, int sx, int sy, int sz, int treeHeight) {
		for (int dy = 0; dy < treeHeight; dy++) {
			this.setBlockAndMetadata(world, sx + 0, sy + dy, sz + 0, this.treeBlock, this.treeMeta);
			this.setBlockAndMetadata(world, sx + 1, sy + dy, sz + 0, this.treeBlock, this.treeMeta);
			this.setBlockAndMetadata(world, sx + 0, sy + dy, sz + 1, this.treeBlock, this.treeMeta);
			this.setBlockAndMetadata(world, sx + 1, sy + dy, sz + 1, this.treeBlock, this.treeMeta);
		}
		
		drawLeafBlob(world, sx + 0, sy + treeHeight, sz + 0, 3, leafBlock, leafMeta);	

	}

	/**
	 * Build a branch with a flat blob of leaves at the end.
	 * 
	 * @param height
	 * @param length
	 * @param angle
	 * @param tilt
	 */
	void buildBranch(World world, int x, int y, int z, int height, double length, double angle, double tilt, boolean trunk, Random treeRNG)
	{
		BlockPos src = new BlockPos(x, y + height, z);
		BlockPos dest = translateCoords(src.posX, src.posY, src.posZ, length, angle, tilt);
		
		// constrain branch spread
		int limit = 5;
		if ((dest.posX - x) < -limit)
		{
			dest.posX = x - limit;
		}
		if ((dest.posX - x) > limit)
		{
			dest.posX = x + limit;
		}
		if ((dest.posZ - z) < -limit)
		{
			dest.posZ = z - limit;
		}
		if ((dest.posZ - z) > limit)
		{
			dest.posZ = z + limit;
		}
		
		drawBresehnam(world, src.posX, src.posY, src.posZ, dest.posX, dest.posY, dest.posZ, treeBlock, trunk ? treeMeta : branchMeta);
	
		// do this here until that bug with the lighting is fixed
		if (trunk) 
		{
			// add a firefly (torch) to the trunk
			addFirefly(world, x, y, z, 3 + treeRNG.nextInt(7), treeRNG.nextDouble());
		}
		
		int blobSize = 2;// + treeRNG.nextInt(2);

		drawLeafBlob(world, dest.posX, dest.posY, dest.posZ, blobSize, leafBlock, leafMeta);	

//		makeLeafCircle(world, dest.posX, dest.posY - 1, dest.posZ, 3, leafBlock, leafMeta, true);	
//		makeLeafCircle(world, dest.posX, dest.posY, dest.posZ, 4, leafBlock, leafMeta, true);	
//		makeLeafCircle(world, dest.posX, dest.posY + 1, dest.posZ, 2, leafBlock, leafMeta, true);	
		
		setBlockAndMetadata(world, dest.posX + 1, dest.posY, dest.posZ, treeBlock, branchMeta);
		setBlockAndMetadata(world, dest.posX - 1, dest.posY, dest.posZ, treeBlock, branchMeta);
		setBlockAndMetadata(world, dest.posX, dest.posY, dest.posZ + 1, treeBlock, branchMeta);
		setBlockAndMetadata(world, dest.posX, dest.posY, dest.posZ - 1, treeBlock, branchMeta);
		
	}
}