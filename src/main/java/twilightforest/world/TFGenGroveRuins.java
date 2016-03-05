package twilightforest.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;


/**
 * Several ruin types that look like the quest grove
 * 
 * @author Ben
 *
 */
public class TFGenGroveRuins extends TFGenerator {


	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {

		if (rand.nextBoolean())
		{
			return generateLargeArch(world, rand, x, y, z);
		}
		else
		{
			return generateSmallArch(world, rand, x, y, z);
		}
	}

	/**
	 * Generate a ruin with the larger arch
	 */
	private boolean generateLargeArch(World world, Random rand, int x, int y, int z) {
		if (!isAreaClear(world, rand, x, y, z, 2, 7, 6))
		{
			return false;
		}
		
		// pillar
		for (int dy = -2; dy <= 7; dy++) {
			this.setBlockAndMetadata(world, x + 0, y + dy, z + 1, Blocks.stonebrick, 1);
			this.setBlockAndMetadata(world, x + 1, y + dy, z + 1, Blocks.stonebrick, 1);
			this.setBlockAndMetadata(world, x + 0, y + dy, z + 2, Blocks.stonebrick, 1);
			this.setBlockAndMetadata(world, x + 1, y + dy, z + 2, Blocks.stonebrick, 1);
		}
		
		// broken floor part
		this.setBlockAndMetadata(world, x + 0, y - 1, z + 3, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 1, y - 1, z + 3, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 0, y - 2, z + 3, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 1, y - 2, z + 3, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 0, y - 1, z + 4, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 1, y - 1, z + 4, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 0, y - 2, z + 4, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 1, y - 2, z + 4, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 0, y - 1, z + 5, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 1, y - 2, z + 5, Blocks.stonebrick, 1);
		
		// broken top part
		this.setBlockAndMetadata(world, x + 0, y + 6, z + 3, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 1, y + 6, z + 3, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 0, y + 7, z + 3, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 1, y + 7, z + 3, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 0, y + 6, z + 4, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 1, y + 6, z + 4, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 0, y + 7, z + 4, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 1, y + 7, z + 4, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 1, y + 7, z + 5, Blocks.stonebrick, 1);
		
		// small piece of chiseled stone brick
		this.setBlockAndMetadata(world, x + 0, y + 5, z + 0, Blocks.stonebrick, 3);
		
		return true;
	}
	
	/**
	 * Generate a ruin with the smaller arch
	 */
	private boolean generateSmallArch(World world, Random rand, int x, int y, int z) {
		if (!isAreaClear(world, rand, x, y, z, 7, 5, 9))
		{
			return false;
		}
		
		// corner
		this.setBlockAndMetadata(world, x + 0, y + 4, z + 0, Blocks.stonebrick, 3);
		this.setBlockAndMetadata(world, x + 0, y + 3, z + 0, Blocks.stonebrick, 3);
		this.setBlockAndMetadata(world, x + 1, y + 4, z + 0, Blocks.stonebrick, 3);
		this.setBlockAndMetadata(world, x + 2, y + 4, z + 0, Blocks.stonebrick, 3);
		this.setBlockAndMetadata(world, x + 0, y + 4, z + 1, Blocks.stonebrick, 3);
		this.setBlockAndMetadata(world, x + 0, y + 4, z + 2, Blocks.stonebrick, 3);
		
		// broken arch in x direction
		for (int dy = -1; dy <= 5; dy++) {
			this.setBlockAndMetadata(world, x + 3, y + dy, z + 0, Blocks.stonebrick, 1);
		}
		this.setBlockAndMetadata(world, x + 4, y - 1, z + 0, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 5, y - 1, z + 0, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 6, y - 1, z + 0, Blocks.stonebrick, 1);
		
		this.setBlockAndMetadata(world, x + 4, y + 5, z + 0, Blocks.stonebrick, 1);
		this.setBlockAndMetadata(world, x + 5, y + 5, z + 0, Blocks.stonebrick, 1);
		
		// full arch in z direction
		for (int dy = -1; dy <= 5; dy++) {
			this.setBlockAndMetadata(world, x + 0, y + dy, z + 3, Blocks.stonebrick, 1);
			this.setBlockAndMetadata(world, x + 0, y + dy, z + 7, Blocks.stonebrick, 1);
		}
		for (int dz = 4; dz < 7; dz++) {
			this.setBlockAndMetadata(world, x + 0, y - 1, z + dz, Blocks.stonebrick, 1);
			this.setBlockAndMetadata(world, x + 0, y + 5, z + dz, Blocks.stonebrick, 1);
		}
		
		// small piece of chiseled stone brick
		this.setBlockAndMetadata(world, x + 0, y + 4, z + 8, Blocks.stonebrick, 3);

		
		return true;
	}

}

