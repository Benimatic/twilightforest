package twilightforest.structures.trollcave;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.TFTreasure;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponent;
import twilightforest.world.TFGenCaveStalactite;
import twilightforest.world.TFGenMyceliumBlob;

public class ComponentTFTrollCaveMain extends StructureTFComponent {
	
	protected int size;
	protected int height;
	
	public static final TFGenMyceliumBlob uberousGen = new TFGenMyceliumBlob(TFBlocks.uberousSoil, 4);



	public ComponentTFTrollCaveMain() { }

	public ComponentTFTrollCaveMain(int index) {
		super(index);
	}


	public ComponentTFTrollCaveMain(World world, Random rand, int i, int x, int y, int z) {
		this.setCoordBaseMode(0);
		
		// adjust y
		y += 10;
		
		this.size = 30;
		this.height = 20;
		
		int radius = this.size / 2;
		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, -radius, -this.height, -radius, this.size, this.height, this.size, 0);

	}
	
	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
		
        par1NBTTagCompound.setInteger("size", this.size);
        par1NBTTagCompound.setInteger("height", this.height);
	}

	/**
	 * Load from NBT
	 */
	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);
        this.size = par1NBTTagCompound.getInteger("size");
        this.height = par1NBTTagCompound.getInteger("height");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void buildComponent(StructureComponent parent, List list, Random rand) {
		// make 4 caves
		for (int i = 0; i < 4; i++) {
			ChunkCoordinates dest = getValidOpening(rand, 5, i);
			
			makeSmallerCave(list, rand, this.getComponentType() + 1, dest.posX, dest.posY, dest.posZ, 18, 15, i);

		}
		
		// add cloud castle
		ComponentTFCloudCastle castle = new ComponentTFCloudCastle(this.getComponentType() + 1, boundingBox.minX + ((boundingBox.maxX - boundingBox.minX) / 2), 168, boundingBox.minZ + ((boundingBox.maxZ - boundingBox.minZ) / 2));
		list.add(castle);
		castle.buildComponent(this, list, rand);
		
		// add vault
		ComponentTFTrollVault vault = new ComponentTFTrollVault(this.getComponentType() + 1, boundingBox.minX + ((boundingBox.maxX - boundingBox.minX) / 2), boundingBox.minY, boundingBox.minZ + ((boundingBox.maxZ - boundingBox.minZ) / 2));
		list.add(vault);
		vault.buildComponent(this, list, rand);
	}


	protected boolean makeSmallerCave(List<StructureComponent> list, Random rand, int index, int x, int y, int z, int caveSize, int caveHeight, int rotation) {
		int direction = (getCoordBaseMode() + rotation) % 4;
		ChunkCoordinates dest = offsetTowerCCoords(x, y, z, caveSize, direction);
		
		ComponentTFTrollCaveConnect cave = new ComponentTFTrollCaveConnect(index, dest.posX, dest.posY, dest.posZ, caveSize, caveHeight, direction);
		// check to see if it intersects something already there
		StructureComponent intersect = StructureComponent.findIntersecting(list, cave.getBoundingBox());
		if (intersect == null || intersect == this) {
			list.add(cave);
			cave.buildComponent(list.get(0), list, rand);
			//addOpening(x, y, z, rotation);
			return true;
		}
		else
		{
			return false;
		}
	}


	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		Random decoRNG = new org.bogdang.modifications.random.XSTR(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));
	
		// clear inside
		hollowCaveMiddle(world, sbb, rand, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1);
		
		// stone stalactites!
		for (int i = 0; i < 128; i++)
		{
			ChunkCoordinates dest = getCoordsInCave(decoRNG);
			generateBlockStalactite(world, decoRNG, Blocks.stone, 0.7F, true, dest.posX, 3, dest.posZ, sbb);
		}
		// stone stalagmites!
		for (int i = 0; i < 32; i++)
		{
			ChunkCoordinates dest = getCoordsInCave(decoRNG);
			generateBlockStalactite(world, decoRNG, Blocks.stone, 0.5F, false, dest.posX, 3, dest.posZ, sbb);
		}
		
		
		// uberous!
		for (int i = 0; i < 32; i++)
		{
			ChunkCoordinates dest = getCoordsInCave(decoRNG);
			
			generateAtSurface(world, uberousGen, decoRNG, dest.posX, 60, dest.posZ, sbb);
		}
		
		return true;
	}

	
    protected ChunkCoordinates getCoordsInCave(Random rand) {
		return new ChunkCoordinates(rand.nextInt(this.size - 1), rand.nextInt(this.height - 1), rand.nextInt(this.size - 1));
	}

	/**
     * arguments: (World worldObj, StructureBoundingBox structBB, int minX, int minY, int minZ, int maxX, int maxY, int
     * maxZ)
	 * @param rand 
     */
    protected void hollowCaveMiddle(World par1World, StructureBoundingBox par2StructureBoundingBox, Random rand, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
    {
        int threshold = this.size / 5;

        for (int y = minY; y <= maxY; ++y)
        {
            for (int x = minX; x <= maxX; ++x)
            {
                for (int z = minZ; z <= maxZ; ++z)
                {
                	int ex = Math.min(x - minX, maxX - x);
                	int ey = Math.min((y - minY) * 2, maxY - y);
                	int ez = Math.min(z - minZ, maxZ - z);
                	
                	double dist = Math.sqrt(ex * ey * ez);
                	
					if (dist > threshold) {
                		this.placeBlockAtCurrentPosition(par1World, Blocks.air, 0, x, y, z, par2StructureBoundingBox);
                	} else if (dist == threshold && rand.nextInt(4) == 0 && this.getBlockAtCurrentPosition(par1World, x, y, z, par2StructureBoundingBox) == Blocks.stone) {
                		this.placeBlockAtCurrentPosition(par1World, TFBlocks.trollSteinn, 0, x, y, z, par2StructureBoundingBox);
                	}
                }
            }
        }
    }
    
    
	/**
	 * Gets a random position in the specified direction that connects to stairs currently in the tower.
	 */
	public ChunkCoordinates getValidOpening(Random rand, int caveHeight, int direction) {
		// variables!
		int offset = this.size / 4; // wall thickness
		int wLength = size - (offset * 2); // wall length

		// for directions 0 or 2, the wall lies along the z axis
		if (direction == 0 || direction == 2) {
			int rx = direction == 0 ? size - 1 : 0;
			int rz = offset + rand.nextInt(wLength);
			int ry = (rand.nextInt(offset) - rand.nextInt(offset));
			
			return new ChunkCoordinates(rx, ry, rz);
		}
		
		// for directions 1 or 3, the wall lies along the x axis
		if (direction == 1 || direction == 3) {
			int rx = offset + rand.nextInt(wLength);
			int rz = direction == 1 ? size - 1 : 0;
			int ry = (rand.nextInt(offset) - rand.nextInt(offset));
			
			return new ChunkCoordinates(rx, ry, rz);
		}
		
		
		return null;
	}
	
	
	/**
	 * Provides coordinates to make a tower such that it will open into the parent tower at the provided coordinates.
	 */
	protected ChunkCoordinates offsetTowerCCoords(int x, int y, int z, int towerSize, int direction) {
		
		int dx = getXWithOffset(x, z);
		int dy = getYWithOffset(y);
		int dz = getZWithOffset(x, z);
		
		if (direction == 0) {
			return new ChunkCoordinates(dx - 1, dy - 1, dz - towerSize / 2);
		} else if (direction == 1) {
			return new ChunkCoordinates(dx + towerSize / 2, dy - 1, dz - 1);
		} else if (direction == 2) {
			return new ChunkCoordinates(dx + 1, dy - 1, dz + towerSize / 2);
		} else if (direction == 3) {
			return new ChunkCoordinates(dx - towerSize / 2, dy - 1, dz + 1);
		}
		
		
		// ugh?
		return new ChunkCoordinates(x, y, z);
	}
	

	public boolean isBoundingBoxOutOfHighlands(World world, StructureBoundingBox sbb) {
        int minX = this.boundingBox.minX - 1;
        int minZ = this.boundingBox.minZ - 1;
        int maxX = this.boundingBox.maxX + 1;
        int maxZ = this.boundingBox.maxZ + 1;

        for (int x = minX; x <= maxX; x++) {
        	for (int z = minZ; z <= maxZ; z++) {
        		if (world.getBiomeGenForCoords(x, z) != TFBiomeBase.highlands) {
        			return true;
        		}
        	}        
        }

        return false;
	}

	
	/**
	 * Make a random stone stalactite
	 */
	protected void generateBlockStalactite(World world, Random rand, Block blockToGenerate, float length, boolean up, int x, int y, int z, StructureBoundingBox sbb) {
		// are the coordinates in our bounding box?
        int dx = getXWithOffset(x, z);
        int dy = getYWithOffset(y);
        int dz = getZWithOffset(x, z);
        if(sbb.isVecInside(dx, dy, dz)) {
        	(new TFGenCaveStalactite(blockToGenerate, length, up)).generate(world, rand, dx, dy, dz);
        }
	}

	/**
	 * Use the generator at the surface above specified coords
	 */
	protected void generateAtSurface(World world, WorldGenerator generator, Random rand, int x, int y, int z, StructureBoundingBox sbb) {
		// are the coordinates in our bounding box?
        int dx = getXWithOffset(x, z);
        int dy = y;
        int dz = getZWithOffset(x, z);
        if(sbb.isVecInside(dx, dy, dz)) {
        	// find surface above the listed coords
        	for (dy = y; dy < y + 32; dy++) {
        		if (world.isAirBlock( dx, dy, dz)) {
        			//System.out.println("Found surface for generator.  It's " + dy);
        			
        			break;
        		}
        	}
        	
        	generator.generate(world, rand, dx, dy, dz);
        }
	}

	protected void makeTreasureCrate(World world, Random rand, StructureBoundingBox sbb) {
		// treasure!
		int mid = this.size / 2;
		this.fillWithBlocks(world, sbb, mid - 2, 0, mid - 2, mid + 1, 3, mid + 1, Blocks.obsidian, Blocks.obsidian, false);
		this.fillWithAir(world, sbb, mid - 1, 1, mid - 1, mid + 0, 2, mid + 0);
		this.placeTreasureAtCurrentPosition(world, rand, mid, 1, mid, TFTreasure.troll_garden, false, sbb);
	}

}
