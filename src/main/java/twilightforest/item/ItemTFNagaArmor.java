package twilightforest.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import twilightforest.TwilightForestMod;
import twilightforest.client.ModelRegisterCallback;

public class ItemTFNagaArmor extends ItemTFArmor implements ModelRegisterCallback {
	protected ItemTFNagaArmor(ArmorMaterial materialIn, EquipmentSlotType equipmentSlotIn, EnumRarity rarity) {
		super(materialIn, equipmentSlotIn, rarity);
	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlotType slot, String layer) {
		if (slot == EquipmentSlotType.LEGS) {
			return TwilightForestMod.ARMOR_DIR + "naga_scale_2.png";
		} else {
			return TwilightForestMod.ARMOR_DIR + "naga_scale_1.png";
		}
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (isInCreativeTab(tab)) {
			ItemStack istack = new ItemStack(this);
			switch (this.armorType) {
				case CHEST:
					istack.addEnchantment(Enchantments.FIRE_PROTECTION, 3);
					break;
				case LEGS:
					istack.addEnchantment(Enchantments.PROTECTION, 3);
					break;
				default:
					break;
			}
			list.add(istack);
		}
	}
}
