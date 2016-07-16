package twilightforest.world;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
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

	@Override
    public boolean generate(World world, Random random, BlockPos pos)
    {
    	for (int i = 0; i < 10; i++) {
			BlockPos dPos = pos.add(
					random.nextInt(8) - random.nextInt(8),
					random.nextInt(4) - random.nextInt(4),
					random.nextInt(8) - random.nextInt(8)
			);

    		dx = (dx >> 1) << 1;
    		dz = (dz >> 1) << 1;

    		if (shouldPlacePadAt(world, dPos)) {
    			// this seems like a difficult way to generate 2 pseudorandom bits
    			rand .setSeed(8890919293L);
    			rand.setSeed((dPos.getX() * rand.nextLong()) ^ (dPos.getZ() * rand.nextLong()) ^ 8890919293L);
    			int orient = rand.nextInt(4) << 2;

    			world.setBlockState(pos, TFBlocks.hugeLilyPad, 0 | orient, 2);
    			world.setBlockState(pos.east(), TFBlocks.hugeLilyPad, 1 | orient, 2);
    			world.setBlockState(pos.east().south(), TFBlocks.hugeLilyPad, 2 | orient, 2);
    			world.setBlockState(pos.south(), TFBlocks.hugeLilyPad, 3 | orient, 2);
    		}
    	}

        return true;
    }


	private boolean shouldPlacePadAt(World world, BlockPos pos) {
		return world.isAirBlock(pos) && world.getBlockState(pos.down()).getMaterial() == Material.WATER
				&& world.isAirBlock(pos.east()) && world.getBlockState(pos.add(1, -1, 0)).getMaterial() == Material.WATER
				&& world.isAirBlock(pos.south()) && world.getBlockState(pos.add(0, -1, 1)).getMaterial() == Material.WATER
				&& world.isAirBlock(pos.east().south()) && world.getBlockState(pos.add(1, -1, 1)).getMaterial() == Material.WATER;
	}
}
