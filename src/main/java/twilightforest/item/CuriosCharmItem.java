package twilightforest.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;
import twilightforest.compat.CuriosCompat;
import twilightforest.compat.TFCompat;

public class CuriosCharmItem extends Item {

	public CuriosCharmItem(Properties props) {
		super(props);
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		if (ModList.get().isLoaded(TFCompat.CURIOS_ID)) {
			//the only reason we do this is because we get a NoClassDefFoundError if curios isnt installed since the class is loaded for some reason.
			//Since CuriosCompat is only loaded when the mod is available this works fine.
			return CuriosCompat.setupCuriosCapability(stack);
		}
		return super.initCapabilities(stack, nbt);
	}
}
