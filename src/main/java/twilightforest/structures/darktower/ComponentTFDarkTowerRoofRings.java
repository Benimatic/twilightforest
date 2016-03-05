package twilightforest.structures.darktower;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.structures.lichtower.ComponentTFTowerWing;

public class ComponentTFDarkTowerRoofRings extends ComponentTFDarkTowerRoof {

	public ComponentTFDarkTowerRoofRings() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ComponentTFDarkTowerRoofRings(int i, ComponentTFTowerWing wing) 
	{
		super(i, wing);
	}

	
	/**
	 * ring a dings
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		super.addComponentParts(world, rand, sbb);
		
		// antenna
		for (int y = 1; y < 10; y++)
		{
			placeBlockAtCurrentPosition(world, deco.blockID, deco.blockMeta, size / 2, y, size / 2, sbb);
		}
		
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size / 2, 10, size / 2, sbb);

		
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size / 2 - 1, 1, size / 2, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size / 2 + 1, 1, size / 2, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size / 2, 1, size / 2 - 1, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size / 2, 1, size / 2 + 1, sbb);

		makeARing(world, 6, sbb);
		makeARing(world, 8, sbb);


		return true;
	}


	protected void makeARing(World world, int y, StructureBoundingBox sbb) {
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size / 2 - 2, y, size / 2 + 1, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size / 2 - 2, y, size / 2 + 0, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size / 2 - 2, y, size / 2 - 1, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size / 2 + 2, y, size / 2 + 1, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size / 2 + 2, y, size / 2 + 0, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size / 2 + 2, y, size / 2 - 1, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size / 2 + 1, y, size / 2 - 2, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size / 2 + 0, y, size / 2 - 2, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size / 2 - 1, y, size / 2 - 2, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size / 2 + 1, y, size / 2 + 2, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size / 2 + 0, y, size / 2 + 2, sbb);
		placeBlockAtCurrentPosition(world, deco.accentID, deco.accentMeta, size / 2 - 1, y, size / 2 + 2, sbb);
	}
}
