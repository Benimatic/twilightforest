package twilightforest.world.components.layer;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.init.TFBiomes;
import twilightforest.init.custom.BiomeLayerTypes;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerType;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerFactory;
import twilightforest.world.components.layer.vanillalegacy.area.LazyArea;
import twilightforest.world.components.layer.vanillalegacy.context.Context;
import twilightforest.world.components.layer.vanillalegacy.context.LazyAreaContext;
import twilightforest.world.components.layer.vanillalegacy.traits.AreaTransformer0;

import java.util.List;
import java.util.function.LongFunction;

/**
 * Applies the twilight forest biomes to the map
 *
 * @author Ben
 */
public enum GenLayerTFBiomes implements AreaTransformer0 {
	INSTANCE(15, ImmutableList.of(
			TFBiomes.FOREST,
			TFBiomes.DENSE_FOREST,
			TFBiomes.MUSHROOM_FOREST,
			TFBiomes.OAK_SAVANNAH,
			TFBiomes.FIREFLY_FOREST
	), ImmutableList.of(
			TFBiomes.LAKE,
			TFBiomes.DENSE_MUSHROOM_FOREST,
			TFBiomes.ENCHANTED_FOREST,
			TFBiomes.CLEARING,
			TFBiomes.SPOOKY_FOREST
	));

	private final int rareBiomeChance;

	private final List<ResourceKey<Biome>> commonBiomes;
	private final List<ResourceKey<Biome>> rareBiomes;

	GenLayerTFBiomes(int rareBiomeChance, List<ResourceKey<Biome>> commonBiomes, List<ResourceKey<Biome>> rareBiomes) {
		this.rareBiomeChance = rareBiomeChance;
		this.commonBiomes = commonBiomes;
		this.rareBiomes = rareBiomes;
	}

	@Override
	public ResourceKey<Biome> applyPixel(Context context, int x, int z) {
		if (context.nextRandom(rareBiomeChance) == 0) {
			// make specialBiomes biome
			return rareBiomes.get(context.nextRandom(rareBiomes.size()));
		} else {
			// make common biome
			return commonBiomes.get(context.nextRandom(commonBiomes.size()));
		}
	}

	public record Layer(int salt) implements BiomeLayerFactory {
		public static final Codec<Layer> CODEC = RecordCodecBuilder.create(inst -> inst.group(
				Codec.INT.fieldOf("salt").forGetter(Layer::salt)
		).apply(inst, Layer::new));

		// TODO Surely these biomes can become parameterized
		@Override
		public LazyArea build(LongFunction<LazyAreaContext> contextFactory) {
			return INSTANCE.run(contextFactory.apply(this.salt));
		}

		@Override
		public BiomeLayerType getType() {
			return BiomeLayerTypes.RANDOM_TWILIGHT_BIOME.get();
		}
	}
}
