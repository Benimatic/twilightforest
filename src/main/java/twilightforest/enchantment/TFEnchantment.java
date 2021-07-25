package twilightforest.enchantment;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.damagesource.DamageSource;
import twilightforest.item.FieryArmorItem;
import twilightforest.item.YetiArmorItem;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class TFEnchantment extends Enchantment {

	protected TFEnchantment(Rarity rarity, EnchantmentCategory type, EquipmentSlot[] slots) {
		super(rarity, type, slots);
	}

	/**
	 * Add up the number of fiery armor pieces the player is wearing, multiplied by 5
	 */
	public static int getFieryAuraLevel(Inventory inventory, DamageSource source) {
		int modifier = 0;

		for (ItemStack armor : inventory.armor) {
			if (!armor.isEmpty() && armor.getItem() instanceof FieryArmorItem) {
				modifier += 5;
			}
		}

		return modifier;
	}

	/**
	 * Add up the number of yeti armor pieces the player is wearing, 0-4
	 */
	public static int getChillAuraLevel(Inventory inventory, DamageSource source) {
		int modifier = 0;

		for (ItemStack armor : inventory.armor) {
			if (!armor.isEmpty() && armor.getItem() instanceof YetiArmorItem) {
				modifier++;
			}
		}

		return modifier;
	}
}
