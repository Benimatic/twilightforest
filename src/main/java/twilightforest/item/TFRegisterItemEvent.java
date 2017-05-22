package twilightforest.item;

import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import twilightforest.TwilightForestMod;

import static twilightforest.item.TFItems.*;

@Mod.EventBusSubscriber()
public class TFRegisterItemEvent {
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        ItemRegistryHelper items = new ItemRegistryHelper(event.getRegistry());
        
        items.register("naga_scale", new ItemTF().setUnlocalizedName("nagaScale"));
        items.register("naga_chestplate", new ItemTFNagaArmor(ARMOR_NAGA, EntityEquipmentSlot.CHEST).setUnlocalizedName("plateNaga").setMaxStackSize(1));
        items.register("naga_leggings", new ItemTFNagaArmor(ARMOR_NAGA, EntityEquipmentSlot.LEGS).setUnlocalizedName("legsNaga").setMaxStackSize(1));
        items.register("twilight_scepter", new ItemTFTwilightWand().setUnlocalizedName("scepterTwilight").setMaxStackSize(1));
        items.register("lifedrain_scepter", new ItemTFScepterLifeDrain().setUnlocalizedName("scepterLifeDrain").setMaxStackSize(1));
        items.register("zombie_scepter", new ItemTFZombieWand().setUnlocalizedName("scepterZombie").setMaxStackSize(1));
        //items.register("Wand of Pacification [NYI]", new ItemTF().setIconIndex(6).setUnlocalizedName("wandPacification").setMaxStackSize(1));
        items.register("ore_meter", new ItemTFOreMeter().setUnlocalizedName("oreMeter").setMaxStackSize(1));
        items.register("magic_map", new ItemTFMagicMap().setUnlocalizedName("magicMap").setMaxStackSize(1));
        items.register("maze_map", new ItemTFMazeMap(false).setUnlocalizedName("mazeMap").setMaxStackSize(1));
        items.register("ore_Map", new ItemTFMazeMap(true).setUnlocalizedName("oreMap").setMaxStackSize(1));
        items.register("raven_feather", new ItemTF().setUnlocalizedName("tfFeather"));
        items.register("magic_map_focus", new ItemTF().setUnlocalizedName("magicMapFocus"));
        items.register("maze_map_focus", new ItemTF().setUnlocalizedName("mazeMapFocus"));
        items.register("liveroot", new ItemTF().setUnlocalizedName("liveRoot"));
        items.register("ironwood_raw", new ItemTF().setUnlocalizedName("ironwoodRaw"));
        items.register("ironwood_ingot", new ItemTF().setUnlocalizedName("ironwoodIngot"));
        items.register("ironwood_helmet", new ItemTFIronwoodArmor(ARMOR_IRONWOOD, EntityEquipmentSlot.HEAD).setUnlocalizedName("ironwoodHelm").setMaxStackSize(1));
        items.register("ironwood_chestplate", new ItemTFIronwoodArmor(ARMOR_IRONWOOD, EntityEquipmentSlot.CHEST).setUnlocalizedName("ironwoodPlate").setMaxStackSize(1));
        items.register("ironwood_leggings", new ItemTFIronwoodArmor(ARMOR_IRONWOOD, EntityEquipmentSlot.LEGS).setUnlocalizedName("ironwoodLegs").setMaxStackSize(1));
        items.register("ironwood_boots", new ItemTFIronwoodArmor(ARMOR_IRONWOOD, EntityEquipmentSlot.FEET).setUnlocalizedName("ironwoodBoots").setMaxStackSize(1));
        items.register("ironwood_sword", new ItemTFIronwoodSword(TOOL_IRONWOOD).setUnlocalizedName("ironwoodSword").setMaxStackSize(1));
        items.register("ironwood_shovel", new ItemTFIronwoodShovel(TOOL_IRONWOOD).setUnlocalizedName("ironwoodShovel").setMaxStackSize(1));
        items.register("ironwood_pickaxe", new ItemTFIronwoodPick(TOOL_IRONWOOD).setUnlocalizedName("ironwoodPick").setMaxStackSize(1));
        items.register("ironwood_axe", new ItemTFIronwoodAxe(TOOL_IRONWOOD).setUnlocalizedName("ironwoodAxe").setMaxStackSize(1));
        items.register("ironwood_hoe", new ItemTFIronwoodHoe(TOOL_IRONWOOD).setUnlocalizedName("ironwoodHoe").setMaxStackSize(1));
        items.register("torchberries", new ItemTF().setUnlocalizedName("torchberries"));
        items.register("raw_venison", (new ItemTFFood(3, 0.3F, true)).setUnlocalizedName("venisonRaw"));
        items.register("cooked_venison", (new ItemTFFood(8, 0.8F, true)).setUnlocalizedName("venisonCooked"));
        items.register("hydra_chop", (new ItemTFHydraChops(18, 2.0F, true)).setPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 0), 1.0F).setUnlocalizedName("hydraChop"));
        items.register("fiery_blood", new ItemTF().makeRare().setUnlocalizedName("fieryBlood"));
        items.register("trophy", new ItemTFTrophy().setUnlocalizedName("trophy"));
        items.register("fiery_ingot", new ItemTF().makeRare().setUnlocalizedName("fieryIngot"));
        items.register("fiery_helmet", new ItemTFFieryArmor(ARMOR_FIERY, EntityEquipmentSlot.HEAD).setUnlocalizedName("fieryHelm").setMaxStackSize(1));
        items.register("fiery_chestplate", new ItemTFFieryArmor(ARMOR_FIERY, EntityEquipmentSlot.CHEST).setUnlocalizedName("fieryPlate").setMaxStackSize(1));
        items.register("fiery_leggings", new ItemTFFieryArmor(ARMOR_FIERY, EntityEquipmentSlot.LEGS).setUnlocalizedName("fieryLegs").setMaxStackSize(1));
        items.register("fiery_boots", new ItemTFFieryArmor(ARMOR_FIERY, EntityEquipmentSlot.FEET).setUnlocalizedName("fieryBoots").setMaxStackSize(1));
        items.register("fiery_sword", new ItemTFFierySword(TOOL_FIERY).setUnlocalizedName("fierySword").setMaxStackSize(1));
        items.register("fiery_pickaxe", new ItemTFFieryPick(TOOL_FIERY).setUnlocalizedName("fieryPick").setMaxStackSize(1));
        items.register("steeleaf_ingot", new ItemTF().setUnlocalizedName("steeleafIngot"));
        items.register("steeleaf_helmet", new ItemTFSteeleafArmor(ARMOR_IRONWOOD, EntityEquipmentSlot.HEAD).setUnlocalizedName("steeleafHelm").setMaxStackSize(1));
        items.register("steeleaf_chestplate", new ItemTFSteeleafArmor(ARMOR_IRONWOOD, EntityEquipmentSlot.CHEST).setUnlocalizedName("steeleafPlate").setMaxStackSize(1));
        items.register("steeleaf_leggings", new ItemTFSteeleafArmor(ARMOR_IRONWOOD, EntityEquipmentSlot.LEGS).setUnlocalizedName("steeleafLegs").setMaxStackSize(1));
        items.register("steeleaf_boots", new ItemTFSteeleafArmor(ARMOR_IRONWOOD, EntityEquipmentSlot.FEET).setUnlocalizedName("steeleafBoots").setMaxStackSize(1));
        items.register("steeleaf_sword", new ItemTFSteeleafSword(TOOL_STEELEAF).setUnlocalizedName("steeleafSword").setMaxStackSize(1));
        items.register("steeleaf_shovel", new ItemTFSteeleafShovel(TOOL_STEELEAF).setUnlocalizedName("steeleafShovel").setMaxStackSize(1));
        items.register("steeleaf_pickaxe", new ItemTFSteeleafPick(TOOL_STEELEAF).setUnlocalizedName("steeleafPick").setMaxStackSize(1));
        items.register("steeleaf_axe", new ItemTFSteeleafAxe(TOOL_STEELEAF).setUnlocalizedName("steeleafAxe").setMaxStackSize(1));
        items.register("steeleaf_hoe", new ItemTFSteeleafHoe(TOOL_STEELEAF).setUnlocalizedName("steeleafHoe").setMaxStackSize(1));
        items.register("minotaur_axe", new ItemTFMinotaurAxe(Item.ToolMaterial.DIAMOND).setUnlocalizedName("minotaurAxe").setMaxStackSize(1));
        items.register("mazebreaker_pickaxe", new ItemTFMazebreakerPick(Item.ToolMaterial.DIAMOND).setUnlocalizedName("mazebreakerPick").setMaxStackSize(1));
        items.register("transformation_powder", new ItemTFTransformPowder().makeRare().setUnlocalizedName("transformPowder"));
        items.register("raw_meef", (new ItemTFFood(2, 0.3F, true)).setUnlocalizedName("meefRaw"));
        items.register("cooked_meef", (new ItemTFFood(6, 0.6F, true)).setUnlocalizedName("meefSteak"));
        items.register("meef_stroganoff", (new ItemTFSoup(8)).setUnlocalizedName("meefStroganoff"));
        items.register("maze_wafer", (new ItemTFFood(4, 0.6F, false)).setUnlocalizedName("mazeWafer"));
        items.register("magic_map_empty", (new ItemTFEmptyMagicMap()).setUnlocalizedName("emptyMagicMap"));
        items.register("maze_map_empty", (new ItemTFEmptyMazeMap(false)).setUnlocalizedName("emptyMazeMap"));
        items.register("ore_map_empty", (new ItemTFEmptyMazeMap(true)).setUnlocalizedName("emptyOreMap"));
        items.register("ore_magnet", (new ItemTFOreMagnet()).setUnlocalizedName("oreMagnet"));
        items.register("crumble_horn", (new ItemTFCrumbleHorn()).setUnlocalizedName("crumbleHorn"));
        items.register("peacock_fan", (new ItemTFPeacockFan()).setUnlocalizedName("peacockFan"));
        items.register("moonworm_queen", (new ItemTFMoonwormQueen()).setUnlocalizedName("moonwormQueen"));
        items.register("charm_of_life_1", new ItemTFCharm().setUnlocalizedName("charmOfLife1"));
        items.register("charm_of_life_2", new ItemTFCharm().setUnlocalizedName("charmOfLife2"));
        items.register("charm_of_keeping_1", new ItemTFCharm().setUnlocalizedName("charmOfKeeping1"));
        items.register("charm_of_keeping_2", new ItemTFCharm().setUnlocalizedName("charmOfKeeping2"));
        items.register("charm_of_keeping_3", new ItemTFCharm().setUnlocalizedName("charmOfKeeping3"));
        items.register("tower_key", new ItemTFTowerKey().setUnlocalizedName("towerKey"));
        items.register("borer_essence", new ItemTF().setUnlocalizedName("borerEssence"));
        items.register("carminite", new ItemTF().makeRare().setUnlocalizedName("carminite"));
        items.register("experiment_115", (new ItemTFFood(4, 0.3F, false)).setUnlocalizedName("experiment115"));
        items.register("armor_shard", new ItemTF().setUnlocalizedName("armorShards"));
        items.register("knightmetal_ingot", new ItemTF().setUnlocalizedName("knightMetal"));
        items.register("armor_shard_cluster", new ItemTF().setUnlocalizedName("shardCluster"));
        items.register("knightmetal_helmet", new ItemTFKnightlyArmor(ARMOR_KNIGHTLY, EntityEquipmentSlot.HEAD).setUnlocalizedName("knightlyHelm").setMaxStackSize(1));
        items.register("knightmetal_chestplate", new ItemTFKnightlyArmor(ARMOR_KNIGHTLY, EntityEquipmentSlot.CHEST).setUnlocalizedName("knightlyPlate").setMaxStackSize(1));
        items.register("knightmetal_leggings", new ItemTFKnightlyArmor(ARMOR_KNIGHTLY, EntityEquipmentSlot.LEGS).setUnlocalizedName("knightlyLegs").setMaxStackSize(1));
        items.register("knightmetal_boots", new ItemTFKnightlyArmor(ARMOR_KNIGHTLY, EntityEquipmentSlot.FEET).setUnlocalizedName("knightlyBoots").setMaxStackSize(1));
        items.register("knightmetal_sword", new ItemTFKnightlySword(TOOL_KNIGHTLY).setUnlocalizedName("knightlySword").setMaxStackSize(1));
        items.register("knightmetal_pickaxe", new ItemTFKnightlyPick(TOOL_KNIGHTLY).setUnlocalizedName("knightlyPick").setMaxStackSize(1));
        items.register("knightmetal_axe", new ItemTFKnightlyAxe(TOOL_KNIGHTLY).setUnlocalizedName("knightlyAxe").setMaxStackSize(1));
        items.register("phantom_helmet", new ItemTFPhantomArmor(ARMOR_PHANTOM, EntityEquipmentSlot.HEAD).setUnlocalizedName("phantomHelm").setMaxStackSize(1));
        items.register("phantom_chestplate", new ItemTFPhantomArmor(ARMOR_PHANTOM, EntityEquipmentSlot.CHEST).setUnlocalizedName("phantomPlate").setMaxStackSize(1));
        items.register("lamp_of_cinders", new ItemTFLampOfCinders().setUnlocalizedName("lampOfCinders"));
        items.register("fiery_tears", new ItemTF().makeRare().setUnlocalizedName("fieryTears"));
        items.register("alpha_fur", new ItemTF().makeRare().setUnlocalizedName("alphaFur"));
        items.register("yeti_helmet", new ItemTFYetiArmor(ARMOR_YETI, EntityEquipmentSlot.HEAD).setUnlocalizedName("yetiHelm").setMaxStackSize(1));
        items.register("yeti_chestplate", new ItemTFYetiArmor(ARMOR_YETI, EntityEquipmentSlot.CHEST).setUnlocalizedName("yetiPlate").setMaxStackSize(1));
        items.register("yeti_leggings", new ItemTFYetiArmor(ARMOR_YETI, EntityEquipmentSlot.LEGS).setUnlocalizedName("yetiLegs").setMaxStackSize(1));
        items.register("yeti_boots", new ItemTFYetiArmor(ARMOR_YETI, EntityEquipmentSlot.FEET).setUnlocalizedName("yetiBoots").setMaxStackSize(1));
        items.register("ice_bomb", new ItemTFIceBomb().makeRare().setUnlocalizedName("iceBomb").setMaxStackSize(16));
        items.register("arctic_fur", new ItemTF().setUnlocalizedName("arcticFur"));
        items.register("arctic_helmet", new ItemTFArcticArmor(ARMOR_ARCTIC, EntityEquipmentSlot.HEAD).setUnlocalizedName("arcticHelm").setMaxStackSize(1));
        items.register("arctic_chestplate", new ItemTFArcticArmor(ARMOR_ARCTIC, EntityEquipmentSlot.CHEST).setUnlocalizedName("arcticPlate").setMaxStackSize(1));
        items.register("arctic_leggings", new ItemTFArcticArmor(ARMOR_ARCTIC, EntityEquipmentSlot.LEGS).setUnlocalizedName("arcticLegs").setMaxStackSize(1));
        items.register("arctic_boots", new ItemTFArcticArmor(ARMOR_ARCTIC, EntityEquipmentSlot.FEET).setUnlocalizedName("arcticBoots").setMaxStackSize(1));
        items.register("magic_beans", new ItemTFMagicBeans().setUnlocalizedName("magicBeans"));
        items.register("giant_pickaxe", new ItemTFGiantPick(TOOL_GIANT).setUnlocalizedName("giantPick").setMaxStackSize(1));
        items.register("giant_sword", new ItemTFGiantSword(TOOL_GIANT).setUnlocalizedName("giantSword").setMaxStackSize(1));
        items.register("triple_bow", new ItemTFTripleBow().setUnlocalizedName("tripleBow").setMaxStackSize(1));
        items.register("seeker_bow", new ItemTFSeekerBow().setUnlocalizedName("seekerBow").setMaxStackSize(1));
        items.register("ice_bow", new ItemTFIceBow().setUnlocalizedName("iceBow").setMaxStackSize(1));
        items.register("ender_bow", new ItemTFEnderBow().setUnlocalizedName("enderBow").setMaxStackSize(1));
        items.register("ice_sword", new ItemTFIceSword(TOOL_ICE).setUnlocalizedName("iceSword").setMaxStackSize(1));
        items.register("glass_sword", new ItemTFGlassSword(TOOL_GLASS).setUnlocalizedName("glassSword").setMaxStackSize(1));
        items.register("knightmetal_ring", new ItemTF().setUnlocalizedName("knightmetalRing"));
        items.register("block_and_chain", new ItemTFChainBlock().setUnlocalizedName("chainBlock").setMaxStackSize(1));
        items.register("cube_talisman", new ItemTF().setUnlocalizedName("cubeTalisman"));
        items.register("cube_of_annihilation", new ItemTFCubeOfAnnihilation().setUnlocalizedName("cubeOfAnnihilation").setMaxStackSize(1));
    }

    private static class ItemRegistryHelper {
        private final IForgeRegistry<Item> registry;

        ItemRegistryHelper(IForgeRegistry<Item> registry)
        {
            this.registry = registry;
        }

        private void register(String registryName, Item item)
        {
            item.setRegistryName(TwilightForestMod.ID, registryName);
            registry.register(item);
        }
    }
}
