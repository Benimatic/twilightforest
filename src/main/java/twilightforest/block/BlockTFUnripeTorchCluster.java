package twilightforest.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class BlockTFUnripeTorchCluster extends BlockTFTrollRoot {
	private static final int RIPEN_THRESHHOLD = 6;

	protected BlockTFUnripeTorchCluster(Properties props) {
		super(props);
	}

	@Override
	@Deprecated
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
		if (world.getLight(pos) >= RIPEN_THRESHHOLD) {
			// ripen!
			world.setBlockState(pos, TFBlocks.trollber.get().getDefaultState());
		}
	}
}
