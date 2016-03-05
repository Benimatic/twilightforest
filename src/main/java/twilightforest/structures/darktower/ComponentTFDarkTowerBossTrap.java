package twilightforest.structures.darktower;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.BlockTFTowerDevice;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponent;

public class ComponentTFDarkTowerBossTrap extends ComponentTFDarkTowerWing 
{

	public ComponentTFDarkTowerBossTrap() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected ComponentTFDarkTowerBossTrap(int i, int x, int y, int z, int pSize, int pHeight, int direction) {
		super(i, x, y, z, pSize, pHeight, direction);
		
		// no spawns
		this.spawnListIndex = -1;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void buildComponent(StructureComponent parent, List list, Random rand) 
	{
		if (parent != null && parent instanceof StructureTFComponent)
		{
			this.deco = ((StructureTFComponent)parent).deco;
		}
		
		// we should have a door where we started
		addOpening(0, 1, size / 2, 2);

		// add a beard
		makeABeard(parent, list, rand);
		
		for (int i = 0; i < 4; i++)
		{
			if (i == 2 || rand.nextBoolean())
			{
				continue;
			}
			// occasional balcony
			int[] dest = getValidOpening(rand, i);
			// move opening to tower base
			dest[1] = 1;
			makeTowerBalcony(list, rand, this.getComponentType(), dest[0], dest[1], dest[2], i);
		}
	}

	/**
	 * Attach a roof to this tower.
	 */
	public void makeARoof(StructureComponent parent, List<StructureComponent> list, Random rand) {
		//nope;
	}
	
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));

		// make walls
		makeEncasedWalls(world, rand, sbb, 0, 0, 0, size - 1, height - 1, size - 1);
		
		// clear inside
		fillWithAir(world, sbb, 1, 1, 1, size - 2, height - 2, size - 2);
		
        // openings
        makeOpenings(world, sbb);
        
		// half floors, always starting at y = 4
		addBossTrapFloors(world, decoRNG, sbb, 4, height - 1);
        
        // demolish some
		destroyTower(world, decoRNG, 5, height + 2, 5, 4, sbb);
		destroyTower(world, decoRNG, 0, height, 0, 3, sbb);
		destroyTower(world, decoRNG, 0, height, 8, 4, sbb);
		
		// hole for boss trap beam
		destroyTower(world, decoRNG, 5, 6, 5, 2, sbb);
		
		// redraw some of the floor in case we destroyed it
		this.fillWithMetadataBlocks(world, sbb, 1, 0, 1, size / 2, 0, size - 2, deco.blockID, deco.blockMeta, Blocks.air, 0, false);
		this.fillWithMetadataBlocks(world, sbb, 1, 1, 1, size / 2, 1, size - 2, Blocks.air, 0, Blocks.air, 0, false);
		
		// add boss trap
		this.placeBlockAtCurrentPosition(world, TFBlocks.towerDevice, BlockTFTowerDevice.META_GHASTTRAP_INACTIVE, 5, 1, 5, sbb);
		this.placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, 5, 1, 6, sbb);
		this.placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, 5, 1, 7, sbb);
		this.placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, 5, 1, 8, sbb);
		this.placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, 4, 1, 8, sbb);
		this.placeBlockAtCurrentPosition(world, Blocks.redstone_wire, 0, 3, 1, 8, sbb);
		this.placeBlockAtCurrentPosition(world, Blocks.wooden_pressure_plate, 0, 2, 1, 8, sbb);
		

		return true;
	}
	
	/**
	 * Add specific boss trap floors
	 */
	protected void addBossTrapFloors(World world, Random rand, StructureBoundingBox sbb, int bottom, int top) {

		makeFullFloor(world, sbb, 3, 4, 4);

		addStairsDown(world, sbb, 3, 4, size - 2, 4);
		addStairsDown(world, sbb, 3, 4, size - 3, 4);

		// stairs to roof
		addStairsDown(world, sbb, 1, this.height - 1, size - 2, 4);
		addStairsDown(world, sbb, 1, this.height - 1, size - 3, 4);
	}


}
