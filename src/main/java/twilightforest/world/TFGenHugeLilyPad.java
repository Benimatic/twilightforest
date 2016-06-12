package twilightforest.world;

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
public class TFGenHugeLilyPad extends WorldGenerator
{

	private Random rand = new Random();


    public boolean generate(World world, Random random, int x, int y, int z)
    {
    	for (int i = 0; i < 10; i++) {
    		int dx = x + random.nextInt(8) - random.nextInt(8);
    		int dy = y + random.nextInt(4) - random.nextInt(4);
    		int dz = z + random.nextInt(8) - random.nextInt(8);

    		dx = (dx >> 1) << 1;
    		dz = (dz >> 1) << 1;

    		if (shouldPlacePadAt(world, dx, dy, dz)) {
    			// this seems like a difficult way to generate 2 pseudorandom bits
    			rand .setSeed(8890919293L);
    			rand.setSeed((dx * rand.nextLong()) ^ (dz * rand.nextLong()) ^ 8890919293L);
    			int orient = rand.nextInt(4) << 2;

    			world.setBlock(dx, dy, dz, TFBlocks.hugeLilyPad, 0 | orient, 2);
    			world.setBlock(dx + 1, dy, dz, TFBlocks.hugeLilyPad, 1 | orient, 2);
    			world.setBlock(dx + 1, dy, dz + 1, TFBlocks.hugeLilyPad, 2 | orient, 2);
    			world.setBlock(dx, dy, dz + 1, TFBlocks.hugeLilyPad, 3 | orient, 2);
    		}
    	}

        return true;
    }


	private boolean shouldPlacePadAt(World world, int dx, int dy, int dz) {
		return world.isAirBlock(dx, dy, dz) && world.getBlock(dx, dy - 1, dz).getMaterial() == Material.WATER
				&& world.isAirBlock(dx + 1, dy, dz) && world.getBlock(dx + 1, dy - 1, dz).getMaterial() == Material.WATER
				&& world.isAirBlock(dx, dy, dz + 1) && world.getBlock(dx, dy - 1, dz + 1).getMaterial() == Material.WATER
				&& world.isAirBlock(dx + 1, dy, dz + 1) && world.getBlock(dx + 1, dy - 1, dz + 1).getMaterial() == Material.WATER;
	}
}
