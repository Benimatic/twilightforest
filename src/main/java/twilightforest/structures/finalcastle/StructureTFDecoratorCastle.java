package twilightforest.structures.finalcastle;

import net.minecraft.block.Blocks;
import twilightforest.block.TFBlocks;
import twilightforest.structures.TFStructureDecorator;

public class StructureTFDecoratorCastle extends TFStructureDecorator {

	public StructureTFDecoratorCastle() {
		this.blockState = TFBlocks.castle_brick.get().getDefaultState();
		this.accentState = Blocks.CHISELED_QUARTZ_BLOCK.getDefaultState();
		this.roofState = TFBlocks.castle_brick_roof.get().getDefaultState();
		this.pillarState = TFBlocks.castle_pillar_bold.get().getDefaultState();
		this.fenceState = Blocks.OAK_FENCE.getDefaultState();
		this.stairState = TFBlocks.castle_stairs_brick.get().getDefaultState();
		this.randomBlocks = new CastleBlockProcessor();
	}

}
