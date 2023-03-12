package twilightforest.world.components.layer.vanillalegacy.area;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import twilightforest.world.components.layer.vanillalegacy.traits.PixelTransformer;

public class LazyArea implements Area {
	private final PixelTransformer transformer;
	private final Long2ObjectLinkedOpenHashMap<ResourceKey<Biome>> cachedSamples;
	private final int maxCache;

	public LazyArea(Long2ObjectLinkedOpenHashMap<ResourceKey<Biome>> p_76493_, int p_76494_, PixelTransformer p_76495_) {
		this.cachedSamples = p_76493_;
		this.maxCache = p_76494_;
		this.transformer = p_76495_;
	}

	public ResourceKey<Biome> get(int x, int z) {
		long i = ChunkPos.asLong(x, z);
		synchronized(this.cachedSamples) {
			ResourceKey<Biome> j = this.cachedSamples.get(i);
			if (j != null && j != Biomes.THE_VOID) {
				return j;
			} else {
				ResourceKey<Biome> k = this.transformer.apply(x, z);
				this.cachedSamples.put(i, k);
				if (this.cachedSamples.size() > this.maxCache) {
					for(int l = 0; l < this.maxCache / 16; ++l) {
						this.cachedSamples.removeFirst();
					}
				}

				return k;
			}
		}
	}

	public int getMaxCache() {
		return this.maxCache;
	}
}
