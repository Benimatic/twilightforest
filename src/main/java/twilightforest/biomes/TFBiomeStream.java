package twilightforest.biomes;

import net.minecraft.entity.EntityClassification;

public class TFBiomeStream extends TFBiomeBase {

	public TFBiomeStream(Builder props) {
		super(props);

		getSpawns(EntityClassification.CREATURE).clear();

		TFBiomeDecorator.addWoodRoots(this);
		TFBiomeDecorator.addOres(this);
		TFBiomeDecorator.addClayDisks(this, 1);
		TFBiomeDecorator.addLakes(this);
		TFBiomeDecorator.addSprings(this);
		TFBiomeDecorator.addPlantRoots(this);
		TFBiomeDecorator.addTorchberries(this);
		TFBiomeDecorator.addMushrooms(this);
		TFBiomeDecorator.addMultipleTrees(this, TFBiomeDecorator.NORMAL_TREES_CONFIG, 10);
		TFBiomeDecorator.addReeds(this, 1);
		TFBiomeDecorator.addWaterlilies(this, 2);
	}
}
