package twilightforest.world.components.layer.vanillalegacy.traits;

public interface DimensionOffset0Transformer extends DimensionTransformer {
	default int getParentX(int x) {
		return x;
	}

	default int getParentY(int z) {
		return z;
	}
}
