package twilightforest.world.components.layer.vanillalegacy.traits;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public interface PixelTransformer {
	ResourceKey<Biome> apply(int x, int z);
}