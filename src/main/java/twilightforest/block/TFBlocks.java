package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.enums.PlantVariant;
import twilightforest.item.TFItems;
import twilightforest.world.feature.*;

import javax.annotation.Nonnull;

import static net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
@ObjectHolder(TwilightForestMod.ID)
@Nonnull
public class TFBlocks {
	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, TwilightForestMod.ID);

	public static final RegistryObject<Block> oak_log                    = BLOCKS.register("oak_log", () -> new BlockTFLog(MaterialColor.WOOD, MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> canopy_log                 = BLOCKS.register("canopy_log", () -> new BlockTFLog(MaterialColor.OBSIDIAN, MaterialColor.BROWN));
	public static final RegistryObject<Block> mangrove_log               = BLOCKS.register("mangrove_log", () -> new BlockTFLog(MaterialColor.DIRT, MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> dark_log                   = BLOCKS.register("dark_log", () -> new BlockTFLog(MaterialColor.ADOBE, MaterialColor.STONE));
	public static final RegistryObject<Block> oak_leaves                 = BLOCKS.register("oak_leaves", () -> new BlockTFLeaves());
	public static final RegistryObject<Block> canopy_leaves              = BLOCKS.register("canopy_leaves", () -> new BlockTFLeaves());
	public static final RegistryObject<Block> mangrove_leaves            = BLOCKS.register("mangrove_leaves", () -> new BlockTFLeaves());
	public static final RegistryObject<Block> rainboak_leaves            = BLOCKS.register("rainboak_leaves", () -> new BlockTFLeaves());
	public static final RegistryObject<Block> firefly                    = BLOCKS.register("firefly", () -> new BlockTFFirefly());
	public static final BlockTFPortal twilight_portal    = iCantBelieveItsNotNull();
	public static final RegistryObject<Block> maze_stone                 = BLOCKS.register("maze_stone", () -> new BlockTFMazestone());
	public static final RegistryObject<Block> maze_stone_brick           = BLOCKS.register("maze_stone_brick", () -> new BlockTFMazestone());
	public static final RegistryObject<Block> maze_stone_chiseled        = BLOCKS.register("maze_stone_chiseled", () -> new BlockTFMazestone());
	public static final RegistryObject<Block> maze_stone_decorative      = BLOCKS.register("maze_stone_decorative", () -> new BlockTFMazestone());
	public static final RegistryObject<Block> maze_stone_cracked         = BLOCKS.register("maze_stone_cracked", () -> new BlockTFMazestone());
	public static final RegistryObject<Block> maze_stone_mossy           = BLOCKS.register("maze_stone_mossy", () -> new BlockTFMazestone());
	public static final RegistryObject<Block> maze_stone_mosaic          = BLOCKS.register("maze_stone_mosaic", () -> new BlockTFMazestone());
	public static final RegistryObject<Block> maze_stone_border          = BLOCKS.register("maze_stone_border", () -> new BlockTFMazestone());
	public static final Block hedge                      = iCantBelieveItsNotNull(); //TODO Flatten?
	public static final RegistryObject<Block> boss_spawner               = BLOCKS.register("boss_spawner", () -> new BlockTFBossSpawner());
	public static final RegistryObject<Block> firefly_jar                = BLOCKS.register("firefly_jar", () -> new BlockTFFireflyJar());
	public static final RegistryObject<Block> moss_patch                 = BLOCKS.register("moss_patch", () -> new BlockTFPlant(PlantVariant.MOSSPATCH));
	public static final RegistryObject<Block> mayapple                   = BLOCKS.register("mayapple", () -> new BlockTFPlant(PlantVariant.MAYAPPLE));
	public static final RegistryObject<Block> clover_patch               = BLOCKS.register("clover_patch", () -> new BlockTFPlant(PlantVariant.CLOVERPATCH));
	public static final RegistryObject<Block> fiddlehead                 = BLOCKS.register("fiddlehead", () -> new BlockTFPlant(PlantVariant.FIDDLEHEAD));
	public static final RegistryObject<Block> mushgloom                  = BLOCKS.register("mushgloom", () -> new BlockTFPlant(PlantVariant.MUSHGLOOM));
	public static final RegistryObject<Block> forest_grass               = BLOCKS.register("forest_grass", () -> new BlockTFPlant(PlantVariant.FORESTGRASS));
	public static final RegistryObject<Block> dead_bush                  = BLOCKS.register("dead_bush", () -> new BlockTFPlant(PlantVariant.DEADBUSH));
	public static final RegistryObject<Block> torchberry                 = BLOCKS.register("torchberry", () -> new BlockTFPlant(PlantVariant.TORCHBERRY));
	public static final RegistryObject<Block> root_strand                = BLOCKS.register("root_strand", () -> new BlockTFPlant(PlantVariant.ROOT_STRAND));
	public static final RegistryObject<Block> fallen_leaves              = BLOCKS.register("fallen_leaves", () -> new BlockTFPlant(PlantVariant.FALLEN_LEAVES));
	public static final RegistryObject<Block> cicada                     = BLOCKS.register("cicada", () -> new BlockTFCicada());
	public static final RegistryObject<Block> root                       = BLOCKS.register("root", () -> new BlockTFRoots());
	public static final RegistryObject<Block> liveroot                   = BLOCKS.register("liveroot", () -> new BlockTFRoots());
	public static final RegistryObject<Block> uncrafting_table           = BLOCKS.register("uncrafting_table", () -> new BlockTFUncraftingTable());
	public static final Block fire_jet                   = iCantBelieveItsNotNull(); //TODO Flatten
	public static final Block naga_stone                 = iCantBelieveItsNotNull(); //TODO Flatten
	public static final RegistryObject<Block> oak_sapling                = BLOCKS.register("oak_sapling", () -> new BlockTFSapling(new TFGenSmallTwilightOak(true))); //TODO Tree
	public static final RegistryObject<Block> canopy_sapling             = BLOCKS.register("canopy_sapling", () -> new BlockTFSapling(new TFGenCanopyOak(true))); //TODO Tree
	public static final RegistryObject<Block> mangrove_sapling           = BLOCKS.register("mangrove_sapling", () -> new BlockTFSapling(new TFGenMangroveTree(true))); //TODO Tree
	public static final RegistryObject<Block> darkwood_sapling           = BLOCKS.register("darkwood_sapling", () -> new BlockTFSapling(new TFGenDarkCanopyTree(true))); //TODO Tree
	public static final RegistryObject<Block> hollow_oak_sapling         = BLOCKS.register("hollow_oak_sapling", () -> new BlockTFSapling(new TFGenHollowTree(true))); //TODO Tree
	public static final RegistryObject<Block> time_sapling               = BLOCKS.register("time_sapling", () -> new BlockTFSapling(new TFGenTreeOfTime(true))); //TODO Tree
	public static final RegistryObject<Block> transformation_sapling     = BLOCKS.register("transformation_sapling", () -> new BlockTFSapling(new TFGenTreeOfTransformation(true))); //TODO Tree
	public static final RegistryObject<Block> mining_sapling             = BLOCKS.register("mining_sapling", () -> new BlockTFSapling(new TFGenMinersTree(true))); //TODO Tree
	public static final RegistryObject<Block> sorting_sapling            = BLOCKS.register("sorting_sapling", () -> new BlockTFSapling(new TFGenSortingTree(true))); //TODO Tree
	public static final RegistryObject<Block> rainboak_sapling           = BLOCKS.register("rainboak_sapling", () -> new BlockTFSapling(new TFGenSmallRainboak())); //TODO Tree
	public static final RegistryObject<Block> time_log                   = BLOCKS.register("time_log", () -> new BlockTFMagicLog(MaterialColor.DIRT, MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> transformation_log         = BLOCKS.register("transformation_log", () -> new BlockTFMagicLog(MaterialColor.WOOD, MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> mining_log                 = BLOCKS.register("mining_log", () -> new BlockTFMagicLog(MaterialColor.SAND, MaterialColor.QUARTZ));
	public static final RegistryObject<Block> sorting_log                = BLOCKS.register("sorting_log", () -> new BlockTFMagicLog(MaterialColor.OBSIDIAN, MaterialColor.BROWN));
	public static final Block magic_log_core                             = iCantBelieveItsNotNull(); //TODO Flatten
	public static final RegistryObject<Block> time_leaves                = BLOCKS.register("time_leaves", () -> new BlockTFMagicLeaves());
	public static final RegistryObject<Block> transformation_leaves      = BLOCKS.register("transformation_leaves", () -> new BlockTFMagicLeaves());
	public static final RegistryObject<Block> mining_leaves              = BLOCKS.register("mining_leaves", () -> new BlockTFMagicLeaves());
	public static final RegistryObject<Block> sorting_leaves             = BLOCKS.register("sorting_leaves", () -> new BlockTFMagicLeaves());
	public static final RegistryObject<Block> moonworm                   = BLOCKS.register("moonworm", () -> new BlockTFMoonworm());
	public static final RegistryObject<Block> tower_wood                 = BLOCKS.register("tower_wood", () -> new BlockTFTowerWood(MaterialColor.ADOBE, 40.0F)); //TODO Flatten
	public static final RegistryObject<Block> tower_wood_encased         = BLOCKS.register("tower_wood_encased", () -> new BlockTFTowerWood(MaterialColor.SAND, 40.0F)); //TODO Flatten
	public static final RegistryObject<Block> tower_wood_cracked         = BLOCKS.register("tower_wood_cracked", () -> new BlockTFTowerWood(MaterialColor.ADOBE, 40.0F)); //TODO Flatten
	public static final RegistryObject<Block> tower_wood_mossy           = BLOCKS.register("tower_wood_mossy", () -> new BlockTFTowerWood(MaterialColor.ADOBE, 40.0F)); //TODO Flatten
	public static final RegistryObject<Block> tower_wood_infested        = BLOCKS.register("tower_wood_infested", () -> new BlockTFTowerWood(MaterialColor.ADOBE, 0.75F)); //TODO Flatten
	public static final BlockTFTowerDevice tower_device                  = iCantBelieveItsNotNull(); //TODO Flatten
	//public static Block towerAntenna                                   = null; // Not you
	public static final Block tower_translucent          = iCantBelieveItsNotNull(); //TODO Flatten?
	public static final Block trophy                     = iCantBelieveItsNotNull(); //TODO Flatten
	public static final Block stronghold_shield          = iCantBelieveItsNotNull();
	public static final RegistryObject<Block> trophy_pedestal            = BLOCKS.register("trophy_pedestal", () -> new BlockTFTrophyPedestal());
	public static final RegistryObject<Block> aurora_block               = BLOCKS.register("aurora_block", () -> new BlockTFAuroraBrick());
	public static final RegistryObject<Block> underbrick                 = BLOCKS.register("underbrick", () -> new BlockTFUnderBrick());
	public static final RegistryObject<Block> underbrick_mossy           = BLOCKS.register("underbrick_mossy", () -> new BlockTFUnderBrick());
	public static final RegistryObject<Block> underbrick_cracked         = BLOCKS.register("underbrick_cracked", () -> new BlockTFUnderBrick());
	public static final RegistryObject<Block> underbrick_floor           = BLOCKS.register("underbrick_floor", () -> new BlockTFUnderBrick());
	public static final Block thorns                     = iCantBelieveItsNotNull(); //TODO Flatten
	public static final Block burnt_thorns               = iCantBelieveItsNotNull();
	public static final Block thorn_rose                 = iCantBelieveItsNotNull();
	public static final RegistryObject<Block> thorn_leaves              = BLOCKS.register("thorn_leaves", () -> new BlockTFLeaves3());
	public static final RegistryObject<Block> beanstalk_leaves          = BLOCKS.register("beanstalk_leaves", () -> new BlockTFLeaves3());
	public static final RegistryObject<Block> deadrock                  = BLOCKS.register("deadrock", () -> new BlockTFDeadrock());
	public static final RegistryObject<Block> deadrock_cracked          = BLOCKS.register("deadrock_cracked", () -> new BlockTFDeadrock());
	public static final RegistryObject<Block> deadrock_weathered        = BLOCKS.register("deadrock_weathered", () -> new BlockTFDeadrock());
	public static final RegistryObject<Block> dark_leaves               = BLOCKS.register("dark_leaves", () -> new BlockTFDarkLeaves());
	public static final RegistryObject<Block> aurora_pillar             = BLOCKS.register("aurora_pillar", () -> new BlockTFPillar(Material.PACKED_ICE, 2.0F, 10.0F));
	public static final RegistryObject<Block> aurora_slab               = BLOCKS.register("aurora_slab", () -> new BlockTFAuroraSlab());
	public static final Block trollsteinn                = iCantBelieveItsNotNull();
	public static final Block wispy_cloud                = iCantBelieveItsNotNull();
	public static final RegistryObject<Block> fluffy_cloud              = BLOCKS.register("fluffy_cloud", () -> new BlockTFFluffyCloud());
	public static final Block giant_cobblestone          = iCantBelieveItsNotNull();
	public static final Block giant_log                  = iCantBelieveItsNotNull();
	public static final Block giant_leaves               = iCantBelieveItsNotNull();
	public static final Block giant_obsidian             = iCantBelieveItsNotNull();
	public static final RegistryObject<Block> uberous_soil              = BLOCKS.register("uberous_soil", () -> new BlockTFUberousSoil());
	public static final Block huge_stalk                 = iCantBelieveItsNotNull();
	public static final RegistryObject<Block> huge_mushgloom            = BLOCKS.register("huge_mushgloom", () -> new BlockTFHugeGloomBlock());
	public static final RegistryObject<Block> trollvidr                 = BLOCKS.register("trollvidr", () -> new BlockTFTrollRoot());
	public static final RegistryObject<Block> unripe_trollber           = BLOCKS.register("unripe_trollber", () -> new BlockTFUnripeTorchCluster());
	public static final RegistryObject<Block> trollber                  = BLOCKS.register("trollber", () -> new BlockTFRipeTorchCluster());
	public static final RegistryObject<Block> knightmetal_block         = BLOCKS.register("knightmetal_block", () -> new BlockTFKnightmetalBlock());
	public static final Block huge_lilypad               = iCantBelieveItsNotNull();
	public static final RegistryObject<Block> huge_waterlily            = BLOCKS.register("huge_waterlily", () -> new BlockTFHugeWaterLily());
	public static final Block slider                     = iCantBelieveItsNotNull();
	public static final RegistryObject<Block> castle_brick               = BLOCKS.register("castle_brick", () -> new BlockTFCastleBlock(MaterialColor.QUARTZ));
	public static final RegistryObject<Block> castle_brick_worn          = BLOCKS.register("castle_brick_worn", () -> new BlockTFCastleBlock(MaterialColor.QUARTZ));
	public static final RegistryObject<Block> castle_brick_cracked       = BLOCKS.register("castle_brick_cracked", () -> new BlockTFCastleBlock(MaterialColor.QUARTZ));
	public static final RegistryObject<Block> castle_brick_roof          = BLOCKS.register("castle_brick_roof", () -> new BlockTFCastleBlock(MaterialColor.GRAY));
	public static final RegistryObject<Block> castle_brick_mossy         = BLOCKS.register("castle_brick_mossy", () -> new BlockTFCastleBlock(MaterialColor.QUARTZ));
	public static final RegistryObject<Block> castle_brick_frame         = BLOCKS.register("castle_brick_frame", () -> new BlockTFCastleBlock(MaterialColor.QUARTZ));
	public static final RegistryObject<Block> castle_pillar_encased      = BLOCKS.register("castle_pillar_encased", () -> new BlockTFCastlePillar());
	public static final RegistryObject<Block> castle_pillar_bold         = BLOCKS.register("castle_pillar_bold", () -> new BlockTFCastlePillar());
	public static final RegistryObject<Block> castle_stairs_encased      = BLOCKS.register("castle_stairs_encased", () -> new BlockTFCastleStairs(castle_pillar_encased.get().getDefaultState()));
	public static final RegistryObject<Block> castle_stairs_bold         = BLOCKS.register("castle_stairs_bold", () -> new BlockTFCastleStairs(castle_pillar_bold.get().getDefaultState()));
	public static final RegistryObject<Block> castle_rune_brick_pink     = BLOCKS.register("castle_rune_brick_pink", () -> new BlockTFCastleMagic());
	public static final RegistryObject<Block> castle_rune_brick_blue     = BLOCKS.register("castle_rune_brick_blue", () -> new BlockTFCastleMagic());
	public static final RegistryObject<Block> castle_rune_brick_yellow   = BLOCKS.register("castle_rune_brick_yellow", () -> new BlockTFCastleMagic());
	public static final RegistryObject<Block> castle_rune_brick_purple   = BLOCKS.register("castle_rune_brick_purple", () -> new BlockTFCastleMagic());
	public static final Block force_field                = iCantBelieveItsNotNull(); //TODO Flatten
	public static final Block cinder_furnace             = iCantBelieveItsNotNull(); //TODO: Merge with cinder_furnace_lit
	public static final Block cinder_furnace_lit         = iCantBelieveItsNotNull(); //TODO: Merge with cinder_furnace
	public static final RegistryObject<Block> cinder_log                 = BLOCKS.register("cinder_log", () -> new BlockTFCinderLog());
	public static final Block castle_door                = iCantBelieveItsNotNull(); //TODO Flatten
	public static final Block castle_door_vanished       = iCantBelieveItsNotNull(); //TODO Flatten
	public static final Block experiment_115             = iCantBelieveItsNotNull();
	public static final RegistryObject<Block> twilight_portal_miniature_structure    = BLOCKS.register("twilight_portal_miniature_structure", () -> new BlockTFMiniatureStructure());
	public static final RegistryObject<Block> hedge_maze_miniature_structure         = BLOCKS.register("hedge_maze_miniature_structure", () -> new BlockTFMiniatureStructure());
	public static final RegistryObject<Block> hollow_hill_miniature_structure        = BLOCKS.register("hollow_hill_miniature_structure", () -> new BlockTFMiniatureStructure());
	public static final RegistryObject<Block> quest_grove_miniature_structure        = BLOCKS.register("quest_grove_miniature_structure", () -> new BlockTFMiniatureStructure());
	public static final RegistryObject<Block> mushroom_tower_miniature_structure     = BLOCKS.register("mushroom_tower_miniature_structure", () -> new BlockTFMiniatureStructure());
	public static final RegistryObject<Block> naga_courtyard_miniature_structure     = BLOCKS.register("naga_courtyard_miniature_structure", () -> new BlockTFMiniatureStructure());
	public static final RegistryObject<Block> lich_tower_miniature_structure         = BLOCKS.register("lich_tower_miniature_structure", () -> new BlockTFMiniatureStructure());
	public static final RegistryObject<Block> minotaur_labyrinth_miniature_structure = BLOCKS.register("minotaur_labyrinth_miniature_structure", () -> new BlockTFMiniatureStructure());
	public static final RegistryObject<Block> hydra_lair_miniature_structure         = BLOCKS.register("hydra_lair_miniature_structure", () -> new BlockTFMiniatureStructure());
	public static final RegistryObject<Block> goblin_stronghold_miniature_structure  = BLOCKS.register("goblin_stronghold_miniature_structure", () -> new BlockTFMiniatureStructure());
	public static final RegistryObject<Block> dark_tower_miniature_structure         = BLOCKS.register("dark_tower_miniature_structure", () -> new BlockTFMiniatureStructure());
	public static final RegistryObject<Block> yeti_cave_miniature_structure          = BLOCKS.register("yeti_cave_miniature_structure", () -> new BlockTFMiniatureStructure());
	public static final RegistryObject<Block> aurora_palace_miniature_structure      = BLOCKS.register("aurora_palace_miniature_structure", () -> new BlockTFMiniatureStructure());
	public static final RegistryObject<Block> troll_cave_cottage_miniature_structure = BLOCKS.register("troll_cave_cottage_miniature_structure", () -> new BlockTFMiniatureStructure());
	public static final RegistryObject<Block> final_castle_miniature_structure       = BLOCKS.register("final_castle_miniature_structure", () -> new BlockTFMiniatureStructure());
	public static final RegistryObject<Block> block_storage_ironwood     = BLOCKS.register("block_storage_ironwood", () -> new BlockTFCompressed(Material.WOOD, MaterialColor.WOOD, 5.0F, SoundType.WOOD));
	public static final RegistryObject<Block> block_storage_fiery        = BLOCKS.register("block_storage_fiery", () -> new BlockTFCompressed(Material.IRON, MaterialColor.BLACK_TERRACOTTA, 5.0F, SoundType.METAL));
	public static final RegistryObject<Block> block_storage_steeleaf     = BLOCKS.register("block_storage_steeleaf", () -> new BlockTFCompressed(Material.LEAVES, MaterialColor.FOLIAGE, 5.0F, SoundType.PLANT));
	public static final RegistryObject<Block> block_storage_arctic_fur   = BLOCKS.register("block_storage_arctic_fur", () -> new BlockTFCompressed(Material.WOOL, MaterialColor.WOOL, 0.8F, SoundType.CLOTH));
	public static final RegistryObject<Block> block_storage_carminite    = BLOCKS.register("block_storage_carminite", () -> new BlockTFCompressed(Material.CLAY, MaterialColor.RED, 0.0F, SoundType.SLIME));
	public static final Block spiral_bricks              = iCantBelieveItsNotNull();
	public static final Block etched_nagastone           = iCantBelieveItsNotNull();
	public static final Block nagastone_pillar           = iCantBelieveItsNotNull();
	public static final RegistryObject<Block> nagastone_stairs           = BLOCKS.register("nagastone_stairs", () -> new BlockTFNagastoneStairs(etched_nagastone.getDefaultState()));
	public static final Block etched_nagastone_mossy     = iCantBelieveItsNotNull();
	public static final Block nagastone_pillar_mossy     = iCantBelieveItsNotNull();
	public static final RegistryObject<Block> nagastone_stairs_mossy     = BLOCKS.register("nagastone_stairs_mossy", () -> new BlockTFNagastoneStairs(etched_nagastone_mossy.getDefaultState()));
	public static final Block etched_nagastone_weathered = iCantBelieveItsNotNull();
	public static final Block nagastone_pillar_weathered = iCantBelieveItsNotNull();
	public static final RegistryObject<Block> nagastone_stairs_weathered = BLOCKS.register("nagastone_stairs_weathered", () -> new BlockTFNagastoneStairs(nagastone_pillar_weathered.getDefaultState()));
	public static final RegistryObject<Block> auroralized_glass          = BLOCKS.register("auroralized_glass", () -> new BlockTFAuroralizedGlass());
	public static final RegistryObject<Block> castle_stairs_brick        = BLOCKS.register("castle_stairs_brick", () -> new BlockTFStairs(castle_brick.get().getDefaultState()));
	public static final RegistryObject<Block> castle_stairs_cracked      = BLOCKS.register("castle_stairs_cracked", () -> new BlockTFStairs(castle_brick_cracked.get().getDefaultState()));
	public static final RegistryObject<Block> castle_stairs_worn         = BLOCKS.register("castle_stairs_worn", () -> new BlockTFStairs(castle_brick_worn.get().getDefaultState()));
	public static final RegistryObject<Block> castle_stairs_mossy        = BLOCKS.register("castle_stairs_mossy", () -> new BlockTFStairs(castle_brick_mossy.get().getDefaultState()));
	public static final BlockTFLadderBars iron_ladder    = iCantBelieveItsNotNull();
	public static final Block terrorcotta_circle         = iCantBelieveItsNotNull();
	public static final Block terrorcotta_diagonal       = iCantBelieveItsNotNull();
	public static final Block stone_twist                = iCantBelieveItsNotNull();
	public static final Block stone_twist_thin           = iCantBelieveItsNotNull();

	// TODO chests? boats?
	public static final RegistryObject<Block> twilight_oak_planks   = BLOCKS.register("twilight_oak_planks", () -> new BlockTF(MaterialColor.WOOD));
	public static final RegistryObject<Block> twilight_oak_stairs   = BLOCKS.register("twilight_oak_stairs", () -> new BlockTFStairs(twilight_oak_planks.get().getDefaultState()));
	public static final RegistryObject<Block> twilight_oak_slab     = BLOCKS.register("twilight_oak_slab", () -> new BlockTFSlab(MaterialColor.WOOD));
	public static final RegistryObject<Block> twilight_oak_button   = BLOCKS.register("twilight_oak_button", () -> new BlockTFButtonWood());
	public static final RegistryObject<Block> twilight_oak_fence    = BLOCKS.register("twilight_oak_fence", () -> new BlockTFFence(MaterialColor.WOOD));
	public static final RegistryObject<Block> twilight_oak_gate     = BLOCKS.register("twilight_oak_gate", () -> new BlockTFFenceGate(MaterialColor.WOOD));
	public static final RegistryObject<Block> twilight_oak_plate    = BLOCKS.register("twilight_oak_plate", () -> new BlockTFPressurePlate(MaterialColor.WOOD));
	public static final RegistryObject<Block> twilight_oak_door     = BLOCKS.register("twilight_oak_door", () -> new BlockTFDoor(MaterialColor.WOOD));
	public static final RegistryObject<Block> twilight_oak_trapdoor = BLOCKS.register("twilight_oak_trapdoor", () -> new BlockTFTrapDoor(MaterialColor.WOOD));
	public static final RegistryObject<Block> canopy_planks         = BLOCKS.register("canopy_planks", () -> new BlockTF(MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> canopy_stairs         = BLOCKS.register("canopy_stairs", () -> new BlockTFStairs(canopy_planks.get().getDefaultState()));
	public static final RegistryObject<Block> canopy_slab           = BLOCKS.register("canopy_slab", () -> new BlockTFSlab(MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> canopy_button         = BLOCKS.register("canopy_button", () -> new BlockTFButtonWood());
	public static final RegistryObject<Block> canopy_fence          = BLOCKS.register("canopy_fence", () -> new BlockTFFence(MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> canopy_gate           = BLOCKS.register("canopy_gate", () -> new BlockTFFenceGate(MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> canopy_plate          = BLOCKS.register("canopy_plate", () -> new BlockTFPressurePlate(MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> canopy_door           = BLOCKS.register("canopy_door", () -> new BlockTFDoor(MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> canopy_trapdoor       = BLOCKS.register("canopy_trapdoor", () -> new BlockTFTrapDoor(MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> mangrove_planks       = BLOCKS.register("mangrove_planks", () -> new BlockTF(MaterialColor.DIRT));
	public static final RegistryObject<Block> mangrove_stairs       = BLOCKS.register("mangrove_stairs", () -> new BlockTFStairs(mangrove_planks.get().getDefaultState()));
	public static final RegistryObject<Block> mangrove_slab         = BLOCKS.register("mangrove_slab", () -> new BlockTFSlab(MaterialColor.DIRT));
	public static final RegistryObject<Block> mangrove_button       = BLOCKS.register("mangrove_button", () -> new BlockTFButtonWood());
	public static final RegistryObject<Block> mangrove_fence        = BLOCKS.register("mangrove_fence", () -> new BlockTFFence(MaterialColor.DIRT));
	public static final RegistryObject<Block> mangrove_gate         = BLOCKS.register("mangrove_gate", () -> new BlockTFFenceGate(MaterialColor.DIRT));
	public static final RegistryObject<Block> mangrove_plate        = BLOCKS.register("mangrove_plate", () -> new BlockTFPressurePlate(MaterialColor.DIRT));
	public static final RegistryObject<Block> mangrove_door         = BLOCKS.register("mangrove_door", () -> new BlockTFDoor(MaterialColor.DIRT));
	public static final RegistryObject<Block> mangrove_trapdoor     = BLOCKS.register("mangrove_trapdoor", () -> new BlockTFTrapDoor(MaterialColor.DIRT));
	public static final RegistryObject<Block> dark_planks           = BLOCKS.register("dark_planks", () -> new BlockTF(MaterialColor.ADOBE));
	public static final RegistryObject<Block> dark_stairs           = BLOCKS.register("dark_stairs", () -> new BlockTFStairs(dark_planks.get().getDefaultState()));
	public static final RegistryObject<Block> dark_slab             = BLOCKS.register("dark_slab", () -> new BlockTFSlab(MaterialColor.ADOBE));
	public static final RegistryObject<Block> dark_button           = BLOCKS.register("dark_button", () -> new BlockTFButtonWood());
	public static final RegistryObject<Block> dark_fence            = BLOCKS.register("dark_fence", () -> new BlockTFFence(MaterialColor.ADOBE));
	public static final RegistryObject<Block> dark_gate             = BLOCKS.register("dark_gate", () -> new BlockTFFenceGate(MaterialColor.ADOBE));
	public static final RegistryObject<Block> dark_plate            = BLOCKS.register("dark_plate", () -> new BlockTFPressurePlate(MaterialColor.ADOBE));
	public static final RegistryObject<Block> dark_door             = BLOCKS.register("dark_door", () -> new BlockTFDoor(MaterialColor.ADOBE));
	public static final RegistryObject<Block> dark_trapdoor         = BLOCKS.register("dark_trapdoor", () -> new BlockTFTrapDoor(MaterialColor.ADOBE));
	public static final RegistryObject<Block> time_planks           = BLOCKS.register("time_planks", () -> new BlockTF(MaterialColor.DIRT));
	public static final RegistryObject<Block> time_stairs           = BLOCKS.register("time_stairs", () -> new BlockTFStairs(time_planks.get().getDefaultState()));
	public static final RegistryObject<Block> time_slab             = BLOCKS.register("time_slab", () -> new BlockTFSlab(MaterialColor.DIRT));
	public static final RegistryObject<Block> time_button           = BLOCKS.register("time_button", () -> new BlockTFButtonWood());
	public static final RegistryObject<Block> time_fence            = BLOCKS.register("time_fence", () -> new BlockTFFence(MaterialColor.DIRT));
	public static final RegistryObject<Block> time_gate             = BLOCKS.register("time_gate", () -> new BlockTFFenceGate(MaterialColor.DIRT));
	public static final RegistryObject<Block> time_plate            = BLOCKS.register("time_plate", () -> new BlockTFPressurePlate(MaterialColor.DIRT));
	public static final RegistryObject<Block> time_door             = BLOCKS.register("time_door", () -> new BlockTFDoor(MaterialColor.DIRT));
	public static final RegistryObject<Block> time_trapdoor         = BLOCKS.register("time_trapdoor", () -> new BlockTFTrapDoor(MaterialColor.DIRT));
	public static final RegistryObject<Block> trans_planks          = BLOCKS.register("trans_planks", () -> new BlockTF(MaterialColor.WOOD));
	public static final RegistryObject<Block> trans_stairs          = BLOCKS.register("trans_stairs", () -> new BlockTFStairs(trans_planks.get().getDefaultState()));
	public static final RegistryObject<Block> trans_slab            = BLOCKS.register("trans_slab", () -> new BlockTFSlab(MaterialColor.WOOD));
	public static final RegistryObject<Block> trans_button          = BLOCKS.register("trans_button", () -> new BlockTFButtonWood());
	public static final RegistryObject<Block> trans_fence           = BLOCKS.register("trans_fence", () -> new BlockTFFence(MaterialColor.WOOD));
	public static final RegistryObject<Block> trans_gate            = BLOCKS.register("trans_gate", () -> new BlockTFFenceGate(MaterialColor.WOOD));
	public static final RegistryObject<Block> trans_plate           = BLOCKS.register("trans_plate", () -> new BlockTFPressurePlate(MaterialColor.WOOD));
	public static final RegistryObject<Block> trans_door            = BLOCKS.register("trans_door", () -> new BlockTFDoor(MaterialColor.WOOD));
	public static final RegistryObject<Block> trans_trapdoor        = BLOCKS.register("trans_trapdoor", () -> new BlockTFTrapDoor(MaterialColor.WOOD));
	public static final RegistryObject<Block> mine_planks           = BLOCKS.register("mine_planks", () -> new BlockTF(MaterialColor.SAND));
	public static final RegistryObject<Block> mine_stairs           = BLOCKS.register("mine_stairs", () -> new BlockTFStairs(mine_planks.get().getDefaultState()));
	public static final RegistryObject<Block> mine_slab             = BLOCKS.register("mine_slab", () -> new BlockTFSlab(MaterialColor.SAND));
	public static final RegistryObject<Block> mine_button           = BLOCKS.register("mine_button", () -> new BlockTFButtonWood());
	public static final RegistryObject<Block> mine_fence            = BLOCKS.register("mine_fence", () -> new BlockTFFence(MaterialColor.SAND));
	public static final RegistryObject<Block> mine_gate             = BLOCKS.register("mine_gate", () -> new BlockTFFenceGate(MaterialColor.SAND));
	public static final RegistryObject<Block> mine_plate            = BLOCKS.register("mine_plate", () -> new BlockTFPressurePlate(MaterialColor.SAND));
	public static final RegistryObject<Block> mine_door             = BLOCKS.register("mine_door", () -> new BlockTFDoor(MaterialColor.SAND));
	public static final RegistryObject<Block> mine_trapdoor         = BLOCKS.register("mine_trapdoor", () -> new BlockTFTrapDoor(MaterialColor.SAND));
	public static final RegistryObject<Block> sort_planks           = BLOCKS.register("sort_planks", () -> new BlockTF(MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> sort_stairs           = BLOCKS.register("sort_stairs", () -> new BlockTFStairs(sort_planks.get().getDefaultState()));
	public static final RegistryObject<Block> sort_slab             = BLOCKS.register("sort_slab", () -> new BlockTFSlab(MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> sort_button           = BLOCKS.register("sort_button", () -> new BlockTFButtonWood());
	public static final RegistryObject<Block> sort_fence            = BLOCKS.register("sort_fence", () -> new BlockTFFence(MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> sort_gate             = BLOCKS.register("sort_gate", () -> new BlockTFFenceGate(MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> sort_plate            = BLOCKS.register("sort_plate", () -> new BlockTFPressurePlate(MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> sort_door             = BLOCKS.register("sort_door", () -> new BlockTFDoor(MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> sort_trapdoor         = BLOCKS.register("sort_trapdoor", () -> new BlockTFTrapDoor(MaterialColor.OBSIDIAN));

	private static <T> T iCantBelieveItsNotNull()
	{
		return null;
	}
}
