package twilightforest.world.components.layer.vanillalegacy.context;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.world.components.layer.vanillalegacy.area.Area;
import twilightforest.world.components.layer.vanillalegacy.traits.PixelTransformer;

public interface BigContext <R extends Area> extends Context {
	void initRandom(long x, long z);

	R createResult(PixelTransformer p_76510_);

	default R createResult(PixelTransformer p_76511_, R p_76512_) {
		return this.createResult(p_76511_);
	}

	default R createResult(PixelTransformer p_76513_, R p_76514_, R p_76515_) {
		return this.createResult(p_76513_);
	}

	default ResourceKey<Biome> random(ResourceKey<Biome> p_76501_, ResourceKey<Biome> p_76502_) {
		return this.nextRandom(2) == 0 ? p_76501_ : p_76502_;
	}

	default ResourceKey<Biome> random(ResourceKey<Biome> p_76504_, ResourceKey<Biome> p_76505_, ResourceKey<Biome> p_76506_, ResourceKey<Biome> p_76507_) {
		int i = this.nextRandom(4);
		if (i == 0) {
			return p_76504_;
		} else if (i == 1) {
			return p_76505_;
		} else {
			return i == 2 ? p_76506_ : p_76507_;
		}
	}
}