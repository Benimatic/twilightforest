package twilightforest.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;

public class CuriosCharmItem extends Item {

	public CuriosCharmItem(Properties props) {
		super(props);
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		if (ModList.get().isLoaded("curios")) {
			//return CuriosCompat.setupCuriosCapability(stack);
		}
		return super.initCapabilities(stack, nbt);
	}
}