package twilightforest.world.components.layer.vanillalegacy.context;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.world.components.layer.vanillalegacy.Area;

public interface BigContext <R extends Area> extends Context {
	void initRandom(long x, long z);

	R createResult(Area p_76510_);

	default R createResult(Area p_76511_, R p_76512_) {
		return this.createResult(p_76511_);
	}

	default R createResult(Area p_76513_, R p_76514_, R p_76515_) {
		return this.createResult(p_76513_);
	}

	default ResourceKey<Biome> random(ResourceKey<Biome> p_76501_, ResourceKey<Biome> p_76502_) {
		return this.nextRandom(2) == 0 ? p_76501_ : p_76502_;
	}

	default ResourceKey<Biome> random(ResourceKey<Biome> biome1, ResourceKey<Biome> biome2, ResourceKey<Biome> biome3, ResourceKey<Biome> biome4) {
		int i = this.nextRandom(4);
		if (i == 0) {
			return biome1;
		} else if (i == 1) {
			return biome2;
		} else {
			return i == 2 ? biome3 : biome4;
		}
	}
}