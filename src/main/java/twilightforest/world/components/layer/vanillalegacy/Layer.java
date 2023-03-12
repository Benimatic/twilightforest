package twilightforest.world.components.layer.vanillalegacy;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.server.ServerLifecycleHooks;
import twilightforest.world.components.layer.vanillalegacy.area.LazyArea;

import java.util.Optional;
import java.util.function.Supplier;

public class Layer {
	public final LazyArea area;

	public Layer(Supplier<LazyArea> p_76714_) {
		this.area = p_76714_.get();
	}

	public Holder<Biome> get(HolderGetter<Biome> registry, int x, int z) {
		ResourceKey<Biome> i = this.area.get(x, z);
		Optional<Holder.Reference<Biome>> biome = ServerLifecycleHooks.getCurrentServer().registryAccess().registryOrThrow(Registries.BIOME).getHolder(i);
		if (biome.isEmpty()) {
			Util.logAndPauseIfInIde("Unknown biome id: " + i);
			return registry.getOrThrow(Biomes.PLAINS);
		} else {
			return biome.get();
		}

	}
}
