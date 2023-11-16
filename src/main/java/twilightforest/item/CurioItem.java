package twilightforest.item;

import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

public interface CurioItem {

	@Nullable
	default ICapabilityProvider setupCurio(ItemStack stack, @Nullable ICapabilityProvider provider) {
		if (ModList.get().isLoaded("curios")) {
			//return CuriosCompat.setupCuriosCapability(stack);
		}
		return provider;
	}
}
