package twilightforest.world.components.layer;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.init.TFBiomes;
import twilightforest.world.components.layer.vanillalegacy.context.Context;
import twilightforest.world.components.layer.vanillalegacy.traits.CastleTransformer;

public enum GenLayerTFCompanionBiomes implements CastleTransformer {
	INSTANCE;

	@Override
	public ResourceKey<Biome> apply(Context noise, ResourceKey<Biome> up, ResourceKey<Biome> left, ResourceKey<Biome> down, ResourceKey<Biome> right, ResourceKey<Biome> center) {
		ResourceKey<Biome> fireSwamp        = TFBiomes.FIRE_SWAMP;
		ResourceKey<Biome> swamp            = TFBiomes.SWAMP;
		ResourceKey<Biome> glacier          = TFBiomes.GLACIER;
		ResourceKey<Biome> snowyForest      = TFBiomes.SNOWY_FOREST;
		ResourceKey<Biome> darkForestCenter = TFBiomes.DARK_FOREST_CENTER;
		ResourceKey<Biome> darkForest       = TFBiomes.DARK_FOREST;
		ResourceKey<Biome> highlandsCenter  = TFBiomes.FINAL_PLATEAU;
		ResourceKey<Biome> highlands        = TFBiomes.HIGHLANDS;

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
	boolean isKey(ResourceKey<Biome> biome, ResourceKey<Biome> center, ResourceKey<Biome> right, ResourceKey<Biome> left, ResourceKey<Biome> up, ResourceKey<Biome> down) {
		return center != biome && (right == biome || left == biome || up == biome || down == biome);
	}
}
