package twilightforest.structures.icetower;

import net.minecraft.init.Blocks;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFDecorator;

public class StructureDecoratorIceTower extends StructureTFDecorator {

	public StructureDecoratorIceTower() 
	{
		this.blockID = TFBlocks.auroraBlock;
		this.blockMeta = 0;
		
		this.accentID = Blocks.PLANKS;
		this.accentMeta = 2;
		
		this.fenceID = Blocks.FENCE;
		
		this.stairID = Blocks.BIRCH_STAIRS;
		
		this.pillarID = TFBlocks.auroraPillar;
		this.pillarMeta = 0;
		
		this.platformID = Blocks.WOODEN_SLAB;
		this.platformMeta = 2;

		this.floorID = Blocks.PLANKS;
		this.floorMeta = 2;

		this.randomBlocks = new StructureTFAuroraBricks();
	}

}
