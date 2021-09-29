package twilightforest;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import twilightforest.world.components.BiomeGrassColors;
import twilightforest.world.registration.biomes.BiomeKeys;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public final class FoliageColorHandler {

	private static final Map<Biome, Handler> HANDLES = new WeakHashMap<>();

	public static int get(int o, Biome biome, double x, double z) {
		Handler handler = HANDLES.get(biome);
		if (handler == null) {
			handler = Handler.REGISTRY.getOrDefault(biome.getRegistryName(), Handler.DEFAULT);
			HANDLES.put(biome, handler);
		}
		return handler.apply(o, biome, x, z);
	}

	@FunctionalInterface
	private interface Handler {

		Map<ResourceLocation, Handler> REGISTRY = new HashMap<>() {{
			put(BiomeKeys.SPOOKY_FOREST.location(), (o, biome, x, z) -> {
				double noise = (Biome.BIOME_INFO_NOISE.getValue(x * 0.0225D, z * 0.0225D, false) + 1D) / 2D;
				return BiomeGrassColors.blendColors(0xFF8501, 0xF7FF01, noise > 0.60D ? noise * 0.2D : noise);
			});
		}};

		Handler DEFAULT = (o, biome, x, z) -> o;

		int apply(int o, Biome biome, double x, double z);


	}

}
