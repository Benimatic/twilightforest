package twilightforest.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;


/**
 * This is a copypasta of the sand/gravel/clay generator that produces mycelium blobs for mushroom biomes
 * 
 * @author Ben
 *
 */
public class TFGenMyceliumBlob extends WorldGenerator
{
    private IBlockState myceliumState;
    private int numberOfBlocks;

    public TFGenMyceliumBlob(int i)
    {
    	this(Blocks.MYCELIUM, i);
    }

    public TFGenMyceliumBlob(Block block, int i)
    {
    	myceliumState = block.getDefaultState();
        numberOfBlocks = i;
    }

    @Override
    public boolean generate(World world, Random random, BlockPos pos)
    {
//        if (world.getBlock(i, j, k).getMaterial() != Material.WATER)
//        {
//            return false;
//        }
        int range = random.nextInt(numberOfBlocks - 2) + 2;
        int yRange = 1;
        for (int dx = x - range; dx <= x + range; dx++)
        {
            for (int dz = z - range; dz <= z + range; dz++)
            {
                int l1 = dx - x;
                int i2 = dz - z;
                if (l1 * l1 + i2 * i2 > range * range)
                {
                    continue;
                }
                for (int dy = y - yRange; dy <= y + yRange; dy++)
                {
                    Block blockThere = world.getBlock(dx, dy, dz);
                    if (blockThere == Blocks.DIRT || blockThere == Blocks.GRASS || blockThere == Blocks.STONE)
                    {
                        world.setBlock(dx, dy, dz, myceliumState, 0, 2);
                    }
                }
            }
        }

        return true;
    }
}
