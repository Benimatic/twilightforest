package twilightforest.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.block.TFBlocks;


/**
 * Makes large mushrooms with flat mushroom tops that provide a canopy for the forest 
 * 
 * @author Ben
 *
 */
public class TFGenCanopyMushroom extends TFTreeGenerator {

    public TFGenCanopyMushroom()
    {
        this(false);
    }    
    
    public TFGenCanopyMushroom(boolean par1)
    {
    	super(par1);
    	treeBlock = Blocks.RED_MUSHROOM_BLOCK;
    	treeMeta = 10;
    	branchMeta = 15;
    	leafBlock = Blocks.RED_MUSHROOM_BLOCK;
    	leafMeta = 5;
    }
	
	@Override
	public boolean generate(World world, Random random, BlockPos pos)
	{
		// determine a height
		int treeHeight = 12;
		if (random.nextInt(3) == 0) {
			treeHeight += random.nextInt(5);
			
			if (random.nextInt(8) == 0) {
				treeHeight += random.nextInt(5);
			}
		}
		
		// check if we're on dirt or grass
		Block blockUnder = world.getBlockState(pos.down()).getBlock();
		if(blockUnder != Blocks.GRASS && blockUnder != Blocks.DIRT && blockUnder != Blocks.MYCELIUM || y >= 256 - treeHeight - 1)
		{
			return false;
		}

		
		this.treeBlock = random.nextInt(3) == 0 ? Blocks.RED_MUSHROOM_BLOCK :  Blocks.BROWN_MUSHROOM_BLOCK;
		this.leafBlock = treeBlock;

		//okay build a tree!  Go up to the height
		buildBranch(world, x, y, z, 0, treeHeight, 0, 0, true, random);
		
		// make 3-4 branches
		int numBranches = 3 + random.nextInt(2);
		double offset = random.nextDouble();
		for (int b = 0; b < numBranches; b++)
		{
			buildBranch(world, x, y, z, treeHeight - 5 + b, 9, 0.3 * b + offset, 0.2, false, random);
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
	private void buildBranch(World world, BlockPos pos, int height, double length, double angle, double tilt, boolean trunk, Random treeRNG)
	{
		BlockPos src = pos.up(height);
		BlockPos dest = translateCoords(src.getX(), src.getY(), src.getZ(), length, angle, tilt);
		
		// constrain branch spread
		if ((dest.getX() - pos.getX()) < -4)
		{
			dest.getX() = x - 4;
		}
		if ((dest.getX() - x) > 4)
		{
			dest.getX() = x + 4;
		}
		if ((dest.getZ() - z) < -4)
		{
			dest.getZ() = z - 4;
		}
		if ((dest.getZ() - z) > 4)
		{
			dest.getZ() = z + 4;
		}
		
		if (src.getX() != dest.getX() || src.getZ() != dest.getZ()) {
			// branch
			drawBresehnam(world, src.getX(), src.getY(), src.getZ(), dest.getX(), src.getY(), dest.getZ(), treeBlock, branchMeta);
			drawBresehnam(world, dest.getX(), src.getY() + 1, dest.getZ(), dest.getX(), dest.getY() - 1, dest.getZ(), treeBlock, treeMeta);
		}
		else {
			// trunk
			drawBresehnam(world, src.getX(), src.getY(), src.getZ(), dest.getX(), dest.getY() - 1, dest.getZ(), treeBlock, treeMeta);
		}
		
		// do this here until that bug with the lighting is fixed
		if (trunk) {
			// add a firefly (torch) to the trunk
			addFirefly(world, x, y, z, 3 + treeRNG.nextInt(7), treeRNG.nextDouble());
		}
		
		drawMushroomCircle(world, dest.getX(), dest.getY(), dest.getZ(), 4, leafBlock);	
	}
	
	/**
	 * Draw a flat blob (a circle?) of whatevs (I'm guessing... leaves).
	 * @param sx
	 * @param sy
	 * @param sz
	 * @param rad radius
	 * @param blockValue
	 * @param metaValue
	 * @param priority do we overwrite what's there?
	 */
	private void drawMushroomCircle(World world, int sx, int sy, int sz, int rad, Block blockValue)
	{
		// trace out a quadrant
		for (byte dx = 0; dx <= rad; dx++)
		{
			for (byte dz = 0; dz <= rad; dz++)
			{
				int dist = (int)(Math.max(dx, dz) + (Math.min(dx, dz) * 0.5));

				//hack!  I keep getting failing leaves at a certain position.
				if (dx == 3 && dz == 3) {
					dist = 6;
				}
				
				// if we're inside the blob, fill it
				if (dx == 0) {
					// two!
					if (dz < rad) {
						setBlockAndNotifyAdequately(world, sx + 0, sy, sz + dz, blockValue, 5);
						setBlockAndNotifyAdequately(world, sx + 0, sy, sz - dz, blockValue, 5);
					}
					else {
						setBlockAndNotifyAdequately(world, sx + 0, sy, sz + dz, blockValue, 8);
						setBlockAndNotifyAdequately(world, sx + 0, sy, sz - dz, blockValue, 2);
					}
				}
				else if (dz == 0) {
					// two!
					if (dx < rad) {
						setBlockAndNotifyAdequately(world, sx + dx, sy, sz + 0, blockValue, 5);
						setBlockAndNotifyAdequately(world, sx - dx, sy, sz + 0, blockValue, 5);
					}
					else {
						setBlockAndNotifyAdequately(world, sx + dx, sy, sz + 0, blockValue, 6);
						setBlockAndNotifyAdequately(world, sx - dx, sy, sz + 0, blockValue, 4);
					}
				}
				else if (dist < rad) {
					// do four at a time for easiness!
					setBlockAndNotifyAdequately(world, sx + dx, sy, sz + dz, blockValue, 5);
					setBlockAndNotifyAdequately(world, sx + dx, sy, sz - dz, blockValue, 5);
					setBlockAndNotifyAdequately(world, sx - dx, sy, sz + dz, blockValue, 5);
					setBlockAndNotifyAdequately(world, sx - dx, sy, sz - dz, blockValue, 5);
				}
				else if (dist == rad) {
					// do four at a time for easiness!
					setBlockAndNotifyAdequately(world, sx + dx, sy, sz + dz, blockValue, 9);
					setBlockAndNotifyAdequately(world, sx + dx, sy, sz - dz, blockValue, 3);
					setBlockAndNotifyAdequately(world, sx - dx, sy, sz + dz, blockValue, 7);
					setBlockAndNotifyAdequately(world, sx - dx, sy, sz - dz, blockValue, 1);
				}
			}
		}
	}

	
	/**
	 * Add a firefly at the specified height and angle.
	 * 
	 * @param height how far up the tree
	 * @param angle from 0 - 1 rotation around the tree
	 */
	private void addFirefly(World world, BlockPos pos, int height, double angle)
	{
		int iAngle = (int)(angle * 4.0);
		if (iAngle == 0)
		{
			setBlockAndNotifyAdequately(world, pos.east().up(height), TFBlocks.firefly.getDefaultState());
		}
		else if (iAngle == 1)
		{
			setBlockAndNotifyAdequately(world, pos.west().up(height), TFBlocks.firefly.getDefaultState());
		}
		else if (iAngle == 2)
		{
			setBlockAndNotifyAdequately(world, pos.south().up(height), TFBlocks.firefly.getDefaultState());
		}
		else if (iAngle == 3)
		{
			setBlockAndNotifyAdequately(world, pos.north().up(height), TFBlocks.firefly.getDefaultState());
		}
	}

}
