package twilightforest.world.components.biomesources;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.NotNull;
import twilightforest.TwilightForestMod;
import twilightforest.init.custom.BiomeLayerStack;
import twilightforest.init.custom.BiomeLayerTypes;
import twilightforest.world.components.chunkgenerators.warp.TerrainColumn;
import twilightforest.world.components.layer.*;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerType;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerFactory;
import twilightforest.world.components.layer.vanillalegacy.SmoothLayer;
import twilightforest.world.components.layer.vanillalegacy.ZoomLayer;
import twilightforest.world.components.layer.vanillalegacy.area.LazyArea;
import twilightforest.world.components.layer.vanillalegacy.context.LazyAreaContext;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Deprecated // Pending rename
public class TFBiomeProvider extends BiomeSource {
	public static final Codec<TFBiomeProvider> TF_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			TerrainColumn.CODEC.listOf().fieldOf("biome_landscape").xmap(l -> l.stream().collect(Collectors.toMap(TerrainColumn::getResourceKey, Function.identity())), m -> m.values().stream().sorted(Comparator.comparing(TerrainColumn::getResourceKey)).toList()).forGetter(o -> o.biomeList),
			Codec.FLOAT.fieldOf("base_offset").forGetter(o -> o.baseOffset),
			Codec.FLOAT.fieldOf("base_factor").forGetter(o -> o.baseFactor),
			BiomeLayerStack.DISPATCH_CODEC.fieldOf("biome_layer_config").orElseGet(s -> { TwilightForestMod.LOGGER.warn("Failed to parse biome layer config: " + s); }, LegacyLayer::new).forGetter(TFBiomeProvider::getBiomeConfig)
	).apply(instance, instance.stable(TFBiomeProvider::new)));

	private final Map<ResourceKey<Biome>, TerrainColumn> biomeList;
	private final float baseOffset;
	private final float baseFactor;

	private final BiomeLayerFactory genBiomeConfig;
	private final LazyArea genBiomes;

	private static final LegacyLayer LEGACY_LAYERS = new LegacyLayer();
	public static final Codec<LegacyLayer> LEGACY_CODEC = Codec.unit(LEGACY_LAYERS);

	@Deprecated
	public TFBiomeProvider(List<TerrainColumn> list, float offset, float factor) {
		this(list.stream().collect(Collectors.toMap(TerrainColumn::getResourceKey, Function.identity())), offset, factor, LEGACY_LAYERS);
	}

	public TFBiomeProvider(Map<ResourceKey<Biome>, TerrainColumn> list, float offset, float factor, BiomeLayerFactory biomeLayerFactory) {
		super();

		//this.genBiomes = buildLayers((salt) -> new LazyAreaContext(25, salt));
		this.genBiomeConfig = biomeLayerFactory;
		this.genBiomes = this.genBiomeConfig.build(salt -> new LazyAreaContext(25, salt));

		this.baseOffset = offset;
		this.baseFactor = factor;

		this.biomeList = list;
	}

	@Override
	protected Stream<Holder<Biome>> collectPossibleBiomes() {
		return this.biomeList.values().stream().flatMap(TerrainColumn::getBiomes);
	}

	@NotNull
	public static LazyArea buildLayers(LongFunction<LazyAreaContext> contextFactory) {
		LazyArea biomes = GenLayerTFBiomes.INSTANCE.run(contextFactory.apply(1L));
		biomes = GenLayerTFKeyBiomes.INSTANCE.run(contextFactory.apply(1000L), biomes);
		biomes = GenLayerTFCompanionBiomes.INSTANCE.run(contextFactory.apply(1000L), biomes);

		biomes = ZoomLayer.NORMAL.run(contextFactory.apply(1000L), biomes);
		biomes = ZoomLayer.NORMAL.run(contextFactory.apply(1001L), biomes);

		biomes = GenLayerTFBiomeStabilize.INSTANCE.run(contextFactory.apply(700L), biomes);

		biomes = GenLayerTFThornBorder.INSTANCE.run(contextFactory.apply(500L), biomes);

		biomes = ZoomLayer.NORMAL.run(contextFactory.apply(1002), biomes);
		biomes = ZoomLayer.NORMAL.run(contextFactory.apply(1003), biomes);
		biomes = ZoomLayer.NORMAL.run(contextFactory.apply(1004), biomes);
		biomes = ZoomLayer.NORMAL.run(contextFactory.apply(1005), biomes);

		LazyArea riverLayer = GenLayerTFStream.INSTANCE.run(contextFactory.apply(1L), biomes);
		riverLayer = SmoothLayer.INSTANCE.run(contextFactory.apply(7000L), riverLayer);
		biomes = GenLayerTFRiverMix.INSTANCE.setup().run(contextFactory.apply(100L), biomes, riverLayer);
		return biomes;
	}

	@Override
	protected Codec<? extends BiomeSource> codec() {
		return TF_CODEC;
	}

	public float getBaseOffset() {
		return this.baseOffset;
	}

	public float getBaseFactor() {
		return this.baseFactor;
	}

	public float getBiomeDepth(int x, int z) {
		return this.getBiomeDepth(this.genBiomes.getBiome(x, z));
	}

	public float getBiomeDepth(ResourceKey<Biome> biome) {
		return this.getBiomeValue(biome, TerrainColumn::depth, 0f);
	}

	public Optional<TerrainColumn> getTerrainColumn(int x, int z) {
		return this.getTerrainColumn(this.genBiomes.getBiome(x, z));
	}

	public Optional<TerrainColumn> getTerrainColumn(ResourceKey<Biome> biome) {
		return this.biomeList.values().stream().filter(p -> p.is(biome)).findFirst();
	}

	public <T> T getBiomeValue(ResourceKey<Biome> biome, Function<TerrainColumn, T> function, T other) {
		return this.getTerrainColumn(biome).map(function).orElse(other);
	}

	@Override
	public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
		return this.biomeList.get(this.genBiomes.getBiome(x, z)).getBiome(y);
	}

	private BiomeLayerFactory getBiomeConfig() {
		return this.genBiomeConfig;
	}

	private static class LegacyLayer implements BiomeLayerFactory {
		// FIXME Replicate into datapack components
		@Override
		public LazyArea build(LongFunction<LazyAreaContext> contextFactory) {
			LazyArea biomes = GenLayerTFBiomes.INSTANCE.run(contextFactory.apply(1L));
			biomes = GenLayerTFKeyBiomes.INSTANCE.run(contextFactory.apply(1000L), biomes);
			biomes = GenLayerTFCompanionBiomes.INSTANCE.run(contextFactory.apply(1000L), biomes);

			biomes = ZoomLayer.NORMAL.run(contextFactory.apply(1000L), biomes);
			biomes = ZoomLayer.NORMAL.run(contextFactory.apply(1001L), biomes);

			biomes = GenLayerTFBiomeStabilize.INSTANCE.run(contextFactory.apply(700L), biomes);

			biomes = GenLayerTFThornBorder.INSTANCE.run(contextFactory.apply(500L), biomes);

			biomes = ZoomLayer.NORMAL.run(contextFactory.apply(1002), biomes);
			biomes = ZoomLayer.NORMAL.run(contextFactory.apply(1003), biomes);
			biomes = ZoomLayer.NORMAL.run(contextFactory.apply(1004), biomes);
			biomes = ZoomLayer.NORMAL.run(contextFactory.apply(1005), biomes);

			LazyArea riverLayer = GenLayerTFStream.INSTANCE.run(contextFactory.apply(1L), biomes);
			riverLayer = SmoothLayer.INSTANCE.run(contextFactory.apply(7000L), riverLayer);
			biomes = GenLayerTFRiverMix.INSTANCE.setup().run(contextFactory.apply(100L), biomes, riverLayer);
			return biomes;
		}

		@Override
		public BiomeLayerType getType() {
			return BiomeLayerTypes.LEGACY.get();
		}
	}
}
