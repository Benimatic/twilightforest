package twilightforest.world.components.layer;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.init.TFBiomes;
import twilightforest.world.components.layer.vanillalegacy.context.Context;

public enum GenLayerTFThornBorder implements IThornsTransformer {
	INSTANCE;

	@Override
	public ResourceKey<Biome> apply(Context noise, ResourceKey<Biome> up, ResourceKey<Biome> left, ResourceKey<Biome> down, ResourceKey<Biome> right, ResourceKey<Biome> center, ResourceKey<Biome> nw, ResourceKey<Biome> sw, ResourceKey<Biome> se, ResourceKey<Biome> ne) {
		ResourceKey<Biome> highlandsCenter = TFBiomes.FINAL_PLATEAU;
		ResourceKey<Biome> thornlands      = TFBiomes.THORNLANDS;

		if (onBorder(highlandsCenter, center, right, left, up, down)) {
			return thornlands;
		} else if (onBorder(highlandsCenter, center, ne, nw, se, sw)) {
			return thornlands;
		} else {
			return center;
		}
	}

	/**
	 * Returns true if the center biome is not the specified biome and any of the surrounding biomes are the specified biomes
	 */
	private boolean onBorder(ResourceKey<Biome> biomeID, ResourceKey<Biome> center, ResourceKey<Biome> right, ResourceKey<Biome> left, ResourceKey<Biome> up, ResourceKey<Biome> down) {
		if (center == biomeID) {
			return false;
		} else if (right == biomeID) {
			return true;
		} else if (left == biomeID) {
			return true;
		} else if (up == biomeID) {
			return true;
		} else return down == biomeID;
	}
}
