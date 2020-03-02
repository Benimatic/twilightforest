package twilightforest.world.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;
import twilightforest.biomes.TFBiomes;

public enum GenLayerTFCompanionBiomes implements ICastleTransformer {

	INSTANCE;

//	public GenLayerTFCompanionBiomes(long l, GenLayer genlayer) {
//		super(l);
//		parent = genlayer;
//	}

	GenLayerTFCompanionBiomes() { }

	/**
	 * If we are next to one of the 4 "key" biomes, we randomly turn into a companion biome for that center biome
	 */
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
//		int fireSwamp        = Biome.getIdForBiome(TFBiomes.fireSwamp);
//		int swamp            = Biome.getIdForBiome(TFBiomes.tfSwamp);
//		int glacier          = Biome.getIdForBiome(TFBiomes.glacier);
//		int snowyForest      = Biome.getIdForBiome(TFBiomes.snowy_forest);
//		int darkForestCenter = Biome.getIdForBiome(TFBiomes.darkForestCenter);
//		int darkForest       = Biome.getIdForBiome(TFBiomes.darkForest);
//		int highlandsCenter  = Biome.getIdForBiome(TFBiomes.highlandsCenter);
//		int highlands        = Biome.getIdForBiome(TFBiomes.highlands);
//
//		for (int dz = 0; dz < depth; dz++) {
//			for (int dx = 0; dx < width; dx++) {
//
//				int right  = input[dx + 0 + (dz + 1) * nwidth];
//				int left   = input[dx + 2 + (dz + 1) * nwidth];
//				int up     = input[dx + 1 + (dz + 0) * nwidth];
//				int down   = input[dx + 1 + (dz + 2) * nwidth];
//				int center = input[dx + 1 + (dz + 1) * nwidth];
//
//				if (isKey(fireSwamp, center, right, left, up, down)) {
//					output[dx + dz * width] = swamp;
//				} else if (isKey(glacier, center, right, left, up, down)) {
//					output[dx + dz * width] = snowyForest;
//				} else if (isKey(darkForestCenter, center, right, left, up, down)) {
//					output[dx + dz * width] = darkForest;
//				} else if (isKey(highlandsCenter, center, right, left, up, down)) {
//					output[dx + dz * width] = highlands;
//				} else {
//					output[dx + dz * width] = center;
//				}
//			}
//		}
//
//		return output;
//	}

	@Override
	public int apply(INoiseRandom noise, int up, int left, int down, int right, int center) {
		int fireSwamp        = Registry.BIOME.getId(TFBiomes.fireSwamp.get());
		int swamp            = Registry.BIOME.getId(TFBiomes.tfSwamp.get());
		int glacier          = Registry.BIOME.getId(TFBiomes.glacier.get());
		int snowyForest      = Registry.BIOME.getId(TFBiomes.snowy_forest.get());
		int darkForestCenter = Registry.BIOME.getId(TFBiomes.darkForestCenter.get());
		int darkForest       = Registry.BIOME.getId(TFBiomes.darkForest.get());
		int highlandsCenter  = Registry.BIOME.getId(TFBiomes.highlandsCenter.get());
		int highlands        = Registry.BIOME.getId(TFBiomes.highlands.get());

		if (isKey(fireSwamp, center, right, left, up, down)) {
			return swamp;
		} else if (isKey(glacier, center, right, left, up, down)) {
			return snowyForest;
		} else if (isKey(darkForestCenter, center, right, left, up, down)) {
			return darkForest;
		} else if (isKey(highlandsCenter, center, right, left, up, down)) {
			return highlands;
		} else {
			return center;
		}
	}

	/**
	 * Returns true if any of the surrounding biomes is the specified biome
	 */
	boolean isKey(int biome, int center, int right, int left, int up, int down) {
		return center != biome && (right == biome || left == biome || up == biome || down == biome);
	}
}
