package twilightforest.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import twilightforest.TwilightForestMod;
import twilightforest.block.BlockTFLog;
import twilightforest.block.BlockTFMagicLog;
import twilightforest.block.TFBlocks;
import twilightforest.client.ModelRegisterCallback;
import twilightforest.compat.TFCompat;
import twilightforest.enums.*;
import twilightforest.util.IMapColorSupplier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.stream;
import static net.minecraft.init.Effects.REGENERATION;
import static net.minecraft.inventory.EquipmentSlotType.*;
import static net.minecraft.item.ItemTier.*;
import static twilightforest.item.TFItems.*;

@Mod.EventBusSubscriber(modid = TwilightForestMod.ID)
public class RegisterItemEvent {
	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		ItemRegistryHelper items = new ItemRegistryHelper(event.getRegistry());

		items.register("naga_scale", "nagaScale", new ItemTF(Rarity.UNCOMMON));
		items.register("naga_chestplate", "plateNaga", new ItemTFNagaArmor(TwilightArmorMaterial.ARMOR_NAGA, CHEST, Rarity.UNCOMMON).setMaxStackSize(1));
		items.register("naga_leggings", "legsNaga", new ItemTFNagaArmor(TwilightArmorMaterial.ARMOR_NAGA, LEGS, Rarity.UNCOMMON).setMaxStackSize(1));
		items.register("twilight_scepter", "scepterTwilight", new ItemTFTwilightWand(Rarity.UNCOMMON).setMaxStackSize(1));
		items.register("lifedrain_scepter", "scepterLifeDrain", new ItemTFScepterLifeDrain(Rarity.UNCOMMON).setMaxStackSize(1));
		items.register("zombie_scepter", "scepterZombie", new ItemTFZombieWand(Rarity.UNCOMMON).setMaxStackSize(1));
		items.register("shield_scepter", "scepterShield", new ItemTFShieldWand(Rarity.UNCOMMON).setMaxStackSize(1));
		//items.register("Wand of Pacification [NYI]", new ItemTF().setIconIndex(6).setTranslationKey("wandPacification").setMaxStackSize(1));
		items.register("ore_meter", "oreMeter", new ItemTFOreMeter().setMaxStackSize(1));
		items.register("magic_map", "magicMap", new ItemTFMagicMap().setMaxStackSize(1));
		items.register("maze_map", "mazeMap", new ItemTFMazeMap(false).setMaxStackSize(1));
		items.register("ore_map", "oreMap", new ItemTFMazeMap(true).setMaxStackSize(1));
		items.register("raven_feather", "tfFeather", new ItemTF());
		items.register("magic_map_focus", "magicMapFocus", new ItemTF());
		items.register("maze_map_focus", "mazeMapFocus", new ItemTF());
		items.register("liveroot", "liveRoot", new ItemTF());
		items.register("ironwood_raw", "ironwoodRaw", new ItemTF());
		items.register("ironwood_ingot", "ironwoodIngot", new ItemTF());
		items.register("ironwood_helmet", "ironwoodHelm", new ItemTFIronwoodArmor(TwilightArmorMaterial.ARMOR_IRONWOOD, HEAD, Rarity.COMMON).setMaxStackSize(1));
		items.register("ironwood_chestplate", "ironwoodPlate", new ItemTFIronwoodArmor(TwilightArmorMaterial.ARMOR_IRONWOOD, CHEST, Rarity.COMMON).setMaxStackSize(1));
		items.register("ironwood_leggings", "ironwoodLegs", new ItemTFIronwoodArmor(TwilightArmorMaterial.ARMOR_IRONWOOD, LEGS, Rarity.COMMON).setMaxStackSize(1));
		items.register("ironwood_boots", "ironwoodBoots", new ItemTFIronwoodArmor(TwilightArmorMaterial.ARMOR_IRONWOOD, FEET, Rarity.COMMON).setMaxStackSize(1));
		items.register("ironwood_sword", "ironwoodSword", new ItemTFIronwoodSword(TwilightItemTier.TOOL_IRONWOOD).setMaxStackSize(1));
		items.register("ironwood_shovel", "ironwoodShovel", new ItemTFIronwoodShovel(TwilightItemTier.TOOL_IRONWOOD).setMaxStackSize(1));
		items.register("ironwood_pickaxe", "ironwoodPick", new ItemTFIronwoodPick(TwilightItemTier.TOOL_IRONWOOD).setMaxStackSize(1));
		items.register("ironwood_axe", "ironwoodAxe", new ItemTFIronwoodAxe(TwilightItemTier.TOOL_IRONWOOD).setMaxStackSize(1));
		items.register("ironwood_hoe", "ironwoodHoe", new ItemTFIronwoodHoe(TwilightItemTier.TOOL_IRONWOOD).setMaxStackSize(1));
		items.register("torchberries", "torchberries", new ItemTF());
		items.register("raw_venison", "venisonRaw", new ItemTFFood(TFItems.FOOD_VENISON_RAW));
		items.register("cooked_venison", "venisonCooked", new ItemTFFood(TFItems.FOOD_VENISON_COOKED));
		items.register("hydra_chop", "hydraChop", new ItemTFHydraChops(TFItems.FOOD_HYDRA_CHOP);
		items.register("fiery_blood", "fieryBlood", new ItemTF(Rarity.UNCOMMON));
		items.register("fiery_tears", "fieryTears", new ItemTF(Rarity.UNCOMMON));
		items.register("trophy", "trophy", new ItemTFTrophy());
		items.register("fiery_ingot", "fieryIngot", new ItemTF(Rarity.UNCOMMON));
		items.register("fiery_helmet", "fieryHelm", new ItemTFFieryArmor(TwilightArmorMaterial.ARMOR_FIERY, HEAD, Rarity.UNCOMMON).setMaxStackSize(1));
		items.register("fiery_chestplate", "fieryPlate", new ItemTFFieryArmor(TwilightArmorMaterial.ARMOR_FIERY, CHEST, Rarity.UNCOMMON).setMaxStackSize(1));
		items.register("fiery_leggings", "fieryLegs", new ItemTFFieryArmor(TwilightArmorMaterial.ARMOR_FIERY, LEGS, Rarity.UNCOMMON).setMaxStackSize(1));
		items.register("fiery_boots", "fieryBoots", new ItemTFFieryArmor(TwilightArmorMaterial.ARMOR_FIERY, FEET, Rarity.UNCOMMON).setMaxStackSize(1));
		items.register("fiery_sword", "fierySword", new ItemTFFierySword(TwilightItemTier.TOOL_FIERY).setMaxStackSize(1));
		items.register("fiery_pickaxe", "fieryPick", new ItemTFFieryPick(TwilightItemTier.TOOL_FIERY).setMaxStackSize(1));
		items.register("steeleaf_ingot", "steeleafIngot", new ItemTF());
		items.register("steeleaf_helmet", "steeleafHelm", new ItemTFSteeleafArmor(TwilightArmorMaterial.ARMOR_STEELEAF, HEAD, Rarity.COMMON).setMaxStackSize(1));
		items.register("steeleaf_chestplate", "steeleafPlate", new ItemTFSteeleafArmor(TwilightArmorMaterial.ARMOR_STEELEAF, CHEST, Rarity.COMMON).setMaxStackSize(1));
		items.register("steeleaf_leggings", "steeleafLegs", new ItemTFSteeleafArmor(TwilightArmorMaterial.ARMOR_STEELEAF, LEGS, Rarity.COMMON).setMaxStackSize(1));
		items.register("steeleaf_boots", "steeleafBoots", new ItemTFSteeleafArmor(TwilightArmorMaterial.ARMOR_STEELEAF, FEET, Rarity.COMMON).setMaxStackSize(1));
		items.register("steeleaf_sword", "steeleafSword", new ItemTFSteeleafSword(TwilightItemTier.TOOL_STEELEAF).setMaxStackSize(1));
		items.register("steeleaf_shovel", "steeleafShovel", new ItemTFSteeleafShovel(TwilightItemTier.TOOL_STEELEAF).setMaxStackSize(1));
		items.register("steeleaf_pickaxe", "steeleafPick", new ItemTFSteeleafPick(TwilightItemTier.TOOL_STEELEAF).setMaxStackSize(1));
		items.register("steeleaf_axe", "steeleafAxe", new ItemTFSteeleafAxe(TwilightItemTier.TOOL_STEELEAF).setMaxStackSize(1));
		items.register("steeleaf_hoe", "steeleafHoe", new ItemTFSteeleafHoe(TwilightItemTier.TOOL_STEELEAF).setMaxStackSize(1));
		items.register("minotaur_axe_gold", "minotaurAxeGold", new ItemTFMinotaurAxe(GOLD, Rarity.COMMON).setMaxStackSize(1));
		items.register("minotaur_axe", "minotaurAxe", new ItemTFMinotaurAxe(DIAMOND, Rarity.UNCOMMON).setMaxStackSize(1));
		items.register("mazebreaker_pickaxe", "mazebreakerPick", new ItemTFMazebreakerPick(DIAMOND).setMaxStackSize(1));
		items.register("transformation_powder", "transformPowder", new ItemTFTransformPowder());
		items.register("raw_meef", "meefRaw", new ItemTFFood(TFItems.FOOD_MEEF_RAW));
		items.register("cooked_meef", "meefSteak", new ItemTFFood(TFItems.FOOD_MEEF_COOKED));
		items.register("meef_stroganoff", "meefStroganoff", new ItemTFSoup(TFItems.FOOD_MEEF_STROGANOFF));
		items.register("maze_wafer", "mazeWafer", new ItemTFFood(TFItems.FOOD_MAZE_WAFER));
		items.register("magic_map_empty", "emptyMagicMap", new ItemTFEmptyMagicMap());
		items.register("maze_map_empty", "emptyMazeMap", new ItemTFEmptyMazeMap(false));
		items.register("ore_map_empty", "emptyOreMap", new ItemTFEmptyMazeMap(true));
		items.register("ore_magnet", "oreMagnet", new ItemTFOreMagnet());
		items.register("crumble_horn", "crumbleHorn", new ItemTFCrumbleHorn(Rarity.RARE));
		items.register("peacock_fan", "peacockFan", new ItemTFPeacockFan(Rarity.RARE));
		items.register("moonworm_queen", "moonwormQueen", new ItemTFMoonwormQueen(Rarity.RARE));
		items.register("charm_of_life_1", "charmOfLife1", new ItemCharmBaubleable(Rarity.UNCOMMON));
		items.register("charm_of_life_2", "charmOfLife2", new ItemCharmBaubleable(Rarity.UNCOMMON));
		items.register("charm_of_keeping_1", "charmOfKeeping1", new ItemCharmBaubleable(Rarity.UNCOMMON));
		items.register("charm_of_keeping_2", "charmOfKeeping2", new ItemCharmBaubleable(Rarity.UNCOMMON));
		items.register("charm_of_keeping_3", "charmOfKeeping3", new ItemCharmBaubleable(Rarity.UNCOMMON));
		items.register("tower_key", "towerKey", new ItemTFTowerKey(Rarity.UNCOMMON));
		items.register("borer_essence", "borerEssence", new ItemTF());
		items.register("carminite", "carminite", new ItemTF());
		items.register("experiment_115", "experiment115", new ItemTFExperiment115(TFItems.FOOD_EXPERIMENT_115));
		items.register("armor_shard", "armorShards", new ItemTF());
		items.register("knightmetal_ingot", "knightMetal", new ItemTF());
		items.register("armor_shard_cluster", "shardCluster", new ItemTF());
		items.register("knightmetal_helmet", "knightlyHelm", new ItemTFKnightlyArmor(TwilightArmorMaterial.ARMOR_KNIGHTLY, HEAD, Rarity.COMMON).setMaxStackSize(1));
		items.register("knightmetal_chestplate", "knightlyPlate", new ItemTFKnightlyArmor(TwilightArmorMaterial.ARMOR_KNIGHTLY, CHEST, Rarity.COMMON).setMaxStackSize(1));
		items.register("knightmetal_leggings", "knightlyLegs", new ItemTFKnightlyArmor(TwilightArmorMaterial.ARMOR_KNIGHTLY, LEGS, Rarity.COMMON).setMaxStackSize(1));
		items.register("knightmetal_boots", "knightlyBoots", new ItemTFKnightlyArmor(TwilightArmorMaterial.ARMOR_KNIGHTLY, FEET, Rarity.COMMON).setMaxStackSize(1));
		items.register("knightmetal_sword", "knightlySword", new ItemTFKnightlySword(TwilightItemTier.TOOL_KNIGHTLY).setMaxStackSize(1));
		items.register("knightmetal_pickaxe", "knightlyPick", new ItemTFKnightlyPick(TwilightItemTier.TOOL_KNIGHTLY).setMaxStackSize(1));
		items.register("knightmetal_axe", "knightlyAxe", new ItemTFKnightlyAxe(TwilightItemTier.TOOL_KNIGHTLY).setMaxStackSize(1));
		items.register("knightmetal_shield", "knightlyShield", new ItemKnightlyShield().setMaxStackSize(1));
		items.register("phantom_helmet", "phantomHelm", new ItemTFPhantomArmor(TwilightArmorMaterial.ARMOR_PHANTOM, HEAD, Rarity.UNCOMMON).setMaxStackSize(1));
		items.register("phantom_chestplate", "phantomPlate", new ItemTFPhantomArmor(TwilightArmorMaterial.ARMOR_PHANTOM, CHEST, Rarity.UNCOMMON).setMaxStackSize(1));
		items.register("lamp_of_cinders", "lampOfCinders", new ItemTFLampOfCinders(Rarity.UNCOMMON));
		items.register("alpha_fur", "alphaFur", new ItemTF(Rarity.UNCOMMON));
		items.register("yeti_helmet", "yetiHelm", new ItemTFYetiArmor(TwilightArmorMaterial.ARMOR_YETI, HEAD, Rarity.UNCOMMON).setMaxStackSize(1));
		items.register("yeti_chestplate", "yetiPlate", new ItemTFYetiArmor(TwilightArmorMaterial.ARMOR_YETI, CHEST, Rarity.UNCOMMON).setMaxStackSize(1));
		items.register("yeti_leggings", "yetiLegs", new ItemTFYetiArmor(TwilightArmorMaterial.ARMOR_YETI, LEGS, Rarity.UNCOMMON).setMaxStackSize(1));
		items.register("yeti_boots", "yetiBoots", new ItemTFYetiArmor(TwilightArmorMaterial.ARMOR_YETI, FEET, Rarity.UNCOMMON).setMaxStackSize(1));
		items.register("ice_bomb", "iceBomb", new ItemTFIceBomb().setMaxStackSize(16));
		items.register("arctic_fur", "arcticFur", new ItemTF());
		items.register("arctic_helmet", "arcticHelm", new ItemTFArcticArmor(TwilightArmorMaterial.ARMOR_ARCTIC, HEAD, Rarity.COMMON).setMaxStackSize(1));
		items.register("arctic_chestplate", "arcticPlate", new ItemTFArcticArmor(TwilightArmorMaterial.ARMOR_ARCTIC, CHEST, Rarity.COMMON).setMaxStackSize(1));
		items.register("arctic_leggings", "arcticLegs", new ItemTFArcticArmor(TwilightArmorMaterial.ARMOR_ARCTIC, LEGS, Rarity.COMMON).setMaxStackSize(1));
		items.register("arctic_boots", "arcticBoots", new ItemTFArcticArmor(TwilightArmorMaterial.ARMOR_ARCTIC, FEET, Rarity.COMMON).setMaxStackSize(1));
		items.register("magic_beans", "magicBeans", new ItemTFMagicBeans());
		items.register("giant_pickaxe", "giantPick", new ItemTFGiantPick(TwilightItemTier.TOOL_GIANT).setMaxStackSize(1));
		items.register("giant_sword", "giantSword", new ItemTFGiantSword(TwilightItemTier.TOOL_GIANT).setMaxStackSize(1));
		items.register("triple_bow", "tripleBow", new ItemTFTripleBow().setMaxStackSize(1));
		items.register("seeker_bow", "seekerBow", new ItemTFSeekerBow().setMaxStackSize(1));
		items.register("ice_bow", "iceBow", new ItemTFIceBow().setMaxStackSize(1));
		items.register("ender_bow", "enderBow", new ItemTFEnderBow().setMaxStackSize(1));
		items.register("ice_sword", "iceSword", new ItemTFIceSword(TwilightItemTier.TOOL_ICE).setMaxStackSize(1));
		items.register("glass_sword", "glassSword", new ItemTFGlassSword(TwilightItemTier.TOOL_GLASS).setMaxStackSize(1));
		items.register("knightmetal_ring", "knightmetalRing", new ItemTF());
		items.register("block_and_chain", "chainBlock", new ItemTFChainBlock().setMaxStackSize(1));
		items.register("cube_talisman", "cubeTalisman", new ItemTF(Rarity.UNCOMMON));
		items.register("cube_of_annihilation", "cubeOfAnnihilation", new ItemTFCubeOfAnnihilation(Rarity.UNCOMMON).setMaxStackSize(1));
		items.register("moon_dial", "moonDial", new ItemTFMoonDial());

		String[] thornNames = stream(ThornVariant.values()).map(IStringSerializable::getName).toArray(String[]::new);
		String[] deadrockNames = stream(DeadrockVariant.values()).map(IStringSerializable::getName).toArray(String[]::new);
		// register blocks with their pickup values
		items.registerSubItemBlock(TFBlocks.twilight_log);
		items.registerSubItemBlock(TFBlocks.root);
		items.register(new ItemBlockTFLeaves(TFBlocks.twilight_leaves));
		items.register(new ItemBlockWearable(TFBlocks.firefly));
		items.register(new ItemBlockWearable(TFBlocks.cicada));
		items.registerSubItemBlock(TFBlocks.maze_stone);
		items.registerSubItemBlock(TFBlocks.hedge);
		items.registerSubItemBlock(TFBlocks.boss_spawner);
		items.registerBlock(TFBlocks.firefly_jar);
		items.register(new ItemBlockTFPlant(TFBlocks.twilight_plant));
		items.registerBlock(TFBlocks.uncrafting_table);
		items.registerSubItemBlock(TFBlocks.fire_jet);
		items.registerSubItemBlock(TFBlocks.naga_stone);
		items.register(new ItemBlockTFMeta(TFBlocks.twilight_sapling) {
			@Override
			public int getItemBurnTime(ItemStack itemStack) {
				return 100;
			}

			@Override
			public Rarity getRarity(ItemStack stack) {
				switch (stack.getMetadata()) {
					case 5:
					case 6:
					case 7:
					case 8:
						return Rarity.RARE;
					default:
						return Rarity.COMMON;
				}
			}
		}.setAppend(true));
		items.register(new ItemBlockWearable(TFBlocks.moonworm));
		items.registerSubItemBlock(TFBlocks.magic_log);
		items.register(new ItemBlockTFLeaves(TFBlocks.magic_leaves));
		items.registerSubItemBlock(TFBlocks.magic_log_core);
		items.registerSubItemBlock(TFBlocks.tower_wood);
		items.registerSubItemBlock(TFBlocks.tower_device);
		items.registerSubItemBlock(TFBlocks.tower_translucent);
		items.registerBlock(TFBlocks.stronghold_shield);
		items.registerSubItemBlock(TFBlocks.trophy_pedestal);
		items.registerBlock(TFBlocks.aurora_block);
		items.registerSubItemBlock(TFBlocks.underbrick);
		items.register(new ItemMultiTexture(TFBlocks.thorns, TFBlocks.thorns, thornNames));
		items.registerBlock(TFBlocks.burnt_thorns);
		items.registerBlock(TFBlocks.thorn_rose);
		items.register(new ItemBlockTFLeaves(TFBlocks.twilight_leaves_3));
		items.register(new ItemMultiTexture(TFBlocks.deadrock, TFBlocks.deadrock, deadrockNames));
		items.registerBlock(TFBlocks.dark_leaves);
		items.registerBlock(TFBlocks.aurora_pillar);
		items.register(new ItemSlab(TFBlocks.aurora_slab, TFBlocks.aurora_slab, TFBlocks.double_aurora_slab));
		items.registerBlock(TFBlocks.trollsteinn);
		items.registerBlock(TFBlocks.wispy_cloud);
		items.registerBlock(TFBlocks.fluffy_cloud);
		items.register(new ItemTFGiantBlock(TFBlocks.giant_cobblestone));
		items.register(new ItemTFGiantBlock(TFBlocks.giant_log));
		items.register(new ItemTFGiantBlock(TFBlocks.giant_leaves));
		items.register(new ItemTFGiantBlock(TFBlocks.giant_obsidian));
		items.registerBlock(TFBlocks.uberous_soil);
		items.registerBlock(TFBlocks.huge_stalk);
		items.registerBlock(TFBlocks.huge_mushgloom);
		items.registerBlock(TFBlocks.trollvidr);
		items.registerBlock(TFBlocks.unripe_trollber);
		items.registerBlock(TFBlocks.trollber);
		items.registerBlock(TFBlocks.knightmetal_block);
		items.register(new ItemBlockTFHugeLilyPad(TFBlocks.huge_lilypad));
		items.register(new ItemBlockTFHugeWaterLily(TFBlocks.huge_waterlily));
		items.registerSubItemBlock(TFBlocks.slider);
		items.registerSubItemBlock(TFBlocks.castle_brick);
		items.registerBlock(TFBlocks.castle_stairs_brick);
		items.registerBlock(TFBlocks.castle_stairs_cracked);
		items.registerBlock(TFBlocks.castle_stairs_worn);
		items.registerBlock(TFBlocks.castle_stairs_mossy);
		items.registerSubItemBlock(TFBlocks.castle_pillar);
		items.registerSubItemBlock(TFBlocks.castle_stairs);
		items.registerSubItemBlock(TFBlocks.castle_rune_brick);
		items.registerSubItemBlock(TFBlocks.force_field);
		items.registerBlock(TFBlocks.cinder_furnace);
		items.registerBlock(TFBlocks.cinder_log);
		items.registerSubItemBlock(TFBlocks.castle_door);
		items.register(new ItemTFMiniatureStructure(TFBlocks.miniature_structure));
		items.register(new ItemTFCompressed(TFBlocks.block_storage));
		//items.registerBlock(TFBlocks.lapis_block);
		items.registerBlock(TFBlocks.spiral_bricks);
		items.registerBlock(TFBlocks.etched_nagastone);
		items.registerBlock(TFBlocks.nagastone_pillar);
		items.registerSubItemBlock(TFBlocks.nagastone_stairs);
		items.registerBlock(TFBlocks.etched_nagastone_mossy);
		items.registerBlock(TFBlocks.nagastone_pillar_mossy);
		items.registerSubItemBlock(TFBlocks.nagastone_stairs_mossy);
		items.registerBlock(TFBlocks.etched_nagastone_weathered);
		items.registerBlock(TFBlocks.nagastone_pillar_weathered);
		items.registerSubItemBlock(TFBlocks.nagastone_stairs_weathered);
		items.registerBlock(TFBlocks.auroralized_glass);
		items.registerBlock(TFBlocks.iron_ladder);
		items.registerBlock(TFBlocks.terrorcotta_circle);
		items.registerBlock(TFBlocks.terrorcotta_diagonal);
		items.registerBlock(TFBlocks.stone_twist);
		items.registerBlock(TFBlocks.stone_twist_thin);

		registerWoodVariants(items, BlockTFLog.VARIANT, WoodVariant.values());
		registerWoodVariants(items, BlockTFMagicLog.VARIANT, MagicWoodVariant.values());

		TFCompat.initCompatItems(items);
	}

