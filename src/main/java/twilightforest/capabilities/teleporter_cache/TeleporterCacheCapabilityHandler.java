package twilightforest.capabilities.teleporter_cache;

import net.minecraft.nbt.CompoundTag;
import twilightforest.world.TFTeleporter;

public class TeleporterCacheCapabilityHandler implements TeleporterCacheCapability {

	@Override
	public CompoundTag serializeNBT() {
		return TFTeleporter.saveLinks();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		TFTeleporter.loadLinks(nbt);
	}
}
