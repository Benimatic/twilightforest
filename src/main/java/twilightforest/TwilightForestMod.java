package twilightforest;

import com.google.common.base.Function;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockSlab;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import twilightforest.biomes.TFBiomeBase;
import twilightforest.block.BlockTFAuroraBrick;
import twilightforest.block.BlockTFAuroraPillar;
import twilightforest.block.BlockTFAuroraSlab;
import twilightforest.block.BlockTFBurntThorns;
import twilightforest.block.BlockTFCastleBlock;
import twilightforest.block.BlockTFCastleMagic;
import twilightforest.block.BlockTFDarkLeaves;
import twilightforest.block.BlockTFDeadrock;
import twilightforest.block.BlockTFFluffyCloud;
import twilightforest.block.BlockTFForceField;
import twilightforest.block.BlockTFGiantCobble;
import twilightforest.block.BlockTFGiantLeaves;
import twilightforest.block.BlockTFGiantLog;
import twilightforest.block.BlockTFGiantObsidian;
import twilightforest.block.BlockTFHugeGloomBlock;
import twilightforest.block.BlockTFHugeLilyPad;
import twilightforest.block.BlockTFHugeStalk;
import twilightforest.block.BlockTFHugeWaterLily;
import twilightforest.block.BlockTFKnightmetalBlock;
import twilightforest.block.BlockTFLeaves3;
import twilightforest.block.BlockTFRipeTorchCluster;
import twilightforest.block.BlockTFShield;
import twilightforest.block.BlockTFSlider;
import twilightforest.block.BlockTFThornRose;
import twilightforest.block.BlockTFThorns;
import twilightforest.block.BlockTFTrollRoot;
import twilightforest.block.BlockTFTrollSteinn;
import twilightforest.block.BlockTFTrophy;
import twilightforest.block.BlockTFTrophyPedestal;
import twilightforest.block.BlockTFUberousSoil;
import twilightforest.block.BlockTFUnderBrick;
import twilightforest.block.BlockTFUnripeTorchCluster;
import twilightforest.block.BlockTFWispyCloud;
import twilightforest.block.TFBlocks;
import twilightforest.entity.TFCreatures;
import twilightforest.item.BehaviorTFMobEggDispense;
import twilightforest.item.ItemTFMagicMap;
import twilightforest.item.ItemTFMazeMap;
import twilightforest.item.TFItems;
import twilightforest.item.TFRecipes;
import twilightforest.structures.StructureTFMajorFeatureStart;
import twilightforest.tileentity.TileEntityTFCReactorActive;
import twilightforest.tileentity.TileEntityTFCicada;
import twilightforest.tileentity.TileEntityTFCinderFurnace;
import twilightforest.tileentity.TileEntityTFFirefly;
import twilightforest.tileentity.TileEntityTFFlameJet;
import twilightforest.tileentity.TileEntityTFGhastTrapActive;
import twilightforest.tileentity.TileEntityTFGhastTrapInactive;
import twilightforest.tileentity.TileEntityTFHydraSpawner;
import twilightforest.tileentity.TileEntityTFKnightPhantomsSpawner;
import twilightforest.tileentity.TileEntityTFLichSpawner;
import twilightforest.tileentity.TileEntityTFMoonworm;
import twilightforest.tileentity.TileEntityTFNagaSpawner;
import twilightforest.tileentity.TileEntityTFPoppingJet;
import twilightforest.tileentity.TileEntityTFReverter;
import twilightforest.tileentity.TileEntityTFSmoker;
import twilightforest.tileentity.TileEntityTFSnowQueenSpawner;
import twilightforest.tileentity.TileEntityTFTowerBossSpawner;
import twilightforest.tileentity.TileEntityTFTowerBuilder;
import twilightforest.tileentity.TileEntityTFTrophy;
import twilightforest.world.WorldProviderTwilightForest;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLMessage.EntitySpawnMessage;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry.EntityRegistration;
import thaumcraft.api.*;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

@Mod(modid = TwilightForestMod.ID, name = "The Twilight Forest", version = TwilightForestMod.VERSION)
public class TwilightForestMod {
	
	public static final String ID = "TwilightForest";
	public static final String VERSION = "2.3.8dev";
	
	public static final String MODEL_DIR = "twilightforest:textures/model/";
	public static final String GUI_DIR = "twilightforest:textures/gui/";
	public static final String ENVRIO_DIR = "twilightforest:textures/environment/";
	public static final String ARMOR_DIR = "twilightforest:textures/armor/";
	public static final String ENFORCED_PROGRESSION_RULE = "tfEnforcedProgression";
	
	public static final int GUI_ID_UNCRAFTING = 1;
	public static final int GUI_ID_FURNACE = 2;


	public static int dimensionID;
	public static int backupdimensionID = -777;
	public static int dimensionProviderID;
    
	// misc options
    public static boolean creatureCompatibility;
    public static boolean silentCicadas;
    public static boolean allowPortalsInOtherDimensions;
    public static boolean adminOnlyPortals;
    public static String twilightForestSeed;
    public static boolean disablePortalCreation;
    public static boolean disableUncrafting;
    public static boolean oldMapGen;
    public static String portalCreationItemString;

    // performance
    public static float canopyCoverage;
    public static int twilightOakChance;

    public static int idMobWildBoar;
	public static int idMobBighornSheep;
	public static int idMobWildDeer;
	public static int idMobRedcap;
	public static int idMobSwarmSpider;
	public static int idMobNaga;
	public static int idMobNagaSegment;
	public static int idMobSkeletonDruid;
	public static int idMobHostileWolf;
	public static int idMobTwilightWraith;
	public static int idMobHedgeSpider;
	public static int idMobHydra;
	public static int idMobLich;
	public static int idMobPenguin;
	public static int idMobLichMinion;
	public static int idMobLoyalZombie;
	public static int idMobTinyBird;
	public static int idMobSquirrel;
	public static int idMobBunny;
	public static int idMobRaven;
	public static int idMobQuestRam;
	public static int idMobKobold;
	public static int idMobBoggard;
	public static int idMobMosquitoSwarm;
	public static int idMobDeathTome;
	public static int idMobMinotaur;
	public static int idMobMinoshroom;
	public static int idMobFireBeetle;
	public static int idMobSlimeBeetle;
	public static int idMobPinchBeetle;
	public static int idMobMazeSlime;
	public static int idMobRedcapSapper;
	public static int idMobMistWolf;
	public static int idMobKingSpider;
	public static int idMobFirefly;
	public static int idMobMiniGhast;
	public static int idMobTowerGhast;
	public static int idMobTowerGolem;
	public static int idMobTowerTermite;
	public static int idMobTowerBroodling;
	public static int idMobTowerBoss;
	public static int idMobBlockGoblin;
	public static int idMobGoblinKnightUpper;
	public static int idMobGoblinKnightLower;
	public static int idMobHelmetCrab;
	public static int idMobKnightPhantom;
	public static int idMobYeti;
	public static int idMobYetiBoss;
	public static int idMobWinterWolf;
	public static int idMobSnowGuardian;
	public static int idMobStableIceCore;
	public static int idMobUnstableIceCore;
	public static int idMobSnowQueen;
	public static int idMobTroll;
	public static int idMobGiantMiner;
	public static int idMobArmoredGiant;
	public static int idMobIceCrystal;
	public static int idMobHarbingerCube;
	public static int idMobAdherent;
	public static int idMobRovingCube;

