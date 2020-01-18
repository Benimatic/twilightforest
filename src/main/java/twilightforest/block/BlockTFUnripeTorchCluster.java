package twilightforest.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class BlockTFUnripeTorchCluster extends BlockTFTrollRoot {
	private static final int RIPEN_THRESHHOLD = 6;

	@Override
	@Deprecated
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
		super.scheduledTick(state, world, pos, rand);

		if (world.getLight(pos) >= RIPEN_THRESHHOLD) {
			// ripen!
			world.setBlockState(pos, TFBlocks.trollber.get().getDefaultState());
		}
	}
}
