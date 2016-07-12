package twilightforest.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
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
	public boolean generate(World world, Random rand, BlockPos pos) {
		// check soil
		Material materialUnder = world.getBlockState(pos.down()).getMaterial();
		if ((materialUnder != Material.GRASS && materialUnder != Material.GROUND) || pos.getY() >= TFWorld.MAXHEIGHT - 12)
		{
			return false;
		}
		
		// 3 block high trunk
		for (int dy = 0; dy < 4; dy++)
		{
			setBlockAndNotifyAdequately(world, pos.up(dy), treeBlock, treeMeta);
		}
		
		// leaves
		putLeaves(world, pos.up(2), false);
		putLeaves(world, pos.up(3), false);
		
		// sorting engine
		setBlockAndNotifyAdequately(world, pos.up(), TFBlocks.magicLogSpecial, BlockTFMagicLogSpecial.META_SORT);

		return true;
	}

	private void putLeaves(World world, BlockPos pos, boolean bushy) {
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
					putLeafBlock(world, pos.add(lx, ly, lz), leafBlock, leafMeta);
				}
			}
		}
	}

}
