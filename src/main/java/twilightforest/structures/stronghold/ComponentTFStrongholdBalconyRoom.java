package twilightforest.structures.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentTFStrongholdBalconyRoom extends StructureTFStrongholdComponent {
	
	boolean enterBottom;

	public ComponentTFStrongholdBalconyRoom() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFStrongholdBalconyRoom(int i, int facing, int x, int y, int z) {
		super(i, facing, x, y, z);
	}

	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
		
        par1NBTTagCompound.setBoolean("enterBottom", this.enterBottom);
	}

	/**
	 * Load from NBT
	 */
	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);
        this.enterBottom = par1NBTTagCompound.getBoolean("enterBottom");
	}

	/**
	 * Make a bounding box for this room
	 */
	public StructureBoundingBox generateBoundingBox(int facing, int x, int y, int z)
	{
		
		if (y > 17)
		{
			this.enterBottom = false;
		}
		else if (y < 11)
		{
			this.enterBottom = true;
		}
		else
		{
			this.enterBottom = (z & 1) == 0;
		}
		
		if (enterBottom)
		{
			return StructureTFStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -4, -1, 0, 18, 14, 27, facing);
		}
		else
		{
			// enter on the top
			return StructureTFStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -13, -8, 0, 18, 14, 27, facing);
		}
	}
	
    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
	@Override
	public void buildComponent(StructureComponent parent, List list, Random random) {
		super.buildComponent(parent, list, random);
		
		// lower left exit
		addNewComponent(parent, list, random, 0, 13, 1, 27);
		
		// lower middle right exit
		addNewComponent(parent, list, random, 1, -1, 1, 13);
		
		// lower middle left exit
		addNewComponent(parent, list, random, 3, 18, 1, 13);
		
		// upper left exit
		addNewComponent(parent, list, random, 0, 4, 8, 27);
		
		// upper close right exit
		addNewComponent(parent, list, random, 1, -1, 8, 4);
		
		// upper far left exit
		addNewComponent(parent, list, random, 3, 18, 8, 22);
		
		if (this.enterBottom)
		{
			this.addDoor(4, 1, 0);
			//this.addDoor(4, 1, 1);
			addNewComponent(parent, list, random, 2, 13, 8, -1);
		}
		else
		{
			this.addDoor(13, 8, 0);
			//this.addDoor(13, 8, 1);
			addNewComponent(parent, list, random, 2, 4, 1, -1);
		}


	}

	/**
	 * Generate the blocks that go here
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		placeStrongholdWalls(world, sbb, 0, 0, 0, 17, 13, 26, rand, deco.randomBlocks);

		// balcony
		this.fillWithRandomizedBlocks(world, sbb, 1, 6, 1, 16, 7, 25, false, rand, deco.randomBlocks);
		this.fillWithBlocks(world, sbb, 4, 8, 4, 13, 8, 22, deco.fenceID, Blocks.air, false);
		this.fillWithAir(world, sbb, 5, 6, 5, 12, 8, 21);
		
		// stairs & pillars
		placeStairsAndPillars(world, sbb, 0);
		placeStairsAndPillars(world, sbb, 2);

		// doors
		placeDoors(world, rand, sbb);
		
		return true;
	}

	private void placeStairsAndPillars(World world, StructureBoundingBox sbb, int rotation) {
		this.fillBlocksRotated(world, sbb, 4, 1, 4, 4, 12, 4, deco.pillarID, deco.pillarMeta, rotation);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(3 + rotation), 4, 1, 5, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(2 + rotation), 5, 1, 4, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(3 + rotation) + 4, 4, 5, 5, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(2 + rotation) + 4, 5, 5, 4, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(3 + rotation) + 4, 4, 12, 5, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(2 + rotation) + 4, 5, 12, 4, rotation, sbb);
		
		this.fillBlocksRotated(world, sbb, 13, 1, 4, 13, 12, 4, deco.pillarID, deco.pillarMeta, rotation);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(3 + rotation), 13, 1, 5, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(0 + rotation), 12, 1, 4, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(3 + rotation) + 4, 13, 5, 5, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(0 + rotation) + 4, 12, 5, 4, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(3 + rotation) + 4, 13, 12, 5, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(0 + rotation) + 4, 12, 12, 4, rotation, sbb);
		
		this.fillBlocksRotated(world, sbb, 13, 1, 8, 13, 12, 8, deco.pillarID, deco.pillarMeta, rotation);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(3 + rotation), 13, 1, 9, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(1 + rotation), 13, 1, 7, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(0 + rotation), 12, 1, 8, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(3 + rotation) + 4, 13, 5, 9, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(1 + rotation) + 4, 13, 5, 7, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(3 + rotation) + 4, 13, 12, 9, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(1 + rotation) + 4, 13, 12, 7, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(0 + rotation) + 4, 12, 12, 8, rotation, sbb);
		
		for (int y = 1; y < 8; y++)
		{
			for (int z = 5; z < 8; z++)
			{
				this.placeBlockRotated(world, Blocks.air, 0, y + 6, y + 1, z, rotation, sbb);
				this.placeBlockRotated(world, deco.stairID, this.getStairMeta(0 + rotation), y + 6, y, z, rotation, sbb);
				this.placeBlockRotated(world, deco.blockID, deco.blockMeta, y + 6, y - 1, z, rotation, sbb);
			}
		}
	}


}
