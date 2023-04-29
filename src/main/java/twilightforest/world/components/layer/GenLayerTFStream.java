package twilightforest.world.components.layer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.init.TFBiomes;
import twilightforest.init.custom.BiomeLayerStack;
import twilightforest.init.custom.BiomeLayerTypes;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerFactory;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerType;
import twilightforest.world.components.layer.vanillalegacy.area.LazyArea;
import twilightforest.world.components.layer.vanillalegacy.context.Context;
import twilightforest.world.components.layer.vanillalegacy.context.LazyAreaContext;
import twilightforest.world.components.layer.vanillalegacy.traits.CastleTransformer;

import java.util.function.LongFunction;

public enum GenLayerTFStream implements CastleTransformer {

	INSTANCE;
	
	@Override
	public ResourceKey<Biome> apply(Context context, ResourceKey<Biome> up, ResourceKey<Biome> left, ResourceKey<Biome> down, ResourceKey<Biome> right, ResourceKey<Biome> mid) {
		if (shouldStream(mid, left) || shouldStream(mid, right) || shouldStream(mid, down) || shouldStream(mid, up)) {
			return TFBiomes.STREAM;
		} else {
			return mid;
		}
	}

	
	boolean shouldStream(ResourceKey<Biome> biome1, ResourceKey<Biome> biome2) {
		if (biome1 == biome2) {
			return false;
		}
		
		final ResourceKey<Biome> tfLake = TFBiomes.LAKE;
		final ResourceKey<Biome> thornlands = TFBiomes.THORNLANDS;
		
		return !(testEitherBiomeOR(biome1, biome2, tfLake, tfLake)
				|| testEitherBiomeOR(biome1, biome2, thornlands, thornlands)
				|| testEitherBiomeOR(biome1, biome2, TFBiomes.CLEARING, TFBiomes.OAK_SAVANNAH)
				|| testEitherBiomeAND(biome1, biome2, TFBiomes.SNOWY_FOREST, TFBiomes.GLACIER)
				|| testEitherBiomeAND(biome1, biome2, TFBiomes.MUSHROOM_FOREST, TFBiomes.DENSE_MUSHROOM_FOREST)
				|| testEitherBiomeAND(biome1, biome2, TFBiomes.SWAMP, TFBiomes.FIRE_SWAMP)
				|| testEitherBiomeAND(biome1, biome2, TFBiomes.DARK_FOREST, TFBiomes.DARK_FOREST_CENTER)
				|| testEitherBiomeAND(biome1, biome2, TFBiomes.HIGHLANDS, TFBiomes.FINAL_PLATEAU));
	}

	private boolean testEitherBiomeAND(ResourceKey<Biome> test1, ResourceKey<Biome> test2, ResourceKey<Biome> predicate1, ResourceKey<Biome> predicate2) {
		return (test1 == predicate1 && test2 == predicate2) || (test2 == predicate1 && test1 == predicate2);
	}

	private boolean testEitherBiomeOR(ResourceKey<Biome> test1, ResourceKey<Biome> test2, ResourceKey<Biome> predicate1, ResourceKey<Biome> predicate2) {
		return (test1 == predicate1 || test2 == predicate2) || (test2 == predicate1 || test1 == predicate2);
	}

	public record Factory(long salt, Holder<BiomeLayerFactory> parent) implements BiomeLayerFactory {
		public static final Codec<Factory> CODEC = RecordCodecBuilder.create(inst -> inst.group(
				Codec.LONG.fieldOf("salt").forGetter(Factory::salt),
				BiomeLayerStack.HOLDER_CODEC.fieldOf("parent").forGetter(Factory::parent)
		).apply(inst, Factory::new));

		// TODO Parameterize these companion biomes in relation to key biomes
		@Override
		public LazyArea build(LongFunction<LazyAreaContext> contextFactory) {
			return INSTANCE.run(contextFactory.apply(this.salt), this.parent.get().build(contextFactory));
		}

		@Override
		public BiomeLayerType getType() {
			return BiomeLayerTypes.STREAM.get();
		}
	}
}
