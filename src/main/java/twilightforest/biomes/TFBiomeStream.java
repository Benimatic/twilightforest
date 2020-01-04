package twilightforest.biomes;

public class TFBiomeStream extends TFBiomeBase {

	public TFBiomeStream(Builder props) {
		super(props);
		getTFBiomeDecorator().setWaterlilyPerChunk(2);
		//TODO: Find out how to clear
		spawnableCreatureList.clear();
	}
}
