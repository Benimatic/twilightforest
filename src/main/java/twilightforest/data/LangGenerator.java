package twilightforest.data;

import net.minecraft.data.PackOutput;
import twilightforest.TFConfig;
import twilightforest.data.helpers.TFLangProvider;
import twilightforest.init.*;

public class LangGenerator extends TFLangProvider {
	public LangGenerator(PackOutput output) {
		super(output);
	}

	@Override
	protected void addTranslations() {

		this.add("itemGroup.twilightforest.blocks", "Twilight Forest: Blocks");
		this.add("itemGroup.twilightforest.items", "Twilight Forest: Items");
		this.add("itemGroup.twilightforest.equipment", "Twilight Forest: Equipment");

		this.addBiome(TFBiomes.FOREST, "Twilight Forest");
		this.addBiome(TFBiomes.DENSE_FOREST, "Dense Forest");
		this.addBiome(TFBiomes.FIREFLY_FOREST, "Firefly Forest");
		this.addBiome(TFBiomes.CLEARING, "Twilight Clearing");
		this.addBiome(TFBiomes.OAK_SAVANNAH, "Oak Savanna");
		this.addBiome(TFBiomes.STREAM, "Twilight Stream");
		this.addBiome(TFBiomes.LAKE, "Twilight Lake");
		this.addBiome(TFBiomes.MUSHROOM_FOREST, "Mushroom Forest");
		this.addBiome(TFBiomes.DENSE_MUSHROOM_FOREST, "Dense Mushroom Forest");
		this.addBiome(TFBiomes.ENCHANTED_FOREST, "Enchanted Forest");
		this.addBiome(TFBiomes.SPOOKY_FOREST, "Spooky Forest");
		this.addBiome(TFBiomes.SWAMP, "Twilight Swamp");
		this.addBiome(TFBiomes.FIRE_SWAMP, "Fire Swamp");
		this.addBiome(TFBiomes.DARK_FOREST, "Dark Forest");
		this.addBiome(TFBiomes.DARK_FOREST_CENTER, "Dark Forest Center");
		this.addBiome(TFBiomes.SNOWY_FOREST, "Snowy Forest");
		this.addBiome(TFBiomes.GLACIER, "Twilight Glacier");
		this.addBiome(TFBiomes.HIGHLANDS, "Twilight Highlands");
		this.addBiome(TFBiomes.THORNLANDS, "Thornlands");
		this.addBiome(TFBiomes.FINAL_PLATEAU, "Final Plateau");
		this.addBiome(TFBiomes.UNDERGROUND, "Underground");

		this.add("dimension.twilightforest.twilightforest", "Twilight Forest");

		this.addStructure(TFStructures.HEDGE_MAZE, "Hedge Maze");
		this.addStructure(TFStructures.HOLLOW_HILL_SMALL, "Small Hollow Hill");
		this.addStructure(TFStructures.HOLLOW_HILL_MEDIUM, "Medium Hollow Hill");
		this.addStructure(TFStructures.HOLLOW_HILL_LARGE, "Large Hollow Hill");
		this.addStructure(TFStructures.QUEST_GROVE, "Quest Grove");
		this.addStructure(TFStructures.MUSHROOM_TOWER, "Mushroom Castle");
		this.addStructure(TFStructures.NAGA_COURTYARD, "Naga Courtyard");
		this.addStructure(TFStructures.LICH_TOWER, "Lich Tower");
		this.addStructure(TFStructures.LABYRINTH, "Minotaur Labyrinth");
		this.addStructure(TFStructures.HYDRA_LAIR, "Hydra's Lair");
		this.addStructure(TFStructures.KNIGHT_STRONGHOLD, "Knight Stronghold");
		this.addStructure(TFStructures.DARK_TOWER, "Dark Tower");
		this.addStructure(TFStructures.YETI_CAVE, "Yeti Cave");
		this.addStructure(TFStructures.AURORA_PALACE, "Aurora Palace");
		this.addStructure(TFStructures.TROLL_CAVE, "Troll Cave");
		this.addStructure(TFStructures.FINAL_CASTLE, "Final Plateau Castle");

		this.addCommand("usage", "/%s <info | reactivate | conquer | center>");
		this.addCommand("not_in_twilight_forest", "You are not in the Twilight Forest dimension");
		this.addCommand("not_player", "This command must be run by a valid player!");
		this.addCommand("none_nearby", "Couldn't find a feature nearby!");
		this.addCommand("nearest", "The nearest feature is: %s");
		this.addCommand("center", "Center of feature: %s");
		this.addCommand("chunk", "Located in feature chunk: %s");
		this.addCommand("structure.inside", "You are in a Landmark structure");
		this.addCommand("structure.outside", "You are not in a Landmark structure");
		this.addCommand("structure.required", "You are not in a structure");
		this.addCommand("structure.conquer.status", "Structure conquer flag: %s");
		this.addCommand("structure.conquer.update", "Structure conquer flag was %s, changing to %s");
		this.addCommand("structure.spawn_list", "Spawn list for this area is:");
		this.addCommand("structure.spawn_info", "%s, Weight %s");

		this.add("gamerule.tfEnforcedProgression", "Twilight Forest: Enforced Progression");
		this.add("gamerule.tfEnforcedProgression.description", "Some Twilight Forest biomes will be locked until you defeat certain bosses in the dimension. (You can check the progression order in your advancements)");

		this.add("effect.twilightforest.frosted", "Frosted");

		this.addEnchantment("chill_aura", "Chill Aura", "Adds a chance for the attacker to gain the frosted effect after hitting the wearer.");
		this.addEnchantment("fire_react", "Fire React", "Adds a chance for the attacker to be set on fire after hitting the wearer.");
		this.addEnchantment("destruction", "Destruction", "Allows the Block and Chain to break blocks with a higher mining tier.");

		this.addAdvancement("root", "Twilight Forest", "Enter the mysterious, magical woodlands: the Twilight Forest");
		this.addAdvancement("kill_cicada", "Shut", "Kill a Cicada");
		this.addAdvancement("hedge", "Bug Stomper", "Defeat a spider in a Hedge Maze");
		this.addAdvancement("hill1", "The Boots Are Mine!", "Defeat a %s in a Small Hollow Hill");
		this.addAdvancement("hill2", "What Was That Noise?", "Defeat a %s in a Medium Hollow Hill");
		this.addAdvancement("hill3", "I See Right Through You", "Defeat a %s in a Large Hollow Hill");
		this.addAdvancement("quest_ram", "Consummate Baaahs", "Give the %s what it is missing");
		this.addAdvancement("magic_map_focus", "With Fire It Writes", "Craft the %s with a %s, %s, and %s");
		this.addAdvancement("magic_map", "I Can See Forever", "Craft the %s");
		this.addAdvancement("maze_map", "And Now, to Find the Exit", "Craft the %s after obtaining the focus from the labyrinth");
		this.addAdvancement("ore_map", "How Can That Be Worth It?", "Craft the %s");
		this.addAdvancement("twilight_hunter", "The Silence of the Forest", "Hunt some of the local wildlife");
		this.addAdvancement("kill_naga", "Time To Even The Scales", "Slay the %s in its forest courtyard and obtain a %s to overcome the barrier magic surrounding the Lich's tower");
		this.addAdvancement("naga_armors", "Naga Armorer", "Craft both %s chest and leg armor");
		this.addAdvancement("kill_lich", "Bring Out Your Dead", "Slay the %s at top of his tower and retrieve a scepter to clear poisonous mosquitoes from the Swamp, see through blinding darkness of the Dark Forest's curse, and resist the Snowy Forest's chill");
		this.addAdvancement("lich_scepters", "By Our Powers Combined!", "Acquire all four scepters of power");
		this.addAdvancement("full_mettle_alchemist", "Full Mettle Alchemist", "Drink four doses of Harming II from a potion flask in under 8 seconds and survive");
		this.addAdvancement("progress_labyrinth", "Mighty Stroganoff", "Eat the Meef Stroganoff to acclimatize your body to the dangerous heat of the Fire Swamp");
		this.addAdvancement("mazebreaker", "Breaking the Maze", "Find the %s pickaxe in the secret labyrinth vault");
		this.addAdvancement("kill_hydra", "Hydra Slayer", "Defeat the mighty %s and empower yourself");
		this.addAdvancement("hydra_chop", "Hydra Chop, Baby!", "Chow down on a %s when your hunger bar is depleted");
		this.addAdvancement("progress_trophy_pedestal", "Trophied Champion", "Claim your title by placing a trophy on the pedestal in the Dark Forest ruins");
		this.addAdvancement("progress_knights", "Carminite Acclimation", "Settle the restless phantoms in the knight's tomb and the carminite tower's devices will obey you");
		this.addAdvancement("ghast_trap", "Something Strange in Towerwood", "Slay the %ss around a %s and activate to wrench the %s from the sky!");
		this.addAdvancement("progress_ur_ghast", "Tears of Fire", "Touch the fiery red tears of the %s");
		this.addAdvancement("experiment_115", "Mystery Meat?", "It looks like cake, though...");
		this.addAdvancement("experiment_115_2", "Making a note: Huge Success!", "It's so delicious and moist!");
		this.addAdvancement("experiment_115_3", "Eating 115 Everyday, 115 Years, Forever", "eating experiment one-hundred-fifteen all day, forever, one-hundred-fifteen times experiment one-hundred-fifteen, experiment one-hundred-fifteen dot com, double-u double-u double-u dot experiment one-hundred-fifteen dot com, one-hundred-fifteen years, every minute experiment one-hundred-fifteen dot com, double-u double-u double-u dot one-hundred-fifteen times experiment one-hundred-fifteen dot com");
		this.addAdvancement("progress_yeti", "Alpha Fur", "Line your garments with the soft fur from the %s, keeping you safe from the Glacier's cold");
		this.addAdvancement("arctic_dyed", "Getting in Fashion", "Dye four unique pieces of Arctic armor");
		this.addAdvancement("progress_glacier", "Clear Skies", "Defeat the %s atop the Aurora Palace");
		this.addAdvancement("glass_sword", "One Hit Wonder", "Hope you made good use of that.");
		this.addAdvancement("fiery_set", "Gallons of Blood and Tears", "Wield a fiery tool or weapon while having at least one piece of fiery armor in your inventory");
		this.addAdvancement("progress_merge", "Ultimate Showdown", "Slay the %s, %s, and %s to clear the acid rain and embolden yourself for the Highlands");
		this.addAdvancement("troll", "We Do a Little Trolling", "Find and kill a %s");
		this.addAdvancement("beanstalk", "Jack and the Beanstalk", "Obtain some %s in the troll caves and use them on the glowing soil beneath the clouds");
		this.addAdvancement("giants", "I'm on Cloud Nine", "Kill a %s in the clouds, retrieve a %s, and return back to the troll caves");
		this.addAdvancement("progress_troll", "I Wish For More Burning", "Find the %s in the troll caves, and you can burn away the thorn barriers");
		this.addAdvancement("progression_end", "End of Progression", "Anything past this point in the Highlands is a work in progress. It will be finished in the future");
		//this.addAdvancement("progress_thorns", "Past the Thorns [NYI]", "Make it past the Thornlands, and unlock the door of the castle");
		//this.addAdvancement("progress_castle", "So Castle Very Wow [NYI]", "What could even be in that castle?!?");
		this.addAdvancement("twilight_dining", "We Dine At Eternal Sundown", "Eat every edible item exclusive to Twilight Forest");
		this.addAdvancement("arborist", "Maniacal Dendrologist", "Get your axes and shears ready. Search every nook and cranny and get anything and everything that comes from trees! Craft, loot, obtain every slab... sapling... leaves... more... everything. ALL OF IT!");

		this.addMessage("advancement_hidden", "<Hidden Advancement>");
		this.addMessage("advancement_required", "Advancement Required:");
		this.addMessage("biome_locked", "Biome Locked!");
		this.addMessage("biome_locked_2", "Check your advancements");
		this.addMessage("core_disabled", "%s is disabled via config");
		this.addMessage("firefly_spawner_radius", "Firefly Particle Radius: %s Blocks");
		this.addMessage("magic_map_fail", "The Magic faltered. Perhaps it doesn't work here?");
		this.addMessage("nyi", "This feature has effects that are not yet implemented.");
		this.addMessage("ore_meter_exposed", "Exposed: %s");
		this.addMessage("ore_meter_range", "Radius: %s, Origin: [%s, %s]");
		this.addMessage("pedestal_ineligible", "You are unworthy.");
		this.addMessage("portal_unsafe", "It doesn't seem safe here...");
		this.addMessage("portal_unworthy", "The Portal pool is unresponsive. Perhaps something was neglected?");
		this.addMessage("wip0", "This feature is a work in progress and may have bugs or unintended effects that may damage your world.");
		this.addMessage("wip1", "Use with caution.");

		this.addBlock(TFBlocks.CICADA, "Cicada");
		this.addBlock(TFBlocks.CICADA_JAR, "Cicada Jar");
		this.addBlock(TFBlocks.FIREFLY, "Firefly");
		this.addBlock(TFBlocks.FIREFLY_JAR, "Firefly Jar");
		this.addBlock(TFBlocks.MOONWORM, "Moonworm");
		this.addBlock(TFBlocks.FIREFLY_SPAWNER, "Firefly Particle Spawner");

		this.addBlock(TFBlocks.TWILIGHT_PORTAL, "Twilight Forest Portal");
		this.addBlock(TFBlocks.TWILIGHT_PORTAL_MINIATURE_STRUCTURE, "Miniature Twilight Forest Portal");

		this.addBlock(TFBlocks.NAGASTONE, "Nagastone");
		this.addBlock(TFBlocks.NAGASTONE_HEAD, "Nagastone Head");
		this.addStoneVariants("etched_nagastone", "Etched Nagastone");
		this.addStoneVariants("nagastone_pillar", "Nagastone Pillar");
		this.addBlock(TFBlocks.NAGASTONE_STAIRS_LEFT, "Nagastone Stairs (Left)");
		this.addBlock(TFBlocks.NAGASTONE_STAIRS_RIGHT, "Nagastone Stairs (Right)");
		this.addBlock(TFBlocks.CRACKED_NAGASTONE_STAIRS_LEFT, "Cracked Nagastone Stairs (Left)");
		this.addBlock(TFBlocks.CRACKED_NAGASTONE_STAIRS_RIGHT, "Cracked Nagastone Stairs (Right)");
		this.addBlock(TFBlocks.MOSSY_NAGASTONE_STAIRS_LEFT, "Mossy Nagastone Stairs (Left)");
		this.addBlock(TFBlocks.MOSSY_NAGASTONE_STAIRS_RIGHT, "Mossy Nagastone Stairs (Right)");
		this.addBlock(TFBlocks.SPIRAL_BRICKS, "Spiral Bricks");
		this.addBlock(TFBlocks.NAGA_COURTYARD_MINIATURE_STRUCTURE, "Miniature Naga Courtyard");

		this.addBlock(TFBlocks.TWISTED_STONE, "Twisted Stone");
		this.addBlock(TFBlocks.TWISTED_STONE_PILLAR, "Twisted Stone Pillar");
		this.addBlock(TFBlocks.BOLD_STONE_PILLAR, "Bold Stone Pillar");
		this.addBlock(TFBlocks.CANDELABRA, "Candelabra");
		this.addBlock(TFBlocks.KEEPSAKE_CASKET, "Keepsake Casket");
		this.add("block.twilightforest.casket.broken", "Your Keepsake Casket was too damaged to hold any more items. All items that would be stored in your casket were dropped on the ground.");
		this.add("block.twilightforest.casket.locked", "This Casket can only be opened by %s!");
		this.addBlock(TFBlocks.LICH_TOWER_MINIATURE_STRUCTURE, "Miniature Lich Tower");

		this.addBlock(TFBlocks.HUGE_LILY_PAD, "Huge Lily Pad");
		this.addBlock(TFBlocks.HUGE_WATER_LILY, "Huge Water Lily");
		this.addBlock(TFBlocks.MAZESTONE, "Mazestone");
		this.addBlock(TFBlocks.MAZESTONE_BRICK, "Mazestone Brick");
		this.addBlock(TFBlocks.CRACKED_MAZESTONE, "Cracked Mazestone Brick");
		this.addBlock(TFBlocks.MOSSY_MAZESTONE, "Mossy Mazestone Brick");
		this.addBlock(TFBlocks.CUT_MAZESTONE, "Cut Mazestone");
		this.addBlock(TFBlocks.DECORATIVE_MAZESTONE, "Decorative Mazestone Brick");
		this.addBlock(TFBlocks.MAZESTONE_MOSAIC, "Mazestone Mosaic");
		this.addBlock(TFBlocks.MAZESTONE_BORDER, "Mazestone Border");
		this.addBlock(TFBlocks.RED_THREAD, "Red Thread");

		this.addBlock(TFBlocks.SMOKER, "Smoker");
		this.addBlock(TFBlocks.FIRE_JET, "Fire Jet");
		this.addBlock(TFBlocks.ENCASED_SMOKER, "Encased Smoker");
		this.addBlock(TFBlocks.ENCASED_FIRE_JET, "Encased Fire Jet");

		this.addBlock(TFBlocks.TROPHY_PEDESTAL, "Trophy Pedestal");
		this.addBlock(TFBlocks.STRONGHOLD_SHIELD, "Stronghold Shield");
		this.addBlock(TFBlocks.UNDERBRICK, "Underbrick");
		this.addBlock(TFBlocks.CRACKED_UNDERBRICK, "Cracked Underbrick");
		this.addBlock(TFBlocks.MOSSY_UNDERBRICK, "Mossy Underbrick");
		this.addBlock(TFBlocks.UNDERBRICK_FLOOR, "Underbrick Floor");

		this.addBlock(TFBlocks.TOWERWOOD, "Towerwood Planks");
		this.addBlock(TFBlocks.CRACKED_TOWERWOOD, "Cracked Towerwood Planks");
		this.addBlock(TFBlocks.MOSSY_TOWERWOOD, "Mossy Towerwood Planks");
		this.addBlock(TFBlocks.INFESTED_TOWERWOOD, "Infested Towerwood Planks");
		this.addBlock(TFBlocks.ENCASED_TOWERWOOD, "Encased Towerwood Planks");
		this.addBlock(TFBlocks.VANISHING_BLOCK, "Vanishing Block");
		this.addBlock(TFBlocks.REAPPEARING_BLOCK, "Reappearing Block");
		this.addBlock(TFBlocks.LOCKED_VANISHING_BLOCK, "Locked Vanishing Block");
		this.addBlock(TFBlocks.CARMINITE_BUILDER, "Carminite Builder");
		this.addBlock(TFBlocks.BUILT_BLOCK, "Built Block");
		this.addBlock(TFBlocks.ANTIBUILDER, "Antibuilder");
		this.addBlock(TFBlocks.ANTIBUILT_BLOCK, "Antibuilt Block");
		this.addBlock(TFBlocks.GHAST_TRAP, "Ghast Trap");
		this.addBlock(TFBlocks.CARMINITE_REACTOR, "Carminite Reactor");
		this.addBlock(TFBlocks.REACTOR_DEBRIS, "Reactor Debris");
		this.addBlock(TFBlocks.FAKE_DIAMOND, "Diamond Block");
		this.addBlock(TFBlocks.FAKE_GOLD, "Gold Block");
		this.addBlock(TFBlocks.EXPERIMENT_115, "Experiment 115");

		this.addBlock(TFBlocks.AURORA_BLOCK, "Aurora Block");
		this.addBlock(TFBlocks.AURORA_SLAB, "Aurora Slab");
		this.addBlock(TFBlocks.AURORA_PILLAR, "Aurora Pillar");
		this.addBlock(TFBlocks.AURORALIZED_GLASS, "Auroralized Glass");

		this.addBlock(TFBlocks.HUGE_MUSHGLOOM, "Huge Mushgloom Block");
		this.addBlock(TFBlocks.HUGE_MUSHGLOOM_STEM, "Huge Mushgloom Stem");
		this.addBlock(TFBlocks.HUGE_STALK, "Huge Stalk");
		this.addBlock(TFBlocks.BEANSTALK_LEAVES, "Beanstalk Leaves");
		this.addBlock(TFBlocks.UBEROUS_SOIL, "Uberous Soil");
		this.addBlock(TFBlocks.TROLLVIDR, "Trollvidr");
		this.addBlock(TFBlocks.UNRIPE_TROLLBER, "Unripe Trollber");
		this.addBlock(TFBlocks.TROLLBER, "Trollber");
		this.addBlock(TFBlocks.TROLLSTEINN, "Trollsteinn");
		this.addBlock(TFBlocks.FLUFFY_CLOUD, "Fluffy Cloud");
		this.addBlock(TFBlocks.WISPY_CLOUD, "Wispy Cloud");
		this.addBlock(TFBlocks.GIANT_LEAVES, "Giant Leaves");
		this.addBlock(TFBlocks.GIANT_LOG, "Giant Log");
		this.addBlock(TFBlocks.GIANT_COBBLESTONE, "Giant Cobblestone");
		this.addBlock(TFBlocks.GIANT_OBSIDIAN, "Giant Obsidian");

		this.addBlock(TFBlocks.BROWN_THORNS, "Thorns");
		this.addBlock(TFBlocks.GREEN_THORNS, "Green Thorns");
		this.addBlock(TFBlocks.BURNT_THORNS, "Burnt Thorns");
		this.addBlock(TFBlocks.THORN_ROSE, "Thorn Rose");
		this.addBlock(TFBlocks.THORN_LEAVES, "Thorn Leaves");
		this.addBlock(TFBlocks.DEADROCK, "Deadrock");
		this.addBlock(TFBlocks.CRACKED_DEADROCK, "Cracked Deadrock");
		this.addBlock(TFBlocks.WEATHERED_DEADROCK, "Weathered Deadrock");

		this.addStoneVariants("castle_brick", "Castle Brick");
		this.addStoneVariants("castle_brick_stairs", "Castle Brick Stairs");
		this.add(TFBlocks.WORN_CASTLE_BRICK.get(), "Worn Castle Brick");
		this.add(TFBlocks.THICK_CASTLE_BRICK.get(), "Thick Castle Brick");
		this.add(TFBlocks.CASTLE_ROOF_TILE.get(), "Castle Roof Tile");
		this.add(TFBlocks.ENCASED_CASTLE_BRICK_PILLAR.get(), "Encased Castle Brick Pillar");
		this.add(TFBlocks.ENCASED_CASTLE_BRICK_TILE.get(), "Encased Castle Brick Tile");
		this.add(TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get(), "Bold Castle Brick Pillar");
		this.add(TFBlocks.BOLD_CASTLE_BRICK_TILE.get(), "Bold Castle Brick Tile");
		this.add(TFBlocks.WORN_CASTLE_BRICK_STAIRS.get(), "Worn Castle Brick Stairs");
		this.add(TFBlocks.ENCASED_CASTLE_BRICK_STAIRS.get(), "Encased Castle Brick Stairs");
		this.add(TFBlocks.BOLD_CASTLE_BRICK_STAIRS.get(), "Bold Castle Brick Stairs");

		this.add(TFBlocks.PINK_CASTLE_RUNE_BRICK.get(), "Magenta Castle Rune Brick");
		this.add(TFBlocks.YELLOW_CASTLE_RUNE_BRICK.get(), "Yellow Castle Rune Brick");
		this.add(TFBlocks.BLUE_CASTLE_RUNE_BRICK.get(), "Blue Castle Rune Brick");
		this.add(TFBlocks.VIOLET_CASTLE_RUNE_BRICK.get(), "Violet Castle Rune Brick");

		this.add(TFBlocks.PINK_CASTLE_DOOR.get(), "Magenta Castle Door");
		this.add(TFBlocks.YELLOW_CASTLE_DOOR.get(), "Yellow Castle Door");
		this.add(TFBlocks.BLUE_CASTLE_DOOR.get(), "Blue Castle Door");
		this.add(TFBlocks.VIOLET_CASTLE_DOOR.get(), "Violet Castle Door");

		this.add(TFBlocks.PINK_FORCE_FIELD.get(), "Magenta Force Field");
		this.add(TFBlocks.ORANGE_FORCE_FIELD.get(), "Orange Force Field");
		this.add(TFBlocks.GREEN_FORCE_FIELD.get(), "Green Force Field");
		this.add(TFBlocks.BLUE_FORCE_FIELD.get(), "Blue Force Field");
		this.add(TFBlocks.VIOLET_FORCE_FIELD.get(), "Violet Force Field");

		this.addBlock(TFBlocks.IRONWOOD_BLOCK, "Block of Ironwood");
		this.addBlock(TFBlocks.STEELEAF_BLOCK, "Block of Steeleaf");
		this.addBlock(TFBlocks.KNIGHTMETAL_BLOCK, "Block of Knightmetal");
		this.add("block.twilightforest.knightmetal_block.desc", "Works as a stronger cactus");
		this.addBlock(TFBlocks.ARCTIC_FUR_BLOCK, "Block of Arctic Fur");
		this.add("block.twilightforest.arctic_fur_block.desc", "Reduces fall damage by 90%");
		this.addBlock(TFBlocks.FIERY_BLOCK, "Block of Fiery Metal");
		this.addBlock(TFBlocks.CARMINITE_BLOCK, "Block of Carminite");

		this.addBlock(TFBlocks.CINDER_FURNACE, "Cinder Furnace");
		this.addBlock(TFBlocks.CINDER_LOG, "Cinder Log");
		this.addBlock(TFBlocks.CINDER_WOOD, "Cinder Wood");

		this.addBlock(TFBlocks.IRON_LADDER, "Iron Ladder");
		this.addBlock(TFBlocks.SLIDER, "Slide Trap");

		this.addBlock(TFBlocks.TWILIGHT_OAK_LEAVES, "Twilight Oak Leaves");
		this.addSapling("twilight_oak", "Sickly Twilight Oak Sapling");
		this.createLogs("twilight_oak", "Twilight Oak");
		this.createWoodSet("twilight_oak", "Twilight Oak");

		this.addBlock(TFBlocks.CANOPY_LEAVES, "Canopy Tree Leaves");
		this.addSapling("canopy", "Canopy Tree Sapling");
		this.createLogs("canopy", "Canopy Tree");
		this.createWoodSet("canopy", "Canopy");
		this.addBlock(TFBlocks.CANOPY_BOOKSHELF, "Canopy Bookshelf");
		this.addBlock(TFBlocks.EMPTY_CANOPY_BOOKSHELF, "Empty Canopy Bookshelf");
		this.addBlock(TFBlocks.DEATH_TOME_SPAWNER, "Death Tome Spawner");

		this.addBlock(TFBlocks.MANGROVE_LEAVES, "Mangrove Leaves");
		this.addSapling("mangrove", "Mangrove Sapling");
		this.createLogs("mangrove", "Mangrove");
		this.createWoodSet("mangrove", "Mangrove");

		this.addBlock(TFBlocks.DARK_LEAVES, "Darkwood Leaves");
		this.addBlock(TFBlocks.HARDENED_DARK_LEAVES, "Thick Darkwood Leaves");
		this.addSapling("darkwood", "Darkwood Sapling");
		this.createLogs("dark", "Darkwood");
		this.createWoodSet("dark", "Darkwood");

		this.addBlock(TFBlocks.TIME_LOG_CORE, "Timewood Clock");
		this.addBlock(TFBlocks.TIME_LEAVES, "Timewood Leaves");
		this.addSapling("time", "Tree of Time Sapling");
		this.createLogs("time", "Timewood");
		this.createWoodSet("time", "Timewood");

		this.addBlock(TFBlocks.TRANSFORMATION_LOG_CORE, "Heart of Transformation");
		this.addBlock(TFBlocks.TRANSFORMATION_LEAVES, "Leaves of Transformation");
		this.addSapling("transformation", "Tree of Transformation Sapling");
		this.createLogs("transformation", "Transwood");
		this.createWoodSet("transformation", "Transwood");

		this.addBlock(TFBlocks.MINING_LOG_CORE, "Minewood Core");
		this.addBlock(TFBlocks.MINING_LEAVES, "Miner's Leaves");
		this.addSapling("mining", "Miner's Tree Sapling");
		this.createLogs("mining", "Minewood");
		this.createWoodSet("mining", "Minewood");

		this.addBlock(TFBlocks.SORTING_LOG_CORE, "Sortingwood Engine");
		this.addBlock(TFBlocks.SORTING_LEAVES, "Sorting Leaves");
		this.addSapling("sorting", "Sorting Tree Sapling");
		this.createLogs("sorting", "Sortingwood");
		this.createWoodSet("sorting", "Sortingwood");

		this.addBlock(TFBlocks.RAINBOW_OAK_LEAVES, "Rainbow Oak Leaves");
		this.addSapling("rainbow_oak", "Rainbow Oak Sapling");
		this.addSapling("hollow_oak", "Robust Twilight Oak Sapling");

		this.addBlock(TFBlocks.OAK_BANISTER, "Oak Banister");
		this.addBlock(TFBlocks.SPRUCE_BANISTER, "Spruce Banister");
		this.addBlock(TFBlocks.BIRCH_BANISTER, "Birch Banister");
		this.addBlock(TFBlocks.JUNGLE_BANISTER, "Jungle Banister");
		this.addBlock(TFBlocks.ACACIA_BANISTER, "Acacia Banister");
		this.addBlock(TFBlocks.DARK_OAK_BANISTER, "Dark Oak Banister");
		this.addBlock(TFBlocks.CRIMSON_BANISTER, "Crimson Banister");
		this.addBlock(TFBlocks.WARPED_BANISTER, "Warped Banister");
		this.addBlock(TFBlocks.VANGROVE_BANISTER, "Mangrove Banister");

		this.createHollowLogs("oak", "Oak", false);
		this.createHollowLogs("spruce", "Spruce", false);
		this.createHollowLogs("birch", "Birch", false);
		this.createHollowLogs("jungle", "Jungle", false);
		this.createHollowLogs("acacia", "Acacia", false);
		this.createHollowLogs("dark_oak", "Dark Oak", false);
		this.createHollowLogs("crimson", "Crimson", true);
		this.createHollowLogs("warped", "Warped", true);
		this.createHollowLogs("vangrove", "Mangrove", false);
		this.add("block.twilightforest.banister.cycle", "Right click with an axe to cycle");

		this.addBlock(TFBlocks.MOSS_PATCH, "Moss Patch");
		this.addBlock(TFBlocks.CLOVER_PATCH, "Clover Patch");
		this.addBlock(TFBlocks.TORCHBERRY_PLANT, "Torchberry Plant");
		this.addBlock(TFBlocks.ROOT_STRAND, "Root Strands");
		this.addBlock(TFBlocks.FALLEN_LEAVES, "Fallen Leaves");
		this.addBlock(TFBlocks.MAYAPPLE, "Mayapple");
		this.addBlock(TFBlocks.POTTED_MAYAPPLE, "Potted Mayapple");
		this.addBlock(TFBlocks.FIDDLEHEAD, "Mayapple");
		this.addBlock(TFBlocks.POTTED_FIDDLEHEAD, "Potted Mayapple");
		this.addBlock(TFBlocks.MUSHGLOOM, "Mushgloom");
		this.addBlock(TFBlocks.POTTED_MUSHGLOOM, "Potted Mushgloom");
		this.addBlock(TFBlocks.POTTED_THORN, "Potted Thorn");
		this.addBlock(TFBlocks.POTTED_GREEN_THORN, "Potted Green Thorn");
		this.addBlock(TFBlocks.POTTED_DEAD_THORN, "Potted Burnt Thorn");

		this.addBlock(TFBlocks.HEDGE, "Hedge");
		this.addBlock(TFBlocks.ROOT_BLOCK, "Roots");
		this.addBlock(TFBlocks.LIVEROOT_BLOCK, "Liveroots");
		this.addBlock(TFBlocks.MANGROVE_ROOT, "Mangrove Roots");

		this.addBlock(TFBlocks.NAGA_TROPHY, "Naga Trophy");
		this.addBlock(TFBlocks.LICH_TROPHY, "Lich Trophy");
		this.addBlock(TFBlocks.MINOSHROOM_TROPHY, "Minoshroom Trophy");
		this.addBlock(TFBlocks.HYDRA_TROPHY, "Hydra Trophy");
		this.addBlock(TFBlocks.KNIGHT_PHANTOM_TROPHY, "Knight Phantom Trophy");
		this.addBlock(TFBlocks.UR_GHAST_TROPHY, "Ur-Ghast Trophy");
		this.addBlock(TFBlocks.ALPHA_YETI_TROPHY, "Alpha Yeti Trophy");
		this.addBlock(TFBlocks.SNOW_QUEEN_TROPHY, "Snow Queen Trophy");
		this.addBlock(TFBlocks.QUEST_RAM_TROPHY, "Quest Ram Trophy");

		this.addBlock(TFBlocks.NAGA_BOSS_SPAWNER, "Naga Boss Spawner");
		this.addBlock(TFBlocks.LICH_BOSS_SPAWNER, "Lich Boss Spawner");
		this.addBlock(TFBlocks.MINOSHROOM_BOSS_SPAWNER, "Minoshroom Boss Spawner");
		this.addBlock(TFBlocks.HYDRA_BOSS_SPAWNER, "Hydra Boss Spawner");
		this.addBlock(TFBlocks.KNIGHT_PHANTOM_BOSS_SPAWNER, "Knight Phantom Boss Spawner");
		this.addBlock(TFBlocks.UR_GHAST_BOSS_SPAWNER, "Ur-Ghast Boss Spawner");
		this.addBlock(TFBlocks.ALPHA_YETI_BOSS_SPAWNER, "Alpha Yeti Boss Spawner");
		this.addBlock(TFBlocks.SNOW_QUEEN_BOSS_SPAWNER, "Snow Queen Boss Spawner");
		this.addBlock(TFBlocks.FINAL_BOSS_BOSS_SPAWNER, "Final Boss Boss Spawner");

		this.addBannerPattern("naga", "Naga Scales");
		this.addBannerPattern("lich", "Lich Crown");
		this.addBannerPattern("minoshroom", "Minoshroom Axes");
		this.addBannerPattern("hydra", "Hydra Flame");
		this.addBannerPattern("knight_phantom", "Knight Helmet");
		this.addBannerPattern("ur_ghast", "Carminite Border");
		this.addBannerPattern("alpha_yeti", "Alpha Yeti Face");
		this.addBannerPattern("snow_queen", "Snow Queen Crown");
		this.addBannerPattern("quest_ram", "Quest Ram Swirls");

		this.addBlock(TFBlocks.ZOMBIE_SKULL_CANDLE, "Zombie Skull Candle");
		this.addBlock(TFBlocks.ZOMBIE_WALL_SKULL_CANDLE, "Zombie Wall Skull Candle");
		this.addBlock(TFBlocks.SKELETON_SKULL_CANDLE, "Skeleton Skull Candle");
		this.addBlock(TFBlocks.SKELETON_WALL_SKULL_CANDLE, "Skeleton Wall Skull Candle");
		this.addBlock(TFBlocks.WITHER_SKELE_SKULL_CANDLE, "Wither Skeleton Skull Candle");
		this.addBlock(TFBlocks.WITHER_SKELE_WALL_SKULL_CANDLE, "Wither Skeleton Wall Skull Candle");
		this.addBlock(TFBlocks.CREEPER_SKULL_CANDLE, "Creeper Skull Candle");
		this.addBlock(TFBlocks.CREEPER_WALL_SKULL_CANDLE, "Creeper Wall Skull Candle");
		this.addBlock(TFBlocks.PLAYER_SKULL_CANDLE, "Player Skull Candle");
		this.add("block.twilightforest.player_skull_candle.named", "%s's Head with Candles");
		this.addBlock(TFBlocks.PLAYER_WALL_SKULL_CANDLE, "Player Wall Skull Candle");

		this.addBlock(TFBlocks.UNCRAFTING_TABLE, "Uncrafting Table");
		this.add("container.twilightforest.uncrafting_table", "Uncrafting Table");
		this.add("container.twilightforest.uncrafting_table.disabled", "Uncrafting is disabled via config");
		this.add("container.twilightforest.uncrafting_table.disabled_item", "Uncrafting this item is disabled.");

		this.addItem(TFItems.NAGA_SCALE, "Naga Scale");
		this.addItem(TFItems.NAGA_CHESTPLATE, "Naga Scale Tunic");
		this.addItem(TFItems.NAGA_LEGGINGS, "Naga Scale Leggings");
		this.addItem(TFItems.FORTIFICATION_SCEPTER, "Scepter of Fortification");
		this.addItem(TFItems.LIFEDRAIN_SCEPTER, "Scepter of Life Draining");
		this.addItem(TFItems.TWILIGHT_SCEPTER, "Scepter of Twilight");
		this.addItem(TFItems.ZOMBIE_SCEPTER, "Zombie Scepter");
		this.add("item.twilightforest.scepter.desc", "%s charges left");
		this.addItem(TFItems.BRITTLE_FLASK, "Brittle Potion Flask");
		this.addItem(TFItems.GREATER_FLASK, "Greater Potion Flask");
		this.add("item.twilightforest.flask.doses", "Doses: %s/%s");
		this.add("item.twilightforest.flask.no_refill", "Cannot be refilled");

		this.addItem(TFItems.ORE_METER, "Ore Meter");
		this.addItem(TFItems.FILLED_MAGIC_MAP, "Magic Map");
		this.addItem(TFItems.FILLED_MAZE_MAP, "Maze Map");
		this.addItem(TFItems.FILLED_ORE_MAP, "Maze/Ore Map");
		this.addItem(TFItems.MAGIC_MAP, "Blank Magic Map");
		this.addItem(TFItems.MAZE_MAP, "Blank Maze Map");
		this.addItem(TFItems.ORE_MAP, "Blank Maze/Ore Map");
		this.addItem(TFItems.RAVEN_FEATHER, "Raven Feather");
		this.addItem(TFItems.MAGIC_MAP_FOCUS, "Magic Map Focus");
		this.addItem(TFItems.MAZE_MAP_FOCUS, "Maze Map Focus");

		this.addItem(TFItems.LIVEROOT, "Liveroot");
		this.addItem(TFItems.RAW_IRONWOOD, "Raw Ironwood");
		this.addItem(TFItems.IRONWOOD_INGOT, "Ironwood Ingot");
		this.addArmor("ironwood", "Ironwood");
		this.addTools("ironwood", "Ironwood");

		this.addItem(TFItems.STEELEAF_INGOT, "Steeleaf");
		this.addArmor("steeleaf", "Steeleaf");
		this.addTools("steeleaf", "Steeleaf");

		this.addItem(TFItems.ARMOR_SHARD, "Armor Shard");
		this.addItem(TFItems.ARMOR_SHARD_CLUSTER, "Armor Shard Cluster");
		this.addItem(TFItems.KNIGHTMETAL_INGOT, "Knightmetal Ingot");
		this.addItem(TFItems.KNIGHTMETAL_HELMET, "Knightmetal Helmet");
		this.addItem(TFItems.KNIGHTMETAL_CHESTPLATE, "Knightmetal Chestplate");
		this.addItem(TFItems.KNIGHTMETAL_LEGGINGS, "Knightmetal Greaves");
		this.addItem(TFItems.KNIGHTMETAL_BOOTS, "Knightmetal Boots");
		this.addItem(TFItems.KNIGHTMETAL_SWORD, "Knightmetal Sword");
		this.add("item.twilightforest.knightmetal_sword.desc", "Extra damage to armored targets");
		this.addItem(TFItems.KNIGHTMETAL_PICKAXE, "Knightmetal Pickaxe");
		this.add("item.twilightforest.knightmetal_pickaxe.desc", "Extra damage to armored targets");
		this.addItem(TFItems.KNIGHTMETAL_AXE, "Knightmetal Axe");
		this.add("item.twilightforest.knightmetal_axe.desc", "Extra damage to unarmored targets");
		this.addItem(TFItems.KNIGHTMETAL_RING, "Knightmetal Loop");
		this.addItem(TFItems.KNIGHTMETAL_SHIELD, "Knightmetal Shield");
		this.addItem(TFItems.BLOCK_AND_CHAIN, "Block and Chain");
		this.addItem(TFItems.PHANTOM_HELMET, "Phantom Helmet");
		this.addItem(TFItems.PHANTOM_CHESTPLATE, "Phantom Chestplate");
		this.add("item.twilightforest.phantom_armor.desc", "Is never lost on death");

		this.addItem(TFItems.FIERY_BLOOD, "Fiery Blood");
		this.addItem(TFItems.FIERY_TEARS, "Fiery Tears");
		this.addItem(TFItems.FIERY_INGOT, "Fiery Ingot");
		this.addArmor("fiery", "Fiery");
		this.add("item.twilightforest.fiery_armor.desc", "Burns attackers");
		this.addItem(TFItems.FIERY_SWORD, "Fiery Sword");
		this.add("item.twilightforest.fiery_sword.desc", "Burns targets");
		this.addItem(TFItems.FIERY_PICKAXE, "Fiery Pickaxe");
		this.add("item.twilightforest.fiery_pickaxe.desc", "Auto-smelting");

		this.addItem(TFItems.ARCTIC_FUR, "Arctic Fur");
		this.addItem(TFItems.ARCTIC_HELMET, "Arctic Hood");
		this.addItem(TFItems.ARCTIC_CHESTPLATE, "Arctic Jacket");
		this.addItem(TFItems.ARCTIC_LEGGINGS, "Arctic Leggings");
		this.addItem(TFItems.ARCTIC_BOOTS, "Arctic Boots");
		this.add("item.twilightforest.arctic_armor.desc", "Dyeable");

		this.addItem(TFItems.ALPHA_YETI_FUR, "Alpha Yeti Fur");
		this.addItem(TFItems.YETI_HELMET, "Yeti Horned Helm");
		this.addItem(TFItems.YETI_CHESTPLATE, "Yeti Jacket");
		this.addItem(TFItems.YETI_LEGGINGS, "Yeti Leggings");
		this.addItem(TFItems.YETI_BOOTS, "Yeti Boots");
		this.add("item.twilightforest.yeti_armor.desc", "Chills attackers");


		this.addItem(TFItems.DIAMOND_MINOTAUR_AXE, "Diamond Minotaur Axe");
		this.addItem(TFItems.GOLDEN_MINOTAUR_AXE, "Golden Minotaur Axe");
		this.add("item.twilightforest.minotaur_axe.desc", "Extra damage while charging");
		this.addItem(TFItems.MAZEBREAKER_PICKAXE, "Mazebreaker");
		this.addItem(TFItems.ICE_SWORD, "Ice Sword");
		this.addItem(TFItems.ICE_BOMB, "Ice Bomb");
		this.addItem(TFItems.GLASS_SWORD, "Glass Sword");
		this.add("item.twilightforest.glass_sword.desc", "Creative Mode only");
		this.addItem(TFItems.TRIPLE_BOW, "Tri-Bow");
		this.addItem(TFItems.SEEKER_BOW, "Seeker Bow");
		this.addItem(TFItems.ICE_BOW, "Ice Bow");
		this.addItem(TFItems.ENDER_BOW, "Ender Bow");
		this.addItem(TFItems.GIANT_SWORD, "Giant's Sword");
		this.addItem(TFItems.GIANT_PICKAXE, "Giant's Pickaxe");
		this.add("item.twilightforest.giant_pickaxe.desc", "Breaks giant blocks");
		this.addItem(TFItems.LAMP_OF_CINDERS, "Lamp of Cinders");
		this.addItem(TFItems.CUBE_OF_ANNIHILATION, "Cube of Annihilation");

		this.addItem(TFItems.MOON_DIAL, "Moon Dial");
		this.add("item.twilightforest.moon_dial.phase_0", "Full Moon");
		this.add("item.twilightforest.moon_dial.phase_1", "Waning Gibbous");
		this.add("item.twilightforest.moon_dial.phase_2", "Third Quarter");
		this.add("item.twilightforest.moon_dial.phase_3", "Waning Crescent");
		this.add("item.twilightforest.moon_dial.phase_4", "New Moon");
		this.add("item.twilightforest.moon_dial.phase_5", "Waxing Crescent");
		this.add("item.twilightforest.moon_dial.phase_6", "First Quarter");
		this.add("item.twilightforest.moon_dial.phase_7", "Waxing Gibbous");
		this.add("item.twilightforest.moon_dial.phase_unknown", "Moon phase indeterminate");
		this.add("item.twilightforest.moon_dial.phase_unknown_fools", "404 moon not found");

		this.addItem(TFItems.ORE_MAGNET, "Ore Magnet");
		this.addItem(TFItems.CRUMBLE_HORN, "Crumble Horn");
		this.addItem(TFItems.MOONWORM_QUEEN, "Moonworm Queen");
		this.addItem(TFItems.PEACOCK_FEATHER_FAN, "Peacock Feather Fan");
		this.addItem(TFItems.TRANSFORMATION_POWDER, "Transformation Powder");
		this.addItem(TFItems.TOWER_KEY, "Tower Key");
		this.addItem(TFItems.BORER_ESSENCE, "Borer Essence");
		this.addItem(TFItems.CARMINITE, "Carminite");
		this.addItem(TFItems.MAGIC_BEANS, "Magic Beans");
		this.addItem(TFItems.CUBE_TALISMAN, "Talisman of the Cube");
		this.add("item.twilightforest.skull_candle.desc", "Has: %s %s Candle");
		this.add("item.twilightforest.skull_candle.desc.multiple", "Has: %s %s Candles");

		this.addItem(TFItems.TORCHBERRIES, "Torchberries");
		this.addItem(TFItems.RAW_VENISON, "Raw Venison");
		this.addItem(TFItems.COOKED_VENISON, "Venison Steak");
		this.addItem(TFItems.MAZE_WAFER, "Maze Wafer");
		this.addItem(TFItems.RAW_MEEF, "Raw Meef");
		this.addItem(TFItems.COOKED_MEEF, "Meef Steak");
		this.addItem(TFItems.MEEF_STROGANOFF, "Meef Stroganoff");
		this.addItem(TFItems.HYDRA_CHOP, "Hydra Chop");

		this.addItem(TFItems.CHARM_OF_LIFE_1, "Charm of Life I");
		this.addItem(TFItems.CHARM_OF_LIFE_2, "Charm of Life II");
		this.addItem(TFItems.CHARM_OF_KEEPING_1, "Charm of Keeping I");
		this.addItem(TFItems.CHARM_OF_KEEPING_2, "Charm of Keeping II");
		this.addItem(TFItems.CHARM_OF_KEEPING_3, "Charm of Keeping III");

		this.addMusicDisc(TFItems.MUSIC_DISC_RADIANCE, "Rotch Gwylt - Radiance");
		this.addMusicDisc(TFItems.MUSIC_DISC_STEPS, "Rotch Gwylt - Steps");
		this.addMusicDisc(TFItems.MUSIC_DISC_SUPERSTITIOUS, "Rotch Gwylt - Superstitious");
		this.addMusicDisc(TFItems.MUSIC_DISC_HOME, "MrCompost - Home");
		this.addMusicDisc(TFItems.MUSIC_DISC_WAYFARER, "MrCompost - Wayfarer");
		this.addMusicDisc(TFItems.MUSIC_DISC_FINDINGS, "MrCompost - Findings");
		this.addMusicDisc(TFItems.MUSIC_DISC_MAKER, "MrCompost - Maker");
		this.addMusicDisc(TFItems.MUSIC_DISC_THREAD, "MrCompost - Thread");
		this.addMusicDisc(TFItems.MUSIC_DISC_MOTION, "MrCompost - Motion");

		this.add("item.twilightforest.lower_goblin_knight_spawn_egg", "Goblin Knight Spawn Egg");

		this.addEntityAndEgg(TFEntities.ADHERENT, "Adherent");
		this.addEntityAndEgg(TFEntities.ALPHA_YETI, "Alpha Yeti");
		this.addEntityAndEgg(TFEntities.ARMORED_GIANT, "Armored Giant");
		this.addEntityAndEgg(TFEntities.BIGHORN_SHEEP, "Bighorn Sheep");
		this.addEntityAndEgg(TFEntities.BLOCKCHAIN_GOBLIN, "Block and Chain Goblin");
		this.addEntityAndEgg(TFEntities.BOAR, "Boar");
		this.addEntityAndEgg(TFEntities.CARMINITE_BROODLING, "Carminite Broodling");
		this.addEntityAndEgg(TFEntities.CARMINITE_GHASTGUARD, "Carminite Ghastguard");
		this.addEntityAndEgg(TFEntities.CARMINITE_GHASTLING, "Carminite Ghastling");
		this.addEntityAndEgg(TFEntities.CARMINITE_GOLEM, "Carminite Golem");
		this.addEntityAndEgg(TFEntities.DEATH_TOME, "Death Tome");
		this.addEntityAndEgg(TFEntities.DEER, "Deer");
		this.addEntityAndEgg(TFEntities.DWARF_RABBIT, "Dwarf Rabbit");
		this.addEntityAndEgg(TFEntities.FIRE_BEETLE, "Fire Beetle");
		this.addEntityAndEgg(TFEntities.GIANT_MINER, "Giant Miner");
		this.addEntityAndEgg(TFEntities.HARBINGER_CUBE, "Harbinger Cube");
		this.addEntityAndEgg(TFEntities.HEDGE_SPIDER, "Hedge Spider");
		this.addEntityAndEgg(TFEntities.HELMET_CRAB, "Helmet Crab");
		this.addEntityAndEgg(TFEntities.HOSTILE_WOLF, "Hostile Wolf");
		this.addEntityAndEgg(TFEntities.HYDRA, "Hydra");
		this.addEntityAndEgg(TFEntities.ICE_CRYSTAL, "Ice Crystal");
		this.addEntityAndEgg(TFEntities.KING_SPIDER, "King Spider");
		this.addEntityAndEgg(TFEntities.KNIGHT_PHANTOM, "Knight Phantom");
		this.addEntityAndEgg(TFEntities.KOBOLD, "Kobold");
		this.addEntityAndEgg(TFEntities.LICH, "Lich");
		this.addEntityAndEgg(TFEntities.MAZE_SLIME, "Maze Slime");
		this.addEntityAndEgg(TFEntities.MINOSHROOM, "Minoshroom");
		this.addEntityAndEgg(TFEntities.MINOTAUR, "Minotaur");
		this.addEntityAndEgg(TFEntities.MIST_WOLF, "Mist Wolf");
		this.addEntityAndEgg(TFEntities.MOSQUITO_SWARM, "Mosquito Swarm");
		this.addEntityAndEgg(TFEntities.NAGA, "Naga");
		this.addEntityAndEgg(TFEntities.PENGUIN, "Penguin");
		this.addEntityAndEgg(TFEntities.PINCH_BEETLE, "Pinch Beetle");
		this.addEntityAndEgg(TFEntities.QUEST_RAM, "Quest Ram");
		this.addEntityAndEgg(TFEntities.RAVEN, "Raven");
		this.addEntityAndEgg(TFEntities.REDCAP, "Redcap");
		this.addEntityAndEgg(TFEntities.REDCAP_SAPPER, "Redcap Sapper");
		this.addEntityAndEgg(TFEntities.SKELETON_DRUID, "Skeleton Druid");
		this.addEntityAndEgg(TFEntities.SLIME_BEETLE, "Slime Beetle");
		this.addEntityAndEgg(TFEntities.SNOW_GUARDIAN, "Snow Guardian");
		this.addEntityAndEgg(TFEntities.SNOW_QUEEN, "Snow Queen");
		this.addEntityAndEgg(TFEntities.SQUIRREL, "Squirrel");
		this.addEntityAndEgg(TFEntities.STABLE_ICE_CORE, "Stable Ice Core");
		this.addEntityAndEgg(TFEntities.SWARM_SPIDER, "Swarm Spider");
		this.addEntityAndEgg(TFEntities.TINY_BIRD, "Tiny Bird");
		this.addEntityAndEgg(TFEntities.TOWERWOOD_BORER, "Towerwood Borer");
		this.addEntityAndEgg(TFEntities.TROLL, "Troll");
		this.addEntityAndEgg(TFEntities.UNSTABLE_ICE_CORE, "Unstable Ice Core");
		this.addEntityAndEgg(TFEntities.UR_GHAST, "Ur-Ghast");
		this.addEntityAndEgg(TFEntities.WINTER_WOLF, "Winter Wolf");
		this.addEntityAndEgg(TFEntities.WRAITH, "Wraith");
		this.addEntityAndEgg(TFEntities.YETI, "Yeti");

		this.addEntityType(TFEntities.LICH_MINION, "Lich Minion");
		this.addEntityType(TFEntities.LOYAL_ZOMBIE, "Loyal Zombie");
		this.addEntityType(TFEntities.LOWER_GOBLIN_KNIGHT, "Lower Goblin Knight");
		this.addEntityType(TFEntities.RISING_ZOMBIE, "Zombie");
		this.addEntityType(TFEntities.ROVING_CUBE, "Roving Cube");
		this.addEntityType(TFEntities.UPPER_GOBLIN_KNIGHT, "Upper Goblin Knight");

		this.addEntityType(TFEntities.ICE_SNOWBALL, "Ice Snowball");
		this.addEntityType(TFEntities.ICE_ARROW, "Ice Arrow");
		this.addEntityType(TFEntities.SEEKER_ARROW, "Seeker Arrow");
		this.addEntityType(TFEntities.MOONWORM_SHOT, "Moonworm");
		this.addEntityType(TFEntities.NATURE_BOLT, "Nature Bolt");
		this.addEntityType(TFEntities.SLIME_BLOB, "Slime Blob");
		this.addEntityType(TFEntities.TOME_BOLT, "Death Tome Bolt");
		this.addEntityType(TFEntities.WAND_BOLT, "Twilight Scepter Bolt");
		this.addEntityType(TFEntities.LICH_BOLT, "Lich Bolt");
		this.addEntityType(TFEntities.LICH_BOMB, "Explosive Lich Bolt");
		this.addEntityType(TFEntities.HYDRA_MORTAR, "Hydra Mortar");
		this.addEntityType(TFEntities.FALLING_ICE, "Falling Ice");
		this.addEntityType(TFEntities.THROWN_WEP, "Thrown Weapon");
		this.addEntityType(TFEntities.CHARM_EFFECT, "Charm Effect");
		this.addEntityType(TFEntities.CHAIN_BLOCK, "Block and Chain");
		this.addEntityType(TFEntities.CUBE_OF_ANNIHILATION, "Cube of Annihilation");
		this.addEntityType(TFEntities.THROWN_BLOCK, "Thrown Block");
		this.addEntityType(TFEntities.SLIDER, "Moving Slide Trap");
		this.addEntityType(TFEntities.PROTECTION_BOX, "Progression Protection Box");
		this.addEntityType(TFEntities.BOAT, "Boat");
		this.addEntityType(TFEntities.CHEST_BOAT, "Boat with Chest");

		this.addSubtitle(TFSounds.ACID_RAIN_BURNS, "Acid rain scalds");

		this.addSubtitle(TFSounds.ALPHA_YETI_ALERT, "Alpha Yeti takes notice");
		this.addSubtitle(TFSounds.ALPHA_YETI_DEATH, "Alpha Yeti dies");
		this.addSubtitle(TFSounds.ALPHA_YETI_GRAB, "Alpha Yeti grabs");
		this.addSubtitle(TFSounds.ALPHA_YETI_GROWL, "Alpha Yeti growls");
		this.addSubtitle(TFSounds.ALPHA_YETI_HURT, "Alpha Yeti hurts");
		this.addSubtitle(TFSounds.ALPHA_YETI_ICE, "Alpha Yeti throws ice");
		this.addSubtitle(TFSounds.ALPHA_YETI_PANT, "Alpha Yeti pants");
		this.addSubtitle(TFSounds.ALPHA_YETI_ROAR, "Alpha Yeti roars");
		this.addSubtitle(TFSounds.ALPHA_YETI_THROW, "Alpha Yeti throws");

		this.addSubtitle(TFSounds.BIGHORN_SHEEP_AMBIENT, "Bighorn Sheep bleats");
		this.addSubtitle(TFSounds.BIGHORN_SHEEP_DEATH, "Bighorn Sheep dies");
		this.addSubtitle(TFSounds.BIGHORN_SHEEP_HURT, "Bighorn Sheep hurts");

		this.addSubtitle(TFSounds.BLOCKCHAIN_GOBLIN_AMBIENT, "Block and Chain Goblin chuckles");
		this.addSubtitle(TFSounds.BLOCKCHAIN_GOBLIN_DEATH, "Block and Chain Goblin dies");
		this.addSubtitle(TFSounds.BLOCKCHAIN_GOBLIN_HURT, "Block and Chain Goblin screams");

		this.addSubtitle(TFSounds.BOAR_AMBIENT, "Boar oinks");
		this.addSubtitle(TFSounds.BOAR_DEATH, "Boar dies");
		this.addSubtitle(TFSounds.BOAR_HURT, "Boar hurts");

		this.addSubtitle(TFSounds.CARMINITE_BROODLING_AMBIENT, "Carminite Broodling hisses");
		this.addSubtitle(TFSounds.CARMINITE_BROODLING_DEATH, "Carminite Broodling dies");
		this.addSubtitle(TFSounds.CARMINITE_BROODLING_HURT, "Carminite Broodling hurts");

		this.addSubtitle(TFSounds.CARMINITE_GHASTGUARD_AMBIENT, "Carminite Ghastguard cries");
		this.addSubtitle(TFSounds.CARMINITE_GHASTGUARD_DEATH, "Carminite Ghastguard dies");
		this.addSubtitle(TFSounds.CARMINITE_GHASTGUARD_HURT, "Carminite Ghastguard screams");
		this.addSubtitle(TFSounds.CARMINITE_GHASTGUARD_SHOOT, "Carminite Ghastguard shoots");

		this.addSubtitle(TFSounds.CARMINITE_GHASTLING_AMBIENT, "Carminite Ghastling cries");
		this.addSubtitle(TFSounds.CARMINITE_GHASTLING_DEATH, "Carminite Ghastling dies");
		this.addSubtitle(TFSounds.CARMINITE_GHASTLING_HURT, "Carminite Ghastling screams");
		this.addSubtitle(TFSounds.CARMINITE_GHASTLING_SHOOT, "Carminite Ghastling shoots");

		this.addSubtitle(TFSounds.CARMINITE_GOLEM_ATTACK, "Carminite Golem swings");
		this.addSubtitle(TFSounds.CARMINITE_GOLEM_DEATH, "Carminite Golem dies");
		this.addSubtitle(TFSounds.CARMINITE_GOLEM_HURT, "Carminite Golem hurts");

		this.addSubtitle(TFSounds.DEATH_TOME_AMBIENT, "Death Tome flips pages");
		this.addSubtitle(TFSounds.DEATH_TOME_DEATH, "Death Tome falls apart");
		this.addSubtitle(TFSounds.DEATH_TOME_HURT, "Death Tome creases");

		this.addSubtitle(TFSounds.DEER_AMBIENT, "Deer moos");
		this.addSubtitle(TFSounds.DEER_DEATH, "Deer dies");
		this.addSubtitle(TFSounds.DEER_HURT, "Deer hurts");

		this.addSubtitle(TFSounds.DWARF_RABBIT_AMBIENT, "Dwarf Rabbit squeaks");
		this.addSubtitle(TFSounds.DWARF_RABBIT_DEATH, "Dwarf Rabbit dies");
		this.addSubtitle(TFSounds.DWARF_RABBIT_HURT, "Dwarf Rabbit hurts");

		this.addSubtitle(TFSounds.SKELETON_DRUID_AMBIENT, "Skeleton Druid rattles");
		this.addSubtitle(TFSounds.SKELETON_DRUID_DEATH, "Skeleton Druid dies");
		this.addSubtitle(TFSounds.SKELETON_DRUID_HURT, "Skeleton Druid hurts");
		this.addSubtitle(TFSounds.SKELETON_DRUID_SHOOT, "Skeleton Druid shoots");

		this.addSubtitle(TFSounds.FIRE_BEETLE_DEATH, "Fire Beetle dies");
		this.addSubtitle(TFSounds.FIRE_BEETLE_HURT, "Fire Beetle hurts");
		this.addSubtitle(TFSounds.FIRE_BEETLE_SHOOT, "Fire Beetle spews flames");

		this.addSubtitle(TFSounds.GOBLIN_KNIGHT_AMBIENT, "Goblin Knight chuckles");
		this.addSubtitle(TFSounds.GOBLIN_KNIGHT_DEATH, "Goblin Knight groans in agony");
		this.addSubtitle(TFSounds.GOBLIN_KNIGHT_HURT, "Goblin Knight screeches in pain");

		this.addSubtitle(TFSounds.GOBLIN_KNIGHT_MUFFLED_AMBIENT, "Muffled Goblin Knight chuckles");
		this.addSubtitle(TFSounds.GOBLIN_KNIGHT_MUFFLED_DEATH, "Muffled Goblin Knight groans in agony");
		this.addSubtitle(TFSounds.GOBLIN_KNIGHT_MUFFLED_HURT, "Muffled Goblin Knight screeches in pain");

		this.addSubtitle(TFSounds.HEDGE_SPIDER_AMBIENT, "Hedge Spider hisses");
		this.addSubtitle(TFSounds.HEDGE_SPIDER_DEATH, "Hedge Spider dies");
		this.addSubtitle(TFSounds.HEDGE_SPIDER_HURT, "Hedge Spider hurts");

		this.addSubtitle(TFSounds.HELMET_CRAB_DEATH, "Helmet Crab dies");
		this.addSubtitle(TFSounds.HELMET_CRAB_HURT, "Helmet Crab hurts");

		this.addSubtitle(TFSounds.HOSTILE_WOLF_AMBIENT, "Hostile Wolf growls");
		this.addSubtitle(TFSounds.HOSTILE_WOLF_DEATH, "Hostile Wolf dies");
		this.addSubtitle(TFSounds.HOSTILE_WOLF_HURT, "Hostile Wolf hurts");
		this.addSubtitle(TFSounds.HOSTILE_WOLF_TARGET, "Hostile Wolf takes notice");

		this.addSubtitle(TFSounds.HYDRA_DEATH, "Hydra roars in defeat");
		this.addSubtitle(TFSounds.HYDRA_GROWL, "Hydra growls");
		this.addSubtitle(TFSounds.HYDRA_HURT, "Hydra hurts");
		this.addSubtitle(TFSounds.HYDRA_ROAR, "Hydra roars");
		this.addSubtitle(TFSounds.HYDRA_SHOOT, "Hydra shoots mortar");
		this.addSubtitle(TFSounds.HYDRA_SHOOT_FIRE, "Hydra shoots fire");
		this.addSubtitle(TFSounds.HYDRA_WARN, "Hydra prepares to bite");

		this.addSubtitle(TFSounds.ICE_CORE_AMBIENT, "Ice Core crackles");
		this.addSubtitle(TFSounds.ICE_CORE_DEATH, "Ice Core dies");
		this.addSubtitle(TFSounds.ICE_CORE_HURT, "Ice Core hurts");
		this.addSubtitle(TFSounds.ICE_CORE_SHOOT, "Ice Core shoots snowball");

		this.addSubtitle(TFSounds.KING_SPIDER_AMBIENT, "King Spider hisses");
		this.addSubtitle(TFSounds.KING_SPIDER_DEATH, "King Spider dies");
		this.addSubtitle(TFSounds.KING_SPIDER_HURT, "King Spider hurts");

		this.addSubtitle(TFSounds.KNIGHT_PHANTOM_AMBIENT, "Knight Phantom gasps");
		this.addSubtitle(TFSounds.KNIGHT_PHANTOM_DEATH, "Knight Phantom dies");
		this.addSubtitle(TFSounds.KNIGHT_PHANTOM_HURT, "Knight Phantom hurts");
		this.addSubtitle(TFSounds.KNIGHT_PHANTOM_THROW_PICK, "Knight Phantom throws axe");
		this.addSubtitle(TFSounds.KNIGHT_PHANTOM_THROW_AXE, "Knight Phantom throws pickaxe");

		this.addSubtitle(TFSounds.KOBOLD_AMBIENT, "Kobold grumbles");
		this.addSubtitle(TFSounds.KOBOLD_DEATH, "Kobold dies");
		this.addSubtitle(TFSounds.KOBOLD_HURT, "Kobold hurts");
		this.addSubtitle(TFSounds.KOBOLD_MUNCH, "Kobold munches on food");

		this.addSubtitle(TFSounds.LICH_AMBIENT, "Lich breathes");
		this.addSubtitle(TFSounds.LICH_CLONE_HURT, "Lich shadow clone bypasses attack");
		this.addSubtitle(TFSounds.LICH_DEATH, "Lich dies");
		this.addSubtitle(TFSounds.LICH_HURT, "Lich hurts");
		this.addSubtitle(TFSounds.LICH_POP_MOB, "Lich absorbs mob");
		this.addSubtitle(TFSounds.LICH_SHOOT, "Lich shoots");
		this.addSubtitle(TFSounds.LICH_TELEPORT, "Lich teleports");

		this.addSubtitle(TFSounds.LOYAL_ZOMBIE_AMBIENT, "Loyal Zombie groans");
		this.addSubtitle(TFSounds.LOYAL_ZOMBIE_DEATH, "Loyal Zombie dies");
		this.addSubtitle(TFSounds.LOYAL_ZOMBIE_HURT, "Loyal Zombie hurts");
		this.addSubtitle(TFSounds.LOYAL_ZOMBIE_SUMMON, "Loyal Zombie summoned");

		this.addSubtitle(TFSounds.MAZE_SLIME_DEATH, "Maze Slime dies");
		this.addSubtitle(TFSounds.MAZE_SLIME_HURT, "Maze Slime hurts");
		this.addSubtitle(TFSounds.MAZE_SLIME_SQUISH, "Maze Slime squishes");

		this.addSubtitle(TFSounds.MINION_AMBIENT, "Lich Minion groans");
		this.addSubtitle(TFSounds.MINION_DEATH, "Lich Minion dies");
		this.addSubtitle(TFSounds.MINION_HURT, "Lich Minion hurts");
		this.addSubtitle(TFSounds.MINION_SUMMON, "Lich summons new Minion");

		this.addSubtitle(TFSounds.MINOSHROOM_AMBIENT, "Minoshroom moos");
		this.addSubtitle(TFSounds.MINOSHROOM_ATTACK, "Minoshroom attacks");
		this.addSubtitle(TFSounds.MINOSHROOM_DEATH, "Minoshroom dies");
		this.addSubtitle(TFSounds.MINOSHROOM_HURT, "Minoshroom hurts");
		this.addSubtitle(TFSounds.MINOSHROOM_SLAM, "Minoshroom slams ground");

		this.addSubtitle(TFSounds.MINOTAUR_AMBIENT, "Minotaur moos");
		this.addSubtitle(TFSounds.MINOTAUR_ATTACK, "Minotaur attacks");
		this.addSubtitle(TFSounds.MINOTAUR_DEATH, "Minotaur dies");
		this.addSubtitle(TFSounds.MINOTAUR_HURT, "Minotaur hurts");

		this.addSubtitle(TFSounds.MIST_WOLF_AMBIENT, "Mist Wolf growls");
		this.addSubtitle(TFSounds.MIST_WOLF_DEATH, "Mist Wolf dies");
		this.addSubtitle(TFSounds.MIST_WOLF_HURT, "Mist Wolf hurts");
		this.addSubtitle(TFSounds.MIST_WOLF_TARGET, "Mist Wolf takes notice");

		this.addSubtitle(TFSounds.MOSQUITO, "Mosquitoes buzz");

		this.addSubtitle(TFSounds.NAGA_HISS, "Naga hisses");
		this.addSubtitle(TFSounds.NAGA_HURT, "Naga hurts");
		this.addSubtitle(TFSounds.NAGA_RATTLE, "Naga rattles");

		this.addSubtitle(TFSounds.PINCH_BEETLE_DEATH, "Pinch Beetle dies");
		this.addSubtitle(TFSounds.PINCH_BEETLE_HURT, "Pinch Beetle hurts");

		this.addSubtitle(TFSounds.QUEST_RAM_AMBIENT, "Quest Ram bleats");
		this.addSubtitle(TFSounds.QUEST_RAM_DEATH, "Quest Ram dies");
		this.addSubtitle(TFSounds.QUEST_RAM_HURT, "Quest Ram hurts");

		this.addSubtitle(TFSounds.RAVEN_CAW, "Raven caws");
		this.addSubtitle(TFSounds.RAVEN_SQUAWK, "Raven squawks in pain");

		this.addSubtitle(TFSounds.REDCAP_AMBIENT, "Redcap chuckles");
		this.addSubtitle(TFSounds.REDCAP_DEATH, "Redcap groans in agony");
		this.addSubtitle(TFSounds.REDCAP_HURT, "Redcap screeches in pain");

		this.addSubtitle(TFSounds.SHIELD_ADD, "Fortification Shield spawns");
		this.addSubtitle(TFSounds.SHIELD_BREAK, "Fortification Shield breaks");

		this.addSubtitle(TFSounds.SLIME_BEETLE_DEATH, "Slime Beetle dies");
		this.addSubtitle(TFSounds.SLIME_BEETLE_HURT, "Slime Beetle hurts");
		this.addSubtitle(TFSounds.SLIME_BEETLE_SQUISH, "Slime Beetle shoots");

		this.addSubtitle(TFSounds.SNOW_GUARDIAN_AMBIENT, "Snow Guardian crackles");
		this.addSubtitle(TFSounds.SNOW_GUARDIAN_DEATH, "Snow Guardian dies");
		this.addSubtitle(TFSounds.SNOW_GUARDIAN_HURT, "Snow Guardian hurts");

		this.addSubtitle(TFSounds.SNOW_QUEEN_AMBIENT, "Snow Queen crackles");
		this.addSubtitle(TFSounds.SNOW_QUEEN_ATTACK, "Snow Queen attacks");
		this.addSubtitle(TFSounds.SNOW_QUEEN_BREAK, "Snow Queen deflects attack");
		this.addSubtitle(TFSounds.SNOW_QUEEN_DEATH, "Snow Queen dies");
		this.addSubtitle(TFSounds.SNOW_QUEEN_HURT, "Snow Queen hurts");

		this.addSubtitle(TFSounds.SWARM_SPIDER_AMBIENT, "Swarm Spider hisses");
		this.addSubtitle(TFSounds.SWARM_SPIDER_DEATH, "Swarm Spider dies");
		this.addSubtitle(TFSounds.SWARM_SPIDER_HURT, "Swarm Spider hurts");

		this.addSubtitle(TFSounds.TEAR_BREAK, "Ur-Ghast tear shatters");

		this.addSubtitle(TFSounds.TINY_BIRD_CHIRP, "Bird chirps");
		this.addSubtitle(TFSounds.TINY_BIRD_HURT, "Bird squeaks in pain");
		this.addSubtitle(TFSounds.TINY_BIRD_SONG, "Bird sings");
		this.addSubtitle(TFSounds.TINY_BIRD_TAKEOFF, "Bird takes off");

		this.addSubtitle(TFSounds.TROLL_THROWS_ROCK, "Troll chucks block");

		this.addSubtitle(TFSounds.UR_GHAST_AMBIENT, "Ur-Ghast cries");
		this.addSubtitle(TFSounds.UR_GHAST_DEATH, "Ur-Ghast dies");
		this.addSubtitle(TFSounds.UR_GHAST_HURT, "Ur-Ghast screams");
		this.addSubtitle(TFSounds.UR_GHAST_SHOOT, "Ur-Ghast shoots");
		this.addSubtitle(TFSounds.UR_GHAST_TANTRUM, "Ur-Ghast wails");

		this.addSubtitle(TFSounds.WINTER_WOLF_AMBIENT, "Winter Wolf growls");
		this.addSubtitle(TFSounds.WINTER_WOLF_DEATH, "Winter Wolf dies");
		this.addSubtitle(TFSounds.WINTER_WOLF_HURT, "Winter Wolf hurts");
		this.addSubtitle(TFSounds.WINTER_WOLF_SHOOT, "Winter Wolf shoots");
		this.addSubtitle(TFSounds.WINTER_WOLF_TARGET, "Winter Wolf takes notice");

		this.addSubtitle(TFSounds.WRAITH_AMBIENT, "Wraith gasps");
		this.addSubtitle(TFSounds.WRAITH_DEATH, "Wraith dies");
		this.addSubtitle(TFSounds.WRAITH_HURT, "Wraith hurts");

		this.addSubtitle(TFSounds.YETI_DEATH, "Yeti dies");
		this.addSubtitle(TFSounds.YETI_GRAB, "Yeti grabs");
		this.addSubtitle(TFSounds.YETI_GROWL, "Yeti growls");
		this.addSubtitle(TFSounds.YETI_HURT, "Yeti hurts");
		this.addSubtitle(TFSounds.YETI_THROW, "Yeti throws");

		this.addSubtitle(TFSounds.ALPHA_YETI_PARROT, "Parrot coldly growls");
		this.addSubtitle(TFSounds.CARMINITE_GOLEM_PARROT, "Parrot thunks");
		this.addSubtitle(TFSounds.DEATH_TOME_PARROT, "Parrot makes book noises");
		this.addSubtitle(TFSounds.HOSTILE_WOLF_PARROT, "Parrot growls");
		this.addSubtitle(TFSounds.HYDRA_PARROT, "Parrot roars");
		this.addSubtitle(TFSounds.ICE_CORE_PARROT, "Parrot crackles");
		this.addSubtitle(TFSounds.KOBOLD_PARROT, "Parrot grumbles");
		this.addSubtitle(TFSounds.MINOTAUR_PARROT, "Parrot moos angrily");
		this.addSubtitle(TFSounds.MOSQUITO_PARROT, "Parrot buzzes");
		this.addSubtitle(TFSounds.NAGA_PARROT, "Parrot rattles");
		this.addSubtitle(TFSounds.REDCAP_PARROT, "Parrot chuckles");
		this.addSubtitle(TFSounds.WRAITH_PARROT, "Parrot gasps");

		this.addSubtitle(TFSounds.BEANSTALK_GROWTH, "Ground rumbles");
		this.addSubtitle(TFSounds.BLOCK_ANNIHILATED, "Block dissolves");
		this.addSubtitle(TFSounds.BOSS_CHEST_APPEAR, "Boss Chest appears");
		this.addSubtitle(TFSounds.BUG_SQUISH, "Bug gets squished :(");
		this.addSubtitle(TFSounds.BUILDER_CREATE, "Carminite Builder creates block");
		this.addSubtitle(TFSounds.BUILDER_OFF, "Carminite Builder deactivates");
		this.addSubtitle(TFSounds.BUILDER_ON, "Carminite Builder activates");
		this.addSubtitle(TFSounds.BUILDER_REPLACE, "Built Block expires");
		this.addSubtitle(TFSounds.CASKET_CLOSE, "Keepsake Casket closes");
		this.addSubtitle(TFSounds.CASKET_LOCKED, "Keepsake Casket clicks");
		this.addSubtitle(TFSounds.CASKET_OPEN, "Keepsake Casket opens");
		this.addSubtitle(TFSounds.CASKET_REPAIR, "Keepsake Casket repaired");
		this.addSubtitle(TFSounds.CICADA, "Cicada screams");
		this.addSubtitle(TFSounds.DOOR_ACTIVATED, "Castle Door clicks");
		this.addSubtitle(TFSounds.DOOR_REAPPEAR, "Castle Door reappears");
		this.addSubtitle(TFSounds.DOOR_VANISH, "Castle Door vanishes");
		this.addSubtitle(TFSounds.GHAST_TRAP_AMBIENT, "Ghast Trap dings");
		this.addSubtitle(TFSounds.GHAST_TRAP_ON, "Ghast Trap buzzes");
		this.addSubtitle(TFSounds.GHAST_TRAP_SPINDOWN, "Ghast Trap shuts off");
		this.addSubtitle(TFSounds.GHAST_TRAP_WARMUP, "Ghast Trap warms up");
		this.addSubtitle(TFSounds.JET_ACTIVE, "Fire Jet flares");
		this.addSubtitle(TFSounds.JET_POP, "Fire Jet pops");
		this.addSubtitle(TFSounds.JET_START, "Fire Jet activates");
		this.addSubtitle(TFSounds.LOCKED_VANISHING_BLOCK, "Towerwood door clicks");
		this.addSubtitle(TFSounds.PEDESTAL_ACTIVATE, "Trophy Pedestal accepts trophy");
		this.addSubtitle(TFSounds.PICKED_TORCHBERRIES, "Torchberries pop");
		this.addSubtitle(TFSounds.PORTAL_WHOOSH, "Twilight Forest Portal beckons");
		this.addSubtitle(TFSounds.REACTOR_AMBIENT, "Carminite Reactor whooshes");
		this.addSubtitle(TFSounds.REAPPEAR_BLOCK, "Reappearing Block reappears");
		this.addSubtitle(TFSounds.REAPPEAR_POOF, "Reappearing Block disappears");
		this.addSubtitle(TFSounds.SLIDER, "Sliding Trap creaks");
		this.addSubtitle(TFSounds.SMOKER_START, "Smoker activates");
		this.addSubtitle(TFSounds.TIME_CORE, "Tree of Time ticks");
		this.addSubtitle(TFSounds.TRANSFORMATION_CORE, "Tree of Transformation hums");
		this.addSubtitle(TFSounds.UNLOCK_VANISHING_BLOCK, "Towerwood door unlocks");
		this.addSubtitle(TFSounds.VANISHING_BLOCK, "Vanishing Block vanishes");

		this.addSubtitle(TFSounds.BLOCK_AND_CHAIN_COLLIDE, "Block and Chain hits block");
		this.addSubtitle(TFSounds.BLOCK_AND_CHAIN_FIRED, "Block and Chain fires");
		this.addSubtitle(TFSounds.BLOCK_AND_CHAIN_HIT, "Block and Chain hits entity");
		this.addSubtitle(TFSounds.BRITTLE_FLASK_BREAK, "Brittle Flask shatters");
		this.addSubtitle(TFSounds.BRITTLE_FLASK_CRACK, "Brittle Flask cracks");
		this.addSubtitle(TFSounds.CHARM_KEEP, "Charm of Keeping returns items");
		this.addSubtitle(TFSounds.CHARM_LIFE, "Charm of Keeping regenerates");
		this.addSubtitle(TFSounds.FAN_WHOOSH, "Peacock Feather Fan blows");
		this.addSubtitle(TFSounds.FLASK_FILL, "Potion Flask fills");
		this.addSubtitle(TFSounds.GLASS_SWORD_BREAK, "Glass Sword shatters");
		this.addSubtitle(TFSounds.ICE_BOMB_FIRED, "Ice Bomb thrown");
		this.addSubtitle(TFSounds.KNIGHTMETAL_EQUIP, "Knightmetal Armor clanks");
		this.addSubtitle(TFSounds.LAMP_BURN, "Lamp of Cinders ignites area");
		this.addSubtitle(TFSounds.MAGNET_GRAB, "Ore Magnet pulls up ore");
		this.addSubtitle(TFSounds.METAL_SHIELD_SHATTERS, "Metal Shield shatters");
		this.addSubtitle(TFSounds.MOONWORM_SQUISH, "Moonworm fires");
		this.addSubtitle(TFSounds.POWDER_USE, "Mob Transforms");
		this.addSubtitle(TFSounds.SCEPTER_DRAIN, "Life Scepter drains");
		this.addSubtitle(TFSounds.SCEPTER_PEARL, "Twilight Scepter throws pearl");
		this.addSubtitle(TFSounds.WOOD_SHIELD_SHATTERS, "Wooden Shield shatters");

		this.addDeathMessage("ghastTear", "%1$s was scalded by fiery tears");
		this.addDeathMessage("ghastTear.player", "%1$s was scalded by fiery tears while escaping %2$s");
		this.addDeathMessage("hydraFire", "%1$s was roasted alive by the Hydra");
		this.addDeathMessage("hydraFire.player", "%1$s was roasted alive by the Hydra while escaping %2$s");
		this.addDeathMessage("hydraBite", "%1$s's skin was ripped off by the Hydra");
		this.addDeathMessage("hydraBite.player", "%1$s's skin was ripped off by the Hydra while escaping %2$s");
		this.addDeathMessage("lichBolt", "The Lich's aim was better than %1$s");
		this.addDeathMessage("lichBolt.player", "The Lich and %2$s had better aim than %1$s");
		this.addDeathMessage("lichBomb", "%1$s succumbed to the Lich's explosive magic");
		this.addDeathMessage("lichBomb.player", "%1$s succumbed to the Lich's explosive magic while escaping %2$s");
		this.addDeathMessage("chillingBreath", "%1$s was frozen to death by the Snow Queen");
		this.addDeathMessage("chillingBreath.player", "%1$s was frozen to death by the Snow Queen while escaping %2$s");
		this.addDeathMessage("squish", "%1$s was squashed by the Snow Queen");
		this.addDeathMessage("squish.player", "%1$s was squashed by the Snow Queen while escaping %2$s");
		this.addDeathMessage("thrownAxe", "%1$s was decapitated by a thrown axe");
		this.addDeathMessage("thrownAxe.player", "%1$s was decapitated by a thrown axe while escaping %2$s");
		this.addDeathMessage("thrownPickaxe", "%1$s was decapitated by a thrown pickaxe");
		this.addDeathMessage("thrownPickaxe.player", "%1$s was decapitated by a thrown pickaxe while escaping %2$s");
		this.addDeathMessage("fireJet", "%1$s accidentally walked into a Fire Jet");
		this.addDeathMessage("fireJet.player", "%1$s accidentally walked into a Fire Jet while escaping %2$s");
		this.addDeathMessage("reactor", "%1$s stood too close to a Carminite Reactor");
		this.addDeathMessage("reactor.player", "%1$s stood too close to a Carminite Reactor while escaping %2$s");
		this.addDeathMessage("slider", "%1$s was sliced up by a Sliding Trap");
		this.addDeathMessage("slider.player", "%1$s was sliced up by a Sliding Trap while escaping %2$s");
		this.addDeathMessage("thorns", "%1$s walked into some thorns");
		this.addDeathMessage("thorns.player", "%1$s walked into some thorns while escaping %2$s");
		this.addDeathMessage("knightmetal", "%1$s was skewered by a Knightmetal block");
		this.addDeathMessage("knightmetal.player", "%1$s was skewered by a Knightmetal block while escaping %2$s");
		this.addDeathMessage("fiery", "%1$s walked onto a Fiery block");
		this.addDeathMessage("fiery.player", "%1$s walked onto a Fiery block while escaping %2$s");
		this.addDeathMessage("thrownBlock", "%1$s was squashed by a thrown block");
		this.addDeathMessage("thrownBlock.player", "%1$s was squashed by a thrown block while escaping %2$s");
		this.addDeathMessage("expired", "%1$s's life expired");
		this.addDeathMessage("expired.player", "%1$s's life expired");

		this.addDeathMessage("axing", "%1$s was chopped up by %2$s");
		this.addDeathMessage("axing.item", "%1$s was chopped up by %2$s using %3$s");
		this.addDeathMessage("moonworm", "%1$s was shot by Moonworm");
		this.addDeathMessage("ant", "%1$s was squashed like an ant by %2$s");
		this.addDeathMessage("ant.item", "%1$s was squashed like an ant by %2$s holding %3$s");
		this.addDeathMessage("haunt", "%1$s joined the %2$s's haunt");
		this.addDeathMessage("haunt.item", "%1$s joined the %2$s's haunt after being killed by %3$s");
		this.addDeathMessage("clamped", "%1$s was squeezed to death by %2$s");
		this.addDeathMessage("clamped.item", "%1$s was squeezed to death by %2$s using %3$s");
		this.addDeathMessage("scorched", "%1$s was scorched by %2$s");
		this.addDeathMessage("scorched.item", "%1$s was scorched by %2$s using %3$s");
		this.addDeathMessage("frozen", "%1$s was frozen by %2$s using an Ice Bomb");
		this.addDeathMessage("frozen.item", "%1$s was frozen by %2$s using %3$s");
		this.addDeathMessage("spiked", "%1$s was skewered by %2$s");
		this.addDeathMessage("spiked.item", "%1$s was skewered by %2$s using %3$s");
		this.addDeathMessage("leafBrain", "%1$s's brain was turned into leaves by %2$s");
		this.addDeathMessage("leafBrain.item", "%1$s's brain was turned into leaves by %2$s using %3$s");
		this.addDeathMessage("lostWords", "%1$s was at a loss for words after being killed by %2$s");
		this.addDeathMessage("lostWords.item", "%1$s was at a loss for words after being killed by %2$s using %3$s");
		this.addDeathMessage("schooled", "%1$s was schooled by %2$s");
		this.addDeathMessage("schooled.item", "%1$s was schooled by %2$s using %3$s");
		this.addDeathMessage("snowballFight", "%1$s lost a snowball fight to %2$s");
		this.addDeathMessage("snowballFight.item", "%1$s lost a snowball fight to %2$s using %3$s");
		this.addDeathMessage("lifedrain", "%1$s's life was drained by %2$s");
		this.addDeathMessage("lifedrain.item", "%1$s's life was drained by %2$s using %3$s");
		this.addDeathMessage("yeeted", "%1$s was yeeted for the last time");
		this.addDeathMessage("yeeted.entity", "%1$s was yeeted for the last time by %2$s");
		this.addDeathMessage("yeeted.item", "%1$s was yeeted for the last time by %2$s while somehow holding %3$s");

		this.addStat("blocks_crumbled", "Blocks Crumbled");
		this.addStat("bugs_squished", "Bugs Squashed");
		this.addStat("e115_slices_eaten", "Experiment 115 Slices Eaten");
		this.addStat("keeping_charms_activated", "Charms of Keeping Used");
		this.addStat("life_charms_activated", "Charms of Life Used");
		this.addStat("skull_candles_made", "Skull Candles Created");
		this.addStat("tf_shields_broken", "Fortification Shields Broken");
		this.addStat("torchberries_harvested", "Torchberries Harvested");
		this.addStat("trophy_pedestals_activated", "Trophy Pedestals Activated");
		this.addStat("uncrafting_table_interactions", "Interactions with Uncrafting Table");

		this.add("config.jade.plugin_twilightforest.quest_ram_wool", "Quest Ram Wool");

		this.add("twilightforest.book.author", "a forgotten explorer");

		this.addBookAndContents("lichtower", "Notes on a Pointy Tower",
				"§8[An explorer's notebook, gnawed on by monsters]§0\n\nI have begun examining the strange aura surrounding this tower. The bricks of the tower are protected by a curse, stronger than any I've seen before. The magic from the curse is boiling off into the",
				"surrounding area.\n\nIn my homeland I would have many options for dealing with this magic, but here my supplies are limited. I shall have to research...",
				"§8[[Many entries later]]§0\n\nA breakthrough! In my journeys I sighted a huge snake-like monster in a decorated courtyard. Nearby, I picked up a worn down, discarded green scale.\n\nThe magic in the scale seems to have the curse-breaking",
				"properties I need, but the magic is too dim. I may need to acquire a fresher specimen, directly from the creature.");

		this.addBookAndContents("labyrinth", "Notes on a Swampy Labyrinth",
				"§8[[An explorer's notebook, written on waterproof paper]]§0\n\nThe mosquitoes in this swamp are vexing, but strange. The vast majority of them seem to have no natural source, nor do they seem to have a role in the local ecology. I have begun to suspect that they are",
				"some kind of magical curse.\n\n§8[[Next entry]]§0\n\nNow that I have encountered a protection spell on the ruined labyrinth here, I consider my suspicions confirmed. Both the protection spell and the mosquitoes are a",
				"curse. This curse seems to have a different source from the others I have encountered. I will have to research further...\n\n§8[[Next entry]]§0\n\nThe curse seems to be of a type too powerful for one being alone to",
				"produce. Several wizards working in combination would be necessary.\n\nIf one of the wizards stopped contributing, the whole of the curse over the entire swamp would fall. Strangely, my divinations do not show signs of any nearby living wizards.",
				"I did see something interesting in one of the nearby pointy-roofed towers though...");

		this.addBookAndContents("hydralair", "Notes on the Fire Swamp",
				"§8[[An explorer's notebook, written on fireproof paper]]§0\n\nFire is a trivial obstacle for a master explorer such as myself. I have traversed seas of fire, and swam through oceans of lava. The burning air here is an interesting variation, but",
				"ultimately no hindrance.\n\nWhat does stop me though is that I have encountered another protection spell, this time surrounding a mighty creature that must be king of this fire swamp. This is not the first protection spell I have encountered, and I am",
				"beginning to unravel the mysteries of how they work.\n\nIf this spell is like the others, it will be sustained by a powerful creature nearby. Surrounding the fire swamp are several wet swamps, and under those swamps are labyrinths full of minotaurs.",
				"The logical choice to bind such a spell to would be some sort of powerful minotaur, different in some way from the others that surround it...");

		this.addBookAndContents("tfstronghold", "Notes on a Stronghold",
				"§8[[An explorer's notebook, written on faintly glowing paper]]§0\n\nThe tendrils of darkness surrounding this area are just a manifestation of a protective spell over the entire dark forest. The spell causes blindness, which is quite vexing. I have seen several",
				"interesting things in the area and would like to keep exploring.\n\n§8[[Next entry]]§0\n\nI have found ruins in the dark forest. They belong to a stronghold, of a type usually inhabited by knights. Rather than knights though, this stronghold is full of",
				"goblins. They wear knightly armor, but their behavior is most un-knightly.\n\n§8[[Next entry]]§0\n\nDeep in the ruins, I have found a pedestal. The pedestal seems to be of a type that knights would place trophies on to prove their strength.",
				"Obtaining a powerful scepter would seem to weaken the curse on the dark forest, and placing a trophy associated with a powerful creature on the pedestal would likely grant access into the main part of the stronghold.");

		this.addBookAndContents("darktower", "Notes on a Wooden Tower",
				"§8[[An explorer's notebook that seems to have survived an explosion]]§0\n\nThis tower clearly has mechanisms that are not responding to me. Their magic almost yearns to acknowledge my touch, but it cannot. It is if the devices of the tower are being",
				"suppressed by a powerful group of beings nearby.\n\n§8[[Next entry]]§0\n\nThe magic seems to emanate from deep within the strongholds nearby. It can't come from the goblins, as their magic is charming, but unfocused. There",
				"must still be some force still active in the strongholds.\n\n§8[[Next entry]]§0\n\nMy analysis indicates that it comes from several sources, operating as a group. I will head back to the stronghold after I resupply...");

		this.addBookAndContents("yeticave", "Notes on an Icy Cave",
				"§8[[An explorer's notebook, covered in frost]]§0\n\nThe blizzard surrounding these snowy lands is unceasing. This is no ordinary snowfall--this is a magical phenomenon. I will have to conduct experiments to find what is capable of",
				"causing such an effect.\n\n§8[[Next entry]]§0\n\nThe curse seems to be of a type too powerful for one being alone to produce. Several wizards working in combination would be necessary. If one of the wizards stopped",
				"contributing, the blizzard would calm. Strangely, my divinations do not show signs of any nearby living wizards. I did see something interesting in one of the nearby pointy-roofed towers though...");

		this.addBookAndContents("icetower", "Notes on Auroral Fortification",
				"§8[[An explorer's notebook, caked in ice]]§0\n\nI overcame one blizzard, only to run into this terrible ice storm atop the glacier. My explorations have shown me the splendor of an ice palace, shining with the colors of the",
				"polar aurora. It all seems protected by some sort of curse.\n\n§8[[Next entry]]§0\n\nI am no novice. This curse is fed by the power of a creature nearby. The cause of the curse surrounding the fire swamp was built off the power of the",
				"leader of the minotaurs nearby.\n\nSurrounding this glacier, there are masses of yetis. Perhaps the yetis have some sort of leader...");

		this.addBookAndContents("trollcave", "Notes on the Highlands",
				"§8[[An explorer's notebook, damaged by acid]]§0\n\nThere seems to be no way to protect myself from the toxic rainstorm surrounding this area. In my brief excursions, I have also encountered another protection spell, similar to the others I have",
				"witnessed. The spell must be connected to the toxic storm in some way. Further research to follow...\n\n§8[[Next entry]]§0\n\nSuch supreme weather magic must be the result of multiple undefeated great evils in this world. My studies contain",
				"several clues pointing at a searing swamp, a forest coated in deep darkness, and a realm coated in snow.");

		this.addBookAndContents("unknown", "Notes on the Unexplained",
				"§8[[This book shows signs of having been copied many times]]§0\n\nI cannot explain the field surrounding this structure, but the magic is powerful. If this curse is like the others, then the answer to unlocking it lies elsewhere. Perhaps there is something I have left",
				"undone, or some monster I have yet to defeat. I will have to turn back. I will return to this place later, to see if anything has changed.");

		this.addScreenMessage("optifine.title", "WARNING: OPTIFINE DETECTED");
		this.addScreenMessage("optifine.message", "Before proceeding, please note that Optifine is known to cause crashes, multipart entity visual bugs and many other issues.\n\nBefore reporting a bug, please remove Optifine first and check again to see if the bug is still present.\n\nOptifine-related issues are not solvable on Twilight Forest's end!\n\nThis screen may be disabled in the Client Config.");
		this.addScreenMessage("optifine.suggestions", "Here's a selection of mods that we recommend using instead.");

		this.addScreenMessage("crumble_horn_jei", "Crumble Horn");
		this.addScreenMessage("transformation_jei", "Transformation Powder");
		this.addScreenMessage("uncrafting_jei", "Uncrafting");
	}
}
