package twilightforest.structures.minotaurmaze;

import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import twilightforest.TFFeature;
import twilightforest.structures.StructureTFComponentOld;

import java.util.Random;

public class ComponentTFMazeDeadEnd extends StructureTFComponentOld {

	public ComponentTFMazeDeadEnd() {
		super();
	}

	public ComponentTFMazeDeadEnd(TFFeature feature, int i, int x, int y, int z, Direction rotation) {
		super(feature, i);
		this.setCoordBaseMode(rotation);
		this.boundingBox = new MutableBoundingBox(x, y, z, x + 5, y + 5, z + 5);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		this.fillWithBlocks(world, sbb, 1, 1, 0, 4, 4, 0, Blocks.OAK_FENCE.getDefaultState(), AIR, false);
		this.fillWithAir(world, sbb, 2, 1, 0, 3, 3, 0);
		return true;
	}
}
