package twilightforest.world.components.structures.darktower;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class TowerwoodProcessor extends StructurePiece.BlockSelector {

	@Override
	public void next(Random random, int x, int y, int z, boolean isWall) {
		if (!isWall) {
			this.next = Blocks.AIR.defaultBlockState();
		} else {
			float randFloat = random.nextFloat();

			if (randFloat < 0.1F) {
				this.next = TFBlocks.tower_wood_cracked.get().defaultBlockState();
			} else if (randFloat < 0.2F) {
				this.next = TFBlocks.tower_wood_mossy.get().defaultBlockState();
			} else if (randFloat < 0.225F) {
				this.next = TFBlocks.tower_wood_infested.get().defaultBlockState();
			} else {
				this.next = TFBlocks.tower_wood.get().defaultBlockState();
			}
		}
	}

}
