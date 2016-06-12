package twilightforest.structures.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.TFBlocks;

public class ComponentTFStrongholdAccessChamber extends StructureTFStrongholdComponent {

	public ComponentTFStrongholdAccessChamber() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFStrongholdAccessChamber(int i, int facing, int x, int y, int z) {
		super(i, facing, x, y, z);
	}

	@Override
	public StructureBoundingBox generateBoundingBox(int facing, int x, int y, int z) {
		return StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, -4, 1, 0, 9, 5, 9, facing);
	}
	
    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
	@Override
	public void buildComponent(StructureComponent parent, List list, Random random) {
		super.buildComponent(parent, list, random);
		
		// make a random component in each direction
		addNewUpperComponent(parent, list, random, 0, 4, 1, 9);
		addNewUpperComponent(parent, list, random, 1, -1, 1, 4);
		addNewUpperComponent(parent, list, random, 2, 4, 1, -1);
		addNewUpperComponent(parent, list, random, 3, 9, 1, 4);
		
	}


	/**
	 * Generate the blocks that go here
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		fillWithRandomizedBlocks(world, sbb, 0, 0, 0, 8, 4, 8, true, rand, deco.randomBlocks);
		
		// doors
		placeSmallDoorwayAt(world, rand, 0, 4, 1, 8, sbb);
		placeSmallDoorwayAt(world, rand, 1, 0, 1, 4, sbb);
		placeSmallDoorwayAt(world, rand, 2, 4, 1, 0, sbb);
		placeSmallDoorwayAt(world, rand, 3, 8, 1, 4, sbb);
		
		// shaft down
		this.fillWithMetadataBlocks(world, sbb, 2, -2, 2, 6, 0, 6, Blocks.STONEBRICK, 1, Blocks.AIR, 0, false);

		this.fillWithAir(world, sbb, 3, -2, 3, 5, 2, 5);
		
		// stairs surrounding shaft
		this.fillWithMetadataBlocks(world, sbb, 2, 0, 3, 2, 0, 6, deco.stairID, this.getStairMeta(2), Blocks.AIR, 0, false);
		this.fillWithMetadataBlocks(world, sbb, 6, 0, 2, 6, 0, 6, deco.stairID, this.getStairMeta(0), Blocks.AIR, 0, false);
		this.fillWithMetadataBlocks(world, sbb, 3, 0, 2, 5, 0, 2, deco.stairID, this.getStairMeta(3), Blocks.AIR, 0, false);
		this.fillWithMetadataBlocks(world, sbb, 3, 0, 6, 5, 0, 6, deco.stairID, this.getStairMeta(1), Blocks.AIR, 0, false);
		
		// pillar
		this.placeBlockAtCurrentPosition(world, deco.pillarID, deco.pillarMeta, 2, 0, 2, sbb);
		
		// pedestal
		this.placeBlockAtCurrentPosition(world, TFBlocks.trophyPedestal, 15, 2, 1, 2, sbb);
		
		// block point
		this.fillWithMetadataBlocks(world, sbb, 2, -1, 2, 6, -1, 6, TFBlocks.shield, 15, Blocks.AIR, 0, false);
		
		return true;
	}
	
	/**
	 * Does this component fall under block protection when progression is turned on, normally true
	 */
	public boolean isComponentProtected() {
		return false;
	}
}
