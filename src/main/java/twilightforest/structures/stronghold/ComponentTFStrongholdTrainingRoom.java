package twilightforest.structures.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentTFStrongholdTrainingRoom extends StructureTFStrongholdComponent {

	public ComponentTFStrongholdTrainingRoom() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFStrongholdTrainingRoom(int i, int facing, int x, int y, int z) {
		super(i, facing, x, y, z);
	}

	/**
	 * Make a bounding box for this room
	 */
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

		// sand floor
		this.randomlyFillWithBlocks(world, sbb, rand, 0.7F, 4, 0, 4, 8, 0, 8, Blocks.SAND, Blocks.SAND, false);
		this.randomlyFillWithBlocks(world, sbb, rand, 0.7F, 9, 0, 4, 13, 0, 8, Blocks.SAND, Blocks.SAND, false);
		this.randomlyFillWithBlocks(world, sbb, rand, 0.7F, 9, 0, 9, 13, 0, 13, Blocks.SAND, Blocks.SAND, false);
		
		// training dummies
		placeTrainingDummy(world, sbb, 0);
		placeTrainingDummy(world, sbb, 1);
		placeTrainingDummy(world, sbb, 2);
		
		// anvil pad
		this.fillWithBlocks(world, sbb, 5, 0, 10, 7, 0, 12, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
		
		this.placeBlockAtCurrentPosition(world, deco.pillarID, deco.pillarMeta, 5, 1, 12, sbb);
		this.placeBlockAtCurrentPosition(world, deco.pillarID, deco.pillarMeta, 5, 2, 12, sbb);
		this.placeBlockAtCurrentPosition(world, deco.pillarID, deco.pillarMeta, 6, 1, 12, sbb);
		this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(2), 6, 2, 12, sbb);
		this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(2), 7, 1, 12, sbb);
		this.placeBlockAtCurrentPosition(world, deco.pillarID, deco.pillarMeta, 5, 1, 11, sbb);
		this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(1), 5, 2, 11, sbb);
		this.placeBlockAtCurrentPosition(world, deco.stairID, this.getStairMeta(1), 5, 1, 10, sbb);

		this.placeBlockAtCurrentPosition(world, Blocks.ANVIL, 0, 6, 1, 11, sbb);

		// doors
		placeDoors(world, rand, sbb);
		
		return true;
	}

	private void placeTrainingDummy(World world, StructureBoundingBox sbb, int rotation) {
		this.fillBlocksRotated(world, sbb, 5, 0, 5, 7, 0, 7, Blocks.SAND, 0, rotation);
		this.placeBlockRotated(world, deco.fenceID, deco.fenceMeta, 6, 1, 6, rotation, sbb);
		this.placeBlockRotated(world, Blocks.PLANKS, 2, 6, 2, 6, rotation, sbb);
		this.placeBlockRotated(world, Blocks.FENCE, 0, 5, 2, 6, rotation, sbb);
		this.placeBlockRotated(world, Blocks.FENCE, 0, 7, 2, 6, rotation, sbb);
		this.placeBlockRotated(world, Blocks.PUMPKIN, this.getStairMeta(0 + rotation), 6, 3, 6, rotation, sbb);
	}

}
