package twilightforest.biomes;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import twilightforest.TFFeature;
import twilightforest.block.BlockTFPlant;
import twilightforest.block.TFBlocks;
import twilightforest.world.TFGenDarkCanopyTree;
import twilightforest.world.TFGenTallGrass;
import twilightforest.world.TFTreeGenerator;
import twilightforest.world.TFWorld;

public class TFDarkForestBiomeDecorator extends TFBiomeDecorator {
	
	TFTreeGenerator darkCanopyTreeGen;
	TFGenTallGrass worldGenDeadBush;
	WorldGenTallGrass worldGenForestGrass;
	WorldGenTallGrass worldGenMushgloom;


	public TFDarkForestBiomeDecorator() {
		darkCanopyTreeGen = new TFGenDarkCanopyTree();
		worldGenDeadBush = new TFGenTallGrass(TFBlocks.plant, BlockTFPlant.META_DEADBUSH, 8);
		worldGenForestGrass = new WorldGenTallGrass(TFBlocks.plant, BlockTFPlant.META_FORESTGRASS);
		worldGenMushgloom = new WorldGenTallGrass(TFBlocks.plant, BlockTFPlant.META_MUSHGLOOM);
	}

    /**
     * Decorates the world. Calls code that was formerly (pre-1.8) in ChunkProviderGenerate.populate
     */
	@Override
	public void decorateChunk(World world, Random rand, Biome biome, int mapX, int mapZ) {
		// just decorate with what we need here
	   	TFFeature nearFeature = TFFeature.getNearestFeature(mapX >> 4, mapZ >> 4, world);
    	if (nearFeature.areChunkDecorationsEnabled) {
    		// add dark canopy trees
	    	int nc = (int)canopyPerChunk + ((rand.nextFloat() < (canopyPerChunk - (int)canopyPerChunk)) ? 1 : 0);
    		for (int i = 0; i < nc; i++) {
    			int rx = mapX + rand.nextInt(16) + 8;
    			int rz = mapZ + rand.nextInt(16) + 8;
    			int ry = world.getHeightValue(rx, rz);
    			darkCanopyTreeGen.generate(world, rand, rx, ry, rz);
    		}

    		// regular trees
    		for (int i = 0; i < this.treesPerChunk; ++i)
    		{
    			int rx = mapX + rand.nextInt(16) + 8;
    			int rz = mapZ + rand.nextInt(16) + 8;
    			int ry = getGroundLevel(world, rx, rz);
    			WorldGenerator var5 = biome.genBigTreeChance(rand);
    			var5.setScale(1.0D, 1.0D, 1.0D);
    			var5.generate(world, rand, rx, ry, rz);
    		}

    		// dead bushes
    		for (int i = 0; i < this.deadBushPerChunk; ++i)
    		{
    			int rx = mapX + rand.nextInt(16) + 8;
    			int rz = mapZ + rand.nextInt(16) + 8;
    			int ry = rand.nextInt(128);
				worldGenDeadBush.generate(world, rand, rx, ry, rz);
    		}

    		// forest grass bushes
    		for (int i = 0; i < this.deadBushPerChunk; ++i)
    		{
    			int rx = mapX + rand.nextInt(16) + 8;
    			int rz = mapZ + rand.nextInt(16) + 8;
    			int ry = rand.nextInt(128);
				worldGenForestGrass.generate(world, rand, rx, ry, rz);
    		}

    		// mushrooms
    		for (int i = 0; i < this.mushroomsPerChunk; ++i)
    		{
    			if (rand.nextInt(8) == 0) {
    				int rx = mapX + rand.nextInt(16) + 8;
    				int rz = mapZ + rand.nextInt(16) + 8;
    				int ry = getGroundLevel(world, rx, rz);
    				this.mushroomBrownGen.generate(world, rand, rx, ry, rz);
    			}
    			if (rand.nextInt(16) == 0) {
    				int rx = mapX + rand.nextInt(16) + 8;
    				int rz = mapZ + rand.nextInt(16) + 8;
    				int ry = getGroundLevel(world, rx, rz);
    				this.mushroomRedGen.generate(world, rand, rx, ry, rz);
    			}
    			if (rand.nextInt(24) == 0) {
    				int rx = mapX + rand.nextInt(16) + 8;
    				int rz = mapZ + rand.nextInt(16) + 8;
    				int ry = getGroundLevel(world, rx, rz);
    				// mushglooms
					worldGenMushgloom.generate(world, rand, rx, ry, rz);
    			}
    		}
    		if (rand.nextInt(4) == 0) {
    			int rx = mapX + rand.nextInt(16) + 8;
    			int rz = mapZ + rand.nextInt(16) + 8;
    			int ry = rand.nextInt(128);
    			this.mushroomBrownGen.generate(world, rand, rx, ry, rz);
    		}
    		if (rand.nextInt(8) == 0) {
    			int rx = mapX + rand.nextInt(16) + 8;
    			int rz = mapZ + rand.nextInt(16) + 8;
    			int ry = rand.nextInt(128);
    			this.mushroomRedGen.generate(world, rand, rx, ry, rz);
    		}
    		
    		// pumpkins
    		if (rand.nextInt(32) == 0) {
    			int rx = mapX + rand.nextInt(16) + 8;
    			int rz = mapZ + rand.nextInt(16) + 8;
				int ry = getGroundLevel(world, rx, rz);
    			(new WorldGenPumpkin()).generate(world, rand, rx, ry, rz);
    		}

    	}
    	
    	// do underground decorations
        decorateUnderground(world, rand, mapX, mapZ);
        decorateOnlyOres(world, rand, mapX, mapZ);

	}
	
    
    public int getGroundLevel(World world, int x, int z) {
    	// go from sea level up.  If we get grass, return that, otherwise return the last dirt, stone or gravel we got
    	Chunk chunk = world.getChunkFromBlockCoords(x, z);
    	int lastDirt = TFWorld.SEALEVEL;
    	for (int y = TFWorld.SEALEVEL; y < TFWorld.CHUNKHEIGHT - 1; y++) {
    		Block blockID = chunk.getBlock(x & 15, y, z & 15);
    		// grass = return immediately
    		if (blockID == Blocks.GRASS) {
    			return y + 1;
    		}
    		else if (blockID == Blocks.DIRT || blockID == Blocks.STONE || blockID == Blocks.GRAVEL) {
    			lastDirt = y + 1;
    		}
    	}
    	
    	return lastDirt;
    }

}
