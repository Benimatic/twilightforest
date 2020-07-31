package twilightforest.world.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;
import twilightforest.biomes.TFBiomes;

public enum GenLayerTFStream implements ICastleTransformer {

	INSTANCE;

//	public GenLayerTFStream(long l, GenLayer genlayer) {
//		super(l);
//		super.parent = genlayer;
//	}

	GenLayerTFStream() { }

	@Override
	public int apply(INoiseRandom iNoiseRandom, int up, int left, int down, int right, int mid) {
		if (shouldStream(mid, left) || shouldStream(mid, right) || shouldStream(mid, down) || shouldStream(mid, up)) {
			return Registry.BIOME.getId(TFBiomes.stream.get());
		} else {
			return mid;
		}
	}

	boolean shouldStream(int id1, int id2) {
		if (id1 == id2) {
			return false;
		}

		if (id1 == -id2) {
			return false;
		}

		Biome biome1 = Registry.BIOME.getByValue(id1);
		Biome biome2 = Registry.BIOME.getByValue(id2);

		return !((biome1 == TFBiomes.tfLake.get() || biome2 == TFBiomes.tfLake.get())
				|| (biome1 == TFBiomes.thornlands.get() || biome2 == TFBiomes.thornlands.get())
				|| testEitherBiome(biome1, biome2, TFBiomes.snowy_forest.get(), TFBiomes.glacier.get())
				|| testEitherBiome(biome1, biome2, TFBiomes.mushrooms.get(), TFBiomes.deepMushrooms.get())
				|| testEitherBiome(biome1, biome2, TFBiomes.tfSwamp.get(), TFBiomes.fireSwamp.get())
				|| testEitherBiome(biome1, biome2, TFBiomes.darkForest.get(), TFBiomes.darkForestCenter.get())
				|| testEitherBiome(biome1, biome2, TFBiomes.highlands.get(), TFBiomes.highlandsCenter.get()));
	}

	private boolean testEitherBiome(Biome test1, Biome test2, Biome predicate1, Biome predicate2) {
		return (test1 == predicate1 && test2 == predicate2) || (test2 == predicate1 && test1 == predicate2);
	}
}
