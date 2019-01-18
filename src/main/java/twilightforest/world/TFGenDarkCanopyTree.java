package twilightforest.world;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import twilightforest.block.BlockTFRoots;
import twilightforest.block.TFBlocks;




/**
 * Makes large trees with flat leaf ovals that provide a canopy for the forest 
 * 
 * @author Ben
 *
 */
public class TFGenDarkCanopyTree extends TFTreeGenerator {


    public TFGenDarkCanopyTree()
    {
        this(false);
    }    
    
    public TFGenDarkCanopyTree(boolean par1)
    {
        super(par1);
    	treeBlock = TFBlocks.log;
    	treeMeta = 3;
    	branchMeta = 15;
    	leafBlock = TFBlocks.darkleaves;
    	leafMeta = 0;
    	rootBlock = TFBlocks.root;
    	rootMeta = BlockTFRoots.ROOT_META;
    }
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		// if we are given leaves as a starting position, seek dirt or grass underneath
		boolean foundDirt = false;
		Material materialUnder;
		for (int dy = y; dy >= TFWorld.SEALEVEL; dy--) {
			materialUnder = world.getBlock(x, dy - 1, z).getMaterial();
			if (materialUnder == Material.grass || materialUnder == Material.ground)
			{
				// yes!
				foundDirt = true;
				y = dy;
				break;
			}
			else if (materialUnder == Material.rock || materialUnder == Material.sand) {
				// nope
				break;
			}
		}
		
		if (!foundDirt) {
			return false;
		}
		
		// do not grow next to another tree
		if (world.getBlock(x + 1, y, z + 0).getMaterial() == Material.wood || world.getBlock(x - 1, y, z + 0).getMaterial() == Material.wood 
				|| world.getBlock(x + 0, y, z + 1).getMaterial() == Material.wood || world.getBlock(x + 0, y, z - 1).getMaterial() == Material.wood) {
			return false;
		}

		// determine a height
		int treeHeight = 6 + random.nextInt(5);
		
		//okay build a tree!  trunk here
		drawBresehnam(world, x, y, z, x, y + treeHeight, z, treeBlock, treeMeta);
		leafAround(world, x, y + treeHeight, z);
		
		// make 4 branches
		int numBranches = 4;
		double offset = random.nextFloat();
		for (int b = 0; b < numBranches; b++)
		{
			buildBranch(world, x, y, z, treeHeight - 3 - numBranches + (b / 2), 10 + random.nextInt(4), 0.23 * b + offset, 0.23, random);
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
	void buildBranch(World world, int x, int y, int z, int height, double length, double angle, double tilt, Random random)
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
		
		drawBresehnam(world, src.posX, src.posY, src.posZ, dest.posX, dest.posY, dest.posZ, treeBlock, branchMeta);

		if (Math.abs(x - dest.posX) + 2 > 7 || Math.abs(z - dest.posZ) + 2 > 7 )
		{
			cpw.mods.fml.common.FMLLog.info("getting branch too far.  x = " + (x - dest.posX + 2) + ", z = " + (z - dest.posZ + 2));
		}
		
		leafAround(world, dest.posX, dest.posY, dest.posZ);

	}

	/**
	 * Make our leaf pattern
	 */
	private void leafAround(World world, int dx, int dy, int dz) {
		int leafSize = 4;
		
		// only leaf if there are no leaves by where we are thinking of leafing
		if (hasAirAround(world, dx, dy, dz)) 
		{
			makeLeafCircle(world, dx, dy - 1, dz, leafSize, leafBlock, leafMeta);	
			makeLeafCircle(world, dx, dy, dz, leafSize + 1, leafBlock, leafMeta); 
			makeLeafCircle(world, dx, dy + 1, dz, leafSize, leafBlock, leafMeta);
			makeLeafCircle(world, dx, dy + 2, dz, leafSize - 2, leafBlock, leafMeta);
		}
	}

}
