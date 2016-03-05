package twilightforest.biomes;

public class TFBiomeStream extends TFBiomeBase {

	public TFBiomeStream(int i) {
		super(i);
		
//		this.rootHeight = -0.75F;
//		this.heightVariation = -0.10F;

		this.temperature = 0.5F;
        this.rainfall = 1F;
        
        getTFBiomeDecorator().setWaterlilyPerChunk(2);
        
        spawnableCreatureList.clear();

	}

}