	private static <T extends IStringSerializable & Comparable<T> & IMapColorSupplier> void registerWoodVariants(ItemRegistryHelper items, IProperty<T> key, T[] types) {
		for (T woodType : types) {
			String woodName = woodType.getName();

			if ("oak".equals(woodName)) // Not really going to rename that enum entry just yet
				woodName = "twilight_oak";

			items.registerBlock(Block.REGISTRY.getObject(TwilightForestMod.prefix(woodName + "_planks")));
			items.registerBlock(Block.REGISTRY.getObject(TwilightForestMod.prefix(woodName + "_stairs")));
			BlockSlab slab = (BlockSlab) Block.REGISTRY.getObject(TwilightForestMod.prefix(woodName + "_slab"));
			items.register(woodName + "_slab", new ItemSlab(slab, slab, (BlockSlab) Block.REGISTRY.getObject(TwilightForestMod.prefix(woodName + "_doubleslab"))));
			items.registerBlock(Block.REGISTRY.getObject(TwilightForestMod.prefix(woodName + "_button")));

			ResourceLocation doorRL = TwilightForestMod.prefix(woodName + "_door");
			Block doorBlock = Block.REGISTRY.getObject(doorRL);
			items.register(doorRL.getPath(), new ItemDoor(doorBlock)).setTranslationKey(doorBlock.getTranslationKey());

			items.registerBlock(Block.REGISTRY.getObject(TwilightForestMod.prefix(woodName + "_trapdoor")));
			items.registerBlock(Block.REGISTRY.getObject(TwilightForestMod.prefix(woodName + "_fence")));
			items.registerBlock(Block.REGISTRY.getObject(TwilightForestMod.prefix(woodName + "_gate")));
			items.registerBlock(Block.REGISTRY.getObject(TwilightForestMod.prefix(woodName + "_plate")));
		}
	}

