package twilightforest;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
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
		
		treasureRNG = new org.bogdang.modifications.random.XSTR();

		// TODO: separate these into plain ol' different classes
		fill(i);
	}

	/**
	 * Generates a treasure chest in the world at the specified coordinates.
	 */
	public boolean generate(World world, Random rand, int cx, int cy, int cz) {

		return this.generate(world, rand, cx, cy, cz, Blocks.chest);
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
		useless.add(Blocks.red_flower, 4);
		useless.add(Blocks.yellow_flower, 4);
		useless.add(Items.feather, 3);
		useless.add(Items.wheat_seeds, 2);
		useless.add(Items.flint, 2);
		useless.add(Blocks.cactus, 2);
		useless.add(Items.reeds, 4);
		useless.add(Blocks.sand, 4);
		useless.add(Items.flower_pot, 1);
		useless.add(new ItemStack(Items.dye, 1, 0));

		if (i == 1) {
			// hill type 1 chest
			common.add(Items.iron_ingot, 4);
			common.add(Items.wheat, 4);
			common.add(Items.string, 4);
			common.add(Items.bucket, 1);

			uncommon.add(Items.bread, 1);
			uncommon.add(TFItems.oreMagnet, 1);
			uncommon.add(Items.gunpowder, 4);
			uncommon.add(Items.arrow, 12);
			uncommon.add(Blocks.torch, 12);

			rare.add(Items.gold_ingot, 3);
			rare.add(Items.iron_pickaxe, 1);
			rare.add(TFItems.liveRoot, 3);

			ultrarare.add(TFItems.transformPowder, 12);
			ultrarare.add(Items.diamond, 1);
			ultrarare.add(TFItems.steeleafIngot, 3);
		}
		
		if (i == 2) {
			// hill type 2 chest
			common.add(Items.iron_ingot, 4);
			common.add(Items.carrot, 4);
			common.add(Blocks.ladder, 6);
			common.add(Items.bucket, 1);

			uncommon.add(Items.baked_potato, 2);
			uncommon.add(TFItems.oreMagnet, 1);
			uncommon.add(TFItems.ironwoodIngot, 4);
			uncommon.add(Items.arrow, 12);
			uncommon.add(Blocks.torch, 12);

			rare.add(TFItems.nagaScale, 1);
			rare.add(TFBlocks.uncraftingTable, 1);
			rare.add(TFItems.transformPowder, 12);

			ultrarare.add(TFItems.peacockFan, 1);
			ultrarare.add(Items.emerald, 6);
			ultrarare.add(Items.diamond, 1);
			ultrarare.add(TFItems.charmOfLife1, 1);
		}
		
		if (i == 3) {
			// hill type 3 chest
			common.add(Items.gold_nugget, 9);
			common.add(Items.potato, 4);
			common.add(Items.fish, 4);
			common.add(TFItems.torchberries, 5);

			uncommon.add(Items.pumpkin_pie, 1);
			uncommon.add(TFItems.oreMagnet, 1);
			uncommon.add(Items.gunpowder, 4);
			uncommon.add(Items.arrow, 12);
			uncommon.add(Blocks.torch, 12);
			uncommon.add(TFItems.steeleafIngot, 4);

			rare.add(TFItems.nagaScale, 1);
			rare.addEnchanted(new ItemStack(TFItems.ironwoodPick, 1), Enchantment.efficiency, 1, Enchantment.fortune, 1);
			rare.add(TFItems.transformPowder, 12);

			ultrarare.add(TFItems.moonwormQueen, 1);
			ultrarare.add(TFBlocks.sapling, 1, 4);
			ultrarare.add(Items.diamond, 2);
			ultrarare.add(TFItems.charmOfLife1, 1);
			ultrarare.add(TFItems.charmOfKeeping1, 1);
		}

		if (i == 4) {
			// hedge maze chest
			common.add(Blocks.planks, 4);
			common.add(Blocks.brown_mushroom, 4);
			common.add(Blocks.red_mushroom, 4);
			common.add(Items.wheat, 4);
			common.add(Items.string, 4);
			common.add(Items.stick, 6);

			uncommon.add(Items.melon, 4);
			uncommon.add(Items.melon_seeds, 4);
			uncommon.add(Items.pumpkin_seeds, 4);
			uncommon.add(Items.arrow, 12);
			uncommon.add(TFBlocks.firefly, 4);

			rare.add(Blocks.web, 3);
			rare.add(Items.shears, 1);
			rare.add(Items.saddle, 1);
			rare.add(Items.bow, 1);
			rare.add(Items.apple, 2);

			ultrarare.add(Items.diamond_hoe, 1);
			ultrarare.add(Items.diamond, 1);
			ultrarare.add(Items.mushroom_stew, 1);
			ultrarare.add(Items.golden_apple, 1);
		}

		if (i == 5) {
			// labyrinth room chest
			useless.clear();
			
			common.add(Items.iron_ingot, 4);
			common.add(TFItems.mazeWafer, 12);
			common.add(Items.gunpowder, 4);
			common.add(TFItems.ironwoodIngot, 4);
			common.add(TFBlocks.firefly, 5);
			common.add(Items.milk_bucket, 1);

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
			rare.add(Blocks.tnt, 3);
			rare.add(new ItemStack(Items.potionitem, 1, 16373)); // health ii
		}

		if (i == 6) {
			// labyrinth dead end chest
			common.add(Items.stick, 12);
			common.add(Items.coal, 12);
			common.add(Items.arrow, 12);
			common.add(TFItems.mazeWafer, 9);
			common.add(Items.paper, 12);
			common.add(Items.leather, 4);
			common.add(Items.mushroom_stew, 1);

			uncommon.add(Items.milk_bucket, 1);
			uncommon.add(Items.paper, 5);
			uncommon.add(Items.iron_ingot, 6);
			uncommon.add(TFItems.ironwoodIngot, 8);
			uncommon.add(TFBlocks.firefly, 5);
			uncommon.add(TFItems.charmOfKeeping1, 1);

			rare.add(TFItems.steeleafIngot, 8);
			rare.add(Items.golden_apple, 1);
			rare.add(Items.blaze_rod, 2);
		}
		
		if (i == 10) {
			// labyrinth vault chest
			useless.clear();
			
			common.add(Items.iron_ingot, 9);
			common.add(Items.emerald, 5);
			common.add(TFItems.mazeWafer, 12);
			common.add(TFItems.ironwoodIngot, 9);
			common.add(new ItemStack(Items.potionitem, 1, 16369)); // regen ii
			common.add(new ItemStack(Items.potionitem, 1, 16373)); // health ii
			common.add(new ItemStack(Items.potionitem, 1, 16370)); // extended swiftness ii

			uncommon.addEnchanted(new ItemStack(Items.bow), Enchantment.infinity, 1, Enchantment.punch, 2);					
			uncommon.addEnchanted(new ItemStack(Items.bow), Enchantment.power, 3, Enchantment.flame, 1);		
			uncommon.addEnchanted(new ItemStack(TFItems.steeleafShovel), Enchantment.efficiency, 4, Enchantment.unbreaking, 2);	
			uncommon.addEnchanted(new ItemStack(TFItems.steeleafAxe), Enchantment.efficiency, 5);
			uncommon.add(TFItems.steeleafIngot, 12);
			uncommon.addEnchanted(new ItemStack(TFItems.steeleafPlate), Enchantment.protection, 3);
			uncommon.addEnchanted(new ItemStack(TFItems.steeleafLegs), Enchantment.fireProtection, 4);
			uncommon.addEnchanted(new ItemStack(TFItems.steeleafBoots), Enchantment.protection, 2);
			uncommon.addEnchanted(new ItemStack(TFItems.steeleafHelm), Enchantment.respiration, 3);
			
			rare.add(Blocks.emerald_block, 1);
			rare.add(Blocks.ender_chest, 1);
			rare.addEnchanted(new ItemStack(TFItems.steeleafPick), Enchantment.efficiency, 4, Enchantment.silkTouch, 1);
			rare.addEnchanted(new ItemStack(TFItems.steeleafSword), Enchantment.sharpness, 4, Enchantment.knockback, 2);
			rare.addEnchanted(new ItemStack(TFItems.steeleafSword), Enchantment.baneOfArthropods, 5, Enchantment.fireAspect, 2);
			rare.addEnchanted(new ItemStack(TFItems.mazebreakerPick), Enchantment.efficiency, 4, Enchantment.unbreaking, 3, Enchantment.fortune, 2);
		}

		if (i == 7) {
			// wizards tower treasure room chest
			common.add(Items.glass_bottle, 6);
			common.add(new ItemStack(Items.potionitem, 1, 0));
			common.add(Items.sugar, 5);
			common.add(Items.spider_eye, 3);
			common.add(Items.ghast_tear, 1);
			common.add(Items.magma_cream, 2);
			common.add(Items.fermented_spider_eye, 1);
			common.add(Items.speckled_melon, 2);
			common.add(Items.blaze_powder, 3);
			common.add(Items.paper, 6);

			uncommon.addRandomEnchanted(Items.golden_sword, 10);
			uncommon.addRandomEnchanted(Items.golden_boots, 7);
			uncommon.add(new ItemStack(Items.potionitem, 1, 16274)); // swiftness
			uncommon.add(new ItemStack(Items.potionitem, 1, 16341)); // healing
			uncommon.add(new ItemStack(Items.potionitem, 1, 16307)); // fire resist
			uncommon.add(new ItemStack(Items.potionitem, 1, 16348)); // harm
			
			rare.addRandomEnchanted(Items.golden_helmet, 18);
			rare.add(new ItemStack(Items.potionitem, 1, 16306)); // swiftness ii
			rare.add(new ItemStack(Items.potionitem, 1, 16305)); // regen ii
			rare.add(new ItemStack(Items.potionitem, 1, 32725)); // splash health
			rare.add(new ItemStack(Items.potionitem, 1, 32764)); // harming ii splash
			rare.add(TFItems.transformPowder, 12);
			rare.add(TFItems.charmOfLife1, 1);
			rare.add(TFItems.charmOfKeeping1, 1);

			ultrarare.addRandomEnchanted(Items.golden_axe, 20);
			ultrarare.add(Items.ender_pearl, 1);
			ultrarare.add(Blocks.obsidian, 4);
			ultrarare.add(Items.diamond, 1);
			ultrarare.add(TFItems.moonwormQueen, 1);
			ultrarare.add(TFItems.peacockFan, 1);
		}

		if (i == 8) {
			// wizards tower library chest
			common.add(Items.glass_bottle, 6);
			common.add(new ItemStack(Items.potionitem, 1, 0));
			common.add(Blocks.ladder, 6);
			common.add(Items.paper, 6);
			common.add(Items.bone, 6);
			common.add(Items.gold_nugget, 6);
			common.add(Items.clay_ball, 12);

			uncommon.addRandomEnchanted(Items.iron_leggings, 5);
			uncommon.add(Items.fire_charge, 3);
			uncommon.add(Items.book, 5);
			uncommon.add(Items.map, 1);
			uncommon.add(new ItemStack(Items.potionitem, 1, 16));
			uncommon.add(new ItemStack(Items.potionitem, 1, 16276)); // poison
			uncommon.add(new ItemStack(Items.potionitem, 1, 16312)); // weakness
			
			rare.addRandomEnchanted(Items.bow, 5);
			rare.addRandomEnchanted(Items.stone_sword, 10);
			rare.addRandomEnchanted(Items.wooden_sword, 15);
			rare.add(new ItemStack(Items.potionitem, 1, 32696)); // splash weakness
			rare.add(new ItemStack(Items.potionitem, 1, 16369)); // regen ii
			rare.add(new ItemStack(Items.potionitem, 1, 16373)); // health ii
			rare.add(new ItemStack(Items.potionitem, 1, 16370)); // extended swiftness ii
			rare.add(TFItems.transformPowder, 12);
			rare.add(TFItems.charmOfKeeping1, 1);
			
			ultrarare.addRandomEnchanted(Items.golden_pickaxe, 10);
			ultrarare.addRandomEnchanted(Items.iron_sword, 20);
			ultrarare.addRandomEnchanted(Items.bow, 30);
			ultrarare.add(Blocks.bookshelf, 5);
			ultrarare.add(Items.ender_pearl, 2);
			ultrarare.add(Items.experience_bottle, 6);
		}
		
		if (i == 9) {
			// basement treasure cache, mostly full of food!
			common.add(new ItemStack(Items.potionitem, 1, 0));
			common.add(Items.rotten_flesh, 6);
			common.add(Items.poisonous_potato, 2);
			common.add(Items.wheat, 6);
			common.add(Items.potato, 6);
			common.add(Items.carrot, 6);
			common.add(Items.melon, 6);
			common.add(Items.water_bucket, 1);
			common.add(Blocks.torch, 12);
			common.add(Items.mushroom_stew, 1);
			common.add(Items.milk_bucket, 1);
			common.add(Items.melon_seeds, 5);
			
			uncommon.add(Items.bread, 8);
			uncommon.add(Items.cooked_beef, 6);
			uncommon.add(Items.cooked_porkchop, 8);
			uncommon.add(Items.baked_potato, 8);
			uncommon.add(Items.cooked_chicken, 10);
			uncommon.add(Items.cooked_fished, 8);
			
			rare.add(Items.speckled_melon, 12);
			rare.add(Items.apple, 12);
			rare.add(Items.map, 1);
			rare.add(TFItems.charmOfKeeping1, 1);
			
			ultrarare.add(Items.golden_apple, 2);
			ultrarare.add(Items.golden_carrot, 2);
			ultrarare.add(Items.cake, 1);
			ultrarare.add(Items.boat, 1);
			ultrarare.add(new ItemStack(TFBlocks.sapling, 1, 4));
		}

		if (i == 11) {
			// dark tower regular treasure cache, contains supplies
			common.add(Items.stick, 12);
			common.add(new ItemStack(Items.coal, 12, 1));
			common.add(Items.arrow, 12);
			common.add(TFItems.experiment115, 9);
			common.add(new ItemStack(Blocks.wool, 1, 14));
			common.add(Items.redstone, 6);

			uncommon.add(Blocks.redstone_lamp, 3);
			uncommon.add(Items.iron_ingot, 6);
			uncommon.add(TFItems.ironwoodIngot, 8);
			uncommon.add(TFBlocks.firefly, 5);
			uncommon.add(TFItems.charmOfKeeping1, 1);

			rare.add(TFItems.steeleafIngot, 8);
			rare.add(Items.diamond, 2);

		}

		if (i == 12) {
			// dark tower key treasure chest, contains higher quality treasure
			// key will be added seperately
			useless.clear();
			
			common.add(Items.iron_ingot, 4);
			common.add(TFItems.experiment115, 12);
			common.add(Items.gunpowder, 4);
			common.add(TFItems.ironwoodIngot, 4);
			common.add(TFBlocks.firefly, 5);
			common.add(Items.redstone, 12);
			common.add(Items.glowstone_dust, 12);

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
			rare.addEnchantedBook(Enchantment.featherFalling, 3);
			rare.addEnchantedBook(Enchantment.knockback, 2);
			rare.addEnchantedBook(Enchantment.efficiency, 3);
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
			common.add(Items.poisonous_potato, 2);
			common.add(Items.wheat, 6);
			common.add(Items.potato, 6);
			common.add(Items.carrot, 6);
			common.add(Items.melon, 6);
			common.add(Items.water_bucket, 1);
			common.add(Items.milk_bucket, 1);
			common.add(Items.melon_seeds, 5);
			
			uncommon.add(new ItemStack(TFBlocks.firefly, 12));
			uncommon.add(new ItemStack(TFBlocks.sapling, 4, 0));
			uncommon.add(new ItemStack(TFBlocks.sapling, 4, 1));
			uncommon.add(new ItemStack(TFBlocks.sapling, 4, 2));
			uncommon.add(new ItemStack(TFBlocks.sapling, 4, 3));
			
			rare.add(Items.pumpkin_pie, 12);
			rare.add(Items.apple, 12);
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
			common.add(Items.stick, 12);
			common.add(new ItemStack(Items.coal, 12));
			common.add(Items.arrow, 12);
			common.add(TFItems.mazeWafer, 9);
			common.add(new ItemStack(Blocks.wool, 1, 11));
			common.add(Items.iron_ingot, 2);

			uncommon.add(Items.bucket, 1);
			uncommon.add(Items.iron_ingot, 6);
			uncommon.add(TFItems.ironwoodIngot, 6);
			uncommon.add(TFBlocks.firefly, 5);
			uncommon.add(TFItems.charmOfKeeping1, 1);
			uncommon.add(TFItems.armorShard, 3);

			rare.add(TFItems.knightMetal, 8);
			rare.addRandomEnchanted(Items.bow, 20);
			rare.addRandomEnchanted(Items.iron_sword, 20);
			rare.addRandomEnchanted(TFItems.ironwoodSword, 15);
			rare.addRandomEnchanted(TFItems.steeleafSword, 10);
			
			ultrarare.addEnchantedBook(Enchantment.baneOfArthropods, 4);
			ultrarare.addEnchantedBook(Enchantment.sharpness, 4);
			ultrarare.addEnchantedBook(Enchantment.smite, 4);
			ultrarare.addEnchantedBook(Enchantment.unbreaking, 2);
			ultrarare.addEnchantedBook(Enchantment.unbreaking, 2);
			ultrarare.addEnchantedBook(Enchantment.protection, 3);
			ultrarare.addEnchantedBook(Enchantment.projectileProtection, 3);
			ultrarare.addEnchantedBook(Enchantment.featherFalling, 3);

		}
		
		if (i == 16) {
			// stronghold treasure room chest - kinda rare
			useless.clear();
			
			common.add(Items.iron_ingot, 4);
			common.add(TFItems.mazeWafer, 12);
			common.add(Items.gunpowder, 4);
			common.add(TFItems.ironwoodIngot, 4);
			common.add(TFBlocks.firefly, 5);
			common.add(Items.milk_bucket, 1);

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
			rare.addRandomEnchanted(Items.bow, 30);
			rare.addRandomEnchanted(Items.iron_sword, 30);
			rare.addRandomEnchanted(TFItems.ironwoodSword, 25);
			rare.addRandomEnchanted(TFItems.steeleafSword, 20);
			rare.addRandomEnchanted(Items.diamond_sword, 15);

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
			common.add(Items.stick, 12);
			common.add(new ItemStack(Items.coal, 12));
			common.add(Items.arrow, 12);
			common.add(TFItems.mazeWafer, 9);
			common.add(Blocks.ice, 4);
			common.add(Blocks.packed_ice, 4);
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
			
			ultrarare.addEnchantedBook(Enchantment.sharpness, 4);
			ultrarare.addEnchantedBook(Enchantment.power, 4);
			ultrarare.addEnchantedBook(Enchantment.punch, 2);
			ultrarare.addEnchantedBook(Enchantment.unbreaking, 2);
			ultrarare.addEnchantedBook(Enchantment.unbreaking, 2);
			ultrarare.addEnchantedBook(Enchantment.infinity, 1);

		}

		if (i == 19) {
			// aurora treasure
			useless.clear();
			
			common.add(Blocks.ice, 4);
			common.add(Blocks.packed_ice, 4);
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
			
			common.add(Blocks.red_mushroom, 4);
			common.add(Blocks.brown_mushroom, 4);
			common.add(Items.wheat_seeds, 6);
			common.add(Items.carrot, 6);
			common.add(Items.potato, 6);
			common.add(Items.melon_seeds, 6);
			common.add(new ItemStack(Items.dye, 12, 15));

			uncommon.add(TFBlocks.uberousSoil, 6);
			
			rare.add(TFItems.magicBeans, 1);

		}

		if (i == 22) {
			// troll vault
			useless.clear();
			
			useless.clear();
			
			common.add(Items.coal, 32);
			common.add(TFItems.torchberries, 16);
			common.add(Items.emerald, 6);

			uncommon.add(TFBlocks.trollSteinn, 6);
			uncommon.add(Blocks.obsidian, 6);
			
			rare.add(TFItems.lampOfCinders, 1);

		}

	}
}
