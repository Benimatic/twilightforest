package twilightforest.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import twilightforest.init.TFEnchantments;
import twilightforest.item.ChainBlockItem;

public class BlockStrengthEnchantment extends LootOnlyEnchantment {

	public BlockStrengthEnchantment(Rarity rarity) {
		super(rarity, TFEnchantments.BLOCK_AND_CHAIN, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
	}

	@Override
	public int getMinCost(int level) {
		return 5 + (level - 1) * 9;
	}

	@Override
	public int getMaxCost(int level) {
		return this.getMinCost(level) + 15;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		return stack.getItem() instanceof ChainBlockItem;
	}

	@Override
	protected boolean checkCompatibility(Enchantment other) {
		return super.checkCompatibility(other) && other != TFEnchantments.DESTRUCTION.get() && other != TFEnchantments.PRESERVATION.get();
	}
}
