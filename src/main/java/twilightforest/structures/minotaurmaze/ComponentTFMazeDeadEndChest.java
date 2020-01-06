package twilightforest.structures.minotaurmaze;

import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.MutableBoundingBox;
import twilightforest.TFFeature;
import twilightforest.loot.TFTreasure;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class ComponentTFMazeDeadEndChest extends ComponentTFMazeDeadEnd {

	public ComponentTFMazeDeadEndChest() {
		super();
	}

	public ComponentTFMazeDeadEndChest(TFFeature feature, int i, int x, int y, int z, Direction rotation) {
		super(feature, i, x, y, z, rotation);

		// specify a non-existant high spawn list value to stop actual monster spawns
		this.spawnListIndex = Integer.MAX_VALUE;
	}

	@Override
	public boolean addComponentParts(IWorld world, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		//super.addComponentParts(world, rand, sbb, chunkPosIn);

		// dais
		this.setBlockState(world, Blocks.OAK_PLANKS.getDefaultState(), 2, 1, 4, sbb);
		this.setBlockState(world, Blocks.OAK_PLANKS.getDefaultState(), 3, 1, 4, sbb);
		this.setBlockState(world, getStairState(Blocks.OAK_STAIRS.getDefaultState(), Direction.NORTH, rotation, false), 2, 1, 3, sbb);
		this.setBlockState(world, getStairState(Blocks.OAK_STAIRS.getDefaultState(), Direction.NORTH, rotation, false), 3, 1, 3, sbb);

		// chest
		this.setBlockState(world, Blocks.CHEST.getDefaultState(), 2, 2, 4, sbb);
		this.placeTreasureAtCurrentPosition(world.getWorld(), rand, 3, 2, 4, TFTreasure.labyrinth_deadend, sbb);

//		// torches
//		this.setBlockState(world, Blocks.TORCH, 0, 1, 3, 4, sbb);
//		this.setBlockState(world, Blocks.TORCH, 0, 4, 3, 4, sbb);

		// doorway w/ bars
		this.fillWithBlocks(world, sbb, 1, 1, 0, 4, 3, 1, TFBlocks.maze_stone_chiseled.get().getDefaultState(), AIR, false);
		this.fillWithBlocks(world, sbb, 1, 4, 0, 4, 4, 1, TFBlocks.maze_stone_decorative.get().getDefaultState(), AIR, false);
		this.fillWithBlocks(world, sbb, 2, 1, 0, 3, 3, 1, Blocks.IRON_BARS.getDefaultState(), AIR, false);

		return true;
	}

}
