package twilightforest;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import twilightforest.block.TFBlocks;
import twilightforest.item.TFItems;


public class TFTreasure {

	int type;
	
	Random treasureRNG;

	protected TFTreasureTable useless; // one quarter of common treasures are
										// one of these useless items
	protected TFTreasureTable common;
	protected TFTreasureTable uncommon;
	protected TFTreasureTable rare;
	protected TFTreasureTable ultrarare; // one quarter of rare treasures are
											// one of these ultrarare items. The
											// list must be at least as large as
											// the large list for this to make
											// sense.

	public static TFTreasure hill1 = new TFTreasure(1);
	public static TFTreasure hill2 = new TFTreasure(2);
	public static TFTreasure hill3 = new TFTreasure(3);
	public static TFTreasure hedgemaze = new TFTreasure(4);
	public static TFTreasure labyrinth_room = new TFTreasure(5);
	public static TFTreasure labyrinth_deadend = new TFTreasure(6);
	public static TFTreasure tower_room = new TFTreasure(7);
	public static TFTreasure tower_library = new TFTreasure(8);
	public static TFTreasure basement = new TFTreasure(9);
	public static TFTreasure labyrinth_vault = new TFTreasure(10);
	public static TFTreasure darktower_cache = new TFTreasure(11);
	public static TFTreasure darktower_key = new TFTreasure(12);
	public static TFTreasure darktower_boss = new TFTreasure(13);
	public static TFTreasure tree_cache = new TFTreasure(14);
	public static TFTreasure stronghold_cache = new TFTreasure(15);
	public static TFTreasure stronghold_room = new TFTreasure(16);
	public static TFTreasure stronghold_boss = new TFTreasure(17);
	public static TFTreasure aurora_cache = new TFTreasure(18);
	public static TFTreasure aurora_room = new TFTreasure(19);
	public static TFTreasure aurora_boss = new TFTreasure(20);
	public static TFTreasure troll_garden = new TFTreasure(21);
	public static TFTreasure troll_vault = new TFTreasure(22);

	
	/**
	 * Construct a new treasure of type i
	 * 
	 * @param i
	 */
	public TFTreasure(int i) {
		this.type = i;

		useless = new TFTreasureTable();
		common = new TFTreasureTable();
		uncommon = new TFTreasureTable();
		rare = new TFTreasureTable();
		ultrarare = new TFTreasureTable();
		
		treasureRNG = new Random();

		// TODO: separate these into plain ol' different classes
		fill(i);
	}

	/**
	 * Generates a treasure chest in the world at the specified coordinates.
	 */
	public boolean generate(World world, Random rand, int cx, int cy, int cz) {

		return this.generate(world, rand, cx, cy, cz, Blocks.CHEST);
	}

	/**
	 * Generates a treasure chest in the world at the specified coordinates.
	 */
	public boolean generate(World world, Random rand, int cx, int cy, int cz, Block chestBlock) {
		boolean flag = true;
		
		treasureRNG.setSeed(world.getSeed() * cx + cy ^ cz);

		// make a chest
		world.setBlock(cx, cy, cz, chestBlock, 0, 2);

		// add 4 common items
		for (int i = 0; i < 4; i++) {
			flag &= addItemToChest(world, treasureRNG, cx, cy, cz, getCommonItem(treasureRNG));
		}

		// add 2 uncommon items
		for (int i = 0; i < 2; i++) {
			flag &= addItemToChest(world, treasureRNG, cx, cy, cz,
					getUncommonItem(treasureRNG));
		}

		// add 1 rare item
		for (int i = 0; i < 1; i++) {
			flag &= addItemToChest(world, treasureRNG, cx, cy, cz, getRareItem(treasureRNG));
		}

		return flag;
	}

	/**
	 * Gets a common item. If the useless list is not empty, 
	 *   there is a 1/4 chance we get a useless item instead.
	 */
	public ItemStack getCommonItem(Random rand) {
		if (!useless.isEmpty() && rand.nextInt(4) == 0) {
			return useless.getRandomItem(rand);
		} else {
			return common.getRandomItem(rand);
		}
	}

