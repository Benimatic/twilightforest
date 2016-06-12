package twilightforest.structures.trollcave;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.TFTreasure;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponent;


public class ComponentTFTrollCaveConnect extends ComponentTFTrollCaveMain {
	
	protected boolean [] openingTowards = new boolean[] {false, false, true, false};

	public ComponentTFTrollCaveConnect() { }

	public ComponentTFTrollCaveConnect(int index, int x, int y, int z, int caveSize, int caveHeight, int direction) {
		super(index);
		this.size = caveSize;
		this.height = caveHeight;
		this.setCoordBaseMode(direction);
		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, size - 1, height - 1, size - 1, direction);
	}
	
	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);

        par1NBTTagCompound.setBoolean("openingTowards0", this.openingTowards[0]);
        par1NBTTagCompound.setBoolean("openingTowards1", this.openingTowards[1]);
        par1NBTTagCompound.setBoolean("openingTowards2", this.openingTowards[2]);
        par1NBTTagCompound.setBoolean("openingTowards3", this.openingTowards[3]);

	}
	
	/**
	 * Load from NBT
	 */
	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);

        // too lazy to do this as a loop
        this.openingTowards[0] = par1NBTTagCompound.getBoolean("openingTowards0");
        this.openingTowards[1] = par1NBTTagCompound.getBoolean("openingTowards1");
        this.openingTowards[2] = par1NBTTagCompound.getBoolean("openingTowards2");
        this.openingTowards[3] = par1NBTTagCompound.getBoolean("openingTowards3");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void buildComponent(StructureComponent parent, List list, Random rand) {
		// make 4 caves
		if (this.getComponentType() < 3) {

			for (int i = 0; i < 4; i++) {
				BlockPos dest = getValidOpening(rand, 2, i);

				if (rand.nextBoolean() || !makeGardenCave(list, rand, this.getComponentType() + 1, dest.posX, dest.posY, dest.posZ, 30, 15, i)) {
					makeSmallerCave(list, rand, this.getComponentType() + 1, dest.posX, dest.posY, dest.posZ, 20, 15, i);
				}

			}
		}
	}
	
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
        if (this.isBoundingBoxOutOfHighlands(world, sbb)) {
            return false;
        } else {
    		// clear inside
    		hollowCaveMiddle(world, sbb, rand, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1);
    		
    		Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));
    		
            // wall decorations
    		for (int i = 0; i < 4; i++) {
    			if (!this.openingTowards[i]) {
    				decorateWall(world, sbb, decoRNG, i);
    			}
    		}
    		
    		decoRNG.setSeed(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));
    		// stone stalactites!
    		for (int i = 0; i < 32; i++)
    		{
    			BlockPos dest = getCoordsInCave(decoRNG);
    			generateBlockStalactite(world, decoRNG, Blocks.STONE, 0.5F, true, dest.posX, 3, dest.posZ, sbb);
    		}
    		// stone stalagmites!
    		for (int i = 0; i < 8; i++)
    		{
    			BlockPos dest = getCoordsInCave(decoRNG);
    			generateBlockStalactite(world, decoRNG, Blocks.STONE, 0.5F, false, dest.posX, 3, dest.posZ, sbb);
    		}
    		
    		// possible treasure
    		decoRNG.setSeed(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));
    		if (this.countExits() == 1 && decoRNG.nextInt(3) == 0) {
	    		// treasure!
	    		makeTreasureCrate(world, decoRNG, sbb);
    		} else if (decoRNG.nextInt(3) == 0) {
    			// or a monolith!
	    		makeMonolith(world, decoRNG, sbb);
    		}

    		return true;
        }
	}

	protected void makeMonolith(World world, Random rand, StructureBoundingBox sbb) {
		// monolith
		int mid = this.size / 2;
		int height = 7 + rand.nextInt(8);
		int rotation = rand.nextInt(4);
		
		this.fillBlocksRotated(world, sbb, mid - 1, 0, mid - 1, mid - 1, height, mid - 1, Blocks.OBSIDIAN, 0, rotation);
		this.fillBlocksRotated(world, sbb, mid + 0, 0, mid - 1, mid + 0, height - 2, mid - 1, Blocks.OBSIDIAN, 0, rotation);
		this.fillBlocksRotated(world, sbb, mid - 1, 0, mid + 0, mid - 1, height - 2, mid + 0, Blocks.OBSIDIAN, 0, rotation);
		this.fillBlocksRotated(world, sbb, mid + 0, 0, mid + 0, mid + 0, height - 4, mid + 0, Blocks.OBSIDIAN, 0, rotation);
	}

	private int countExits() {
		int count = 0;
		for (int i = 0; i < this.openingTowards.length; i++) {
			if (this.openingTowards[i] == true) {
				count++;
			}
		}
		return count;
	}

	private void decorateWall(World world, StructureBoundingBox sbb, Random decoRNG, int rotation) {
		
		if (decoRNG.nextBoolean()) {
			decorateBracketMushrooms(world, sbb, decoRNG, rotation);
		} else if (decoRNG.nextBoolean()) {
			decorateStoneFormation(world, sbb, decoRNG, rotation);
			decorateStoneFormation(world, sbb, decoRNG, rotation);
		} else {
			decorateStoneProjection(world, sbb, decoRNG, rotation);
		}

	}

	private void decorateStoneFormation(World world, StructureBoundingBox sbb, Random decoRNG, int rotation) {
		int z = 5 + decoRNG.nextInt(7);
		int startY = 1 + decoRNG.nextInt(2);
		
		for (int y = startY; y < this.height; y += 2) {
			
			int width = 1;
			int depth = 1 + (decoRNG.nextInt(3) == 0 ? 1 : 0);
			makeSingleStoneFormation(world, sbb, decoRNG, rotation, z, y, width, depth);

			// wiggle a little
			z += decoRNG.nextInt(4) - decoRNG.nextInt(4);
			if (z < 5 || z > this.size - 5) {
				z = 5 + decoRNG.nextInt(7);
			}
		}
	}

	private void makeSingleStoneFormation(World world, StructureBoundingBox sbb, Random decoRNG, int rotation, int z, int y, int width, int depth) {
		if (decoRNG.nextInt(8) == 0) {
			this.fillBlocksRotated(world, sbb, size - (depth + 1), y - width, z - width, size - 1, y + width, z + width, Blocks.OBSIDIAN, 0, rotation);
		} else if (decoRNG.nextInt(4) == 0) {
			this.fillBlocksRotated(world, sbb, size - (depth + 1), y - width, z - width, size - 1, y + width, z + width, TFBlocks.trollSteinn, 0, rotation);
		} else {
			// normal stone
			this.fillBlocksRotated(world, sbb, size - (depth + 1), y - width, z - width, size - 1, y + width, z + width, Blocks.STONE, 0, rotation);
		}
		//this.randomlyFillBlocksRotated(world, sbb, decoRNG, 0.5F, size - (depth + 1), y - width, z - width, size - 1, y + width, z + width, TFBlocks.trollSteinn, 0, Blocks.STONE, 0, rotation);
	}

	private void decorateStoneProjection(World world, StructureBoundingBox sbb, Random decoRNG, int rotation) {
		int z = 7 + decoRNG.nextInt(3) - decoRNG.nextInt(3);
		int y = 7 + decoRNG.nextInt(3) - decoRNG.nextInt(3);
		
		this.randomlyFillBlocksRotated(world, sbb, decoRNG, 0.25F, size - 9, y, z, size - 2, y + 3, z + 3, TFBlocks.trollSteinn, 0, Blocks.STONE, 0, rotation);
		if (decoRNG.nextBoolean()) {
			// down
			this.randomlyFillBlocksRotated(world, sbb, decoRNG, 0.25F, size - 9, 1, z, size - 6, y - 1, z + 3, TFBlocks.trollSteinn, 0, Blocks.STONE, 0, rotation);
		} else {
			// up
			this.randomlyFillBlocksRotated(world, sbb, decoRNG, 0.25F, size - 9, y + 4, z, size - 6, height - 2, z + 3, TFBlocks.trollSteinn, 0, Blocks.STONE, 0, rotation);
		}
	}

	/**
	 * Decorate with a patch of bracket fungi
	 */
	private void decorateBracketMushrooms(World world, StructureBoundingBox sbb, Random decoRNG, int rotation) {
		int z = 5 + decoRNG.nextInt(7);
		int startY = 1 + decoRNG.nextInt(4);
		
		for (int y = startY; y < this.height; y += 2) {
			
			int width = 1 + decoRNG.nextInt(2) + decoRNG.nextInt(2);
			int depth = 1 + decoRNG.nextInt(2) + decoRNG.nextInt(2);
			Block mushBlock = ((decoRNG.nextInt(3) == 0) ? TFBlocks.hugeGloomBlock : (decoRNG.nextBoolean() ? Blocks.BROWN_MUSHROOM_BLOCK : Blocks.RED_MUSHROOM_BLOCK));
			makeSingleBracketMushroom(world, sbb, rotation, z, y, width, depth, mushBlock);

			// wiggle a little
			z += decoRNG.nextInt(4) - decoRNG.nextInt(4);
			if (z < 5 || z > this.size - 5) {
				z = 5 + decoRNG.nextInt(7);
			}
		}
	}

	/**
	 * Make one mushroom with the specified parameters
	 */
	private void makeSingleBracketMushroom(World world, StructureBoundingBox sbb, int rotation, int z, int y, int width, int depth, Block mushBlock) {
		
		this.fillBlocksRotated(world, sbb, size - depth, y, z - (width - 1), size - 2, y, z + (width - 1), mushBlock, 5, rotation);

		this.fillBlocksRotated(world, sbb, size - (depth + 1), y, z - (width - 1), size - (depth + 1), y, z + (width - 1), mushBlock, getMushroomMetaFor(4, rotation), rotation);
		
		for (int d = 0; d < (depth - 1); d++) {
			this.placeBlockRotated(world, mushBlock, getMushroomMetaFor(2, rotation), size - (2 + d), y, z - width, rotation, sbb);
		}
		this.placeBlockRotated(world, mushBlock, getMushroomMetaFor(1, rotation), size - (depth + 1), y, z - width, rotation, sbb);

		for (int d = 0; d < (depth - 1); d++) {
			this.placeBlockRotated(world, mushBlock, getMushroomMetaFor(8, rotation), size - (2 + d), y, z + width, rotation, sbb);
		}
		this.placeBlockRotated(world, mushBlock, getMushroomMetaFor(7, rotation), size - (depth + 1), y, z + width, rotation, sbb);

		
	}


	/**
	 * Hacky, incomplete method to generate mushroom block metadata
	 */
	private int getMushroomMetaFor(int meta, int rotation) {
		if (meta > 0 && meta < 10) {

			int totalRot = (coordBaseMode + rotation) % 4;

			switch (totalRot) {
			case 0:
				return meta;
			case 1:
				switch (meta) {
				case 1:
					return 3;
				case 2:
					return 6;
				case 4:
					return 2;
				case 7:
					return 1;
				case 8:
					return 4;
				}			
			case 2:
				return 10 - (meta % 10);
			case 3:
				switch (meta) {
				case 1:
					return 7;
				case 2:
					return 4;
				case 4:
					return 8;
				case 7:
					return 9;
				case 8:
					return 6;
				}			
			default:
				return 15;
			}
		} else {
			return meta;
		}
		
	}

	protected boolean makeGardenCave(List<StructureComponent> list, Random rand, int index, int x, int y, int z, int caveSize, int caveHeight, int rotation) {
		int direction = (getCoordBaseMode() + rotation) % 4;
		BlockPos dest = offsetTowerCCoords(x, y, z, caveSize, direction);
		
		ComponentTFTrollCaveMain cave = new ComponentTFTrollCaveGarden(index, dest.posX, dest.posY, dest.posZ, caveSize, caveHeight, direction);
		// check to see if it intersects something already there
		StructureComponent intersect = StructureComponent.findIntersecting(list, cave.getBoundingBox());
		StructureComponent otherGarden = findNearbyGarden(list, cave.getBoundingBox());
		if ((intersect == null || intersect == this) && otherGarden == null) {
			list.add(cave);
			cave.buildComponent(list.get(0), list, rand);
			//addOpening(x, y, z, rotation);
			
			this.openingTowards[rotation] = true;

			return true;
		}
		else
		{
			return false;
		}
	}

	private StructureComponent findNearbyGarden(List<StructureComponent> list, StructureBoundingBox boundingBox) {
		
		StructureBoundingBox largeBox = new StructureBoundingBox(boundingBox);
		largeBox.minX -= 30;
		largeBox.minY -= 30;
		largeBox.minZ -= 30;
		largeBox.maxX += 30;
		largeBox.maxY += 30;
		largeBox.maxZ += 30;
		
		for (StructureComponent component : list) {
			if (component instanceof ComponentTFTrollCaveGarden && component.getBoundingBox().intersectsWith(largeBox)) {
				//System.out.println("found intersecting garden");
				return component;
			}
		}
		
		return null;
	}
	
	protected boolean makeSmallerCave(List<StructureComponent> list, Random rand, int index, int x, int y, int z, int caveSize, int caveHeight, int rotation) {
		if (super.makeSmallerCave(list, rand, index, x, y, z, caveSize, caveHeight, rotation)) {
			this.openingTowards[rotation] = true;
			return true;
		} else {
			return false;
		}
	}
}
