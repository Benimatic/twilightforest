package twilightforest.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import twilightforest.item.ChainBlockItem;

public class DestructionEnchantment extends LootOnlyEnchantment {

	public DestructionEnchantment(Rarity rarity) {
		super(rarity, TFEnchantments.BLOCK_AND_CHAIN, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
	}

	@Override
	public int getMinCost(int pEnchantmentLevel) {
		return 5 + (pEnchantmentLevel - 1) * 9;
	}

	@Override
	public int getMaxCost(int pEnchantmentLevel) {
		return this.getMinCost(pEnchantmentLevel) + 15;
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public boolean canEnchant(ItemStack pStack) {
		return pStack.getItem() instanceof ChainBlockItem;
	}

	@Override
	protected boolean checkCompatibility(Enchantment other) {
		return super.checkCompatibility(other) && other != TFEnchantments.BLOCK_STRENGTH.get() && other != TFEnchantments.PRESERVATION.get();
	}

	@Override
	public float getDamageBonus(int pLevel, MobType pType) {
		return -pLevel * 2.0F;
	}
}
