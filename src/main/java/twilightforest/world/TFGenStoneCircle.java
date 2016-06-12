package twilightforest.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;


public class TFGenStoneCircle extends TFGenerator {

	
	/**
	 * We make a stone circle, generally 3 squares in diameter
	 */	
	public boolean generate(World world, Random rand, int x, int y, int z) {
		if (!isAreaSuitable(world, rand, x - 3, y, z - 3, 6, 4, 6))
		{
			return false;
		}
		
		// okay!  circle!
		for (int cy = 0; cy <= 2; cy++)
		{
			setBlock(world, x - 3, y + cy, z, Blocks.MOSSY_COBBLESTONE);
			setBlock(world, x + 3, y + cy, z, Blocks.MOSSY_COBBLESTONE);
			setBlock(world, x, y + cy, z - 3, Blocks.MOSSY_COBBLESTONE);
			setBlock(world, x, y + cy, z + 3, Blocks.MOSSY_COBBLESTONE);
			setBlock(world, x - 2, y + cy, z - 2, Blocks.MOSSY_COBBLESTONE);
			setBlock(world, x + 2, y + cy, z - 2, Blocks.MOSSY_COBBLESTONE);
			setBlock(world, x - 2, y + cy, z + 2, Blocks.MOSSY_COBBLESTONE);
			setBlock(world, x + 2, y + cy, z + 2, Blocks.MOSSY_COBBLESTONE);
		}
		
		
		return true;
	}

}
