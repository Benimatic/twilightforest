package twilightforest.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import twilightforest.TFTreasure;



public class TFGenWell extends TFGenerator {

	
	
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		if (rand.nextInt(4) == 0)
		{
			return generate4x4Well(world, rand, x, y, z);
		}
		else
		{
			return generate3x3Well(world, rand, x, y, z);
		}
	}

	
	/**
	 * make a cute little well
	 */	
	public boolean generate3x3Well(World world, Random rand, int x, int y, int z)
	{
		if (!isAreaSuitable(world, rand, x, y, z, 3, 4, 3))
		{
			return false;
		}
		
		
		// make a cute well!
		setBlock(world, x + 0, y, z + 0, Blocks.MOSSY_COBBLESTONE);
		setBlock(world, x + 1, y, z + 0, Blocks.MOSSY_COBBLESTONE);
		setBlock(world, x + 2, y, z + 0, Blocks.MOSSY_COBBLESTONE);
		setBlock(world, x + 0, y, z + 2, Blocks.MOSSY_COBBLESTONE);
		setBlock(world, x + 1, y, z + 2, Blocks.MOSSY_COBBLESTONE);
		setBlock(world, x + 2, y, z + 2, Blocks.MOSSY_COBBLESTONE);
		setBlock(world, x + 0, y, z + 1, Blocks.MOSSY_COBBLESTONE);
		setBlock(world, x + 2, y, z + 1, Blocks.MOSSY_COBBLESTONE);

		setBlock(world, x + 1, y, z + 1, Blocks.WATER);

		setBlock(world, x + 0, y + 1, z + 0, Blocks.FENCE);
		setBlock(world, x + 2, y + 1, z + 0, Blocks.FENCE);
		setBlock(world, x + 0, y + 1, z + 2, Blocks.FENCE);
		setBlock(world, x + 2, y + 1, z + 2, Blocks.FENCE);

		setBlock(world, x + 0, y + 2, z + 0, Blocks.FENCE);
		setBlock(world, x + 2, y + 2, z + 0, Blocks.FENCE);
		setBlock(world, x + 0, y + 2, z + 2, Blocks.FENCE);
		setBlock(world, x + 2, y + 2, z + 2, Blocks.FENCE);

		setBlockAndMetadata(world, x + 0, y + 3, z + 0, Blocks.WOODEN_SLAB, 0);
		setBlockAndMetadata(world, x + 1, y + 3, z + 0, Blocks.WOODEN_SLAB, 0);
		setBlockAndMetadata(world, x + 2, y + 3, z + 0, Blocks.WOODEN_SLAB, 0);
		setBlockAndMetadata(world, x + 0, y + 3, z + 2, Blocks.WOODEN_SLAB, 0);
		setBlockAndMetadata(world, x + 1, y + 3, z + 2, Blocks.WOODEN_SLAB, 0);
		setBlockAndMetadata(world, x + 2, y + 3, z + 2, Blocks.WOODEN_SLAB, 0);
		setBlockAndMetadata(world, x + 0, y + 3, z + 1, Blocks.WOODEN_SLAB, 0);
		setBlockAndMetadata(world, x + 2, y + 3, z + 1, Blocks.WOODEN_SLAB, 0);

		setBlock(world, x + 1, y + 3, z + 1, Blocks.PLANKS);


		boolean madeTreasure = false;
		// now drill each the well square down 20 squares, or until we hit something
		for (int dy = -1; dy >= -20; dy--)
		{
			Block dblock = world.getBlock(x + 1, y + dy, z + 1);
			// we only drill through dirt, grass, gravel and stone
			if (dblock != Blocks.DIRT && dblock != Blocks.GRASS && dblock != Blocks.GRAVEL && dblock != Blocks.STONE)
			{
				break;
			}
			// we also need a solid block under where we're digging
			if (!world.getBlock(x + 1, y + dy - 1, z + 1).getMaterial().isSolid())
			{
				break;
			}

			// okay, we're good to dig.
			setBlock(world, x + 1, y + dy, z + 1, Blocks.WATER);
			
			// if we're below 15 squares, there's a small chance of treasure
			if (dy < -15 && madeTreasure == false && rand.nextInt(8) == 0) {
				//TODO: more directions
				setBlock(world, x + 2, y + dy, z + 1, Blocks.WATER);
				setBlock(world, x + 3, y + dy + 1, z + 1, Blocks.AIR);
				setBlock(world, x + 3, y + dy, z + 1, Blocks.AIR);
				
				//TODO: unique treasure table that is themed for underwater well exploration
				TFTreasure.basement.generate(world, rand, x + 3, y + dy, z + 1);
				
				// set flag so we only get one chest
				madeTreasure = true;
			}
		}

		return true;
	}	
	



	/**
	 * Make a larger well
	 */
	public boolean generate4x4Well(World world, Random rand, int x, int y, int z) {
		if (!isAreaSuitable(world, rand, x, y, z, 4, 4, 4))
		{
			return false;
		}

		// make a cute well!
		setBlock(world, x + 0, y, z + 0, Blocks.MOSSY_COBBLESTONE);
		setBlock(world, x + 1, y, z + 0, Blocks.MOSSY_COBBLESTONE);
		setBlock(world, x + 2, y, z + 0, Blocks.MOSSY_COBBLESTONE);
		setBlock(world, x + 3, y, z + 0, Blocks.MOSSY_COBBLESTONE);
		setBlock(world, x + 0, y, z + 3, Blocks.MOSSY_COBBLESTONE);
		setBlock(world, x + 1, y, z + 3, Blocks.MOSSY_COBBLESTONE);
		setBlock(world, x + 2, y, z + 3, Blocks.MOSSY_COBBLESTONE);
		setBlock(world, x + 3, y, z + 3, Blocks.MOSSY_COBBLESTONE);
		setBlock(world, x + 0, y, z + 1, Blocks.MOSSY_COBBLESTONE);
		setBlock(world, x + 0, y, z + 2, Blocks.MOSSY_COBBLESTONE);
		setBlock(world, x + 3, y, z + 1, Blocks.MOSSY_COBBLESTONE);
		setBlock(world, x + 3, y, z + 2, Blocks.MOSSY_COBBLESTONE);

		setBlock(world, x + 1, y, z + 1, Blocks.WATER);
		setBlock(world, x + 2, y, z + 1, Blocks.WATER);
		setBlock(world, x + 1, y, z + 2, Blocks.WATER);
		setBlock(world, x + 2, y, z + 2, Blocks.WATER);

		setBlock(world, x + 0, y + 1, z + 0, Blocks.FENCE);
		setBlock(world, x + 3, y + 1, z + 0, Blocks.FENCE);
		setBlock(world, x + 0, y + 1, z + 3, Blocks.FENCE);
		setBlock(world, x + 3, y + 1, z + 3, Blocks.FENCE);

		setBlock(world, x + 0, y + 2, z + 0, Blocks.FENCE);
		setBlock(world, x + 3, y + 2, z + 0, Blocks.FENCE);
		setBlock(world, x + 0, y + 2, z + 3, Blocks.FENCE);
		setBlock(world, x + 3, y + 2, z + 3, Blocks.FENCE);

		setBlockAndMetadata(world, x + 0, y + 3, z + 0, Blocks.WOODEN_SLAB, 0);
		setBlockAndMetadata(world, x + 1, y + 3, z + 0, Blocks.WOODEN_SLAB, 0);
		setBlockAndMetadata(world, x + 2, y + 3, z + 0, Blocks.WOODEN_SLAB, 0);
		setBlockAndMetadata(world, x + 3, y + 3, z + 0, Blocks.WOODEN_SLAB, 0);
		setBlockAndMetadata(world, x + 0, y + 3, z + 3, Blocks.WOODEN_SLAB, 0);
		setBlockAndMetadata(world, x + 1, y + 3, z + 3, Blocks.WOODEN_SLAB, 0);
		setBlockAndMetadata(world, x + 2, y + 3, z + 3, Blocks.WOODEN_SLAB, 0);
		setBlockAndMetadata(world, x + 3, y + 3, z + 3, Blocks.WOODEN_SLAB, 0);
		setBlockAndMetadata(world, x + 0, y + 3, z + 1, Blocks.WOODEN_SLAB, 0);
		setBlockAndMetadata(world, x + 0, y + 3, z + 2, Blocks.WOODEN_SLAB, 0);
		setBlockAndMetadata(world, x + 3, y + 3, z + 1, Blocks.WOODEN_SLAB, 0);
		setBlockAndMetadata(world, x + 3, y + 3, z + 2, Blocks.WOODEN_SLAB, 0);

		setBlock(world, x + 1, y + 3, z + 1, Blocks.PLANKS);
		setBlock(world, x + 2, y + 3, z + 1, Blocks.PLANKS);
		setBlock(world, x + 1, y + 3, z + 2, Blocks.PLANKS);
		setBlock(world, x + 2, y + 3, z + 2, Blocks.PLANKS);

		
		// now drill each of the 4 well squares down 20 squares, or until we hit something
		for (int dx = 1; dx <= 2; dx++)
		{
			for (int dz = 1; dz <= 2; dz++)
			{
				for (int dy = -1; dy >= -20; dy--)
				{
					Block dblock = world.getBlock(x + dx, y + dy, z + dz);
					// we only drill through dirt, grass, gravel and stone
					if (dblock != Blocks.DIRT && dblock != Blocks.GRASS && dblock != Blocks.GRAVEL && dblock != Blocks.STONE)
					{
						break;
					}
					// we also need a solid block under where we're digging
					if (!world.getBlock(x + dx, y + dy - 1, z + dz).getMaterial().isSolid())
					{
						break;
					}
					
					// okay, we're good to dig.
					setBlock(world, x + dx, y + dy, z + dz, Blocks.WATER);
				}

			}
			
		}
		
		return true;
	}

}
