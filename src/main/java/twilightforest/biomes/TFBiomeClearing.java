package twilightforest.biomes;

public class TFBiomeClearing extends TFBiomeBase {

	public TFBiomeClearing(Builder props) {
		super(props);

		TFBiomeDecorator.addClayDisks(this, 1);
		TFBiomeDecorator.addWoodRoots(this);
		TFBiomeDecorator.addOres(this);
		TFBiomeDecorator.addPlantRoots(this);
		TFBiomeDecorator.addLakes(this);
		TFBiomeDecorator.addSprings(this);
		TFBiomeDecorator.addTorchberries(this);
		TFBiomeDecorator.addGrass(this, 10);
		TFBiomeDecorator.addFlowers(this, 4);
		TFBiomeDecorator.addMushrooms(this);
	}
}
