package twilightforest.structures.darktower;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.structures.lichtower.ComponentTFTowerWing;

public class ComponentTFDarkTowerRoofFourPost extends ComponentTFDarkTowerRoof {

	public ComponentTFDarkTowerRoofFourPost() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ComponentTFDarkTowerRoofFourPost(int i, ComponentTFTowerWing wing) {
		super(i, wing);
	}

	
	/**
	 * four posts
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		super.addComponentParts(world, rand, sbb);
		
		makeSmallAntenna(world, sbb, 4, size - 2, size - 2);
		makeSmallAntenna(world, sbb, 5, 1, size - 2);
		makeSmallAntenna(world, sbb, 6, size - 2, 1);
		makeSmallAntenna(world, sbb, 7, 1, 1);
		

		return true;
	}


	protected void makeSmallAntenna(World world, StructureBoundingBox sbb, int height, int x, int z) {
		// antenna
		for (int y = 1; y < height; y++)
		{
			placeBlockAtCurrentPosition(world, deco.blockID, deco.blockMeta, x, y, z, sbb);
		}
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, x, height + 0, z, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, x, height + 1, z, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, x + 1, height + 1, z, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, x - 1, height + 1, z, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, x, height + 1, z + 1, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, x, height + 1, z - 1, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, x, height + 2, z, sbb);
	}

}
