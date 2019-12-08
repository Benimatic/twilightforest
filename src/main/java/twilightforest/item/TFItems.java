package twilightforest.item;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import twilightforest.TwilightForestMod;

import java.util.UUID;

/* TODO 1.14: There's a list of things to change...
	1. Move to DeferredRegister.
	2. Since all items are put in the Twilight Forest Item Group, make a method for registering into this group rather than set per class?
		2.1 Either that, or move them into classes.
	3. Make the Item Group an anonymous class, rather than a new, separate one.
	4. Food is a thing. Make Food fields for all food items
 */
@GameRegistry.ObjectHolder(TwilightForestMod.ID)
public class TFItems {
	public static final Food FOOD_EXPERIMENT_115 = new Food.Builder().hunger(4).saturation(0.3F).build();
	public static final Food FOOD_HYDRA_CHOP = new Food.Builder().hunger(18).saturation(2.0F).effect(new EffectInstance(Effects.REGENERATION, 100, 0), 1.0F).build();
	public static final Food FOOD_MEEF_STROGANOFF = new Food.Builder().hunger(8).saturation(0.6F).build();

	static final UUID GIANT_REACH_MODIFIER = UUID.fromString("7f10172d-de69-49d7-81bd-9594286a6827");

	@GameRegistry.ObjectHolder("naga_scale")
	public static final Item naga_scale;
	@GameRegistry.ObjectHolder("naga_chestplate")
	public static final Item naga_chestplate;
	@GameRegistry.ObjectHolder("naga_leggings")
	public static final Item naga_leggings;
	@GameRegistry.ObjectHolder("twilight_scepter")
	public static final Item twilight_scepter;
	@GameRegistry.ObjectHolder("lifedrain_scepter")
	public static final Item lifedrain_scepter;
	@GameRegistry.ObjectHolder("zombie_scepter")
	public static final Item zombie_scepter;
	@GameRegistry.ObjectHolder("shield_scepter")
	public static final Item shield_scepter;
	@GameRegistry.ObjectHolder("ore_meter")
	public static final Item ore_meter;
	@GameRegistry.ObjectHolder("magic_map")
	public static final Item magic_map;
	@GameRegistry.ObjectHolder("maze_map")
	public static final Item maze_map;
	@GameRegistry.ObjectHolder("ore_map")
	public static final Item ore_map;
	@GameRegistry.ObjectHolder("raven_feather")
	public static final Item raven_feather;
	@GameRegistry.ObjectHolder("magic_map_focus")
	public static final Item magic_map_focus;
	@GameRegistry.ObjectHolder("maze_map_focus")
	public static final Item maze_map_focus;
	@GameRegistry.ObjectHolder("liveroot")
	public static final Item liveroot;
	@GameRegistry.ObjectHolder("ironwood_raw")
	public static final Item ironwood_raw;
	@GameRegistry.ObjectHolder("ironwood_ingot")
	public static final Item ironwood_ingot;
	@GameRegistry.ObjectHolder("ironwood_helmet")
	public static final Item ironwood_helmet;
	@GameRegistry.ObjectHolder("ironwood_chestplate")
	public static final Item ironwood_chestplate;
	@GameRegistry.ObjectHolder("ironwood_leggings")
	public static final Item ironwood_leggings;
	@GameRegistry.ObjectHolder("ironwood_boots")
	public static final Item ironwood_boots;
	@GameRegistry.ObjectHolder("ironwood_sword")
	public static final Item ironwood_sword;
	@GameRegistry.ObjectHolder("ironwood_shovel")
	public static final Item ironwood_shovel;
	@GameRegistry.ObjectHolder("ironwood_pickaxe")
	public static final Item ironwood_pickaxe;
	@GameRegistry.ObjectHolder("ironwood_axe")
	public static final Item ironwood_axe;
	@GameRegistry.ObjectHolder("ironwood_hoe")
	public static final Item ironwood_hoe;
	@GameRegistry.ObjectHolder("torchberries")
	public static final Item torchberries;
	@GameRegistry.ObjectHolder("raw_venison")
	public static final Item raw_venison;
	@GameRegistry.ObjectHolder("cooked_venison")
	public static final Item cooked_venison;
	@GameRegistry.ObjectHolder("hydra_chop")
	public static final Item hydra_chop;
	@GameRegistry.ObjectHolder("fiery_blood")
	public static final Item fiery_blood;
	@GameRegistry.ObjectHolder("trophy")
	public static final Item trophy;
	@GameRegistry.ObjectHolder("fiery_ingot")
	public static final Item fiery_ingot;
	@GameRegistry.ObjectHolder("fiery_helmet")
	public static final Item fiery_helmet;
	@GameRegistry.ObjectHolder("fiery_chestplate")
	public static final Item fiery_chestplate;
	@GameRegistry.ObjectHolder("fiery_leggings")
	public static final Item fiery_leggings;
	@GameRegistry.ObjectHolder("fiery_boots")
	public static final Item fiery_boots;
	@GameRegistry.ObjectHolder("fiery_sword")
	public static final Item fiery_sword;
	@GameRegistry.ObjectHolder("fiery_pickaxe")
	public static final Item fiery_pickaxe;
	@GameRegistry.ObjectHolder("steeleaf_ingot")
	public static final Item steeleaf_ingot;
	@GameRegistry.ObjectHolder("steeleaf_helmet")
	public static final Item steeleaf_helmet;
	@GameRegistry.ObjectHolder("steeleaf_chestplate")
	public static final Item steeleaf_chestplate;
	@GameRegistry.ObjectHolder("steeleaf_leggings")
	public static final Item steeleaf_leggings;
	@GameRegistry.ObjectHolder("steeleaf_boots")
	public static final Item steeleaf_boots;
	@GameRegistry.ObjectHolder("steeleaf_sword")
	public static final Item steeleaf_sword;
	@GameRegistry.ObjectHolder("steeleaf_shovel")
	public static final Item steeleaf_shovel;
	@GameRegistry.ObjectHolder("steeleaf_pickaxe")
	public static final Item steeleaf_pickaxe;
	@GameRegistry.ObjectHolder("steeleaf_axe")
	public static final Item steeleaf_axe;
	@GameRegistry.ObjectHolder("steeleaf_hoe")
	public static final Item steeleaf_hoe;
	@GameRegistry.ObjectHolder("minotaur_axe")
	public static final Item minotaur_axe;
	@GameRegistry.ObjectHolder("minotaur_axe_gold")
	public static final Item minotaur_axe_gold;
	@GameRegistry.ObjectHolder("mazebreaker_pickaxe")
	public static final Item mazebreaker_pickaxe;
	@GameRegistry.ObjectHolder("transformation_powder")
	public static final Item transformation_powder;
	@GameRegistry.ObjectHolder("raw_meef")
	public static final Item raw_meef;
	@GameRegistry.ObjectHolder("cooked_meef")
	public static final Item cooked_meef;
	@GameRegistry.ObjectHolder("meef_stroganoff")
	public static final Item meef_stroganoff;
	@GameRegistry.ObjectHolder("maze_wafer")
	public static final Item maze_wafer;
	@GameRegistry.ObjectHolder("magic_map_empty")
	public static final Item magic_map_empty;
	@GameRegistry.ObjectHolder("maze_map_empty")
	public static final Item maze_map_empty;
	@GameRegistry.ObjectHolder("ore_map_empty")
	public static final Item ore_map_empty;
	@GameRegistry.ObjectHolder("ore_magnet")
	public static final Item ore_magnet;
	@GameRegistry.ObjectHolder("crumble_horn")
	public static final Item crumble_horn;
	@GameRegistry.ObjectHolder("peacock_fan")
	public static final Item peacock_fan;
	@GameRegistry.ObjectHolder("moonworm_queen")
	public static final Item moonworm_queen;
	@GameRegistry.ObjectHolder("charm_of_life_1")
	public static final Item charm_of_life_1;
	@GameRegistry.ObjectHolder("charm_of_life_2")
	public static final Item charm_of_life_2;
	@GameRegistry.ObjectHolder("charm_of_keeping_1")
	public static final Item charm_of_keeping_1;
	@GameRegistry.ObjectHolder("charm_of_keeping_2")
	public static final Item charm_of_keeping_2;
	@GameRegistry.ObjectHolder("charm_of_keeping_3")
	public static final Item charm_of_keeping_3;
	@GameRegistry.ObjectHolder("tower_key")
	public static final Item tower_key;
	@GameRegistry.ObjectHolder("borer_essence")
	public static final Item borer_essence;
	@GameRegistry.ObjectHolder("carminite")
	public static final Item carminite;
	@GameRegistry.ObjectHolder("experiment_115")
	public static final Item experiment_115;
	@GameRegistry.ObjectHolder("armor_shard")
	public static final Item armor_shard;
	@GameRegistry.ObjectHolder("knightmetal_ingot")
	public static final Item knightmetal_ingot;
	@GameRegistry.ObjectHolder("armor_shard_cluster")
	public static final Item armor_shard_cluster;
	@GameRegistry.ObjectHolder("knightmetal_helmet")
	public static final Item knightmetal_helmet;
	@GameRegistry.ObjectHolder("knightmetal_chestplate")
	public static final Item knightmetal_chestplate;
	@GameRegistry.ObjectHolder("knightmetal_leggings")
	public static final Item knightmetal_leggings;
	@GameRegistry.ObjectHolder("knightmetal_boots")
	public static final Item knightmetal_boots;
	@GameRegistry.ObjectHolder("knightmetal_shield")
	public static final Item knightmetal_shield;
	@GameRegistry.ObjectHolder("knightmetal_sword")
	public static final Item knightmetal_sword;
	@GameRegistry.ObjectHolder("knightmetal_pickaxe")
	public static final Item knightmetal_pickaxe;
	@GameRegistry.ObjectHolder("knightmetal_axe")
	public static final Item knightmetal_axe;
	@GameRegistry.ObjectHolder("phantom_helmet")
	public static final Item phantom_helmet;
	@GameRegistry.ObjectHolder("phantom_chestplate")
	public static final Item phantom_chestplate;
	@GameRegistry.ObjectHolder("lamp_of_cinders")
	public static final Item lamp_of_cinders;
	@GameRegistry.ObjectHolder("fiery_tears")
	public static final Item fiery_tears;
	@GameRegistry.ObjectHolder("alpha_fur")
	public static final Item alpha_fur;
	@GameRegistry.ObjectHolder("yeti_helmet")
	public static final Item yeti_helmet;
	@GameRegistry.ObjectHolder("yeti_chestplate")
	public static final Item yeti_chestplate;
	@GameRegistry.ObjectHolder("yeti_leggings")
	public static final Item yeti_leggings;
	@GameRegistry.ObjectHolder("yeti_boots")
	public static final Item yeti_boots;
	@GameRegistry.ObjectHolder("ice_bomb")
	public static final Item ice_bomb;
	@GameRegistry.ObjectHolder("arctic_fur")
	public static final Item arctic_fur;
	@GameRegistry.ObjectHolder("arctic_helmet")
	public static final Item arctic_helmet;
	@GameRegistry.ObjectHolder("arctic_chestplate")
	public static final Item arctic_chestplate;
	@GameRegistry.ObjectHolder("arctic_leggings")
	public static final Item arctic_leggings;
	@GameRegistry.ObjectHolder("arctic_boots")
	public static final Item arctic_boots;
	@GameRegistry.ObjectHolder("magic_beans")
	public static final Item magic_beans;
	@GameRegistry.ObjectHolder("giant_pickaxe")
	public static final Item giant_pickaxe;
	@GameRegistry.ObjectHolder("giant_sword")
	public static final Item giant_sword;
	@GameRegistry.ObjectHolder("triple_bow")
	public static final Item triple_bow;
	@GameRegistry.ObjectHolder("seeker_bow")
	public static final Item seeker_bow;
	@GameRegistry.ObjectHolder("ice_bow")
	public static final Item ice_bow;
	@GameRegistry.ObjectHolder("ender_bow")
	public static final Item ender_bow;
	@GameRegistry.ObjectHolder("ice_sword")
	public static final Item ice_sword;
	@GameRegistry.ObjectHolder("glass_sword")
	public static final Item glass_sword;
	@GameRegistry.ObjectHolder("knightmetal_ring")
	public static final Item knightmetal_ring;
	@GameRegistry.ObjectHolder("block_and_chain")
	public static final Item block_and_chain;
	@GameRegistry.ObjectHolder("cube_talisman")
	public static final Item cube_talisman;
	@GameRegistry.ObjectHolder("cube_of_annihilation")
	public static final Item cube_of_annihilation;
	@GameRegistry.ObjectHolder("miniature_structure")
	public static final Item miniature_structure;
	@GameRegistry.ObjectHolder("castle_door")
	public static final Item castle_door;
	@GameRegistry.ObjectHolder("block_storage")
	public static final Item block_storage;

