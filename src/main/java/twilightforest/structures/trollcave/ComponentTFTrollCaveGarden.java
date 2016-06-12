package twilightforest.structures.trollcave;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.structures.StructureTFComponent;
import twilightforest.world.TFGenBigMushgloom;
import twilightforest.world.TFGenMyceliumBlob;

public class ComponentTFTrollCaveGarden extends ComponentTFTrollCaveMain {
	
	TFGenMyceliumBlob myceliumBlobGen = new TFGenMyceliumBlob(5);
	TFGenMyceliumBlob dirtGen = new TFGenMyceliumBlob(Blocks.DIRT, 5);
	WorldGenBigMushroom bigMushroomGen = new WorldGenBigMushroom();
	TFGenBigMushgloom bigMushgloomGen = new TFGenBigMushgloom();

	
	public ComponentTFTrollCaveGarden() { }

	public ComponentTFTrollCaveGarden(int index, int x, int y, int z, int caveSize, int caveHeight, int direction) {
		super(index);
		this.size = caveSize;
		this.height = caveHeight;
		this.setCoordBaseMode(direction);
		this.boundingBox = StructureTFComponent.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, size - 1, height - 1, size - 1, direction);
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void buildComponent(StructureComponent parent, List list, Random rand) {
		// add a cloud
//		ComponentTFTrollCloud cloud = new ComponentTFTrollCloud(1, boundingBox.minX + ((boundingBox.maxX - boundingBox.minX) / 2), rand.nextInt(64) + 160, boundingBox.minZ + ((boundingBox.maxZ - boundingBox.minZ) / 2));
//		list.add(cloud);
//		cloud.buildComponent(this, list, rand);
	}
	
	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
        if (this.isBoundingBoxOutOfHighlands(world, sbb)) {
            return false;
        } else {
    		// clear inside
    		hollowCaveMiddle(world, sbb, rand, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1);
    		Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));

    		// treasure!
    		makeTreasureCrate(world, rand, sbb);

    		// dirt!
    		for (int i = 0; i < 24; i++)
    		{
    			ChunkCoordinates dest = getCoordsInCave(decoRNG);
    			generate(world, dirtGen, decoRNG, dest.posX, 1, dest.posZ, sbb);
    		}
    		
    		// mycelium!
    		for (int i = 0; i < 16; i++)
    		{
    			ChunkCoordinates dest = getCoordsInCave(decoRNG);
    			generate(world, myceliumBlobGen, decoRNG, dest.posX, 1, dest.posZ, sbb);
    		}
    		
    		// uberous!
    		for (int i = 0; i < 16; i++)
    		{
    			ChunkCoordinates dest = getCoordsInCave(decoRNG);
    			generate(world, uberousGen, decoRNG, dest.posX, 1, dest.posZ, sbb);
    			
    			generateAtSurface(world, uberousGen, decoRNG, dest.posX, 60, dest.posZ, sbb);
    		}
    		
    		// mushglooms first
    		for (int i = 0; i < 32; i++)
    		{
    			ChunkCoordinates dest = getCoordsInCave(decoRNG);
    			generate(world, bigMushgloomGen, decoRNG, dest.posX, 1, dest.posZ, sbb);
    		}
    		
    		// mushrooms!
    		for (int i = 0; i < 64; i++)
    		{
    			ChunkCoordinates dest = getCoordsInCave(decoRNG);
    			generate(world, bigMushroomGen, decoRNG, dest.posX, 1, dest.posZ, sbb);
    		}
    		
    		// stone stalactites!
    		for (int i = 0; i < 128; i++)
    		{
    			ChunkCoordinates dest = getCoordsInCave(decoRNG);
    			generateBlockStalactite(world, decoRNG, Blocks.STONE, 0.7F, true, dest.posX, 3, dest.posZ, sbb);
    		}

    		
    		return true;
        }
	}

	/**
	 * Use the generator at the specified coords
	 */
	protected void generate(World world, WorldGenerator generator, Random rand, int x, int y, int z, StructureBoundingBox sbb) {
		// are the coordinates in our bounding box?
        int dx = getXWithOffset(x, z);
        int dy = getYWithOffset(y);
        int dz = getZWithOffset(x, z);
        if(sbb.isVecInside(dx, dy, dz)) {
        	generator.generate(world, rand, dx, dy, dz);
        }
	}
	


}
