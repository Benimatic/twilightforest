package twilightforest.structures.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentTFStrongholdUpperRightTurn extends StructureTFStrongholdComponent {


	public ComponentTFStrongholdUpperRightTurn() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFStrongholdUpperRightTurn(int i, int facing, int x, int y, int z) {
		super(i, facing, x, y, z);
	}

	/**
	 * Make a bounding box for this room
	 */
	@Override
	public StructureBoundingBox generateBoundingBox(int facing, int x, int y, int z)
	{
		return StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, -2, -1, 0, 5, 5, 5, facing);
	}
	
    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
	@Override
	public void buildComponent(StructureComponent parent, List list, Random random) {
		super.buildComponent(parent, list, random);

		// make a random component to the right
		addNewUpperComponent(parent, list, random, 1, -1, 1, 2);
		
	}

	/**
	 * Generate the blocks that go here
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
        if (this.isLiquidInStructureBoundingBox(world, sbb))
        {
            return false;
        }
        else
        {
        	placeUpperStrongholdWalls(world, sbb, 0, 0, 0, 4, 4, 4, rand, deco.randomBlocks);

        	// entrance doorway
        	placeSmallDoorwayAt(world, rand, 2, 2, 1, 0, sbb);

        	// right turn doorway
        	placeSmallDoorwayAt(world, rand, 1, 0, 1, 2, sbb);

        	return true;
        }
	}

	/**
	 * Does this component fall under block protection when progression is turned on, normally true
	 */
	@Override
	public boolean isComponentProtected() {
		return false;
	}

}
