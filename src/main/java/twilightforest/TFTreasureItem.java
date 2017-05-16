package twilightforest;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class TFTreasureItem {

	ItemStack itemStack;
	int rarity;
	
	int randomEnchantmentLevel = 0;
	
	public TFTreasureItem(Item item) {
		this(item, 1, 10);
	}
	
	public TFTreasureItem(Item item, int quantity) {
		this(item, quantity, 10);
	}
	
	public TFTreasureItem(Item item, int quantity, int rarity) {
		this.itemStack = new ItemStack(item, quantity);
		this.rarity = rarity;
	}
	
	public TFTreasureItem(Block block, int quantity, int rarity) {
		this.itemStack = new ItemStack(block, quantity);
		this.rarity = rarity;
	}
	
	public TFTreasureItem(ItemStack itemStack, int rarity) {
		this.itemStack = itemStack.copy();
		this.rarity = rarity;
	}
	
	
	/**
	 * Get a new ItemStack ready for use!
	 * 
	 * @param rand
	 * @return
	 */
	public ItemStack getItemStack(Random rand) {
		ItemStack result = this.itemStack.copy();
		result.stackSize = rand.nextInt(result.stackSize) + 1;
		
		// randomly enchant this item if needed
		if (result.isItemEnchantable() && randomEnchantmentLevel > 0)
		{
			EnchantmentHelper.addRandomEnchantment(rand, result, randomEnchantmentLevel, true);
		}
		
		return result;
	}
	
	/**
	 * Returns this items rarity value
	 * 
	 * @param total
	 * @return
	 */
	public int getRarity() {
		return rarity;
	}

	public int getRandomEnchantmentLevel() {
		return randomEnchantmentLevel;
	}

	public void setRandomEnchantmentLevel(int randomEnchantmentLevel) {
		this.randomEnchantmentLevel = randomEnchantmentLevel;
	}
}


