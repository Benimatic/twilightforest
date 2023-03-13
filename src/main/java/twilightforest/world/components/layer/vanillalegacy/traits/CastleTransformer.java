package twilightforest.world.components.layer.vanillalegacy.traits;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.world.components.layer.vanillalegacy.Area;
import twilightforest.world.components.layer.vanillalegacy.context.BigContext;
import twilightforest.world.components.layer.vanillalegacy.context.Context;

public interface CastleTransformer extends AreaTransformer1, DimensionOffset1Transformer {
	ResourceKey<Biome> apply(Context p_77059_, ResourceKey<Biome> p_77060_, ResourceKey<Biome> p_77061_, ResourceKey<Biome> p_77062_, ResourceKey<Biome> p_77063_, ResourceKey<Biome> p_77064_);

	@Override
	default ResourceKey<Biome> applyPixel(BigContext<?> context, Area area, int x, int z) {
		return this.apply(context, area.getBiome(this.getParentX(x + 1), this.getParentY(z)), area.getBiome(this.getParentX(x + 2), this.getParentY(z + 1)), area.getBiome(this.getParentX(x + 1), this.getParentY(z + 2)), area.getBiome(this.getParentX(x), this.getParentY(z + 1)), area.getBiome(this.getParentX(x + 1), this.getParentY(z + 1)));
	}
}
