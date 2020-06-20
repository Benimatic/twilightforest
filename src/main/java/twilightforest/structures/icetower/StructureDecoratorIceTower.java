package twilightforest.structures.icetower;

import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFDecorator;

public class StructureDecoratorIceTower extends StructureTFDecorator {

	public StructureDecoratorIceTower() {
		this.blockState = TFBlocks.aurora_block.get().getDefaultState();
		this.accentState = Blocks.BIRCH_PLANKS.getDefaultState();
		this.fenceState = Blocks.OAK_FENCE.getDefaultState();
		this.stairState = Blocks.BIRCH_STAIRS.getDefaultState();
		this.pillarState = TFBlocks.aurora_pillar.get().getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y);
		this.platformState = Blocks.BIRCH_SLAB.getDefaultState();
		this.floorState = Blocks.BIRCH_PLANKS.getDefaultState();
		this.randomBlocks = new StructureTFAuroraBricks();
	}

}
