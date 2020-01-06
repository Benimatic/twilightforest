package twilightforest.structures.minotaurmaze;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.MutableBoundingBox;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class ComponentTFMazeRoomFountain extends ComponentTFMazeRoom {

	public ComponentTFMazeRoomFountain() {
		super();
	}

	public ComponentTFMazeRoomFountain(TFFeature feature, int i, Random rand, int x, int y, int z) {
		super(feature, i, rand, x, y, z);
	}

	@Override
	public boolean addComponentParts(IWorld world, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		super.addComponentParts(world, rand, sbb, chunkPosIn);

		this.fillWithBlocks(world, sbb, 5, 1, 5, 10, 1, 10, TFBlocks.maze_stone_decorative.get().getDefaultState(), AIR, false);
		this.fillWithBlocks(world, sbb, 6, 1, 6, 9, 1, 9, Blocks.WATER.getDefaultState(), AIR, false);

		return true;
	}
}
