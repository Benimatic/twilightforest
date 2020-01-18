package twilightforest.item;

import net.minecraft.item.Rarity;

//TODO 1.14: Baubles is dead
public class ItemCharmBaubleable extends ItemTF {
    ItemCharmBaubleable(Rarity rarity, Properties props) {
        super(rarity, props);
    }

//    @Nullable
//    @Override
//    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
//        return TFCompat.BAUBLES.isActivated() ? new Baubles.BasicBaubleProvider() : null;
//    }
}
