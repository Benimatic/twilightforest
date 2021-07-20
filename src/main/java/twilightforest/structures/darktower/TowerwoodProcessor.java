package twilightforest.structures.darktower;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class TowerwoodProcessor extends StructurePiece.BlockSelector {

	@Override
	public void selectBlocks(Random random, int x, int y, int z, boolean isWall) {
		if (!isWall) {
			this.blockstate = Blocks.AIR.getDefaultState();
		} else {
			float randFloat = random.nextFloat();

			if (randFloat < 0.1F) {
				this.blockstate = TFBlocks.tower_wood_cracked.get().getDefaultState();
			} else if (randFloat < 0.2F) {
				this.blockstate = TFBlocks.tower_wood_mossy.get().getDefaultState();
			} else if (randFloat < 0.225F) {
				this.blockstate = TFBlocks.tower_wood_infested.get().getDefaultState();
			} else {
				this.blockstate = TFBlocks.tower_wood.get().getDefaultState();
			}
		}
	}

}
