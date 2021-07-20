package twilightforest.structures.stronghold;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class KnightStones extends StructurePiece.BlockSelector {

	@Override
	public void selectBlocks(Random random, int x, int y, int z, boolean edge) {
		if (!edge) {
			this.blockstate = Blocks.AIR.getDefaultState();
		} else {
			float f = random.nextFloat();

			if (f < 0.2F) {
				this.blockstate = TFBlocks.underbrick_cracked.get().getDefaultState();
			} else if (f < 0.5F) {
				this.blockstate = TFBlocks.underbrick_mossy.get().getDefaultState();
			} else {
				this.blockstate = TFBlocks.underbrick.get().getDefaultState();
			}
		}
	}

}