	/**
	 * Gets an uncommon item.
	 */
	public ItemStack getUncommonItem(Random rand) {
		return uncommon.getRandomItem(rand);
	}

	/**
	 * Gets a rare item. If there are items on the ultrarare list, 
	 *   there is a 1/4 chance we get a ultrarare item instead.
	 */
	public ItemStack getRareItem(Random rand) 
	{
		if (!ultrarare.isEmpty() && rand.nextInt(4) == 0) 
		{
			return ultrarare.getRandomItem(rand);
		}
		else 
		{
			return rare.getRandomItem(rand);
		}
	}

	/**
	 * Used internally to add items into chests
	 */
	protected boolean addItemToChest(World world, Random rand, int cx, int cy,
			int cz, ItemStack itemStack) {

		TileEntityChest chest = (TileEntityChest) world.getTileEntity(cx, cy, cz);

		// make sure the chest is not full
		if (chest != null) {
			int slot = findRandomInventorySlot(chest, rand);

			if (slot != -1) {
				chest.setInventorySlotContents(slot, itemStack);
				return true;
			}

		}
		return false;
	}

	/**
	 * Finds an open slot in the chest, or returns -1 if the chest is full.
	 */
	protected int findRandomInventorySlot(TileEntityChest chest, Random rand) {
		// TODO: this is lazy

		// try 100 times to find an empty slot
		for (int i = 0; i < 100; i++) {
			int slot = rand.nextInt(chest.getSizeInventory());
			if (chest.getStackInSlot(slot) == null) {
				return slot;
			}
		}

		// didn't find one
		return -1;
	}

