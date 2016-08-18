package twilightforest.biomes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.world.TFGenCanopyMushroom;
import twilightforest.world.TFGenCanopyTree;
import twilightforest.world.TFGenFallenHollowLog;
import twilightforest.world.TFGenFallenSmallLog;
import twilightforest.world.TFGenFoundation;
import twilightforest.world.TFGenGroveRuins;
import twilightforest.world.TFGenTorchBerries;
import twilightforest.world.TFGenHollowStump;
import twilightforest.world.TFGenHollowTree;
import twilightforest.world.TFGenMangroveTree;
import twilightforest.world.TFGenMonolith;
import twilightforest.world.TFGenMyceliumBlob;
import twilightforest.world.TFGenOutsideStalagmite;
import twilightforest.world.TFGenPlantRoots;
import twilightforest.world.TFGenStoneCircle;
import twilightforest.world.TFGenWell;
import twilightforest.world.TFGenWitchHut;
import twilightforest.world.TFGenWoodRoots;
import twilightforest.world.TFGenerator;
import twilightforest.world.TFTreeGenerator;



public class TFBiomeDecorator extends BiomeDecorator {
	
	TFGenCanopyTree canopyTreeGen;
	TFTreeGenerator alternateCanopyGen;
	TFGenHollowTree hollowTreeGen;
	TFGenMyceliumBlob myceliumBlobGen;
	WorldGenLakes extraLakeGen;
	WorldGenLakes extraLavaPoolGen;
	TFGenMangroveTree mangroveTreeGen;
	
	TFGenPlantRoots plantRootGen;
	TFGenWoodRoots woodRootGen;
	WorldGenLiquids caveWaterGen;
	TFGenTorchBerries torchBerryGen;
	   
    public float canopyPerChunk;
    public float alternateCanopyChance;
    public int myceliumPerChunk;
    public int mangrovesPerChunk;
    public int lakesPerChunk;
    public float lavaPoolChance;
    
    static final List<RuinEntry> ruinList = new ArrayList<RuinEntry>();
    static {
    	// make list of ruins
    	ruinList.add(new RuinEntry(new TFGenStoneCircle(), 10));
    	ruinList.add(new RuinEntry(new TFGenWell(), 10));
    	ruinList.add(new RuinEntry(new TFGenWitchHut(), 5));
    	ruinList.add(new RuinEntry(new TFGenOutsideStalagmite(), 12));
    	ruinList.add(new RuinEntry(new TFGenFoundation(), 10));
    	ruinList.add(new RuinEntry(new TFGenMonolith(), 10));
    	ruinList.add(new RuinEntry(new TFGenGroveRuins(), 5));
    	ruinList.add(new RuinEntry(new TFGenHollowStump(), 12));
    	ruinList.add(new RuinEntry(new TFGenFallenHollowLog(), 10));
    	ruinList.add(new RuinEntry(new TFGenFallenSmallLog(), 10));
    }
    
    /**
     * WeightedRandomItem for making the minor features
     */
    static class RuinEntry extends WeightedRandom.Item
    {
        public final TFGenerator generator;
        public RuinEntry(TFGenerator generator, int weight)
        {
            super(weight);
            this.generator = generator;
        }
    }


	public TFBiomeDecorator() {
		super();

		canopyTreeGen = new TFGenCanopyTree();
		alternateCanopyGen = new TFGenCanopyMushroom();
		mangroveTreeGen = new TFGenMangroveTree();
		myceliumBlobGen = new TFGenMyceliumBlob(5);
		hollowTreeGen = new TFGenHollowTree();
		extraLakeGen = new WorldGenLakes(Blocks.water);
		extraLavaPoolGen = new WorldGenLakes(Blocks.lava);
		
		plantRootGen = new TFGenPlantRoots();
		woodRootGen = new TFGenWoodRoots();
		caveWaterGen = new WorldGenLiquids(Blocks.flowing_water);
		torchBerryGen = new TFGenTorchBerries();

		canopyPerChunk = TwilightForestMod.canopyCoverage;
		alternateCanopyChance = 0;
		myceliumPerChunk = 0;
		lakesPerChunk = 0;
		lavaPoolChance = 0;
		mangrovesPerChunk = 0;
	}

