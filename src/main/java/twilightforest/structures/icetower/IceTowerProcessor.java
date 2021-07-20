package twilightforest.structures.icetower;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import twilightforest.block.AuroraBrickBlock;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class IceTowerProcessor extends StructurePiece.BlockSelector {

	@Override
	public void selectBlocks(Random random, int x, int y, int z, boolean wall) {
		if (!wall) {
			this.blockstate = Blocks.AIR.getDefaultState();
		} else {
			this.blockstate = TFBlocks.aurora_block.get().getDefaultState().with(
					AuroraBrickBlock.VARIANT,
					Math.abs(x + z) % 16
			);
		}
	}

}
