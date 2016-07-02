package twilightforest.biomes;


public class TFBiomeTwilightForest extends TFBiomeBase {

    public TFBiomeTwilightForest(BiomeProperties props)
    {
        super(props);
    }

    public TFBiomeTwilightForest()
    {
        this(new BiomeProperties("Twilight Forest").setWaterColor(0x005500));
    }

}
