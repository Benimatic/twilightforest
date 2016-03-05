package twilightforest.structures.minotaurmaze;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class ComponentTFMazeDeadEndPainting extends ComponentTFMazeDeadEnd {

	public ComponentTFMazeDeadEndPainting() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFMazeDeadEndPainting(int i, int x, int y, int z, int rotation) {
		super(i, x, y, z, rotation);
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {		
		// normal doorway
		super.addComponentParts(world, rand, sbb);
		
		// torches
		this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 1, 3, 3, sbb);
		this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 4, 3, 3, sbb);
		
//		// painting
//		EntityPainting painting = new EntityPainting(world, pCoords.posX, pCoords.posY, pCoords.posZ, this.get); 
//		painting.art = getPaintingOfSize(rand, minSize);
//		painting.setDirection(direction);
//		
//		world.spawnEntityInWorld(painting);
		
		return true;
	}
}
