package twilightforest.biomes;

public class TFBiomeDarkForestCenter extends TFBiomeDarkForest {

	public TFBiomeDarkForestCenter(BiomeProperties props) {
		super(props);
	}
	
    @Override
    public int getBiomeGrassColor(int x, int y, int z)
    {
        double d0 = plantNoise.func_151601_a((double)x * 0.0225D, (double)z * 0.0225D);
        return d0 < -0.2D ? 0x667540 : 0x554114;
    }

    @Override
    public int getBiomeFoliageColor(int x, int y, int z)
    {
        double d0 = plantNoise.func_151601_a((double)x * 0.0225D, (double)z * 0.0225D);
        return d0 < -0.1D ? 0xf9821e : 0xe94e14;
    }
}
