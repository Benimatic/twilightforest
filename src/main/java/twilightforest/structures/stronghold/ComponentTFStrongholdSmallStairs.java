package twilightforest.structures.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.TFTreasure;

public class ComponentTFStrongholdSmallStairs extends
		StructureTFStrongholdComponent {

	private boolean enterBottom;
	public boolean hasTreasure;
	public boolean chestTrapped;

	public ComponentTFStrongholdSmallStairs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFStrongholdSmallStairs(int i, int facing, int x, int y, int z) {
		super(i, facing, x, y, z);
	}
	
	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
		
        par1NBTTagCompound.setBoolean("enterBottom", this.enterBottom);
        par1NBTTagCompound.setBoolean("hasTreasure", this.hasTreasure);
        par1NBTTagCompound.setBoolean("chestTrapped", this.chestTrapped);
	}

	/**
	 * Load from NBT
	 */
	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);
        this.enterBottom = par1NBTTagCompound.getBoolean("enterBottom");
        this.hasTreasure = par1NBTTagCompound.getBoolean("hasTreasure");
        this.chestTrapped = par1NBTTagCompound.getBoolean("chestTrapped");
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
			return StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, -4, -1, 0, 9, 14, 9, facing);
		}
		else
		{
			// enter on the top
			return StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, -4, -8, 0, 9, 14, 9, facing);
		}
	}

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
	@Override
	public void buildComponent(StructureComponent parent, List list, Random random) {
		super.buildComponent(parent, list, random);
		
		if (this.enterBottom)
		{
			this.addDoor(4, 1, 0);
			addNewComponent(parent, list, random, 0, 4, 8, 9);
		}
		else
		{
			this.addDoor(4, 8, 0);
			addNewComponent(parent, list, random, 0, 4, 1, 9);
		}
		
		this.hasTreasure = random.nextBoolean();
		this.chestTrapped = random.nextInt(3) == 0;
	}

	/**
	 * Generate the blocks that go here
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		placeStrongholdWalls(world, sbb, 0, 0, 0, 8, 13, 8, rand, deco.randomBlocks);
		
		// railing
		this.fillWithMetadataBlocks(world, sbb, 1, 7, 1, 7, 7, 7, deco.platformID, deco.platformMeta, Blocks.air, 0, false);
		this.fillWithAir(world, sbb, 2, 7, 2, 6, 7, 6);
		
		int rotation = this.enterBottom ? 0 : 2;
		
		// stairs
		for (int y = 1; y < 8; y++)
		{
			for (int x = 3; x < 6; x++)
			{
				this.placeBlockRotated(world, Blocks.air, 0, x, y + 1, y, rotation, sbb);
				this.placeBlockRotated(world, deco.stairID, this.getStairMeta(1 + rotation),x, y, y, rotation, sbb);
				this.placeBlockRotated(world, deco.blockID, deco.blockMeta, x, y - 1, y, rotation, sbb);
			}
		}
		
		// treasure
		if (this.hasTreasure)
		{
			this.placeTreasureRotated(world, 4, 1, 6, rotation, TFTreasure.stronghold_cache, this.chestTrapped, sbb);
			
			if (this.chestTrapped)
			{
				this.placeBlockRotated(world, Blocks.tnt, 0, 4, 0, 6, rotation, sbb);
			}

			for (int z = 5; z < 8; z++)
			{
				this.placeBlockRotated(world, deco.stairID, this.getStairMeta(0 + rotation), 3, 1, z, rotation, sbb);
				this.placeBlockRotated(world, deco.stairID, this.getStairMeta(2 + rotation), 5, 1, z, rotation, sbb);
			}

			this.placeBlockRotated(world, deco.stairID, this.getStairMeta(1 + rotation), 4, 1, 5, rotation, sbb);
			this.placeBlockRotated(world, deco.stairID, this.getStairMeta(3 + rotation), 4, 1, 7, rotation, sbb);
			this.placeBlockRotated(world, deco.stairID, this.getStairMeta(1 + rotation), 4, 2, 6, rotation, sbb);
		}
		
		if (enterBottom)
		{
			this.placeWallStatue(world, 4, 8, 1, 2, sbb);
		}
		else
		{
			this.placeWallStatue(world, 4, 8, 7, 0, sbb);
		}
		
		// doors
		placeDoors(world, rand, sbb);
		
		return true;
	}

}
