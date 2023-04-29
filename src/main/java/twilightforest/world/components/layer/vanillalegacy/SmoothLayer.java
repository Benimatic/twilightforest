package twilightforest.world.components.layer.vanillalegacy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.init.custom.BiomeLayerStack;
import twilightforest.init.custom.BiomeLayerTypes;
import twilightforest.world.components.layer.vanillalegacy.area.LazyArea;
import twilightforest.world.components.layer.vanillalegacy.context.Context;
import twilightforest.world.components.layer.vanillalegacy.context.LazyAreaContext;
import twilightforest.world.components.layer.vanillalegacy.traits.CastleTransformer;

import java.util.function.LongFunction;

public enum SmoothLayer implements CastleTransformer {
	INSTANCE;

	@Override
	public ResourceKey<Biome> apply(Context p_76938_, ResourceKey<Biome> p_76939_, ResourceKey<Biome> p_76940_, ResourceKey<Biome> p_76941_, ResourceKey<Biome> p_76942_, ResourceKey<Biome> p_76943_) {
		boolean flag = p_76940_ == p_76942_;
		boolean flag1 = p_76939_ == p_76941_;
		if (flag == flag1) {
			if (flag) {
				return p_76938_.nextRandom(2) == 0 ? p_76942_ : p_76939_;
			} else {
				return p_76943_;
			}
		} else {
			return flag ? p_76942_ : p_76939_;
		}
	}

	public record Factory(long salt, Holder<BiomeLayerFactory> parent) implements BiomeLayerFactory {
		public static final Codec<Factory> CODEC = RecordCodecBuilder.create(inst -> inst.group(
				Codec.LONG.fieldOf("salt").forGetter(Factory::salt),
				BiomeLayerStack.HOLDER_CODEC.fieldOf("parent").forGetter(Factory::parent)
		).apply(inst, Factory::new));

		@Override
		public LazyArea build(LongFunction<LazyAreaContext> contextFactory) {
			return INSTANCE.run(contextFactory.apply(this.salt), this.parent.get().build(contextFactory));
		}

		@Override
		public BiomeLayerType getType() {
			return BiomeLayerTypes.SMOOTH.get();
		}
	}
}
