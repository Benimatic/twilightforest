package twilightforest.structures.minotaurmaze;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
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
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		// normal doorway
		super.addComponentParts(world, rand, sbb);

		// torches
		this.setBlockState(world, Blocks.TORCH.getDefaultState().with(BlockTorch.FACING, Direction.WEST), 1, 3, 3, sbb);
		this.setBlockState(world, Blocks.TORCH.getDefaultState().with(BlockTorch.FACING, Direction.EAST), 4, 3, 3, sbb);

//		// painting
//		EntityPainting painting = new EntityPainting(world, pCoords.posX, pCoords.posY, pCoords.posZ, this.get); 
//		painting.art = getPaintingOfSize(rand, minSize);
//		painting.setDirection(direction);
//		
//		world.addEntity(painting);

		return true;
	}
}
