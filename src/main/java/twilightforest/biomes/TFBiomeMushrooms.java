package twilightforest.biomes;


public class TFBiomeMushrooms extends TFBiomeBase {

	public TFBiomeMushrooms(int i) {
		super(i);
		
		this.rainfall = 0.8F;
		this.temperature = 0.8F;
		
		getTFBiomeDecorator().setTreesPerChunk(8);
        
        getTFBiomeDecorator().setMushroomsPerChunk(8);
        getTFBiomeDecorator().setBigMushroomsPerChunk(2);
        
        getTFBiomeDecorator().alternateCanopyChance = 0.2F;

	}
 
}
