package twilightforest.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class TFGenStoneCircle extends TFGenerator {

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		if (!isAreaSuitable(world, rand, pos.add(-3, 0, -3), 6, 4, 6))
		{
			return false;
		}
		
		// okay!  circle!
		for (int cy = 0; cy <= 2; cy++)
		{
			setBlockAndNotifyAdequately(world, x - 3, y + cy, z, Blocks.MOSSY_COBBLESTONE.getDefaultState());
			setBlockAndNotifyAdequately(world, x + 3, y + cy, z, Blocks.MOSSY_COBBLESTONE.getDefaultState());
			setBlockAndNotifyAdequately(world, x, y + cy, z - 3, Blocks.MOSSY_COBBLESTONE.getDefaultState());
			setBlockAndNotifyAdequately(world, x, y + cy, z + 3, Blocks.MOSSY_COBBLESTONE.getDefaultState());
			setBlockAndNotifyAdequately(world, x - 2, y + cy, z - 2, Blocks.MOSSY_COBBLESTONE.getDefaultState());
			setBlockAndNotifyAdequately(world, x + 2, y + cy, z - 2, Blocks.MOSSY_COBBLESTONE.getDefaultState());
			setBlockAndNotifyAdequately(world, x - 2, y + cy, z + 2, Blocks.MOSSY_COBBLESTONE.getDefaultState());
			setBlockAndNotifyAdequately(world, x + 2, y + cy, z + 2, Blocks.MOSSY_COBBLESTONE.getDefaultState());
		}
		
		
		return true;
	}

}
