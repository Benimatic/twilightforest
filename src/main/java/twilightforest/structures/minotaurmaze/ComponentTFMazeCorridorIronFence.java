package twilightforest.structures.minotaurmaze;

import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.TFFeature;
import twilightforest.block.BlockTFMazestone;
import twilightforest.block.TFBlocks;
import twilightforest.enums.MazestoneVariant;

import java.util.Random;

public class ComponentTFMazeCorridorIronFence extends ComponentTFMazeCorridor {

	public ComponentTFMazeCorridorIronFence() {
		super();
	}

	public ComponentTFMazeCorridorIronFence(TFFeature feature, int i, int x, int y, int z, Direction rotation) {
		super(feature, i, x, y, z, rotation);
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		this.fillWithBlocks(world, sbb, 1, 4, 2, 4, 4, 3, TFBlocks.maze_stone.getDefaultState().with(BlockTFMazestone.VARIANT, MazestoneVariant.DECORATIVE), AIR, false);
		this.fillWithBlocks(world, sbb, 1, 1, 2, 4, 3, 3, TFBlocks.maze_stone.getDefaultState().with(BlockTFMazestone.VARIANT, MazestoneVariant.CHISELED), AIR, false);
		this.fillWithBlocks(world, sbb, 2, 1, 2, 3, 3, 3, Blocks.IRON_BARS.getDefaultState(), Blocks.AIR.getDefaultState(), false);
		return true;
	}
}
