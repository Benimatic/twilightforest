package twilightforest.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import twilightforest.block.BlockTFRoots;
import twilightforest.block.TFBlocks;

public class TFGenLargeWinter extends TFTreeGenerator {
	
    public TFGenLargeWinter()
    {
        this(false);
    }    
    
    public TFGenLargeWinter(boolean par1)
    {
    	super(par1);
    	treeBlock = Blocks.LOG;
    	treeMeta = 1;
    	branchMeta = 13;
    	leafBlock = Blocks.LEAVES;
    	leafMeta = 1;
    	rootBlock = TFBlocks.root;
    	rootMeta = BlockTFRoots.ROOT_META;
    	
    }

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		// determine a height
		int treeHeight = 35;
		if (random.nextInt(3) == 0) {
			treeHeight += random.nextInt(10);
			
			if (random.nextInt(8) == 0) {
				treeHeight += random.nextInt(10);
			}
		}
		
		// check if we're on dirt or grass
		Block blockUnder = world.getBlock(x, y - 1, z);
		if(blockUnder != Blocks.GRASS && blockUnder != Blocks.DIRT || y >= TFWorld.MAXHEIGHT - treeHeight)
		{
			return false;
		}

		
		//okay build a tree!  Go up to the height
		buildTrunk(world, x, y, z, treeHeight);
		
		// make leaves
		makeLeaves(world, x, y, z, treeHeight);
		
		// roots!
		int numRoots = 4 + random.nextInt(3);
		float offset = random.nextFloat();
		for (int b = 0; b < numRoots; b++)
		{
			buildRoot(world, x, y, z, offset, b);
		}
		
		return true;
	}

	private void makeLeaves(World world, int x, int y, int z, int treeHeight) {
		int offGround = 3;
		int leafType = 1;
		
		for (int dy = 0; dy < treeHeight; dy++) {

			int radius = leafRadius(treeHeight, dy, leafType);
			
			this.makeLeafCircle2(world, x, y + offGround + treeHeight - dy, z, radius, leafBlock, leafMeta, false);
			this.makePineBranches(world, x, y + offGround + treeHeight - dy, z, radius);
		}
	}

	private void makePineBranches(World world, int x, int y, int z, int radius) {
		int branchLength = radius > 4 ? radius - 1 : radius - 2;
		
		switch (y % 2)
		{
		case 0:
			// branches
			for (int i = 1; i <= branchLength; i++)
			{
				this.setBlockAndMetadata(world, x + 0 - i, y, z + 0, treeBlock, branchMeta & 3 | 4);
				this.setBlockAndMetadata(world, x + 0, y, z + 1 + i, treeBlock, branchMeta & 3 | 8);
				this.setBlockAndMetadata(world, x + 1 + i, y, z + 1, treeBlock, branchMeta & 3 | 4);
				this.setBlockAndMetadata(world, x + 1, y, z - 0 - i, treeBlock, branchMeta & 3 | 8);
			}
			break;
		case 1:
			for (int i = 1; i <= branchLength; i++)
			{
				this.setBlockAndMetadata(world, x + 0 - i, y, z + 1, treeBlock, branchMeta & 3 | 4);
				this.setBlockAndMetadata(world, x + 1, y, z + 1 + i, treeBlock, branchMeta & 3 | 8);
				this.setBlockAndMetadata(world, x + 1 + i, y, z + 0, treeBlock, branchMeta & 3 | 4);
				this.setBlockAndMetadata(world, x + 0, y, z - 0 - i, treeBlock, branchMeta & 3 | 8);
			}
			break;
		}
	}

	private int leafRadius(int treeHeight, int dy, int functionType) {
		switch (functionType) {
		case 0:
		default:
			return (dy - 1) % 4;
		case 1:
			return (int)(4F * (float)dy / (float)treeHeight + (0.75F * dy % 3));
		case 99:
			return (treeHeight - (dy / 2) - 1) % 4; // bad
		}
	}

	private void buildTrunk(World world, int x, int y, int z, int treeHeight) {
		for (int dy = 0; dy < treeHeight; dy++) {
			this.setBlockAndMetadata(world, x + 0, y + dy, z + 0, treeBlock, treeMeta);
			this.setBlockAndMetadata(world, x + 1, y + dy, z + 0, treeBlock, treeMeta);
			this.setBlockAndMetadata(world, x + 0, y + dy, z + 1, treeBlock, treeMeta);
			this.setBlockAndMetadata(world, x + 1, y + dy, z + 1, treeBlock, treeMeta);
		}
	}

}
