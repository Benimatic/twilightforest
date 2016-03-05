package twilightforest.structures.darktower;

import java.util.List;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentTFDarkTowerEntrance extends ComponentTFDarkTowerWing 
{

	public ComponentTFDarkTowerEntrance() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected ComponentTFDarkTowerEntrance(int i, int x, int y, int z, int pSize, int pHeight, int direction) {
		super(i, x, y, z, pSize, pHeight, direction);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void buildComponent(StructureComponent parent, List list, Random rand) 
	{
		super.buildComponent(parent, list, rand);
		
		// a few more openings
		addOpening(size / 2, 1, 0, 1, EnumDarkTowerDoor.REAPPEARING);
		addOpening(size / 2, 1, size - 1, 3, EnumDarkTowerDoor.REAPPEARING);
	}


	/**
	 * Add a beard to this structure.  There is only one type of beard.
	 */
	public void makeABeard(StructureComponent parent, List<StructureComponent> list, Random rand) {
		//nope;
	}
	
	/**
	 * Attach a roof to this tower.
	 */
	@Override
	public void makeARoof(StructureComponent parent, List<StructureComponent> list, Random rand) 
	{
		// nope
	}
	
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		// make walls
		makeEncasedWalls(world, rand, sbb, 0, 0, 0, size - 1, height - 1, size - 1);
		
		// deco to ground
		for (int x = 0; x < this.size; x++)
		{
			for (int z = 0; z < this.size; z++)
			{
				this.func_151554_b(world, deco.accentID, deco.accentMeta, x, -1, z, sbb);
			}
		}
		
		// clear inside
		fillWithAir(world, sbb, 1, 1, 1, size - 2, height - 2, size - 2);

		// sky light
		nullifySkyLightForBoundingBox(world);
		
        // openings
        makeOpenings(world, sbb);
        
		return true;
	}

}
