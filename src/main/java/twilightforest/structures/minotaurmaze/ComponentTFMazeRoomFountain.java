package twilightforest.structures.minotaurmaze;

import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class ComponentTFMazeRoomFountain extends ComponentTFMazeRoom {

	public ComponentTFMazeRoomFountain(TemplateManager manager, CompoundNBT nbt) {
		super(TFMinotaurMazePieces.TFMMRF, nbt);
	}

	public ComponentTFMazeRoomFountain(TFFeature feature, int i, Random rand, int x, int y, int z) {
		super(TFMinotaurMazePieces.TFMMRF, feature, i, rand, x, y, z);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		super.generate(world, generator, rand, sbb, chunkPosIn);

		this.fillWithBlocks(world, sbb, 5, 1, 5, 10, 1, 10, TFBlocks.maze_stone_decorative.get().getDefaultState(), AIR, false);
		this.fillWithBlocks(world, sbb, 6, 1, 6, 9, 1, 9, Blocks.WATER.getDefaultState(), AIR, false);

		return true;
	}
}