    /**
     * Decorates the world. Calls code that was formerly (pre-1.8) in ChunkProviderGenerate.populate
     */
	@Override
    public void decorateChunk(World world, Random rand, BiomeGenBase biome, int mapX, int mapZ)
    {

    	// check for features
    	TFFeature nearFeature = TFFeature.getNearestFeature(mapX >> 4, mapZ >> 4, world);

    	if (!nearFeature.areChunkDecorationsEnabled) {
        	// no normal decorations here, these parts supply their own decorations.
            decorateUnderground(world, rand, mapX, mapZ);
            decorateOnlyOres(world, rand, mapX, mapZ);
    	} else {
//    		// hollow trees!
//	    	if (rand.nextInt(24) == 0) {
//		        int rx = mapX + rand.nextInt(16) + 8;
//		        int rz = mapZ + rand.nextInt(16) + 8;
//		        int ry = world.getHeightValue(rx, rz);
//	    		hollowTreeGen.generate(world, rand, rx, ry, rz);
//	    	}
	    	
	    	// regular decorations
    		this.currentWorld = null; // suppress "Already decorating" error.  I'm fairly sure that this mod does not cause this on its own, but cross-mod interactions seem to.
	    	super.decorateChunk(world, rand, biome, mapX, mapZ);
	    	
    	}

    }
    
    /**
     * The method that does the work of actually decorating chunks
     */
    protected void genDecorations(BiomeGenBase biome)
    {
        
    	try {
    	//at this point the error did not occur, but seeing what could be randomGenerator this null, then add a cap and here
    	// random features!
        if(randomGenerator.nextInt(6) == 0)
        {
            int rx = chunk_X + randomGenerator.nextInt(16) + 8;
            int rz = chunk_Z + randomGenerator.nextInt(16) + 8;
            int ry =  currentWorld.getHeightValue(rx, rz);
            TFGenerator rf = randomFeature(randomGenerator);
            //rf.generate(currentWorld, randomGenerator, rx, ry, rz);
            if (rf.generate(currentWorld, randomGenerator, rx, ry, rz))
            {
//            	System.out.println(rf + " success at " + rx + ", " + ry + ", " + rz);
            	//cpw.mods.fml.common.FMLLog.info(rf + " success at " + rx + ", " + ry + ", " + rz);
            }
        }
        } catch (Throwable thrw) {cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, thrw, "TwilightForest: skip random feature, ");}

