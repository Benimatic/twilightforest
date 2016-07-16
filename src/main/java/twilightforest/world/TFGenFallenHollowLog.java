package twilightforest.world;

import java.util.Random;

import net.minecraft.block.BlockLog;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.block.BlockTFLog;
import twilightforest.block.BlockTFPlant;
import twilightforest.block.TFBlocks;



public class TFGenFallenHollowLog extends TFGenerator {

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		
		return rand.nextBoolean() ? makeLog4Z(world, rand, pos) : makeLog4X(world, rand, pos);
	}

	private boolean makeLog4Z(World world, Random rand, BlockPos pos) {
		// +Z 4x4 log
		if (!isAreaSuitable(world, rand, pos, 9, 3, 4))
		{
			return false;
		}
		
		// jaggy parts
		makeNegativeZJaggy(world, pos, rand.nextInt(3), 0, 0);
		makeNegativeZJaggy(world, pos, rand.nextInt(3), 3, 0);
		makeNegativeZJaggy(world, pos, rand.nextInt(3), 0, 1);
		makeNegativeZJaggy(world, pos, rand.nextInt(3), 3, 1);
		makeNegativeZJaggy(world, pos, rand.nextInt(3), 1, 2);
		makeNegativeZJaggy(world, pos, rand.nextInt(3), 2, 2);
		
		makePositiveZJaggy(world, pos, rand.nextInt(3), 0, 0);
		makePositiveZJaggy(world, pos, rand.nextInt(3), 3, 0);
		makePositiveZJaggy(world, pos, rand.nextInt(3), 0, 1);
		makePositiveZJaggy(world, pos, rand.nextInt(3), 3, 1);
		makePositiveZJaggy(world, pos, rand.nextInt(3), 1, 2);
		makePositiveZJaggy(world, pos, rand.nextInt(3), 2, 2);
		
		// center
		for (int dz = 0; dz < 4; dz++)
		{
			// floor
			if (rand.nextBoolean())
			{
				this.setBlockAndNotifyAdequately(world, x + 1, y - 1, z + dz + 3, TFBlocks.log, 8);
				if (rand.nextBoolean())
				{
					this.setBlockAndNotifyAdequately(world, x + 1, y + 0, z + dz + 3, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
				}
			}
			else
			{
				this.setBlockAndNotifyAdequately(world, x + 1, y - 1, z + dz + 3, Blocks.DIRT, 0);
				this.setBlockAndNotifyAdequately(world, x + 1, y + 0, z + dz + 3, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
			}
			if (rand.nextBoolean())
			{
				this.setBlockAndNotifyAdequately(world, x + 2, y - 1, z + dz + 3, TFBlocks.log, 8);
				if (rand.nextBoolean())
				{
					this.setBlockAndNotifyAdequately(world, x + 2, y + 0, z + dz + 3, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
				}
			}
			else
			{
				this.setBlockAndNotifyAdequately(world, x + 2, y - 1, z + dz + 3, Blocks.DIRT, 0);
				this.setBlockAndNotifyAdequately(world, x + 2, y + 0, z + dz + 3, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
			}

			// log part
			this.setBlockAndNotifyAdequately(world, x + 0, y + 0, z + dz + 3, TFBlocks.log, 8);
			this.setBlockAndNotifyAdequately(world, x + 3, y + 0, z + dz + 3, TFBlocks.log, 8);
			this.setBlockAndNotifyAdequately(world, x + 0, y + 1, z + dz + 3, TFBlocks.log, 8);
			this.setBlockAndNotifyAdequately(world, x + 3, y + 1, z + dz + 3, TFBlocks.log, 8);
			this.setBlockAndNotifyAdequately(world, x + 1, y + 2, z + dz + 3, TFBlocks.log, 8);
			this.setBlockAndNotifyAdequately(world, x + 2, y + 2, z + dz + 3, TFBlocks.log, 8);
			if (rand.nextBoolean())
			{
				this.setBlockAndNotifyAdequately(world, x + 1, y + 3, z + dz + 3, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
			}
			if (rand.nextBoolean())
			{
				this.setBlockAndNotifyAdequately(world, x + 2, y + 3, z + dz + 3, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
			}
		}

		// a few leaves?
		int offZ = rand.nextInt(3) + 2;
		boolean plusX = rand.nextBoolean();
		for (int dz = 0; dz < 3; dz++)
		{
			if (rand.nextBoolean())
			{
				this.setBlockAndNotifyAdequately(world, x + (plusX ? 3 : 0), y + 2, z + dz + offZ, TFBlocks.leaves, 0);
				if (rand.nextBoolean())
				{
					this.setBlockAndNotifyAdequately(world, x + (plusX ? 3 : 0), y + 3, z + dz + offZ, TFBlocks.leaves, 0);
				}
				if (rand.nextBoolean())
				{
					this.setBlockAndNotifyAdequately(world, x + (plusX ? 4 : -1), y + 2, z + dz + offZ, TFBlocks.leaves, 0);
				}
			}
		}
		
		
		// firefly
		this.setBlockAndNotifyAdequately(world, x + (plusX ? 0 : 3), y + 2, z + rand.nextInt(4) + 3, TFBlocks.firefly, 0);

		
		return true;
	}
	
	private void makeNegativeZJaggy(World world, BlockPos pos, int length, int dx, int dy) {
		for (int dz = -length; dz < 0; dz++)
		{
			this.setBlockAndNotifyAdequately(world, pos.add(dx, dy, dz + 3), TFBlocks.log.getDefaultState().withProperty(BlockTFLog.LOG_AXIS, BlockLog.EnumAxis.Z));
		}
	}
	
	private void makePositiveZJaggy(World world, BlockPos pos, int length, int dx, int dy) {
		for (int dz = 0; dz < length; dz++)
		{
			this.setBlockAndNotifyAdequately(world, pos.add(dx, dy, dz + 7), TFBlocks.log.getDefaultState().withProperty(BlockTFLog.LOG_AXIS, BlockLog.EnumAxis.Z));
		}
	}

	/**
	 * Make a 4x4 log in the +X direction
	 */
	private boolean makeLog4X(World world, Random rand, BlockPos pos) {
		// +Z 4x4 log
		if (!isAreaSuitable(world, rand, pos, 4, 3, 9))
		{
			return false;
		}
		
		// jaggy parts
		makeNegativeXJaggy(world, pos, rand.nextInt(3), 0, 0);
		makeNegativeXJaggy(world, pos, rand.nextInt(3), 3, 0);
		makeNegativeXJaggy(world, pos, rand.nextInt(3), 0, 1);
		makeNegativeXJaggy(world, pos, rand.nextInt(3), 3, 1);
		makeNegativeXJaggy(world, pos, rand.nextInt(3), 1, 2);
		makeNegativeXJaggy(world, pos, rand.nextInt(3), 2, 2);
		
		makePositiveXJaggy(world, pos, rand.nextInt(3), 0, 0);
		makePositiveXJaggy(world, pos, rand.nextInt(3), 3, 0);
		makePositiveXJaggy(world, pos, rand.nextInt(3), 0, 1);
		makePositiveXJaggy(world, pos, rand.nextInt(3), 3, 1);
		makePositiveXJaggy(world, pos, rand.nextInt(3), 1, 2);
		makePositiveXJaggy(world, pos, rand.nextInt(3), 2, 2);
		
		// center
		for (int dx = 0; dx < 4; dx++)
		{
			// floor
			if (rand.nextBoolean())
			{
				this.setBlockAndNotifyAdequately(world, x + dx + 3, y - 1, z + 1, TFBlocks.log, 4);
				if (rand.nextBoolean())
				{
					this.setBlockAndNotifyAdequately(world, x + dx + 3, y, z + 1, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
				}
			}
			else
			{
				this.setBlockAndNotifyAdequately(world, x + dx + 3, y - 1, z + 1, Blocks.DIRT, 0);
				this.setBlockAndNotifyAdequately(world, x + dx + 3, y, z + 1, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
			}
			if (rand.nextBoolean())
			{
				this.setBlockAndNotifyAdequately(world, x + dx + 3, y - 1, z + 2, TFBlocks.log, 4);
				if (rand.nextBoolean())
				{
					this.setBlockAndNotifyAdequately(world, x + dx + 3, y, z + 2, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
				}
			}
			else
			{
				this.setBlockAndNotifyAdequately(world, x + dx + 3, y - 1, z + 2, Blocks.DIRT, 0);
				this.setBlockAndNotifyAdequately(world, x + dx + 3, y, z + 2, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
			}
	
			// log part
			this.setBlockAndNotifyAdequately(world, x + dx + 3, y + 0, z + 0, TFBlocks.log.getDefaultState().withProperty(BlockTFLog.LOG_AXIS, BlockLog.EnumAxis.X));
			this.setBlockAndNotifyAdequately(world, x + dx + 3, y + 0, z + 3, TFBlocks.log.getDefaultState().withProperty(BlockTFLog.LOG_AXIS, BlockLog.EnumAxis.X));
			this.setBlockAndNotifyAdequately(world, x + dx + 3, y + 1, z + 0, TFBlocks.log.getDefaultState().withProperty(BlockTFLog.LOG_AXIS, BlockLog.EnumAxis.X));
			this.setBlockAndNotifyAdequately(world, x + dx + 3, y + 1, z + 3, TFBlocks.log.getDefaultState().withProperty(BlockTFLog.LOG_AXIS, BlockLog.EnumAxis.X));
			this.setBlockAndNotifyAdequately(world, x + dx + 3, y + 2, z + 1, TFBlocks.log.getDefaultState().withProperty(BlockTFLog.LOG_AXIS, BlockLog.EnumAxis.X));
			this.setBlockAndNotifyAdequately(world, x + dx + 3, y + 2, z + 2, TFBlocks.log.getDefaultState().withProperty(BlockTFLog.LOG_AXIS, BlockLog.EnumAxis.X));
			if (rand.nextBoolean())
			{
				this.setBlockAndNotifyAdequately(world, x + dx + 3, y + 3, z + 1, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
			}
			if (rand.nextBoolean())
			{
				this.setBlockAndNotifyAdequately(world, x + dx + 3, y + 3, z + 2, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
			}

		}
	
		// a few leaves?
		int offX = rand.nextInt(3) + 2;
		boolean plusZ = rand.nextBoolean();
		for (int dx = 0; dx < 3; dx++)
		{
			if (rand.nextBoolean())
			{
				this.setBlockAndNotifyAdequately(world, x + dx + offX, y + 2, z + (plusZ ? 3 : 0), TFBlocks.leaves, 0);
				if (rand.nextBoolean())
				{
					this.setBlockAndNotifyAdequately(world, x + dx + offX, y + 3, z + (plusZ ? 3 : 0), TFBlocks.leaves, 0);
				}
				if (rand.nextBoolean())
				{
					this.setBlockAndNotifyAdequately(world, x + dx + offX, y + 2, z + (plusZ ? 4 : -1), TFBlocks.leaves, 0);
				}
			}
		}
		
		
		// firefly
		this.setBlockAndNotifyAdequately(world, x + rand.nextInt(4) + 3, y + 2, z + (plusZ ? 0 : 3), TFBlocks.firefly, 0);
		
		return true;
	}
	
	private void makeNegativeXJaggy(World world, BlockPos pos, int length, int dz, int dy) {
		for (int dx = -length; dx < 0; dx++)
		{
			this.setBlockAndNotifyAdequately(world, pos.add(dx + 3, dy, dz), TFBlocks.log.getDefaultState().withProperty(BlockTFLog.LOG_AXIS, BlockLog.EnumAxis.X));
		}
	}
	
	private void makePositiveXJaggy(World world, BlockPos pos, int length, int dz, int dy) {
		for (int dx = 0; dx < length; dx++)
		{
			this.setBlockAndNotifyAdequately(world, pos.add(dx + 7, dy, dz), TFBlocks.log.getDefaultState().withProperty(BlockTFLog.LOG_AXIS, BlockLog.EnumAxis.X));
		}
	}
}
