package twilightforest.world.feature;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class TFGenHangingLamps extends TFGenerator {

	private static final int MAX_HANG = 8;

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		// this must be an air block, surrounded by air
		if (!world.isAirBlock(pos) || !TFGenerator.surroundedByAir(world, pos)) {
			return false;
		}

		// there should be leaves or wood within 12 blocks above
		if (!areLeavesAbove(world, pos)) {
			return false;
		}

		// we need to be at least 4 above ground
		if (!isClearBelow(world, pos)) {
			return false;
		}

		// generate lamp
		world.setBlockState(pos, TFBlocks.firefly_jar.getDefaultState());

		for (int cy = 1; cy < MAX_HANG; cy++) {
			Material above = world.getBlockState(pos.up(cy)).getMaterial();
			if (above.isSolid() || above == Material.LEAVES) {
				break;
			}
			world.setBlockState(pos.up(cy), Blocks.OAK_FENCE.getDefaultState());
		}

		return true;
	}

	private boolean areLeavesAbove(World world, BlockPos pos) {
		for (int cy = 1; cy < MAX_HANG; cy++) {
			Material above = world.getBlockState(pos.up(cy)).getMaterial();
			if (above.isSolid() || above == Material.LEAVES) {
				return true;
			}
		}
		return false;
	}

	private boolean isClearBelow(World world, BlockPos pos) {
		for (int cy = 1; cy < 4; cy++) {
			if (world.getBlockState(pos.down(cy)).isSideSolid(world, pos, EnumFacing.UP)) {
				return false;
			}
		}
		return true;
	}
}
