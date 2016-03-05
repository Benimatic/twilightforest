package twilightforest.biomes;

public class TFBiomeDarkForestCenter extends TFBiomeDarkForest {

	public TFBiomeDarkForestCenter(int i) {
		super(i);
	}
	
	
    /**
     * Provides the basic grass color based on the biome temperature and rainfall
     */
    @Override
    public int getBiomeGrassColor(int x, int y, int z)
    {
        double d0 = plantNoise.func_151601_a((double)x * 0.0225D, (double)z * 0.0225D);
        return d0 < -0.2D ? 0x667540 : 0x554114;
    }

    /**
     * Provides the basic foliage color based on the biome temperature and rainfall
     */
    @Override
    public int getBiomeFoliageColor(int x, int y, int z)
    {
        double d0 = plantNoise.func_151601_a((double)x * 0.0225D, (double)z * 0.0225D);
        return d0 < -0.1D ? 0xf9821e : 0xe94e14;
    }
}
