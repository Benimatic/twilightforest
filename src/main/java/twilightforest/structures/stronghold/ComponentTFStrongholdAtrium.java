package twilightforest.structures.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.world.TFGenSmallRainboak;
import twilightforest.world.TFGenSmallTwilightOak;

public class ComponentTFStrongholdAtrium extends StructureTFStrongholdComponent {

	private boolean enterBottom;

	public ComponentTFStrongholdAtrium() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFStrongholdAtrium(int i, int facing, int x, int y, int z) {
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
	@Override
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
			return StructureTFStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -4, -1, 0, 18, 14, 18, facing);
		}
		else
		{
			// enter on the top
			return StructureTFStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -13, -8, 0, 18, 14, 18, facing);
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
			addNewComponent(parent, list, random, 2, 13, 8, -1);
		}
		else
		{
			this.addDoor(13, 8, 0);
			addNewComponent(parent, list, random, 2, 4, 1, -1);
		}
		
		addNewComponent(parent, list, random, 0, 13, 1, 18);
		addNewComponent(parent, list, random, 0, 4, 8, 18);

	}

	/**
	 * Generate the blocks that go here
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		placeStrongholdWalls(world, sbb, 0, 0, 0, 17, 13, 17, rand, deco.randomBlocks);
		

		// balcony
		this.fillWithRandomizedBlocks(world, sbb, 1, 6, 1, 16, 7, 16, false, rand, deco.randomBlocks);
		this.fillWithBlocks(world, sbb, 5, 8, 5, 12, 8, 12, deco.fenceID, Blocks.AIR, false);
		this.fillWithAir(world, sbb, 6, 6, 6, 11, 8, 11);
		
		// balcony pillars
		placeBalconyPillar(world, sbb, 0);
		placeBalconyPillar(world, sbb, 1);
		placeBalconyPillar(world, sbb, 2);
		placeBalconyPillar(world, sbb, 3);
		
		// corner pillars
		this.fillWithRandomizedBlocks(world, sbb, 1, 1, 1, 1, 12, 2, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 2, 1, 1, 2, 12, 1, false, rand, deco.randomBlocks);
		
		this.fillWithRandomizedBlocks(world, sbb, 16, 1, 1, 16, 12, 2, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 15, 1, 1, 15, 12, 1, false, rand, deco.randomBlocks);
		
		this.fillWithRandomizedBlocks(world, sbb, 1, 1, 15, 1, 12, 16, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 2, 1, 16, 2, 12, 16, false, rand, deco.randomBlocks);

		this.fillWithRandomizedBlocks(world, sbb, 16, 1, 15, 16, 12, 16, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 15, 1, 16, 15, 12, 16, false, rand, deco.randomBlocks);

		// grass
		this.randomlyFillWithBlocks(world, sbb, rand, 0.5F, 6, 0, 6, 11, 0, 11, Blocks.GRASS, Blocks.GRASS, false);
		this.fillWithBlocks(world, sbb, 7, 0, 7, 10, 0, 10, Blocks.GRASS, Blocks.AIR, false);
		
		// tree
		this.spawnATree(world, rand.nextInt(5), 8, 1, 8, sbb);
		
		// statues
		placeCornerStatue(world, 2, 8, 2, 0, sbb);
		placeCornerStatue(world, 2, 1, 15, 1, sbb);
		placeCornerStatue(world, 15, 1, 2, 2, sbb);
		placeCornerStatue(world, 15, 8, 15, 3, sbb);

		// doors
		placeDoors(world, rand, sbb);
		
		return true;
	}
	
	private void spawnATree(World world, int treeNum, int x, int y, int z, StructureBoundingBox sbb) 
	{
        int dx = getXWithOffset(x, z);
        int dy = getYWithOffset(y);
        int dz = getZWithOffset(x, z);
        if (sbb.isVecInside(dx, dy, dz))
        {
    		WorldGenerator treeGen;
    		// grow a tree
    		int minHeight = 8;
    		
			switch (treeNum)
    		{
    		case 0:
    		default:
    			// oak tree
    			treeGen = new WorldGenTrees(true, minHeight, 0, 0, false);
    			break;
    		case 1:
    			// jungle tree
    			treeGen = new WorldGenTrees(true, minHeight, 3, 3, false);
    			break;
    		case 2:
    			// birch
    			treeGen = new WorldGenTrees(true, minHeight, 2, 2, false);
    			break;
    		case 3:
    			treeGen = new TFGenSmallTwilightOak(false, minHeight);
    			break;
    		case 4:
    			treeGen = new TFGenSmallRainboak(false);
    			break;
    		}
    		
    		for (int i = 0; i < 100; i++)
    		{
    			if (treeGen.generate(world, world.rand, dx, dy, dz))
    			{
    				break;
    			}
    			
    			if (i == 99)
    			{
    				//System.out.println("Never generated " + treeGen);
    			}
    		}
        }
	}

	
	private void placeBalconyPillar(World world, StructureBoundingBox sbb, int rotation) {
		this.fillBlocksRotated(world, sbb, 5, 1, 5, 5, 12, 5, deco.pillarID, deco.pillarMeta, rotation);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(3 + rotation), 5, 1, 6, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(2 + rotation), 6, 1, 5, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(3 + rotation) + 4, 5, 5, 6, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(2 + rotation) + 4, 6, 5, 5, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(3 + rotation) + 4, 5, 12, 6, rotation, sbb);
		this.placeBlockRotated(world, deco.stairID, this.getStairMeta(2 + rotation) + 4, 6, 12, 5, rotation, sbb);
	}
}
