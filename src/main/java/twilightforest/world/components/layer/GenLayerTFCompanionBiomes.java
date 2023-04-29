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

public enum GenLayerTFCompanionBiomes implements CastleTransformer {
	INSTANCE;

	private final ResourceKey<Biome> fireSwamp = TFBiomes.FIRE_SWAMP;
	private final ResourceKey<Biome> swamp = TFBiomes.SWAMP;
	private final ResourceKey<Biome> glacier = TFBiomes.GLACIER;
	private final ResourceKey<Biome> snowyForest = TFBiomes.SNOWY_FOREST;
	private final ResourceKey<Biome> darkForestCenter = TFBiomes.DARK_FOREST_CENTER;
	private final ResourceKey<Biome> darkForest = TFBiomes.DARK_FOREST;
	private final ResourceKey<Biome> finalPlateau = TFBiomes.FINAL_PLATEAU;
	private final ResourceKey<Biome> highlands = TFBiomes.HIGHLANDS;

	@Override
	public ResourceKey<Biome> apply(Context context, ResourceKey<Biome> up, ResourceKey<Biome> right, ResourceKey<Biome> down, ResourceKey<Biome> left, ResourceKey<Biome> center) {
		if (isKey(this.fireSwamp, center, left, right, up, down)) {
			return this.swamp;
		} else if (isKey(this.glacier, center, left, right, up, down)) {
			return this.snowyForest;
		} else if (isKey(this.darkForestCenter, center, left, right, up, down)) {
			return this.darkForest;
		} else if (isKey(this.finalPlateau, center, left, right, up, down)) {
			return this.highlands;
		} else {
			return center;
		}
	}

	/**
	 * Returns true if any of the surrounding biomes is the specified biome
	 */
	boolean isKey(ResourceKey<Biome> biome, ResourceKey<Biome> center, ResourceKey<Biome> right, ResourceKey<Biome> left, ResourceKey<Biome> up, ResourceKey<Biome> down) {
		return center != biome && (right == biome || left == biome || up == biome || down == biome);
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
			return BiomeLayerTypes.COMPANION_BIOMES.get();
		}
	}
}
