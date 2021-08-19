package twilightforest.world.components.structures.finalcastle;

import net.minecraft.world.level.block.Blocks;
import twilightforest.block.TFBlocks;
import twilightforest.world.components.structures.TFStructureDecorator;

public class StructureTFDecoratorCastle extends TFStructureDecorator {

	public StructureTFDecoratorCastle() {
		this.blockState = TFBlocks.castle_brick.get().defaultBlockState();
		this.accentState = Blocks.CHISELED_QUARTZ_BLOCK.defaultBlockState();
		this.roofState = TFBlocks.castle_brick_roof.get().defaultBlockState();
		this.pillarState = TFBlocks.castle_pillar_bold.get().defaultBlockState();
		this.fenceState = Blocks.OAK_FENCE.defaultBlockState();
		this.stairState = TFBlocks.castle_stairs_brick.get().defaultBlockState();
		this.randomBlocks = new CastleBlockProcessor();
	}

}
