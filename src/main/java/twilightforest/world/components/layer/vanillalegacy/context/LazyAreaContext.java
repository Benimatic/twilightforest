package twilightforest.world.components.layer.vanillalegacy.context;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.LinearCongruentialGenerator;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import twilightforest.init.TFDimensionSettings;
import twilightforest.world.components.layer.vanillalegacy.area.LazyArea;
import twilightforest.world.components.layer.vanillalegacy.traits.PixelTransformer;

public class LazyAreaContext implements BigContext<LazyArea> {
	private final Long2ObjectLinkedOpenHashMap<ResourceKey<Biome>> cache;
	private final int maxCache;
	private final long seed;
	private long rval;

	public LazyAreaContext(int maxCache, long salt) {
		this.seed = mixSeed(TFDimensionSettings.seed, salt);
		this.cache = new Long2ObjectLinkedOpenHashMap<>(16, 0.25F);
		this.cache.defaultReturnValue(Biomes.THE_VOID);
		this.maxCache = maxCache;
	}

	public LazyArea createResult(PixelTransformer p_76552_) {
		return new LazyArea(this.cache, this.maxCache, p_76552_);
	}

	public LazyArea createResult(PixelTransformer p_76541_, LazyArea p_76542_) {
		return new LazyArea(this.cache, Math.min(1024, p_76542_.getMaxCache() * 4), p_76541_);
	}

	public LazyArea createResult(PixelTransformer p_76544_, LazyArea p_76545_, LazyArea p_76546_) {
		return new LazyArea(this.cache, Math.min(1024, Math.max(p_76545_.getMaxCache(), p_76546_.getMaxCache()) * 4), p_76544_);
	}

	public void initRandom(long x, long z) {
		long i = this.seed;
		i = LinearCongruentialGenerator.next(i, x);
		i = LinearCongruentialGenerator.next(i, z);
		i = LinearCongruentialGenerator.next(i, x);
		i = LinearCongruentialGenerator.next(i, z);
		this.rval = i;
	}

	public int nextRandom(int p_76527_) {
		int i = Math.floorMod(this.rval >> 24, p_76527_);
		this.rval = LinearCongruentialGenerator.next(this.rval, this.seed);
		return i;
	}

	private static long mixSeed(long seed, long salt) {
		long i = LinearCongruentialGenerator.next(salt, salt);
		i = LinearCongruentialGenerator.next(i, salt);
		i = LinearCongruentialGenerator.next(i, salt);
		long j = LinearCongruentialGenerator.next(seed, i);
		j = LinearCongruentialGenerator.next(j, i);
		return LinearCongruentialGenerator.next(j, i);
	}
}
