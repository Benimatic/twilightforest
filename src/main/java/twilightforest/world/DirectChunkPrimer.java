package twilightforest.world;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.chunk.ChunkPrimer;

/**
 * ChunkPrimer that stores states directly.
 */
public class DirectChunkPrimer extends ChunkPrimer {

	private static final BlockState DEFAULT_STATE = Blocks.AIR.getDefaultState();

	private final BlockState[] states = new BlockState[65536];

	@Override
	public BlockState getBlockState(int x, int y, int z) {
		BlockState state = states[getBlockIndex(x, y, z)];
		return state == null ? DEFAULT_STATE : state;
	}

	@Override
	public void setBlockState(int x, int y, int z, BlockState state) {
		states[getBlockIndex(x, y, z)] = state;
	}

	private static int getBlockIndex(int x, int y, int z) {
		return x << 12 | z << 8 | y;
	}
}
