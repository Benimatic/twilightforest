package twilightforest.world.components.layer.vanillalegacy;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.world.components.layer.vanillalegacy.area.Area;
import twilightforest.world.components.layer.vanillalegacy.context.BigContext;
import twilightforest.world.components.layer.vanillalegacy.traits.AreaTransformer1;

public enum ZoomLayer implements AreaTransformer1 {
	NORMAL,
	FUZZY {
		protected ResourceKey<Biome> modeOrRandom(BigContext<?> p_76979_, ResourceKey<Biome> p_76980_, ResourceKey<Biome> p_76981_, ResourceKey<Biome> p_76982_, ResourceKey<Biome> p_76983_) {
			return p_76979_.random(p_76980_, p_76981_, p_76982_, p_76983_);
		}
	};

	private static final int ZOOM_BITS = 1;
	private static final int ZOOM_MASK = 1;

	public int getParentX(int x) {
		return x >> 1;
	}

	public int getParentY(int z) {
		return z >> 1;
	}

	@Override
	public ResourceKey<Biome> applyPixel(BigContext<?> p_76966_, Area p_76967_, int x, int z) {
		ResourceKey<Biome> i = p_76967_.get(this.getParentX(x), this.getParentY(z));
		p_76966_.initRandom(x >> 1 << 1, z >> 1 << 1);
		int j = x & 1;
		int k = z & 1;
		if (j == 0 && k == 0) {
			return i;
		} else {
			ResourceKey<Biome> l = p_76967_.get(this.getParentX(x), this.getParentY(z + 1));
			ResourceKey<Biome> i1 = p_76966_.random(i, l);
			if (j == 0 && k == 1) {
				return i1;
			} else {
				ResourceKey<Biome> j1 = p_76967_.get(this.getParentX(x + 1), this.getParentY(z));
				ResourceKey<Biome> k1 = p_76966_.random(i, j1);
				if (j == 1 && k == 0) {
					return k1;
				} else {
					ResourceKey<Biome> l1 = p_76967_.get(this.getParentX(x + 1), this.getParentY(z + 1));
					return this.modeOrRandom(p_76966_, i, j1, l, l1);
				}
			}
		}
	}

	protected ResourceKey<Biome> modeOrRandom(BigContext<?> p_76960_, ResourceKey<Biome> p_76961_, ResourceKey<Biome> p_76962_, ResourceKey<Biome> p_76963_, ResourceKey<Biome> p_76964_) {
		if (p_76962_ == p_76963_ && p_76963_ == p_76964_) {
			return p_76962_;
		} else if (p_76961_ == p_76962_ && p_76961_ == p_76963_) {
			return p_76961_;
		} else if (p_76961_ == p_76962_ && p_76961_ == p_76964_) {
			return p_76961_;
		} else if (p_76961_ == p_76963_ && p_76961_ == p_76964_) {
			return p_76961_;
		} else if (p_76961_ == p_76962_ && p_76963_ != p_76964_) {
			return p_76961_;
		} else if (p_76961_ == p_76963_ && p_76962_ != p_76964_) {
			return p_76961_;
		} else if (p_76961_ == p_76964_ && p_76962_ != p_76963_) {
			return p_76961_;
		} else if (p_76962_ == p_76963_ && p_76961_ != p_76964_) {
			return p_76962_;
		} else if (p_76962_ == p_76964_ && p_76961_ != p_76963_) {
			return p_76962_;
		} else {
			return p_76963_ == p_76964_ && p_76961_ != p_76962_ ? p_76963_ : p_76960_.random(p_76961_, p_76962_, p_76963_, p_76964_);
		}
	}
}
