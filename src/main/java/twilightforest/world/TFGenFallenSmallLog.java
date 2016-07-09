package twilightforest.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.block.BlockTFPlant;
import twilightforest.block.TFBlocks;

public class TFGenFallenSmallLog extends TFGenerator {

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {

		// determine direction
		boolean goingX = rand.nextBoolean();
		
		// length
		int length = rand.nextInt(4) + 3;
		
		// check area clear
		if (goingX)
		{
			if (!isAreaSuitable(world, rand, pos, length, 3, 2))
			{
				return false;
			}
		}
		else
		{
			if (!isAreaSuitable(world, rand, pos, 3, length, 2))
			{
				return false;
			}
		}
		
		// determine wood type
		IBlockState logState;
		IBlockState branchState;

		switch (rand.nextInt(7))
		{
		case 0:
		default:
			logID = TFBlocks.log;
			logMeta = 0;
		case 1:
			logID = TFBlocks.log;
			logMeta = 1;
		case 2:
			logID = TFBlocks.log;
			logMeta = 2;		
		case 3:
			logID = Blocks.LOG;
			logMeta = 0;
		case 4:
			logID = Blocks.LOG;
			logMeta = 1;
		case 5:
			logID = Blocks.LOG;
			logMeta = 2;
		case 6:
			logID = Blocks.LOG;
			logMeta = 3;
		}
		logMetaBranch = logMeta;
		
		// check biome
		

		// make log
		if (goingX)
		{
			logMeta |= 4;
			logMetaBranch |= 8;

			for (int lx = 0; lx < length; lx++)
			{
				this.setBlockAndNotifyAdequately(world, pos.add(lx, 0, 1), logState);
				if (rand.nextInt(3) > 0)
				{
					this.setBlockAndNotifyAdequately(world, pos.add(lx, 1, 1), TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
				}
			}
		}
		else
		{
			logMeta |= 8;
			logMetaBranch |= 4;
			
			for (int lz = 0; lz < length; lz++)
			{
				this.setBlockAndMetadata(world, x + 1, y + 0, z + lz, logID, logMeta);
				if (rand.nextInt(3) > 0)
				{
					this.setBlockAndMetadata(world, x + 1, y + 1, z + lz, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
				}
			}
		}
		
		// possibly make branch
		if (rand.nextInt(3) > 0)
		{
			if (goingX)
			{
				int bx = rand.nextInt(length);
				int bz = rand.nextBoolean() ? 2 : 0;
				
				this.setBlockAndMetadata(world, x + bx, y + 0, z + bz, logID, logMetaBranch);
				if (rand.nextBoolean())
				{
					this.setBlockAndMetadata(world, x + bx, y + 1, z + bz, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
				}
			}
			else
			{
				int bx = rand.nextBoolean() ? 2 : 0;
				int bz = rand.nextInt(length);
				
				this.setBlockAndMetadata(world, x + bx, y + 0, z + bz, logID, logMetaBranch);
				if (rand.nextBoolean())
				{
					this.setBlockAndMetadata(world, x + bx, y + 1, z + bz, TFBlocks.plant, BlockTFPlant.META_MOSSPATCH);
				}
			}
		}
		
		return true;
	}

}
