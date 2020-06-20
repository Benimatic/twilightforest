package twilightforest.structures;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.structure.StructurePiece;

import java.util.Random;

public class StructureTFStrongholdStones extends StructurePiece.BlockSelector {

	@Override
	public void selectBlocks(Random random, int x, int y, int z, boolean wall) {
		if (!wall) {
			blockstate = Blocks.AIR.getDefaultState();
		} else {
			float f = random.nextFloat();

			if (f < 0.2F) {
				blockstate = Blocks.CRACKED_STONE_BRICKS.getDefaultState();
			} else if (f < 0.5F) {
				blockstate = Blocks.MOSSY_STONE_BRICKS.getDefaultState();
			} else if (f < 0.55F) {
				blockstate = Blocks.INFESTED_STONE_BRICKS.getDefaultState();
			} else {
				blockstate = Blocks.STONE_BRICKS.getDefaultState();
			}
		}
	}

}
