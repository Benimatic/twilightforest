package twilightforest.structures.stronghold;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.Blocks;
import twilightforest.block.BlockTFUnderBrick;
import twilightforest.block.TFBlocks;
import twilightforest.enums.UnderBrickVariant;
import twilightforest.structures.StructureTFDecorator;

public class StructureTFDecoratorStronghold extends StructureTFDecorator {


	public StructureTFDecoratorStronghold() {
		this.blockState = TFBlocks.underbrick.getDefaultState();
		this.accentState = TFBlocks.underbrick.getDefaultState().with(BlockTFUnderBrick.VARIANT, UnderBrickVariant.CRACKED);
		this.fenceState = Blocks.COBBLESTONE_WALL.getDefaultState();
		this.stairState = Blocks.STONE_BRICK_STAIRS.getDefaultState();
		this.pillarState = Blocks.STONEBRICK.getDefaultState().with(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY);
		this.platformState = Blocks.STONE_SLAB.getDefaultState().with(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SMOOTHBRICK)
				.with(BlockStoneSlab.HALF, BlockSlab.EnumBlockHalf.TOP);
		this.randomBlocks = new StructureTFKnightStones();
	}
}
