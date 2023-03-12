package twilightforest.world.components.layer.vanillalegacy.traits;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.world.components.layer.vanillalegacy.area.Area;
import twilightforest.world.components.layer.vanillalegacy.context.BigContext;

import java.util.function.Supplier;

public interface AreaTransformer1  extends DimensionTransformer {
	default <R extends Area> Supplier<R> run(BigContext<R> p_77003_, Supplier<R> p_77004_) {
		return () -> {
			R r = p_77004_.get();
			return p_77003_.createResult((x, z) -> {
				p_77003_.initRandom(x, z);
				return this.applyPixel(p_77003_, r, x, z);
			}, r);
		};
	}

	ResourceKey<Biome> applyPixel(BigContext<?> p_76998_, Area p_76999_, int x, int z);
}