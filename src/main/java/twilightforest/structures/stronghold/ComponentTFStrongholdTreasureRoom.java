package twilightforest.structures.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.TFTreasure;
import twilightforest.entity.TFCreatures;

public class ComponentTFStrongholdTreasureRoom extends
		StructureTFStrongholdComponent {

	private boolean enterBottom;

	public ComponentTFStrongholdTreasureRoom() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFStrongholdTreasureRoom(int i, int facing, int x, int y, int z) {
		super(i, facing, x, y, z);
		// TODO Auto-generated constructor stub
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
		return StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, -4, -1, 0, 9, 7, 18, facing);
	}

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
	@Override
	public void buildComponent(StructureComponent parent, List list, Random random) {
		super.buildComponent(parent, list, random);
		
		this.addDoor(4, 1, 0);
	}

	/**
	 * Generate the blocks that go here
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		placeStrongholdWalls(world, sbb, 0, 0, 0, 8, 6, 17, rand, deco.randomBlocks);
		
		// statues
		this.placeWallStatue(world, 1, 1, 4, 1, sbb);
		this.placeWallStatue(world, 1, 1, 13, 1, sbb);
		this.placeWallStatue(world, 7, 1, 4, 3, sbb);
		this.placeWallStatue(world, 7, 1, 13, 3, sbb);
		this.placeWallStatue(world, 4, 1, 16, 0, sbb);
		
		this.fillWithRandomizedBlocks(world, sbb, 1, 1, 8, 7, 5, 9, false, rand, deco.randomBlocks);
		this.fillWithBlocks(world, sbb, 3, 1, 8, 5, 4, 9, Blocks.IRON_BARS, Blocks.IRON_BARS, false);
		
		// spawnwers
		this.placeSpawnerAtCurrentPosition(world, rand, 4, 1, 4, TFCreatures.getSpawnerNameFor("Helmet Crab"), sbb);
		
		this.placeSpawnerAtCurrentPosition(world, rand, 4, 4, 15, TFCreatures.getSpawnerNameFor("Helmet Crab"), sbb);
		
		// treasure!
		this.placeTreasureAtCurrentPosition(world, rand, 2, 4, 13, TFTreasure.stronghold_room, sbb);
		this.placeTreasureAtCurrentPosition(world, rand, 6, 4, 13, TFTreasure.stronghold_room, sbb);
		
		// doors
		placeDoors(world, rand, sbb);
		
		return true;
	}
	
	/**
	 * Make a doorway
	 */
	protected void placeDoorwayAt(World world, Random rand, int x, int y, int z, StructureBoundingBox sbb) {
		if (x == 0 || x == getXSize())
		{
			this.fillWithMetadataBlocks(world, sbb, x, y, z - 1, x, y + 3, z + 1, Blocks.IRON_BARS, 0, Blocks.AIR, 0, false);
		}
		else
		{
			this.fillWithMetadataBlocks(world, sbb, x - 1, y, z, x + 1, y + 3, z, Blocks.IRON_BARS, 0, Blocks.AIR, 0, false);
		}
	}


}
