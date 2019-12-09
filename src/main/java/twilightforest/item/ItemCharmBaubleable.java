package twilightforest.item;

import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import twilightforest.compat.Baubles;
import twilightforest.compat.TFCompat;

import javax.annotation.Nullable;

//TODO 1.14: Baubles is dead
public class ItemCharmBaubleable extends ItemTF {
    ItemCharmBaubleable(Rarity rarity, Properties props) {
        super(rarity, props);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return TFCompat.BAUBLES.isActivated() ? new Baubles.BasicBaubleProvider() : null;
    }
}
