package twilightforest.structures.darktower;

import net.minecraft.init.Blocks;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFDecorator;

public class StructureDecoratorDarkTower extends StructureTFDecorator 
{

	public StructureDecoratorDarkTower() 
	{
		this.blockID = TFBlocks.towerWood;
		this.blockMeta = 0;
		
		this.accentID = TFBlocks.towerWood;
		this.accentMeta = 1;
		
		this.fenceID = Blocks.FENCE;
		
		this.stairID = Blocks.SPRUCE_STAIRS;
		
		this.pillarID = TFBlocks.towerWood;
		this.pillarMeta = 1;
		
		this.platformID = TFBlocks.towerWood;;
		this.platformMeta = 1;

		this.randomBlocks = new StructureTFTowerWoods();
	}

}
