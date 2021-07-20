package twilightforest.structures.stronghold;

import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.state.properties.SlabType;
import twilightforest.block.TFBlocks;
import twilightforest.structures.TFStructureDecorator;

public class StrongholdDecorator extends TFStructureDecorator {

	public StrongholdDecorator() {
		this.blockState = TFBlocks.underbrick.get().getDefaultState();
		this.accentState = TFBlocks.underbrick_cracked.get().getDefaultState();
		this.fenceState = Blocks.COBBLESTONE_WALL.getDefaultState();
		this.stairState = Blocks.STONE_BRICK_STAIRS.getDefaultState();
		this.pillarState = Blocks.MOSSY_STONE_BRICKS.getDefaultState();
		this.platformState = Blocks.SMOOTH_STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP);
		this.randomBlocks = new KnightStones();
	}
}
