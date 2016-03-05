package twilightforest;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


/**
 * This is not much more than a list of TFTreasureEntries.
 * 
 * @author Ben
 *
 */
public class TFTreasureTable {
	
	private static final int DEFAULT_RARITY = 10;
	protected ArrayList<TFTreasureItem> list;
	
	public TFTreasureTable() {
		this.list = new ArrayList<TFTreasureItem>();
	}
	

	/**
	 * Adds the item onto this list to be generated at normal rarity (10), in the specified quantity
	 * 
	 * @param item
	 * @param quantity
	 */
	public void add(Item item, int quantity) {
		this.add(item, quantity, DEFAULT_RARITY);
	}
	
	/**
	 * Adds the item onto the list, with the values specified
	 * 
	 * @param item
	 * @param quantity
	 * @param rarity
	 */
	public void add(Item item, int quantity, int rarity) {
		list.add(new TFTreasureItem(item, quantity, rarity));
	}
	
	
	/**
	 * Adds the ItemStack onto this list to be generated at normal rarity (10), in the quantity the itemstack contains
	 * 
	 * @param itemstack
	 */
	public void add(ItemStack itemstack) {
		list.add(new TFTreasureItem(itemstack, DEFAULT_RARITY));
	}
	
	/**
	 * Adds the item at the specified enchantment level
	 */
	public void addEnchanted(ItemStack itemstack, Enchantment ench1, int enchLevel1) {
		itemstack.addEnchantment(ench1, enchLevel1);
		list.add(new TFTreasureItem(itemstack, DEFAULT_RARITY));
	}
	
	/**
	 * Adds an enchanted book at the specified enchantment level
	 */
	public void addEnchantedBook(Enchantment ench1, int enchLevel1) {
		ItemStack itemstack = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(ench1, enchLevel1));
		list.add(new TFTreasureItem(itemstack, DEFAULT_RARITY));
	}
	
	/**
	 * Adds the item at the specified enchantment level, 2 enchantments
	 */
	public void addEnchanted(ItemStack itemstack, Enchantment ench1, int enchLevel1, Enchantment ench2, int enchLevel2) {
		itemstack.addEnchantment(ench1, enchLevel1);
		itemstack.addEnchantment(ench2, enchLevel2);
		list.add(new TFTreasureItem(itemstack, DEFAULT_RARITY));
	}
	
	/**
	 * Adds the item at the specified enchantment level, 3 enchantments
	 */
	public void addEnchanted(ItemStack itemstack, Enchantment ench1, int enchLevel1, Enchantment ench2, int enchLevel2, Enchantment ench3, int enchLevel3) {
		itemstack.addEnchantment(ench1, enchLevel1);
		itemstack.addEnchantment(ench2, enchLevel2);
		itemstack.addEnchantment(ench3, enchLevel3);
		list.add(new TFTreasureItem(itemstack, DEFAULT_RARITY));
	}
	
	/**
	 * Adds the item with a random enchantment level
	 */
	public void addRandomEnchanted(ItemStack itemstack, int randomLevel)
	{
		TFTreasureItem treasure = new TFTreasureItem(itemstack, DEFAULT_RARITY);
		treasure.setRandomEnchantmentLevel(randomLevel);
		list.add(treasure);
	}
	
	/**
	 * Adds the item with a random enchantment level
	 */
	public void addRandomEnchanted(Item item, int randomLevel)
	{
		TFTreasureItem treasure = new TFTreasureItem(item, 1, DEFAULT_RARITY);
		treasure.setRandomEnchantmentLevel(randomLevel);
		list.add(treasure);
	}
	
	/**
	 * Adds the block onto this list to be generated at normal rarity (10), in the specified quantity
	 * 
	 * @param block
	 * @param quantity
	 */
	public void add(Block block, int quantity) {
		this.add(block, quantity, DEFAULT_RARITY);
	}
	

	/**
	 * Adds the block onto the list, with the values specified
	 * 
	 * @param block
	 * @param quantity
	 * @param rarity
	 */
	public void add(Block block, int quantity, int rarity) {
		list.add(new TFTreasureItem(block, quantity, rarity));
	}
	
	
	/**
	 * What is the total of all the rarity entries in our table?
	 * 
	 * @return
	 */
	protected int total() {
		int value = 0;
		
		for (TFTreasureItem item : list) {
			value += item.getRarity();
		}
		
		return value;
	}
	
	public ItemStack getRandomItem(Random rand) {
		
		int value = rand.nextInt(total());
		
		for (TFTreasureItem item : list) {
			if (item.getRarity() > value) {
				return item.getItemStack(rand);
			}
			else
			{
				value -= item.getRarity();
			}
		}
		
		// we should not actually get here
		return null;
	}
	
	/**
	 * Is this table empty 
	 */
	public boolean isEmpty()
	{
		return list.isEmpty();
	}
	
	/**
	 * Clear this list
	 */
	public void clear()
	{
		list.clear();
	}
}
