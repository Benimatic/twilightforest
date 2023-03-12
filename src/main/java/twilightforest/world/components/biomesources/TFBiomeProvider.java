package twilightforest.world.components.biomesources;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import twilightforest.init.TFDimensionSettings;
import twilightforest.world.components.chunkgenerators.warp.TerrainColumn;
import twilightforest.world.components.layer.vanillalegacy.Layer;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Stream;

@Deprecated
public class TFBiomeProvider extends BiomeSource {
	public static final Codec<TFBiomeProvider> TF_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			RegistryOps.retrieveGetter(Registries.BIOME),
			TerrainColumn.CODEC.listOf().fieldOf("biome_landscape").xmap(l -> l.stream().collect(Collectors.toMap(TerrainColumn::getResourceKey, Function.identity())), m -> m.values().stream().sorted(Comparator.comparing(TerrainColumn::getResourceKey)).toList()).forGetter(o -> o.biomeList),
			Codec.FLOAT.fieldOf("base_offset").forGetter(o -> o.baseOffset),
			Codec.FLOAT.fieldOf("base_factor").forGetter(o -> o.baseFactor)
	).apply(instance, instance.stable(TFBiomeProvider::new)));

	private final HolderGetter<Biome> registry;
	private final Map<ResourceKey<Biome>, TerrainColumn> biomeList;
	private final float baseOffset;
	private final float baseFactor;

	private Layer genBiomes;

	public TFBiomeProvider(HolderGetter<Biome> registry, List<TerrainColumn> list, float offset, float factor) {
		this(registry, list.stream().collect(Collectors.toMap(TerrainColumn::getResourceKey, Function.identity())), offset, factor);
	}

	public TFBiomeProvider(HolderGetter<Biome> registryIn, Map<ResourceKey<Biome>, TerrainColumn> list, float offset, float factor) {
		super();

		this.baseOffset = offset;
		this.baseFactor = factor;

		this.registry = registryIn;
		this.biomeList = list;
	}

	@Override
	protected Stream<Holder<Biome>> collectPossibleBiomes() {
		return this.biomeList.values().stream().flatMap(TerrainColumn::getBiomes);
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
		lazyLoadGenBiomes();
		return this.getBiomeDepth(this.genBiomes.get(x, z));
	}

	public float getBiomeDepth(ResourceKey<Biome> biome) {
		return this.getBiomeValue(biome, TerrainColumn::depth, 0f);
	}

	public Optional<TerrainColumn> getTerrainColumn(int x, int z) {
		lazyLoadGenBiomes();
		return this.getTerrainColumn(this.genBiomes.get(x, z));
	}

	public Optional<TerrainColumn> getTerrainColumn(ResourceKey<Biome> biome) {
		return this.biomeList.values().stream().filter(p -> p.is(biome)).findFirst();
	}

	public <T> T getBiomeValue(ResourceKey<Biome> biome, Function<TerrainColumn, T> function, T other) {
		return this.getTerrainColumn(biome).map(function).orElse(other);
	}

	@Override
	public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
		// TODO Further positional-smoothing, see TFTerrainWarp.fillNoiseColumn's use of TFTerrainWarp.BIOME_WEIGHTS
		//  That method already calls this method, resulting in search-space duplication

		// FIXME Hacky double-dipping of biomes
		lazyLoadGenBiomes();

		return this.biomeList.get(this.genBiomes.get(x, z)).getBiome(y);
	}

	private void lazyLoadGenBiomes() {
		if (genBiomes == null)
			this.genBiomes = Layer.makeLayers(TFDimensionSettings.seed, registry);
	}
}
