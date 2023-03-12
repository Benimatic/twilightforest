package twilightforest.world.components.layer.vanillalegacy.traits;

public interface DimensionOffset1Transformer extends DimensionTransformer {
	default int getParentX(int x) {
		return x - 1;
	}

	default int getParentY(int z) {
		return z - 1;
	}
}
