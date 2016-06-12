package twilightforest.structures.mushroomtower;

import net.minecraft.init.Blocks;
import twilightforest.structures.StructureTFDecorator;

public class StructureDecoratorMushroomTower extends StructureTFDecorator {

	
	
	public StructureDecoratorMushroomTower() 
	{
		this.blockID = Blocks.RED_MUSHROOM_BLOCK;
		this.blockMeta = 10;
		
		this.accentID = Blocks.RED_MUSHROOM_BLOCK;
		this.accentMeta = 14;
		
		this.fenceID = Blocks.FENCE;
		
		this.stairID = Blocks.SPRUCE_STAIRS;
		
		this.pillarID = Blocks.RED_MUSHROOM_BLOCK;
		this.pillarMeta = 10;
		
		this.floorID = Blocks.PLANKS;
		this.floorMeta = 0;
	}
	
}
