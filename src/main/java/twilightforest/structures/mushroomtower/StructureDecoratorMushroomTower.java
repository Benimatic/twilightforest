package twilightforest.structures.mushroomtower;

import net.minecraft.block.Blocks;
import net.minecraft.block.HugeMushroomBlock;
import twilightforest.structures.StructureTFDecorator;

public class StructureDecoratorMushroomTower extends StructureTFDecorator {


	public StructureDecoratorMushroomTower() {
		this.blockState = Blocks.RED_MUSHROOM_BLOCK.getDefaultState().with(HugeMushroomBlock.VARIANT, HugeMushroomBlock.EnumType.STEM);
		this.accentState = Blocks.RED_MUSHROOM_BLOCK.getDefaultState().with(HugeMushroomBlock.VARIANT, HugeMushroomBlock.EnumType.ALL_OUTSIDE);
		this.fenceState = Blocks.OAK_FENCE.getDefaultState();
		this.stairState = Blocks.SPRUCE_STAIRS.getDefaultState();
		this.pillarState = Blocks.RED_MUSHROOM_BLOCK.getDefaultState().with(HugeMushroomBlock.VARIANT, HugeMushroomBlock.EnumType.STEM);
		this.floorState = Blocks.OAK_PLANKS.getDefaultState();
	}

}
