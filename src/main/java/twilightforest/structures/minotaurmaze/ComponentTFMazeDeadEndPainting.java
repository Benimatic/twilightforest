package twilightforest.structures.minotaurmaze;

import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.MutableBoundingBox;
import twilightforest.TFFeature;

import java.util.Random;

public class ComponentTFMazeDeadEndPainting extends ComponentTFMazeDeadEnd {

	public ComponentTFMazeDeadEndPainting() {
		super();
	}

	public ComponentTFMazeDeadEndPainting(TFFeature feature, int i, int x, int y, int z, Direction rotation) {
		super(feature, i, x, y, z, rotation);
	}

	@Override
	public boolean addComponentParts(IWorld world, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn) {
		// normal doorway
		super.addComponentParts(world, rand, sbb, chunkPosIn);

		// torches
		this.setBlockState(world, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.WEST), 1, 3, 3, sbb);
		this.setBlockState(world, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.EAST), 4, 3, 3, sbb);

//		// painting
//		EntityPainting painting = new EntityPainting(world, pCoords.posX, pCoords.posY, pCoords.posZ, this.get); 
//		painting.art = getPaintingOfSize(rand, minSize);
//		painting.setDirection(direction);
//		
//		world.addEntity(painting);

		return true;
	}
}
