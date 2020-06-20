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
		if (shouldStream(mid, left, down, right, up)) {
			return Registry.BIOME.getId(TFBiomes.stream.get());
		} else {
			return -1;
		}
	}

	//	@Override
//	public int[] getInts(int x, int z, int width, int depth) {
//
//		int nx = x - 1;
//		int nz = z - 1;
//		int nwidth = width + 2;
//		int ndepth = depth + 2;
//		int input[] = parent.getInts(nx, nz, nwidth, ndepth);
//		int output[] = IntCache.getIntCache(width * depth);
//
//		int stream = Biome.getIdForBiome(TFBiomes.stream);
//
//		for (int dz = 0; dz < depth; dz++) {
//			for (int dx = 0; dx < width; dx++) {
//
//				int left  = input[dx + 0 + (dz + 1) * nwidth];
//				int right = input[dx + 2 + (dz + 1) * nwidth];
//				int down  = input[dx + 1 + (dz + 0) * nwidth];
//				int up    = input[dx + 1 + (dz + 2) * nwidth];
//				int mid   = input[dx + 1 + (dz + 1) * nwidth];
////                if(mid == 0 || left == 0 || right == 0 || down == 0 || up == 0)
////                {
////                    output[dx + dz * width] = Biome.getIdForBiome(BiomeLibrary.stream);
////                    continue;
////                }
//				if (shouldStream(mid, left, down, right, up)) {
//					output[dx + dz * width] = stream;
//				} else {
//					output[dx + dz * width] = -1;
//				}
//			}
//		}
//
//		return output;
//	}

	boolean shouldStream(int mid, int left, int down, int right, int up) {
		if (shouldStream(mid, left)) {
			return true;
		} else if (shouldStream(mid, right)) {
			return true;
		} else if (shouldStream(mid, down)) {
			return true;
		} else if (shouldStream(mid, up)) {
			return true;
		} else {
			return false;
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

		// glacier and snow have no border
		if (biome1 == TFBiomes.glacier.get() && biome2 == TFBiomes.snowy_forest.get()) {
			return false;
		}
		if (biome1 == TFBiomes.snowy_forest.get() && biome2 == TFBiomes.glacier.get()) {
			return false;
		}
		// mushrooms
		if (biome1 == TFBiomes.deepMushrooms.get() && biome2 == TFBiomes.mushrooms.get()) {
			return false;
		}
		if (biome1 == TFBiomes.mushrooms.get() && biome2 == TFBiomes.deepMushrooms.get()) {
			return false;
		}
		// fire swamp
		if (biome1 == TFBiomes.tfSwamp.get() && biome2 == TFBiomes.fireSwamp.get()) {
			return false;
		}
		if (biome1 == TFBiomes.fireSwamp.get() && biome2 == TFBiomes.tfSwamp.get()) {
			return false;
		}
		// highlands
		if (biome1 == TFBiomes.highlands.get() && biome2 == TFBiomes.highlandsCenter.get()) {
			return false;
		}
		if (biome1 == TFBiomes.highlandsCenter.get() && biome2 == TFBiomes.highlands.get()) {
			return false;
		}
		// dark forest
		if (biome1 == TFBiomes.darkForest.get() && biome2 == TFBiomes.darkForestCenter.get()) {
			return false;
		}
		if (biome1 == TFBiomes.darkForestCenter.get() && biome2 == TFBiomes.darkForest.get()) {
			return false;
		}
		// no lake border
		if (biome1 == TFBiomes.tfLake.get() || biome2 == TFBiomes.tfLake.get()) {
			return false;
		}
		// clearing
		if (biome1 == TFBiomes.clearing.get() || biome2 == TFBiomes.oakSavanna.get()) {
			return false;
		}
		if (biome1 == TFBiomes.oakSavanna.get() || biome2 == TFBiomes.clearing.get()) {
			return false;
		}
		// thorns need no stream
		if (biome1 == TFBiomes.thornlands.get() || biome2 == TFBiomes.thornlands.get()) {
			return false;
		}

		return true;
	}
}
