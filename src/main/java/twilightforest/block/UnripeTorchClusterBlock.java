package twilightforest.block;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import twilightforest.init.TFBlocks;

public class UnripeTorchClusterBlock extends TrollRootBlock {
	private static final int RIPEN_THRESHOLD = 6;

	public UnripeTorchClusterBlock(Properties props) {
		super(props);
	}

	@Override
	@Deprecated
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
		if (level.getMaxLocalRawBrightness(pos) >= RIPEN_THRESHOLD) {
			// ripen!
			level.setBlockAndUpdate(pos, TFBlocks.TROLLBER.get().defaultBlockState());
		}
	}
}
