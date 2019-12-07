package twilightforest.item;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import twilightforest.compat.Baubles;
import twilightforest.compat.TFCompat;

import javax.annotation.Nullable;

public class ItemCharmBaubleable extends ItemTF {
    ItemCharmBaubleable(EnumRarity rarity) {
        super(rarity);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return TFCompat.BAUBLES.isActivated() ? new Baubles.BasicBaubleProvider() : null;
    }
}
