package twilightforest.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import twilightforest.TwilightForestMod;
import twilightforest.client.ModelRegisterCallback;

public class ItemTFIronwoodArmor extends ItemTFArmor implements ModelRegisterCallback {

	public ItemTFIronwoodArmor(ArmorMaterial armorMaterial, EntityEquipmentSlot armorType, EnumRarity rarity) {
		super(armorMaterial, armorType, rarity);
		this.setCreativeTab(TFItems.creativeTab);
	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, EntityEquipmentSlot slot, String layer) {
		if (slot == EntityEquipmentSlot.LEGS) {
			return TwilightForestMod.ARMOR_DIR + "ironwood_2.png";
		} else {
			return TwilightForestMod.ARMOR_DIR + "ironwood_1.png";
		}
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (isInCreativeTab(tab)) {
			ItemStack istack = new ItemStack(this);
			switch (this.armorType) {
				case HEAD:
					istack.addEnchantment(Enchantments.AQUA_AFFINITY, 1);
					break;
				case CHEST:
					istack.addEnchantment(Enchantments.PROTECTION, 1);
					break;
				case LEGS:
					istack.addEnchantment(Enchantments.PROTECTION, 1);
					break;
				case FEET:
					istack.addEnchantment(Enchantments.FEATHER_FALLING, 1);
					break;
				default:
					break;
			}
			list.add(istack);
		}
	}
}
