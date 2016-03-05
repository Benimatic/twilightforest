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
		
		this.fenceID = Blocks.cobblestone_wall;
		
		this.stairID = Blocks.stone_brick_stairs;
		
		this.pillarID = Blocks.stonebrick;
		this.pillarMeta = 1;
		
		this.platformID = Blocks.stone_slab;
		this.platformMeta = 5 + 8;

		this.randomBlocks = new StructureTFKnightStones();
	}
}