	public static List<ModelRegisterCallback> getItemModels() {
		return Collections.unmodifiableList(ItemRegistryHelper.itemModels);
	}

	public static class ItemRegistryHelper {

		static final List<ModelRegisterCallback> itemModels = new ArrayList<>();

		private final IForgeRegistry<Item> registry;

		ItemRegistryHelper(IForgeRegistry<Item> registry) {
			this.registry = registry;
		}

		<T extends Item> void register(String registryName, String translationKey, T item) {
			item.setTranslationKey(TwilightForestMod.ID + "." + translationKey);
			register(registryName, item);
		}

		public <T extends Item> Item register(String registryName, T item) {
			item.setRegistryName(TwilightForestMod.ID, registryName);
			if (item instanceof ModelRegisterCallback) {
				itemModels.add((ModelRegisterCallback) item);
			}
			registry.register(item);
			return item;
		}

		void registerBlock(Block block) {
			register(new ItemBlock(block));
		}

		void registerSubItemBlock(Block block) {
			registerSubItemBlock(block, true);
		}

		void registerSubItemBlock(Block block, boolean shouldAppendNumber) {
			register(new ItemBlockTFMeta(block).setAppend(shouldAppendNumber));
		}

		<T extends ItemBlock> void register(T item) {
			item.setRegistryName(item.getBlock().getRegistryName());
			item.setTranslationKey(item.getBlock().getTranslationKey());
			registry.register(item);
		}
	}
}
