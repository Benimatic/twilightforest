package twilightforest.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;

import static baubles.api.BaubleType.TRINKET;

@Optional.InterfaceList({
        @Optional.Interface(modid = "baubles", iface = "baubles.api.BaubleType"),
        @Optional.Interface(modid = "baubles", iface = "baubles.api.IBauble")
})
public class ItemCharmBaubleable extends ItemTF implements IBauble {
    @Optional.Method(modid = "baubles")
    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return TRINKET;
    }

    @Optional.Method(modid = "baubles")
    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
    }

    @Optional.Method(modid = "baubles")
    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
    }

    @Optional.Method(modid = "baubles")
    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
    }

    @Optional.Method(modid = "baubles")
    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Optional.Method(modid = "baubles")
    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Optional.Method(modid = "baubles")
    @Override
    public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
        return false;
    }
}
