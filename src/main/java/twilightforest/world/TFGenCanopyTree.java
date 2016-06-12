package twilightforest.world;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import twilightforest.block.BlockTFRoots;
import twilightforest.block.TFBlocks;




/**
 * Makes large trees with flat leaf ovals that provide a canopy for the forest 
 * 
 * @author Ben
 *
 */
public class TFGenCanopyTree extends TFTreeGenerator {
	
	protected int minHeight = 20;
	protected int chanceAddFirstFive = 3;
	protected int chanceAddSecondFive = 8;
	
	
    public TFGenCanopyTree()
    {
        this(false);
    }    
    
    public TFGenCanopyTree(boolean par1)
    {
    	super(par1);
    	treeBlock = TFBlocks.log;
    	treeMeta = 1;
    	branchMeta = 13;
    	leafBlock = TFBlocks.leaves;
    	leafMeta = 1;
    	rootBlock = TFBlocks.root;
    	rootMeta = BlockTFRoots.ROOT_META;
    	
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
		buildBranch(world, x, y, z, 0, treeHeight, 0, 0, true, random);
		
		// make 3-4 branches
		int numBranches = 3 + random.nextInt(2);
		float offset = random.nextFloat();
		for (int b = 0; b < numBranches; b++)
		{
			buildBranch(world, x, y, z, treeHeight - 10 + b, 9, 0.3 * b + offset, 0.2, false, random);
		}
		
		// root bulb
		if (hasAirAround(world, x, y - 1, z)) {
			this.setBlockAndMetadata(world, x, y - 1, z, treeBlock, treeMeta);
		}
		else {
			this.setBlockAndMetadata(world, x, y - 1, z, rootBlock, rootMeta);
		}

		// roots!
		int numRoots = 3 + random.nextInt(2);
		offset = random.nextFloat();
		for (int b = 0; b < numRoots; b++)
		{
			buildRoot(world, x, y, z, offset, b);
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
	void buildBranch(World world, int x, int y, int z, int height, double length, double angle, double tilt, boolean trunk, Random treeRNG)
	{
		BlockPos src = new BlockPos(x, y + height, z);
		BlockPos dest = translateCoords(src.posX, src.posY, src.posZ, length, angle, tilt);
		
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
	
		// do this here until that bug with the lighting is fixed
		if (trunk) 
		{
			// add a firefly (torch) to the trunk
			addFirefly(world, x, y, z, 3 + treeRNG.nextInt(7), treeRNG.nextDouble());
		}

		makeLeafCircle(world, dest.posX, dest.posY - 1, dest.posZ, 3, leafBlock, leafMeta, true);	
		makeLeafCircle(world, dest.posX, dest.posY, dest.posZ, 4, leafBlock, leafMeta, true);	
		makeLeafCircle(world, dest.posX, dest.posY + 1, dest.posZ, 2, leafBlock, leafMeta, true);	
		
		setBlockAndMetadata(world, dest.posX + 1, dest.posY, dest.posZ, treeBlock, branchMeta);
		setBlockAndMetadata(world, dest.posX - 1, dest.posY, dest.posZ, treeBlock, branchMeta);
		setBlockAndMetadata(world, dest.posX, dest.posY, dest.posZ + 1, treeBlock, branchMeta);
		setBlockAndMetadata(world, dest.posX, dest.posY, dest.posZ - 1, treeBlock, branchMeta);
		
	}
	
	/**
	 * Add a firefly at the specified height and angle.
	 * 
	 * @param height how far up the tree
	 * @param angle from 0 - 1 rotation around the tree
	 */
	protected void addFirefly(World world, int x, int y, int z, int height, double angle)
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
