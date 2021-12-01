package twilightforest.world.components.layer;

import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import twilightforest.world.registration.biomes.BiomeKeys;
import twilightforest.world.components.TFBiomeProvider;

import java.util.List;

/**
 * Applies the twilight forest biomes to the map
 *
 * @author Ben
 */
public enum GenLayerTFBiomes implements AreaTransformer0 {
	INSTANCE;
	private static final int RARE_BIOME_CHANCE = 15;

	protected static final List<ResourceKey<Biome>> commonBiomes = ImmutableList.of(
			BiomeKeys.FOREST,
			BiomeKeys.DENSE_FOREST,
			BiomeKeys.MUSHROOM_FOREST,
			BiomeKeys.OAK_SAVANNAH,
			BiomeKeys.FIREFLY_FOREST
	);
	protected static final List<ResourceKey<Biome>> rareBiomes = ImmutableList.of(
			BiomeKeys.LAKE,
			BiomeKeys.DENSE_MUSHROOM_FOREST,
			BiomeKeys.ENCHANTED_FOREST,
			BiomeKeys.CLEARING,
			BiomeKeys.SPOOKY_FOREST
	);

	private Registry<Biome> registry;

	public GenLayerTFBiomes setup(Registry<Biome> registry) {
		this.registry = registry;
		return this;
	}

	GenLayerTFBiomes() {

	}

	@Override
	public int applyPixel(Context iNoiseRandom, int x, int y) {
		//return 0; //getRandomBiome(iNoiseRandom, commonBiomes));

		if (iNoiseRandom.nextRandom(RARE_BIOME_CHANCE) == 0) {
			// make rare biome
			return getRandomBiome(iNoiseRandom, rareBiomes);
		} else {
			// make common biome
			return getRandomBiome(iNoiseRandom, commonBiomes);
		}
	}

	private int getRandomBiome(Context random, List<ResourceKey<Biome>> biomes) {
		return TFBiomeProvider.getBiomeId(biomes.get(random.nextRandom(biomes.size())), registry);
	}
}
