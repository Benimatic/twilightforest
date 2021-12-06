package twilightforest.world.components.layer.vanillalegacy.traits;

import twilightforest.world.components.layer.vanillalegacy.area.Area;
import twilightforest.world.components.layer.vanillalegacy.context.BigContext;
import twilightforest.world.components.layer.vanillalegacy.context.Context;

public interface CastleTransformer  extends AreaTransformer1, DimensionOffset1Transformer {
	int apply(Context p_77059_, int p_77060_, int p_77061_, int p_77062_, int p_77063_, int p_77064_);

	default int applyPixel(BigContext<?> p_77055_, Area p_77056_, int p_77057_, int p_77058_) {
		return this.apply(p_77055_, p_77056_.get(this.getParentX(p_77057_ + 1), this.getParentY(p_77058_)), p_77056_.get(this.getParentX(p_77057_ + 2), this.getParentY(p_77058_ + 1)), p_77056_.get(this.getParentX(p_77057_ + 1), this.getParentY(p_77058_ + 2)), p_77056_.get(this.getParentX(p_77057_), this.getParentY(p_77058_ + 1)), p_77056_.get(this.getParentX(p_77057_ + 1), this.getParentY(p_77058_ + 1)));
	}
}
