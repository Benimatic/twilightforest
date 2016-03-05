package twilightforest.structures.icetower;

import net.minecraft.init.Blocks;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFDecorator;

public class StructureDecoratorIceTower extends StructureTFDecorator {

	public StructureDecoratorIceTower() 
	{
		this.blockID = TFBlocks.auroraBlock;
		this.blockMeta = 0;
		
		this.accentID = Blocks.planks;
		this.accentMeta = 2;
		
		this.fenceID = Blocks.fence;
		
		this.stairID = Blocks.birch_stairs;
		
		this.pillarID = TFBlocks.auroraPillar;
		this.pillarMeta = 0;
		
		this.platformID = Blocks.wooden_slab;
		this.platformMeta = 2;

		this.floorID = Blocks.planks;
		this.floorMeta = 2;

		this.randomBlocks = new StructureTFAuroraBricks();
	}

}
