package twilightforest.structures;

import net.minecraft.init.Blocks;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFDecorator;

public class StructureTFDecoratorCastle extends StructureTFDecorator 
{

	public StructureTFDecoratorCastle() 
	{
		this.blockID = TFBlocks.castleBlock;
		this.blockMeta = 0;
		
		this.accentID = Blocks.quartz_block;
		this.accentMeta = 1;
		
		this.roofID = TFBlocks.castleBlock;
		this.roofMeta = 3;
		
		this.pillarID = Blocks.quartz_block;
		this.pillarMeta = 2;
		
		this.fenceID = Blocks.fence;
		this.fenceMeta = 0;
		
		this.stairID = Blocks.quartz_stairs;

		this.randomBlocks = new StructureTFCastleBlocks();
	}

}
