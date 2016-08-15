package twilightforest.structures.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class ComponentTFStrongholdFoundry extends StructureTFStrongholdComponent {
	
	int entranceLevel;

	public ComponentTFStrongholdFoundry() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComponentTFStrongholdFoundry(int i, int facing, int x, int y, int z) {
		super(i, facing, x, y, z);
	}
	
	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
		
        par1NBTTagCompound.setInteger("entranceLevel", this.entranceLevel);
	}

	/**
	 * Load from NBT
	 */
	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);
        this.entranceLevel = par1NBTTagCompound.getInteger("entranceLevel");
	}

	@Override
	public StructureBoundingBox generateBoundingBox(int facing, int x, int y, int z) {
		if (y > 17)
		{
			this.entranceLevel = 3;
			return StructureTFStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -4, -20, 0, 18, 25, 18, facing);
		}
		else if (y < 11)
		{
			this.entranceLevel = 1;
			return StructureTFStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -4, -6, 0, 18, 25, 18, facing);
		}
		else
		{
			this.entranceLevel = 2;
			return StructureTFStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -4, -13, 0, 18, 25, 18, facing);
		}
	}
	
    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
	@Override
	public void buildComponent(StructureComponent parent, List list, Random random) {
		super.buildComponent(parent, list, random);
		
		switch (this.entranceLevel)
		{
		case 1:
			this.addDoor(4, 6, 0);
			addNewComponent(parent, list, random, 1, -1, 13, 13);
			addNewComponent(parent, list, random, 3, 18, 13, 4);
			addNewComponent(parent, list, random, 0, 13, 20, 18);
			break;
		case 2:
			this.addDoor(4, 13, 0);
			addNewComponent(parent, list, random, 1, -1, 6, 13);
			addNewComponent(parent, list, random, 3, 18, 20, 4);
			addNewComponent(parent, list, random, 0, 13, 13, 18);
			break;
		case 3:
			this.addDoor(4, 20, 0);
			addNewComponent(parent, list, random, 0, 13, 6, 18);
			addNewComponent(parent, list, random, 1, -1, 13, 13);
			addNewComponent(parent, list, random, 3, 18, 13, 4);
		}
		

	}

	/**
	 * Generate the blocks that go here
	 */
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		placeStrongholdWalls(world, sbb, 0, 0, 0, 17, 25, 17, rand, deco.randomBlocks);
		
		// lava bottom
		this.fillWithBlocks(world, sbb, 1, 0, 1, 16, 4, 16, Blocks.lava, Blocks.lava, false);
		
		// top ledge
		this.fillWithRandomizedBlocks(world, sbb, 1, 19, 1, 16, 19, 16, false, rand, deco.randomBlocks);
		this.fillWithAir(world, sbb, 2, 19, 2, 15, 19, 15);		
		
		// middle ledge
		this.fillWithRandomizedBlocks(world, sbb, 1, 12, 1, 16, 12, 16, false, rand, deco.randomBlocks);
		this.fillWithAir(world, sbb, 2, 12, 2, 15, 12, 15);	
		
		// bottom ledge
		this.fillWithRandomizedBlocks(world, sbb, 1, 5, 1, 16, 5, 16, false, rand, deco.randomBlocks);
		this.fillWithAir(world, sbb, 2, 5, 2, 15, 5, 15);
		
		// corner pillars
		this.fillWithRandomizedBlocks(world, sbb, 1, 1, 1, 1, 24, 2, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 2, 1, 1, 2, 24, 1, false, rand, deco.randomBlocks);
		
		this.fillWithRandomizedBlocks(world, sbb, 16, 1, 1, 16, 24, 2, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 15, 1, 1, 15, 24, 1, false, rand, deco.randomBlocks);
		
		this.fillWithRandomizedBlocks(world, sbb, 1, 1, 15, 1, 24, 16, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 2, 1, 16, 2, 24, 16, false, rand, deco.randomBlocks);

		this.fillWithRandomizedBlocks(world, sbb, 16, 1, 15, 16, 24, 16, false, rand, deco.randomBlocks);
		this.fillWithRandomizedBlocks(world, sbb, 15, 1, 16, 15, 24, 16, false, rand, deco.randomBlocks);
		
		// suspended mass
		Random massRandom = new org.bogdang.modifications.random.XSTR(rand.nextLong());
		
		for (int x = 4; x < 14; x++)
		{
			for (int z = 4; z < 14; z++)
			{
				for (int y = 8; y < 23; y++)
				{
					float c = Math.abs(x - 8.5F) + Math.abs(z - 8.5F) + Math.abs(y - 18F);
					float r = 5.5F + ((massRandom.nextFloat() - massRandom.nextFloat()) * 3.5F);
					
					if (c < r)
					{
						this.placeBlockAtCurrentPosition(world, Blocks.stone, 0, x, y, z, sbb);
					}
				}
			}
		}
		
		// drips
		for (int i = 0; i < 400; i++)
		{
			int dx = massRandom.nextInt(9) + 5;
			int dz = massRandom.nextInt(9) + 5;
			int dy = massRandom.nextInt(13) + 10;

			if (this.getBlockAtCurrentPosition(world, dx, dy, dz, sbb) != Blocks.air)
			{
				for (int y = 0; y < 3; y++)
				{
					this.placeBlockAtCurrentPosition(world, Blocks.stone, 0, dx, dy - y, dz, sbb);
				}
			}
		}
		
		// add some redstone ore
		for (int i = 0; i < 8; i++)
		{
			Block blockID = Blocks.redstone_ore;
			int blockMeta = 0;
			
			addOreToMass(world, sbb, massRandom, blockID, blockMeta);
		}
		
		// add some iron ore
		for (int i = 0; i < 8; i++)
		{
			Block blockID = Blocks.iron_ore;
			int blockMeta = 0;
			
			addOreToMass(world, sbb, massRandom, blockID, blockMeta);
		}
		
		// add some gold ore
		for (int i = 0; i < 6; i++)
		{
			Block blockID = Blocks.gold_ore;
			int blockMeta = 0;
			
			addOreToMass(world, sbb, massRandom, blockID, blockMeta);
		}
		
		// add some glowstone
		for (int i = 0; i < 2; i++)
		{
			Block blockID = Blocks.glowstone;
			int blockMeta = 0;
			
			addOreToMass(world, sbb, massRandom, blockID, blockMeta);
		}
		
		
		// add some emerald ore
		for (int i = 0; i < 2; i++)
		{
			Block blockID = Blocks.emerald_ore;
			int blockMeta = 0;
			
			addOreToMass(world, sbb, massRandom, blockID, blockMeta);
		}
		
		// add some diamond ore
		for (int i = 0; i < 4; i++)
		{
			Block blockID = Blocks.diamond_ore;
			int blockMeta = 0;
			
			addOreToMass(world, sbb, massRandom, blockID, blockMeta);
		}
		
		// doors
		placeDoors(world, rand, sbb);
		
		return true;
	}

	/**
	 * Add a block of ore to the mass
	 */
	private void addOreToMass(World world, StructureBoundingBox sbb, Random massRandom, Block blockID, int blockMeta) {
		for (int i = 0; i < 10; i++)
		{
			int dx = massRandom.nextInt(9) + 5;
			int dz = massRandom.nextInt(9) + 5;
			int dy = massRandom.nextInt(13) + 10;
	
			if (this.getBlockAtCurrentPosition(world, dx, dy, dz, sbb) != Blocks.air)
			{
				this.placeBlockAtCurrentPosition(world, blockID, blockMeta, dx, dy, dz, sbb);
				// we have succeeded, stop looping
				break;
			}
		}
	}

}
