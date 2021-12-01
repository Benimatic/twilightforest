package twilightforest.world.components.layer;

import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer1;
import net.minecraft.world.level.newbiome.layer.traits.DimensionOffset1Transformer;

/**
 * Works like ICastleTransformer, but has extra parameters for diagonal checks
 * TODO: Verify the logic
 */
public interface IThornsTransformer extends AreaTransformer1, DimensionOffset1Transformer {
	int apply(Context noise, int north, int west, int south, int east, int middle, int nw, int sw, int se, int ne);

	@Override
	default int applyPixel(BigContext<?> noise, Area area, int width, int depth) {
		return this.apply(noise,
				area.get(this.getParentX(width + 1), this.getParentY(depth)),
				area.get(this.getParentX(width + 2), this.getParentY(depth + 1)),
				area.get(this.getParentX(width + 1), this.getParentY(depth + 2)),
				area.get(this.getParentX(width), this.getParentY(depth + 1)),
				area.get(this.getParentX(width + 1), this.getParentY(depth + 1)),
				area.get(this.getParentX(width + 2), this.getParentY(depth)),
				area.get(this.getParentX(width + 2), this.getParentY(depth + 2)),
				area.get(this.getParentX(width), this.getParentY(depth + 2)),
				area.get(this.getParentX(width), this.getParentY(depth))
		);
	}
}
