package twilightforest.world.components.layer.vanillalegacy;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;
import twilightforest.world.components.layer.*;
import twilightforest.world.components.layer.vanillalegacy.area.LazyArea;
import twilightforest.world.components.layer.vanillalegacy.context.LazyAreaContext;

import java.util.function.LongFunction;

public class Layer implements Area {
	public final LazyArea area;

	public Layer() {
		this.area = buildLayers((salt) -> new LazyAreaContext(25, salt));
	}

	@NotNull
	private static LazyArea buildLayers(LongFunction<LazyAreaContext> contextFactory) {
		// TODO Data-drive constructing the LazyArea
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
	public ResourceKey<Biome> getBiome(int x, int z) {
		return this.area.getBiome(x, z);
	}
}
