package twilightforest.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import twilightforest.TwilightForestMod;
import twilightforest.item.ItemTFFieryArmor;
import twilightforest.item.ItemTFYetiArmor;

public class TFEnchantment extends Enchantment
{

	protected TFEnchantment(Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot[] slots) {
		super(rarity, type, slots);
	}


	/**
	 * Add up the number of fiery armor pieces the player is wearing, multiplied by 5
	 */
	public static int getFieryAuraLevel(InventoryPlayer par0InventoryPlayer, DamageSource par1DamageSource)
	{
		int modifier = 0;

		for (int i = 0; i < par0InventoryPlayer.armorInventory.length; i++) {
			ItemStack armor = par0InventoryPlayer.armorInventory[i];

			if (armor != null && armor.getItem() instanceof ItemTFFieryArmor) {
				modifier += 5;
			}
		}

		return modifier;
	}

	/**
	 * Add up the number of yeti armor pieces the player is wearing, 0-4
	 */
	public static int getChillAuraLevel(InventoryPlayer par0InventoryPlayer, DamageSource par1DamageSource)
	{
		int modifier = 0;

		for (int i = 0; i < par0InventoryPlayer.armorInventory.length; i++) {
			ItemStack armor = par0InventoryPlayer.armorInventory[i];

			if (armor != null && armor.getItem() instanceof ItemTFYetiArmor) {
				modifier++;
			}
		}

		return modifier;
	}
}
