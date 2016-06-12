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
		
		this.accentID = Blocks.QUARTZ_BLOCK;
		this.accentMeta = 1;
		
		this.roofID = TFBlocks.castleBlock;
		this.roofMeta = 3;
		
		this.pillarID = Blocks.QUARTZ_BLOCK;
		this.pillarMeta = 2;
		
		this.fenceID = Blocks.FENCE;
		this.fenceMeta = 0;
		
		this.stairID = Blocks.QUARTZ_STAIRS;

		this.randomBlocks = new StructureTFCastleBlocks();
	}

}
