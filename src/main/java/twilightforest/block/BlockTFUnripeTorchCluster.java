package twilightforest.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTFUnripeTorchCluster extends BlockTFTrollRoot {
	private static final int RIPEN_THRESHHOLD = 6;

	@Override
	public void tick(BlockState state, World world, BlockPos pos, Random rand) {
		super.tick(state, world, pos, rand);

		if (world.getLight(pos) >= RIPEN_THRESHHOLD) {
			// ripen!
			world.setBlockState(pos, TFBlocks.trollber.getDefaultState());
		}
	}
}
