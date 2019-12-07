package twilightforest.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class EnchantmentTFFireReact extends Enchantment {
	// TODO implement
	public EnchantmentTFFireReact(Rarity rarity) {
		super(rarity, EnumEnchantmentType.ARMOR, new EquipmentSlotType[]{
				EquipmentSlotType.HEAD, EquipmentSlotType.CHEST,
				EquipmentSlotType.LEGS, EquipmentSlotType.FEET
		});
		this.setName("tfFireReact");
	}
}
