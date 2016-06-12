package twilightforest.structures.stronghold;

import net.minecraft.init.Blocks;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFDecorator;

public class StructureTFDecoratorStronghold extends StructureTFDecorator {

	
	public StructureTFDecoratorStronghold() 
	{
		this.blockID = TFBlocks.underBrick;
		this.blockMeta = 0;
		
		this.accentID = TFBlocks.underBrick;
		this.accentMeta = 3;
		
		this.fenceID = Blocks.COBBLESTONE_WALL;
		
		this.stairID = Blocks.STONE_BRICK_STAIRS;
		
		this.pillarID = Blocks.STONEBRICK;
		this.pillarMeta = 1;
		
		this.platformID = Blocks.STONE_SLAB;
		this.platformMeta = 5 + 8;

		this.randomBlocks = new StructureTFKnightStones();
	}
}
