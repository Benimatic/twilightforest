package twilightforest.world;

import java.util.Random;

import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


/**
 * Several ruin types that look like the quest grove
 * 
 * @author Ben
 *
 */
public class TFGenGroveRuins extends TFGenerator {

	private static final IBlockState MOSSY_STONEBRICK = Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY);
	private static final IBlockState CHISELED_STONEBRICK = Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED);

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {

		if (rand.nextBoolean())
		{
			return generateLargeArch(world, rand, pos);
		}
		else
		{
			return generateSmallArch(world, rand, pos);
		}
	}

	/**
	 * Generate a ruin with the larger arch
	 */
	private boolean generateLargeArch(World world, Random rand, BlockPos pos) {
		if (!isAreaSuitable(world, rand, pos, 2, 7, 6))
		{
			return false;
		}
		
		// pillar
		for (int dy = -2; dy <= 7; dy++) {
			this.setBlockAndNotifyAdequately(world, x + 0, y + dy, z + 1, MOSSY_STONEBRICK);
			this.setBlockAndNotifyAdequately(world, x + 1, y + dy, z + 1, MOSSY_STONEBRICK);
			this.setBlockAndNotifyAdequately(world, x + 0, y + dy, z + 2, MOSSY_STONEBRICK);
			this.setBlockAndNotifyAdequately(world, x + 1, y + dy, z + 2, MOSSY_STONEBRICK);
		}
		
		// broken floor part
		this.setBlockAndNotifyAdequately(world, x + 0, y - 1, z + 3, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 1, y - 1, z + 3, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 0, y - 2, z + 3, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 1, y - 2, z + 3, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 0, y - 1, z + 4, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 1, y - 1, z + 4, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 0, y - 2, z + 4, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 1, y - 2, z + 4, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 0, y - 1, z + 5, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 1, y - 2, z + 5, MOSSY_STONEBRICK);
		
		// broken top part
		this.setBlockAndNotifyAdequately(world, x + 0, y + 6, z + 3, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 1, y + 6, z + 3, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 0, y + 7, z + 3, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 1, y + 7, z + 3, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 0, y + 6, z + 4, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 1, y + 6, z + 4, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 0, y + 7, z + 4, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 1, y + 7, z + 4, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 1, y + 7, z + 5, MOSSY_STONEBRICK);
		
		// small piece of chiseled stone brick
		this.setBlockAndNotifyAdequately(world, x + 0, y + 5, z + 0, CHISELED_STONEBRICK);
		
		return true;
	}
	
	/**
	 * Generate a ruin with the smaller arch
	 */
	private boolean generateSmallArch(World world, Random rand, int x, int y, int z) {
		if (!isAreaSuitable(world, rand, x, y, z, 7, 5, 9))
		{
			return false;
		}
		
		// corner
		this.setBlockAndNotifyAdequately(world, x + 0, y + 4, z + 0, CHISELED_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 0, y + 3, z + 0, CHISELED_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 1, y + 4, z + 0, CHISELED_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 2, y + 4, z + 0, CHISELED_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 0, y + 4, z + 1, CHISELED_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 0, y + 4, z + 2, CHISELED_STONEBRICK);
		
		// broken arch in x direction
		for (int dy = -1; dy <= 5; dy++) {
			this.setBlockAndNotifyAdequately(world, x + 3, y + dy, z + 0, MOSSY_STONEBRICK);
		}
		this.setBlockAndNotifyAdequately(world, x + 4, y - 1, z + 0, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 5, y - 1, z + 0, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 6, y - 1, z + 0, MOSSY_STONEBRICK);
		
		this.setBlockAndNotifyAdequately(world, x + 4, y + 5, z + 0, MOSSY_STONEBRICK);
		this.setBlockAndNotifyAdequately(world, x + 5, y + 5, z + 0, MOSSY_STONEBRICK);
		
		// full arch in z direction
		for (int dy = -1; dy <= 5; dy++) {
			this.setBlockAndNotifyAdequately(world, x + 0, y + dy, z + 3, MOSSY_STONEBRICK);
			this.setBlockAndNotifyAdequately(world, x + 0, y + dy, z + 7, MOSSY_STONEBRICK);
		}
		for (int dz = 4; dz < 7; dz++) {
			this.setBlockAndNotifyAdequately(world, x + 0, y - 1, z + dz, MOSSY_STONEBRICK);
			this.setBlockAndNotifyAdequately(world, x + 0, y + 5, z + dz, MOSSY_STONEBRICK);
		}
		
		// small piece of chiseled stone brick
		this.setBlockAndNotifyAdequately(world, x + 0, y + 4, z + 8, CHISELED_STONEBRICK);

		
		return true;
	}

}

