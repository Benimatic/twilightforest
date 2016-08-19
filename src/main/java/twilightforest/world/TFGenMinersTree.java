package twilightforest.world;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import twilightforest.block.BlockTFMagicLogSpecial;
import twilightforest.block.BlockTFRoots;
import twilightforest.block.TFBlocks;


public class TFGenMinersTree extends TFTreeGenerator 
{
	
	public TFGenMinersTree() 
	{
		this(false);
	}


	public TFGenMinersTree(boolean notify) 
	{
		super(notify);
		this.treeBlock = TFBlocks.magicLog;
		this.treeMeta = 2;
		this.branchMeta = treeMeta | 12;
		this.leafBlock = TFBlocks.magicLeaves;
		this.leafMeta = 2;
		this.rootBlock = TFBlocks.root;
		this.rootMeta = BlockTFRoots.ROOT_META;
	}


	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) 
	{
		
		// check soil
		Material materialUnder = world.getBlock(x, y - 1, z).getMaterial();
		if ((materialUnder != Material.grass && materialUnder != Material.ground) || y >= TFWorld.MAXHEIGHT - 12)
		{
			return false;
		}
		
		// 9 block high trunk
		for (int dy = 0; dy < 10; dy++)
		{
			setBlockAndMetadata(world, x, y + dy, z, treeBlock, branchMeta);
		}
		
		// branches with leaf blocks
		putBranchWithLeaves(world, x, y + 9, z + 1, true);
		putBranchWithLeaves(world, x, y + 9, z + 2, false);
		putBranchWithLeaves(world, x, y + 8, z + 3, false);
		putBranchWithLeaves(world, x, y + 7, z + 4, false);
		putBranchWithLeaves(world, x, y + 6, z + 5, false);

		putBranchWithLeaves(world, x, y + 9, z - 1, true);
		putBranchWithLeaves(world, x, y + 9, z - 2, false);
		putBranchWithLeaves(world, x, y + 8, z - 3, false);
		putBranchWithLeaves(world, x, y + 7, z - 4, false);
		putBranchWithLeaves(world, x, y + 6, z - 5, false);

		// place minewood core
		setBlockAndMetadata(world, x, y + 1, z, TFBlocks.magicLogSpecial, BlockTFMagicLogSpecial.META_MINE);
		world.scheduleBlockUpdate(x, y + 1, z, TFBlocks.magicLogSpecial, TFBlocks.magicLogSpecial.tickRate(world));

		
		// root bulb
		if (hasAirAround(world, x, y - 1, z)) {
			this.setBlockAndMetadata(world, x, y - 1, z, treeBlock, treeMeta);
		}
		else {
			this.setBlockAndMetadata(world, x, y - 1, z, rootBlock, rootMeta);
		}

		// roots!
		int numRoots = 3 + rand.nextInt(2);
		double offset = rand.nextFloat();
		for (int b = 0; b < numRoots; b++)
		{
			buildRoot(world, x, y, z, offset, b);
		}
		
		return true;
	}


	protected void putBranchWithLeaves(World world, int bx, int by, int bz, boolean bushy) {
		setBlockAndMetadata(world, bx, by, bz, treeBlock, branchMeta);
		
		for (int lx = -1; lx <= 1; lx++)
		{
			for (int ly = -1; ly <= 1; ly++)
			{
				for (int lz = -1; lz <= 1; lz++)
				{
					if (!bushy && Math.abs(ly) > 0 && Math.abs(lx) > 0)
					{
						continue;
					}
					putLeafBlock(world, bx + lx, by + ly, bz + lz, leafBlock, leafMeta);
				}
			}
		}
	}


}
