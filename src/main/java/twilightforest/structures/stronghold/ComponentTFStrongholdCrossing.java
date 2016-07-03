package twilightforest.structures.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentTFStrongholdCrossing extends StructureTFStrongholdComponent {

	public ComponentTFStrongholdCrossing() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFStrongholdCrossing(int i, int facing, int x, int y, int z) {
		super(i, facing, x, y, z);
	}

	/**
	 * Make a bounding box for this room
	 */
	@Override
	public StructureBoundingBox generateBoundingBox(int facing, int x, int y, int z)
	{
		return StructureTFStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -13, -1, 0, 18, 7, 18, facing);
	}

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
	@Override
	public void buildComponent(StructureComponent parent, List list, Random random) {
		super.buildComponent(parent, list, random);
		
		this.addDoor(13, 1, 0);
		addNewComponent(parent, list, random, 0, 4, 1, 18);
		addNewComponent(parent, list, random, 1, -1, 1, 13);
		addNewComponent(parent, list, random, 3, 18, 1, 4);
	}

	/**
	 * Generate the blocks that go here
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		placeStrongholdWalls(world, sbb, 0, 0, 0, 17, 6, 17, rand, deco.randomBlocks);
		
		// statues
		placeCornerStatue(world, 2, 1, 2, 0, sbb);
		placeCornerStatue(world, 15, 1, 15, 3, sbb);
		
		// center pillar
		this.fillBlocksRotated(world, sbb, 8, 1, 8, 9, 5, 9, deco.pillarID, deco.pillarMeta, 0);
		
		// statues
		placeWallStatue(world, 8, 1, 7, 0, sbb);
		placeWallStatue(world, 7, 1, 9, 3, sbb);
		placeWallStatue(world, 9, 1, 10, 2, sbb);
		placeWallStatue(world, 10, 1, 8, 1, sbb);

		
		// tables
		placeTableAndChairs(world, sbb, 0);
		placeTableAndChairs(world, sbb, 1);
		placeTableAndChairs(world, sbb, 2);
		placeTableAndChairs(world, sbb, 3);

		// doors
		placeDoors(world, rand, sbb);
		
		return true;
	}

	private void placeTableAndChairs(World world, StructureBoundingBox sbb, int rotation) {
		// table
		this.placeBlockRotated(world, Blocks.OAK_STAIRS, this.getStairMeta(0 + rotation) + 4, 5, 1, 3, rotation, sbb);
		this.placeBlockRotated(world, Blocks.OAK_STAIRS, this.getStairMeta(3 + rotation) + 4, 5, 1, 4, rotation, sbb);
		this.placeBlockRotated(world, Blocks.OAK_STAIRS, this.getStairMeta(1 + rotation) + 4, 6, 1, 3, rotation, sbb);
		this.placeBlockRotated(world, Blocks.OAK_STAIRS, this.getStairMeta(2 + rotation) + 4, 6, 1, 4, rotation, sbb);
		// chairs
		this.placeBlockRotated(world, Blocks.SPRUCE_STAIRS, this.getStairMeta(3 + rotation), 5, 1, 2, rotation, sbb);
		this.placeBlockRotated(world, Blocks.SPRUCE_STAIRS, this.getStairMeta(0 + rotation), 7, 1, 3, rotation, sbb);
		this.placeBlockRotated(world, Blocks.SPRUCE_STAIRS, this.getStairMeta(1 + rotation), 6, 1, 5, rotation, sbb);
		this.placeBlockRotated(world, Blocks.SPRUCE_STAIRS, this.getStairMeta(2 + rotation), 4, 1, 4, rotation, sbb);
	}



}
