package twilightforest.world.components.layer;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.world.components.layer.vanillalegacy.Area;
import twilightforest.world.components.layer.vanillalegacy.context.BigContext;
import twilightforest.world.components.layer.vanillalegacy.traits.AreaTransformer1;

public enum GenLayerTFMedian implements AreaTransformer1 {
	INSTANCE;

	GenLayerTFMedian() {}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public ResourceKey<Biome> applyPixel(BigContext<?> context, Area layer, int x, int z) {
		ResourceKey[] biomes = new ResourceKey[9];

		for (int pos = 0; pos < 9; pos++) {
			biomes[pos] = layer.getBiome(x + (pos % 3), z + (pos / 3));
		}

		int biomeRecordIndex = 0;
		int biomeRecordCount = 0;

		int iterationQuantity;
		for (int index = 0; index < 9; index++) {
			iterationQuantity = 0;

			for (int test = 0; test < 9; test++) {
				if (biomes[index] == biomes[test]) {
					iterationQuantity++;

					if (iterationQuantity > 4) { // 5 out of 9 is a Home Run! Bye!
						return biomes[index];
					}
				}
			}

			// If there are two biomes with same dominating quantity, then randomly pick unless it is the central biome.
			if (biomeRecordCount == iterationQuantity && (index == 5 || (biomeRecordIndex != 5 && context.nextRandom(2) == 0))) {
				biomeRecordIndex = index;
			}

			if (biomeRecordCount > iterationQuantity) {
				biomeRecordIndex = index;
				biomeRecordCount = iterationQuantity;
			}
		}

		return biomes[biomeRecordIndex]; // default center biome
	}

	@Override
	public int getParentX(int x) {
		return x;
	}

	@Override
	public int getParentY(int z) {
		return z;
	}
}
