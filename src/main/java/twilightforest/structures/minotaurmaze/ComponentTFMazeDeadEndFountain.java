package twilightforest.structures.minotaurmaze;

import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.util.math.MutableBoundingBox;
import twilightforest.TFFeature;
import twilightforest.block.BlockTFMazestone;
import twilightforest.block.TFBlocks;
import twilightforest.enums.MazestoneVariant;

import java.util.Random;

public class ComponentTFMazeDeadEndFountain extends ComponentTFMazeDeadEnd {

	public ComponentTFMazeDeadEndFountain() {
		super();
	}

	public ComponentTFMazeDeadEndFountain(TFFeature feature, int i, int x, int y, int z, Direction rotation) {
		super(feature, i, x, y, z, rotation);
	}

	@Override
	public boolean addComponentParts(IWorld world, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		// normal doorway
		super.addComponentParts(world, rand, sbb, chunkPosIn);

		// back wall brick
		this.fillWithBlocks(world, sbb, 1, 1, 4, 4, 4, 4, TFBlocks.maze_stone_brick.get().getDefaultState(), AIR, false);

		// water
		this.setBlockState(world, Blocks.WATER.getDefaultState(), 2, 3, 4, sbb);
		this.setBlockState(world, Blocks.WATER.getDefaultState(), 3, 3, 4, sbb);

		// receptacle
		this.setBlockState(world, AIR, 2, 0, 3, sbb);
		this.setBlockState(world, AIR, 3, 0, 3, sbb);

		return true;
	}
}
