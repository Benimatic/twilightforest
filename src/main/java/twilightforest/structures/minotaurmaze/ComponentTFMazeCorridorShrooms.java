package twilightforest.structures.minotaurmaze;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class ComponentTFMazeCorridorShrooms extends ComponentTFMazeCorridor {

	public ComponentTFMazeCorridorShrooms() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFMazeCorridorShrooms(int i, int x, int y, int z, int rotation) {
		super(i, x, y, z, rotation);
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {		

		// mycelium & mushrooms
		for (int x = 1; x < 5; x++)
		{
			for (int z = 0; z < 5; z++)
			{
				if (rand.nextInt(2) > 0)
				{
					this.placeBlockAtCurrentPosition(world, Blocks.MYCELIUM, 0, x, 0, z, sbb);
				}
				if (rand.nextInt(2) > 0)
				{
					this.placeBlockAtCurrentPosition(world, rand.nextBoolean() ? Blocks.RED_MUSHROOM : Blocks.BROWN_MUSHROOM, 0, x, 1, z, sbb);
				}
			}
		}
		
		// brackets?
		Block mushType = rand.nextBoolean() ? Blocks.RED_MUSHROOM_BLOCK : Blocks.BROWN_MUSHROOM_BLOCK;
		int mushY = rand.nextInt(4) + 1;
		int mushZ = rand.nextInt(4) + 1;
		this.placeBlockAtCurrentPosition(world, mushType, 15, 1, mushY - 1, mushZ, sbb);
		this.fillWithMetadataBlocks(world, sbb, 1, 1, mushZ, 1, mushY, mushZ, mushType, 10, Blocks.AIR, 0, false);
		this.fillWithMetadataBlocks(world, sbb, 1, mushY, mushZ - 1, 2, mushY, mushZ + 1, mushType, 14, Blocks.AIR, 0, false);

		mushType = mushType == Blocks.BROWN_MUSHROOM_BLOCK ? Blocks.RED_MUSHROOM_BLOCK : Blocks.BROWN_MUSHROOM_BLOCK;
		mushY = rand.nextInt(4) + 1;
		mushZ = rand.nextInt(4) + 1;
		this.fillWithMetadataBlocks(world, sbb, 4, 1, mushZ, 4, mushY, mushZ, mushType, 10, Blocks.AIR, 0, false);
		this.fillWithMetadataBlocks(world, sbb, 3, mushY, mushZ - 1, 4, mushY, mushZ + 1, mushType, 14, Blocks.AIR, 0, false);

		
		return true;
	}
}
