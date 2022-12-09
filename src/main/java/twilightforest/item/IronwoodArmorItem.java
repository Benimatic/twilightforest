package twilightforest.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import twilightforest.TwilightForestMod;

public class IronwoodArmorItem extends ArmorItem {

	public IronwoodArmorItem(ArmorMaterial armorMaterial, EquipmentSlot armorType, Properties properties) {
		super(armorMaterial, armorType, properties);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String layer) {
		if (slot == EquipmentSlot.LEGS) {
			return TwilightForestMod.ARMOR_DIR + "ironwood_2.png";
		} else {
			return TwilightForestMod.ARMOR_DIR + "ironwood_1.png";
		}
	}
}