package twilightforest.item;


import static net.minecraftforge.oredict.RecipeSorter.Category.SHAPELESS;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import twilightforest.TwilightForestMod;
import twilightforest.block.BlockTFFireJet;
import twilightforest.block.BlockTFPlant;
import twilightforest.block.BlockTFTowerDevice;
import twilightforest.block.TFBlocks;
import twilightforest.enchantment.TFEnchantment;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class TFRecipes {

	public static void registerRecipes() {
		
		// ore dictionary
        OreDictionary.registerOre("logWood", new ItemStack(TFBlocks.log, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("logWood", new ItemStack(TFBlocks.magicLog, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("treeSapling", new ItemStack(TFBlocks.sapling, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("treeLeaves", new ItemStack(TFBlocks.leaves, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("treeLeaves", new ItemStack(TFBlocks.magicLeaves, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("plankWood", new ItemStack(TFBlocks.towerWood, 1, OreDictionary.WILDCARD_VALUE));
        
        // register my ores, just for fun
        OreDictionary.registerOre("ingotFiery", new ItemStack(TFItems.fieryIngot));
        OreDictionary.registerOre("oreIronwood", new ItemStack(TFItems.ironwoodRaw));
        OreDictionary.registerOre("ingotIronwood", new ItemStack(TFItems.ironwoodIngot));
        OreDictionary.registerOre("ingotSteeleaf", new ItemStack(TFItems.steeleafIngot));
        OreDictionary.registerOre("oreKnightmetal", new ItemStack(TFItems.shardCluster));
        OreDictionary.registerOre("ingotKnightmetal", new ItemStack(TFItems.knightMetal));
        
        // recipe sorter
        RecipeSorter.register(TwilightForestMod.ID + ":mapcloning",  TFMapCloningRecipe.class,   SHAPELESS, "after:minecraft:shapeless");

		// smelting for logs
		GameRegistry.addSmelting(TFBlocks.log, new ItemStack(Items.COAL, 1, 1), 0.1F);
		GameRegistry.addSmelting(TFBlocks.magicLog, new ItemStack(Items.COAL, 1, 1), 0.1F);
        
        // recipes
		GameRegistry.addRecipe(new ItemStack(Blocks.PLANKS, 4, 0), new Object[] {"w", 'w', new ItemStack(TFBlocks.log, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(Blocks.PLANKS, 4, 1), new Object[] {"w", 'w', new ItemStack(TFBlocks.log, 1, 1)});
		GameRegistry.addRecipe(new ItemStack(Blocks.PLANKS, 4, 2), new Object[] {"w", 'w', new ItemStack(TFBlocks.log, 1, 2)});
		GameRegistry.addRecipe(new ItemStack(Blocks.PLANKS, 4, 1), new Object[] {"w", 'w', new ItemStack(TFBlocks.log, 1, 3)});

		addEnchantedRecipe(TFItems.plateNaga, Enchantments.FIREPROTECTION, 3, new Object[] {"# #", "###", "###", '#', TFItems.nagaScale});
		addEnchantedRecipe(TFItems.legsNaga, Enchantments.PROTECTION, 3, new Object[] {"###", "# #", "# #", '#', TFItems.nagaScale});

		GameRegistry.addShapelessRecipe(new ItemStack(TFBlocks.fireflyJar, 1, 0), new Object[] {TFBlocks.firefly, Items.GLASS_BOTTLE});

		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.scepterTwilight), new Object[] {new ItemStack(TFItems.scepterTwilight, 1, TFItems.scepterTwilight.getMaxDamage()), Items.ENDER_PEARL});
		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.scepterLifeDrain), new Object[] {new ItemStack(TFItems.scepterLifeDrain, 1, TFItems.scepterLifeDrain.getMaxDamage()), Items.FERMENTED_SPIDER_EYE});
		// aah, why are there so many potions of strength
		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.scepterZombie), new Object[] {new ItemStack(TFItems.scepterZombie, 1, TFItems.scepterZombie.getMaxDamage()), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.POTIONITEM, 1, 16281)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.scepterZombie), new Object[] {new ItemStack(TFItems.scepterZombie, 1, TFItems.scepterZombie.getMaxDamage()), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.POTIONITEM, 1, 16313)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.scepterZombie), new Object[] {new ItemStack(TFItems.scepterZombie, 1, TFItems.scepterZombie.getMaxDamage()), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.POTIONITEM, 1, 16345)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.scepterZombie), new Object[] {new ItemStack(TFItems.scepterZombie, 1, TFItems.scepterZombie.getMaxDamage()), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.POTIONITEM, 1, 16377)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.scepterZombie), new Object[] {new ItemStack(TFItems.scepterZombie, 1, TFItems.scepterZombie.getMaxDamage()), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.POTIONITEM, 1, 8201)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.scepterZombie), new Object[] {new ItemStack(TFItems.scepterZombie, 1, TFItems.scepterZombie.getMaxDamage()), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.POTIONITEM, 1, 8265)});
		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.scepterZombie), new Object[] {new ItemStack(TFItems.scepterZombie, 1, TFItems.scepterZombie.getMaxDamage()), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.POTIONITEM, 1, 8233)});

		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.magicMapFocus), new Object[] {TFItems.feather, TFItems.torchberries, Items.GLOWSTONE_DUST});
		GameRegistry.addRecipe(new ItemStack(TFItems.emptyMagicMap), new Object[] {"###", "#X#", "###", '#', Items.PAPER, 'X', TFItems.magicMapFocus});
		GameRegistry.addRecipe(new ItemStack(TFItems.emptyMazeMap), new Object[] {"###", "#X#", "###", '#', Items.PAPER, 'X', TFItems.mazeMapFocus});
		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.emptyOreMap), new Object[] {new ItemStack(TFItems.mazeMap, 1, Short.MAX_VALUE), Blocks.GOLD_BLOCK, Blocks.DIAMOND_BLOCK, Blocks.IRON_BLOCK});
		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.emptyOreMap), new Object[] {new ItemStack(TFItems.emptyMazeMap, 1, Short.MAX_VALUE), Blocks.GOLD_BLOCK, Blocks.DIAMOND_BLOCK, Blocks.IRON_BLOCK});

		GameRegistry.addRecipe(new ItemStack(Items.ARROW, 4), new Object[] {"X", "#", "Y", 'Y', TFItems.feather, 'X', Items.FLINT, '#', Items.STICK});

		GameRegistry.addShapelessRecipe(new ItemStack(Items.STICK), new Object[] {new ItemStack(TFBlocks.plant, 1, BlockTFPlant.META_ROOT_STRAND)});
		GameRegistry.addRecipe(new ItemStack(Blocks.TORCH, 5), new Object[] {"B", "S", 'B', TFItems.torchberries, 'S', Items.STICK});

		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.ironwoodRaw), new Object[] {TFItems.liveRoot, Items.IRON_INGOT, Items.GOLD_NUGGET});
		GameRegistry.addSmelting(TFItems.ironwoodRaw, new ItemStack(TFItems.ironwoodIngot, 2), 1.0F);

		addEnchantedRecipe(TFItems.ironwoodHelm, Enchantments.AQUAAFFINITY, 1, new Object[] {"###", "# #", '#', TFItems.ironwoodIngot});
		addEnchantedRecipe(TFItems.ironwoodPlate, Enchantments.PROTECTION, 1, new Object[] {"# #", "###", "###", '#', TFItems.ironwoodIngot});
		addEnchantedRecipe(TFItems.ironwoodLegs, Enchantments.PROTECTION, 1, new Object[] {"###", "# #", "# #", '#', TFItems.ironwoodIngot});
		addEnchantedRecipe(TFItems.ironwoodBoots, Enchantments.FEATHERFALLING, 1, new Object[] {"# #", "# #", '#', TFItems.ironwoodIngot});
		addEnchantedRecipe(TFItems.ironwoodSword, Enchantments.KNOCKBACK, 1, new Object[] {"#", "#", "X", '#', TFItems.ironwoodIngot, 'X', Items.STICK});
		addEnchantedRecipe(TFItems.ironwoodShovel, Enchantments.UNBREAKING, 1, new Object[] {"#", "X", "X", '#', TFItems.ironwoodIngot, 'X', Items.STICK});
		addEnchantedRecipe(TFItems.ironwoodPick, Enchantments.EFFICIENCY, 1, new Object[] {"###", " X ", " X ", '#', TFItems.ironwoodIngot, 'X', Items.STICK});
		addEnchantedRecipe(TFItems.ironwoodAxe, Enchantments.FORTUNE, 1, new Object[] {"##", "#X", " X", '#', TFItems.ironwoodIngot, 'X', Items.STICK});
		addEnchantedRecipe(TFItems.ironwoodHoe, null, 0, new Object[] {"##", " X", " X", '#', TFItems.ironwoodIngot, 'X', Items.STICK});

		GameRegistry.addRecipe(new ItemStack(TFBlocks.uncraftingTable), new Object[] {"###", "#X#", "###", '#', Blocks.CRAFTING_TABLE, 'X', TFItems.mazeMapFocus});

		GameRegistry.addSmelting(TFItems.venisonRaw, new ItemStack(TFItems.venisonCooked), 0.3F);

		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.fieryIngot), new Object[] {TFItems.fieryBlood, Items.IRON_INGOT});
		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.fieryIngot), new Object[] {TFItems.fieryTears, Items.IRON_INGOT});
		GameRegistry.addRecipe(new ItemStack(TFItems.fieryHelm), new Object[] {"###", "# #", '#', TFItems.fieryIngot});
		GameRegistry.addRecipe(new ItemStack(TFItems.fieryPlate), new Object[] {"# #", "###", "###", '#', TFItems.fieryIngot});
		GameRegistry.addRecipe(new ItemStack(TFItems.fieryLegs), new Object[] {"###", "# #", "# #", '#', TFItems.fieryIngot});
		GameRegistry.addRecipe(new ItemStack(TFItems.fieryBoots), new Object[] {"# #", "# #", '#', TFItems.fieryIngot});
		addEnchantedRecipe(TFItems.fierySword, Enchantments.FIREASPECT, 2, new Object[] {"#", "#", "X", '#', TFItems.fieryIngot, 'X', Items.BLAZE_ROD});
		GameRegistry.addRecipe(new ItemStack(TFItems.fieryPick), new Object[] {"###", " X ", " X ", '#', TFItems.fieryIngot, 'X', Items.BLAZE_ROD});

		addEnchantedRecipe(TFItems.steeleafHelm, Enchantments.PROJECTILEPROTECTION, 2, new Object[] {"###", "# #", '#', TFItems.steeleafIngot});
		addEnchantedRecipe(TFItems.steeleafPlate, Enchantments.BLASTPROTECTION, 2, new Object[] {"# #", "###", "###", '#', TFItems.steeleafIngot});
		addEnchantedRecipe(TFItems.steeleafLegs, Enchantments.FIREPROTECTION, 2, new Object[] {"###", "# #", "# #", '#', TFItems.steeleafIngot});
		addEnchantedRecipe(TFItems.steeleafBoots, Enchantments.FEATHERFALLING, 2, new Object[] {"# #", "# #", '#', TFItems.steeleafIngot});
		addEnchantedRecipe(TFItems.steeleafSword, Enchantments.LOOTING, 2, new Object[] {"#", "#", "X", '#', TFItems.steeleafIngot, 'X', Items.STICK});
		addEnchantedRecipe(TFItems.steeleafShovel, Enchantments.EFFICIENCY, 2, new Object[] {"#", "X", "X", '#', TFItems.steeleafIngot, 'X', Items.STICK});
		addEnchantedRecipe(TFItems.steeleafPick, Enchantments.FORTUNE, 2, new Object[] {"###", " X ", " X ", '#', TFItems.steeleafIngot, 'X', Items.STICK});
		addEnchantedRecipe(TFItems.steeleafAxe, Enchantments.EFFICIENCY, 2, new Object[] {"##", "#X", " X", '#', TFItems.steeleafIngot, 'X', Items.STICK});
		addEnchantedRecipe(TFItems.steeleafHoe, null, 0, new Object[] {"##", " X", " X", '#', TFItems.steeleafIngot, 'X', Items.STICK});

		GameRegistry.addSmelting(TFItems.meefRaw, new ItemStack(TFItems.meefSteak), 0.3F);

		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.moonwormQueen), new Object[] {new ItemStack(TFItems.moonwormQueen, 1, Short.MAX_VALUE), TFItems.torchberries, TFItems.torchberries, TFItems.torchberries});

		GameRegistry.addRecipe(new ItemStack(TFItems.emptyMagicMap), new Object[] {"###", "#X#", "###", '#', Items.PAPER, 'X', TFItems.magicMapFocus});

		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.charmOfKeeping2), new Object[] {TFItems.charmOfKeeping1, TFItems.charmOfKeeping1, TFItems.charmOfKeeping1, TFItems.charmOfKeeping1});
		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.charmOfKeeping3), new Object[] {TFItems.charmOfKeeping2, TFItems.charmOfKeeping2, TFItems.charmOfKeeping2, TFItems.charmOfKeeping2});

		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.charmOfLife2), new Object[] {TFItems.charmOfLife1, TFItems.charmOfLife1, TFItems.charmOfLife1, TFItems.charmOfLife1});

		GameRegistry.addRecipe(new TFMapCloningRecipe(TFItems.magicMap, TFItems.emptyMagicMap));
		GameRegistry.addRecipe(new TFMapCloningRecipe(TFItems.mazeMap, TFItems.emptyMazeMap));
		GameRegistry.addRecipe(new TFMapCloningRecipe(TFItems.oreMap, TFItems.emptyOreMap));
		
		// dark tower recipes
		GameRegistry.addRecipe(new ItemStack(TFBlocks.towerWood, 4, 0), new Object[] {"##", "##", '#', new ItemStack(TFBlocks.log, 1, 3)});
		GameRegistry.addRecipe(new ItemStack(TFBlocks.towerWood, 3, 1), new Object[] {"#", "#", "#", '#', new ItemStack(TFBlocks.towerWood, 1, 0)});
		GameRegistry.addRecipe(new ItemStack(TFItems.carminite), new Object[] {"brb", "rgr", "brb", 'b', new ItemStack(TFItems.borerEssence), 'r', new ItemStack(Items.REDSTONE), 'g', new ItemStack(Items.GHAST_TEAR)});
		GameRegistry.addRecipe(new ItemStack(TFItems.carminite), new Object[] {"rbr", "bgb", "rbr", 'b', new ItemStack(TFItems.borerEssence), 'r', new ItemStack(Items.REDSTONE), 'g', new ItemStack(Items.GHAST_TEAR)});
		GameRegistry.addRecipe(new ItemStack(TFBlocks.towerDevice, 8, BlockTFTowerDevice.META_VANISH_INACTIVE), new Object[] {"ewe", "wcw", "ewe", 'e', new ItemStack(TFBlocks.towerWood, 1, 1), 'w', new ItemStack(TFBlocks.towerWood, 1, 0), 'c', new ItemStack(TFItems.carminite)});
		GameRegistry.addRecipe(new ItemStack(TFBlocks.towerDevice, 2, BlockTFTowerDevice.META_REAPPEARING_INACTIVE), new Object[] {"ere", "rcr", "ere", 'e', new ItemStack(TFBlocks.towerWood, 1, 1), 'r', new ItemStack(Items.REDSTONE), 'c', new ItemStack(TFItems.carminite)});
		GameRegistry.addRecipe(new ItemStack(TFBlocks.towerDevice, 1, BlockTFTowerDevice.META_BUILDER_INACTIVE), new Object[] {"ece", "cdc", "ece", 'e', new ItemStack(TFBlocks.towerWood, 1, 1), 'd', new ItemStack(Blocks.DISPENSER), 'c', new ItemStack(TFItems.carminite)});
		GameRegistry.addRecipe(new ItemStack(TFBlocks.towerDevice, 1, BlockTFTowerDevice.META_REACTOR_INACTIVE), new Object[] {"ece", "coc", "ece", 'e', new ItemStack(TFBlocks.towerWood, 1, 1), 'o', new ItemStack(Blocks.REDSTONE_ORE), 'c', new ItemStack(TFItems.carminite)});
		GameRegistry.addRecipe(new ItemStack(TFBlocks.fireJet, 1, BlockTFFireJet.META_ENCASED_SMOKER_OFF), new Object[] {"ere", "rsr", "ere", 'e', new ItemStack(TFBlocks.towerWood, 1, 1), 'r', new ItemStack(Items.REDSTONE), 's', new ItemStack(TFBlocks.fireJet, 1, BlockTFFireJet.META_SMOKER)});
		GameRegistry.addRecipe(new ItemStack(TFBlocks.fireJet, 1, BlockTFFireJet.META_ENCASED_JET_IDLE), new Object[] {"ere", "rjr", "lll", 'e', new ItemStack(TFBlocks.towerWood, 1, 1), 'r', new ItemStack(Items.REDSTONE), 'l', new ItemStack(Items.LAVA_BUCKET), 'j', new ItemStack(TFBlocks.fireJet, 1, BlockTFFireJet.META_JET_IDLE)});

		
		GameRegistry.addRecipe(new ItemStack(TFItems.shardCluster), new Object[] {"###", "###", "###", '#', TFItems.armorShard});

		GameRegistry.addSmelting(TFItems.shardCluster, new ItemStack(TFItems.knightMetal), 1.0F);
		
		GameRegistry.addRecipe(new ItemStack(TFItems.knightlyHelm), new Object[] {"###", "# #", '#', TFItems.knightMetal});
		GameRegistry.addRecipe(new ItemStack(TFItems.knightlyPlate), new Object[] {"# #", "###", "###", '#', TFItems.knightMetal});
		GameRegistry.addRecipe(new ItemStack(TFItems.knightlyLegs), new Object[] {"###", "# #", "# #", '#', TFItems.knightMetal});
		GameRegistry.addRecipe(new ItemStack(TFItems.knightlyBoots), new Object[] {"# #", "# #", '#', TFItems.knightMetal});

		GameRegistry.addRecipe(new ItemStack(TFItems.knightlySword), new Object[] {"#", "#", "X", '#', TFItems.knightMetal, 'X', Items.STICK});
		GameRegistry.addRecipe(new ItemStack(TFItems.knightlyPick), new Object[] {"###", " X ", " X ", '#', TFItems.knightMetal, 'X', Items.STICK});
		GameRegistry.addRecipe(new ItemStack(TFItems.knightlyAxe), new Object[] {"##", "#X", " X", '#', TFItems.knightMetal, 'X', Items.STICK});
		GameRegistry.addRecipe(new ItemStack(TFItems.knightlyAxe), new Object[] {"##", "X#", "X ", '#', TFItems.knightMetal, 'X', Items.STICK});
		
		addEnchantedRecipe(TFItems.yetiHelm, Enchantments.PROTECTION, 2, new Object[] {"###", "# #", '#', TFItems.alphaFur});
		addEnchantedRecipe(TFItems.yetiPlate, Enchantments.PROTECTION, 2, new Object[] {"# #", "###", "###", '#', TFItems.alphaFur});
		addEnchantedRecipe(TFItems.yetiLegs, Enchantments.PROTECTION, 2, new Object[] {"###", "# #", "# #", '#', TFItems.alphaFur});
		addEnchantedRecipe(TFItems.yetiBoots, Enchantments.PROTECTION, 2, Enchantments.FEATHERFALLING, 4, new Object[] {"# #", "# #", '#', TFItems.alphaFur});

		GameRegistry.addRecipe(new ItemStack(TFItems.arcticHelm), new Object[] {"###", "# #", '#', TFItems.arcticFur});
		GameRegistry.addRecipe(new ItemStack(TFItems.arcticPlate), new Object[] {"# #", "###", "###", '#', TFItems.arcticFur});
		GameRegistry.addRecipe(new ItemStack(TFItems.arcticLegs), new Object[] {"###", "# #", "# #", '#', TFItems.arcticFur});
		GameRegistry.addRecipe(new ItemStack(TFItems.arcticBoots), new Object[] {"# #", "# #", '#', TFItems.arcticFur});
		
		GameRegistry.addRecipe(new ItemStack(TFBlocks.auroraSlab, 6, 0), new Object[] {"###", '#', TFBlocks.auroraBlock});
		GameRegistry.addRecipe(new ItemStack(TFBlocks.auroraPillar, 2, 0), new Object[] {"#", "#", '#', TFBlocks.auroraBlock});
		
		GameRegistry.addRecipe(new ItemStack(TFItems.giantPick), new Object[] {"###", " X ", " X ", '#', TFBlocks.giantCobble, 'X', TFBlocks.giantLog});
		GameRegistry.addRecipe(new ItemStack(TFItems.giantSword), new Object[] {"#", "#", "X", '#', TFBlocks.giantCobble, 'X', TFBlocks.giantLog});

		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.COBBLESTONE, 64), new Object[] {new ItemStack(TFBlocks.giantCobble)});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.PLANKS, 64), new Object[] {new ItemStack(TFBlocks.giantLog)});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.LEAVES, 64), new Object[] {new ItemStack(TFBlocks.giantLeaves)});
		
		GameRegistry.addRecipe(new ItemStack(TFItems.knightmetalRing), new Object[] {" # ", "# #", " # ", '#', TFItems.knightMetal});
		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.chainBlock), new Object[] {TFItems.knightmetalRing, TFItems.knightMetal, TFItems.knightMetal, TFItems.knightMetal, TFBlocks.knightmetalStorage});

		GameRegistry.addShapelessRecipe(new ItemStack(TFItems.knightMetal, 9), new Object[] {new ItemStack(TFBlocks.knightmetalStorage)});
		GameRegistry.addRecipe(new ItemStack(TFBlocks.knightmetalStorage), new Object[] {"###", "###", "###", '#', TFItems.knightMetal});

	}
	 
	/**
	 * Add a recipe for an enchanted item.  Always shaped.
	 * 
	 * @param item
	 * @param enchantment
	 * @param enchantmentLevel
	 * @param ingredientArray
	 */
	public static void addEnchantedRecipe(Item item, Enchantment enchantment, int enchantmentLevel, Object ... ingredientArray) {
		ItemStack result = new ItemStack(item);
		if (enchantment != null) {
			result.addEnchantment(enchantment, enchantmentLevel);
		}
		
		GameRegistry.addRecipe(result, ingredientArray);
	}
	/**
	 * Add a recipe for an enchanted item.  Always shaped.
	 * 
	 * @param item
	 * @param enchantment
	 * @param enchantmentLevel
	 * @param ingredientArray
	 */
	public static void addEnchantedRecipe(Item item, Enchantment enchantment, int enchantmentLevel, Enchantment enchantment2, int enchantmentLevel2, Object ... ingredientArray) {
		ItemStack result = new ItemStack(item);
		if (enchantment != null) {
			result.addEnchantment(enchantment, enchantmentLevel);
		}
		if (enchantment2 != null) {
			result.addEnchantment(enchantment2, enchantmentLevel2);
		}
		
		GameRegistry.addRecipe(result, ingredientArray);
	}
}
