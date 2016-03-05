package twilightforest.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import twilightforest.block.BlockTFMagicLog;
import twilightforest.block.BlockTFMagicLogSpecial;
import twilightforest.block.BlockTFRoots;
import twilightforest.block.TFBlocks;


public class TFGenSortingTree extends TFGenerator 
{
	protected Block treeBlock = TFBlocks.magicLog;
	protected int treeMeta = BlockTFMagicLog.META_SORT;
	protected int branchMeta = treeMeta | 12;
	protected Block leafBlock = TFBlocks.magicLeaves;
	protected int leafMeta = 3;
	protected Block rootBlock = TFBlocks.root;
	protected int rootMeta = BlockTFRoots.ROOT_META;
	
	public TFGenSortingTree() 
	{
		this(false);
	}


	public TFGenSortingTree(boolean notify) 
	{
		super(notify);
	}


	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		// check soil
		Material materialUnder = world.getBlock(x, y - 1, z).getMaterial();
		if ((materialUnder != Material.grass && materialUnder != Material.ground) || y >= TFWorld.MAXHEIGHT - 12)
		{
			return false;
		}
		
		// 3 block high trunk
		for (int dy = 0; dy < 4; dy++)
		{
			setBlockAndMetadata(world, x, y + dy, z, treeBlock, treeMeta);
		}
		
		// leaves
		putLeaves(world, x, y + 2, z, false);
		putLeaves(world, x, y + 3, z, false);
		
		// sorting engine
		setBlockAndMetadata(world, x, y + 1, z, TFBlocks.magicLogSpecial, BlockTFMagicLogSpecial.META_SORT);

		return true;
	}

	
	protected void putLeaves(World world, int bx, int by, int bz, boolean bushy) {
		for (int lx = -1; lx <= 1; lx++)
		{
			for (int ly = -1; ly <= 1; ly++)
			{
				for (int lz = -1; lz <= 1; lz++)
				{
					if (!bushy && Math.abs(ly) > 0 && (Math.abs(lx) + Math.abs(lz)) > 1)
					{
						continue;
					}
					putLeafBlock(world, bx + lx, by + ly, bz + lz, leafBlock, leafMeta);
				}
			}
		}
	}

}
