package twilightforest.biomes;

public class TFBiomeStream extends TFBiomeBase {

	public TFBiomeStream(BiomeProperties props) {
		super(props);
		
//		this.rootHeight = -0.75F;
//		this.heightVariation = -0.10F;

        getTFBiomeDecorator().setWaterlilyPerChunk(2);
        
        spawnableCreatureList.clear();

	}

}