	public static int idVehicleSpawnNatureBolt = 1;  // non-configurable, since there's no way for them to conflict with anything
	public static int idVehicleSpawnLichBolt = 2;
	public static int idVehicleSpawnTwilightWandBolt = 3;
	public static int idVehicleSpawnTomeBolt = 4;
	public static int idVehicleSpawnHydraMortar = 5;
	public static int idVehicleSpawnLichBomb = 6;
	public static int idVehicleSpawnMoonwormShot = 7;
	public static int idVehicleSpawnSlimeBlob = 8;
	public static int idVehicleSpawnCharmEffect = 9;
	public static int idVehicleSpawnThrownAxe = 10;
	public static int idVehicleSpawnThrownPick = 13;
	public static int idVehicleSpawnFallingIce = 14;
	public static int idVehicleSpawnThrownIce = 15;
	public static int idVehicleSpawnSeekerArrow = 16;
	public static int idVehicleSpawnIceSnowball = 17;
	public static int idVehicleSpawnChainBlock = 18;
	public static int idVehicleSpawnCubeOfAnnihilation = 19;
	public static int idVehicleSpawnSlideBlock = 20;
	
	public static int idBiomeLake;
	public static int idBiomeTwilightForest;
	public static int idBiomeTwilightForestVariant;
	public static int idBiomeHighlands;
	public static int idBiomeMushrooms;
	public static int idBiomeSwamp;
	public static int idBiomeStream;
	public static int idBiomeSnowfield;
	public static int idBiomeGlacier;
	public static int idBiomeClearing;
	public static int idBiomeOakSavanna;
	public static int idBiomeFireflyForest;
	public static int idBiomeDeepMushrooms;
	public static int idBiomeDarkForestCenter;
	public static int idBiomeHighlandsCenter;
	public static int idBiomeDarkForest;
	public static int idBiomeEnchantedForest;
	public static int idBiomeFireSwamp;
	public static int idBiomeThornlands;
	
	// used to report conflicts
	public static boolean hasBiomeIdConflicts = false;
	public static boolean hasAssignedBiomeID = false;

	public static final TFEventListener eventListener = new TFEventListener();
	public static final TFTickHandler tickHandler = new TFTickHandler();
	public static FMLEventChannel genericChannel;

	
	@Instance(ID)
	public static TwilightForestMod instance;

	@SidedProxy(clientSide = "twilightforest.client.TFClientProxy", serverSide = "twilightforest.TFCommonProxy")
	public static TFCommonProxy proxy;

	public TwilightForestMod()
	{
		TwilightForestMod.instance = this;
	}
	
    @EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// load config
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		loadConfiguration(config);
		// sounds on client, and whatever else needs to be registered pre-load
		proxy.doPreLoadRegistration();

		// initialize & register blocks
		TFBlocks.registerBlocks();

		// items
		TFItems.registerItems();
		
		// cheevos!
		AchievementPage.registerAchievementPage(new TFAchievementPage());
		
		// just call this so that we register structure IDs correctly
		new StructureTFMajorFeatureStart();
		

