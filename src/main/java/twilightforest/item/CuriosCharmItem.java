package twilightforest.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

public class CuriosCharmItem extends Item implements CurioItem {

	public CuriosCharmItem(Properties props) {
		super(props);
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		return this.setupCurio(stack, super.initCapabilities(stack, nbt));
	}
}