package twilightforest.world.components.layer.vanillalegacy.traits;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.world.components.layer.vanillalegacy.area.Area;
import twilightforest.world.components.layer.vanillalegacy.context.BigContext;
import twilightforest.world.components.layer.vanillalegacy.context.Context;

import java.util.function.Supplier;

public interface AreaTransformer0 {
	default <R extends Area> Supplier<R> run(BigContext<R> p_76985_) {
		return () -> p_76985_.createResult((x, z) -> {
			p_76985_.initRandom(x, z);
			return this.applyPixel(p_76985_, x, z);
		});
	}

	ResourceKey<Biome> applyPixel(Context p_76990_, int x, int z);
}
