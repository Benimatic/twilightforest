package twilightforest.world.layer;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import twilightforest.biomes.TFBiomes;

import java.util.List;
import java.util.function.Supplier;

/**
 * Applies the twilight forest biomes to the map
 *
 * @author Ben
 */
public class GenLayerTFBiomes implements IAreaTransformer0 {
	private static final int RARE_BIOME_CHANCE = 15;

	protected static final List<Supplier<Biome>> commonBiomes = ImmutableList.of(
			TFBiomes.twilightForest,
			TFBiomes.denseTwilightForest,
			TFBiomes.mushrooms,
			TFBiomes.oakSavanna,
			TFBiomes.fireflyForest
	);
	protected static final List<Supplier<Biome>> rareBiomes = ImmutableList.of(
			TFBiomes.tfLake,
			TFBiomes.deepMushrooms,
			TFBiomes.enchantedForest,
			TFBiomes.clearing,
			TFBiomes.spookyForest
	);

	private static final List<Supplier<Biome>> BIOMES = ImmutableList.of( //TODO: Can we do this more efficiently?
			TFBiomes.clearing,
			TFBiomes.oakSavanna,
			TFBiomes.twilightForest,
			TFBiomes.denseTwilightForest,
			TFBiomes.fireflyForest,
			TFBiomes.mushrooms,
			TFBiomes.deepMushrooms,
			TFBiomes.enchantedForest,

			TFBiomes.fireSwamp,
			TFBiomes.darkForestCenter,
			TFBiomes.glacier,
			TFBiomes.highlandsCenter
	);

	public GenLayerTFBiomes() { }

	@Override
	public int apply(INoiseRandom iNoiseRandom, int x, int y) {
		if (iNoiseRandom.random(RARE_BIOME_CHANCE) == 0) {
			// make rare biome
			return Registry.BIOME.getId(getRandomBiome(iNoiseRandom, rareBiomes));
		} else {
			// make common biome
			return Registry.BIOME.getId(getRandomBiome(iNoiseRandom, commonBiomes));
		}
	}

	private Biome getRandomBiome(INoiseRandom random, List<Supplier<Biome>> biomes) {
		return biomes.get(random.random(biomes.size())).get();
	}
}
