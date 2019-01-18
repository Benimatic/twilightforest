package twilightforest.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.world.layer.GenLayerTF;


public class TFWorldChunkManager extends WorldChunkManager
{
    private GenLayer unzoomedBiomes;

    /** A GenLayer containing the indices into BiomeGenBase.biomeList[] */
    private GenLayer zoomedBiomes;

    /** The BiomeCache object for this world. */
    private BiomeCache myBiomeCache;

    /** A list of biomes that the player can spawn in. */
    private List<BiomeGenBase> myBiomesToSpawnIn;

    protected TFWorldChunkManager()
    {
        myBiomeCache = new BiomeCache(this);
        myBiomesToSpawnIn = new ArrayList<BiomeGenBase>();
        myBiomesToSpawnIn.add(TFBiomeBase.twilightForest);
        myBiomesToSpawnIn.add(TFBiomeBase.twilightForest2);
        myBiomesToSpawnIn.add(TFBiomeBase.clearing);
        myBiomesToSpawnIn.add(TFBiomeBase.tfSwamp);
        myBiomesToSpawnIn.add(TFBiomeBase.mushrooms);
    }

    public TFWorldChunkManager(long par1, WorldType par3WorldType)
    {
        this();
        GenLayer agenlayer[];
        // new world gen!
        if (TwilightForestMod.oldMapGen)
        {
        	agenlayer = GenLayerTF.makeTheWorldOldMapGen(par1);
        }
        else
        {
        	agenlayer = GenLayerTF.makeTheWorld(par1);
        }
        unzoomedBiomes = agenlayer[0];
        zoomedBiomes = agenlayer[1];
    }

    public TFWorldChunkManager(World par1World)
    {
        this(par1World.getSeed(), par1World.getWorldInfo().getTerrainType());
    }

    /**
     * Gets the list of valid biomes for the player to spawn in.
     */
    @SuppressWarnings("rawtypes")
	public List getBiomesToSpawnIn()
    {
        return myBiomesToSpawnIn;
    }

    /**
     * Returns the BiomeGenBase related to the x, z position on the world.
     */
    public BiomeGenBase getBiomeGenAt(int par1, int par2)
    {
    	BiomeGenBase biome = myBiomeCache.getBiomeGenAt(par1, par2);
    	if (biome == null)
    	{
    		//FMLLog.warning("[TwilightForest] Suppressing bad biome data in getBiomeGenAt, %s at %d, %d", biome, par1, par2);
    		
    		//throw new IllegalArgumentException();
    		return TFBiomeBase.twilightForest;
    	}
    	else
    	{
            return biome;
    	}
    }

    /**
     * Returns a list of rainfall values for the specified blocks. Args: listToReuse, x, z, width, length.
     */
    public float[] getRainfall(float par1ArrayOfFloat[], int par2, int par3, int par4, int par5)
    {
        IntCache.resetIntCache();

        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
        {
            par1ArrayOfFloat = new float[par4 * par5];
        }

        int ai[] = zoomedBiomes.getInts(par2, par3, par4, par5);

        for (int i = 0; i < par4 * par5; i++)
        {
        	// this keeps NPEing, I wonder why
        	if (ai[i] >= 0 && BiomeGenBase.getBiome(ai[i]) != null) {
        		float f = (float)BiomeGenBase.getBiome(ai[i]).getIntRainfall() / 65536F;

        		if (f > 1.0F)
        		{
        			f = 1.0F;
        		}

        		par1ArrayOfFloat[i] = f;
        	}
        	else
        	{
        		// nothing
        	}
        }

        return par1ArrayOfFloat;
    }

    /**
     * Return an adjusted version of a given temperature based on the y height
     */
    public float getTemperatureAtHeight(float par1, int par2)
    {
        return par1;
    }

