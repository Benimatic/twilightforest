package twilightforest.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.BlockState;

public class BlockTFCastleStairs extends StairsBlock {

	BlockTFCastleStairs(BlockState state) {
		super(() -> state, Properties.from(state.getBlock()).hardnessAndResistance(100.0F, 35F).sound(SoundType.STONE));
	}
}
