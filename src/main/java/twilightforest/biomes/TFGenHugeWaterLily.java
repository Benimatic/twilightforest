package twilightforest.biomes;

import java.util.Random;

import twilightforest.block.TFBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;


/**
 * Generate huge lily pads
 * 
 * @author Ben
 *
 */
public class TFGenHugeWaterLily extends WorldGenerator
{

	private Random rand = new Random();


    public boolean generate(World world, Random random, int x, int y, int z)
    {
    	for (int i = 0; i < 4; i++) {
    		int dx = x + random.nextInt(8) - random.nextInt(8);
    		int dy = y + random.nextInt(4) - random.nextInt(4);
    		int dz = z + random.nextInt(8) - random.nextInt(8);

    		if (shouldPlacePadAt(world, dx, dy, dz)) {
    			world.setBlock(dx, dy, dz, TFBlocks.hugeWaterLily);
    		}
    	}

        return true;
    }


	private boolean shouldPlacePadAt(World world, int dx, int dy, int dz) {
		return world.isAirBlock(dx, dy, dz) && world.getBlock(dx, dy - 1, dz).getMaterial() == Material.WATER;
	}
}