    	// check for biome conflicts, load biomes
		TFBiomeBase.assignBlankBiomeIds();
		if (TwilightForestMod.hasAssignedBiomeID) {
			FMLLog.info("[TwilightForest] Twilight Forest mod has auto-assigned some biome IDs.  This will break any existing Twilight Forest saves.");
			saveBiomeIds(config);
		}
		TwilightForestMod.hasBiomeIdConflicts = TFBiomeBase.areThereBiomeIdConflicts();
	}

    @EventHandler
	public void load(FMLInitializationEvent evt) {

		// creatures
		registerCreatures();

		// recipes
		TFRecipes.registerRecipes();

		// tile entities
		registerTileEntities();
		// GUI
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		
		// event listener, for those events that seem worth listening to
		MinecraftForge.EVENT_BUS.register(eventListener);
		FMLCommonHandler.instance().bus().register(eventListener); // we're getting events off this bus too

		// tick listener
		FMLCommonHandler.instance().bus().register(tickHandler);
		
		// set up portal item
		Item portalItem;
		if (Item.itemRegistry.containsKey(portalCreationItemString)) {
			portalItem = (Item) Item.itemRegistry.getObject(portalCreationItemString);
			if (portalItem != Items.DIAMOND) {
				FMLLog.info("Set Twilight Forest portal item to %s", portalItem.getUnlocalizedName());
			}
		} else if (Block.blockRegistry.containsKey(portalCreationItemString)) {
			portalItem = Item.getItemFromBlock((Block) Block.blockRegistry.getObject(portalCreationItemString));
			FMLLog.info("Set Twilight Forest portal item to %s", portalItem.getUnlocalizedName());
		} else {
			FMLLog.info("Twilight Forest config lists portal item as '%s'.  Not found, defaulting to diamond.", portalCreationItemString);
			portalItem = Items.DIAMOND;
		}
		tickHandler.portalItem = portalItem;
		
		// make some channels for our maps
		TFMapPacketHandler mapPacketHandler = new TFMapPacketHandler();
		NetworkRegistry.INSTANCE.newEventDrivenChannel(ItemTFMagicMap.STR_ID).register(mapPacketHandler);
		NetworkRegistry.INSTANCE.newEventDrivenChannel(ItemTFMazeMap.STR_ID).register(mapPacketHandler);

		// generic channel that handles biome change packets, but could handle some other stuff in the future
		TwilightForestMod.genericChannel = NetworkRegistry.INSTANCE.newEventDrivenChannel(TwilightForestMod.ID);
		
		// render and other client stuff
		proxy.doOnLoadRegistration();
		
		// dimension provider
		DimensionManager.registerProviderType(TwilightForestMod.dimensionProviderID, WorldProviderTwilightForest.class, false);

		// enter biomes into dictionary
		TFBiomeBase.registerWithBiomeDictionary();
	}
	
	/**
	 * Post init
	 */
    @EventHandler
	public void postInit(FMLPostInitializationEvent evt) 
	{
		// register dimension with Forge
		if (!DimensionManager.isDimensionRegistered(TwilightForestMod.dimensionID))
		{
			DimensionManager.registerDimension(TwilightForestMod.dimensionID, TwilightForestMod.dimensionProviderID);
		}
		else
		{
			FMLLog.warning("[TwilightForest] Twilight Forest detected that the configured dimension id '%d' is being used.  Using backup ID.  It is recommended that you configure this mod to use a unique dimension ID.", dimensionID);
			DimensionManager.registerDimension(TwilightForestMod.backupdimensionID, TwilightForestMod.dimensionProviderID);
			TwilightForestMod.dimensionID = TwilightForestMod.backupdimensionID;
		}
		
		// thaumcraft integration
		if (Loader.isModLoaded("Thaumcraft"))
		{
			registerThaumcraftIntegration();
		}
		else
		{
			FMLLog.info("[TwilightForest] Did not find Thaumcraft, did not load ThaumcraftApi integration.");
		}
		
		// final check for biome ID conflicts
		TwilightForestMod.hasBiomeIdConflicts = TFBiomeBase.areThereBiomeIdConflicts();
	}
	
    @EventHandler
	public void startServer(FMLServerStartingEvent event)
	{
		// dispenser behaviors
		registerDispenseBehaviors(event.getServer());
		
		//event.registerServerCommand(new CommandTFFeature());
		event.registerServerCommand(new CommandTFProgress());
	}

	private void registerCreatures() {
		TFCreatures.registerTFCreature(twilightforest.entity.passive.EntityTFBoar.class, "Wild Boar", idMobWildBoar, 0x83653b, 0xffefca);
		TFCreatures.registerTFCreature(twilightforest.entity.passive.EntityTFBighorn.class, "Bighorn Sheep", idMobBighornSheep, 0xdbceaf, 0xd7c771);
		TFCreatures.registerTFCreature(twilightforest.entity.passive.EntityTFDeer.class, "Wild Deer", idMobWildDeer, 0x7b4d2e, 0x4b241d);

		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFRedcap.class, "Redcap", idMobRedcap, 0x3b3a6c, 0xab1e14);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFSwarmSpider.class, "Swarm Spider", idMobSwarmSpider, 0x32022e, 0x17251e);
		TFCreatures.registerTFCreature(twilightforest.entity.boss.EntityTFNaga.class, "Naga", idMobNaga, 0xa4d316, 0x1b380b);
		//TFCreatures.registerTFCreature(twilightforest.entity.EntityTFNagaSegmentOld.class, "Naga Segment", idMobNagaSegment);  // no longer needed, regenerated instantly as required
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFSkeletonDruid.class, "Skeleton Druid", idMobSkeletonDruid, 0xa3a3a3, 0x2a3b17);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFHostileWolf.class, "Hostile Wolf", idMobHostileWolf, 0xd7d3d3, 0xab1e14);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFWraith.class, "Twilight Wraith", idMobTwilightWraith, 0x505050, 0x838383);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFHedgeSpider.class, "Hedge Spider", idMobHedgeSpider, 0x235f13, 0x562653);
		TFCreatures.registerTFCreature(twilightforest.entity.boss.EntityTFHydra.class, "Hydra", idMobHydra, 0x142940, 0x29806b);
		TFCreatures.registerTFCreature(twilightforest.entity.boss.EntityTFLich.class, "Twilight Lich", idMobLich, 0xaca489, 0x360472);
		TFCreatures.registerTFCreature(twilightforest.entity.passive.EntityTFPenguin.class, "Glacier Penguin", idMobPenguin, 0x12151b, 0xf9edd2);
		TFCreatures.registerTFCreature(twilightforest.entity.boss.EntityTFLichMinion.class, "Lich Minion", idMobLichMinion);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFLoyalZombie.class, "Loyal Zombie", idMobLoyalZombie);
		TFCreatures.registerTFCreature(twilightforest.entity.passive.EntityTFTinyBird.class, "Tiny Bird", idMobTinyBird, 0x33aadd, 0x1188ee);
		TFCreatures.registerTFCreature(twilightforest.entity.passive.EntityTFSquirrel.class, "Forest Squirrel", idMobSquirrel, 0x904f12, 0xeeeeee);
		TFCreatures.registerTFCreature(twilightforest.entity.passive.EntityTFBunny.class, "Forest Bunny", idMobBunny, 0xfefeee, 0xccaa99);
		TFCreatures.registerTFCreature(twilightforest.entity.passive.EntityTFRaven.class, "Forest Raven", idMobRaven, 0x000011, 0x222233);
		TFCreatures.registerTFCreature(twilightforest.entity.passive.EntityTFQuestRam.class, "Questing Ram", idMobQuestRam);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFKobold.class, "Twilight Kobold", idMobKobold, 0x372096, 0x895d1b);
		//TFCreatures.registerTFCreature(twilightforest.entity.EntityTFBoggard.class, "Boggard", idMobBoggard);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFMosquitoSwarm.class, "Mosquito Swarm", idMobMosquitoSwarm, 0x080904, 0x2d2f21);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFDeathTome.class, "Death Tome", idMobDeathTome, 0x774e22, 0xdbcdbe);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFMinotaur.class, "Minotaur", idMobMinotaur, 0x3f3024, 0xaa7d66);
		TFCreatures.registerTFCreature(twilightforest.entity.boss.EntityTFMinoshroom.class, "Minoshroom", idMobMinoshroom, 0xa81012, 0xaa7d66);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFFireBeetle.class, "Fire Beetle", idMobFireBeetle, 0x1d0b00, 0xcb6f25);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFSlimeBeetle.class, "Slime Beetle", idMobSlimeBeetle, 0x0c1606, 0x60a74c);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFPinchBeetle.class, "Pinch Beetle", idMobPinchBeetle, 0xbc9327, 0x241609);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFMazeSlime.class, "Maze Slime", idMobMazeSlime, 0xa3a3a3, 0x2a3b17);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFRedcapSapper.class, "Redcap Sapper", idMobRedcapSapper, 0x575d21, 0xab1e14);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFMistWolf.class, "Mist Wolf", idMobMistWolf, 0x3a1411, 0xe2c88a);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFKingSpider.class, "King Spider", idMobKingSpider, 0x2c1a0e, 0xffc017);
		TFCreatures.registerTFCreature(twilightforest.entity.passive.EntityTFMobileFirefly.class, "Firefly", idMobFirefly, 0xa4d316, 0xbaee02);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFMiniGhast.class, "Mini Ghast", idMobMiniGhast, 0xbcbcbc, 0xa74343);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFTowerGhast.class, "Tower Ghast", idMobTowerGhast, 0xbcbcbc, 0xb77878);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFTowerGolem.class, "Tower Golem", idMobTowerGolem, 0x6b3d20, 0xe2ddda);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFTowerTermite.class, "Tower Termite", idMobTowerTermite, 0x5d2b21, 0xaca03a);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFTowerBroodling.class, "Redscale Broodling", idMobTowerBroodling, 0x343c14, 0xbaee02);
		TFCreatures.registerTFCreature(twilightforest.entity.boss.EntityTFUrGhast.class, "Tower Boss", idMobTowerBoss, 0xbcbcbc, 0xb77878);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFBlockGoblin.class, "Block&Chain Goblin", idMobBlockGoblin, 0xd3e7bc, 0x1f3fff);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFGoblinKnightUpper.class, "Upper Goblin Knight", idMobGoblinKnightUpper);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFGoblinKnightLower.class, "Lower Goblin Knight", idMobGoblinKnightLower, 0x566055, 0xd3e7bc);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFHelmetCrab.class, "Helmet Crab", idMobHelmetCrab, 0xfb904b, 0xd3e7bc);
		TFCreatures.registerTFCreature(twilightforest.entity.boss.EntityTFKnightPhantom.class, "Knight Phantom", idMobKnightPhantom, 0xa6673b, 0xd3e7bc);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFYeti.class, "Yeti", idMobYeti, 0xdedede, 0x4675bb);
		TFCreatures.registerTFCreature(twilightforest.entity.boss.EntityTFYetiAlpha.class, "Yeti Boss", idMobYetiBoss, 0xcdcdcd, 0x29486e);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFWinterWolf.class, "WinterWolf", idMobWinterWolf, 0xdfe3e5, 0xb2bcca);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFSnowGuardian.class, "SnowGuardian", idMobSnowGuardian, 0xd3e7bc, 0xfefefe);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFIceShooter.class, "Stable Ice Core", idMobStableIceCore, 0xa1bff3, 0x7000f8);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFIceExploder.class, "Unstable Ice Core", idMobUnstableIceCore, 0x9aacf5, 0x9b0fa5);
		TFCreatures.registerTFCreature(twilightforest.entity.boss.EntityTFSnowQueen.class, "Snow Queen", idMobSnowQueen, 0xb1b2d4, 0x87006e);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFTroll.class, "Troll", idMobTroll, 0x9ea98f, 0xb0948e);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFGiantMiner.class, "Giant Miner", idMobGiantMiner, 0x211b52, 0x9a9a9a);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFArmoredGiant.class, "Armored Giant", idMobArmoredGiant, 0x239391, 0x9a9a9a);
		TFCreatures.registerTFCreature(twilightforest.entity.boss.EntityTFIceCrystal.class, "Ice Crystal", idMobIceCrystal, 0xdce9fe, 0xadcafb);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFHarbingerCube.class, "Harbinger Cube", idMobHarbingerCube, 0x00000a, 0x8b0000);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFAdherent.class, "Adherent", idMobAdherent, 0x0a0000, 0x00008b);
		TFCreatures.registerTFCreature(twilightforest.entity.EntityTFRovingCube.class, "RovingCube", idMobRovingCube, 0x0a0000, 0x00009b);
		
		EntityRegistry.registerModEntity(twilightforest.entity.boss.EntityTFHydraHead.class, "HydraHead", 11, this, 150, 3, false);
		
		EntityRegistry.registerModEntity(twilightforest.entity.EntityTFNatureBolt.class, "tfnaturebolt", idVehicleSpawnNatureBolt, this, 150, 5, true);
		EntityRegistry.registerModEntity(twilightforest.entity.boss.EntityTFLichBolt.class, "tflichbolt",  idVehicleSpawnLichBolt, this, 150, 2, true);
		EntityRegistry.registerModEntity(twilightforest.entity.EntityTFTwilightWandBolt.class, "tftwilightwandbolt", idVehicleSpawnTwilightWandBolt, this, 150, 5, true);
		EntityRegistry.registerModEntity(twilightforest.entity.EntityTFTomeBolt.class, "tftomebolt", idVehicleSpawnTomeBolt, this, 150, 5, true);
		EntityRegistry.registerModEntity(twilightforest.entity.boss.EntityTFHydraMortar.class, "tfhydramortar", idVehicleSpawnHydraMortar, this, 150, 3, true);
		EntityRegistry.registerModEntity(twilightforest.entity.boss.EntityTFLichBomb.class, "tflichbomb", idVehicleSpawnLichBomb, this, 150, 3, true);
		EntityRegistry.registerModEntity(twilightforest.entity.EntityTFMoonwormShot.class, "tfmoonwormshot", idVehicleSpawnMoonwormShot, this, 150, 3, true);
		EntityRegistry.registerModEntity(twilightforest.entity.EntityTFSlimeProjectile.class, "tfslimeblob", idVehicleSpawnSlimeBlob, this, 150, 3, true);
		EntityRegistry.registerModEntity(twilightforest.entity.EntityTFCharmEffect.class, "tfcharmeffect", idVehicleSpawnCharmEffect, this, 80, 3, true);
		EntityRegistry.registerModEntity(twilightforest.entity.boss.EntityTFThrownAxe.class, "tfthrownaxe", idVehicleSpawnThrownAxe, this, 80, 3, true);
		EntityRegistry.registerModEntity(twilightforest.entity.boss.EntityTFThrownPick.class, "tfthrownpick", idVehicleSpawnThrownPick, this, 80, 3, true);
		EntityRegistry.registerModEntity(twilightforest.entity.boss.EntityTFFallingIce.class, "tffallingice", idVehicleSpawnFallingIce, this, 80, 3, true);
		EntityRegistry.registerModEntity(twilightforest.entity.boss.EntityTFIceBomb.class, "tfthrownice", idVehicleSpawnThrownIce, this, 80, 2, true);
		EntityRegistry.registerModEntity(twilightforest.entity.EntitySeekerArrow.class, "tfSeekerArrow", idVehicleSpawnSeekerArrow, this, 150, 1, true);
		EntityRegistry.registerModEntity(twilightforest.entity.EntityTFIceSnowball.class, "tficesnowball", idVehicleSpawnIceSnowball, this, 150, 3, true);
		EntityRegistry.registerModEntity(twilightforest.entity.EntityTFChainBlock.class, "tfchainBlock", idVehicleSpawnChainBlock, this, 80, 1, true);
		EntityRegistry.registerModEntity(twilightforest.entity.EntityTFCubeOfAnnihilation.class, "tfcubeannihilation", this.idVehicleSpawnCubeOfAnnihilation, this, 80, 1, true);
		EntityRegistry.registerModEntity(twilightforest.entity.EntityTFSlideBlock.class, "tfslideblock", this.idVehicleSpawnSlideBlock, this, 80, 1, true);

	}
	
	
	private void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityTFFirefly.class, "Firefly");
		GameRegistry.registerTileEntity(TileEntityTFCicada.class, "Cicada");
		GameRegistry.registerTileEntity(TileEntityTFNagaSpawner.class, "Naga Spawner");
		GameRegistry.registerTileEntity(TileEntityTFLichSpawner.class, "Lich Spawner");
		GameRegistry.registerTileEntity(TileEntityTFHydraSpawner.class, "Hydra Spawner");
		GameRegistry.registerTileEntity(TileEntityTFSmoker.class, "Swamp Smoker");
		GameRegistry.registerTileEntity(TileEntityTFPoppingJet.class, "Popping Flame Jet");
		GameRegistry.registerTileEntity(TileEntityTFFlameJet.class, "Lit Flame Jet");
		GameRegistry.registerTileEntity(TileEntityTFMoonworm.class, "Moonworm");
		GameRegistry.registerTileEntity(TileEntityTFTowerBuilder.class, "Tower Builder");
		GameRegistry.registerTileEntity(TileEntityTFReverter.class, "Tower Reverter");
		GameRegistry.registerTileEntity(TileEntityTFTrophy.class, "TF Trophy");
		GameRegistry.registerTileEntity(TileEntityTFTowerBossSpawner.class, "Tower Boss Spawner");
		GameRegistry.registerTileEntity(TileEntityTFGhastTrapInactive.class, "Inactive Ghast Trap");
		GameRegistry.registerTileEntity(TileEntityTFGhastTrapActive.class, "Active Ghast Trap");
		GameRegistry.registerTileEntity(TileEntityTFCReactorActive.class, "Active Carminite Reactor");
		GameRegistry.registerTileEntity(TileEntityTFKnightPhantomsSpawner.class, "Knight Phantom Spawner");
		GameRegistry.registerTileEntity(TileEntityTFSnowQueenSpawner.class, "Snow Queen Spawner");
		GameRegistry.registerTileEntity(TileEntityTFCinderFurnace.class, "Cinder Furnace");
	}

	/**
	 * Use the thaumcraft API to register our things with aspects and biomes with values
	 */
	private void registerThaumcraftIntegration() 
	{
		try {
	
			// items
			registerTCObjectTag(TFItems.nagaScale, -1, (new AspectList()).add(Aspect.MOTION, 2).add(Aspect.ARMOR, 3));
			registerTCObjectTag(TFItems.scepterTwilight, -1, (new AspectList()).add(Aspect.MAGIC, 8).add(Aspect.ELDRITCH, 8).add(Aspect.WEAPON, 8));
			registerTCObjectTag(TFItems.scepterLifeDrain, -1, (new AspectList()).add(Aspect.MAGIC, 8).add(Aspect.LIFE, 8).add(Aspect.HUNGER, 8));
			registerTCObjectTag(TFItems.scepterZombie, -1, (new AspectList()).add(Aspect.MAGIC, 8).add(Aspect.UNDEAD, 8).add(Aspect.ENTROPY, 8));
			registerTCObjectTag(TFItems.magicMapFocus, -1, (new AspectList()).add(Aspect.MAGIC, 4).add(Aspect.SENSES, 8));
			registerTCObjectTag(TFItems.mazeMapFocus, -1, (new AspectList()).add(Aspect.MAGIC, 4).add(Aspect.SENSES, 8).add(Aspect.ORDER, 4));
			registerTCObjectTag(TFItems.feather, -1, (new AspectList()).add(Aspect.FLIGHT, 2).add(Aspect.AIR, 1).add(Aspect.DARKNESS, 1));
			registerTCObjectTag(TFItems.liveRoot, -1, (new AspectList()).add(Aspect.MAGIC, 1).add(Aspect.TREE, 2).add(Aspect.LIFE, 2));
			registerTCObjectTag(TFItems.ironwoodIngot, -1, (new AspectList()).add(Aspect.MAGIC, 2).add(Aspect.TREE, 1).add(Aspect.METAL, 4).add(Aspect.CRAFT, 2));
			registerTCObjectTag(TFItems.torchberries, -1, (new AspectList()).add(Aspect.PLANT, 2).add(Aspect.LIGHT, 2));
			registerTCObjectTag(TFItems.fieryBlood, -1, (new AspectList()).add(Aspect.FIRE, 8).add(Aspect.LIFE, 8).add(Aspect.MAGIC, 4));
			registerTCObjectTag(TFItems.trophy, -1, (new AspectList()).add(Aspect.LIFE, 6).add(Aspect.BEAST, 6).add(Aspect.SOUL, 6));
			registerTCObjectTag(TFItems.steeleafIngot, -1, (new AspectList()).add(Aspect.PLANT, 4).add(Aspect.METAL, 2));
			registerTCObjectTag(TFItems.minotaurAxe, -1, (new AspectList()).add(Aspect.TOOL, 2).add(Aspect.WEAPON, 4).add(Aspect.CRYSTAL, 6).add(Aspect.GREED, 8));
			registerTCObjectTag(TFItems.mazebreakerPick, -1, (new AspectList()).add(Aspect.CRYSTAL, 6).add(Aspect.TOOL, 8).add(Aspect.MINE, 8));
			registerTCObjectTag(TFItems.oreMagnet, -1, (new AspectList()).add(Aspect.GREED, 10).add(Aspect.TOOL, 6).add(Aspect.METAL, 8).add(Aspect.MOTION, 6));
			registerTCObjectTag(TFItems.crumbleHorn, -1, (new AspectList()).add(Aspect.ENTROPY, 12).add(Aspect.BEAST, 2));
			registerTCObjectTag(TFItems.peacockFan, -1, (new AspectList()).add(Aspect.AIR, 8).add(Aspect.MOTION, 6).add(Aspect.FLIGHT, 10));
			registerTCObjectTag(TFItems.moonwormQueen, -1, (new AspectList()).add(Aspect.LIGHT, 12).add(Aspect.MAGIC, 1));
			registerTCObjectTag(TFItems.charmOfLife1, -1, (new AspectList()).add(Aspect.LIFE, 2).add(Aspect.HEAL, 2).add(Aspect.GREED, 4));
			registerTCObjectTag(TFItems.charmOfKeeping1, -1, (new AspectList()).add(Aspect.DEATH, 1).add(Aspect.TRAVEL, 2).add(Aspect.GREED, 4));
			registerTCObjectTag(TFItems.towerKey, -1, (new AspectList()).add(Aspect.MECHANISM, 4).add(Aspect.MAGIC, 4));
			registerTCObjectTag(TFItems.transformPowder, -1, (new AspectList()).add(Aspect.MAGIC, 8).add(Aspect.EXCHANGE, 4));
			registerTCObjectTag(TFItems.borerEssence, -1, (new AspectList()).add(Aspect.BEAST, 2).add(Aspect.TREE, 2).add(Aspect.SOUL, 4).add(Aspect.MAGIC, 2));
			registerTCObjectTag(TFItems.armorShard, -1, (new AspectList()).add(Aspect.METAL, 1));
			registerTCObjectTag(TFItems.knightMetal, -1, (new AspectList()).add(Aspect.METAL, 8).add(Aspect.ORDER, 1));
			registerTCObjectTag(TFItems.phantomHelm, -1, (new AspectList()).add(Aspect.METAL, 6).add(Aspect.ARMOR, 6).add(Aspect.UNDEAD, 6));
			registerTCObjectTag(TFItems.phantomPlate, -1, (new AspectList()).add(Aspect.METAL, 8).add(Aspect.ARMOR, 8).add(Aspect.UNDEAD, 8));
			registerTCObjectTag(TFItems.armorShard, -1, (new AspectList()).add(Aspect.METAL, 1));
			registerTCObjectTag(TFItems.lampOfCinders, -1, (new AspectList()).add(Aspect.FIRE, 4).add(Aspect.MAGIC, 4).add(Aspect.TOOL, 4));
			registerTCObjectTag(TFItems.fieryTears, -1, (new AspectList()).add(Aspect.FIRE, 8).add(Aspect.LIFE, 8).add(Aspect.MAGIC, 4));
			registerTCObjectTag(TFItems.alphaFur, -1, (new AspectList()).add(Aspect.COLD, 3).add(Aspect.BEAST, 3).add(Aspect.MAGIC, 4).add(Aspect.ARMOR, 1));
			registerTCObjectTag(TFItems.iceBomb, -1, (new AspectList()).add(Aspect.COLD, 3).add(Aspect.AIR, 1));
			registerTCObjectTag(TFItems.arcticFur, -1, (new AspectList()).add(Aspect.COLD, 3).add(Aspect.BEAST, 3));
			registerTCObjectTag(TFItems.tripleBow, -1, (new AspectList()).add(Aspect.TREE, 6).add(Aspect.BEAST, 6).add(Aspect.CLOTH, 6).add(Aspect.WEAPON, 9).add(Aspect.AIR, 3));
			registerTCObjectTag(TFItems.seekerBow, -1, (new AspectList()).add(Aspect.MIND, 3).add(Aspect.BEAST, 2).add(Aspect.CLOTH, 2).add(Aspect.WEAPON, 3).add(Aspect.AIR, 1));
			registerTCObjectTag(TFItems.iceBow, -1, (new AspectList()).add(Aspect.COLD, 2).add(Aspect.BEAST, 2).add(Aspect.CLOTH, 2).add(Aspect.WEAPON, 3).add(Aspect.AIR, 1));
			registerTCObjectTag(TFItems.enderBow, -1, (new AspectList()).add(Aspect.TRAVEL, 2).add(Aspect.BEAST, 2).add(Aspect.CLOTH, 2).add(Aspect.WEAPON, 3).add(Aspect.AIR, 1));
			registerTCObjectTag(TFItems.iceSword, -1, (new AspectList()).add(Aspect.WEAPON, 4).add(Aspect.CRYSTAL, 4).add(Aspect.COLD, 4));
			registerTCObjectTag(TFItems.glassSword, -1, (new AspectList()).add(Aspect.WEAPON, 5).add(Aspect.CRYSTAL, 4));
			registerTCObjectTag(TFItems.cubeTalisman, -1, (new AspectList()).add(Aspect.VOID, 4).add(Aspect.MAGIC, 4).add(Aspect.ENTROPY, 4));
			registerTCObjectTag(TFItems.cubeOfAnnihilation, -1, (new AspectList()).add(Aspect.VOID, 7).add(Aspect.MAGIC, 7).add(Aspect.ENTROPY, 7));
			
			// food
			registerTCObjectTag(TFItems.venisonRaw, -1, (new AspectList()).add(Aspect.HUNGER, 2).add(Aspect.FLESH, 4).add(Aspect.BEAST, 2));
			registerTCObjectTag(TFItems.venisonCooked, -1, (new AspectList()).add(Aspect.HUNGER, 4).add(Aspect.FLESH, 4).add(Aspect.CRAFT, 1));
			registerTCObjectTag(TFItems.hydraChop, -1, (new AspectList()).add(Aspect.HUNGER, 6).add(Aspect.FLESH, 6).add(Aspect.LIFE, 4));
			registerTCObjectTag(TFItems.meefRaw, -1, (new AspectList()).add(Aspect.BEAST, 2).add(Aspect.FLESH, 4).add(Aspect.LIFE, 2));
			registerTCObjectTag(TFItems.meefSteak, -1, (new AspectList()).add(Aspect.FIRE, 1).add(Aspect.BEAST, 1).add(Aspect.FLESH, 4).add(Aspect.LIFE, 2));
			registerTCObjectTag(TFItems.meefStroganoff, -1, (new AspectList()).add(Aspect.HUNGER, 4).add(Aspect.BEAST, 2).add(Aspect.FLESH, 4));
			registerTCObjectTag(TFItems.mazeWafer, -1, (new AspectList()).add(Aspect.HUNGER, 2));
			registerTCObjectTag(TFItems.experiment115, -1, (new AspectList()).add(Aspect.HUNGER, 3).add(Aspect.MECHANISM, 1));

			// blocks
			registerTCObjectTag(TFBlocks.firefly, -1, (new AspectList()).add(Aspect.FLIGHT, 1).add(Aspect.LIGHT, 2));
			registerTCObjectTag(TFBlocks.leaves, -1, (new AspectList()).add(Aspect.PLANT, 2));
			registerTCObjectTag(TFBlocks.mazestone, -1, (new AspectList()).add(Aspect.ORDER, 2).add(Aspect.TRAP, 1).add(Aspect.ARMOR, 1));
			registerTCObjectTag(TFBlocks.hedge, 0, (new AspectList()).add(Aspect.PLANT, 1).add(Aspect.WEAPON, 1));
			registerTCObjectTag(TFBlocks.hedge, 1, (new AspectList()).add(Aspect.PLANT, 3).add(Aspect.DARKNESS, 1));
			registerTCObjectTag(TFBlocks.root, -1, (new AspectList()).add(Aspect.TREE, 2));
			registerTCObjectTag(TFBlocks.cicada, -1, (new AspectList()).add(Aspect.SENSES, 2));
			registerTCObjectTag(TFBlocks.uncraftingTable, -1, (new AspectList()).add(Aspect.TREE, 4).add(Aspect.ENTROPY, 8).add(Aspect.EXCHANGE, 12).add(Aspect.CRAFT, 16));
			registerTCObjectTag(TFBlocks.fireJet, -1, (new AspectList()).add(Aspect.FIRE, 4).add(Aspect.AIR, 2).add(Aspect.MOTION, 2));
			registerTCObjectTag(TFBlocks.nagastone, -1, (new AspectList()).add(Aspect.ORDER, 2).add(Aspect.MOTION, 2));
			registerTCObjectTag(TFBlocks.magicLeaves, -1, (new AspectList()).add(Aspect.PLANT, 2));
			registerTCObjectTag(TFBlocks.towerWood, -1, (new AspectList()).add(Aspect.TREE, 2).add(Aspect.MECHANISM, 2));
			registerTCObjectTag(TFBlocks.towerDevice, -1, (new AspectList()).add(Aspect.TREE, 4).add(Aspect.MECHANISM, 4).add(Aspect.MAGIC, 4));
			registerTCObjectTag(TFBlocks.towerTranslucent, -1, (new AspectList()).add(Aspect.TREE, 4).add(Aspect.MECHANISM, 4).add(Aspect.MAGIC, 4).add(Aspect.VOID, 2));
			registerTCObjectTag(TFBlocks.trophy, -1, (new AspectList()).add(Aspect.LIFE, 6).add(Aspect.BEAST, 6).add(Aspect.SOUL, 6));
			registerTCObjectTag(TFBlocks.plant, 3, (new AspectList()).add(Aspect.PLANT, 1));
			registerTCObjectTag(TFBlocks.plant, 4, (new AspectList()).add(Aspect.PLANT, 1));
			registerTCObjectTag(TFBlocks.plant, 5, (new AspectList()).add(Aspect.PLANT, 2));
			registerTCObjectTag(TFBlocks.plant, 8, (new AspectList()).add(Aspect.PLANT, 2).add(Aspect.MAGIC, 1));
			registerTCObjectTag(TFBlocks.plant, 9, (new AspectList()).add(Aspect.PLANT, 2).add(Aspect.DARKNESS, 1).add(Aspect.LIGHT, 1));
			registerTCObjectTag(TFBlocks.plant, 10, (new AspectList()).add(Aspect.PLANT, 2).add(Aspect.DARKNESS, 1));
			registerTCObjectTag(TFBlocks.plant, 11, (new AspectList()).add(Aspect.PLANT, 2).add(Aspect.DARKNESS, 1));
			registerTCObjectTag(TFBlocks.plant, 13, (new AspectList()).add(Aspect.PLANT, 2).add(Aspect.LIGHT, 2));
			registerTCObjectTag(TFBlocks.plant, 14, (new AspectList()).add(Aspect.PLANT, 2));
			registerTCObjectTag(TFBlocks.sapling, -1, (new AspectList()).add(Aspect.PLANT, 4).add(Aspect.TREE, 2));
			registerTCObjectTag(TFBlocks.moonworm, -1, (new AspectList()).add(Aspect.DARKNESS, 2).add(Aspect.LIGHT, 2));
			registerTCObjectTag(TFBlocks.shield, -1, (new AspectList()).add(Aspect.TRAP, 1).add(Aspect.MAGIC, 1).add(Aspect.ARMOR, 1));
			registerTCObjectTag(TFBlocks.trophyPedestal, -1, (new AspectList()).add(Aspect.GREED, 6).add(Aspect.BEAST, 5));
			registerTCObjectTag(TFBlocks.auroraBlock, -1, (new AspectList()).add(Aspect.COLD, 2).add(Aspect.CRYSTAL, 2));
			registerTCObjectTag(TFBlocks.underBrick, -1, (new AspectList()).add(Aspect.DARKNESS, 2).add(Aspect.EARTH, 2));
			registerTCObjectTag(TFBlocks.portal, -1, (new AspectList()).add(Aspect.MAGIC, 1).add(Aspect.MOTION, 2));
			registerTCObjectTag(TFBlocks.trophy, -1, (new AspectList()).add(Aspect.PLANT, 2).add(Aspect.LIGHT, 10));
			registerTCObjectTag(TFBlocks.shield, -1, (new AspectList()).add(Aspect.EARTH, 1).add(Aspect.ORDER, 2).add(Aspect.ARMOR, 2));
			registerTCObjectTag(TFBlocks.thorns, -1, (new AspectList()).add(Aspect.PLANT, 3).add(Aspect.ENTROPY, 2).add(Aspect.TRAP, 2));
			registerTCObjectTag(TFBlocks.thornRose, -1, (new AspectList()).add(Aspect.PLANT, 1).add(Aspect.TRAP, 1).add(Aspect.SENSES, 2));
			registerTCObjectTag(TFBlocks.burntThorns, -1, (new AspectList()).add(Aspect.ENTROPY, 2).add(Aspect.FIRE, 1));
			registerTCObjectTag(TFBlocks.leaves3, -1, (new AspectList()).add(Aspect.PLANT, 2));
			registerTCObjectTag(TFBlocks.deadrock, -1, (new AspectList()).add(Aspect.EARTH, 1).add(Aspect.ENTROPY, 1).add(Aspect.DEATH, 1));
			registerTCObjectTag(TFBlocks.darkleaves, -1, (new AspectList()).add(Aspect.PLANT, 2).add(Aspect.DARKNESS, 2));
			registerTCObjectTag(TFBlocks.auroraPillar, -1, (new AspectList()).add(Aspect.COLD, 2).add(Aspect.ORDER, 2));
			registerTCObjectTag(TFBlocks.auroraSlab, -1, (new AspectList()).add(Aspect.COLD, 2).add(Aspect.ORDER, 2));
			registerTCObjectTag(TFBlocks.auroraDoubleSlab, -1, (new AspectList()).add(Aspect.COLD, 2).add(Aspect.ORDER, 2));
			registerTCObjectTag(TFBlocks.trollSteinn, -1, (new AspectList()).add(Aspect.EARTH, 1).add(Aspect.ORDER, 1).add(Aspect.CRYSTAL, 1));
			registerTCObjectTag(TFBlocks.wispyCloud, -1, (new AspectList()).add(Aspect.WEATHER, 1).add(Aspect.AIR, 1).add(Aspect.FLIGHT, 1));
			registerTCObjectTag(TFBlocks.fluffyCloud, -1, (new AspectList()).add(Aspect.WEATHER, 2).add(Aspect.AIR, 2));
			registerTCObjectTag(TFBlocks.giantCobble, -1, (new AspectList()).add(Aspect.EARTH, 8).add(Aspect.ENTROPY, 8));
			registerTCObjectTag(TFBlocks.giantLog, -1, (new AspectList()).add(Aspect.TREE, 32));
			registerTCObjectTag(TFBlocks.giantLeaves, -1, (new AspectList()).add(Aspect.PLANT, 32));
			registerTCObjectTag(TFBlocks.giantObsidian, -1, (new AspectList()).add(Aspect.FIRE, 16).add(Aspect.DARKNESS, 8).add(Aspect.EARTH, 16));
			registerTCObjectTag(TFBlocks.uberousSoil, -1, (new AspectList()).add(Aspect.EARTH, 4).add(Aspect.SENSES, 2).add(Aspect.PLANT, 2));
			registerTCObjectTag(TFBlocks.hugeStalk, -1, (new AspectList()).add(Aspect.PLANT, 2).add(Aspect.TREE, 2));
			registerTCObjectTag(TFBlocks.hugeGloomBlock, -1, (new AspectList()).add(Aspect.PLANT, 2).add(Aspect.DARKNESS, 1).add(Aspect.LIGHT, 1));
			registerTCObjectTag(TFBlocks.trollVidr, -1, (new AspectList()).add(Aspect.PLANT, 2));
			registerTCObjectTag(TFBlocks.unripeTrollBer, -1, (new AspectList()).add(Aspect.PLANT, 2).add(Aspect.DARKNESS, 1));
			registerTCObjectTag(TFBlocks.trollBer, -1, (new AspectList()).add(Aspect.PLANT, 2).add(Aspect.LIGHT, 4));
			registerTCObjectTag(TFBlocks.knightmetalStorage, -1, (new AspectList()).add(Aspect.METAL, 12).add(Aspect.ORDER, 12));
			registerTCObjectTag(TFBlocks.hugeLilyPad, -1, (new AspectList()).add(Aspect.WATER, 3).add(Aspect.PLANT, 6));
			registerTCObjectTag(TFBlocks.hugeWaterLily, -1, (new AspectList()).add(Aspect.WATER, 2).add(Aspect.PLANT, 2).add(Aspect.SENSES, 2));
			registerTCObjectTag(TFBlocks.slider, -1, (new AspectList()).add(Aspect.MOTION, 4).add(Aspect.TRAP, 6));
			registerTCObjectTag(TFBlocks.castleBlock, -1, (new AspectList()).add(Aspect.ORDER, 2));
			registerTCObjectTag(TFBlocks.castleMagic, -1, (new AspectList()).add(Aspect.ORDER, 2).add(Aspect.MAGIC, 3).add(Aspect.ENERGY, 2));
			registerTCObjectTag(TFBlocks.forceField, -1, (new AspectList()).add(Aspect.MAGIC, 3).add(Aspect.ARMOR, 4));
			
			FMLLog.info("[TwilightForest] Loaded ThaumcraftApi integration.");
			
			
		} 
		catch (Exception e) 
		{
			FMLLog.warning("[TwilightForest] Had an %s error while trying to register with ThaumcraftApi.", e.getLocalizedMessage());
			// whatever.
		}

	}
	
	/**
	 * Register a block with Thaumcraft aspects
	 */
	private void registerTCObjectTag(Block block, int meta, AspectList list) {
		if (meta == -1) {
			meta = OreDictionary.WILDCARD_VALUE;
		}		
		ThaumcraftApi.registerObjectTag(new ItemStack(block, 1, meta), list);
	}
	
	/**
	 * Register an item with Thaumcraft aspects
	 */
	private void registerTCObjectTag(Item item, int meta, AspectList list) {
		if (meta == -1) {
			meta = OreDictionary.WILDCARD_VALUE;
		}	
		ThaumcraftApi.registerObjectTag(new ItemStack(item, 1, meta), list);
	}


	/**
	 * Register all dispenser behaviors.
	 */
	private void registerDispenseBehaviors(MinecraftServer minecraftServer)
	{
		BlockDispenser.dispenseBehaviorRegistry.putObject(TFItems.spawnEgg, new BehaviorTFMobEggDispense(minecraftServer));
	}
	
	/**
	 * Load our config file and set default values
	 */
	private void loadConfiguration(Configuration configFile) {
		configFile.load();
		
		dimensionID = configFile.get("dimension", "dimensionID", 7).getInt();
		configFile.get("dimension", "dimensionID", 7).comment = "What ID number to assign to the Twilight Forest dimension.  Change if you are having conflicts with another mod.";
		
		dimensionProviderID = configFile.get("dimension", "dimensionProviderID", -777).getInt();
		configFile.get("dimension", "dimensionProviderID", 7).comment = "Dimension provider ID.  Does not normally need to be changed, but the option is provided to work around a bug in MCPC+";

	    // other misc otions
	    silentCicadas = configFile.get(Configuration.CATEGORY_GENERAL, "SilentCicadas", false).getBoolean(false);
		configFile.get(Configuration.CATEGORY_GENERAL, "SilentCicadas", false).comment = "Make cicadas silent  for those having sound library problems, or otherwise finding them annoying";
	    allowPortalsInOtherDimensions = configFile.get(Configuration.CATEGORY_GENERAL, "AllowPortalsInOtherDimensions", false).getBoolean(false);
	    configFile.get(Configuration.CATEGORY_GENERAL, "AllowPortalsInOtherDimensions", false).comment = "Allow portals to the Twilight Forest to be made outside of dimension 0.  May be considered an exploit.";
	    adminOnlyPortals = configFile.get(Configuration.CATEGORY_GENERAL, "AdminOnlyPortals", false).getBoolean(false);
	    configFile.get(Configuration.CATEGORY_GENERAL, "AdminOnlyPortals", false).comment = "Allow portals only for admins (ops).  This severly reduces the range in which the mod usually scans for valid portal conditions, and it scans near ops only.";
	    twilightForestSeed = configFile.get(Configuration.CATEGORY_GENERAL, "TwilightForestSeed", "").getString();
	    configFile.get(Configuration.CATEGORY_GENERAL, "TwilightForestSeed", "").comment = "If set, this will override the normal world seed when generating parts of the Twilight Forest Dimension.";
	    disablePortalCreation = configFile.get(Configuration.CATEGORY_GENERAL, "DisablePortalCreation", false).getBoolean(false);
	    configFile.get(Configuration.CATEGORY_GENERAL, "DisablePortalCreation", false).comment = "Disable Twilight Forest portal creation entirely.  Provided for server operators looking to restrict action to the dimension.";
	    disableUncrafting = configFile.get(Configuration.CATEGORY_GENERAL, "DisableUncrafting", false).getBoolean(false);
	    configFile.get(Configuration.CATEGORY_GENERAL, "DisableUncrafting", false).comment = "Disable the uncrafting function of the uncrafting table.  Provided as an option when interaction with other mods produces exploitable recipes.";
	    oldMapGen = configFile.get(Configuration.CATEGORY_GENERAL, "OldMapGen", false).getBoolean(false);
	    configFile.get(Configuration.CATEGORY_GENERAL, "OldMapGen", false).comment = "Use old (pre Minecraft 1.7) map gen.  May not be fully supported.";
	    portalCreationItemString = configFile.get(Configuration.CATEGORY_GENERAL, "PortalCreationItem", "diamond").getString();
	    configFile.get(Configuration.CATEGORY_GENERAL, "PortalCreationItem", "diamond").comment = "Item to create the Twilight Forest Portal.  Defaults to 'diamond'";

	    canopyCoverage = (float) (configFile.get("Performance", "CanopyCoverage", 1.7).getDouble(1.7));
		configFile.get("performance", "CanopyCoverage", 1.7).comment = "Amount of canopy coverage, from 0.0 on up.  Lower numbers improve chunk generation speed at the cost of a thinner forest.";
		twilightOakChance = configFile.get("Performance", "TwilightOakChance", 48).getInt(48);
		configFile.get("Performance", "TwilightOakChance", 48).comment = "Chance that a chunk in the Twilight Forest will contain a twilight oak tree.  Higher numbers reduce the number of trees, increasing performance.";

    	// fixed values, don't even read the config
    	idMobWildBoar = 177;
    	idMobBighornSheep = 178;
    	idMobWildDeer = 179;
    	idMobRedcap = 180;
    	idMobSwarmSpider = 181;
    	idMobNaga = 182;
    	idMobNagaSegment = 183;
    	idMobSkeletonDruid = 184;
    	idMobHostileWolf = 185;
    	idMobTwilightWraith = 186;
    	idMobHedgeSpider = 187;
    	idMobHydra = 189;
    	idMobLich = 190;
    	idMobPenguin = 191;
    	idMobLichMinion = 192;
    	idMobLoyalZombie = 193;
    	idMobTinyBird = 194;
    	idMobSquirrel = 195;
    	idMobBunny = 196;
    	idMobRaven = 197;
    	idMobQuestRam = 198;
    	idMobKobold = 199;
    	idMobBoggard = 201;
    	idMobMosquitoSwarm = 202;
    	idMobDeathTome = 203;
    	idMobMinotaur = 204;
    	idMobMinoshroom = 205;
    	idMobFireBeetle = 206;
    	idMobSlimeBeetle = 207;
    	idMobPinchBeetle = 208;
    	idMobMazeSlime = 209;
    	idMobRedcapSapper = 210;
    	idMobMistWolf = 211;
    	idMobKingSpider = 212;
    	idMobFirefly = 213;
    	idMobMiniGhast = 214;
    	idMobTowerGhast = 215;
    	idMobTowerGolem = 216;
    	idMobTowerTermite = 218;
    	idMobTowerBroodling = 219;
    	idMobTowerBoss = 217;
    	idMobBlockGoblin = 220;
    	idMobGoblinKnightUpper = 221;
    	idMobGoblinKnightLower = 222;
    	idMobHelmetCrab = 223;
    	idMobKnightPhantom = 224;
    	idMobYeti = 225;
    	idMobYetiBoss = 226;
    	idMobWinterWolf = 227;
    	idMobSnowGuardian = 228;
    	idMobStableIceCore = 229;
    	idMobUnstableIceCore = 230;
    	idMobSnowQueen = 231;
    	idMobTroll = 232;
    	idMobGiantMiner = 233;
    	idMobArmoredGiant = 234;
    	idMobIceCrystal = 235;
    	idMobHarbingerCube = 236;
    	idMobAdherent = 237;
	    
	    // biomes
	    idBiomeLake = configFile.get("biome", "biome.id.Lake", -1).getInt();
	    idBiomeTwilightForest = configFile.get("biome", "biome.id.TwilightForest", -1).getInt();
	    idBiomeTwilightForestVariant = configFile.get("biome", "biome.id.TwilightForestVariant", -1).getInt();
	    idBiomeHighlands = configFile.get("biome", "biome.id.Highlands", -1).getInt();
	    idBiomeMushrooms = configFile.get("biome", "biome.id.Mushrooms", -1).getInt();
	    idBiomeSwamp = configFile.get("biome", "biome.id.Swamp", -1).getInt();
	    idBiomeStream = configFile.get("biome", "biome.id.Stream", -1).getInt();
	    idBiomeSnowfield = configFile.get("biome", "biome.id.Snowfield", -1).getInt();
	    idBiomeGlacier = configFile.get("biome", "biome.id.Glacier", -1).getInt();
	    idBiomeClearing = configFile.get("biome", "biome.id.Clearing", -1).getInt();
	    idBiomeOakSavanna = configFile.get("biome", "biome.id.OakSavanna", -1).getInt();
	    idBiomeFireflyForest = configFile.get("biome", "biome.id.LightedForest", -1).getInt();
	    idBiomeDeepMushrooms = configFile.get("biome", "biome.id.DeepMushrooms", -1).getInt();
	    idBiomeDarkForestCenter = configFile.get("biome", "biome.id.DarkForestCenter", -1).getInt();
	    idBiomeHighlandsCenter = configFile.get("biome", "biome.id.HighlandsCenter", -1).getInt();
	    idBiomeDarkForest = configFile.get("biome", "biome.id.DarkForest", -1).getInt();
	    idBiomeEnchantedForest = configFile.get("biome", "biome.id.EnchantedForest", -1).getInt();
	    idBiomeFireSwamp = configFile.get("biome", "biome.id.FireSwamp", -1).getInt();
	    idBiomeThornlands = configFile.get("biome", "biome.id.Thornlands", -1).getInt();

	    if (configFile.hasChanged()) {
	    	configFile.save();
	    }
	}

	/**
	 * Write changed biome IDs back to the config file
	 */
	private void saveBiomeIds(Configuration config) {
		config.get("biome", "biome.id.Lake", -1).set(idBiomeLake);
		config.get("biome", "biome.id.TwilightForest", -1).set(idBiomeTwilightForest);
		config.get("biome", "biome.id.TwilightForestVariant", -1).set(idBiomeTwilightForestVariant);
		config.get("biome", "biome.id.Highlands", -1).set(idBiomeHighlands);
		config.get("biome", "biome.id.Mushrooms", -1).set(idBiomeMushrooms);
		config.get("biome", "biome.id.Swamp", -1).set(idBiomeSwamp);
		config.get("biome", "biome.id.Stream", -1).set(idBiomeStream);
		config.get("biome", "biome.id.Snowfield", -1).set(idBiomeSnowfield);
		config.get("biome", "biome.id.Glacier", -1).set(idBiomeGlacier);
		config.get("biome", "biome.id.Clearing", -1).set(idBiomeClearing);
		config.get("biome", "biome.id.OakSavanna", -1).set(idBiomeOakSavanna);
		config.get("biome", "biome.id.LightedForest", -1).set(idBiomeFireflyForest);
		config.get("biome", "biome.id.DeepMushrooms", -1).set(idBiomeDeepMushrooms);
		config.get("biome", "biome.id.DarkForestCenter", -1).set(idBiomeDarkForestCenter);
		config.get("biome", "biome.id.HighlandsCenter", -1).set(idBiomeHighlandsCenter);
		config.get("biome", "biome.id.DarkForest", -1).set(idBiomeDarkForest);
		config.get("biome", "biome.id.EnchantedForest", -1).set(idBiomeEnchantedForest);
		config.get("biome", "biome.id.FireSwamp", -1).set(idBiomeFireSwamp);
		config.get("biome", "biome.id.Thornlands", -1).set(idBiomeThornlands);

		config.save();
	}

	/**
	 * Change what dimension ID the Twilight Forest is.  
	 * This is called when we connect to a server that has a different dimensionID set.
	 */
	public static void setDimensionID(int dim) 
	{
		if (TwilightForestMod.dimensionID != dim)
		{
			FMLLog.info("[TwilightForest] Server has a different dimension ID (%d) for the Twilight Forest.  Changing this on the client.  This change will not be saved.", dim);

			DimensionManager.unregisterDimension(TwilightForestMod.dimensionID);
			TwilightForestMod.dimensionID = dim;
			DimensionManager.registerDimension(TwilightForestMod.dimensionID, TwilightForestMod.dimensionProviderID);
		}
	}
}
