package twilightforest.world.components.layer.vanillalegacy.traits;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.world.components.layer.vanillalegacy.area.Area;
import twilightforest.world.components.layer.vanillalegacy.context.BigContext;
import twilightforest.world.components.layer.vanillalegacy.context.Context;

import java.util.function.Supplier;

public interface AreaTransformer2 extends DimensionTransformer {
	default <R extends Area> Supplier<R> run(BigContext<R> p_77021_, Supplier<R> p_77022_, Supplier<R> p_77023_) {
		return () -> {
			R r = p_77022_.get();
			R r1 = p_77023_.get();
			return p_77021_.createResult((x, z) -> {
				p_77021_.initRandom(x, z);
				return this.applyPixel(p_77021_, r, r1, x, z);
			}, r, r1);
		};
	}

	ResourceKey<Biome> applyPixel(Context p_77024_, Area p_77025_, Area p_77026_, int x, int z);
}
