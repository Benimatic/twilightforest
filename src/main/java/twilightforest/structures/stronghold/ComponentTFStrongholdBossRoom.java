package twilightforest.structures.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.TFBlocks;

public class ComponentTFStrongholdBossRoom extends StructureTFStrongholdComponent {
	
	public ComponentTFStrongholdBossRoom() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFStrongholdBossRoom(int i, int facing, int x, int y, int z) {
		super(i, facing, x, y, z);
		this.spawnListIndex = Integer.MAX_VALUE;
	}

	/**
	 * Make a bounding box for this room
	 */
	public StructureBoundingBox generateBoundingBox(int facing, int x, int y, int z)
	{
		return StructureTFStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -13, -1, 0, 27, 7, 27, facing);
	}
	
    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
	@Override
	public void buildComponent(StructureComponent parent, List list, Random random) {
		super.buildComponent(parent, list, random);

		this.addDoor(13, 1, 0);
	}

	/**
	 * Generate the blocks that go here
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		placeStrongholdWalls(world, sbb, 0, 0, 0, 26, 6, 26, rand, deco.randomBlocks);
		
		// inner walls
		this.fillWithRandomizedBlocks(world, sbb, 1, 1, 1, 3, 5, 25, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 23, 1, 1, 25, 5, 25, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 4, 1, 1, 22, 5, 3, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 4, 1, 23, 22, 5, 25, false, rand, deco.randomBlocks);
		
		// obsidian filler
		this.fillWithMetadataBlocks(world, sbb, 1, 1, 1, 2, 5, 25, Blocks.obsidian, 0, Blocks.obsidian, 0, false);
		this.fillWithMetadataBlocks(world, sbb, 24, 1, 1, 25, 5, 25, Blocks.obsidian, 0, Blocks.obsidian, 0, false);
		this.fillWithMetadataBlocks(world, sbb, 4, 1, 1, 22, 5, 2, Blocks.obsidian, 0, Blocks.obsidian, 0, false);
		this.fillWithMetadataBlocks(world, sbb, 4, 1, 24, 22, 5, 25, Blocks.obsidian, 0, Blocks.obsidian, 0, false);
		
		// corner pillars
		this.fillWithRandomizedBlocks(world, sbb, 4, 1, 4, 4, 5, 7, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 5, 1, 4, 5, 5, 5, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 6, 1, 4, 7, 5, 4, false, rand, deco.randomBlocks);

		this.fillWithRandomizedBlocks(world, sbb, 4, 1, 19, 4, 5, 22, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 5, 1, 21, 5, 5, 22, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 6, 1, 22, 7, 5, 22, false, rand, deco.randomBlocks);

		this.fillWithRandomizedBlocks(world, sbb, 22, 1, 4, 22, 5, 7, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 21, 1, 4, 21, 5, 5, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 19, 1, 4, 20, 5, 4, false, rand, deco.randomBlocks);

		this.fillWithRandomizedBlocks(world, sbb, 22, 1, 19, 22, 5, 22, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 21, 1, 21, 21, 5, 22, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 19, 1, 22, 20, 5, 22, false, rand, deco.randomBlocks);
		
		// pillar decorations (stairs)
		placePillarDecorations(world, sbb, 0);
		placePillarDecorations(world, sbb, 1);
		placePillarDecorations(world, sbb, 2);
		placePillarDecorations(world, sbb, 3);
		
		// sarcophagi
		placeSarcophagus(world, sbb, 8, 1, 8, 0);
		placeSarcophagus(world, sbb, 13, 1, 8, 0);
		placeSarcophagus(world, sbb, 18, 1, 8, 0);

		placeSarcophagus(world, sbb, 8, 1, 15, 0);
		placeSarcophagus(world, sbb, 13, 1, 15, 0);
		placeSarcophagus(world, sbb, 18, 1, 15, 0);

		
		// doorway
		this.fillWithAir(world, sbb, 12, 1, 1, 14, 4, 2);
		this.fillWithBlocks(world, sbb, 12, 1, 3, 14, 4, 3, Blocks.iron_bars, Blocks.iron_bars, false);
		
		int var8 = this.getXWithOffset(0, 0);
		int var9 = this.getYWithOffset(0);
		int var10 = this.getZWithOffset(0, 0);

		//System.out.println("Drawing stronghold boss room at " + var8 + ", " + var9 + ", " + var10);

		
		//spawner
		placeBlockAtCurrentPosition(world, TFBlocks.bossSpawner, 4, 13, 2, 13, sbb);

		
		// out of order
		//this.placeSignAtCurrentPosition(world, 13, 1, 5, "Boss Room", "Out of Order", sbb);
		
		
		// doors
		placeDoors(world, rand, sbb);
		
		return true;
	}



	private void placeSarcophagus(World world, StructureBoundingBox sbb, int x, int y, int z, int rotation) {
		
		this.placeBlockRotated(world, deco.pillarID, deco.pillarMeta, x + 1, y, z + 0, rotation, sbb);
		this.placeBlockRotated(world, deco.pillarID, deco.pillarMeta, x - 1, y, z + 0, rotation, sbb);
		this.placeBlockRotated(world, deco.pillarID, deco.pillarMeta, x + 1, y, z + 3, rotation, sbb);
		this.placeBlockRotated(world, deco.pillarID, deco.pillarMeta, x - 1, y, z + 3, rotation, sbb);
		
		// make either torches or fence posts
		
		if (world.rand.nextInt(7) == 0)
		{
			this.placeBlockRotated(world, Blocks.torch, 0, x + 1, y + 1, z + 0, rotation, sbb);
		}
		else
		{
			this.placeBlockRotated(world, deco.fenceID, deco.fenceMeta, x + 1, y + 1, z + 0, rotation, sbb);
		}
		if (world.rand.nextInt(7) == 0)
		{
			this.placeBlockRotated(world, Blocks.torch, 0, x - 1, y + 1, z + 0, rotation, sbb);
		}
		else
		{
			this.placeBlockRotated(world, deco.fenceID, deco.fenceMeta, x - 1, y + 1, z + 0, rotation, sbb);
		}
		if (world.rand.nextInt(7) == 0)
		{
			this.placeBlockRotated(world, Blocks.torch, 0, x + 1, y + 1, z + 3, rotation, sbb);
		}
		else
		{
			this.placeBlockRotated(world, deco.fenceID, deco.fenceMeta, x + 1, y + 1, z + 3, rotation, sbb);
		}
		if (world.rand.nextInt(7) == 0)
		{
			this.placeBlockRotated(world, Blocks.torch, 0, x - 1, y + 1, z + 3, rotation, sbb);
		}
		else
		{
			this.placeBlockRotated(world, deco.fenceID, deco.fenceMeta, x - 1, y + 1, z + 3, rotation, sbb);
		}
		
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(1 + rotation), x + 0, y, z + 0, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(3 + rotation), x + 0, y, z + 3, rotation, sbb);
	
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(2 + rotation), x + 1, y, z + 1, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(2 + rotation), x + 1, y, z + 2, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(0 + rotation), x - 1, y, z + 1, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(0 + rotation), x - 1, y, z + 2, rotation, sbb);
		
		this.placeBlockRotated(world, Blocks.stone_slab, 0, x + 0, y + 1, z + 1, rotation, sbb);
		this.placeBlockRotated(world, Blocks.stone_slab, 0, x + 0, y + 1, z + 2, rotation, sbb);

	}

	protected void placePillarDecorations(World world, StructureBoundingBox sbb, int rotation) {
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(3 + rotation), 4, 1, 8, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(2 + rotation), 8, 1, 4, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(3 + rotation) + 4, 4, 5, 8, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(2 + rotation) + 4, 8, 5, 4, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(3 + rotation), 5, 1, 6, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(2 + rotation), 6, 1, 6, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(2 + rotation), 6, 1, 5, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(3 + rotation) + 4, 5, 5, 6, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(2 + rotation) + 4, 6, 5, 6, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(2 + rotation) + 4, 6, 5, 5, rotation, sbb);
	}
	
	/**
	 * Make a doorway - this is the iron fence door
	 */
	protected void placeDoorwayAt(World world, Random rand, int x, int y, int z, StructureBoundingBox sbb) {
		if (x == 0 || x == getXSize())
		{
			this.fillWithMetadataBlocks(world, sbb, x, y, z - 1, x, y + 3, z + 1, Blocks.iron_bars, 0, Blocks.air, 0, false);
		}
		else
		{
			this.fillWithMetadataBlocks(world, sbb, x - 1, y, z, x + 1, y + 3, z, Blocks.iron_bars, 0, Blocks.air, 0, false);
		}
	}
	
	/**
	 * Is the specified point a valid spot to break in?
	 */
	protected boolean isValidBreakInPoint(int wx, int wy, int wz) {
		return false;
	}
}
