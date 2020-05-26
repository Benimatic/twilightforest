package twilightforest.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Food;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.enums.BossVariant;
import twilightforest.enums.TwilightArmorMaterial;
import twilightforest.enums.TwilightItemTier;

import java.util.UUID;

/* TODO 1.14: There's a list of things to change...
	1. Since all items are put in the Twilight Forest Item Group, make a method for registering into this group rather than set per class?
		1.1 Either that, or move them into classes.
	2. Make the Item Group an anonymous class, rather than a new, separate one.
 */
public class TFItems {
	public static final Food EXPERIMENT_115 = new Food.Builder().hunger(4).saturation(0.3F).build();
	public static final Food HYDRA_CHOP = new Food.Builder().hunger(18).saturation(2.0F).effect(new EffectInstance(Effects.REGENERATION, 100, 0), 1.0F).build();
	public static final Food MAZE_WAFER = new Food.Builder().hunger(4).saturation(0.6F).build();
	public static final Food MEEF_COOKED = new Food.Builder().hunger(6).saturation(0.6F).meat().build();
	public static final Food MEEF_RAW = new Food.Builder().hunger(2).saturation(0.3F).meat().build();
	public static final Food MEEF_STROGANOFF = new Food.Builder().hunger(8).saturation(0.6F).build();
	public static final Food VENISON_COOKED = new Food.Builder().hunger(8).saturation(0.8F).meat().build();
	public static final Food VENISON_RAW = new Food.Builder().hunger(3).saturation(0.3F).meat().build();