    /**
     * Returns an array of biomes for the location input.
     */
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase par1ArrayOfBiomeGenBase[], int x, int z, int length, int width)
    {
        IntCache.resetIntCache();

        if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < length * width)
        {
            par1ArrayOfBiomeGenBase = new BiomeGenBase[length * width];
        }

        int arrayOfInts[] = unzoomedBiomes.getInts(x, z, length, width);

        for (int i = 0; i < length * width; i++)
        {
        	// biome validity check
        	if (arrayOfInts[i] >= 0)
        	{
                par1ArrayOfBiomeGenBase[i] = BiomeGenBase.getBiome(arrayOfInts[i]);
        	}
        	else
        	{
        		//System.err.println("Got bad biome data : " + ai[i]);
        		par1ArrayOfBiomeGenBase[i] = TFBiomeBase.twilightForest;
        	}
        }

        return par1ArrayOfBiomeGenBase;
    }

    /**
     * Returns biomes to use for the blocks and loads the other data like temperature and humidity onto the
     * WorldChunkManager Args: oldBiomeList, x, z, width, depth
     */
    public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase par1ArrayOfBiomeGenBase[], int par2, int par3, int par4, int par5)
    {
        return getBiomeGenAt(par1ArrayOfBiomeGenBase, par2, par3, par4, par5, true);
    }

    /**
     * Return a list of biomes for the specified blocks. Args: listToReuse, x, y, width, length, cacheFlag (if false,
     * don't check biomeCache to avoid infinite loop in BiomeCacheBlock)
     */
    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase par1ArrayOfBiomeGenBase[], int x, int y, int width, int length, boolean cacheFlag)
    {
        IntCache.resetIntCache();

        if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < width * length)
        {
            par1ArrayOfBiomeGenBase = new BiomeGenBase[width * length];
        }

        if (cacheFlag && width == 16 && length == 16 && (x & 0xf) == 0 && (y & 0xf) == 0)
        {
            BiomeGenBase abiomegenbase[] = myBiomeCache.getCachedBiomes(x, y);
            System.arraycopy(abiomegenbase, 0, par1ArrayOfBiomeGenBase, 0, width * length);
            return par1ArrayOfBiomeGenBase;
        }

        int ai[] = zoomedBiomes.getInts(x, y, width, length);

        for (int i = 0; i < width * length; i++)
        {
        	// biome validity check
        	if (ai[i] >= 0)
        	{
                par1ArrayOfBiomeGenBase[i] = BiomeGenBase.getBiome(ai[i]);
        	}
        	else
        	{
        		//System.err.println("Got bad biome data : " + ai[i]);
        		par1ArrayOfBiomeGenBase[i] = TFBiomeBase.twilightForest;
        	}
        }

        return par1ArrayOfBiomeGenBase;
    }

    /**
     * checks given Chunk's Biomes against List of allowed ones
     */
    @SuppressWarnings("rawtypes")
	public boolean areBiomesViable(int par1, int par2, int par3, List par4List)
    {
        int i = par1 - par3 >> 2;
        int j = par2 - par3 >> 2;
        int k = par1 + par3 >> 2;
        int l = par2 + par3 >> 2;
        int i1 = (k - i) + 1;
        int j1 = (l - j) + 1;
        int ai[] = unzoomedBiomes.getInts(i, j, i1, j1);

        for (int k1 = 0; k1 < i1 * j1; k1++)
        {
            BiomeGenBase biomegenbase = BiomeGenBase.getBiome(ai[k1]);

            if (!par4List.contains(biomegenbase))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Finds a valid position within a range, that is once of the listed biomes.
     */
    @SuppressWarnings("rawtypes")
	public ChunkPosition findBiomePosition(int par1, int par2, int par3, List par4List, Random par5Random)
    {
        int i = par1 - par3 >> 2;
        int j = par2 - par3 >> 2;
        int k = par1 + par3 >> 2;
        int l = par2 + par3 >> 2;
        int i1 = (k - i) + 1;
        int j1 = (l - j) + 1;
        int ai[] = unzoomedBiomes.getInts(i, j, i1, j1);
        ChunkPosition chunkposition = null;
        int k1 = 0;

        for (int l1 = 0; l1 < ai.length; l1++)
        {
            int i2 = i + l1 % i1 << 2;
            int j2 = j + l1 / i1 << 2;
            BiomeGenBase biomegenbase = BiomeGenBase.getBiome(ai[l1]);

            if (par4List.contains(biomegenbase) && (chunkposition == null || par5Random.nextInt(k1 + 1) == 0))
            {
                chunkposition = new ChunkPosition(i2, 0, j2);
                k1++;
            }
        }

        return chunkposition;
    }

    /**
     * Calls the WorldChunkManager's biomeCache.cleanupCache()
     */
    public void cleanupCache()
    {
        myBiomeCache.cleanupCache();
    }
    
    /**
     * Returns feature ID at the specified location
     */
    public int getFeatureID(int mapX, int mapZ, World world)
    {
		return getFeatureAt(mapX, mapZ, world).featureID;
	}

    /**
     * Returns feature at the specified location
     */
    public TFFeature getFeatureAt(int mapX, int mapZ, World world)
    {
		return TFFeature.generateFeatureFor1Point7(mapX >> 4, mapZ >> 4, world);
    }

	/**
     * Checks if the coordinates are in a feature chunk.
	 * @param world 
     */
    public boolean isInFeatureChunk(World world, int mapX, int mapZ) {
    	// legacy support
		if (TwilightForestMod.oldMapGen)
		{
			return isInFeatureChunkOld(world, mapX, mapZ);
		}
		
		int chunkX = mapX >> 4;
		int chunkZ = mapZ >> 4;
		ChunkCoordinates cc = TFFeature.getNearestCenterXYZ(chunkX, chunkZ, world);
		
		return chunkX == (cc.posX >> 4) && chunkZ == (cc.posZ >> 4);
    	
//    	logic moved to TFFeature.getNearestCenterXYZ :
		
//    	int chunkX = (mapX + 128) >> 4; 
//    	int chunkZ = (mapZ + 128) >> 4;
//    	
//    	// generate random number for the whole biome area
//    	int regionX = chunkX >> 4;
//    	int regionZ = chunkZ >> 4;
//    	
//	    long seed = (regionX * 3129871L) ^ (long)regionZ * 116129781L;
//	    seed = seed * seed * 42317861L + seed * 7L;
//	    
//	    int num0 = (int) (seed >> 12 & 3L);
//	    int num1 = (int) (seed >> 15 & 3L);
//	    int num2 = (int) (seed >> 18 & 3L);
//	    int num3 = (int) (seed >> 21 & 3L);
//
//	    // slightly randomize center of biome (+/- 3)
//	    int centerX = 8 + num0 - num1;
//	    int centerZ = 8 + num2 - num3;
//    	
//    	return Math.abs(chunkX % 16) == centerX && Math.abs(chunkZ % 16) == centerZ;

    }

	/**
     * Checks if the coordinates are in a feature chunk.
	 * @param world 
     */
    public boolean isInFeatureChunkOld(World world, int mapX, int mapZ) {
    	int chunkX = mapX >> 4; 
    	int chunkZ = mapZ >> 4;

    	return chunkX % 16 == 0 && chunkZ % 16 == 0;
    }

}
