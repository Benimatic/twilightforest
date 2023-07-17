package twilightforest.world.components.structures.stronghold;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import twilightforest.init.TFBlocks;

public class KnightStones extends StructurePiece.BlockSelector {

	@Override
	public void next(RandomSource random, int x, int y, int z, boolean edge) {
		if (!edge) {
			this.next = Blocks.AIR.defaultBlockState();
		} else {
			float f = random.nextFloat();

			if (f < 0.2F) {
				this.next = TFBlocks.CRACKED_UNDERBRICK.get().defaultBlockState();
			} else if (f < 0.5F) {
				this.next = TFBlocks.MOSSY_UNDERBRICK.get().defaultBlockState();
			} else {
				this.next = TFBlocks.UNDERBRICK.get().defaultBlockState();
			}
		}
	}

}