		// add canopy trees
    	try {
    	int nc = (int)canopyPerChunk + ((randomGenerator.nextFloat() < (canopyPerChunk - (int)canopyPerChunk)) ? 1 : 0);
    	for (int i = 0; i < nc; i++) {
    		try {//this in order not to miss the party generating the trees and miss only a few trees
    		int rx = chunk_X + randomGenerator.nextInt(16) + 8;
    		int rz = chunk_Z + randomGenerator.nextInt(16) + 8;
    		int ry = currentWorld.getHeightValue(rx,  rz);
    		if (this.alternateCanopyChance > 0 && randomGenerator.nextFloat() <= alternateCanopyChance) {
    			alternateCanopyGen.generate(currentWorld, randomGenerator, rx, ry, rz);
    		} else {
    			canopyTreeGen.generate(currentWorld, randomGenerator, rx, ry, rz);
	        }
	        } catch (Throwable thrwe) {cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, thrwe, "TwilightForest: skip canopy tree(s), ");}
    	}
    	} catch (Throwable thrw) {cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, thrw, "TwilightForest: skip canopy trees gen, ");}

    	// mangrove trees
    	for (int i = 0; i < mangrovesPerChunk; i++) {
	        try {//yes, not good variant, fix NPE, if randomGenerator this null, yeah
	        int rx = chunk_X + randomGenerator.nextInt(16) + 8;
	        int rz = chunk_Z + randomGenerator.nextInt(16) + 8;
	        int ry = currentWorld.getHeightValue(rx, rz);
	        mangroveTreeGen.generate(currentWorld, randomGenerator, rx, ry, rz);
	        } catch (Throwable thrw) {cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, thrw, "TwilightForest: skip mangrove tree(s), ");}
    	}
    	// add extra lakes for swamps
    	for (int i = 0; i < lakesPerChunk; i++) {
	        try {
	        int rx = chunk_X + randomGenerator.nextInt(16) + 8;
	        int rz = chunk_Z + randomGenerator.nextInt(16) + 8;
	        int ry = currentWorld.getHeightValue(rx, rz);
	        extraLakeGen.generate(currentWorld, randomGenerator, rx, ry, rz);
	        } catch (Throwable thrw) {cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, thrw, "TwilightForest: skip extra lakes for swamps, ");}
    	}
    	
    	// add extra lava for fire swamps
    	if (randomGenerator.nextFloat() <= lavaPoolChance) {
	        try {
	        int rx = chunk_X + randomGenerator.nextInt(16) + 8;
	        int rz = chunk_Z + randomGenerator.nextInt(16) + 8;
	        int ry = currentWorld.getHeightValue(rx, rz);
	        extraLavaPoolGen.generate(currentWorld, randomGenerator, rx, ry, rz);
	        } catch (Throwable thrw) {cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, thrw, "TwilightForest: skip extra lava for fire swamps, ");}
    	}
    	
    	// mycelium blobs
    	for (int i = 0; i < myceliumPerChunk; i++) {
	        try {
	        int rx = chunk_X + randomGenerator.nextInt(16) + 8;
	        int rz = chunk_Z + randomGenerator.nextInt(16) + 8;
	        int ry = currentWorld.getHeightValue(rx, rz);
	        myceliumBlobGen.generate(currentWorld, randomGenerator, rx, ry, rz);
	        } catch (Throwable thrw) {cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, thrw, "TwilightForest: skip mycelium blobs, ");}
    	}
    	
        super.genDecorations(biome);
        
        decorateUnderground(currentWorld, randomGenerator, chunk_X, chunk_Z);
        

    }

    /**
     * Generate the Twilight Forest underground decorations
     */
	protected void decorateUnderground(World world, Random rand, int mapX, int mapZ) {
		// generate roots
		for (int i = 0; i < 12; ++i)
		{
		    try {
		    int rx = mapX + rand.nextInt(16) + 8;
		    byte ry = 64;
		    int rz = mapZ + rand.nextInt(16) + 8;
		    plantRootGen.generate(world, rand, rx, ry, rz);
		    } catch (Throwable thrw) {cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, thrw, "TwilightForest: skip generate roots, ");}
		}

		// generate roots
		for (int i = 0; i < 20; ++i)
		{
		    try {
		    int rx = mapX + rand.nextInt(16) + 8;
		    int ry = rand.nextInt(64);
		    int rz = mapZ + rand.nextInt(16) + 8;
		    woodRootGen.generate(world, rand, rx, ry, rz);
		    } catch (Throwable thrw) {cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, thrw, "TwilightForest: skip generate roots 2, ");}
		}
		
		// extra underground water sources
		if (this.generateLakes) {
			for (int i = 0; i < 50; ++i)
			{
				try {
				int rx = mapX + rand.nextInt(16) + 8;
				int ry = rand.nextInt(24) + 4;
				int rz = mapZ + rand.nextInt(16) + 8;
				caveWaterGen.generate(world, rand, rx, ry, rz);
				} catch (Throwable thrw) {cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, thrw, "TwilightForest: skip extra underground water sources, ");}
			}
		}
		
        // torch berries are almost guaranteed to spawn so we don't need many
        for (int i = 0; i < 3; ++i)
        {
            try {
            int rx = mapX + rand.nextInt(16) + 8;
            int ry = 64;
            int rz = mapZ + rand.nextInt(16) + 8;
            torchBerryGen.generate(world, rand, rx, ry, rz);
            } catch (Throwable thrw) {cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, thrw, "TwilightForest: skip torch berries, ");}
        }
	}
	
	/**
	 * Generates ores only
	 */
	public void decorateOnlyOres(World world, Random rand, int mapX, int mapZ) {
        this.currentWorld = world;
        this.randomGenerator = rand;
        this.chunk_X = mapX;
        this.chunk_Z = mapZ;
        this.generateOres();
        this.currentWorld = null;
        this.randomGenerator = null;
	}

    /**
     * Gets a random feature suitible to the current biome.
     */
    public TFGenerator randomFeature(Random rand)
    {
    	return ((RuinEntry)WeightedRandom.getRandomItem(rand, ruinList)).generator;
    }
    
    public void setTreesPerChunk(int treesPerChunk) {
    	this.treesPerChunk = treesPerChunk;
    }
    
    public void setBigMushroomsPerChunk(int bigMushroomsPerChunk) {
    	this.bigMushroomsPerChunk = bigMushroomsPerChunk;
    }
    
    public void setClayPerChunk(int clayPerChunk) {
    	this.clayPerChunk = clayPerChunk;
    }
    
    public void setDeadBushPerChunk(int deadBushPerChunk) {
    	this.deadBushPerChunk = deadBushPerChunk;
    }
    
    public void setMushroomsPerChunk(int mushroomsPerChunk) {
    	this.mushroomsPerChunk = mushroomsPerChunk;
    }
    
    public void setFlowersPerChunk(int flowersPerChunk) {
    	this.flowersPerChunk = flowersPerChunk;
    }
    
    public void setReedsPerChunk(int reedsPerChunk) {
    	this.reedsPerChunk = reedsPerChunk;
    }
    
    public void setWaterlilyPerChunk(int waterlilyPerChunk) {
    	this.waterlilyPerChunk = waterlilyPerChunk;
    }
    
    public void setGrassPerChunk(int grassPerChunk) {
    	this.grassPerChunk = grassPerChunk;
    }
    
    
}
