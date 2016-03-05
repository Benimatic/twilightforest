package twilightforest.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import twilightforest.block.BlockTFRoots;
import twilightforest.block.TFBlocks;

public class TFGenSmallTwilightOak extends TFTreeGenerator
{
    /** The minimum height of a generated tree. */
	protected final int minTreeHeight;

    public TFGenSmallTwilightOak(boolean par1)
    {
        this(par1, 4);
    }

    public TFGenSmallTwilightOak(boolean par1, int par2)
    {
        super(par1);
        this.minTreeHeight = par2;
        
    	treeBlock = TFBlocks.log;
    	treeMeta = 0;
    	branchMeta = treeMeta | 12;
    	leafBlock = TFBlocks.leaves;
    	leafMeta = 0;
    	rootBlock = TFBlocks.root;
    	rootMeta = BlockTFRoots.ROOT_META;
    }

    public boolean generate(World par1World, Random par2Random, int x, int y, int z)
    {
        int height = par2Random.nextInt(3) + this.minTreeHeight;
        boolean allClear = true;

        if (y >= 1 && y + height + 1 <= 256)
        {
            int cy;
            byte width;
            int cz;
            Block blockID;

            for (cy = y; cy <= y + 1 + height; ++cy)
            {
                width = 1;

                if (cy == y)
                {
                    width = 0;
                }

                if (cy >= y + 1 + height - 2)
                {
                    width = 2;
                }

                for (int cx = x - width; cx <= x + width && allClear; ++cx)
                {
                    for (cz = z - width; cz <= z + width && allClear; ++cz)
                    {
                        if (cy >= 0 && cy < 256)
                        {
                            blockID = par1World.getBlock(cx, cy, cz);

                            Block block = blockID;

                            if (blockID != Blocks.air &&
                               !block.isLeaves(par1World, cx, cy, cz) &&
                                blockID != Blocks.grass &&
                                blockID != Blocks.dirt &&
                               !block.isWood(par1World, cx, cy, cz))
                            {
                                allClear = false;
                            }
                        }
                        else
                        {
                            allClear = false;
                        }
                    }
                }
            }

            if (!allClear)
            {
                return false;
            }
            else
            {
                Block blockUsing = par1World.getBlock(x, y - 1, z);

                if ((blockUsing == Blocks.grass || blockUsing == Blocks.dirt) && y < 256 - height - 1)
                {
                    this.setBlock(par1World, x, y - 1, z, Blocks.dirt);
                    width = 3;
                    byte var18 = 0;
                    int treeWidth;
                    int tx;
                    int var15;

                    for (cz = y - width + height; cz <= y + height; ++cz)
                    {
                        int number = cz - (y + height);
                        treeWidth = var18 + 1 - number / 2;

                        for (tx = x - treeWidth; tx <= x + treeWidth; ++tx)
                        {
                            var15 = tx - x;

                            for (int tz = z - treeWidth; tz <= z + treeWidth; ++tz)
                            {
                                int var17 = tz - z;

                                Block block = par1World.getBlock(tx, cz, tz);

                                if ((Math.abs(var15) != treeWidth || Math.abs(var17) != treeWidth || par2Random.nextInt(2) != 0 && number != 0) &&
                                    (block == null || block.canBeReplacedByLeaves(par1World, tx, cz, tz)))
                                {
                                    this.setBlockAndMetadata(par1World, tx, cz, tz, this.leafBlock, this.leafMeta);
                                }
                            }
                        }
                    }

                    for (cz = 0; cz < height; ++cz)
                    {
                        blockID = par1World.getBlock(x, y + cz, z);

                        Block block = blockID;

                        if (blockID == Blocks.air || block == null || block.isLeaves(par1World, x, y + cz, z))
                        {
                            this.setBlockAndMetadata(par1World, x, y + cz, z, this.treeBlock, this.treeMeta);
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }
}
