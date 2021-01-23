package twilightforest.world.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;
import twilightforest.biomes.TFBiomes;
import twilightforest.world.TFBiomeProvider;

public enum GenLayerTFStream implements ICastleTransformer {

	INSTANCE;

	private Registry<Biome> registry;

	GenLayerTFStream() { }

	public GenLayerTFStream setup(Registry<Biome> registry) {
		this.registry = registry;
		return this;
	}
	
	@Override
	public int apply(INoiseRandom iNoiseRandom, int up, int left, int down, int right, int mid) {
		if (shouldStream(mid, left) || shouldStream(mid, right) || shouldStream(mid, down) || shouldStream(mid, up)) {
			return TFBiomeProvider.getBiomeId(TFBiomes.stream, registry);
		} else {
			return mid;
		}
	}

	
	boolean shouldStream(int biome1, int biome2) {
		if (biome1 == biome2) {
			return false;
		}

		if (biome1 == -biome2) {
			return false;
		}
		
		final int tfLake = TFBiomeProvider.getBiomeId(TFBiomes.tfLake, registry);
		final int thornlands = TFBiomeProvider.getBiomeId(TFBiomes.thornlands, registry);
		
		return !((biome1 == tfLake || biome2 == tfLake)
				|| (biome1 == thornlands || biome2 == thornlands)
				|| testEitherBiome(biome1, biome2, TFBiomeProvider.getBiomeId(TFBiomes.snowy_forest, registry), TFBiomeProvider.getBiomeId(TFBiomes.glacier, registry))
				|| testEitherBiome(biome1, biome2, TFBiomeProvider.getBiomeId(TFBiomes.mushrooms, registry), TFBiomeProvider.getBiomeId(TFBiomes.deepMushrooms, registry))
				|| testEitherBiome(biome1, biome2, TFBiomeProvider.getBiomeId(TFBiomes.tfSwamp, registry), TFBiomeProvider.getBiomeId(TFBiomes.fireSwamp, registry))
				|| testEitherBiome(biome1, biome2, TFBiomeProvider.getBiomeId(TFBiomes.darkForest, registry), TFBiomeProvider.getBiomeId(TFBiomes.darkForestCenter, registry))
				|| testEitherBiome(biome1, biome2, TFBiomeProvider.getBiomeId(TFBiomes.highlands, registry), TFBiomeProvider.getBiomeId(TFBiomes.finalPlateau, registry)));
	}

	private boolean testEitherBiome(int test1, int test2, int predicate1, int predicate2) {
		return (test1 == predicate1 && test2 == predicate2) || (test2 == predicate1 && test1 == predicate2);
	}
}
