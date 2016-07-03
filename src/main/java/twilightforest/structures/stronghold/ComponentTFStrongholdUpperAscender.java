package twilightforest.structures.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentTFStrongholdUpperAscender extends StructureTFStrongholdComponent {
	
	boolean exitTop;

	public ComponentTFStrongholdUpperAscender() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFStrongholdUpperAscender(int i, int facing, int x, int y, int z) {
		super(i, facing, x, y, z);
	}
	
	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
		
        par1NBTTagCompound.setBoolean("exitTop", this.exitTop);
	}

	/**
	 * Load from NBT
	 */
	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);
        this.exitTop = par1NBTTagCompound.getBoolean("exitTop");
	}


	/**
	 * Make a bounding box for this room
	 */
	@Override
	public StructureBoundingBox generateBoundingBox(int facing, int x, int y, int z)
	{
		if (y < 36)
		{
			this.exitTop = true;
			return StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, -2, -1, 0, 5, 10, 10, facing);
		}
		else
		{
			this.exitTop = false;
			return StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, -2, -6, 0, 5, 10, 10, facing);
		}
	}
	
    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
	@Override
	public void buildComponent(StructureComponent parent, List list, Random random) {
		super.buildComponent(parent, list, random);

		// make a random component on the other side
		addNewUpperComponent(parent, list, random, 0, 2, exitTop ? 6 : 1, 10);
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
        	placeUpperStrongholdWalls(world, sbb, 0, 0, 0, 4, 9, 9, rand, deco.randomBlocks);
        	
        	// entrance doorway
        	placeSmallDoorwayAt(world, rand, 2, 2, exitTop ? 1 : 6, 0, sbb);

        	// exit doorway
        	placeSmallDoorwayAt(world, rand, 0, 2, exitTop ? 6 : 1, 9, sbb);

        	// steps!
        	if (exitTop)
        	{
        		makeStairsAt(world, 1, 3, 1, sbb);
        		makeStairsAt(world, 2, 4, 1, sbb);
        		makeStairsAt(world, 3, 5, 1, sbb);
        		makeStairsAt(world, 4, 6, 1, sbb);
        		makeStairsAt(world, 5, 7, 1, sbb);
        		makePlatformAt(world, 5, 8, sbb);
        	}
        	else
        	{
        		makeStairsAt(world, 1, 6, 3, sbb);
        		makeStairsAt(world, 2, 5, 3, sbb);
        		makeStairsAt(world, 3, 4, 3, sbb);
        		makeStairsAt(world, 4, 3, 3, sbb);
        		makeStairsAt(world, 5, 2, 3, sbb);
        		makePlatformAt(world, 5, 1, sbb);
        	}
        	return true;
        }
	}

	/**
	 * Check if we can find at least one wall, and if so, generate stairs
	 */
	private void makeStairsAt(World world, int y, int z, int facing, StructureBoundingBox sbb) {
		// check walls
		if (this.getBlockAtCurrentPosition(world, 0, y, z, sbb) != Blocks.AIR || this.getBlockAtCurrentPosition(world, 4, y, z, sbb) != Blocks.AIR)
		{
			for (int x = 1; x < 4; x++)
			{
				this.placeBlockAtCurrentPosition(world, Blocks.STONE_BRICK_STAIRS, this.getStairMeta(facing), x, y, z, sbb);
			}
		}
	}
	
	/**
	 * Check if we can find at least one wall, and if so, generate blocks
	 */
	private void makePlatformAt(World world, int y, int z, StructureBoundingBox sbb) {
		// check walls
		if (this.getBlockAtCurrentPosition(world, 0, y, z, sbb) != Blocks.AIR || this.getBlockAtCurrentPosition(world, 4, y, z, sbb) != Blocks.AIR)
		{
			for (int x = 1; x < 4; x++)
			{
				this.placeBlockAtCurrentPosition(world, Blocks.STONEBRICK, 0, x, y, z, sbb);
			}
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
