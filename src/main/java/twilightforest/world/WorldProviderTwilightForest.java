/**
 * 
 */
package twilightforest.world;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;
import twilightforest.TwilightForestMod;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.client.renderer.TFSkyRenderer;
import twilightforest.client.renderer.TFWeatherRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


/**
 * @author Ben
 *
 */
public class WorldProviderTwilightForest extends WorldProviderSurface {


	public final String saveFolder;
	public ChunkProviderTwilightForest chunkProvider;
	private boolean firstsetrnd = false;
	
	public WorldProviderTwilightForest() {
		setDimension(TwilightForestMod.dimensionID);
		saveFolder = "DIM" + TwilightForestMod.dimensionID;
		if (!firstsetrnd) { 
		try{
		long seed = this.getSeed();
		TwilightForestMod.Scatter.setSeed(seed);
		firstsetrnd=true;
		} catch(Exception e){firstsetrnd=false;}
		}
	}
	
	/**
	 * Always return these colors
	 */
	@Override
	public float[] calcSunriseSunsetColors(float celestialAngle, float f1) {
		return null;//super.calcSunriseSunsetColors(celestialAngle, f1);
	}
	
	/**
	 * Fog color
	 */
	@Override
    public Vec3 getFogColor(float f, float f1)
    {
        float bright = MathHelper.cos(0.25f * (float)Math.PI * 2.0F) * 2.0F + 0.5F;
        if(bright < 0.0F)
        {
            bright = 0.0F;
        }
        if(bright > 1.0F)
        {
            bright = 1.0F;
        }
        float red = 0.7529412F;
        float green = 1.0F;
        float blue = 0.8470588F;
        red *= bright * 0.94F + 0.06F;
        green *= bright * 0.94F + 0.06F;
        blue *= bright * 0.91F + 0.09F;
        return Vec3.createVectorHelper(red, green, blue);
    }
	
    /**
     * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
     */
    public float calculateCelestialAngle(long par1, float par3)
    {
    	//return super.calculateCelestialAngle(par1, par3);
        return 0.225f;
    }


	@Override
    public void registerWorldChunkManager()
    {
		this.worldChunkMgr = new TFWorldChunkManager(worldObj);
        this.dimensionId = TwilightForestMod.dimensionID;
    }
   
	
    /**
     * Returns a new chunk provider which generates chunks for this world
     */
    public IChunkProvider createChunkGenerator()
    {
    	// save chunk generator?
    	if (this.chunkProvider == null) {
	    	this.chunkProvider = new ChunkProviderTwilightForest(worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled());
	        return this.chunkProvider;
    	} else {
    		return new ChunkProviderTwilightForest(worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled());
    	}
    }
    
    public ChunkProviderTwilightForest getChunkProvider() {
    	return this.chunkProvider;
    }
    
	/**
	 * This seems to be a function checking whether we have an ocean.
	 */
	@Override
	public boolean isSkyColored() 
	{
		return false;
	}


//
//	@Override
//    public float getCloudHeight()
//    {
//        return 64F;
//    }
	
    public int getAverageGroundLevel()
    {
        return 30;
    }

//	@Override
//    public boolean canCoordinateBeSpawn(int i, int j)
//    {
//        int k = worldObj.getFirstUncoveredBlock(i, j);
//        if(k == 0)
//        {
//            return false;
//        } else
//        {
//            return Blocks.blocksList[k].getMaterial().isSolid();
//        }
//    }
	
	
    /**
     * True if the player can respawn in this dimension (true = overworld, false = nether).
     */
	@Override
    public boolean canRespawnHere()
    {
		// lie about this until the world is initialized
		// otherwise the server will try to generate enough terrain for a spawn point and that's annoying
        return worldObj.getWorldInfo().isInitialized();
    }

	@Override
	public String getSaveFolder() {
		return this.saveFolder;
	}

	@Override
	public String getWelcomeMessage() {
		return "Entering the Twilight Forest";
	}

	@Override
	public String getDepartMessage() {
		return "Leaving the Twilight Forest";
	}

	@Override
	public String getDimensionName() {
		return "Twilight Forest";
	}

	/**
	 * Sleep anytime!
	 */
	@Override
	public boolean isDaytime() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getSkyColor(Entity cameraEntity, float partialTicks) {
		return Vec3.createVectorHelper(43 / 256.0, 46 / 256.0, 99 / 256.0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getStarBrightness(float par1) {
		return 1.0F;
	}


	@Override
	public double getHorizon() {
		return 32.0D;
	}

	@Override
	public BiomeGenBase getBiomeGenForCoords(int x, int z) {
		BiomeGenBase biome = super.getBiomeGenForCoords(x, z);
		if (biome == null)
		{
			biome = TFBiomeBase.twilightForest;
		}
		return biome;
	}
	
	/**
	 * If there is a specific twilight forest seed set, use that.  Otherwise use the world seed.
	 */
	@Override
	public long getSeed() 
	{
		if (TwilightForestMod.twilightForestSeed == null || TwilightForestMod.twilightForestSeed.length() == 0)
		{
			return super.getSeed();
		}
		else
		{
			return TwilightForestMod.twilightForestSeed.hashCode();
		}
	}
	
	/**
	 * We're just going to check here for chunks with the relight flag set and KILL THEM!
	 */
	@Override
    public void updateWeather()
    {
        super.updateWeather();
        
//        for (ChunkCoordIntPair coord : (HashSet<ChunkCoordIntPair>)this.worldObj.activeChunkSet)
//        {
//        	Chunk chunk = this.worldObj.getChunkFromChunkCoords(coord.chunkXPos, coord.chunkZPos);
//        	
//        	if (chunk.queuedLightChecks < 4096)
//        	{
//        		//System.out.println("Stopping light checks!");
//        		chunk.queuedLightChecks = 4096;
//        	}
//        }
    }

	
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer()
	{
		if (super.getSkyRenderer() == null)
		{
			this.setSkyRenderer(new TFSkyRenderer());
		}

		return super.getSkyRenderer();
	}
	
    @SideOnly(Side.CLIENT)
    public IRenderHandler getWeatherRenderer()
    {
    	if (super.getWeatherRenderer() == null)
		{
			this.setWeatherRenderer(new TFWeatherRenderer());
		}

		return super.getWeatherRenderer();
    }
	
    /**
     * the y level at which clouds are rendered.
     */
    //@SideOnly(Side.CLIENT) // need for magic beans, even on server
    public float getCloudHeight()
    {
        return 161.0F;
    }
}
