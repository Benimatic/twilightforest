package twilightforest.structures.icetower;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.block.BlockTFAuroraBrick;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class StructureTFAuroraBricks extends StructurePiece.BlockSelector {

	@Override
	public void selectBlocks(Random random, int x, int y, int z, boolean wall) {
		if (!wall) {
			this.blockstate = Blocks.AIR.getDefaultState();
		} else {
			this.blockstate = TFBlocks.aurora_block.get().getDefaultState().with(
					BlockTFAuroraBrick.VARIANT,
					Math.abs(x + z) % 16
			);
		}
	}

}
