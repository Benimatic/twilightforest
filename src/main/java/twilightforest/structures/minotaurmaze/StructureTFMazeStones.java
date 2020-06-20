package twilightforest.structures.minotaurmaze;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class StructureTFMazeStones extends StructurePiece.BlockSelector {

	@Override
	public void selectBlocks(Random random, int x, int y, int z, boolean wall) {
		if (!wall) {
			this.blockstate = Blocks.AIR.getDefaultState();
		} else {
			this.blockstate = TFBlocks.maze_stone.get().getDefaultState();
			float rf = random.nextFloat();

			if (rf < 0.2F) {
				this.blockstate = TFBlocks.maze_stone_mossy.get().getDefaultState();
			} else if (rf < 0.5F) {
				this.blockstate = TFBlocks.maze_stone_cracked.get().getDefaultState();
			} else {
				this.blockstate = TFBlocks.maze_stone_brick.get().getDefaultState();
			}
		}
	}

}
