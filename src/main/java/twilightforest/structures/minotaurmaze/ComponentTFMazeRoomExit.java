package twilightforest.structures.minotaurmaze;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class ComponentTFMazeRoomExit extends ComponentTFMazeRoom {

	public ComponentTFMazeRoomExit() {
		super();
	}

	public ComponentTFMazeRoomExit(TFFeature feature, int i, Random rand, int x, int y, int z) {
		super(feature, i, rand, x, y, z);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		super.generate(world, generator, rand, sbb, chunkPosIn);

		// shaft down
		this.fillWithBlocks(world, sbb, 5, -5, 5, 10, 0, 10, TFBlocks.maze_stone_brick.get().getDefaultState(), AIR, false);
		this.fillWithBlocks(world, sbb, 5, 1, 5, 10, 1, 10, TFBlocks.maze_stone_decorative.get().getDefaultState(), AIR, false);
		this.fillWithBlocks(world, sbb, 5, 2, 5, 10, 3, 10, Blocks.IRON_BARS.getDefaultState(), AIR, false);
		this.fillWithBlocks(world, sbb, 5, 4, 5, 10, 4, 10, TFBlocks.maze_stone_decorative.get().getDefaultState(), AIR, false);
		this.fillWithAir(world, sbb, 6, -5, 6, 9, 4, 9);

		return true;
	}
}
