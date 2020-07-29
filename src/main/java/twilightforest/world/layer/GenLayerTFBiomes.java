package twilightforest.world.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import twilightforest.biomes.TFBiomes;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Applies the twilight forest biomes to the map
 *
 * @author Ben
 */
public class GenLayerTFBiomes implements IAreaTransformer0 {

	private static final int RARE_BIOME_CHANCE = 15;

	protected static final List<Supplier<Biome>> commonBiomes = Arrays.asList(
			TFBiomes.twilightForest,
			TFBiomes.denseTwilightForest,
			TFBiomes.mushrooms,
			TFBiomes.oakSavanna,
			TFBiomes.fireflyForest
	);
	protected static final List<Supplier<Biome>> rareBiomes = Arrays.asList(
			TFBiomes.tfLake,
			TFBiomes.deepMushrooms,
			TFBiomes.enchantedForest,
			TFBiomes.clearing,
			TFBiomes.spookyForest
	);

//	public GenLayerTFBiomes(long l, Layer genlayer) {
//		super(l);
//		parent = genlayer;
//	}
//
//	public GenLayerTFBiomes(long l) {
//		super(l);
//	}

	public GenLayerTFBiomes() { }

	@Override
	public int apply(INoiseRandom iNoiseRandom, int i, int i1) {
		if (iNoiseRandom.random(RARE_BIOME_CHANCE) == 0) {
			// make rare biome
			return Registry.BIOME.getId(getRandomBiome(iNoiseRandom, rareBiomes));
		} else {
			// make common biome
			return Registry.BIOME.getId(getRandomBiome(iNoiseRandom, commonBiomes));
		}
	}

//	@Override
//	public int[] getInts(int x, int z, int width, int depth) {
//
//		int dest[] = IntCache.getIntCache(width * depth);
//
//		for (int dz = 0; dz < depth; dz++) {
//			for (int dx = 0; dx < width; dx++) {
//				initChunkSeed(dx + x, dz + z);
//				if (nextInt(RARE_BIOME_CHANCE) == 0) {
//					// make rare biome
//					dest[dx + dz * width] = Biome.getIdForBiome(getRandomBiome(rareBiomes));
//				} else {
//					// make common biome
//					dest[dx + dz * width] = Biome.getIdForBiome(getRandomBiome(commonBiomes));
//				}
//			}
//		}

//		for (int i = 0; i < width * depth; i++)
//		{
//			if (dest[i] < 0 || dest[i] > TFBiomeBase.fireSwamp.biomeID)
//			{
//				System.err.printf("Made a bad ID, %d at %d, %d while generating\n", dest[i], x, z);
//			}
//		}

	private Biome getRandomBiome(INoiseRandom random, List<Supplier<Biome>> biomes) {
		return biomes.get(random.random(biomes.size())).get();
	}
}
