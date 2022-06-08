package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import twilightforest.TFSounds;
import twilightforest.item.OreMagnetItem;
import twilightforest.util.WorldUtil;

public class MineLogCoreBlock extends SpecialMagicLogBlock {

	public MineLogCoreBlock(Properties props) {
		super(props);
	}

	/**
	 * The miner's tree generates the ore magnet effect randomly every second
	 */
	@Override
	void performTreeEffect(Level world, BlockPos pos, RandomSource rand) {
		BlockPos dPos = WorldUtil.randomOffset(rand, pos, 32);
		int moved = OreMagnetItem.doMagnet(world, pos, dPos);

		if (moved > 0) {
			world.playSound(null, pos, TFSounds.MAGNET_GRAB.get(), SoundSource.BLOCKS, 0.1F, 1.0F);
		}
	}
}
