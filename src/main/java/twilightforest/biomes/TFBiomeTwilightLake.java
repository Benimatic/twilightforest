package twilightforest.biomes;

import net.minecraft.entity.passive.EntitySquid;

public class TFBiomeTwilightLake extends TFBiomeBase {

	@SuppressWarnings("unchecked")
	public TFBiomeTwilightLake(int i) {
		super(i);
		
		
//		this.rootHeight = -1.9F;
//		this.heightVariation = 0.5F;

		this.temperature = 0.66F;
        this.rainfall = 1F;
        
        //spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquid.class, 10, 4, 4));

	}

}
