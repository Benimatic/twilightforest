package twilightforest.capabilities.teleporter_cache;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.INBTSerializable;
import twilightforest.TwilightForestMod;

public interface TeleporterCacheCapability extends INBTSerializable<CompoundTag> {

	ResourceLocation ID = TwilightForestMod.prefix("teleporter_cache");

}
