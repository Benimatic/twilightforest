package twilightforest.world;

import net.minecraft.world.biome.provider.IBiomeProviderSettings;
import net.minecraft.world.storage.WorldInfo;

public class TFBiomeProviderSettings implements IBiomeProviderSettings {

	private long seed;

	public TFBiomeProviderSettings(WorldInfo info) {
		this.seed = info.getSeed();
	}

	public long getSeed() {
		return seed;
	}
}
