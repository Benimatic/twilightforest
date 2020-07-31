package twilightforest.world.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;
import twilightforest.biomes.TFBiomes;

public enum GenLayerTFCompanionBiomes implements ICastleTransformer {
	INSTANCE;

	GenLayerTFCompanionBiomes() { }


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
