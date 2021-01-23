package twilightforest.world;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.SmoothLayer;
import net.minecraft.world.gen.layer.ZoomLayer;
import net.minecraftforge.fml.RegistryObject;
import twilightforest.TwilightForestMod;
import twilightforest.biomes.TFBiomes;
import twilightforest.world.layer.GenLayerTFBiomeStabilize;
import twilightforest.world.layer.GenLayerTFBiomes;
import twilightforest.world.layer.GenLayerTFCompanionBiomes;
import twilightforest.world.layer.GenLayerTFKeyBiomes;
import twilightforest.world.layer.GenLayerTFRiverMix;
import twilightforest.world.layer.GenLayerTFStream;
import twilightforest.world.layer.GenLayerTFThornBorder;

import java.util.Arrays;
import java.util.List;
import java.util.function.LongFunction;
import java.util.function.Supplier;

public class TFBiomeProvider extends BiomeProvider {
	public static final Codec<TFBiomeProvider> tfBiomeProviderCodec = RecordCodecBuilder.create((instance) ->
			instance.group(Codec.LONG.fieldOf("seed").stable().orElseGet(() -> TFDimensions.seed).forGetter((obj) -> obj.seed),
					RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter(provider -> provider.registry))
					.apply(instance, instance.stable(TFBiomeProvider::new)));

	private final Registry<Biome> registry;
	private final Layer genBiomes;
	private final TFBiomeCache mapCache;
	private final long seed;
	private static final List<RegistryKey<Biome>> BIOMES = ImmutableList.of( //TODO: Can we do this more efficiently?
			TFBiomes.tfLake,
			TFBiomes.twilightForest,
			TFBiomes.denseTwilightForest,
			TFBiomes.highlands,
			TFBiomes.mushrooms,
			TFBiomes.tfSwamp,
			TFBiomes.stream,
			TFBiomes.snowy_forest,
			TFBiomes.glacier,
			TFBiomes.clearing,
			TFBiomes.oakSavanna,
			TFBiomes.fireflyForest,
			TFBiomes.deepMushrooms,
			TFBiomes.darkForest,
			TFBiomes.enchantedForest,
			TFBiomes.fireSwamp,
			TFBiomes.darkForestCenter,
			TFBiomes.finalPlateau,
			TFBiomes.thornlands,
			TFBiomes.spookyForest
	);

	public TFBiomeProvider(long seed, Registry<Biome> reg) {
		super(BIOMES.stream().map(key -> () -> reg.getValueForKey(key)));
		this.seed = seed;
		//getBiomesToSpawnIn().clear();
		//getBiomesToSpawnIn().add(TFBiomes.twilightForest.get());
		//getBiomesToSpawnIn().add(TFBiomes.denseTwilightForest.get());
		//getBiomesToSpawnIn().add(TFBiomes.clearing.get());
		//getBiomesToSpawnIn().add(TFBiomes.tfSwamp.get());
		//getBiomesToSpawnIn().add(TFBiomes.mushrooms.get());

		registry = reg;
		genBiomes = makeLayers(seed, reg);
		mapCache = new TFBiomeCache(this, 512, true);
	}

	public static int getBiomeId(RegistryKey<Biome> biome, Registry<Biome> registry) {
		return registry.getId(registry.getValueForKey(biome));
	}

	private static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> makeLayers(LongFunction<C> seed, Registry<Biome> registry) {
		IAreaFactory<T> biomes = GenLayerTFBiomes.INSTANCE.setup(registry).apply(seed.apply(1L));
		biomes = GenLayerTFKeyBiomes.INSTANCE.setup(registry).apply(seed.apply(1000L), biomes);
		biomes = GenLayerTFCompanionBiomes.INSTANCE.apply(seed.apply(1000L), biomes);

		biomes = ZoomLayer.NORMAL.apply(seed.apply(1000L), biomes);
		biomes = ZoomLayer.NORMAL.apply(seed.apply(1001), biomes);

		biomes = GenLayerTFBiomeStabilize.INSTANCE.apply(seed.apply(700L), biomes);

		biomes = GenLayerTFThornBorder.INSTANCE.setup(registry).apply(seed.apply(500L), biomes);

		biomes = ZoomLayer.NORMAL.apply(seed.apply(1002), biomes);
		biomes = ZoomLayer.NORMAL.apply(seed.apply(1003), biomes);
		biomes = ZoomLayer.NORMAL.apply(seed.apply(1004), biomes);
		biomes = ZoomLayer.NORMAL.apply(seed.apply(1005), biomes);

		IAreaFactory<T> riverLayer = GenLayerTFStream.INSTANCE.setup(registry).apply(seed.apply(1L), biomes);
		riverLayer = SmoothLayer.INSTANCE.apply(seed.apply(7000L), riverLayer);
		biomes = GenLayerTFRiverMix.INSTANCE.setup(registry).apply(seed.apply(100L), biomes, riverLayer);

		return biomes;
	}

	public static Layer makeLayers(long seed, Registry<Biome> registry) {
		IAreaFactory<LazyArea> areaFactory = makeLayers((context) -> new LazyAreaLayerContext(25, seed, context), registry);
		return new Layer(areaFactory) {
			public Biome func_242936_a(Registry<Biome> p_242936_1_, int p_242936_2_, int p_242936_3_) {
				int i = this.field_215742_b.getValue(p_242936_2_, p_242936_3_);
				Biome biome = registry.getByValue(i);
				if (biome == null)
					throw new IllegalStateException("Unknown biome id emitted by layers: " + i);
				return biome;
			}
		};
	}

	@Override
	protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
		return tfBiomeProviderCodec;
	}

	@Override
	public BiomeProvider getBiomeProvider(long l) {
		return new TFBiomeProvider(l, registry);
	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z) {
		return getNoiseBiome(x, y, z, true);
	}

	public Biome getNoiseBiome(int x, int y, int z, boolean useCache) {
		// for grid-centred magic maps, get from map cache
				if (useCache && mapCache.isGridAligned(x, z)) {
					return mapCache.getBiome(x, z);
				}
		return genBiomes.func_242936_a(registry, x, z);
	}

//	@Override
//	public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height) {
//		return getBiomesForGeneration(biomes, x, z, width, height, true);
//	}

//	public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height, boolean useCache) {
//		// for grid-centred magic maps, get from map cache
//		if (useCache && mapCache.isGridAligned(x, z, width, height)) {
//			Biome[] cached = mapCache.getBiomes(x, z);
//			return Arrays.copyOf(cached, cached.length);
//		}
//		return super.getBiomesForGeneration(biomes, x, z, width, height);
//	}

//	@Override
//	public void cleanupCache() {
//		mapCache.cleanup();
//		super.cleanupCache();
//	}
}