	static final UUID GIANT_REACH_MODIFIER = UUID.fromString("7f10172d-de69-49d7-81bd-9594286a6827");

	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, TwilightForestMod.ID);

	public static final RegistryObject<Item> naga_scale = ITEMS.register("naga_scale", () -> new ItemTF(Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> naga_chestplate = ITEMS.register("naga_chestplate", () -> new ItemTFNagaArmor(TwilightArmorMaterial.ARMOR_NAGA, EquipmentSlotType.CHEST, Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> naga_leggings = ITEMS.register("naga_leggings", () -> new ItemTFNagaArmor(TwilightArmorMaterial.ARMOR_NAGA, EquipmentSlotType.LEGS, Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> twilight_scepter = ITEMS.register("twilight_scepter", () -> new ItemTFTwilightWand(Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> lifedrain_scepter = ITEMS.register("lifedrain_scepter", () -> new ItemTFScepterLifeDrain(Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> zombie_scepter = ITEMS.register("zombie_scepter", () -> new ItemTFZombieWand(Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> shield_scepter = ITEMS.register("shield_scepter", () -> new ItemTFShieldWand(Rarity.UNCOMMON, new Item.Properties()));
	//items.register("Wand of Pacification [NYI]", new ItemTF().setIconIndex(6).setTranslationKey("wandPacification").setMaxStackSize(1));
	public static final RegistryObject<Item> ore_meter = ITEMS.register("ore_meter", () -> new ItemTFOreMeter(new Item.Properties()));
	public static final RegistryObject<Item> magic_map = ITEMS.register("magic_map", () -> new ItemTFMagicMap(new Item.Properties()));
	public static final RegistryObject<Item> maze_map = ITEMS.register("maze_map", () -> new ItemTFMazeMap(false, new Item.Properties().maxStackSize(1)));
	public static final RegistryObject<Item> ore_map = ITEMS.register("ore_map", () -> new ItemTFMazeMap(true, new Item.Properties().maxStackSize(1)));
	public static final RegistryObject<Item> raven_feather = ITEMS.register("raven_feather", () -> new ItemTF(new Item.Properties()));
	public static final RegistryObject<Item> magic_map_focus = ITEMS.register("magic_map_focus", () -> new ItemTF(new Item.Properties()));
	public static final RegistryObject<Item> maze_map_focus = ITEMS.register("maze_map_focus", () -> new ItemTF(new Item.Properties()));
	public static final RegistryObject<Item> liveroot = ITEMS.register("liveroot", () -> new ItemTF(new Item.Properties()));
	public static final RegistryObject<Item> ironwood_raw = ITEMS.register("ironwood_raw", () -> new ItemTF(new Item.Properties()));
	public static final RegistryObject<Item> ironwood_ingot = ITEMS.register("ironwood_ingot", () -> new ItemTF(new Item.Properties()));
	public static final RegistryObject<Item> ironwood_helmet = ITEMS.register("ironwood_helmet", () -> new ItemTFIronwoodArmor(TwilightArmorMaterial.ARMOR_IRONWOOD, EquipmentSlotType.HEAD, Rarity.COMMON, new Item.Properties()));
	public static final RegistryObject<Item> ironwood_chestplate = ITEMS.register("ironwood_chestplate", () -> new ItemTFIronwoodArmor(TwilightArmorMaterial.ARMOR_IRONWOOD, EquipmentSlotType.CHEST, Rarity.COMMON, new Item.Properties()));
	public static final RegistryObject<Item> ironwood_leggings = ITEMS.register("ironwood_leggings", () -> new ItemTFIronwoodArmor(TwilightArmorMaterial.ARMOR_IRONWOOD, EquipmentSlotType.LEGS, Rarity.COMMON, new Item.Properties()));
	public static final RegistryObject<Item> ironwood_boots = ITEMS.register("ironwood_boots", () -> new ItemTFIronwoodArmor(TwilightArmorMaterial.ARMOR_IRONWOOD, EquipmentSlotType.FEET, Rarity.COMMON, new Item.Properties()));
	public static final RegistryObject<Item> ironwood_sword = ITEMS.register("ironwood_sword", () -> new ItemTFIronwoodSword(TwilightItemTier.TOOL_IRONWOOD, new Item.Properties()));
	public static final RegistryObject<Item> ironwood_shovel = ITEMS.register("ironwood_shovel", () -> new ItemTFIronwoodShovel(TwilightItemTier.TOOL_IRONWOOD, new Item.Properties()));
	public static final RegistryObject<Item> ironwood_pickaxe = ITEMS.register("ironwood_pickaxe", () -> new ItemTFIronwoodPick(TwilightItemTier.TOOL_IRONWOOD, new Item.Properties()));
	public static final RegistryObject<Item> ironwood_axe = ITEMS.register("ironwood_axe", () -> new ItemTFIronwoodAxe(TwilightItemTier.TOOL_IRONWOOD, new Item.Properties()));
	public static final RegistryObject<Item> ironwood_hoe = ITEMS.register("ironwood_hoe", () -> new ItemTFIronwoodHoe(TwilightItemTier.TOOL_IRONWOOD, new Item.Properties()));
	public static final RegistryObject<Item> torchberries = ITEMS.register("torchberries", () -> new ItemTF(new Item.Properties()));
	public static final RegistryObject<Item> raw_venison = ITEMS.register("raw_venison", () -> new ItemTFFood(TFItems.VENISON_RAW, new Item.Properties()));
	public static final RegistryObject<Item> cooked_venison = ITEMS.register("cooked_venison", () -> new ItemTFFood(TFItems.VENISON_COOKED, new Item.Properties()));
	public static final RegistryObject<Item> hydra_chop = ITEMS.register("hydra_chop", () -> new ItemTFHydraChops(TFItems.HYDRA_CHOP, new Item.Properties()));
	public static final RegistryObject<Item> fiery_blood = ITEMS.register("fiery_blood", () -> new ItemTF(Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> fiery_tears = ITEMS.register("fiery_tears", () -> new ItemTF(Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> naga_trophy = ITEMS.register("naga_trophy", () -> new ItemTFTrophy(new Item.Properties(), TFBlocks.naga_trophy, BossVariant.NAGA));
	public static final RegistryObject<Item> lich_trophy = ITEMS.register("lich_trophy", () -> new ItemTFTrophy(new Item.Properties(), TFBlocks.lich_trophy, BossVariant.LICH));
	public static final RegistryObject<Item> minoshroom_trophy = ITEMS.register("minoshroom_trophy", () -> new ItemTFTrophy(new Item.Properties(), TFBlocks.minoshroom_trophy, BossVariant.MINOSHROOM));
	public static final RegistryObject<Item> hydra_trophy = ITEMS.register("hydra_trophy", () -> new ItemTFTrophy(new Item.Properties(), TFBlocks.hydra_trophy, BossVariant.HYDRA));
	public static final RegistryObject<Item> knight_phantom_trophy = ITEMS.register("knight_phantom_trophy", () -> new ItemTFTrophy(new Item.Properties(), TFBlocks.knight_phantom_trophy, BossVariant.KNIGHT_PHANTOM));
	public static final RegistryObject<Item> ur_ghast_trophy = ITEMS.register("ur_ghast_trophy", () -> new ItemTFTrophy(new Item.Properties(), TFBlocks.ur_ghast_trophy, BossVariant.UR_GHAST));
	public static final RegistryObject<Item> snow_queen_trophy = ITEMS.register("snow_queen_trophy", () -> new ItemTFTrophy(new Item.Properties(), TFBlocks.snow_queen_trophy, BossVariant.SNOW_QUEEN));
	public static final RegistryObject<Item> quest_ram_trophy = ITEMS.register("quest_ram_trophy", () -> new ItemTFTrophy(new Item.Properties(), TFBlocks.quest_ram_trophy, BossVariant.QUEST_RAM));
	public static final RegistryObject<Item> fiery_ingot = ITEMS.register("fiery_ingot", () -> new ItemTF(Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> fiery_helmet = ITEMS.register("fiery_helmet", () -> new ItemTFFieryArmor(TwilightArmorMaterial.ARMOR_FIERY, EquipmentSlotType.HEAD, Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> fiery_chestplate = ITEMS.register("fiery_chestplate", () -> new ItemTFFieryArmor(TwilightArmorMaterial.ARMOR_FIERY, EquipmentSlotType.CHEST, Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> fiery_leggings = ITEMS.register("fiery_leggings", () -> new ItemTFFieryArmor(TwilightArmorMaterial.ARMOR_FIERY, EquipmentSlotType.LEGS, Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> fiery_boots = ITEMS.register("fiery_boots", () -> new ItemTFFieryArmor(TwilightArmorMaterial.ARMOR_FIERY, EquipmentSlotType.FEET, Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> fiery_sword = ITEMS.register("fiery_sword", () -> new ItemTFFierySword(TwilightItemTier.TOOL_FIERY, new Item.Properties()));
	public static final RegistryObject<Item> fiery_pickaxe = ITEMS.register("fiery_pickaxe", () -> new ItemTFFieryPick(TwilightItemTier.TOOL_FIERY, new Item.Properties()));
	public static final RegistryObject<Item> steeleaf_ingot = ITEMS.register("steeleaf_ingot", () -> new ItemTF(new Item.Properties()));
	public static final RegistryObject<Item> steeleaf_helmet = ITEMS.register("steeleaf_helmet", () -> new ItemTFSteeleafArmor(TwilightArmorMaterial.ARMOR_STEELEAF, EquipmentSlotType.HEAD, Rarity.COMMON, new Item.Properties()));
	public static final RegistryObject<Item> steeleaf_chestplate = ITEMS.register("steeleaf_chestplate", () -> new ItemTFSteeleafArmor(TwilightArmorMaterial.ARMOR_STEELEAF, EquipmentSlotType.CHEST, Rarity.COMMON, new Item.Properties()));
	public static final RegistryObject<Item> steeleaf_leggings = ITEMS.register("steeleaf_leggings", () -> new ItemTFSteeleafArmor(TwilightArmorMaterial.ARMOR_STEELEAF, EquipmentSlotType.LEGS, Rarity.COMMON, new Item.Properties()));
	public static final RegistryObject<Item> steeleaf_boots = ITEMS.register("steeleaf_boots", () -> new ItemTFSteeleafArmor(TwilightArmorMaterial.ARMOR_STEELEAF, EquipmentSlotType.FEET, Rarity.COMMON, new Item.Properties()));
	public static final RegistryObject<Item> steeleaf_sword = ITEMS.register("steeleaf_sword", () -> new ItemTFSteeleafSword(TwilightItemTier.TOOL_STEELEAF, new Item.Properties()));
	public static final RegistryObject<Item> steeleaf_shovel = ITEMS.register("steeleaf_shovel", () -> new ItemTFSteeleafShovel(TwilightItemTier.TOOL_STEELEAF, new Item.Properties()));
	public static final RegistryObject<Item> steeleaf_pickaxe = ITEMS.register("steeleaf_pickaxe", () -> new ItemTFSteeleafPick(TwilightItemTier.TOOL_STEELEAF, new Item.Properties()));
	public static final RegistryObject<Item> steeleaf_axe = ITEMS.register("steeleaf_axe", () -> new ItemTFSteeleafAxe(TwilightItemTier.TOOL_STEELEAF, new Item.Properties()));
	public static final RegistryObject<Item> steeleaf_hoe = ITEMS.register("steeleaf_hoe", () -> new ItemTFSteeleafHoe(TwilightItemTier.TOOL_STEELEAF, new Item.Properties()));
	public static final RegistryObject<Item> minotaur_axe_gold = ITEMS.register("minotaur_axe_gold", () -> new ItemTFMinotaurAxe(ItemTier.GOLD, Rarity.COMMON, new Item.Properties()));
	public static final RegistryObject<Item> minotaur_axe = ITEMS.register("minotaur_axe", () -> new ItemTFMinotaurAxe(ItemTier.DIAMOND, Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> mazebreaker_pickaxe = ITEMS.register("mazebreaker_pickaxe", () -> new ItemTFMazebreakerPick(ItemTier.DIAMOND, new Item.Properties()));
	public static final RegistryObject<Item> transformation_powder = ITEMS.register("transformation_powder", () -> new ItemTFTransformPowder(new Item.Properties()));
	public static final RegistryObject<Item> raw_meef = ITEMS.register("raw_meef", () -> new ItemTFFood(TFItems.MEEF_RAW, new Item.Properties()));
	public static final RegistryObject<Item> cooked_meef = ITEMS.register("cooked_meef", () -> new ItemTFFood(TFItems.MEEF_COOKED, new Item.Properties()));
	public static final RegistryObject<Item> meef_stroganoff = ITEMS.register("meef_stroganoff", () -> new ItemTFSoup(TFItems.MEEF_STROGANOFF, new Item.Properties()));
	public static final RegistryObject<Item> maze_wafer = ITEMS.register("maze_wafer", () -> new ItemTFFood(TFItems.MAZE_WAFER, new Item.Properties()));
	public static final RegistryObject<Item> magic_map_empty = ITEMS.register("magic_map_empty", () -> new ItemTFEmptyMagicMap(new Item.Properties()));
	public static final RegistryObject<Item> maze_map_empty = ITEMS.register("maze_map_empty", () -> new ItemTFEmptyMazeMap(false, new Item.Properties()));
	public static final RegistryObject<Item> ore_map_empty = ITEMS.register("ore_map_empty", () -> new ItemTFEmptyMazeMap(true, new Item.Properties()));
	public static final RegistryObject<Item> ore_magnet = ITEMS.register("ore_magnet", () -> new ItemTFOreMagnet(new Item.Properties()));
	public static final RegistryObject<Item> crumble_horn = ITEMS.register("crumble_horn", () -> new ItemTFCrumbleHorn(Rarity.RARE, new Item.Properties()));
	public static final RegistryObject<Item> peacock_fan = ITEMS.register("peacock_fan", () -> new ItemTFPeacockFan(Rarity.RARE, new Item.Properties()));
	public static final RegistryObject<Item> moonworm_queen = ITEMS.register("moonworm_queen", () -> new ItemTFMoonwormQueen(Rarity.RARE, new Item.Properties()));
	public static final RegistryObject<Item> charm_of_life_1 = ITEMS.register("charm_of_life_1", () -> new ItemCharmBaubleable(Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> charm_of_life_2 = ITEMS.register("charm_of_life_2", () -> new ItemCharmBaubleable(Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> charm_of_keeping_1 = ITEMS.register("charm_of_keeping_1", () -> new ItemCharmBaubleable(Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> charm_of_keeping_2 = ITEMS.register("charm_of_keeping_2", () -> new ItemCharmBaubleable(Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> charm_of_keeping_3 = ITEMS.register("charm_of_keeping_3", () -> new ItemCharmBaubleable(Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> tower_key = ITEMS.register("tower_key", () -> new ItemTFTowerKey(Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> borer_essence = ITEMS.register("borer_essence", () -> new ItemTF(new Item.Properties()));
	public static final RegistryObject<Item> carminite = ITEMS.register("carminite", () -> new ItemTF(new Item.Properties()));
	public static final RegistryObject<Item> experiment_115 = ITEMS.register("experiment_115", () -> new ItemTFExperiment115(TFItems.EXPERIMENT_115, new Item.Properties()));
	public static final RegistryObject<Item> armor_shard = ITEMS.register("armor_shard", () -> new ItemTF(new Item.Properties()));
	public static final RegistryObject<Item> knightmetal_ingot = ITEMS.register("knightmetal_ingot", () -> new ItemTF(new Item.Properties()));
	public static final RegistryObject<Item> armor_shard_cluster = ITEMS.register("armor_shard_cluster", () -> new ItemTF(new Item.Properties()));
	public static final RegistryObject<Item> knightmetal_helmet = ITEMS.register("knightmetal_helmet", () -> new ItemTFKnightlyArmor(TwilightArmorMaterial.ARMOR_KNIGHTLY, EquipmentSlotType.HEAD, Rarity.COMMON, new Item.Properties()));
	public static final RegistryObject<Item> knightmetal_chestplate = ITEMS.register("knightmetal_chestplate", () -> new ItemTFKnightlyArmor(TwilightArmorMaterial.ARMOR_KNIGHTLY, EquipmentSlotType.CHEST, Rarity.COMMON, new Item.Properties()));
	public static final RegistryObject<Item> knightmetal_leggings = ITEMS.register("knightmetal_leggings", () -> new ItemTFKnightlyArmor(TwilightArmorMaterial.ARMOR_KNIGHTLY, EquipmentSlotType.LEGS, Rarity.COMMON, new Item.Properties()));
	public static final RegistryObject<Item> knightmetal_boots = ITEMS.register("knightmetal_boots", () -> new ItemTFKnightlyArmor(TwilightArmorMaterial.ARMOR_KNIGHTLY, EquipmentSlotType.FEET, Rarity.COMMON, new Item.Properties()));
	public static final RegistryObject<Item> knightmetal_sword = ITEMS.register("knightmetal_sword", () -> new ItemTFKnightlySword(TwilightItemTier.TOOL_KNIGHTLY, new Item.Properties()));
	public static final RegistryObject<Item> knightmetal_pickaxe = ITEMS.register("knightmetal_pickaxe", () -> new ItemTFKnightlyPick(TwilightItemTier.TOOL_KNIGHTLY, new Item.Properties()));
	public static final RegistryObject<Item> knightmetal_axe = ITEMS.register("knightmetal_axe", () -> new ItemTFKnightlyAxe(TwilightItemTier.TOOL_KNIGHTLY, new Item.Properties()));
	public static final RegistryObject<Item> knightmetal_shield = ITEMS.register("knightmetal_shield", () -> new ItemKnightlyShield(new Item.Properties()));
	public static final RegistryObject<Item> phantom_helmet = ITEMS.register("phantom_helmet", () -> new ItemTFPhantomArmor(TwilightArmorMaterial.ARMOR_PHANTOM, EquipmentSlotType.HEAD, Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> phantom_chestplate = ITEMS.register("phantom_chestplate", () -> new ItemTFPhantomArmor(TwilightArmorMaterial.ARMOR_PHANTOM, EquipmentSlotType.CHEST, Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> lamp_of_cinders = ITEMS.register("lamp_of_cinders", () -> new ItemTFLampOfCinders(Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> alpha_fur = ITEMS.register("alpha_fur", () -> new ItemTF(Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> yeti_helmet = ITEMS.register("yeti_helmet", () -> new ItemTFYetiArmor(TwilightArmorMaterial.ARMOR_YETI, EquipmentSlotType.HEAD, Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> yeti_chestplate = ITEMS.register("yeti_chestplate", () -> new ItemTFYetiArmor(TwilightArmorMaterial.ARMOR_YETI, EquipmentSlotType.CHEST, Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> yeti_leggings = ITEMS.register("yeti_leggings", () -> new ItemTFYetiArmor(TwilightArmorMaterial.ARMOR_YETI, EquipmentSlotType.LEGS, Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> yeti_boots = ITEMS.register("yeti_boots", () -> new ItemTFYetiArmor(TwilightArmorMaterial.ARMOR_YETI, EquipmentSlotType.FEET, Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> ice_bomb = ITEMS.register("ice_bomb", () -> new ItemTFIceBomb(new Item.Properties()));
	public static final RegistryObject<Item> arctic_fur = ITEMS.register("arctic_fur", () -> new ItemTF(new Item.Properties()));
	public static final RegistryObject<Item> arctic_helmet = ITEMS.register("arctic_helmet", () -> new ItemTFArcticArmor(TwilightArmorMaterial.ARMOR_ARCTIC, EquipmentSlotType.HEAD, Rarity.COMMON, new Item.Properties()));
	public static final RegistryObject<Item> arctic_chestplate = ITEMS.register("arctic_chestplate", () -> new ItemTFArcticArmor(TwilightArmorMaterial.ARMOR_ARCTIC, EquipmentSlotType.CHEST, Rarity.COMMON, new Item.Properties()));
	public static final RegistryObject<Item> arctic_leggings = ITEMS.register("arctic_leggings", () -> new ItemTFArcticArmor(TwilightArmorMaterial.ARMOR_ARCTIC, EquipmentSlotType.LEGS, Rarity.COMMON, new Item.Properties()));
	public static final RegistryObject<Item> arctic_boots = ITEMS.register("arctic_boots", () -> new ItemTFArcticArmor(TwilightArmorMaterial.ARMOR_ARCTIC, EquipmentSlotType.FEET, Rarity.COMMON, new Item.Properties()));
	public static final RegistryObject<Item> magic_beans = ITEMS.register("magic_beans", () -> new ItemTFMagicBeans(new Item.Properties()));
	public static final RegistryObject<Item> giant_pickaxe = ITEMS.register("giant_pickaxe", () -> new ItemTFGiantPick(TwilightItemTier.TOOL_GIANT, new Item.Properties()));
	public static final RegistryObject<Item> giant_sword = ITEMS.register("giant_sword", () -> new ItemTFGiantSword(TwilightItemTier.TOOL_GIANT, new Item.Properties()));
	public static final RegistryObject<Item> triple_bow = ITEMS.register("triple_bow", () -> new ItemTFTripleBow(new Item.Properties()));
	public static final RegistryObject<Item> seeker_bow = ITEMS.register("seeker_bow", () -> new ItemTFSeekerBow(new Item.Properties()));
	public static final RegistryObject<Item> ice_bow = ITEMS.register("ice_bow", () -> new ItemTFIceBow(new Item.Properties()));
	public static final RegistryObject<Item> ender_bow = ITEMS.register("ender_bow", () -> new ItemTFEnderBow(new Item.Properties()));
	public static final RegistryObject<Item> ice_sword = ITEMS.register("ice_sword", () -> new ItemTFIceSword(TwilightItemTier.TOOL_ICE, new Item.Properties()));
	public static final RegistryObject<Item> glass_sword = ITEMS.register("glass_sword", () -> new ItemTFGlassSword(TwilightItemTier.TOOL_GLASS, new Item.Properties()));
	public static final RegistryObject<Item> knightmetal_ring = ITEMS.register("knightmetal_ring", () -> new ItemTF(new Item.Properties()));
	public static final RegistryObject<Item> block_and_chain = ITEMS.register("block_and_chain", () -> new ItemTFChainBlock(new Item.Properties()));
	public static final RegistryObject<Item> cube_talisman = ITEMS.register("cube_talisman", () -> new ItemTF(Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> cube_of_annihilation = ITEMS.register("cube_of_annihilation", () -> new ItemTFCubeOfAnnihilation(Rarity.UNCOMMON, new Item.Properties()));
	public static final RegistryObject<Item> moon_dial = ITEMS.register("moon_dial", () -> new ItemTFMoonDial(new Item.Properties()));

	//TODO 1.14: These may potentially not be used
//	@GameRegistry.ObjectHolder("miniature_structure")
//	public static final RegistryObject<Item> miniature_structure;
//	@GameRegistry.ObjectHolder("castle_door")
//	public static final RegistryObject<Item> castle_door;
//	@GameRegistry.ObjectHolder("block_storage")
//	public static final RegistryObject<Item> block_storage;

	public static CreativeTabTwilightForest creativeTab = new CreativeTabTwilightForest("twilightForest");
}
