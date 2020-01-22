package twilightforest.structures.minotaurmaze;

import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import twilightforest.TFFeature;

import java.util.Random;

public class ComponentTFMazeDeadEndFountainLava extends ComponentTFMazeDeadEndFountain {

	public ComponentTFMazeDeadEndFountainLava() {
		super();
	}

	public ComponentTFMazeDeadEndFountainLava(TFFeature feature, int i, int x, int y, int z, Direction rotation) {
		super(feature, i, x, y, z, rotation);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		// normal fountain
		super.generate(world, generator, rand, sbb, chunkPosIn);

		// remove water
		this.setBlockState(world, AIR, 2, 3, 4, sbb);
		this.setBlockState(world, AIR, 3, 3, 4, sbb);

		// lava instead of water
		this.setBlockState(world, Blocks.LAVA.getDefaultState(), 2, 3, 4, sbb);
		this.setBlockState(world, Blocks.LAVA.getDefaultState(), 3, 3, 4, sbb);

		return true;
	}


}
