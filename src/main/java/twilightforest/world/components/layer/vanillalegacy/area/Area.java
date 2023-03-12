package twilightforest.world.components.layer.vanillalegacy.area;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public interface Area {
	ResourceKey<Biome> get(int x, int z);
}
