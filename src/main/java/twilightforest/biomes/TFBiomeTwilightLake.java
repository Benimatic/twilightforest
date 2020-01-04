package twilightforest.biomes;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import twilightforest.TFFeature;

public class TFBiomeTwilightLake extends TFBiomeBase {

	public TFBiomeTwilightLake(Builder props) {
		super(props);
		addSpawn(EntityClassification.WATER_CREATURE, new SpawnListEntry(EntityType.SQUID, 10, 4, 4));
	}

	@Override
	protected TFFeature getContainedFeature() {
		return TFFeature.QUEST_ISLAND;
	}
}
