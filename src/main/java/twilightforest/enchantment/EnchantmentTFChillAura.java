package twilightforest.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class EnchantmentTFChillAura extends Enchantment {
	// TODO implement
	public EnchantmentTFChillAura(Rarity rarity) {
		super(rarity, EnumEnchantmentType.ARMOR, new EquipmentSlotType[]{
				EquipmentSlotType.HEAD, EquipmentSlotType.CHEST,
				EquipmentSlotType.LEGS, EquipmentSlotType.FEET
		});
		this.setName("tfChillAura");
	}
}
