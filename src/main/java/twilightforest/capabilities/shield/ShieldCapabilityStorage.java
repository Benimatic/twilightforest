package twilightforest.capabilities.shield;

import net.minecraft.nbt.Tag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class ShieldCapabilityStorage implements Capability.IStorage<IShieldCapability> {

	@Override
	public CompoundTag writeNBT(Capability<IShieldCapability> capability, IShieldCapability instance, Direction side) {
		CompoundTag tag = new CompoundTag();
		tag.putInt("tempshields", instance.temporaryShieldsLeft());
		tag.putInt("permshields", instance.permanentShieldsLeft());
		return tag;
	}

	@Override
	public void readNBT(Capability<IShieldCapability> capability, IShieldCapability instance, Direction side, Tag nbt) {
		if (nbt instanceof CompoundTag && instance instanceof ShieldCapabilityHandler) {
			CompoundTag tag = (CompoundTag) nbt;
			((ShieldCapabilityHandler) instance).initShields(
					tag.getInt("tempshields"),
					tag.getInt("permshields")
			);
		}
	}
}
