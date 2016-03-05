package twilightforest.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import twilightforest.block.BlockTFPlant;
import twilightforest.block.TFBlocks;



public class TFGenFallenHollowLog extends TFGenerator {

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		
		return rand.nextBoolean() ? makeLog4Z(world, rand, x, y, z) : makeLog4X(world, rand, x, y, z);
	}

	private boolean makeLog4Z(World world, Random rand, int x, int y, int z) {
		// +Z 4x4 log
		if (!isAreaClear(world, rand, x, y, z, 9, 3, 4))
		{
			return false;
		}
		
		// jaggy parts
		makeNegativeZJaggy(world, x, y, z, rand.nextInt(3), 0, 0);
		makeNegativeZJaggy(world, x, y, z, rand.nextInt(3), 3, 0);
		makeNegativeZJaggy(world, x, y, z, rand.nextInt(3), 0, 1);
		makeNegativeZJaggy(world, x, y, z, rand.nextInt(3), 3, 1);
		makeNegativeZJaggy(world, x, y, z, rand.nextInt(3), 1, 2);
		makeNegativeZJaggy(world, x, y, z, rand.nextInt(3), 2, 2);
		
		makePositiveZJaggy(world, x, y, z, rand.nextInt(3), 0, 0);
		makePositiveZJaggy(world, x, y, z, rand.nextInt(3), 3, 0);
		makePositiveZJaggy(world, x, y, z, rand.nextInt(3), 0, 1);
		makePositiveZJaggy(world, x, y, z, rand.nextInt(3), 3, 1);
		makePositiveZJaggy(world, x, y, z, rand.nextInt(3), 1, 2);
		makePositiveZJaggy(world, x, y, z, rand.nextInt(3), 2, 2);
		
		// center
		for (int dz = 0; dz < 4; dz++)
		{
			// floor
			if (rand.nextBoolean())
			{
				this.setBlockAndMetadata(world, x + 1, y - 1, z + dz + 3, TFBlocks.log, 8);
				if (rand.nextBoolean())
				{
					this.setBlockAndMetadata(world, x + 1, y + 0, z + dz + 3, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
				}
			}
			else
			{
				this.setBlockAndMetadata(world, x + 1, y - 1, z + dz + 3, Blocks.dirt, 0);
				this.setBlockAndMetadata(world, x + 1, y + 0, z + dz + 3, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
			}
			if (rand.nextBoolean())
			{
				this.setBlockAndMetadata(world, x + 2, y - 1, z + dz + 3, TFBlocks.log, 8);
				if (rand.nextBoolean())
				{
					this.setBlockAndMetadata(world, x + 2, y + 0, z + dz + 3, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
				}
			}
			else
			{
				this.setBlockAndMetadata(world, x + 2, y - 1, z + dz + 3, Blocks.dirt, 0);
				this.setBlockAndMetadata(world, x + 2, y + 0, z + dz + 3, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
			}

			// log part
			this.setBlockAndMetadata(world, x + 0, y + 0, z + dz + 3, TFBlocks.log, 8);
			this.setBlockAndMetadata(world, x + 3, y + 0, z + dz + 3, TFBlocks.log, 8);
			this.setBlockAndMetadata(world, x + 0, y + 1, z + dz + 3, TFBlocks.log, 8);
			this.setBlockAndMetadata(world, x + 3, y + 1, z + dz + 3, TFBlocks.log, 8);
			this.setBlockAndMetadata(world, x + 1, y + 2, z + dz + 3, TFBlocks.log, 8);
			this.setBlockAndMetadata(world, x + 2, y + 2, z + dz + 3, TFBlocks.log, 8);
			if (rand.nextBoolean())
			{
				this.setBlockAndMetadata(world, x + 1, y + 3, z + dz + 3, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
			}
			if (rand.nextBoolean())
			{
				this.setBlockAndMetadata(world, x + 2, y + 3, z + dz + 3, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
			}
		}

		// a few leaves?
		int offZ = rand.nextInt(3) + 2;
		boolean plusX = rand.nextBoolean();
		for (int dz = 0; dz < 3; dz++)
		{
			if (rand.nextBoolean())
			{
				this.setBlockAndMetadata(world, x + (plusX ? 3 : 0), y + 2, z + dz + offZ, TFBlocks.leaves, 0);
				if (rand.nextBoolean())
				{
					this.setBlockAndMetadata(world, x + (plusX ? 3 : 0), y + 3, z + dz + offZ, TFBlocks.leaves, 0);
				}
				if (rand.nextBoolean())
				{
					this.setBlockAndMetadata(world, x + (plusX ? 4 : -1), y + 2, z + dz + offZ, TFBlocks.leaves, 0);
				}
			}
		}
		
		
		// firefly
		this.setBlockAndMetadata(world, x + (plusX ? 0 : 3), y + 2, z + rand.nextInt(4) + 3, TFBlocks.firefly, 0);

		
		return true;
	}
	
	private void makeNegativeZJaggy(World world, int x, int y, int z, int length, int dx, int dy) {
		for (int dz = -length; dz < 0; dz++)
		{
			this.setBlockAndMetadata(world, x + dx, y + dy, z + dz + 3, TFBlocks.log, 8);
		}
	}
	
	private void makePositiveZJaggy(World world, int x, int y, int z, int length, int dx, int dy) {
		for (int dz = 0; dz < length; dz++)
		{
			this.setBlockAndMetadata(world, x + dx, y + dy, z + dz + 7, TFBlocks.log, 8);
		}
	}

	/**
	 * Make a 4x4 log in the +X direction
	 */
	private boolean makeLog4X(World world, Random rand, int x, int y, int z) {
		// +Z 4x4 log
		if (!isAreaClear(world, rand, x, y, z, 4, 3, 9))
		{
			return false;
		}
		
		// jaggy parts
		makeNegativeXJaggy(world, x, y, z, rand.nextInt(3), 0, 0);
		makeNegativeXJaggy(world, x, y, z, rand.nextInt(3), 3, 0);
		makeNegativeXJaggy(world, x, y, z, rand.nextInt(3), 0, 1);
		makeNegativeXJaggy(world, x, y, z, rand.nextInt(3), 3, 1);
		makeNegativeXJaggy(world, x, y, z, rand.nextInt(3), 1, 2);
		makeNegativeXJaggy(world, x, y, z, rand.nextInt(3), 2, 2);
		
		makePositiveXJaggy(world, x, y, z, rand.nextInt(3), 0, 0);
		makePositiveXJaggy(world, x, y, z, rand.nextInt(3), 3, 0);
		makePositiveXJaggy(world, x, y, z, rand.nextInt(3), 0, 1);
		makePositiveXJaggy(world, x, y, z, rand.nextInt(3), 3, 1);
		makePositiveXJaggy(world, x, y, z, rand.nextInt(3), 1, 2);
		makePositiveXJaggy(world, x, y, z, rand.nextInt(3), 2, 2);
		
		// center
		for (int dx = 0; dx < 4; dx++)
		{
			// floor
			if (rand.nextBoolean())
			{
				this.setBlockAndMetadata(world, x + dx + 3, y - 1, z + 1, TFBlocks.log, 4);
				if (rand.nextBoolean())
				{
					this.setBlockAndMetadata(world, x + dx + 3, y, z + 1, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
				}
			}
			else
			{
				this.setBlockAndMetadata(world, x + dx + 3, y - 1, z + 1, Blocks.dirt, 0);
				this.setBlockAndMetadata(world, x + dx + 3, y, z + 1, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
			}
			if (rand.nextBoolean())
			{
				this.setBlockAndMetadata(world, x + dx + 3, y - 1, z + 2, TFBlocks.log, 4);
				if (rand.nextBoolean())
				{
					this.setBlockAndMetadata(world, x + dx + 3, y, z + 2, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
				}
			}
			else
			{
				this.setBlockAndMetadata(world, x + dx + 3, y - 1, z + 2, Blocks.dirt, 0);
				this.setBlockAndMetadata(world, x + dx + 3, y, z + 2, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
			}
	
			// log part
			this.setBlockAndMetadata(world, x + dx + 3, y + 0, z + 0, TFBlocks.log, 4);
			this.setBlockAndMetadata(world, x + dx + 3, y + 0, z + 3, TFBlocks.log, 4);
			this.setBlockAndMetadata(world, x + dx + 3, y + 1, z + 0, TFBlocks.log, 4);
			this.setBlockAndMetadata(world, x + dx + 3, y + 1, z + 3, TFBlocks.log, 4);
			this.setBlockAndMetadata(world, x + dx + 3, y + 2, z + 1, TFBlocks.log, 4);
			this.setBlockAndMetadata(world, x + dx + 3, y + 2, z + 2, TFBlocks.log, 4);
			if (rand.nextBoolean())
			{
				this.setBlockAndMetadata(world, x + dx + 3, y + 3, z + 1, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
			}
			if (rand.nextBoolean())
			{
				this.setBlockAndMetadata(world, x + dx + 3, y + 3, z + 2, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
			}

		}
	
		// a few leaves?
		int offX = rand.nextInt(3) + 2;
		boolean plusZ = rand.nextBoolean();
		for (int dx = 0; dx < 3; dx++)
		{
			if (rand.nextBoolean())
			{
				this.setBlockAndMetadata(world, x + dx + offX, y + 2, z + (plusZ ? 3 : 0), TFBlocks.leaves, 0);
				if (rand.nextBoolean())
				{
					this.setBlockAndMetadata(world, x + dx + offX, y + 3, z + (plusZ ? 3 : 0), TFBlocks.leaves, 0);
				}
				if (rand.nextBoolean())
				{
					this.setBlockAndMetadata(world, x + dx + offX, y + 2, z + (plusZ ? 4 : -1), TFBlocks.leaves, 0);
				}
			}
		}
		
		
		// firefly
		this.setBlockAndMetadata(world, x + rand.nextInt(4) + 3, y + 2, z + (plusZ ? 0 : 3), TFBlocks.firefly, 0);
		
		return true;
	}
	
	private void makeNegativeXJaggy(World world, int x, int y, int z, int length, int dz, int dy) {
		for (int dx = -length; dx < 0; dx++)
		{
			this.setBlockAndMetadata(world, x + 3 + dx, y + dy, z + dz, TFBlocks.log, 4);
		}
	}
	
	private void makePositiveXJaggy(World world, int x, int y, int z, int length, int dz, int dy) {
		for (int dx = 0; dx < length; dx++)
		{
			this.setBlockAndMetadata(world, x + dx + 7, y + dy, z + dz, TFBlocks.log, 4);
		}
	}
}
