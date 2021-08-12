package twilightforest.world;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.chunk.ProtoChunk;

/**
 * ChunkPrimer that stores states directly.
 */
public class DirectChunkPrimer extends ProtoChunk {

	private static final BlockState DEFAULT_STATE = Blocks.AIR.defaultBlockState();

	private final BlockState[] states = new BlockState[65536];

	public DirectChunkPrimer(ChunkPos pos, LevelAccessor level) {
		super(pos, UpgradeData.EMPTY, level);
	}

	@Override
	public BlockState getBlockState(BlockPos pos) {
		BlockState state = states[getBlockIndex(pos)];
		return state == null ? DEFAULT_STATE : state;
	}

	@Override
	public BlockState setBlockState(BlockPos pos, BlockState state, boolean isMoving) {
		states[getBlockIndex(pos)] = state;
		return state;
	}

	private static int getBlockIndex(BlockPos pos) {
		return pos.getX() << 12 | pos.getZ() << 8 | pos.getY();
	}
}