	/**
	 * Fills the table with items for the specified type. This should be
	 * refactored into seperate functions someday.
	 */
	protected void fill(int i) {
		useless.add(Blocks.RED_FLOWER, 4);
		useless.add(Blocks.YELLOW_FLOWER, 4);
		useless.add(Items.FEATHER, 3);
		useless.add(Items.WHEAT_SEEDS, 2);
		useless.add(Items.FLINT, 2);
		useless.add(Blocks.CACTUS, 2);
		useless.add(Items.REEDS, 4);
		useless.add(Blocks.SAND, 4);
		useless.add(Items.FLOWER_POT, 1);
		useless.add(new ItemStack(Items.DYE, 1, 0));

		if (i == 1) {
			// hill type 1 chest
			common.add(Items.IRON_INGOT, 4);
			common.add(Items.WHEAT, 4);
			common.add(Items.STRING, 4);
			common.add(Items.BUCKET, 1);

			uncommon.add(Items.BREAD, 1);
			uncommon.add(TFItems.oreMagnet, 1);
			uncommon.add(Items.GUNPOWDER, 4);
			uncommon.add(Items.ARROW, 12);
			uncommon.add(Blocks.TORCH, 12);

			rare.add(Items.GOLD_INGOT, 3);
			rare.add(Items.IRON_PICKAXE, 1);
			rare.add(TFItems.liveRoot, 3);

			ultrarare.add(TFItems.transformPowder, 12);
			ultrarare.add(Items.DIAMOND, 1);
			ultrarare.add(TFItems.steeleafIngot, 3);
		}
		
		if (i == 2) {
			// hill type 2 chest
			common.add(Items.IRON_INGOT, 4);
			common.add(Items.CARROT, 4);
			common.add(Blocks.LADDER, 6);
			common.add(Items.BUCKET, 1);

			uncommon.add(Items.BAKED_POTATO, 2);
			uncommon.add(TFItems.oreMagnet, 1);
			uncommon.add(TFItems.ironwoodIngot, 4);
			uncommon.add(Items.ARROW, 12);
			uncommon.add(Blocks.TORCH, 12);

			rare.add(TFItems.nagaScale, 1);
			rare.add(TFBlocks.uncraftingTable, 1);
			rare.add(TFItems.transformPowder, 12);

			ultrarare.add(TFItems.peacockFan, 1);
			ultrarare.add(Items.EMERALD, 6);
			ultrarare.add(Items.DIAMOND, 1);
			ultrarare.add(TFItems.charmOfLife1, 1);
		}
		
		if (i == 3) {
			// hill type 3 chest
			common.add(Items.GOLD_NUGGET, 9);
			common.add(Items.POTATO, 4);
			common.add(Items.FISH, 4);
			common.add(TFItems.torchberries, 5);

			uncommon.add(Items.PUMPKIN_PIE, 1);
			uncommon.add(TFItems.oreMagnet, 1);
			uncommon.add(Items.GUNPOWDER, 4);
			uncommon.add(Items.ARROW, 12);
			uncommon.add(Blocks.TORCH, 12);
			uncommon.add(TFItems.steeleafIngot, 4);

			rare.add(TFItems.nagaScale, 1);
			rare.addEnchanted(new ItemStack(TFItems.ironwoodPick, 1), Enchantments.EFFICIENCY, 1, Enchantments.FORTUNE, 1);
			rare.add(TFItems.transformPowder, 12);

			ultrarare.add(TFItems.moonwormQueen, 1);
			ultrarare.add(TFBlocks.sapling, 1, 4);
			ultrarare.add(Items.DIAMOND, 2);
			ultrarare.add(TFItems.charmOfLife1, 1);
			ultrarare.add(TFItems.charmOfKeeping1, 1);
		}

		if (i == 4) {
			// hedge maze chest
			common.add(Blocks.PLANKS, 4);
			common.add(Blocks.BROWN_MUSHROOM, 4);
			common.add(Blocks.RED_MUSHROOM, 4);
			common.add(Items.WHEAT, 4);
			common.add(Items.STRING, 4);
			common.add(Items.STICK, 6);

			uncommon.add(Items.MELON, 4);
			uncommon.add(Items.MELON_SEEDS, 4);
			uncommon.add(Items.PUMPKIN_SEEDS, 4);
			uncommon.add(Items.ARROW, 12);
			uncommon.add(TFBlocks.firefly, 4);

			rare.add(Blocks.WEB, 3);
			rare.add(Items.SHEARS, 1);
			rare.add(Items.SADDLE, 1);
			rare.add(Items.BOW, 1);
			rare.add(Items.APPLE, 2);

			ultrarare.add(Items.DIAMOND_HOE, 1);
			ultrarare.add(Items.DIAMOND, 1);
			ultrarare.add(Items.MUSHROOM_STEW, 1);
			ultrarare.add(Items.GOLDEN_APPLE, 1);
		}

		if (i == 5) {
			// labyrinth room chest
			useless.clear();
			
			common.add(Items.IRON_INGOT, 4);
			common.add(TFItems.mazeWafer, 12);
			common.add(Items.GUNPOWDER, 4);
			common.add(TFItems.ironwoodIngot, 4);
			common.add(TFBlocks.firefly, 5);
			common.add(Items.MILK_BUCKET, 1);

			uncommon.add(TFItems.steeleafIngot, 6);
			uncommon.add(TFItems.steeleafLegs, 1);
			uncommon.add(TFItems.steeleafPlate, 1);
			uncommon.add(TFItems.steeleafHelm, 1);
			uncommon.add(TFItems.steeleafBoots, 1);
			uncommon.add(TFItems.steeleafPick, 1);
			uncommon.add(TFItems.ironwoodPlate, 1);
			uncommon.add(TFItems.ironwoodSword, 1);
			uncommon.add(TFItems.charmOfKeeping1, 1);
			
			rare.add(TFItems.mazeMapFocus, 1);
			rare.add(Blocks.TNT, 3);
			rare.add(new ItemStack(Items.POTIONITEM, 1, 16373)); // health ii
		}

		if (i == 6) {
			// labyrinth dead end chest
			common.add(Items.STICK, 12);
			common.add(Items.COAL, 12);
			common.add(Items.ARROW, 12);
			common.add(TFItems.mazeWafer, 9);
			common.add(Items.PAPER, 12);
			common.add(Items.LEATHER, 4);
			common.add(Items.MUSHROOM_STEW, 1);

			uncommon.add(Items.MILK_BUCKET, 1);
			uncommon.add(Items.PAPER, 5);
			uncommon.add(Items.IRON_INGOT, 6);
			uncommon.add(TFItems.ironwoodIngot, 8);
			uncommon.add(TFBlocks.firefly, 5);
			uncommon.add(TFItems.charmOfKeeping1, 1);

			rare.add(TFItems.steeleafIngot, 8);
			rare.add(Items.GOLDEN_APPLE, 1);
			rare.add(Items.BLAZE_ROD, 2);
		}
		
		if (i == 10) {
			// labyrinth vault chest
			useless.clear();
			
			common.add(Items.IRON_INGOT, 9);
			common.add(Items.EMERALD, 5);
			common.add(TFItems.mazeWafer, 12);
			common.add(TFItems.ironwoodIngot, 9);
			common.add(new ItemStack(Items.POTIONITEM, 1, 16369)); // regen ii
			common.add(new ItemStack(Items.POTIONITEM, 1, 16373)); // health ii
			common.add(new ItemStack(Items.POTIONITEM, 1, 16370)); // extended swiftness ii

			uncommon.addEnchanted(new ItemStack(Items.BOW), Enchantments.INFINITY, 1, Enchantments.PUNCH, 2);
			uncommon.addEnchanted(new ItemStack(Items.BOW), Enchantments.POWER, 3, Enchantments.FLAME, 1);
			uncommon.addEnchanted(new ItemStack(TFItems.steeleafShovel), Enchantments.EFFICIENCY, 4, Enchantments.UNBREAKING, 2);
			uncommon.addEnchanted(new ItemStack(TFItems.steeleafAxe), Enchantments.EFFICIENCY, 5);
			uncommon.add(TFItems.steeleafIngot, 12);
			uncommon.addEnchanted(new ItemStack(TFItems.steeleafPlate), Enchantments.PROTECTION, 3);
			uncommon.addEnchanted(new ItemStack(TFItems.steeleafLegs), Enchantments.FIREPROTECTION, 4);
			uncommon.addEnchanted(new ItemStack(TFItems.steeleafBoots), Enchantments.PROTECTION, 2);
			uncommon.addEnchanted(new ItemStack(TFItems.steeleafHelm), Enchantments.RESPIRATION, 3);
			
			rare.add(Blocks.EMERALD_BLOCK, 1);
			rare.add(Blocks.ENDER_CHEST, 1);
			rare.addEnchanted(new ItemStack(TFItems.steeleafPick), Enchantments.EFFICIENCY, 4, Enchantments.SILKTOUCH, 1);
			rare.addEnchanted(new ItemStack(TFItems.steeleafSword), Enchantments.SHARPNESS, 4, Enchantments.KNOCKBACK, 2);
			rare.addEnchanted(new ItemStack(TFItems.steeleafSword), Enchantments.BANEOFARTHROPODS, 5, Enchantments.FIREASPECT, 2);
			rare.addEnchanted(new ItemStack(TFItems.mazebreakerPick), Enchantments.EFFICIENCY, 4, Enchantments.UNBREAKING, 3, Enchantments.FORTUNE, 2);
		}

		if (i == 7) {
			// wizards tower treasure room chest
			common.add(Items.GLASS_BOTTLE, 6);
			common.add(new ItemStack(Items.POTIONITEM, 1, 0));
			common.add(Items.SUGAR, 5);
			common.add(Items.SPIDER_EYE, 3);
			common.add(Items.GHAST_TEAR, 1);
			common.add(Items.MAGMA_CREAM, 2);
			common.add(Items.FERMENTED_SPIDER_EYE, 1);
			common.add(Items.SPECKLED_MELON, 2);
			common.add(Items.BLAZE_POWDER, 3);
			common.add(Items.PAPER, 6);

			uncommon.addRandomEnchanted(Items.GOLDEN_SWORD, 10);
			uncommon.addRandomEnchanted(Items.GOLDEN_BOOTS, 7);
			uncommon.add(new ItemStack(Items.POTIONITEM, 1, 16274)); // swiftness
			uncommon.add(new ItemStack(Items.POTIONITEM, 1, 16341)); // healing
			uncommon.add(new ItemStack(Items.POTIONITEM, 1, 16307)); // fire resist
			uncommon.add(new ItemStack(Items.POTIONITEM, 1, 16348)); // harm
			
			rare.addRandomEnchanted(Items.GOLDEN_HELMET, 18);
			rare.add(new ItemStack(Items.POTIONITEM, 1, 16306)); // swiftness ii
			rare.add(new ItemStack(Items.POTIONITEM, 1, 16305)); // regen ii
			rare.add(new ItemStack(Items.POTIONITEM, 1, 32725)); // splash health
			rare.add(new ItemStack(Items.POTIONITEM, 1, 32764)); // harming ii splash
			rare.add(TFItems.transformPowder, 12);
			rare.add(TFItems.charmOfLife1, 1);
			rare.add(TFItems.charmOfKeeping1, 1);

			ultrarare.addRandomEnchanted(Items.GOLDEN_AXE, 20);
			ultrarare.add(Items.ENDER_PEARL, 1);
			ultrarare.add(Blocks.OBSIDIAN, 4);
			ultrarare.add(Items.DIAMOND, 1);
			ultrarare.add(TFItems.moonwormQueen, 1);
			ultrarare.add(TFItems.peacockFan, 1);
		}

		if (i == 8) {
			// wizards tower library chest
			common.add(Items.GLASS_BOTTLE, 6);
			common.add(new ItemStack(Items.POTIONITEM, 1, 0));
			common.add(Blocks.LADDER, 6);
			common.add(Items.PAPER, 6);
			common.add(Items.BONE, 6);
			common.add(Items.GOLD_NUGGET, 6);
			common.add(Items.CLAY_BALL, 12);

			uncommon.addRandomEnchanted(Items.IRON_LEGGINGS, 5);
			uncommon.add(Items.FIRE_CHARGE, 3);
			uncommon.add(Items.BOOK, 5);
			uncommon.add(Items.MAP, 1);
			uncommon.add(new ItemStack(Items.POTIONITEM, 1, 16));
			uncommon.add(new ItemStack(Items.POTIONITEM, 1, 16276)); // poison
			uncommon.add(new ItemStack(Items.POTIONITEM, 1, 16312)); // weakness
			
			rare.addRandomEnchanted(Items.BOW, 5);
			rare.addRandomEnchanted(Items.STONE_SWORD, 10);
			rare.addRandomEnchanted(Items.WOODEN_SWORD, 15);
			rare.add(new ItemStack(Items.POTIONITEM, 1, 32696)); // splash weakness
			rare.add(new ItemStack(Items.POTIONITEM, 1, 16369)); // regen ii
			rare.add(new ItemStack(Items.POTIONITEM, 1, 16373)); // health ii
			rare.add(new ItemStack(Items.POTIONITEM, 1, 16370)); // extended swiftness ii
			rare.add(TFItems.transformPowder, 12);
			rare.add(TFItems.charmOfKeeping1, 1);
			
			ultrarare.addRandomEnchanted(Items.GOLDEN_PICKAXE, 10);
			ultrarare.addRandomEnchanted(Items.IRON_SWORD, 20);
			ultrarare.addRandomEnchanted(Items.BOW, 30);
			ultrarare.add(Blocks.BOOKSHELF, 5);
			ultrarare.add(Items.ENDER_PEARL, 2);
			ultrarare.add(Items.EXPERIENCE_BOTTLE, 6);
		}
		
		if (i == 9) {
			// basement treasure cache, mostly full of food!
			common.add(new ItemStack(Items.POTIONITEM, 1, 0));
			common.add(Items.ROTTEN_FLESH, 6);
			common.add(Items.POISONOUS_POTATO, 2);
			common.add(Items.WHEAT, 6);
			common.add(Items.POTATO, 6);
			common.add(Items.CARROT, 6);
			common.add(Items.MELON, 6);
			common.add(Items.WATER_BUCKET, 1);
			common.add(Blocks.TORCH, 12);
			common.add(Items.MUSHROOM_STEW, 1);
			common.add(Items.MILK_BUCKET, 1);
			common.add(Items.MELON_SEEDS, 5);
			
			uncommon.add(Items.BREAD, 8);
			uncommon.add(Items.COOKED_BEEF, 6);
			uncommon.add(Items.COOKED_PORKCHOP, 8);
			uncommon.add(Items.BAKED_POTATO, 8);
			uncommon.add(Items.COOKED_CHICKEN, 10);
			uncommon.add(Items.COOKED_FISHED, 8);
			
			rare.add(Items.SPECKLED_MELON, 12);
			rare.add(Items.APPLE, 12);
			rare.add(Items.MAP, 1);
			rare.add(TFItems.charmOfKeeping1, 1);
			
			ultrarare.add(Items.GOLDEN_APPLE, 2);
			ultrarare.add(Items.GOLDEN_CARROT, 2);
			ultrarare.add(Items.CAKE, 1);
			ultrarare.add(Items.BOAT, 1);
			ultrarare.add(new ItemStack(TFBlocks.sapling, 1, 4));
		}

		if (i == 11) {
			// dark tower regular treasure cache, contains supplies
			common.add(Items.STICK, 12);
			common.add(new ItemStack(Items.COAL, 12, 1));
			common.add(Items.ARROW, 12);
			common.add(TFItems.experiment115, 9);
			common.add(new ItemStack(Blocks.WOOL, 1, 14));
			common.add(Items.REDSTONE, 6);

			uncommon.add(Blocks.REDSTONE_LAMP, 3);
			uncommon.add(Items.IRON_INGOT, 6);
			uncommon.add(TFItems.ironwoodIngot, 8);
			uncommon.add(TFBlocks.firefly, 5);
			uncommon.add(TFItems.charmOfKeeping1, 1);

			rare.add(TFItems.steeleafIngot, 8);
			rare.add(Items.DIAMOND, 2);

		}

		if (i == 12) {
			// dark tower key treasure chest, contains higher quality treasure
			// key will be added seperately
			useless.clear();
			
			common.add(Items.IRON_INGOT, 4);
			common.add(TFItems.experiment115, 12);
			common.add(Items.GUNPOWDER, 4);
			common.add(TFItems.ironwoodIngot, 4);
			common.add(TFBlocks.firefly, 5);
			common.add(Items.REDSTONE, 12);
			common.add(Items.GLOWSTONE_DUST, 12);

			uncommon.add(TFItems.steeleafIngot, 6);
			uncommon.add(TFItems.steeleafLegs, 1);
			uncommon.add(TFItems.steeleafPlate, 1);
			uncommon.add(TFItems.steeleafHelm, 1);
			uncommon.add(TFItems.steeleafBoots, 1);
			uncommon.add(TFItems.steeleafPick, 1);
			uncommon.add(TFItems.ironwoodPlate, 1);
			uncommon.add(TFItems.ironwoodSword, 1);
			uncommon.add(TFItems.charmOfKeeping1, 1);
			
			rare.add(TFItems.charmOfLife1, 1);
			rare.addEnchantedBook(Enchantments.FEATHERFALLING, 3);
			rare.addEnchantedBook(Enchantments.KNOCKBACK, 2);
			rare.addEnchantedBook(Enchantments.EFFICIENCY, 3);
		}

		if (i == 13) {
			// dark tower boss treasure
			useless.clear();
			
			common.add(TFItems.carminite, 3);
			uncommon.add(TFItems.fieryTears, 5);
			rare.add(new ItemStack(TFItems.trophy, 1, 3));
		}

		if (i == 14) {
			// tree branch treasure cache, food and saplings
			common.add(Items.POISONOUS_POTATO, 2);
			common.add(Items.WHEAT, 6);
			common.add(Items.POTATO, 6);
			common.add(Items.CARROT, 6);
			common.add(Items.MELON, 6);
			common.add(Items.WATER_BUCKET, 1);
			common.add(Items.MILK_BUCKET, 1);
			common.add(Items.MELON_SEEDS, 5);
			
			uncommon.add(new ItemStack(TFBlocks.firefly, 12));
			uncommon.add(new ItemStack(TFBlocks.sapling, 4, 0));
			uncommon.add(new ItemStack(TFBlocks.sapling, 4, 1));
			uncommon.add(new ItemStack(TFBlocks.sapling, 4, 2));
			uncommon.add(new ItemStack(TFBlocks.sapling, 4, 3));
			
			rare.add(Items.PUMPKIN_PIE, 12);
			rare.add(Items.APPLE, 12);
			rare.add(TFItems.charmOfLife1, 1);
			rare.add(TFItems.charmOfKeeping1, 1);

			ultrarare.add(new ItemStack(TFBlocks.sapling, 1, 4));
			ultrarare.add(new ItemStack(TFBlocks.sapling, 1, 5));
			ultrarare.add(new ItemStack(TFBlocks.sapling, 1, 6));
			ultrarare.add(new ItemStack(TFBlocks.sapling, 1, 7));
			ultrarare.add(new ItemStack(TFBlocks.sapling, 1, 8));
		}
		
		if (i == 15) {
			// stronghold regular treasure cache, contains supplies
			common.add(Items.STICK, 12);
			common.add(new ItemStack(Items.COAL, 12));
			common.add(Items.ARROW, 12);
			common.add(TFItems.mazeWafer, 9);
			common.add(new ItemStack(Blocks.WOOL, 1, 11));
			common.add(Items.IRON_INGOT, 2);

			uncommon.add(Items.BUCKET, 1);
			uncommon.add(Items.IRON_INGOT, 6);
			uncommon.add(TFItems.ironwoodIngot, 6);
			uncommon.add(TFBlocks.firefly, 5);
			uncommon.add(TFItems.charmOfKeeping1, 1);
			uncommon.add(TFItems.armorShard, 3);

			rare.add(TFItems.knightMetal, 8);
			rare.addRandomEnchanted(Items.BOW, 20);
			rare.addRandomEnchanted(Items.IRON_SWORD, 20);
			rare.addRandomEnchanted(TFItems.ironwoodSword, 15);
			rare.addRandomEnchanted(TFItems.steeleafSword, 10);
			
			ultrarare.addEnchantedBook(Enchantments.BANEOFARTHROPODS, 4);
			ultrarare.addEnchantedBook(Enchantments.SHARPNESS, 4);
			ultrarare.addEnchantedBook(Enchantments.SMITE, 4);
			ultrarare.addEnchantedBook(Enchantments.UNBREAKING, 2);
			ultrarare.addEnchantedBook(Enchantments.UNBREAKING, 2);
			ultrarare.addEnchantedBook(Enchantments.PROTECTION, 3);
			ultrarare.addEnchantedBook(Enchantments.PROJECTILEPROTECTION, 3);
			ultrarare.addEnchantedBook(Enchantments.FEATHERFALLING, 3);

		}
		
		if (i == 16) {
			// stronghold treasure room chest - kinda rare
			useless.clear();
			
			common.add(Items.IRON_INGOT, 4);
			common.add(TFItems.mazeWafer, 12);
			common.add(Items.GUNPOWDER, 4);
			common.add(TFItems.ironwoodIngot, 4);
			common.add(TFBlocks.firefly, 5);
			common.add(Items.MILK_BUCKET, 1);

			uncommon.add(TFItems.steeleafIngot, 6);
			uncommon.add(TFItems.steeleafLegs, 1);
			uncommon.add(TFItems.steeleafPlate, 1);
			uncommon.add(TFItems.steeleafHelm, 1);
			uncommon.add(TFItems.steeleafBoots, 1);
			uncommon.add(TFItems.steeleafPick, 1);
			uncommon.add(TFItems.ironwoodPlate, 1);
			uncommon.add(TFItems.ironwoodSword, 1);
			uncommon.add(TFItems.charmOfLife1, 1);
			
			rare.add(TFItems.mazeMapFocus, 1);
			rare.addRandomEnchanted(Items.BOW, 30);
			rare.addRandomEnchanted(Items.IRON_SWORD, 30);
			rare.addRandomEnchanted(TFItems.ironwoodSword, 25);
			rare.addRandomEnchanted(TFItems.steeleafSword, 20);
			rare.addRandomEnchanted(Items.DIAMOND_SWORD, 15);

		}
		
		if (i == 17) {
			// stronghold boss treasure
			useless.clear();
			
			common.addRandomEnchanted(TFItems.knightlySword, 20);
			common.addRandomEnchanted(TFItems.knightlyPick, 20);
			common.addRandomEnchanted(TFItems.knightlyAxe, 20);
			uncommon.addRandomEnchanted(TFItems.phantomHelm, 20);
			uncommon.addRandomEnchanted(TFItems.phantomPlate, 20);
			rare.addRandomEnchanted(TFItems.phantomHelm, 30);
			rare.addRandomEnchanted(TFItems.phantomPlate, 30);
		}

		if (i == 18) {
			// aurora cache
			common.add(Items.STICK, 12);
			common.add(new ItemStack(Items.COAL, 12));
			common.add(Items.ARROW, 12);
			common.add(TFItems.mazeWafer, 9);
			common.add(Blocks.ICE, 4);
			common.add(Blocks.PACKED_ICE, 4);
			common.add(TFItems.ironwoodIngot, 2);

			uncommon.add(TFBlocks.auroraBlock, 12);
			uncommon.add(TFItems.ironwoodIngot, 6);
			uncommon.add(TFBlocks.firefly, 5);
			uncommon.add(TFItems.charmOfKeeping1, 1);
			uncommon.add(TFItems.arcticFur, 3);

			rare.add(TFItems.arcticFur, 8);
			rare.add(TFItems.iceBow, 1);
			rare.add(TFItems.enderBow, 1);
			rare.add(TFItems.iceSword, 1);
			
			ultrarare.addEnchantedBook(Enchantments.SHARPNESS, 4);
			ultrarare.addEnchantedBook(Enchantments.POWER, 4);
			ultrarare.addEnchantedBook(Enchantments.PUNCH, 2);
			ultrarare.addEnchantedBook(Enchantments.UNBREAKING, 2);
			ultrarare.addEnchantedBook(Enchantments.UNBREAKING, 2);
			ultrarare.addEnchantedBook(Enchantments.INFINITY, 1);

		}

		if (i == 19) {
			// aurora treasure
			useless.clear();
			
			common.add(Blocks.ICE, 4);
			common.add(Blocks.PACKED_ICE, 4);
			common.add(TFItems.mazeWafer, 12);
			common.add(TFItems.iceBomb, 4);
			common.add(TFBlocks.firefly, 5);

			uncommon.add(TFItems.arcticFur, 6);
			uncommon.add(TFItems.arcticLegs, 1);
			uncommon.add(TFItems.arcticPlate, 1);
			uncommon.add(TFItems.arcticHelm, 1);
			uncommon.add(TFItems.arcticBoots, 1);
			uncommon.add(TFItems.knightlyPlate, 1);
			uncommon.add(TFItems.knightlySword, 1);
			uncommon.add(TFItems.charmOfLife1, 1);
			
			rare.addRandomEnchanted(TFItems.iceBow, 30);
			rare.addRandomEnchanted(TFItems.enderBow, 5);
			rare.addRandomEnchanted(TFItems.iceSword, 25);
			rare.addRandomEnchanted(TFItems.glassSword, 20);

		}

		if (i == 21) {
			// troll garden
			useless.clear();
			
			common.add(Blocks.RED_MUSHROOM, 4);
			common.add(Blocks.BROWN_MUSHROOM, 4);
			common.add(Items.WHEAT_SEEDS, 6);
			common.add(Items.CARROT, 6);
			common.add(Items.POTATO, 6);
			common.add(Items.MELON_SEEDS, 6);
			common.add(new ItemStack(Items.DYE, 12, 15));

			uncommon.add(TFBlocks.uberousSoil, 6);
			
			rare.add(TFItems.magicBeans, 1);

		}

		if (i == 22) {
			// troll vault
			useless.clear();
			
			useless.clear();
			
			common.add(Items.COAL, 32);
			common.add(TFItems.torchberries, 16);
			common.add(Items.EMERALD, 6);

			uncommon.add(TFBlocks.trollSteinn, 6);
			uncommon.add(Blocks.OBSIDIAN, 6);
			
			rare.add(TFItems.lampOfCinders, 1);

		}

	}
}
