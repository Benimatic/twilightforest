package twilightforest.structures.finalcastle;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class CastleBlockProcessor extends StructurePiece.BlockSelector {

	@Override
	public void selectBlocks(Random random, int x, int y, int z, boolean isWall) {
		if (!isWall) {
			blockstate = Blocks.AIR.getDefaultState();
		} else {
			float randFloat = random.nextFloat();

			if (randFloat < 0.1F) {
				blockstate = TFBlocks.castle_brick_worn.get().getDefaultState();
			} else if (randFloat < 0.2F) {
				blockstate = TFBlocks.castle_brick_cracked.get().getDefaultState();
			} else {
				blockstate = TFBlocks.castle_brick.get().getDefaultState();
			}
		}
	}

}
