package twilightforest.structures.mushroomtower;

import net.minecraft.init.Blocks;
import twilightforest.structures.StructureTFDecorator;

public class StructureDecoratorMushroomTower extends StructureTFDecorator {

	
	
	public StructureDecoratorMushroomTower() 
	{
		this.blockID = Blocks.red_mushroom_block;
		this.blockMeta = 10;
		
		this.accentID = Blocks.red_mushroom_block;
		this.accentMeta = 14;
		
		this.fenceID = Blocks.fence;
		
		this.stairID = Blocks.spruce_stairs;
		
		this.pillarID = Blocks.red_mushroom_block;
		this.pillarMeta = 10;
		
		this.floorID = Blocks.planks;
		this.floorMeta = 0;
	}
	
}
