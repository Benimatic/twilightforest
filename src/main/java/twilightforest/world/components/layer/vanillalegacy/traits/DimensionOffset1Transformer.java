package twilightforest.world.components.layer.vanillalegacy.traits;

public interface DimensionOffset1Transformer extends DimensionTransformer {
	default int getParentX(int p_77070_) {
		return p_77070_ - 1;
	}

	default int getParentY(int p_77072_) {
		return p_77072_ - 1;
	}
}