	public static CreativeTabTwilightForest creativeTab = new CreativeTabTwilightForest("twilightForest");

	static {
		naga_scale = null;
		naga_chestplate = null;
		naga_leggings = null;
		twilight_scepter = null;
		lifedrain_scepter = null;
		zombie_scepter = null;
		shield_scepter = null;
		//items.register(wandPacification, "Wand of Pacification [NYI]");
		ore_meter = null;
		magic_map = null;
		maze_map = null;
		ore_map = null;
		raven_feather = null;
		magic_map_focus = null;
		maze_map_focus = null;
		liveroot = null;
		ironwood_raw = null;
		ironwood_ingot = null;
		ironwood_helmet = null;
		ironwood_chestplate = null;
		ironwood_leggings = null;
		ironwood_boots = null;
		ironwood_sword = null;
		ironwood_shovel = null;
		ironwood_pickaxe = null;
		ironwood_axe = null;
		ironwood_hoe = null;
		torchberries = null;
		raw_venison = null;
		cooked_venison = null;
		hydra_chop = null;
		fiery_blood = null;
		trophy = null;
		fiery_ingot = null;
		fiery_helmet = null;
		fiery_chestplate = null;
		fiery_leggings = null;
		fiery_boots = null;
		fiery_sword = null;
		fiery_pickaxe = null;
		steeleaf_ingot = null;
		steeleaf_helmet = null;
		steeleaf_chestplate = null;
		steeleaf_leggings = null;
		steeleaf_boots = null;
		steeleaf_sword = null;
		steeleaf_shovel = null;
		steeleaf_pickaxe = null;
		steeleaf_axe = null;
		steeleaf_hoe = null;
		minotaur_axe = null;
		minotaur_axe_gold = null;
		mazebreaker_pickaxe = null;
		transformation_powder = null;
		raw_meef = null;
		cooked_meef = null;
		meef_stroganoff = null;
		maze_wafer = null;
		magic_map_empty = null;
		maze_map_empty = null;
		ore_map_empty = null;
		ore_magnet = null;
		crumble_horn = null;
		peacock_fan = null;
		moonworm_queen = null;
		charm_of_life_1 = null;
		charm_of_life_2 = null;
		charm_of_keeping_1 = null;
		charm_of_keeping_2 = null;
		charm_of_keeping_3 = null;
		tower_key = null;
		borer_essence = null;
		carminite = null;
		experiment_115 = null;
		armor_shard = null;
		knightmetal_ingot = null;
		armor_shard_cluster = null;
		knightmetal_helmet = null;
		knightmetal_chestplate = null;
		knightmetal_leggings = null;
		knightmetal_boots = null;
		knightmetal_shield = null;
		knightmetal_sword = null;
		knightmetal_pickaxe = null;
		knightmetal_axe = null;
		phantom_helmet = null;
		phantom_chestplate = null;
		lamp_of_cinders = null;
		fiery_tears = null;
		alpha_fur = null;
		yeti_helmet = null;
		yeti_chestplate = null;
		yeti_leggings = null;
		yeti_boots = null;
		ice_bomb = null;
		arctic_fur = null;
		arctic_helmet = null;
		arctic_chestplate = null;
		arctic_leggings = null;
		arctic_boots = null;
		magic_beans = null;
		giant_pickaxe = null;
		giant_sword = null;
		triple_bow = null;
		seeker_bow = null;
		ice_bow = null;
		ender_bow = null;
		ice_sword = null;
		glass_sword = null;
		knightmetal_ring = null;
		block_and_chain = null;
		cube_talisman = null;
		cube_of_annihilation = null;
		miniature_structure = null;
		castle_door = null;
		block_storage = null;
	}
}